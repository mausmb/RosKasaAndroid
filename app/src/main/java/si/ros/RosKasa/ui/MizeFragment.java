package si.ros.RosKasa.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentMizeBinding;

public class MizeFragment extends Fragment {

    private FragmentMizeBinding binding;
    private AppPreferences prefs;

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
    }

    private void setupMizeGrid() {
        List<String> mizeList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            mizeList.add("Miza " + i);
        }

        binding.rvMizeGrid.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        binding.rvMizeGrid.setAdapter(new MizeAdapter(mizeList, miza -> {
            prefs.setActiveMarker(miza);
            Toast.makeText(requireContext(), "Izbrana " + miza, Toast.LENGTH_SHORT).show();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
