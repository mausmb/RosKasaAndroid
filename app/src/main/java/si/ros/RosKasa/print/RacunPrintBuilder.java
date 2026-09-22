package si.ros.RosKasa.print;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.models.NacPlacTp;
import si.ros.RosKasa.models.PlaciloTp;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunTp;

public class RacunPrintBuilder {

    public static class ReceiptResult {
        private final String textPreview;
        private final byte[] printBytes;

        public ReceiptResult(String textPreview, byte[] printBytes) {
            this.textPreview = textPreview;
            this.printBytes = printBytes;
        }

        public String getTextPreview() {
            return textPreview;
        }

        public byte[] getPrintBytes() {
            return printBytes;
        }
    }

    private static class DavcnaPostavka {
        double stopnja;
        BigDecimal osnova = BigDecimal.ZERO;
        BigDecimal ddv = BigDecimal.ZERO;
        BigDecimal znesek = BigDecimal.ZERO;
    }

    public static ReceiptResult buildReceipt(RacunTp racun, Globals globals, int stKopij) {
        if (racun == null) {
            return new ReceiptResult("", new byte[0]);
        }
        if (globals == null) {
            globals = Globals.getInstance();
        }

        int width = globals.getPrinterSteviloZnakov();
        if (width <= 0) width = 32;
        if (width == 48) width = 42;

        StringBuilder preview = new StringBuilder();
        ByteArrayOutputStream printStream = new ByteArrayOutputStream();

        // Inicializacija tiskalnika
        writeEsc(printStream, globals.getEscInitPrint());

        // 1. GLAVA PODJETJA
        String podjetje = globals.getNazivPodjetja();
        if (podjetje == null || podjetje.trim().isEmpty()) podjetje = "ROS d.o.o. ,";
        String obratPE = globals.getNazivObratPE();
        String prodajnoMesto = globals.getNazivProdajnegaMesta();
        String naslovProdajno = globals.getNaslovProdajnega();
        String naslovPodjetje = globals.getNaslovPodjetja();
        String ddv = globals.getDdvStevilka();
        if (ddv == null || ddv.trim().isEmpty()) ddv = "SI32355058";

        // Podjetje (lahko krepko na tiskalniku)
        writeLine(preview, printStream, podjetje, false, true, globals);

        if (obratPE != null && !obratPE.trim().isEmpty()) {
            writeLine(preview, printStream, obratPE.trim(), false, false, globals);
        }
        if (prodajnoMesto != null && !prodajnoMesto.trim().isEmpty()) {
            writeLine(preview, printStream, prodajnoMesto.trim(), false, false, globals);
        }
        if (naslovProdajno != null && !naslovProdajno.trim().isEmpty()) {
            writeLine(preview, printStream, naslovProdajno.trim(), false, false, globals);
        }
        if (naslovPodjetje != null && !naslovPodjetje.trim().isEmpty()) {
            writeLine(preview, printStream, naslovPodjetje.trim(), false, false, globals);
        }
        writeLine(preview, printStream, "IDzaDV : " + ddv.trim(), false, false, globals);
        writeBlankLine(preview, printStream);

        // 2. ŠTEVILKA DOKUMENTA IN REFERENCA
        boolean isFiskal = (racun.getFiskalizacija() != null && racun.getFiskalizacija() == 1);
        String docTitle;
        if (isFiskal) {
            int pId = (racun.getfPoslovniProstorId() != null && racun.getfPoslovniProstorId() > 0) ? racun.getfPoslovniProstorId() : 5000;
            int bId = (racun.getfPosId() != null && racun.getfPosId() > 0) ? racun.getfPosId() : 500;
            int sId = (racun.getfStevilkaRacuna() != null && racun.getfStevilkaRacuna() > 0) ? racun.getfStevilkaRacuna() : racun.getRacunId();
            docTitle = "Račun številka " + pId + "-" + bId + "-" + sId;
        } else {
            docTitle = "Dobavnica številka " + racun.getRacunId();
        }
        writeLine(preview, printStream, docTitle, false, false, globals);
        writeLine(preview, printStream, "Referenčna številka " + racun.getRacunId(), false, false, globals);

        // Storno oznaka
        if (racun.getStornoRacunId() != null && racun.getStornoRacunId() > 0) {
            writeLine(preview, printStream, "Storno računa: " + racun.getStornoRacunId(), true, false, globals);
        }

        // Kopija oznaka
        if (stKopij > 0) {
            writeLine(preview, printStream, "Kopija št.: " + stKopij, true, false, globals);
        }

        // Datum in čas
        Date docDate = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String datumStr = sdfDate.format(docDate);
        String uraStr = sdfTime.format(docDate);
        if (racun.getDatum() != null && !racun.getDatum().trim().isEmpty()) {
            datumStr = racun.getDatum().trim();
        }
        if (racun.getUra() != null && !racun.getUra().trim().isEmpty()) {
            uraStr = racun.getUra().trim();
        }
        writeBlankLine(preview, printStream);
        writeLine(preview, printStream, "Datum " + datumStr + " Ura " + uraStr, false, false, globals);

        // 3. KUPEC / PARTNER (če obstaja dobavnica ali partner na plačilih)
        String partnerNaziv = "";
        String partnerNaslov = "";
        String partnerDavcna = "";
        if (racun.getRacPlaci() != null) {
            for (PlaciloTp pl : racun.getRacPlaci()) {
                if (pl != null && pl.getNazivPartner() != null && !pl.getNazivPartner().trim().isEmpty()) {
                    partnerNaziv = pl.getNazivPartner().trim();
                    partnerNaslov = pl.getNaslovPartner() != null ? pl.getNaslovPartner().trim() : "";
                    partnerDavcna = pl.getDavcnaSt() != null ? pl.getDavcnaSt().trim() : "";
                    break;
                }
            }
        }
        if (!partnerNaziv.isEmpty()) {
            writeBlankLine(preview, printStream);
            writeLine(preview, printStream, "Kupec", false, false, globals);
            writeLine(preview, printStream, partnerNaziv, false, false, globals);
            if (!partnerNaslov.isEmpty()) {
                writeLine(preview, printStream, partnerNaslov, false, false, globals);
            }
            if (!partnerDavcna.isEmpty()) {
                writeLine(preview, printStream, partnerDavcna, false, false, globals);
            }
        }

        writeBlankLine(preview, printStream);

        // 4. POSTAVKE (ARTIKLI)
        // Glava tabele artiklov
        String artHeader = formatHeader(width);
        writeLine(preview, printStream, artHeader, false, false, globals);
        writeLine(preview, printStream, makeDashes(width), false, false, globals);

        BigDecimal skupajZnesek = BigDecimal.ZERO;
        BigDecimal skupajPopust = BigDecimal.ZERO;

        Map<Double, DavcnaPostavka> davkiMap = new LinkedHashMap<>();

        if (racun.getRacPozic() != null) {
            for (PozicijaTp poz : racun.getRacPozic()) {
                if (poz == null || poz.isRowDeleted()) continue;

                int nivo4 = (poz.getNivo4Id() != null) ? poz.getNivo4Id() : 0;
                String naziv = (poz.getNaziv() != null) ? poz.getNaziv().trim() : "";
                if ((naziv.isEmpty() || naziv.startsWith("Artikel #")) && nivo4 > 0 && globals != null) {
                    String lookup = globals.findNazivByNivo4Id(nivo4);
                    if (lookup != null && !lookup.trim().isEmpty()) {
                        naziv = lookup.trim();
                        poz.setNaziv(naziv);
                    }
                }
                if (naziv.isEmpty()) {
                    naziv = (nivo4 > 0) ? "Artikel #" + nivo4 : "Artikel";
                    poz.setNaziv(naziv);
                }
                if (naziv.length() > width) {
                    naziv = naziv.substring(0, width);
                }
                writeLine(preview, printStream, naziv, false, false, globals);

                // Vrstica s količino, enoto, ceno in zneskom
                // Primer: "   1,00*  1,00     4,50     4,50"
                BigDecimal ep = poz.getEnotaProdajeId() != null ? poz.getEnotaProdajeId() : BigDecimal.ONE;
                double kol = poz.getKolicina();
                BigDecimal cena = poz.getCena() != null ? poz.getCena() : BigDecimal.ZERO;
                BigDecimal zn = poz.getZnesek() != null ? poz.getZnesek() : cena.multiply(BigDecimal.valueOf(kol));

                String rowLine = formatPositionRow(ep, kol, cena, zn, width);
                writeLine(preview, printStream, rowLine, false, false, globals);

                if (poz.getZnesekPopust() != null && poz.getZnesekPopust().abs().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal pop = poz.getZnesekPopust().abs();
                    skupajPopust = skupajPopust.add(pop);
                    writeLine(preview, printStream, "   Popust: -" + formatCurrency(pop), false, false, globals);
                }

                skupajZnesek = skupajZnesek.add(zn);

                // Davčna evidenca
                double stopnja = poz.getStopnjaDavka();
                if (stopnja == 0.0 && poz.getTarifaId() != null) {
                    // Fallback če stopnja ni bila nastavljena
                    if (poz.getTarifaId() == 1 || poz.getTarifaId() == 40) stopnja = 22.0;
                    else if (poz.getTarifaId() == 2) stopnja = 9.5;
                }
                if (stopnja == 0.0) stopnja = 22.0; // Privzeto 22%

                DavcnaPostavka dp = davkiMap.get(stopnja);
                if (dp == null) {
                    dp = new DavcnaPostavka();
                    dp.stopnja = stopnja;
                    davkiMap.put(stopnja, dp);
                }
                dp.znesek = dp.znesek.add(zn);
                if (poz.getZnesekDavka() != null && poz.getZnesekDavka().compareTo(BigDecimal.ZERO) > 0) {
                    dp.ddv = dp.ddv.add(poz.getZnesekDavka());
                }
            }
        }

        // 5. SKUPAJ IN ZA PLAČILO
        writeLine(preview, printStream, makeDashes(width), false, false, globals);
        writeLine(preview, printStream, formatKeyValue("Skupaj", formatCurrency(skupajZnesek), width), false, false, globals);
        if (skupajPopust.compareTo(BigDecimal.ZERO) > 0) {
            writeLine(preview, printStream, formatKeyValue("Popust", "-" + formatCurrency(skupajPopust), width), false, false, globals);
        }

        // "Za plačilo" - povečano na tiskalniku
        String zaPlaciloVal = formatCurrency(racun.getZnesek() != null && racun.getZnesek().compareTo(BigDecimal.ZERO) > 0 ? racun.getZnesek() : skupajZnesek);
        String zaPlaciloLine = formatKeyValue("Za plačilo", zaPlaciloVal, width);

        // Preview: navadna vrstica
        preview.append(zaPlaciloLine).append("\n");
        // Tiskalnik: Dvojna širina / krepko
        writeEsc(printStream, globals.getEscWidth2xOn());
        writeEsc(printStream, globals.getEscBoldOn());
        printStream.write(zaPlaciloLine.getBytes(StandardCharsets.UTF_8), 0, zaPlaciloLine.getBytes(StandardCharsets.UTF_8).length);
        printStream.write(0x0A);
        writeEsc(printStream, globals.getEscWidth2xOff());
        writeEsc(printStream, globals.getEscBoldOff());

        // 6. PLAČILA
        writeBlankLine(preview, printStream);
        writeLine(preview, printStream, "Plačila", false, false, globals);
        writeLine(preview, printStream, makeDashes(width), false, false, globals);

        boolean needsSignature = false;
        if (racun.getRacPlaci() != null && !racun.getRacPlaci().isEmpty()) {
            for (PlaciloTp pl : racun.getRacPlaci()) {
                if (pl == null || pl.isRowDeleted()) continue;
                String plNaziv = "GOTOVINA";
                NacPlacTp np = globals.getPlaciloById(pl.getPlaciloId());
                if (np != null && !np.getNaziv().isEmpty()) {
                    plNaziv = np.getNaziv();
                } else if (pl.getPlaciloId() == 399) {
                    plNaziv = "KREDIT. KARTICA";
                }
                BigDecimal plZn = pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0 ? pl.getDelniZnesek() : pl.getZnesek();
                writeLine(preview, printStream, formatKeyValue(plNaziv, formatCurrency(plZn), width), false, false, globals);

                // Če je dobavnica ali nepogodbeni kupec -> zahteva podpis
                if (plNaziv.toUpperCase().contains("DOBAVNICA") || plNaziv.toUpperCase().contains("SOBA") || pl.getPartnerId() != null && pl.getPartnerId() > 0) {
                    needsSignature = true;
                }
            }
        } else {
            writeLine(preview, printStream, formatKeyValue("GOTOVINA", zaPlaciloVal, width), false, false, globals);
        }

        if (needsSignature) {
            writeBlankLine(preview, printStream);
            writeLine(preview, printStream, makeUnderscores(width), false, false, globals);
        }

        // 7. DAVČNA REKAPITULACIJA
        writeBlankLine(preview, printStream);
        writeLine(preview, printStream, formatTaxHeader(width), false, false, globals);
        writeLine(preview, printStream, makeDashes(width), false, false, globals);

        for (DavcnaPostavka dp : davkiMap.values()) {
            BigDecimal sto = BigDecimal.valueOf(dp.stopnja);
            if (dp.ddv.compareTo(BigDecimal.ZERO) == 0 && sto.compareTo(BigDecimal.ZERO) > 0) {
                // Izračun osnove in DDV: osnova = znesek / (1 + stopnja/100)
                BigDecimal divisor = BigDecimal.ONE.add(sto.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
                dp.osnova = dp.znesek.divide(divisor, 2, RoundingMode.HALF_UP);
                dp.ddv = dp.znesek.subtract(dp.osnova);
            } else {
                dp.osnova = dp.znesek.subtract(dp.ddv);
            }
            writeLine(preview, printStream, formatTaxRow(dp.stopnja, dp.osnova, dp.ddv, dp.znesek, width), false, false, globals);
        }

        // 8. FURS FISKALIZACIJA (ZOI, EOR, QR KODA)
        if (isFiskal) {
            writeBlankLine(preview, printStream);
            String zoi = racun.getfPodpis() != null ? racun.getfPodpis().trim() : "";
            String eor = racun.getfOznakaDu() != null ? racun.getfOznakaDu().trim() : "";

            if (!zoi.isEmpty()) {
                writeWrappedLine(preview, printStream, "ZOI: " + zoi, width, globals);
            }
            if (!eor.isEmpty()) {
                writeWrappedLine(preview, printStream, "EOR: " + eor, width, globals);
            }

            // QR koda
            String cleanDavcna = globals.getDavcnaZaFurs();
            if (cleanDavcna == null || cleanDavcna.trim().isEmpty()) {
                cleanDavcna = globals.getDdvStevilka();
            }
            String fursNumericCode = FursQrHelper.pripraviFursKodo(zoi, cleanDavcna, docDate);

            // Preview: tekstualna oznaka QR kode
            preview.append("\n[QR KODA FISKALIZACIJE]\n");

            // Tiskalnik: nativni ESC/POS QR ukazi
            if (!fursNumericCode.isEmpty()) {
                byte[] qrBytes = FursQrHelper.kreirajEscQrKodoBytes(fursNumericCode);
                try {
                    printStream.write(qrBytes);
                    printStream.write(0x0A);
                } catch (Exception ignored) {}
            }
        }

        // 9. NOGA RAČUNA (STREGEL, ZAHVALE)
        writeBlankLine(preview, printStream);
        String natakar = globals.getNazivStregelVasJe();
        if (natakar == null || natakar.trim().isEmpty()) {
            natakar = "ROS OSEBA";
        }
        writeLine(preview, printStream, "Stregel/a vas je : " + natakar.trim(), false, false, globals);
        writeLine(preview, printStream, "Hvala za vaš obisk !", false, false, globals);

        if (globals.getNazivZahvala1() != null && !globals.getNazivZahvala1().trim().isEmpty()) {
            writeLine(preview, printStream, globals.getNazivZahvala1().trim(), false, false, globals);
        }
        if (globals.getNazivZahvala2() != null && !globals.getNazivZahvala2().trim().isEmpty()) {
            writeLine(preview, printStream, globals.getNazivZahvala2().trim(), false, false, globals);
        }
        if (globals.getNazivZahvala3() != null && !globals.getNazivZahvala3().trim().isEmpty()) {
            writeLine(preview, printStream, globals.getNazivZahvala3().trim(), false, false, globals);
        }
        if (globals.getNazivZahvala4() != null && !globals.getNazivZahvala4().trim().isEmpty()) {
            writeLine(preview, printStream, globals.getNazivZahvala4().trim(), false, false, globals);
        }

        // Odrez papirja
        writeBlankLine(preview, printStream);
        writeBlankLine(preview, printStream);
        if (globals.getEscCut() != null && !globals.getEscCut().isEmpty()) {
            writeEsc(printStream, globals.getEscCut());
        } else {
            writeEsc(printStream, "\n\n\n\n");
            // Standard ESC/POS partial cut: GS V 0
            try {
                printStream.write(new byte[]{0x1D, 0x56, 0x00});
            } catch (Exception ignored) {}
        }

        return new ReceiptResult(preview.toString(), printStream.toByteArray());
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
            printStream.write(0x0A); // Novi red

            if (bold && g.getEscBoldOff() != null && !g.getEscBoldOff().isEmpty()) {
                writeEsc(printStream, g.getEscBoldOff());
            }
        } catch (Exception ignored) {}
    }

