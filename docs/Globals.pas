unit Globals;

interface

uses
  System.Variants, System.math, System.StrUtils, System.SyncObjs, System.Classes,
  System.character, system.SysUtils, System.DateUtils, System.Diagnostics,
  FMX.Forms, RosKasa_ceniki_wsdl, System.Net.HttpClient, FMX.Controls, FMX.StdCtrls,
  System.UITypes, FMX.Platform, FMX.Dialogs, FMX.Layouts, FMX.virtualkeyboard,
  uMobIni, System.RegularExpressions,
  {$IF DEFINED(ANDROID)}
  Androidapi.JNI.Toast, Androidapi.JNI.JavaTypes, System.Permissions, System.Types,
  {$ENDIF}
  {$IF DEFINED(MSWINDOWS)}
  winapi.shellapi, TlHelp32, winapi.windows, FMX.Toast, WinPosPrint,
  {$ENDIF}
  FMX.Types, FMX.DialogService;

type Davki = TObject;

const
  // Android Permissions
  LOCATION_PERMISSION = 'android.permission.ACCESS_FINE_LOCATION';
  BLUETOOTH_SCAN_PERMISSION = 'android.permission.BLUETOOTH_SCAN';
  BLUETOOTH_ADVERTISE_PERMISSION = 'android.permission.BLUETOOTH_ADVERTISE';
  BLUETOOTH_CONNECT_PERMISSION = 'android.permission.BLUETOOTH_CONNECT';
  fReadStorage = 'android.permission.READ_EXTERNAL_STORAGE';
  fWriteStorage = 'android.permission.WRITE_EXTERNAL_STORAGE';
  fBlueTooth = 'android.permission.BLUETOOTH';
  fBlueToothAdmin = 'android.permission.BLUETOOTH_ADMIN';

  // Strings
  sNapakaPrenosOdprtihNarocil = 'Napaka network - prenos naročil ni izveden !';
  SPrenosRacunov = 'Prenos računov';
  SKronologija = 'Kronologija';
  SLahkoDelaSKaso = 'Lahko dela s kaso';
  sNodata = 'Ni podatkov !';
  SLahkoStorniraRacun = 'Lahko stornira račun';
  SLahkoStorniraPozicijoRacuna = 'Lahko stornira pozicijo računa';
  SStorniraPoslanoNarocilo = 'Stornira poslano naročilo';
  SLahkoBrisePozicijePlacil = 'Lahko briše pozicije plačil';
  SVidiVseRacune = 'Vidi vse račune';
  SVidiVsaNarocila = 'Vidi Vsa Narocila';
  SLahkoPonovnoIzpiseRacun = 'Lahko ponovno izpiše račun';
  SvezanaKnjigaRacunov = 'Vezana knjiga računov (VKR)';
  SLahkoPozeneObracun = 'Lahko požene obračun';
  SpreprecimEditStornoPozicij = 'Preprečim urejanje pozicij pri stornaciji računa';
  SLahkoPreklopiCenik = 'Lahko preklopi cenik';
  SIzvedeKompletenFinancniPregled = 'Izvede kompleten finančni pregled';
  SImaVpogledVTrenutneZaloge = 'Ima vpogled v trenutne zaloge';
  SImaVpogledVKoncneZaloge = 'Ima vpogled v končne zaloge';
  SImaVpogledVKronologijo = 'Ima vpogled v kronologijo';
  SIzvedeKontrolniObracun = 'Izvede kontrolni obračun';
  SRezerviranoStornoRETA = 'Rezervirano (storno RETA)';
  SIzvedeFinancniPregled = 'Izvede finančni pregled';
  SKnjigovodskiStornoMinusKolicina = 'Knjigovodski storno (minus količina)';
  SRezerviranoTiskRETA = 'Rezervirano (tisk RETA)';
  SIzpisRacunaPopust = 'Izpis računa-popust';
  SPonovnoIzpiseLepRacun = 'Ponovno izpiše lep račun';
  SUporabaCRMSistema = 'Uporaba CRM sistema';
  SRocnaIzbiraKarticeDobroimetja = 'Ročna izbira kartice dobroimetja/malice';
  SDovoljenRocenVnosCMPKARTICE = 'Dovoljen ročen vnos CMP KARTICE';

var
  // General & Toggles
  NarociloStornoDo:Integer=0;   // vminutah, do kdaj lahko storniramo narocilo kontrola na RACPOZIC.CAS_ZADNJE_SPREMEMBE																														  NarociloStornoDo:Integer=0;   // vminutah, do kdaj lahko storniramo narocilo kontrola na RACPOZIC.CAS_ZADNJE_SPREMEMBE
  PomTIPKE_POS_ID:Integer=0;
  CenikLokalno:Boolean=False;
  CenikStDniObnova:Integer=3;
  TimerEnableEkran:Integer=10000;
  TimeOutConnect:Integer=10000;
  TimeOutSend:Integer=20000;
  TimeOutReceive:Integer=30000;
  UpostevamZgorajSpodaj:Boolean=false;
  BazenProdajaSKpaket:Boolean=false;
  TiskanjeInicialke:Boolean=false;

  TiskanjePavza: Boolean = False;
  ObracunBrezIzpisa: Boolean;
  crmzapis_stevec: Integer = 0;
  Stevec_race: Integer = 0;
  TEMP_RACUN_ID: Integer = 0;

  // Debug & Logging
  DEBUGL0: Boolean = True;
  DEBUGL1: Boolean = False;
  DEBUGL2: Boolean = False;
  DEBUGL3: Boolean = False;

  // Recovery & Login
  TapOnRecoverStevec: Integer = 0;
  TapOnRecoverLogout: Boolean = False;
  TestRecoveryDummy: Boolean = False;
  TapOnRecoverIntent: Boolean = False;
  TapOnRecoverIntentStari: Boolean = False;
  PINLOGIN: Boolean = True;
  HotKeyPlacilo: Boolean = False;
  Obroki: Boolean = False;
  PrintAlignLeft: Boolean = False;
  ZapiramFormo: Boolean = False;
  RecoverTapOn: Boolean = False;
  PlacilaEnabled: Boolean = True;
  StevecNetworkError: Integer;

  // Threads & Sync
  GCriticalTaskCounter: Int64 = 0;
  GLogSendSignal: TEvent = nil;
  GLogSenderThread: TThread = nil;

  // Payments & Tips
  NapitninaROS: Boolean;
  NapitninaPOS: Boolean = True;
  Napitnine: Boolean;
  NapitnineProcentKuh: Integer = 0;

  // System
  PRINTOFF: Boolean;
  KbpsLimit: Integer = 0;
  PrintLight: Integer = 0;
  WSRECONNECT: Boolean = False;
  KRONOLOGIZKLOP: Boolean;
  KronologPavza: Boolean;
  THREADT: Boolean;
  prejTHREADT: Boolean;

  // Android Network (Specifics)
  ANDROIDNET2X: Boolean = False;
  ANDROIDNETTCP: String = '8.8.8.8';
  ANDROIDNETTCPPORT: Integer = 53;

  // Tenzor
  TenzorGat: Boolean;
  TENZORLOG: Boolean;
  TenzorIP: string;
  TenzorPort: string;
  TenzorCitalnik: string;
  TenzorZamudnina: string;
  TENZORMID: STRING = 'MIF';

