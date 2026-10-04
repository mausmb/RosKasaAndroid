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
        g.initContext(getApplicationContext());
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
        initPaymentLaunchers();

        // Nastavitev napak za SOAP klice v debug načinu
        RosKasaSoapClient.setErrorListener((method, errorMessage) -> {
            Toast.makeText(MainActivity.this, "[SOAP DEBUG NAPAKA - " + method + "]: " + errorMessage, Toast.LENGTH_LONG).show();
        });

        if (savedInstanceState == null) {
            navigateToFragment(new LoginFragment());
        }

        checkPaymentRecoveryOnStartup();
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

    public interface PaymentResultListener {
        void onPaytenResult(int resultCode, android.content.Intent data);
        void onSixTapResult(int resultCode, android.content.Intent data);
    }

    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> paytenLauncher;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> sixTapLauncher;
    private PaymentResultListener paymentResultListener;

    private void initPaymentLaunchers() {
        paytenLauncher = registerForActivityResult(
                new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (paymentResultListener != null) {
                        paymentResultListener.onPaytenResult(result.getResultCode(), result.getData());
                    }
                }
        );

        sixTapLauncher = registerForActivityResult(
                new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (paymentResultListener != null) {
                        paymentResultListener.onSixTapResult(result.getResultCode(), result.getData());
                    } else {
                        handleStartupSixTapResult(result.getResultCode(), result.getData());
                    }
                }
        );
    }

    public void launchPayten(android.content.Intent intent, PaymentResultListener listener) {
        this.paymentResultListener = listener;
        if (paytenLauncher != null) {
            try {
                paytenLauncher.launch(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                Toast.makeText(this, "Aplikacija PayTen POS ni nameščena na napravi!", Toast.LENGTH_LONG).show();
                if (paymentResultListener != null) {
                    paymentResultListener.onPaytenResult(RESULT_CANCELED, null);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Napaka pri zagonu PayTen POS: " + e.getMessage(), Toast.LENGTH_LONG).show();
                if (paymentResultListener != null) {
                    paymentResultListener.onPaytenResult(RESULT_CANCELED, null);
                }
            }
        }
    }

    public void launchSixTap(android.content.Intent intent, PaymentResultListener listener) {
        this.paymentResultListener = listener;
        if (sixTapLauncher != null) {
            try {
                sixTapLauncher.launch(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                Toast.makeText(this, "Aplikacija Worldline Tap On ni nameščena na napravi!", Toast.LENGTH_LONG).show();
                if (paymentResultListener != null) {
                    paymentResultListener.onSixTapResult(RESULT_CANCELED, null);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Napaka pri zagonu Worldline Tap On: " + e.getMessage(), Toast.LENGTH_LONG).show();
                if (paymentResultListener != null) {
                    paymentResultListener.onSixTapResult(RESULT_CANCELED, null);
                }
            }
        }
    }

    public void setPaymentResultListener(PaymentResultListener listener) {
        this.paymentResultListener = listener;
    }

    public void checkPaymentRecoveryOnStartup() {
        Globals g = Globals.getInstance();
        if (!g.isSixTap()) {
            return;
        }

        // 1. Preveri, ali obstaja že potrjena POS transakcija
        si.ros.RosKasa.payment.models.SixTapResponse confirmedResp = new si.ros.RosKasa.payment.models.SixTapResponse();
        int[] confirmedState = new int[1];
        if (si.ros.RosKasa.payment.PaymentRecoveryManager.tryGetConfirmedPOSTransaction(this, confirmedResp, confirmedState)) {
            if (confirmedState[0] >= si.ros.RosKasa.payment.PaymentRecoveryManager.POS_STATE_POS_CONFIRMED && confirmedResp.getpRacunId() > 0) {
                android.util.Log.i("MainActivity", "ZAGON RECOVERY: Najdena potrjena transakcija za R:" + confirmedResp.getpRacunId());
                bookConfirmedPOSTransaction(confirmedResp);
                return;
            }
        }

        // 2. Preveri nedokončano ali prekinjeno sejo v SharedPreferences
        si.ros.RosKasa.payment.PaymentRecoveryManager.PendingPaymentInfo info =
                si.ros.RosKasa.payment.PaymentRecoveryManager.tryGetRecoveryPayment(this);
        if (!info.isValid()) {
            info = si.ros.RosKasa.payment.PaymentRecoveryManager.tryGetPendingPayment(this);
        }

        if (info.isValid()) {
            int retry = si.ros.RosKasa.payment.PaymentRecoveryManager.getRecoveryRetryCount(this);
            if (retry >= 3) {
                android.util.Log.w("MainActivity", "ZAGON RECOVERY: Število poskusov >= 3, ponastavljam hrambo sej.");
                si.ros.RosKasa.payment.PaymentRecoveryManager.clearAllRecoveryData(this);
                Toast.makeText(this, "Obnova transakcije 3x ni uspela. Shramba plačil je bila ponastavljena.", Toast.LENGTH_LONG).show();
                return;
            }

            si.ros.RosKasa.payment.PaymentRecoveryManager.incrementRecoveryRetryCount(this);
            final String finalSessionId = info.getEffectiveSessionId();
            final int racunId;
            try { racunId = Integer.parseInt(info.getEffectiveRacunId()); } catch (Exception e) { return; }

            final AppPreferences prefs = new AppPreferences(this);
            final java.util.concurrent.ExecutorService bg = java.util.concurrent.Executors.newSingleThreadExecutor();
            bg.execute(() -> {
                try {
                    si.ros.RosKasa.models.RacunTp r = si.ros.RosKasa.soap.RosKasaSoapClient.getRacun(prefs.getServerUrl(), prefs.getToken(), racunId);
                    if (r != null) {
                        r.preracunajVsote();
                        java.math.BigDecimal saldo = r.getZnesek().subtract(r.getPlacano());
                        Integer fiskalniId = r.getFiskalniRacunId();
                        if ((fiskalniId == null || fiskalniId == 0) && saldo.compareTo(java.math.BigDecimal.ZERO) > 0) {
                            runOnUiThread(() -> {
                                Toast.makeText(MainActivity.this, "Zagon: Preverjam prekinjeno plačilo za račun #" + racunId + "...", Toast.LENGTH_SHORT).show();
                                android.content.Intent lastIntent = si.ros.RosKasa.payment.SixTapPaymentService.buildPaymentIntent(
                                        MainActivity.this, racunId, 0, saldo, java.math.BigDecimal.ZERO,
                                        si.ros.RosKasa.payment.SixTapPaymentService.OP_LAST_TRANSACTION, null, finalSessionId
                                );
                                launchSixTap(lastIntent, null);
                            });
                        } else {
                            // Račun je že plačan ali fiskaliziran
                            runOnUiThread(() -> si.ros.RosKasa.payment.PaymentRecoveryManager.clearAllRecoveryData(MainActivity.this));
                        }
                    }
                } catch (Exception e) {
                    android.util.Log.w("MainActivity", "Napaka pri preverjanju računa ob zagonu: " + e.getMessage());
                }
            });
        }
    }

    private void handleStartupSixTapResult(int resultCode, android.content.Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            si.ros.RosKasa.payment.models.SixTapResponse resp = si.ros.RosKasa.payment.SixTapPaymentService.parseResponseIntent(data);
            if (resp.isSuccess() && resp.getpRacunId() > 0) {
                android.util.Log.i("MainActivity", "LAST_TRANSACTION ob zagonu uspel za R:" + resp.getpRacunId());
                bookConfirmedPOSTransaction(resp);
            } else {
                si.ros.RosKasa.payment.PaymentRecoveryManager.clearAllRecoveryData(this);
            }
        } else {
            si.ros.RosKasa.payment.PaymentRecoveryManager.clearAllRecoveryData(this);
        }
    }

    public void bookConfirmedPOSTransaction(si.ros.RosKasa.payment.models.SixTapResponse resp) {
        final AppPreferences prefs = new AppPreferences(this);
        final java.util.concurrent.ExecutorService bg = java.util.concurrent.Executors.newSingleThreadExecutor();
        bg.execute(() -> {
            try {
                int racunId = resp.getpRacunId();
                si.ros.RosKasa.models.RacunTp r = si.ros.RosKasa.soap.RosKasaSoapClient.getRacun(prefs.getServerUrl(), prefs.getToken(), racunId);
                if (r != null) {
                    r.preracunajVsote();
                    // Preveri, ali plačilo morda že obstaja
                    boolean zeVpisan = false;
                    if (r.getRacPlaci() != null) {
                        for (si.ros.RosKasa.models.PlaciloTp pl : r.getRacPlaci()) {
                            if (pl != null && !pl.isRowDeleted() && resp.getPaymentSolutionReference().equals(pl.getMRef())) {
                                zeVpisan = true;
                                break;
                            }
                        }
                    }

                    if (!zeVpisan) {
                        int placiloId = resp.getpPlaciloId() > 0 ? resp.getpPlaciloId() : (Globals.getInstance().getSixTapPlaciloId() > 0 ? Globals.getInstance().getSixTapPlaciloId() : 2);
                        java.math.BigDecimal znesek = resp.getZnesekPOS().compareTo(java.math.BigDecimal.ZERO) > 0 ? resp.getZnesekPOS() : r.getZnesek().subtract(r.getPlacano());
                        si.ros.RosKasa.models.PlaciloTp pl = new si.ros.RosKasa.models.PlaciloTp(racunId, placiloId, znesek);
                        pl.setDelniZnesek(znesek);
                        pl.setZnesek(java.math.BigDecimal.ZERO);
                        pl.setPozicijaId(-1);
                        pl.setStKartice(resp.getCardnumber());
                        pl.setMRef(resp.getPaymentSolutionReference());
                        pl.setWpiSessionId(resp.getpWpiSessionId());
                        if (r.getRacPlaci() == null) r.setRacPlaci(new java.util.ArrayList<>());
                        r.getRacPlaci().add(pl);
                        r.setPlacano(r.getPlacano().add(znesek));
                        r.setStatus(1);
                        r.preracunajVsote();

                        // 1. setSlipEma
                        si.ros.RosKasa.models.SlipEmaTp slip = new si.ros.RosKasa.models.SlipEmaTp();
                        slip.setStevilkaRacuna(racunId);
                        slip.setPozicijaId(1);
                        slip.setSlipPrint(resp.getClient());
                        slip.setSlipPrints(resp.getMerchant());
                        slip.setStevilkaKartice(resp.getCardnumber());
                        slip.setUspelo("DA");
                        slip.setProjektId(3);
                        slip.setStType(resp.getpOperacija());
                        slip.setZnesek(znesek);
                        slip.setZnesekSlip(resp.getZnesekPOS());
                        slip.setAcqTransRef(resp.getpWpiSessionId());
                        slip.setAppIdentifier(resp.getApplicationIdentifier());
                        slip.setAcqReference(resp.getAcqreference());
                        slip.setAuthReference(resp.getPaymentSolutionReference());
                        slip.setAvtorizacija("PAY");
                        si.ros.RosKasa.soap.RosKasaSoapClient.setSlipEma(prefs.getServerUrl(), prefs.getToken(), slip);

                        // 2. setRacun
                        int mobileId = 1;
                        try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
                        si.ros.RosKasa.models.GetRacunRsTp res = si.ros.RosKasa.soap.RosKasaSoapClient.setRacun(prefs.getServerUrl(), prefs.getToken(), mobileId, r);
                        if (res != null && res.getRacGlava() != null) {
                            r = res.getRacGlava();
                        }

                        // 3. Tiskanje zaključenega računa
                        final si.ros.RosKasa.models.RacunTp finalR = r;
                        runOnUiThread(() -> {
                            si.ros.RosKasa.print.BluetoothPrintHelper.printReceiptComplete(MainActivity.this, finalR, null);
                            Toast.makeText(MainActivity.this, "Okrevanje: Račun #" + racunId + " je bil uspešno knjižen in natisnjen!", Toast.LENGTH_LONG).show();
                        });
                    }
                }
            } catch (Exception e) {
                android.util.Log.e("MainActivity", "Napaka pri knjiženju potrjene transakcije: " + e.getMessage(), e);
            } finally {
                runOnUiThread(() -> si.ros.RosKasa.payment.PaymentRecoveryManager.clearAllRecoveryData(MainActivity.this));
            }
        });
    }
}
