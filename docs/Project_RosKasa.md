# Opis Android aplikacije RosKasa 

## Splošno:
1. delovanje preko web servisov, ki se nahajajo C:\GeminiCli\RosKasa\docs\RosKasa_ceniki_wsdl.wsdl in delphi import wsdl v unit RosKasa_ceniki_wsdl.pas
2. tiskanje preko bluetooth vmesnika
3. Primaren design je telefon (portrait). Sekundaren design landscape.
4. prijava v aplikacijo : PIN koda ali NFC kartica
5. Aplikacija mora delovatu tudina 32-bit Androidu.

## Prvi zagon na novi napravi (v internem pomnilniku ne obstaja datoteka z token, url in mobile_id):
1. Aplikacija prikaže prijavno okno, ki vsebuje edit polja:
 - url
 - mobile_id
 - username
 - password
2. Pri potrdtivi se kliče metoda "getOsebaToken" za vnešen url.
3. Po uspešnem klicu se shrani v interni pomnilnik datoteka s podatki (token, url in mobile_id). 


## Zagon Aplikacije
1. iz internega pomnilnika se prebere datoteka s podatki  (token, url in mobile_id) in podatki se hranijo v globlane sprejemljivke ali globalni recors/class ki je ves čas na voljo med delom aplikacije.
2. klici metode za setup in inicialne podatke "GetAppConfigRsTp" in rezultat je "MobileSetupTp". 
V tem odogovoru so vsi podatki za delovanje aplikacije, tiskanja, esc code za tiskalni,  osebja, prioritet osebja, plačil, mize, davčne tarife in "MOBINI" Json  struktura za vklop ali izklop delovanja posameznih funkcij.

## Temelji delovanja - ekrani (frame ali page). 
1. Naročila (orders) - trenuten design "C:\GeminiCli\RosKasa\docs\FormKasaMobile_TabCenik.png"
 - glavne krmilne funkcije : mize, računi, plačila, naroči(post), 4 hitre tipke (shortcuts), ostale funkcije (brisanje, preklop tipke/lista cenika), preklop na cena2, hod-i, dodanta tipka katere funkcija se nastavi preko MOBINI
 - sekcija seznam artiklov . kaj je na nekem naročilu
 - sekcija hitre tipke za izbor (C:\GeminiCli\RosKasa\docs\FormKasaMobile_TabCenik.png) - tipke so organiziran za "drill in" skupina in potem artikli za izbor. Lahko se gnezdi v globino. Vedno je na prvem mestu tipka "nazaj" kar pomeni 1 nivo višje.
 - sekcija cenik - lista artiklov za izbor (C:\GeminiCli\RosKasa\docs\FormKasaMobile_TabCenik_lista)
 - primer za Landscape (C:\GeminiCli\RosKasa\docs\FormKasaMobile_TabCenik_Landscape.png) - funkcijske tipke na desno stran
2. Mize in rajoni C:\GeminiCli\RosKasa\docs\FormKasaMobile_TabMize.png
- glavne krmilne tipke : mize, računi, plačila, odjava
- sekcija rajoni (filter za prikazane mize). Vidnost oziroma aktivnost te sekcije se nastavi.
- zamenjaj "marker", info o aplikaciji, 2 nastavljivi tipki,
3. Računi C:\GeminiCli\RosKasa\docs\FormKasaMobile_TabRacGlava.png)
- glavne krmilne tipke : mize, izpisani ali odprti računi, plačila, naročilo, odjava, 
- brisanje, združi račune, delitev računa, zamenjaj marke, izpis računa
- storno računa, inkaso, obračun, test tiskanja, osveži podatke

4. Plačila C:\GeminiCli\RosKasa\docs\FormKasaMobile_TabRacPlaci.png
- glavne krmilne tipke : mize, računi, naročilo, odjava, 
- sekcija seznam plačil na računu
- tipke brisanje, opis, delovni nalogom izpis računa
- sekcija tipke plačil, ki se kreirajo na podlagi "MobileSetupTp.MOBILE_SETUP_PLACILA"