// Credit cards
  KKROCNO: Boolean = False;
  KKARTICA_KUPECID_ROCNO: Integer = 0;
  KREDKARTICA_KUPECID_DINERS: Integer = 0;
  KREDKARTICA_KUPECID: Integer = 0;
  KKarticaTippartnerRocno: Integer = 4;
  KREDKARTICA_KUPECID_NACPLAC:Boolean = False;


  // Hotel & Storno
  HOTELKREDIT0: Boolean = False;
  STORNOPOSVIRTUAL: Boolean = False;
  STORNOPOSOVERRIDE: Boolean = False;
  SORTPLACILA: Boolean = False;
  MODELSTORNOPOS: Integer = 0;
  METRAAKTIVNA: Boolean = True;
  CROFISK: Boolean = False;
  CRONAPITNINE: Boolean = False;
  VraciloNum: Boolean;

  // Values
  pN_ZNESEK: Currency;
  pN_PLACILO: String;

  // Payments Signature
  PlacilaPodpis: String;
  PlacilaPodpisID: Array of Integer;

  // UI & Interaction
  SKRIJGUMBIZKLOP: Boolean = False;
  LastInteractionTime: TdateTime;
  TiskamText: Boolean = False;

  // Lists
  RacizpisanStrL: TstringList;
  RacunStringList: Tstringlist;
  PomFooter: Tstringlist;

  // HIS
  TekocaHisPrijavaId: Integer;
  PomHisPrijavaTxt: String;

  // Prazniki & Settings
  Prazniki: Boolean = False;
  PraznikiObrat: Integer = 0;
  barcodenivo4: Boolean;
  PrintamSlipNaRacun: Boolean = True;
  SlipString: String;

  // Threads
  ThreadStorno: Boolean = False;
  ThreadOdprtiIzpisani: Boolean = False;
  ThreadNarocilo: Boolean = False;
  Threadizpis: Boolean = False;
  ThreadVse: Boolean = False;
  IzpisRacunaParalel: Boolean = False;

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

  // ECR
  EcrPay: Boolean;
  EcrPay262:Boolean=True;
  EcrIP: string;
  EcrPort: String;
  EcrPrint: Boolean;
  EcrStorno: Boolean;
  EcrRefund: Boolean;
  EcrZadnjePotrdilo: String;
  EcrDebug: Boolean;
  EcrResult: Boolean;
  EcrZaba: Boolean;
  ECRRRNMESTO: Integer = 34;

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

  // SixPay (Windows/Terminal)
  SixCdll: Boolean = False;
  TimDll:string = 'TimApi.dll';
  SixNapitnine: Boolean=True;
  SixCdllPath:String;
  SixCdllPathInit:string;
  SixPayStatus: Boolean = False;
  SixPayThread: Boolean;
  SixPayReset: Boolean = False;
  Six2Active: Boolean = False;
  SixZadnjiTransSeq: String;
  SixPayStorno: Boolean;
  SixPayPrint: Boolean;
  SixPay: Boolean;
  SixTerminalIdIP: string;
  SixPosId: string;
  SixUserId: Integer;
  SixResult: Boolean;

  // ICE / POS
  StornoPOSICE: Boolean = False;
  SlipPOSICE: Boolean = False;
  POSICEBREZSLIP: Boolean = False;
  POSICEPRINT: Boolean = False;
  Storno_ICE_Banka_Opcijsko: Boolean;
  ICE_Ini_File: String = 'ICE5500.ini';
  Ice_pos_id: Integer;
  ICEPOS: Boolean;
  StKopijSlip: Integer = 1;

  STORNORAZLOGVR: Boolean;
  TIPKAGOTOVINA: Boolean = True;
  PrintamStornoNarocila: Boolean = False;
  OpombeTabelaOff: Boolean = False;
  ZamenjavaMarkerMize: Boolean = False;
  ZamenjavaMarkerEdit: Boolean = False;
  VnosCeneZaVse: Boolean = True;
  VnosCeneZaVseINI: Boolean = True;
  MizeInRacuni: Boolean = False;
  DavkiFiskalMetoda: Integer = 0;
  HODNAROCILA: Boolean = False;
  HODNAROCILAHT: Boolean = False;
  TekociHod: String = '';
  HTfontAndroid: Integer = 0;
  BonNivo4Id: Integer = 1200130003;
  StDniBonVeljaven: Integer = 365;
  Popust99Proporcialno: Boolean = False;
  ProcentNivelacije: Currency = 30;
  PlaciloHK: Integer = 31;
  IzpisOpombaRacun: Boolean = True;
  Permissions: TArray<string>;
  PODATKINANAPRAVI: Boolean = False;
  ZnesekNivelacije: Currency;
  EDENAR: Boolean;
  PrintBrezTiskalnika: Boolean;
  StornoOsebaStorniral: Boolean;
  WebNarocila: Boolean;
  RacuniSoLahkoFakture: Boolean;
  RacunFaktura: Boolean;
  PlacilaFaktura: string;
  KlavzulaFaktura: string;
  KlavzulaFakturaDavek0: string;
  Davek0: Boolean;
  StDniValuta: Integer;
  PartnerFurs: Boolean;
  PETEKWEND: Boolean;
  escpredal: string;
  tippartnerKK: Integer;
  LangApp: String;
  ERRLOG: Boolean;
  Scanqrkoda: string;
  AkcijaStornoRacplaciPopravek: Boolean;
  partnerValue: Integer;
  ValuTID: Integer = 91035;
  partnerValu: Integer;
  MasterValuRacunid: Integer;
  PlaciloValu: Boolean;
  ZamudaMinute: Integer = 30;
  TiskamoRacun: Boolean = True;
  BazenCenaSerijskeKarteCenik: Boolean;
  PartnerPopustRocno: Boolean = False;
  CENA2VIKEND: Boolean = False;
  CENA2PREKLOPOFF: Boolean;
  ZamenjavaVecPlacil: Boolean = False;
  FiskalnoEnako: Boolean = True;
  BoldNazivPodjetja: Boolean = True;
  PrintNarocilaLokalno: Boolean = False;
  PRINTBLOKINNAROCILO: Boolean = False;
  NaselPRINTER_RACUNI: Boolean = False;

  // Turboni
  TBdonatorON: Boolean;
  TurBoniNivo1OK: string;
  TurBoniNivo1Dovoljeni: ArrayOfint;
  TurBoniAktivni: Boolean;
  TurBoniMinZnesek: Currency;

  // Mobini specific
  BRISIPLACILAZAWINDOWS: string;
  BRISIPLACILAZAANDROID: string;
  KOREKCIJAGUMBMIZE: Integer = 0;
  SkupineNaSobo: Boolean = False;
  ObracunDovoljen: Boolean = True;

  // Boni
  BoniVKmd5: Boolean;
  BoniIDdolzina: Integer = 4;
  BoniVKdolzina: Integer = 6;

  // Metra
  MetraStornoKontrola: Boolean;
  MetraLog: Boolean;
  METRAQRKODA: Boolean;
  METRAAREACODE: STRING;
  METRAWBCCODE: STRING;
  METRACITALEC: BOOLEAN = False;
  METRAPOS: STRING = '1';
  METRAURL: STRING = 'http://127.0.0.1:6781/';
  METRADEVICE: STRING = '550';
  METRAISSUECOUNT: STRING;
  METRAUSERDATA: STRING = 'xyz';
  METRATIMEOUT: STRING = '5000';
  METRAUSER: STRING = '2';
  METRAITEM: STRING = '# 100';
  METRAKONECDNEVA: TdateTime;
  METRAHEXTODEC: Boolean = False;
  Bazeni: Boolean;

  Akcije: Boolean;
  CENA0CENIK: Boolean;
  LESTVICAEPHT: Boolean = True;
  ObracunAndroid: Boolean = False;
  SOAPAES: Boolean = True;
  lokatorji: Boolean;
  barcodeprodaja: Boolean;
  VKR: Boolean;
  LESTVICAEP: Boolean;
  DNCENIK: Boolean;
  PLUAKTIVEN: Boolean;
  RAZLOGSTORNO: Boolean;
  IzbranRazlogStornoId: Integer;
  InkasoAnalitika: Boolean;
  LOGOUTCAS: Integer;
  TrgovskoBlagoNivo1Id: Integer;
  pReportFormat: String = 'txt';
  REPREZENTANCAPOFIRMAH: Boolean;

  // Keyboard & UI
  kbcapsmalecrke: Boolean;
  kbtop: Single;
  kbleft: Single;
  kbwidth: Single;
  kbHeight: Single;
  FormaSirina: Integer;
  FormaVisina: Integer;
  FunkcTipkeLevo: Single;
  FunkcTipkeSirina: Single = 640;
  FunkcTipkeVisina: Single;
  FunkcTipkeAlign: TAlignLayout;
  EkranLezece: Boolean;
  tipqrkode: Integer = 1;
  partnermBills: Integer;
  placiloidmBills: Integer;
  MastermBillsRacunid: Integer;
  PlaciloMbills: Boolean;
  hotkey3: Integer;
  hotkey4: Integer;
  hotkey5: Integer;
  urlzatest: string;
  EditKolicinaAdd: Boolean;
  VidiVsaNarocila: Boolean;
  NOVAVRSTICANAROCILA: Boolean;
  CENA0DOVOLJENA: Boolean;
  NIVO4IDVOUCHER: Integer;
  AutoPrintRacun: Boolean;
  rajoni: boolean;
  rajondefault: integer = 0;
  DPO_VR1: String;
  DPO_VR2: String;
  DPO_VR3: String;
  DPO_VR4: String;
  DPO_VR5: String;
  DPO_VR6: String;
  INTERVALIZPISANI: Integer;
  FORCETABLETSCREEN: Boolean;
  WINDOWSDOUBLECLICK: Boolean;
  MIZESDOUBLECLICK: Boolean;
  MAXIZPISOVRACUNA: Integer = 1;
  CENA2AKTIVNA: Boolean;
  MODELCENA2: Integer = 1;
  LahkoPreklopiCenik: Boolean;
  OVERRIDESTORNOPOSLANONAROCILO: Boolean;
  BREZVIRTUALKEYBOARD: Boolean;
  EKRAN1280: Boolean;

  {$IF DEFINED(MSWINDOWS)}
  FService: IFMXVirtualKeyboardService;
  {$ENDIF}

  ModelWinKeyboard: Integer = 2;
  PlacilaNizPogoj: String;
  PonovnoIzpiseRacun: Boolean;
  LahkoStornira: Boolean;
  PreprecimEditStornoPozicij: Boolean;
  pomformaTop, pomFormaLeft: Integer;
  IzpisaniVidniDo: Integer;
  ObracunNovi: Boolean;
  NfcPinPrijava: Integer = 1;
  Domaca_Valuta_ID: Integer = 978;
  NoviRacunIzStorno: Boolean;

  CRMaktiven: Boolean;
  CRM_VRSTASTORITEV: Integer = 100;
  CRM_BAREA: Integer = 2;
  BlueToothPrint, WinSpoolPrint: Boolean;
  WinSubDir: String;
  AndroidSubDir: String;
  OdpriVseRacune: Boolean = True;
  InternetPovezava: Boolean;
  HISOBRATI: String;
  CenaPolnjenje: String = 'D';
  AktivenStyleBook: TStyleBook;
  PRINTBLOKPAVZA: Integer;
  ws: KasaSoap;
  token: string;
  tempFursAi: string;
  pPrinterBTOptiPos: string = 'InnerPrinter';
  NiStornoIzpisanegaNarocila: Boolean;
  tempf1, tempf2, tempf3, tempf4, tempf6, tempbazeni: string;
  tempf5: String = 'Kronologija.json';
  pCHECKPINPOTEKEL: Boolean;
  globalnfc: Boolean;
  PrvaPinPrijava: Boolean = False;
  semvzagonu, PrvicJavim: boolean;
  pnfcprijava: Boolean;
  pHIDprijava: Boolean;
  pHidPrijavaIni: Boolean;
  pHIDprijavaIniFS: Boolean;
  NFCID: string;
  NFCIDHEX: string;
  HIDID: string;
  HIDIDHEX: string;
  PrintamSLIP: Boolean;
  PrintamNarocila: Boolean;
  PrintamNarocilaNikamor: Boolean = False;
  NePrintamNarT1: Boolean;
  NePrintamNarT2: Boolean;
  NePrintamNarT3: Boolean;
  NePrintamNarT4: Boolean;
  PrintamVoucher: Boolean;
  pNFC: Boolean;
  pLogout: Boolean = True;
  pOdjavaDialog: Boolean;
  pLogoutPoIzpisu: Boolean;
  pLogoutPoNarocilu: Boolean;
  pLojalnostPopust: Boolean;
  pNacPlacMakro: Boolean;
  pPopust99: Boolean;
  pPopustIzpisPozicije: Boolean;
  PlaciloGotovina: Boolean;
  mizestevilke: Boolean;
  mizeprefix: string;
  KreditnaKarticaPlacilo: Boolean;
  stkopijaracuna: Integer;
  Pogrinjki: Boolean;
  MaxPopOseba: Integer;
  stpogrinjkov: Integer;
  vlogaosebe: Integer = 4;
  AktivenStyle: string = 'Style1';
  BarvamTipke: Boolean;
  ZOILOKALNO: Boolean;
  StatusRacunov: Integer = 1;
  DatumPrazenString: String = '30. 12. 1899';
  Certifikat: String = '10025421-1.pfx';
  CERTIFIKATGESLO: String;
  ModelOrderman: Boolean;
  LogirajAktivnost: Boolean;
  FISKALIZACIJA: Integer;
  f_stevilka_racuna_init: integer;
  F_POS_ID: Integer;
  F_POSLOVNI_PROSTOR_ID: Integer;
  MOBILE_ID: Integer = 1;
  ESCprinterLokalno: Boolean;
  TipTiskalnika: Integer;
  defUrl: string;
  NAZIV_MOBILE: STRING;
  TIPKE_POS_ID: Integer = 1;
  TOCILNICA_ID: Integer;
  KUHINJA_ID: Integer;
  DAVCNA_ZAFURS: STRING = '10025421';
  TEMP_MAX_RACUN_ID: Integer;
  PRINTER_RACUNI: STRING;
  PRINTER_TIP_NAZIV: string;
  PRINTER_TIP: integer;
  PRINTER_STEVILOZNAKOV: INTEGER = 32;
  snazivglaveracuna: STRING = 'Naziv        kol      cena vred.';
  snazivcrtenaracunu: string;
  snazivdavcnarekapitulacija: String = 'Stopnja  osnova   DDV     Znesek';
  PRINTER_NAROCILA: STRING;
  PRINTER_KUHINJA: STRING;
  HIS_DESTINACIJA: INTEGER;
  HIS_OBRAT: INTEGER;
  pin: string;
  TekocaOseba: Integer = 9999;
  TekocaOsebaPrijava: Integer = 0;
  TekocaOsebaNaziv: String;
  TekociStrm: Integer;
  PrijavaLokalno: Boolean;
  P_print_koda: string;
  nazivpodjetja: string = 'ROS d.o.o.,';
  naslovpodjetja: string = 'Mlinska 32, 2000 Maribor';
  ddvstevilka: string = 'IDDDV : 323550';
  nazivprodajnegamesta: string = 'Restavracija ROS ';
  nazivobratPE: string;
  naslovprodajnega: string;
  nazivStregelVasJe: String = 'Stregel/a vas je :';
  nazivIzstavil: String = 'Izstavil: ';
  nazivZahvala1: String = 'Hvala za vaš obisk ';
  nazivZahvala2: String = ' ';
  nazivZahvala3: String = ' ';
  nazivZahvala4: String = ' ';

  // ESC Codes
  ESCALIGNCENTER: string = chr(27) + chr(97) + chr(49);
  ESCALIGNLEFT: string = chr(27) + chr(97) + chr(48);
  ESCALIGNRIGHT: string = chr(27) + chr(97) + chr(50);
  ESCBOLDOFF: string = chr(27) + chr(33) + chr(0);
  ESCBOLDON: string = chr(27) + chr(33) + chr(8);
  ESCCPI16: string = chr(27) + chr(77) + chr(48);
  ESCCPI20: string = chr(27) + chr(77) + chr(49);
  ESCCUT: string = chr(29) + chr(86) + chr(65);
  ESCEOL: string = chr(13) + chr(10);
  ESCINITPRINT: string = chr(27) + chr(100) + chr(1) + chr(27) + chr(33) + chr(0);
  ESCINVERSEOFF: string = chr(29) + chr(66) + chr(0);
  ESCINVERSEON: string = chr(29) + chr(66) + chr(1);
  ESCNEWLINE: string = chr(10);
  ESCRESET: string = chr(27) + chr(64);
  ESCUNDERLINEOFF: string = chr(27) + chr(45) + chr(0);
  ESCUNDERLINEON: string = chr(27) + chr(45) + chr(1);
  ESCWIDTH2XOFF: string = chr(27) + chr(33) + chr(0);
  ESCWIDTH2XON: string = chr(27) + chr(33) + chr(16);
  ESCtestOn: string = chr(27) + chr(33) + chr(16);
  ESCtestOff: string = chr(27) + chr(33) + chr(0);

