package si.ros.RosKasa.print;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FursQrHelper {

    /**
     * Izračuna FURS numerično kodo za QR kodo po uradnem FURS algoritmu (modulo 10):
     * 1. ZOI (32 hex znakov) -> decimalni niz, dopolnjen z vodilnimi ničlami na 39 znakov.
     * 2. Davčna številka podjetja (številke brez oznake SI).
     * 3. Datum in čas izdaje računa v formatu YYMMDDHHmmss (12 znakov).
     * 4. Kontrolna vsota po modulu 10 (vsota vseh števk mod 10).
     * Skupaj: 39 + 8 + 12 + 1 = 60 znakov (oz. ustrezno dolžini davčne).
     */
    public static String pripraviFursKodo(String zoi, String davcna, Date datumUra) {
        if (zoi == null || zoi.trim().isEmpty()) {
            return "";
        }
        try {
            String cleanZoi = zoi.trim().replace("-", "").replace(" ", "");
            BigInteger bi = new BigInteger(cleanZoi, 16);
            String decZoi = bi.toString(10);
            String lpadZoi = lpadZeros(decZoi, 39);

            String cleanDavcna = davcna != null ? davcna.replaceAll("[^0-9]", "") : "";

            Date d = datumUra != null ? datumUra : new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss", Locale.US);
            String datura = sdf.format(d);

            String rez = lpadZoi + cleanDavcna + datura;
            int sum = 0;
            for (int i = 0; i < rez.length(); i++) {
                char c = rez.charAt(i);
                if (Character.isDigit(c)) {
                    sum += (c - '0');
                }
            }
            int modulo = sum % 10;
            return rez + modulo;
        } catch (Exception e) {
            return "";
        }
    }

    private static String lpadZeros(String input, int len) {
        if (input.length() >= len) {
            return input.substring(0, len);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len - input.length(); i++) {
            sb.append('0');
        }
        sb.append(input);
        return sb.toString();
    }

    /**
     * Generira standardne ESC/POS ukaze (GS ( k) za izris QR kode na termičnem tiskalniku.
     */
    public static byte[] kreirajEscQrKodoBytes(String qrData) {
        if (qrData == null || qrData.isEmpty()) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 1. Model: GS ( k 4 0 49 65 50 0 (Model 2)
            baos.write(new byte[]{0x1D, 0x28, 0x6B, 0x04, 0x00, 0x31, 0x41, 0x32, 0x00});

            // 2. Velikost modula: GS ( k 3 0 49 67 4
            baos.write(new byte[]{0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x43, 0x04});

            // 3. Error level: GS ( k 3 0 49 69 49 (Level M)
            baos.write(new byte[]{0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x45, 0x31});

            // 4. Shranjevanje podatkov v tiskalnik: GS ( k pL pH 49 80 48 <data>
            byte[] dataBytes = qrData.getBytes(StandardCharsets.US_ASCII);
            int len = dataBytes.length + 3;
            byte pL = (byte) (len % 256);
            byte pH = (byte) (len / 256);
            baos.write(new byte[]{0x1D, 0x28, 0x6B, pL, pH, 0x31, 0x50, 0x30});
            baos.write(dataBytes);

            // 5. Tisk QR kode: GS ( k 3 0 49 81 48
            baos.write(new byte[]{0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x51, 0x30});

            return baos.toByteArray();
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
