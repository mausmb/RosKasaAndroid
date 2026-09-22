# Hitretipke in liste artiklov RosKasa 

## Splošno:
1. Tap on ali tap on item v listi artiklov povzroči knjižbo artikla v naročilo (RACGLAVA.RACPOZIC)
2. Cena artikla se določi po pogojih spodaj
3. Posebnosti npr. lestvica točenja (0.1 , 0.2., 0.25, ...) se določi glede na atribute artikla 
4. Default skupina je vedno 1
5. NAROČILA tipka "preklopi cenik" preklaplja med prikazom hitrih tipk ali liste artiklov. Če smo v listi se "preklopi cenik"  postavimo v tipke skupina 1 (default)
6. upoštevaj spremenljivke v globals
7. Delphi kodo kjer nimaš povezave ali izvorne kode samo pripravi usterzno metodo 


## Cena artikla hitra tipka ali lista:
1. poiščemo artikle v ceniku in mu določimo ceno
2. imamo več vrst artiklov :
- navaden artikel se knjiži  1:1
- paket artikel - knjiži se sestava paketa in prikazuje kot paket v listi Naročila
- poseben artikle ki zahteva vnos cene
- poseben  artikel ki prikaže lestvico polnjenja, ki ustrezno spremeni ceno (cena = cena*polnjenje)

Kje so cene : 
 mojcenik :GetCenikRsTp;
 mojcenikvr : ArrayOfCenikVrTp;
 mojcenikvrvr : ArrayOfCenikVrVrTp;
 mojcenikvrcene : ArrayOfCenikVrCeneTp;

## Delphi koda za cene hitre tipke  
 
procedure TfrmKasaMobile.ButtonHTClick(Sender: TObject);
var poms : string;
    pomnivo4id,pomtarifaid,poizvorstrmid, pompaket,pomnivo1,pomplac,pomtipka,pomem,poprihodekstrmid :integer;
    pomcena,pomdavkeproc,pomep,pompolnjenje: double;
    pomdodatekid:Integer;
    ProgramiramoZamudnino,ProgramiramoVracilo:Boolean;
    pREPAIR:STRING;
begin
//09.11
//(*
try
 if not lv_narocilo.Enabled then begin
    RosMessage(SNapakaPlačanRačun);
    exit;
 end;
  ProgramiramoZamudnino:=False;
  ProgramiramoVracilo:=False;
  pomnivo4id:=0;
  poms:='';
  poms:=(sender as tbutton).text;
  poms:=(sender as tbutton).nazivtipke;
  if (sender as tbutton).integer1<>0 then
    pomnivo4id:=(sender as tbutton).integer1;

  if (sender as tbutton).Integer2<>0 then
    pomtipka:=(sender as tbutton).Integer2
  else
    pomtipka:=0;

  if (sender as tbutton).DodatekId<>0 then
    pomdodatekid:=(sender as tbutton).DodatekId
  else
    pomdodatekid:=0;
  if pomnivo4id=0 then exit;


// preveri ali je tipka NIVO4 ali drugi Level

  frmKasaMobile.PostaviPozicPlacTabe;

  IzbranNivo4HT:=0;
  pomep:=1;
//  if (pomnivo4id>0) and lv_narocilo.Enabled and (pomnivo4id<>98989897) and (pomnivo4id<>98989898) then Begin
  if (pomnivo4id>0) and lv_narocilo.Enabled  then Begin
     IzbranNivo4HT:=pomnivo4id;
     pomep:=dmGisOrder.NajdiEPzaNivo4IzTipk(pomnivo4id,pomtipka);
     if not LESTVICAEPHT and (pomep=0) then // 13.06.2020
        pomep:=1
     else begin // lestvica za EP
        if pomep=0 then begin // delamo z lestvico
          frmKasaMobile.PostaviEdit(pomnivo4id,(sender as tbutton).text); // 13.06.2020
//          PripraviPogledHitreTipke;
          exit;
        end;
     end;

    pomcena:=dmGisOrder.NajdiCenoZaNivo4(pomnivo4id,'',pomtarifaid,poizvorstrmid,
                                          pomdavkeproc, pompaket,pomnivo1,pompolnjenje,pomem,poprihodekstrmid,dmGisOrder.tblRacGlavaDN_ID.AsString);