// Procedures & Functions
procedure CheckWS;
procedure SetGridPanelItemsEnabledStateByName(AOwner: TComponent; AGridPanelLayoutName: string; AEnableState: Boolean; AExcludedControlName: string = '');
procedure LogD(const ptag: string);
Function PreveriMarker(const pvstop:string):String;
Function ReturnColor(id:integer): TAlphaColor ;
function RequestWriteStoragePermission: Boolean;
Function SplitRef(const pText:STRING):String;
function HexToDec(hex: string): string;
function DelimiterStringVsebujeStevilo(const PList: string; PNumber: integer): boolean;
Function PreveriEmail(const pemail:string):Boolean;
function RemoveWhitespace(const S: string):string;
function RemoveCharsInSet(const S: string; const aSet: TSysCharset):string;
Function StringVnizInteger(const pString:string): ArrayOfint;
Function MetraNapaka(CONST pError:String): String;
Function RoundRos(CONST Znesek: Double; stdec:integer=2): Double;
Function PreveriAlpha(const pvstop:string):String;
function CheckPovezava:Boolean;
Procedure RosMessageLabel(const pLabel:TLabel;const pSporocilo:string; const pFontColor:Integer=1);
Procedure RosMessage(const pSporocilo:string;aParent : TFMXObject=Nil;pDelay:Single=5);
function IsNumber(const vhod:string): boolean;
function MStrToFloat(const S: string): Extended;
function PretvoriROSESC(vhodnistring:string):string;
function IzracunProcentaIzZneska(Osnova,Popust:Currency):Currency;
function RetrogradniIzracunProcenta(Osnova,Procent:Currency):Currency;
function EscKodaPrint(const pkoda:string):string;
function GetParentForm(O: TFmxObject): TForm;
function OccurrencesOfChar(const ContentString: string; const CharToCount: char): integer;
Function IntegerVnizu(const ppogoj:string;const pInput:integer):Boolean;
Function GetLangDevice:string;
function DaysToNow:Integer;
function ReplaceAll(const Subject: String; const OldPatterns, NewPatterns: array of String; IgnoreCase: Boolean): String;
Procedure MobIniRead(pini:Tmobini);
Function dolociformo:TFMXObject;
function KillTask(ExeFileName: string): Integer;
function ProcessRunning (sExeName: String) : Boolean;
Procedure PreveriZagonExe;

{$IF DEFINED(MSWINDOWS)}
procedure GetBuildInfo(var V1, V2, V3, V4: word);
function GetBuildInfoAsString: string;
Procedure KeyboardWinHideShow(pobjekt:TFMXObject);
Procedure KeyboardWinHide;
Procedure KeyboardWinShow(pobjekt:TFMXObject);
{$ENDIF}

{$IFDEF WIN32 }
function ExecuteFile(const filename, Params, DefaultDir: string;ShowCmd: integer): THandle;
{$ENDIF}

implementation

uses Network, FormShowMessage, FormKasaMobile;

Function GetLangDevice:string;
var
  LocServ: IFMXLocaleService;
begin
  if TPlatformServices.Current.SupportsPlatformService(IFMXLocaleService, IInterface(LocServ)) then
    Result := LocServ.GetCurrentLangID;
end;

function FirstDayOfMonth(Date: TDateTime): TDateTime;
var
  Year, Month, Day: Word;
begin
  DecodeDate(Date, Year, Month, Day);
  Result := EncodeDate(Year, Month, 1);
end;

function DaysToNow:Integer;
Begin
  try
    Result:=abs(DaysBetween(date,FirstDayOfMonth(date)));
  except
    result:=1;
  end;
end;

procedure CheckWS;
Begin
  If ws=nil then
     ws:=GetKasaSoap;
  {$IF DEFINED(ANDROID)}
  If WSRECONNECT Then Begin
    try
     ws:=nil;
     ws:=GetKasaSoap;
    except
    end;
  end;
  {$ENDIF}
End;

function CheckPovezava:Boolean;
Begin
  result:=false;
  {$IF DEFINED(IOS) or DEFINED(ANDROID)}
  result:=IsConnected;
  {$ENDIF}

  {$IF defined(MSWINDOWS)}
  try
    result:=IsWinConnected;
  except
  end;
  {$ENDIF}

  if ZOILOKALNO and not InternetPovezava then
     Result:=False;

  if not result then Begin
     {$IF DEFINED(IOS) or DEFINED(ANDROID)}
     frmKasaMobile.TimerReconnect.Enabled:=True;
     if StevecNetworkError > 10 then begin
       RosMessage('Network error !');
       StevecNetworkError := 0;
     end else
       Inc(StevecNetworkError);
     {$ELSE}
     // On Windows/Others we might warn immediately or handle differently
     {$ENDIF}
  End
  else begin
     {$IF DEFINED(IOS) or DEFINED(ANDROID)}
     frmKasaMobile.TimerReconnect.Enabled:=False;
     frmKasaMobile.EnableEkran;
     {$ENDIF}
  end;
end;

Function IntegerVnizu(const ppogoj:string;const pInput:integer):Boolean;
var
  i,j,kk:integer;
  pomi : array of integer;
  pom:string;
Begin
  Result:=false;
  try
  SetLength(pomi,ppogoj.CountChar(',')+1);
  j:=low(pomi);
  for i := Low(ppogoj) to High(ppogoj) do begin
    if TryStrToInt(ppogoj[i],kk) then
       pom:=pom+ppogoj[i]
    else if ppogoj[i]=',' then Begin
       pomi[j]:=pom.ToInteger;
       j:=j+1;
       pom:='';
     end;
  end;
 for i := Low(pomi) to High(pomi) do
    if pomi[i]=pInput then
       Result:=true;
 except
   Result:=false;
 end;
End;

Procedure RosMessageLabel(const pLabel:TLabel;const pSporocilo:string; const pFontColor:Integer=1);
Begin
  pLabel.text:=pSporocilo;
  case pFontColor of
   1: pLabel.TextSettings.FontColor:=TAlphaColorRec.white;
   2: pLabel.TextSettings.FontColor:=TAlphaColorRec.red;
  end;
End;

function GetParentForm2(Control: TFmxObject): TCommonCustomForm;
begin
  if (Control.Root <> nil) and
      (Control.Root.GetObject is TCommonCustomForm) then
    Result := TCommonCustomForm(Control.Root.GetObject)
  else
    Result := nil;
end;

Procedure RosMessage(const pSporocilo:string;aParent : TFMXObject=Nil;pDelay:Single=5);
{$IF defined(MSWINDOWS)}
var
  FMXToast1:TFMXToast;
{$ENDIF}
Begin
{$IF defined(MSWINDOWS)}
  try
    if aParent=nil then Begin
        aparent:=frmKasaMobile;
    end;
    FMXToast1:=TFMXToast.Create(aParent);
    FMXToast1.Delay:=pDelay;
    FMXToast1.ToastMessage:=pSporocilo;
    FMXToast1.Show(aParent);
  except
  end;
{$ENDIF}
{$IF DEFINED(IOS) or DEFINED(ANDROID)}
     Toast(pSporocilo,LongToast);
{$ENDIF}
End;

function IzracunProcentaIzZneska(Osnova,Popust:Currency):Currency;
begin
  try
    Result := (Popust/Osnova)*100;
  except
    Result:=0;
  end;
end;

function RetrogradniIzracunProcenta(Osnova,Procent:Currency):Currency;
begin
  try
    Result := Osnova-Osnova*100/(Procent+100);
  except
    Result:=0;
  end;
end;

function Beseda_V_Stevila(Beseda: RawByteString): variant;
var
  I, J, St: Integer;
  R: variant;
begin
  J := 0;
  st:=0;
  R := VarArrayCreate([0, J], varInteger);
  if Beseda = '' then
  begin
     R[J] := 0;
     Result := R;
     Exit;
  end;

  for I := low(Beseda) to high(Beseda) do
  begin
     if Beseda[I] = '-' then
     begin
       R[J] := St;
       St := 0;
       Inc(J);
       VarArrayRedim(R, J);
     end
     else
     begin
       St := St * 10 + Ord(Beseda[I]) - 48;
     end;
  end;
  R[J] := St;
  Result := R;
end;

function IsNumber(const vhod:string): boolean;
var
  pom : double;
begin
  try
   result:=TryStrToFloat(vhod,pom);
  except
   result:=false;
  end;
end;

