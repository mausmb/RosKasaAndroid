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
