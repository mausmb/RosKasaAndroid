package si.ros.RosKasa.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentNarocilaBinding;
import si.ros.RosKasa.models.HitraTipkaTp;
import si.ros.RosKasa.models.NarociloItem;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class NarocilaFragment extends Fragment {

    private FragmentNarocilaBinding binding;
    private AppPreferences prefs;

    private NarociloItemAdapter orderAdapter;
    private QuickKeyAdapter quickKeyAdapter;

    private final List<NarociloItem> orderItems = new ArrayList<>();
    private String activeMarker = "Miza 5";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNarocilaBinding.inflate(inflater, container, false);
        prefs = new AppPreferences(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!prefs.getActiveMarker().isEmpty()) {
            activeMarker = prefs.getActiveMarker();
        }

        setupOrderRecyclerView();
        setupQuickKeysRecyclerView();
        setupNavigationButtons();

        loadInitialOrderData();
        loadHitreTipkeFromApi();
    }

    private void setupOrderRecyclerView() {
        orderAdapter = new NarociloItemAdapter();
        binding.rvNarociloItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvNarociloItems.setAdapter(orderAdapter);
    }

    private void setupQuickKeysRecyclerView() {
        quickKeyAdapter = new QuickKeyAdapter(key -> {
            if (key.isBack) {
                loadHitreTipkeFromApi();
            } else if (key.isCategory) {
                showArticlesForCategory(key.title);
            } else {
                addArticleToOrder(key.title, new BigDecimal(String.valueOf(key.price)));
            }
        });

        binding.rvQuickKeys.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        binding.rvQuickKeys.setAdapter(quickKeyAdapter);
    }

    private void loadInitialOrderData() {
        orderItems.clear();
        orderItems.add(new NarociloItem("PIVO GUINNES 0,33 ST", new BigDecimal("4.00"), 1.0));
        updateOrderSummary();
    }

    private void updateOrderSummary() {
        orderAdapter.setItems(orderItems);

        BigDecimal totalZnesek = BigDecimal.ZERO;
        for (NarociloItem item : orderItems) {
            totalZnesek = totalZnesek.add(item.getZnesek());
        }

        binding.tvMizaStatus.setText(String.format(Locale.getDefault(), "M: %s  -  Zn: %.2f / Pl: 0,00", activeMarker, totalZnesek));
    }

    private void loadHitreTipkeFromApi() {
        int tipkePosId = prefs.getTipkePosId();

        if (!prefs.isRegistered()) {
            showCategoryKeys();
            return;
        }

        executor.execute(() -> {
            try {
                // Klic WSDL metode getHitreTipke(stroskovnoId, token)
                List<HitraTipkaTp> result = RosKasaSoapClient.getHitreTipke(
                        prefs.getServerUrl(),
                        prefs.getToken(),
                        tipkePosId
                );

                mainHandler.post(() -> {
                    if (result != null && !result.isEmpty()) {
                        populateQuickKeysFromApi(result);
                    } else {
                        showCategoryKeys();
                    }
                });
            } catch (Exception e) {
                mainHandler.post(this::showCategoryKeys);
            }
        });
    }

    private void populateQuickKeysFromApi(List<HitraTipkaTp> apiTipke) {
        List<QuickKeyAdapter.QuickKey> keys = new ArrayList<>();
        for (HitraTipkaTp item : apiTipke) {
            boolean isCat = item.getSkupinaId() != null && item.getSkupinaId() > 0 && (item.getNivo4Id() == null || item.getNivo4Id() == 0);
            double price = item.getEnotaProdaje() != null ? item.getEnotaProdaje().doubleValue() : 0.0;
            keys.add(new QuickKeyAdapter.QuickKey(
                    item.getNaziv() != null ? item.getNaziv() : "",
                    isCat,
                    false,
                    null,
                    price
            ));
        }
        quickKeyAdapter.setKeys(keys);
    }

    private void showCategoryKeys() {
        List<QuickKeyAdapter.QuickKey> keys = new ArrayList<>();
        // Row 1 skupin
        keys.add(new QuickKeyAdapter.QuickKey("BREZALKOHOLNE", true, false, null, 0));
        keys.add(new QuickKeyAdapter.QuickKey("PIVO", true, false, null, 0));
        keys.add(new QuickKeyAdapter.QuickKey("TOPLI NAPITKI", true, false, null, 0));
        keys.add(new QuickKeyAdapter.QuickKey("VINA", true, false, null, 0));
        keys.add(new QuickKeyAdapter.QuickKey("", false, false, null, 0));

        // Row 2 skupin / hitrih artiklov
        keys.add(new QuickKeyAdapter.QuickKey("PGA", false, false, null, 3.50));
        keys.add(new QuickKeyAdapter.QuickKey("SKUP test", false, false, null, 5.00));
        keys.add(new QuickKeyAdapter.QuickKey("PENINA RADGO", false, false, null, 18.00));
        keys.add(new QuickKeyAdapter.QuickKey("COCA COLA 0.25", false, false, null, 2.80));
        keys.add(new QuickKeyAdapter.QuickKey("STEAKI", true, false, null, 0));

        // Row 3 skupin / hitrih artiklov
        keys.add(new QuickKeyAdapter.QuickKey("ČAJ", false, false, null, 2.20));
        keys.add(new QuickKeyAdapter.QuickKey("AMARO", false, false, null, 3.00));
        keys.add(new QuickKeyAdapter.QuickKey("8% DDV PAVŠAL", false, false, null, 0.00));
        keys.add(new QuickKeyAdapter.QuickKey("", false, false, null, 0));
        keys.add(new QuickKeyAdapter.QuickKey("", false, false, null, 0));

        quickKeyAdapter.setKeys(keys);
    }

    private void showArticlesForCategory(String category) {
        List<QuickKeyAdapter.QuickKey> keys = new ArrayList<>();
        // Prva tipka je vedno [ NAZAJ ] (rdeča tipka za nivo navzgor)
        keys.add(new QuickKeyAdapter.QuickKey("[ NAZAJ ]", false, true, category, 0));

        switch (category) {
            case "PIVO":
                keys.add(new QuickKeyAdapter.QuickKey("GUINNES 0.33", false, false, category, 4.00));
                keys.add(new QuickKeyAdapter.QuickKey("LAŠKO 0.5", false, false, category, 3.20));
                keys.add(new QuickKeyAdapter.QuickKey("UNION 0.5", false, false, category, 3.20));
                keys.add(new QuickKeyAdapter.QuickKey("RADLER 0.5", false, false, category, 3.00));
                keys.add(new QuickKeyAdapter.QuickKey("HEINEKEN 0.33", false, false, category, 3.50));
                keys.add(new QuickKeyAdapter.QuickKey("BEZALKOHOLNO PIVO", false, false, category, 3.00));
                break;

            case "BREZALKOHOLNE":
                keys.add(new QuickKeyAdapter.QuickKey("COCA COLA 0.25", false, false, category, 2.80));
                keys.add(new QuickKeyAdapter.QuickKey("COCKTA 0.25", false, false, category, 2.80));
                keys.add(new QuickKeyAdapter.QuickKey("FANTA 0.25", false, false, category, 2.80));
                keys.add(new QuickKeyAdapter.QuickKey("VODA 0.5", false, false, category, 2.00));
                keys.add(new QuickKeyAdapter.QuickKey("RADENSKA 0.25", false, false, category, 2.20));
                keys.add(new QuickKeyAdapter.QuickKey("JABOLČNI SOK", false, false, category, 2.60));
                break;

            case "TOPLI NAPITKI":
                keys.add(new QuickKeyAdapter.QuickKey("KAVA ESPRESSO", false, false, category, 1.80));
                keys.add(new QuickKeyAdapter.QuickKey("KAVA Z MLEKOM", false, false, category, 2.20));
                keys.add(new QuickKeyAdapter.QuickKey("CAPPUCCINO", false, false, category, 2.40));
                keys.add(new QuickKeyAdapter.QuickKey("ČAJ Z MEDOM", false, false, category, 2.50));
                keys.add(new QuickKeyAdapter.QuickKey("KAKAV", false, false, category, 2.60));
                break;

            case "VINA":
                keys.add(new QuickKeyAdapter.QuickKey("MALVAZIJA 0.1", false, false, category, 2.20));
                keys.add(new QuickKeyAdapter.QuickKey("REFOŠK 0.1", false, false, category, 2.20));
                keys.add(new QuickKeyAdapter.QuickKey("RENSKI RIZLING", false, false, category, 3.50));
                keys.add(new QuickKeyAdapter.QuickKey("CABERNET", false, false, category, 3.80));
                break;

            default:
                keys.add(new QuickKeyAdapter.QuickKey("BIFTEK 250G", false, false, category, 22.00));
                keys.add(new QuickKeyAdapter.QuickKey("RAMSTEK 300G", false, false, category, 18.00));
                keys.add(new QuickKeyAdapter.QuickKey("T-BONE 500G", false, false, category, 28.00));
                break;
        }

        quickKeyAdapter.setKeys(keys);
    }

    private void addArticleToOrder(String naziv, BigDecimal cena) {
        if (naziv == null || naziv.trim().isEmpty()) return;

        boolean exists = false;
        for (NarociloItem item : orderItems) {
            if (item.getNaziv().equals(naziv)) {
                item.setKolicina(item.getKolicina() + 1.0);
                exists = true;
                break;
            }
        }
        if (!exists) {
            orderItems.add(new NarociloItem(naziv, cena, 1.0));
        }

        updateOrderSummary();
        Toast.makeText(requireContext(), "Dodan " + naziv, Toast.LENGTH_SHORT).show();
    }

    private void setupNavigationButtons() {
        binding.btnNavMize.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
            }
        });

        binding.btnNavRacuni.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new RacuniFragment());
            }
        });

        binding.btnNavPlacila.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new PlacilaFragment());
            }
        });

        binding.btnNarociPost.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Naročilo poslano na kuhinjo/šank (setRacun)!", Toast.LENGTH_SHORT).show();
        });

        binding.btnBrisanje.setOnClickListener(v -> {
            if (!orderItems.isEmpty()) {
                orderItems.remove(orderItems.size() - 1);
                updateOrderSummary();
                Toast.makeText(requireContext(), "Zadnja pozicija izbrisana", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
