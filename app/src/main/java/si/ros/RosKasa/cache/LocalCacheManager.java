package si.ros.RosKasa.cache;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.models.MizaTp;
import si.ros.RosKasa.models.MobileSetupTp;
import si.ros.RosKasa.models.OsebaTp;
import si.ros.RosKasa.models.PrioritetaProjektaTp;
import si.ros.RosKasa.models.TarifaTp;

public class LocalCacheManager {
    private static final String TAG = "LocalCacheManager";
    private static final String SIFRANTI_FILENAME = "sifranti_setup.json";

    public static synchronized void saveSifranti(Context context, MobileSetupTp setup) {
        if (context == null || setup == null) return;
        try {
            JSONObject root = new JSONObject();
            root.put("timestamp", System.currentTimeMillis());

            // Osebe
            JSONArray arrOsebe = new JSONArray();
            if (setup.getOsebe() != null) {
                for (OsebaTp o : setup.getOsebe()) {
                    JSONObject jo = new JSONObject();
                    jo.put("osebaId", o.getOsebaId());
                    jo.put("naziv", o.getNaziv());
                    jo.put("uporabniskoIme", o.getUporabniskoIme());
                    jo.put("pin", o.getPin());
                    jo.put("privilegiji", o.getPrivilegiji());
                    jo.put("oddelek", o.getOddelek());
                    jo.put("karticaId", o.getKarticaId());
                    jo.put("kartica2Id", o.getKartica2Id());
                    jo.put("pravice", o.getPravice());
                    jo.put("prioriteta", o.getPrioriteta());
                    jo.put("veljavnostPin", o.getVeljavnostPin());
                    if (o.getDatumSpremembePin() != null) {
                        jo.put("datumSpremembePin", o.getDatumSpremembePin().getTime());
                    }
                    arrOsebe.put(jo);
                }
            }
            root.put("osebe", arrOsebe);

            // Prioritete
            JSONArray arrPrio = new JSONArray();
            if (setup.getPrioriteteProjektov() != null) {
                for (PrioritetaProjektaTp p : setup.getPrioriteteProjektov()) {
                    JSONObject jp = new JSONObject();
                    jp.put("caption", p.getCaption());
                    jp.put("pozicijaId", p.getPozicijaId());
                    arrPrio.put(jp);
                }
            }
            root.put("prioritete", arrPrio);

            // Tarife
            JSONArray arrTarife = new JSONArray();
            if (setup.getTarife() != null) {
                for (TarifaTp t : setup.getTarife()) {
                    JSONObject jt = new JSONObject();
                    jt.put("tarifaId", t.getTarifaId());
                    jt.put("naziv", t.getNaziv());
                    jt.put("oznaka", t.getOznaka());
                    if (t.getMetodaId() != null) jt.put("metodaId", t.getMetodaId());
                    if (t.getDavekProc() != null) jt.put("davekProc", t.getDavekProc().toString());
                    arrTarife.put(jt);
                }
            }
            root.put("tarife", arrTarife);

            // Mize
            JSONArray arrMize = new JSONArray();
            if (setup.getMobileSetupMize() != null) {
                for (MizaTp m : setup.getMobileSetupMize()) {
                    JSONObject jm = new JSONObject();
                    jm.put("naziv", m.getNaziv());
                    jm.put("rajon", m.getRajon());
                    jm.put("zap", m.getZap());
                    arrMize.put(jm);
                }
            }
            root.put("mize", arrMize);

            // MobileSetupPlacila (seznam dovoljenih ID-jev placil)
            JSONArray arrPlac = new JSONArray();
            if (setup.getMobileSetupPlacila() != null) {
                for (Integer pId : setup.getMobileSetupPlacila()) {
                    if (pId != null) arrPlac.put(pId);
                }
            }
            root.put("mobileSetupPlacila", arrPlac);

            File file = new File(context.getFilesDir(), SIFRANTI_FILENAME);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(root.toString().getBytes(StandardCharsets.UTF_8));
            }
            Log.d(TAG, "Lokalni šifranti uspešno shranjeni: " + file.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "Napaka pri shranjevanju lokalnih šifrantov", e);
        }
    }

    public static synchronized boolean loadSifrantiIfValid(Context context) {
        if (context == null) return false;
        File file = new File(context.getFilesDir(), SIFRANTI_FILENAME);
        if (!file.exists()) return false;

        try {
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(bytes);
            }
            String content = new String(bytes, StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(content);

            long ts = root.optLong("timestamp", 0);
            Globals g = Globals.getInstance();
            int maxDays = g.getCenikStDniObnova() > 0 ? g.getCenikStDniObnova() : 3;
            long ageDays = (System.currentTimeMillis() - ts) / (1000L * 60 * 60 * 24);

            if (g.isCenikLokalno() && ageDays > maxDays) {
                Log.d(TAG, "Lokalni šifranti so potekli (" + ageDays + " dni > " + maxDays + " dni)");
                // Cache je prestar za redno obnovo, vendar ga lahko vseeno uporabimo kot fallback ob odsotnosti povezave
            }

            // Osebe
            List<OsebaTp> osebe = new ArrayList<>();
            JSONArray arrOsebe = root.optJSONArray("osebe");
            if (arrOsebe != null) {
                for (int i = 0; i < arrOsebe.length(); i++) {
                    JSONObject jo = arrOsebe.getJSONObject(i);
                    OsebaTp o = new OsebaTp();
                    o.setOsebaId(jo.optInt("osebaId"));
                    o.setNaziv(jo.optString("naziv"));
                    o.setUporabniskoIme(jo.optString("uporabniskoIme"));
                    o.setPin(jo.optString("pin"));
                    o.setPrivilegiji(jo.optString("privilegiji"));
                    o.setOddelek(jo.optString("oddelek"));
                    o.setKarticaId(jo.optString("karticaId"));
                    o.setKartica2Id(jo.optString("kartica2Id"));
                    o.setPravice(jo.optString("pravice"));
                    o.setPrioriteta(jo.optInt("prioriteta"));
                    o.setVeljavnostPin(jo.optInt("veljavnostPin"));
                    if (jo.has("datumSpremembePin")) {
                        o.setDatumSpremembePin(new Date(jo.optLong("datumSpremembePin")));
                    }
                    osebe.add(o);
                }
            }

            // Prioritete
            List<PrioritetaProjektaTp> prioritete = new ArrayList<>();
            JSONArray arrPrio = root.optJSONArray("prioritete");
            if (arrPrio != null) {
                for (int i = 0; i < arrPrio.length(); i++) {
                    JSONObject jp = arrPrio.getJSONObject(i);
                    prioritete.add(new PrioritetaProjektaTp(jp.optString("caption"), jp.optInt("pozicijaId")));
                }
            }

            // Tarife
            List<TarifaTp> tarife = new ArrayList<>();
            JSONArray arrTarife = root.optJSONArray("tarife");
            if (arrTarife != null) {
                for (int i = 0; i < arrTarife.length(); i++) {
                    JSONObject jt = arrTarife.getJSONObject(i);
                    TarifaTp t = new TarifaTp();
                    t.setTarifaId(jt.optInt("tarifaId"));
                    t.setNaziv(jt.optString("naziv"));
                    t.setOznaka(jt.optString("oznaka"));
                    if (jt.has("metodaId")) t.setMetodaId(jt.optInt("metodaId"));
                    if (jt.has("davekProc")) {
                        t.setDavekProc(new BigDecimal(jt.optString("davekProc")));
                    }
                    tarife.add(t);
                }
            }

            // Mize
            List<MizaTp> mize = new ArrayList<>();
            JSONArray arrMize = root.optJSONArray("mize");
            if (arrMize != null) {
                for (int i = 0; i < arrMize.length(); i++) {
                    JSONObject jm = arrMize.getJSONObject(i);
                    mize.add(new MizaTp(jm.optString("naziv"), jm.optInt("rajon"), jm.optInt("zap")));
                }
            }

            g.setCachedSifranti(osebe, prioritete, tarife, mize);

            // MobileSetupPlacila
            JSONArray arrPlac = root.optJSONArray("mobileSetupPlacila");
            if (arrPlac != null && arrPlac.length() > 0) {
                List<Integer> placila = new ArrayList<>();
                for (int i = 0; i < arrPlac.length(); i++) {
                    placila.add(arrPlac.optInt(i));
                }
                g.getPlacilnaSredstva().clear();
                g.getPlacilnaSredstva().addAll(placila);
            }

            Log.d(TAG, "Lokalni šifranti naloženi: Osebe=" + osebe.size() + ", Prioritete=" + prioritete.size() + ", Tarife=" + tarife.size() + ", Mize=" + mize.size());
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Napaka pri branju lokalnih šifrantov", e);
            return false;
        }
    }
}
