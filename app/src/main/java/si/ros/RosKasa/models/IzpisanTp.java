package si.ros.RosKasa.models;

public class IzpisanTp {
    private int racunId;
    private int status = 2;
    private int zakljucen = 1;
    private int tocilnicaId;
    private int osebaId;
    private String vsebina = "";
    private String datum;
    private String ura;
    private String casIzpisa;
    private int izpisAi = 0;

    public IzpisanTp() {}

    public int getIzpisAi() {
        return izpisAi;
    }

    public void setIzpisAi(int izpisAi) {
        this.izpisAi = izpisAi;
    }

    public int getRacunId() {
        return racunId;
    }

    public void setRacunId(int racunId) {
        this.racunId = racunId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getZakljucen() {
        return zakljucen;
    }

    public void setZakljucen(int zakljucen) {
        this.zakljucen = zakljucen;
    }

    public int getTocilnicaId() {
        return tocilnicaId;
    }

    public void setTocilnicaId(int tocilnicaId) {
        this.tocilnicaId = tocilnicaId;
    }

    public int getOsebaId() {
        return osebaId;
    }

    public void setOsebaId(int osebaId) {
        this.osebaId = osebaId;
    }

    public String getVsebina() {
        return vsebina != null ? vsebina : "";
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getUra() {
        return ura;
    }

    public void setUra(String ura) {
        this.ura = ura;
    }

    public String getCasIzpisa() {
        return casIzpisa;
    }

    public void setCasIzpisa(String casIzpisa) {
        this.casIzpisa = casIzpisa;
    }
}
