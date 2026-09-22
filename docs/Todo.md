# RosKasa - Razvojni Načrt in Seznam Opravil (TODO)

Posodobljeno na podlagi projektne dokumentacije (`Project_RosKasa.md`, `Globals.md`, `HitreTipke.md`, `popusti.md`, `Arhitektura_in_Status.md`, `LEARNED_RULES.md`).
Namen tega dokumenta je sledenje napredku in pregledno nadaljevanje razvoja aplikacije RosKasa.

---

## 0. Pregled Že Dokončanih Funkcionalnosti (Status: Dokončano)
- [x] **Osnovna arhitektura in navigacija:** `MainActivity` z zamenjavo fragmentov (`LoginFragment`, `MizeFragment`, `NarocilaFragment`, `PlacilaFragment`, `RacuniFragment`).
- [x] **SOAP klici (`RosKasaSoapClient`):** `getOsebaToken`, `getAppConfig`, `getCenik`, `getHitreTipke`, `getRacuniSeznam`, `getRacun`, `setRacun`, `insertKronologija`.
- [x] **Inicializacija in nastavitve:** Shranjevanje v `AppPreferences`, singleton `Globals` z dinamičnim polnjenjem iz `MobileSetupTp`.
- [x] **Hierarhične hitre tipke:** Navigacija po skupinah (nivojih), gumb za korak nazaj, preklop med hitrimi tipkami in seznamom cenika.
- [x] **Pravilo `NIVO4_ID`:** Avtomatski lookup naziva artikla preko `findNazivByNivo4Id` v vseh vrnjenih postavkah računa.
- [x] **Pravilo `F_POS_ID` vs `TIPKE_POS_ID`:** Ločitev identifikatorja hitrih tipk od blagajniškega `POS_ID`.
- [x] **Fiskalizacija (`RACGLAVA.FISKALIZACIJA`):** Določitev fiskalnosti glede na plačilna sredstva (`NACPLAC.FISKALIZACIJA == 1`), posodabljanje le kadar je enako 1, sicer `null`.
- [x] **Generator računa (`RacunPrintBuilder`):** Generiranje čistega teksta za predogled (`RACIZPISAN`) in ESC/POS byte toka za tiskalnik s FURS QR kodo.
- [x] **Bluetooth tiskanje:** Povezovanje z Bluetooth termičnimi tiskalniki v ozadju (`BluetoothPrintHelper`).
- [x] **Odstranitev mock podatkov:** Izbris `populateMockData` iz `RacuniFragment`, prikaz dejanskega stanja iz SOAP servisa.

---

## 1. Prijava & Avtentikacija (NFC, PIN, Osebje)
- [ ] **Model osebja (`OsebaTp` / `MOBILE_SETUP_OSEBJE`):**
  - Kreiranje razreda `OsebaTp` (`OSEBA_ID`, `NAZIV`, `PIN`, `KARTICA_ID`, `KARTICA2_ID`, `VELJAVNOSTPIN`, `PRIORITETA`, `Pravice`).
  - Shranjevanje seznama osebja iz `getAppConfig` v `Globals`.
- [ ] **NFC branje kartice:**
  - Integracija Android NFC podsistema (`NfcAdapter`, foreground dispatch / reader mode v `MainActivity` in `LoginFragment`).
  - Branje serijske številke UID kartice ob približanju telefonu.
- [ ] **Vklop/izklop NFC glede na parametre:**
  - Upoštevanje `MobileSetupTp.NFCPRIJAVA` ('D' / 'N') in MOBINI vrednosti `ANDROIDNFCIZKLOP` (`Globals.pNfc`, `Globals.pnfcprijava`).
- [ ] **Avtentikacija z vnosom PIN-a ali NFC kartice:**
  - Preverjanje vnešenega PIN-a ali prebranega `KARTICA_ID` zoper seznam `MOBILE_SETUP_OSEBJE`.
  - Preverjanje veljavnosti PIN-a (`DATUM_SPREMEMBEPIN`, `VELJAVNOSTPIN`).
  - Nastavitev prijavljenega natakarja v `Globals` (`kasiral`, `natakarId`) za knjiženje v glavo računa (`RACGLAVA.KASIRAL`, `RACGLAVA.NATAKAR_ID`).
- [ ] **Odjava (Logout):**
  - Gumb `[ODJAVA]` na vseh zaslonih (Mize, Računi, Plačila) pobriše sejo aktivnega uporabnika in preklopi na hitro prijavo.

---

