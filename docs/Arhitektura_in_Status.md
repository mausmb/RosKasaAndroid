# RosKasa Android - Tehnična Dokumentacija in Trenutno Stanje

Ta dokument služi kot uradna razvojna dokumentacija za sledenje napredku, razumevanje trenutne arhitekture in nadaljnji razvoj Android mobilne blagajne **RosKasa**.

---

## 1. Pregled sistema in arhitektura

Aplikacija RosKasa je Android POS blagajna (podprta na telefonih/tablicah, primarno pokončni - portrait način, sekundarno ležeči - landscape način; deluje na 32-bit in 64-bit Android sistemih).
Celotno delovanje temelji na komunikaciji s centralnim SOAP spletnim servisom (`RosKasa_ceniki_wsdl.wsdl`), tiskanje pa se izvaja preko prenosnih Bluetooth termičnih tiskalnikov (ESC/POS protokol, UTF-8 nabor znakov).

### 1.1 Glavne komponente
- **Aktivnost & Navigacija:** Ena glavna aktivnost (`MainActivity`), ki gosti fragmente za posamezne ekrane:
  - `LoginFragment`: Prijava z uporabniškim imenom/geslom ob prvem zagonu ali hitra prijava z natakarjevo številko/PIN ter NFC kartico.
  - `MizeFragment`: Pregled miz in rajonov z barvnimi indikatorji zasedenosti (rdeča = odprt račun).
  - `NarocilaFragment`: Delo z naročili (dodajanje artiklov preko hierarhičnih hitrih tipk ali seznama cenika, podpora za lestvice polnjenja, vnos lastne cene, popuste in količine).
  - `PlacilaFragment`: Razdelitev in vnos plačil po plačilnih sredstvih (gotovina, kartice, dobavnica, soba/hotel kredit, kuponi) ter zaključek in fiskalizacija računa.
  - `RacuniFragment`: Pregled odprtih in izpisanih računov (status 1 = odprt, status 2 = izpisan, status 4 = obračunan), predogled računov, ponovni tisk kopije in storno.
- **Globalno stanje (`Globals.java`):**
  Singleton razred, ki ob zagonu iz `getAppConfig` (`MobileSetupTp`) naloži vse parametre sistema:
  - Podatke podjetja (naziv, naslov, DDV, prodajno mesto, enota).
  - Nastavitve tiskalnika in prednastavljene ESC kode (`ESCINITPRINT`, `ESCBOLDON`, `ESCWIDTH2XON`, `escCut`, itd.).
  - Šifrante (davčne stopnje, načine plačil `NacPlacTp`, prioritete, osebje, hitre tipke).
  - Tekoče stanje (prijavljena oseba, aktivni račun `currentRacun`, tekoča miza/marker).
- **Nastavitve naprave (`AppPreferences.java`):**
  Lokalna hramba (`SharedPreferences`): URL strežnika, token, `mobileId`, zadnji izbrani Bluetooth tiskalnik, aktivni `racunId`.

---

## 2. SOAP Komunikacija (`RosKasaSoapClient.java`)

Komunikacija poteka preko protokola SOAP 1.1 z uporabo knjižnice `ksoap2-android`.

### 2.1 Ključne metode servisa:
1. `getOsebaToken(url, mobileId, username, password)`: Avtentikacija in pridobitev varnega žetona (`token`).
2. `getAppConfig(mobileId, token)`: Začetna konfiguracija blagajne (`MobileSetupTp`).
3. `getCenik(cenikId, token)`: Prenos celotnega cenika z artikli in podartikli (`CENIKVR`, `CENIKVRVR`).
4. `getHitreTipke(posId, tip, token)`: Hierarhične hitre tipke za naročanje.
5. `getMize(hisObrat, token)`: Seznam miz in rajonov.
6. `getRacuniSeznam(mobileId, filterStatus, token)`: Seznam odprtih ali zaključenih računov.
7. `getRacun(racunId, token)`: Prenos celotnih podatkov računa (`RacunTp`) z vsemi postavkami (`RACPOZIC`) in plačili (`RACPLACI`).
8. `setRacun(serverUrl, token, mobileId, racun)`: Ključna metoda za shranjevanje ali zaključek računa.
9. `insertKronologija(serverUrl, token, items)`: Beleženje revizijske sledi delovanja.

