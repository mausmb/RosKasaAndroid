package si.ros.RosKasa.print;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.Globals;

public class BluetoothPrintHelper {

    private static final String TAG = "BluetoothPrintHelper";
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface OnPrintListener {
        void onStart();
        void onSuccess(String message);
        void onError(String errorMessage);
    }

    public static boolean hasBluetoothPermissions(Context context) {
        if (context == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static void printReceipt(Context context, si.ros.RosKasa.models.RacunTp racun, int stKopij, OnPrintListener listener) {
        si.ros.RosKasa.print.RacunPrintBuilder.ReceiptResult result = si.ros.RosKasa.print.RacunPrintBuilder.buildReceipt(racun, Globals.getInstance(), stKopij);
        printReceiptBytes(context, result.getPrintBytes(), new OnPrintListener() {
            @Override
            public void onStart() {
                if (listener != null) listener.onStart();
            }

            @Override
            public void onSuccess(String message) {
                // Šele ko je tiskanje dejansko uspelo, pošljemo racIzpisan na strežnik!
                sendRacIzpisanNaServer(context, racun, result.getTextPreview(), stKopij);
                if (listener != null) listener.onSuccess(message);
            }

            @Override
            public void onError(String errorMessage) {
                if (listener != null) listener.onError(errorMessage);
            }
        });
    }

    public static void sendRacIzpisanNaServer(Context context, si.ros.RosKasa.models.RacunTp racun, String vsebina, int stKopij) {
        if (racun == null || racun.getRacunId() <= 0) return;
        executor.execute(() -> {
            try {
                si.ros.RosKasa.AppPreferences prefs = new si.ros.RosKasa.AppPreferences(context);
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                if (serverUrl == null || serverUrl.isEmpty() || token == null || token.isEmpty()) {
                    serverUrl = Globals.getInstance().getServerUrl();
                    token = Globals.getInstance().getToken();
                }
                if (serverUrl == null || serverUrl.isEmpty() || token == null || token.isEmpty()) return;

                Globals g = Globals.getInstance();
                int tocilnicaId = (racun.getTocilnicaId() != null && racun.getTocilnicaId() > 0)
                        ? racun.getTocilnicaId()
                        : (g.getTocilnicaId() != null && g.getTocilnicaId() > 0 ? g.getTocilnicaId() : 512200);

                int osebaId = g.getTekocaOsebaId() > 0
                        ? g.getTekocaOsebaId()
                        : (racun.getKasiral() != null && racun.getKasiral() > 0 ? racun.getKasiral() : 1);

                si.ros.RosKasa.models.IzpisanTp izpisan = new si.ros.RosKasa.models.IzpisanTp();
                izpisan.setRacunId(racun.getRacunId());
                izpisan.setStatus(2);
                izpisan.setZakljucen(1);
                izpisan.setTocilnicaId(tocilnicaId);
                izpisan.setOsebaId(osebaId);
                izpisan.setVsebina(vsebina != null ? vsebina : "");

                long nowMs = System.currentTimeMillis() + ((long) (stKopij + 1) * 1000L);
                java.text.SimpleDateFormat sdfCas = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US);
                izpisan.setCasIzpisa(sdfCas.format(new java.util.Date(nowMs)));

                java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd'T'00:00:00'Z'", java.util.Locale.US);
                String datumStr = racun.getDatum();
                if (datumStr != null && !datumStr.trim().isEmpty()) {
                    datumStr = datumStr.trim();
                    if (!datumStr.contains("T")) {
                        try {
                            if (datumStr.contains(".")) {
                                java.text.SimpleDateFormat sdfDot = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.US);
                                java.util.Date parsed = sdfDot.parse(datumStr);
                                datumStr = sdfDate.format(parsed);
                            } else if (datumStr.contains("-")) {
                                java.text.SimpleDateFormat sdfDash = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
                                java.util.Date parsed = sdfDash.parse(datumStr);
                                datumStr = sdfDate.format(parsed);
                            } else {
                                datumStr = sdfDate.format(new java.util.Date());
                            }
                        } catch (Exception ignored) {
                            datumStr = sdfDate.format(new java.util.Date());
                        }
                    }
                } else {
                    datumStr = sdfDate.format(new java.util.Date());
                }
                izpisan.setDatum(datumStr);

                String uraStr = racun.getUra();
                if (uraStr != null && !uraStr.trim().isEmpty()) {
                    uraStr = uraStr.trim();
                    if (!uraStr.contains("T")) {
                        if (uraStr.length() == 8) {
                            uraStr = "1899-12-30T" + uraStr + "Z";
                        } else if (uraStr.length() == 5) {
                            uraStr = "1899-12-30T" + uraStr + ":00Z";
                        } else {
                            java.text.SimpleDateFormat sdfUra = new java.text.SimpleDateFormat("'1899-12-30T'HH:mm:ss'Z'", java.util.Locale.US);
                            uraStr = sdfUra.format(new java.util.Date());
                        }
                    }
                } else {
                    java.text.SimpleDateFormat sdfUra = new java.text.SimpleDateFormat("'1899-12-30T'HH:mm:ss'Z'", java.util.Locale.US);
                    uraStr = sdfUra.format(new java.util.Date());
                }
                izpisan.setUra(uraStr);

                // Kronologija pred pošiljanjem (Delphi skladnost)
                g.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                        "RacIzpisanNaServer R:" + racun.getRacunId() + " stk: " + stKopij,
                        osebaId, tocilnicaId);

                if (vsebina != null && !vsebina.isEmpty()) {
                    g.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                            "RacIzpisanNaServer stringlist memo R:" + racun.getRacunId(),
                            osebaId, tocilnicaId);
                }

                g.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                        "RacIzpisanNaServer ws post R:" + racun.getRacunId(),
                        osebaId, tocilnicaId);

                si.ros.RosKasa.soap.RosKasaSoapClient.insertIzpisan(serverUrl, token, izpisan);
                Log.d(TAG, "sendRacIzpisanNaServer: uspešno poslan izpisan račun R:" + racun.getRacunId());

                g.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                        "RacIzpisanNaServer ws post USPEH R:" + racun.getRacunId(),
                        osebaId, tocilnicaId);

