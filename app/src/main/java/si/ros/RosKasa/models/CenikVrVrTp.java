package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class CenikVrVrTp {
    private int cenikId;
    private int cenikvrnivo4Id; // Paket NIVO4_ID
    private int nivo4Id;        // Komponenta NIVO4_ID
    private double kolicina = 1.0;
    private BigDecimal cena1 = BigDecimal.ZERO;
    private BigDecimal cena2 = BigDecimal.ZERO;
    private int izvorStrmId;
    private int izvorPrihodekId;
    private int nivo1Id;
    private int tarifaId;
    private int popustDane;

    public CenikVrVrTp() {}

    public CenikVrVrTp(int cenikvrnivo4Id, int nivo4Id, double kolicina, BigDecimal cena1, BigDecimal cena2) {
        this.cenikvrnivo4Id = cenikvrnivo4Id;
        this.nivo4Id = nivo4Id;
        this.kolicina = kolicina;
        this.cena1 = cena1 != null ? cena1 : BigDecimal.ZERO;
        this.cena2 = cena2 != null ? cena2 : BigDecimal.ZERO;
    }

    public int getCenikId() { return cenikId; }
    public void setCenikId(int cenikId) { this.cenikId = cenikId; }

    public int getCenikvrnivo4Id() { return cenikvrnivo4Id; }
    public void setCenikvrnivo4Id(int cenikvrnivo4Id) { this.cenikvrnivo4Id = cenikvrnivo4Id; }

    public int getNivo4Id() { return nivo4Id; }
    public void setNivo4Id(int nivo4Id) { this.nivo4Id = nivo4Id; }

    public double getKolicina() { return kolicina; }
    public void setKolicina(double kolicina) { this.kolicina = kolicina; }

    public BigDecimal getCena1() { return cena1; }
    public void setCena1(BigDecimal cena1) { this.cena1 = cena1 != null ? cena1 : BigDecimal.ZERO; }

    public BigDecimal getCena2() { return cena2; }
    public void setCena2(BigDecimal cena2) { this.cena2 = cena2 != null ? cena2 : BigDecimal.ZERO; }

    public int getIzvorStrmId() { return izvorStrmId; }
    public void setIzvorStrmId(int izvorStrmId) { this.izvorStrmId = izvorStrmId; }

    public int getIzvorPrihodekId() { return izvorPrihodekId; }
    public void setIzvorPrihodekId(int izvorPrihodekId) { this.izvorPrihodekId = izvorPrihodekId; }

    public int getNivo1Id() { return nivo1Id; }
    public void setNivo1Id(int nivo1Id) { this.nivo1Id = nivo1Id; }

    public int getTarifaId() { return tarifaId; }
    public void setTarifaId(int tarifaId) { this.tarifaId = tarifaId; }

    public int getPopustDane() { return popustDane; }
    public void setPopustDane(int popustDane) { this.popustDane = popustDane; }
}
