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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentLoginBinding;
import si.ros.RosKasa.models.MobileSetupTp;
import si.ros.RosKasa.models.OsebaTokenResult;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private AppPreferences prefs;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        prefs = new AppPreferences(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateUiMode();

        binding.btnRegister.setOnClickListener(v -> handleRegistration());
        binding.btnLoginPin.setOnClickListener(v -> handlePinLogin());
        binding.btnResetConfig.setOnClickListener(v -> {
            prefs.clearAll();
            updateUiMode();
            Toast.makeText(requireContext(), "Nastavitve ponastavljene", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateUiMode() {
        if (prefs.isRegistered()) {
            binding.containerFirstRun.setVisibility(View.GONE);
            binding.containerRegularLogin.setVisibility(View.VISIBLE);
        } else {
            binding.containerFirstRun.setVisibility(View.VISIBLE);
            binding.containerRegularLogin.setVisibility(View.GONE);
        }
    }

    private void handleRegistration() {
        String url = binding.etServerUrl.getText().toString().trim();
        String mobileId = binding.etMobileId.getText().toString().trim();
        String uIme = binding.etUsername.getText().toString().trim();
        String geslo = binding.etPassword.getText().toString().trim();

        if (url.isEmpty() || mobileId.isEmpty() || uIme.isEmpty() || geslo.isEmpty()) {
            Toast.makeText(requireContext(), "Prosim izpolnite vsa polja!", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnRegister.setEnabled(false);

        executor.execute(() -> {
            try {
                OsebaTokenResult result = RosKasaSoapClient.getOsebaToken(url, uIme, geslo);
                if (result.isSuccess()) {
                    prefs.saveDeviceInfo(url, mobileId, result.getToken());

                    // Pridobi še zagonske nastavitve strežnika (MobileSetup)
                    try {
                        MobileSetupTp setup = RosKasaSoapClient.getAppConfig(url, result.getToken());
                        prefs.saveMobileSetup(setup);
                    } catch (Exception setupEx) {
                        // Ignoriramo napako pri pridobivanju setupa (uporabi se fallback)
                    }

                    mainHandler.post(() -> {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnRegister.setEnabled(true);
                        Toast.makeText(requireContext(), "Registracija uspešna! Dobrodošli, " + result.getNaziv(), Toast.LENGTH_LONG).show();
                        updateUiMode();
                    });
                } else {
                    mainHandler.post(() -> {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnRegister.setEnabled(true);
                        Toast.makeText(requireContext(), "Napaka: " + result.getFault(), Toast.LENGTH_LONG).show();
                    });
                }
            } catch (Exception e) {
                mainHandler.post(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnRegister.setEnabled(true);
                    Toast.makeText(requireContext(), "Povezava ni uspela: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void handlePinLogin() {
        String pin = binding.etPinCode.getText().toString().trim();
        if (pin.isEmpty()) {
            Toast.makeText(requireContext(), "Vnesite PIN kodo!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Osveži nastavitve v ozadju pred preusmeritvijo
        if (prefs.isRegistered()) {
            executor.execute(() -> {
                try {
                    MobileSetupTp setup = RosKasaSoapClient.getAppConfig(prefs.getServerUrl(), prefs.getToken());
                    prefs.saveMobileSetup(setup);
                } catch (Exception ignored) {}
            });
        }

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
