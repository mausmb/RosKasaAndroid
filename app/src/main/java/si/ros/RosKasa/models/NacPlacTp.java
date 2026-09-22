package si.ros.RosKasa.models;

public class NacPlacTp {
    private int placiloId;
    private String naziv = "";
    private int metoda = 1;
    private Integer fiskalno = 1;
    private String fPlacilo = "";
    private Integer inkaso = 1;
    private Long kupecId = 0L;
    private Integer stKopij = 1;
    private Integer storitevId = 0;
    private int vrsta = 1;
    private Integer crm = 0;

    public NacPlacTp() {}

    public NacPlacTp(int placiloId, String naziv, int metoda) {
        this.placiloId = placiloId;
        this.naziv = naziv;
        this.metoda = metoda;
    }

    public int getPlaciloId() {
        return placiloId;
    }

    public void setPlaciloId(int placiloId) {
        this.placiloId = placiloId;
    }

    public String getNaziv() {
        return naziv != null ? naziv : "";
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getMetoda() {
        return metoda;
    }

    public void setMetoda(int metoda) {
        this.metoda = metoda;
    }

    public Integer getFiskalno() {
        return fiskalno;
    }

    public void setFiskalno(Integer fiskalno) {
        this.fiskalno = fiskalno;
    }

    public String getfPlacilo() {
        return fPlacilo;
    }

    public void setfPlacilo(String fPlacilo) {
        this.fPlacilo = fPlacilo;
    }

    public Integer getInkaso() {
        return inkaso;
    }

    public void setInkaso(Integer inkaso) {
        this.inkaso = inkaso;
    }

    public Long getKupecId() {
        return kupecId;
    }

    public void setKupecId(Long kupecId) {
        this.kupecId = kupecId;
    }

    public Integer getStKopij() {
        return stKopij;
    }

    public void setStKopij(Integer stKopij) {
        this.stKopij = stKopij;
    }

    public Integer getStoritevId() {
        return storitevId;
    }

    public void setStoritevId(Integer storitevId) {
        this.storitevId = storitevId;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public Integer getCrm() {
        return crm;
    }

    public void setCrm(Integer crm) {
        this.crm = crm;
    }
}
