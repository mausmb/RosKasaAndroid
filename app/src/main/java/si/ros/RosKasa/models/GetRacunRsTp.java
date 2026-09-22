package si.ros.RosKasa.models;

public class GetRacunRsTp {
    private RacunTp racGlava;
    private String fault = "";
    private String data1 = "";
    private boolean success = true;

    public GetRacunRsTp() {}

    public GetRacunRsTp(RacunTp racGlava) {
        this.racGlava = racGlava;
        this.success = true;
    }

    public RacunTp getRacGlava() { return racGlava; }
    public void setRacGlava(RacunTp racGlava) { this.racGlava = racGlava; }

    public String getFault() { return fault; }
    public void setFault(String fault) {
        this.fault = fault != null ? fault : "";
        if (!this.fault.isEmpty()) {
            this.success = false;
        }
    }

    public String getData1() { return data1; }
    public void setData1(String data1) { this.data1 = data1 != null ? data1 : ""; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}
