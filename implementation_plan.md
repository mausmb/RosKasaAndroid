# Načrt implementacije: Nalaganje šifrantov iz MobileSetup, avtentikacija (PIN / NFC), rajoni in pravice

Posodobljeno z upoštevanjem vseh komentarjev uporabnika (reference: `FormNFClogin.pas`, `FormSobe.pas`, `DataGisOrder.pas`, `FormKasaMobile.pas`).

---

## 1. Pregled rešitve in povezava z Delphi

### Delphi referenčna logika
1. **Zagonski šifranti (`MobileSetup`)**:
   Ob klicu `getAppConfig` strežnik vrne celoten `MobileSetup`, iz katerega se napolnijo:
   - `MOBILE_SETUP_PLACILA` $\rightarrow$ načini plačil
   - `MOBILE_SETUP_MIZE` $\rightarrow$ mize z atributi `NAZIV`, `RAJON`, `ZAP`
   - `OSEBE` $\rightarrow$ natakarji/uporabniki (PIN, NFC kartica 1 in 2, pravice, prioriteta/popust, veljavnost PIN)
   - `PRIORITETE_PROJEKTOV` $\rightarrow$ seznam dovoljenj (`CAPTION`, `POZICIJA_ID`)
   - `TARIFE` $\rightarrow$ davčne stopnje (`TARIFA_ID`, `NAZIV`, `OZNAKA`, `METODA_ID`, `DAVEK_PROC`)

2. **Rajoni miz (`FormKasaMobile.pas`)**:
   - Iz seznama miz se dinamično sestavi seznam unikatnih rajonov (`RajonList`).
   - Če je rajonov več kot 1 (ali je vklopljen parameter `rajoni`), se prikažejo gumbi za rajone: gumb **"Vsi"** ter **"R 1"**, **"R 2"**, ...
   - Klik na rajon filtrira prikaz miz na tiste z izbranim rajonom. Privzeti začetni rajon določa parameter `rajonDefault`.

3. **NFC branje in dekodiranje (`FormNFClogin.pas`)**:
   - Branje značke iz NFC vmesnika vrne bajte `Tag.getId()`.
   - Ti se pretvorijo v hexadecimalni niz brez ločil (npr. `04A1B2C3D4`) ter v desetiško številko (`Long.parseLong(hex, 16)`).
   - **Prijava natakarja (`vo = 1`)**: koda (tako v desetiški kot v hex obliki) se primerja z zapisi `KARTICA_ID` in `KARTICA2_ID` v šifrantu oseb.
   - **Iskanje sobe za Hotel kredit (`vo = 2`, `FormSobe.pas`)**: ista tehnika branja kartice se uporabi za iskanje gosta/sobe pri plačilu `Hotel kredit` (metoda plačila = 6) prek klica SOAP `getKartprij`.

4. **Struktura niza pravic (`Pravice`)**:
   - Primer niza v bazi: `'111111111111111111111110111001'`.
   - V `PRIORITETE_PROJEKTOV` ima vsaka operacija (npr. *"Lahko stornira račun"*) določen svoj `POZICIJA_ID` (1-based indeks).
   - V Javi (0-based) je preverjanje: `pravice.charAt(pozicijaId - 1) == '1'`.

5. **Lokalna hramba (`CenikLokalno` in `CenikStDniObnova`)**:
   - Če je v `MOBINI` nastavljen `CenikLokalno = 'D'`, se šifranti (osebje, prioritete, mize, tarife, cenik, hitre tipke) shranijo lokalno v JSON datoteke.
   - Šifranti veljajo $N$ dni (`CenikStDniObnova`, npr. 3 dni). Če je lokalni cache svež, se aplikacija lahko zažene in prijavi natakarja tudi ob odsotnosti mrežne povezave.

6. **Prikaz prijavljene osebe v orodni vrstici**:
   - V zgornji vrstici (poleg gumba Odjava) se prikaže ime oziroma inicialke prijavljenega natakarja (npr. `👤 Janez Novak (JN)`).

---

## 2. Podrobne faze implementacije

### Faza 1: Novi podatkovni modeli (`si.ros.RosKasa.models`)