// POLNJENJE - CENA NA LITER !!! VKLOPI KO BO WIN KASA VERZIJA !! ČE JE EM )2 LITER NE DELUJE PRAVILNO , ČE JE BUTELJAKA EM=1 KOS DELA PRAVILNO
    pompolnjenje:=roundto(pompolnjenje,-4);
    if pomep<>1 then begin
      if CenaPolnjenje= 'D' then Begin
         if (pomep<>pompolnjenje) and (pompolnjenje<>0) then // 05.05.2018 kontrola da ne bi preračunavali cene ko je originalno polnjenje
            pomcena:=pomcena/pompolnjenje; // če je cena na tipki za 0,1 ==> preračun cene
       end
       else
         begin // 09.05.2018 dodano zaradi primera krka
         if (pomep<>pompolnjenje) and (pomem=1) then // delamo z buteljko - kos preračunamo ceno
            pomcena:=pomcena/pompolnjenje // če je cena na tipki za 0,1 ==> preračun cene
         else
           pomcena:=pomcena;
       end
    end;
// 05.05.2018 - upoštevaj polnjenje tudi za kos !! Kaj je z ceno ? buteljke cena je za buteljko ==> ne smemo preračunavat na liter ?
//     if (pomep=1) and (pompolnjenje<>1) then
//        pomep:=pompolnjenje;

    if pomcena=-99  then begin
//       RosMessage(SNiCeneZa+' '+pomnivo4id.ToString+' !',frmKasaMobile);
       exit; // ni cene
    end;
    if (pomcena=0) and not CENA0DOVOLJENA then begin
//       RosMessage(SNiCeneZa+' '+pomnivo4id.ToString+' !',frmKasaMobile);
       exit; // ni cene
    end;
// light verzija brez mix/match
//    dmGisOrder.ZapisVnarocilo(dmGisOrder.TekociRacunid,0,pomnivo4id,pompaket,pomnivo1,1,pomep,pomcena,pomtarifaid,poizvorstrmid,poPrihodekstrmid,pomdavkeproc,0,pomdodatekid);            // količina se mora prenesti iz artikla npr. 0,03 whiseky ipd.

