package si.ros.RosKasa.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.widget.LinearLayout;
import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.Globals;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentMizeBinding;
import si.ros.RosKasa.models.MizaTp;
import si.ros.RosKasa.models.OsebaTp;
import si.ros.RosKasa.models.RacunSeznamItem;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class MizeFragment extends Fragment {

    private static final String TAG = "MizeFragment";
    private FragmentMizeBinding binding;
    private AppPreferences prefs;
    private MizeAdapter mizeAdapter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMizeBinding.inflate(inflater, container, false);
        prefs = new AppPreferences(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnNavRacuni.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new RacuniFragment());
            }
        });

        binding.btnNavNaroci.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        });

        binding.btnNavPlacila.setOnClickListener(v -> {
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

        binding.btnMarker.setOnClickListener(v -> odpriNovMarkerNarocilo());

        updateNatakarInfo();
        setupRajoniBar();
        setupMizeGrid();
        loadOpenTablesData();
    }

    private void updateNatakarInfo() {
        OsebaTp tekoca = Globals.getInstance().getTekocaOseba();
        if (tekoca != null && !tekoca.getNaziv().isEmpty()) {
            binding.tvNatakarInfo.setText("👤 " + tekoca.getNaziv() + " (" + tekoca.getInicialke() + ")");
        } else {
            binding.tvNatakarInfo.setText("");
        }
    }

    private int selectedRajon = 0; // 0 = vsi
    private List<RacunSeznamItem> lastLoadedOpenAccounts = new ArrayList<>();

    private void setupRajoniBar() {
        binding.containerRajoni.removeAllViews();
        Globals g = Globals.getInstance();
        List<Integer> rajoni = g.getCachedRajoni();

        if (rajoni.isEmpty() && !g.isRajoni()) {
            binding.scrollRajoni.setVisibility(View.GONE);
            return;
        }
        binding.scrollRajoni.setVisibility(View.VISIBLE);

        // Gumb Vsi
        addRajonButton("Vsi", 0);

        for (Integer r : rajoni) {
            addRajonButton("R " + r, r);
        }

        if (g.getRajonDefault() > 0 && rajoni.contains(g.getRajonDefault())) {
            selectedRajon = g.getRajonDefault();
        } else {
            selectedRajon = 0;
        }
        updateRajonButtonStyles();
    }

    private void addRajonButton(String label, int rajonId) {
        com.google.android.material.button.MaterialButton btn = new com.google.android.material.button.MaterialButton(requireContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (int) (76 * getResources().getDisplayMetrics().density),
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        lp.setMargins(4, 0, 4, 0);
        btn.setLayoutParams(lp);
        btn.setText(label);
        btn.setTextSize(12);
        btn.setTag(rajonId);
        btn.setPadding(4, 0, 4, 0);
        btn.setCornerRadius((int) (4 * getResources().getDisplayMetrics().density));
        btn.setOnClickListener(v -> {
            selectedRajon = rajonId;
            updateRajonButtonStyles();
            populateMizeGrid(lastLoadedOpenAccounts);
        });
        binding.containerRajoni.addView(btn);
    }

    private void updateRajonButtonStyles() {
        for (int i = 0; i < binding.containerRajoni.getChildCount(); i++) {
            View child = binding.containerRajoni.getChildAt(i);
            if (child instanceof com.google.android.material.button.MaterialButton) {
                com.google.android.material.button.MaterialButton b = (com.google.android.material.button.MaterialButton) child;
                int rId = (int) b.getTag();
                if (rId == selectedRajon) {
                    b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF2196F3));
                    b.setTextColor(0xFFFFFFFF);
                } else {
                    b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFE0E0E0));
                    b.setTextColor(0xFF000000);
                }
            }
        }
    }

    private void setupMizeGrid() {
        mizeAdapter = new MizeAdapter(miza -> {
            Globals g = Globals.getInstance();
            // Preveri pravico SVidiVseRacune, če je mizo odprl drug natakar
            if (miza.isOccupied && miza.kasiralOsebaId > 0 && miza.kasiralOsebaId != g.getTekocaOsebaId()) {
                if (!g.isDovoljeno(si.ros.RosKasa.models.PraviceConsts.SVidiVseRacune)) {
                    String natakarIme = !miza.kasiralNaziv.isEmpty() ? miza.kasiralNaziv : miza.kasiralInicialke;
                    Toast.makeText(requireContext(), "Miza je zasedena (odprl: " + natakarIme + ")!\nNimate pravice za odpiranje računov drugih natakarjev.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            prefs.setActiveMarker(miza.naziv);
            prefs.setActiveRacunId(miza.racunId);
            Globals.getInstance().setActiveRacunId(miza.racunId);
            Toast.makeText(requireContext(), "Izbrana " + miza.naziv + (miza.racunId > 0 ? " (Račun #" + miza.racunId + ")" : " (Nova miza)"), Toast.LENGTH_SHORT).show();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        });

        binding.rvMizeGrid.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        binding.rvMizeGrid.setAdapter(mizeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNatakarInfo();
        loadOpenTablesData();
    }

    private String calculateOdDatum() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    private void loadOpenTablesData() {
        if (!prefs.isRegistered()) {
            populateMizeGrid(new ArrayList<>());
            return;
        }

        String odDatum = calculateOdDatum();

        executor.execute(() -> {
            try {
                // Klic getRacuniSeznam (STATUS=1 = odprti računi, OD_DATUM za zadnje 3 dni - enako kot RacuniFragment)
                List<RacunSeznamItem> openAccounts = RosKasaSoapClient.getRacuniSeznam(
                        prefs.getServerUrl(),
                        prefs.getToken(),
                        prefs.getMobileId(),
                        1,
                        odDatum
                );

                mainHandler.post(() -> {
                    populateMizeGrid(openAccounts != null ? openAccounts : new ArrayList<>());
                });
            } catch (Exception e) {
                Log.e(TAG, "Napaka pri nalaganju zasednosti miz iz getRacuniSeznam", e);
                mainHandler.post(() -> populateMizeGrid(new ArrayList<>()));
            }
        });
    }

    private void populateMizeGrid(List<RacunSeznamItem> openAccounts) {
        this.lastLoadedOpenAccounts = openAccounts != null ? openAccounts : new ArrayList<>();

        // Filtriramo le aktivne odprte račune (STATUS == 1 ter brez stornacije)
        List<RacunSeznamItem> activeOpenAccounts = new ArrayList<>();
        if (openAccounts != null) {
            for (RacunSeznamItem acc : openAccounts) {
                if (acc != null && acc.getStatus() != null && acc.getStatus() == 1
                        && (acc.getStornoRacunId() == null || acc.getStornoRacunId() == 0)) {
                    activeOpenAccounts.add(acc);
                }
            }
        }

        Set<Integer> processedRacunIds = new HashSet<>();
        Map<String, List<RacunSeznamItem>> markerMap = new HashMap<>();

        for (RacunSeznamItem acc : activeOpenAccounts) {
            String markerStr = (acc.getMarker() != null) ? acc.getMarker().trim() : "";
            String key = markerStr.toLowerCase(Locale.getDefault());
            if (!markerMap.containsKey(key)) {
                markerMap.put(key, new ArrayList<>());
            }
            markerMap.get(key).add(acc);
        }

        List<MizeAdapter.MizaItem> mizeList = new ArrayList<>();
        Map<String, Integer> markerCounts = new HashMap<>();

        List<MizaTp> cachedMize = Globals.getInstance().getCachedMize();
        if (cachedMize != null && !cachedMize.isEmpty()) {
            // Uporabi šifrant miz iz MobileSetup
            List<MizaTp> filtered = new ArrayList<>();
            for (MizaTp m : cachedMize) {
                if (selectedRajon == 0 || (m.getRajon() != null && m.getRajon() == selectedRajon)) {
                    filtered.add(m);
                }
            }
            filtered.sort((m1, m2) -> Integer.compare(m1.getZap() != null ? m1.getZap() : 0, m2.getZap() != null ? m2.getZap() : 0));

            for (MizaTp m : filtered) {
                String mizaName = m.getNaziv();
                String keyFull = mizaName.toLowerCase(Locale.getDefault());
                String keyNum = mizaName.replaceAll("[^0-9]", "");

                List<RacunSeznamItem> matches = null;
                if (markerMap.containsKey(keyFull) && !markerMap.get(keyFull).isEmpty()) {
                    matches = markerMap.get(keyFull);
                } else if (!keyNum.isEmpty() && markerMap.containsKey(keyNum) && !markerMap.get(keyNum).isEmpty()) {
                    matches = markerMap.get(keyNum);
                }

                if (matches != null && !matches.isEmpty()) {
                    RacunSeznamItem firstAcc = matches.get(0);
                    int kasiralId = firstAcc.getKasiral() != null ? firstAcc.getKasiral() : 0;
                    OsebaTp kasiralOseba = Globals.getInstance().najdiOseboById(kasiralId);
                    String kasiralNaziv = kasiralOseba != null ? kasiralOseba.getNaziv() : "";
                    String kasiralIni = kasiralOseba != null ? kasiralOseba.getInicialke() : "";
                    boolean isMy = kasiralId > 0 && kasiralId == Globals.getInstance().getTekocaOsebaId();

                    mizeList.add(new MizeAdapter.MizaItem(mizaName, true, firstAcc.getZnesek(), firstAcc.getRacunId(),
                            kasiralId, kasiralNaziv, kasiralIni, isMy));
                    processedRacunIds.add(firstAcc.getRacunId());
                    markerCounts.put(mizaName.toLowerCase(Locale.getDefault()), 1);
                } else {
                    mizeList.add(new MizeAdapter.MizaItem(mizaName, false, BigDecimal.ZERO, 0));
                }
            }
        } else {
            // Fallback: standardne mize 1..30
            for (int i = 1; i <= 30; i++) {
                String mizaName = "Miza " + i;
                String keyFull = mizaName.toLowerCase(Locale.getDefault());
                String keyNum = String.valueOf(i);

                List<RacunSeznamItem> matches = null;
                if (markerMap.containsKey(keyFull) && !markerMap.get(keyFull).isEmpty()) {
                    matches = markerMap.get(keyFull);
                } else if (markerMap.containsKey(keyNum) && !markerMap.get(keyNum).isEmpty()) {
                    matches = markerMap.get(keyNum);
                }

                if (matches != null && !matches.isEmpty()) {
                    RacunSeznamItem firstAcc = matches.get(0);
                    int kasiralId = firstAcc.getKasiral() != null ? firstAcc.getKasiral() : 0;
                    OsebaTp kasiralOseba = Globals.getInstance().najdiOseboById(kasiralId);
                    String kasiralNaziv = kasiralOseba != null ? kasiralOseba.getNaziv() : "";
                    String kasiralIni = kasiralOseba != null ? kasiralOseba.getInicialke() : "";
                    boolean isMy = kasiralId > 0 && kasiralId == Globals.getInstance().getTekocaOsebaId();

                    mizeList.add(new MizeAdapter.MizaItem(mizaName, true, firstAcc.getZnesek(), firstAcc.getRacunId(),
                            kasiralId, kasiralNaziv, kasiralIni, isMy));
                    processedRacunIds.add(firstAcc.getRacunId());
                    markerCounts.put(mizaName.toLowerCase(Locale.getDefault()), 1);
                } else {
                    mizeList.add(new MizeAdapter.MizaItem(mizaName, false, BigDecimal.ZERO, 0));
                }
            }
        }

        // 2. Obdelava preostalih neobdelanih odprtih računov (če gledamo "Vsi", selectedRajon == 0)
        if (selectedRajon == 0) {
            int emptyMarkerIndex = 1;

            for (RacunSeznamItem acc : activeOpenAccounts) {
                if (processedRacunIds.contains(acc.getRacunId())) {
                    continue;
                }

                String rawMarker = (acc.getMarker() != null) ? acc.getMarker().trim() : "";
                String buttonName;

                if (rawMarker.isEmpty()) {
                    buttonName = "Brez oznake #" + (emptyMarkerIndex++);
                } else {
                    String markerKey = rawMarker.toLowerCase(Locale.getDefault());
                    int currentCount = markerCounts.containsKey(markerKey) ? markerCounts.get(markerKey) + 1 : 1;
                    markerCounts.put(markerKey, currentCount);

                    if (currentCount == 1) {
                        buttonName = rawMarker;
                    } else {
                        buttonName = rawMarker + " (#" + currentCount + ")";
                    }
                }

                int kasiralId = acc.getKasiral() != null ? acc.getKasiral() : 0;
                OsebaTp kasiralOseba = Globals.getInstance().najdiOseboById(kasiralId);
                String kasiralNaziv = kasiralOseba != null ? kasiralOseba.getNaziv() : "";
                String kasiralIni = kasiralOseba != null ? kasiralOseba.getInicialke() : "";
                boolean isMy = kasiralId > 0 && kasiralId == Globals.getInstance().getTekocaOsebaId();

                mizeList.add(new MizeAdapter.MizaItem(buttonName, true, acc.getZnesek(), acc.getRacunId(),
                        kasiralId, kasiralNaziv, kasiralIni, isMy));
                processedRacunIds.add(acc.getRacunId());
            }
        }

        mizeAdapter.setItems(mizeList);
    }

    private void odpriNovMarkerNarocilo() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Nov marker");
        builder.setMessage("Vnesite marker za novo naročilo:");
        final android.widget.EditText input = new android.widget.EditText(requireContext());
        input.setHint("npr. Šank, Terasa 1, VIP...");
        input.setPadding(32, 16, 32, 16);
        builder.setView(input);

        builder.setPositiveButton("Odpri", (d, w) -> {
            String raw = input.getText() != null ? input.getText().toString() : "";
            String clean = Globals.preveriMarker(raw);
            if (clean.isEmpty()) {
                Toast.makeText(requireContext(), "Marker ne sme biti prazen!", Toast.LENGTH_SHORT).show();
                return;
            }

            prefs.setActiveMarker(clean);
            prefs.setActiveRacunId(0);
            Globals.getInstance().setActiveRacunId(0);
            Globals.getInstance().setCurrentRacun(null);

            Toast.makeText(requireContext(), "Odprto naročilo za marker: " + clean, Toast.LENGTH_SHORT).show();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        });
        builder.setNegativeButton("Prekliči", null);
        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
