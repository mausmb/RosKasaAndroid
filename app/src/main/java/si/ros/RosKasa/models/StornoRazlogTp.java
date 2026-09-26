package si.ros.RosKasa.models;

public class StornoRazlogTp {
    private int stornoRazlogId;
    private String naziv;

    public StornoRazlogTp() {
    }

    public StornoRazlogTp(int stornoRazlogId, String naziv) {
        this.stornoRazlogId = stornoRazlogId;
        this.naziv = naziv;
    }

    public int getStornoRazlogId() {
        return stornoRazlogId;
    }

    public void setStornoRazlogId(int stornoRazlogId) {
        this.stornoRazlogId = stornoRazlogId;
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