//(*
    if pompaket=2 then begin
        if frmPartner=nil then
           Application.CreateForm(TfrmPartner, frmPartner);
  {$REGION 'partner parametri'}
        frmPartner.ModelMixMatch:=True;
        frmPartner.PracunId:=dmGisOrder.TekociRacunid;
        frmPartner.PpozicijaId:=0;
        frmPartner.Pnivo4id:=pomnivo4id;
        frmPartner.ppaket:=pompaket;
        frmPartner.Pnivo1_id:=pomnivo1;
        frmPartner.pkol:=1;
        frmPartner.Pep:=pomep;
        frmPartner.pcena:=pomcena;
        frmPartner.ptarifaid:=pomtarifaid;
        frmPartner.pizvorstrmid:=poizvorstrmid;
        frmPartner.pPrihodekStrmId:=poPrihodekstrmid;
        frmPartner.pdavekproc:=pomdavkeproc;
        frmPartner.ppopustproc:=0;
  {$ENDREGION}
        {$IF DEFINED(IOS) or DEFINED(ANDROID)}
         frmpartner.show;
        {$ENDIF}
    end;

        if ((pomcena=-1) or (pomcena=-2))
        and not (dmGisOrder.tblCenikNAZIV50.AsString.Contains('KAVCIJA')) // and VnosCeneZaVse
        then begin   //29.07.2021 če je kavcija je problem
          if (pompaket<>2) and VnosCeneZaVse then Begin
             SetLength(PomArrayInteger,10);
             SetLength(PomArrayCurrency,5);
             PomArrayInteger[0]:=dmGisOrder.TekociRacunid;
             PomArrayInteger[1]:=0;
             PomArrayInteger[2]:=pomnivo4id;
             PomArrayInteger[3]:=pompaket;
             PomArrayInteger[4]:=pomnivo1;
             PomArrayInteger[5]:=pomtarifaid;
             PomArrayInteger[6]:=poizvorstrmid;
             PomArrayInteger[7]:=poPrihodekstrmid;

             PomArrayCurrency[0]:=pomcena;
             PomArrayCurrency[1]:=pomdavkeproc;
             PomArrayCurrency[2]:=pomep;
             PomArrayCurrency[3]:=1; // pkolicina
             PomArrayCurrency[4]:=0; // ppopust

             if frmNumDlg=nil then
               Application.CreateForm(TfrmNumDlg, frmNumDlg);
             frmNumDlg.pVrednost:=0;
             if pomcena=-2 then begin
               frmNumDlg.btnMinus.Visible:=True;
               frmNumDlg.pMinusPredznak:=True
             end
             else begin
               frmNumDlg.btnMinus.Visible:=False;
               frmNumDlg.pMinusPredznak:=False;
             end;
             frmNumDlg.edNumValue.enabled:=True;
             frmNumDlg.ShowDialog(self,sVnoscene,dlgVnosCenaOK, dlgVnosCenaCancel);
          end
          else Begin
            if not KRONOLOGIZKLOP AND DEBUGL1 then dmGisOrder.VpisiKronologijo('Napaka - pogoji vnosa niso izpolnjeni za N4: '+pomnivo4id.ToString+' Cena: '+pomcena.ToString+' R:'+dmGisOrder.TekociRacunid.ToString);
            RosMessage('Napaka - pogoji vnosa niso izpolnjeni !');
          End;

    end
    else  Begin
        if (pompaket<>2) and not ProgramiramoZamudnino and not ProgramiramoVracilo then Begin
           dmGisOrder.ZapisVnarocilo(dmGisOrder.TekociRacunid,0,pomnivo4id,pompaket,pomnivo1,1,pomep,pomcena,pomtarifaid,poizvorstrmid,poPrihodekstrmid,pomdavkeproc,0,pomdodatekid);            // količina se mora prenesti iz artikla npr. 0,03 whiseky ipd.
           if pomnivo4id=BonNivo4Id then begin
              if not BonInput(pomcena) then // vnos številke bona
                 RosMessage('Napaka knjiženja darilnega bona !');
             end;
        end;
     end
   end
  else   // tipka je drugi Level
  if (pomnivo4id<0) then Begin //14.09.2021 Hitretipke funkcije nad 1000 funkcije
     if abs(pomnivo4id)<100000 then // skupina v globino
       NapolniHitreTipkeGumbe(abs(pomnivo4id))
     else begin // funkcijska tipka

     end;
//  *)
  End
  else   // tipka je drugi Level
     if abs(pomnivo4id)<100000 then // skupina v globino
       NapolniHitreTipkeGumbe(abs(pomnivo4id))
     else begin // funkcijska tipka

     end;
  End;
except
if not KRONOLOGIZKLOP AND DEBUGL1 then dmGisOrder.VpisiKronologijo(SNapakaEXCEPTIONButtonHTClick);
end;
//*)
end;

 
## Delphi koda za cene v listi/seznamu artiklov

procedure TfrmKasaMobile.Napolnilv_cenik;
VAR
   NacinProdaje,tempn4 :Integer;
    tempnazivn4:string;
    pomnacinprodaje :integer;
    pompolnjenje:double;
    INICENA:currency;
    tempbook:Tbookmark;
    Dalje:Boolean;
begin
try
//  dmGisOrder.tblCenik.DisableControls;
  frmKasaMobile.lv_cenik.BeginUpdate;
  frmKasaMobile.lv_cenik.Items.Clear;
  dmGisOrder.tblCenik.first;
  while not dmGisOrder.tblCenik.eof do
  begin
    Dalje:=False;
//    if CENA2AKTIVNA and (dmGisOrder.tblCenikCena2.AsCurrency<>0) then // 13.04.2022 da bi dobili vse cene
//    if CENA2AKTIVNA  then
    if CENA2AKTIVNA  and (dmGisOrder.tblCenikCena2.AsCurrency<>0) then // 09.07.2024 dodan pogoj da je vedno neka cena
      INICENA:=dmGisOrder.tblCenikCena2.AsCurrency
    else
      INICENA:=dmGisOrder.tblCenikCena.AsCurrency;

if CENA2AKTIVNA and (MODELCENA2=2) and (INICENA<>0) then // gremo dalje
  Dalje:=True;

