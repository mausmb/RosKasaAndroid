package si.ros.RosKasa.models;

public class DodatekTp {
    private int dodatekId;
    private String dodatekText = "";

    public DodatekTp() {}

    public DodatekTp(int dodatekId, String dodatekText) {
        this.dodatekId = dodatekId;
        this.dodatekText = dodatekText != null ? dodatekText : "";
    }

    public int getDodatekId() { return dodatekId; }
    public void setDodatekId(int dodatekId) { this.dodatekId = dodatekId; }

    public String getDodatekText() { return dodatekText; }
    public void setDodatekText(String dodatekText) { this.dodatekText = dodatekText != null ? dodatekText : ""; }

    @Override
    public String toString() {
        return dodatekText;
    }
}
