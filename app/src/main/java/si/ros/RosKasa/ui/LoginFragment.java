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
import si.ros.RosKasa.Globals;
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
                        int mId = 1;
                        try { mId = Integer.parseInt(mobileId); } catch (Exception ignored) {}
                        MobileSetupTp setup = RosKasaSoapClient.getAppConfig(url, result.getToken(), mId);
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

        // Osveži nastavitve in naloži plačila ter cenik v ozadju
        if (prefs.isRegistered()) {
            executor.execute(() -> {
                try {
                    int mId = 1;
                    try { mId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
                    MobileSetupTp setup = RosKasaSoapClient.getAppConfig(prefs.getServerUrl(), prefs.getToken(), mId);
                    prefs.saveMobileSetup(setup);

                    // Nalaganje plačilnih metod (getNacPlac2)
                    try {
                        java.util.List<si.ros.RosKasa.models.NacPlacTp> placila = RosKasaSoapClient.getNacPlac2(prefs.getServerUrl(), prefs.getToken(), mId);
                        Globals.getInstance().filterAndSetCachedPlacila(placila);
                    } catch (Exception ex) {
                        android.util.Log.w("LoginFragment", "Napaka pri nalaganju plačil: " + ex.getMessage());
                    }

                    // Preload cenika v ozadju za takojšen lookup nazivov
                    if (!Globals.getInstance().hasCachedCenik()) {
                        int strmId = prefs.getHisObrat() > 0 ? prefs.getHisObrat() : 512200;
                        try {
                            java.util.List<CenikListAdapter.CenikItem> cenik = RosKasaSoapClient.getCenik(prefs.getServerUrl(), prefs.getToken(), strmId);
                            if (cenik != null && !cenik.isEmpty()) {
                                Globals.getInstance().setCachedCenik(cenik);
                            }
                        } catch (Exception ex) {
                            android.util.Log.w("LoginFragment", "Napaka pri prednalaganju cenika: " + ex.getMessage());
                        }
                    }

                    // Preload hitrih tipk v ozadju za takojšen lookup nazivov
                    int tipkePosId = prefs.getTipkePosId();
                    if (tipkePosId > 0 && !Globals.getInstance().hasCachedHitreTipke()) {
                        try {
                            java.util.List<si.ros.RosKasa.models.HitraTipkaTp> tipke = RosKasaSoapClient.getHitreTipke(prefs.getServerUrl(), prefs.getToken(), tipkePosId);
                            if (tipke != null && !tipke.isEmpty()) {
                                Globals.getInstance().setCachedHitreTipke(tipke);
                            }
                        } catch (Exception ex) {
                            android.util.Log.w("LoginFragment", "Napaka pri prednalaganju hitrih tipk: " + ex.getMessage());
                        }
                    }
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
