package si.ros.RosKasa.models;

public class LojalnostnaTp {
    private int bonitetniRazred;
    private String naziv;

    public LojalnostnaTp() {
    }

    public LojalnostnaTp(int bonitetniRazred, String naziv) {
        this.bonitetniRazred = bonitetniRazred;
        this.naziv = naziv;
    }

    public int getBonitetniRazred() {
        return bonitetniRazred;
    }

    public void setBonitetniRazred(int bonitetniRazred) {
        this.bonitetniRazred = bonitetniRazred;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return naziv != null ? naziv : "";
    }
}
