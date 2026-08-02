package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class NarociloItem {
    private String naziv;
    private BigDecimal cena;
    private double kolicina;

    public NarociloItem(String naziv, BigDecimal cena, double kolicina) {
        this.naziv = naziv;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public String getNaziv() {
        return naziv;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getZnesek() {
        return cena.multiply(BigDecimal.valueOf(kolicina));
    }
}