if not CENA2AKTIVNA and ((INICENA<>0) or CENA0DOVOLJENA) then //gremo dalje za cena1
  Dalje:=True;

if CENA2AKTIVNA and (MODELCENA2=1) and ((INICENA<>0) or CENA0DOVOLJENA) then  // gremo dalje Adria
   Dalje:=True;

if not CENA0CENIK and (INICENA=0) then  // za izključitev izbora artiklov s ceno0
   Dalje:=False;

if dmGisOrder.tblCenikNIMOZNAIZBIRA.AsBoolean=true then   //25.05.2022
     Dalje:=False;



    if Dalje then Begin
        tempn4:=dmGisOrder.tblCenikNIVO4_ID.AsInteger;
        tempnazivn4:=dmGisOrder.tblCenikNaziv50.AsString;
//        tempnazivn4:=dmGisOrder.tblCenikNaziv.AsString;
    {$IF DEFINED(IOS) or DEFINED(ANDROID)}
//        frmKasaMobile.AddCene(frmKasaMobile.lv_cenik.Items, dmGisOrder.tblCenikNaziv.AsString,INICENA,dmGisOrder.tblCenikNivo4_id.AsInteger);
        frmKasaMobile.AddCene(frmKasaMobile.lv_cenik.Items, dmGisOrder.tblCenikNaziv50.AsString,INICENA,dmGisOrder.tblCenikNivo4_id.AsInteger);
    {$ENDIF}
    {$IF DEFINED(MSWINDOWS)}
    if PLUAKTIVEN and (dmGisOrder.tblCenikPLU.AsString<>'') then
      tempnazivn4:=tempnazivn4+' ['+ StringOfChar('0', 5-Length(dmGisOrder.tblCenikPLU.AsString)) + dmGisOrder.tblCenikPLU.AsString+']';

//      tempnazivn4:=tempnazivn4+' '+RightStr(StringOfChar('0',length) + dmGisOrder.tblCenikPLU.AsString, length );
    tempbook:=dmGisOrder.tblCenik.Bookmark;
    NacinProdaje:=dmGisOrder.NacinProdajeZaNivo4(tempn4,pompolnjenje,pomnacinprodaje, inicena);
    case NacinProdaje of
       1,3,4,9,10 : Begin // od 0,1 - 1
           frmKasaMobile.AddCene(frmKasaMobile.lv_cenik.Items,
           tempnazivn4+' (0,1/0,125/0,5/1)',
           INICENA,
           tempn4)

       End;
       2 : begin // žgane pijače >=0,01 and <= 0,05
           frmKasaMobile.AddCene(frmKasaMobile.lv_cenik.Items,
           tempnazivn4+' (0,03/0,05)',
           INICENA,
           tempn4)
       end;
       else begin
           frmKasaMobile.AddCene(frmKasaMobile.lv_cenik.Items,
           tempnazivn4,
           INICENA,
           tempn4);
       end;
      end;
     dmGisOrder.tblCenik.GotoBookmark(tempbook);
    {$ENDIF}

        end; // if dalje

    dmGisOrder.tblCenik.next;
  end;
//  dmGisOrder.tblCenik.EnableControls;
  frmKasaMobile.lv_cenik.EndUpdate;
except
  frmKasaMobile.lv_cenik.EndUpdate;
  if not KRONOLOGIZKLOP AND DEBUGL1 then dmGisOrder.VpisiKronologijo(SNapakaEXCEPTIONNapolnilv_cenik);
end;
end;


### on tap list/seznamu
procedure TfrmKasaMobile.lv_cenikItemClick(const Sender: TObject;
  const AItem: TListViewItem);
begin
  LyNarFunkcTipke.Align:=TAlignLayout.Bottom;
  LyNarNarocilo.Align:=TAlignLayout.Top;
  LyNarNarocilo.visible:=True;
  LyNarNarocilo.Align:=TAlignLayout.Top;
  LyNarFunkcTipke.Align:=TAlignLayout.top;
  if LV_narocilo.Enabled and Assigned(lv_cenik.Selected) then Begin
    frmKasaMobile.PostaviEdit(StrToInt(lv_cenik.Items[lv_cenik.Selected.Index].Objects.DrawableByName(SNivo4id).Data.AsString),
    lv_cenik.Items[lv_cenik.Selected.Index].Objects.DrawableByName('naziv').Data.AsString);
  end
  else
    if not LV_narocilo.Enabled  then
      RosMessage(sNapakaRacunZaklenjen,frmKasaMobile);
  btn1CenikTipkeClick(self);