function MStrToFloat(const S: string): Extended;
const
  Komma: TFormatSettings = (DecimalSeparator: ',');
  Dot: TFormatSettings = (DecimalSeparator: '.');
begin
  if not TryStrToFloat(S, Result, Komma) then
    Result := StrToFloat(S, Dot);
end;

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

function EscKodaPrint(const pkoda:string):string;
var
  pomvar1:variant;
  stevec:integer;
begin
  pomvar1:=Beseda_V_Stevila(pkoda);
  for Stevec := VarArrayLowBound(pomvar1, 1) to VarArrayHighBound(pomvar1, 1) do
    Result :=Result+chr(Round(pomvar1[Stevec]));
end;

function GetParentForm(O: TFmxObject): TForm;
var
  P: TFmxObject;
begin
  Result := nil;
  P := O.Parent;
  while (P <> nil) and (not(P is TForm)) do
    P := P.Parent;
  if P <> nil then
    Result := P as TForm;
end;

function OccurrencesOfChar(const ContentString: string;
  const CharToCount: char): integer;
var
  C: Char;
begin
  result := 0;
  for C in ContentString do
    if C = CharToCount then
      Inc(result);
end;

{$IFDEF WIN32 }
function ExecuteFile(const filename, Params, DefaultDir: string;
      ShowCmd: integer): THandle;
begin
  result := ShellExecute(0, 'Open', PChar(filename), PChar(Params),
    PChar(DefaultDir), ShowCmd);
end;
{$ENDIF}

Procedure KeyboardWinHide;
Begin
{$IF DEFINED(MSWINDOWS)}
  if ModelWinKeyboard=3 then
    KillTask('TouchKey.exe')
  else begin
    TPlatformServices.Current.SupportsPlatformService(IFMXVirtualKeyboardService, IInterface(FService));
    if (FService <> nil) then
    begin
       if (TVirtualKeyboardState.Visible  in FService.VirtualKeyboardState)  then
          FService.HideVirtualKeyboard
    end;
  end;
{$ENDIF}
end;

{$IF DEFINED(MSWINDOWS)}
procedure GetBuildInfo(var V1, V2, V3, V4: word);
var
  VerInfoSize, VerValueSize, Dummy: DWORD;
  VerInfo: Pointer;
  VerValue: PVSFixedFileInfo;
