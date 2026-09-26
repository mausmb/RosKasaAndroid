package si.ros.RosKasa.models;

public class KartprijTp {
    private int prijavaId;
    private int prostorId;
    private int obratId;
    private String imeGosta = "";
    private String nazivStoritve = "";
    private String nazivStrm = "";
    private String tipKart = ""; // "X" (v odhodu), "S" (Blokada HK)

    public KartprijTp() {}

    public int getPrijavaId() {
        return prijavaId;
    }

    public void setPrijavaId(int prijavaId) {
        this.prijavaId = prijavaId;
    }

    public int getProstorId() {
        return prostorId;
    }

    public void setProstorId(int prostorId) {
        this.prostorId = prostorId;
    }

    public int getObratId() {
        return obratId;
    }

    public void setObratId(int obratId) {
        this.obratId = obratId;
    }

    public String getImeGosta() {
        return imeGosta != null ? imeGosta : "";
    }

    public void setImeGosta(String imeGosta) {
        this.imeGosta = imeGosta;
    }

    public String getNazivStoritve() {
        return nazivStoritve != null ? nazivStoritve : "";
    }

    public void setNazivStoritve(String nazivStoritve) {
        this.nazivStoritve = nazivStoritve;
    }

    public String getNazivStrm() {
        return nazivStrm != null ? nazivStrm : "";
    }

    public void setNazivStrm(String nazivStrm) {
        this.nazivStrm = nazivStrm;
    }

    public String getTipKart() {
        return tipKart != null ? tipKart : "";
    }

    public void setTipKart(String tipKart) {
        this.tipKart = tipKart;
    }

    public boolean isBlokadaHK() {
        return "S".equalsIgnoreCase(tipKart);
    }

    public boolean isVOdhodu() {
        return "X".equalsIgnoreCase(tipKart);
    }
}
