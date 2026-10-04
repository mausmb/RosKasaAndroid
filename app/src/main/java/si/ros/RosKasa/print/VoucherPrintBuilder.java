package si.ros.RosKasa.print;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunTp;

/**
 * Graditelj izpisa bona / vavčerja, skladen z Delphi FormKasaMobile.pas PrintVoucher.
 */
public class VoucherPrintBuilder {

    public static RacunPrintBuilder.ReceiptResult buildVoucher(RacunTp racun, Globals globals) {
        if (racun == null) {
            return new RacunPrintBuilder.ReceiptResult("", new byte[0]);
        }
        if (globals == null) {
            globals = Globals.getInstance();
        }

        // Preveri filter NIVO4IDVOUCHER (če je nastavljen, mora račun vsebovati ta artikel)
        int voucherNivo4 = globals.getNivo4IdVoucher();
        BigDecimal voucherZnesek = BigDecimal.ZERO;
        boolean hasVoucherItem = false;

        if (voucherNivo4 != 0 && racun.getRacPozic() != null) {
            for (PozicijaTp poz : racun.getRacPozic()) {
                if (poz != null && !poz.isRowDeleted() && poz.getNivo4Id() != null && poz.getNivo4Id() == voucherNivo4) {
                    hasVoucherItem = true;
                    if (poz.getZnesek() != null) {
                        voucherZnesek = voucherZnesek.add(poz.getZnesek());
                    }
                }
            }
            if (!hasVoucherItem) {
                return new RacunPrintBuilder.ReceiptResult("", new byte[0]);
            }
        } else {
            voucherZnesek = racun.getZnesek() != null ? racun.getZnesek() : BigDecimal.ZERO;
        }

        int width = globals.getPrinterSteviloZnakov();
        if (width <= 0) width = 42;
        if (width == 48) width = 42;

        StringBuilder preview = new StringBuilder();
        ByteArrayOutputStream printStream = new ByteArrayOutputStream();

        // 1. Reset & Init
        try {
            printStream.write(new byte[]{0x1B, 0x40}); // ESC @
            printStream.write(new byte[]{0x1C, 0x26}); // OptiPos UTF-8
            printStream.write(new byte[]{0x1C, 0x43, (byte) 0xFF});
            printStream.write(new byte[]{0x1B, 0x4D, 0x00}); // Font A
        } catch (Exception ignored) {}

        if (globals.getEscInitPrint() != null && !globals.getEscInitPrint().isEmpty()) {
            writeEsc(printStream, globals.getEscInitPrint());
        }

        // 2. Glava (podjetje + prodajno mesto)
        String podjetje = globals.getNazivPodjetja();
        if (podjetje == null || podjetje.trim().isEmpty()) podjetje = "ROS d.o.o.";
        String prodajnoMesto = globals.getNazivProdajnegaMesta();

        preview.append(podjetje).append("\n");
        writeEsc(printStream, globals.getEscAlignCenter());
        writeEsc(printStream, globals.getEscBoldOn());
        if (globals.getEscWidth2xOn() != null && !globals.getEscWidth2xOn().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOn());
        }
        try {
            printStream.write(podjetje.getBytes(StandardCharsets.UTF_8));
            printStream.write(0x0A);
        } catch (Exception ignored) {}
        if (globals.getEscWidth2xOff() != null && !globals.getEscWidth2xOff().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOff());
        }
        writeEsc(printStream, globals.getEscBoldOff());

        if (prodajnoMesto != null && !prodajnoMesto.trim().isEmpty()) {
            writeLine(preview, printStream, prodajnoMesto.trim(), false, true, globals);
        }
        writeBlankLine(preview, printStream);

        // 3. Oznaka BLOK / Številka (double width)
        String naslovBloka = "BLOK";
        writeLine(preview, printStream, naslovBloka, true, true, globals);

        String stevilka = String.valueOf(racun.getRacunId());
        preview.append(stevilka).append("\n");
        writeEsc(printStream, globals.getEscAlignCenter());
        writeEsc(printStream, globals.getEscBoldOn());
        if (globals.getEscWidth2xOn() != null && !globals.getEscWidth2xOn().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOn());
        }
        try {
            printStream.write(stevilka.getBytes(StandardCharsets.UTF_8));
            printStream.write(0x0A);
        } catch (Exception ignored) {}
        if (globals.getEscWidth2xOff() != null && !globals.getEscWidth2xOff().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOff());
        }
        writeEsc(printStream, globals.getEscBoldOff());
        writeBlankLine(preview, printStream);

        // 4. Datum in čas
        Date now = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String datumStr = sdfDate.format(now);
        String uraStr = sdfTime.format(now);
        if (racun.getDatum() != null && !racun.getDatum().trim().isEmpty()) {
            String raw = racun.getDatum().trim();
            if (raw.contains("T")) {
                String dPart = raw.substring(0, raw.indexOf("T"));
                String[] parts = dPart.split("-");
                if (parts.length == 3) datumStr = parts[2] + "." + parts[1] + "." + parts[0];
            } else {
                datumStr = raw;
            }
        }
        if (racun.getUra() != null && !racun.getUra().trim().isEmpty()) {
            String raw = racun.getUra().trim();
            if (raw.contains("T")) {
                uraStr = raw.substring(raw.indexOf("T") + 1);
                if (uraStr.length() > 8) uraStr = uraStr.substring(0, 8);
            } else {
                uraStr = raw;
            }
        }
        writeLine(preview, printStream, "Datum: " + datumStr + "  Ura: " + uraStr, false, false, globals);

        // 5. Vrednost (double width)
        writeLine(preview, printStream, makeDashes(width), false, false, globals);
        String vrednostStr = "Vrednost: " + String.format(Locale.US, "%.2f EUR", voucherZnesek.doubleValue()).replace('.', ',');
        preview.append(vrednostStr).append("\n");
        writeEsc(printStream, globals.getEscAlignLeft());
        writeEsc(printStream, globals.getEscBoldOn());
        if (globals.getEscWidth2xOn() != null && !globals.getEscWidth2xOn().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOn());
        }
        try {
            printStream.write(vrednostStr.getBytes(StandardCharsets.UTF_8));
            printStream.write(0x0A);
        } catch (Exception ignored) {}
        if (globals.getEscWidth2xOff() != null && !globals.getEscWidth2xOff().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOff());
        }
        writeEsc(printStream, globals.getEscBoldOff());

        // 6. Noga (Izstavil + črta za podpis)
        writeLine(preview, printStream, makeDashes(width), false, false, globals);
        String izstavil = globals.getTekocaOsebaNaziv();
        if (izstavil == null || izstavil.trim().isEmpty()) izstavil = "Blagajnik";
        writeLine(preview, printStream, "Izstavil: " + izstavil, false, false, globals);

        // 7. Odrez papirja
        writeBlankLine(preview, printStream);
        writeBlankLine(preview, printStream);
        if (globals.getEscCut() != null && !globals.getEscCut().isEmpty()) {
            writeEsc(printStream, globals.getEscCut());
        } else {
            writeEsc(printStream, "\n\n\n");
            try {
                printStream.write(new byte[]{0x1D, 0x56, 0x42, 0x00}); // GS V B 0
            } catch (Exception ignored) {}
        }

        return new RacunPrintBuilder.ReceiptResult(preview.toString(), printStream.toByteArray());
    }

    private static void writeLine(StringBuilder preview, ByteArrayOutputStream printStream, String line, boolean bold, boolean center, Globals g) {
        if (line == null) line = "";
        preview.append(line).append("\n");
        try {
            if (center && g.getEscAlignCenter() != null && !g.getEscAlignCenter().isEmpty()) {
                writeEsc(printStream, g.getEscAlignCenter());
            } else if (g.getEscAlignLeft() != null && !g.getEscAlignLeft().isEmpty()) {
                writeEsc(printStream, g.getEscAlignLeft());
            }
            if (bold && g.getEscBoldOn() != null && !g.getEscBoldOn().isEmpty()) {
                writeEsc(printStream, g.getEscBoldOn());
            }
            printStream.write(line.getBytes(StandardCharsets.UTF_8));
            printStream.write(0x0A);
            if (bold && g.getEscBoldOff() != null && !g.getEscBoldOff().isEmpty()) {
                writeEsc(printStream, g.getEscBoldOff());
            }
        } catch (Exception ignored) {}
    }

    private static void writeBlankLine(StringBuilder preview, ByteArrayOutputStream printStream) {
        preview.append("\n");
        try {
            printStream.write(0x0A);
        } catch (Exception ignored) {}
    }

    private static void writeEsc(ByteArrayOutputStream printStream, String escStr) {
        if (escStr == null || escStr.isEmpty()) return;
        try {
            printStream.write(escStr.getBytes(StandardCharsets.ISO_8859_1));
        } catch (Exception ignored) {}
    }

    private static String makeDashes(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append('-');
        return sb.toString();
    }
}
