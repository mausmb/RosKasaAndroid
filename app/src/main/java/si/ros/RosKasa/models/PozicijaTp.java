package si.ros.RosKasa.models;

import java.math.BigDecimal;
import si.ros.RosKasa.Globals;

public class PozicijaTp {
    private int pozicijaId = -1;
    private int racunId = -1;
    private Integer nivo4Id;
    private Integer cenikId;
    private String naziv = "";
    private BigDecimal cena = BigDecimal.ZERO;
    private double kolicina = 1.0;
    private BigDecimal znesek = BigDecimal.ZERO;
    private double stopnjaDavka = 0.0;
    private BigDecimal znesekDavka = BigDecimal.ZERO;
    private BigDecimal znesekPopust = BigDecimal.ZERO;
    private Integer tarifaId;
    private int verzijaZapisa = 0;
    private boolean rowDeleted = false;
    private boolean neNarocaj = true;
    private String hod = "";
    private String dodatniOpis = "";
    private BigDecimal enotaProdajeId = BigDecimal.ONE;
    private Integer natakarId;
    private Integer tocilnicaId;
    private Integer kuhinjaId;
    private Integer izvorStrmId;
    private Integer izvorPrihodekId;
    private Integer posId;
    private Integer paketDistinct;
    private Integer paketNivo4Id = 0;
    private BigDecimal status = BigDecimal.ZERO;
    private BigDecimal cenaNabavna = BigDecimal.ZERO;
    private BigDecimal lojalnostPopust = BigDecimal.ZERO;
    private BigDecimal znesekLojalnost = BigDecimal.ZERO;
    private BigDecimal paketKol = BigDecimal.ONE;
    private Integer statusPoz = 0;
    private Integer narociloPoslano = 0;
    private String bonId = "";

    // Vgnezdeni predhodni objekt (prejšnje stanje za setRacun)
    private PozicijaTp originalObject;

    public PozicijaTp() {}

    public PozicijaTp(int pozicijaId, int racunId, Integer nivo4Id, String naziv, BigDecimal cena, double kolicina) {
        this(pozicijaId, racunId, nivo4Id, naziv, cena, kolicina, BigDecimal.ONE);
    }

    public PozicijaTp(int pozicijaId, int racunId, Integer nivo4Id, String naziv, BigDecimal cena, double kolicina, BigDecimal enotaProdajeId) {
        this.pozicijaId = pozicijaId;
        this.racunId = racunId;
        this.nivo4Id = nivo4Id;
        this.naziv = naziv != null ? naziv : "";
        this.cena = cena != null ? cena : BigDecimal.ZERO;
        this.kolicina = kolicina;
        this.enotaProdajeId = (enotaProdajeId != null && enotaProdajeId.compareTo(BigDecimal.ZERO) > 0) ? enotaProdajeId : BigDecimal.ONE;
        recalculateZnesek();
    }

    public PozicijaTp deepCopy() {
        PozicijaTp copy = new PozicijaTp();
        copy.pozicijaId = this.pozicijaId;
        copy.racunId = this.racunId;
        copy.nivo4Id = this.nivo4Id;
        copy.cenikId = this.cenikId;
        copy.naziv = this.naziv;
        copy.cena = this.cena;
        copy.kolicina = this.kolicina;
        copy.znesek = this.znesek;
        copy.stopnjaDavka = this.stopnjaDavka;
        copy.znesekDavka = this.znesekDavka;
        copy.znesekPopust = this.znesekPopust;
        copy.tarifaId = this.tarifaId;
        copy.verzijaZapisa = this.verzijaZapisa;
        copy.rowDeleted = this.rowDeleted;
        copy.neNarocaj = this.neNarocaj;
        copy.hod = this.hod;
        copy.dodatniOpis = this.dodatniOpis;
        copy.enotaProdajeId = this.enotaProdajeId;
        copy.natakarId = this.natakarId;
        copy.tocilnicaId = this.tocilnicaId;
        copy.kuhinjaId = this.kuhinjaId;
        copy.izvorStrmId = this.izvorStrmId;
        copy.izvorPrihodekId = this.izvorPrihodekId;
        copy.posId = this.posId;
        copy.paketDistinct = this.paketDistinct;
        copy.paketNivo4Id = this.paketNivo4Id;
        copy.status = this.status;
        copy.cenaNabavna = this.cenaNabavna;
        copy.lojalnostPopust = this.lojalnostPopust;
        copy.znesekLojalnost = this.znesekLojalnost;
        copy.paketKol = this.paketKol;
        copy.statusPoz = this.statusPoz;
        copy.narociloPoslano = this.narociloPoslano;
        copy.bonId = this.bonId;
        if (this.originalObject != null) {
            copy.originalObject = this.originalObject.deepCopy();
        }
        return copy;
    }