## 2. Mize in Rajoni (`MizeFragment`)
- [ ] **Dinamični prenos miz (`MOBILE_SETUP_MIZE`):**
  - Kreiranje modela `MizaTp` (`NAZIV`, `RAJON`, `ZAP`).
  - Nadomestitev trenutno fiksnega seznama (Miza 1..30) z dejanskim seznamom miz iz konfiguracije `MobileSetupTp.MOBILE_SETUP_MIZE`.
- [ ] **Sekcija Rajoni & Filtriranje:**
  - Preverjanje parametra `RAJONI = 'D'` v MOBINI / nastavitvah.
  - Prikaz vodoravne vrstice gumbov z rajoni (npr. Terasa, Notranjost, Vrt itd.).
  - Upoštevanje privzetega rajona `RAJONDEFAULT`.
  - Tap na rajon filtrira prikazane mize v mreži.
- [ ] **Stanje zasedenosti miz:**
  - Povezava odprtih računov (`getRacuniSeznam` status=1) z mizami glede na `RACGLAVA.MARKER`.
  - Zasedena miza je rdeče obarvana, prikazuje znesek odprtega računa in številko računa.
  - Tap na zasedeno mizo neposredno odpre tekoče naročilo / račun.
- [ ] **Zamenjava markerja mize (`ZAMENJAVAMARKERMIZE`):**
  - Funkcija za premik računa na drugo mizo ali preimenovanje oznake mize.
- [ ] **Povezava Mize & Računi (`MIZEINRACUNI`):**
  - Izbira ali se ob izbiri mize neposredno odprejo računi ali naročilo.

---

## 3. Naročila in Hitre Tipke (`NarocilaFragment`)
- [ ] **Lestvice točenja / polnjenja (EP):**
  - Upoštevanje načina prodaje artikla (`NACIN_PRODAJE`: 1, 2, 3, 4, 9, 10 za točene pijače, 2 za žgane pijače).
  - Ob kliku na artikel z lestvico odpiranje izbirnega okna za polnjenje (npr. 0.1, 0.2, 0.5, 1.0 ali 0.03, 0.05).
  - Preračun cene po pravilu: `CenaPolnjenje = 'D'` -> `cena = (osnovnaCena / polnjenje) * izbranEP`.
- [ ] **Paketni artikli (`pompaket`):**
  - `pompaket = 1`: klasični paket (v naročilu se prikaže paket, v postavke `RACPOZIC` pa se razknjižijo sestavni artikli iz `CENIKVRVR`).
  - `pompaket = 2`: paket z izbiro sestave (Mix & Match) – odpre se izbirni dialog sestavin.
- [ ] **Ročni vnos cene (`pomcena = -1` ali `-2`):**
  - `pomcena = -1`: odpiranje numeričnega dialoga za prosti vnos pozitivne cene.
  - `pomcena = -2`: vnos cene z možnostjo negativnega predznaka (vračila, posebne kavcije).
- [ ] **Podpora za drugo ceno (`CENA2AKTIVNA`, `MODELCENA2`):**
  - Preklop med rednimi cenami (Cena 1) in posebnimi cenami (Cena 2 – npr. nočni meni, vikend meni).
- [ ] **Dovoljene cene 0.00 (`CENA0DOVOLJENA`, `CENA0CENIK`):**
  - Pravilo filtriranja v ceniku: če `CENA0CENIK == false`, artikli s ceno 0 niso vidni na seznamu, razen če je izrecno dovoljeno knjiženje (`CENA0DOVOLJENA`).
- [ ] **Preklop med prikazom hitrih tipk in seznama cenika:**
  - Tipka "Cenik / Tipke" preklopi med seznamom in mrežo tipk.
  - Ob povratku iz seznama cenika nazaj na hitre tipke se prikaz vedno ponastavi na korensko skupino 1.
- [ ] **Urejanje postavke v naročilu:**
  - Tap na vrstico naročila odpre pogovorno okno za spremembo količine, popusta ali pripis opombe k postavki (npr. "brez ledu").

---

## 4. Sistem Popustov (`popusti.md`)
- [ ] **Popust 99 na celoten račun (`PLACILO_ID = 99`):**
  - Odpiranje dialoga za vnos popusta na celoten znesek računa.
  - Vnos v odstotkih (%): v `RACPLACI.STATUS` se vpiše odstotek, v `RACPLACI.ZNESEK` izračunana vrednost popusta, `RACPLACI.DELNI_ZNESEK = 0`.
  - Vnos v znesku (€): v `RACPLACI.STATUS` se preračuna ustrezni odstotek, v `RACPLACI.ZNESEK` vpiše vnešeni znesek.
  - Pogojna vidnost gumba glede na parameter `POPUST99` ('D' / 'N').