1. **`OsebaTp.java`**
   - Polja:
     - `int osebaId` (`OSEBA_ID`)
     - `String naziv` (`NAZIV`)
     - `String uporabniskoIme` (`UPORABNISKO_IME`)
     - `String pin` (`PIN`)
     - `String privilegiji` (`PRIVILEGIJI`)
     - `String oddelek` (`ODDELEK`)
     - `String karticaId` (`KARTICA_ID`)
     - `String kartica2Id` (`KARTICA2_ID`)
     - `String pravice` (`Pravice` - npr. `"111111111111111111111110111001"`)
     - `Integer prioriteta` (`prioriteta` - max popust v %)
     - `Integer veljavnostPin` (`VELJAVNOSTPIN` - veljavnost v dneh)
     - `Date datumSpremembePin` (`DATUM_SPREMEMBEPIN`)
   - Pomožne metode:
     - `getInicialke()` (npr. "Janez Novak" $\rightarrow$ "JN")
     - `ujemaSeKartica(String cardCode)` (preveri `karticaId` ali `kartica2Id` v hex ali decimalni obliki)
     - `isPinPotekel()` (izračun `DaysBetween(danes, datumSpremembePin) > veljavnostPin`)

2. **`PrioritetaProjektaTp.java`**
   - `String caption` (`CAPTION` - npr. *"Lahko stornira račun"*)
   - `int pozicijaId` (`POZICIJA_ID` - 1-based pozicija v nizu `pravice`)

3. **`TarifaTp.java`**
   - `int tarifaId` (`TARIFA_ID`)
   - `String naziv` (`NAZIV`)
   - `String oznaka` (`OZNAKA`)
   - `Integer metodaId` (`METODA_ID`)
   - `BigDecimal davekProc` (`DAVEK_PROC`)

4. **`MizaTp.java`**
   - `String naziv` (`NAZIV`)
   - `Integer rajon` (`RAJON`)
   - `Integer zap` (`ZAP`)

5. **`PraviceConsts.java`** (konstante pravic po vzoru Delphi `AppConsts.pas`)
   - `SLahkoDelaSKaso = "Lahko dela s kaso"`
   - `SLahkoStorniraRacun = "Lahko stornira račun"`
   - `SLahkoStorniraPozicijoRacuna = "Lahko stornira pozicijo računa"`
   - `SStorniraPoslanoNarocilo = "Stornira poslano naročilo"`
   - `SLahkoBrisePozicijePlacil = "Lahko briše pozicije plačil"`
   - `SVidiVseRacune = "Vidi vse račune"`
   - `SVidiVsaNarocila = "Vidi Vsa Narocila"`
   - `SLahkoPonovnoIzpiseRacun = "Lahko ponovno izpiše račun"`
   - `SvezanaKnjigaRacunov = "Vezana knjiga računov (VKR)"`
   - `SLahkoPozeneObracun = "Lahko požene obračun"`
   - `SpreprecimEditStornoPozicij = "Preprečim urejanje pozicij pri stornaciji računa"`
   - `SLahkoPreklopiCenik = "Lahko preklopi cenik"`
   - `SIzvedeFinancniPregled = "Izvede finančni pregled"`
   - `SIzvedeKompletenFinancniPregled = "Izvede kompleten finančni pregled"`

---

### Faza 2: Razširitev `MobileSetupTp` in SOAP parserja (`RosKasaSoapClient.java`)

1. **V `MobileSetupTp.java`**:
   - `List<OsebaTp> osebe`
   - `List<PrioritetaProjektaTp> prioriteteProjektov`
   - `List<TarifaTp> tarife`
   - `List<MizaTp> mobileSetupMize`
   - `List<Integer> rajoniList` (izpeljan seznam unikatnih rajonov iz miz)

2. **V `RosKasaSoapClient.parseMobileSetupTp()`**:
   - Razčlenitev polj `<OSEBE>` $\rightarrow$ seznam `OsebaTp`
   - Razčlenitev polj `<PRIORITETE_PROJEKTOV>` $\rightarrow$ seznam `PrioritetaProjektaTp`
   - Razčlenitev polj `<TARIFE>` $\rightarrow$ seznam `TarifaTp`
   - Razčlenitev polj `<MOBILE_SETUP_MIZE>` $\rightarrow$ seznam `MizaTp` ter sestava liste unikatnih rajonov.

---

### Faza 3: Shranjevanje, lokalni cache (`CenikLokalno`) in preverjanje pravic v `Globals.java`

