package si.ros.RosKasa.models;

import java.math.BigDecimal;

public class RacunSeznamItem {
    private int racunId;
    private Integer kasiral;
    private String marker;
    private Integer status;
    private Integer stornoRacunId;
    private BigDecimal znesek;

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

    public BigDecimal getZnesek() {
        return znesek;
    }

    public void setZnesek(BigDecimal znesek) {
        this.znesek = znesek;
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
