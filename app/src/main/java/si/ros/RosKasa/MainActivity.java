package si.ros.RosKasa;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import si.ros.RosKasa.soap.RosKasaSoapClient;
import si.ros.RosKasa.ui.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppPreferences prefs = new AppPreferences(this);
        Globals g = Globals.getInstance();
        g.setServerUrl(prefs.getServerUrl());
        g.setToken(prefs.getToken());
        g.setNazivMobile(prefs.getNaziv());
        g.setTipkePosId(prefs.getTipkePosId());
        g.setfPosId(prefs.getfPosId());
        g.setfPoslovniProstorId(prefs.getfPoslovniProstorId());
        g.setTocilnicaId(prefs.getTocilnicaId());
        g.setKuhinjaId(prefs.getKuhinjaId());
        g.setHisObrat(prefs.getHisObrat());
        g.setPrinterRacuni(prefs.getPrinterRacuni());
        try { g.setMobileId(Integer.parseInt(prefs.getMobileId())); } catch (Exception ignored) {}
        prefs.loadSavedPrinterSetup(g);

        // Nastavitev napak za SOAP klice v debug načinu
        RosKasaSoapClient.setErrorListener((method, errorMessage) -> {
            Toast.makeText(MainActivity.this, "[SOAP DEBUG NAPAKA - " + method + "]: " + errorMessage, Toast.LENGTH_LONG).show();
        });

        if (savedInstanceState == null) {
            navigateToFragment(new LoginFragment());
        }
    }

    public void navigateToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
