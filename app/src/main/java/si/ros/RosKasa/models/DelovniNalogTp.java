package si.ros.RosKasa.models;

import java.io.Serializable;

public class DelovniNalogTp implements Serializable {
    private int dnAi;
    private String dnId = "";
    private String naziv = "";
    private Integer partnerId;
    private String partnerNaziv = "";
    private int strMestoId;
    private String strMestoNaziv = "";
    private String firma = "";

    public DelovniNalogTp() {}

    public int getDnAi() {
        return dnAi;
    }

    public void setDnAi(int dnAi) {
        this.dnAi = dnAi;
    }

    public String getDnId() {
        return dnId;
    }

    public void setDnId(String dnId) {
        this.dnId = dnId != null ? dnId : "";
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv != null ? naziv : "";
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerNaziv() {
        return partnerNaziv;
    }

    public void setPartnerNaziv(String partnerNaziv) {
        this.partnerNaziv = partnerNaziv != null ? partnerNaziv : "";
    }

    public int getStrMestoId() {
        return strMestoId;
    }

    public void setStrMestoId(int strMestoId) {
        this.strMestoId = strMestoId;
    }

    public String getStrMestoNaziv() {
        return strMestoNaziv;
    }

    public void setStrMestoNaziv(String strMestoNaziv) {
        this.strMestoNaziv = strMestoNaziv != null ? strMestoNaziv : "";
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma != null ? firma : "";
    }

    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        if (!dnId.isEmpty()) sb.append("ID: ").append(dnId).append(" - ");
        sb.append(naziv);
        if (!strMestoNaziv.isEmpty()) sb.append(" (").append(strMestoNaziv).append(")");
        if (!partnerNaziv.isEmpty()) sb.append(" / P: ").append(partnerNaziv);
        return sb.toString();
    }
}
