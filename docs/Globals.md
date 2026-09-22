# Globalne sprejemljivke 

## Splošno:
1. zaporedje branja MOBINI : MOBINI0, MOBINI1, MOBINI2 (VREDNOSTI SE LAHKO DODAJO ALI SPREMENIJO)
2. tiskanje - konverzija kod za tiskanje
3. ostale algoritem sprejemljivke - se dodjo (ali pa spremenijo v lokalne po potrebi)
4. Delphi osnova za Java : zajeti je potrebno vse spremenljivke pri zagonu. Da ne bi med delom klicali "getAppConfig"


## Uvoz in refaktor iz Delphi MOBINI iz "getAppConfig":
1. v Globals.pas vsebuje vse spremenljivke ki se preberejo 3 krat

2. Mobini delphi koda:

   try
   Fmobini0 := Tmobini.Create;
   try
     if mojsetupodg.MobileSetup.MOBINI0<>'' then Begin
       Fmobini0:=ReadIni(mojsetupodg.MobileSetup.MOBINI0);
       MobIniRead(Fmobini0); //MOBINI 1 branje MOBILE_SETUP za MOBILE_ID = 0
     end;
   except
   end;
   finally
    Fmobini0.Free;
  end;

  try
   Fmobini := Tmobini.Create;
   Fmobini:=ReadIni(mojsetupodg.MobileSetup.MOBINI);
   if CROFISK AND CRONAPITNINE Then begin
      btnVkr.Text:=SNapojnice;
      btnVkr.enabled:=True;
      btnVkr.visible:=True;
   end;
   MobIniRead(Fmobini); // MOBINI 2 branje MOBILE_SETUP za MOBILE_ID
  finally
    Fmobini.Free;
  end;
{$ENDREGION}
except
end;
try
{$REGION 'napolni MOBINI2 json'}
  try
   Fmobini2:= Tmobini.Create;
   Fmobini2:=ReadIni(mojsetupodg.MobileSetup.MOBINI2);
   MobIni2Read;  // MOBINI 3 branje za MOBILE_ID
   if WebNarocila then
      btnPrenesiRacune.text:=SOsvežiRačuneWebNaročila;
   finally
    Fmobini2.Free;
  end;
{$ENDREGION}
  GumbNarocilaLevo;
except
end;
if (TIPKE_POS_ID<0) or (TIPKE_POS_ID>1000000) then Begin
   TIPKE_POS_ID:=PomTIPKE_POS_ID;

End;


## Uvoz in refaktor iz Delphi splošne spremenljivke - tiskanlnik ipd. iz "getAppConfig":
     mojsetupodg := GetAppConfigRsTp.Create;
     try
     stoparica1:=TStopWatch.StartNew;
     mojsetupodg:= frmKasaMobile.R16K.Servis.getAppConfig(MOBILE_ID,token);
     stoparica1.Stop;
     CasGetAppConfig:=stoparica1.ElapsedMilliseconds.ToString;
     NapakaZagona:=mojsetupodg.fault;
     if (mojsetupodg.fault<>'') then
         NapakaZagona:=mojsetupodg.fault;
     except
       NapakaZagona:=mojsetupodg.fault;
     end;
    if MOBILE_ID=0 then
       MOBILE_ID:= 1; // NIČ NI NA NAPRAVI
    if mojsetupodg.fault<>'' then begin
       NapakaZagona:=mojsetupodg.fault;
       RosMessage(SNapakaSetupIzWeba+mojsetupodg.fault);
       exit;
    end;
  except

  end;
{$ENDREGION}
  if  pini then begin

