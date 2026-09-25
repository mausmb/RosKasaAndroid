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
    private static final String KEY_PRINTER_STEVILO_ZNAKOV = "printer_stevilo_znakov";
    private static final String KEY_ESC_INIT_PRINT = "esc_init_print";
    private static final String KEY_ESC_RESET = "esc_reset";
    private static final String KEY_ESC_WIDTH2X_ON = "esc_width2x_on";
    private static final String KEY_ESC_WIDTH2X_OFF = "esc_width2x_off";
    private static final String KEY_ESC_BOLD_ON = "esc_bold_on";
    private static final String KEY_ESC_BOLD_OFF = "esc_bold_off";
    private static final String KEY_ESC_ALIGN_CENTER = "esc_align_center";
    private static final String KEY_ESC_ALIGN_LEFT = "esc_align_left";
    private static final String KEY_ESC_CUT = "esc_cut";
    private static final String KEY_NAZIV_STREGEL_VAS_JE = "naziv_stregel_vas_je";
    private static final String KEY_NAZIV_ZAHVALA_1 = "naziv_zahvala_1";
    private static final String KEY_NAZIV_ZAHVALA_2 = "naziv_zahvala_2";
    private static final String KEY_NAZIV_ZAHVALA_3 = "naziv_zahvala_3";
    private static final String KEY_NAZIV_ZAHVALA_4 = "naziv_zahvala_4";

    private final SharedPreferences prefs;
    private final Context context;

    public AppPreferences(Context context) {
        this.context = context.getApplicationContext();
        prefs = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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
        if (setup.getSteviloZnakov() != null) editor.putString(KEY_PRINTER_STEVILO_ZNAKOV, setup.getSteviloZnakov());
        if (setup.getEscInitPrint() != null) editor.putString(KEY_ESC_INIT_PRINT, setup.getEscInitPrint());
        if (setup.getEscReset() != null) editor.putString(KEY_ESC_RESET, setup.getEscReset());
        if (setup.getEscWidth2xOn() != null) editor.putString(KEY_ESC_WIDTH2X_ON, setup.getEscWidth2xOn());
        if (setup.getEscWidth2xOff() != null) editor.putString(KEY_ESC_WIDTH2X_OFF, setup.getEscWidth2xOff());
        if (setup.getEscBoldOn() != null) editor.putString(KEY_ESC_BOLD_ON, setup.getEscBoldOn());
        if (setup.getEscBoldOff() != null) editor.putString(KEY_ESC_BOLD_OFF, setup.getEscBoldOff());
        if (setup.getEscAlignCenter() != null) editor.putString(KEY_ESC_ALIGN_CENTER, setup.getEscAlignCenter());
        if (setup.getEscAlignLeft() != null) editor.putString(KEY_ESC_ALIGN_LEFT, setup.getEscAlignLeft());
        if (setup.getEscCut() != null) editor.putString(KEY_ESC_CUT, setup.getEscCut());
        if (setup.getNazivStregelVasJe() != null) editor.putString(KEY_NAZIV_STREGEL_VAS_JE, setup.getNazivStregelVasJe());
        if (setup.getNazivZahvala1() != null) editor.putString(KEY_NAZIV_ZAHVALA_1, setup.getNazivZahvala1());
        if (setup.getNazivZahvala2() != null) editor.putString(KEY_NAZIV_ZAHVALA_2, setup.getNazivZahvala2());
        if (setup.getNazivZahvala3() != null) editor.putString(KEY_NAZIV_ZAHVALA_3, setup.getNazivZahvala3());
        if (setup.getNazivZahvala4() != null) editor.putString(KEY_NAZIV_ZAHVALA_4, setup.getNazivZahvala4());
        editor.apply();

        int mobId = 1;
        try { mobId = Integer.parseInt(getMobileId()); } catch (Exception ignored) {}
        Globals.getInstance().loadFromMobileSetup(setup, mobId);

        // Shrani šifrante tudi lokalno v JSON datoteko
        si.ros.RosKasa.cache.LocalCacheManager.saveSifranti(context, setup);

        String savedPrinter = getPrinterRacuni();
        if ((Globals.getInstance().getPrinterRacuni() == null || Globals.getInstance().getPrinterRacuni().isEmpty()) && !savedPrinter.isEmpty()) {
            Globals.getInstance().setPrinterRacuni(savedPrinter);
        }
    }

    public boolean loadSavedSifranti() {
        return si.ros.RosKasa.cache.LocalCacheManager.loadSifrantiIfValid(context);
    }

    public void loadSavedPrinterSetup(Globals g) {
        if (g == null) return;
        String stZn = prefs.getString(KEY_PRINTER_STEVILO_ZNAKOV, "");
        if (!stZn.isEmpty()) {
            try {
                int w = Integer.parseInt(stZn);
                if (w == 48) w = 42;
                if (w > 0) g.setPrinterSteviloZnakov(w);
            } catch (Exception ignored) {}
        }
        String init = prefs.getString(KEY_ESC_INIT_PRINT, "");
        if (!init.isEmpty()) g.setEscInitPrint(Globals.pretvoriROSESC(init));
        String reset = prefs.getString(KEY_ESC_RESET, "");
        if (!reset.isEmpty()) g.setEscReset(Globals.pretvoriROSESC(reset));
        String wOn = prefs.getString(KEY_ESC_WIDTH2X_ON, "");
        if (!wOn.isEmpty()) g.setEscWidth2xOn(Globals.pretvoriROSESC(wOn));
        String wOff = prefs.getString(KEY_ESC_WIDTH2X_OFF, "");
        if (!wOff.isEmpty()) g.setEscWidth2xOff(Globals.pretvoriROSESC(wOff));
        String bOn = prefs.getString(KEY_ESC_BOLD_ON, "");
        if (!bOn.isEmpty()) g.setEscBoldOn(Globals.pretvoriROSESC(bOn));
        String bOff = prefs.getString(KEY_ESC_BOLD_OFF, "");
        if (!bOff.isEmpty()) g.setEscBoldOff(Globals.pretvoriROSESC(bOff));
        String aCenter = prefs.getString(KEY_ESC_ALIGN_CENTER, "");
        if (!aCenter.isEmpty()) g.setEscAlignCenter(Globals.pretvoriROSESC(aCenter));
        String aLeft = prefs.getString(KEY_ESC_ALIGN_LEFT, "");
        if (!aLeft.isEmpty()) g.setEscAlignLeft(Globals.pretvoriROSESC(aLeft));
        String cut = prefs.getString(KEY_ESC_CUT, "");
        if (!cut.isEmpty()) g.setEscCut(Globals.pretvoriROSESC(cut));
        String stregel = prefs.getString(KEY_NAZIV_STREGEL_VAS_JE, "");
        if (!stregel.isEmpty()) g.setNazivStregelVasJe(stregel);
        String z1 = prefs.getString(KEY_NAZIV_ZAHVALA_1, "");
        if (!z1.isEmpty()) g.setNazivZahvala1(z1);
        String z2 = prefs.getString(KEY_NAZIV_ZAHVALA_2, "");
        if (!z2.isEmpty()) g.setNazivZahvala2(z2);
        String z3 = prefs.getString(KEY_NAZIV_ZAHVALA_3, "");
        if (!z3.isEmpty()) g.setNazivZahvala3(z3);
        String z4 = prefs.getString(KEY_NAZIV_ZAHVALA_4, "");
        if (!z4.isEmpty()) g.setNazivZahvala4(z4);
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
