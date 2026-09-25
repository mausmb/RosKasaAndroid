package si.ros.RosKasa.models;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OsebaTp {
    private int osebaId;
    private String naziv = "";
    private String uporabniskoIme = "";
    private String pin = "";
    private String privilegiji = "";
    private String oddelek = "";
    private String karticaId = "";
    private String kartica2Id = "";
    private String pravice = "";
    private Integer prioriteta = 0; // max dovoljen popust v %
    private Integer veljavnostPin = 0; // v dneh
    private Date datumSpremembePin;
    private String inicialke = "";

    public OsebaTp() {}

    public int getOsebaId() {
        return osebaId;
    }

    public void setOsebaId(int osebaId) {
        this.osebaId = osebaId;
    }

    public String getNaziv() {
        return naziv != null ? naziv : "";
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv != null ? naziv : "";
        izracunajInicialke();
    }

    public String getUporabniskoIme() {
        return uporabniskoIme != null ? uporabniskoIme : "";
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme != null ? uporabniskoIme : "";
    }

    public String getPin() {
        return pin != null ? pin : "";
    }

    public void setPin(String pin) {
        this.pin = pin != null ? pin : "";
    }

    public String getPrivilegiji() {
        return privilegiji != null ? privilegiji : "";
    }

    public void setPrivilegiji(String privilegiji) {
        this.privilegiji = privilegiji != null ? privilegiji : "";
    }

    public String getOddelek() {
        return oddelek != null ? oddelek : "";
    }

    public void setOddelek(String oddelek) {
        this.oddelek = oddelek != null ? oddelek : "";
    }

    public String getKarticaId() {
        return karticaId != null ? karticaId : "";
    }

    public void setKarticaId(String karticaId) {
        this.karticaId = karticaId != null ? karticaId.trim() : "";
    }

    public String getKartica2Id() {
        return kartica2Id != null ? kartica2Id : "";
    }

    public void setKartica2Id(String kartica2Id) {
        this.kartica2Id = kartica2Id != null ? kartica2Id.trim() : "";
    }

    public String getPravice() {
        return pravice != null ? pravice : "";
    }

    public void setPravice(String pravice) {
        this.pravice = pravice != null ? pravice : "";
    }

    public Integer getPrioriteta() {
        return prioriteta != null ? prioriteta : 0;
    }

    public void setPrioriteta(Integer prioriteta) {
        this.prioriteta = prioriteta;
    }

    public Integer getVeljavnostPin() {
        return veljavnostPin != null ? veljavnostPin : 0;
    }

    public void setVeljavnostPin(Integer veljavnostPin) {
        this.veljavnostPin = veljavnostPin;
    }

    public Date getDatumSpremembePin() {
        return datumSpremembePin;
    }

    public void setDatumSpremembePin(Date datumSpremembePin) {
        this.datumSpremembePin = datumSpremembePin;
    }

    public String getInicialke() {
        if (inicialke == null || inicialke.isEmpty()) {
            izracunajInicialke();
        }
        return inicialke != null ? inicialke : "";
    }

    public void setInicialke(String inicialke) {
        this.inicialke = inicialke;
    }

    private void izracunajInicialke() {
        if (naziv == null || naziv.trim().isEmpty()) {
            this.inicialke = "";
            return;
        }
        String[] deli = naziv.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        if (deli.length > 0 && !deli[0].isEmpty()) {
            sb.append(deli[0].substring(0, 1).toUpperCase());
        }
        if (deli.length > 1 && !deli[1].isEmpty()) {
            sb.append(deli[1].substring(0, 1).toUpperCase());
        }
        this.inicialke = sb.toString();
    }

    /**
     * Preveri, če se dana koda kartice (hex ali decimal) ujema s KARTICA_ID ali KARTICA2_ID.
     */
    public boolean ujemaSeKartica(String cardCode) {
        if (cardCode == null || cardCode.trim().isEmpty()) return false;
        String trimmed = cardCode.trim();
        if (trimmed.equalsIgnoreCase(karticaId) || trimmed.equalsIgnoreCase(kartica2Id)) {
            return true;
        }
        // Primerjava brez morebitnih vodilnih ničel
        String noZeros = trimmed.replaceFirst("^0+", "");
        String k1NoZeros = (karticaId != null) ? karticaId.trim().replaceFirst("^0+", "") : "";
        String k2NoZeros = (kartica2Id != null) ? kartica2Id.trim().replaceFirst("^0+", "") : "";
        if (!noZeros.isEmpty()) {
            if (noZeros.equalsIgnoreCase(k1NoZeros) || noZeros.equalsIgnoreCase(k2NoZeros)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Preveri ali je PIN potekel glede na datum spremembe in veljavnost (v dneh).
     */
    public boolean isPinPotekel() {
        if (veljavnostPin == null || veljavnostPin <= 0 || datumSpremembePin == null) {
            return false;
        }
        long diffMs = Math.abs(System.currentTimeMillis() - datumSpremembePin.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(diffMs);
        return days > veljavnostPin;
    }

    /**
     * Izračuna vlogo osebe na podlagi oddelka in privilegijev (Delphi Vlogaosebe 1..6).
     */
    public int getVlogaOsebe() {
        if ("XXL".equalsIgnoreCase(privilegiji) || "ADMIN".equalsIgnoreCase(oddelek)) {
            return 6;
        }
        if ("SEF2".equalsIgnoreCase(oddelek)) return 5;
        if ("SEF1".equalsIgnoreCase(oddelek)) return 4;
        if ("NAT3".equalsIgnoreCase(oddelek)) return 3;
        if ("NAT2".equalsIgnoreCase(oddelek)) return 2;
        return 1; // privzeto natakar (NAT0, NAT1 itd.)
    }
}