- [ ] **Zneskovni popust na postavko:**
  - Vnos zneska popusta neposredno na nivoju postavke v polje `RACPOZIC.ZNESEK_POPUST`.
- [ ] **Lojalnostni popust na postavko (`POPUSTLOJALNOST`):**
  - Vnos lojalnostnega popusta: `RACPOZIC.LOJALNOST_POPUST` (%) in `RACPOZIC.ZNESEK_LOJALNOST` (€).
- [ ] **Nastavitev izpisa popustov:**
  - Parameter `POPUSTIZPIS` ('D' / 'N') določa ali se popusti posameznih postavk izpišejo na računu.

---

## 5. Posebna Plačila & POS Integracija
- [ ] **Plačilo na dobavnico:**
  - Nefiskalno plačilo (`FISKALIZACIJA = null`).
  - Izbira partnerja / podjetja iz šifranta.
  - Izpis naziva, naslova in davčne številke partnerja na glavi računa.
- [ ] **Hotel kredit (Soba):**
  - Nefiskalno plačilo za hotelske goste.
  - Vnos ali izbira številke sobe / imena gosta in prenos na izpis računa.
- [ ] **POS integracija z zunanjimi terminali:**
  - Sprožitev plačilne transakcije preko Android Intent protokola za WorldLine ali Payten POS terminale.
  - Obdelava odziva POS terminala (odobritev / zavrnitev).
  - Samodejno tiskanje POS slipa po zaključenem plačilu.
- [ ] **Darilni boni in kuponi:**
  - Knjiženje plačila z darilnim bonom, vnos številke bona (`BonInput`) ter validacija.

---

## 6. Posebne Operacije na Računih (`RacuniFragment`)
- [ ] **Storno računa:**
  - Možnost storna za račune v statusu 2 (izpisan) in statusu 4 (obračunan).
  - Dialog z izbiro razloga za stornacijo.
  - Pošiljanje stornacije na strežnik preko `setRacun` z novim storno računom ter povezavo na `STORNO_RACUN_ID`.
- [ ] **Delitev računa:**
  - Izbira postavk iz odprtega računa in prenos izbranih količin/postavk na nov samostojen račun.
- [ ] **Združevanje računov:**
  - Združitev dveh odprtih računov / miz v en skupen račun.
- [ ] **Inkaso (polog / dvig gotovine):**
  - Vnos zneska pologa ali dviga iz blagajne ter izpis inkaso potrdila.
- [ ] **Dnevni obračun (Dnevni zaključek / X in Z poročilo):**
  - Pregled prometa po plačilnih sredstvih in davčnih stopnjah za tekoči dan ter tisk poročila zaključka.

---

## 7. Tiskanje & Kopije Računov
- [ ] **Pravilno število kopij izpisa:**
  - Določitev števila kopij računa glede na plačilna sredstva (`NACPLAC.STKOPIJ`, funkcija `stKopijPlacila`).
- [ ] **Inkrement števca kopij (`RACGLAVA.STKOPIJ`):**
  - Pri ponovnem tiskanju že izpisanega računa (Status 2/4) se v bazi poveča števec `STKOPIJ`.
  - Na računu se izpiše oznaka npr. "KOPIJA RAČUNA št. X".
- [ ] **Kuhinjski in šank tiskalnik (Naročila):**
  - Pošiljanje delovnih nalogov / naročil na ločene termične tiskalnike glede na `KUHINJA_ID` in `TOCILNICA_ID`.
- [ ] **Hramba izpisanega računa (`RACIZPISAN`):**
  - Shranjevanje tekstovne oblike izpisa brez ESC kod v `RACIZPISAN` ob zaključku računa.

---

## 8. Konfiguracija & MOBINI Spremenljivke (`Globals`)
- [ ] **Tri-stopenjsko zaporedje branja MOBINI:**
  - 1. stopnja: `MOBINI0` (privzete sistemske nastavitve za `MOBILE_ID = 0`).
  - 2. stopnja: `MOBINI` (specifične nastavitve za napravo `MOBILE_ID`).
  - 3. stopnja: `MOBINI2` (napredne nastavitve za napravo).
- [ ] **Prioritete osebja (`ArrayOfPrioritetaProjektaTp`):**
  - Preverjanje nivoja pooblastil (`PRIORITETA`) pred izvedbo kritičnih operacij (storno, brisanje postavk, odobritev popustov).
