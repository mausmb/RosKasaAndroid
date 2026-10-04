package si.ros.RosKasa.models;

public class AkcijaTp {
    private int akcijaId;
    private String kuponId = "";
    private String naziv = "";
    private String tipNaziv = "";
    private String opis = "";
    private String tisk = "";
    private String datumOd = "";
    private String datumDo = "";

    public AkcijaTp() {}

    public int getAkcijaId() {
        return akcijaId;
    }

    public void setAkcijaId(int akcijaId) {
        this.akcijaId = akcijaId;
    }

    public String getKuponId() {
        return kuponId != null ? kuponId : "";
    }

    public void setKuponId(String kuponId) {
        this.kuponId = kuponId;
    }

    public String getNaziv() {
        return naziv != null ? naziv : "";
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTipNaziv() {
        return tipNaziv != null ? tipNaziv : "";
    }

    public void setTipNaziv(String tipNaziv) {
        this.tipNaziv = tipNaziv;
    }

    public String getOpis() {
        return opis != null ? opis : "";
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTisk() {
        return tisk != null ? tisk : "";
    }

    public void setTisk(String tisk) {
        this.tisk = tisk;
    }

    public String getDatumOd() {
        return datumOd != null ? datumOd : "";
    }

    public void setDatumOd(String datumOd) {
        this.datumOd = datumOd;
    }

    public String getDatumDo() {
        return datumDo != null ? datumDo : "";
    }

    public void setDatumDo(String datumDo) {
        this.datumDo = datumDo;
    }
}
