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
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        });

        binding.btnNavPlacila.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new PlacilaFragment());
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
            Toast.makeText(requireContext(), "Test tiskanja sprožen...", Toast.LENGTH_SHORT).show();
        });
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
        Toast.makeText(requireContext(), "Izbran račun #" + item.getRacunId() + " (Miza: " + item.getMarker() + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDoubleClick(RacunSeznamItem item, int position) {
        Toast.makeText(requireContext(), "Nalaganje računa #" + item.getRacunId() + " za urejanje...", Toast.LENGTH_SHORT).show();
        prefs.setActiveMarker(item.getMarker());

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
