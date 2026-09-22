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

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.Globals;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentMizeBinding;
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

        setupMizeGrid();
        loadOpenTablesData();
    }

    private void setupMizeGrid() {
        mizeAdapter = new MizeAdapter(miza -> {
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

        // 1. Obdelava standardnih miz "Miza 1" .. "Miza 30"
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
                mizeList.add(new MizeAdapter.MizaItem(mizaName, true, firstAcc.getZnesek(), firstAcc.getRacunId()));
                processedRacunIds.add(firstAcc.getRacunId());
                markerCounts.put(mizaName.toLowerCase(Locale.getDefault()), 1);
            } else {
                mizeList.add(new MizeAdapter.MizaItem(mizaName, false, BigDecimal.ZERO, 0));
            }
        }

        // 2. Obdelava preostalih neobdelanih odprtih računov (prazni ali podvojeni markerji)
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

            mizeList.add(new MizeAdapter.MizaItem(buttonName, true, acc.getZnesek(), acc.getRacunId()));
            processedRacunIds.add(acc.getRacunId());
        }

        mizeAdapter.setItems(mizeList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