    // Getters and Setters
    public int getPozicijaId() { return pozicijaId; }
    public void setPozicijaId(int pozicijaId) { this.pozicijaId = pozicijaId; }

    public int getRacunId() { return racunId; }
    public void setRacunId(int racunId) { this.racunId = racunId; }

    public Integer getNivo4Id() { return nivo4Id; }
    public void setNivo4Id(Integer nivo4Id) { this.nivo4Id = nivo4Id; }

    public Integer getCenikId() { return cenikId; }
    public void setCenikId(Integer cenikId) { this.cenikId = cenikId; }

    public String getNazivRaw() {
        return naziv;
    }

    public String getNaziv() {
        if ((naziv == null || naziv.trim().isEmpty() || naziv.startsWith("Artikel #")) && nivo4Id != null && nivo4Id > 0) {
            String lookup = Globals.getInstance().findNazivByNivo4Id(nivo4Id);
            if (lookup != null && !lookup.trim().isEmpty()) {
                naziv = lookup.trim();
            }
        }
        if ((naziv == null || naziv.trim().isEmpty()) && nivo4Id != null && nivo4Id > 0) {
            return "Artikel #" + nivo4Id;
        }
        return naziv != null ? naziv : "";
    }

    public void setNaziv(String naziv) {
        this.naziv = (naziv != null) ? naziv.trim() : "";
        if (this.nivo4Id != null && this.nivo4Id > 0 && !this.naziv.isEmpty() && !this.naziv.startsWith("Artikel #")) {
            Globals.getInstance().registerNazivForNivo4(this.nivo4Id, this.naziv);
        }
    }

    public void recalculateZnesek() {
        if (this.cena == null) {
            this.znesek = BigDecimal.ZERO;
            return;
        }
        BigDecimal ep = (this.enotaProdajeId != null && this.enotaProdajeId.compareTo(BigDecimal.ZERO) > 0)
                ? this.enotaProdajeId : BigDecimal.ONE;
        this.znesek = this.cena.multiply(BigDecimal.valueOf(this.kolicina)).multiply(ep).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public BigDecimal getCena() { return cena; }
    public void setCena(BigDecimal cena) {
        this.cena = cena != null ? cena : BigDecimal.ZERO;
        recalculateZnesek();
    }

    public double getKolicina() { return kolicina; }
    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
        recalculateZnesek();
    }

    public BigDecimal getZnesek() { return znesek; }
    public void setZnesek(BigDecimal znesek) { this.znesek = znesek != null ? znesek : BigDecimal.ZERO; }

    public double getStopnjaDavka() { return stopnjaDavka; }
    public void setStopnjaDavka(double stopnjaDavka) { this.stopnjaDavka = stopnjaDavka; }

    public BigDecimal getZnesekDavka() { return znesekDavka; }
    public void setZnesekDavka(BigDecimal znesekDavka) { this.znesekDavka = znesekDavka; }

    public BigDecimal getZnesekPopust() { return znesekPopust; }
    public void setZnesekPopust(BigDecimal znesekPopust) { this.znesekPopust = znesekPopust; }

    public Integer getTarifaId() { return tarifaId; }
    public void setTarifaId(Integer tarifaId) { this.tarifaId = tarifaId; }

    public int getVerzijaZapisa() { return verzijaZapisa; }
    public void setVerzijaZapisa(int verzijaZapisa) { this.verzijaZapisa = verzijaZapisa; }

