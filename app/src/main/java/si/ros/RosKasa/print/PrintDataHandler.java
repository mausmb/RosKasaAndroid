package si.ros.RosKasa.print;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.Globals;
import si.ros.RosKasa.models.AkcijaTp;
import si.ros.RosKasa.models.KartprijTp;
import si.ros.RosKasa.models.PartnerTp;
import si.ros.RosKasa.models.PlaciloTp;
import si.ros.RosKasa.models.RacunTp;
import si.ros.RosKasa.models.SlipEmaTp;
import si.ros.RosKasa.models.StornoRazlogTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

/**
 * Celovit asinhroni upravitelj tiskanja, skladen z Delphi uPrintData.pas.
 * Upravlja kaskado pridobivanja podatkov, pavze za odrez papirja ter tiskanje
 * računov, voucherjev, naročil in kuponov prek enotne Bluetooth seje.
 */
public class PrintDataHandler {

    private static final String TAG = "PrintDataHandler";
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * Glavna vstopna točka za celovito tiskanje računa in vseh dodatkov.
     */
    public static void printReceiptComplete(Context context, RacunTp racun, BluetoothPrintHelper.OnPrintListener listener) {
        if (listener != null) listener.onStart();

        executor.execute(() -> {
            BluetoothSocket socket = null;
            OutputStream outputStream = null;

            try {
                if (racun == null || racun.getRacunId() <= 0) {
                    postError(listener, "Neveljaven račun za tiskanje!");
                    return;
                }

                if (!BluetoothPrintHelper.hasBluetoothPermissions(context)) {
                    postError(listener, "Aplikacija nima dovoljenja za Bluetooth! Zahtevajte BLUETOOTH_CONNECT.");
                    return;
                }

                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                    postError(listener, "Bluetooth vmesnik na napravi ni na voljo ali je izklopljen!");
                    return;
                }

                Globals globals = Globals.getInstance();
                AppPreferences prefs = new AppPreferences(context);
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                if (serverUrl == null || serverUrl.isEmpty()) serverUrl = globals.getServerUrl();
                if (token == null || token.isEmpty()) token = globals.getToken();

                // 1. KASKADA PRIDOBIVANJA PODATKOV (Pre-Print Retrieval)
                globals.vpisiKronologijo("PrintDataHandler: Začetek priprave podatkov za R:" + racun.getRacunId());
                PrintDataWS printData = retrievePrintData(serverUrl, token, racun, globals);

                // 2. PRIPRAVA BLUETOOTH TISKALNIKA
                String targetPrinterName = globals.getPrinterRacuni();
                if (targetPrinterName == null || targetPrinterName.trim().isEmpty()) {
                    targetPrinterName = prefs.getPrinterRacuni();
                    if (targetPrinterName != null && !targetPrinterName.trim().isEmpty()) {
                        globals.setPrinterRacuni(targetPrinterName.trim());
                    }
                }
                if (targetPrinterName == null) targetPrinterName = "";
                targetPrinterName = targetPrinterName.trim();

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if (pairedDevices == null || pairedDevices.isEmpty()) {
                    postError(listener, "Ni najdenih seznanjenih Bluetooth naprav!");
                    return;
                }

                BluetoothDevice selectedDevice = null;
                if (!targetPrinterName.isEmpty()) {
                    for (BluetoothDevice device : pairedDevices) {
                        String devName = device.getName();
                        if (devName != null && (devName.equalsIgnoreCase(targetPrinterName) || devName.toLowerCase().contains(targetPrinterName.toLowerCase()))) {
                            selectedDevice = device;
                            break;
                        }
                    }
                }
                if (selectedDevice == null) {
                    selectedDevice = pairedDevices.iterator().next();
                }

                final String deviceName = selectedDevice.getName() != null ? selectedDevice.getName() : "POS Tiskalnik";
                bluetoothAdapter.cancelDiscovery();

                try {
                    socket = selectedDevice.createRfcommSocketToServiceRecord(SPP_UUID);
                    socket.connect();
                } catch (Exception e1) {
                    try {
                        java.lang.reflect.Method m = selectedDevice.getClass().getMethod("createRfcommSocket", int.class);
                        socket = (BluetoothSocket) m.invoke(selectedDevice, 1);
                        socket.connect();
                    } catch (Exception e2) {
                        throw e1;
                    }
                }
                outputStream = socket.getOutputStream();

                // 3. TISKANJE RAČUNA (ZANKA KOPIJ + PAVZA ZA ODREZ)
                int zaPrintKopij = printData.getZaPrintStKopij();
                int zacStKopij = printData.getZacStKopij();
                String textPreviewZaServer = "";

                for (int j = 0; j < zaPrintKopij; j++) {
                    int stKopijeOznaka = 0;
                    if (j > 0 || zacStKopij > 0) {
                        stKopijeOznaka = (zacStKopij > 0 ? zacStKopij + j : j + 1);
                    }

                    RacunPrintBuilder.ReceiptResult racResult = RacunPrintBuilder.buildReceipt(racun, globals, stKopijeOznaka, printData);
                    if (j == 0) {
                        textPreviewZaServer = racResult.getTextPreview();
                    }

                    sendBytesToPrinter(outputStream, racResult.getPrintBytes());

                    // Pavza za odrez med posameznimi kopijami (razen po zadnji)
                    if (j < zaPrintKopij - 1) {
                        waitForUserCut(context, "Odrežite papir in pritisnite OK za naslednjo kopijo!");
                    }
                }

                // 4. TISKANJE VOUCHERJA (če je omogočeno)
                boolean isStorno = (racun.getStornoRacunId() != null && racun.getStornoRacunId() > 0)
                        || (racun.getStornoOriginal() != null && racun.getStornoOriginal() > 0);

                if (globals.isPrintamVoucher() && !isStorno && (racun.getStatus() < 2)) {
                    RacunPrintBuilder.ReceiptResult voucherRes = VoucherPrintBuilder.buildVoucher(racun, globals);
                    if (voucherRes.getPrintBytes().length > 0) {
                        waitForUserCut(context, "Odrežite papir in pritisnite OK.");
                        sendBytesToPrinter(outputStream, voucherRes.getPrintBytes());
                    }
                }

                // 5. TISKANJE NAROČILA OB RAČUNU (če je omogočeno)
                if (globals.isPrintamNarocila() && !isStorno && (racun.getStatus() < 2)) {
                    List<RacunPrintBuilder.ReceiptResult> blokiNar = NarociloBlokPrintBuilder.buildNarociloBloki(racun, globals);
                    for (RacunPrintBuilder.ReceiptResult blok : blokiNar) {
                        waitForUserCut(context, "Odrežite papir in pritisnite OK.");
                        sendBytesToPrinter(outputStream, blok.getPrintBytes());
                    }
                }

                // 6. TISKANJE AKCIJ IN KUPONOV (če obstajajo)
                if (globals.isAkcije() && printData.isAkcijePrint() && !printData.getAkcijeList().isEmpty()) {
                    List<RacunPrintBuilder.ReceiptResult> kuponiRes = AkcijePrintBuilder.buildAkcijeKuponi(racun, printData.getAkcijeList(), globals);
                    for (RacunPrintBuilder.ReceiptResult kRes : kuponiRes) {
                        waitForUserCut(context, "Odrežite papir in pritisnite OK za nadaljevanje.");
                        sendBytesToPrinter(outputStream, kRes.getPrintBytes());
                    }
                }

                // Varno zaprtje tiskalniške seje
                try {
                    outputStream.flush();
                } catch (Exception ignored) {}

                // 7. POST-PRINT POSTOPKI NA STREŽNIKU
                BluetoothPrintHelper.sendRacIzpisanNaServer(context, racun, textPreviewZaServer, zaPrintKopij);

                postSuccess(listener, "Izpis uspešno poslan na " + deviceName + "!");

            } catch (SecurityException se) {
                Log.e(TAG, "Bluetooth SecurityException", se);
                postError(listener, "Dovoljenje za Bluetooth ni odobreno!");
            } catch (Exception e) {
                Log.e(TAG, "Napaka pri celovitem tiskanju", e);
                postError(listener, "Napaka tiskanja: " + (e.getMessage() != null ? e.getMessage() : e.toString()));
            } finally {
                Globals.getInstance().clearZadnjiSlip();
                try {
                    if (outputStream != null) outputStream.close();
                    if (socket != null) socket.close();
                } catch (Exception ignored) {}
            }
        });
    }

    /**
     * Kaskadno asinhrono pridobivanje vseh manjkajočih podatkov pred tiskom (skladno z Delphi uPrintData.pas).
     */
    public static PrintDataWS retrievePrintData(String serverUrl, String token, RacunTp racun, Globals globals) {
        PrintDataWS data = new PrintDataWS();
        data.initData();
        data.setRacun(racun);
        data.setRacunId(racun.getRacunId());
        data.setZnesek(racun.getZnesek());
        data.setKasiral(racun.getKasiral() != null ? racun.getKasiral() : 0);
        data.setFPodpis(racun.getfPodpis() != null ? racun.getfPodpis().trim() : "");
        data.setFOznakaDu(racun.getfOznakaDu() != null ? racun.getfOznakaDu().trim() : "");

        int zacStk = racun.getStKopij() != null ? racun.getStKopij() : 0;
        int zaPrintStk = globals.stKopijPlacila(racun);
        data.setZacStKopij(zacStk);
        data.setZaPrintStKopij(zaPrintStk);
        data.setKonStKopij((zacStk == 0 && zaPrintStk > 1) ? zaPrintStk : zacStk + zaPrintStk);

        int stornoId = racun.getStornoRazlogId() != null ? racun.getStornoRazlogId() : 0;
        data.setStornoRazlogId(stornoId);

        // A. Partner
        int partnerId = racun.getPartnerId() != null ? racun.getPartnerId() : 0;
        if (racun.getRacPlaci() != null) {
            for (PlaciloTp pl : racun.getRacPlaci()) {
                if (pl != null && pl.getPartnerId() != null && pl.getPartnerId() > 0) {
                    partnerId = pl.getPartnerId();
                    if (pl.getNazivPartner() != null && !pl.getNazivPartner().trim().isEmpty()) {
                        data.setPartnerNaziv(pl.getNazivPartner().trim());
                        data.setPartnerNaslov(pl.getNaslovPartner() != null ? pl.getNaslovPartner().trim() : "");
                        data.setPartnerDavcna(pl.getDavcnaSt() != null ? pl.getDavcnaSt().trim() : "");
                        data.setStNarocilnice(pl.getStNarocilnice() != null ? pl.getStNarocilnice().trim() : "");
                    }
                    break;
                }
            }
        }
        data.setPartnerId(partnerId);
        if (partnerId > 0 && data.getPartnerNaziv().isEmpty() && serverUrl != null && !serverUrl.isEmpty()) {
            try {
                List<PartnerTp> pList = RosKasaSoapClient.getPartner(serverUrl, token, null, partnerId, null, null, false);
                if (pList != null && !pList.isEmpty()) {
                    PartnerTp p = pList.get(0);
                    data.setPartnerNaziv(p.getNaziv());
                    String naslov = p.getNasUlica() + (!p.getNazivPosta().isEmpty() ? ", " + p.getNazivPosta() : "");
                    data.setPartnerNaslov(naslov);
                    data.setPartnerDavcna(p.getDavcnaSt());
                }
            } catch (Exception e) {
                Log.w(TAG, "retrievePrintData napaka getPartner: " + e.getMessage());
            }
        }

        // B. Gost hotela (Prijava)
        int prijavaId = 0;
        if (racun.getRacPlaci() != null) {
            for (PlaciloTp pl : racun.getRacPlaci()) {
                if (pl != null && pl.getGostPrijavaId() != null && pl.getGostPrijavaId() > 0) {
                    prijavaId = pl.getGostPrijavaId();
                    break;
                }
            }
        }
        data.setPrijavaId(prijavaId);
        if (prijavaId > 0 && serverUrl != null && !serverUrl.isEmpty()) {
            try {
                KartprijTp kp = RosKasaSoapClient.getPrijava(serverUrl, token, prijavaId);
                if (kp != null && (kp.getProstorId() > 0 || !kp.getImeGosta().isEmpty())) {
                    String obrat = kp.getObratNaziv() != null ? kp.getObratNaziv() : "";
                    data.setPrijavaImeGosta("HK " + kp.getProstorId() + "/" + kp.getImeGosta() + (obrat.isEmpty() ? "" : "/" + obrat));
                }
            } catch (Exception e) {
                Log.w(TAG, "retrievePrintData napaka getPrijava: " + e.getMessage());
            }
        }

        // C. Storno razlog
        if (stornoId > 0) {
            List<StornoRazlogTp> razlogi = globals.getCachedStornoRazlogi();
            if (razlogi != null) {
                for (StornoRazlogTp sr : razlogi) {
                    if (sr != null && sr.getStornoRazlogId() == stornoId) {
                        data.setStornoRazlog(sr.getNaziv());
                        break;
                    }
                }
            }
        }

        // D. POS Slip (Ema2 / Worldline)
        boolean hasCardPayment = false;
        if (racun.getRacPlaci() != null) {
            for (PlaciloTp pl : racun.getRacPlaci()) {
                if (pl != null) {
                    si.ros.RosKasa.models.NacPlacTp np = globals.getPlaciloById(pl.getPlaciloId());
                    String plNaz = (np != null && np.getNaziv() != null) ? np.getNaziv() : "";
                    int metoda = (np != null) ? np.getMetoda() : globals.placilometoda(pl.getPlaciloId());
                    if (metoda == 3 || metoda == 14 || pl.getPlaciloId() == 399 || pl.getPlaciloId() == 2
                            || (pl.getMRef() != null && !pl.getMRef().trim().isEmpty())
                            || plNaz.toUpperCase().contains("KART") || plNaz.toUpperCase().contains("POS")) {
                        hasCardPayment = true;
                        break;
                    }
                }
            }
        }
        if (hasCardPayment && serverUrl != null && !serverUrl.isEmpty()) {
            try {
                List<SlipEmaTp> slips = RosKasaSoapClient.getSlipEma2(serverUrl, token, racun.getRacunId());
                if (slips != null && !slips.isEmpty()) {
                    StringBuilder slipSb = new StringBuilder();
                    for (SlipEmaTp s : slips) {
                        if (s == null) continue;
                        String sp = (s.getSlipPrint() != null && !s.getSlipPrint().trim().isEmpty())
                                ? s.getSlipPrint().trim()
                                : (s.getSlipPrints() != null ? s.getSlipPrints().trim() : "");
                        if (!sp.isEmpty()) {
                            slipSb.append("\n").append(sp);
                        }
                    }
                    if (slipSb.length() > 0) {
                        data.setSlipString(si.ros.RosKasa.payment.SixTapPaymentService.cleanSlipText(slipSb.toString()));
                    }
                }
            } catch (Exception e) {
                Log.w(TAG, "retrievePrintData napaka getSlipEma2: " + e.getMessage());
            }
        }

        // Fallback: če iz strežnika nismo pridobili slipa, preveri lokalni zadnji prejeti POS slip
        // POMEMBNO: Fallback se SME izvesti SAMO, če gre dejansko za kartično/POS plačilo (hasCardPayment == true)
        // IN če lokalni slip pripada temu računu ali je bil pravkar sprejet za ta račun!
        if (hasCardPayment && data.getSlipString().isEmpty() && globals.getZadnjiSlipText() != null && !globals.getZadnjiSlipText().trim().isEmpty()) {
            if (globals.getZadnjiSlipRacunId() == 0 || globals.getZadnjiSlipRacunId() == racun.getRacunId()) {
                data.setSlipString(si.ros.RosKasa.payment.SixTapPaymentService.cleanSlipText(globals.getZadnjiSlipText()));
            }
        }

        // E. Akcije in kuponi (skladno z Delphi uPrintData.pas)
        boolean isAkcije = globals.isAkcije();
        int racId = racun.getRacunId();
        int racStatus = racun.getStatus();
        int stornoRacId = racun.getStornoRazlogId() != null ? racun.getStornoRazlogId() : (racun.getStornoRacunId() != null ? racun.getStornoRacunId() : 0);
        int stornoOrig = racun.getStornoOriginal() != null ? racun.getStornoOriginal() : 0;
        boolean hasServer = serverUrl != null && !serverUrl.trim().isEmpty();

        String diagAkcije = "retrievePrintData preverjanje akcij: racunId=" + racId
                + ", isAkcije=" + isAkcije
                + ", status=" + racStatus
                + ", stornoRacunId=" + stornoRacId
                + ", stornoOriginal=" + stornoOrig
                + ", hasServerUrl=" + hasServer;
        Log.d(TAG, diagAkcije);
        globals.vpisiKronologijoDebugL0(diagAkcije);

        if (!isAkcije) {
            String razlog = "Akcije preskocene: AKCIJE niso omogocene v nastavitvah (isAkcije=false).";
            Log.d(TAG, razlog);
            globals.vpisiKronologijoDebugL0(razlog);
        } else if (!hasServer) {
            String razlog = "Akcije preskocene: ni povezave s streznikom (serverUrl je prazen).";
            Log.w(TAG, razlog);
            globals.vpisiKronologijoDebugL0(razlog);
        } else if (racId <= 0) {
            String razlog = "Akcije preskocene: racunId <= 0 (" + racId + ").";
            Log.w(TAG, razlog);
            globals.vpisiKronologijoDebugL0(razlog);
        } else if (stornoRacId > 0 || stornoOrig > 0) {
            String razlog = "Akcije preskocene: racun je storno (stornoRacunId=" + stornoRacId + ", stornoOriginal=" + stornoOrig + ").";
            Log.d(TAG, razlog);
            globals.vpisiKronologijoDebugL0(razlog);
        } else if (racStatus != 1 && racStatus != 2) {
            String razlog = "Akcije preskocene: status racuna (" + racStatus + ") ni 1 ali 2.";
            Log.d(TAG, razlog);
            globals.vpisiKronologijoDebugL0(razlog);
        } else {
            try {
                // 1. Naziv akcije, če je akcija vezana na sam račun
                if (racun.getAkcijaId() != null && racun.getAkcijaId() > 0) {
                    AkcijaTp an = RosKasaSoapClient.akcijaNaziv(serverUrl, token, racun.getAkcijaId());
                    if (an != null) {
                        data.setAkcijaNaziv(an.getNaziv());
                        data.setAkcijaTipNaziv(an.getTipNaziv());
                    }
                }

                // 2. Klic akcijaSetKuponiRacuna: preveri artikle na računu in KREIRA kupone na strežniku
                List<AkcijaTp> kuponi = RosKasaSoapClient.akcijaSetKuponiRacuna(serverUrl, token, racId);

                // 3. Fallback na akcijaGet, če akcijaSetKuponiRacuna ni vrnila seznama neposredno
                if (kuponi == null || kuponi.isEmpty()) {
                    kuponi = RosKasaSoapClient.akcijaGet(serverUrl, token, racId);
                }

                // 4. Če imamo kupone, jih dodamo v podatke za tisk
                if (kuponi != null && !kuponi.isEmpty()) {
                    data.setAkcijeList(kuponi);
                    data.setAkcijePrint(true);
                    String kMsg = "Akcije uspesno pripravljene za tisk: " + kuponi.size() + " kuponov.";
                    Log.d(TAG, kMsg);
                    globals.vpisiKronologijoDebugL0(kMsg);
                } else {
                    String kMsg = "Za racunId=" + racId + " ni bilo generiranih promocijskih kuponov.";
                    Log.d(TAG, kMsg);
                    globals.vpisiKronologijoDebugL0(kMsg);
                }
            } catch (Exception e) {
                String errMsg = "retrievePrintData napaka akcije: " + e.getMessage();
                Log.w(TAG, errMsg, e);
                globals.vpisiKronologijoDebugL0(errMsg);
            }
        }

        return data;
    }

    /**
     * Varna pavza za odrez papirja med kopijami in dodatki (skladno z Delphi uPrintData.pas in FormKasaMobile.pas).
     * Upošteva TISKANJEPAVZA (modalni dialog za odrez z gumbom OK) in PRINTBLOKPAVZA (časovna pavza v ms ali s).
     */
    public static void waitForUserCut(Context context, String sporocilo) {
        Globals g = Globals.getInstance();

        // 1. Določitev časovne pavze iz MobIni PRINTBLOKPAVZA
        int rawPavza = g.getPrintBlokPavza();
        long casPavzeMs;
        if (rawPavza > 0) {
            // Če je vrednost <= 30, pomeni sekunde (npr. 2, 3, 4 s -> 2000, 3000, 4000 ms), sicer milisekunde (npr. 1000, 2000, 4000 ms)
            casPavzeMs = (rawPavza <= 30) ? (rawPavza * 1000L) : rawPavza;
        } else {
            // Privzeta varna pavza: zmanjšana na 1 sekundo (1000 ms)
            casPavzeMs = 1000L;
        }

        Activity act = findActivity(context);

        // 2. Če je vklopljena interaktivna pavza z dialogom (TISKANJEPAVZA = 'D' oz. true) in imamo aktiven Activity
        if (g.isTiskanjePavza() && act != null && !act.isFinishing()) {
            g.vpisiKronologijo("Tiskanje dialog pavza za odrez: " + sporocilo);
            CountDownLatch latch = new CountDownLatch(1);
            mainHandler.post(() -> {
                try {
                    new AlertDialog.Builder(act)
                            .setTitle("Tiskanje")
                            .setMessage(sporocilo != null ? sporocilo : "Odrežite papir in pritisnite OK za nadaljevanje.")
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, which) -> {
                                dialog.dismiss();
                                latch.countDown();
                            })
                            .show();
                } catch (Exception e) {
                    latch.countDown();
                }
            });

            try {
                // Počakamo na uporabnikov pritisk na OK (največ 30 sekund)
                boolean ok = latch.await(30, TimeUnit.SECONDS);
                if (!ok) {
                    g.vpisiKronologijo("Tiskanje pavza: potekel 30s timeout za dialog");
                }
            } catch (InterruptedException ignored) {}
        } else {
            // 3. Avtomatska časovna pavza brez dialoga (PRINTBLOKPAVZA oz. 3000 ms)
            g.vpisiKronologijo("Tiskanje časovna pavza med kopijama: " + casPavzeMs + " ms");
            try {
                Thread.sleep(casPavzeMs);
            } catch (InterruptedException ignored) {}
        }
    }

    private static Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    private static void sendBytesToPrinter(OutputStream outputStream, byte[] bytes) throws Exception {
        if (outputStream == null || bytes == null || bytes.length == 0) return;
        outputStream.write(bytes);
        outputStream.write("\n\n\n".getBytes(StandardCharsets.UTF_8));
        outputStream.write(new byte[]{0x1D, 0x56, 0x42, 0x00}); // GS V B 0 (cut)
        outputStream.flush();
    }

    private static void postSuccess(BluetoothPrintHelper.OnPrintListener listener, String msg) {
        Globals.getInstance().vpisiKronologijo("PrintDataHandler USPEH: " + msg);
        mainHandler.post(() -> {
            if (listener != null) listener.onSuccess(msg);
        });
    }

    private static void postError(BluetoothPrintHelper.OnPrintListener listener, String errorMsg) {
        Globals.getInstance().vpisiKronologijo("PrintDataHandler NAPAKA: " + errorMsg);
        mainHandler.post(() -> {
            if (listener != null) listener.onError(errorMsg);
        });
    }
}
