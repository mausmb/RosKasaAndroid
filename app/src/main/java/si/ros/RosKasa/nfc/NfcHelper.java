package si.ros.RosKasa.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

import java.math.BigInteger;

public class NfcHelper {
    private static final String TAG = "NfcHelper";

    public static class NfcCardInfo {
        public final String hexId;
        public final String decimalId;
        public final String reversedHexId;
        public final String reversedDecimalId;

        public NfcCardInfo(String hexId, String decimalId) {
            this(hexId, decimalId, "", "");
        }

        public NfcCardInfo(String hexId, String decimalId, String reversedHexId, String reversedDecimalId) {
            this.hexId = hexId != null ? hexId : "";
            this.decimalId = decimalId != null ? decimalId : "";
            this.reversedHexId = reversedHexId != null ? reversedHexId : "";
            this.reversedDecimalId = reversedDecimalId != null ? reversedDecimalId : "";
        }

        @Override
        public String toString() {
            return "NfcCardInfo{hex='" + hexId + "', dec='" + decimalId + "', revHex='" + reversedHexId + "', revDec='" + reversedDecimalId + "'}";
        }
    }

    public interface OnNfcTagReadListener {
        void onTagRead(NfcCardInfo cardInfo);
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * Dekodira NFC Tag v HEX in DESETIŠKO obliko (tako v standardnem kot obrnjenem vrstnem redu bajtov).
     */
    public static NfcCardInfo decodeTag(byte[] tagId) {
        if (tagId == null || tagId.length == 0) return null;
        String hex = bytesToHex(tagId).replace("-", "").replace(":", "").trim();
        String decimal = "";
        try {
            BigInteger bi = new BigInteger(hex, 16);
            decimal = bi.toString();
        } catch (Exception e) {
            Log.w(TAG, "Napaka pri pretvorbi hex v decimal: " + e.getMessage());
        }

        // Obrnjen vrstni red bajtov (Little Endian / LSB first)
        byte[] reversed = new byte[tagId.length];
        for (int i = 0; i < tagId.length; i++) {
            reversed[i] = tagId[tagId.length - 1 - i];
        }
        String revHex = bytesToHex(reversed).replace("-", "").replace(":", "").trim();
        String revDecimal = "";
        try {
            BigInteger biRev = new BigInteger(revHex, 16);
            revDecimal = biRev.toString();
        } catch (Exception e) {
            Log.w(TAG, "Napaka pri pretvorbi revHex v decimal: " + e.getMessage());
        }

        return new NfcCardInfo(hex, decimal, revHex, revDecimal);
    }

    public static NfcCardInfo extractTagFromIntent(Intent intent) {
        if (intent == null) return null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Tag tag;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag.class);
            } else {
                tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            }
            if (tag != null && tag.getId() != null) {
                return decodeTag(tag.getId());
            }
        }
        return null;
    }

    /**
     * Omogoči ReaderMode (Android 4.4+ / API 19+).
     * To je najbolj zanesljiv način za branje RFID/NFC kartic na POS napravah in telefonih,
     * saj obide težave z Intent-i, PendingIntent filtri in življenjskim ciklom aktivnosti.
     */
    public static boolean enableReaderMode(Activity activity, NfcAdapter nfcAdapter, OnNfcTagReadListener listener) {
        if (activity == null || nfcAdapter == null || !nfcAdapter.isEnabled()) return false;
        try {
            int flags = NfcAdapter.FLAG_READER_NFC_A |
                        NfcAdapter.FLAG_READER_NFC_B |
                        NfcAdapter.FLAG_READER_NFC_F |
                        NfcAdapter.FLAG_READER_NFC_V;
            nfcAdapter.enableReaderMode(activity, tag -> {
                if (tag != null && tag.getId() != null) {
                    NfcCardInfo cardInfo = decodeTag(tag.getId());
                    if (cardInfo != null && listener != null) {
                        activity.runOnUiThread(() -> listener.onTagRead(cardInfo));
                    }
                }
            }, flags, null);
            Log.d(TAG, "enableReaderMode uspešno aktiviran");
            return true;
        } catch (Exception e) {
            Log.w(TAG, "enableReaderMode napaka: " + e.getMessage());
            return false;
        }
    }

    public static void disableReaderMode(Activity activity, NfcAdapter nfcAdapter) {
        if (activity == null || nfcAdapter == null) return;
        try {
            nfcAdapter.disableReaderMode(activity);
            Log.d(TAG, "disableReaderMode deaktiviran");
        } catch (Exception e) {
            Log.w(TAG, "disableReaderMode napaka: " + e.getMessage());
        }
    }

    public static void enableForegroundDispatch(Activity activity, NfcAdapter nfcAdapter) {
        if (activity == null || nfcAdapter == null || !nfcAdapter.isEnabled()) return;
        try {
            Intent intent = new Intent(activity, activity.getClass());
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                flags |= PendingIntent.FLAG_MUTABLE;
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, flags);
            // Pomembno: filters = null in techLists = null zajame VSE tipe kartic brez izločanja!
            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, null, null);
            Log.d(TAG, "enableForegroundDispatch uspešno aktiviran (catch-all)");
        } catch (Exception e) {
            Log.w(TAG, "enableForegroundDispatch napaka: " + e.getMessage());
        }
    }

    public static void disableForegroundDispatch(Activity activity, NfcAdapter nfcAdapter) {
        if (activity == null || nfcAdapter == null) return;
        try {
            nfcAdapter.disableForegroundDispatch(activity);
            Log.d(TAG, "disableForegroundDispatch deaktiviran");
        } catch (Exception e) {
            Log.w(TAG, "disableForegroundDispatch napaka: " + e.getMessage());
        }
    }
}
