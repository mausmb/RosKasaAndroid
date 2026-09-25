package si.ros.RosKasa;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import si.ros.RosKasa.models.MobileSetupTp;
import si.ros.RosKasa.ui.CenikListAdapter;
import si.ros.RosKasa.models.HitraTipkaTp;
import si.ros.RosKasa.models.RacunSeznamItem;
import si.ros.RosKasa.models.RacunTp;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.NacPlacTp;
import si.ros.RosKasa.models.PlaciloTp;
import si.ros.RosKasa.models.CenikVrVrTp;
import si.ros.RosKasa.models.DodatekTp;
import si.ros.RosKasa.models.MizaTp;
import si.ros.RosKasa.models.OsebaTp;
import si.ros.RosKasa.models.PrioritetaProjektaTp;
import si.ros.RosKasa.models.TarifaTp;

public class Globals {
    private static final String TAG = "Globals";
    private static volatile Globals instance;

    // Cache za statične šifrante (cenik in hitre tipke)
    private final List<CenikListAdapter.CenikItem> cachedCenik = new ArrayList<>();
    private final List<HitraTipkaTp> cachedHitreTipke = new ArrayList<>();
    private final List<CenikVrVrTp> cachedCenikVrVr = new ArrayList<>();
    private final List<DodatekTp> cachedDodatki = new ArrayList<>();
    private final Map<Integer, String> nivo4NazivLookup = new java.util.concurrent.ConcurrentHashMap<>();

    // Šifranti iz MobileSetup
    private final List<OsebaTp> cachedOsebje = new ArrayList<>();
    private final List<PrioritetaProjektaTp> cachedPrioritete = new ArrayList<>();
    private final List<TarifaTp> cachedTarife = new ArrayList<>();
    private final List<MizaTp> cachedMize = new ArrayList<>();
    private final List<Integer> cachedRajoni = new ArrayList<>();

    // Trenutno prijavljena oseba (natakar)
    private OsebaTp tekocaOseba;
    private int tekocaOsebaId = 0;
    private String tekocaOsebaNaziv = "";
    private int vlogaOsebe = 1;
    private int maxPopOseba = 0;

    // Payment methods cache (getNacPlac2) in seznam dovoljenih plačilnih sredstev (MOBILE_SETUP_PLACILA)
    private final List<NacPlacTp> cachedPlacila = new ArrayList<>();
    private final List<Integer> placilnaSredstva = new ArrayList<>();

    // Device & Session
    private int mobileId = 1;
    private String serverUrl = "";
    private String token = "";
    private String fault = "";
    private String casGetAppConfig = "";
    private String napakaZagona = "";

    // Active Bill / State
    private int activeRacunId = 0;
    private RacunTp currentRacun;
    private static final java.util.concurrent.atomic.AtomicInteger localNegativeRacunCounter = new java.util.concurrent.atomic.AtomicInteger(-1);

    public static int getNextNegativeRacunId() {
        return localNegativeRacunCounter.getAndDecrement();
    }

    // NFC Settings
    private boolean pNfc = false;
    private boolean pnfcprijava = false;

    // General Setup Parameters
    private Integer fiskalizacija;
    private String davcnaZaFurs = "";
    private boolean pogrinjki = false;
    private boolean barvamTipke = false;
    private String aktivenStyle = "";
    private Integer fPosId;
    private Integer fPoslovniProstorId;
    private String nazivMobile = "";
    private int tipkePosId = 512200;
    private int pomTipkePosId = 512200;
    private Integer tocilnicaId;
    private Integer tekociStrm;
    private Integer kuhinjaId;
    private String printerRacuni = "";
    private String nazivPodjetja = "";
    private String naslovPodjetja = "";
    private String ddvStevilka = "";
    private String nazivProdajnegaMesta = "";
    private String nazivObratPE = "";
    private String naslovProdajnega = "";

    // Discounts & Payments
    private boolean pPopustIzpisPozicije = true;
    private boolean pLojalnostPopust = false;
    private boolean pPopust99 = true;
    private boolean kreditnaKarticaPlacilo = false;
    private String nazivStregelVasJe = "";
    private String nazivZahvala1 = "";
    private String nazivZahvala2 = "";
    private String nazivZahvala3 = "";
    private String nazivZahvala4 = "";
    private int hisDestinacija = 0;
    private int hisObrat = 512200;

    // Printer Settings & ESC Codes
    private int printerSteviloZnakov = 42;
    private String escAlignCenter = "";
    private String escAlignLeft = "";
    private String escAlignRight = "";
    private String escBoldOff = "";
    private String escBoldOn = "";
    private String escCpi16 = "";
    private String escCpi20 = "";
    private String escCut = "";
    private String escEol = "";
    private String escInitPrint = "";
    private String escInverseOff = "";
    private String escInverseOn = "";
    private String escNewLine = "";
    private String escReset = "";
    private String escUnderlineOff = "";
    private String escUnderlineOn = "";
    private String escWidth2xOff = "";
    private String escWidth2xOn = "";
    private String escPredal = "";

    // Complete MOBINI Parameters (All 240 variables from Globals.pas MobIniRead)
    private boolean upostevamZgorajSpodaj = false;
    private boolean cenikLokalno = false;
    private int cenikStDniObnova = 3;
    private boolean bazenProdajaSkPaket = false;
    private boolean tiskanjeInicialke = false;
    private int davkiFiskalMetoda = 0;
    private boolean rajoni = false;
    private int rajonDefault = 0;
    private boolean printAlignLeft = false;
    private boolean placilaEnabled = true;
    private int zamudaMinute = 0;
    private int valutId = 0;
    private boolean kronologIzklop = false;
    private boolean debugL1 = false;
    private boolean debugL2 = false;
    private boolean debugL3 = false;
    private boolean wsReconnect = false;
    private boolean obroki = false;
    private boolean obracunBrezIzpisa = false;
    private boolean printOff = false;
    private boolean racuniSoLahkoFakture = false;
    private int stdNiValuta = 0;
    private String klavzulaFaktura = "";
    private String klavzulaFakturaDavek0 = "";
    private String placilaFaktura = "";
    private String reportFormat = "";
    private boolean reprezentancaPoFirmah = false;
    private boolean lokatorji = false;
    private boolean barcodeProdaja = false;
    private boolean barcodeNivo4 = false;
    private boolean printamSlipNaRacun = true;
    private boolean skrijGumbiZklop = false;
    private boolean akcije = false;
    private boolean inkasoAnalitika = false;
    private boolean autoPrintRacun = false;
    private boolean pLogout = false;
    private boolean pLogoutPoIzpisu = false;
    private boolean pLogoutPoNarocilu = false;
    private boolean pOdjavaDialog = false;
    private boolean pinLogin = true;
    private int logoutCas = 0;
    private boolean printamNarocila = false;
    private boolean printBlokInNarocilo = false;
    private boolean printamNarocilaNikamor = false;
    private boolean nePrintamNarT1 = false;
    private boolean nePrintamNarT2 = false;
    private boolean nePrintamNarT3 = false;
    private boolean nePrintamNarT4 = false;
    private int printBlokPavza = 0;
    private boolean printBrezTiskalnika = false;
    private boolean printamVoucher = false;
    private int nivo4IdVoucher = 0;
    private int napitnineProcentKuh = 0;
    private boolean napitnine = false;
    private boolean vraciloNum = false;
    private boolean croFisk = false;
    private boolean croNapitnine = false;
    private boolean sortPlacila = false;
    private String dpoVr1 = "";
    private String dpoVr2 = "";
    private String dpoVr3 = "";
    private String dpoVr4 = "";
    private String dpoVr5 = "";
    private String dpoVr6 = "";
    private boolean pCheckPinPotekel = false;
    private boolean niStornoIzpisanegaNarocila = false;
    private boolean overrideStornoPoslanoNarocilo = false;
    private boolean stornoRazlogVr = false;
    private boolean stornoOsebaStorniral = false;
    private boolean stornoPosVirtual = false;
    private boolean stornoPosOverride = false;
    private boolean novaVrsticaNarocila = false;
    private boolean editKolicinaAdd = false;
    private boolean pluAktiven = false;
    private boolean podatkiNaNapravi = false;
    private boolean izpisOpombaRacun = false;
    private boolean zamenjavaMarkerMize = false;
    private boolean opombeTabelaOff = false;
    private boolean zamenjavaMarkerEdit = false;
    private boolean vnosCeneZaVseIni = true;
    private boolean vnosCeneZaVse = true;
    private boolean hodNarocila = false;
    private boolean printamStornoNarocila = false;
    private boolean mizeInRacuni = false;
    private boolean printNarocilaLokalno = false;
    private boolean lestvicaEpHt = false;
    private boolean lestvicaEp = false;
    private boolean dnCenik = false;
    private boolean cena0Cenik = false;
    private boolean cena0Dovoljena = false;
    private boolean popust99Proporcialno = false;
    private int placiloHk = 0;
    private int bonNivo4Id = 0;
    private double procentNivelacije = 0.0;
    private int modelCena2 = 0;
    private boolean cena2Vikend = false;
    private boolean cena2PreklopOff = false;
    private boolean odpriVseRacune = false;
    private boolean razlogStorno = false;
    private boolean crmAktiven = false;
    private boolean skupineNaSobo = false;
    private int stKopijSlip = 0;
    private boolean kkRocno = false;
    private int kredKarticaKupecId = 0;
    private int kredKarticaKupecIdDiners = 0;
    private int kKarticaKupecIdRocno = 0;
    private int kKarticaTippartnerRocno = 4;
    private int hotkey3 = 0;
    private int hotkey4 = 0;
    private int hotkey5 = 0;
    private boolean tipkaGotovina = false;
    private int praznikiObrat = 0;
    private boolean prazniki = false;
    private boolean tiskamText = false;
    private boolean izpisRacunaParalel = false;
    private boolean threadIzpis = false;
    private boolean threadNarocilo = false;
    private boolean threadOdprtiIzpisani = false;
    private boolean threadVse = false;
    private boolean threadStorno = false;
    private boolean threadT = false;
    private boolean obracunDovoljen = false;
    private double turBoniMinZnesek = 0.0;
    private boolean bazenCenaSerijskeKarteCenik = false;
    private boolean cena2Aktivna = false;
    private boolean errLog = false;
    private boolean partnerFurs = false;
    private boolean petekWend = false;
    private boolean turBoniAktivni = false;
    private boolean tbDonatorOn = false;
    private boolean boldNazivPodjetja = false;
    private boolean fiskalnoEnako = true;
    private String turBoniNivo1Ok = "";
    private List<Integer> turBoniNivo1Dovoljeni = new ArrayList<>();
    private String langApp = "";
    private boolean hotelKredit0 = false;
    private boolean boniVkMd5 = false;
    private int boniVkDolzina = 0;
    private int boniIdDolzina = 0;
    private String placilaNizPogoj = "";
    private int maxIzpisovRacuna = 0;
    private int intervalIzpisani = 0;
    private String cenaPolnjenje = "";
    private String hisObrati = "";
    private int izpisaniVidniDo = 0;
    private int narociloStornoDo = 0;
    private String placilaPodpis = "";
    private List<Integer> placilaPodpisId = new ArrayList<>();
    private boolean brezVirtualKeyboard = false;
    private boolean windowsDoubleClick = false;
    private boolean ekran1280 = false;
    private boolean posiceBrezSlip = false;
    private boolean posicePrint = false;
    private boolean stornoPosice = false;
    private boolean icePos = false;
    private boolean ecrPay = false;
    private boolean ecrPay262 = false;
    private boolean ecrPrint = false;
    private boolean ecrStorno = false;
    private boolean ecrDebug = false;
    private boolean ecrZaba = false;
    private boolean ecrRefund = false;
    private String ecrIp = "";
    private int ecrRrnMesto = 0;
    private String ecrPort = "";
    private boolean sixPay = false;
    private boolean sixCdll = false;
    private boolean sixNapitnine = false;
    private String sixCdllPath = "";
    private String sixCdllPathInit = "";
    private boolean sixPayStorno = false;
    private boolean sixPayThread = false;
    private boolean sixPayReset = false;
    private boolean six2Active = false;
    private boolean sixPayPrint = false;
    private String sixTerminalIdIp = "";
    private String sixPosId = "";
    private int sixUserId = 0;
    private boolean eDenar = false;
    private boolean webNarocila = false;
    private String brisiPlacilaZaWindows = "";
    private boolean bazeni = false;
    private boolean metraAktivna = true;
    private boolean metraQrKoda = false;
    private boolean metraCitalec = false;
    private String metraWbcCode = "";
    private String metraAreaCode = "";
    private String metraUrl = "";
    private String metraPos = "";
    private String metraDevice = "";
    private String metraIssueCount = "";
    private String metraUserData = "";
    private String metraTimeout = "";
    private String metraUser = "";
    private boolean metraHexToDec = false;
    private boolean metraStornoKontrola = false;
    private boolean metraLog = false;
    private boolean pHidPrijavaIni = false;
    private boolean pHidPrijava = false;
    private boolean pHidPrijavaIniFs = false;
    private boolean winSpoolPrint = false;
    private boolean blueToothPrint = true;
    private boolean tenzorGat = false;
    private String tenzorIp = "";
    private String tenzorMid = "MIF";
    private String tenzorPort = "";
    private String tenzorZamudnina = "";
    private boolean tenzorLog = false;
    private int tipQrKode = 0;
    private int kbpsLimit = 0;
    private boolean androidNet2x = false;
    private String androidNetTcp = "8.8.8.8";
    private int timerEnableEkran = 10000;
    private int timeOutConnect = 10000;
    private int timeOutSend = 20000;
    private int timeOutReceive = 30000;
    private int androidNetTcpPort = 53;
    private int htFontAndroid = 0;
    private boolean tiskanjePavza = false;
    private boolean obracunAndroid = false;
    private boolean payTenA = false;
    private String payTenARosPackage = "";
    private String payTenAActivityMain = "";
    private String payTenAActivities = "";
    private String payTenAPin = "";
    private boolean payTenAStorno = false;

