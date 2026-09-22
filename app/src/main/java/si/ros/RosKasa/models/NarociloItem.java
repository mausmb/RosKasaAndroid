package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class NarociloItem {
    private int pozicijaId = 0;
    private int nivo4Id;
    private String naziv;
    private BigDecimal cena;
    private double kolicina;
    private double ep; // Enota prodaje / polnjenje (točenje)
    private int paket;
    private int nivo1Id;
    private int tarifaId;
    private int izvorStrmId;
    private int prihodkeStrmId;
    private double davekProc;
    private int dodatekId;

    public NarociloItem(String naziv, BigDecimal cena, double kolicina) {
        this(0, naziv, cena, kolicina, 1.0, 0, 0, 0, 0, 0, 22.0, 0);
    }

    public NarociloItem(int nivo4Id, String naziv, BigDecimal cena, double kolicina, double ep,
                        int paket, int nivo1Id, int tarifaId, int izvorStrmId, int prihodkeStrmId,
                        double davekProc, int dodatekId) {
        this.nivo4Id = nivo4Id;
        this.naziv = naziv;
        this.cena = cena;
        this.kolicina = kolicina;
        this.ep = ep;
        this.paket = paket;
        this.nivo1Id = nivo1Id;
        this.tarifaId = tarifaId;
        this.izvorStrmId = izvorStrmId;
        this.prihodkeStrmId = prihodkeStrmId;
        this.davekProc = davekProc;
        this.dodatekId = dodatekId;
    }

    public int getPozicijaId() {
        return pozicijaId;
    }

    public void setPozicijaId(int pozicijaId) {
        this.pozicijaId = pozicijaId;
    }

    public int getNivo4Id() {
        return nivo4Id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public double getEp() {
        return ep;
    }

    public void setEp(double ep) {
        this.ep = ep;
    }

    public int getPaket() {
        return paket;
    }

    public int getNivo1Id() {
        return nivo1Id;
    }

    public int getTarifaId() {
        return tarifaId;
    }

    public int getIzvorStrmId() {
        return izvorStrmId;
    }

    public int getPrihodkeStrmId() {
        return prihodkeStrmId;
    }

    public double getDavekProc() {
        return davekProc;
    }

    public int getDodatekId() {
        return dodatekId;
    }

    public BigDecimal getZnesek() {
        return cena.multiply(BigDecimal.valueOf(kolicina));
    }
}
