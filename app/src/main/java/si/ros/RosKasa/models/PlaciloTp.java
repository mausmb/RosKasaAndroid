package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class PlaciloTp {
    private int placiloId;
    private int racunId;
    private int pozicijaId = 0;
    private Integer tocilnicaId;
    private Integer partnerId;
    private BigDecimal znesek = BigDecimal.ZERO;
    private BigDecimal delniZnesek = BigDecimal.ZERO;
    private Integer vrstaReklame; // ID načina plačila
    private BigDecimal tecaj = BigDecimal.ONE;
    private Integer valutaId;
    private int verzijaZapisa = 0;
    private boolean rowDeleted = false;
    private String stKartice = "";
    private String davcnaSt = "";
    private String nazivPartner = "";
    private String naslovPartner = "";
    private String bonId = "";
    private String kuponId = "";
    private BigDecimal napitnina = BigDecimal.ZERO;
    private BigDecimal status = BigDecimal.ZERO;

    // Vgnezdeni predhodni objekt
    private PlaciloTp originalObject;

    public PlaciloTp() {}

    public PlaciloTp(int racunId, Integer vrstaReklame, BigDecimal znesek) {
        this.racunId = racunId;
        this.placiloId = vrstaReklame != null ? vrstaReklame : 1;
        this.vrstaReklame = this.placiloId;
        this.znesek = znesek != null ? znesek : BigDecimal.ZERO;
        this.delniZnesek = this.znesek;
    }

    public PlaciloTp deepCopy() {
        PlaciloTp copy = new PlaciloTp();
        copy.placiloId = this.placiloId;
        copy.racunId = this.racunId;
        copy.pozicijaId = this.pozicijaId;
        copy.tocilnicaId = this.tocilnicaId;
        copy.partnerId = this.partnerId;
        copy.znesek = this.znesek;
        copy.delniZnesek = this.delniZnesek;
        copy.vrstaReklame = this.vrstaReklame;
        copy.tecaj = this.tecaj;
        copy.valutaId = this.valutaId;
        copy.verzijaZapisa = this.verzijaZapisa;
        copy.rowDeleted = this.rowDeleted;
        copy.stKartice = this.stKartice;
        copy.davcnaSt = this.davcnaSt;
        copy.nazivPartner = this.nazivPartner;
        copy.naslovPartner = this.naslovPartner;
        copy.bonId = this.bonId;
        copy.kuponId = this.kuponId;
        copy.napitnina = this.napitnina;
        copy.status = this.status;
        if (this.originalObject != null) {
            copy.originalObject = this.originalObject.deepCopy();
        }
        return copy;
    }

    // Getters and Setters
    public int getPlaciloId() { return placiloId; }
    public void setPlaciloId(int placiloId) { this.placiloId = placiloId; }

    public int getRacunId() { return racunId; }
    public void setRacunId(int racunId) { this.racunId = racunId; }

    public BigDecimal getZnesek() { return znesek; }
    public void setZnesek(BigDecimal znesek) { this.znesek = znesek != null ? znesek : BigDecimal.ZERO; }

    public BigDecimal getDelniZnesek() { return delniZnesek; }
    public void setDelniZnesek(BigDecimal delniZnesek) { this.delniZnesek = delniZnesek != null ? delniZnesek : BigDecimal.ZERO; }

    public Integer getVrstaReklame() { return vrstaReklame; }
    public void setVrstaReklame(Integer vrstaReklame) { this.vrstaReklame = vrstaReklame; }

    public BigDecimal getTecaj() { return tecaj; }
    public void setTecaj(BigDecimal tecaj) { this.tecaj = tecaj != null ? tecaj : BigDecimal.ONE; }

    public Integer getValutaId() { return valutaId; }
    public void setValutaId(Integer valutaId) { this.valutaId = valutaId; }

    public int getVerzijaZapisa() { return verzijaZapisa; }
    public void setVerzijaZapisa(int verzijaZapisa) { this.verzijaZapisa = verzijaZapisa; }

    public boolean isRowDeleted() { return rowDeleted; }
    public void setRowDeleted(boolean rowDeleted) { this.rowDeleted = rowDeleted; }

    public String getStKartice() { return stKartice; }
    public void setStKartice(String stKartice) { this.stKartice = stKartice != null ? stKartice : ""; }

    public String getDavcnaSt() { return davcnaSt; }
    public void setDavcnaSt(String davcnaSt) { this.davcnaSt = davcnaSt != null ? davcnaSt : ""; }

    public String getNazivPartner() { return nazivPartner; }
    public void setNazivPartner(String nazivPartner) { this.nazivPartner = nazivPartner != null ? nazivPartner : ""; }

    public String getNaslovPartner() { return naslovPartner; }
    public void setNaslovPartner(String naslovPartner) { this.naslovPartner = naslovPartner != null ? naslovPartner : ""; }

    public String getBonId() { return bonId; }
    public void setBonId(String bonId) { this.bonId = bonId != null ? bonId : ""; }

    public String getKuponId() { return kuponId; }
    public void setKuponId(String kuponId) { this.kuponId = kuponId != null ? kuponId : ""; }

    public BigDecimal getNapitnina() { return napitnina; }
    public void setNapitnina(BigDecimal napitnina) { this.napitnina = napitnina != null ? napitnina : BigDecimal.ZERO; }

    public PlaciloTp getOriginalObject() { return originalObject; }
    public void setOriginalObject(PlaciloTp originalObject) { this.originalObject = originalObject; }

    public int getPozicijaId() { return pozicijaId; }
    public void setPozicijaId(int pozicijaId) { this.pozicijaId = pozicijaId; }

    public Integer getTocilnicaId() { return tocilnicaId; }
    public void setTocilnicaId(Integer tocilnicaId) { this.tocilnicaId = tocilnicaId; }

    public Integer getPartnerId() { return partnerId; }
    public void setPartnerId(Integer partnerId) { this.partnerId = partnerId; }

    public BigDecimal getStatus() { return status; }
    public void setStatus(BigDecimal status) { this.status = status != null ? status : BigDecimal.ZERO; }
}
