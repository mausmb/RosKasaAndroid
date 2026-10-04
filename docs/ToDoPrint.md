

Tiskanje , pavza in tiskanje dodatkov (podatki o partnerju, podatki o gostu,PrintamVoucher, PrintamNarocila, akcije)

Primarna Delphi koda je  v uPrintData.pas . Analiziraj vrstni red in preveri katere operacije name manjkajo in naredi plan izvedbe.

procedure TfrmKasaMobile.IzpisSetupIzvedba(const pRacunid:integer);
Begin
    dmGisOrder.RacGlavaFilterRacunId(dmGisorder.TekociRacunId);
    uPrintdata.PrintDH.printData.pRacunid:=dmGisOrder.tblRacGlavaRACUN_ID.AsInteger;
    uPrintdata.PrintDH.printData.pSTORNO_RAZLOG_ID:=dmGisOrder.tblRacGlavaSTORNO_RAZLOG_ID.AsInteger;
    uPrintdata.PrintDH.printData.pCrmId:=dmGisOrder.tblRacGlavaCRM_ID.AsString;
    uPrintdata.PrintDH.printData.pAkcijaId:=dmGisOrder.tblRacGlavaAKCIJA_ID.AsInteger;
    uPrintdata.PrintDH.printData.pAkcijaKuponId:=dmGisOrder.tblRacGlavaKUPON_ID.AsString;
    PrintDH.printData.pF_podpis:=dmGisOrder.tblRacGlavaF_PODPIS.AsString;
    PrintDH.printData.pF_oznaka_du:= dmGisOrder.tblRacGlavaF_OZNAKA_DU.AsString;
    PrintDH.printData.pZnesek:=dmGisOrder.tblRacGlavaZNESEK.AsCurrency;
    PrintDH.printData.pKasiral:=dmGisOrder.tblRacGlavaKASIRAL.AsInteger;
    PrintDH.printData.pdatum:=dmGisOrder.tblRacGlavaDATUM.AsDateTime;
    PrintDH.printData.pura:=dmGisOrder.tblRacGlavaURA.AsDateTime;

    PrintDH.toprint:=True;
    if (PrintLight=0) then Begin
      If dmGisOrder.RacPlaciCheckPartner(dmGisOrder.tblRacGlavaRACUN_ID.AsInteger) then
        uPrintdata.PrintDH.printData.pPartnerId:=dmGisOrder.tblRacPlaciPARTNER_ID.AsInteger;
    end
    else
      uPrintdata.PrintDH.printData.pPartnerId:=0;
     if (PrintLight=0) or (PrintLight=3) then
      dmGisOrder.RacPlaciCheckPrijavaId(dmGisOrder.tblRacGlavaRACUN_ID.AsInteger,uPrintdata.PrintDH.printData.pPrijavaId,uPrintdata.PrintDH.printData.pHisCenikAi)
    else
      PrintDH.printData.pPrijavaId:=0;
//    uPrintdata.postRacun(uPrintdata.PrintDH.printData.pRacunid);  // analizapost - zakaj je tukaj post ? lahko bi bil samo zgoraj
    crmzapis_stevec:=0; // 25.08.2025
    PrintDH.StartPrintDataRetrieval;
End;


procedure TPrintDataHandler.PrintToPrinterAsync(pPprintData: TprintDataWS);
var
  j, zacstk: integer;
