package si.ros.RosKasa.soap;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.models.HitraTipkaTp;
import si.ros.RosKasa.models.KronologijaTp;
import si.ros.RosKasa.models.MobileSetupTp;
import si.ros.RosKasa.models.OsebaTokenResult;
import si.ros.RosKasa.models.RacunSeznamItem;

public class RosKasaSoapClient {
    public static final String NAMESPACE = "http://ros.si/R16";
    private static final int TIMEOUT_MS = 15000;
    private static final String TAG = "RosKasaSoapClient";
    private static final ExecutorService asyncExecutor = Executors.newCachedThreadPool();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface OnSoapErrorListener {
        void onError(String method, String errorMessage);
    }

    private static OnSoapErrorListener errorListener;

    public static void setErrorListener(OnSoapErrorListener listener) {
        errorListener = listener;
    }

    /**
     * Metoda za asinhron vpis kronologije (insertKronologija)
     * Obvezno izvede klic s parametroma OSEBA_ID = 9999 in OBRAT_ID = 512200
     */
    public static void vpisKronologijeAsync(String serverUrl, String token, String mobileId, String opisOperacije, Integer osebaId, Integer obratId) {
        int finalOsebaId = (osebaId != null) ? osebaId : 9999;
        int finalObratId = (obratId != null) ? obratId : 512200;

        if (opisOperacije != null && opisOperacije.length() > 950) {
            opisOperacije = opisOperacije.substring(0, 950);
        }

        KronologijaTp kronologija = new KronologijaTp(opisOperacije, finalOsebaId, finalObratId);

        asyncExecutor.execute(() -> {
            try {
                vpisKronologije(serverUrl, token, mobileId, kronologija);
                Log.d(TAG, "vpisKronologije USPEŠEN: " + kronologija.getOpisOperacije());
            } catch (Exception e) {
                String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
                String errorMsg = "vpisKronologije napaka: " + msg;
                Log.e(TAG, errorMsg, e);
                notifyError("insertKronologija", errorMsg);
            }
        });
    }

    /**
     * Klic WSDL metode insertKronologija
     */
    public static void vpisKronologije(String serverUrl, String token, String mobileId, KronologijaTp kronologija) throws Exception {
        String methodName = "insertKronologija";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);

        String opis = kronologija.getOpisOperacije();
        if (opis != null && opis.length() > 950) {
            opis = opis.substring(0, 950);
        }

        SoapObject rq = new SoapObject(NAMESPACE, "ArrayOfKronologijaTp");
        SoapObject item = new SoapObject(NAMESPACE, "KronologijaTp");
        item.addProperty("DATUM_URA", kronologija.getDatumUra());
        item.addProperty("OBRAT_ID", kronologija.getObratId());
        item.addProperty("OPIS_OPERACIJE", opis != null ? opis : "");
        item.addProperty("OSEBA_ID", kronologija.getOsebaId());

