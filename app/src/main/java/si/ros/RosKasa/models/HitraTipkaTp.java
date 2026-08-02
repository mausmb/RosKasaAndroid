package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class HitraTipkaTp {
    private int tipkaId;
    private String naziv;
    private Integer barva;
    private Integer skupinaId;
    private Integer nivo4Id;
    private BigDecimal enotaProdaje;
    private Integer kolicina;
    private Integer dodatekId;

    public HitraTipkaTp() {}

    public HitraTipkaTp(int tipkaId, String naziv, Integer skupinaId, Integer nivo4Id, Integer barva) {
        this.tipkaId = tipkaId;
        this.naziv = naziv;
        this.skupinaId = skupinaId;
        this.nivo4Id = nivo4Id;
        this.barva = barva;
    }

    public int getTipkaId() {
        return tipkaId;
    }

    public void setTipkaId(int tipkaId) {
        this.tipkaId = tipkaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBarva() {
        return barva;
    }

    public void setBarva(Integer barva) {
        this.barva = barva;
    }

    public Integer getSkupinaId() {
        return skupinaId;
    }

    public void setSkupinaId(Integer skupinaId) {
        this.skupinaId = skupinaId;
    }

    public Integer getNivo4Id() {
        return nivo4Id;
    }

    public void setNivo4Id(Integer nivo4Id) {
        this.nivo4Id = nivo4Id;
    }

    public BigDecimal getEnotaProdaje() {
        return enotaProdaje;
    }

    public void setEnotaProdaje(BigDecimal enotaProdaje) {
        this.enotaProdaje = enotaProdaje;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getDodatekId() {
        return dodatekId;
    }

    public void setDodatekId(Integer dodatekId) {
        this.dodatekId = dodatekId;
    }
}