    private static void writeWrappedLine(StringBuilder preview, ByteArrayOutputStream printStream, String line, int width, Globals g) {
        if (line == null) return;
        while (line.length() > width) {
            String part = line.substring(0, width);
            writeLine(preview, printStream, part, false, false, g);
            line = line.substring(width);
        }
        if (!line.isEmpty()) {
            writeLine(preview, printStream, line, false, false, g);
        }
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

    private static String formatHeader(int width) {
        // Naziv (10) Kol (9) Cena (8) Vred. (5) = 32
        return padRight("Naziv", 10) + padRight("Kol", 9) + padRight("Cena", 8) + padRight("Vred.", 5);
    }

    private static String formatPositionRow(BigDecimal ep, double kol, BigDecimal cena, BigDecimal znesek, int width) {
        // "   1,00*  1,00     4,50     4,50"
        String epStr = String.format(Locale.US, "%.2f*", ep.doubleValue());
        String kolStr = String.format(Locale.US, "%.2f", kol);
        String cenaStr = formatCurrency(cena);
        String znStr = formatCurrency(znesek);

        return "   " + padRight(epStr, 7) + padRight(kolStr, 6) + padLeft(cenaStr, 8) + padLeft(znStr, 8);
    }

    private static String formatKeyValue(String key, String value, int width) {
        int space = width - key.length() - value.length();
        if (space < 1) space = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        for (int i = 0; i < space; i++) sb.append(' ');
        sb.append(value);
        return sb.toString();
    }

    private static String formatTaxHeader(int width) {
        // "Stopnja  Osnova   DDV     Znesek"
        return padRight("Stopnja", 8) + padLeft("Osnova", 8) + padLeft("DDV", 6) + padLeft("Znesek", 10);
    }

    private static String formatTaxRow(double stopnja, BigDecimal osnova, BigDecimal ddv, BigDecimal znesek, int width) {
        String sStr = String.format(Locale.US, "%.2f", stopnja).replace('.', ',');
        String oStr = formatCurrency(osnova);
        String dStr = formatCurrency(ddv);
        String zStr = formatCurrency(znesek);

        return padLeft(sStr, 6) + "  " + padLeft(oStr, 8) + padLeft(dStr, 7) + padLeft(zStr, 9);
    }

    private static String formatCurrency(BigDecimal bd) {
        if (bd == null) return "0,00";
        return String.format(Locale.US, "%.2f", bd.doubleValue()).replace('.', ',');
    }

    private static String makeDashes(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append('-');
        return sb.toString();
    }

    private static String makeUnderscores(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append('_');
        return sb.toString();
    }

    private static String padRight(String s, int n) {
        if (s == null) s = "";
        if (s.length() >= n) return s;
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < n) sb.append(' ');
        return sb.toString();
    }

    private static String padLeft(String s, int n) {
        if (s == null) s = "";
        if (s.length() >= n) return s;
        StringBuilder sb = new StringBuilder();
        while (sb.length() < n - s.length()) sb.append(' ');
        sb.append(s);
        return sb.toString();
    }
}
