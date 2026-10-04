package si.ros.RosKasa.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import si.ros.RosKasa.models.AkcijaTp;
import si.ros.RosKasa.models.RacunTp;

/**
 * Podatkovni vsebnik za tiskalni posel, skladen s strukturo TprintDataWS iz Delphi uPrintData.pas.
 */
public class PrintDataWS {
    private RacunTp racun;
    private int racunId;
    private int prijavaId;
    private int hisCenikAi;
    private int partnerId;
    private String crmId = "";
    private int akcijaId;
    private int stornoRazlogId;

    private String akcijaTipNaziv = "";
    private String akcijaNaziv = "";
    private String akcijaKuponId = "";
    private String slipString = "";
    private String prijavaImeGosta = "";
    private String hisCenikNaziv = "";
    private String partnerNaziv = "";
    private String partnerNaslov = "";
    private String partnerDavcna = "";
    private String stNarocilnice = "";
    private String stornoRazlog = "";

    private String fPodpis = "";
    private String fOznakaDu = "";
    private BigDecimal znesek = BigDecimal.ZERO;
    private int kasiral;

    private int zaPrintStKopij = 1;
    private int konStKopij = 1;
    private int zacStKopij = 0;

    private boolean akcijePrint = false;
    private List<AkcijaTp> akcijeList = new ArrayList<>();
    private List<String> blok1Akcije = new ArrayList<>();

    private boolean preview = false;

    public PrintDataWS() {}

    public void initData() {
        racun = null;
        racunId = 0;
        prijavaId = 0;
        hisCenikAi = 0;
        partnerId = 0;
        crmId = "";
        akcijaId = 0;
        stornoRazlogId = 0;

        akcijaTipNaziv = "";
        akcijaNaziv = "";
        akcijaKuponId = "";
        slipString = "";
        prijavaImeGosta = "";
        hisCenikNaziv = "";
        partnerNaziv = "";
        partnerNaslov = "";
        partnerDavcna = "";
        stNarocilnice = "";
        stornoRazlog = "";

        fPodpis = "";
        fOznakaDu = "";
        znesek = BigDecimal.ZERO;
        kasiral = 0;

        zaPrintStKopij = 1;
        konStKopij = 1;
        zacStKopij = 0;

        akcijePrint = false;
        akcijeList.clear();
        blok1Akcije.clear();
        preview = false;
    }

    public RacunTp getRacun() {
        return racun;
    }

    public void setRacun(RacunTp racun) {
        this.racun = racun;
    }

    public int getRacunId() {
        return racunId;
    }

    public void setRacunId(int racunId) {
        this.racunId = racunId;
    }

    public int getPrijavaId() {
        return prijavaId;
    }

    public void setPrijavaId(int prijavaId) {
        this.prijavaId = prijavaId;
    }

    public int getHisCenikAi() {
        return hisCenikAi;
    }

