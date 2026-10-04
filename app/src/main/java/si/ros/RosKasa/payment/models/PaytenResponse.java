package si.ros.RosKasa.payment.models;

import java.math.BigDecimal;

public class PaytenResponse {
    private String result = "";
    private String pOperacija = "purchase";
    private String pFault = "";
    private String pMessage = "";
    private String pReceiptData = "";
    private String pStatus = "";
    private boolean pStornoPlacila = false;
    private int pKupecId = 0;
    private int pRacunId = 0;
    private int pPozicijaId = 0;
    private int pPlaciloId = 0;
    private boolean pDeletePlacilo = false;
    private BigDecimal pZnesek = BigDecimal.ZERO;
    private int pZnesekInteger = 0;
    private BigDecimal pNapitnina = BigDecimal.ZERO;
    private int pNapitninaInteger = 0;
    private BigDecimal znesekPOS = BigDecimal.ZERO;
    private String errorCondition = "";
    private String remark = "";
    private String timestamp = "";
    private String currency = "EUR";
    private String authorizedAmount = "";
    private String tipAmount = "";
    private String brandName = "";
    private String customerLanguage = "";
    private String applicationIdentifier = "";
    private String applicationLabel = "";
    private String paymentSolutionReference = "";
    private String reference = "";
    private String receipt = "";
    private String client = "";
    private String merchant = "";
    private String operationName = "";
    private String cardNumber = "";
    private String merchantId = "";
    private String message = "";
    private String response = "";
    private String code = "";
    private String bankName = "";
    private String rrn = "";
    private String terminalId = "";
    private String status = "";
    private String paymentIdentificator = "";
    private String pPin = "";
    private String pPackageName = "si.ros.RosKasaLight2";
    private String pTransactionType = "POS";
    private String pTransactionClass = "purchase";
    private String pAuthorizationCode = "";
    private String pMerchantUniqueID = "";
    private String pIntRef = "";
    private String pCurr = "EUR";
    private boolean pDoNotPrint = false;

    public PaytenResponse() {}

    public boolean isSuccess() {
        return "00".equals(code) || "OK".equalsIgnoreCase(result);
    }