begin
  VerInfoSize := GetFileVersionInfoSize(PChar(ParamStr(0)), Dummy);
  if VerInfoSize > 0 then
  begin
      GetMem(VerInfo, VerInfoSize);
      try
        if GetFileVersionInfo(PChar(ParamStr(0)), 0, VerInfoSize, VerInfo) then
        begin
          VerQueryValue(VerInfo, '\', Pointer(VerValue), VerValueSize);
          with VerValue^ do
          begin
            V1 := dwFileVersionMS shr 16;
            V2 := dwFileVersionMS and $FFFF;
            V3 := dwFileVersionLS shr 16;
            V4 := dwFileVersionLS and $FFFF;
          end;
        end;
      finally
        FreeMem(VerInfo, VerInfoSize);
      end;
  end;
end;

function GetBuildInfoAsString: string;
var
  V1, V2, V3, V4: word;
begin
  GetBuildInfo(V1, V2, V3, V4);
  Result := IntToStr(V1) + '.' + IntToStr(V2) + '.' +
    IntToStr(V3) + '.' + IntToStr(V4);
end;

Procedure KeyboardWinHideShow(pobjekt:TFMXObject);
Begin
 if (ModelWinKeyboard=0) then exit;
 if not Assigned(FService) then
    TPlatformServices.Current.SupportsPlatformService(IFMXVirtualKeyboardService, IInterface(FService));
  if (FService <> nil) then
    begin
     if (TVirtualKeyboardState.Visible  in FService.VirtualKeyboardState)  then
        FService.HideVirtualKeyboard
     else
       KeyboardWinShow(pobjekt);
    end;
end;

Procedure KeyboardWinShow(pobjekt:TFMXObject);
Begin
 if (ModelWinKeyboard=0) then exit;
 if frmKasaMobile<>nil then
   frmKasaMobile.ResetLogout;
 if not Assigned(FService) then
    TPlatformServices.Current.SupportsPlatformService(IFMXVirtualKeyboardService, IInterface(FService));
 if (FService <> nil) then
   begin
    if  not (TVirtualKeyboardState.Visible  in FService.VirtualKeyboardState)  then Begin
    {$IFDEF WIN32 }
      if ModelWinKeyboard=3 then
         ExecuteFile('TouchKey.exe',kbtop.tostring+' '+kbleft.tostring+' '+kbwidth.ToString+' '+kbHeight.ToString,'',0);
      if ModelWinKeyboard=2 then begin
        if System.SysUtils.TOSVersion.Architecture=arIntelX86 then
          ExecuteFile('OSKLauncher.exe','','',0);
        if System.SysUtils.TOSVersion.Architecture=arIntelX64 then
          ExecuteFile('OSKLauncher64.exe','','',0);
      end;
    {$ENDIF}
      if ModelWinKeyboard=1 then
         FService.ShowVirtualKeyboard(pobjekt);
    end;
   end;
end;
{$ENDIF}

function ReplaceAll(const Subject: String;
  const OldPatterns, NewPatterns: array of String;
  IgnoreCase: Boolean): String;
var
  ReplaceFlags: TReplaceFlags;
  NewPattern: String;
  I: Integer;
begin
  ReplaceFlags := [rfReplaceAll];
  if IgnoreCase then
    Include(ReplaceFlags, rfIgnoreCase);
  Result := Subject;
  for I := Low(OldPatterns) to High(OldPatterns) do
  begin
    if I <= High(NewPatterns) then
      NewPattern := NewPatterns[I]
    else
      NewPattern := '';
    Result := StringReplace(Result, OldPatterns[I], NewPattern, ReplaceFlags);
  end;
end;


Function dolociformo:TFMXObject;
Begin
  {$IF DEFINED(MSWINDOWS)}
  Result:=application.MainForm;
  {$ELSE}
  Result:=nil;
  {$ENDIF}
End;

function KillTask(ExeFileName: string): Integer;
{$IFDEF WIN32 }
const
  PROCESS_TERMINATE = $0001;
var
  ContinueLoop: Boolean;
  FSnapshotHandle: THandle;
  FProcessEntry32: TProcessEntry32;
{$ENDIF}
begin
  Result := 0;
{$IFDEF WIN32 }
  FSnapshotHandle := CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
  FProcessEntry32.dwSize := SizeOf(FProcessEntry32);
  ContinueLoop := Process32First(FSnapshotHandle, FProcessEntry32);

  while Integer(ContinueLoop) <> 0 do
  begin
    if ((UpperCase(ExtractFileName(FProcessEntry32.szExeFile)) =
      UpperCase(ExeFileName)) or (UpperCase(FProcessEntry32.szExeFile) =
      UpperCase(ExeFileName))) then
      Result := Integer(TerminateProcess(
                        OpenProcess(PROCESS_TERMINATE,
                                    Boolean(0),
                                    FProcessEntry32.th32ProcessID),
                                    0));
     ContinueLoop := Process32Next(FSnapshotHandle, FProcessEntry32);
  end;
  CloseHandle(FSnapshotHandle);
{$ENDIF}
end;

function ProcessRunning (sExeName: String) : Boolean;
{$IFDEF WIN32 }
var
    hSnapShot : THandle;
    ProcessEntry32 : TProcessEntry32;
    stexe:integer;
{$ENDIF}
begin
    Result := false;
{$IFDEF WIN32 }
    stexe:=0;
    hSnapShot := CreateToolhelp32Snapshot (TH32CS_SNAPPROCESS, 0);
    Win32Check (hSnapShot <> INVALID_HANDLE_VALUE);

    sExeName := LowerCase (sExeName);

    FillChar (ProcessEntry32, SizeOf (TProcessEntry32), #0);
    ProcessEntry32.dwSize := SizeOf (TProcessEntry32);

    if (Process32First (hSnapShot, ProcessEntry32)) then
        repeat
            if (Pos (sExeName,
                     LowerCase (ProcessEntry32.szExeFile)) = 1) then
            begin
                stexe:=stexe+1;
            end;
        until (Process32Next (hSnapShot, ProcessEntry32) = false);
    if stexe>1 then
       result:=true;
    CloseHandle (hSnapShot);
{$ENDIF}
end;

Procedure PreveriZagonExe;
var
  MutexHandle: THandle;
begin
{$IF DEFINED(MSWINDOWS)}
    MutexHandle := CreateMutex(nil, False, 'RosKasaMobile.exe');
    if WaitForSingleObject(MutexHandle, 0) = Wait_TimeOut then
    begin
      Application.Terminate;
    end;
{$ENDIF}
end;

Function PreveriAlpha(const pvstop:string):String;
var i:integer;
begin
 Result:='';
 for i := low(pvstop) to high(pvstop) do begin
     if pvstop[i].IsLetter or pvstop[i].IsWhiteSpace or pvstop[i].IsNumber
       or (pvstop[i]='.')
       or (pvstop[i]='!')
       or (pvstop[i]='?')
       or (pvstop[i]='+')
       or (pvstop[i]=',')
       or (pvstop[i]='-')
       or (pvstop[i]='/')
       or (pvstop[i]='''')
         then
       Result:=Result+pvstop[i];
 end;
end;

Function RoundRos(CONST Znesek: Double; stdec:integer=2): Double;
Begin
  Result:=SimpleRoundto(Znesek,-2);
End;

Function MetraNapaka(CONST pError:String): String;
Begin
  Result:='';
  if pError='5' then
     Result:='Napaka ACESS DENIED - PC nima Metra SW nastavitve ';
  if pError='0' then
     Result:='OK';
  if pError='21' then
     Result:='Napaka ERROR_NOT_READY - The device (only WBI) is not ready ';
  if pError='85' then
     Result:='Napaka ERROR_ALREADY_ASSIGNED – The media already has a ticket programmed ';
  if pError='87' then
     Result:='Napaka ERROR_INVALID_PARAMETER – If parameters are not in the right format (e.g. invalid date format), or if some are missing ';
End;

Function StringVnizInteger(const pString:string):ArrayOfint;
var i,j:integer;
pom:string;
Begin
  i:=0;
  j:=0;
  SetLength(Result,pString.CountChar(',')+1);
  pom:='';
  for i := Low(pString) to High(pString) do begin
    if pString[i].isnumber then
       pom:=pom+pString[i]
    else if pString[i]=',' then Begin
       Result[j]:=pom.tointeger;
       j:=j+1;
       pom:='';
     end;
  end;
  if pom<>'' then begin
    Result[j]:=pom.tointeger;
  end;
End;

function RemoveCharsInSet(const S: string; const aSet: TSysCharset):string;
var
  I: Integer;
begin
  Result := S;
  for I := Length(S) downto 1 do
    if CharInSet(S[I],aSet) then
      Delete(Result, I, 1);
end;

function RemoveWhitespace(const S: string):string;
const
  Whitespace = [#0..' '];
begin
  Result := RemoveCharsInSet(S, Whitespace);
end;

Function PreveriEmail(const pemail:string):Boolean;
const
  EMAIL_REGEX = '^((?>[a-zA-Z\d!#$%&''*+\-/=?^_`{|}~]+\x20*|"((?=[\x01-\x7f])'
             +'[^"\\]|\\[\x01-\x7f])*"\x20*)*(?<angle><))?((?!\.)'
             +'(?>\.?[a-zA-Z\d!#$%&''*+\-/=?^_`{|}~]+)+|"((?=[\x01-\x7f])'
             +'[^"\\]|\\[\x01-\x7f])*")@(((?!-)[a-zA-Z\d\-]+(?<!-)\.)+[a-zA-Z]'
             +'{2,}|\[(((?(?<!\[)\.)(25[0-5]|2[0-4]\d|[01]?\d?\d))'
             +'{4}|[a-zA-Z\d\-]*[a-zA-Z\d]:((?=[\x01-\x7f])[^\\\[\]]|\\'
             +'[\x01-\x7f])+)\])(?(angle)>)$';
var
 i,j,k:Integer;
 pom,poms:string;
 pomarr: array of array of string;
Begin
  Result:=False;
  SetLength(pomarr,pemail.CountChar(';')+1,50);
  j:=low(pomarr);
  pom:='';
  poms:=pemail;
  for k := Low(pomarr) to High(pomarr) do Begin
  for i := Low(poms) to High(poms) do begin
    if poms[i]=';' then Begin
       pomarr[k,0]:=pom;
       j:=j+1;
       pom:=Default(String);
       poms:=poms.Remove(0,i);
       break
     end
     else begin
       pom:=pom+poms[i];
     end;
  end;
  if pomarr[k,0]='' then
     pomarr[k,0]:=pom;
  End;
 for k := Low(pomarr) to High(pomarr) do Begin
   Result := Tregex.IsMatch(pomarr[k,0], EMAIL_REGEX);
   if not result then
      break;
 End;
End;

function DelimiterStringVsebujeStevilo(const PList: string; PNumber: integer): boolean;
begin
  Result := MatchStr(PNumber.ToString, PList.Split([';', ',']));
end;

function HexToDec(hex: string): string;
var
  i: Integer;
  dec: Int64;
begin
  dec := 0;
  for i := 1 to Length(hex) do
  begin
    dec := dec * 16 + StrToInt('$' + hex[i]);
  end;
  Result := IntToStr(dec);
end;

Function SplitRef(const pText:STRING):String;
Begin
  Result:='';
  If pText<>'' then
    Result:=' IZVOR: '+pText;
End;

Function ReturnColor(id:integer): TAlphaColor ;
  Begin
{$REGION 'napolni barve'}
result:=0;
try
  Case id of
    1: result:= TAlphaColorRec.Peru;
    2: result:= TAlphaColorRec.Chocolate;
    3: result:= TAlphaColorRec.Darkseagreen;
    4: result:= TAlphaColorRec.Seagreen;
    5: result:= TAlphaColorRec.MediumSeagreen;
    6: result:= TAlphaColorRec.DkGray;
    7: result:= TAlphaColorRec.MedGray;
    8: result:= TAlphaColorRec.LtGray;
    9: result:= TAlphaColorRec.Slateblue;
    10: result:= TAlphaColorRec.Mediumslateblue;
    11: result:= TAlphaColorRec.Royalblue;
    12: result:= TAlphaColorRec.Lightsalmon;
    13: result:= TAlphaColorRec.Darkkhaki;
    14: result:= TAlphaColorRec.Darkcyan;
    15: result:= TAlphaColorRec.Crimson;
    16: result:= TAlphaColorRec.Mediumslateblue;
    17: result:= TAlphaColorRec.Mediumpurple;
    18: result:= TAlphaColorRec.Crimson;
    19: result:= TAlphaColorRec.Blueviolet;
    20: result:= TAlphaColorRec.Hotpink;
    21: result:= TAlphaColorRec.red;
    22: result:= TAlphaColorRec.Salmon;
    23: result:= TAlphaColorRec.whitesmoke;
    24: result:= TAlphaColorRec.Lightsteelblue;
    25: result:= TAlphaColorRec.OrangeRed;
    26: result:= TAlphaColorRec.Aquamarine;
    27: result:= TAlphaColorRec.Green;
    28: result:= TAlphaColorRec.yellow;
    29: result:= TAlphaColorRec.Darkred;
    30: result:= TAlphaColorRec.White;
    31: result:= TAlphaColorRec.Silver;
    32: result:= TAlphaColorRec.Mediumaquamarine;
    33: result:= TAlphaColorRec.gold;
    34: result:= TAlphaColorRec.Cornflowerblue;
    35: result:= TAlphaColorRec.LightGrey;
    36: result:= TAlphaColorRec.LightSkyBlue;
    37: result:= TAlphaColorRec.LightGrey;
    else result:=TAlphaColorRec.Silver;
{$ENDREGION}
  end;
except
end;
end;

procedure SetGridPanelItemsEnabledStateByName(
  AOwner: TComponent;
  AGridPanelLayoutName: string;
  AEnableState: Boolean;
  AExcludedControlName: string = ''
);
var
  i: Integer;
  GridPanelLayout: TGridPanelLayout;
  FoundComponent: TComponent;
  ChildControl: TControl;
  IsExcluded: Boolean;
begin
  FoundComponent := AOwner.FindComponent(AGridPanelLayoutName);

  if not Assigned(FoundComponent) then
    Exit;

  if not (FoundComponent is TGridPanelLayout) then
     Exit;

  GridPanelLayout := FoundComponent as TGridPanelLayout;

  for i := 0 to GridPanelLayout.ControlCollection.Count- 1 do
  begin
    if GridPanelLayout.Controls[i] is TControl then
    begin
      ChildControl := TControl(GridPanelLayout.Controls[i]);
      IsExcluded := (AExcludedControlName <> '') and (CompareText(ChildControl.Name, AExcludedControlName) = 0);
      if not IsExcluded then
      begin
        ChildControl.Enabled := AEnableState;
      end
    end;
  end;
end;

procedure LogD(const ptag: string);
Begin
{$IFDEF DEBUG}
   {$IF DEFINED(ANDROID)}
   Log.d(ptag);
   {$ENDIF}
{$ENDIF}
End;

function RequestWriteStoragePermission: Boolean;
var
  LocalResult:Boolean;
{$IF DEFINED(ANDROID)}
  GrantResults: TClassicPermissionStatusDynArray;
  PermissionGranted: Boolean;
{$ENDIF}
begin
  Result := True;
{$IF DEFINED(ANDROID)}
try
  if TOSVersion.Check(13) then
  begin
    Result := True;
    exit;
  end;
  Permissions := [fWriteStorage];
  PermissionGranted := False;
  PermissionsService.RequestPermissions(Permissions,
    procedure(const Permissions: TClassicStringDynArray; const aGrantResults: TClassicPermissionStatusDynArray)
    begin
         if ((Length(aGrantResults) = 1) and (aGrantResults[0] = TPermissionStatus.Granted) )
          then begin
            PermissionGranted := True;
         end
    end);

  TThread.Synchronize(TThread.CurrentThread,
    procedure
    begin
      LocalResult := PermissionGranted;
    end);
    Result := LocalResult;
except
end;
{$ENDIF}
end;

Function PreveriMarker(const pvstop:string):String;
var i:integer;
begin
 Result:='';
 for i := low(pvstop) to high(pvstop) do begin
     if pvstop[i].IsLetter or pvstop[i].IsWhiteSpace or pvstop[i].IsNumber
       then
         Result:=Result+pvstop[i];

 end;
end;

procedure MobIniRead(pini:Tmobini);
var
  temps:string;
  pomTIPKE_POS_ID:Integer;
  i, j: integer;
  poms, pom: string;
begin

  temps:='';
  temps:=MobIniVrednost('UPOSTEVAMZGORAJSPODAJ', pini);
  if temps<>'' then
    UPOSTEVAMZGORAJSPODAJ:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('CENIKLOKALNO', pini);
  if temps<>'' then
    CenikLokalno:= temps = 'D';

//  TimerEnableEkran:=3;
  if MobIniVrednost('CENIKSTDNIOBNOVA', pini) <> '' then
    TryStrToInt(MobIniVrednost('CENIKSTDNIOBNOVA', pini), CenikStDniObnova);

  if MobIniVrednost('POMTIPKE_POS_ID', pini) <> '' then
    TryStrToInt(MobIniVrednost('POMTIPKE_POS_ID', pini), PomTIPKE_POS_ID);


  temps:='';
  temps:=MobIniVrednost('BAZENPRODAJASKPAKET', pini);
  if temps<>'' then
    BazenProdajaSKpaket:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('TISKANJEINICIALKE', pini);
  if temps<>'' then
    TISKANJEINICIALKE:= temps = 'D';


//  DavkiFiskalMetoda:=0;
  if MobIniVrednost('DAVKIFISKALMETODA', pini) <> '' then
    TryStrToInt(MobIniVrednost('DAVKIFISKALMETODA', pini), DavkiFiskalMetoda);

//  pomTIPKE_POS_ID:=0;
  if MobIniVrednost('POMTIPKE_POS_ID', pini) <> '' then
    TryStrToInt(MobIniVrednost('POMTIPKE_POS_ID', pini), pomTIPKE_POS_ID);

  temps:='';
  temps:=MobIniVrednost('RAJONI', pini);
  if temps<>'' then Begin
     RAJONI:= temps = 'D';;
  end;

  if MobIniVrednost('RAJONDEFAULT', pini) <> '' then
    TryStrToInt(MobIniVrednost('RAJONDEFAULT', pini), RAJONDEFAULT);

  temps:='';
  temps:=MobIniVrednost('PRINTALIGNLEFT', pini);
  if temps<>'' then
    PRINTALIGNLEFT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PLACILAENABLED', pini);
  if temps<>'' then
    PLACILAENABLED := temps = 'D';

  if MobIniVrednost('ZAMUDAMINUTE', pini) <> '' then
    TryStrToInt(MobIniVrednost('ZAMUDAMINUTE', pini), ZAMUDAMINUTE);

  if MobIniVrednost('VALUTID', pini) <> '' then
    TryStrToInt(MobIniVrednost('VALUTID', pini), VALUTID);

  temps:='';
  temps:=MobIniVrednost('KRONOLOGIZKLOP', pini);
  if temps<>'' then
    KRONOLOGIZKLOP := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('DEBUGL1', pini);
  if temps<>'' then DEBUGL1 := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('DEBUGL2', pini);
  if temps<>'' then DEBUGL2 := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('DEBUGL3', pini);
  if temps<>'' then DEBUGL3 := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('WSRECONNECT', pini);
  if temps<>'' then
    WSRECONNECT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('OBROKI', pini);
  if temps<>'' then
    OBROKI := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('OBRACUNBREZIZPISA', pini);
  if temps<>'' then
    OBRACUNBREZIZPISA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PRINTOFF', pini);
  if temps<>'' then
    PRINTOFF := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('RACUNISOLAHKOFAKTURE', pini);
  if temps<>'' then
    RACUNISOLAHKOFAKTURE := temps = 'D';

  if MobIniVrednost('STDNIVALUTA', pini) <> '' then
    TryStrToInt(MobIniVrednost('STDNIVALUTA', pini), STDNIVALUTA);

  temps:='';
  temps:=MobIniVrednost('KLAVZULAFAKTURA', pini);
  if temps<>'' then
    KLAVZULAFAKTURA := temps;

  temps:='';
  temps:=MobIniVrednost('KLAVZULAFAKTURADAVEK0', pini);
  if temps<>'' then
    KLAVZULAFAKTURADAVEK0 := temps;

  temps:='';
  temps:=MobIniVrednost('PLACILAFAKTURA', pini);
  if temps<>'' then
    PLACILAFAKTURA := temps;

  temps:='';
  temps:=MobIniVrednost('REPORTFORMAT', pini);
  if temps<>'' then
    pREPORTFORMAT := temps.ToLower;

  temps:='';
  temps:=MobIniVrednost('REPREZENTANCAPOFIRMAH', pini);
  if temps<>'' then
    REPREZENTANCAPOFIRMAH := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('LOKATORJI', pini);
  if temps<>'' then
    lokatorji := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('BARCODEPRODAJA', pini);
  if temps<>'' then
    BARCODEPRODAJA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('BARCODENIVO4', pini);
  if temps<>'' then
    BARCODENIVO4 := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PRINTAMSLIPNARACUN', pini);
  if temps<>'' then
    PrintamSlipNaRacun := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SKRIJGUMBIZKLOP', pini);
  if temps<>'' then
    SKRIJGUMBIZKLOP := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('AKCIJE', pini);
  if temps<>'' then
    Akcije := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('INKASOANALITIKA', pini);
  if temps<>'' then
    InkasoAnalitika := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('AVTOMATSKIIZPISRACUNA', pini);
  if temps<>'' then
    AutoPrintRacun := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ODJAVA', pini);
  if temps<>'' then
    pLogout := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ODJAVAPOIZPISU', pini);
  if temps<>'' then
    pLogoutPoIzpisu := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ODJAVAPONAROCILU', pini);
  if temps<>'' then
    pLogoutPoNarocilu := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ODJAVADIALOG', pini);
  if temps<>'' then
    pOdjavaDialog := temps = 'D';


  temps:='';
  temps:=MobIniVrednost('PINLOGIN', pini);
  if temps<>'' then
    PINLOGIN := temps = 'D';

  if MobIniVrednost('LOGOUTCAS', pini) <> '' then
    TryStrToInt(MobIniVrednost('LOGOUTCAS', pini), LOGOUTCAS);
  if LOGOUTCAS<>0 then begin
     LOGOUTCAS:=LOGOUTCAS*6000;
  end;

  temps:='';
  temps:=MobIniVrednost('PRINTBLOK', pini);
  if temps<>'' then
    PrintamNarocila:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PRINTBLOKINNAROCILO', pini);
  if temps<>'' then
    PRINTBLOKINNAROCILO:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PRINTBLOKNIKAMOR', pini);
  if temps<>'' then
    PrintamNarocilaNikamor:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('NEPRINTAMNART1', pini);
  if temps<>'' then
    NePrintamNarT1:= temps = 'D';
  temps:='';
  temps:=MobIniVrednost('NEPRINTAMNART2', pini);
  if temps<>'' then
    NePrintamNarT2:= temps = 'D';
  temps:='';
  temps:=MobIniVrednost('NEPRINTAMNART3', pini);
  if temps<>'' then
    NePrintamNarT3:= temps = 'D';
  temps:='';
  temps:=MobIniVrednost('NEPRINTAMNART4', pini);
  if temps<>'' then
    NePrintamNarT4:= temps = 'D';

  if MobIniVrednost('PRINTBLOKPAVZA', pini) <> '' then
    TryStrToInt(MobIniVrednost('PRINTBLOKPAVZA', pini), PRINTBLOKPAVZA);

  temps:='';
  temps:=MobIniVrednost('PRINTBREZTISKALNIKA', pini);
  if temps<>'' then
    PRINTBREZTISKALNIKA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ESCPREDAL', pini);
  if temps<>'' then
    escpredal :=PretvoriROSESC(temps);

  temps:='';
  temps:=MobIniVrednost('PRINTVOUCHER', pini);
  if temps<>'' then
    PrintamVoucher := temps = 'D';
  if MobIniVrednost('NIVO4IDVOUCHER', pini) <> '' then
    TryStrToInt(MobIniVrednost('NIVO4IDVOUCHER', pini), NIVO4IDVOUCHER);

  if MobIniVrednost('NAPITNINEPROCENTKUH', pini) <> '' then
    TryStrToInt(MobIniVrednost('NAPITNINEPROCENTKUH', pini), NAPITNINEPROCENTKUH);

  temps:='';
  temps:=MobIniVrednost('NAPITNINE', pini);
  if temps<>'' then
    NAPITNINE := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('VRACILONUM', pini);
  if temps<>'' then
    VRACILONUM := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('CROFISK', pini);
  if temps<>'' then
    CROFISK := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('CRONAPITNINE', pini);
  if temps<>'' then
    CRONAPITNINE := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SORTPLACILA', pini);
  if temps<>'' then
    SORTPLACILA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('DPO_VR1', pini);
  if temps<>'' then DPO_VR1 :=temps;
  temps:='';
  temps:=MobIniVrednost('DPO_VR2', pini);
  if temps<>'' then DPO_VR2 :=temps;
  temps:='';
  temps:=MobIniVrednost('DPO_VR3', pini);
  if temps<>'' then DPO_VR3 :=temps;
  temps:='';
  temps:=MobIniVrednost('DPO_VR4', pini);
  if temps<>'' then DPO_VR4 :=temps;
  temps:='';
  temps:=MobIniVrednost('DPO_VR5', pini);
  if temps<>'' then DPO_VR5 :=temps;
  temps:='';
  temps:=MobIniVrednost('DPO_VR6', pini);
  if temps<>'' then DPO_VR6 :=temps;

  temps:='';
  temps:=MobIniVrednost('CHECKPINPOTEKEL', pini);
  if temps<>'' then
    pCHECKPINPOTEKEL := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('NISTORNONAROCILA', pini);
  if temps<>'' then
    NiStornoIzpisanegaNarocila := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('OVERRIDESTORNOPOSLAN', pini);
  if temps<>'' then
    OVERRIDESTORNOPOSLANONAROCILO := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('STORNORAZLOGVR', pini);
  if temps<>'' then
    STORNORAZLOGVR := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('STORNOOSEBASTORNIRAL', pini);
  if temps <> '' then begin
    STORNOOSEBASTORNIRAL:= temps = 'D';
  end;

  temps:='';
  temps:=MobIniVrednost('STORNOPOSVIRTUAL', pini);
  if temps<>'' then
    STORNOPOSVIRTUAL:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('STORNOPOSOVERRIDE', pini);
  if temps<>'' then
    STORNOPOSOVERRIDE:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('NOVAVRSTICANAROCILA', pini);
  if temps<>'' then
    NOVAVRSTICANAROCILA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('EDITKOLICINAADD', pini);
  if temps<>'' then
    EditKolicinaAdd := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PLUAKTIVEN', pini);
  if temps<>'' then
    PLUAKTIVEN := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PODATKINANAPRAVI', pini);
  if temps<>'' then
    PODATKINANAPRAVI:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('IZPISOPOMBARACUN', pini);
  if temps<>'' then
    IZPISOPOMBARACUN:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ZAMENJAVAMARKERMIZE', pini);
  if temps<>'' then
    ZAMENJAVAMARKERMIZE:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('OPOMBETABELAOFF', pini);
  if temps<>'' then
    OPOMBETABELAOFF:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ZAMENJAVAMARKEREDIT', pini);
  if temps<>'' then
    ZAMENJAVAMARKEREDIT:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('VNOSCENEZAVSE', pini);
  if (temps<>'') and (temps='N') then Begin
    VNOSCENEZAVSEINI:=False;
    VNOSCENEZAVSE:=False;
  End;

  temps:='';
  temps:=MobIniVrednost('HODNAROCILA', pini);
  if temps<>'' then
    HODNAROCILA:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PRINTAMSTORNONAROCILA', pini);
  if temps<>'' then
    PRINTAMSTORNONAROCILA:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('MIZEINRACUNI', pini);
  if temps<>'' then
    MIZEINRACUNI:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PRINTNAROCILALOKALNO', pini);
  if temps<>'' then
    PRINTNAROCILALOKALNO := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('LESTVICAEPHT', pini);
  if temps<>'' then
    LESTVICAEPHT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('LESTVICAEP', pini);
  if temps<>'' then
    LESTVICAEP := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('DNCENIK', pini);
  if temps<>'' then
    DNCENIK := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('CENA0CENIK', pini);
  if temps<>'' then
    CENA0CENIK := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('CENA0DOVOLJENA', pini);
  if temps<>'' then
    CENA0DOVOLJENA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('POPUST99PROPORCIALNO', pini);
  if temps<>'' then
    POPUST99PROPORCIALNO := temps = 'D';

  if MobIniVrednost('PLACILOHK', pini) <> '' then
    TryStrToInt(MobIniVrednost('PLACILOHK', pini), PLACILOHK);

  if MobIniVrednost('BONNIVO4ID', pini) <> '' then
    TryStrToInt(MobIniVrednost('BONNIVO4ID', pini), BonNivo4Id);

  if MobIniVrednost('PROCENTNIVELACIJE', pini) <> '' then
    TryStrToCurr(MobIniVrednost('PROCENTNIVELACIJE', pini), PROCENTNIVELACIJE);

  if MobIniVrednost('MODELCENA2', pini) <> '' then
    TryStrToInt(MobIniVrednost('MODELCENA2', pini), MODELCENA2);

  temps:='';
  temps:=MobIniVrednost('CENA2VIKEND', pini);
  if temps<>'' then
    CENA2VIKEND := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('CENA2PREKLOPOFF', pini);
  if temps<>'' then
    CENA2PREKLOPOFF := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('VIDNIVSIRACUNI', pini);
  if temps<>'' then
    OdpriVseRacune := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('RAZLOGSTORNO', pini);
  if temps<>'' then
    RAZLOGSTORNO := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('CRMAKTIVEN', pini);
  if temps<>'' then
    CRMaktiven := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SKUPINENASOBO', pini);
  if temps<>'' then
    SkupineNaSobo := temps = 'D';

  if MobIniVrednost('STKOPIJSLIP', pini) <> '' then
    TryStrToInt(MobIniVrednost('STKOPIJSLIP', pini), STKOPIJSLIP);

  temps:='';
  temps:=MobIniVrednost('KKROCNO', pini);
  if temps<>'' then
    KKROCNO := temps = 'D';

  if MobIniVrednost('KREDKARTICA_KUPECID', pini) <> '' then
    TryStrToInt(MobIniVrednost('KREDKARTICA_KUPECID', pini), KREDKARTICA_KUPECID);

  if MobIniVrednost('KREDKARTICA_KUPECID_DINERS', pini) <> '' then
    TryStrToInt(MobIniVrednost('KREDKARTICA_KUPECID_DINERS', pini), KREDKARTICA_KUPECID_DINERS);

  if MobIniVrednost('KKARTICA_KUPECID_ROCNO', pini) <> '' then
    TryStrToInt(MobIniVrednost('KKARTICA_KUPECID_ROCNO', pini), KKARTICA_KUPECID_ROCNO);

  temps:='';
  temps:=MobIniVrednost('KKarticaTippartnerRocno', pini);
  if temps<>'' then
    TryStrToInt(temps, KKarticaTippartnerRocno);

  temps:='';
  temps:=MobIniVrednost('HOTKEY3PLACILOID', pini);
  if temps<>'' then
    TryStrToInt(temps, HOTKEY3);
  temps:='';
  temps:=MobIniVrednost('HOTKEY4PLACILOID', pini);
  if temps<>'' then
    TryStrToInt(temps, HOTKEY4);
  temps:='';
  temps:=MobIniVrednost('HOTKEY5PLACILOID', pini);
  if temps<>'' then
    TryStrToInt(temps, HOTKEY5);

  temps:='';
  temps:=MobIniVrednost('TIPKAGOTOVINA', pini);
  if temps<>'' then
    TIPKAGOTOVINA := temps = 'D';

  if MobIniVrednost('PRAZNIKIOBRAT', pini) <> '' then
    TryStrToInt(MobIniVrednost('PRAZNIKIOBRAT', pini), PRAZNIKIOBRAT);

  temps:='';
  temps:=MobIniVrednost('PRAZNIKI', pini);
  if temps<>'' then
    PRAZNIKI := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('TISKAMTEXT', pini);
  if temps<>'' then
    TISKAMTEXT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('IZPISRACUNAPARALEL', pini);
  if temps<>'' then
    IzpisRacunaParalel := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('THREADIZPIS', pini);
  if temps<>'' then THREADIZPIS := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('THREADNAROCILO', pini);
  if temps<>'' then THREADNAROCILO := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('THREADODPRTIIZPISANI', pini);
  if temps<>'' then THREADODPRTIIZPISANI := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('THREADVSE', pini);
  if temps<>'' then THREADVSE := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('THREADSTORNO', pini);
  if temps<>'' then THREADSTORNO := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('THREADT', pini);
  if temps<>'' then THREADT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('OBRACUNDOVOLJEN', pini);
  if temps<>'' then
    ObracunDovoljen := temps = 'D';

  if MobIniVrednost('TURBONIMINZNESEK', pini) <> '' then
    TryStrToCurr(MobIniVrednost('TURBONIMINZNESEK', pini), TURBONIMINZNESEK);

  temps:='';
  temps:=MobIniVrednost('BAZENCENASERIJSKEKARTECENIK', pini);
  if temps<>'' then
    BAZENCENASERIJSKEKARTECENIK := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ERRLOG', pini);
  if temps<>'' then
    ERRLOG := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PARTNERFURS', pini);
  if temps<>'' then
    PARTNERFURS := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PETEKWEND', pini);
  if temps<>'' then
    PETEKWEND := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('TURBONIAKTIVNI', pini);
  if temps<>'' then
    TurBoniAktivni := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('TBDONATORON', pini);
  if temps<>'' then
    TBdonatorON := temps = 'D';

  temps:=MobIniVrednost('BOLDNAZIVPODJETJA', pini);
  if temps<>'' then
    BOLDNAZIVPODJETJA := temps = 'D';

  temps:=MobIniVrednost('FISKALNOENAKO', pini);
  if temps<>'' then
    FISKALNOENAKO:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('TURBONINIVO1OK', pini);
  if temps <> '' then begin
    TURBONINIVO1OK:= temps;
    if TURBONINIVO1OK<>'' then
       TurBoniNivo1Dovoljeni:=StringVnizInteger(TURBONINIVO1OK);
  end;

  temps:='';
  temps:=MobIniVrednost('LANGAPP', pini);
  if temps <> '' then begin
    LangApp:= temps.ToLower;
  end;

  temps:='';
  temps:=MobIniVrednost('HOTELKREDIT0', pini);
  if temps<>'' then
    HOTELKREDIT0:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('BONIVKMD5', pini);
  if temps<>'' then
    BoniVKmd5:= temps = 'D';
  if MobIniVrednost('BONIVKDOLZINA', pini) <> '' then
    TryStrToInt(MobIniVrednost('BONIVKDOLZINA', pini), BoniVKdolzina);
  if MobIniVrednost('BONIIDDOLZINA', pini) <> '' then
    TryStrToInt(MobIniVrednost('BONIIDDOLZINA', pini), BoniIDdolzina);

  if MobIniVrednost('PLACILANIZPOGOJ', pini) <> '' then
    PlacilaNizPogoj := (MobIniVrednost('PLACILANIZPOGOJ', pini));

  if MobIniVrednost('MAXIZPISOVRACUNA', pini) <> '' then
    TryStrToInt(MobIniVrednost('MAXIZPISOVRACUNA', pini), MAXIZPISOVRACUNA);

  if MobIniVrednost('INTERVALIZPISANI', pini) <> '' then
    TryStrToInt(MobIniVrednost('INTERVALIZPISANI', pini), INTERVALIZPISANI);

  if MobIniVrednost('CENAPOLNJENJE', pini) = 'N' then
    CenaPolnjenje := 'N';

  if MobIniVrednost('HISOBRATI', pini) <> '' then
    HISOBRATI := MobIniVrednost('HISOBRATI', pini);

  if MobIniVrednost('IZPISANIVIDNIDO', pini) <> '' then
    TryStrToInt(MobIniVrednost('IZPISANIVIDNIDO', pini), IzpisaniVidniDo);

   if MobIniVrednost('NAROCILOSTORNODO', pini) <> '' then
    TryStrToInt(MobIniVrednost('NAROCILOSTORNODO', pini), NarociloStornoDo);

  if MobIniVrednost('PLACILAPODPIS', pini) <> '' then
    PlacilaPodpis := MobIniVrednost('PLACILAPODPIS', pini);
  if PlacilaPodpis<>'' then begin
     try
      poms:=PlacilaPodpis;
      SetLength(PlacilaPodpisID,poms.CountChar(',')+1);
      j:=low(PlacilaPodpisID);
      pom:='';
      for i := Low(poms) to High(poms) do begin
        if poms[i].isnumber then
           pom:=pom+poms[i]
        else if poms[i]=',' then Begin
           PlacilaPodpisID[j]:=pom.ToInteger;
           j:=j+1;
           pom:='';
         end;
      end;
      if pom<>'' then
        PlacilaPodpisID[j]:=pom.ToInteger;
      except
        PlacilaPodpis:='';
      end;
  end;

  {$IF DEFINED(MSWINDOWS)}
  temps:='';
  temps:=MobIniVrednost('BREZVIRTUALKEYBOARD', pini);
  if temps<>'' then
    BREZVIRTUALKEYBOARD := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('WINDOWSDOUBLECLICK', pini);
  if temps<>'' then
    WINDOWSDOUBLECLICK := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('EKRAN1280', pini);
  if temps<>'' then
    EKRAN1280 := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('POSICEBREZSLIP', pini);
  if temps<>'' then
    POSICEBREZSLIP := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('POSICEPRINT', pini);
  if temps<>'' then
    POSICEPRINT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('STORNOPOSICE', pini);
  if temps<>'' then
    StornoPOSICE := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ICEPOS', pini);
  if temps<>'' then
    ICEPOS := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ECRPAY', pini);
  if temps<>'' then
    ECRPAY := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ECRPAY262', pini);
  if temps<>'' then
    EcrPay262 := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ECRPRINT', pini);
  if temps<>'' then
    ECRPRINT := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('ECRSTORNO', pini);
  if temps<>'' then
    ECRSTORNO := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ECRDEBUG', pini);
  if temps<>'' then
    ECRDEBUG := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ECRZABA', pini);
  if temps<>'' then
    ECRZABA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ECRREFUND', pini);
  if temps<>'' then
    ECRREFUND := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('ECRIP', pini);
  if temps<>'' then
    ECRIP := temps;

  if MobIniVrednost('ECRRRNMESTO', pini) <> '' then
    TryStrToInt(MobIniVrednost('ECRRRNMESTO', pini), ECRRRNMESTO);

  temps:='';
  temps:=MobIniVrednost('ECRPORT', pini);
  if temps<>'' then
    ECRPORT := temps;

  temps:='';
  temps:=MobIniVrednost('SIXPAY', pini);
  if temps<>'' then
    SIXPAY := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXCDLL', pini);
  if temps<>'' then
    SixCdll := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXNAPITNINE', pini);
  if temps<>'' then
    SixNapitnine := temps = 'D';


  temps:='';
  temps:=MobIniVrednost('SIXCDLLPATH', pini);
  if temps<>'' then Begin
    SixCdllPath := temps;
    SixCdllPathInit := temps;
  end;

  temps:='';
  temps:=MobIniVrednost('SIXPAYSTORNO', pini);
  if temps<>'' then
    SIXPAYSTORNO := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXPAYTHREAD', pini);
  if temps<>'' then
    SIXPAYTHREAD := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXPAYRESET', pini);
  if temps<>'' then
    SIXPAYRESET := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIX2ACTIVE', pini);
  if temps<>'' then
    SIX2ACTIVE := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXPAYPRINT', pini);
  if temps<>'' then
    SIXPAYPRINT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXTERMINALIDIP', pini);
  if temps<>'' then
    SIXTERMINALIDIP := temps;

  temps:='';
  temps:=MobIniVrednost('SIXPOSID', pini);
  if temps<>'' then
    SIXPOSID := temps;
  if Length(SIXPOSID)>6 then
     SIXPOSID:=COPY(SIXPOSID,0,6);

  if MobIniVrednost('SIXUSERID', pini) <> '' then
    TryStrToInt(MobIniVrednost('SIXUSERID', pini), SIXUSERID);

  if SIXPAY then
     ICEPOS := False;
  if ECRPAY then
     ICEPOS := False;

  temps:='';
  temps:=MobIniVrednost('EDENAR', pini);
  if temps<>'' then
    EDENAR := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('WEBNAROCILA', pini);
  if temps<>'' then
    WEBNAROCILA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('BRISIPLACILAZAWINDOWS', pini);
  if temps <> '' then begin
    BRISIPLACILAZAWINDOWS:= temps;
  end;

  temps:='';
  temps:=MobIniVrednost('BAZENI', pini);
  if temps<>'' then Begin
    Bazeni := temps = 'D';
    METRAAKTIVNA:=True;
  end;

  temps:='';
  temps:=MobIniVrednost('METRAAKTIVNA', pini);
  if temps <> '' then begin
    if temps='N' then
      METRAAKTIVNA:=False;
  end;

  temps:='';
  temps:=MobIniVrednost('METRAQRKODA', pini);
  if temps<>'' then
    METRAQRKODA := temps = 'D';
  temps:='';
  temps:=MobIniVrednost('METRACITALEC', pini);
  if temps<>'' then
    METRACITALEC := temps = 'D';

  if MobIniVrednost('METRAWBCCODE', pini) <> '' then
    METRAWBCCODE := MobIniVrednost('METRAWBCCODE', pini);
  if MobIniVrednost('METRAAREACODE', pini) <> '' then
    METRAAREACODE := MobIniVrednost('METRAAREACODE', pini);
  if MobIniVrednost('METRAURL', pini) <> '' then
    METRAURL := MobIniVrednost('METRAURL', pini);
  if MobIniVrednost('METRAPOS', pini) <> '' then
    METRAPOS := MobIniVrednost('METRAPOS', pini);
  if MobIniVrednost('METRADEVICE', pini) <> '' then
    METRADEVICE := MobIniVrednost('METRADEVICE', pini);
  if MobIniVrednost('METRAISSUECOUNT', pini) <> '' then
    METRAISSUECOUNT := MobIniVrednost('METRAISSUECOUNT', pini);
  if MobIniVrednost('METRAUSERDATA', pini) <> '' then
    METRAUSERDATA := MobIniVrednost('METRAUSERDATA', pini);
  if MobIniVrednost('METRATIMEOUT', pini) <> '' then
    METRATIMEOUT := MobIniVrednost('METRATIMEOUT', pini);
  if MobIniVrednost('METRAUSER', pini) <> '' then
    METRAUSER := MobIniVrednost('METRAUSER', pini);
  temps:='';
  temps:=MobIniVrednost('METRAHEXTODEC', pini);
  if temps<>'' then
    METRAHEXTODEC:= temps = 'D';
  temps:='';
  temps:=MobIniVrednost('METRASTORNOKONTROLA', pini);
  if temps<>'' then
    METRASTORNOKONTROLA:= temps = 'D';
  temps:='';
  temps:=MobIniVrednost('METRALOG', pini);
  if temps<>'' then
    METRALOG:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('HIDPRIJAVA', pini);
  if temps<>'' then
    pHidPrijavaIni:= temps = 'D';
  pHIDprijava:=pHidPrijavaIni;

  temps:='';
  temps:=MobIniVrednost('HIDPRIJAVAFS', pini);
  if temps<>'' then
    pHIDprijavaIniFS:= temps = 'D';

  if pHIDprijavaIniFS and not pHidPrijavaIni  then
    pHIDprijava:=True;

  temps:='';
  temps:=MobIniVrednost('WINDOWSPRINTER', pini);
  if temps<>'' then
    WinSpoolPrint := temps = 'D';
  if WinSpoolPrint then
    BlueToothPrint:= False
  else
    BlueToothPrint:= true;

  temps:='';
  temps:=MobIniVrednost('TENZORGAT', pini);
  if temps<>'' then
    TENZORGAT:= temps = 'D';

  temps:='';
  temps:=MobIniVrednost('TENZORIP', pini);
  if temps<>'' then
    TENZORIP:= temps;

  temps:='';
  temps:=MobIniVrednost('TENZORMID', pini);
  if temps<>'' then
    TENZORMID:= temps;

  temps:='';
  temps:=MobIniVrednost('TENZORPORT', pini);
  if temps<>'' then
    TENZORPORT:= temps;

  temps:='';
  temps:=MobIniVrednost('TENZORZAMUDNINA', pini);
  if temps<>'' then
    TENZORZAMUDNINA:= temps;

  temps:='';
  temps:=MobIniVrednost('TENZORLOG', pini);
  if temps<>'' then
    TENZORLOG:= temps = 'D';



  if MobIniVrednost('TIPQRKODE', pini) <> '' then
    TryStrToInt(MobIniVrednost('TIPQRKODE', pini), tipqrkode);

  {$ENDIF}

  {$IF DEFINED(IOS) or DEFINED(ANDROID)}
  BlueToothPrint:= true;

  if MobIniVrednost('KBPSLIMIT', pini) <> '' then
    TryStrToInt(MobIniVrednost('KBPSLIMIT', pini), KbpsLimit);

  temps:='';
  temps:=MobIniVrednost('ANDROIDNET2X', pini);
  if temps<>'' then Begin
     ANDROIDNET2X:= temps = 'D';;
  end;

  temps:='';
  temps:=MobIniVrednost('ANDROIDNETTCP', pini);
  if temps<>'' then Begin
     ANDROIDNETTCP:= temps;
  end;


  TimerEnableEkran:=10000;
  if MobIniVrednost('TIMERENABLEEKRAN', pini) <> '' then
    TryStrToInt(MobIniVrednost('TIMERENABLEEKRAN', pini), TimerEnableEkran);


  TimeOutConnect:=10000;
  if MobIniVrednost('TIMEOUTCONNECT', pini) <> '' then
    TryStrToInt(MobIniVrednost('TIMEOUTCONNECT', pini), TimeOutConnect);

  TimeOutSend:=20000;
  if MobIniVrednost('TIMEOUTSEND', pini) <> '' then
    TryStrToInt(MobIniVrednost('TIMEOUTSEND', pini), TimeOutSend);

  TimeOutReceive:=30000;
  if MobIniVrednost('TIMEOUTRECEIVE', pini) <> '' then
    TryStrToInt(MobIniVrednost('TIMEOUTRECEIVE', pini), TimeOutReceive);



  ANDROIDNETTCPPORT:=53;
  if MobIniVrednost('ANDROIDNETTCPPORT', pini) <> '' then
    TryStrToInt(MobIniVrednost('ANDROIDNETTCPPORT', pini), ANDROIDNETTCPPORT);


  temps:='';
  temps:=MobIniVrednost('ANDROIDNFCIZKLOP', pini);
  if temps<>'' then Begin
     pnfcprijava:= temps = 'D';;
     globalnfc:=pnfcprijava;
  end;
  if MobIniVrednost('HTFONTANDROID', pini) <> '' then
    TryStrToInt(MobIniVrednost('HTFONTANDROID', pini), HTfontAndroid);

  temps:='';
  temps:=MobIniVrednost('TISKANJEPAVZA', pini);
  if temps<>'' then
    TISKANJEPAVZA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('OBRACUNANDROID', pini);
  if temps<>'' then
    ObracunAndroid := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PAYTENA', pini);
  if temps<>'' then
    PAYTENA := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('PAYTENAROSPACKAGE', pini);
  if temps<>'' then
    PAYTENAROSPACKAGE := temps;

  temps:='';
  temps:=MobIniVrednost('PAYTENAACTIVITYMAIN', pini);
  if temps<>'' then
    PAYTENAACTIVITYMAIN := temps;

  temps:='';
  temps:=MobIniVrednost('PAYTENAACTIVITIES', pini);
  if temps<>'' then
    PAYTENAACTIVITIES := temps;

  temps:='';
  temps:=MobIniVrednost('PAYTENAPIN', pini);
  if temps<>'' then
    PAYTENAPIN := temps;

  temps:='';
  temps:=MobIniVrednost('PAYTENASTORNO', pini);
  if temps<>'' then
    PAYTENASTORNO := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXTAP', pini);
  if temps<>'' then
    SixTap := temps = 'D';
  if SixTap then Begin
     ICEPOS := False;
     SIXPAY := False;
     PAYTENA:= False;
  end;

  temps:='';
  temps:=MobIniVrednost('SIXTAP_MANUAL_LAST', pini);
  if temps<>'' then
    SixTapManualLastTransaction := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXTAP_AUTO_LAST', pini);
  if temps<>'' then
    SixTapAutoLastTransaction := temps = 'D';


  temps:='';
  temps:=MobIniVrednost('SIXTAPPRINT', pini);
  if temps<>'' then
    SIXTAPPRINT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXTAPSTORNO', pini);
  if temps<>'' then
    SIXTAPSTORNO := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXTAP_DEBUG', pini);
  if temps<>'' then
    SIXTAP_DEBUG := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('SIXTAPSTORNOZADNJI', pini);
  if temps<>'' then
    SIXTAPSTORNOZADNJI := temps = 'D';

  temps:='';
  SIXTAPWPI_VERSION:='';
  temps:=MobIniVrednost('SIXTAPWPI_VERSION', pini);
  if temps<>'' then
    SIXTAPWPI_VERSION := temps;

  if SIXTAPWPI_VERSION.Contains('2.') then
    RECOVERTAPON:=True;

  temps:='';
  SIXTAP_FORMAT:='';
  temps:=MobIniVrednost('SIXTAP_FORMAT', pini);
  if temps<>'' then
    SIXTAP_FORMAT := temps;

  temps:='';
  temps:=MobIniVrednost('RECOVERTAPON', pini);
  if temps<>'' then
    RECOVERTAPON := temps = 'D';
  if RECOVERTAPON then Begin
    SixTapWPI_VERSION:='2.2';
    TapOnRecoverIntentStari:=False;
  end;

  temps:='';
  temps:=MobIniVrednost('TAPONRECOVERINTENT', pini);
  if temps<>'' then
    TAPONRECOVERINTENT := temps = 'D';
  if TAPONRECOVERINTENT then
     TapOnRecoverIntentStari:=False;

  temps:='';
  temps:=MobIniVrednost('TAPONRECOVERLOGOUT', pini);
  if temps<>'' then
    TAPONRECOVERLOGOUT := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('NAPITNINAPOS', pini);
  if temps<>'' then
    NAPITNINAPOS := temps = 'D';

  if MobIniVrednost('PRINTEROPTIPOS32', pini) <> '' then
    pPrinterBTOptiPos := MobIniVrednost('PRINTEROPTIPOS32', pini);

  temps:='';
  temps:=MobIniVrednost('FORCETABLETSCREEN', pini);
  if temps<>'' then
    FORCETABLETSCREEN := temps = 'D';

  temps:='';
  temps:=MobIniVrednost('BRISIPLACILAZAANDROID', pini);
  if temps <> '' then begin
    BRISIPLACILAZAANDROID:= temps;
  end;

  temps:='';
  temps:=MobIniVrednost('NAPITNINAROS', pini);
  if temps<>'' then
    NAPITNINAROS := temps = 'D';

  if MobIniVrednost('KOREKCIJAGUMBMIZE', pini) <> '' then
    TryStrToInt(MobIniVrednost('KOREKCIJAGUMBMIZE', pini), KOREKCIJAGUMBMIZE);

  if NAPITNINAROS then
    NapitninaPOS:=false;
  {$ENDIF}

end;


end.