    // SIX / TapOn Payment Terminal Toggles
    private boolean sixTap = false;
    private boolean sixTapManualLast = false;
    private boolean sixTapAutoLast = false;
    private boolean sixTapPrint = false;
    private boolean sixTapStorno = false;
    private boolean sixTapDebug = false;
    private boolean sixTapStornoZadnji = false;
    private String sixTapWpiVersion = "";
    private String sixTapFormat = "";
    private boolean recoverTapOn = false;
    private boolean tapOnRecoverIntent = false;
    private boolean tapOnRecoverLogout = false;
    private boolean napitninaPos = true;
    private boolean napitninaRos = false;
    private String pPrinterBtOptiPos = "";
    private boolean forceTabletScreen = false;
    private String brisiPlacilaZaAndroid = "";
    private int korekcijaGumbMize = 0;

    private final Map<String, String> mobIniValues = new HashMap<>();

    private Globals() {}

    public static Globals getInstance() {
        if (instance == null) {
            synchronized (Globals.class) {
                if (instance == null) {
                    instance = new Globals();
                }
            }
        }
        return instance;
    }

    /**
     * Converts ROS ESC string like "27-33-8" into control characters
     */
    public static String pretvoriROSESC(String vhodnistring) {
        if (vhodnistring == null || vhodnistring.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String[] parts = vhodnistring.split("-");
        for (String part : parts) {
            String p = part.trim();
            if (!p.isEmpty()) {
                try {
                    int code = Integer.parseInt(p);
                    sb.append((char) code);
                } catch (NumberFormatException ignored) {}
            }
        }
        return sb.toString();
    }

    /**
     * Vpisi operacijo ali napako v kronologijo
     */
    public void vpisiKronologijo(String opisOperacije) {
        if (this.kronologIzklop) return;
        si.ros.RosKasa.soap.RosKasaSoapClient.vpisKronologijeAsync(
                this.serverUrl != null ? this.serverUrl : "",
                this.token != null ? this.token : "",
                String.valueOf(this.mobileId),
                opisOperacije,
                9999,
                this.hisObrat != 0 ? this.hisObrat : 512200
        );
    }

    /**
     * Loads startup parameters from MobileSetupTp into Globals instance
     */
    public synchronized void loadFromMobileSetup(MobileSetupTp setup, int initialMobileId) {
        if (setup == null) return;

        // Rule: if MOBILE_ID = 0 then MOBILE_ID := 1
        this.mobileId = (initialMobileId == 0) ? 1 : initialMobileId;
        if (setup.getMobileId() != 0) {
            this.mobileId = setup.getMobileId();
        }

        if (setup.getMobileSetupPlacila() != null && !setup.getMobileSetupPlacila().isEmpty()) {
            this.placilnaSredstva.clear();
            this.placilnaSredstva.addAll(setup.getMobileSetupPlacila());
        }

        if (setup.getOsebe() != null && !setup.getOsebe().isEmpty()) {
            this.cachedOsebje.clear();
            this.cachedOsebje.addAll(setup.getOsebe());
        }

        if (setup.getPrioriteteProjektov() != null && !setup.getPrioriteteProjektov().isEmpty()) {
            this.cachedPrioritete.clear();
            this.cachedPrioritete.addAll(setup.getPrioriteteProjektov());
        }

        if (setup.getTarife() != null && !setup.getTarife().isEmpty()) {
            this.cachedTarife.clear();
            this.cachedTarife.addAll(setup.getTarife());
        }

        if (setup.getMobileSetupMize() != null && !setup.getMobileSetupMize().isEmpty()) {
            this.cachedMize.clear();
            this.cachedMize.addAll(setup.getMobileSetupMize());
            this.cachedRajoni.clear();
            this.cachedRajoni.addAll(setup.getRajoniList());
        }

        // Sequential reading of MOBINI: MOBINI0 -> MOBINI -> MOBINI2
        if (setup.getMobIni0() != null && !setup.getMobIni0().isEmpty()) {
            mobIniRead(setup.getMobIni0());
        }

        if (setup.getMobIni() != null && !setup.getMobIni().isEmpty()) {
            mobIniRead(setup.getMobIni());
        }

        if (setup.getMobIni2() != null && !setup.getMobIni2().isEmpty()) {
            mobIniRead(setup.getMobIni2());
        }

        // Check TIPKE_POS_ID bounds rule
        if (setup.getTipkePosId() != 0) {
            this.tipkePosId = setup.getTipkePosId();
        }
        if (this.tipkePosId < 0 || this.tipkePosId > 1000000) {
            this.tipkePosId = this.pomTipkePosId;
        }

        // NFC setup
        if (setup.getNfcPrijava() != null) {
            if ("D".equalsIgnoreCase(setup.getNfcPrijava())) {
                this.pNfc = true;
                this.pnfcprijava = true;
            } else {
                this.pNfc = false;
                this.pnfcprijava = false;
            }
        }

        // General settings
        if (setup.getFiskalizacija() != null) this.fiskalizacija = setup.getFiskalizacija();
        if (setup.getDavcnaZaFurs() != null) this.davcnaZaFurs = setup.getDavcnaZaFurs();
        if ("D".equalsIgnoreCase(setup.getVnosPogrinjkov())) this.pogrinjki = true;
        if ("D".equalsIgnoreCase(setup.getHtColor())) this.barvamTipke = true;
        if (setup.getHtStyleName() != null) this.aktivenStyle = setup.getHtStyleName();
        if (setup.getfPosId() != null) this.fPosId = setup.getfPosId();
        if (setup.getfPoslovniProstorId() != null) this.fPoslovniProstorId = setup.getfPoslovniProstorId();
        if (setup.getNaziv() != null) this.nazivMobile = setup.getNaziv();
        if (setup.getTocilnicaId() != null) {
            this.tocilnicaId = setup.getTocilnicaId();
            this.tekociStrm = setup.getTocilnicaId();
        }
        if (setup.getPrinterRacuni() != null && !setup.getPrinterRacuni().trim().isEmpty()) {
            this.printerRacuni = setup.getPrinterRacuni().trim();
        }
        if (setup.getNazivPodjetja() != null) this.nazivPodjetja = setup.getNazivPodjetja();
        if (setup.getNaslovPodjetja() != null) this.naslovPodjetja = setup.getNaslovPodjetja();
        if (setup.getDdvPodjetja() != null) this.ddvStevilka = setup.getDdvPodjetja();
        if (setup.getNazivProdajnegaMesta() != null) this.nazivProdajnegaMesta = setup.getNazivProdajnegaMesta();
        if (setup.getObratProdajnegaMesta() != null) this.nazivObratPE = setup.getObratProdajnegaMesta();
        if (setup.getNaslovProdajnega() != null) this.naslovProdajnega = setup.getNaslovProdajnega();

        // Discounts
        this.pPopustIzpisPozicije = !"N".equalsIgnoreCase(setup.getPopustIzpis());
        this.pLojalnostPopust = "D".equalsIgnoreCase(setup.getPopustLojalnost());
        this.pPopust99 = !"N".equalsIgnoreCase(setup.getPopust99());
        this.kreditnaKarticaPlacilo = "D".equalsIgnoreCase(setup.getkKarticeVPlacilih());

        if (setup.getNazivStregelVasJe() != null) this.nazivStregelVasJe = setup.getNazivStregelVasJe();
        if (setup.getNazivZahvala1() != null) this.nazivZahvala1 = setup.getNazivZahvala1();
        if (setup.getNazivZahvala2() != null) this.nazivZahvala2 = setup.getNazivZahvala2();
        if (setup.getNazivZahvala3() != null) this.nazivZahvala3 = setup.getNazivZahvala3();
        if (setup.getNazivZahvala4() != null) this.nazivZahvala4 = setup.getNazivZahvala4();

        if (setup.getHisDestinacija() != 0) this.hisDestinacija = setup.getHisDestinacija();
        if (setup.getHisObrat() != 0) this.hisObrat = setup.getHisObrat();
        if (setup.getKuhinjaId() != null) this.kuhinjaId = setup.getKuhinjaId();

        // Printer ESC Codes
        if (setup.getSteviloZnakov() != null && !setup.getSteviloZnakov().isEmpty()) {
            try {
                this.printerSteviloZnakov = Integer.parseInt(setup.getSteviloZnakov());
            } catch (Exception ignored) {}
        }
        // Rule: if printerSteviloZnakov = 48 -> set to 42
        if (this.printerSteviloZnakov == 48) {
            this.printerSteviloZnakov = 42;
        }

        if (setup.getEscAlignCenter() != null) this.escAlignCenter = pretvoriROSESC(setup.getEscAlignCenter());
        if (setup.getEscWidth2xOff() != null) this.escWidth2xOff = pretvoriROSESC(setup.getEscWidth2xOff());
        if (setup.getEscWidth2xOn() != null) this.escWidth2xOn = pretvoriROSESC(setup.getEscWidth2xOn());
        if (setup.getEscAlignLeft() != null) this.escAlignLeft = pretvoriROSESC(setup.getEscAlignLeft());
        if (setup.getEscAlignRight() != null) this.escAlignRight = pretvoriROSESC(setup.getEscAlignRight());
        if (setup.getEscBoldOff() != null) this.escBoldOff = pretvoriROSESC(setup.getEscBoldOff());
        if (setup.getEscBoldOn() != null) this.escBoldOn = pretvoriROSESC(setup.getEscBoldOn());
        if (setup.getEscCpi16() != null) this.escCpi16 = pretvoriROSESC(setup.getEscCpi16());
        if (setup.getEscCpi20() != null) this.escCpi20 = pretvoriROSESC(setup.getEscCpi20());
        if (setup.getEscCut() != null) this.escCut = pretvoriROSESC(setup.getEscCut());
        if (setup.getEscEol() != null) this.escEol = pretvoriROSESC(setup.getEscEol());
        if (setup.getEscInitPrint() != null) this.escInitPrint = pretvoriROSESC(setup.getEscInitPrint());
        if (setup.getEscInverseOff() != null) this.escInverseOff = pretvoriROSESC(setup.getEscInverseOff());
        if (setup.getEscInverseOn() != null) this.escInverseOn = pretvoriROSESC(setup.getEscInverseOn());
        if (setup.getEscNewLine() != null) this.escNewLine = pretvoriROSESC(setup.getEscNewLine());
        if (setup.getEscReset() != null) this.escReset = pretvoriROSESC(setup.getEscReset());
        if (setup.getEscUnderlineOff() != null) this.escUnderlineOff = pretvoriROSESC(setup.getEscUnderlineOff());
        if (setup.getEscUnderlineOn() != null) this.escUnderlineOn = pretvoriROSESC(setup.getEscUnderlineOn());
    }

    /**
     * Parses INI or JSON (objects or arrays of objects with naziv/vrednost) MOBINI string into Globals
     */
    public synchronized void mobIniRead(String rawIni) {
        if (rawIni == null || rawIni.trim().isEmpty()) return;

        Map<String, String> kvPairs = new HashMap<>();

        String trimmed = rawIni.trim();
        if (trimmed.startsWith("[")) {
            try {
                JSONArray arr = new JSONArray(trimmed);
                parseJsonArrayIntoKvPairs(arr, kvPairs);
            } catch (Exception e) {
                Log.w(TAG, "MOBINI JSONArray parse warning: " + e.getMessage());
            }
        } else if (trimmed.startsWith("{")) {
            try {
                JSONObject json = new JSONObject(trimmed);
                parseJsonObjectIntoKvPairs(json, kvPairs);
            } catch (Exception e) {
                Log.w(TAG, "MOBINI JSONObject parse warning: " + e.getMessage());
            }
        }

        // Complement with INI line parsing (e.g. KEY=VALUE)
        String[] lines = rawIni.split("\r?\n");
        for (String line : lines) {
            String l = line.trim();
            if (l.isEmpty() || l.startsWith(";") || l.startsWith("#") || l.startsWith("[")) continue;
            int idx = l.indexOf('=');
            if (idx > 0) {
                String k = l.substring(0, idx).trim().toUpperCase();
                String v = l.substring(idx + 1).trim();
                kvPairs.put(k, v);
            }
        }

        // Store into mobIniValues map
        mobIniValues.putAll(kvPairs);

        // Map parsed KV pairs to typed Globals fields (All 240 fields from Globals.pas)
        if (kvPairs.containsKey("UPOSTEVAMZGORAJSPODAJ")) this.upostevamZgorajSpodaj = "D".equalsIgnoreCase(kvPairs.get("UPOSTEVAMZGORAJSPODAJ"));
        if (kvPairs.containsKey("CENIKLOKALNO")) this.cenikLokalno = "D".equalsIgnoreCase(kvPairs.get("CENIKLOKALNO"));
        if (kvPairs.containsKey("CENIKSTDNIOBNOVA")) { try { this.cenikStDniObnova = Integer.parseInt(kvPairs.get("CENIKSTDNIOBNOVA")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("POMTIPKE_POS_ID")) { try { this.pomTipkePosId = Integer.parseInt(kvPairs.get("POMTIPKE_POS_ID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("BAZENPRODAJASKPAKET")) this.bazenProdajaSkPaket = "D".equalsIgnoreCase(kvPairs.get("BAZENPRODAJASKPAKET"));
        if (kvPairs.containsKey("TISKANJEINICIALKE")) this.tiskanjeInicialke = "D".equalsIgnoreCase(kvPairs.get("TISKANJEINICIALKE"));
        if (kvPairs.containsKey("DAVKIFISKALMETODA")) { try { this.davkiFiskalMetoda = Integer.parseInt(kvPairs.get("DAVKIFISKALMETODA")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("RAJONI")) this.rajoni = "D".equalsIgnoreCase(kvPairs.get("RAJONI"));
        if (kvPairs.containsKey("RAJONDEFAULT")) { try { this.rajonDefault = Integer.parseInt(kvPairs.get("RAJONDEFAULT")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PRINTALIGNLEFT")) this.printAlignLeft = "D".equalsIgnoreCase(kvPairs.get("PRINTALIGNLEFT"));
        if (kvPairs.containsKey("PLACILAENABLED")) this.placilaEnabled = "D".equalsIgnoreCase(kvPairs.get("PLACILAENABLED"));
        if (kvPairs.containsKey("ZAMUDAMINUTE")) { try { this.zamudaMinute = Integer.parseInt(kvPairs.get("ZAMUDAMINUTE")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("VALUTID")) { try { this.valutId = Integer.parseInt(kvPairs.get("VALUTID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("KRONOLOGIZKLOP")) this.kronologIzklop = "D".equalsIgnoreCase(kvPairs.get("KRONOLOGIZKLOP"));
        if (kvPairs.containsKey("DEBUGL1")) this.debugL1 = "D".equalsIgnoreCase(kvPairs.get("DEBUGL1"));
        if (kvPairs.containsKey("DEBUGL2")) this.debugL2 = "D".equalsIgnoreCase(kvPairs.get("DEBUGL2"));
        if (kvPairs.containsKey("DEBUGL3")) this.debugL3 = "D".equalsIgnoreCase(kvPairs.get("DEBUGL3"));
        if (kvPairs.containsKey("WSRECONNECT")) this.wsReconnect = "D".equalsIgnoreCase(kvPairs.get("WSRECONNECT"));
        if (kvPairs.containsKey("OBROKI")) this.obroki = "D".equalsIgnoreCase(kvPairs.get("OBROKI"));
        if (kvPairs.containsKey("OBRACUNBREZIZPISA")) this.obracunBrezIzpisa = "D".equalsIgnoreCase(kvPairs.get("OBRACUNBREZIZPISA"));
        if (kvPairs.containsKey("PRINTOFF")) this.printOff = "D".equalsIgnoreCase(kvPairs.get("PRINTOFF"));
        if (kvPairs.containsKey("RACUNISOLAHKOFAKTURE")) this.racuniSoLahkoFakture = "D".equalsIgnoreCase(kvPairs.get("RACUNISOLAHKOFAKTURE"));
        if (kvPairs.containsKey("STDNIVALUTA")) { try { this.stdNiValuta = Integer.parseInt(kvPairs.get("STDNIVALUTA")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("KLAVZULAFAKTURA")) this.klavzulaFaktura = kvPairs.get("KLAVZULAFAKTURA");
        if (kvPairs.containsKey("KLAVZULAFAKTURADAVEK0")) this.klavzulaFakturaDavek0 = kvPairs.get("KLAVZULAFAKTURADAVEK0");
        if (kvPairs.containsKey("PLACILAFAKTURA")) this.placilaFaktura = kvPairs.get("PLACILAFAKTURA");
        if (kvPairs.containsKey("REPORTFORMAT")) { String rf = kvPairs.get("REPORTFORMAT"); this.reportFormat = (rf != null) ? rf.toLowerCase() : ""; }
        if (kvPairs.containsKey("REPREZENTANCAPOFIRMAH")) this.reprezentancaPoFirmah = "D".equalsIgnoreCase(kvPairs.get("REPREZENTANCAPOFIRMAH"));
        if (kvPairs.containsKey("LOKATORJI")) this.lokatorji = "D".equalsIgnoreCase(kvPairs.get("LOKATORJI"));
        if (kvPairs.containsKey("BARCODEPRODAJA")) this.barcodeProdaja = "D".equalsIgnoreCase(kvPairs.get("BARCODEPRODAJA"));
        if (kvPairs.containsKey("BARCODENIVO4")) this.barcodeNivo4 = "D".equalsIgnoreCase(kvPairs.get("BARCODENIVO4"));
        if (kvPairs.containsKey("PRINTAMSLIPNARACUN")) this.printamSlipNaRacun = "D".equalsIgnoreCase(kvPairs.get("PRINTAMSLIPNARACUN"));
        if (kvPairs.containsKey("SKRIJGUMBIZKLOP")) this.skrijGumbiZklop = "D".equalsIgnoreCase(kvPairs.get("SKRIJGUMBIZKLOP"));
        if (kvPairs.containsKey("AKCIJE")) this.akcije = "D".equalsIgnoreCase(kvPairs.get("AKCIJE"));
        if (kvPairs.containsKey("INKASOANALITIKA")) this.inkasoAnalitika = "D".equalsIgnoreCase(kvPairs.get("INKASOANALITIKA"));
        if (kvPairs.containsKey("AVTOMATSKIIZPISRACUNA")) this.autoPrintRacun = "D".equalsIgnoreCase(kvPairs.get("AVTOMATSKIIZPISRACUNA"));
        if (kvPairs.containsKey("ODJAVA")) this.pLogout = "D".equalsIgnoreCase(kvPairs.get("ODJAVA"));
        if (kvPairs.containsKey("ODJAVAPOIZPISU")) this.pLogoutPoIzpisu = "D".equalsIgnoreCase(kvPairs.get("ODJAVAPOIZPISU"));
        if (kvPairs.containsKey("ODJAVAPONAROCILU")) this.pLogoutPoNarocilu = "D".equalsIgnoreCase(kvPairs.get("ODJAVAPONAROCILU"));
        if (kvPairs.containsKey("ODJAVADIALOG")) this.pOdjavaDialog = "D".equalsIgnoreCase(kvPairs.get("ODJAVADIALOG"));
        if (kvPairs.containsKey("PINLOGIN")) this.pinLogin = "D".equalsIgnoreCase(kvPairs.get("PINLOGIN"));
        if (kvPairs.containsKey("LOGOUTCAS")) { try { int sec = Integer.parseInt(kvPairs.get("LOGOUTCAS")); this.logoutCas = (sec != 0) ? sec * 6000 : 0; } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PRINTBLOK")) this.printamNarocila = "D".equalsIgnoreCase(kvPairs.get("PRINTBLOK"));
        if (kvPairs.containsKey("PRINTBLOKINNAROCILO")) this.printBlokInNarocilo = "D".equalsIgnoreCase(kvPairs.get("PRINTBLOKINNAROCILO"));
        if (kvPairs.containsKey("PRINTBLOKNIKAMOR")) this.printamNarocilaNikamor = "D".equalsIgnoreCase(kvPairs.get("PRINTBLOKNIKAMOR"));
        if (kvPairs.containsKey("NEPRINTAMNART1")) this.nePrintamNarT1 = "D".equalsIgnoreCase(kvPairs.get("NEPRINTAMNART1"));
        if (kvPairs.containsKey("NEPRINTAMNART2")) this.nePrintamNarT2 = "D".equalsIgnoreCase(kvPairs.get("NEPRINTAMNART2"));
        if (kvPairs.containsKey("NEPRINTAMNART3")) this.nePrintamNarT3 = "D".equalsIgnoreCase(kvPairs.get("NEPRINTAMNART3"));
        if (kvPairs.containsKey("NEPRINTAMNART4")) this.nePrintamNarT4 = "D".equalsIgnoreCase(kvPairs.get("NEPRINTAMNART4"));
        if (kvPairs.containsKey("PRINTBLOKPAVZA")) { try { this.printBlokPavza = Integer.parseInt(kvPairs.get("PRINTBLOKPAVZA")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PRINTBREZTISKALNIKA")) this.printBrezTiskalnika = "D".equalsIgnoreCase(kvPairs.get("PRINTBREZTISKALNIKA"));
        if (kvPairs.containsKey("ESCPREDAL")) this.escPredal = pretvoriROSESC(kvPairs.get("ESCPREDAL"));
        if (kvPairs.containsKey("PRINTVOUCHER")) this.printamVoucher = "D".equalsIgnoreCase(kvPairs.get("PRINTVOUCHER"));
        if (kvPairs.containsKey("NIVO4IDVOUCHER")) { try { this.nivo4IdVoucher = Integer.parseInt(kvPairs.get("NIVO4IDVOUCHER")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("NAPITNINEPROCENTKUH")) { try { this.napitnineProcentKuh = Integer.parseInt(kvPairs.get("NAPITNINEPROCENTKUH")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("NAPITNINE")) this.napitnine = "D".equalsIgnoreCase(kvPairs.get("NAPITNINE"));
        if (kvPairs.containsKey("VRACILONUM")) this.vraciloNum = "D".equalsIgnoreCase(kvPairs.get("VRACILONUM"));
        if (kvPairs.containsKey("CROFISK")) this.croFisk = "D".equalsIgnoreCase(kvPairs.get("CROFISK"));
        if (kvPairs.containsKey("CRONAPITNINE")) this.croNapitnine = "D".equalsIgnoreCase(kvPairs.get("CRONAPITNINE"));
        if (kvPairs.containsKey("SORTPLACILA")) this.sortPlacila = "D".equalsIgnoreCase(kvPairs.get("SORTPLACILA"));
        if (kvPairs.containsKey("DPO_VR1")) this.dpoVr1 = kvPairs.get("DPO_VR1");
        if (kvPairs.containsKey("DPO_VR2")) this.dpoVr2 = kvPairs.get("DPO_VR2");
        if (kvPairs.containsKey("DPO_VR3")) this.dpoVr3 = kvPairs.get("DPO_VR3");
        if (kvPairs.containsKey("DPO_VR4")) this.dpoVr4 = kvPairs.get("DPO_VR4");
        if (kvPairs.containsKey("DPO_VR5")) this.dpoVr5 = kvPairs.get("DPO_VR5");
        if (kvPairs.containsKey("DPO_VR6")) this.dpoVr6 = kvPairs.get("DPO_VR6");
        if (kvPairs.containsKey("CHECKPINPOTEKEL")) this.pCheckPinPotekel = "D".equalsIgnoreCase(kvPairs.get("CHECKPINPOTEKEL"));
        if (kvPairs.containsKey("NISTORNONAROCILA")) this.niStornoIzpisanegaNarocila = "D".equalsIgnoreCase(kvPairs.get("NISTORNONAROCILA"));
        if (kvPairs.containsKey("OVERRIDESTORNOPOSLAN")) this.overrideStornoPoslanoNarocilo = "D".equalsIgnoreCase(kvPairs.get("OVERRIDESTORNOPOSLAN"));
        if (kvPairs.containsKey("STORNORAZLOGVR")) this.stornoRazlogVr = "D".equalsIgnoreCase(kvPairs.get("STORNORAZLOGVR"));
        if (kvPairs.containsKey("STORNOOSEBASTORNIRAL")) this.stornoOsebaStorniral = "D".equalsIgnoreCase(kvPairs.get("STORNOOSEBASTORNIRAL"));
        if (kvPairs.containsKey("STORNOPOSVIRTUAL")) this.stornoPosVirtual = "D".equalsIgnoreCase(kvPairs.get("STORNOPOSVIRTUAL"));
        if (kvPairs.containsKey("STORNOPOSOVERRIDE")) this.stornoPosOverride = "D".equalsIgnoreCase(kvPairs.get("STORNOPOSOVERRIDE"));
        if (kvPairs.containsKey("NOVAVRSTICANAROCILA")) this.novaVrsticaNarocila = "D".equalsIgnoreCase(kvPairs.get("NOVAVRSTICANAROCILA"));
        if (kvPairs.containsKey("EDITKOLICINAADD")) this.editKolicinaAdd = "D".equalsIgnoreCase(kvPairs.get("EDITKOLICINAADD"));
        if (kvPairs.containsKey("PLUAKTIVEN")) this.pluAktiven = "D".equalsIgnoreCase(kvPairs.get("PLUAKTIVEN"));
        if (kvPairs.containsKey("PODATKINANAPRAVI")) this.podatkiNaNapravi = "D".equalsIgnoreCase(kvPairs.get("PODATKINANAPRAVI"));
        if (kvPairs.containsKey("IZPISOPOMBARACUN")) this.izpisOpombaRacun = "D".equalsIgnoreCase(kvPairs.get("IZPISOPOMBARACUN"));
        if (kvPairs.containsKey("ZAMENJAVAMARKERMIZE")) this.zamenjavaMarkerMize = "D".equalsIgnoreCase(kvPairs.get("ZAMENJAVAMARKERMIZE"));
        if (kvPairs.containsKey("OPOMBETABELAOFF")) this.opombeTabelaOff = "D".equalsIgnoreCase(kvPairs.get("OPOMBETABELAOFF"));
        if (kvPairs.containsKey("ZAMENJAVAMARKEREDIT")) this.zamenjavaMarkerEdit = "D".equalsIgnoreCase(kvPairs.get("ZAMENJAVAMARKEREDIT"));
        if (kvPairs.containsKey("VNOSCENEZAVSE")) {
            String v = kvPairs.get("VNOSCENEZAVSE");
            if ("N".equalsIgnoreCase(v)) {
                this.vnosCeneZaVseIni = false;
                this.vnosCeneZaVse = false;
            }
        }
        if (kvPairs.containsKey("HODNAROCILA")) this.hodNarocila = "D".equalsIgnoreCase(kvPairs.get("HODNAROCILA"));
        if (kvPairs.containsKey("PRINTAMSTORNONAROCILA")) this.printamStornoNarocila = "D".equalsIgnoreCase(kvPairs.get("PRINTAMSTORNONAROCILA"));
        if (kvPairs.containsKey("MIZEINRACUNI")) this.mizeInRacuni = "D".equalsIgnoreCase(kvPairs.get("MIZEINRACUNI"));
        if (kvPairs.containsKey("PRINTNAROCILALOKALNO")) this.printNarocilaLokalno = "D".equalsIgnoreCase(kvPairs.get("PRINTNAROCILALOKALNO"));
        if (kvPairs.containsKey("LESTVICAEPHT")) this.lestvicaEpHt = "D".equalsIgnoreCase(kvPairs.get("LESTVICAEPHT"));
        if (kvPairs.containsKey("LESTVICAEP")) this.lestvicaEp = "D".equalsIgnoreCase(kvPairs.get("LESTVICAEP"));
        if (kvPairs.containsKey("DNCENIK")) this.dnCenik = "D".equalsIgnoreCase(kvPairs.get("DNCENIK"));
        if (kvPairs.containsKey("CENA0CENIK")) this.cena0Cenik = "D".equalsIgnoreCase(kvPairs.get("CENA0CENIK"));
        if (kvPairs.containsKey("CENA0DOVOLJENA")) this.cena0Dovoljena = "D".equalsIgnoreCase(kvPairs.get("CENA0DOVOLJENA"));
        if (kvPairs.containsKey("POPUST99PROPORCIALNO")) this.popust99Proporcialno = "D".equalsIgnoreCase(kvPairs.get("POPUST99PROPORCIALNO"));
        if (kvPairs.containsKey("PLACILOHK")) { try { this.placiloHk = Integer.parseInt(kvPairs.get("PLACILOHK")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("BONNIVO4ID")) { try { this.bonNivo4Id = Integer.parseInt(kvPairs.get("BONNIVO4ID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PROCENTNIVELACIJE")) { try { this.procentNivelacije = Double.parseDouble(kvPairs.get("PROCENTNIVELACIJE")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("MODELCENA2")) { try { this.modelCena2 = Integer.parseInt(kvPairs.get("MODELCENA2")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("CENA2VIKEND")) this.cena2Vikend = "D".equalsIgnoreCase(kvPairs.get("CENA2VIKEND"));
        if (kvPairs.containsKey("CENA2PREKLOPOFF")) this.cena2PreklopOff = "D".equalsIgnoreCase(kvPairs.get("CENA2PREKLOPOFF"));
        if (kvPairs.containsKey("VIDNIVSIRACUNI")) this.odpriVseRacune = "D".equalsIgnoreCase(kvPairs.get("VIDNIVSIRACUNI"));
        if (kvPairs.containsKey("RAZLOGSTORNO")) this.razlogStorno = "D".equalsIgnoreCase(kvPairs.get("RAZLOGSTORNO"));
        if (kvPairs.containsKey("CRMAKTIVEN")) this.crmAktiven = "D".equalsIgnoreCase(kvPairs.get("CRMAKTIVEN"));
        if (kvPairs.containsKey("SKUPINENASOBO")) this.skupineNaSobo = "D".equalsIgnoreCase(kvPairs.get("SKUPINENASOBO"));
        if (kvPairs.containsKey("STKOPIJSLIP")) { try { this.stKopijSlip = Integer.parseInt(kvPairs.get("STKOPIJSLIP")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("KKROCNO")) this.kkRocno = "D".equalsIgnoreCase(kvPairs.get("KKROCNO"));
        if (kvPairs.containsKey("KREDKARTICA_KUPECID")) { try { this.kredKarticaKupecId = Integer.parseInt(kvPairs.get("KREDKARTICA_KUPECID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("KREDKARTICA_KUPECID_DINERS")) { try { this.kredKarticaKupecIdDiners = Integer.parseInt(kvPairs.get("KREDKARTICA_KUPECID_DINERS")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("KKARTICA_KUPECID_ROCNO")) { try { this.kKarticaKupecIdRocno = Integer.parseInt(kvPairs.get("KKARTICA_KUPECID_ROCNO")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("KKARTICATIPPARTNERROCNO")) { try { this.kKarticaTippartnerRocno = Integer.parseInt(kvPairs.get("KKARTICATIPPARTNERROCNO")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("HOTKEY3PLACILOID")) { try { this.hotkey3 = Integer.parseInt(kvPairs.get("HOTKEY3PLACILOID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("HOTKEY4PLACILOID")) { try { this.hotkey4 = Integer.parseInt(kvPairs.get("HOTKEY4PLACILOID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("HOTKEY5PLACILOID")) { try { this.hotkey5 = Integer.parseInt(kvPairs.get("HOTKEY5PLACILOID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("TIPKAGOTOVINA")) this.tipkaGotovina = "D".equalsIgnoreCase(kvPairs.get("TIPKAGOTOVINA"));
        if (kvPairs.containsKey("PRAZNIKIOBRAT")) { try { this.praznikiObrat = Integer.parseInt(kvPairs.get("PRAZNIKIOBRAT")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PRAZNIKI")) this.prazniki = "D".equalsIgnoreCase(kvPairs.get("PRAZNIKI"));
        if (kvPairs.containsKey("TISKAMTEXT")) this.tiskamText = "D".equalsIgnoreCase(kvPairs.get("TISKAMTEXT"));
        if (kvPairs.containsKey("IZPISRACUNAPARALEL")) this.izpisRacunaParalel = "D".equalsIgnoreCase(kvPairs.get("IZPISRACUNAPARALEL"));
        if (kvPairs.containsKey("THREADIZPIS")) this.threadIzpis = "D".equalsIgnoreCase(kvPairs.get("THREADIZPIS"));
        if (kvPairs.containsKey("THREADNAROCILO")) this.threadNarocilo = "D".equalsIgnoreCase(kvPairs.get("THREADNAROCILO"));
        if (kvPairs.containsKey("THREADODPRTIIZPISANI")) this.threadOdprtiIzpisani = "D".equalsIgnoreCase(kvPairs.get("THREADODPRTIIZPISANI"));
        if (kvPairs.containsKey("THREADVSE")) this.threadVse = "D".equalsIgnoreCase(kvPairs.get("THREADVSE"));
        if (kvPairs.containsKey("THREADSTORNO")) this.threadStorno = "D".equalsIgnoreCase(kvPairs.get("THREADSTORNO"));
        if (kvPairs.containsKey("THREADT")) this.threadT = "D".equalsIgnoreCase(kvPairs.get("THREADT"));
        if (kvPairs.containsKey("OBRACUNDOVOLJEN")) this.obracunDovoljen = "D".equalsIgnoreCase(kvPairs.get("OBRACUNDOVOLJEN"));
        if (kvPairs.containsKey("TURBONIMINZNESEK")) { try { this.turBoniMinZnesek = Double.parseDouble(kvPairs.get("TURBONIMINZNESEK")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("BAZENCENASERIJSKEKARTECENIK")) this.bazenCenaSerijskeKarteCenik = "D".equalsIgnoreCase(kvPairs.get("BAZENCENASERIJSKEKARTECENIK"));
        if (kvPairs.containsKey("ERRLOG")) this.errLog = "D".equalsIgnoreCase(kvPairs.get("ERRLOG"));
        if (kvPairs.containsKey("PARTNERFURS")) this.partnerFurs = "D".equalsIgnoreCase(kvPairs.get("PARTNERFURS"));
        if (kvPairs.containsKey("PETEKWEND")) this.petekWend = "D".equalsIgnoreCase(kvPairs.get("PETEKWEND"));
        if (kvPairs.containsKey("TURBONIAKTIVNI")) this.turBoniAktivni = "D".equalsIgnoreCase(kvPairs.get("TURBONIAKTIVNI"));
        if (kvPairs.containsKey("TBDONATORON")) this.tbDonatorOn = "D".equalsIgnoreCase(kvPairs.get("TBDONATORON"));
        if (kvPairs.containsKey("BOLDNAZIVPODJETJA")) this.boldNazivPodjetja = "D".equalsIgnoreCase(kvPairs.get("BOLDNAZIVPODJETJA"));
        if (kvPairs.containsKey("FISKALNOENAKO")) this.fiskalnoEnako = "D".equalsIgnoreCase(kvPairs.get("FISKALNOENAKO"));
        if (kvPairs.containsKey("TURBONINIVO1OK")) {
            this.turBoniNivo1Ok = kvPairs.get("TURBONINIVO1OK");
            parseIntegerList(this.turBoniNivo1Ok, this.turBoniNivo1Dovoljeni);
        }
        if (kvPairs.containsKey("LANGAPP")) {
            String l = kvPairs.get("LANGAPP");
            this.langApp = (l != null) ? l.toLowerCase() : "";
        }
        if (kvPairs.containsKey("HOTELKREDIT0")) this.hotelKredit0 = "D".equalsIgnoreCase(kvPairs.get("HOTELKREDIT0"));
        if (kvPairs.containsKey("BONIVKMD5")) this.boniVkMd5 = "D".equalsIgnoreCase(kvPairs.get("BONIVKMD5"));
        if (kvPairs.containsKey("BONIVKDOLZINA")) { try { this.boniVkDolzina = Integer.parseInt(kvPairs.get("BONIVKDOLZINA")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("BONIIDDOLZINA")) { try { this.boniIdDolzina = Integer.parseInt(kvPairs.get("BONIIDDOLZINA")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PLACILANIZPOGOJ")) this.placilaNizPogoj = kvPairs.get("PLACILANIZPOGOJ");
        if (kvPairs.containsKey("MAXIZPISOVRACUNA")) { try { this.maxIzpisovRacuna = Integer.parseInt(kvPairs.get("MAXIZPISOVRACUNA")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("INTERVALIZPISANI")) { try { this.intervalIzpisani = Integer.parseInt(kvPairs.get("INTERVALIZPISANI")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("CENAPOLNJENJE")) this.cenaPolnjenje = kvPairs.get("CENAPOLNJENJE");
        if (kvPairs.containsKey("HISOBRATI")) this.hisObrati = kvPairs.get("HISOBRATI");
        if (kvPairs.containsKey("IZPISANIVIDNIDO")) { try { this.izpisaniVidniDo = Integer.parseInt(kvPairs.get("IZPISANIVIDNIDO")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("NAROCILOSTORNODO")) { try { this.narociloStornoDo = Integer.parseInt(kvPairs.get("NAROCILOSTORNODO")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PLACILAPODPIS")) {
            this.placilaPodpis = kvPairs.get("PLACILAPODPIS");
            parseIntegerList(this.placilaPodpis, this.placilaPodpisId);
        }
        if (kvPairs.containsKey("BREZVIRTUALKEYBOARD")) this.brezVirtualKeyboard = "D".equalsIgnoreCase(kvPairs.get("BREZVIRTUALKEYBOARD"));
        if (kvPairs.containsKey("WINDOWSDOUBLECLICK")) this.windowsDoubleClick = "D".equalsIgnoreCase(kvPairs.get("WINDOWSDOUBLECLICK"));
        if (kvPairs.containsKey("EKRAN1280")) this.ekran1280 = "D".equalsIgnoreCase(kvPairs.get("EKRAN1280"));
        if (kvPairs.containsKey("POSICEBREZSLIP")) this.posiceBrezSlip = "D".equalsIgnoreCase(kvPairs.get("POSICEBREZSLIP"));
        if (kvPairs.containsKey("POSICEPRINT")) this.posicePrint = "D".equalsIgnoreCase(kvPairs.get("POSICEPRINT"));
        if (kvPairs.containsKey("STORNOPOSICE")) this.stornoPosice = "D".equalsIgnoreCase(kvPairs.get("STORNOPOSICE"));
        if (kvPairs.containsKey("ICEPOS")) this.icePos = "D".equalsIgnoreCase(kvPairs.get("ICEPOS"));
        if (kvPairs.containsKey("ECRPAY")) this.ecrPay = "D".equalsIgnoreCase(kvPairs.get("ECRPAY"));
        if (kvPairs.containsKey("ECRPAY262")) this.ecrPay262 = "D".equalsIgnoreCase(kvPairs.get("ECRPAY262"));
        if (kvPairs.containsKey("ECRPRINT")) this.ecrPrint = "D".equalsIgnoreCase(kvPairs.get("ECRPRINT"));
        if (kvPairs.containsKey("ECRSTORNO")) this.ecrStorno = "D".equalsIgnoreCase(kvPairs.get("ECRSTORNO"));
        if (kvPairs.containsKey("ECRDEBUG")) this.ecrDebug = "D".equalsIgnoreCase(kvPairs.get("ECRDEBUG"));
        if (kvPairs.containsKey("ECRZABA")) this.ecrZaba = "D".equalsIgnoreCase(kvPairs.get("ECRZABA"));
        if (kvPairs.containsKey("ECRREFUND")) this.ecrRefund = "D".equalsIgnoreCase(kvPairs.get("ECRREFUND"));
        if (kvPairs.containsKey("ECRIP")) this.ecrIp = kvPairs.get("ECRIP");
        if (kvPairs.containsKey("ECRRRNMESTO")) { try { this.ecrRrnMesto = Integer.parseInt(kvPairs.get("ECRRRNMESTO")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("ECRPORT")) this.ecrPort = kvPairs.get("ECRPORT");
        if (kvPairs.containsKey("SIXPAY")) this.sixPay = "D".equalsIgnoreCase(kvPairs.get("SIXPAY"));
        if (kvPairs.containsKey("SIXCDLL")) this.sixCdll = "D".equalsIgnoreCase(kvPairs.get("SIXCDLL"));
        if (kvPairs.containsKey("SIXNAPITNINE")) this.sixNapitnine = "D".equalsIgnoreCase(kvPairs.get("SIXNAPITNINE"));
        if (kvPairs.containsKey("SIXCDLLPATH")) {
            this.sixCdllPath = kvPairs.get("SIXCDLLPATH");
            this.sixCdllPathInit = this.sixCdllPath;
        }
        if (kvPairs.containsKey("SIXPAYSTORNO")) this.sixPayStorno = "D".equalsIgnoreCase(kvPairs.get("SIXPAYSTORNO"));
        if (kvPairs.containsKey("SIXPAYTHREAD")) this.sixPayThread = "D".equalsIgnoreCase(kvPairs.get("SIXPAYTHREAD"));
        if (kvPairs.containsKey("SIXPAYRESET")) this.sixPayReset = "D".equalsIgnoreCase(kvPairs.get("SIXPAYRESET"));
        if (kvPairs.containsKey("SIX2ACTIVE")) this.six2Active = "D".equalsIgnoreCase(kvPairs.get("SIX2ACTIVE"));
        if (kvPairs.containsKey("SIXPAYPRINT")) this.sixPayPrint = "D".equalsIgnoreCase(kvPairs.get("SIXPAYPRINT"));
        if (kvPairs.containsKey("SIXTERMINALIDIP")) this.sixTerminalIdIp = kvPairs.get("SIXTERMINALIDIP");
        if (kvPairs.containsKey("SIXPOSID")) {
            this.sixPosId = kvPairs.get("SIXPOSID");
            if (this.sixPosId != null && this.sixPosId.length() > 6) {
                this.sixPosId = this.sixPosId.substring(0, 6);
            }
        }
        if (kvPairs.containsKey("SIXUSERID")) { try { this.sixUserId = Integer.parseInt(kvPairs.get("SIXUSERID")); } catch (Exception ignored) {} }
        if (this.sixPay || this.ecrPay) this.icePos = false;
        if (kvPairs.containsKey("EDENAR")) this.eDenar = "D".equalsIgnoreCase(kvPairs.get("EDENAR"));
        if (kvPairs.containsKey("WEBNAROCILA")) this.webNarocila = "D".equalsIgnoreCase(kvPairs.get("WEBNAROCILA"));
        if (kvPairs.containsKey("BRISIPLACILAZAWINDOWS")) this.brisiPlacilaZaWindows = kvPairs.get("BRISIPLACILAZAWINDOWS");
        if (kvPairs.containsKey("BAZENI")) {
            this.bazeni = "D".equalsIgnoreCase(kvPairs.get("BAZENI"));
            if (this.bazeni) this.metraAktivna = true;
        }
        if (kvPairs.containsKey("METRAAKTIVNA")) {
            String m = kvPairs.get("METRAAKTIVNA");
            if ("N".equalsIgnoreCase(m)) this.metraAktivna = false;
        }
        if (kvPairs.containsKey("METRAQRKODA")) this.metraQrKoda = "D".equalsIgnoreCase(kvPairs.get("METRAQRKODA"));
        if (kvPairs.containsKey("METRACITALEC")) this.metraCitalec = "D".equalsIgnoreCase(kvPairs.get("METRACITALEC"));
        if (kvPairs.containsKey("METRAWBCCODE")) this.metraWbcCode = kvPairs.get("METRAWBCCODE");
        if (kvPairs.containsKey("METRAAREACODE")) this.metraAreaCode = kvPairs.get("METRAAREACODE");
        if (kvPairs.containsKey("METRAURL")) this.metraUrl = kvPairs.get("METRAURL");
        if (kvPairs.containsKey("METRAPOS")) this.metraPos = kvPairs.get("METRAPOS");
        if (kvPairs.containsKey("METRADEVICE")) this.metraDevice = kvPairs.get("METRADEVICE");
        if (kvPairs.containsKey("METRAISSUECOUNT")) this.metraIssueCount = kvPairs.get("METRAISSUECOUNT");
        if (kvPairs.containsKey("METRAUSERDATA")) this.metraUserData = kvPairs.get("METRAUSERDATA");
        if (kvPairs.containsKey("METRATIMEOUT")) this.metraTimeout = kvPairs.get("METRATIMEOUT");
        if (kvPairs.containsKey("METRAUSER")) this.metraUser = kvPairs.get("METRAUSER");
        if (kvPairs.containsKey("METRAHEXTODEC")) this.metraHexToDec = "D".equalsIgnoreCase(kvPairs.get("METRAHEXTODEC"));
        if (kvPairs.containsKey("METRASTORNOKONTROLA")) this.metraStornoKontrola = "D".equalsIgnoreCase(kvPairs.get("METRASTORNOKONTROLA"));
        if (kvPairs.containsKey("METRALOG")) this.metraLog = "D".equalsIgnoreCase(kvPairs.get("METRALOG"));
        if (kvPairs.containsKey("HIDPRIJAVA")) this.pHidPrijavaIni = "D".equalsIgnoreCase(kvPairs.get("HIDPRIJAVA"));
        this.pHidPrijava = this.pHidPrijavaIni;
        if (kvPairs.containsKey("HIDPRIJAVAFS")) this.pHidPrijavaIniFs = "D".equalsIgnoreCase(kvPairs.get("HIDPRIJAVAFS"));
        if (this.pHidPrijavaIniFs && !this.pHidPrijavaIni) this.pHidPrijava = true;

        if (kvPairs.containsKey("WINDOWSPRINTER")) {
            this.winSpoolPrint = "D".equalsIgnoreCase(kvPairs.get("WINDOWSPRINTER"));
            this.blueToothPrint = !this.winSpoolPrint;
        } else {
            this.blueToothPrint = true;
        }

        if (kvPairs.containsKey("TENZORGAT")) this.tenzorGat = "D".equalsIgnoreCase(kvPairs.get("TENZORGAT"));
        if (kvPairs.containsKey("TENZORIP")) this.tenzorIp = kvPairs.get("TENZORIP");
        if (kvPairs.containsKey("TENZORMID")) this.tenzorMid = kvPairs.get("TENZORMID");
        if (kvPairs.containsKey("TENZORPORT")) this.tenzorPort = kvPairs.get("TENZORPORT");
        if (kvPairs.containsKey("TENZORZAMUDNINA")) this.tenzorZamudnina = kvPairs.get("TENZORZAMUDNINA");
        if (kvPairs.containsKey("TENZORLOG")) this.tenzorLog = "D".equalsIgnoreCase(kvPairs.get("TENZORLOG"));
        if (kvPairs.containsKey("TIPQRKODE")) { try { this.tipQrKode = Integer.parseInt(kvPairs.get("TIPQRKODE")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("KBPSLIMIT")) { try { this.kbpsLimit = Integer.parseInt(kvPairs.get("KBPSLIMIT")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("ANDROIDNET2X")) this.androidNet2x = "D".equalsIgnoreCase(kvPairs.get("ANDROIDNET2X"));
        if (kvPairs.containsKey("ANDROIDNETTCP")) this.androidNetTcp = kvPairs.get("ANDROIDNETTCP");
        if (kvPairs.containsKey("TIMERENABLEEKRAN")) { try { this.timerEnableEkran = Integer.parseInt(kvPairs.get("TIMERENABLEEKRAN")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("TIMEOUTCONNECT")) { try { this.timeOutConnect = Integer.parseInt(kvPairs.get("TIMEOUTCONNECT")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("TIMEOUTSEND")) { try { this.timeOutSend = Integer.parseInt(kvPairs.get("TIMEOUTSEND")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("TIMEOUTRECEIVE")) { try { this.timeOutReceive = Integer.parseInt(kvPairs.get("TIMEOUTRECEIVE")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("ANDROIDNETTCPPORT")) { try { this.androidNetTcpPort = Integer.parseInt(kvPairs.get("ANDROIDNETTCPPORT")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("ANDROIDNFCIZKLOP")) {
            this.pnfcprijava = "D".equalsIgnoreCase(kvPairs.get("ANDROIDNFCIZKLOP"));
            this.pNfc = this.pnfcprijava;
        }
        if (kvPairs.containsKey("HTFONTANDROID")) { try { this.htFontAndroid = Integer.parseInt(kvPairs.get("HTFONTANDROID")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("TISKANJEPAVZA")) this.tiskanjePavza = "D".equalsIgnoreCase(kvPairs.get("TISKANJEPAVZA"));
        if (kvPairs.containsKey("OBRACUNANDROID")) this.obracunAndroid = "D".equalsIgnoreCase(kvPairs.get("OBRACUNANDROID"));
        if (kvPairs.containsKey("PAYTENA")) this.payTenA = "D".equalsIgnoreCase(kvPairs.get("PAYTENA"));
        if (kvPairs.containsKey("PAYTENAROSPACKAGE")) this.payTenARosPackage = kvPairs.get("PAYTENAROSPACKAGE");
        if (kvPairs.containsKey("PAYTENAACTIVITYMAIN")) this.payTenAActivityMain = kvPairs.get("PAYTENAACTIVITYMAIN");
        if (kvPairs.containsKey("PAYTENAACTIVITIES")) this.payTenAActivities = kvPairs.get("PAYTENAACTIVITIES");
        if (kvPairs.containsKey("PAYTENAPIN")) this.payTenAPin = kvPairs.get("PAYTENAPIN");
        if (kvPairs.containsKey("PAYTENASTORNO")) this.payTenAStorno = "D".equalsIgnoreCase(kvPairs.get("PAYTENASTORNO"));

        if (kvPairs.containsKey("SIXTAP")) {
            this.sixTap = "D".equalsIgnoreCase(kvPairs.get("SIXTAP"));
            if (this.sixTap) {
                this.icePos = false;
                this.sixPay = false;
                this.payTenA = false;
            }
        }
        if (kvPairs.containsKey("SIXTAP_MANUAL_LAST")) this.sixTapManualLast = "D".equalsIgnoreCase(kvPairs.get("SIXTAP_MANUAL_LAST"));
        if (kvPairs.containsKey("SIXTAP_AUTO_LAST")) this.sixTapAutoLast = "D".equalsIgnoreCase(kvPairs.get("SIXTAP_AUTO_LAST"));
        if (kvPairs.containsKey("SIXTAPPRINT")) this.sixTapPrint = "D".equalsIgnoreCase(kvPairs.get("SIXTAPPRINT"));
        if (kvPairs.containsKey("SIXTAPSTORNO")) this.sixTapStorno = "D".equalsIgnoreCase(kvPairs.get("SIXTAPSTORNO"));
        if (kvPairs.containsKey("SIXTAP_DEBUG")) this.sixTapDebug = "D".equalsIgnoreCase(kvPairs.get("SIXTAP_DEBUG"));
        if (kvPairs.containsKey("SIXTAPSTORNOZADNJI")) this.sixTapStornoZadnji = "D".equalsIgnoreCase(kvPairs.get("SIXTAPSTORNOZADNJI"));
        if (kvPairs.containsKey("SIXTAPWPI_VERSION")) {
            this.sixTapWpiVersion = kvPairs.get("SIXTAPWPI_VERSION");
            if (this.sixTapWpiVersion != null && this.sixTapWpiVersion.contains("2.")) {
                this.recoverTapOn = true;
            }
        }
        if (kvPairs.containsKey("SIXTAP_FORMAT")) this.sixTapFormat = kvPairs.get("SIXTAP_FORMAT");
        if (kvPairs.containsKey("RECOVERTAPON")) {
            this.recoverTapOn = "D".equalsIgnoreCase(kvPairs.get("RECOVERTAPON"));
            if (this.recoverTapOn) {
                this.sixTapWpiVersion = "2.2";
            }
        }
        if (kvPairs.containsKey("TAPONRECOVERINTENT")) this.tapOnRecoverIntent = "D".equalsIgnoreCase(kvPairs.get("TAPONRECOVERINTENT"));
        if (kvPairs.containsKey("TAPONRECOVERLOGOUT")) this.tapOnRecoverLogout = "D".equalsIgnoreCase(kvPairs.get("TAPONRECOVERLOGOUT"));
        if (kvPairs.containsKey("NAPITNINAPOS")) this.napitninaPos = "D".equalsIgnoreCase(kvPairs.get("NAPITNINAPOS"));
        if (kvPairs.containsKey("PRINTEROPTIPOS32")) this.pPrinterBtOptiPos = kvPairs.get("PRINTEROPTIPOS32");
        if (kvPairs.containsKey("FORCETABLETSCREEN")) this.forceTabletScreen = "D".equalsIgnoreCase(kvPairs.get("FORCETABLETSCREEN"));
        if (kvPairs.containsKey("BRISIPLACILAZAANDROID")) this.brisiPlacilaZaAndroid = kvPairs.get("BRISIPLACILAZAANDROID");
        if (kvPairs.containsKey("NAPITNINAROS")) {
            this.napitninaRos = "D".equalsIgnoreCase(kvPairs.get("NAPITNINAROS"));
            if (this.napitninaRos) this.napitninaPos = false;
        }
        if (kvPairs.containsKey("KOREKCIJAGUMBMIZE")) { try { this.korekcijaGumbMize = Integer.parseInt(kvPairs.get("KOREKCIJAGUMBMIZE")); } catch (Exception ignored) {} }
        if (kvPairs.containsKey("PRINTER_RACUNI") && !kvPairs.get("PRINTER_RACUNI").trim().isEmpty()) this.printerRacuni = kvPairs.get("PRINTER_RACUNI").trim();
        if (kvPairs.containsKey("PRINTER_RACUN") && !kvPairs.get("PRINTER_RACUN").trim().isEmpty()) this.printerRacuni = kvPairs.get("PRINTER_RACUN").trim();
        if (kvPairs.containsKey("PRINTERRACUNI") && !kvPairs.get("PRINTERRACUNI").trim().isEmpty()) this.printerRacuni = kvPairs.get("PRINTERRACUNI").trim();
        if (kvPairs.containsKey("PRINTERRACUN") && !kvPairs.get("PRINTERRACUN").trim().isEmpty()) this.printerRacuni = kvPairs.get("PRINTERRACUN").trim();
    }

    private static void parseJsonArrayIntoKvPairs(JSONArray arr, Map<String, String> kvPairs) {
        if (arr == null) return;
        for (int i = 0; i < arr.length(); i++) {
            try {
                Object item = arr.get(i);
                if (item instanceof JSONObject) {
                    JSONObject obj = (JSONObject) item;
                    if (obj.has("naziv") && obj.has("vrednost")) {
                        String key = obj.optString("naziv", "");
                        String val = obj.optString("vrednost", "");
                        if (!key.isEmpty()) {
                            kvPairs.put(key.toUpperCase(), val);
                        }
                    } else if (obj.has("key") && obj.has("value")) {
                        String key = obj.optString("key", "");
                        String val = obj.optString("value", "");
                        if (!key.isEmpty()) {
                            kvPairs.put(key.toUpperCase(), val);
                        }
                    } else {
                        parseJsonObjectIntoKvPairs(obj, kvPairs);
                    }
                }
            } catch (Exception ignored) {}
        }
    }

    private static void parseJsonObjectIntoKvPairs(JSONObject json, Map<String, String> kvPairs) {
        if (json == null) return;
        if (json.has("naziv") && json.has("vrednost")) {
            String key = json.optString("naziv", "");
            String val = json.optString("vrednost", "");
            if (!key.isEmpty()) {
                kvPairs.put(key.toUpperCase(), val);
            }
        }
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object val = json.opt(key);
            if (val instanceof JSONArray) {
                parseJsonArrayIntoKvPairs((JSONArray) val, kvPairs);
            } else if (val instanceof JSONObject) {
                parseJsonObjectIntoKvPairs((JSONObject) val, kvPairs);
            } else if (val != null) {
                kvPairs.put(key.toUpperCase(), val.toString());
            }
        }
    }

    private static void parseIntegerList(String raw, List<Integer> targetList) {
        if (raw == null || targetList == null) return;
        targetList.clear();
        String[] parts = raw.split(",");
        for (String p : parts) {
            String t = p.trim();
            if (!t.isEmpty()) {
                try {
                    targetList.add(Integer.parseInt(t));
                } catch (Exception ignored) {}
            }
        }
    }

    // Getters and Setters
    public int getMobileId() { return mobileId; }
    public void setMobileId(int mobileId) { this.mobileId = (mobileId == 0) ? 1 : mobileId; }

    public String getServerUrl() { return serverUrl; }
    public void setServerUrl(String serverUrl) { this.serverUrl = serverUrl; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getFault() { return fault; }
    public void setFault(String fault) { this.fault = fault; }

    public String getCasGetAppConfig() { return casGetAppConfig; }
    public void setCasGetAppConfig(String casGetAppConfig) { this.casGetAppConfig = casGetAppConfig; }

    public String getNapakaZagona() { return napakaZagona; }
    public void setNapakaZagona(String napakaZagona) { this.napakaZagona = napakaZagona; }

    public boolean ispNfc() { return pNfc; }
    public void setpNfc(boolean pNfc) { this.pNfc = pNfc; }

    public boolean isPnfcprijava() { return pnfcprijava; }
    public void setPnfcprijava(boolean pnfcprijava) { this.pnfcprijava = pnfcprijava; }

    public Integer getFiskalizacija() { return fiskalizacija; }
    public void setFiskalizacija(Integer fiskalizacija) { this.fiskalizacija = fiskalizacija; }

    public String getDavcnaZaFurs() { return davcnaZaFurs; }
    public void setDavcnaZaFurs(String davcnaZaFurs) { this.davcnaZaFurs = davcnaZaFurs; }

    public boolean isPogrinjki() { return pogrinjki; }
    public void setPogrinjki(boolean pogrinjki) { this.pogrinjki = pogrinjki; }

    public boolean isBarvamTipke() { return barvamTipke; }
    public void setBarvamTipke(boolean barvamTipke) { this.barvamTipke = barvamTipke; }

    public String getAktivenStyle() { return aktivenStyle; }
    public void setAktivenStyle(String aktivenStyle) { this.aktivenStyle = aktivenStyle; }

    public Integer getfPosId() { return (fPosId != null && fPosId > 0) ? fPosId : 500; }
    public Integer getPosId() { return getfPosId(); }
    public void setfPosId(Integer fPosId) { this.fPosId = fPosId; }

    public Integer getfPoslovniProstorId() { return fPoslovniProstorId; }
    public void setfPoslovniProstorId(Integer fPoslovniProstorId) { this.fPoslovniProstorId = fPoslovniProstorId; }

    public String getNazivMobile() { return nazivMobile; }
    public void setNazivMobile(String nazivMobile) { this.nazivMobile = nazivMobile; }

    public int getTipkePosId() { return tipkePosId; }
    public void setTipkePosId(int tipkePosId) {
        if (tipkePosId < 0 || tipkePosId > 1000000) {
            this.tipkePosId = this.pomTipkePosId;
        } else {
            this.tipkePosId = tipkePosId;
        }
    }

    public int getPomTipkePosId() { return pomTipkePosId; }
    public void setPomTipkePosId(int pomTipkePosId) { this.pomTipkePosId = pomTipkePosId; }

    public Integer getTocilnicaId() { return tocilnicaId; }
    public void setTocilnicaId(Integer tocilnicaId) {
        this.tocilnicaId = tocilnicaId;
        this.tekociStrm = tocilnicaId;
    }

    public Integer getTekociStrm() { return tekociStrm; }
    public void setTekociStrm(Integer tekociStrm) { this.tekociStrm = tekociStrm; }

    public Integer getKuhinjaId() { return kuhinjaId; }
    public void setKuhinjaId(Integer kuhinjaId) { this.kuhinjaId = kuhinjaId; }

    public String getPrinterRacuni() { return printerRacuni; }
    public void setPrinterRacuni(String printerRacuni) { this.printerRacuni = printerRacuni; }

    public String getNazivPodjetja() { return nazivPodjetja; }
    public void setNazivPodjetja(String nazivPodjetja) { this.nazivPodjetja = nazivPodjetja; }

    public String getNaslovPodjetja() { return naslovPodjetja; }
    public void setNaslovPodjetja(String naslovPodjetja) { this.naslovPodjetja = naslovPodjetja; }

    public String getDdvStevilka() { return ddvStevilka; }
    public void setDdvStevilka(String ddvStevilka) { this.ddvStevilka = ddvStevilka; }

    public String getNazivProdajnegaMesta() { return nazivProdajnegaMesta; }
    public void setNazivProdajnegaMesta(String nazivProdajnegaMesta) { this.nazivProdajnegaMesta = nazivProdajnegaMesta; }

    public String getNazivObratPE() { return nazivObratPE; }
    public void setNazivObratPE(String nazivObratPE) { this.nazivObratPE = nazivObratPE; }

    public String getNaslovProdajnega() { return naslovProdajnega; }
    public void setNaslovProdajnega(String naslovProdajnega) { this.naslovProdajnega = naslovProdajnega; }

    public boolean ispPopustIzpisPozicije() { return pPopustIzpisPozicije; }
    public void setpPopustIzpisPozicije(boolean pPopustIzpisPozicije) { this.pPopustIzpisPozicije = pPopustIzpisPozicije; }

    public boolean ispLojalnostPopust() { return pLojalnostPopust; }
    public void setpLojalnostPopust(boolean pLojalnostPopust) { this.pLojalnostPopust = pLojalnostPopust; }

    public boolean ispPopust99() { return pPopust99; }
    public void setpPopust99(boolean pPopust99) { this.pPopust99 = pPopust99; }

    public boolean isKreditnaKarticaPlacilo() { return kreditnaKarticaPlacilo; }
    public void setKreditnaKarticaPlacilo(boolean kreditnaKarticaPlacilo) { this.kreditnaKarticaPlacilo = kreditnaKarticaPlacilo; }

    public String getNazivStregelVasJe() { return nazivStregelVasJe; }
    public void setNazivStregelVasJe(String nazivStregelVasJe) { this.nazivStregelVasJe = nazivStregelVasJe; }

    public String getNazivZahvala1() { return nazivZahvala1; }
    public void setNazivZahvala1(String nazivZahvala1) { this.nazivZahvala1 = nazivZahvala1; }

    public String getNazivZahvala2() { return nazivZahvala2; }
    public void setNazivZahvala2(String nazivZahvala2) { this.nazivZahvala2 = nazivZahvala2; }

    public String getNazivZahvala3() { return nazivZahvala3; }
    public void setNazivZahvala3(String nazivZahvala3) { this.nazivZahvala3 = nazivZahvala3; }

    public String getNazivZahvala4() { return nazivZahvala4; }
    public void setNazivZahvala4(String nazivZahvala4) { this.nazivZahvala4 = nazivZahvala4; }

    public int getHisDestinacija() { return hisDestinacija; }
    public void setHisDestinacija(int hisDestinacija) { this.hisDestinacija = hisDestinacija; }

    public int getHisObrat() { return hisObrat; }
    public void setHisObrat(int hisObrat) { this.hisObrat = hisObrat; }

    public int getPrinterSteviloZnakov() { return printerSteviloZnakov; }
    public void setPrinterSteviloZnakov(int printerSteviloZnakov) {
        this.printerSteviloZnakov = (printerSteviloZnakov == 48) ? 42 : printerSteviloZnakov;
    }

    public String getEscAlignCenter() { return escAlignCenter; }
    public void setEscAlignCenter(String val) { this.escAlignCenter = val; }
    public String getEscAlignLeft() { return escAlignLeft; }
    public void setEscAlignLeft(String val) { this.escAlignLeft = val; }
    public String getEscAlignRight() { return escAlignRight; }
    public void setEscAlignRight(String val) { this.escAlignRight = val; }
    public String getEscBoldOff() { return escBoldOff; }
    public void setEscBoldOff(String val) { this.escBoldOff = val; }
    public String getEscBoldOn() { return escBoldOn; }
    public void setEscBoldOn(String val) { this.escBoldOn = val; }
    public String getEscCpi16() { return escCpi16; }
    public void setEscCpi16(String val) { this.escCpi16 = val; }
    public String getEscCpi20() { return escCpi20; }
    public void setEscCpi20(String val) { this.escCpi20 = val; }
    public String getEscCut() { return escCut; }
    public void setEscCut(String val) { this.escCut = val; }
    public String getEscEol() { return escEol; }
    public void setEscEol(String val) { this.escEol = val; }
    public String getEscInitPrint() { return escInitPrint; }
    public void setEscInitPrint(String val) { this.escInitPrint = val; }
    public String getEscInverseOff() { return escInverseOff; }
    public void setEscInverseOff(String val) { this.escInverseOff = val; }
    public String getEscInverseOn() { return escInverseOn; }
    public void setEscInverseOn(String val) { this.escInverseOn = val; }
    public String getEscNewLine() { return escNewLine; }
    public void setEscNewLine(String val) { this.escNewLine = val; }
    public String getEscReset() { return escReset; }
    public void setEscReset(String val) { this.escReset = val; }
    public String getEscUnderlineOff() { return escUnderlineOff; }
    public void setEscUnderlineOff(String val) { this.escUnderlineOff = val; }
    public String getEscUnderlineOn() { return escUnderlineOn; }
    public void setEscUnderlineOn(String val) { this.escUnderlineOn = val; }
    public String getEscWidth2xOff() { return escWidth2xOff; }
    public void setEscWidth2xOff(String val) { this.escWidth2xOff = val; }
    public String getEscWidth2xOn() { return escWidth2xOn; }
    public void setEscWidth2xOn(String val) { this.escWidth2xOn = val; }
    public String getEscPredal() { return escPredal; }
    public void setEscPredal(String val) { this.escPredal = val; }

    public boolean isUpostevamZgorajSpodaj() { return upostevamZgorajSpodaj; }
    public boolean isCenikLokalno() { return cenikLokalno; }
    public int getCenikStDniObnova() { return cenikStDniObnova; }
    public boolean isBazenProdajaSkPaket() { return bazenProdajaSkPaket; }
    public boolean isTiskanjeInicialke() { return tiskanjeInicialke; }
    public int getDavkiFiskalMetoda() { return davkiFiskalMetoda; }
    public boolean isRajoni() { return rajoni; }
    public int getRajonDefault() { return rajonDefault; }
    public boolean isPrintAlignLeft() { return printAlignLeft; }
    public boolean isPlacilaEnabled() { return placilaEnabled; }
    public int getZamudaMinute() { return zamudaMinute; }
    public int getValutId() { return valutId; }
    public boolean isKronologIzklop() { return kronologIzklop; }
    public boolean isDebugL1() { return debugL1; }
    public boolean isDebugL2() { return debugL2; }
    public boolean isDebugL3() { return debugL3; }
    public boolean isWsReconnect() { return wsReconnect; }
    public boolean isObroki() { return obroki; }
    public boolean isObracunBrezIzpisa() { return obracunBrezIzpisa; }
    public boolean isPrintOff() { return printOff; }
    public boolean isRacuniSoLahkoFakture() { return racuniSoLahkoFakture; }
    public int getStdNiValuta() { return stdNiValuta; }
    public String getKlavzulaFaktura() { return klavzulaFaktura; }
    public String getKlavzulaFakturaDavek0() { return klavzulaFakturaDavek0; }
    public String getPlacilaFaktura() { return placilaFaktura; }
    public String getReportFormat() { return reportFormat; }
    public boolean isReprezentancaPoFirmah() { return reprezentancaPoFirmah; }
    public boolean isLokatorji() { return lokatorji; }
    public boolean isBarcodeProdaja() { return barcodeProdaja; }
    public boolean isBarcodeNivo4() { return barcodeNivo4; }
    public boolean isPrintamSlipNaRacun() { return printamSlipNaRacun; }
    public boolean isSkrijGumbiZklop() { return skrijGumbiZklop; }
    public boolean isAkcije() { return akcije; }
    public boolean isInkasoAnalitika() { return inkasoAnalitika; }
    public boolean isAutoPrintRacun() { return autoPrintRacun; }
    public boolean ispLogout() { return pLogout; }
    public boolean ispLogoutPoIzpisu() { return pLogoutPoIzpisu; }
    public boolean ispLogoutPoNarocilu() { return pLogoutPoNarocilu; }
    public boolean ispOdjavaDialog() { return pOdjavaDialog; }
    public boolean isPinLogin() { return pinLogin; }
    public int getLogoutCas() { return logoutCas; }
    public boolean isPrintamNarocila() { return printamNarocila; }
    public boolean isPrintBlokInNarocilo() { return printBlokInNarocilo; }
    public boolean isPrintamNarocilaNikamor() { return printamNarocilaNikamor; }
    public boolean isNePrintamNarT1() { return nePrintamNarT1; }
    public boolean isNePrintamNarT2() { return nePrintamNarT2; }
    public boolean isNePrintamNarT3() { return nePrintamNarT3; }
    public boolean isNePrintamNarT4() { return nePrintamNarT4; }
    public int getPrintBlokPavza() { return printBlokPavza; }
    public boolean isPrintBrezTiskalnika() { return printBrezTiskalnika; }
    public boolean isPrintamVoucher() { return printamVoucher; }
    public int getNivo4IdVoucher() { return nivo4IdVoucher; }
    public int getNapitnineProcentKuh() { return napitnineProcentKuh; }
    public boolean isNapitnine() { return napitnine; }
    public boolean isVraciloNum() { return vraciloNum; }
    public boolean isCroFisk() { return croFisk; }
    public boolean isCroNapitnine() { return croNapitnine; }
    public boolean isSortPlacila() { return sortPlacila; }
    public String getDpoVr1() { return dpoVr1; }
    public String getDpoVr2() { return dpoVr2; }
    public String getDpoVr3() { return dpoVr3; }
    public String getDpoVr4() { return dpoVr4; }
    public String getDpoVr5() { return dpoVr5; }
    public String getDpoVr6() { return dpoVr6; }
    public boolean ispCheckPinPotekel() { return pCheckPinPotekel; }
    public boolean isNiStornoIzpisanegaNarocila() { return niStornoIzpisanegaNarocila; }
    public boolean isOverrideStornoPoslanoNarocilo() { return overrideStornoPoslanoNarocilo; }
    public boolean isStornoRazlogVr() { return stornoRazlogVr; }
    public boolean isStornoOsebaStorniral() { return stornoOsebaStorniral; }
    public boolean isStornoPosVirtual() { return stornoPosVirtual; }
    public boolean isStornoPosOverride() { return stornoPosOverride; }
    public boolean isNovaVrsticaNarocila() { return novaVrsticaNarocila; }
    public boolean isEditKolicinaAdd() { return editKolicinaAdd; }
    public boolean isPluAktiven() { return pluAktiven; }
    public boolean isPodatkiNaNapravi() { return podatkiNaNapravi; }
    public boolean isIzpisOpombaRacun() { return izpisOpombaRacun; }
    public boolean isZamenjavaMarkerMize() { return zamenjavaMarkerMize; }
    public boolean isOpombeTabelaOff() { return opombeTabelaOff; }
    public boolean isZamenjavaMarkerEdit() { return zamenjavaMarkerEdit; }
    public boolean isVnosCeneZaVseIni() { return vnosCeneZaVseIni; }
    public boolean isVnosCeneZaVse() { return vnosCeneZaVse; }
    public boolean isHodNarocila() { return hodNarocila; }
    public boolean isPrintamStornoNarocila() { return printamStornoNarocila; }
    public boolean isMizeInRacuni() { return mizeInRacuni; }
    public boolean isPrintNarocilaLokalno() { return printNarocilaLokalno; }
    public boolean isLestvicaEpHt() { return lestvicaEpHt; }
    public boolean isLestvicaEp() { return lestvicaEp; }
    public boolean isDnCenik() { return dnCenik; }
    public boolean isCena0Cenik() { return cena0Cenik; }
    public boolean isCena0Dovoljena() { return cena0Dovoljena; }
    public boolean isPopust99Proporcialno() { return popust99Proporcialno; }
    public int getPlaciloHk() { return placiloHk; }
    public int getBonNivo4Id() { return bonNivo4Id; }
    public double getProcentNivelacije() { return procentNivelacije; }
    public int getModelCena2() { return modelCena2; }
    public boolean isCena2Vikend() { return cena2Vikend; }
    public boolean isCena2Aktivna() { return cena2Aktivna; }
    public void setCena2Aktivna(boolean cena2Aktivna) { this.cena2Aktivna = cena2Aktivna; }
    public boolean isLahkoPreklopiCenik() { return !this.cena2PreklopOff; }
    public boolean isCena2PreklopOff() { return cena2PreklopOff; }
    public boolean isOdpriVseRacune() { return odpriVseRacune; }
    public boolean isRazlogStorno() { return razlogStorno; }
    public boolean isCrmAktiven() { return crmAktiven; }
    public boolean isSkupineNaSobo() { return skupineNaSobo; }
    public int getStKopijSlip() { return stKopijSlip; }
    public boolean isKkRocno() { return kkRocno; }
    public int getKredKarticaKupecId() { return kredKarticaKupecId; }
    public int getKredKarticaKupecIdDiners() { return kredKarticaKupecIdDiners; }
    public int getkKarticaKupecIdRocno() { return kKarticaKupecIdRocno; }
    public int getkKarticaTippartnerRocno() { return kKarticaTippartnerRocno; }

    public synchronized boolean hasCachedCenik() { return !cachedCenik.isEmpty(); }
    public synchronized List<CenikListAdapter.CenikItem> getCachedCenik() { return new ArrayList<>(cachedCenik); }
    public synchronized void setCachedCenik(List<CenikListAdapter.CenikItem> items) {
        cachedCenik.clear();
        if (items != null) {
            cachedCenik.addAll(items);
            for (CenikListAdapter.CenikItem ci : items) {
                if (ci != null && ci.nivo4Id > 0 && ci.naziv != null && !ci.naziv.trim().isEmpty()) {
                    nivo4NazivLookup.put(ci.nivo4Id, ci.naziv.trim());
                }
            }
        }
    }

    public synchronized boolean hasCachedHitreTipke() { return !cachedHitreTipke.isEmpty(); }
    public synchronized List<HitraTipkaTp> getCachedHitreTipke() { return new ArrayList<>(cachedHitreTipke); }
    public synchronized void setCachedHitreTipke(List<HitraTipkaTp> items) {
        cachedHitreTipke.clear();
        if (items != null) {
            cachedHitreTipke.addAll(items);
            for (HitraTipkaTp ht : items) {
                if (ht != null && ht.getNivo4Id() != null && ht.getNivo4Id() > 0 && ht.getNaziv() != null && !ht.getNaziv().trim().isEmpty()) {
                    nivo4NazivLookup.putIfAbsent(ht.getNivo4Id(), ht.getNaziv().trim());
                }
            }
        }
    }

    public synchronized void clearCenikCache() {
        cachedCenik.clear();
        cachedHitreTipke.clear();
        nivo4NazivLookup.clear();
    }

    public void registerNazivForNivo4(int nivo4Id, String naziv) {
        if (nivo4Id > 0 && naziv != null && !naziv.trim().isEmpty() && !naziv.startsWith("Artikel #")) {
            nivo4NazivLookup.put(nivo4Id, naziv.trim());
        }
    }

    public String findNazivByNivo4Id(int nivo4Id) {
        if (nivo4Id <= 0) return "";
        String found = nivo4NazivLookup.get(nivo4Id);
        if (found != null && !found.trim().isEmpty()) {
            return found;
        }
        for (CenikListAdapter.CenikItem ci : cachedCenik) {
            if (ci.nivo4Id == nivo4Id && ci.naziv != null && !ci.naziv.trim().isEmpty()) {
                nivo4NazivLookup.put(nivo4Id, ci.naziv.trim());
                return ci.naziv.trim();
            }
        }
        for (HitraTipkaTp ht : cachedHitreTipke) {
            if (ht.getNivo4Id() != null && ht.getNivo4Id() == nivo4Id && ht.getNaziv() != null && !ht.getNaziv().trim().isEmpty()) {
                nivo4NazivLookup.put(nivo4Id, ht.getNaziv().trim());
                return ht.getNaziv().trim();
            }
        }
        if (currentRacun != null && currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null && p.getNivo4Id() != null && p.getNivo4Id() == nivo4Id) {
                    String pNaziv = p.getNazivRaw();
                    if (pNaziv != null && !pNaziv.trim().isEmpty() && !pNaziv.startsWith("Artikel #")) {
                        nivo4NazivLookup.put(nivo4Id, pNaziv.trim());
                        return pNaziv.trim();
                    }
                }
            }
        }
        return "";
    }

    public synchronized boolean hasCachedPlacila() { return !cachedPlacila.isEmpty(); }
    public synchronized List<NacPlacTp> getCachedPlacila() { return new ArrayList<>(cachedPlacila); }
    public synchronized List<Integer> getPlacilnaSredstva() { return new ArrayList<>(placilnaSredstva); }
    public synchronized void setPlacilnaSredstva(List<Integer> list) {
        this.placilnaSredstva.clear();
        if (list != null) this.placilnaSredstva.addAll(list);
    }

    public synchronized void filterAndSetCachedPlacila(List<NacPlacTp> rawPlacila) {
        cachedPlacila.clear();
        if (rawPlacila == null || rawPlacila.isEmpty()) return;

        if (placilnaSredstva != null && !placilnaSredstva.isEmpty()) {
            for (Integer psId : placilnaSredstva) {
                if (psId == null) continue;
                for (NacPlacTp np : rawPlacila) {
                    if (np.getPlaciloId() == psId) {
                        cachedPlacila.add(np);
                        break;
                    }
                }
            }
        } else {
            cachedPlacila.addAll(rawPlacila);
        }

        // Delphi sintetično plačilo 399 za ročno kreditno kartico (če je aktivno)
        if (kreditnaKarticaPlacilo && isKkRocno()) {
            boolean alreadyHas399 = false;
            for (NacPlacTp np : cachedPlacila) {
                if (np.getPlaciloId() == 399) {
                    alreadyHas399 = true;
                    break;
                }
            }
            if (!alreadyHas399) {
                NacPlacTp kk = new NacPlacTp(399, "KREDIT. KARTICA", 3);
                kk.setInkaso(1);
                kk.setFiskalno(1);
                kk.setVrsta(1);
                kk.setStoritevId(getkKarticaTippartnerRocno());
                kk.setStKopij(1);
                cachedPlacila.add(kk);
            }
        }
    }

    public synchronized int placilometoda(int placiloId) {
        for (NacPlacTp np : cachedPlacila) {
            if (np.getPlaciloId() == placiloId) {
                return np.getMetoda();
            }
        }
        return 1; // Privzeto 1 (Gotovina)
    }

    public synchronized NacPlacTp getPlaciloById(int placiloId) {
        for (NacPlacTp np : cachedPlacila) {
            if (np.getPlaciloId() == placiloId) {
                return np;
            }
        }
        return null;
    }

    public synchronized int stKopijPlacila(RacunTp racun) {
        int maxKopije = 1;
        if (racun != null && racun.getRacPlaci() != null) {
            for (PlaciloTp pl : racun.getRacPlaci()) {
                if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() != 99) {
                    NacPlacTp np = getPlaciloById(pl.getPlaciloId());
                    if (np != null && np.getStKopij() != null && np.getStKopij() > maxKopije) {
                        maxKopije = np.getStKopij();
                    }
                }
            }
        }
        return maxKopije;
    }
    public int getHotkey3() { return hotkey3; }
    public int getHotkey4() { return hotkey4; }
    public int getHotkey5() { return hotkey5; }
    public boolean isTipkaGotovina() { return tipkaGotovina; }
    public int getPraznikiObrat() { return praznikiObrat; }
    public boolean isPrazniki() { return prazniki; }
    public boolean isTiskamText() { return tiskamText; }
    public boolean isIzpisRacunaParalel() { return izpisRacunaParalel; }
    public boolean isThreadIzpis() { return threadIzpis; }
    public boolean isThreadNarocilo() { return threadNarocilo; }
    public boolean isThreadOdprtiIzpisani() { return threadOdprtiIzpisani; }
    public boolean isThreadVse() { return threadVse; }
    public boolean isThreadStorno() { return threadStorno; }
    public boolean isThreadT() { return threadT; }
    public boolean isObracunDovoljen() { return obracunDovoljen; }
    public double getTurBoniMinZnesek() { return turBoniMinZnesek; }
    public boolean isBazenCenaSerijskeKarteCenik() { return bazenCenaSerijskeKarteCenik; }
    public boolean isErrLog() { return errLog; }
    public boolean isPartnerFurs() { return partnerFurs; }
    public boolean isPetekWend() { return petekWend; }
    public boolean isTurBoniAktivni() { return turBoniAktivni; }
    public boolean isTbDonatorOn() { return tbDonatorOn; }
    public boolean isBoldNazivPodjetja() { return boldNazivPodjetja; }
    public boolean isFiskalnoEnako() { return fiskalnoEnako; }
    public void setFiskalnoEnako(boolean fiskalnoEnako) { this.fiskalnoEnako = fiskalnoEnako; }
    public String getTurBoniNivo1Ok() { return turBoniNivo1Ok; }
    public List<Integer> getTurBoniNivo1Dovoljeni() { return turBoniNivo1Dovoljeni; }
    public String getLangApp() { return langApp; }
    public boolean isHotelKredit0() { return hotelKredit0; }
    public boolean isBoniVkMd5() { return boniVkMd5; }
    public int getBoniVkDolzina() { return boniVkDolzina; }
    public int getBoniIdDolzina() { return boniIdDolzina; }
    public String getPlacilaNizPogoj() { return placilaNizPogoj; }
    public int getMaxIzpisovRacuna() { return maxIzpisovRacuna; }
    public int getIntervalIzpisani() { return intervalIzpisani; }
    public String getCenaPolnjenje() { return cenaPolnjenje; }
    public String getHisObrati() { return hisObrati; }
    public int getIzpisaniVidniDo() { return izpisaniVidniDo; }
    public int getNarociloStornoDo() { return narociloStornoDo; }
    public String getPlacilaPodpis() { return placilaPodpis; }
    public List<Integer> getPlacilaPodpisId() { return placilaPodpisId; }
    public boolean isBrezVirtualKeyboard() { return brezVirtualKeyboard; }
    public boolean isWindowsDoubleClick() { return windowsDoubleClick; }
    public boolean isEkran1280() { return ekran1280; }
    public boolean isPosiceBrezSlip() { return posiceBrezSlip; }
    public boolean isPosicePrint() { return posicePrint; }
    public boolean isStornoPosice() { return stornoPosice; }
    public boolean isIcePos() { return icePos; }
    public boolean isEcrPay() { return ecrPay; }
    public boolean isEcrPay262() { return ecrPay262; }
    public boolean isEcrPrint() { return ecrPrint; }
    public boolean isEcrStorno() { return ecrStorno; }
    public boolean isEcrDebug() { return ecrDebug; }
    public boolean isEcrZaba() { return ecrZaba; }
    public boolean isEcrRefund() { return ecrRefund; }
    public String getEcrIp() { return ecrIp; }
    public int getEcrRrnMesto() { return ecrRrnMesto; }
    public String getEcrPort() { return ecrPort; }
    public boolean isSixPay() { return sixPay; }
    public boolean isSixCdll() { return sixCdll; }
    public boolean isSixNapitnine() { return sixNapitnine; }
    public String getSixCdllPath() { return sixCdllPath; }
    public String getSixCdllPathInit() { return sixCdllPathInit; }
    public boolean isSixPayStorno() { return sixPayStorno; }
    public boolean isSixPayThread() { return sixPayThread; }
    public boolean isSixPayReset() { return sixPayReset; }
    public boolean isSix2Active() { return six2Active; }
    public boolean isSixPayPrint() { return sixPayPrint; }
    public String getSixTerminalIdIp() { return sixTerminalIdIp; }
    public String getSixPosId() { return sixPosId; }
    public int getSixUserId() { return sixUserId; }
    public boolean iseDenar() { return eDenar; }
    public boolean isWebNarocila() { return webNarocila; }
    public String getBrisiPlacilaZaWindows() { return brisiPlacilaZaWindows; }
    public boolean isBazeni() { return bazeni; }
    public boolean isMetraAktivna() { return metraAktivna; }
    public boolean isMetraQrKoda() { return metraQrKoda; }
    public boolean isMetraCitalec() { return metraCitalec; }
    public String getMetraWbcCode() { return metraWbcCode; }
    public String getMetraAreaCode() { return metraAreaCode; }
    public String getMetraUrl() { return metraUrl; }
    public String getMetraPos() { return metraPos; }
    public String getMetraDevice() { return metraDevice; }
    public String getMetraIssueCount() { return metraIssueCount; }
    public String getMetraUserData() { return metraUserData; }
    public String getMetraTimeout() { return metraTimeout; }
    public String getMetraUser() { return metraUser; }
    public boolean isMetraHexToDec() { return metraHexToDec; }
    public boolean isMetraStornoKontrola() { return metraStornoKontrola; }
    public boolean isMetraLog() { return metraLog; }
    public boolean ispHidPrijavaIni() { return pHidPrijavaIni; }
    public boolean ispHidPrijava() { return pHidPrijava; }
    public boolean ispHidPrijavaIniFs() { return pHidPrijavaIniFs; }
    public boolean isWinSpoolPrint() { return winSpoolPrint; }
    public boolean isBlueToothPrint() { return blueToothPrint; }
    public boolean isTenzorGat() { return tenzorGat; }
    public String getTenzorIp() { return tenzorIp; }
    public String getTenzorMid() { return tenzorMid; }
    public String getTenzorPort() { return tenzorPort; }
    public String getTenzorZamudnina() { return tenzorZamudnina; }
    public boolean isTenzorLog() { return tenzorLog; }
    public int getTipQrKode() { return tipQrKode; }
    public int getKbpsLimit() { return kbpsLimit; }
    public boolean isAndroidNet2x() { return androidNet2x; }
    public String getAndroidNetTcp() { return androidNetTcp; }
    public int getTimerEnableEkran() { return timerEnableEkran; }
    public int getTimeOutConnect() { return timeOutConnect; }
    public int getTimeOutSend() { return timeOutSend; }
    public int getTimeOutReceive() { return timeOutReceive; }
    public int getAndroidNetTcpPort() { return androidNetTcpPort; }
    public int getHtFontAndroid() { return htFontAndroid; }
    public boolean isTiskanjePavza() { return tiskanjePavza; }
    public boolean isObracunAndroid() { return obracunAndroid; }
    public boolean isPayTenA() { return payTenA; }
    public String getPayTenARosPackage() { return payTenARosPackage; }
    public String getPayTenAActivityMain() { return payTenAActivityMain; }
    public String getPayTenAActivities() { return payTenAActivities; }
    public String getPayTenAPin() { return payTenAPin; }
    public boolean isPayTenAStorno() { return payTenAStorno; }

    public boolean isSixTap() { return sixTap; }
    public boolean isSixTapManualLast() { return sixTapManualLast; }
    public boolean isSixTapAutoLast() { return sixTapAutoLast; }
    public boolean isSixTapPrint() { return sixTapPrint; }
    public boolean isSixTapStorno() { return sixTapStorno; }
    public boolean isSixTapDebug() { return sixTapDebug; }
    public boolean isSixTapStornoZadnji() { return sixTapStornoZadnji; }
    public String getSixTapWpiVersion() { return sixTapWpiVersion; }
    public String getSixTapFormat() { return sixTapFormat; }
    public boolean isRecoverTapOn() { return recoverTapOn; }
    public boolean isTapOnRecoverIntent() { return tapOnRecoverIntent; }
    public boolean isTapOnRecoverLogout() { return tapOnRecoverLogout; }
    public boolean isNapitninaPos() { return napitninaPos; }
    public String getpPrinterBtOptiPos() { return pPrinterBtOptiPos; }
    public boolean isForceTabletScreen() { return forceTabletScreen; }
    public String getBrisiPlacilaZaAndroid() { return brisiPlacilaZaAndroid; }
    public boolean isNapitninaRos() { return napitninaRos; }
    public int getKorekcijaGumbMize() { return korekcijaGumbMize; }

    public int getActiveRacunId() { return activeRacunId; }
    public void setActiveRacunId(int activeRacunId) { this.activeRacunId = activeRacunId; }

    public RacunTp getCurrentRacun() { return currentRacun; }
    public void setCurrentRacun(RacunTp currentRacun) { this.currentRacun = currentRacun; }

    public String getMobIniValue(String key) {
        if (key == null) return null;
        return mobIniValues.get(key.toUpperCase());
    }

    // Cache za CenikVrVr (sestavine paketov)
    public List<CenikVrVrTp> getCachedCenikVrVr() {
        return new ArrayList<>(cachedCenikVrVr);
    }

    public void setCachedCenikVrVr(List<CenikVrVrTp> items) {
        cachedCenikVrVr.clear();
        if (items != null) {
            cachedCenikVrVr.addAll(items);
        }
    }

    public boolean hasCachedCenikVrVr() {
        return !cachedCenikVrVr.isEmpty();
    }

    public List<CenikVrVrTp> findCenikVrVrByPaketNivo4Id(int paketNivo4Id) {
        List<CenikVrVrTp> res = new ArrayList<>();
        for (CenikVrVrTp item : cachedCenikVrVr) {
            if (item.getCenikvrnivo4Id() == paketNivo4Id) {
                res.add(item);
            }
        }
        return res;
    }

    // Cache za Dodatke (opombe naročila)
    public List<DodatekTp> getCachedDodatki() {
        return new ArrayList<>(cachedDodatki);
    }

    public void setCachedDodatki(List<DodatekTp> items) {
        cachedDodatki.clear();
        if (items != null) {
            cachedDodatki.addAll(items);
        }
    }

    public boolean hasCachedDodatki() {
        return !cachedDodatki.isEmpty();
    }

    /**
     * Razporedi popust 99 na vse aktivne postavke racuna po formuli iz Delphi PopustNaRacun.
     */
    public BigDecimal popustNaRacun(RacunTp racun, BigDecimal procent, BigDecimal znesek) {
        if (racun == null || racun.getRacPozic() == null) return BigDecimal.ZERO;
        BigDecimal skupniPopust = BigDecimal.ZERO;

        for (PozicijaTp p : racun.getRacPozic()) {
            if (p != null && !p.isRowDeleted()) {
                BigDecimal pKol = BigDecimal.valueOf(p.getKolicina());
                BigDecimal pEp = (p.getEnotaProdajeId() != null && p.getEnotaProdajeId().compareTo(BigDecimal.ZERO) > 0) ? p.getEnotaProdajeId() : BigDecimal.ONE;
                BigDecimal polnaVrednost = p.getCena().multiply(pKol).multiply(pEp).setScale(2, java.math.RoundingMode.HALF_UP);

                BigDecimal vrsticaPopust = BigDecimal.ZERO;
                if (procent != null && procent.compareTo(BigDecimal.ZERO) > 0) {
                    vrsticaPopust = polnaVrednost.multiply(procent).divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
                    p.setCenaNabavna(p.getCenaNabavna() != null ? p.getCenaNabavna().add(procent) : procent);
                } else if (znesek != null && znesek.compareTo(BigDecimal.ZERO) > 0) {
                    vrsticaPopust = znesek;
                }

                p.setZnesekPopust(vrsticaPopust);
                // 100% KONTROLA: Za Popust 99 se ZNESEK in CENA na RACPOZIC NE spremenita!
                // ZNESEK ostane polnaVrednost (kolicina * ep * cena), popust pa je zabelezen v ZNESEK_POPUST in CENA_NABAVNA.
                // Glava racuna (RACGLAVA.ZNESEK) je VEDNO suma narocila in se NIKOLI ne spreminja pri popustu 99!
                p.setZnesek(polnaVrednost);

                if (p.getStopnjaDavka() > 0 && polnaVrednost.compareTo(BigDecimal.ZERO) > 0) {
                    double davekProc = p.getStopnjaDavka();
                    BigDecimal zd = polnaVrednost.multiply(BigDecimal.valueOf(davekProc)).divide(BigDecimal.valueOf(100.0 + davekProc), 4, java.math.RoundingMode.HALF_UP);
                    p.setZnesekDavka(zd);
                }

                skupniPopust = skupniPopust.add(vrsticaPopust);
            }
        }

        racun.posodobiZnesekIzNarocila();
        racun.preracunajVsote();
        return skupniPopust;
    }

    /**
     * Razveljavi popust 99 na postavkah racuna (Delphi BrisiPopustNaRacun).
     */
    public void brisiPopustNaRacun(RacunTp racun, BigDecimal procent, BigDecimal znesek) {
        if (racun == null || racun.getRacPozic() == null) return;

        for (PozicijaTp p : racun.getRacPozic()) {
            if (p != null && !p.isRowDeleted()) {
                BigDecimal pKol = BigDecimal.valueOf(p.getKolicina());
                BigDecimal pEp = (p.getEnotaProdajeId() != null && p.getEnotaProdajeId().compareTo(BigDecimal.ZERO) > 0) ? p.getEnotaProdajeId() : BigDecimal.ONE;
                BigDecimal polnaVrednost = p.getCena().multiply(pKol).multiply(pEp).setScale(2, java.math.RoundingMode.HALF_UP);

                p.setZnesekPopust(BigDecimal.ZERO);
                p.setCenaNabavna(BigDecimal.ZERO);
                p.setZnesek(polnaVrednost);

                if (p.getStopnjaDavka() > 0 && polnaVrednost.compareTo(BigDecimal.ZERO) > 0) {
                    double davekProc = p.getStopnjaDavka();
                    BigDecimal zd = polnaVrednost.multiply(BigDecimal.valueOf(davekProc)).divide(BigDecimal.valueOf(100.0 + davekProc), 4, java.math.RoundingMode.HALF_UP);
                    p.setZnesekDavka(zd);
                }
            }
        }

        racun.posodobiZnesekIzNarocila();
        racun.preracunajVsote();
    }

    public synchronized List<OsebaTp> getCachedOsebje() {
        return new ArrayList<>(cachedOsebje);
    }

    public synchronized List<PrioritetaProjektaTp> getCachedPrioritete() {
        return new ArrayList<>(cachedPrioritete);
    }

    public synchronized List<TarifaTp> getCachedTarife() {
        return new ArrayList<>(cachedTarife);
    }

    public synchronized List<MizaTp> getCachedMize() {
        return new ArrayList<>(cachedMize);
    }

    public synchronized List<Integer> getCachedRajoni() {
        return new ArrayList<>(cachedRajoni);
    }

    public synchronized void setCachedSifranti(List<OsebaTp> osebe, List<PrioritetaProjektaTp> prioritete, List<TarifaTp> tarife, List<MizaTp> mize) {
        if (osebe != null) {
            this.cachedOsebje.clear();
            this.cachedOsebje.addAll(osebe);
        }
        if (prioritete != null) {
            this.cachedPrioritete.clear();
            this.cachedPrioritete.addAll(prioritete);
        }
        if (tarife != null) {
            this.cachedTarife.clear();
            this.cachedTarife.addAll(tarife);
        }
        if (mize != null) {
            this.cachedMize.clear();
            this.cachedMize.addAll(mize);
            java.util.Set<Integer> unique = new java.util.TreeSet<>();
            for (MizaTp m : mize) {
                if (m.getRajon() != null && m.getRajon() > 0) unique.add(m.getRajon());
            }
            this.cachedRajoni.clear();
            this.cachedRajoni.addAll(unique);
        }
    }

    public synchronized OsebaTp getTekocaOseba() {
        return tekocaOseba;
    }

    public synchronized int getTekocaOsebaId() {
        return tekocaOsebaId;
    }

    public synchronized String getTekocaOsebaNaziv() {
        return tekocaOsebaNaziv != null ? tekocaOsebaNaziv : "";
    }

    public synchronized int getVlogaOsebe() {
        return vlogaOsebe;
    }

    public synchronized int getMaxPopOseba() {
        return maxPopOseba;
    }

    public synchronized void setTekocaOseba(OsebaTp oseba) {
        this.tekocaOseba = oseba;
        if (oseba != null) {
            this.tekocaOsebaId = oseba.getOsebaId();
            this.tekocaOsebaNaziv = oseba.getNaziv();
            this.vlogaOsebe = oseba.getVlogaOsebe();
            this.maxPopOseba = (oseba.getPrioriteta() != null) ? oseba.getPrioriteta() : 0;
        } else {
            this.tekocaOsebaId = 0;
            this.tekocaOsebaNaziv = "";
            this.vlogaOsebe = 1;
            this.maxPopOseba = 0;
        }
    }

    public synchronized OsebaTp najdiOseboZaPin(String pin) {
        if (pin == null || pin.trim().isEmpty()) return null;
        String trimmed = pin.trim();
        for (OsebaTp o : cachedOsebje) {
            if (o.getPin() != null && trimmed.equals(o.getPin().trim())) {
                return o;
            }
        }
        return null;
    }

    public synchronized OsebaTp najdiOseboZaKartico(String cardCode) {
        if (cardCode == null || cardCode.trim().isEmpty()) return null;
        for (OsebaTp o : cachedOsebje) {
            if (o.ujemaSeKartica(cardCode)) {
                return o;
            }
        }
        return null;
    }

    public synchronized OsebaTp najdiOseboById(int osebaId) {
        if (osebaId <= 0) return null;
        for (OsebaTp o : cachedOsebje) {
            if (o.getOsebaId() == osebaId) {
                return o;
            }
        }
        return null;
    }

    public synchronized TarifaTp najdiTarifoZaId(int tarifaId) {
        for (TarifaTp t : cachedTarife) {
            if (t.getTarifaId() == tarifaId) {
                return t;
            }
        }
        return null;
    }

    public synchronized MizaTp najdiMizoZaNaziv(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) return null;
        String trimmed = naziv.trim();
        for (MizaTp m : cachedMize) {
            if (trimmed.equalsIgnoreCase(m.getNaziv())) {
                return m;
            }
        }
        return null;
    }

    public synchronized boolean osebiDovoljeno(int osebaId, String pravicaCaption) {
        if (pravicaCaption == null || pravicaCaption.trim().isEmpty()) return false;
        OsebaTp oseba = null;
        if (tekocaOseba != null && tekocaOseba.getOsebaId() == osebaId) {
            oseba = tekocaOseba;
        } else {
            for (OsebaTp o : cachedOsebje) {
                if (o.getOsebaId() == osebaId) {
                    oseba = o;
                    break;
                }
            }
        }
        if (oseba == null) return false;

        // Admin ima vse pravice
        if ("XXL".equalsIgnoreCase(oseba.getPrivilegiji()) || "ADMIN".equalsIgnoreCase(oseba.getOddelek())) {
            return true;
        }

        // Poišči POZICIJA_ID v PRIORITETE_PROJEKTOV
        int pozicijaId = -1;
        for (PrioritetaProjektaTp p : cachedPrioritete) {
            if (pravicaCaption.equalsIgnoreCase(p.getCaption().trim())) {
                pozicijaId = p.getPozicijaId();
                break;
            }
        }
        if (pozicijaId <= 0) return false;

        // V Delphi: if pomString[pomPozicija_ID] = '1' (1-based indeks)
        // V Javi: pravice.charAt(pozicijaId - 1) == '1'
        int idx = pozicijaId - 1;
        String pravice = oseba.getPravice();
        if (pravice != null && idx >= 0 && idx < pravice.length()) {
            return pravice.charAt(idx) == '1';
        }
        return false;
    }

    public synchronized boolean isDovoljeno(String pravicaCaption) {
        return osebiDovoljeno(this.tekocaOsebaId, pravicaCaption);
    }
}
