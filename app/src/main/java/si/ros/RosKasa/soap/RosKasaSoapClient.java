package si.ros.RosKasa.soap;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.Globals;

import si.ros.RosKasa.models.CenikVrVrTp;
import si.ros.RosKasa.models.DodatekTp;
import si.ros.RosKasa.models.GetRacunRsTp;
import si.ros.RosKasa.models.HitraTipkaTp;
import si.ros.RosKasa.models.KronologijaTp;
import si.ros.RosKasa.models.MobileSetupTp;
import si.ros.RosKasa.models.NacPlacTp;
import si.ros.RosKasa.models.OsebaTokenResult;
import si.ros.RosKasa.models.PlaciloTp;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunSeznamItem;
import si.ros.RosKasa.models.RacunTp;
import si.ros.RosKasa.ui.CenikListAdapter;

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

    public static MobileSetupTp getAppConfig(String serverUrl, String token) throws Exception {
        return getAppConfig(serverUrl, token, 1);
    }

    public static MobileSetupTp getAppConfig(String serverUrl, String token, int mobileId) throws Exception {
        String methodName = "getAppConfig";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("mobileId", mobileId > 0 ? mobileId : 1);
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

                SoapObject msSoap = null;

                // 1. Preveri nesenzitivno po vseh pod-lastnostih
                for (int i = 0; i < response.getPropertyCount(); i++) {
                    PropertyInfo info = new PropertyInfo();
                    response.getPropertyInfo(i, info);
                    if (info.name != null && (info.name.equalsIgnoreCase("MobileSetup") ||
                            info.name.equalsIgnoreCase("MobileSetupTp") ||
                            info.name.equalsIgnoreCase("MOBILESETUP") ||
                            info.name.equalsIgnoreCase("mobilesetup"))) {
                        Object obj = response.getProperty(i);
                        if (obj instanceof SoapObject) {
                            msSoap = (SoapObject) obj;
                            break;
                        }
                    }
                }

                // 2. Če pod-lastnost ni bila najdena, uporabi sam response objekt
                if (msSoap == null) {
                    msSoap = response;
                }

                setup = parseMobileSetupTp(msSoap);
            }

            int finalMobId = mobileId > 0 ? mobileId : 1;
            Globals.getInstance().loadFromMobileSetup(setup, finalMobId);
            Globals.getInstance().setServerUrl(serverUrl);
            Globals.getInstance().setToken(token);

            Log.d(TAG, "MOBILE_SETUP PARSED SUCCESS: mobileId=" + setup.getMobileId() +
                    ", tipkePosId=" + setup.getTipkePosId() +
                    ", hisObrat=" + setup.getHisObrat() +
                    ", printerRacuni='" + Globals.getInstance().getPrinterRacuni() + "'");

            String opis = "Klic getAppConfig (PRINTER_RACUNI='" + Globals.getInstance().getPrinterRacuni() + "', TIPKE_POS_ID=" + setup.getTipkePosId() + ", HIS_OBRAT=" + setup.getHisObrat() + ") -> uspeh.";
            vpisKronologijeAsync(serverUrl, token, String.valueOf(finalMobId), opis, 9999, setup.getHisObrat());

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

        String mobileIdStr = getPropertyStringSafe(soap, "MOBILE_ID");
        if (mobileIdStr != null) {
            try { setup.setMobileId(Integer.parseInt(mobileIdStr)); } catch (Exception ignored) {}
        }

        String tipkePosIdStr = getPropertyStringSafe(soap, "TIPKE_POS_ID");
        if (tipkePosIdStr != null) {
            try { setup.setTipkePosId(Integer.parseInt(tipkePosIdStr)); } catch (Exception ignored) {}
        }

        String hisObratStr = getPropertyStringSafe(soap, "HIS_OBRAT");
        if (hisObratStr != null) {
            try { setup.setHisObrat(Integer.parseInt(hisObratStr)); } catch (Exception ignored) {}
        }

        String hisDestStr = getPropertyStringSafe(soap, "HIS_DESTINACIJA");
        if (hisDestStr != null) {
            try { setup.setHisDestinacija(Integer.parseInt(hisDestStr)); } catch (Exception ignored) {}
        }

        String fPosIdStr = getPropertyStringSafe(soap, "F_POS_ID");
        if (fPosIdStr != null) {
            try { setup.setfPosId(Integer.parseInt(fPosIdStr)); } catch (Exception ignored) {}
        }

        String fPosProstorStr = getPropertyStringSafe(soap, "F_POSLOVNI_PROSTOR_ID");
        if (fPosProstorStr != null) {
            try { setup.setfPoslovniProstorId(Integer.parseInt(fPosProstorStr)); } catch (Exception ignored) {}
        }

        String kuhinjaStr = getPropertyStringSafe(soap, "KUHINJA_ID");
        if (kuhinjaStr != null) {
            try { setup.setKuhinjaId(Integer.parseInt(kuhinjaStr)); } catch (Exception ignored) {}
        }

        String tocilnicaStr = getPropertyStringSafe(soap, "TOCILNICA_ID");
        if (tocilnicaStr != null) {
            try { setup.setTocilnicaId(Integer.parseInt(tocilnicaStr)); } catch (Exception ignored) {}
        }

        String fiskStr = getPropertyStringSafe(soap, "FISKALIZACIJA");
        if (fiskStr != null) {
            try { setup.setFiskalizacija(Integer.parseInt(fiskStr)); } catch (Exception ignored) {}
        }

        setup.setFirma(getPropertyStringSafe(soap, "FIRMA"));
        setup.setNaziv(getPropertyStringSafe(soap, "NAZIV"));
        setup.setNazivPodjetja(getPropertyStringSafe(soap, "NAZIVPODJETJA"));
        setup.setNaslovPodjetja(getPropertyStringSafe(soap, "NASLOVPODJETJA"));
        setup.setNazivProdajnegaMesta(getPropertyStringSafe(soap, "NAZIVPRODAJNEGAMESTA"));
        setup.setNaslovProdajnega(getPropertyStringSafe(soap, "NASLOVPRODAJNEGA"));
        setup.setObratProdajnegaMesta(getPropertyStringSafe(soap, "OBRATPRODAJNEGAMESTA"));

        String printer = getPropertyStringSafe(soap, "PRINTER_RACUNI");
        if (printer == null || printer.trim().isEmpty()) printer = getPropertyStringSafe(soap, "PRINTER_RACUN");
        if (printer == null || printer.trim().isEmpty()) printer = getPropertyStringSafe(soap, "PRINTERRACUNI");
        if (printer == null || printer.trim().isEmpty()) printer = getPropertyStringSafe(soap, "PRINTERRACUN");
        if (printer == null || printer.trim().isEmpty()) printer = getPropertyStringSafe(soap, "printer_racuni");
        if (printer == null || printer.trim().isEmpty()) printer = getPropertyStringSafe(soap, "printer_racun");
        setup.setPrinterRacuni(printer);

        setup.setDavcnaZaFurs(getPropertyStringSafe(soap, "DAVCNA_ZAFURS"));
        setup.setDdvPodjetja(getPropertyStringSafe(soap, "DDVPODJETJA"));
        setup.setNfcPrijava(getPropertyStringSafe(soap, "NFCPRIJAVA"));
        setup.setVnosPogrinjkov(getPropertyStringSafe(soap, "VNOSPOGRINJKOV"));
        setup.setHtColor(getPropertyStringSafe(soap, "HTCOLOR"));
        setup.setHtStyleName(getPropertyStringSafe(soap, "HTSTYLENAME"));
        setup.setPopustIzpis(getPropertyStringSafe(soap, "POPUSTIZPIS"));
        setup.setPopustLojalnost(getPropertyStringSafe(soap, "POPUSTLOJALNOST"));
        setup.setPopust99(getPropertyStringSafe(soap, "POPUST99"));
        setup.setkKarticeVPlacilih(getPropertyStringSafe(soap, "KKARTICEVPLACILIH"));
        setup.setNazivStregelVasJe(getPropertyStringSafe(soap, "NAZIVSTREGELVASJE"));
        setup.setNazivZahvala1(getPropertyStringSafe(soap, "NAZIVZAHVALA1"));
        setup.setNazivZahvala2(getPropertyStringSafe(soap, "NAZIVZAHVALA2"));
        setup.setNazivZahvala3(getPropertyStringSafe(soap, "NAZIVZAHVALA3"));
        setup.setNazivZahvala4(getPropertyStringSafe(soap, "NAZIVZAHVALA4"));
        setup.setMobIni0(getPropertyStringSafe(soap, "MOBINI0"));
        setup.setMobIni(getPropertyStringSafe(soap, "MOBINI"));
        setup.setMobIni2(getPropertyStringSafe(soap, "MOBINI2"));

        setup.setSteviloZnakov(getPropertyStringSafe(soap, "STEVILOZNAKOV"));
        setup.setEscAlignCenter(getPropertyStringSafe(soap, "ESCALIGNCENTER"));
        setup.setEscAlignLeft(getPropertyStringSafe(soap, "ESCALIGNLEFT"));
        setup.setEscAlignRight(getPropertyStringSafe(soap, "ESCALIGNRIGHT"));
        setup.setEscBoldOff(getPropertyStringSafe(soap, "ESCBOLDOFF"));
        setup.setEscBoldOn(getPropertyStringSafe(soap, "ESCBOLDON"));
        setup.setEscCpi16(getPropertyStringSafe(soap, "ESCCPI16"));
        setup.setEscCpi20(getPropertyStringSafe(soap, "ESCCPI20"));
        setup.setEscCut(getPropertyStringSafe(soap, "escCut"));
        if (setup.getEscCut() == null) setup.setEscCut(getPropertyStringSafe(soap, "ESCCUT"));
        setup.setEscEol(getPropertyStringSafe(soap, "ESCEOL"));
        setup.setEscInitPrint(getPropertyStringSafe(soap, "ESCINITPRINT"));
        setup.setEscInverseOff(getPropertyStringSafe(soap, "ESCINVERSEOFF"));
        setup.setEscInverseOn(getPropertyStringSafe(soap, "ESCINVERSEON"));
        setup.setEscNewLine(getPropertyStringSafe(soap, "ESCNEWLINE"));
        setup.setEscReset(getPropertyStringSafe(soap, "ESCRESET"));
        setup.setEscUnderlineOff(getPropertyStringSafe(soap, "ESCUNDERLINEOFF"));
        setup.setEscUnderlineOn(getPropertyStringSafe(soap, "ESCUNDERLINEON"));
        setup.setEscWidth2xOff(getPropertyStringSafe(soap, "ESCWIDTH2XOFF"));
        setup.setEscWidth2xOn(getPropertyStringSafe(soap, "ESCWIDTH2XON"));

        if (soap.hasProperty("MOBILE_SETUP_PLACILA")) {
            Object placilaObj = soap.getProperty("MOBILE_SETUP_PLACILA");
            if (placilaObj instanceof SoapObject) {
                SoapObject placilaSoap = (SoapObject) placilaObj;
                int pCount = placilaSoap.getPropertyCount();
                for (int i = 0; i < pCount; i++) {
                    try {
                        String val = placilaSoap.getPropertyAsString(i);
                        if (val != null && !val.trim().isEmpty()) {
                            setup.getMobileSetupPlacila().add(Integer.parseInt(val.trim()));
                        }
                    } catch (Exception ignored) {}
                }
            }
        }

        return setup;
    }

    public static List<CenikListAdapter.CenikItem> getCenik(String serverUrl, String token, int stroskovnoId) throws Exception {
        String methodName = "getCenik";
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

        List<CenikListAdapter.CenikItem> list = new ArrayList<>();

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);

            checkResponseFault(envelope, methodName);

            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.hasProperty("getCenikResult")) {
                    Object resObj = response.getProperty("getCenikResult");
                    if (resObj instanceof SoapObject) {
                        response = (SoapObject) resObj;
                    }
                }

                if (response.hasProperty("CENIKGL")) {
                    Object cglObj = response.getProperty("CENIKGL");
                    if (cglObj instanceof SoapObject) {
                        SoapObject cglSoap = (SoapObject) cglObj;
                        if (cglSoap.hasProperty("CENIKVR")) {
                            Object cvrObj = cglSoap.getProperty("CENIKVR");
                            if (cvrObj instanceof SoapObject) {
                                SoapObject cvrSoap = (SoapObject) cvrObj;
                                int count = cvrSoap.getPropertyCount();
                                for (int i = 0; i < count; i++) {
                                    Object itemObj = cvrSoap.getProperty(i);
                                    if (itemObj instanceof SoapObject) {
                                        SoapObject itemSoap = (SoapObject) itemObj;
                                        CenikListAdapter.CenikItem item = parseCenikVrItem(itemSoap);
                                        if (item != null) {
                                            list.add(item);
                                        }
                                    }
                                }
                            }
                        }
                        if (cglSoap.hasProperty("CENIKVRVR")) {
                            Object cvrvrObj = cglSoap.getProperty("CENIKVRVR");
                            if (cvrvrObj instanceof SoapObject) {
                                SoapObject cvrvrSoap = (SoapObject) cvrvrObj;
                                int vrvrCount = cvrvrSoap.getPropertyCount();
                                List<CenikVrVrTp> vrvrList = new ArrayList<>();
                                for (int j = 0; j < vrvrCount; j++) {
                                    Object vrvrObj = cvrvrSoap.getProperty(j);
                                    if (vrvrObj instanceof SoapObject) {
                                        CenikVrVrTp vrvrItem = parseCenikVrVrItem((SoapObject) vrvrObj);
                                        if (vrvrItem != null) {
                                            vrvrList.add(vrvrItem);
                                        }
                                    }
                                }
                                Globals.getInstance().setCachedCenikVrVr(vrvrList);
                                Log.d(TAG, "Naloženih CENIKVRVR sestavin paketov: " + vrvrList.size());
                            }
                        }
                    }
                }
            }

            String opis = "Klic getCenik (stroskovnoId=" + stroskovnoId + ") -> USPEH. Naloženih artiklov: " + list.size();
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, 512200);

        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            String errorMsg = "getCenik napaka: " + msg;
            Log.e(TAG, errorMsg, e);
            notifyError(methodName, errorMsg);

            String opis = "Klic getCenik NAPAKA: " + msg;
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, 512200);
            throw e;
        }

        return list;
    }

    private static CenikListAdapter.CenikItem parseCenikVrItem(SoapObject soap) {
        if (soap == null) return null;

        String nivo4IdStr = getPropertyStringSafe(soap, "NIVO4_ID");
        int nivo4Id = 0;
        if (nivo4IdStr != null) {
            try { nivo4Id = Integer.parseInt(nivo4IdStr); } catch (Exception ignored) {}
        }

        String naziv = getPropertyStringSafe(soap, "NAZIV");
        if (naziv == null || naziv.isEmpty()) {
            naziv = getPropertyStringSafe(soap, "NAZIV_ZA_RAC");
        }

        String cena1Str = getPropertyStringSafe(soap, "CENA1");
        BigDecimal cena = BigDecimal.ZERO;
        if (cena1Str != null) {
            try {
                cena1Str = cena1Str.replace(",", ".").trim();
                cena = new BigDecimal(cena1Str);
            } catch (Exception ignored) {}
        }

        String cena2Str = getPropertyStringSafe(soap, "CENA2");
        BigDecimal cena2 = BigDecimal.ZERO;
        if (cena2Str != null) {
            try {
                cena2Str = cena2Str.replace(",", ".").trim();
                cena2 = new BigDecimal(cena2Str);
            } catch (Exception ignored) {}
        }

        if (cena.compareTo(BigDecimal.ZERO) == 0 && cena2.compareTo(BigDecimal.ZERO) != 0) {
            cena = cena2;
        }

        String nacinProdajeStr = getPropertyStringSafe(soap, "NACIN_PRODAJE");
        int nacinProdaje = 0;
        if (nacinProdajeStr != null) {
            try { nacinProdaje = Integer.parseInt(nacinProdajeStr); } catch (Exception ignored) {}
        }

        String polnjenjeStr = getPropertyStringSafe(soap, "POLNJENJE");
        double polnjenje = 1.0;
        if (polnjenjeStr != null) {
            try {
                polnjenjeStr = polnjenjeStr.replace(",", ".").trim();
                polnjenje = Double.parseDouble(polnjenjeStr);
            } catch (Exception ignored) {}
        }

        String pluStr = getPropertyStringSafe(soap, "PLU_CODE");

        String paketStr = getPropertyStringSafe(soap, "PAKET");
        int paket = 0;
        if (paketStr != null) {
            try { paket = Integer.parseInt(paketStr); } catch (Exception ignored) {}
        }

        Globals g = Globals.getInstance();
        if (g.isCena2Aktivna()) {
            if (g.getModelCena2() == 1) {
                if (cena2.compareTo(BigDecimal.ZERO) != 0) cena = cena2;
            } else if (g.getModelCena2() == 2) {
                cena = cena2;
            }
        }

        CenikListAdapter.CenikItem item = new CenikListAdapter.CenikItem(nivo4Id, naziv, pluStr, cena, nacinProdaje, polnjenje);
        item.paket = paket;

        String tarifaIdStr = getPropertyStringSafe(soap, "TARIFA_ID");
        if (tarifaIdStr != null) {
            try { item.tarifaId = Integer.parseInt(tarifaIdStr); } catch (Exception ignored) {}
        }

        String nivo1IdStr = getPropertyStringSafe(soap, "NIVO1_ID");
        if (nivo1IdStr != null) {
            try { item.nivo1Id = Integer.parseInt(nivo1IdStr); } catch (Exception ignored) {}
        }

        String izvorStrmStr = getPropertyStringSafe(soap, "IZVOR_STRM_ID");
        if (izvorStrmStr != null) {
            try { item.izvorStrmId = Integer.parseInt(izvorStrmStr); } catch (Exception ignored) {}
        }

        String davekStr = getPropertyStringSafe(soap, "DAVEK_PROC");
        if (davekStr != null) {
            try { item.davekProc = Double.parseDouble(davekStr); } catch (Exception ignored) {}
        }

        return item;
    }

    private static CenikVrVrTp parseCenikVrVrItem(SoapObject soap) {
        if (soap == null) return null;
        CenikVrVrTp item = new CenikVrVrTp();

        String cvrNivo4Str = getPropertyStringSafe(soap, "CENIKVRNIVO4_ID");
        if (cvrNivo4Str != null) {
            try { item.setCenikvrnivo4Id(Integer.parseInt(cvrNivo4Str)); } catch (Exception ignored) {}
        }

        String nivo4Str = getPropertyStringSafe(soap, "NIVO4_ID");
        if (nivo4Str != null) {
            try { item.setNivo4Id(Integer.parseInt(nivo4Str)); } catch (Exception ignored) {}
        }

        String kolStr = getPropertyStringSafe(soap, "KOLICINA");
        if (kolStr != null) {
            try { item.setKolicina(Double.parseDouble(kolStr.replace(",", ".").trim())); } catch (Exception ignored) {}
        }

        String c1Str = getPropertyStringSafe(soap, "CENA1");
        if (c1Str != null) {
            try { item.setCena1(new BigDecimal(c1Str.replace(",", ".").trim())); } catch (Exception ignored) {}
        }

        String c2Str = getPropertyStringSafe(soap, "CENA2");
        if (c2Str != null) {
            try { item.setCena2(new BigDecimal(c2Str.replace(",", ".").trim())); } catch (Exception ignored) {}
        }

        String strmStr = getPropertyStringSafe(soap, "IZVOR_STRM_ID");
        if (strmStr != null) {
            try { item.setIzvorStrmId(Integer.parseInt(strmStr)); } catch (Exception ignored) {}
        }

        String prihStr = getPropertyStringSafe(soap, "IZVOR_PRIHODEK_ID");
        if (prihStr != null) {
            try { item.setIzvorPrihodekId(Integer.parseInt(prihStr)); } catch (Exception ignored) {}
        }

        String nivo1Str = getPropertyStringSafe(soap, "NIVO1_ID");
        if (nivo1Str != null) {
            try { item.setNivo1Id(Integer.parseInt(nivo1Str)); } catch (Exception ignored) {}
        }

        String popDaneStr = getPropertyStringSafe(soap, "POPUST_DANE");
        if (popDaneStr != null) {
            try { item.setPopustDane(Integer.parseInt(popDaneStr)); } catch (Exception ignored) {}
        }

        String cenikIdStr = getPropertyStringSafe(soap, "CENIK_ID");
        if (cenikIdStr != null) {
            try { item.setCenikId(Integer.parseInt(cenikIdStr)); } catch (Exception ignored) {}
        }

        return item;
    }

    public static List<DodatekTp> getDodatki(String serverUrl, String token) throws Exception {
        String methodName = "getDodatki";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        List<DodatekTp> result = new ArrayList<>();

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);
            checkResponseFault(envelope, methodName);

            SoapObject response = null;
            if (envelope.bodyIn instanceof SoapObject) {
                response = (SoapObject) envelope.bodyIn;
                if (response.hasProperty("getDodatkiResult")) {
                    Object resObj = response.getProperty("getDodatkiResult");
                    if (resObj instanceof SoapObject) response = (SoapObject) resObj;
                }
            }

            if (response != null && response.hasProperty("Dodatki")) {
                Object dodatkiProp = response.getProperty("Dodatki");
                if (dodatkiProp instanceof SoapObject) {
                    SoapObject dodatkiSoap = (SoapObject) dodatkiProp;
                    int count = dodatkiSoap.getPropertyCount();
                    for (int i = 0; i < count; i++) {
                        Object itemObj = dodatkiSoap.getProperty(i);
                        if (itemObj instanceof SoapObject) {
                            SoapObject dSoap = (SoapObject) itemObj;
                            int dId = 0;
                            String idStr = getPropertyStringSafe(dSoap, "DODATEK_ID");
                            if (idStr != null) {
                                try { dId = Integer.parseInt(idStr); } catch (Exception ignored) {}
                            }
                            String dText = getPropertyStringSafe(dSoap, "DODATEK_TEXT");
                            if (dText != null && !dText.trim().isEmpty()) {
                                result.add(new DodatekTp(dId, dText.trim()));
                            }
                        }
                    }
                }
            }

            Globals.getInstance().setCachedDodatki(result);
            String opis = "Klic getDodatki -> USPEH. Naloženih dodatkov/opomb: " + result.size();
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, 512200);

        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            Log.e(TAG, "getDodatki napaka: " + msg, e);
            notifyError(methodName, "getDodatki napaka: " + msg);
            throw e;
        }

        return result;
    }

    public static List<NacPlacTp> getNacPlac2(String serverUrl, String token, int mobileId) throws Exception {
        String methodName = "getNacPlac2";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("mobileId", mobileId > 0 ? mobileId : 1);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        List<NacPlacTp> result = new ArrayList<>();

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);
            checkResponseFault(envelope, methodName);

            SoapObject response = null;
            if (envelope.bodyIn instanceof SoapObject) {
                response = (SoapObject) envelope.bodyIn;
                if (response.hasProperty("getNacPlac2Result")) {
                    Object resObj = response.getProperty("getNacPlac2Result");
                    if (resObj instanceof SoapObject) response = (SoapObject) resObj;
                }
            }

            if (response != null && response.hasProperty("NACPLAC")) {
                Object nacPlacProp = response.getProperty("NACPLAC");
                if (nacPlacProp instanceof SoapObject) {
                    SoapObject nacPlacSoap = (SoapObject) nacPlacProp;
                    int count = nacPlacSoap.getPropertyCount();
                    for (int i = 0; i < count; i++) {
                        Object itemObj = nacPlacSoap.getProperty(i);
                        if (itemObj instanceof SoapObject) {
                            NacPlacTp np = parseNacPlacTp((SoapObject) itemObj);
                            if (np != null) result.add(np);
                        }
                    }
                }
            }

            String opis = "Klic getNacPlac2 (mobileId=" + mobileId + ") -> USPEH. Naloženih plačil: " + result.size();
            vpisKronologijeAsync(serverUrl, token, String.valueOf(mobileId), opis, 9999, 512200);

        } catch (Exception e) {
            Log.w(TAG, "getNacPlac2 ni uspel, poskus z getNacPlac fallback: " + e.getMessage());
            try {
                result = getNacPlac(serverUrl, token);
            } catch (Exception fallbackEx) {
                String msg = (e != null && e.getMessage() != null) ? e.getMessage() : "Neznana napaka";
                Log.e(TAG, "getNacPlac2 napaka: " + msg, e);
                notifyError(methodName, "getNacPlac2 napaka: " + msg);
                throw e;
            }
        }

        return result;
    }

    public static List<NacPlacTp> getNacPlac(String serverUrl, String token) throws Exception {
        String methodName = "getNacPlac";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        List<NacPlacTp> result = new ArrayList<>();
        transport.call(soapAction, envelope);
        checkResponseFault(envelope, methodName);

        SoapObject response = null;
        if (envelope.bodyIn instanceof SoapObject) {
            response = (SoapObject) envelope.bodyIn;
            if (response.hasProperty("getNacPlacResult")) {
                Object resObj = response.getProperty("getNacPlacResult");
                if (resObj instanceof SoapObject) response = (SoapObject) resObj;
            }
        }

        if (response != null && response.hasProperty("NACPLAC")) {
            Object nacPlacProp = response.getProperty("NACPLAC");
            if (nacPlacProp instanceof SoapObject) {
                SoapObject nacPlacSoap = (SoapObject) nacPlacProp;
                int count = nacPlacSoap.getPropertyCount();
                for (int i = 0; i < count; i++) {
                    Object itemObj = nacPlacSoap.getProperty(i);
                    if (itemObj instanceof SoapObject) {
                        NacPlacTp np = parseNacPlacTp((SoapObject) itemObj);
                        if (np != null) result.add(np);
                    }
                }
            }
        }
        return result;
    }

    private static NacPlacTp parseNacPlacTp(SoapObject soap) {
        if (soap == null) return null;
        NacPlacTp np = new NacPlacTp();

        String plIdStr = getPropertyStringSafe(soap, "PLACILO_ID");
        if (plIdStr != null) {
            try { np.setPlaciloId(Integer.parseInt(plIdStr)); } catch (Exception ignored) {}
        }

        String nazivStr = getPropertyStringSafe(soap, "NAZIV");
        if (nazivStr != null) np.setNaziv(nazivStr);

        String metodaStr = getPropertyStringSafe(soap, "METODA");
        if (metodaStr != null) {
            try { np.setMetoda(Integer.parseInt(metodaStr)); } catch (Exception ignored) {}
        }

        String fiskStr = getPropertyStringSafe(soap, "FISKALNO");
        if (fiskStr != null) {
            try { np.setFiskalno(Integer.parseInt(fiskStr)); } catch (Exception ignored) {}
        }

        String inkasoStr = getPropertyStringSafe(soap, "INKASO");
        if (inkasoStr != null) {
            try { np.setInkaso(Integer.parseInt(inkasoStr)); } catch (Exception ignored) {}
        }

        String kupecStr = getPropertyStringSafe(soap, "KUPEC_ID");
        if (kupecStr != null) {
            try { np.setKupecId(Long.parseLong(kupecStr)); } catch (Exception ignored) {}
        }

        String stKopijStr = getPropertyStringSafe(soap, "STKOPIJ");
        if (stKopijStr != null) {
            try { np.setStKopij(Integer.parseInt(stKopijStr)); } catch (Exception ignored) {}
        }

        String storitevStr = getPropertyStringSafe(soap, "STORITEV_ID");
        if (storitevStr != null) {
            try { np.setStoritevId(Integer.parseInt(storitevStr)); } catch (Exception ignored) {}
        }

        String vrstaStr = getPropertyStringSafe(soap, "VRSTA");
        if (vrstaStr != null) {
            try { np.setVrsta(Integer.parseInt(vrstaStr)); } catch (Exception ignored) {}
        }

        String crmStr = getPropertyStringSafe(soap, "CRM");
        if (crmStr != null) {
            try { np.setCrm(Integer.parseInt(crmStr)); } catch (Exception ignored) {}
        }

        String fPlaciloStr = getPropertyStringSafe(soap, "F_PLACILO");
        if (fPlaciloStr != null) np.setfPlacilo(fPlaciloStr);

        return np;
    }

    public static List<RacunSeznamItem> getRacuniSeznam(String serverUrl, String token, String mobileId, int status, String odDatum) throws Exception {
        String methodName = "getRacuniSeznam";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);

        SoapObject rq = new SoapObject(NAMESPACE, "GetRacuniRqTp");
        if (odDatum != null && !odDatum.isEmpty()) {
            rq.addProperty("OD_DATUM", odDatum);
        }
        rq.addProperty("STATUS", status);

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

        String enotaStr = getPropertyStringSafe(soap, "ENOTA_PRODAJE");
        if (enotaStr != null) {
            try {
                enotaStr = enotaStr.replace(",", ".").trim();
                item.setEnotaProdaje(new BigDecimal(enotaStr));
            } catch (Exception ignored) {}
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
        if (soap == null || name == null) return null;
        if (soap.hasProperty(name)) {
            Object obj = soap.getProperty(name);
            if (obj != null && !obj.toString().equals("anyType{}")) {
                return obj.toString();
            }
        }
        for (int i = 0; i < soap.getPropertyCount(); i++) {
            PropertyInfo info = new PropertyInfo();
            soap.getPropertyInfo(i, info);
            if (info.name != null && info.name.equalsIgnoreCase(name)) {
                Object obj = soap.getProperty(i);
                if (obj != null && !obj.toString().equals("anyType{}")) {
                    return obj.toString();
                }
            }
        }
        return null;
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

    public static int getVerzijaOfRacglava(String serverUrl, String token, int racunId) throws Exception {
        String methodName = "getVerzijaOfRacglava";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("racunId", racunId);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);
            checkResponseFault(envelope, methodName);

            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;
                String res = getPropertyStringSafe(response, "getVerzijaOfRacglavaResult");
                if (res != null) {
                    try { return Integer.parseInt(res); } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            Log.e(TAG, "getVerzijaOfRacglava napaka: " + msg, e);
            throw e;
        }
        return 0;
    }

    public static RacunTp getRacun(String serverUrl, String token, int racunId) throws Exception {
        String methodName = "getRacuni";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        SoapObject rq = new SoapObject(NAMESPACE, "GetRacuniRqTp");
        rq.addProperty("DO_RACUN_ID", racunId);
        rq.addProperty("OD_RACUN_ID", racunId);
        request.addProperty("rq", rq);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        RacunTp racun = null;

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);
            checkResponseFault(envelope, methodName);

            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.hasProperty("getRacuniResult")) {
                    Object resObj = response.getProperty("getRacuniResult");
                    if (resObj instanceof SoapObject) response = (SoapObject) resObj;
                }

                if (response.hasProperty("Racuni")) {
                    Object racuniProp = response.getProperty("Racuni");
                    if (racuniProp instanceof SoapObject) {
                        SoapObject racuniSoap = (SoapObject) racuniProp;
                        int count = racuniSoap.getPropertyCount();
                        for (int i = 0; i < count; i++) {
                            Object itemObj = racuniSoap.getProperty(i);
                            if (itemObj instanceof SoapObject) {
                                RacunTp candidate = parseRacunTp((SoapObject) itemObj);
                                if (candidate != null) {
                                    if (candidate.getRacunId() == racunId) {
                                        racun = candidate;
                                        break;
                                    } else if (racun == null) {
                                        racun = candidate;
                                    }
                                }
                            }
                        }
                    }
                }
                if (racun == null && response.hasProperty("RacunTp")) {
                    Object rtObj = response.getProperty("RacunTp");
                    if (rtObj instanceof SoapObject) {
                        racun = parseRacunTp((SoapObject) rtObj);
                    }
                }
                if (racun == null && response.hasProperty("RACGLAVA")) {
                    Object rgObj = response.getProperty("RACGLAVA");
                    if (rgObj instanceof SoapObject) {
                        racun = parseRacunTp((SoapObject) rgObj);
                    }
                }
            }

            if (racun != null) {
                racun.preracunajVsote();
                racun.setOriginalObject(racun.deepCopy());
                if (racun.getRacPozic() != null) {
                    for (PozicijaTp p : racun.getRacPozic()) {
                        if (p != null) p.setOriginalObject(p.deepCopy());
                    }
                }
                if (racun.getRacPlaci() != null) {
                    for (PlaciloTp pl : racun.getRacPlaci()) {
                        if (pl != null) pl.setOriginalObject(pl.deepCopy());
                    }
                }
            }

            String opis = "Klic getRacuni (RACUN_ID=" + racunId + ") -> " + (racun != null ? "Naložen račun V=" + racun.getVerzijaZapisa() + " Pozicij=" + (racun.getRacPozic() != null ? racun.getRacPozic().size() : 0) : "Ni podatkov");
            vpisKronologijeAsync(serverUrl, token, "", opis, 9999, 512200);

        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            Log.e(TAG, "getRacun napaka: " + msg, e);
            notifyError(methodName, "getRacun napaka: " + msg);
            vpisKronologijeAsync(serverUrl, token, "", "Klic getRacun NAPAKA: R:" + racunId + " " + msg, 9999, 512200);
            throw e;
        }

        return racun;
    }

    public static GetRacunRsTp setRacun(String serverUrl, String token, int mobileId, RacunTp racun) throws Exception {
        String methodName = "setRacun";
        String soapAction = NAMESPACE + "/" + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        SoapObject racunSoap = buildSoapRacunTp(racun, 0);
        request.addProperty("racun", racunSoap);
        request.addProperty("mobileId", mobileId > 0 ? mobileId : 1);
        if (token != null && !token.isEmpty()) {
            request.addProperty("token", token);
        }

        SoapSerializationEnvelope envelope = createEnvelope(request);
        String fullEndpoint = formatEndpoint(serverUrl);
        HttpTransportSE transport = new HttpTransportSE(fullEndpoint, TIMEOUT_MS);
        transport.debug = true;

        GetRacunRsTp result = new GetRacunRsTp();

        try {
            transport.call(soapAction, envelope);
            if (transport.requestDump != null) Log.d(TAG, methodName + " Request XML: " + transport.requestDump);
            if (transport.responseDump != null) Log.d(TAG, methodName + " Response XML: " + transport.responseDump);

            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault fault = (SoapFault) envelope.bodyIn;
                String faultStr = fault.faultstring != null ? fault.faultstring : fault.toString();
                notifyError(methodName, "SoapFault: " + faultStr);
                if (faultStr.toLowerCase().contains("verzija") || faultStr.toLowerCase().contains("version") || faultStr.toLowerCase().contains("race")) {
                    throw new VersionConflictException(racun.getRacunId(), racun.getVerzijaZapisa(), -1, faultStr);
                }
                throw new Exception(faultStr);
            }

            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.bodyIn;
                if (response.hasProperty("setRacunResult")) {
                    Object resObj = response.getProperty("setRacunResult");
                    if (resObj instanceof SoapObject) response = (SoapObject) resObj;
                }

                String faultVal = getPropertyStringSafe(response, "fault");
                String data1Val = getPropertyStringSafe(response, "data1");
                result.setFault(faultVal);
                result.setData1(data1Val);

                if (faultVal != null && !faultVal.isEmpty()) {
                    if (faultVal.toLowerCase().contains("verzija") || faultVal.toLowerCase().contains("version") || faultVal.toLowerCase().contains("race")) {
                        throw new VersionConflictException(racun.getRacunId(), racun.getVerzijaZapisa(), -1, faultVal);
                    }
                    throw new Exception("SERVER FAULT v setRacun: " + faultVal);
                }

                if (response.hasProperty("RACGLAVA")) {
                    Object rgObj = response.getProperty("RACGLAVA");
                    if (rgObj instanceof SoapObject) {
                        RacunTp returnedRacun = parseRacunTp((SoapObject) rgObj);
                        if (returnedRacun != null) {
                            returnedRacun.setOriginalObject(returnedRacun.deepCopy());
                            if (returnedRacun.getRacPozic() != null) {
                                for (PozicijaTp p : returnedRacun.getRacPozic()) {
                                    if (p != null) p.setOriginalObject(p.deepCopy());
                                }
                            }
                            if (returnedRacun.getRacPlaci() != null) {
                                for (PlaciloTp pl : returnedRacun.getRacPlaci()) {
                                    if (pl != null) pl.setOriginalObject(pl.deepCopy());
                                }
                            }
                            result.setRacGlava(returnedRacun);
                        }
                    }
                }
            }

            String opis = "Klic setRacun (RACUN_ID=" + racun.getRacunId() + ", STATUS=" + racun.getStatus() + ") -> USPEH. Nova VERZIJA=" + (result.getRacGlava() != null ? result.getRacGlava().getVerzijaZapisa() : "?");
            vpisKronologijeAsync(serverUrl, token, String.valueOf(mobileId), opis, 9999, 512200);

        } catch (VersionConflictException vce) {
            String opis = "Klic setRacun KONFLIKT VERZIJE: R:" + racun.getRacunId() + " " + vce.getMessage();
            Log.w(TAG, opis);
            vpisKronologijeAsync(serverUrl, token, String.valueOf(mobileId), opis, 9999, 512200);
            throw vce;
        } catch (Exception e) {
            String msg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
            String opis = "Klic setRacun NAPAKA: R:" + racun.getRacunId() + " " + msg;
            Log.e(TAG, opis, e);
            notifyError(methodName, opis);
            vpisKronologijeAsync(serverUrl, token, String.valueOf(mobileId), opis, 9999, 512200);
            throw e;
        }

        return result;
    }

    private static SoapObject buildSoapRacunTp(RacunTp racun, int depth) {
        SoapObject soap = new SoapObject(NAMESPACE, "RacunTp");
        int racunId = racun.getRacunId();
        if (racunId == 0) racunId = -1;

        if (racun.getCrmId() != null && !racun.getCrmId().isEmpty()) {
            soap.addProperty("CRM_ID", racun.getCrmId());
        }

        // DATUM: yyyy-MM-ddT00:00:00Z
        String datumStr = racun.getDatum();
        if (datumStr == null || datumStr.trim().isEmpty()) {
            java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd'T'00:00:00'Z'", java.util.Locale.US);
            sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            datumStr = sdfDate.format(new java.util.Date());
        }
        soap.addProperty("DATUM", datumStr);

        if (racun.getDnId() != null && !racun.getDnId().isEmpty()) {
            soap.addProperty("DN_ID", racun.getDnId());
        }

        // Todo.md: Update FISKALIZACIJA je samo za FISKALIZACIJA=1 drugače je null.
        if (racun.getFiskalizacija() != null && racun.getFiskalizacija() == 1) {
            soap.addProperty("FISKALIZACIJA", 1);
        }
        if (racun.getfOznakaDu() != null && !racun.getfOznakaDu().isEmpty()) {
            soap.addProperty("F_OZNAKA_DU", racun.getfOznakaDu());
        }
        if (racun.getfPodpis() != null && !racun.getfPodpis().isEmpty()) {
            soap.addProperty("F_PODPIS", racun.getfPodpis());
        }

        int fPoslovniProstorId = (racun.getfPoslovniProstorId() != null && racun.getfPoslovniProstorId() > 0)
                ? racun.getfPoslovniProstorId()
                : (Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000);
        soap.addProperty("F_POSLOVNI_PROSTOR_ID", fPoslovniProstorId);

        int tipkePosIdVal = Globals.getInstance().getTipkePosId();
        int fPosId = (racun.getfPosId() != null && racun.getfPosId() > 0 && (tipkePosIdVal <= 0 || racun.getfPosId() != tipkePosIdVal || racun.getfPosId() == Globals.getInstance().getfPosId()))
                ? racun.getfPosId()
                : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500);
        soap.addProperty("F_POS_ID", fPosId);

        if (racun.getfPrintKoda() != null && !racun.getfPrintKoda().isEmpty()) {
            soap.addProperty("F_PRINT_KODA", racun.getfPrintKoda());
        }
        if (racun.getfPrintVrsta() != null && !racun.getfPrintVrsta().isEmpty()) {
            soap.addProperty("F_PRINT_VRSTA", racun.getfPrintVrsta());
        }
        if (racun.getfStevilkaRacuna() != null && racun.getfStevilkaRacuna() != 0) {
            soap.addProperty("F_STEVILKA_RACUNA", racun.getfStevilkaRacuna());
        }

        int kasiral = (racun.getKasiral() != null && racun.getKasiral() > 0) ? racun.getKasiral() : 9999;
        soap.addProperty("KASIRAL", kasiral);

        if (racun.getLojalnostId() != null && racun.getLojalnostId() != 0) {
            soap.addProperty("LOJALNOST_ID", racun.getLojalnostId());
        }

        if (racun.getMarker() != null && !racun.getMarker().isEmpty()) {
            soap.addProperty("MARKER", racun.getMarker());
        }

        // PLACANO
        soap.addProperty("PLACANO", racun.getPlacano() != null ? racun.getPlacano().toPlainString() : "0.00");

        // RACPLACI - pred RACPOZIC po WSDL shemi
        if (racun.getRacPlaci() != null && !racun.getRacPlaci().isEmpty()) {
            SoapObject racPlaciSoap = new SoapObject(NAMESPACE, "ArrayOfPlaciloTp");
            int negPlId = -1;
            for (int i = 0; i < racun.getRacPlaci().size(); i++) {
                PlaciloTp pl = racun.getRacPlaci().get(i);
                if (pl != null) {
                    int defaultPlId = (pl.getPozicijaId() != 0) ? pl.getPozicijaId() : (negPlId--);
                    racPlaciSoap.addProperty("PlaciloTp", buildSoapPlaciloTp(pl, racunId, defaultPlId, depth));
                }
            }
            requestPropertySafe(soap, "RACPLACI", racPlaciSoap);
        }

        // RACPOZIC - po RACPLACI in pred RACUN_ID po WSDL shemi
        if (racun.getRacPozic() != null) {
            SoapObject racPozicSoap = new SoapObject(NAMESPACE, "ArrayOfPozicijaTp");
            int negIdx = -1;
            for (int i = 0; i < racun.getRacPozic().size(); i++) {
                PozicijaTp poz = racun.getRacPozic().get(i);
                if (poz != null) {
                    int defaultPozId = (poz.getPozicijaId() != 0) ? poz.getPozicijaId() : (negIdx--);
                    racPozicSoap.addProperty("PozicijaTp", buildSoapPozicijaTp(poz, racunId, defaultPozId, depth));
                }
            }
            requestPropertySafe(soap, "RACPOZIC", racPozicSoap);
        }

        soap.addProperty("RACUN_ID", racunId);
        soap.addProperty("RowDeleted", racun.isRowDeleted());

        int status = racun.getStatus();
        if (status <= 0) status = 1;
        soap.addProperty("STATUS", status);

        int stKopij = racun.getStKopij() != null ? racun.getStKopij() : 0;
        soap.addProperty("STKOPIJ", stKopij);

        if (racun.getStornoOriginal() != null && racun.getStornoOriginal() != 0) {
            soap.addProperty("STORNO_ORIGINAL", racun.getStornoOriginal());
        }
        if (racun.getStornoOsebaId() != null && racun.getStornoOsebaId() != 0) {
            soap.addProperty("STORNO_OSEBA_ID", racun.getStornoOsebaId());
        }
        if (racun.getStornoRacunId() != null && racun.getStornoRacunId() != 0) {
            soap.addProperty("STORNO_RACUN_ID", racun.getStornoRacunId());
        }

        int stPogrinjkov = (racun.getStPogrinjkov() != null && racun.getStPogrinjkov() > 0) ? racun.getStPogrinjkov() : 1;
        soap.addProperty("STPOGRINJKOV", stPogrinjkov);

        int tipRacuna = (racun.getTipRacuna() != null && racun.getTipRacuna() > 0) ? racun.getTipRacuna() : 1;
        soap.addProperty("TIP_RACUNA", tipRacuna);

        int tocilnicaId = (racun.getTocilnicaId() != null && racun.getTocilnicaId() > 0)
                ? racun.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        soap.addProperty("TOCILNICA_ID", tocilnicaId);

        // URA: 1899-12-30THH:mm:ssZ
        String uraStr = racun.getUra();
        if (uraStr == null || uraStr.trim().isEmpty()) {
            java.text.SimpleDateFormat sdfTime = new java.text.SimpleDateFormat("'1899-12-30T'HH:mm:ss'Z'", java.util.Locale.US);
            sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            uraStr = sdfTime.format(new java.util.Date());
        }
        soap.addProperty("URA", uraStr);

        // URA_PLACILA: 1899-12-30THH:mm:ssZ (ko je račun plačan ali ima nastavljeno uro plačila)
        if (racun.getUraPlacila() != null && !racun.getUraPlacila().isEmpty()) {
            soap.addProperty("URA_PLACILA", racun.getUraPlacila());
        } else if (racun.getPlacano() != null && racun.getPlacano().compareTo(BigDecimal.ZERO) > 0) {
            java.text.SimpleDateFormat sdfUraPlacila = new java.text.SimpleDateFormat("'1899-12-30T'HH:mm:ss'Z'", java.util.Locale.US);
            sdfUraPlacila.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            soap.addProperty("URA_PLACILA", sdfUraPlacila.format(new java.util.Date()));
        }

        if (racun.getUrejamStorno() != null) {
            soap.addProperty("UREJAMSTORNO", racun.getUrejamStorno());
        }

        soap.addProperty("VERZIJA_ZAPISA", racun.getVerzijaZapisa());

        if (racun.getvStatus() != null && racun.getvStatus() > 0) {
            soap.addProperty("V_STATUS", racun.getvStatus());
        }

        soap.addProperty("ZNESEK", racun.getZnesek() != null ? racun.getZnesek().toPlainString() : "0.00");

        // Vgnezdena zgodovina __OriginalObject (do 3 stopnje) - po ZNESEK po WSDL shemi
        if (depth < 3 && racun.getOriginalObject() != null) {
            soap.addProperty("__OriginalObject", buildSoapRacunTp(racun.getOriginalObject(), depth + 1));
        }

        if (racun.getStornoRazlogId() != null && racun.getStornoRazlogId() != 0) {
            soap.addProperty("STORNO_RAZLOG_ID", racun.getStornoRazlogId());
        }
        if (racun.getLokator() != null && !racun.getLokator().isEmpty()) {
            soap.addProperty("LOKATOR", racun.getLokator());
        }
        if (racun.getOpomba() != null && !racun.getOpomba().isEmpty()) {
            soap.addProperty("OPOMBA", racun.getOpomba());
        }

        return soap;
    }

    private static void requestPropertySafe(SoapObject parent, String name, Object val) {
        if (parent != null && name != null && val != null) {
            parent.addProperty(name, val);
        }
    }

    private static SoapObject buildSoapPozicijaTp(PozicijaTp poz, int parentRacunId, int defaultPozId, int depth) {
        SoapObject soap = new SoapObject(NAMESPACE, "PozicijaTp");

        int pozId = poz.getPozicijaId() != 0 ? poz.getPozicijaId() : defaultPozId;
        soap.addProperty("POZICIJA_ID", pozId);

        int rId = poz.getRacunId() != 0 ? poz.getRacunId() : parentRacunId;
        if (rId == 0) rId = -1;
        soap.addProperty("RACUN_ID", rId);
        soap.addProperty("VERZIJA_ZAPISA", poz.getVerzijaZapisa());

        int cenikId = (poz.getCenikId() != null && poz.getCenikId() > 0) ? poz.getCenikId() : 11246;
        soap.addProperty("CENIK_ID", cenikId);

        int nivo4Id = poz.getNivo4Id() != null ? poz.getNivo4Id() : 0;
        if (nivo4Id <= 0 && poz.getNaziv() != null) {
            String n = poz.getNaziv().trim().toUpperCase(java.util.Locale.ROOT);
            if (Globals.getInstance().hasCachedCenik()) {
                for (CenikListAdapter.CenikItem ci : Globals.getInstance().getCachedCenik()) {
                    if (ci.naziv != null && (ci.naziv.trim().equalsIgnoreCase(n) || ci.naziv.toUpperCase(java.util.Locale.ROOT).contains(n) || n.contains(ci.naziv.toUpperCase(java.util.Locale.ROOT)))) {
                        nivo4Id = ci.nivo4Id;
                        if (ci.tarifaId > 0 && (poz.getTarifaId() == null || poz.getTarifaId() == 0)) {
                            poz.setTarifaId(ci.tarifaId);
                        }
                        if (ci.davekProc > 0 && poz.getStopnjaDavka() <= 0) {
                            poz.setStopnjaDavka(ci.davekProc);
                        }
                        break;
                    }
                }
            }
            if (nivo4Id <= 0) {
                if (n.contains("COCA COLA")) nivo4Id = 1001;
                else if (n.contains("KAVA")) nivo4Id = 1002;
                else if (n.contains("LAŠKO") || n.contains("LASKO")) nivo4Id = 1003;
                else if (n.contains("RAMSTEK")) nivo4Id = 1004;
                else if (n.contains("VRAČILO") || n.contains("VRACILO")) nivo4Id = 1005;
                else if (n.contains("VB7")) nivo4Id = 2933;
                else if (n.contains("WHISKY")) nivo4Id = 3744;
                else if (poz.getCena() != null && poz.getCena().compareTo(new BigDecimal("23.00")) == 0) nivo4Id = 315100008;
                else nivo4Id = 315100008; // Delphi fallback test item
            }
            poz.setNivo4Id(nivo4Id);
        }
        if (nivo4Id <= 0) nivo4Id = 315100008;
        soap.addProperty("NIVO4_ID", nivo4Id);

        int mobileIdDef = Globals.getInstance().getMobileId() > 0 ? Globals.getInstance().getMobileId() : 1;
        int tipkePosId = Globals.getInstance().getTipkePosId();
        int fPosIdVal = Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500;
        int posId = (poz.getPosId() != null && poz.getPosId() > 0 && (tipkePosId <= 0 || poz.getPosId() != tipkePosId || poz.getPosId() == fPosIdVal))
                ? poz.getPosId()
                : (mobileIdDef > 0 ? mobileIdDef : 1);
        soap.addProperty("POS_ID", posId);

        int natakarId = (poz.getNatakarId() != null && poz.getNatakarId() > 0) ? poz.getNatakarId() : 9999;
        soap.addProperty("NATAKAR_ID", natakarId);

        int tocilnicaId = (poz.getTocilnicaId() != null && poz.getTocilnicaId() > 0) ? poz.getTocilnicaId() : 512200;
        soap.addProperty("TOCILNICA_ID", tocilnicaId);

        int kuhinjaId = (poz.getKuhinjaId() != null && poz.getKuhinjaId() > 0) ? poz.getKuhinjaId() : 512600;
        soap.addProperty("KUHINJA_ID", kuhinjaId);

        int izvorStrmId = (poz.getIzvorStrmId() != null && poz.getIzvorStrmId() > 0) ? poz.getIzvorStrmId() : 512200;
        soap.addProperty("IZVOR_STRM_ID", izvorStrmId);

        int izvorPrihodekId = (poz.getIzvorPrihodekId() != null && poz.getIzvorPrihodekId() > 0) ? poz.getIzvorPrihodekId() : 512200;
        soap.addProperty("IZVOR_PRIHODEK_ID", izvorPrihodekId);

        int tarifaId = (poz.getTarifaId() != null && poz.getTarifaId() > 0) ? poz.getTarifaId() : 40;
        soap.addProperty("TARIFA_ID", tarifaId);

        // _NeNarocaj: v Delphi referenci je true na root pozicijah
        soap.addProperty("_NeNarocaj", poz.isNeNarocaj());
        soap.addProperty("RowDeleted", poz.isRowDeleted());

        if (poz.getBonId() != null && !poz.getBonId().isEmpty()) {
            soap.addProperty("BON_ID", poz.getBonId());
        }
        if (poz.getHod() != null && !poz.getHod().isEmpty()) {
            soap.addProperty("HOD", poz.getHod());
        }

        soap.addProperty("ENOTA_PRODAJE_ID", poz.getEnotaProdajeId() != null ? poz.getEnotaProdajeId().toPlainString() : "1");
        soap.addProperty("STATUS", poz.getStatus() != null ? poz.getStatus().toPlainString() : "0");
        soap.addProperty("KOLICINA", String.format(java.util.Locale.US, "%.4f", poz.getKolicina()));
        soap.addProperty("CENA", poz.getCena() != null ? poz.getCena().toPlainString() : "0.00");
        soap.addProperty("CENA_NABAVNA", poz.getCenaNabavna() != null ? poz.getCenaNabavna().toPlainString() : "0");

        double stopnja = poz.getStopnjaDavka();
        if (stopnja <= 0) {
            stopnja = 26.5;
        }
        soap.addProperty("STOPNJA_DAVKA", String.format(java.util.Locale.US, "%.2f", stopnja));

        soap.addProperty("ZNESEK", poz.getZnesek() != null ? poz.getZnesek().toPlainString() : "0.00");
        soap.addProperty("ZNESEK_DAVKA", poz.getZnesekDavka() != null ? poz.getZnesekDavka().toPlainString() : "0.00");

        if (poz.getZnesekPopust() != null && poz.getZnesekPopust().compareTo(BigDecimal.ZERO) != 0) {
            soap.addProperty("ZNESEK_POPUST", poz.getZnesekPopust().toPlainString());
        }

        soap.addProperty("LOJALNOST_POPUST", poz.getLojalnostPopust() != null ? poz.getLojalnostPopust().toPlainString() : "0");
        soap.addProperty("ZNESEK_LOJALNOST", poz.getZnesekLojalnost() != null ? poz.getZnesekLojalnost().toPlainString() : "0");

        soap.addProperty("PAKET_KOL", poz.getPaketKol() != null ? poz.getPaketKol().toPlainString() : "1");
        soap.addProperty("PAKET_NIVO4_ID", poz.getPaketNivo4Id() != null ? poz.getPaketNivo4Id() : 0);

        if (poz.getPaketDistinct() != null && poz.getPaketDistinct() != 0) {
            soap.addProperty("PAKET_DISTINCT", poz.getPaketDistinct());
        }
        soap.addProperty("STATUS_POZ", poz.getStatusPoz() != null ? poz.getStatusPoz() : 0);

        if (poz.getNarociloPoslano() != null && poz.getNarociloPoslano() != 0) {
            soap.addProperty("NAROCILO_POSLANO", poz.getNarociloPoslano());
        }
        if (poz.getDodatniOpis() != null && !poz.getDodatniOpis().isEmpty()) {
            soap.addProperty("DODATNI_OPIS", poz.getDodatniOpis().length() > 240 ? poz.getDodatniOpis().substring(0, 240) : poz.getDodatniOpis());
        }

        if (depth < 3 && poz.getOriginalObject() != null) {
            soap.addProperty("__OriginalObject", buildSoapPozicijaTp(poz.getOriginalObject(), parentRacunId, defaultPozId, depth + 1));
        }
        return soap;
    }

    private static SoapObject buildSoapPlaciloTp(PlaciloTp pl, int parentRacunId, int defaultPlId, int depth) {
        SoapObject soap = new SoapObject(NAMESPACE, "PlaciloTp");

        // 1. DATUM: yyyy-MM-ddT00:00:00Z
        java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd'T'00:00:00'Z'", java.util.Locale.US);
        sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        soap.addProperty("DATUM", sdfDate.format(new java.util.Date()));

        // 2. DELNI_ZNESEK
        soap.addProperty("DELNI_ZNESEK", pl.getDelniZnesek() != null ? pl.getDelniZnesek().toPlainString() : (pl.getZnesek() != null ? pl.getZnesek().toPlainString() : "0.00"));

        // 3. GOST_PRIJAVA_ID
        soap.addProperty("GOST_PRIJAVA_ID", 0);

        // 4. KUPEC_ID
        soap.addProperty("KUPEC_ID", 0);

        // 5. PARTNER_ID
        if (pl.getPartnerId() != null && pl.getPartnerId() != 0) {
            soap.addProperty("PARTNER_ID", pl.getPartnerId());
        }

        // 6. PLACILO_ID: šifra načina plačila (1=Gotovina, 2=Kreditna POS, itd.)
        int placiloId = pl.getPlaciloId();
        if (placiloId <= 0 && pl.getVrstaReklame() != null && pl.getVrstaReklame() > 0) {
            placiloId = pl.getVrstaReklame();
        }
        if (placiloId <= 0) {
            placiloId = 1;
        }
        soap.addProperty("PLACILO_ID", placiloId);

        // 7. POZICIJA_ID: zaporedna številka pozicije plačila (-1, -2, ...)
        int pozId = pl.getPozicijaId() != 0 ? pl.getPozicijaId() : defaultPlId;
        soap.addProperty("POZICIJA_ID", pozId);

        // 8. RACUN_ID
        int rId = pl.getRacunId() != 0 ? pl.getRacunId() : parentRacunId;
        if (rId == 0) rId = -1;
        soap.addProperty("RACUN_ID", rId);

        // 9. RowDeleted
        soap.addProperty("RowDeleted", pl.isRowDeleted());

        // 10. STATUS
        soap.addProperty("STATUS", pl.getStatus() != null ? pl.getStatus().toPlainString() : "0");

        // 11. ST_KARTICE
        if (pl.getStKartice() != null && !pl.getStKartice().isEmpty()) {
            soap.addProperty("ST_KARTICE", pl.getStKartice());
        }

        // 12. ST_NAROCILNICE
        soap.addProperty("ST_NAROCILNICE", 0);

        // 13. TOCILNICA_ID
        int tocilnicaId = (pl.getTocilnicaId() != null && pl.getTocilnicaId() > 0)
                ? pl.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        soap.addProperty("TOCILNICA_ID", tocilnicaId);

        // 14. URA: yyyy-MM-ddTHH:mm:ssZ (npr. "2026-09-21T19:51:23Z")
        java.text.SimpleDateFormat sdfUra = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US);
        sdfUra.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        soap.addProperty("URA", sdfUra.format(new java.util.Date()));

        // 15. URA_PLACILA: 1899-12-30THH:mm:ssZ (Delphi: "1899-12-30T19:51:23Z")
        java.text.SimpleDateFormat sdfUraPlacila = new java.text.SimpleDateFormat("'1899-12-30T'HH:mm:ss'Z'", java.util.Locale.US);
        sdfUraPlacila.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        soap.addProperty("URA_PLACILA", sdfUraPlacila.format(new java.util.Date()));

        // 16. VALUTA_ID
        if (pl.getValutaId() != null && pl.getValutaId() != 0) {
            soap.addProperty("VALUTA_ID", pl.getValutaId());
        }

        // 17. VERZIJA_ZAPISA
        soap.addProperty("VERZIJA_ZAPISA", pl.getVerzijaZapisa());

        // 18. VRSTA_REKLAME
        if (pl.getVrstaReklame() != null && pl.getVrstaReklame() != 0) {
            soap.addProperty("VRSTA_REKLAME", pl.getVrstaReklame());
        }

        // 19. ZNESEK (opcijsko)
        if (pl.getZnesek() != null && pl.getZnesek().compareTo(BigDecimal.ZERO) != 0) {
            soap.addProperty("ZNESEK", pl.getZnesek().toPlainString());
        }

        // 20. __OriginalObject
        if (depth < 3 && pl.getOriginalObject() != null) {
            soap.addProperty("__OriginalObject", buildSoapPlaciloTp(pl.getOriginalObject(), parentRacunId, defaultPlId, depth + 1));
        }
        return soap;
    }

    public static RacunTp parseRacunTp(SoapObject soap) {
        if (soap == null) return null;
        RacunTp racun = new RacunTp();

        String racunIdStr = getPropertyStringSafe(soap, "RACUN_ID");
        if (racunIdStr != null) {
            try { racun.setRacunId(Integer.parseInt(racunIdStr)); } catch (Exception ignored) {}
        }

        String verzijaStr = getPropertyStringSafe(soap, "VERZIJA_ZAPISA");
        if (verzijaStr != null) {
            try { racun.setVerzijaZapisa(Integer.parseInt(verzijaStr)); } catch (Exception ignored) {}
        }

        String statusStr = getPropertyStringSafe(soap, "STATUS");
        if (statusStr != null) {
            try { racun.setStatus(Integer.parseInt(statusStr)); } catch (Exception ignored) {}
        }

        String markerStr = getPropertyStringSafe(soap, "MARKER");
        if (markerStr != null) racun.setMarker(markerStr);

        String znesekStr = getPropertyStringSafe(soap, "ZNESEK");
        if (znesekStr != null) {
            try {
                znesekStr = znesekStr.replace(",", ".").trim();
                racun.setZnesek(new BigDecimal(znesekStr));
            } catch (Exception ignored) {}
        }

        String placanoStr = getPropertyStringSafe(soap, "PLACANO");
        if (placanoStr != null) {
            try {
                placanoStr = placanoStr.replace(",", ".").trim();
                racun.setPlacano(new BigDecimal(placanoStr));
            } catch (Exception ignored) {}
        }

        String kasiralStr = getPropertyStringSafe(soap, "KASIRAL");
        if (kasiralStr != null) {
            try { racun.setKasiral(Integer.parseInt(kasiralStr)); } catch (Exception ignored) {}
        }

        String fPosStr = getPropertyStringSafe(soap, "F_POS_ID");
        if (fPosStr != null) {
            try { racun.setfPosId(Integer.parseInt(fPosStr)); } catch (Exception ignored) {}
        }

        String fPosProstorStr = getPropertyStringSafe(soap, "F_POSLOVNI_PROSTOR_ID");
        if (fPosProstorStr != null) {
            try { racun.setfPoslovniProstorId(Integer.parseInt(fPosProstorStr)); } catch (Exception ignored) {}
        }

        String tocilnicaStr = getPropertyStringSafe(soap, "TOCILNICA_ID");
        if (tocilnicaStr != null) {
            try { racun.setTocilnicaId(Integer.parseInt(tocilnicaStr)); } catch (Exception ignored) {}
        }

        String fStevilkaStr = getPropertyStringSafe(soap, "F_STEVILKA_RACUNA");
        if (fStevilkaStr != null) {
            try { racun.setfStevilkaRacuna(Integer.parseInt(fStevilkaStr)); } catch (Exception ignored) {}
        }

        racun.setfOznakaDu(getPropertyStringSafe(soap, "F_OZNAKA_DU"));
        racun.setfPodpis(getPropertyStringSafe(soap, "F_PODPIS"));
        racun.setDatum(getPropertyStringSafe(soap, "DATUM"));
        racun.setUra(getPropertyStringSafe(soap, "URA"));
        racun.setUraPlacila(getPropertyStringSafe(soap, "URA_PLACILA"));
        racun.setOpomba(getPropertyStringSafe(soap, "OPOMBA"));

        if (soap.hasProperty("__OriginalObject")) {
            Object origObj = soap.getProperty("__OriginalObject");
            if (origObj instanceof SoapObject) {
                racun.setOriginalObject(parseRacunTp((SoapObject) origObj));
            }
        }

        // Parsiranje RACPOZIC
        if (soap.hasProperty("RACPOZIC")) {
            Object rpObj = soap.getProperty("RACPOZIC");
            if (rpObj instanceof SoapObject) {
                SoapObject rpSoap = (SoapObject) rpObj;
                int count = rpSoap.getPropertyCount();
                for (int i = 0; i < count; i++) {
                    Object itemObj = rpSoap.getProperty(i);
                    if (itemObj instanceof SoapObject) {
                        PozicijaTp poz = parsePozicijaTp((SoapObject) itemObj);
                        if (poz != null) {
                            racun.getRacPozic().add(poz);
                        }
                    }
                }
            }
        }

        // Parsiranje RACPLACI
        if (soap.hasProperty("RACPLACI")) {
            Object rplObj = soap.getProperty("RACPLACI");
            if (rplObj instanceof SoapObject) {
                SoapObject rplSoap = (SoapObject) rplObj;
                int count = rplSoap.getPropertyCount();
                for (int i = 0; i < count; i++) {
                    Object itemObj = rplSoap.getProperty(i);
                    if (itemObj instanceof SoapObject) {
                        PlaciloTp pl = parsePlaciloTp((SoapObject) itemObj);
                        if (pl != null) {
                            racun.getRacPlaci().add(pl);
                        }
                    }
                }
            }
        }

        return racun;
    }

    private static PozicijaTp parsePozicijaTp(SoapObject soap) {
        if (soap == null) return null;
        PozicijaTp poz = new PozicijaTp();

        String pozIdStr = getPropertyStringSafe(soap, "POZICIJA_ID");
        if (pozIdStr != null) {
            try { poz.setPozicijaId(Integer.parseInt(pozIdStr)); } catch (Exception ignored) {}
        }

        String racunIdStr = getPropertyStringSafe(soap, "RACUN_ID");
        if (racunIdStr != null) {
            try { poz.setRacunId(Integer.parseInt(racunIdStr)); } catch (Exception ignored) {}
        }

        String verzijaStr = getPropertyStringSafe(soap, "VERZIJA_ZAPISA");
        if (verzijaStr != null) {
            try { poz.setVerzijaZapisa(Integer.parseInt(verzijaStr)); } catch (Exception ignored) {}
        }

        String nivo4Str = getPropertyStringSafe(soap, "NIVO4_ID");
        if (nivo4Str != null) {
            try { poz.setNivo4Id(Integer.parseInt(nivo4Str)); } catch (Exception ignored) {}
        }

        String cenikIdStr = getPropertyStringSafe(soap, "CENIK_ID");
        if (cenikIdStr != null) {
            try { poz.setCenikId(Integer.parseInt(cenikIdStr)); } catch (Exception ignored) {}
        }

        String nazivStr = getPropertyStringSafe(soap, "NAZIV");
        if (nazivStr != null && !nazivStr.trim().isEmpty()) {
            poz.setNaziv(nazivStr.trim());
        } else if (poz.getNivo4Id() != null && poz.getNivo4Id() > 0) {
            String lookup = Globals.getInstance().findNazivByNivo4Id(poz.getNivo4Id());
            if (lookup != null && !lookup.trim().isEmpty()) {
                poz.setNaziv(lookup.trim());
            }
        }

        String kolStr = getPropertyStringSafe(soap, "KOLICINA");
        if (kolStr != null) {
            try {
                kolStr = kolStr.replace(",", ".").trim();
                poz.setKolicina(Double.parseDouble(kolStr));
            } catch (Exception ignored) {}
        }

        String cenaStr = getPropertyStringSafe(soap, "CENA");
        if (cenaStr != null) {
            try {
                cenaStr = cenaStr.replace(",", ".").trim();
                poz.setCena(new BigDecimal(cenaStr));
            } catch (Exception ignored) {}
        }

        String znesekStr = getPropertyStringSafe(soap, "ZNESEK");
        if (znesekStr != null) {
            try {
                znesekStr = znesekStr.replace(",", ".").trim();
                poz.setZnesek(new BigDecimal(znesekStr));
            } catch (Exception ignored) {}
        }
        if ((poz.getZnesek() == null || poz.getZnesek().compareTo(BigDecimal.ZERO) == 0) && poz.getCena() != null) {
            poz.setZnesek(poz.getCena().multiply(BigDecimal.valueOf(poz.getKolicina())));
        }

        String davekStr = getPropertyStringSafe(soap, "STOPNJA_DAVKA");
        if (davekStr != null) {
            try {
                davekStr = davekStr.replace(",", ".").trim();
                poz.setStopnjaDavka(Double.parseDouble(davekStr));
            } catch (Exception ignored) {}
        }

        String tarifaStr = getPropertyStringSafe(soap, "TARIFA_ID");
        if (tarifaStr != null) {
            try { poz.setTarifaId(Integer.parseInt(tarifaStr)); } catch (Exception ignored) {}
        }

        String epStr = getPropertyStringSafe(soap, "ENOTA_PRODAJE_ID");
        if (epStr != null) {
            try {
                epStr = epStr.replace(",", ".").trim();
                poz.setEnotaProdajeId(new BigDecimal(epStr));
            } catch (Exception ignored) {}
        }

        String statusStr = getPropertyStringSafe(soap, "STATUS");
        if (statusStr != null) {
            try {
                statusStr = statusStr.replace(",", ".").trim();
                poz.setStatus(new BigDecimal(statusStr));
            } catch (Exception ignored) {}
        }

        String zdStr = getPropertyStringSafe(soap, "ZNESEK_DAVKA");
        if (zdStr != null) {
            try {
                zdStr = zdStr.replace(",", ".").trim();
                poz.setZnesekDavka(new BigDecimal(zdStr));
            } catch (Exception ignored) {}
        }

        String popStr = getPropertyStringSafe(soap, "ZNESEK_POPUST");
        if (popStr != null) {
            try {
                popStr = popStr.replace(",", ".").trim();
                poz.setZnesekPopust(new BigDecimal(popStr));
            } catch (Exception ignored) {}
        }

        String natakarStr = getPropertyStringSafe(soap, "NATAKAR_ID");
        if (natakarStr != null) {
            try { poz.setNatakarId(Integer.parseInt(natakarStr)); } catch (Exception ignored) {}
        }

        String tocStr = getPropertyStringSafe(soap, "TOCILNICA_ID");
        if (tocStr != null) {
            try { poz.setTocilnicaId(Integer.parseInt(tocStr)); } catch (Exception ignored) {}
        }

        String kuhStr = getPropertyStringSafe(soap, "KUHINJA_ID");
        if (kuhStr != null) {
            try { poz.setKuhinjaId(Integer.parseInt(kuhStr)); } catch (Exception ignored) {}
        }

        String strmStr = getPropertyStringSafe(soap, "IZVOR_STRM_ID");
        if (strmStr != null) {
            try { poz.setIzvorStrmId(Integer.parseInt(strmStr)); } catch (Exception ignored) {}
        }

        String prihStr = getPropertyStringSafe(soap, "IZVOR_PRIHODEK_ID");
        if (prihStr != null) {
            try { poz.setIzvorPrihodekId(Integer.parseInt(prihStr)); } catch (Exception ignored) {}
        }

        String posStr = getPropertyStringSafe(soap, "POS_ID");
        if (posStr != null) {
            try { poz.setPosId(Integer.parseInt(posStr)); } catch (Exception ignored) {}
        }

        String bonStr = getPropertyStringSafe(soap, "BON_ID");
        if (bonStr != null) poz.setBonId(bonStr);

        String hodStr = getPropertyStringSafe(soap, "HOD");
        if (hodStr != null) poz.setHod(hodStr);

        String opisStr = getPropertyStringSafe(soap, "DODATNI_OPIS");
        if (opisStr != null) poz.setDodatniOpis(opisStr);

        String cenaNabavnaStr = getPropertyStringSafe(soap, "CENA_NABAVNA");
        if (cenaNabavnaStr != null) {
            try {
                cenaNabavnaStr = cenaNabavnaStr.replace(",", ".").trim();
                poz.setCenaNabavna(new BigDecimal(cenaNabavnaStr));
            } catch (Exception ignored) {}
        }

        String lojPopStr = getPropertyStringSafe(soap, "LOJALNOST_POPUST");
        if (lojPopStr != null) {
            try {
                lojPopStr = lojPopStr.replace(",", ".").trim();
                poz.setLojalnostPopust(new BigDecimal(lojPopStr));
            } catch (Exception ignored) {}
        }

        String lojZnStr = getPropertyStringSafe(soap, "ZNESEK_LOJALNOST");
        if (lojZnStr != null) {
            try {
                lojZnStr = lojZnStr.replace(",", ".").trim();
                poz.setZnesekLojalnost(new BigDecimal(lojZnStr));
            } catch (Exception ignored) {}
        }

        String paketKolStr = getPropertyStringSafe(soap, "PAKET_KOL");
        if (paketKolStr != null) {
            try {
                paketKolStr = paketKolStr.replace(",", ".").trim();
                poz.setPaketKol(new BigDecimal(paketKolStr));
            } catch (Exception ignored) {}
        }

        String paketNivo4Str = getPropertyStringSafe(soap, "PAKET_NIVO4_ID");
        if (paketNivo4Str != null) {
            try { poz.setPaketNivo4Id(Integer.parseInt(paketNivo4Str)); } catch (Exception ignored) {}
        }

        String paketDistStr = getPropertyStringSafe(soap, "PAKET_DISTINCT");
        if (paketDistStr != null) {
            try { poz.setPaketDistinct(Integer.parseInt(paketDistStr)); } catch (Exception ignored) {}
        }

        String statusPozStr = getPropertyStringSafe(soap, "STATUS_POZ");
        if (statusPozStr != null) {
            try { poz.setStatusPoz(Integer.parseInt(statusPozStr)); } catch (Exception ignored) {}
        }

        String narPoslanoStr = getPropertyStringSafe(soap, "NAROCILO_POSLANO");
        if (narPoslanoStr != null) {
            try { poz.setNarociloPoslano(Integer.parseInt(narPoslanoStr)); } catch (Exception ignored) {}
        }

        String delStr = getPropertyStringSafe(soap, "RowDeleted");
        if (delStr != null) poz.setRowDeleted("true".equalsIgnoreCase(delStr));

        String neNarocajStr = getPropertyStringSafe(soap, "_NeNarocaj");
        if (neNarocajStr != null) {
            poz.setNeNarocaj("true".equalsIgnoreCase(neNarocajStr));
        }

        if (soap.hasProperty("__OriginalObject")) {
            Object origObj = soap.getProperty("__OriginalObject");
            if (origObj instanceof SoapObject) {
                poz.setOriginalObject(parsePozicijaTp((SoapObject) origObj));
            }
        }

        return poz;
    }

    private static PlaciloTp parsePlaciloTp(SoapObject soap) {
        if (soap == null) return null;
        PlaciloTp pl = new PlaciloTp();

        String plIdStr = getPropertyStringSafe(soap, "PLACILO_ID");
        if (plIdStr != null) {
            try { pl.setPlaciloId(Integer.parseInt(plIdStr)); } catch (Exception ignored) {}
        }

        String racunIdStr = getPropertyStringSafe(soap, "RACUN_ID");
        if (racunIdStr != null) {
            try { pl.setRacunId(Integer.parseInt(racunIdStr)); } catch (Exception ignored) {}
        }

        String pozIdStr = getPropertyStringSafe(soap, "POZICIJA_ID");
        if (pozIdStr != null) {
            try { pl.setPozicijaId(Integer.parseInt(pozIdStr)); } catch (Exception ignored) {}
        }

        String verzijaStr = getPropertyStringSafe(soap, "VERZIJA_ZAPISA");
        if (verzijaStr != null) {
            try { pl.setVerzijaZapisa(Integer.parseInt(verzijaStr)); } catch (Exception ignored) {}
        }

        String znesekStr = getPropertyStringSafe(soap, "ZNESEK");
        if (znesekStr != null) {
            try {
                znesekStr = znesekStr.replace(",", ".").trim();
                pl.setZnesek(new BigDecimal(znesekStr));
            } catch (Exception ignored) {}
        }

        String delniZnesekStr = getPropertyStringSafe(soap, "DELNI_ZNESEK");
        if (delniZnesekStr != null) {
            try {
                delniZnesekStr = delniZnesekStr.replace(",", ".").trim();
                pl.setDelniZnesek(new BigDecimal(delniZnesekStr));
            } catch (Exception ignored) {}
        }

        String statusStr = getPropertyStringSafe(soap, "STATUS");
        if (statusStr != null) {
            try {
                statusStr = statusStr.replace(",", ".").trim();
                pl.setStatus(new BigDecimal(statusStr));
            } catch (Exception ignored) {}
        }

        if (pl.getPlaciloId() != 99) {
            if ((pl.getZnesek() == null || pl.getZnesek().compareTo(BigDecimal.ZERO) == 0) && pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0) {
                pl.setZnesek(pl.getDelniZnesek());
            }
            if ((pl.getDelniZnesek() == null || pl.getDelniZnesek().compareTo(BigDecimal.ZERO) == 0) && pl.getZnesek() != null && pl.getZnesek().compareTo(BigDecimal.ZERO) > 0) {
                pl.setDelniZnesek(pl.getZnesek());
            }
        } else {
            // Popust 99: delni_znesek je vedno 0 po pravilih iz popusti.md
            pl.setDelniZnesek(BigDecimal.ZERO);
        }

        String tocStr = getPropertyStringSafe(soap, "TOCILNICA_ID");
        if (tocStr != null) {
            try { pl.setTocilnicaId(Integer.parseInt(tocStr)); } catch (Exception ignored) {}
        }

        String valutaStr = getPropertyStringSafe(soap, "VALUTA_ID");
        if (valutaStr != null) {
            try { pl.setValutaId(Integer.parseInt(valutaStr)); } catch (Exception ignored) {}
        }

        String partStr = getPropertyStringSafe(soap, "PARTNER_ID");
        if (partStr != null) {
            try { pl.setPartnerId(Integer.parseInt(partStr)); } catch (Exception ignored) {}
        }

        String vrstaStr = getPropertyStringSafe(soap, "VRSTA_REKLAME");
        if (vrstaStr != null) {
            try { pl.setVrstaReklame(Integer.parseInt(vrstaStr)); } catch (Exception ignored) {}
        }

        String delStr = getPropertyStringSafe(soap, "RowDeleted");
        if (delStr != null) pl.setRowDeleted("true".equalsIgnoreCase(delStr));

        String karticaStr = getPropertyStringSafe(soap, "ST_KARTICE");
        if (karticaStr != null) pl.setStKartice(karticaStr);

        if (soap.hasProperty("__OriginalObject")) {
            Object origObj = soap.getProperty("__OriginalObject");
            if (origObj instanceof SoapObject) {
                pl.setOriginalObject(parsePlaciloTp((SoapObject) origObj));
            }
        }

        return pl;
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
