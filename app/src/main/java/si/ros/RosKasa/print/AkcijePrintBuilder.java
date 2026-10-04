package si.ros.RosKasa.print;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.models.AkcijaTp;
import si.ros.RosKasa.models.RacunTp;

/**
 * Graditelj izpisa promocijskih akcij in kuponov s QR kodo,
 * skladen z Delphi uPrintData.pas RspAkcijaKuponi.
 */
public class AkcijePrintBuilder {

    public static List<RacunPrintBuilder.ReceiptResult> buildAkcijeKuponi(RacunTp racun, List<AkcijaTp> kuponi, Globals globals) {
        List<RacunPrintBuilder.ReceiptResult> list = new ArrayList<>();
        if (kuponi == null || kuponi.isEmpty()) {
            return list;
        }
        if (globals == null) {
            globals = Globals.getInstance();
        }

        int width = globals.getPrinterSteviloZnakov();
        if (width <= 0) width = 42;
        if (width == 48) width = 42;

        String podjetje = globals.getNazivPodjetja();
        if (podjetje == null || podjetje.trim().isEmpty()) podjetje = "ROS d.o.o.";
        String prodajnoMesto = globals.getNazivProdajnegaMesta();
        int racunId = racun != null ? racun.getRacunId() : 0;

        for (AkcijaTp k : kuponi) {
            if (k == null) continue;

            StringBuilder preview = new StringBuilder();
            ByteArrayOutputStream printStream = new ByteArrayOutputStream();

            // 1. Init
            try {
                printStream.write(new byte[]{0x1B, 0x40});
                printStream.write(new byte[]{0x1C, 0x26});
                printStream.write(new byte[]{0x1C, 0x43, (byte) 0xFF});
                printStream.write(new byte[]{0x1B, 0x4D, 0x00});
            } catch (Exception ignored) {}

            // 2. Glava
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
            writeLine(preview, printStream, "Račun ref.: " + racunId, false, false, globals);
            writeLine(preview, printStream, makeDashes(width), false, false, globals);

            // 3. Kupon oznaka
            String tisk = k.getTisk().isEmpty() ? "KUPON" : k.getTisk();
            String kuponLine = tisk + ":    " + k.getKuponId();
            writeLine(preview, printStream, kuponLine, true, false, globals);

            // 4. QR koda kupona (nativna ESC/POS)
            String kuponId = k.getKuponId().trim();
            if (!kuponId.isEmpty()) {
                preview.append("\n[QR KODA KUPONA: ").append(kuponId).append("]\n\n");
                byte[] qrBytes = FursQrHelper.kreirajEscQrKodoBytes(kuponId);
                if (qrBytes != null && qrBytes.length > 0) {
                    try {
                        writeEsc(printStream, globals.getEscAlignCenter());
                        printStream.write(qrBytes);
                        printStream.write(0x0A);
                        writeEsc(printStream, globals.getEscAlignLeft());
                    } catch (Exception ignored) {}
                }
            }

            // 5. Opis akcije
            writeLine(preview, printStream, makeDashes(width), false, false, globals);
            if (k.getOpis() != null && !k.getOpis().trim().isEmpty()) {
                writeLine(preview, printStream, k.getOpis().trim(), false, false, globals);
            }
            writeLine(preview, printStream, makeDashes(width), false, false, globals);

            // 6. Veljavnost
            if (k.getDatumOd() != null && !k.getDatumOd().trim().isEmpty()) {
                writeLine(preview, printStream, "VELJA OD: " + k.getDatumOd().trim(), false, false, globals);
            }
            if (k.getDatumDo() != null && !k.getDatumDo().trim().isEmpty()) {
                writeLine(preview, printStream, "VELJA DO: " + k.getDatumDo().trim(), false, false, globals);
            }

            // 7. Odrez
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

            list.add(new RacunPrintBuilder.ReceiptResult(preview.toString(), printStream.toByteArray()));
        }

        return list;
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
