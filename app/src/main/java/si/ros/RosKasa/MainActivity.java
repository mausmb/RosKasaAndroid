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
        prefs.loadSavedSifranti();

        initNfc();

        // Nastavitev napak za SOAP klice v debug načinu
        RosKasaSoapClient.setErrorListener((method, errorMessage) -> {
            Toast.makeText(MainActivity.this, "[SOAP DEBUG NAPAKA - " + method + "]: " + errorMessage, Toast.LENGTH_LONG).show();
        });

        if (savedInstanceState == null) {
            navigateToFragment(new LoginFragment());
        }
    }

    private android.nfc.NfcAdapter nfcAdapter;
    private si.ros.RosKasa.nfc.NfcHelper.OnNfcTagReadListener nfcTagListener;

    private void initNfc() {
        try {
            nfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(this);
            if (nfcAdapter == null) {
                android.util.Log.w("MainActivity", "NFC strojna oprema ni na voljo na tej napravi.");
            } else if (!nfcAdapter.isEnabled()) {
                android.util.Log.w("MainActivity", "NFC adapter je izklopljen v nastavitvah sistema.");
                Toast.makeText(this, "NFC je izklopljen v nastavitvah telefona!", Toast.LENGTH_SHORT).show();
            } else {
                android.util.Log.i("MainActivity", "NFC adapter je pripravljen.");
            }
        } catch (Exception e) {
            android.util.Log.w("MainActivity", "Napaka pri preverjanju NFC: " + e.getMessage());
        }
    }

    public void setOnNfcTagReadListener(si.ros.RosKasa.nfc.NfcHelper.OnNfcTagReadListener listener) {
        this.nfcTagListener = listener;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startNfcListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopNfcListening();
    }

    private void startNfcListening() {
        if (nfcAdapter == null) return;
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Opozorilo: NFC je izklopljen v nastavitvah sistema!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Poskusi najprej z ReaderMode (najbolj zanesljiv na Android 4.4+)
        boolean readerModeOk = si.ros.RosKasa.nfc.NfcHelper.enableReaderMode(this, nfcAdapter, cardInfo -> {
            if (nfcTagListener != null) {
                nfcTagListener.onTagRead(cardInfo);
            }
        });

        // Kot rezervo omogoči tudi ForegroundDispatch
        if (!readerModeOk) {
            si.ros.RosKasa.nfc.NfcHelper.enableForegroundDispatch(this, nfcAdapter);
        }
    }

    private void stopNfcListening() {
        if (nfcAdapter == null) return;
        si.ros.RosKasa.nfc.NfcHelper.disableReaderMode(this, nfcAdapter);
        si.ros.RosKasa.nfc.NfcHelper.disableForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        si.ros.RosKasa.nfc.NfcHelper.NfcCardInfo cardInfo = si.ros.RosKasa.nfc.NfcHelper.extractTagFromIntent(intent);
        if (cardInfo != null && nfcTagListener != null) {
            nfcTagListener.onTagRead(cardInfo);
        }
    }

    public void navigateToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
