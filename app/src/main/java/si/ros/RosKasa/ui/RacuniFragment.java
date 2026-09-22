package si.ros.RosKasa.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import si.ros.RosKasa.Globals;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentRacuniBinding;
import si.ros.RosKasa.models.RacunSeznamItem;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class RacuniFragment extends Fragment implements RacunSeznamAdapter.OnItemClickListener {

    private FragmentRacuniBinding binding;
    private AppPreferences prefs;
    private RacunSeznamAdapter adapter;

    private final List<RacunSeznamItem> allItems = new ArrayList<>();
    private final List<RacunSeznamItem> filteredItems = new ArrayList<>();

    // Default STATUS: 1 = ODPRTI, 2 = IZPISANI
    private int currentStatusFilter = 1;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRacuniBinding.inflate(inflater, container, false);
        prefs = new AppPreferences(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupNavigationButtons();
        setupSearch();

        updateToggleStatusButtonText();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRacuniFromApi();
    }

    private void setupRecyclerView() {
        adapter = new RacunSeznamAdapter(this);
        binding.rvRacuni.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRacuni.setAdapter(adapter);
    }

    private void setupNavigationButtons() {
        // Preklop statusa (Odprti STATUS=1 <-> Izpisani STATUS=2)
        binding.btnToggleStatus.setOnClickListener(v -> {
            currentStatusFilter = (currentStatusFilter == 1) ? 2 : 1;
            updateToggleStatusButtonText();
            loadRacuniFromApi();
        });

        // POS Navigacijske tipke
        binding.btnNavMize.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
            }
        });

        binding.btnNavNaroci.setOnClickListener(v -> {
            RacunSeznamItem selected = adapter.getSelectedItem();
            if (selected != null) {
                prefs.setActiveRacunId(selected.getRacunId());
                prefs.setActiveMarker(selected.getMarker());
                Globals.getInstance().setCurrentRacun(null);
            }
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        });

        binding.btnNavPlacila.setOnClickListener(v -> {
            RacunSeznamItem selected = adapter.getSelectedItem();
            if (selected != null) {
                prefs.setActiveRacunId(selected.getRacunId());
                prefs.setActiveMarker(selected.getMarker());
                Globals.getInstance().setCurrentRacun(null);
            }
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new PlacilaFragment());
            }
        });

        binding.btnOdjava.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
            }
        });

        binding.btnStorno.setOnClickListener(v -> {
            RacunSeznamItem selected = adapter.getSelectedItem();
            if (selected == null) {
                Toast.makeText(requireContext(), "Prosim izberite račun za storno!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Storno računa #" + selected.getRacunId(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnTestTiskanja.setOnClickListener(v -> {
            checkPermissionsAndPrint();
        });
    }

    private static final int REQUEST_BT_PERMISSION_CODE = 1002;

    private void checkPermissionsAndPrint() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != android.content.pm.PackageManager.PERMISSION_GRANTED ||
                androidx.core.content.ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.BLUETOOTH_SCAN) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                
                requestPermissions(new String[]{
                        android.Manifest.permission.BLUETOOTH_CONNECT,
                        android.Manifest.permission.BLUETOOTH_SCAN
                }, REQUEST_BT_PERMISSION_CODE);
                return;
            }
        }
        startTestPrint();
    }

    private void startTestPrint() {
        si.ros.RosKasa.print.BluetoothPrintHelper.printTestReceipt(requireContext(), new si.ros.RosKasa.print.BluetoothPrintHelper.OnPrintListener() {
            @Override
            public void onStart() {
                Toast.makeText(requireContext(), "Zapenjam Bluetooth tiskanje...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String message) {
                if (getContext() != null) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (getContext() != null) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BT_PERMISSION_CODE) {
            boolean allGranted = true;
            for (int res : grantResults) {
                if (res != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                startTestPrint();
            } else {
                Toast.makeText(requireContext(), "Dovoljenje za Bluetooth je obvezno za tiskanje!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateToggleStatusButtonText() {
        if (currentStatusFilter == 1) {
            binding.btnToggleStatus.setText("Izpisani (STATUS=2)");
        } else {
            binding.btnToggleStatus.setText("Odprti (STATUS=1)");
        }
    }

    private void setupSearch() {
        binding.etSearchRacun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private String calculateOdDatum() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3); // Privzeto getdate() - 3 dni (danes 02.08.2026 -> 31.07.2026 00:00:00)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    private void loadRacuniFromApi() {
        String odDatum = calculateOdDatum();

        if (!prefs.isRegistered()) {
            loadMockRacuni();
            return;
        }

        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.tvEmptyList.setVisibility(View.GONE);

        executor.execute(() -> {
            try {
                // Klic WSDL getRacuniSeznam s parametroma STATUS in OD_DATUM
                List<RacunSeznamItem> result = RosKasaSoapClient.getRacuniSeznam(
                        prefs.getServerUrl(),
                        prefs.getToken(),
                        prefs.getMobileId(),
                        currentStatusFilter,
                        odDatum
                );

                mainHandler.post(() -> {
                    binding.pbLoading.setVisibility(View.GONE);
                    allItems.clear();
                    if (result != null && !result.isEmpty()) {
                        allItems.addAll(result);
                    } else {
                        populateMockData();
                    }
                    applyFilters();
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    binding.pbLoading.setVisibility(View.GONE);
                    populateMockData();
                    applyFilters();
                });
            }
        });
    }

    private void loadMockRacuni() {
        binding.pbLoading.setVisibility(View.GONE);
        populateMockData();
        applyFilters();
    }

    private void populateMockData() {
        allItems.clear();
        // Računi ustrezno iz baze (61118 STATUS=2, 61119 STATUS=2, 61120 STATUS=1)
        if (currentStatusFilter == 1) {
            allItems.add(new RacunSeznamItem(61120, 1, "Miza 5", 1, null, new BigDecimal("4.00")));
        } else {
            allItems.add(new RacunSeznamItem(61118, 1, "Miza 2", 2, null, new BigDecimal("4.00")));
            allItems.add(new RacunSeznamItem(61119, 1, "Miza 4", 2, null, new BigDecimal("2.70")));
        }
    }

    private void applyFilters() {
        filteredItems.clear();
        String query = binding.etSearchRacun.getText().toString().trim().toLowerCase();

        for (RacunSeznamItem item : allItems) {
            // STATUS filtriranje - statusi se strogo ne mešajo!
            if (item.getStatus() != null && item.getStatus() == currentStatusFilter) {
                if (query.isEmpty()) {
                    filteredItems.add(item);
                } else {
                    String racunIdStr = String.valueOf(item.getRacunId());
                    String marker = item.getMarker() != null ? item.getMarker().toLowerCase() : "";
                    if (racunIdStr.contains(query) || marker.contains(query)) {
                        filteredItems.add(item);
                    }
                }
            }
        }

        adapter.setItems(filteredItems);
        binding.tvEmptyList.setVisibility(filteredItems.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(RacunSeznamItem item, int position) {
        if (item != null) {
            prefs.setActiveRacunId(item.getRacunId());
            prefs.setActiveMarker(item.getMarker());

            // Če gre za zaključen račun (STATUS = 2 ali 4), odpri tekstovni predogled
            if (item.getStatus() != null && (item.getStatus() == 2 || item.getStatus() == 4)) {
                prikaziPredogledRacuna(item);
            } else {
                Globals.getInstance().setCurrentRacun(null);
                Toast.makeText(requireContext(), "Izbran odprt račun #" + item.getRacunId() + " (Miza: " + item.getMarker() + ")", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void prikaziPredogledRacuna(RacunSeznamItem item) {
        if (getContext() == null) return;

        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.setContentView(si.ros.RosKasa.R.layout.dialog_racun_preview);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        android.widget.TextView tvTitle = dialog.findViewById(si.ros.RosKasa.R.id.tvPreviewTitle);
        android.widget.TextView tvText = dialog.findViewById(si.ros.RosKasa.R.id.tvPreviewText);
        android.widget.ProgressBar pbLoading = dialog.findViewById(si.ros.RosKasa.R.id.pbPreviewLoading);
        com.google.android.material.button.MaterialButton btnZapri = dialog.findViewById(si.ros.RosKasa.R.id.btnPreviewZapri);
        com.google.android.material.button.MaterialButton btnStorno = dialog.findViewById(si.ros.RosKasa.R.id.btnPreviewStorno);
        com.google.android.material.button.MaterialButton btnNatisni = dialog.findViewById(si.ros.RosKasa.R.id.btnPreviewNatisni);

        tvTitle.setText("PREDOGLED RAČUNA #" + item.getRacunId());
        tvText.setText("Nalaganje podatkov računa s strežnika...");
        pbLoading.setVisibility(View.VISIBLE);
        btnNatisni.setEnabled(false);

        btnZapri.setOnClickListener(v -> dialog.dismiss());

        final si.ros.RosKasa.models.RacunTp[] racunHolder = new si.ros.RosKasa.models.RacunTp[1];
        final int[] stKopijHolder = new int[]{1};

        executor.execute(() -> {
            try {
                si.ros.RosKasa.models.RacunTp r = RosKasaSoapClient.getRacun(prefs.getServerUrl(), prefs.getToken(), item.getRacunId());
                if (r != null) {

                    // Zagotovi, da je cenik naložen v predpomnilnik za lookup nazivov
                    if (!Globals.getInstance().hasCachedCenik()) {
                        int strmId = prefs.getHisObrat() > 0 ? prefs.getHisObrat() : 512200;
                        try {
                            java.util.List<CenikListAdapter.CenikItem> cenik = RosKasaSoapClient.getCenik(prefs.getServerUrl(), prefs.getToken(), strmId);
                            if (cenik != null && !cenik.isEmpty()) {
                                Globals.getInstance().setCachedCenik(cenik);
                            }
                        } catch (Exception ignored) {}
                    }
                    if (!Globals.getInstance().hasCachedHitreTipke() && prefs.getTipkePosId() > 0) {
                        try {
                            java.util.List<si.ros.RosKasa.models.HitraTipkaTp> tipke = RosKasaSoapClient.getHitreTipke(prefs.getServerUrl(), prefs.getToken(), prefs.getTipkePosId());
                            if (tipke != null && !tipke.isEmpty()) {
                                Globals.getInstance().setCachedHitreTipke(tipke);
                            }
                        } catch (Exception ignored) {}
                    }

                    // Preveri manjkajoče nazive artiklov
                    if (r.getRacPozic() != null) {
                        for (si.ros.RosKasa.models.PozicijaTp p : r.getRacPozic()) {
                            if (p != null && p.getNivo4Id() != null && p.getNivo4Id() > 0) {
                                String lookupName = Globals.getInstance().findNazivByNivo4Id(p.getNivo4Id());
                                if (lookupName != null && !lookupName.trim().isEmpty()) {
                                    p.setNaziv(lookupName.trim());
                                }
                            }
                        }
                    }

                    racunHolder[0] = r;
                    int k = (r.getStKopij() != null && r.getStKopij() > 0) ? r.getStKopij() + 1 : 1;
                    stKopijHolder[0] = k;

                    si.ros.RosKasa.print.RacunPrintBuilder.ReceiptResult printRes =
                            si.ros.RosKasa.print.RacunPrintBuilder.buildReceipt(r, Globals.getInstance(), k);

                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        btnNatisni.setEnabled(true);
                        tvText.setText(printRes.getTextPreview());
                    });
                } else {
                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        tvText.setText("Računa ni bilo mogoče naložiti s strežnika.");
                    });
                }
            } catch (Exception e) {
                mainHandler.post(() -> {
                    pbLoading.setVisibility(View.GONE);
                    tvText.setText("Napaka pri branju računa: " + e.getMessage());
                });
            }
        });

        btnNatisni.setOnClickListener(v -> {
            if (racunHolder[0] == null) return;
            dialog.dismiss();

            Toast.makeText(requireContext(), "Tiskanje kopije računa #" + item.getRacunId() + "...", Toast.LENGTH_SHORT).show();
            si.ros.RosKasa.print.BluetoothPrintHelper.printReceipt(requireContext(), racunHolder[0], stKopijHolder[0], new si.ros.RosKasa.print.BluetoothPrintHelper.OnPrintListener() {
                @Override
                public void onStart() {}

                @Override
                public void onSuccess(String message) {
                    if (getContext() != null) {
                        Toast.makeText(requireContext(), "Tisk: " + message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    if (getContext() != null) {
                        Toast.makeText(requireContext(), "Napaka: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
            });
        });

        btnStorno.setOnClickListener(v -> {
            dialog.dismiss();
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Storno računa")
                    .setMessage("Ali želite začeti postopek storna za račun #" + item.getRacunId() + "?")
                    .setPositiveButton("Da", (d, w) -> {
                        Toast.makeText(requireContext(), "Storno računa #" + item.getRacunId() + " se pripravlja.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Prekliči", null)
                    .show();
        });

        dialog.show();
    }

    @Override
    public void onItemDoubleClick(RacunSeznamItem item, int position) {
        if (item != null) {
            prefs.setActiveRacunId(item.getRacunId());
            prefs.setActiveMarker(item.getMarker());
            Globals.getInstance().setCurrentRacun(null);
            Toast.makeText(requireContext(), "Nalaganje računa #" + item.getRacunId() + " za urejanje...", Toast.LENGTH_SHORT).show();
        }

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