    public void setHisCenikAi(int hisCenikAi) {
        this.hisCenikAi = hisCenikAi;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getCrmId() {
        return crmId != null ? crmId : "";
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public int getAkcijaId() {
        return akcijaId;
    }

    public void setAkcijaId(int akcijaId) {
        this.akcijaId = akcijaId;
    }

    public int getStornoRazlogId() {
        return stornoRazlogId;
    }

    public void setStornoRazlogId(int stornoRazlogId) {
        this.stornoRazlogId = stornoRazlogId;
    }

    public String getAkcijaTipNaziv() {
        return akcijaTipNaziv != null ? akcijaTipNaziv : "";
    }

    public void setAkcijaTipNaziv(String akcijaTipNaziv) {
        this.akcijaTipNaziv = akcijaTipNaziv;
    }

    public String getAkcijaNaziv() {
        return akcijaNaziv != null ? akcijaNaziv : "";
    }

    public void setAkcijaNaziv(String akcijaNaziv) {
        this.akcijaNaziv = akcijaNaziv;
    }

    public String getAkcijaKuponId() {
        return akcijaKuponId != null ? akcijaKuponId : "";
    }

    public void setAkcijaKuponId(String akcijaKuponId) {
        this.akcijaKuponId = akcijaKuponId;
    }

    public String getSlipString() {
        return slipString != null ? slipString : "";
    }

    public void setSlipString(String slipString) {
        this.slipString = slipString;
    }

    public String getPrijavaImeGosta() {
        return prijavaImeGosta != null ? prijavaImeGosta : "";
    }

    public void setPrijavaImeGosta(String prijavaImeGosta) {
        this.prijavaImeGosta = prijavaImeGosta;
    }

    public String getHisCenikNaziv() {
        return hisCenikNaziv != null ? hisCenikNaziv : "";
    }

    public void setHisCenikNaziv(String hisCenikNaziv) {
        this.hisCenikNaziv = hisCenikNaziv;
    }

    public String getPartnerNaziv() {
        return partnerNaziv != null ? partnerNaziv : "";
    }

    public void setPartnerNaziv(String partnerNaziv) {
        this.partnerNaziv = partnerNaziv;
    }

    public String getPartnerNaslov() {
        return partnerNaslov != null ? partnerNaslov : "";
    }

    public void setPartnerNaslov(String partnerNaslov) {
        this.partnerNaslov = partnerNaslov;
    }

    public String getPartnerDavcna() {
        return partnerDavcna != null ? partnerDavcna : "";
    }

    public void setPartnerDavcna(String partnerDavcna) {
        this.partnerDavcna = partnerDavcna;
    }

    public String getStNarocilnice() {
        return stNarocilnice != null ? stNarocilnice : "";
    }

    public void setStNarocilnice(String stNarocilnice) {
        this.stNarocilnice = stNarocilnice;
    }

    public String getStornoRazlog() {
        return stornoRazlog != null ? stornoRazlog : "";
    }

    public void setStornoRazlog(String stornoRazlog) {
        this.stornoRazlog = stornoRazlog;
    }

    public String getFPodpis() {
        return fPodpis != null ? fPodpis : "";
    }

    public void setFPodpis(String fPodpis) {
        this.fPodpis = fPodpis;
    }

    public String getFOznakaDu() {
        return fOznakaDu != null ? fOznakaDu : "";
    }

    public void setFOznakaDu(String fOznakaDu) {
        this.fOznakaDu = fOznakaDu;
    }

    public BigDecimal getZnesek() {
        return znesek != null ? znesek : BigDecimal.ZERO;
    }

    public void setZnesek(BigDecimal znesek) {
        this.znesek = znesek;
    }

    public int getKasiral() {
        return kasiral;
    }

    public void setKasiral(int kasiral) {
        this.kasiral = kasiral;
    }

    public int getZaPrintStKopij() {
        return zaPrintStKopij;
    }

    public void setZaPrintStKopij(int zaPrintStKopij) {
        this.zaPrintStKopij = zaPrintStKopij;
    }

    public int getKonStKopij() {
        return konStKopij;
    }

    public void setKonStKopij(int konStKopij) {
        this.konStKopij = konStKopij;
    }

    public int getZacStKopij() {
        return zacStKopij;
    }

    public void setZacStKopij(int zacStKopij) {
        this.zacStKopij = zacStKopij;
    }

    public boolean isAkcijePrint() {
        return akcijePrint;
    }

    public void setAkcijePrint(boolean akcijePrint) {
        this.akcijePrint = akcijePrint;
    }

    public List<AkcijaTp> getAkcijeList() {
        return akcijeList;
    }

    public void setAkcijeList(List<AkcijaTp> akcijeList) {
        this.akcijeList = akcijeList != null ? akcijeList : new ArrayList<>();
    }

    public List<String> getBlok1Akcije() {
        return blok1Akcije;
    }

    public void setBlok1Akcije(List<String> blok1Akcije) {
        this.blok1Akcije = blok1Akcije != null ? blok1Akcije : new ArrayList<>();
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }
}
