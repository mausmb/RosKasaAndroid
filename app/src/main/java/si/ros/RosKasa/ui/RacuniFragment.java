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
import android.util.Log;
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
import si.ros.RosKasa.models.PraviceConsts;
import si.ros.RosKasa.models.StornoResult;

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
            Globals.getInstance().setTekocaOseba(null);
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
            }
        });

        binding.btnStorno.setOnClickListener(v -> {
            if (!Globals.getInstance().isDovoljeno(PraviceConsts.SLahkoStorniraRacun)) {
                Toast.makeText(requireContext(), "Nimate pravice za storno računa!", Toast.LENGTH_SHORT).show();
                return;
            }
            RacunSeznamItem selected = adapter.getSelectedItem();
            if (selected == null) {
                Toast.makeText(requireContext(), "Prosim izberite račun za storno!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selected.getStatus() == null || (selected.getStatus() != 2 && selected.getStatus() != 4)) {
                Toast.makeText(requireContext(), "Stornirati je mogoče le zaključene račune (status 2 ali status 4)!", Toast.LENGTH_SHORT).show();
                return;
            }
            prikaziPredogledRacuna(selected);
        });

        binding.btnTestTiskanja.setOnClickListener(v -> checkPermissionsAndPrint());

        binding.btnInkaso.setOnClickListener(v -> izvediPrintInkaso());
        binding.btnObracun.setOnClickListener(v -> izvediObracun());
        binding.btnZdruzi.setOnClickListener(v -> izvediZdruziRacune());
        binding.btnDeli.setOnClickListener(v -> izvediSplitRacuna());
        binding.btnZamenjajMarker.setOnClickListener(v -> izvediZamenjajMarker());
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
            // Filter: Storno računi ne smejo biti v seznamu (ne original, ne storniran). Filter: STORNO_RACUN_ID IS NOT NULL
            if ((item.getStornoRacunId() != null && item.getStornoRacunId() > 0) ||
                (item.getStornoOriginal() != null && item.getStornoOriginal() > 0) ||
                (item.getZnesek() != null && item.getZnesek().compareTo(BigDecimal.ZERO) < 0)) {
                // Če pa uporabnik v iskalno polje vpiše številko računa, ga prikažemo, da je omogočen ponovni izpis!
                if (!query.isEmpty() && String.valueOf(item.getRacunId()).contains(query)) {
                    // Dovoljen prikaz storno računa ob eksplicitnem iskanju
                } else {
                    continue;
                }
            }

            // STATUS filtriranje: če je izbran prikaz izpisanih računov (filter 2), vključimo tako status 2 kot status 4 (obračunan)
            boolean statusMatch = false;
            if (item.getStatus() != null) {
                if (currentStatusFilter == 2) {
                    statusMatch = (item.getStatus() == 2 || item.getStatus() == 4);
                } else {
                    statusMatch = (item.getStatus() == currentStatusFilter);
                }
            }
            if (statusMatch) {
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

        // Izpisani računi: uredi sort descending po številki računa
        if (currentStatusFilter == 2) {
            filteredItems.sort((a, b) -> Integer.compare(b.getRacunId(), a.getRacunId()));
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
        btnStorno.setEnabled(false);

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
                        btnStorno.setEnabled(true);
                        tvText.setText(printRes.getTextPreview());
                    });
                } else {
                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        btnNatisni.setEnabled(false);
                        btnStorno.setEnabled(false);
                        tvText.setText(R.string.preview_error_load);
                    });
                }
            } catch (Exception e) {
                mainHandler.post(() -> {
                    pbLoading.setVisibility(View.GONE);
                    btnNatisni.setEnabled(false);
                    btnStorno.setEnabled(false);
                    tvText.setText(getString(R.string.preview_error_read, e.getMessage()));
                });
            }
        });

        btnNatisni.setOnClickListener(v -> {
            if (racunHolder[0] == null) return;
            dialog.dismiss();

            final RacunTp r = racunHolder[0];
            final int stKopij = stKopijHolder[0];

            binding.pbLoading.setVisibility(View.VISIBLE);
            executor.execute(() -> {
                // Pravilo: Vedno, ko izpisujemo račun, ki ima status=1, ga postavimo v status=2 na strežniku (Delphi uPrintData.pas:856-871)
                if (r.getStatus() == 1) {
                    try {
                        r.setStatus(2);
                        r.setStKopij(Math.max(1, stKopij));
                        int mobileId = 0;
                        try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
                        RosKasaSoapClient.setRacun(prefs.getServerUrl(), prefs.getToken(), mobileId, r);
                        Globals.getInstance().vpisiKronologijoDebugL1(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                                "UpdateInvoiceStatus R:" + r.getRacunId() + " na status 2 STKOPIJ:" + r.getStKopij(),
                                Globals.getInstance().getTekocaOsebaId(), Globals.getInstance().getTocilnicaId());
                    } catch (Exception exStatus) {
                        Log.e("RacuniFragment", "Napaka pri posodobitvi statusa 2 za račun #" + r.getRacunId(), exStatus);
                    }
                } else if (r.getStatus() >= 2) {
                    try {
                        int novaKopija = (r.getStKopij() != null ? r.getStKopij() : 1) + 1;
                        r.setStKopij(novaKopija);
                        int mobileId = 0;
                        try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
                        RosKasaSoapClient.setRacun(prefs.getServerUrl(), prefs.getToken(), mobileId, r);
                    } catch (Exception ignored) {}
                }

                mainHandler.post(() -> {
                    if (binding != null) binding.pbLoading.setVisibility(View.GONE);
                    if (getContext() == null) return;

                    Toast.makeText(requireContext(), "Tiskanje računa #" + item.getRacunId() + "...", Toast.LENGTH_SHORT).show();
                    BluetoothPrintHelper.printReceipt(requireContext(), r, stKopij, new BluetoothPrintHelper.OnPrintListener() {
                        @Override
                        public void onStart() {}

                        @Override
                        public void onSuccess(String message) {
                            if (getContext() != null) {
                                Toast.makeText(requireContext(), "Tisk: " + message, Toast.LENGTH_SHORT).show();
                                loadRacuniFromApi();
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            if (getContext() != null) {
                                Toast.makeText(requireContext(), "Napaka: " + errorMessage, Toast.LENGTH_LONG).show();
                                loadRacuniFromApi();
                            }
                        }
                    });
                });
            });
        });

        btnStorno.setOnClickListener(v -> {
            if (!Globals.getInstance().isDovoljeno(PraviceConsts.SLahkoStorniraRacun)) {
                Toast.makeText(requireContext(), "Nimate pravice za storno računa!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (item.getStatus() == null || (item.getStatus() != 2 && item.getStatus() != 4)) {
                Toast.makeText(requireContext(), "Stornirati je mogoče le zaključene račune (status 2 ali status 4)!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (racunHolder[0] == null) {
                Toast.makeText(requireContext(), "Podatki računa se še nalagajo...", Toast.LENGTH_SHORT).show();
                return;
            }

            android.bluetooth.BluetoothAdapter bAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
            if (!BluetoothPrintHelper.hasBluetoothPermissions(requireContext()) || bAdapter == null || !bAdapter.isEnabled()) {
                Toast.makeText(requireContext(), "Bluetooth ni vklopljen ali nimate ustreznih dovoljenj!", Toast.LENGTH_LONG).show();
                return;
            }

            String sporocilo = "Storno računa #" + item.getRacunId() + ", " + item.getFormattedZnesek() + "\n\nAli želite nadaljevati?";
            new AlertDialog.Builder(requireContext())
                    .setTitle("Storno računa")
                    .setMessage(sporocilo)
                    .setPositiveButton("Da", (d, w) -> {
                        dialog.dismiss();

                        boolean preprecimEdit = Globals.getInstance().isDovoljeno(PraviceConsts.SpreprecimEditStornoPozicij);
                        if (preprecimEdit) {
                            // Če ima uporabnik nastavljeno SpreprecimEditStornoPozicij se avtomatsko naredi novo naročilo brez možnosti urejanja
                            izvediStornoRacuna(item, racunHolder[0], null, true, 0);
                        } else {
                            // Odpremo dialog za izbiro storno razloga in vprašanje za nov račun
                            StornoRazlogDialog.show(requireContext(), prefs.getServerUrl(), prefs.getToken(), (stornoRazlogId, stornoRazlogNaziv, novoNarocilo) -> {
                                izvediStornoRacuna(item, racunHolder[0], stornoRazlogId, novoNarocilo, 1);
                            });
                        }
                    })
                    .setNegativeButton("Ne", null)
                    .show();
        });

        dialog.show();
    }

    private void izvediStornoRacuna(RacunSeznamItem item, RacunTp racun, Integer stornoRazlogId, boolean novoNarocilo, int urejamStorno) {
        if (getContext() == null) return;

        int racunId = item.getRacunId();
        int verzija = racun != null ? racun.getVerzijaZapisa() : 0;
        int osebaId = Globals.getInstance().getTekocaOsebaId();
        if (osebaId <= 0 && racun != null && racun.getKasiral() != null) {
            osebaId = racun.getKasiral();
        }
        if (osebaId <= 0) {
            osebaId = 1;
        }

        binding.pbLoading.setVisibility(View.VISIBLE);
        Toast.makeText(requireContext(), "Izvajam storno računa #" + racunId + "...", Toast.LENGTH_SHORT).show();

        final int finalOsebaId = osebaId;
        final int finalVerzija = verzija;

        executor.execute(() -> {
            int vZapisa = finalVerzija;
            if (vZapisa <= 0) {
                try {
                    vZapisa = RosKasaSoapClient.getVerzijaOfRacglava(prefs.getServerUrl(), prefs.getToken(), racunId);
                } catch (Exception ignored) {}
            }

            try {
                Globals.getInstance().vpisiKronologijoDebugL1(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                        "Storno računa R:" + racunId,
                        Globals.getInstance().getTekocaOsebaId(), Globals.getInstance().getTocilnicaId());

                StornoResult result = RosKasaSoapClient.setStornoRacuna(
                        prefs.getServerUrl(),
                        prefs.getToken(),
                        racunId,
                        vZapisa,
                        finalOsebaId,
                        stornoRazlogId,
                        novoNarocilo,
                        urejamStorno
                );

                RacunTp fetchedStornoRacun = null;
                int stornoRacunId = 0;
                String stornoIdStr = result != null ? result.getData1() : null;
                String novIdStr = result != null ? result.getData2() : null;

                if (result != null && result.isSuccess() && stornoIdStr != null && !stornoIdStr.trim().isEmpty() && !stornoIdStr.equals("0")) {
                    try {
                        stornoRacunId = Integer.parseInt(stornoIdStr.trim());
                        if (stornoRacunId > 0) {
                            fetchedStornoRacun = RosKasaSoapClient.getRacun(prefs.getServerUrl(), prefs.getToken(), stornoRacunId);
                            if (fetchedStornoRacun != null) {
                                // Preveri manjkajoče nazive artiklov
                                if (fetchedStornoRacun.getRacPozic() != null) {
                                    for (PozicijaTp p : fetchedStornoRacun.getRacPozic()) {
                                        if (p != null && p.getNivo4Id() != null && p.getNivo4Id() > 0) {
                                            String lookupName = Globals.getInstance().findNazivByNivo4Id(p.getNivo4Id());
                                            if (lookupName != null && !lookupName.trim().isEmpty()) {
                                                p.setNaziv(lookupName.trim());
                                            }
                                        }
                                    }
                                }

                                // 2.1. Ob izpisu storno računa storniran račun dobi STATUS=2 in STKOPIJ=1 na strežniku (Delphi uPrintData.pas:856-871)
                                try {
                                    fetchedStornoRacun.setStatus(2);
                                    fetchedStornoRacun.setStKopij(1);
                                    int mobileId = 0;
                                    try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
                                    RosKasaSoapClient.setRacun(prefs.getServerUrl(), prefs.getToken(), mobileId, fetchedStornoRacun);
                                    Globals.getInstance().vpisiKronologijoDebugL1(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                                            "UpdateInvoiceStatus R:" + stornoRacunId + " na status 2 STKOPIJ:1",
                                            Globals.getInstance().getTekocaOsebaId(), Globals.getInstance().getTocilnicaId());
                                } catch (Exception exStatus) {
                                    Log.e("RacuniFragment", "Napaka pri posodobitvi statusa storno računa #" + stornoRacunId + ": " + exStatus.getMessage(), exStatus);
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("RacuniFragment", "Napaka pri obdelavi storno ID: " + stornoIdStr, e);
                    }
                }

                final RacunTp finalStornoRacun = fetchedStornoRacun;
                final int finalStornoRacunId = stornoRacunId;

                mainHandler.post(() -> {
                    if (binding != null) {
                        binding.pbLoading.setVisibility(View.GONE);
                    }
                    if (getContext() == null) return;

                    if (result != null && result.isSuccess()) {
                        // 1. Samodejno tiskanje storno računa (osvežen s pridobljenimi fiskalnimi podatki)
                        if (finalStornoRacun != null) {
                            BluetoothPrintHelper.printReceipt(requireContext(), finalStornoRacun, 1, new BluetoothPrintHelper.OnPrintListener() {
                                @Override
                                public void onStart() {
                                    Toast.makeText(requireContext(), "Tiskanje storno računa #" + finalStornoRacunId + "...", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess(String message) {
                                    if (getContext() != null) {
                                        Toast.makeText(requireContext(), "Storno račun #" + finalStornoRacunId + " natisnjen.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    if (getContext() != null) {
                                        new AlertDialog.Builder(requireContext())
                                                .setTitle("Napaka pri tiskanju storno računa")
                                                .setMessage("Storno račun #" + finalStornoRacunId + " je bil uspešno kreiran na strežniku, vendar tiskanje ni uspelo:\n\n" + errorMessage + "\n\nPreverite tiskalnik in kliknite 'Ponovi tisk'.")
                                                .setPositiveButton("Ponovi tisk", (dRetry, wRetry) -> {
                                                    if (finalStornoRacun != null) {
                                                        BluetoothPrintHelper.printReceipt(requireContext(), finalStornoRacun, 1, this);
                                                    }
                                                })
                                                .setNegativeButton("Zapri", null)
                                                .show();
                                    }
                                }
                            });
                        }

                        // 2. Osveži seznam računov (račun ima na strežniku že status 2)
                        loadRacuniFromApi();

                        // 3. Obvesti uporabnika in opcijsko preusmeri na nov račun
                        if (novIdStr != null && !novIdStr.trim().isEmpty() && !novIdStr.equals("0")) {
                            try {
                                int novRacunId = Integer.parseInt(novIdStr.trim());
                                new AlertDialog.Builder(requireContext())
                                        .setTitle("Storno uspešen")
                                        .setMessage("Račun #" + racunId + " je bil uspešno storniran (storno račun #" + stornoIdStr + ").\n\nUstvarjen je bil nov račun #" + novRacunId + ".\nAli ga želite odpreti za urejanje?")
                                        .setPositiveButton("Da", (d, w) -> {
                                            prefs.setActiveRacunId(novRacunId);
                                            prefs.setActiveMarker(item.getMarker());
                                            Globals.getInstance().setCurrentRacun(null);
                                            if (getActivity() instanceof MainActivity) {
                                                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
                                            }
                                        })
                                        .setNegativeButton("Ne", null)
                                        .show();
                                return;
                            } catch (Exception ignored) {}
                        }

                        new AlertDialog.Builder(requireContext())
                                .setTitle("Storno uspešen")
                                .setMessage("Račun #" + racunId + " je bil uspešno storniran (storno račun #" + stornoIdStr + ").")
                                .setPositiveButton("V redu", null)
                                .show();

                    } else {
                        String errMsg = (result != null && result.getFault() != null && !result.getFault().isEmpty())
                                ? result.getFault() : "Neznana napaka pri klicu setStornoRacuna.";
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Napaka pri stornu")
                                .setMessage(errMsg)
                                .setPositiveButton("Zapri", null)
                                .show();
                    }
                });

            } catch (Exception e) {
                mainHandler.post(() -> {
                    if (binding != null) {
                        binding.pbLoading.setVisibility(View.GONE);
                    }
                    if (getContext() != null) {
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Napaka pri stornu")
                                .setMessage("Prišlo je do napake: " + e.getMessage())
                                .setPositiveButton("Zapri", null)
                                .show();
                    }
                });
            }
        });
    }



    private void izvediPrintInkaso() {
        Globals g = Globals.getInstance();
        if (!g.isDovoljeno(PraviceConsts.SIzvedeKompletenFinancniPregled) && !g.isDovoljeno(PraviceConsts.SIzvedeFinancniPregled)) {
            Toast.makeText(requireContext(), "Nimate pravice za izpis inkasa!", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.pbLoading.setVisibility(View.VISIBLE);
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int tocilnicaId = (g.getTocilnicaId() != null && g.getTocilnicaId() > 0) ? g.getTocilnicaId() : 512200;
                int osebaId = g.isDovoljeno(PraviceConsts.SIzvedeKompletenFinancniPregled) ? 0 : (g.getTekocaOsebaId() > 0 ? g.getTekocaOsebaId() : 1);

                si.ros.RosKasa.models.SoapReportResult res = RosKasaSoapClient.getReport(serverUrl, token, "Zakljucni1", tocilnicaId, osebaId, "ESC/POS");
                mainHandler.post(() -> {
                    binding.pbLoading.setVisibility(View.GONE);
                    if (res != null && res.getFault() != null && !res.getFault().isEmpty()) {
                        Toast.makeText(requireContext(), "Napaka pri izpisu inkasa: " + res.getFault(), Toast.LENGTH_LONG).show();
                    } else if (res != null && res.getPrintBytes() != null && res.getPrintBytes().length > 0) {
                        BluetoothPrintHelper.printReceiptBytes(requireContext(), res.getPrintBytes(), new BluetoothPrintHelper.OnPrintListener() {
                            @Override public void onStart() {
                                Toast.makeText(requireContext(), "Tiskanje inkasa...", Toast.LENGTH_SHORT).show();
                            }
                            @Override public void onSuccess(String message) {
                                if (getContext() != null) Toast.makeText(requireContext(), "Inkaso uspešno natisnjen!", Toast.LENGTH_SHORT).show();
                            }
                            @Override public void onError(String errorMessage) {
                                if (getContext() != null) Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        });
                        RosKasaSoapClient.vpisKronologijeAsync(serverUrl, token, prefs.getMobileId(), "PRINT INKASO za: " + tocilnicaId, g.getTekocaOsebaId(), tocilnicaId);
                    } else {
                        Toast.makeText(requireContext(), "Strežnik ni vrnil podatkov za izpis inkasa.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    binding.pbLoading.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Napaka: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void izvediObracun() {
        Globals g = Globals.getInstance();
        if (!g.isDovoljeno(PraviceConsts.SLahkoPozeneObracun)) {
            Toast.makeText(requireContext(), "Obračun blagajne ni dovoljen v nastavitvah!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder confirm = new AlertDialog.Builder(requireContext());
        confirm.setTitle("Obračun blagajne");
        confirm.setMessage("Ali želite izvesti dnevni obračun blagajne?");
        confirm.setPositiveButton("Izvedi obračun", (d, w) -> {
            binding.pbLoading.setVisibility(View.VISIBLE);
            executor.execute(() -> {
                try {
                    String serverUrl = prefs.getServerUrl();
                    String token = prefs.getToken();
                    int tocilnicaId = (g.getTocilnicaId() != null && g.getTocilnicaId() > 0) ? g.getTocilnicaId() : 512200;

                    si.ros.RosKasa.models.SoapReportResult res = RosKasaSoapClient.narediObracun(serverUrl, token, 1, tocilnicaId, 0, "ESC/POS");
                    mainHandler.post(() -> {
                        binding.pbLoading.setVisibility(View.GONE);
                        if (res != null && res.getFault() != null && !res.getFault().isEmpty()) {
                            Toast.makeText(requireContext(), "Obračun NI izveden: " + res.getFault(), Toast.LENGTH_LONG).show();
                        } else if (res != null && res.getPrintBytes() != null && res.getPrintBytes().length > 0) {
                            BluetoothPrintHelper.printReceiptBytes(requireContext(), res.getPrintBytes(), new BluetoothPrintHelper.OnPrintListener() {
                                @Override public void onStart() {
                                    Toast.makeText(requireContext(), "Tiskanje obračuna...", Toast.LENGTH_SHORT).show();
                                }
                                @Override public void onSuccess(String message) {
                                    if (getContext() != null) Toast.makeText(requireContext(), "Obračun zaključen in natisnjen!", Toast.LENGTH_SHORT).show();
                                }
                                @Override public void onError(String errorMessage) {
                                    if (getContext() != null) Toast.makeText(requireContext(), "Obračun končan, vendar tiskalnik ni povezan!", Toast.LENGTH_LONG).show();
                                }
                            });
                            RosKasaSoapClient.vpisKronologijeAsync(serverUrl, token, prefs.getMobileId(), "OBRAČUN BLAGAJNE KONČAN za: " + tocilnicaId, g.getTekocaOsebaId(), tocilnicaId);
                            loadRacuniFromApi();
                        } else {
                            Toast.makeText(requireContext(), "Obračun končan.", Toast.LENGTH_SHORT).show();
                            loadRacuniFromApi();
                        }
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                        binding.pbLoading.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Napaka: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
        confirm.setNegativeButton("Prekliči", null);
        confirm.show();
    }

    private void izvediZdruziRacune() {
        ZdruziRacuneDialog.show(requireContext(), allItems, this::loadRacuniFromApi);
    }

    private void izvediSplitRacuna() {
        RacunSeznamItem selected = adapter.getSelectedItem();
        if (selected == null) {
            Toast.makeText(requireContext(), "Prosim izberite račun za delitev!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selected.getStatus() == null || selected.getStatus() != 1) {
            Toast.makeText(requireContext(), "Delitev je možna le na odprtih računih (status 1)!", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.pbLoading.setVisibility(View.VISIBLE);
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                RacunTp fullRacun = RosKasaSoapClient.getRacun(serverUrl, token, selected.getRacunId());
                mainHandler.post(() -> {
                    binding.pbLoading.setVisibility(View.GONE);
                    if (fullRacun == null) {
                        Toast.makeText(requireContext(), "Napaka pri nalaganju računa za delitev!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SplitRacunDialog.show(requireContext(), fullRacun, this::loadRacuniFromApi);
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    binding.pbLoading.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Napaka: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void izvediZamenjajMarker() {
        RacunSeznamItem selected = adapter.getSelectedItem();
        if (selected == null) {
            Toast.makeText(requireContext(), "Prosim izberite račun za zamenjavo markerja!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selected.getStatus() == null || selected.getStatus() != 1) {
            Toast.makeText(requireContext(), "Zamenjava markerja je možna le na odprtih računih (status 1)!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Zamenjaj marker");
        final android.widget.EditText input = new android.widget.EditText(requireContext());
        final String oldMarker = selected.getMarker() != null ? selected.getMarker() : "";
        input.setText(oldMarker);
        input.setSelectAllOnFocus(true);
        input.setPadding(32, 16, 32, 16);
        builder.setView(input);

        builder.setPositiveButton("Shrani", (d, w) -> {
            String raw = input.getText() != null ? input.getText().toString() : "";
            String clean = Globals.preveriMarker(raw);
            if (clean.isEmpty()) {
                Toast.makeText(requireContext(), "Marker ne sme biti prazen!", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.pbLoading.setVisibility(View.VISIBLE);
            executor.execute(() -> {
                try {
                    String serverUrl = prefs.getServerUrl();
                    String token = prefs.getToken();
                    RacunTp fullRacun = RosKasaSoapClient.getRacun(serverUrl, token, selected.getRacunId());
                    if (fullRacun != null) {
                        fullRacun.setMarker(clean);
                        int mobileId = 1;
                        try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
                        RosKasaSoapClient.setRacun(serverUrl, token, mobileId, fullRacun);
                        Globals g = Globals.getInstance();
                        RosKasaSoapClient.vpisKronologijeAsync(serverUrl, token, prefs.getMobileId(),
                                "MARKER ZAMENJAVA R:" + fullRacun.getRacunId() + " iz: " + oldMarker + " v: " + clean,
                                g.getTekocaOsebaId(), fullRacun.getTocilnicaId());
                    }
                    mainHandler.post(() -> {
                        binding.pbLoading.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Marker posodobljen v: " + clean, Toast.LENGTH_SHORT).show();
                        loadRacuniFromApi();
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                        binding.pbLoading.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Napaka pri zamenjavi markerja: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
        builder.setNegativeButton("Prekliči", null);
        builder.show();
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
