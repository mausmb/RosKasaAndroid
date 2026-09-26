package si.ros.RosKasa.models;

import java.io.Serializable;

public class SoapReportResult implements Serializable {
    private String fault = "";
    private byte[] printBytes;
    private String textPreview = "";

    public SoapReportResult() {}

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault != null ? fault : "";
    }

    public boolean isSuccess() {
        return fault.isEmpty();
    }

    public byte[] getPrintBytes() {
        return printBytes;
    }

    public void setPrintBytes(byte[] printBytes) {
        this.printBytes = printBytes;
    }

    public String getTextPreview() {
        return textPreview;
    }

    public void setTextPreview(String textPreview) {
        this.textPreview = textPreview != null ? textPreview : "";
    }
}