{$REGION 'NFC'}
  {$IF DEFINED(ANDROID)}

  if mojsetupodg.MobileSetup.NFCPRIJAVA<>null then begin
    if mojsetupodg.MobileSetup.NFCPRIJAVA='D' then Begin
      pNfc:=true;
      pnfcprijava:=True;
    end
    else Begin
       pnfcprijava:=False;
       pNfc:=false;
   end;
  end;
 {$ENDIF}
{$ENDREGION}
{$REGION 'SPLOŠNE NASTAVITVE'}
  if mojsetupodg.MobileSetup.FISKALIZACIJA<>nil then
    FISKALIZACIJA:= mojsetupodg.MobileSetup.FISKALIZACIJA.AsInteger;
  DAVCNA_ZAFURS:=mojsetupodg.MobileSetup.DAVCNA_ZAFURS;
  if mojsetupodg.MobileSetup.VNOSPOGRINJKOV<>null then begin
     if mojsetupodg.MobileSetup.VNOSPOGRINJKOV='D' then
        Pogrinjki:=true;
  end;
  if mojsetupodg.MobileSetup.VNOSPOGRINJKOV='D' then
     Pogrinjki:=true;
  if mojsetupodg.MobileSetup.HTCOLOR<>null then begin
     if mojsetupodg.MobileSetup.HTCOLOR='D' then
        BarvamTipke:=true;
  end;
  if mojsetupodg.MobileSetup.HTSTYLENAME<>null then
     AktivenStyle:=mojsetupodg.MobileSetup.HTSTYLENAME;
  if mojsetupodg.MobileSetup.F_POS_ID<>nil then
    F_POS_ID:= mojsetupodg.MobileSetup.F_POS_ID.AsInteger;
  if mojsetupodg.MobileSetup.F_POSLOVNI_PROSTOR_ID<>nil then
    F_POSLOVNI_PROSTOR_ID:= mojsetupodg.MobileSetup.F_POSLOVNI_PROSTOR_ID.AsInteger;
  NAZIV_MOBILE:= mojsetupodg.MobileSetup.NAZIV;
  if mojsetupodg.MobileSetup.TIPKE_POS_ID<>nil then
    TIPKE_POS_ID := mojsetupodg.MobileSetup.TIPKE_POS_ID.AsInteger;
  if mojsetupodg.MobileSetup.TOCILNICA_ID<>nil then
    TOCILNICA_ID := mojsetupodg.MobileSetup.TOCILNICA_ID.AsInteger;
  PRINTER_RACUNI := mojsetupodg.MobileSetup.PRINTER_RACUNI;