end;

Procedure TfrmKasaMobile.lv_cenikKnjizi(pnivo4:Integer;pomep:double=1);
var pomtarifaid,poizvorstrmid,pomnivo4id, pompaket,pomnivo1,pomem,poprihodekstrmid :integer;
  pomcena,pomdavkeproc,pompolnjenje: double;
  ProgramiramoZamudnino,ProgramiramoVracilo:Boolean;
  pREPAIR:STRING;
begin
if pnivo4<>0 then begin // pnivo4 je property od button - tako dobimo šifro artikla ki je vedno nivo4_id iz cenika
//        pomep:=1;
  pomnivo4id:=pnivo4;
  pomcena:=dmGisOrder.NajdiCenoZaNivo4(pomnivo4id,'',pomtarifaid,poizvorstrmid,pomdavkeproc, pompaket,pomnivo1,pompolnjenje,pomem,poprihodekstrmid,dmGisOrder.tblRacGlavaDN_ID.AsString);
  ProgramiramoZamudnino:=False;
  ProgramiramoVracilo:=False;
  pompolnjenje:=roundto(pompolnjenje,-4);
  if (pomep<>1) and (pompolnjenje<>0) then Begin
     if CenaPolnjenje= 'D' then
       pomcena:=pomcena/pompolnjenje
     else Begin
         if (pomep<>pompolnjenje) and (pomem=1) then // delamo z buteljko - kos preračunamo ceno
            pomcena:=pomcena/pompolnjenje // če je cena na tipki za 0,1 ==> preračun cene
     End;
  End;
// za kasalight
//   dmGisOrder.ZapisVnarocilo(dmGisOrder.TekociRacunid,0,pomnivo4id,pompaket,pomnivo1,1,pomep,pomcena,pomtarifaid,poizvorstrmid,poPrihodekstrmid,pomdavkeproc,0); // količina se mora prenesti iz artikla npr. 0,03 whiseky ipd.


//(*
        if pomcena=-99 then begin
           RosMessage(SNiCeneZa+' '+pomnivo4id.ToString+' !',frmKasaMobile);
           exit; // ni cene
        end;
        if (pomcena=0) and not CENA0DOVOLJENA then begin
           RosMessage(SNiCeneZa+' '+pomnivo4id.ToString+' !',frmKasaMobile);
           exit; // ni cene
        end;
       if pompaket=2 then begin
          if frmPartner=nil then
             Application.CreateForm(TfrmPartner, frmPartner);
          frmPartner.ModelMixMatch:=True;
          frmPartner.PracunId:=dmGisOrder.TekociRacunid;
          frmPartner.PpozicijaId:=0;
          frmPartner.Pnivo4id:=pomnivo4id;
          frmPartner.ppaket:=pompaket;
          frmPartner.Pnivo1_id:=pomnivo1;
          frmPartner.pkol:=1;
          frmPartner.Pep:=pomep;
          frmPartner.pcena:=pomcena;
          frmPartner.ptarifaid:=pomtarifaid;
          frmPartner.pizvorstrmid:=poizvorstrmid;
          frmPartner.pPrihodekStrmId:=poPrihodekstrmid;
          frmPartner.pdavekproc:=pomdavkeproc;
          frmPartner.ppopustproc:=0;
         {$IF DEFINED(IOS) or DEFINED(ANDROID)}
           frmpartner.show;
         {$ENDIF}
       end;
        if ((pomcena=-1) or (pomcena=-2))
        and not (dmGisOrder.tblCenikNAZIV50.AsString.Contains('KAVCIJA')) // and VnosCeneZaVse
        then begin   //29.07.2021 če je kavcija je problem
