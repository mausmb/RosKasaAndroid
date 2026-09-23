## popust 99
1. Popust na celoten račun (placilo_id=99). V tem primeru se izvede popust na celoten znesek računa in zapiše placilo_id=99 in v racplaci.znesek je zapisan znesek popusta. racplaci.delni_znesek=0
2. VNOS PROCENTA POPUSTA. V racplaci.STATUS se zapiše procent . v RACPLACI.ZNESEK se vpiše vrednost izračunana iz procenta
3. VNOS ZNESEK POPSUTA. Iz zneska se izračuna procent popusta in v racplaci.STATUS se zapiše procent, v RACPLACI.ZNESEK se vpiše znesek.


## ZNESKOVNI popust na racpozic
1. Popust na vrstico pozicijaTP (vpiše se v racpozic.ZNESEK_POPUST)
2. V ta namen potebujemo novi frame/formo za urejanje vrstice naročila. 

## Frame/forma za urejanje vrstice naročila
1. double tap na seznamu artiklo naročila odpre edit formo za urejanje vrstice (količina, popust, opomba, hod in opcijsko enota prodaje če je takšen artikel)
2. button "Opomba" odpre seznam opomb (metoda web servisa getDodatki(token))

## LOJALNOSTNI  popust na racpozic
1. Popust na vrstico pozicijaTP (racpozic.LOJALNOST_POPUST = procent popusta in racpozic.ZNESEK_LOJALNOST = znesek popusta)


## paketi v naročilu
1. Če iz cenika izberemo paket (PAKET=1), se mora v RACPOZIC zapisat sestava paketa. Vizualno pa se prikazuje paket. V naročilu in na izpisu računa.
2. analiziraj Delphi kodo - kje inkako dobimo pomožne  podatke za RACPOZIC.

    pomcena:=dmGisOrder.NajdiCenoZaNivo4(pomnivo4id,'',pomtarifaid,poizvorstrmid,
                                          pomdavkeproc, pompaket,pomnivo1,pompolnjenje,pomem,poprihodekstrmid,dmGisOrder.tblRacGlavaDN_ID.AsString);

Function TdmGisOrder.NajdiCenoZaNivo4(const Pnivo4id: Integer; const Pnaziv: string;
          var ptarifaid:Integer;
          var pizvorstrmid:Integer;
          var pdavekproc:double;
          var ppaket:integer;
          var pnivo1:integer;
          var ppolnjenje:double;
          var pem : Integer;
          var pprihodekstrmid : Integer;
          const pDN_ID:String=''):Currency;
		  

Delphi koda:

procedure TdmGisOrder.ZapisVnarocilo(const PracunId,PpozicijaId,Pnivo4id,Ppaket,Pnivo1_id:integer;
Const Pkol, pep:double; pcena:double; ptarifaid,
pizvorstrmid:Integer;
pprihodekstrmid:Integer;
pdavekproc,ppopustproc:currency;
pdodatekid:integer=0;
ppaketkol:double=1;
paketfaktorcene:Currency=1;
paketcenapaketa:Currency=0);
....
  if Ppaket=0 then begin
    ZapisiPozicijoVnarocilo(PpozicijaId, Pnivo4id,Pnivo1_id, ptarifaid, pizvorstrmid,pprihodekstrmid, pdavekproc,ppopustproc, pcena, Pkol,pep,pdodatekid);
  end;
  if Ppaket=1 then begin
    ZapisiPaketVnarocilo(PpozicijaId,Pnivo4id,Pnivo1_id, ptarifaid, pizvorstrmid,pprihodekstrmid, pdavekproc,ppopustproc,Pkol,ppaketkol, tblCenikVrVr,paketfaktorcene,paketcenapaketa);
  end;
  if Ppaket>10 then begin // obstojeè paket - potreben edit kolièine
    ZapisiPaketVnarocilo(PpozicijaId,Pnivo4id,Pnivo1_id, ptarifaid, pizvorstrmid,pprihodekstrmid, pdavekproc,ppopustproc,Pkol,ppaketkol, tblCenikVrVr,paketfaktorcene,paketcenapaketa);
  end;
  if Ppaket=2 then begin // 31.05.2018
//    exit; // todo kaj èe je mix&match 31.05.2018
   if tblCenikVrVrPom<>nil then begin
     if tblCenikVrVrPom.active then Begin
      ZapisiPaketVnarocilo(PpozicijaId,Pnivo4id,Pnivo1_id, ptarifaid, pizvorstrmid,pprihodekstrmid, pdavekproc,ppopustproc,Pkol,ppaketkol, tblCenikVrVrPom,paketfaktorcene,paketcenapaketa);
   end
   end
     else
       exit;
  end;

tukaj je koda za insert ali update paketa.
procedure TdmGisOrder.ZapisiPaketVnarocilo(const PpozicijaId,Pnivo4id, Pnivo1_id: Integer; ptarifaid: Integer;
pizvorstrmid: Integer;
pprihodekstrmid: Integer;
pdavekproc,ppopustproc: Currency; const Pkol,ppaketkol: Double;
const pdataset:TFDMemTable;
paketfaktorcene:Currency=1;
pCenaPaketa:Currency=0)

...


## posebna plačila, ki zahtevajo dodaten vnos ali dodatne operacije
1. dobavnica
2. hotel kredit
3. POS plačilo
4. darilni boni
5. boni in kuponi