begin
  try
    // NOTE: Be careful accessing Main Form variables (PrintDH, FrmKasaMobile)
    // from a thread. If they are just reading data, it is usually okay.

    // TThread.Synchronize is used to safely update UI or read unsafe properties
    TThread.Synchronize(nil, procedure
      begin
        PrintLight := 0;
      end);

    zacstk := pPprintData.pZacStKopij; // Read from local param is safer
    if zacstk < 0 then zacstk := 0;

    for j := 0 to pPprintData.pZaPrintStkopij - 1 do
    begin
      // 1. PRINT THE INVOICE
      // If IzpisRacuna draws to canvas/UI, keep Synchronize.
      // If it just writes to Bluetooth socket, remove Synchronize for better performance.
      TThread.Synchronize(nil, procedure
        begin
          FrmKasaMobile.IzpisRacuna(j, zacstk);
        end);

      // 2. PAUSE FOR CUT (Replcaement for Sleep)
      // Only pause if this is not the last copy
      if (j < pPprintData.pZaPrintStkopij - 1) then
      begin
         WaitForUserCut('Odrežite papir in pritisnite OK za naslednjo kopijo !');
      end;
    end;

    // Update flag on main thread
    TThread.Synchronize(nil, procedure
      begin
        frmKasaMobile.ZeIzpisan := False;
      end);

    // 3. PRINT VOUCHER
    if PrintamVoucher and
       (dmGisOrder.tblRacGlavaSTORNO_RACUN_ID.AsInteger = 0) and
       (dmgisorder.tblRacGlavaSTATUS.AsInteger < 2) and TiskamoRacun then
    begin
       // Optional: Pause before voucher?
       WaitForUserCut('Odrežite papir in pritisnite OK.');

       TThread.Synchronize(nil, procedure
         begin
           frmKasaMobile.PrintVoucher(
             dmGisOrder.tblRacGlavaRACUN_ID.AsString,
             FormatDateTime('dd.mm.yyyy', dmGisOrder.tblRacGlavaDATUM.AsDateTime),
             FormatDateTime('hh:nn:ss', dmGisOrder.tblRacGlavaURA.AsDateTime)
           );
         end);
    end;

    // 3. PRINT NAROČILO
    if PrintamNarocila and
       (dmGisOrder.tblRacGlavaSTORNO_RACUN_ID.AsInteger = 0) and
       (dmgisorder.tblRacGlavaSTATUS.AsInteger < 2) and TiskamoRacun then
    begin
       // Optional: Pause before voucher?
       WaitForUserCut('Odrežite papir in pritisnite OK.');

       TThread.Synchronize(nil, procedure
         begin
           frmKasaMobile.PrintNarocila;
         end);
    end;

    // 4. PRINT ACTIONS (Marketing messages etc)
    if Akcije and (pPprintData.Blok1Akcije.Count > 0) then
    begin
       if BlueToothPrint then
       begin
          // Optional: Pause before actions?
          WaitForUserCut('Odrežite papir in pritisnite OK.');

          for j := 0 to pPprintData.Blok1Akcije.Count - 1 do
          begin
             // Printing specific line
             try
               TThread.Synchronize(nil, procedure
                 begin
                   frmKasaMobile.TiskajBTvrstico(TEncoding.UTF8.GetBytes(pPprintData.Blok1Akcije.Strings[j]));
                 end);
             except
             end;
          end;
       end;
    end;

  except
    // Catch thread errors
  end;
end;

---

## Implementacija v Android (si.ros.RosKasa.print) - Zaključeno

V celoti implementirano skladno z Delphi `uPrintData.pas`, `FormKasaMobile.pas` in `PrintAll.pas`:
1. **Modeli**: `PrintDataWS`, `AkcijaTp`, `SlipEmaTp`, dopolnjen `KartprijTp` (`obratNaziv`), `PlaciloTp` (`hisCenikAi`), `RacunTp` (`akcijaId`).
2. **SOAP metode v `RosKasaSoapClient`**: `getPrijava`, `getSlipEma2`, `akcijaSetKuponiRacuna`, `akcijaGet`, `akcijaNaziv`.
3. **Kaskada pridobivanja podatkov (`PrintDataHandler.retrievePrintData`)**:
   - Pred tiskom asinhrono pridobi manjkajoče podatke o partnerju (`getPartner`), hotelskem gostu (`getPrijava` -> `HK [soba]/[gost]/[obrat]`), storno razlogu, kartičnem slipu (`getSlipEma2`) in akcijskih kuponih (`akcijaGet`/`akcijaNaziv`).
4. **Varna pavza za odrez papirja (`PrintDataHandler.waitForUserCut`)**:
   - Uporablja `CountDownLatch(1)` in prikaže modalni `AlertDialog` na glavnem UI niti za potrditev odreza papirja med posameznimi kopijami in pred vsakim dodatkom (voucher, naročilo, akcije). Če je `TISKANJEPAVZA=N`, upošteva zakasnitev `PRINTBLOKPAVZA`.
5. **Graditelji dodatnih izpisov**:
   - `RacunPrintBuilder`: posodobljen z glavo partnerja (naziv, naslov, davčna, št. naročilnice), vrstico hotelskega gosta (`HK ...`), nazivom storno razloga in POS slipom.
   - `VoucherPrintBuilder`: tisk bona/vavčerja ob `PRINTVOUCHER=D` in `NIVO4IDVOUCHER`.
   - `NarociloBlokPrintBuilder`: tisk naročila ob računu (Blok 1 šank / Blok 2 kuhinja z markerjem in lokatorjem) ob `PRINTAMNAROCILA=D`.
   - `AkcijePrintBuilder`: tisk promocijskih kuponov z nativno ESC/POS QR kodo ob `AKCIJE=D`.
6. **Enotna Bluetooth seja**:
   - `PrintDataHandler.printReceiptComplete` izvede vse izpise prek ene same Bluetooth povezave (brez prekinitev) ter po končanem tisku samodejno pošlje `insertIzpisan` in račun postavi v `STATUS = 2`.
7. **Integracija v vmesnik**:
   - Posodobljeni `PlacilaFragment`, `NarocilaFragment` in `RacuniFragment` za klic `BluetoothPrintHelper.printReceiptComplete`.

