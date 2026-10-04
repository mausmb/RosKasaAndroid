package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class SlipEmaTp {
    private int stevilkaRacuna;
    private int pozicijaId;
    private String stevilkaKartice = "";
    private String slipPrint = "";
    private String uspelo = "DA";
    private String slipPrints = "";
    private String avtorizacija = "PAY";
    private int projektId = 3;
    private String acqTransRef = "";
    private String stType = "";
    private String appIdentifier = "";
    private String authReference = "";
    private String authNumber = "";
    private String cardNumber = "";
    private String acqReference = "";
    private Integer strmId;
    private BigDecimal znesekSlip = BigDecimal.ZERO;
    private BigDecimal znesek = BigDecimal.ZERO;

    public SlipEmaTp() {}

    public int getStevilkaRacuna() { return stevilkaRacuna; }
    public void setStevilkaRacuna(int stevilkaRacuna) { this.stevilkaRacuna = stevilkaRacuna; }

    public int getPozicijaId() { return pozicijaId; }
    public void setPozicijaId(int pozicijaId) { this.pozicijaId = pozicijaId; }

    public String getStevilkaKartice() { return stevilkaKartice; }
    public void setStevilkaKartice(String stevilkaKartice) { this.stevilkaKartice = stevilkaKartice != null ? stevilkaKartice : ""; }

    public String getSlipPrint() { return slipPrint; }
    public void setSlipPrint(String slipPrint) { this.slipPrint = slipPrint != null ? slipPrint : ""; }

    public String getUspelo() { return uspelo; }
    public void setUspelo(String uspelo) { this.uspelo = uspelo != null ? uspelo : "DA"; }

    public String getSlipPrints() { return slipPrints; }
    public void setSlipPrints(String slipPrints) { this.slipPrints = slipPrints != null ? slipPrints : ""; }

    public String getAvtorizacija() { return avtorizacija; }
    public void setAvtorizacija(String avtorizacija) { this.avtorizacija = avtorizacija != null ? avtorizacija : ""; }

    public int getProjektId() { return projektId; }
    public void setProjektId(int projektId) { this.projektId = projektId; }

    public String getAcqTransRef() { return acqTransRef; }
    public void setAcqTransRef(String acqTransRef) { this.acqTransRef = acqTransRef != null ? acqTransRef : ""; }

    public String getStType() { return stType; }
    public void setStType(String stType) { this.stType = stType != null ? stType : ""; }

    public String getAppIdentifier() { return appIdentifier; }
    public void setAppIdentifier(String appIdentifier) { this.appIdentifier = appIdentifier != null ? appIdentifier : ""; }

    public String getAuthReference() { return authReference; }
    public void setAuthReference(String authReference) { this.authReference = authReference != null ? authReference : ""; }

    public String getAuthNumber() { return authNumber; }
    public void setAuthNumber(String authNumber) { this.authNumber = authNumber != null ? authNumber : ""; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber != null ? cardNumber : ""; }

    public String getAcqReference() { return acqReference; }
    public void setAcqReference(String acqReference) { this.acqReference = acqReference != null ? acqReference : ""; }

    public Integer getStrmId() { return strmId; }
    public void setStrmId(Integer strmId) { this.strmId = strmId; }

    public BigDecimal getZnesekSlip() { return znesekSlip; }
    public void setZnesekSlip(BigDecimal znesekSlip) { this.znesekSlip = znesekSlip != null ? znesekSlip : BigDecimal.ZERO; }

    public BigDecimal getZnesek() { return znesek; }
    public void setZnesek(BigDecimal znesek) { this.znesek = znesek != null ? znesek : BigDecimal.ZERO; }
}