1. **Strukture v `Globals.java`**:
   - `cachedOsebje`, `cachedPrioritete`, `cachedTarife`, `cachedMize`, `cachedRajoni`.
   - Tekoči uporabnik: `tekocaOseba` (`OsebaTp`), `tekocaOsebaId`, `tekocaOsebaNaziv`, `vlogaOsebe`, `maxPopOseba`.

2. **Preverjanje pravic**:
   - `boolean osebiDovoljeno(int osebaId, String pravicaCaption)`:
     - Poišče `PrioritetaProjektaTp` za dani `pravicaCaption`.
     - Vzame `pozicijaId`.
     - V nizu `pravice` preveri: `pravice.charAt(pozicijaId - 1) == '1'`.
   - `boolean isDovoljeno(String pravicaCaption)` za trenutno prijavljeno osebo.

3. **Lokalna hramba (`CenikLokalno` / `CenikStDniObnova`)**:
   - Pomožni razred `LocalCacheManager`:
     - Shrani prejete šifrante (`Osebe`, `Prioritete`, `Mize`, `Tarife`) v lokalno datoteko (JSON) z časovnim žigom.
     - Če `CenikLokalno == true` in je povezava ob zagonu nedosegljiva, preveri, ali je lokalna kopija mlajša od `CenikStDniObnova` dni $\rightarrow$ naloži iz lokalne shrambe.

---

### Faza 4: Prijava s PIN-om in NFC (`LoginFragment.java`)

1. **PIN Prijava**:
   - Ob kliku na prijavo s PIN-om:
     - `Globals.getInstance().najdiOseboZaPin(pin)`
     - Če oseba ni najdena $\rightarrow$ javi napako *"Napačna PIN koda!"*.
     - Če `pCheckPinPotekel` in je potekel $\rightarrow$ javi opozorilo.
     - Preveri pravico: `osebiDovoljeno(oseba.getOsebaId(), PraviceConsts.SLahkoDelaSKaso)`.
     - Nastavi prijavljeno osebo v `Globals`.
     - Preusmeri na `MizeFragment`.

2. **NFC Prijava (`NfcHelper` / `NfcAdapter`)**:
   - V `LoginFragment` vklop NFC čitalnika (če naprava podpira NFC in je `pNfc` vklopljen).
   - Ob zaznavi značke:
     - Pretvorba bajtov `tag.getId()` v `hex` in `decimal`.
     - Klic `Globals.getInstance().najdiOseboZaKartico(tagCode)`.
     - Samodejna prijava prepoznane osebe (enako kot pri uspešnem PIN-u).
   - *Opomba za naprej:* Ista metoda dekodiranja NFC niza (`NfcHelper.decodeTagId(tag)`) bo uporabljena v `FormSobe` za iskanje gosta pri plačilu `Hotel kredit`.

---

### Faza 5: Dinamični prikaz miz in filtriranje po rajonih (`MizeFragment.java`)

1. **Mize**:
   - Uporabi `Globals.getInstance().getCachedMize()` (urejene po `zap`).
   - Fallback: če je šifrant prazen, se prikažejo privzete mize 1..30.
2. **Rajoni (filter)**:
   - Če je unikatnih rajonov > 1 ali `rajoni == true`:
     - Nad mrežo miz se prikaže vodoravna vrstica gumbov za rajone: **"Vsi"** ter **"R 1"**, **"R 2"**, ...
     - Izbran je privzeti rajon (`rajonDefault` oz. "Vsi").
     - Ob kliku na gumb rajona se takoj filtrira prikaz miz za izbrani rajon.

---

### Faza 6: Prikaz prijavljenega natakarja in uveljavitev pravic na UI

1. **Prikaz v orodni vrstici**:
   - V glavi aplikacije (poleg gumba za odjavo) se prikaže ikona in ime ali inicialke prijavljenega natakarja: npr. `👤 Janez Novak`.
2. **Pravice**:
   - Storno poslanega naročila / vrstice $\rightarrow$ preverjanje `SStorniraPoslanoNarocilo` / `SLahkoStorniraPozicijoRacuna`.
   - Storno računa v `RacuniFragment` / `PlacilaFragment` $\rightarrow$ preverjanje `SLahkoStorniraRacun`.
   - Filter vseh računov $\rightarrow$ preverjanje `SVidiVseRacune`.
   - Finančni pregled / obračun $\rightarrow$ preverjanje `SIzvedeFinancniPregled` / `SLahkoPozeneObracun`.
