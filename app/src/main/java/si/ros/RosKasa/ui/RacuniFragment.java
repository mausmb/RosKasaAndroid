package si.ros.RosKasa.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.R;

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
import si.ros.RosKasa.models.HitraTipkaTp;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunSeznamItem;
import si.ros.RosKasa.models.RacunTp;
import si.ros.RosKasa.print.BluetoothPrintHelper;
import si.ros.RosKasa.print.RacunPrintBuilder;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class RacuniFragment extends Fragment implements RacunSeznamAdapter.OnItemClickListener {

    private FragmentRacuniBinding binding;
    private AppPreferences prefs;
    private RacunSeznamAdapter adapter;

    private final List<RacunSeznamItem> allItems = new ArrayList<>();
    private final List<RacunSeznamItem> filteredItems = new ArrayList<>();

    // Default STATUS: 1 = ODPRTI, 2 = IZPISANI
    private int currentStatusFilter = 1;

    private final ActivityResultLauncher<String[]> bluetoothPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                if (!result.containsValue(false)) {
                    startTestPrint();
                } else {
                    Toast.makeText(requireContext(), "Dovoljenje za Bluetooth je obvezno za tiskanje!", Toast.LENGTH_LONG).show();
                }
            });

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

        binding.btnTestTiskanja.setOnClickListener(v -> checkPermissionsAndPrint());
    }

    private void checkPermissionsAndPrint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

                bluetoothPermissionLauncher.launch(new String[]{
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                });
                return;
            }
        }
        startTestPrint();
    }

    private void startTestPrint() {
        BluetoothPrintHelper.printTestReceipt(requireContext(), new BluetoothPrintHelper.OnPrintListener() {
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

    private void updateToggleStatusButtonText() {
        if (currentStatusFilter == 1) {
            binding.btnToggleStatus.setText(R.string.btn_toggle_status_izpisani);
        } else {
            binding.btnToggleStatus.setText(R.string.btn_toggle_status_odprti);
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
            binding.pbLoading.setVisibility(View.GONE);
            allItems.clear();
            applyFilters();
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
                    if (!result.isEmpty()) {
                        allItems.addAll(result);
                    }
                    applyFilters();
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    binding.pbLoading.setVisibility(View.GONE);
                    allItems.clear();
                    applyFilters();
                });
            }
        });
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

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_racun_preview);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView tvTitle = dialog.findViewById(R.id.tvPreviewTitle);
        TextView tvText = dialog.findViewById(R.id.tvPreviewText);
        ProgressBar pbLoading = dialog.findViewById(R.id.pbPreviewLoading);
        MaterialButton btnZapri = dialog.findViewById(R.id.btnPreviewZapri);
        MaterialButton btnStorno = dialog.findViewById(R.id.btnPreviewStorno);
        MaterialButton btnNatisni = dialog.findViewById(R.id.btnPreviewNatisni);

        tvTitle.setText(getString(R.string.preview_racun_title, item.getRacunId()));
        tvText.setText(R.string.preview_loading_data);
        pbLoading.setVisibility(View.VISIBLE);
        btnNatisni.setEnabled(false);

        btnZapri.setOnClickListener(v -> dialog.dismiss());

        final RacunTp[] racunHolder = new RacunTp[1];
        final int[] stKopijHolder = new int[]{1};

        executor.execute(() -> {
            try {
                RacunTp r = RosKasaSoapClient.getRacun(prefs.getServerUrl(), prefs.getToken(), item.getRacunId());
                if (r != null) {

                    // Zagotovi, da je cenik naložen v predpomnilnik za lookup nazivov
                    if (!Globals.getInstance().hasCachedCenik()) {
                        int strmId = prefs.getHisObrat() > 0 ? prefs.getHisObrat() : 512200;
                        try {
                            List<CenikListAdapter.CenikItem> cenik = RosKasaSoapClient.getCenik(prefs.getServerUrl(), prefs.getToken(), strmId);
                            if (!cenik.isEmpty()) {
                                Globals.getInstance().setCachedCenik(cenik);
                            }
                        } catch (Exception ignored) {}
                    }
                    if (!Globals.getInstance().hasCachedHitreTipke() && prefs.getTipkePosId() > 0) {
                        try {
                            List<HitraTipkaTp> tipke = RosKasaSoapClient.getHitreTipke(prefs.getServerUrl(), prefs.getToken(), prefs.getTipkePosId());
                            if (!tipke.isEmpty()) {
                                Globals.getInstance().setCachedHitreTipke(tipke);
                            }
                        } catch (Exception ignored) {}
                    }

                    // Preveri manjkajoče nazive artiklov
                    if (r.getRacPozic() != null) {
                        for (PozicijaTp p : r.getRacPozic()) {
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

                    RacunPrintBuilder.ReceiptResult printRes =
                            RacunPrintBuilder.buildReceipt(r, Globals.getInstance(), k);

                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        btnNatisni.setEnabled(true);
                        tvText.setText(printRes.getTextPreview());
                    });
                } else {
                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        tvText.setText(R.string.preview_error_load);
                    });
                }
            } catch (Exception e) {
                mainHandler.post(() -> {
                    pbLoading.setVisibility(View.GONE);
                    tvText.setText(getString(R.string.preview_error_read, e.getMessage()));
                });
            }
        });

        btnNatisni.setOnClickListener(v -> {
            if (racunHolder[0] == null) return;
            dialog.dismiss();

            Toast.makeText(requireContext(), "Tiskanje kopije računa #" + item.getRacunId() + "...", Toast.LENGTH_SHORT).show();
            BluetoothPrintHelper.printReceipt(requireContext(), racunHolder[0], stKopijHolder[0], new BluetoothPrintHelper.OnPrintListener() {
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
            new AlertDialog.Builder(requireContext())
                    .setTitle("Storno računa")
                    .setMessage("Ali želite začeti postopek storna za račun #" + item.getRacunId() + "?")
                    .setPositiveButton("Da", (d, w) ->
                            Toast.makeText(requireContext(), "Storno računa #" + item.getRacunId() + " se pripravlja.", Toast.LENGTH_SHORT).show()
                    )
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