    public boolean isRowDeleted() { return rowDeleted; }
    public void setRowDeleted(boolean rowDeleted) { this.rowDeleted = rowDeleted; }

    public boolean isNeNarocaj() { return neNarocaj; }
    public void setNeNarocaj(boolean neNarocaj) { this.neNarocaj = neNarocaj; }

    public String getHod() { return hod; }
    public void setHod(String hod) { this.hod = hod != null ? hod : ""; }

    public String getDodatniOpis() { return dodatniOpis; }
    public void setDodatniOpis(String dodatniOpis) { this.dodatniOpis = dodatniOpis != null ? dodatniOpis : ""; }

    public BigDecimal getEnotaProdajeId() { return enotaProdajeId; }
    public void setEnotaProdajeId(BigDecimal enotaProdajeId) {
        this.enotaProdajeId = (enotaProdajeId != null && enotaProdajeId.compareTo(BigDecimal.ZERO) > 0)
                ? enotaProdajeId : BigDecimal.ONE;
        recalculateZnesek();
    }

    public Integer getNatakarId() { return natakarId; }
    public void setNatakarId(Integer natakarId) { this.natakarId = natakarId; }

    public Integer getTocilnicaId() { return tocilnicaId; }
    public void setTocilnicaId(Integer tocilnicaId) { this.tocilnicaId = tocilnicaId; }

    public Integer getKuhinjaId() { return kuhinjaId; }
    public void setKuhinjaId(Integer kuhinjaId) { this.kuhinjaId = kuhinjaId; }

    public Integer getIzvorStrmId() { return izvorStrmId; }
    public void setIzvorStrmId(Integer izvorStrmId) { this.izvorStrmId = izvorStrmId; }

    public Integer getIzvorPrihodekId() { return izvorPrihodekId; }
    public void setIzvorPrihodekId(Integer izvorPrihodekId) { this.izvorPrihodekId = izvorPrihodekId; }

    public Integer getPosId() { return posId; }
    public void setPosId(Integer posId) { this.posId = posId; }

    public Integer getPaketDistinct() { return paketDistinct; }
    public void setPaketDistinct(Integer paketDistinct) { this.paketDistinct = paketDistinct; }

    public Integer getPaketNivo4Id() { return paketNivo4Id; }
    public void setPaketNivo4Id(Integer paketNivo4Id) { this.paketNivo4Id = paketNivo4Id; }

    public PozicijaTp getOriginalObject() { return originalObject; }
    public void setOriginalObject(PozicijaTp originalObject) { this.originalObject = originalObject; }

    public BigDecimal getStatus() { return status; }
    public void setStatus(BigDecimal status) { this.status = status != null ? status : BigDecimal.ZERO; }

    public BigDecimal getCenaNabavna() { return cenaNabavna; }
    public void setCenaNabavna(BigDecimal cenaNabavna) { this.cenaNabavna = cenaNabavna != null ? cenaNabavna : BigDecimal.ZERO; }

    public BigDecimal getLojalnostPopust() { return lojalnostPopust; }
    public void setLojalnostPopust(BigDecimal lojalnostPopust) { this.lojalnostPopust = lojalnostPopust != null ? lojalnostPopust : BigDecimal.ZERO; }

    public BigDecimal getZnesekLojalnost() { return znesekLojalnost; }
    public void setZnesekLojalnost(BigDecimal znesekLojalnost) { this.znesekLojalnost = znesekLojalnost != null ? znesekLojalnost : BigDecimal.ZERO; }

    public BigDecimal getPaketKol() { return paketKol; }
    public void setPaketKol(BigDecimal paketKol) { this.paketKol = paketKol != null ? paketKol : BigDecimal.ONE; }

    public Integer getStatusPoz() { return statusPoz; }
    public void setStatusPoz(Integer statusPoz) { this.statusPoz = statusPoz; }

    public Integer getNarociloPoslano() { return narociloPoslano; }
    public void setNarociloPoslano(Integer narociloPoslano) { this.narociloPoslano = narociloPoslano; }

    public String getBonId() { return bonId; }
    public void setBonId(String bonId) { this.bonId = bonId != null ? bonId : ""; }
}