//        if (pomcena=-1) or (pomcena=-2) then begin   //29.07.2021 če je kavcija je problem
          if (pompaket<>2) and VnosCeneZaVse then Begin
             SetLength(PomArrayInteger,10);
             SetLength(PomArrayCurrency,5);
             PomArrayInteger[0]:=dmGisOrder.TekociRacunid;
             PomArrayInteger[1]:=0;
             PomArrayInteger[2]:=pomnivo4id;
             PomArrayInteger[3]:=pompaket;
             PomArrayInteger[4]:=pomnivo1;
             PomArrayInteger[5]:=pomtarifaid;
             PomArrayInteger[6]:=poizvorstrmid;

             PomArrayCurrency[0]:=pomcena;
             PomArrayCurrency[1]:=pomdavkeproc;
             PomArrayCurrency[2]:=pomep;
             PomArrayCurrency[3]:=1; // pkolicina
             PomArrayCurrency[4]:=0; // ppopust

             if frmNumDlg=nil then
               Application.CreateForm(TfrmNumDlg, frmNumDlg);
             frmNumDlg.pVrednost:=0;
             if pomcena=-2 then begin
               frmNumDlg.btnMinus.Visible:=True;
               frmNumDlg.pMinusPredznak:=True
             end
             else begin
               frmNumDlg.btnMinus.Visible:=False;
               frmNumDlg.pMinusPredznak:=False;
             end;

             frmNumDlg.edNumValue.enabled:=True;
             frmNumDlg.ShowDialog(self,sVnoscene,dlgVnosCenaOK, dlgVnosCenaCancel);
          end
          else Begin
            if not KRONOLOGIZKLOP AND DEBUGL1 then dmGisOrder.VpisiKronologijo('Napaka - pogoji vnosa niso izpolnjeni za N4: '+pomnivo4id.ToString+' Cena: '+pomcena.ToString+' R:'+dmGisOrder.TekociRacunid.ToString);
            RosMessage('Napaka - pogoji vnosa niso izpolnjeni !');
          End;
        end
        else Begin
      {$IF DEFINED(MSWINDOWS)}



      {$ENDIF}
          if (pompaket<>2) and not ProgramiramoZamudnino and not ProgramiramoVracilo then Begin
             dmGisOrder.ZapisVnarocilo(dmGisOrder.TekociRacunid,0,pomnivo4id,pompaket,pomnivo1,1,pomep,pomcena,pomtarifaid,poizvorstrmid,poPrihodekstrmid,pomdavkeproc,0); // količina se mora prenesti iz artikla npr. 0,03 whiseky ipd.
             if pomnivo4id=BonNivo4Id then begin
              if not BonInput(pomcena) then
                 RosMessage('Napaka knjiženja darilnega bona !');
             end;
          End;
        end;
//        *)
  end
end;



## Odgovori na vprašanja :

1. Poslovna logika in preračun cen (Cene, EP in Polnjenje)
Preračunavanje cene glede na polnjenje in EP (pomep / pompolnjenje):
Kako točno deluje logika za CenaPolnjenje = 'D' nasproti primeru, ko je pomem = 1 (kos / buteljka)?
Ali se pri pijačah (npr. 0,1 L ali žganih pijačah 0,03 L) vedno deli osnovna cena z originalnim polnjenjem (pomcena / pompolnjenje), ali obstajajo ceniki, kjer je cena že določena na enoto točenja?
Dovoljene cene 0,00 EUR:
Kdaj in pod kakšnimi pogoji mora biti aktivna zastavica CENA0DOVOLJENA oz. CENA0CENIK? Ali obstaja možnost, da artikel s ceno 0 knjižimo kot brezplačni vzorec ali popust?
Ročni vnos cene (pomcena = -1 ali -2):
Kakšna je natančna razlika med -1 (prosti vnos cene) in -2 (vnos cene z omogočenim negativnim predznakom za vračila/popuste)?
Datoteka omenja posebnost pri kavcijah (NAZIV50.Contains('KAVCIJA')). Kako se kavcije obravnavajo, kadar imajo negativno ali spremenljivo ceno?

Odgovori:
imamo "enoto mere" in imamo "polnjenje" in imamo "nacin prodaje" ki vplivajo na preračun in ocpijsko prikaz na ekranu lestvice

function TfrmRacPozicEdit.PreveriNacinprodaje(var input: Double): Boolean;
begin
//
  Result:=false;
  case Nacinprodaje of
   1,3,4,9,10 : Begin // od 0,1 - 1
      if (input>=0.1) and (input<=1) then
         result:=true;
   End;
   2 : begin // žgane pijače >=0,01 and <= 0,05