        rq.addProperty("KronologijaTp", item);
        request.addProperty("rq", rq);

        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response: " + transport.responseDump);
            checkResponseFault(envelope, methodName);
        } catch (Exception e) {
            if (transport.requestDump != null) Log.e(TAG, "insertKronologija Request: " + transport.requestDump);
            if (transport.responseDump != null) Log.e(TAG, "insertKronologija Response: " + transport.responseDump);
            throw e;
        }
    }

    /**
     * Klic WSDL metode getOsebaToken
     */
    public static OsebaTokenResult getOsebaToken(String serverUrl, String uIme, String geslo) throws Exception {
        String methodName = "getOsebaToken";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("uIme", uIme);
        request.addProperty("geslo", geslo);

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        OsebaTokenResult result = new OsebaTokenResult();

        try {
            transport.call(soapAction, envelope);

            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault fault = (SoapFault) envelope.bodyIn;
                result.setFault(fault.faultstring);
                result.setSuccess(false);
                notifyError(methodName, "SoapFault: " + fault.faultstring);
            } else if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.hasProperty("getOsebaTokenResult")) {
                    Object resObj = response.getProperty("getOsebaTokenResult");
                    if (resObj instanceof SoapObject) {
                        response = (SoapObject) resObj;
                    }
                }

                String tokenVal = getPropertyStringSafe(response, "token");
                String nazivVal = getPropertyStringSafe(response, "naziv");
                String faultVal = getPropertyStringSafe(response, "fault");

                result.setToken(tokenVal);
                result.setNaziv(nazivVal);
                result.setFault(faultVal);

                if (faultVal != null && !faultVal.isEmpty()) {
                    notifyError(methodName, "SERVER FAULT: " + faultVal);
                }
                result.setSuccess(result.getFault() == null || result.getFault().isEmpty());
            }
        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            String errorMsg = "getOsebaToken napaka: " + msg;
            Log.e(TAG, errorMsg, e);
            notifyError(methodName, errorMsg);
            result.setFault(msg);
            result.setSuccess(false);
        }

        String opis = "Klic getOsebaToken za uIme=" + uIme + " -> " + (result.isSuccess() ? "Uspeh" : "Napaka: " + result.getFault());
        vpisKronologijeAsync(serverUrl, result.getToken() != null ? result.getToken() : "", "", opis, 9999, 512200);

        return result;
    }

    /**
     * Klic WSDL metode getAppConfig za pridobitev nastavitvenih parametrov (MobileSetupTp)
     */
    public static MobileSetupTp getAppConfig(String serverUrl, String token) throws Exception {
        String methodName = "getAppConfig";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        MobileSetupTp setup = new MobileSetupTp();

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);

            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault fault = (SoapFault) envelope.bodyIn;
                String faultMsg = "SoapFault: " + fault.faultstring;
                notifyError(methodName, faultMsg);
                throw new Exception(faultMsg);
            }

            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.hasProperty("getAppConfigResult")) {
                    Object resObj = response.getProperty("getAppConfigResult");
                    if (resObj instanceof SoapObject) {
                        response = (SoapObject) resObj;
                    }
                }

                checkResponseFault(envelope, methodName);

                if (response.hasProperty("MobileSetup")) {
                    Object msObj = response.getProperty("MobileSetup");
                    if (msObj instanceof SoapObject) {
                        SoapObject msSoap = (SoapObject) msObj;
                        setup = parseMobileSetupTp(msSoap);
                    }
                }
            }

            String opis = "Klic getAppConfig (TIPKE_POS_ID=" + setup.getTipkePosId() + ", HIS_OBRAT=" + setup.getHisObrat() + ") -> uspeh.";
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, setup.getHisObrat());

        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            String errorMsg = "getAppConfig napaka: " + msg;
            Log.e(TAG, errorMsg, e);
            notifyError(methodName, errorMsg);

            String opis = "Klic getAppConfig NAPAKA: " + msg;
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, 512200);

            throw e;
        }

        return setup;
    }

    private static MobileSetupTp parseMobileSetupTp(SoapObject soap) {
        if (soap == null) return new MobileSetupTp();
        MobileSetupTp setup = new MobileSetupTp();

        String tipkePosIdStr = getPropertyStringSafe(soap, "TIPKE_POS_ID");
        if (tipkePosIdStr != null) {
            try { setup.setTipkePosId(Integer.parseInt(tipkePosIdStr)); } catch (Exception ignored) {}
        }

        String hisObratStr = getPropertyStringSafe(soap, "HIS_OBRAT");
        if (hisObratStr != null) {
            try { setup.setHisObrat(Integer.parseInt(hisObratStr)); } catch (Exception ignored) {}
        }

        String fPosIdStr = getPropertyStringSafe(soap, "F_POS_ID");
        if (fPosIdStr != null) {
            try { setup.setfPosId(Integer.parseInt(fPosIdStr)); } catch (Exception ignored) {}
        }

        String fPosProstorStr = getPropertyStringSafe(soap, "F_POSLOVNI_PROSTOR_ID");
        if (fPosProstorStr != null) {
            try { setup.setfPoslovniProstorId(Integer.parseInt(fPosProstorStr)); } catch (Exception ignored) {}
        }

        String nazivPodjetja = getPropertyStringSafe(soap, "NAZIVPODJETJA");
        if (nazivPodjetja != null) setup.setNazivPodjetja(nazivPodjetja);

        String davcna = getPropertyStringSafe(soap, "DAVCNA_ZAFURS");
        if (davcna != null) setup.setDavcnaZaFurs(davcna);

        String printer = getPropertyStringSafe(soap, "PRINTER_RACUNI");
        if (printer != null) setup.setPrinterRacuni(printer);

        return setup;
    }

    /**
     * Klic WSDL metode getRacuniSeznam (rq: GetRacuniRqTp z parametri STATUS in OD_DATUM)
     */
    public static List<RacunSeznamItem> getRacuniSeznam(String serverUrl, String token, String mobileId, int status, String odDatum) throws Exception {
        String methodName = "getRacuniSeznam";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);

        SoapObject rq = new SoapObject(NAMESPACE, "GetRacuniRqTp");
        rq.addProperty("STATUS", status);
        if (odDatum != null && !odDatum.isEmpty()) {
            rq.addProperty("OD_DATUM", odDatum);
        }

        request.addProperty("rq", rq);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        List<RacunSeznamItem> list = new ArrayList<>();

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);

            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault fault = (SoapFault) envelope.bodyIn;
                String faultMsg = "SoapFault: " + fault.faultstring;
                notifyError(methodName, faultMsg);
                throw new Exception(faultMsg);
            }

            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;

                if (response.hasProperty("getRacuniSeznamResult")) {
                    Object resObj = response.getProperty("getRacuniSeznamResult");
                    if (resObj instanceof SoapObject) {
                        response = (SoapObject) resObj;
                    }
                }

                String faultVal = getPropertyStringSafe(response, "fault");
                if (faultVal != null && !faultVal.isEmpty()) {
                    String errorMsg = "SERVER FAULT v " + methodName + ": " + faultVal;
                    notifyError(methodName, errorMsg);
                    throw new Exception(errorMsg);
                }

                if (response.hasProperty("Racuni")) {
                    Object racuniProperty = response.getProperty("Racuni");
                    if (racuniProperty instanceof SoapObject) {
                        SoapObject racuniObj = (SoapObject) racuniProperty;
                        int count = racuniObj.getPropertyCount();
                        for (int i = 0; i < count; i++) {
                            Object itemObj = racuniObj.getProperty(i);
                            if (itemObj instanceof SoapObject) {
                                SoapObject itemSoap = (SoapObject) itemObj;
                                RacunSeznamItem item = parseRacunSeznamItem(itemSoap);
                                if (item != null) {
                                    list.add(item);
                                }
                            }
                        }
                    }
                }
            }

            String opis = "Klic getRacuniSeznam (STATUS=" + status + ", OD_DATUM=" + odDatum + ") -> pridobljeno " + list.size() + " računov.";
            vpisKronologijeAsync(serverUrl, token, mobileId, opis, 9999, 512200);

        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            String errorMsg = "getRacuniSeznam napaka: " + msg;
            Log.e(TAG, errorMsg, e);
            notifyError(methodName, errorMsg);

            String opis = "Klic getRacuniSeznam NAPAKA: " + msg;
            vpisKronologijeAsync(serverUrl, token, mobileId, opis, 9999, 512200);

            throw e;
        }

        return list;
    }

    /**
     * Klic WSDL metode getHitreTipke (stroskovnoId, token)
     */
    public static List<HitraTipkaTp> getHitreTipke(String serverUrl, String token, int stroskovnoId) throws Exception {
        String methodName = "getHitreTipke";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("stroskovnoId", stroskovnoId);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        List<HitraTipkaTp> list = new ArrayList<>();

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);

            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault fault = (SoapFault) envelope.bodyIn;
                String faultMsg = "SoapFault: " + fault.faultstring;
                notifyError(methodName, faultMsg);
                throw new Exception(faultMsg);
            }

            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;

                if (response.hasProperty("getHitreTipkeResult")) {
                    Object resObj = response.getProperty("getHitreTipkeResult");
                    if (resObj instanceof SoapObject) {
                        response = (SoapObject) resObj;
                    }
                }

                String faultVal = getPropertyStringSafe(response, "fault");
                if (faultVal != null && !faultVal.isEmpty()) {
                    String errorMsg = "SERVER FAULT v " + methodName + ": " + faultVal;
                    notifyError(methodName, errorMsg);
                    throw new Exception(errorMsg);
                }

                if (response.hasProperty("HITRETIPKE")) {
                    Object tipkeProperty = response.getProperty("HITRETIPKE");
                    if (tipkeProperty instanceof SoapObject) {
                        SoapObject tipkeObj = (SoapObject) tipkeProperty;
                        int count = tipkeObj.getPropertyCount();
                        for (int i = 0; i < count; i++) {
                            Object itemObj = tipkeObj.getProperty(i);
                            if (itemObj instanceof SoapObject) {
                                SoapObject itemSoap = (SoapObject) itemObj;
                                HitraTipkaTp item = parseHitraTipkaTp(itemSoap);
                                if (item != null) {
                                    list.add(item);
                                }
                            }
                        }
                    }
                }
            }

            String opis = "Klic getHitreTipke (stroskovnoId=" + stroskovnoId + ") -> pridobljeno " + list.size() + " hitrih tipk.";
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, 512200);

        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            String errorMsg = "getHitreTipke napaka: " + msg;
            Log.e(TAG, errorMsg, e);
            notifyError(methodName, errorMsg);

            String opis = "Klic getHitreTipke NAPAKA: " + msg;
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, 512200);

            throw e;
        }

        return list;
    }

    private static HitraTipkaTp parseHitraTipkaTp(SoapObject soap) {
        if (soap == null) return null;
        HitraTipkaTp item = new HitraTipkaTp();

        String tipkaIdStr = getPropertyStringSafe(soap, "TIPKA_ID");
        if (tipkaIdStr != null) {
            try { item.setTipkaId(Integer.parseInt(tipkaIdStr)); } catch (Exception ignored) {}
        }

        String nazivStr = getPropertyStringSafe(soap, "NAZIV");
        if (nazivStr != null) {
            item.setNaziv(nazivStr);
        }

        String barvaStr = getPropertyStringSafe(soap, "BARVA");
        if (barvaStr != null) {
            try { item.setBarva(Integer.parseInt(barvaStr)); } catch (Exception ignored) {}
        }

        String skupinaStr = getPropertyStringSafe(soap, "SKUPINA_ID");
        if (skupinaStr != null) {
            try { item.setSkupinaId(Integer.parseInt(skupinaStr)); } catch (Exception ignored) {}
        }

        String nivo4Str = getPropertyStringSafe(soap, "NIVO4_ID");
        if (nivo4Str != null) {
            try { item.setNivo4Id(Integer.parseInt(nivo4Str)); } catch (Exception ignored) {}
        }

        return item;
    }

    private static void checkResponseFault(SoapSerializationEnvelope envelope, String methodName) throws Exception {
        if (envelope == null || envelope.bodyIn == null) {
            return;
        }

        if (envelope.bodyIn instanceof SoapFault) {
            SoapFault fault = (SoapFault) envelope.bodyIn;
            String faultText = fault.faultstring != null ? fault.faultstring : fault.toString();
            String errorMsg = "SoapFault v " + methodName + ": " + faultText;
            notifyError(methodName, errorMsg);
            throw new Exception(errorMsg);
        }

        if (envelope.bodyIn instanceof SoapObject) {
            SoapObject body = (SoapObject) envelope.bodyIn;

            if (body.hasProperty(methodName + "Result")) {
                Object res = body.getProperty(methodName + "Result");
                if (res instanceof SoapObject) {
                    body = (SoapObject) res;
                }
            }

            String faultStr = getPropertyStringSafe(body, "fault");
            if (faultStr != null && !faultStr.isEmpty()) {
                String errorMsg = "SERVER FAULT v " + methodName + ": " + faultStr;
                notifyError(methodName, errorMsg);
                throw new Exception(errorMsg);
            }
        }
    }

    private static String getPropertyStringSafe(SoapObject soap, String name) {
        if (soap == null || !soap.hasProperty(name)) return null;
        Object obj = soap.getProperty(name);
        if (obj == null) return null;
        String str = obj.toString();
        if (str == null || str.equals("anyType{}")) return null;
        return str;
    }

    private static void notifyError(String method, String errorMsg) {
        if (errorMsg == null) errorMsg = "Neznana napaka (null)";
        final String finalMsg = errorMsg;
        if (errorListener != null) {
            mainHandler.post(() -> errorListener.onError(method, finalMsg));
        }
    }

    private static RacunSeznamItem parseRacunSeznamItem(SoapObject soap) {
        if (soap == null) return null;
        RacunSeznamItem item = new RacunSeznamItem();

        String racunIdStr = getPropertyStringSafe(soap, "RACUN_ID");
        if (racunIdStr != null) {
            try {
                item.setRacunId(Integer.parseInt(racunIdStr));
            } catch (Exception ignored) {}
        }

        String markerStr = getPropertyStringSafe(soap, "MARKER");
        if (markerStr != null) {
            item.setMarker(markerStr);
        }

        String statusStr = getPropertyStringSafe(soap, "STATUS");
        if (statusStr != null) {
            try {
                item.setStatus(Integer.parseInt(statusStr));
            } catch (Exception ignored) {}
        }

        String kasiralStr = getPropertyStringSafe(soap, "KASIRAL");
        if (kasiralStr != null) {
            try {
                item.setKasiral(Integer.parseInt(kasiralStr));
            } catch (Exception ignored) {}
        }

        String stornoStr = getPropertyStringSafe(soap, "STORNO_RACUN_ID");
        if (stornoStr != null) {
            try {
                item.setStornoRacunId(Integer.parseInt(stornoStr));
            } catch (Exception ignored) {}
        }

        String znesekStr = getPropertyStringSafe(soap, "ZNESEK");
        if (znesekStr != null) {
            try {
                item.setZnesek(new BigDecimal(znesekStr));
            } catch (Exception ignored) {}
        }

        return item;
    }

    private static SoapSerializationEnvelope createEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return envelope;
    }

    private static String formatEndpoint(String url) {
        if (url == null || url.isEmpty()) return "";

        if (url.toLowerCase().contains(".asmx") || url.toLowerCase().contains(".svc")) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                return "http://" + url;
            }
            return url;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        return url + "RosKasa_ceniki_wsdl.asmx";
    }
}