## Organizacija podatkov:
1. method "GetRacunRsTp" nam vrne podatke "RacunTp" o računu, ki vsebujejo :
-  RacunTp - podatki glave računa
-  RACPLACI : ArrayOfPlaciloTp (vsa plačila na računu)
-  RACPOZIC : ArrayOfPozicijaTp (vse vrstice/pozicije na računu)
2. ko dobimo podatke, si jih shranimo, ker jih potrebujemo pri post(metoda "setRacun") računa (potebujemo orginalne podatke in  nove podatke)
3. RACIZPISAN - tekst oblika izpisanega računa.
4. post račun : meotda "setRacun" s paramteri RacunTp, mobileId, token. RacunTp vsebuje nove podatke in originalne podatke , če smo račun pridobili iz serverja.
5. Seznam računov (RACUN_ID,STATUS,MARKER,ZNESEK, KASIRAL,STORNO_RACUN_ID): metoda "GetRacuniSeznamRsTp" odgovor Racuni: ArrayOfRacunSeznamTp, ki vsebuje "RacunSeznamTp"
6. ostali šifranti - ceniki: metoda "getCenik" vsebuje cene v odgovoru "GetCenikRsTp" CenikGlTp , ki vsebuje "CENIKVR:ArrayOfCenikVrTp" za artikle in opcijsko sestavo "CENIKVRVR:ArrayOfCenikVrVrTp"
7. Info : Delphi interpretacija podatkov (klici metod , kjer smo uporabili tfdmemetable) C:\GeminiCli\Delphi\RosKasaLight16\DataGisOrder.pas
8. Info : Delphi global variables C:\GeminiCli\Delphi\RosKasaLight16\Globals.pas 

## Splošna pravila delovanja
1. preprečiti kakršenkoli Android ANR pri izvajanju
2. RACGLAVA.STATUS=1 račun oz. naročilo v obdelavi. STATUS=2 izpisan račun. STATUS=4 obračunan račun. Status 2 in 4 se lahko pod pogoji stornirata.
3. Plačilne metode : GetNacPlacRsTp.NACPLAC : ArrayOfNacPlacTp.NacPlacTp



## Nekaj  splošnih pravil :
1. Mize : če za mizo (RACGLAVA.MARKER)  obstaja račun - se obarva rdeče in tap na mizo odpre "naročilo"
2. Rajoni (če so vklopljeni) tap na rajon - naredi filter za mize tega rajona.
3. Naročila : tap na seznamu odpre možnost editiranja (količina, popust in opomba)
4. Iskanje v listah : ne upoštevajo se velike ali male črke. Iskanje kjerkoli ( like %:iskalnik%)
5. Ko se izvajajo operacije s serverjem, aplikacija NE sme dovoliti ponovnega proženja česarkoli.
6. Prioritete (ArrayOfPrioritetaProjektaTp) se preverjajo za vse operacije, ki so v prioritetah. To je advance funkcija, ki jo bom razdelal naknadno.

## Tiskanje:
1. Uporablja se bluetooth tiskalnik. Default je UTF-8
2. Možnost nastavljanja širine.
3. Dve operaciji : text preview (brez ESC kod) in print (z ESC kodami)
4. ESC kode so v GetAppConfigRsTp.MobileSetup.STEVILOZNAKOV, GetAppConfigRsTp.MobileSetup.ESCBOLDON,...
primer kaj je v bazi in dovimo preko web servisa:

ESCBOLDON: 27-33-8

Delphi koda za pretvorbo:
function PretvoriROSESC(vhodnistring:string):string;
var
  i,iValue,iCode: Integer;
  pomchar:string;
begin
  Result:='';
  for i := Low(vhodnistring) to High(vhodnistring) do begin
    if vhodnistring[i]='-' then begin
      Result:=Result+chr(strtoint64(pomchar));
      pomchar:='';
    end
    else Begin
      val(vhodnistring[i], iValue, iCode);
      if icode=0 then
        pomchar:=pomchar+vhodnistring[i];
    End;
  end;
  if high(Result)>0 then
    Result:=Result+chr(strtoint64(pomchar));
end;



## Posebne funkcije, ki jih bomo obdelali kasneje.
1. Delitev računa
2. Združevanje računov
3. Storno računa
4. Inkaso
5. Obračun
6. Posebna plačila : 
- hotel kredit
- plačilo na dobavnico
- kreditna kaeritca via Intent WorldLine
- kreditna kaeritca via Intent Payten






