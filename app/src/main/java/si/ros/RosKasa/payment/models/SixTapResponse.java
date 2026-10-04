package si.ros.RosKasa.payment.models;

import java.math.BigDecimal;

public class SixTapResponse {
    private String pOperacija = "WPI_SVC_PAYMENT";
    private boolean pStornoPlacila = false;
    private boolean pDeletePlacilo = false;
    private int pRacunId = 0;
    private int pPozicijaId = 0;
    private int pPlaciloId = 0;
    private int pKupecId = 0;
    private BigDecimal pZnesek = BigDecimal.ZERO;
    private int pZnesekInteger = 0;
    private BigDecimal pNapitnina = BigDecimal.ZERO;
    private int pNapitninaInteger = 0;
    private String tipAmount = "";
    private String pCurr = "EUR";
    private String pReference = "";
    private String result = "";
    private String pFault = "";
    private String errorCondition = "";
    private String remark = "";
    private String timestamp = "";
    private String currency = "EUR";
    private String authorizedAmount = "";
    private BigDecimal znesekPOS = BigDecimal.ZERO;
    private String brandName = "";
    private String customerLanguage = "";
    private String applicationIdentifier = "";
    private String applicationLabel = "";
    private String formattedReceipt = "";
    private String paymentSolutionReference = "";
    private String reference = "";
    private String receipt = "";
    private String client = "";
    private String merchant = "";
    private String acqreference = "";
    private String cardnumber = "";
    private String appStatus = "";
    private String pWpiSessionId = "";
    private boolean pDoNotPrint = false;
    private String authNumber = "";

    public SixTapResponse() {}

    public boolean isSuccess() {
        return "WPI_RESULT_SUCCESS".equalsIgnoreCase(result);
    }

    // Getters and Setters
    public String getpOperacija() { return pOperacija; }
    public void setpOperacija(String pOperacija) { this.pOperacija = pOperacija != null ? pOperacija : ""; }

    public boolean ispStornoPlacila() { return pStornoPlacila; }
    public void setpStornoPlacila(boolean pStornoPlacila) { this.pStornoPlacila = pStornoPlacila; }

    public boolean ispDeletePlacilo() { return pDeletePlacilo; }
    public void setpDeletePlacilo(boolean pDeletePlacilo) { this.pDeletePlacilo = pDeletePlacilo; }

    public int getpRacunId() { return pRacunId; }
    public void setpRacunId(int pRacunId) { this.pRacunId = pRacunId; }

    public int getpPozicijaId() { return pPozicijaId; }
    public void setpPozicijaId(int pPozicijaId) { this.pPozicijaId = pPozicijaId; }

    public int getpPlaciloId() { return pPlaciloId; }
    public void setpPlaciloId(int pPlaciloId) { this.pPlaciloId = pPlaciloId; }

    public int getpKupecId() { return pKupecId; }
    public void setpKupecId(int pKupecId) { this.pKupecId = pKupecId; }

    public BigDecimal getpZnesek() { return pZnesek; }
    public void setpZnesek(BigDecimal pZnesek) { this.pZnesek = pZnesek != null ? pZnesek : BigDecimal.ZERO; }

    public int getpZnesekInteger() { return pZnesekInteger; }
    public void setpZnesekInteger(int pZnesekInteger) { this.pZnesekInteger = pZnesekInteger; }

    public BigDecimal getpNapitnina() { return pNapitnina; }
    public void setpNapitnina(BigDecimal pNapitnina) { this.pNapitnina = pNapitnina != null ? pNapitnina : BigDecimal.ZERO; }

    public int getpNapitninaInteger() { return pNapitninaInteger; }
    public void setpNapitninaInteger(int pNapitninaInteger) { this.pNapitninaInteger = pNapitninaInteger; }

    public String getTipAmount() { return tipAmount; }
    public void setTipAmount(String tipAmount) { this.tipAmount = tipAmount != null ? tipAmount : ""; }

    public String getpCurr() { return pCurr; }
    public void setpCurr(String pCurr) { this.pCurr = pCurr != null ? pCurr : "EUR"; }

    public String getpReference() { return pReference; }
    public void setpReference(String pReference) { this.pReference = pReference != null ? pReference : ""; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result != null ? result : ""; }

    public String getpFault() { return pFault; }
    public void setpFault(String pFault) { this.pFault = pFault != null ? pFault : ""; }

    public String getErrorCondition() { return errorCondition; }
    public void setErrorCondition(String errorCondition) { this.errorCondition = errorCondition != null ? errorCondition : ""; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark != null ? remark : ""; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp != null ? timestamp : ""; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency != null ? currency : "EUR"; }

    public String getAuthorizedAmount() { return authorizedAmount; }
    public void setAuthorizedAmount(String authorizedAmount) { this.authorizedAmount = authorizedAmount != null ? authorizedAmount : ""; }

    public BigDecimal getZnesekPOS() { return znesekPOS; }
    public void setZnesekPOS(BigDecimal znesekPOS) { this.znesekPOS = znesekPOS != null ? znesekPOS : BigDecimal.ZERO; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName != null ? brandName : ""; }

    public String getCustomerLanguage() { return customerLanguage; }
    public void setCustomerLanguage(String customerLanguage) { this.customerLanguage = customerLanguage != null ? customerLanguage : ""; }

    public String getApplicationIdentifier() { return applicationIdentifier; }
    public void setApplicationIdentifier(String applicationIdentifier) { this.applicationIdentifier = applicationIdentifier != null ? applicationIdentifier : ""; }

    public String getApplicationLabel() { return applicationLabel; }
    public void setApplicationLabel(String applicationLabel) { this.applicationLabel = applicationLabel != null ? applicationLabel : ""; }

    public String getFormattedReceipt() { return formattedReceipt; }
    public void setFormattedReceipt(String formattedReceipt) { this.formattedReceipt = formattedReceipt != null ? formattedReceipt : ""; }

    public String getPaymentSolutionReference() { return paymentSolutionReference; }
    public void setPaymentSolutionReference(String ref) { this.paymentSolutionReference = ref != null ? ref : ""; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference != null ? reference : ""; }

    public String getReceipt() { return receipt; }
    public void setReceipt(String receipt) { this.receipt = receipt != null ? receipt : ""; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client != null ? client : ""; }

    public String getMerchant() { return merchant; }
    public void setMerchant(String merchant) { this.merchant = merchant != null ? merchant : ""; }

    public String getAcqreference() { return acqreference; }
    public void setAcqreference(String acqreference) { this.acqreference = acqreference != null ? acqreference : ""; }

    public String getCardnumber() { return cardnumber; }
    public void setCardnumber(String cardnumber) { this.cardnumber = cardnumber != null ? cardnumber : ""; }

    public String getAppStatus() { return appStatus; }
    public void setAppStatus(String appStatus) { this.appStatus = appStatus != null ? appStatus : ""; }

    public String getpWpiSessionId() { return pWpiSessionId; }
    public void setpWpiSessionId(String pWpiSessionId) { this.pWpiSessionId = pWpiSessionId != null ? pWpiSessionId : ""; }

    public boolean ispDoNotPrint() { return pDoNotPrint; }
    public void setpDoNotPrint(boolean pDoNotPrint) { this.pDoNotPrint = pDoNotPrint; }

    public String getAuthNumber() { return authNumber; }
    public void setAuthNumber(String authNumber) { this.authNumber = authNumber != null ? authNumber : ""; }
}