                // Vedno ko izpišemo račun, ki ima status=1, ga postavimo v status=2 (Delphi uPrintData.pas:856-871)
                if (racun.getStatus() == 1) {
                    try {
                        racun.setStatus(2);
                        int konStKopij = stKopij > 0 ? stKopij : (racun.getStKopij() != null && racun.getStKopij() > 0 ? racun.getStKopij() : 1);
                        racun.setStKopij(konStKopij);
                        int mobileId = 0;
                        try {
                            mobileId = Integer.parseInt(prefs.getMobileId());
                        } catch (Exception ignored) {}

                        si.ros.RosKasa.soap.RosKasaSoapClient.setRacun(serverUrl, token, mobileId, racun);
                        Log.d(TAG, "UpdateInvoiceStatus: račun R:" + racun.getRacunId() + " uspešno postavljen na status 2 (STKOPIJ=" + konStKopij + ")");

                        g.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                                "UpdateInvoiceStatus R:" + racun.getRacunId() + " na status 2 STKOPIJ:" + konStKopij,
                                g.getTekocaOsebaId(), g.getTocilnicaId());
                    } catch (Exception exStatus) {
                        Log.e(TAG, "Napaka pri UpdateInvoiceStatus na status 2 za R:" + racun.getRacunId() + ": " + exStatus.getMessage(), exStatus);
                        g.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                                "Napaka UpdateInvoiceStatus za R:" + racun.getRacunId() + " " + exStatus.getMessage(),
                                g.getTekocaOsebaId(), g.getTocilnicaId());
                    }
                }
            } catch (Exception e) {
                String errMsg = (e != null && e.getMessage() != null) ? e.getMessage() : (e != null ? e.toString() : "Neznana napaka");
                Log.w(TAG, "sendRacIzpisanNaServer napaka za R:" + racun.getRacunId() + ": " + errMsg);
                Globals.getInstance().vpisiKronologijoDebugL1("Napaka PostRacIzpisan za R:" + racun.getRacunId() + " " + errMsg);
            }
        });
    }

    public static void printReceiptBytes(Context context, byte[] bytesToPrint, OnPrintListener listener) {
        if (listener != null) listener.onStart();

        executor.execute(() -> {
            BluetoothSocket socket = null;
            OutputStream outputStream = null;

            try {
                if (!hasBluetoothPermissions(context)) {
                    postError(listener, "Aplikacija nima dovoljenja za Bluetooth! Zahtevajte dovoljenje za BLUETOOTH_CONNECT.");
                    return;
                }

                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null) {
                    postError(listener, "Bluetooth vmesnik na napravi ni na voljo!");
                    return;
                }

                if (!bluetoothAdapter.isEnabled()) {
                    postError(listener, "Bluetooth je izklopljen! Prosim vklopite Bluetooth.");
                    return;
                }

                Globals g = Globals.getInstance();
                String targetPrinterName = g.getPrinterRacuni();
                if (targetPrinterName == null || targetPrinterName.trim().isEmpty()) {
                    si.ros.RosKasa.AppPreferences prefs = new si.ros.RosKasa.AppPreferences(context);
                    targetPrinterName = prefs.getPrinterRacuni();
                    if (targetPrinterName != null && !targetPrinterName.trim().isEmpty()) {
                        g.setPrinterRacuni(targetPrinterName.trim());
                    }
                }
                if (targetPrinterName == null) targetPrinterName = "";
                targetPrinterName = targetPrinterName.trim();

                g.vpisiKronologijo("Bluetooth tiskanje: Ciljni tiskalnik iz Globals='" + targetPrinterName + "'");

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if (pairedDevices == null || pairedDevices.isEmpty()) {
                    postError(listener, "Ni najdenih seznanjanih Bluetooth naprav!");
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
                    if (!targetPrinterName.isEmpty()) {
                        Log.w(TAG, "Tiskalnik '" + targetPrinterName + "' ni najden med seznanjenimi. Uporaba prve Bluetooth naprave.");
                    }
                    selectedDevice = pairedDevices.iterator().next();
                }

                final String deviceName = selectedDevice.getName() != null ? selectedDevice.getName() : "Neznana naprava";

                bluetoothAdapter.cancelDiscovery();
                try {
                    socket = selectedDevice.createRfcommSocketToServiceRecord(SPP_UUID);
                    socket.connect();
                } catch (Exception e1) {
                    Log.w(TAG, "Standard SPP povezava ni uspela (" + e1.getMessage() + "), poskušam z nadomestno metodo (reflection)...");
                    try {
                        java.lang.reflect.Method m = selectedDevice.getClass().getMethod("createRfcommSocket", int.class);
                        socket = (BluetoothSocket) m.invoke(selectedDevice, 1);
                        socket.connect();
                    } catch (Exception e2) {
                        Log.e(TAG, "Tudi nadomestna metoda povezave ni uspela (" + e2.getMessage() + ")");
                        throw e1;
                    }
                }
                outputStream = socket.getOutputStream();

                if (bytesToPrint != null && bytesToPrint.length > 0) {
                    outputStream.write(bytesToPrint);
                    outputStream.flush();
                }

                Thread.sleep(500);
                postSuccess(listener, "Izpis uspešno poslan na " + deviceName + "!");

            } catch (SecurityException se) {
                Log.e(TAG, "Bluetooth SecurityException", se);
                postError(listener, "Dovoljenje za Bluetooth ni odobreno (SecurityException)!");
            } catch (Exception e) {
                Log.e(TAG, "Napaka pri Bluetooth tiskanju", e);
                postError(listener, "Napaka tiskanja: " + e.getMessage());
            } finally {
                try {
                    if (outputStream != null) outputStream.close();
                    if (socket != null) socket.close();
                } catch (Exception ignored) {}
            }
        });
    }

    public static void printTestReceipt(Context context, OnPrintListener listener) {
        Globals g = Globals.getInstance();
        int charCount = g.getPrinterSteviloZnakov() > 0 ? g.getPrinterSteviloZnakov() : 42;
        StringBuilder receipt = new StringBuilder();

        receipt.append(g.getEscInitPrint());
        receipt.append(g.getEscAlignCenter());
        receipt.append(g.getEscBoldOn());
        String podjetje = g.getNazivPodjetja().isEmpty() ? "ROS KASA D.O.O." : g.getNazivPodjetja();
        receipt.append(podjetje).append("\n");
        receipt.append("TEST TISKANJA BLUETOOTH\n");
        receipt.append(g.getEscBoldOff());
        receipt.append(g.getEscNewLine());
        receipt.append(g.getEscAlignLeft());

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        receipt.append("Datum: ").append(sdf.format(new Date())).append("\n");
        receipt.append("Število znakov/vrstico: ").append(charCount).append("\n");
        receipt.append(makeDivider(charCount)).append("\n");
        receipt.append(formatLine("1x KAVA ESPRESSO", "1,80 EUR", charCount)).append("\n");
        receipt.append(formatLine("1x LAŠKO PIVO 0.5", "3,20 EUR", charCount)).append("\n");
        receipt.append(makeDivider(charCount)).append("\n");
        receipt.append(g.getEscBoldOn());
        receipt.append(formatLine("SKUPAJ ZA PLAČILO:", "5,00 EUR", charCount)).append("\n");
        receipt.append(g.getEscBoldOff());
        receipt.append(makeDivider(charCount)).append("\n");
        receipt.append(g.getEscNewLine());
        receipt.append(g.getEscAlignCenter());
        receipt.append("HVALA ZA OBISK!\n");
        receipt.append("RosKasa Mobile POS\n");
        receipt.append(g.getEscNewLine());

        if (!g.getEscCut().isEmpty()) {
            receipt.append(g.getEscCut());
        } else {
            receipt.append("\n\n\n");
        }

        byte[] bytes = receipt.toString().getBytes(StandardCharsets.UTF_8);
        printReceiptBytes(context, bytes, listener);
    }

    private static String makeDivider(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    private static String formatLine(String left, String right, int width) {
        int space = width - left.length() - right.length();
        if (space < 1) space = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(left);
        for (int i = 0; i < space; i++) {
            sb.append(" ");
        }
        sb.append(right);
        return sb.toString();
    }

    private static void postSuccess(OnPrintListener listener, String msg) {
        Globals.getInstance().vpisiKronologijo("Bluetooth test tiskanja USPEH: " + msg);
        mainHandler.post(() -> {
            if (listener != null) listener.onSuccess(msg);
        });
    }

    private static void postError(OnPrintListener listener, String errorMsg) {
        Globals.getInstance().vpisiKronologijo("Bluetooth test tiskanja NAPAKA: " + errorMsg);
        mainHandler.post(() -> {
            if (listener != null) listener.onError(errorMsg);
        });
    }
}
