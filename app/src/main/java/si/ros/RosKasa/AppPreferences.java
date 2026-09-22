package si.ros.RosKasa;

import android.content.Context;
import android.content.SharedPreferences;

import si.ros.RosKasa.models.MobileSetupTp;

public class AppPreferences {
    private static final String PREF_NAME = "RosKasaPrefs";
    private static final String KEY_SERVER_URL = "server_url";
    private static final String KEY_MOBILE_ID = "mobile_id";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_NAZIV = "naziv";
    private static final String KEY_ACTIVE_MARKER = "active_marker";
    private static final String KEY_TIPKE_POS_ID = "tipke_pos_id";
    private static final String KEY_HIS_OBRAT = "his_obrat";
    private static final String KEY_F_POS_ID = "f_pos_id";
    private static final String KEY_F_POSLOVNI_PROSTOR_ID = "f_poslovni_prostor_id";
    private static final String KEY_NAZIV_PODJETJA = "naziv_podjetja";
    private static final String KEY_DAVCNA_ZAFURS = "davcna_zafurs";
    private static final String KEY_PRINTER_RACUNI = "PRINTER_RACUNI";
    private static final String KEY_ACTIVE_RACUN_ID = "active_racun_id";
    private static final String KEY_KUHINJA_ID = "kuhinja_id";
    private static final String KEY_TOCILNICA_ID = "tocilnica_id";
    private static final String KEY_F_POS_ID_VAL = "f_pos_id_val";

    private final SharedPreferences prefs;

    public AppPreferences(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveRegistration(String serverUrl, String mobileId, String token, String naziv) {
        prefs.edit()
                .putString(KEY_SERVER_URL, serverUrl)
                .putString(KEY_MOBILE_ID, mobileId)
                .putString(KEY_TOKEN, token)
                .putString(KEY_NAZIV, naziv)
                .apply();

        Globals g = Globals.getInstance();
        g.setServerUrl(serverUrl);
        g.setToken(token);
        g.setNazivMobile(naziv);
        try { g.setMobileId(Integer.parseInt(mobileId)); } catch (Exception ignored) {}
    }

    public void saveDeviceInfo(String serverUrl, String mobileId, String token) {
        saveRegistration(serverUrl, mobileId, token, "");
    }

    public void saveMobileSetup(MobileSetupTp setup) {
        if (setup == null) return;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_TIPKE_POS_ID, setup.getTipkePosId());
        editor.putInt(KEY_HIS_OBRAT, setup.getHisObrat());
        if (setup.getfPosId() != null) {
            editor.putInt(KEY_F_POS_ID_VAL, setup.getfPosId());
            editor.putInt(KEY_F_POS_ID, setup.getfPosId());
        }
        if (setup.getfPoslovniProstorId() != null) editor.putInt(KEY_F_POSLOVNI_PROSTOR_ID, setup.getfPoslovniProstorId());
        if (setup.getKuhinjaId() != null) editor.putInt(KEY_KUHINJA_ID, setup.getKuhinjaId());
        if (setup.getTocilnicaId() != null) editor.putInt(KEY_TOCILNICA_ID, setup.getTocilnicaId());
        if (setup.getNazivPodjetja() != null) editor.putString(KEY_NAZIV_PODJETJA, setup.getNazivPodjetja());
        if (setup.getDavcnaZaFurs() != null) editor.putString(KEY_DAVCNA_ZAFURS, setup.getDavcnaZaFurs());
        if (setup.getPrinterRacuni() != null && !setup.getPrinterRacuni().trim().isEmpty()) {
            editor.putString(KEY_PRINTER_RACUNI, setup.getPrinterRacuni().trim());
        }
        editor.apply();

        int mobId = 1;
        try { mobId = Integer.parseInt(getMobileId()); } catch (Exception ignored) {}
        Globals.getInstance().loadFromMobileSetup(setup, mobId);

        String savedPrinter = getPrinterRacuni();
        if ((Globals.getInstance().getPrinterRacuni() == null || Globals.getInstance().getPrinterRacuni().isEmpty()) && !savedPrinter.isEmpty()) {
            Globals.getInstance().setPrinterRacuni(savedPrinter);
        }
    }

    public String getPrinterRacuni() {
        return prefs.getString(KEY_PRINTER_RACUNI, "");
    }

    public void setPrinterRacuni(String printerName) {
        prefs.edit().putString(KEY_PRINTER_RACUNI, printerName != null ? printerName.trim() : "").apply();
        Globals.getInstance().setPrinterRacuni(printerName);
    }

    public boolean isRegistered() {
        String token = getToken();
        return token != null && !token.trim().isEmpty();
    }

    public String getServerUrl() {
        return prefs.getString(KEY_SERVER_URL, "https://test.ros.si/r16f/asmx/kasa.asmx");
    }

    public String getMobileId() {
        return prefs.getString(KEY_MOBILE_ID, "");
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, "");
    }

    public String getNaziv() {
        return prefs.getString(KEY_NAZIV, "");
    }

    public String getActiveMarker() {
        return prefs.getString(KEY_ACTIVE_MARKER, "Miza 1");
    }

    public void setActiveMarker(String marker) {
        prefs.edit().putString(KEY_ACTIVE_MARKER, marker).apply();
    }

    public int getActiveRacunId() {
        return prefs.getInt(KEY_ACTIVE_RACUN_ID, 0);
    }

    public void setActiveRacunId(int racunId) {
        prefs.edit().putInt(KEY_ACTIVE_RACUN_ID, racunId).apply();
        Globals.getInstance().setActiveRacunId(racunId);
    }

    public int getTipkePosId() {
        return prefs.getInt(KEY_TIPKE_POS_ID, 512200);
    }

    public void setTipkePosId(int posId) {
        prefs.edit().putInt(KEY_TIPKE_POS_ID, posId).apply();
    }

    public int getHisObrat() {
        return prefs.getInt(KEY_HIS_OBRAT, 512200);
    }

    public void setHisObrat(int obratId) {
        prefs.edit().putInt(KEY_HIS_OBRAT, obratId).apply();
    }

    public int getKuhinjaId() {
        return prefs.getInt(KEY_KUHINJA_ID, 0);
    }

    public int getTocilnicaId() {
        return prefs.getInt(KEY_TOCILNICA_ID, 0);
    }

    public int getfPosId() {
        int pos = prefs.getInt(KEY_F_POS_ID_VAL, 0);
        if (pos <= 0) {
            pos = prefs.getInt(KEY_F_POS_ID, 0);
        }
        return pos > 0 ? pos : 500;
    }

    public int getPosId() {
        return getfPosId();
    }

    public int getfPoslovniProstorId() {
        int pp = prefs.getInt(KEY_F_POSLOVNI_PROSTOR_ID, 0);
        return pp > 0 ? pp : 5000;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }

    public void clearAll() {
        clear();
    }
}
