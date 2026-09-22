# RosKasa - Learned Technical Guidelines & Best Practices

This document summarizes critical architectural, SOAP protocol, and Android layout rules established during development.

---

## 1. ASP.NET ASMX ksoap2 Complex Parameter Mapping
- **Issue:** Calling `envelope.addSoapObject(rqObject)` attaches `xmlns="http://ros.si/R16"` directly to the parameter root element `<rq>`, which causes ASP.NET `.NET XmlSerializer` to crash with:
  `The specified type was not recognized: name=':rq', namespace='http://ros.si/R16'`.
- **Rule:**
  Complex request parameters in `.NET` ASMX SOAP services must be attached via `request.addProperty("rq", rqObject)` where `rqObject` is instantiated as `new SoapObject(NAMESPACE, "GetRacuniRqTp")` or `new SoapObject(NAMESPACE, "ArrayOfKronologijaTp")`.

---

## 2. MaterialButton Top Edge Alignment & Uniform Height
- **Issue:** Default MaterialComponents `Widget.MaterialComponents.Button` applies `insetTop="6dp"` and `insetBottom="6dp"`. When text wraps to 2 lines, baseline alignment shifts the top border downward relative to 1-line buttons in the same row.
- **Rule:**
  For POS button grids where text wraps dynamically:
  1. Set `android:insetTop="0dp"` and `android:insetBottom="0dp"`.
  2. Set `android:gravity="center"`, `android:includeFontPadding="false"`, and `android:baselineAligned="false"`.
  3. Set `android:alignmentMode="alignBounds"` on parent `GridLayout` containers.
  4. Enforce uniform explicit heights (e.g., `56dp` for POS control buttons).

---

## 3. Database Field Constraints (`OPIS_OPERACIJE`)
- **Issue:** The database column for `OPIS_OPERACIJE` in `insertKronologija` has a strict maximum length of 950 characters.
- **Rule:**
  Always enforce string truncation to a maximum of 950 characters in `KronologijaTp.setOpisOperacije(...)` and `RosKasaSoapClient` prior to SOAP invocation.

---

## 4. Dynamic Startup Setup (`getAppConfig` & `MobileSetupTp`)
- **Issue:** `TIPKE_POS_ID`, `HIS_OBRAT`, `F_POS_ID`, and `F_POSLOVNI_PROSTOR_ID` must not be hardcoded.
- **Rule:**
  Always fetch startup parameters dynamically from WSDL method `getAppConfig` (`MobileSetupTp`) upon login/registration and persist them into `AppPreferences`.

---

## 5. Slovenian Character Encoding & Delphi File Preservation
- **Rule:**
  Always preserve ANSI / Windows-1250 encoding in source files containing Slovenian characters (`č`, `š`, `ž`, `ć`, `đ`). Do not convert Delphi source files to UTF-8 with BOM.

---

## 6. Razlika med TIPKE_POS_ID in F_POS_ID / POS_ID
- **Problem:** Klic `setRacun` z `mobile_setup.TIPKE_POS_ID` namesto `mobile_setup.F_POS_ID` (ali `POS_ID`) povzroči strežniško napako:
  `Klic setRacun NAPAKA: R:... SERVER FAULT v setRacun: Neznan pos 542`.
- **Pravilo:**
  - `TIPKE_POS_ID` (npr. 542) je izključno ID za postavitev hitrih tipk v naročilih (`getHitreTipke`).
  - Za davčno blagajno, klic `setRacun`, `getRacun`, fiskalizacijo in glavo računa se MORA vedno uporabiti `F_POS_ID` oziroma `POS_ID` (npr. 1 ali 512200).
  - Ta dva identifikatorja nista enaka in se ju nikoli ne sme zamenjati!

---

## 7. Izpis računa in postavke: Naziv artikla (NIVO4_ID) se VEDNO poišče iz cenika
- **Problem:** V WSDL / SOAP shemi strežnika struktura `PozicijaTp` (tabela `RACPOZIC`) **ne vsebuje** atributa `NAZIV`, temveč le `NIVO4_ID`. Ko strežnik vrne račun prek `setRacun` ali `getRacun`, so vsa polja `NAZIV` v vrnjenih postavkah prazna (`null` oziroma `""`).
- **Pravilo:**
  1. Za vsako postavko računa se mora naziv artikla **vedno** poiskati iz cenika oziroma hitrih tipk preko:
     `Globals.getInstance().findNazivByNivo4Id(nivo4Id)`.
  2. Cenik (`getCenik`) in hitre tipke (`getHitreTipke`) se morata prednaložiti v predpomnilnik (`Globals.setCachedCenik`, `Globals.setCachedHitreTipke`) takoj ob prijavi (`LoginFragment`) in ob odprtju naročil.
  3. Metoda `PozicijaTp.getNaziv()` ter gradnik računa `RacunPrintBuilder` morata avtomatsko izvesti lookup preko `findNazivByNivo4Id(nivo4Id)`, če je polje `naziv` prazno ali enako privzetemu `Artikel #...`.
  4. Na izpisu računa (Bluetooth ali tekstovni predogled) ne sme biti praznih nazivov ali izmišljenih fiksnih nizov (kot je bil npr. "Artiker"). Če naziv v ceniku ne obstaja, je fallback `Artikel #<nivo4Id>`.

