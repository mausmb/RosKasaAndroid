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
        printReceiptBytes(context, result.getPrintBytes(), listener);
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
                socket = selectedDevice.createRfcommSocketToServiceRecord(SPP_UUID);
                socket.connect();
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