//      if (input>=0.01) and (input<0.051) then // 05.05.2018 dodano za kos/liter/0,7
      if (input>=0.01) and (input<=1) then
         Result:=true;
   end;
   5: begin // karkoli teža v kg
      if (input>=0.01) then
         result:=true;
   end;
   6: begin // Integer >=1 oz. noedit
      if round(input) = input then
         result:=true;
   end;
   else begin
      if input=1 then
         Result:=True;
   end;
  end;
end;

CenaPolnjenje = 'D' - 99% primerov je takšen preračun

CENA0DOVOLJENA true : pomeni da lahko knjižimo artikle s ceno 0
CENA0CENIK True : pomeni da v listi/seznamu cenika prikazujemo takšne artikle. Po default je False. Ne prikazujemo artiklo s ceno 0


2. Posebni artikli (Paketi, Mix & Match, Boni)
Paketni artikli (pompaket = 2):
Ko gre za paket ali Mix&Match artikel, se odpre okno TfrmPartner. Ali je ta obrazec opredeljen za vse platforme (iOS/Android/Windows), ali se v Windows verziji odpre drugačno pogovorno okno?
Darilni boni in zamudnine:
V kodi sta vidni spremenljivki ProgramiramoZamudnino in ProgramiramoVracilo (trenutno nastavljeni na False), pa tudi zakomentirana logika za BonInput(pomcena). Ali se darilni boni in zamudnine knjižijo preko standardne metode ZapisVnarocilo ali zahtevajo ločen postopek?

odgovor : 
pompaket=0 normalen artikel
pompaket=1 klasičen paket (artikel ki ima sestavo). v RACPOZIC zapišemo vsebino paketa, prikazujemo pa paket.
pompaket=2 paket kjer izberemo sestavo (odpre se seznam artiklo paketa in označimo nekaj artiklov)

pomcena=-1 : pomeni prosti vnos cene. Preikaže se dialog za vnos cene
pomcena=-2  ali manjše kot minus 2 : poknjiži se vnešena negativna cena 

Zamudnine NE obračunavamo na Android blagajni



6. Preklapljanje med skupinami in prikazi:
Ali se ob kliku na gumb "preklopi cenik" iz liste artiklov vedno ponastavi prikaz na skupino 1 hitrih tipk, tudi če je uporabnik prej delal v drugi podskupini?

Da vedno ko se vrnemo iz liste/seznama prikažemo skupino 1

7.Lestvice točenja na ListView (Napolnilv_cenik):
Pri prikazu artiklov na Windows platformi se v naziv artikla doda niz npr. (0,1/0,125/0,5/1) ali (0,03/0,05). Ali mora tap na tak artikel v listi odpreti pogovorno okno za izbiro lestvice točenja, ali se knjiži s privzeto vrednostjo pomep = 1?

če je takšen artikel se vedno odpre lestvica za izbor polnjenja (hitra tipka, lista/seznam, ročno editiranje)

8. Karkoli od izvorne kode je v komentarju - ignore

9. Globalne spremenljivke (globals):
Katere vse globalne spremenljivke vplivajo na knjiženje (poleg CENA0DOVOLJENA, CENA2AKTIVNA, MODELCENA2, CenaPolnjenje, PLUAKTIVEN, VnosCeneZaVse) in kako jih je potrebno inicializirati v novih modulih?

PLUAKTIVEN true. iskalnik mora delovati tudi preko skritega property PLU iz cenika

VnosCeneZaVse : trenutno ni aktivno.

Vse od naštetih. Logika se na primer vidi za CENA2AKTIVNA:

Function TdmGisOrder.NacinProdajeZaNivo4(const Pnivo4id: Integer; var ppolnjenje:double; var pnacinprodaje : integer; VAR pcena:currency):Integer;
var
  INICENA:currency;
