package si.ros.RosKasa.print;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunTp;

/**
 * Graditelj izpisa naročila ob zaključku računa (Blok 1 šank / Blok 2 kuhinja),
 * skladen z Delphi FormKasaMobile.pas PrintNarocila.
 */
public class NarociloBlokPrintBuilder {

    public static List<RacunPrintBuilder.ReceiptResult> buildNarociloBloki(RacunTp racun, Globals globals) {
        List<RacunPrintBuilder.ReceiptResult> bloki = new ArrayList<>();
        if (racun == null || racun.getRacPozic() == null || racun.getRacPozic().isEmpty()) {
            return bloki;
        }
        if (globals == null) {
            globals = Globals.getInstance();
        }

        List<PozicijaTp> blok1Items = new ArrayList<>();
        List<PozicijaTp> blok2Items = new ArrayList<>();

        for (PozicijaTp poz : racun.getRacPozic()) {
            if (poz == null || poz.isRowDeleted()) continue;
            // Če ima določen kuhinjaId > 0, gre v Blok 2 (kuhinja), sicer v Blok 1 (šank/točilnica)
            if (poz.getKuhinjaId() != null && poz.getKuhinjaId() > 0) {
                blok2Items.add(poz);
            } else {
                blok1Items.add(poz);
            }
        }

        if (!blok1Items.isEmpty()) {
            bloki.add(buildPosamezniBlok(racun, globals, "NAROČILO - ŠANK", blok1Items));
        }
        if (!blok2Items.isEmpty()) {
            bloki.add(buildPosamezniBlok(racun, globals, "NAROČILO - KUHINJA", blok2Items));
        }

        return bloki;
    }

    private static RacunPrintBuilder.ReceiptResult buildPosamezniBlok(RacunTp racun, Globals globals, String naslov, List<PozicijaTp> items) {
        int width = globals.getPrinterSteviloZnakov();
        if (width <= 0) width = 42;
        if (width == 48) width = 42;

        StringBuilder preview = new StringBuilder();
        ByteArrayOutputStream printStream = new ByteArrayOutputStream();

        // 1. Init
        try {
            printStream.write(new byte[]{0x1B, 0x40});
            printStream.write(new byte[]{0x1C, 0x26});
            printStream.write(new byte[]{0x1C, 0x43, (byte) 0xFF});
            printStream.write(new byte[]{0x1B, 0x4D, 0x00});
        } catch (Exception ignored) {}

        if (globals.getEscInitPrint() != null && !globals.getEscInitPrint().isEmpty()) {
            writeEsc(printStream, globals.getEscInitPrint());
        }

        // 2. Glava
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

        writeLine(preview, printStream, naslov, true, true, globals);
        writeBlankLine(preview, printStream);

        // 3. Številka naročila / marker / lokator
        String stNarocila = "Naročilo #" + racun.getRacunId();
        preview.append(stNarocila).append("\n");
        writeEsc(printStream, globals.getEscAlignLeft());
        writeEsc(printStream, globals.getEscBoldOn());
        if (globals.getEscWidth2xOn() != null && !globals.getEscWidth2xOn().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOn());
        }
        try {
            printStream.write(stNarocila.getBytes(StandardCharsets.UTF_8));
            printStream.write(0x0A);
        } catch (Exception ignored) {}
        if (globals.getEscWidth2xOff() != null && !globals.getEscWidth2xOff().isEmpty()) {
            writeEsc(printStream, globals.getEscWidth2xOff());
        }
        writeEsc(printStream, globals.getEscBoldOff());

        if (racun.getMarker() != null && !racun.getMarker().trim().isEmpty()) {
            writeLine(preview, printStream, "Marker: " + racun.getMarker().trim(), false, false, globals);
        }
        if (racun.getLokator() != null && !racun.getLokator().trim().isEmpty()) {
            String lokatorStr = "Lokator: " + racun.getLokator().trim();
            preview.append(lokatorStr).append("\n");
            writeEsc(printStream, globals.getEscBoldOn());
            if (globals.getEscWidth2xOn() != null && !globals.getEscWidth2xOn().isEmpty()) {
                writeEsc(printStream, globals.getEscWidth2xOn());
            }
            try {
                printStream.write(lokatorStr.getBytes(StandardCharsets.UTF_8));
                printStream.write(0x0A);
            } catch (Exception ignored) {}
            if (globals.getEscWidth2xOff() != null && !globals.getEscWidth2xOff().isEmpty()) {
                writeEsc(printStream, globals.getEscWidth2xOff());
            }
            writeEsc(printStream, globals.getEscBoldOff());
        }

        writeLine(preview, printStream, makeDashes(width), false, false, globals);

        // 4. Postavke
        for (PozicijaTp poz : items) {
            String kolStr = String.format(Locale.US, "%.2f", poz.getKolicina());
            String epStr = (poz.getEnotaProdajeId() != null && poz.getEnotaProdajeId().compareTo(java.math.BigDecimal.ONE) > 0)
                    ? " x " + String.format(Locale.US, "%.2f", poz.getEnotaProdajeId().doubleValue())
                    : " x 1";

            String naziv = (poz.getNaziv() != null && !poz.getNaziv().trim().isEmpty()) ? poz.getNaziv().trim() : "Artikel";
            if (naziv.length() > width) naziv = naziv.substring(0, width);

            // Delphi: kolicina x enota (krepko), v naslednji vrstici naziv artikla
            writeLine(preview, printStream, kolStr + epStr, true, false, globals);
            writeLine(preview, printStream, naziv, true, false, globals);
            if (poz.getDodatniOpis() != null && !poz.getDodatniOpis().trim().isEmpty()) {
                writeLine(preview, printStream, "  * " + poz.getDodatniOpis().trim(), false, false, globals);
            }
        }

        writeLine(preview, printStream, makeDashes(width), false, false, globals);

        // 5. Noga
        String stregel = globals.getNazivStregelVasJe();
        String oseba = globals.getTekocaOsebaNaziv();
        if (stregel == null || stregel.trim().isEmpty()) stregel = "Stregel:";
        if (oseba == null || oseba.trim().isEmpty()) oseba = "Blagajnik";
        writeLine(preview, printStream, stregel + " " + oseba, false, false, globals);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        writeLine(preview, printStream, "Datum: " + sdf.format(new Date()), false, false, globals);

        // 6. Odrez papirja
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