### 2.2 Naučena pravila SOAP integracije:
- Kompleksni parametri v ksoap2 se dodajajo preko `request.addProperty("rq", rqObject)` z ustreznim `SoapObject(NAMESPACE, "Tip")`.
- Reševanje sočasnosti: Ob klicu `setRacun` strežnik preverja polje `VERZIJA_ZAPISA`. V primeru sočasnih sprememb drugega natakarja strežnik javi napako verzije, ki jo aplikacija ujame kot `VersionConflictException` in uporabnika pozove k osvežitvi.
- Polje `OPIS_OPERACIJE` v kronologiji je v bazi omejeno na maksimalno 950 znakov in ga je potrebno pred pošiljanjem skrajšati.

---

## 3. Podsistem za tiskanje in fiskalizacijo

### 3.1 Bluetooth tiskanje (`BluetoothPrintHelper.java`)
- Komunikacija preko Bluetooth RFCOMM SPP protokola (UUID `00001101-0000-1000-8000-00805F9B34FB`).
- Povezovanje poteka asinhrono na ločeni niti (`ExecutorService`), s čimer je preprečen Android ANR.
- Samodejno iskanje seznanjenega tiskalnika glede na ime iz `Globals.getPrinterRacuni()` ali privzete povezane naprave.

### 3.2 Generator računa (`RacunPrintBuilder.java`)
V skladu z Delphi logiko `kreirajRacunS2` (`PrintAll.pas`) generator ustvari dva izhoda:
1. **Tekstovni predogled (String):** Čist tekst brez ESC kod, poravnan po širini (običajno 32 ali 42 znakov), uporaben za predogled na zaslonu in shranjevanje v bazo (`RACIZPISAN`).
2. **ESC/POS podatkovni tok (byte[]):** Vsebuje formatirne ukaze:
   - Inicializacija tiskalnika (`ESCINITPRINT`)
   - Dvojna širina/višina za naziv podjetja in `Za plačilo` (`ESCWIDTH2XON`)
   - Poravnave levo / sredina (`ESCALIGNLEFT`, `ESCALIGNCENTER`)
   - Izpis ločilnih črt (`-` in `_`)
   - Tisk FURS QR kode preko nativnih ESC/POS ukazov (`ESC 29 121 ...`)
   - Odrez papirja (`escCut`).

### 3.3 Pravila fiskalizacije (`RACGLAVA.FISKALIZACIJA`):
- Pri zaključku računa se pregledajo vsa vnešena plačila:
  - Če je vsaj eno plačilo (kjer `PLACILO_ID <> 99`) v `NACPLAC.FISKALIZACIJA == 1` (npr. gotovina, kartica), se račun označi kot fiskalni: `RACGLAVA.FISKALIZACIJA = 1`.
  - Če nobeno plačilo ni fiskalno (npr. dobavnica podjetju), se račun obravnava kot nefiskalni: `RACGLAVA.FISKALIZACIJA = 2` (lokalno) oz. `null` ob klicu SOAP `setRacun`.
- Skladno z Delphi pravilom se polje `FISKALIZACIJA` posodablja le, kadar je enako 1:
  `Update FISKALIZACIJA je samo za FISKALIZACIJA=1 drugače je null.`

---

## 4. Stanje razvoja (Tracking napredka)

| Modul / Funkcionalnost | Status | Opombe |
|---|---|---|
| Prijava (Login) | Dokončano | Vnos URL, Token, MobileId, shranjevanje v Preferences |
| Branje konfiguracije (`getAppConfig`) | Dokončano | Parametri podjetja, tiskalnika, ESC kode, davki, načini plačil |
| Mize in rajoni (`MizeFragment`) | Dokončano | Prikaz miz, filtri po rajonih, indikatorji zasedenosti |
| Naročanje (`NarocilaFragment`) | Dokončano | Hitre tipke, cenik lista, lestvice polnjenja, lastna cena |
| Plačila (`PlacilaFragment`) | Dokončano | Delna plačila, seštevki, preverjanje salda pred izpisom |
| Določitev fiskalizacije (`NACPLAC`) | Dokončano | Pravilna nastavitev `FISKALIZACIJA=1` glede na plačila |
| Formatiranje računa (`RacunPrintBuilder`) | Dokončano | Po vzoru Delphi `kreirajRacunS2` (preview + ESC tisk) |
| Število kopij izpisa | Dokončano | `stKopijPlacila`, oznaka "Kopija št. X", inkrement v bazi |
| Predogled in ponovni izpis (Status 2/4) | Dokončano | V zavihku Računi: modalni predogled, ponoven tisk, storno |
| Storno računa | V pripravi | Dialog za izbiro razloga storna in prenos na strežnik |
| Delitev / združevanje računov | V pripravi | Planirano za naslednjo fazo |
| Tiskanje POS terminal slipov | V pripravi | WorldLine / Payten integracija |