Begin
  result:=0;
  if not dmGisOrder.tblRacPozic.active then
     dmGisOrder.tblRacPozic.active:=true;
  if (Pnivo4id <> 0) and (dmGisOrder.tblCenikNivo4_id.asinteger <> Pnivo4id) then
      if not dmGisOrder.tblCenik.locate('Nivo4_id', Pnivo4id, []) then begin
        Result:=-1;
        Exit;
      end;
  Result:=dmGisOrder.tblCenikNACIN_PRODAJE.AsInteger;
  pnacinprodaje:=dmGisOrder.tblCenikNACIN_PRODAJE.AsInteger;
  ppolnjenje:=dmGisOrder.tblCenikPOLNJENJE.AsFloat;
  if MODELCENA2=1 then begin
    if CENA2AKTIVNA and (dmGisOrder.tblCenikCena2.AsCurrency<>0) then
      INICENA:=dmGisOrder.tblCenikCena2.AsCurrency
    else
      INICENA:=dmGisOrder.tblCenikCena.AsCurrency;
    if INICENA>0 then
       pcena:=INICENA;
  end;
  if MODELCENA2=2 then begin
    if CENA2AKTIVNA then
      INICENA:=dmGisOrder.tblCenikCena2.AsCurrency
    else
      INICENA:=dmGisOrder.tblCenikCena.AsCurrency;
    if INICENA>0 then
       pcena:=INICENA;
  end;

End;

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

var
  najden: Boolean;
begin
  Result:=0;
  if not dmGisOrder.tblRacPozic.active then
     dmGisOrder.tblRacPozic.active:=true;
  if not dmGisOrder.tblCenik.active then
     dmGisOrder.tblCenik.active:=true;
  if not dmGisOrder.tblCenikVrVr.active then
     dmGisOrder.tblCenikVrVr.active:=true;
  if not dmGisOrder.tblCenikVrCene.active then
     dmGisOrder.tblCenikVrCene.active:=true;

  najden := true;
  if (Pnivo4id <> 0) and (dmGisOrder.tblCenikNivo4_id.asinteger <> Pnivo4id) then
    if not dmGisOrder.tblCenik.locate('Nivo4_id', Pnivo4id, []) then
      najden := False;
  if not najden then
  begin
    if dmGisOrder.tblCenikNaziv.AsString <> Pnaziv then
      if not dmGisOrder.tblCenik.locate('Naziv', Pnaziv, []) then
        najden := False;
  end;
  if najden then
    begin
      if MODELCENA2=1 then begin
        if CENA2AKTIVNA and (dmGisOrder.tblCenikCena2.AsCurrency<>0) then
          result := dmGisOrder.tblcenikcena2.AsCurrency
        else
          result := dmGisOrder.tblcenikcena.AsCurrency;
      end;
      if MODELCENA2=2 then begin // 22.05.2023 dodal na to mesto za kontrolo cene
        if CENA2AKTIVNA  then
          result := dmGisOrder.tblcenikcena2.AsCurrency
        else
          result := dmGisOrder.tblcenikcena.AsCurrency;
      end;
      // vrni ceno za delovni  nalog
      if (pDN_ID<>'') and DNCENIK then begin
        if dmGisOrder.tblCenikVrCene.Locate('DN_ID;NIVO4_ID',VarArrayOf([pDN_ID,Pnivo4id])) then
           result:=dmGisOrder.tblCenikVrCeneCENA1.AsCurrency
      end;

      ptarifaid:=dmGisOrder.tblcenikTARIFA_ID.AsInteger;
      pizvorstrmid:=dmGisOrder.tblcenikIZVOR_STRM_ID.AsInteger;
      pprihodekstrmid:=dmGisOrder.tblCenikIZVOR_PRIHODEK_ID.AsInteger;
      pnivo1:=dmGisOrder.tblCeniknivo1_id.AsInteger;
      if (TrgovskoBlagoNivo1Id>0) and (TrgovskoBlagoNivo1Id=pnivo1) then begin// PreverjamoZalogo
      end;
      pdavekproc:=dmGisOrder.tblcenikDAVEK_PROC.AsCurrency;
      ppaket:=dmGisOrder.tblCenikPAKET.AsInteger;
      pem:=dmGisOrder.tblCenikem.AsInteger;
      if dmGisOrder.tblCenikPOLNJENJE.AsFloat<>0 then
         ppolnjenje:=dmGisOrder.tblCenikPOLNJENJE.AsFloat
      else
         ppolnjenje:=1;
    end
  else
    begin
      result:=-99;
    end;
  if (result=0) and not CENA0DOVOLJENA then // cena niè gremo ven   // Adria ?
    result:=-99;

end;