#Todo 

## Splošno:
1. Izvedi Git commit in prenos na github
2. Pripravi dokumentacijo za trenutno stanje programa. Namen je tracking napredka , dokumentacija in predvsem da bova lahko nadaljevala z razvojem.
3. pri izpisu se ne upošteva RACGLAVA.FISKALIZACIJA=1 odvisno od podatka v NACPLAC.FISKALIZACIJA glej Delphi kodo. Update FISKALIZACIJA je samo za FISKALIZACIJA=1 drugače je null.



##Izpis računa :
1. Primer attached priloga (navaden račun gotovina fiskaliziran, enako s popustom, dobavnica je nefiskalni račun s podatki podjetja)
2. Delphi koda za tiskanje računa - za pregled in razumevanje kako se tiska:
procedure TFrmKasaMobile.IzpisRacuna(const pStKopijZaPrint,pZacStevecKopij:integer);
PrintAll.pas
uPrintData.pas
3. Splošna pravila pri kreiranju računa:

PrintAll.pas - function TConfigQRCode.kreirajRacunS2(Const PracunId,vo:integer):Tstringlist; 



Število Kopij računa:
- za vsako plačilno sredstvo je podano število kopij izpisa :  getNacPlac.STKOPIJ (  uPrintdata.PrintDH.printData.pZaPrintStkopij:=dmGisOrder.StKopijPlacila(dmGisOrder.tblRacGlavaRACUN_ID.AsInteger); // 18.04.2025)
Function TdmGisOrder.StKopijPlacila(const pracunid:integer):integer;
var pomkopije,maxkopije:integer;
Begin
 result:=1;
 pomkopije:=1;
 maxkopije:=0;
 if not tblPlacila.active then
    tblPlacila.active:=true;
 dmgisorder.RacPlaciFilterRacunId(dmgisorder.TekociRacunId,3);
 dmgisorder.tblracplaci.First;
 while not dmgisorder.tblracplaci.eof do begin
    if tblPlacila.Locate('PLACILO_ID',dmgisorder.tblracplaciPLACILO_ID.AsInteger,[]) then Begin
//       if tblPlacilaSTKOPIJ.AsInteger>pomkopije then 14.11.2024 krka metra
         if tblPlacilaSTKOPIJ.AsInteger>maxkopije then
           maxkopije:=tblPlacilaSTKOPIJ.AsInteger;
    End;
    dmgisorder.tblracplaci.next;
 end;
 Result:=maxkopije;
End;
- so odvisne od racglava.stkopij- Pri tiskanju izpisanega računa se mora število kopij povečat v RACGLAVA.


3. Izpis računa STATUS=2 ali 4 : 
3.1. getRacun iz seznama in preview računa na ekran (ni obvezno da se izpiše qrkoda fiskalizacije)
3.2. Možne akcije : ponoven izpis računa ali storno računa
3.3. Ponoven izpis računa in plačilo dobavnica ali hotel kredit : na izpisu se morajo natisniti podtki o podjetju sli gostu hotela

4. Možna rešitev (tako je sedaj rešeno v Delphi kodi)
4.1. kreiranja računa v 2 stringlist-a na 2 načina : 
4.1.1 brez esc kod za tiskalnik za namen preview in insertRacIzpisan
4.1.2 z esc kodami za tiskalnik

5. Dodatna tiskanja po izpisu računa, ki je odvisno od paramterov in načina plačila - zaenkrat samo v vednost. 
5.1. tiskanje SLIP-a od POS terminala
5.2. tiskanje kupona
5.3. tiskanje naročila















