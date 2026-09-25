package si.ros.RosKasa.models;

public class MizaTp {
    private String naziv = "";
    private Integer rajon = 0;
    private Integer zap = 0;

    public MizaTp() {}

    public MizaTp(String naziv, Integer rajon, Integer zap) {
        this.naziv = naziv;
        this.rajon = rajon != null ? rajon : 0;
        this.zap = zap != null ? zap : 0;
    }

    public String getNaziv() {
        return naziv != null ? naziv : "";
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv != null ? naziv : "";
    }

    public Integer getRajon() {
        return rajon != null ? rajon : 0;
    }

    public void setRajon(Integer rajon) {
        this.rajon = rajon != null ? rajon : 0;
    }

    public Integer getZap() {
        return zap != null ? zap : 0;
    }

    public void setZap(Integer zap) {
        this.zap = zap != null ? zap : 0;
    }
}
