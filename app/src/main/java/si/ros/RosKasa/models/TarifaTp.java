package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class TarifaTp {
    private int tarifaId = 0;
    private String naziv = "";
    private String oznaka = "";
    private Integer metodaId;
    private BigDecimal davekProc = BigDecimal.ZERO;

    public TarifaTp() {}

    public TarifaTp(int tarifaId, String naziv, String oznaka, Integer metodaId, BigDecimal davekProc) {
        this.tarifaId = tarifaId;
        this.naziv = naziv;
        this.oznaka = oznaka;
        this.metodaId = metodaId;
        this.davekProc = davekProc != null ? davekProc : BigDecimal.ZERO;
    }

    public int getTarifaId() {
        return tarifaId;
    }

    public void setTarifaId(int tarifaId) {
        this.tarifaId = tarifaId;
    }

    public String getNaziv() {
        return naziv != null ? naziv : "";
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv != null ? naziv : "";
    }

    public String getOznaka() {
        return oznaka != null ? oznaka : "";
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka != null ? oznaka : "";
    }

    public Integer getMetodaId() {
        return metodaId;
    }

    public void setMetodaId(Integer metodaId) {
        this.metodaId = metodaId;
    }

    public BigDecimal getDavekProc() {
        return davekProc != null ? davekProc : BigDecimal.ZERO;
    }

    public void setDavekProc(BigDecimal davekProc) {
        this.davekProc = davekProc != null ? davekProc : BigDecimal.ZERO;
    }
}
