package si.ros.RosKasa.models;

public class PrioritetaProjektaTp {
    private String caption = "";
    private int pozicijaId = 0; // 1-based pozicija v nizu pravic

    public PrioritetaProjektaTp() {}

    public PrioritetaProjektaTp(String caption, int pozicijaId) {
        this.caption = caption;
        this.pozicijaId = pozicijaId;
    }

    public String getCaption() {
        return caption != null ? caption : "";
    }

    public void setCaption(String caption) {
        this.caption = caption != null ? caption : "";
    }

    public int getPozicijaId() {
        return pozicijaId;
    }

    public void setPozicijaId(int pozicijaId) {
        this.pozicijaId = pozicijaId;
    }
}
