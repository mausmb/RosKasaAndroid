package si.ros.RosKasa.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentPlacilaBinding;
import si.ros.RosKasa.models.NarociloItem;

public class PlacilaFragment extends Fragment {

    private FragmentPlacilaBinding binding;
    private AppPreferences prefs;

    private NarociloItemAdapter placilaAdapter;
    private final List<NarociloItem> placilaItems = new ArrayList<>();
    private String activeMarker = "Miza 5";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlacilaBinding.inflate(inflater, container, false);
        prefs = new AppPreferences(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!prefs.getActiveMarker().isEmpty()) {
            activeMarker = prefs.getActiveMarker();
        }

        setupPlacilaRecyclerView();
        setupNavigationButtons();
        setupPaymentMethodButtons();

        updatePlacilaSummary();
    }

    private void setupPlacilaRecyclerView() {
        placilaAdapter = new NarociloItemAdapter();
        binding.rvPlacilaItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPlacilaItems.setAdapter(placilaAdapter);
    }

    private void updatePlacilaSummary() {
        placilaAdapter.setItems(placilaItems);

        BigDecimal totalPlacano = BigDecimal.ZERO;
        for (NarociloItem item : placilaItems) {
            totalPlacano = totalPlacano.add(item.getZnesek());
        }

        binding.tvMizaStatus.setText(String.format("M: %s  -  Zn: 4,00 / Pl: %.2f", activeMarker, totalPlacano));
    }

    private void addPlacilo(String nacin, BigDecimal znesek) {
        placilaItems.add(new NarociloItem(nacin, znesek, 1.0));
        updatePlacilaSummary();
        Toast.makeText(requireContext(), "Dodano plačilo: " + nacin + " (" + znesek + " €)", Toast.LENGTH_SHORT).show();
    }

    private void setupPaymentMethodButtons() {
        binding.btnPayGotovina.setOnClickListener(v -> addPlacilo("GOTOVINA", new BigDecimal("4.00")));
        binding.btnPayKredRocno.setOnClickListener(v -> addPlacilo("KRED. K ROCNO", new BigDecimal("4.00")));
        binding.btnPayKreditnaPos.setOnClickListener(v -> addPlacilo("KREDITNA K POS", new BigDecimal("4.00")));
        binding.btnPayReprezentanca.setOnClickListener(v -> addPlacilo("REPREZENTANCA", new BigDecimal("4.00")));
        binding.btnPayDobavnica.setOnClickListener(v -> addPlacilo("DOBAVNICA", new BigDecimal("4.00")));
        binding.btnPayValu.setOnClickListener(v -> addPlacilo("VALU", new BigDecimal("4.00")));
        binding.btnPayMBills.setOnClickListener(v -> addPlacilo("mBills", new BigDecimal("4.00")));
        binding.btnPayGostHotela.setOnClickListener(v -> addPlacilo("GOST HOTELA", new BigDecimal("4.00")));
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

        binding.btnNavNaroci.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        });

        binding.btnOdjava.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
            }
        });

        binding.btnBrisanje.setOnClickListener(v -> {
            if (!placilaItems.isEmpty()) {
                placilaItems.remove(placilaItems.size() - 1);
                updatePlacilaSummary();
                Toast.makeText(requireContext(), "Zadnje plačilo izbrisano", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnIzpisRacuna.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Račun zakoličen in tiskanje sproženo!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
