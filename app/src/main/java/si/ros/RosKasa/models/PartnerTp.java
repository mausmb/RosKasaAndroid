package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class PartnerTp {
    private int partnerId;
    private String naziv = "";
    private String nasUlica = "";
    private String nazivPosta = "";
    private String davcnaSt = "";
    private BigDecimal rabat = BigDecimal.ZERO;
    private String sklic = "";

    public PartnerTp() {}

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getNaziv() {
        return naziv != null ? naziv : "";
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getNasUlica() {
        return nasUlica != null ? nasUlica : "";
    }

    public void setNasUlica(String nasUlica) {
        this.nasUlica = nasUlica;
    }

    public String getNazivPosta() {
        return nazivPosta != null ? nazivPosta : "";
    }

    public void setNazivPosta(String nazivPosta) {
        this.nazivPosta = nazivPosta;
    }

    public String getDavcnaSt() {
        return davcnaSt != null ? davcnaSt : "";
    }

    public void setDavcnaSt(String davcnaSt) {
        this.davcnaSt = davcnaSt;
    }

    public BigDecimal getRabat() {
        return rabat != null ? rabat : BigDecimal.ZERO;
    }

    public void setRabat(BigDecimal rabat) {
        this.rabat = rabat != null ? rabat : BigDecimal.ZERO;
    }

    public String getSklic() {
        return sklic != null ? sklic : "";
    }

    public void setSklic(String sklic) {
        this.sklic = sklic;
    }

    public String getPolniNaslov() {
        String ulica = getNasUlica().trim();
        String posta = getNazivPosta().trim();
        if (ulica.isEmpty()) return posta;
        if (posta.isEmpty()) return ulica;
        return ulica + ", " + posta;
    }
}
