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
    }

    public void saveDeviceInfo(String serverUrl, String mobileId, String token) {
        saveRegistration(serverUrl, mobileId, token, "");
    }

    public void saveMobileSetup(MobileSetupTp setup) {
        if (setup == null) return;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_TIPKE_POS_ID, setup.getTipkePosId());
        editor.putInt(KEY_HIS_OBRAT, setup.getHisObrat());
        if (setup.getfPosId() != null) editor.putInt(KEY_F_POS_ID, setup.getfPosId());
        if (setup.getfPoslovniProstorId() != null) editor.putInt(KEY_F_POSLOVNI_PROSTOR_ID, setup.getfPoslovniProstorId());
        if (setup.getNazivPodjetja() != null) editor.putString(KEY_NAZIV_PODJETJA, setup.getNazivPodjetja());
        if (setup.getDavcnaZaFurs() != null) editor.putString(KEY_DAVCNA_ZAFURS, setup.getDavcnaZaFurs());
        editor.apply();
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

    public void clear() {
        prefs.edit().clear().apply();
    }

    public void clearAll() {
        clear();
    }
}