    // Getters and Setters
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result != null ? result : ""; }

    public String getpOperacija() { return pOperacija; }
    public void setpOperacija(String pOperacija) { this.pOperacija = pOperacija != null ? pOperacija : ""; }

    public String getpFault() { return pFault; }
    public void setpFault(String pFault) { this.pFault = pFault != null ? pFault : ""; }

    public String getpMessage() { return pMessage; }
    public void setpMessage(String pMessage) { this.pMessage = pMessage != null ? pMessage : ""; }

    public String getpReceiptData() { return pReceiptData; }
    public void setpReceiptData(String pReceiptData) { this.pReceiptData = pReceiptData != null ? pReceiptData : ""; }

    public String getpStatus() { return pStatus; }
    public void setpStatus(String pStatus) { this.pStatus = pStatus != null ? pStatus : ""; }

    public boolean ispStornoPlacila() { return pStornoPlacila; }
    public void setpStornoPlacila(boolean pStornoPlacila) { this.pStornoPlacila = pStornoPlacila; }

    public int getpKupecId() { return pKupecId; }
    public void setpKupecId(int pKupecId) { this.pKupecId = pKupecId; }

    public int getpRacunId() { return pRacunId; }
    public void setpRacunId(int pRacunId) { this.pRacunId = pRacunId; }

    public int getpPozicijaId() { return pPozicijaId; }
    public void setpPozicijaId(int pPozicijaId) { this.pPozicijaId = pPozicijaId; }

    public int getpPlaciloId() { return pPlaciloId; }
    public void setpPlaciloId(int pPlaciloId) { this.pPlaciloId = pPlaciloId; }

    public boolean ispDeletePlacilo() { return pDeletePlacilo; }
    public void setpDeletePlacilo(boolean pDeletePlacilo) { this.pDeletePlacilo = pDeletePlacilo; }

    public BigDecimal getpZnesek() { return pZnesek; }
    public void setpZnesek(BigDecimal pZnesek) { this.pZnesek = pZnesek != null ? pZnesek : BigDecimal.ZERO; }

    public int getpZnesekInteger() { return pZnesekInteger; }
    public void setpZnesekInteger(int pZnesekInteger) { this.pZnesekInteger = pZnesekInteger; }

    public BigDecimal getpNapitnina() { return pNapitnina; }
    public void setpNapitnina(BigDecimal pNapitnina) { this.pNapitnina = pNapitnina != null ? pNapitnina : BigDecimal.ZERO; }

    public int getpNapitninaInteger() { return pNapitninaInteger; }
    public void setpNapitninaInteger(int pNapitninaInteger) { this.pNapitninaInteger = pNapitninaInteger; }

    public BigDecimal getZnesekPOS() { return znesekPOS; }
    public void setZnesekPOS(BigDecimal znesekPOS) { this.znesekPOS = znesekPOS != null ? znesekPOS : BigDecimal.ZERO; }

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

    public String getTipAmount() { return tipAmount; }
    public void setTipAmount(String tipAmount) { this.tipAmount = tipAmount != null ? tipAmount : ""; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName != null ? brandName : ""; }

    public String getCustomerLanguage() { return customerLanguage; }
    public void setCustomerLanguage(String customerLanguage) { this.customerLanguage = customerLanguage != null ? customerLanguage : ""; }

    public String getApplicationIdentifier() { return applicationIdentifier; }
    public void setApplicationIdentifier(String applicationIdentifier) { this.applicationIdentifier = applicationIdentifier != null ? applicationIdentifier : ""; }

    public String getApplicationLabel() { return applicationLabel; }
    public void setApplicationLabel(String applicationLabel) { this.applicationLabel = applicationLabel != null ? applicationLabel : ""; }

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

    public String getOperationName() { return operationName; }
    public void setOperationName(String operationName) { this.operationName = operationName != null ? operationName : ""; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber != null ? cardNumber : ""; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId != null ? merchantId : ""; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message != null ? message : ""; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response != null ? response : ""; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code != null ? code : ""; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName != null ? bankName : ""; }

    public String getRrn() { return rrn; }
    public void setRrn(String rrn) { this.rrn = rrn != null ? rrn : ""; }

    public String getTerminalId() { return terminalId; }
    public void setTerminalId(String terminalId) { this.terminalId = terminalId != null ? terminalId : ""; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status != null ? status : ""; }

    public String getPaymentIdentificator() { return paymentIdentificator; }
    public void setPaymentIdentificator(String id) { this.paymentIdentificator = id != null ? id : ""; }

    public String getpPin() { return pPin; }
    public void setpPin(String pPin) { this.pPin = pPin != null ? pPin : ""; }

    public String getpPackageName() { return pPackageName; }
    public void setpPackageName(String pPackageName) { this.pPackageName = pPackageName != null ? pPackageName : ""; }

    public String getpTransactionType() { return pTransactionType; }
    public void setpTransactionType(String pTransactionType) { this.pTransactionType = pTransactionType != null ? pTransactionType : ""; }

    public String getpTransactionClass() { return pTransactionClass; }
    public void setpTransactionClass(String pTransactionClass) { this.pTransactionClass = pTransactionClass != null ? pTransactionClass : ""; }

    public String getpAuthorizationCode() { return pAuthorizationCode; }
    public void setpAuthorizationCode(String code) { this.pAuthorizationCode = code != null ? code : ""; }

    public String getpMerchantUniqueID() { return pMerchantUniqueID; }
    public void setpMerchantUniqueID(String id) { this.pMerchantUniqueID = id != null ? id : ""; }

    public String getpIntRef() { return pIntRef; }
    public void setpIntRef(String pIntRef) { this.pIntRef = pIntRef != null ? pIntRef : ""; }

    public String getpCurr() { return pCurr; }
    public void setpCurr(String pCurr) { this.pCurr = pCurr != null ? pCurr : "EUR"; }

    public boolean ispDoNotPrint() { return pDoNotPrint; }
    public void setpDoNotPrint(boolean pDoNotPrint) { this.pDoNotPrint = pDoNotPrint; }
}
