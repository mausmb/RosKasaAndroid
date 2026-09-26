package si.ros.RosKasa.models;

public class StornoResult {
    private boolean success;
    private String fault = "";
    private String data1 = ""; // Številka storno računa
    private String data2 = ""; // Številka novega računa (če NovoNarocilo)

    public StornoResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
