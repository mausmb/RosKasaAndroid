package si.ros.RosKasa.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RacunTp {
    private int racunId = -1;
    private int verzijaZapisa = 0;
    private int status = 1; // 1 = v obdelavi / naročilo, 2 = izpisan račun, 4 = obračunan
    private String marker = ""; // miza npr. "Miza 5"
    private BigDecimal znesek = BigDecimal.ZERO;
    private BigDecimal placano = BigDecimal.ZERO;
    private Integer kasiral = 9999;
    private Integer fPosId = 500;
    private Integer fPoslovniProstorId = 5000;
    private Integer tocilnicaId = 512200;
    private Integer blagId;
    private Integer tip;
    private Integer tipRacuna = 1;
    private Integer vStatus;
    private Integer fStevilkaRacuna;
    private String fOznakaDu = "";
    private String fPodpis = "";
    private String datum = "";
    private String ura = "";
    private String uraPlacila = "";
    private boolean rowDeleted = false;
    private String opomba = "";
    private Integer fiskalizacija;
    private Integer lojalnostId;
    private Integer stKopij = 0;
    private Integer stPogrinjkov = 1;
    private Integer stornoOsebaId;
    private Integer stornoRacunId;
    private Integer stornoOriginal;
    private Integer stornoRazlogId;
    private Integer urejamStorno;
    private String fPrintKoda = "";
    private String fPrintVrsta = "";
    private String crmId = "";
    private String dnId = "";
    private String lokator = "";
    private Integer partnerId;

    private List<PozicijaTp> racPozic = new ArrayList<>();
    private List<PlaciloTp> racPlaci = new ArrayList<>();

    // Vgnezdeni predhodni objekt (prejšnje stanje za setRacun)
    private RacunTp originalObject;

    public RacunTp() {}

    public RacunTp(int racunId, String marker) {
        this.racunId = racunId;
        this.marker = marker != null ? marker : "";
        this.status = 1;
    }

    public RacunTp deepCopy() {
        RacunTp copy = new RacunTp();
        copy.racunId = this.racunId;
        copy.verzijaZapisa = this.verzijaZapisa;
        copy.status = this.status;
        copy.marker = this.marker;
        copy.znesek = this.znesek;
        copy.placano = this.placano;
        copy.kasiral = this.kasiral;
        copy.fPosId = this.fPosId;
        copy.fPoslovniProstorId = this.fPoslovniProstorId;
        copy.tocilnicaId = this.tocilnicaId;
        copy.blagId = this.blagId;
        copy.tip = this.tip;
        copy.tipRacuna = this.tipRacuna;
        copy.vStatus = this.vStatus;
        copy.fStevilkaRacuna = this.fStevilkaRacuna;
        copy.fOznakaDu = this.fOznakaDu;
        copy.fPodpis = this.fPodpis;
        copy.datum = this.datum;
        copy.ura = this.ura;
        copy.uraPlacila = this.uraPlacila;
        copy.rowDeleted = this.rowDeleted;
        copy.opomba = this.opomba;
        copy.fiskalizacija = this.fiskalizacija;
        copy.lojalnostId = this.lojalnostId;
        copy.stKopij = this.stKopij;
        copy.stPogrinjkov = this.stPogrinjkov;
        copy.stornoOsebaId = this.stornoOsebaId;
        copy.stornoRacunId = this.stornoRacunId;
        copy.stornoOriginal = this.stornoOriginal;
        copy.stornoRazlogId = this.stornoRazlogId;
        copy.urejamStorno = this.urejamStorno;
        copy.fPrintKoda = this.fPrintKoda;
        copy.fPrintVrsta = this.fPrintVrsta;
        copy.crmId = this.crmId;
        copy.dnId = this.dnId;
        copy.lokator = this.lokator;
        copy.partnerId = this.partnerId;

        if (this.originalObject != null) {
            copy.originalObject = this.originalObject.deepCopy();
        }

        copy.racPozic = new ArrayList<>();
        if (this.racPozic != null) {
            for (PozicijaTp p : this.racPozic) {
                if (p != null) copy.racPozic.add(p.deepCopy());
            }
        }

        copy.racPlaci = new ArrayList<>();
        if (this.racPlaci != null) {
            for (PlaciloTp pl : this.racPlaci) {
                if (pl != null) copy.racPlaci.add(pl.deepCopy());
            }
        }

        return copy;
    }

    /**
     * Izracuna sumo narocila iz postavk skladno z Delphi TdmGisOrder.SumaNarocila:
     * Vrednost := tblRacPozicZnesek - tblRacPozicZNESEK_POPUST - tblRacPozicZNESEK_LOJALNOST;
     * Result := Result + Vrednost;
     *
     * To je VEDNO RACGLAVA.ZNESEK!
     */
    public BigDecimal izracunajSumoNarocila() {
        if (racPozic == null || racPozic.isEmpty()) {
            return this.znesek != null ? this.znesek : BigDecimal.ZERO;
        }
        BigDecimal suma = BigDecimal.ZERO;
        boolean hasActive = false;
        for (PozicijaTp p : racPozic) {
            if (p != null && !p.isRowDeleted()) {
                hasActive = true;
                BigDecimal kol = BigDecimal.valueOf(p.getKolicina());
                BigDecimal ep = (p.getEnotaProdajeId() != null && p.getEnotaProdajeId().compareTo(BigDecimal.ZERO) > 0)
                        ? p.getEnotaProdajeId() : BigDecimal.ONE;
                BigDecimal cena = p.getCena() != null ? p.getCena() : BigDecimal.ZERO;
                BigDecimal polna = cena.multiply(kol).multiply(ep).setScale(2, java.math.RoundingMode.HALF_UP);

                // ZNESEK na racpozic vedno odraža cena * kolicina * ep (cena ostane iz cenika, ep se upošteva v znesku)
                p.setZnesek(polna);

                BigDecimal pop = (p.getZnesekPopust() != null) ? p.getZnesekPopust().abs() : BigDecimal.ZERO;
                BigDecimal loj = (p.getZnesekLojalnost() != null) ? p.getZnesekLojalnost().abs() : BigDecimal.ZERO;

                BigDecimal vrednost = polna.subtract(pop).subtract(loj);
                suma = suma.add(vrednost);
            }
        }
        return hasActive ? suma : (this.znesek != null ? this.znesek : BigDecimal.ZERO);
    }

    /**
     * Posodobi RACGLAVA.ZNESEK iz postavk narocila (Delphi SumaNarocila).
     */
    public void posodobiZnesekIzNarocila() {
        this.znesek = izracunajSumoNarocila();
    }

    public void preracunajPlacano() {
        BigDecimal sumPlacano = BigDecimal.ZERO;
        if (racPlaci != null && !racPlaci.isEmpty()) {
            for (PlaciloTp pl : racPlaci) {
                if (pl != null && !pl.isRowDeleted()) {
                    // placilo_id == 99 is a discount, delni_znesek = 0, does not count towards placano
                    if (pl.getPlaciloId() != 99) {
                        BigDecimal amt = (pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0)
                                ? pl.getDelniZnesek()
                                : (pl.getZnesek() != null ? pl.getZnesek() : BigDecimal.ZERO);
                        sumPlacano = sumPlacano.add(amt);
                    }
                }
            }
        }
        this.placano = sumPlacano;
    }

    public void preracunajVsote() {
        preracunajPlacano();
        // 100% KONTROLA ZA RACGLAVA.ZNESEK:
        // RACGLAVA.ZNESEK je VEDNO suma narocila.
        // NIKOLI ga ne spreminjamo tukaj, ce je ze postavljen!
        // Inicializiramo ga le, ce je se null ali 0 in imamo pozicije.
        if (this.znesek == null || (this.znesek.compareTo(BigDecimal.ZERO) == 0 && racPozic != null && !racPozic.isEmpty())) {
            this.znesek = izracunajSumoNarocila();
        }
    }

    // Getters and Setters
    public int getRacunId() { return racunId; }
    public void setRacunId(int racunId) { this.racunId = racunId; }

    public int getVerzijaZapisa() { return verzijaZapisa; }
    public void setVerzijaZapisa(int verzijaZapisa) { this.verzijaZapisa = verzijaZapisa; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMarker() { return marker; }
    public void setMarker(String marker) { this.marker = marker != null ? marker : ""; }

    public BigDecimal getZnesek() { return znesek != null ? znesek : BigDecimal.ZERO; }
    public void setZnesek(BigDecimal znesek) { this.znesek = znesek != null ? znesek : BigDecimal.ZERO; }

    public BigDecimal getPlacano() { return placano != null ? placano : BigDecimal.ZERO; }
    public void setPlacano(BigDecimal placano) { this.placano = placano != null ? placano : BigDecimal.ZERO; }

    public boolean isPlacan() {
        if (status == 2) return true;
        if (znesek != null && placano != null && znesek.compareTo(BigDecimal.ZERO) > 0) {
            return placano.compareTo(znesek) >= 0;
        }
        return false;
    }

    public Integer getKasiral() { return kasiral; }
    public void setKasiral(Integer kasiral) { this.kasiral = kasiral; }

    public Integer getfPosId() { return fPosId; }
    public void setfPosId(Integer fPosId) { this.fPosId = fPosId; }

    public Integer getfPoslovniProstorId() { return fPoslovniProstorId; }
    public void setfPoslovniProstorId(Integer fPoslovniProstorId) { this.fPoslovniProstorId = fPoslovniProstorId; }

    public Integer getTocilnicaId() { return tocilnicaId; }
    public void setTocilnicaId(Integer tocilnicaId) { this.tocilnicaId = tocilnicaId; }

    public Integer getBlagId() { return blagId; }
    public void setBlagId(Integer blagId) { this.blagId = blagId; }

    public Integer getTip() { return tip; }
    public void setTip(Integer tip) { this.tip = tip; }

    public Integer getTipRacuna() { return tipRacuna; }
    public void setTipRacuna(Integer tipRacuna) { this.tipRacuna = tipRacuna; }

    public Integer getvStatus() { return vStatus; }
    public void setvStatus(Integer vStatus) { this.vStatus = vStatus; }

    public Integer getfStevilkaRacuna() { return fStevilkaRacuna; }
    public void setfStevilkaRacuna(Integer fStevilkaRacuna) { this.fStevilkaRacuna = fStevilkaRacuna; }

    public String getfOznakaDu() { return fOznakaDu; }
    public void setfOznakaDu(String fOznakaDu) { this.fOznakaDu = fOznakaDu != null ? fOznakaDu : ""; }

    public String getfPodpis() { return fPodpis; }
    public void setfPodpis(String fPodpis) { this.fPodpis = fPodpis != null ? fPodpis : ""; }

    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum != null ? datum : ""; }

    public String getUra() { return ura; }
    public void setUra(String ura) { this.ura = ura != null ? ura : ""; }

    public boolean isRowDeleted() { return rowDeleted; }
    public void setRowDeleted(boolean rowDeleted) { this.rowDeleted = rowDeleted; }

    public String getOpomba() { return opomba; }
    public void setOpomba(String opomba) { this.opomba = opomba != null ? opomba : ""; }

    public List<PozicijaTp> getRacPozic() { return racPozic; }
    public void setRacPozic(List<PozicijaTp> racPozic) { this.racPozic = racPozic != null ? racPozic : new ArrayList<>(); }

    public List<PlaciloTp> getRacPlaci() { return racPlaci; }
    public void setRacPlaci(List<PlaciloTp> racPlaci) { this.racPlaci = racPlaci != null ? racPlaci : new ArrayList<>(); }

    public RacunTp getOriginalObject() { return originalObject; }
    public void setOriginalObject(RacunTp originalObject) { this.originalObject = originalObject; }

    public Integer getFiskalizacija() { return fiskalizacija; }
    public void setFiskalizacija(Integer fiskalizacija) { this.fiskalizacija = fiskalizacija; }

    public Integer getLojalnostId() { return lojalnostId; }
    public void setLojalnostId(Integer lojalnostId) { this.lojalnostId = lojalnostId; }

    public Integer getStKopij() { return stKopij; }
    public void setStKopij(Integer stKopij) { this.stKopij = stKopij; }

    public Integer getStPogrinjkov() { return stPogrinjkov; }
    public void setStPogrinjkov(Integer stPogrinjkov) { this.stPogrinjkov = stPogrinjkov; }

    public Integer getStornoOsebaId() { return stornoOsebaId; }
    public void setStornoOsebaId(Integer stornoOsebaId) { this.stornoOsebaId = stornoOsebaId; }

    public Integer getStornoRacunId() { return stornoRacunId; }
    public void setStornoRacunId(Integer stornoRacunId) { this.stornoRacunId = stornoRacunId; }

    public Integer getStornoOriginal() { return stornoOriginal; }
    public void setStornoOriginal(Integer stornoOriginal) { this.stornoOriginal = stornoOriginal; }

    public Integer getStornoRazlogId() { return stornoRazlogId; }
    public void setStornoRazlogId(Integer stornoRazlogId) { this.stornoRazlogId = stornoRazlogId; }

    public Integer getUrejamStorno() { return urejamStorno; }
    public void setUrejamStorno(Integer urejamStorno) { this.urejamStorno = urejamStorno; }

    public String getfPrintKoda() { return fPrintKoda; }
    public void setfPrintKoda(String fPrintKoda) { this.fPrintKoda = fPrintKoda != null ? fPrintKoda : ""; }

    public String getfPrintVrsta() { return fPrintVrsta; }
    public void setfPrintVrsta(String fPrintVrsta) { this.fPrintVrsta = fPrintVrsta != null ? fPrintVrsta : ""; }

    public String getCrmId() { return crmId; }
    public void setCrmId(String crmId) { this.crmId = crmId != null ? crmId : ""; }

    public String getDnId() { return dnId; }
    public void setDnId(String dnId) { this.dnId = dnId != null ? dnId : ""; }

    public String getLokator() { return lokator; }
    public void setLokator(String lokator) { this.lokator = lokator != null ? lokator : ""; }

    public String getUraPlacila() { return uraPlacila; }
    public void setUraPlacila(String uraPlacila) { this.uraPlacila = uraPlacila != null ? uraPlacila : ""; }

    public Integer getPartnerId() { return partnerId; }
    public void setPartnerId(Integer partnerId) { this.partnerId = partnerId; }
}