//  TipTiskalnika:= mojsetupodg.MobileSetup.PRINTER_RACUNI_TIP;
  if PRINTER_RACUNI.Contains('\\') and not WinSpoolPrint then begin
  end;
  nazivpodjetja := mojsetupodg.MobileSetup.NAZIVPODJETJA;
  naslovpodjetja := mojsetupodg.MobileSetup.NASLOVPODJETJA;
  ddvstevilka := mojsetupodg.MobileSetup.DDVPODJETJA;
  nazivprodajnegamesta := mojsetupodg.MobileSetup.NAZIVPRODAJNEGAMESTA;
  nazivobratPE := mojsetupodg.MobileSetup.OBRATPRODAJNEGAMESTA;
  naslovprodajnega := mojsetupodg.MobileSetup.NASLOVPRODAJNEGA;

  pPopustIzpisPozicije:=true;
  if mojsetupodg.MobileSetup.POPUSTIZPIS<>null then begin
     if mojsetupodg.MobileSetup.POPUSTIZPIS='N' then
        pPopustIzpisPozicije:=false;
  end;

  pLojalnostPopust:=false;
  if mojsetupodg.MobileSetup.POPUSTLOJALNOST<>null then begin
     if mojsetupodg.MobileSetup.POPUSTLOJALNOST='D' then
       pLojalnostPopust:=true
     else
       pLojalnostPopust:=false;
  end;
  pPopust99:=True;
  if mojsetupodg.MobileSetup.Popust99<>null then begin
     if mojsetupodg.MobileSetup.POPUST99='N' then
       pPopust99:=false;
  end;
  if not pPopust99 then
     btnPopust.Visible:=False;
  KreditnaKarticaPlacilo:=false;


  if mojsetupodg.MobileSetup.KKARTICEVPLACILIH<>null then begin
     if mojsetupodg.MobileSetup.KKARTICEVPLACILIH='D' then
       KreditnaKarticaPlacilo:=true
     else
       KreditnaKarticaPlacilo:=false;
  end;
  nazivStregelVasJe := mojsetupodg.MobileSetup.NAZIVSTREGELVASJE;
  nazivZahvala1 := mojsetupodg.MobileSetup.NAZIVZAHVALA1;
  nazivZahvala2 := mojsetupodg.MobileSetup.NAZIVZAHVALA2;
  nazivZahvala3 := mojsetupodg.MobileSetup.NAZIVZAHVALA3;
  nazivZahvala4 := mojsetupodg.MobileSetup.NAZIVZAHVALA4;
  HIS_DESTINACIJA := mojsetupodg.MobileSetup.HIS_DESTINACIJA;
  HIS_OBRAT := mojsetupodg.MobileSetup.HIS_OBRAT;
  TekociStrm := TOCILNICA_ID;
  if mojsetupodg.MobileSetup.KUHINJA_ID<>nil then
     KUHINJA_ID := mojsetupodg.MobileSetup.KUHINJA_ID.AsInteger;
{$ENDREGION}
//  PRINTER_TIP_NAZIV:= mojsetupodg.MobileSetup.PRINTER_RACUNI_TIP_NAZIV;
//  PRINTER_TIP:=mojsetupodg.MobileSetup.PRINTER_RACUNI_TIP;
{$REGION 'Tiskalnik'}
  if mojsetupodg.MobileSetup.STEVILOZNAKOV<>'' then
    PRINTER_STEVILOZNAKOV:=mojsetupodg.MobileSetup.STEVILOZNAKOV.ToInteger;
  if PRINTER_STEVILOZNAKOV=48 then // marjan 3.12.2018 ker piše preko roba
     PRINTER_STEVILOZNAKOV:=42;
  if mojsetupodg.MobileSetup.ESCALIGNCENTER<>'' then
     ESCALIGNCENTER:= PretvoriROSESC(mojsetupodg.MobileSetup.ESCALIGNCENTER);
  if mojsetupodg.MobileSetup.ESCWIDTH2XOFF <> '' then
    ESCWIDTH2XOFF := PretvoriROSESC(mojsetupodg.MobileSetup.ESCWIDTH2XOFF);
  if mojsetupodg.MobileSetup.ESCWIDTH2XON <> '' then
    ESCWIDTH2XON := PretvoriROSESC(mojsetupodg.MobileSetup.ESCWIDTH2XON);
  if mojsetupodg.MobileSetup.ESCALIGNLEFT <> '' then
    ESCALIGNLEFT := PretvoriROSESC(mojsetupodg.MobileSetup.ESCALIGNLEFT);
  if mojsetupodg.MobileSetup.ESCALIGNRIGHT <> '' then
    ESCALIGNRIGHT := PretvoriROSESC(mojsetupodg.MobileSetup.ESCALIGNRIGHT);
  if mojsetupodg.MobileSetup.ESCBOLDOFF <> '' then
    ESCBOLDOFF := PretvoriROSESC(mojsetupodg.MobileSetup.ESCBOLDOFF);
  if mojsetupodg.MobileSetup.ESCBOLDON <> '' then
    ESCBOLDON := PretvoriROSESC(mojsetupodg.MobileSetup.ESCBOLDON);
  if mojsetupodg.MobileSetup.ESCCPI16 <> '' then
    ESCCPI16 := PretvoriROSESC(mojsetupodg.MobileSetup.ESCCPI16);
  if mojsetupodg.MobileSetup.ESCCPI20 <> '' then
    ESCCPI20 := PretvoriROSESC(mojsetupodg.MobileSetup.ESCCPI20);
  if mojsetupodg.MobileSetup.escCut <> '' then
    escCut := PretvoriROSESC(mojsetupodg.MobileSetup.escCut);
  if mojsetupodg.MobileSetup.ESCEOL <> '' then
    ESCEOL := PretvoriROSESC(mojsetupodg.MobileSetup.ESCEOL);
  if mojsetupodg.MobileSetup.ESCINITPRINT <> '' then
    ESCINITPRINT := PretvoriROSESC(mojsetupodg.MobileSetup.ESCINITPRINT);
  if mojsetupodg.MobileSetup.ESCINVERSEOFF <> '' then
    ESCINVERSEOFF := PretvoriROSESC(mojsetupodg.MobileSetup.ESCINVERSEOFF);
  if mojsetupodg.MobileSetup.ESCINVERSEON <> '' then
    ESCINVERSEON := PretvoriROSESC(mojsetupodg.MobileSetup.ESCINVERSEON);
  if mojsetupodg.MobileSetup.ESCNEWLINE <> '' then
    ESCNEWLINE := PretvoriROSESC(mojsetupodg.MobileSetup.ESCNEWLINE);
  if mojsetupodg.MobileSetup.ESCRESET <> '' then
    ESCRESET := PretvoriROSESC(mojsetupodg.MobileSetup.ESCRESET);
  if mojsetupodg.MobileSetup.ESCUNDERLINEOFF <> '' then
    ESCUNDERLINEOFF := PretvoriROSESC(mojsetupodg.MobileSetup.ESCUNDERLINEOFF);
  if mojsetupodg.MobileSetup.ESCUNDERLINEON <> '' then
    ESCUNDERLINEON := PretvoriROSESC(mojsetupodg.MobileSetup.ESCUNDERLINEON);
{$ENDREGION}



