package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class RacunSeznamItem {
    private int racunId;
    private Integer kasiral;
    private String marker;
    private Integer status;
    private Integer stornoRacunId;
    private Integer stornoOriginal;
    private BigDecimal znesek;
    private BigDecimal placano;
    private Boolean isLocked;

    public RacunSeznamItem() {
    }

    public RacunSeznamItem(int racunId, Integer kasiral, String marker, Integer status, Integer stornoRacunId, BigDecimal znesek) {
        this.racunId = racunId;
        this.kasiral = kasiral;
        this.marker = marker;
        this.status = status;
        this.stornoRacunId = stornoRacunId;
        this.znesek = znesek;
    }

    public int getRacunId() {
        return racunId;
    }

    public void setRacunId(int racunId) {
        this.racunId = racunId;
    }

    public Integer getKasiral() {
        return kasiral;
    }

    public void setKasiral(Integer kasiral) {
        this.kasiral = kasiral;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStornoRacunId() {
        return stornoRacunId;
    }

    public void setStornoRacunId(Integer stornoRacunId) {
        this.stornoRacunId = stornoRacunId;
    }

    public Integer getStornoOriginal() {
        return stornoOriginal;
    }

    public void setStornoOriginal(Integer stornoOriginal) {
        this.stornoOriginal = stornoOriginal;
    }

    public BigDecimal getZnesek() {
        return znesek;
    }

    public void setZnesek(BigDecimal znesek) {
        this.znesek = znesek;
    }

    public BigDecimal getPlacano() {
        return placano;
    }

    public void setPlacano(BigDecimal placano) {
        this.placano = placano;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isPlacan() {
        if (Boolean.TRUE.equals(isLocked)) return true;
        if (placano != null && placano.compareTo(BigDecimal.ZERO) > 0) {
            if (znesek != null && znesek.compareTo(BigDecimal.ZERO) > 0) {
                return placano.compareTo(znesek) >= 0;
            }
            return true;
        }
        return false;
    }

    public boolean hasAnyPayment() {
        if (Boolean.TRUE.equals(isLocked)) return true;
        return placano != null && placano.compareTo(BigDecimal.ZERO) > 0;
    }

    public String getFormattedZnesek() {
        if (znesek != null) {
            return String.format("%.2f €", znesek);
        }
        return "0,00 €";
    }

    public String getStatusDescription() {
        if (status == null) return "Neznano";
        switch (status) {
            case 1: return "Odprt";
            case 2: return "Izpisan";
            case 4: return "Obračunan";
            default: return "Status " + status;
        }
    }
}
