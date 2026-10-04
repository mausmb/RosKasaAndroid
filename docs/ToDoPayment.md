# Payment intent servisi

## uPayten.pas
Vasa logika je znana in v Delphi kodi. Vezani parametri, ki se preberjo iz MOBINI. :

  // PayTen
  PayTenA: Boolean;
  PayTenApin: String; // Merged type from boolean/string conflict (using String to cover both logic)
  PayTenAstorno: Boolean;
  PayTenAPlaciloId: Integer;
  ZadnjiPayTenARacunId: Integer;
  ZadnjaPayTenAPozicijaId: Integer;
  PAYTENAROSPACKAGE: STRING = 'si.ros.RosKasaLight2';
  PayTenAactivityMain: string = 'com.payten.nlb.slovenia';
  PayTenAactivities: string = 'com.payten.nlb.slovenia.activities.SplashActivity';
  

## uSixTapPayment.pas in uSixTapResponseU.pas
Vasa logika je znana in v Delphi kodi.

Zelo pomembno je obdelava LAST_TRANSACTION. Dodatna naloga : v glavnem progrmau pri zagonu se preveri logika LAST_TRANSACTION - v primeru da se aplikacije "obesijo" se mora preverit ali obstaja aktivna transkacija. če ja se poknjiži na račun. 
Pomembno je pazir da se počistijo "flag-i" ko se izvede uspešna transkacija ali LAST_TRANSACTION.

Vezani parametri :

  // SixTap / Worldline SoftPOS
  SixTap: Boolean;
  SixTapManualLastTransaction: Boolean = False;
  SixTapAutoLastTransaction: Boolean = False;
(*
Delovanje (SIXTAP_MANUAL_LAST='D'):
1. če je rezultat Notify=0 se shrani SESSION_ID in RACUN_ID. Pojavi se obvestilo "Plačila - Tap On tipka !"
2. če kliknemo na tipko "Tap On" v plačilih in je odprt račun enak shranjenem računu se izvede LAST_TRANSACTION, če obstaja shranjen SESSION in je saldo računa>0 in ni plačil
3. v primeru težav da se blagajna "zacikla" pri Worldline TapOn ==> RAČUNI GUMB TEST TISKANJA počisti shranjene vrednosti.

*)
  SixTapStatus: Boolean;
  SixTapPlaciloId: Integer;
  SIXTAPPRINT: Boolean;
  SixTapStorno: Boolean;
  SixTapStornoZadnji: Boolean = False;
  SixTapWPI_VERSION: string;
  SIXTAP_FORMAT: String;
  SIXTAP_DEBUG: Boolean;
  ZadnjiWpiSessionId: string;
  ZadnjiSixRacunId: Integer;
  ZadnjaSixPozicijaId: Integer;
  sixtapintransaction: Boolean;