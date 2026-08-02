package si.ros.RosKasa.models;

public class OsebaTokenResult {
    private String token;
    private String naziv;
    private String fault;
    private boolean isSuccess;

    public OsebaTokenResult() {
    }

    public OsebaTokenResult(String token, String naziv, String fault) {
        this.token = token;
        this.naziv = naziv;
        this.fault = fault;
        this.isSuccess = (fault == null || fault.isEmpty()) && (token != null && !token.isEmpty());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
