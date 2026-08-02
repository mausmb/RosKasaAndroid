// ************************************************************************ //
// The types declared in this file were generated from data read from the
// WSDL File described below:
// WSDL     : C:\xeros\RosKasaMobile2020\RosKasa_ceniki_wsdl.wsdl
//  >Import : C:\xeros\RosKasaMobile2020\RosKasa_ceniki_wsdl.wsdl>0
//  >Import : C:\xeros\RosKasaMobile2020\RosKasa_ceniki_wsdl.wsdl>1
// Codegen  : [wfUseXSTypeForSimpleNillable+]
// (1. 06. 2026 11:20:28 - - $Rev: 125242 $)
// ************************************************************************ //

unit RosKasa_ceniki_wsdl;

interface

uses Soap.InvokeRegistry, Soap.SOAPHTTPClient, System.Types, Soap.XSBuiltIns;

const
  IS_OPTN = $0001;
  IS_UNBD = $0002;
  IS_NLBL = $0004;
  IS_REF  = $0080;


type

  // ************************************************************************ //
  // The following types, referred to in the WSDL document are not being represented
  // in this file. They are either aliases[@] of other types represented or were referred
  // to but never[!] declared in the document. The types from the latter category
  // typically map to predefined/known XML or Embarcadero types; however, they could also
  // indicate incorrect WSDL documents that failed to declare or import a schema type.
  // ************************************************************************ //
  // !:base64Binary    - "http://www.w3.org/2001/XMLSchema"[Gbl]
  // !:dateTime        - "http://www.w3.org/2001/XMLSchema"[Gbl]
  // !:string          - "http://www.w3.org/2001/XMLSchema"[Gbl]
  // !:int             - "http://www.w3.org/2001/XMLSchema"[Gbl]
  // !:decimal         - "http://www.w3.org/2001/XMLSchema"[Gbl]
  // !:boolean         - "http://www.w3.org/2001/XMLSchema"[Gbl]
  // !:long            - "http://www.w3.org/2001/XMLSchema"[Gbl]

  BaseDAL              = class;                 { "http://ros.si/R16"[GblCplx] }
  ExtensionDataObject  = class;                 { "http://ros.si/R16"[GblCplx] }
  TiskajTp             = class;                 { "http://ros.si/R16"[GblCplx] }
  ResponseType         = class;                 { "http://ros.si/R16"[GblCplx] }
  GetAppConfigRsTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  GetRacuniRsTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetRacunRsTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  GetInkasoOsebeRsTp   = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNacPlacMakroRsTp  = class;                 { "http://ros.si/R16"[GblCplx] }
  GetObrokiRsTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNovaNarocilaRsTp  = class;                 { "http://ros.si/R16"[GblCplx] }
  GetProstorRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetKartprijRsTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  GetPartnerRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetListaSkupinRsTp   = class;                 { "http://ros.si/R16"[GblCplx] }
  GetPrijavaRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetRacuniSeznamRsTp  = class;                 { "http://ros.si/R16"[GblCplx] }
  PrioritetaProjektaTp = class;                 { "http://ros.si/R16"[GblCplx] }
  OsebaTokenRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  OsebaTp              = class;                 { "http://ros.si/R16"[GblCplx] }
  MobileSetupsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  MizaTp               = class;                 { "http://ros.si/R16"[GblCplx] }
  MobileSetupTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  IzpisanTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  NarediObracunRqTp    = class;                 { "http://ros.si/R16"[GblCplx] }
  GetRacuniRqTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  SetStornoRqTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  NacPlacMakroTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  NarociloTp           = class;                 { "http://ros.si/R16"[GblCplx] }
  ObrokTp              = class;                 { "http://ros.si/R16"[GblCplx] }
  GetObrokiRqTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  KartprijTp           = class;                 { "http://ros.si/R16"[GblCplx] }
  ProstorTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  ZdruziRacuneRqTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  GetKartprijRqTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  GetListaSkupinRqTp   = class;                 { "http://ros.si/R16"[GblCplx] }
  GetProstorRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  PrijavaTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  GetPrijavaRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  ListaSkupinTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  RacunSeznamTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  TarifaTp             = class;                 { "http://ros.si/R16"[GblCplx] }
  InkasoOsebeTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  PartnerTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  CenikVrTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  CenikVrVrTp          = class;                 { "http://ros.si/R16"[GblCplx] }
  CenikVrCeneTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetPartnerRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  ZamenjajLastnikaRqTp = class;                 { "http://ros.si/R16"[GblCplx] }
  RacTBonTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  SetStornoRacunaRqTp  = class;                 { "http://ros.si/R16"[GblCplx] }
  PozicijaTp           = class;                 { "http://ros.si/R16"[GblCplx] }
  GetCenikRsTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNacPlacRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  CenikGlTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  GetTimesRsTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  KronologijaTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetMobileSetupsRsTp  = class;                 { "http://ros.si/R16"[GblCplx] }
  TimesTp              = class;                 { "http://ros.si/R16"[GblCplx] }
  HitraTipkaTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  GetHitreTipkeRsTp    = class;                 { "http://ros.si/R16"[GblCplx] }
  InsertKronologRqTp   = class;                 { "http://ros.si/R16"[GblCplx] }
  NacPlacTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  RacunTp              = class;                 { "http://ros.si/R16"[GblCplx] }
  PlaciloTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  LojalnostnaTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  RequestTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsSaleRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsGetStatusRqTp  = class;                 { "http://ros.si/R16"[GblCplx] }
  ResponseTp           = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsGetStatusRsTp  = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsSaleRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsFursRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsVoidRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsVoidRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  ValuStartPaymentRsTp = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsFursRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsRefundRqTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  MbillsRefundRsTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaNazivRsTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaSetLojalnostRqTp = class;               { "http://ros.si/R16"[GblCplx] }
  AkcijaArtikliRqTp    = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaArtikelTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaArtikliRsTp    = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaSetLojalnostRsTp = class;               { "http://ros.si/R16"[GblCplx] }
  TransactionDetailsWs = class;                 { "http://ros.si/R16"[GblCplx] }
  MonetaRsTp           = class;                 { "http://ros.si/R16"[GblCplx] }
  GetTransactionStatusRsTp = class;             { "http://ros.si/R16"[GblCplx] }
  GetTokenRsTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  CancelTransactionRsTp = class;                { "http://ros.si/R16"[GblCplx] }
  KuponAkcijaSaldoRqTp = class;                 { "http://ros.si/R16"[GblCplx] }
  KuponAkcijaKnjiziRqTp = class;                { "http://ros.si/R16"[GblCplx] }
  KuponKnjiziRsTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  KuponAkcijaTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetKuponAkcijaRsTp   = class;                 { "http://ros.si/R16"[GblCplx] }
  TbBalanceRqTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  TBonRefundTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  TBonRefund21Tp       = class;                 { "http://ros.si/R16"[GblCplx] }
  TbStornoRqTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  TBonDocumentTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  CrmTockeRsTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  BonSaldoRqTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  BonSaldoRsTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  ValuGetPaymentStatusRsTp = class;             { "http://ros.si/R16"[GblCplx] }
  CrmInfoRqTp          = class;                 { "http://ros.si/R16"[GblCplx] }
  CrmInfoRsTp          = class;                 { "http://ros.si/R16"[GblCplx] }
  BonKnjiziRqTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  KuponSaldoRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  KuponSaldoRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  KuponKnjiziRqTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  BonKnjiziRsTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  BoniIzdajaRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  BoniIzdajaRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetReportRqTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  DelovniNalogTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetSlipEmaRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetSlipEmaRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  SetSlipEmaRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetDelovniNalogiRsTp = class;                 { "http://ros.si/R16"[GblCplx] }
  StornoRazlogTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetStornoRazlogiRsTp = class;                 { "http://ros.si/R16"[GblCplx] }
  RecepturaItemTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  GetRecepturaRsTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  GetDodatkiRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetQrFursRsTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetLojalnostnaRsTp   = class;                 { "http://ros.si/R16"[GblCplx] }
  DodatekTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  SearchGostTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  GetSlipEma2Tp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetSlipEma2RsTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  SearchGostRsTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  GetSlipEma2RqTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNapitninaRqTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNapitninaRsTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  GetBazenKarteRqTp    = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNapitninaSkupajRsTp = class;               { "http://ros.si/R16"[GblCplx] }
  SetNapitninaRqTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  BazenKartaTp         = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaGetRqTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaTp             = class;                 { "http://ros.si/R16"[GblCplx] }
  AkcijaGetRsTp        = class;                 { "http://ros.si/R16"[GblCplx] }
  GetBazenKarteRsTp    = class;                 { "http://ros.si/R16"[GblCplx] }
  SetBazenKarteRqTp    = class;                 { "http://ros.si/R16"[GblCplx] }
  NatisniHodRqTp       = class;                 { "http://ros.si/R16"[GblCplx] }
  Nivo4TujiTp          = class;                 { "http://ros.si/R16"[GblCplx] }
  GetStornoIdRsTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNivo4IdFromBarcodeRqTp = class;            { "http://ros.si/R16"[GblCplx] }
  GetNivo4IdFromBarcodeRsTp = class;            { "http://ros.si/R16"[GblCplx] }
  GetNivo4TujiRsTp     = class;                 { "http://ros.si/R16"[GblCplx] }
  GetNapitninaSkupajRqTp = class;               { "http://ros.si/R16"[GblCplx] }
  GetNapitninaSkupajTp = class;                 { "http://ros.si/R16"[GblCplx] }
  GetPraznikiRqTp      = class;                 { "http://ros.si/R16"[GblCplx] }
  PraznikTp            = class;                 { "http://ros.si/R16"[GblCplx] }
  GetPraznikiRsTp      = class;                 { "http://ros.si/R16"[GblCplx] }

  ArrayOfRacTBonTp = array of RacTBonTp;        { "http://ros.si/R16"[GblCplx] }
  ArrayOfPlaciloTp = array of PlaciloTp;        { "http://ros.si/R16"[GblCplx] }
  ArrayOfPozicijaTp = array of PozicijaTp;      { "http://ros.si/R16"[GblCplx] }
  ArrayOfListaSkupinTp = array of ListaSkupinTp;   { "http://ros.si/R16"[GblCplx] }
  ArrayOfProstorTp = array of ProstorTp;        { "http://ros.si/R16"[GblCplx] }
  ArrayOfKartprijTp = array of KartprijTp;      { "http://ros.si/R16"[GblCplx] }
  ArrayOfObrokTp = array of ObrokTp;            { "http://ros.si/R16"[GblCplx] }
  ArrayOfNarociloTp = array of NarociloTp;      { "http://ros.si/R16"[GblCplx] }
  ArrayOfNacPlacMakroTp = array of NacPlacMakroTp;   { "http://ros.si/R16"[GblCplx] }
  ArrayOfInkasoOsebeTp = array of InkasoOsebeTp;   { "http://ros.si/R16"[GblCplx] }
  ArrayOfTiskajTp = array of TiskajTp;          { "http://ros.si/R16"[GblCplx] }
  ArrayOfRacunTp = array of RacunTp;            { "http://ros.si/R16"[GblCplx] }
  ArrayOfPartnerTp = array of PartnerTp;        { "http://ros.si/R16"[GblCplx] }
  ArrayOfMizaTp = array of MizaTp;              { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : BaseDAL, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BaseDAL = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
  end;

  ArrayOfTarifaTp = array of TarifaTp;          { "http://ros.si/R16"[GblCplx] }
  ArrayOfOsebaTp = array of OsebaTp;            { "http://ros.si/R16"[GblCplx] }
  ArrayOfPrioritetaProjektaTp = array of PrioritetaProjektaTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : ExtensionDataObject, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ExtensionDataObject = class(TRemotable)
  private
  published
  end;



  // ************************************************************************ //
  // XML       : TiskajTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TiskajTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FTiskalnik: string;
    FTiskalnik_Specified: boolean;
    FVsebina: TByteSOAPArray;
    FVsebina_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetTiskalnik(Index: Integer; const Astring: string);
    function  Tiskalnik_Specified(Index: Integer): boolean;
    procedure SetVsebina(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
    function  Vsebina_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property Tiskalnik:     string               Index (IS_OPTN) read FTiskalnik write SetTiskalnik stored Tiskalnik_Specified;
    property Vsebina:       TByteSOAPArray       Index (IS_OPTN) read FVsebina write SetVsebina stored Vsebina_Specified;
  end;

  ArrayOfString = array of string;              { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : ResponseType, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ResponseType = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    Fdata1: string;
    Fdata1_Specified: boolean;
    Fdata2: string;
    Fdata2_Specified: boolean;
    Ffault: string;
    Ffault_Specified: boolean;
    Fstrings: ArrayOfString;
    Fstrings_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure Setdata1(Index: Integer; const Astring: string);
    function  data1_Specified(Index: Integer): boolean;
    procedure Setdata2(Index: Integer; const Astring: string);
    function  data2_Specified(Index: Integer): boolean;
    procedure Setfault(Index: Integer; const Astring: string);
    function  fault_Specified(Index: Integer): boolean;
    procedure Setstrings(Index: Integer; const AArrayOfString: ArrayOfString);
    function  strings_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property data1:         string               Index (IS_OPTN) read Fdata1 write Setdata1 stored data1_Specified;
    property data2:         string               Index (IS_OPTN) read Fdata2 write Setdata2 stored data2_Specified;
    property fault:         string               Index (IS_OPTN) read Ffault write Setfault stored fault_Specified;
    property strings:       ArrayOfString        Index (IS_OPTN) read Fstrings write Setstrings stored strings_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetAppConfigRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetAppConfigRsTp = class(ResponseType)
  private
    FMobileSetup: MobileSetupTp;
    FMobileSetup_Specified: boolean;
    procedure SetMobileSetup(Index: Integer; const AMobileSetupTp: MobileSetupTp);
    function  MobileSetup_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property MobileSetup: MobileSetupTp  Index (IS_OPTN) read FMobileSetup write SetMobileSetup stored MobileSetup_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetRacuniRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetRacuniRsTp = class(ResponseType)
  private
    FRacuni: ArrayOfRacunTp;
    FRacuni_Specified: boolean;
    procedure SetRacuni(Index: Integer; const AArrayOfRacunTp: ArrayOfRacunTp);
    function  Racuni_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Racuni: ArrayOfRacunTp  Index (IS_OPTN) read FRacuni write SetRacuni stored Racuni_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetRacunRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetRacunRsTp = class(ResponseType)
  private
    FRACGLAVA: RacunTp;
    FRACGLAVA_Specified: boolean;
    FTiskaj: ArrayOfTiskajTp;
    FTiskaj_Specified: boolean;
    procedure SetRACGLAVA(Index: Integer; const ARacunTp: RacunTp);
    function  RACGLAVA_Specified(Index: Integer): boolean;
    procedure SetTiskaj(Index: Integer; const AArrayOfTiskajTp: ArrayOfTiskajTp);
    function  Tiskaj_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property RACGLAVA: RacunTp          Index (IS_OPTN) read FRACGLAVA write SetRACGLAVA stored RACGLAVA_Specified;
    property Tiskaj:   ArrayOfTiskajTp  Index (IS_OPTN) read FTiskaj write SetTiskaj stored Tiskaj_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetInkasoOsebeRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetInkasoOsebeRsTp = class(ResponseType)
  private
    FInkaso: ArrayOfInkasoOsebeTp;
    FInkaso_Specified: boolean;
    procedure SetInkaso(Index: Integer; const AArrayOfInkasoOsebeTp: ArrayOfInkasoOsebeTp);
    function  Inkaso_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Inkaso: ArrayOfInkasoOsebeTp  Index (IS_OPTN) read FInkaso write SetInkaso stored Inkaso_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetNacPlacMakroRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNacPlacMakroRsTp = class(ResponseType)
  private
    FMakro: ArrayOfNacPlacMakroTp;
    FMakro_Specified: boolean;
    procedure SetMakro(Index: Integer; const AArrayOfNacPlacMakroTp: ArrayOfNacPlacMakroTp);
    function  Makro_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Makro: ArrayOfNacPlacMakroTp  Index (IS_OPTN) read FMakro write SetMakro stored Makro_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetObrokiRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetObrokiRsTp = class(ResponseType)
  private
    FObroki: ArrayOfObrokTp;
    FObroki_Specified: boolean;
    procedure SetObroki(Index: Integer; const AArrayOfObrokTp: ArrayOfObrokTp);
    function  Obroki_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Obroki: ArrayOfObrokTp  Index (IS_OPTN) read FObroki write SetObroki stored Obroki_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetNovaNarocilaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNovaNarocilaRsTp = class(ResponseType)
  private
    FNarocila: ArrayOfNarociloTp;
    FNarocila_Specified: boolean;
    procedure SetNarocila(Index: Integer; const AArrayOfNarociloTp: ArrayOfNarociloTp);
    function  Narocila_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Narocila: ArrayOfNarociloTp  Index (IS_OPTN) read FNarocila write SetNarocila stored Narocila_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetProstorRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetProstorRsTp = class(ResponseType)
  private
    FPROSTOR: ArrayOfProstorTp;
    FPROSTOR_Specified: boolean;
    procedure SetPROSTOR(Index: Integer; const AArrayOfProstorTp: ArrayOfProstorTp);
    function  PROSTOR_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property PROSTOR: ArrayOfProstorTp  Index (IS_OPTN) read FPROSTOR write SetPROSTOR stored PROSTOR_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetKartprijRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetKartprijRsTp = class(ResponseType)
  private
    FKARTPRIJ: ArrayOfKartprijTp;
    FKARTPRIJ_Specified: boolean;
    procedure SetKARTPRIJ(Index: Integer; const AArrayOfKartprijTp: ArrayOfKartprijTp);
    function  KARTPRIJ_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property KARTPRIJ: ArrayOfKartprijTp  Index (IS_OPTN) read FKARTPRIJ write SetKARTPRIJ stored KARTPRIJ_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetPartnerRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetPartnerRsTp = class(ResponseType)
  private
    FPARTNER: ArrayOfPartnerTp;
    FPARTNER_Specified: boolean;
    procedure SetPARTNER(Index: Integer; const AArrayOfPartnerTp: ArrayOfPartnerTp);
    function  PARTNER_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property PARTNER: ArrayOfPartnerTp  Index (IS_OPTN) read FPARTNER write SetPARTNER stored PARTNER_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetListaSkupinRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetListaSkupinRsTp = class(ResponseType)
  private
    FSkupine: ArrayOfListaSkupinTp;
    FSkupine_Specified: boolean;
    procedure SetSkupine(Index: Integer; const AArrayOfListaSkupinTp: ArrayOfListaSkupinTp);
    function  Skupine_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Skupine: ArrayOfListaSkupinTp  Index (IS_OPTN) read FSkupine write SetSkupine stored Skupine_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetPrijavaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetPrijavaRsTp = class(ResponseType)
  private
    FPRIJAVA: PrijavaTp;
    FPRIJAVA_Specified: boolean;
    procedure SetPRIJAVA(Index: Integer; const APrijavaTp: PrijavaTp);
    function  PRIJAVA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property PRIJAVA: PrijavaTp  Index (IS_OPTN) read FPRIJAVA write SetPRIJAVA stored PRIJAVA_Specified;
  end;

  ArrayOfRacunSeznamTp = array of RacunSeznamTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetRacuniSeznamRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetRacuniSeznamRsTp = class(ResponseType)
  private
    FRacuni: ArrayOfRacunSeznamTp;
    FRacuni_Specified: boolean;
    procedure SetRacuni(Index: Integer; const AArrayOfRacunSeznamTp: ArrayOfRacunSeznamTp);
    function  Racuni_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Racuni: ArrayOfRacunSeznamTp  Index (IS_OPTN) read FRacuni write SetRacuni stored Racuni_Specified;
  end;



  // ************************************************************************ //
  // XML       : PrioritetaProjektaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  PrioritetaProjektaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FCAPTION: string;
    FCAPTION_Specified: boolean;
    FPOZICIJA_ID: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetCAPTION(Index: Integer; const Astring: string);
    function  CAPTION_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property CAPTION:       string               Index (IS_OPTN) read FCAPTION write SetCAPTION stored CAPTION_Specified;
    property POZICIJA_ID:   Integer              read FPOZICIJA_ID write FPOZICIJA_ID;
  end;



  // ************************************************************************ //
  // XML       : OsebaTokenRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  OsebaTokenRsTp = class(TRemotable)
  private
    Ffault: string;
    Ffault_Specified: boolean;
    Fnaziv: string;
    Fnaziv_Specified: boolean;
    FosebaId: TXSInteger;
    Ftoken: string;
    Ftoken_Specified: boolean;
    Fpin: string;
    Fpin_Specified: boolean;
    FstrmId: TXSInteger;
    Fopozorilo: string;
    Fopozorilo_Specified: boolean;
    Fmodel: string;
    Fmodel_Specified: boolean;
    procedure Setfault(Index: Integer; const Astring: string);
    function  fault_Specified(Index: Integer): boolean;
    procedure Setnaziv(Index: Integer; const Astring: string);
    function  naziv_Specified(Index: Integer): boolean;
    procedure Settoken(Index: Integer; const Astring: string);
    function  token_Specified(Index: Integer): boolean;
    procedure Setpin(Index: Integer; const Astring: string);
    function  pin_Specified(Index: Integer): boolean;
    procedure Setopozorilo(Index: Integer; const Astring: string);
    function  opozorilo_Specified(Index: Integer): boolean;
    procedure Setmodel(Index: Integer; const Astring: string);
    function  model_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property fault:     string      Index (IS_OPTN) read Ffault write Setfault stored fault_Specified;
    property naziv:     string      Index (IS_OPTN) read Fnaziv write Setnaziv stored naziv_Specified;
    property osebaId:   TXSInteger  Index (IS_NLBL) read FosebaId write FosebaId;
    property token:     string      Index (IS_OPTN) read Ftoken write Settoken stored token_Specified;
    property pin:       string      Index (IS_OPTN) read Fpin write Setpin stored pin_Specified;
    property strmId:    TXSInteger  Index (IS_NLBL) read FstrmId write FstrmId;
    property opozorilo: string      Index (IS_OPTN) read Fopozorilo write Setopozorilo stored opozorilo_Specified;
    property model:     string      Index (IS_OPTN) read Fmodel write Setmodel stored model_Specified;
  end;



  // ************************************************************************ //
  // XML       : OsebaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  OsebaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDATUM_SPREMEMBEPIN: TXSDateTime;
    FKARTICA_ID: string;
    FKARTICA_ID_Specified: boolean;
    FKARTICA2_ID: string;
    FKARTICA2_ID_Specified: boolean;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FODDELEK: string;
    FODDELEK_Specified: boolean;
    FOSEBA_ID: Integer;
    FPIN: string;
    FPIN_Specified: boolean;
    FPRIORITETA: TXSInteger;
    FPRIVILEGIJI: string;
    FPRIVILEGIJI_Specified: boolean;
    FPravice: string;
    FPravice_Specified: boolean;
    FUPORABNISKO_IME: string;
    FUPORABNISKO_IME_Specified: boolean;
    FVELJAVNOSTPIN: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKARTICA_ID(Index: Integer; const Astring: string);
    function  KARTICA_ID_Specified(Index: Integer): boolean;
    procedure SetKARTICA2_ID(Index: Integer; const Astring: string);
    function  KARTICA2_ID_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetODDELEK(Index: Integer; const Astring: string);
    function  ODDELEK_Specified(Index: Integer): boolean;
    procedure SetPIN(Index: Integer; const Astring: string);
    function  PIN_Specified(Index: Integer): boolean;
    procedure SetPRIVILEGIJI(Index: Integer; const Astring: string);
    function  PRIVILEGIJI_Specified(Index: Integer): boolean;
    procedure SetPravice(Index: Integer; const Astring: string);
    function  Pravice_Specified(Index: Integer): boolean;
    procedure SetUPORABNISKO_IME(Index: Integer; const Astring: string);
    function  UPORABNISKO_IME_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:      ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DATUM_SPREMEMBEPIN: TXSDateTime          Index (IS_NLBL) read FDATUM_SPREMEMBEPIN write FDATUM_SPREMEMBEPIN;
    property KARTICA_ID:         string               Index (IS_OPTN) read FKARTICA_ID write SetKARTICA_ID stored KARTICA_ID_Specified;
    property KARTICA2_ID:        string               Index (IS_OPTN) read FKARTICA2_ID write SetKARTICA2_ID stored KARTICA2_ID_Specified;
    property NAZIV:              string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property ODDELEK:            string               Index (IS_OPTN) read FODDELEK write SetODDELEK stored ODDELEK_Specified;
    property OSEBA_ID:           Integer              read FOSEBA_ID write FOSEBA_ID;
    property PIN:                string               Index (IS_OPTN) read FPIN write SetPIN stored PIN_Specified;
    property PRIORITETA:         TXSInteger           Index (IS_NLBL) read FPRIORITETA write FPRIORITETA;
    property PRIVILEGIJI:        string               Index (IS_OPTN) read FPRIVILEGIJI write SetPRIVILEGIJI stored PRIVILEGIJI_Specified;
    property Pravice:            string               Index (IS_OPTN) read FPravice write SetPravice stored Pravice_Specified;
    property UPORABNISKO_IME:    string               Index (IS_OPTN) read FUPORABNISKO_IME write SetUPORABNISKO_IME stored UPORABNISKO_IME_Specified;
    property VELJAVNOSTPIN:      TXSInteger           Index (IS_NLBL) read FVELJAVNOSTPIN write FVELJAVNOSTPIN;
  end;

  ArrayOfInt = array of Integer;                { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : MobileSetupsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MobileSetupsTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FFIRMA: string;
    FFIRMA_Specified: boolean;
    FMOBILE_SETUP_ID: Integer;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FTOCILNICA_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetFIRMA(Index: Integer; const Astring: string);
    function  FIRMA_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:   ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property FIRMA:           string               Index (IS_OPTN) read FFIRMA write SetFIRMA stored FIRMA_Specified;
    property MOBILE_SETUP_ID: Integer              read FMOBILE_SETUP_ID write FMOBILE_SETUP_ID;
    property NAZIV:           string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property TOCILNICA_ID:    TXSInteger           Index (IS_NLBL) read FTOCILNICA_ID write FTOCILNICA_ID;
  end;



  // ************************************************************************ //
  // XML       : MizaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MizaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FRAJON: TXSInteger;
    FZAP: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property NAZIV:         string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property RAJON:         TXSInteger           Index (IS_NLBL) read FRAJON write FRAJON;
    property ZAP:           TXSInteger           Index (IS_NLBL) read FZAP write FZAP;
  end;



  // ************************************************************************ //
  // XML       : MobileSetupTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MobileSetupTp = class(BaseDAL)
  private
    FCERTIFIKAT: string;
    FCERTIFIKAT_Specified: boolean;
    FCertBytes: TByteSOAPArray;
    FCertBytes_Specified: boolean;
    FCertPass: string;
    FCertPass_Specified: boolean;
    FDAVCNA_ZAFURS: string;
    FDAVCNA_ZAFURS_Specified: boolean;
    FDDVPODJETJA: string;
    FDDVPODJETJA_Specified: boolean;
    FESCALIGNCENTER: string;
    FESCALIGNCENTER_Specified: boolean;
    FESCALIGNLEFT: string;
    FESCALIGNLEFT_Specified: boolean;
    FESCALIGNRIGHT: string;
    FESCALIGNRIGHT_Specified: boolean;
    FESCBOLDOFF: string;
    FESCBOLDOFF_Specified: boolean;
    FESCBOLDON: string;
    FESCBOLDON_Specified: boolean;
    FESCCPI16: string;
    FESCCPI16_Specified: boolean;
    FESCCPI20: string;
    FESCCPI20_Specified: boolean;
    FESCCUT: string;
    FESCCUT_Specified: boolean;
    FESCEOL: string;
    FESCEOL_Specified: boolean;
    FESCINITPRINT: string;
    FESCINITPRINT_Specified: boolean;
    FESCINVERSEOFF: string;
    FESCINVERSEOFF_Specified: boolean;
    FESCINVERSEON: string;
    FESCINVERSEON_Specified: boolean;
    FESCNEWLINE: string;
    FESCNEWLINE_Specified: boolean;
    FESCRESET: string;
    FESCRESET_Specified: boolean;
    FESCUNDERLINEOFF: string;
    FESCUNDERLINEOFF_Specified: boolean;
    FESCUNDERLINEON: string;
    FESCUNDERLINEON_Specified: boolean;
    FESCWIDTH2XOFF: string;
    FESCWIDTH2XOFF_Specified: boolean;
    FESCWIDTH2XON: string;
    FESCWIDTH2XON_Specified: boolean;
    FFIRMA: string;
    FFIRMA_Specified: boolean;
    FFISKALIZACIJA: TXSInteger;
    FF_POSLOVNI_PROSTOR_ID: TXSInteger;
    FF_POS_ID: TXSInteger;
    FF_STEVILKA_RACUNA_ZADNJA: Integer;
    FHIS_DESTINACIJA: Integer;
    FHIS_OBRAT: Integer;
    FHTCOLOR: string;
    FHTCOLOR_Specified: boolean;
    FHTSTYLENAME: string;
    FHTSTYLENAME_Specified: boolean;
    FKKARTICEVPLACILIH: string;
    FKKARTICEVPLACILIH_Specified: boolean;
    FKUHINJA_ID: TXSInteger;
    FMOBILE_ID: Integer;
    FMOBILE_NAZIV: string;
    FMOBILE_NAZIV_Specified: boolean;
    FMOBILE_SETUP_MIZE: ArrayOfMizaTp;
    FMOBILE_SETUP_MIZE_Specified: boolean;
    FMOBILE_SETUP_PLACILA: ArrayOfInt;
    FMOBILE_SETUP_PLACILA_Specified: boolean;
    FMOBINI: string;
    FMOBINI_Specified: boolean;
    FMOBINI2: string;
    FMOBINI2_Specified: boolean;
    FNASLOVPODJETJA: string;
    FNASLOVPODJETJA_Specified: boolean;
    FNASLOVPRODAJNEGA: string;
    FNASLOVPRODAJNEGA_Specified: boolean;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FNAZIVPODJETJA: string;
    FNAZIVPODJETJA_Specified: boolean;
    FNAZIVPRODAJNEGAMESTA: string;
    FNAZIVPRODAJNEGAMESTA_Specified: boolean;
    FNAZIVSTREGELVASJE: string;
    FNAZIVSTREGELVASJE_Specified: boolean;
    FNAZIVZAHVALA1: string;
    FNAZIVZAHVALA1_Specified: boolean;
    FNAZIVZAHVALA2: string;
    FNAZIVZAHVALA2_Specified: boolean;
    FNAZIVZAHVALA3: string;
    FNAZIVZAHVALA3_Specified: boolean;
    FNAZIVZAHVALA4: string;
    FNAZIVZAHVALA4_Specified: boolean;
    FNFCPRIJAVA: string;
    FNFCPRIJAVA_Specified: boolean;
    FOBRATPRODAJNEGAMESTA: string;
    FOBRATPRODAJNEGAMESTA_Specified: boolean;
    FOSEBE: ArrayOfOsebaTp;
    FOSEBE_Specified: boolean;
    FPOPUST99: string;
    FPOPUST99_Specified: boolean;
    FPOPUSTIZPIS: string;
    FPOPUSTIZPIS_Specified: boolean;
    FPOPUSTLOJALNOST: string;
    FPOPUSTLOJALNOST_Specified: boolean;
    FPRINTER_RACUNI: string;
    FPRINTER_RACUNI_Specified: boolean;
    FPRIORITETE_PROJEKTOV: ArrayOfPrioritetaProjektaTp;
    FPRIORITETE_PROJEKTOV_Specified: boolean;
    FSTEVILOZNAKOV: string;
    FSTEVILOZNAKOV_Specified: boolean;
    FTARIFE: ArrayOfTarifaTp;
    FTARIFE_Specified: boolean;
    FTIPKE_POS_ID: TXSInteger;
    FTOCILNICA_ID: TXSInteger;
    FVNOSPOGRINJKOV: string;
    FVNOSPOGRINJKOV_Specified: boolean;
    FZOI_LOKALNO: string;
    FZOI_LOKALNO_Specified: boolean;
    FMOBINI0: string;
    FMOBINI0_Specified: boolean;
    procedure SetCERTIFIKAT(Index: Integer; const Astring: string);
    function  CERTIFIKAT_Specified(Index: Integer): boolean;
    procedure SetCertBytes(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
    function  CertBytes_Specified(Index: Integer): boolean;
    procedure SetCertPass(Index: Integer; const Astring: string);
    function  CertPass_Specified(Index: Integer): boolean;
    procedure SetDAVCNA_ZAFURS(Index: Integer; const Astring: string);
    function  DAVCNA_ZAFURS_Specified(Index: Integer): boolean;
    procedure SetDDVPODJETJA(Index: Integer; const Astring: string);
    function  DDVPODJETJA_Specified(Index: Integer): boolean;
    procedure SetESCALIGNCENTER(Index: Integer; const Astring: string);
    function  ESCALIGNCENTER_Specified(Index: Integer): boolean;
    procedure SetESCALIGNLEFT(Index: Integer; const Astring: string);
    function  ESCALIGNLEFT_Specified(Index: Integer): boolean;
    procedure SetESCALIGNRIGHT(Index: Integer; const Astring: string);
    function  ESCALIGNRIGHT_Specified(Index: Integer): boolean;
    procedure SetESCBOLDOFF(Index: Integer; const Astring: string);
    function  ESCBOLDOFF_Specified(Index: Integer): boolean;
    procedure SetESCBOLDON(Index: Integer; const Astring: string);
    function  ESCBOLDON_Specified(Index: Integer): boolean;
    procedure SetESCCPI16(Index: Integer; const Astring: string);
    function  ESCCPI16_Specified(Index: Integer): boolean;
    procedure SetESCCPI20(Index: Integer; const Astring: string);
    function  ESCCPI20_Specified(Index: Integer): boolean;
    procedure SetESCCUT(Index: Integer; const Astring: string);
    function  ESCCUT_Specified(Index: Integer): boolean;
    procedure SetESCEOL(Index: Integer; const Astring: string);
    function  ESCEOL_Specified(Index: Integer): boolean;
    procedure SetESCINITPRINT(Index: Integer; const Astring: string);
    function  ESCINITPRINT_Specified(Index: Integer): boolean;
    procedure SetESCINVERSEOFF(Index: Integer; const Astring: string);
    function  ESCINVERSEOFF_Specified(Index: Integer): boolean;
    procedure SetESCINVERSEON(Index: Integer; const Astring: string);
    function  ESCINVERSEON_Specified(Index: Integer): boolean;
    procedure SetESCNEWLINE(Index: Integer; const Astring: string);
    function  ESCNEWLINE_Specified(Index: Integer): boolean;
    procedure SetESCRESET(Index: Integer; const Astring: string);
    function  ESCRESET_Specified(Index: Integer): boolean;
    procedure SetESCUNDERLINEOFF(Index: Integer; const Astring: string);
    function  ESCUNDERLINEOFF_Specified(Index: Integer): boolean;
    procedure SetESCUNDERLINEON(Index: Integer; const Astring: string);
    function  ESCUNDERLINEON_Specified(Index: Integer): boolean;
    procedure SetESCWIDTH2XOFF(Index: Integer; const Astring: string);
    function  ESCWIDTH2XOFF_Specified(Index: Integer): boolean;
    procedure SetESCWIDTH2XON(Index: Integer; const Astring: string);
    function  ESCWIDTH2XON_Specified(Index: Integer): boolean;
    procedure SetFIRMA(Index: Integer; const Astring: string);
    function  FIRMA_Specified(Index: Integer): boolean;
    procedure SetHTCOLOR(Index: Integer; const Astring: string);
    function  HTCOLOR_Specified(Index: Integer): boolean;
    procedure SetHTSTYLENAME(Index: Integer; const Astring: string);
    function  HTSTYLENAME_Specified(Index: Integer): boolean;
    procedure SetKKARTICEVPLACILIH(Index: Integer; const Astring: string);
    function  KKARTICEVPLACILIH_Specified(Index: Integer): boolean;
    procedure SetMOBILE_NAZIV(Index: Integer; const Astring: string);
    function  MOBILE_NAZIV_Specified(Index: Integer): boolean;
    procedure SetMOBILE_SETUP_MIZE(Index: Integer; const AArrayOfMizaTp: ArrayOfMizaTp);
    function  MOBILE_SETUP_MIZE_Specified(Index: Integer): boolean;
    procedure SetMOBILE_SETUP_PLACILA(Index: Integer; const AArrayOfInt: ArrayOfInt);
    function  MOBILE_SETUP_PLACILA_Specified(Index: Integer): boolean;
    procedure SetMOBINI(Index: Integer; const Astring: string);
    function  MOBINI_Specified(Index: Integer): boolean;
    procedure SetMOBINI2(Index: Integer; const Astring: string);
    function  MOBINI2_Specified(Index: Integer): boolean;
    procedure SetNASLOVPODJETJA(Index: Integer; const Astring: string);
    function  NASLOVPODJETJA_Specified(Index: Integer): boolean;
    procedure SetNASLOVPRODAJNEGA(Index: Integer; const Astring: string);
    function  NASLOVPRODAJNEGA_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetNAZIVPODJETJA(Index: Integer; const Astring: string);
    function  NAZIVPODJETJA_Specified(Index: Integer): boolean;
    procedure SetNAZIVPRODAJNEGAMESTA(Index: Integer; const Astring: string);
    function  NAZIVPRODAJNEGAMESTA_Specified(Index: Integer): boolean;
    procedure SetNAZIVSTREGELVASJE(Index: Integer; const Astring: string);
    function  NAZIVSTREGELVASJE_Specified(Index: Integer): boolean;
    procedure SetNAZIVZAHVALA1(Index: Integer; const Astring: string);
    function  NAZIVZAHVALA1_Specified(Index: Integer): boolean;
    procedure SetNAZIVZAHVALA2(Index: Integer; const Astring: string);
    function  NAZIVZAHVALA2_Specified(Index: Integer): boolean;
    procedure SetNAZIVZAHVALA3(Index: Integer; const Astring: string);
    function  NAZIVZAHVALA3_Specified(Index: Integer): boolean;
    procedure SetNAZIVZAHVALA4(Index: Integer; const Astring: string);
    function  NAZIVZAHVALA4_Specified(Index: Integer): boolean;
    procedure SetNFCPRIJAVA(Index: Integer; const Astring: string);
    function  NFCPRIJAVA_Specified(Index: Integer): boolean;
    procedure SetOBRATPRODAJNEGAMESTA(Index: Integer; const Astring: string);
    function  OBRATPRODAJNEGAMESTA_Specified(Index: Integer): boolean;
    procedure SetOSEBE(Index: Integer; const AArrayOfOsebaTp: ArrayOfOsebaTp);
    function  OSEBE_Specified(Index: Integer): boolean;
    procedure SetPOPUST99(Index: Integer; const Astring: string);
    function  POPUST99_Specified(Index: Integer): boolean;
    procedure SetPOPUSTIZPIS(Index: Integer; const Astring: string);
    function  POPUSTIZPIS_Specified(Index: Integer): boolean;
    procedure SetPOPUSTLOJALNOST(Index: Integer; const Astring: string);
    function  POPUSTLOJALNOST_Specified(Index: Integer): boolean;
    procedure SetPRINTER_RACUNI(Index: Integer; const Astring: string);
    function  PRINTER_RACUNI_Specified(Index: Integer): boolean;
    procedure SetPRIORITETE_PROJEKTOV(Index: Integer; const AArrayOfPrioritetaProjektaTp: ArrayOfPrioritetaProjektaTp);
    function  PRIORITETE_PROJEKTOV_Specified(Index: Integer): boolean;
    procedure SetSTEVILOZNAKOV(Index: Integer; const Astring: string);
    function  STEVILOZNAKOV_Specified(Index: Integer): boolean;
    procedure SetTARIFE(Index: Integer; const AArrayOfTarifaTp: ArrayOfTarifaTp);
    function  TARIFE_Specified(Index: Integer): boolean;
    procedure SetVNOSPOGRINJKOV(Index: Integer; const Astring: string);
    function  VNOSPOGRINJKOV_Specified(Index: Integer): boolean;
    procedure SetZOI_LOKALNO(Index: Integer; const Astring: string);
    function  ZOI_LOKALNO_Specified(Index: Integer): boolean;
    procedure SetMOBINI0(Index: Integer; const Astring: string);
    function  MOBINI0_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property CERTIFIKAT:               string                       Index (IS_OPTN) read FCERTIFIKAT write SetCERTIFIKAT stored CERTIFIKAT_Specified;
    property CertBytes:                TByteSOAPArray               Index (IS_OPTN) read FCertBytes write SetCertBytes stored CertBytes_Specified;
    property CertPass:                 string                       Index (IS_OPTN) read FCertPass write SetCertPass stored CertPass_Specified;
    property DAVCNA_ZAFURS:            string                       Index (IS_OPTN) read FDAVCNA_ZAFURS write SetDAVCNA_ZAFURS stored DAVCNA_ZAFURS_Specified;
    property DDVPODJETJA:              string                       Index (IS_OPTN) read FDDVPODJETJA write SetDDVPODJETJA stored DDVPODJETJA_Specified;
    property ESCALIGNCENTER:           string                       Index (IS_OPTN) read FESCALIGNCENTER write SetESCALIGNCENTER stored ESCALIGNCENTER_Specified;
    property ESCALIGNLEFT:             string                       Index (IS_OPTN) read FESCALIGNLEFT write SetESCALIGNLEFT stored ESCALIGNLEFT_Specified;
    property ESCALIGNRIGHT:            string                       Index (IS_OPTN) read FESCALIGNRIGHT write SetESCALIGNRIGHT stored ESCALIGNRIGHT_Specified;
    property ESCBOLDOFF:               string                       Index (IS_OPTN) read FESCBOLDOFF write SetESCBOLDOFF stored ESCBOLDOFF_Specified;
    property ESCBOLDON:                string                       Index (IS_OPTN) read FESCBOLDON write SetESCBOLDON stored ESCBOLDON_Specified;
    property ESCCPI16:                 string                       Index (IS_OPTN) read FESCCPI16 write SetESCCPI16 stored ESCCPI16_Specified;
    property ESCCPI20:                 string                       Index (IS_OPTN) read FESCCPI20 write SetESCCPI20 stored ESCCPI20_Specified;
    property ESCCUT:                   string                       Index (IS_OPTN) read FESCCUT write SetESCCUT stored ESCCUT_Specified;
    property ESCEOL:                   string                       Index (IS_OPTN) read FESCEOL write SetESCEOL stored ESCEOL_Specified;
    property ESCINITPRINT:             string                       Index (IS_OPTN) read FESCINITPRINT write SetESCINITPRINT stored ESCINITPRINT_Specified;
    property ESCINVERSEOFF:            string                       Index (IS_OPTN) read FESCINVERSEOFF write SetESCINVERSEOFF stored ESCINVERSEOFF_Specified;
    property ESCINVERSEON:             string                       Index (IS_OPTN) read FESCINVERSEON write SetESCINVERSEON stored ESCINVERSEON_Specified;
    property ESCNEWLINE:               string                       Index (IS_OPTN) read FESCNEWLINE write SetESCNEWLINE stored ESCNEWLINE_Specified;
    property ESCRESET:                 string                       Index (IS_OPTN) read FESCRESET write SetESCRESET stored ESCRESET_Specified;
    property ESCUNDERLINEOFF:          string                       Index (IS_OPTN) read FESCUNDERLINEOFF write SetESCUNDERLINEOFF stored ESCUNDERLINEOFF_Specified;
    property ESCUNDERLINEON:           string                       Index (IS_OPTN) read FESCUNDERLINEON write SetESCUNDERLINEON stored ESCUNDERLINEON_Specified;
    property ESCWIDTH2XOFF:            string                       Index (IS_OPTN) read FESCWIDTH2XOFF write SetESCWIDTH2XOFF stored ESCWIDTH2XOFF_Specified;
    property ESCWIDTH2XON:             string                       Index (IS_OPTN) read FESCWIDTH2XON write SetESCWIDTH2XON stored ESCWIDTH2XON_Specified;
    property FIRMA:                    string                       Index (IS_OPTN) read FFIRMA write SetFIRMA stored FIRMA_Specified;
    property FISKALIZACIJA:            TXSInteger                   Index (IS_NLBL) read FFISKALIZACIJA write FFISKALIZACIJA;
    property F_POSLOVNI_PROSTOR_ID:    TXSInteger                   Index (IS_NLBL) read FF_POSLOVNI_PROSTOR_ID write FF_POSLOVNI_PROSTOR_ID;
    property F_POS_ID:                 TXSInteger                   Index (IS_NLBL) read FF_POS_ID write FF_POS_ID;
    property F_STEVILKA_RACUNA_ZADNJA: Integer                      read FF_STEVILKA_RACUNA_ZADNJA write FF_STEVILKA_RACUNA_ZADNJA;
    property HIS_DESTINACIJA:          Integer                      read FHIS_DESTINACIJA write FHIS_DESTINACIJA;
    property HIS_OBRAT:                Integer                      read FHIS_OBRAT write FHIS_OBRAT;
    property HTCOLOR:                  string                       Index (IS_OPTN) read FHTCOLOR write SetHTCOLOR stored HTCOLOR_Specified;
    property HTSTYLENAME:              string                       Index (IS_OPTN) read FHTSTYLENAME write SetHTSTYLENAME stored HTSTYLENAME_Specified;
    property KKARTICEVPLACILIH:        string                       Index (IS_OPTN) read FKKARTICEVPLACILIH write SetKKARTICEVPLACILIH stored KKARTICEVPLACILIH_Specified;
    property KUHINJA_ID:               TXSInteger                   Index (IS_NLBL) read FKUHINJA_ID write FKUHINJA_ID;
    property MOBILE_ID:                Integer                      read FMOBILE_ID write FMOBILE_ID;
    property MOBILE_NAZIV:             string                       Index (IS_OPTN) read FMOBILE_NAZIV write SetMOBILE_NAZIV stored MOBILE_NAZIV_Specified;
    property MOBILE_SETUP_MIZE:        ArrayOfMizaTp                Index (IS_OPTN) read FMOBILE_SETUP_MIZE write SetMOBILE_SETUP_MIZE stored MOBILE_SETUP_MIZE_Specified;
    property MOBILE_SETUP_PLACILA:     ArrayOfInt                   Index (IS_OPTN) read FMOBILE_SETUP_PLACILA write SetMOBILE_SETUP_PLACILA stored MOBILE_SETUP_PLACILA_Specified;
    property MOBINI:                   string                       Index (IS_OPTN) read FMOBINI write SetMOBINI stored MOBINI_Specified;
    property MOBINI2:                  string                       Index (IS_OPTN) read FMOBINI2 write SetMOBINI2 stored MOBINI2_Specified;
    property NASLOVPODJETJA:           string                       Index (IS_OPTN) read FNASLOVPODJETJA write SetNASLOVPODJETJA stored NASLOVPODJETJA_Specified;
    property NASLOVPRODAJNEGA:         string                       Index (IS_OPTN) read FNASLOVPRODAJNEGA write SetNASLOVPRODAJNEGA stored NASLOVPRODAJNEGA_Specified;
    property NAZIV:                    string                       Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property NAZIVPODJETJA:            string                       Index (IS_OPTN) read FNAZIVPODJETJA write SetNAZIVPODJETJA stored NAZIVPODJETJA_Specified;
    property NAZIVPRODAJNEGAMESTA:     string                       Index (IS_OPTN) read FNAZIVPRODAJNEGAMESTA write SetNAZIVPRODAJNEGAMESTA stored NAZIVPRODAJNEGAMESTA_Specified;
    property NAZIVSTREGELVASJE:        string                       Index (IS_OPTN) read FNAZIVSTREGELVASJE write SetNAZIVSTREGELVASJE stored NAZIVSTREGELVASJE_Specified;
    property NAZIVZAHVALA1:            string                       Index (IS_OPTN) read FNAZIVZAHVALA1 write SetNAZIVZAHVALA1 stored NAZIVZAHVALA1_Specified;
    property NAZIVZAHVALA2:            string                       Index (IS_OPTN) read FNAZIVZAHVALA2 write SetNAZIVZAHVALA2 stored NAZIVZAHVALA2_Specified;
    property NAZIVZAHVALA3:            string                       Index (IS_OPTN) read FNAZIVZAHVALA3 write SetNAZIVZAHVALA3 stored NAZIVZAHVALA3_Specified;
    property NAZIVZAHVALA4:            string                       Index (IS_OPTN) read FNAZIVZAHVALA4 write SetNAZIVZAHVALA4 stored NAZIVZAHVALA4_Specified;
    property NFCPRIJAVA:               string                       Index (IS_OPTN) read FNFCPRIJAVA write SetNFCPRIJAVA stored NFCPRIJAVA_Specified;
    property OBRATPRODAJNEGAMESTA:     string                       Index (IS_OPTN) read FOBRATPRODAJNEGAMESTA write SetOBRATPRODAJNEGAMESTA stored OBRATPRODAJNEGAMESTA_Specified;
    property OSEBE:                    ArrayOfOsebaTp               Index (IS_OPTN) read FOSEBE write SetOSEBE stored OSEBE_Specified;
    property POPUST99:                 string                       Index (IS_OPTN) read FPOPUST99 write SetPOPUST99 stored POPUST99_Specified;
    property POPUSTIZPIS:              string                       Index (IS_OPTN) read FPOPUSTIZPIS write SetPOPUSTIZPIS stored POPUSTIZPIS_Specified;
    property POPUSTLOJALNOST:          string                       Index (IS_OPTN) read FPOPUSTLOJALNOST write SetPOPUSTLOJALNOST stored POPUSTLOJALNOST_Specified;
    property PRINTER_RACUNI:           string                       Index (IS_OPTN) read FPRINTER_RACUNI write SetPRINTER_RACUNI stored PRINTER_RACUNI_Specified;
    property PRIORITETE_PROJEKTOV:     ArrayOfPrioritetaProjektaTp  Index (IS_OPTN) read FPRIORITETE_PROJEKTOV write SetPRIORITETE_PROJEKTOV stored PRIORITETE_PROJEKTOV_Specified;
    property STEVILOZNAKOV:            string                       Index (IS_OPTN) read FSTEVILOZNAKOV write SetSTEVILOZNAKOV stored STEVILOZNAKOV_Specified;
    property TARIFE:                   ArrayOfTarifaTp              Index (IS_OPTN) read FTARIFE write SetTARIFE stored TARIFE_Specified;
    property TIPKE_POS_ID:             TXSInteger                   Index (IS_NLBL) read FTIPKE_POS_ID write FTIPKE_POS_ID;
    property TOCILNICA_ID:             TXSInteger                   Index (IS_NLBL) read FTOCILNICA_ID write FTOCILNICA_ID;
    property VNOSPOGRINJKOV:           string                       Index (IS_OPTN) read FVNOSPOGRINJKOV write SetVNOSPOGRINJKOV stored VNOSPOGRINJKOV_Specified;
    property ZOI_LOKALNO:              string                       Index (IS_OPTN) read FZOI_LOKALNO write SetZOI_LOKALNO stored ZOI_LOKALNO_Specified;
    property MOBINI0:                  string                       Index (IS_OPTN) read FMOBINI0 write SetMOBINI0 stored MOBINI0_Specified;
  end;



  // ************************************************************************ //
  // XML       : IzpisanTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  IzpisanTp = class(BaseDAL)
  private
    FCAS_IZPISA: TXSDateTime;
    FDATUM: TXSDateTime;
    FIZPIS_AI: Integer;
    FOSEBA_ID: Integer;
    FRACUN_ID: Integer;
    FSTATUS: Integer;
    FTOCILNICA_ID: Integer;
    FURA: TXSDateTime;
    FVSEBINA: string;
    FVSEBINA_Specified: boolean;
    FZAKLJUCEN: Integer;
    procedure SetVSEBINA(Index: Integer; const Astring: string);
    function  VSEBINA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property CAS_IZPISA:   TXSDateTime  read FCAS_IZPISA write FCAS_IZPISA;
    property DATUM:        TXSDateTime  read FDATUM write FDATUM;
    property IZPIS_AI:     Integer      read FIZPIS_AI write FIZPIS_AI;
    property OSEBA_ID:     Integer      read FOSEBA_ID write FOSEBA_ID;
    property RACUN_ID:     Integer      read FRACUN_ID write FRACUN_ID;
    property STATUS:       Integer      read FSTATUS write FSTATUS;
    property TOCILNICA_ID: Integer      read FTOCILNICA_ID write FTOCILNICA_ID;
    property URA:          TXSDateTime  read FURA write FURA;
    property VSEBINA:      string       Index (IS_OPTN) read FVSEBINA write SetVSEBINA stored VSEBINA_Specified;
    property ZAKLJUCEN:    Integer      read FZAKLJUCEN write FZAKLJUCEN;
  end;



  // ************************************************************************ //
  // XML       : NarediObracunRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  NarediObracunRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FVrstaObracuna: Integer;
    FStrm: Integer;
    FOsebaId: Integer;
    FReportFormat: string;
    FReportFormat_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetReportFormat(Index: Integer; const Astring: string);
    function  ReportFormat_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property VrstaObracuna: Integer              read FVrstaObracuna write FVrstaObracuna;
    property Strm:          Integer              read FStrm write FStrm;
    property OsebaId:       Integer              read FOsebaId write FOsebaId;
    property ReportFormat:  string               Index (IS_OPTN) read FReportFormat write SetReportFormat stored ReportFormat_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetRacuniRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetRacuniRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDO_RACUN_ID: TXSInteger;
    FF_POSLOVNI_PROSTOR_ID: TXSInteger;
    FF_POS_ID: TXSInteger;
    FF_STEVILKA_RACUNA: TXSInteger;
    FOD_DATUM: TXSDateTime;
    FOD_RACUN_ID: TXSInteger;
    FOSEBA_ID: TXSInteger;
    FSTATUS: TXSInteger;
    FTOCILNICA_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:         ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DO_RACUN_ID:           TXSInteger           Index (IS_NLBL) read FDO_RACUN_ID write FDO_RACUN_ID;
    property F_POSLOVNI_PROSTOR_ID: TXSInteger           Index (IS_NLBL) read FF_POSLOVNI_PROSTOR_ID write FF_POSLOVNI_PROSTOR_ID;
    property F_POS_ID:              TXSInteger           Index (IS_NLBL) read FF_POS_ID write FF_POS_ID;
    property F_STEVILKA_RACUNA:     TXSInteger           Index (IS_NLBL) read FF_STEVILKA_RACUNA write FF_STEVILKA_RACUNA;
    property OD_DATUM:              TXSDateTime          Index (IS_NLBL) read FOD_DATUM write FOD_DATUM;
    property OD_RACUN_ID:           TXSInteger           Index (IS_NLBL) read FOD_RACUN_ID write FOD_RACUN_ID;
    property OSEBA_ID:              TXSInteger           Index (IS_NLBL) read FOSEBA_ID write FOSEBA_ID;
    property STATUS:                TXSInteger           Index (IS_NLBL) read FSTATUS write FSTATUS;
    property TOCILNICA_ID:          TXSInteger           Index (IS_NLBL) read FTOCILNICA_ID write FTOCILNICA_ID;
  end;



  // ************************************************************************ //
  // XML       : SetStornoRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  SetStornoRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FRACUN_ID: Integer;
    FSTATUS: Integer;
    FSTKOPIJ: TXSInteger;
    FSTORNO_RACUN_ID: TXSInteger;
    FSTORNO_OSEBA_ID: TXSInteger;
    FSTORNO_RAZLOG_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property RACUN_ID:         Integer              read FRACUN_ID write FRACUN_ID;
    property STATUS:           Integer              read FSTATUS write FSTATUS;
    property STKOPIJ:          TXSInteger           Index (IS_NLBL) read FSTKOPIJ write FSTKOPIJ;
    property STORNO_RACUN_ID:  TXSInteger           Index (IS_NLBL) read FSTORNO_RACUN_ID write FSTORNO_RACUN_ID;
    property STORNO_OSEBA_ID:  TXSInteger           Index (IS_NLBL) read FSTORNO_OSEBA_ID write FSTORNO_OSEBA_ID;
    property STORNO_RAZLOG_ID: TXSInteger           Index (IS_NLBL) read FSTORNO_RAZLOG_ID write FSTORNO_RAZLOG_ID;
  end;



  // ************************************************************************ //
  // XML       : NacPlacMakroTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  NacPlacMakroTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FAI: Integer;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FP1: string;
    FP1_Specified: boolean;
    FP2: string;
    FP2_Specified: boolean;
    FP3: string;
    FP3_Specified: boolean;
    FP4: string;
    FP4_Specified: boolean;
    FPLACILO_ID: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetP1(Index: Integer; const Astring: string);
    function  P1_Specified(Index: Integer): boolean;
    procedure SetP2(Index: Integer; const Astring: string);
    function  P2_Specified(Index: Integer): boolean;
    procedure SetP3(Index: Integer; const Astring: string);
    function  P3_Specified(Index: Integer): boolean;
    procedure SetP4(Index: Integer; const Astring: string);
    function  P4_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property AI:            Integer              read FAI write FAI;
    property NAZIV:         string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property P1:            string               Index (IS_OPTN) read FP1 write SetP1 stored P1_Specified;
    property P2:            string               Index (IS_OPTN) read FP2 write SetP2 stored P2_Specified;
    property P3:            string               Index (IS_OPTN) read FP3 write SetP3 stored P3_Specified;
    property P4:            string               Index (IS_OPTN) read FP4 write SetP4 stored P4_Specified;
    property PLACILO_ID:    Integer              read FPLACILO_ID write FPLACILO_ID;
  end;



  // ************************************************************************ //
  // XML       : NarociloTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  NarociloTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FRACUN_ID: Integer;
    FMARKER: string;
    FMARKER_Specified: boolean;
    FIZVOR_DATUMURA: TXSDateTime;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetMARKER(Index: Integer; const Astring: string);
    function  MARKER_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:  ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property RACUN_ID:       Integer              read FRACUN_ID write FRACUN_ID;
    property MARKER:         string               Index (IS_OPTN) read FMARKER write SetMARKER stored MARKER_Specified;
    property IZVOR_DATUMURA: TXSDateTime          read FIZVOR_DATUMURA write FIZVOR_DATUMURA;
  end;



  // ************************************************************************ //
  // XML       : ObrokTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ObrokTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FIMEGOSTA: string;
    FIMEGOSTA_Specified: boolean;
    FKOLICINA_Z: Integer;
    FKOLICINA_K: Integer;
    FKOLICINA_V: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetIMEGOSTA(Index: Integer; const Astring: string);
    function  IMEGOSTA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property IMEGOSTA:      string               Index (IS_OPTN) read FIMEGOSTA write SetIMEGOSTA stored IMEGOSTA_Specified;
    property KOLICINA_Z:    Integer              read FKOLICINA_Z write FKOLICINA_Z;
    property KOLICINA_K:    Integer              read FKOLICINA_K write FKOLICINA_K;
    property KOLICINA_V:    Integer              read FKOLICINA_V write FKOLICINA_V;
  end;



  // ************************************************************************ //
  // XML       : GetObrokiRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetObrokiRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOBRAT_ID: Integer;
    FPROSTOR_ID: Integer;
    FDATUM: TXSDateTime;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OBRAT_ID:      Integer              read FOBRAT_ID write FOBRAT_ID;
    property PROSTOR_ID:    Integer              read FPROSTOR_ID write FPROSTOR_ID;
    property DATUM:         TXSDateTime          read FDATUM write FDATUM;
  end;



  // ************************************************************************ //
  // XML       : KartprijTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KartprijTp = class(BaseDAL)
  private
    FIMEGOSTA: string;
    FIMEGOSTA_Specified: boolean;
    FNAZIVSTORITVE: string;
    FNAZIVSTORITVE_Specified: boolean;
    FNAZIV_STRM: string;
    FNAZIV_STRM_Specified: boolean;
    FOBRAT_ID: Integer;
    FPRIJAVA_ID: Integer;
    FPROSTOR_ID: Integer;
    FTIP_KART: string;
    FTIP_KART_Specified: boolean;
    procedure SetIMEGOSTA(Index: Integer; const Astring: string);
    function  IMEGOSTA_Specified(Index: Integer): boolean;
    procedure SetNAZIVSTORITVE(Index: Integer; const Astring: string);
    function  NAZIVSTORITVE_Specified(Index: Integer): boolean;
    procedure SetNAZIV_STRM(Index: Integer; const Astring: string);
    function  NAZIV_STRM_Specified(Index: Integer): boolean;
    procedure SetTIP_KART(Index: Integer; const Astring: string);
    function  TIP_KART_Specified(Index: Integer): boolean;
  published
    property IMEGOSTA:      string   Index (IS_OPTN) read FIMEGOSTA write SetIMEGOSTA stored IMEGOSTA_Specified;
    property NAZIVSTORITVE: string   Index (IS_OPTN) read FNAZIVSTORITVE write SetNAZIVSTORITVE stored NAZIVSTORITVE_Specified;
    property NAZIV_STRM:    string   Index (IS_OPTN) read FNAZIV_STRM write SetNAZIV_STRM stored NAZIV_STRM_Specified;
    property OBRAT_ID:      Integer  read FOBRAT_ID write FOBRAT_ID;
    property PRIJAVA_ID:    Integer  read FPRIJAVA_ID write FPRIJAVA_ID;
    property PROSTOR_ID:    Integer  read FPROSTOR_ID write FPROSTOR_ID;
    property TIP_KART:      string   Index (IS_OPTN) read FTIP_KART write SetTIP_KART stored TIP_KART_Specified;
  end;



  // ************************************************************************ //
  // XML       : ProstorTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ProstorTp = class(BaseDAL)
  private
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FOBRAT_ID: Integer;
    FPROSTOR_ID: Integer;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
  published
    property NAZIV:      string   Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property OBRAT_ID:   Integer  read FOBRAT_ID write FOBRAT_ID;
    property PROSTOR_ID: Integer  read FPROSTOR_ID write FPROSTOR_ID;
  end;

  ArrayOfInt1 = array of Integer;               { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : ZdruziRacuneRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ZdruziRacuneRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FMARKER: string;
    FMARKER_Specified: boolean;
    FOSEBA_ID: Integer;
    FRacuni: ArrayOfInt1;
    FRacuni_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetMARKER(Index: Integer; const Astring: string);
    function  MARKER_Specified(Index: Integer): boolean;
    procedure SetRacuni(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
    function  Racuni_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property MARKER:        string               Index (IS_OPTN) read FMARKER write SetMARKER stored MARKER_Specified;
    property OSEBA_ID:      Integer              read FOSEBA_ID write FOSEBA_ID;
    property Racuni:        ArrayOfInt1          Index (IS_OPTN) read FRacuni write SetRacuni stored Racuni_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetKartprijRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetKartprijRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDESTINACIJA_ID: TXSInteger;
    FNajdi: string;
    FNajdi_Specified: boolean;
    FOBRATI: ArrayOfInt1;
    FOBRATI_Specified: boolean;
    FOBRAT_ID: TXSInteger;
    FDAT_ODH_X: TXSDateTime;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNajdi(Index: Integer; const Astring: string);
    function  Najdi_Specified(Index: Integer): boolean;
    procedure SetOBRATI(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
    function  OBRATI_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:  ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DESTINACIJA_ID: TXSInteger           Index (IS_NLBL) read FDESTINACIJA_ID write FDESTINACIJA_ID;
    property Najdi:          string               Index (IS_OPTN) read FNajdi write SetNajdi stored Najdi_Specified;
    property OBRATI:         ArrayOfInt1          Index (IS_OPTN) read FOBRATI write SetOBRATI stored OBRATI_Specified;
    property OBRAT_ID:       TXSInteger           Index (IS_NLBL) read FOBRAT_ID write FOBRAT_ID;
    property DAT_ODH_X:      TXSDateTime          Index (IS_NLBL) read FDAT_ODH_X write FDAT_ODH_X;
  end;



  // ************************************************************************ //
  // XML       : GetListaSkupinRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetListaSkupinRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOBRATI: ArrayOfInt1;
    FOBRATI_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetOBRATI(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
    function  OBRATI_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OBRATI:        ArrayOfInt1          Index (IS_OPTN) read FOBRATI write SetOBRATI stored OBRATI_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetProstorRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetProstorRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDESTINACIJA_ID: TXSInteger;
    FOBRATI: ArrayOfInt1;
    FOBRATI_Specified: boolean;
    FOBRAT_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetOBRATI(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
    function  OBRATI_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:  ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DESTINACIJA_ID: TXSInteger           Index (IS_NLBL) read FDESTINACIJA_ID write FDESTINACIJA_ID;
    property OBRATI:         ArrayOfInt1          Index (IS_OPTN) read FOBRATI write SetOBRATI stored OBRATI_Specified;
    property OBRAT_ID:       TXSInteger           Index (IS_NLBL) read FOBRAT_ID write FOBRAT_ID;
  end;



  // ************************************************************************ //
  // XML       : PrijavaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  PrijavaTp = class(BaseDAL)
  private
    FIMEGOSTA: string;
    FIMEGOSTA_Specified: boolean;
    FOBRAT_ID: Integer;
    FOBRAT_NAZIV: string;
    FOBRAT_NAZIV_Specified: boolean;
    FPRIJAVA_ID: Integer;
    FPROSTOR_ID: Integer;
    FNAZIV_CENIKA: string;
    FNAZIV_CENIKA_Specified: boolean;
    procedure SetIMEGOSTA(Index: Integer; const Astring: string);
    function  IMEGOSTA_Specified(Index: Integer): boolean;
    procedure SetOBRAT_NAZIV(Index: Integer; const Astring: string);
    function  OBRAT_NAZIV_Specified(Index: Integer): boolean;
    procedure SetNAZIV_CENIKA(Index: Integer; const Astring: string);
    function  NAZIV_CENIKA_Specified(Index: Integer): boolean;
  published
    property IMEGOSTA:     string   Index (IS_OPTN) read FIMEGOSTA write SetIMEGOSTA stored IMEGOSTA_Specified;
    property OBRAT_ID:     Integer  read FOBRAT_ID write FOBRAT_ID;
    property OBRAT_NAZIV:  string   Index (IS_OPTN) read FOBRAT_NAZIV write SetOBRAT_NAZIV stored OBRAT_NAZIV_Specified;
    property PRIJAVA_ID:   Integer  read FPRIJAVA_ID write FPRIJAVA_ID;
    property PROSTOR_ID:   Integer  read FPROSTOR_ID write FPROSTOR_ID;
    property NAZIV_CENIKA: string   Index (IS_OPTN) read FNAZIV_CENIKA write SetNAZIV_CENIKA stored NAZIV_CENIKA_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetPrijavaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetPrijavaRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOBRAT_ID: Integer;
    FPRIJAVA_ID: Integer;
    FPROSTOR_ID: Integer;
    FCENIK_AI: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OBRAT_ID:      Integer              read FOBRAT_ID write FOBRAT_ID;
    property PRIJAVA_ID:    Integer              read FPRIJAVA_ID write FPRIJAVA_ID;
    property PROSTOR_ID:    Integer              read FPROSTOR_ID write FPROSTOR_ID;
    property CENIK_AI:      TXSInteger           Index (IS_NLBL) read FCENIK_AI write FCENIK_AI;
  end;



  // ************************************************************************ //
  // XML       : ListaSkupinTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ListaSkupinTp = class(BaseDAL)
  private
    FOBRAT_ID: Integer;
    FSKUPINA_ID: Integer;
    FSKUPINA_NAZIV: string;
    FSKUPINA_NAZIV_Specified: boolean;
    FREZERVACIJA_ID: Integer;
    FSTORITEV_NAZIV: string;
    FSTORITEV_NAZIV_Specified: boolean;
    FSTEVILO_GOSTOV: Integer;
    FCENIK_AI: Integer;
    procedure SetSKUPINA_NAZIV(Index: Integer; const Astring: string);
    function  SKUPINA_NAZIV_Specified(Index: Integer): boolean;
    procedure SetSTORITEV_NAZIV(Index: Integer; const Astring: string);
    function  STORITEV_NAZIV_Specified(Index: Integer): boolean;
  published
    property OBRAT_ID:       Integer  read FOBRAT_ID write FOBRAT_ID;
    property SKUPINA_ID:     Integer  read FSKUPINA_ID write FSKUPINA_ID;
    property SKUPINA_NAZIV:  string   Index (IS_OPTN) read FSKUPINA_NAZIV write SetSKUPINA_NAZIV stored SKUPINA_NAZIV_Specified;
    property REZERVACIJA_ID: Integer  read FREZERVACIJA_ID write FREZERVACIJA_ID;
    property STORITEV_NAZIV: string   Index (IS_OPTN) read FSTORITEV_NAZIV write SetSTORITEV_NAZIV stored STORITEV_NAZIV_Specified;
    property STEVILO_GOSTOV: Integer  read FSTEVILO_GOSTOV write FSTEVILO_GOSTOV;
    property CENIK_AI:       Integer  read FCENIK_AI write FCENIK_AI;
  end;



  // ************************************************************************ //
  // XML       : RacunSeznamTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  RacunSeznamTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FKASIRAL: TXSInteger;
    FMARKER: string;
    FMARKER_Specified: boolean;
    FRACUN_ID: Integer;
    FSTATUS: TXSInteger;
    FSTORNO_RACUN_ID: TXSInteger;
    FZNESEK: TXSDecimal;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetMARKER(Index: Integer; const Astring: string);
    function  MARKER_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:   ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property KASIRAL:         TXSInteger           Index (IS_NLBL) read FKASIRAL write FKASIRAL;
    property MARKER:          string               Index (IS_OPTN) read FMARKER write SetMARKER stored MARKER_Specified;
    property RACUN_ID:        Integer              read FRACUN_ID write FRACUN_ID;
    property STATUS:          TXSInteger           Index (IS_NLBL) read FSTATUS write FSTATUS;
    property STORNO_RACUN_ID: TXSInteger           Index (IS_NLBL) read FSTORNO_RACUN_ID write FSTORNO_RACUN_ID;
    property ZNESEK:          TXSDecimal           Index (IS_NLBL) read FZNESEK write FZNESEK;
  end;



  // ************************************************************************ //
  // XML       : TarifaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TarifaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDAVEK_PROC: TXSDecimal;
    FMETODA_ID: TXSInteger;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FOZNAKA: string;
    FOZNAKA_Specified: boolean;
    FTARIFA_ID: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetOZNAKA(Index: Integer; const Astring: string);
    function  OZNAKA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DAVEK_PROC:    TXSDecimal           Index (IS_NLBL) read FDAVEK_PROC write FDAVEK_PROC;
    property METODA_ID:     TXSInteger           Index (IS_NLBL) read FMETODA_ID write FMETODA_ID;
    property NAZIV:         string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property OZNAKA:        string               Index (IS_OPTN) read FOZNAKA write SetOZNAKA stored OZNAKA_Specified;
    property TARIFA_ID:     Integer              read FTARIFA_ID write FTARIFA_ID;
  end;



  // ************************************************************************ //
  // XML       : InkasoOsebeTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  InkasoOsebeTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FNaziv: string;
    FNaziv_Specified: boolean;
    FOseba: string;
    FOseba_Specified: boolean;
    FOsebaId: TXSInteger;
    FZnesek: TXSDecimal;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNaziv(Index: Integer; const Astring: string);
    function  Naziv_Specified(Index: Integer): boolean;
    procedure SetOseba(Index: Integer; const Astring: string);
    function  Oseba_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property Naziv:         string               Index (IS_OPTN) read FNaziv write SetNaziv stored Naziv_Specified;
    property Oseba:         string               Index (IS_OPTN) read FOseba write SetOseba stored Oseba_Specified;
    property OsebaId:       TXSInteger           Index (IS_NLBL) read FOsebaId write FOsebaId;
    property Znesek:        TXSDecimal           read FZnesek write FZnesek;
  end;



  // ************************************************************************ //
  // XML       : PartnerTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  PartnerTp = class(BaseDAL)
  private
    FDAVCNAST: string;
    FDAVCNAST_Specified: boolean;
    FNAS_ULICA: string;
    FNAS_ULICA_Specified: boolean;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FNAZIVPOSTA: string;
    FNAZIVPOSTA_Specified: boolean;
    FPARTNER_ID: Integer;
    FRABAT: TXSDecimal;
    FSKLIC: string;
    FSKLIC_Specified: boolean;
    procedure SetDAVCNAST(Index: Integer; const Astring: string);
    function  DAVCNAST_Specified(Index: Integer): boolean;
    procedure SetNAS_ULICA(Index: Integer; const Astring: string);
    function  NAS_ULICA_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetNAZIVPOSTA(Index: Integer; const Astring: string);
    function  NAZIVPOSTA_Specified(Index: Integer): boolean;
    procedure SetSKLIC(Index: Integer; const Astring: string);
    function  SKLIC_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property DAVCNAST:   string      Index (IS_OPTN) read FDAVCNAST write SetDAVCNAST stored DAVCNAST_Specified;
    property NAS_ULICA:  string      Index (IS_OPTN) read FNAS_ULICA write SetNAS_ULICA stored NAS_ULICA_Specified;
    property NAZIV:      string      Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property NAZIVPOSTA: string      Index (IS_OPTN) read FNAZIVPOSTA write SetNAZIVPOSTA stored NAZIVPOSTA_Specified;
    property PARTNER_ID: Integer     read FPARTNER_ID write FPARTNER_ID;
    property RABAT:      TXSDecimal  read FRABAT write FRABAT;
    property SKLIC:      string      Index (IS_OPTN) read FSKLIC write SetSKLIC stored SKLIC_Specified;
  end;



  // ************************************************************************ //
  // XML       : CenikVrTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CenikVrTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FCENA1: TXSDecimal;
    FCENA2: TXSDecimal;
    FDAVEK_PROC: TXSDecimal;
    FENOTA_MERE_ID: TXSInteger;
    FENOTA_NAZIV: string;
    FENOTA_NAZIV_Specified: boolean;
    FIZPIS_NAROCILA: TXSInteger;
    FIZVOR_STRM_ID: TXSInteger;
    FNACIN_PRODAJE: TXSInteger;
    FNAZIV_ZA_RAC: string;
    FNAZIV_ZA_RAC_Specified: boolean;
    FNIVO1_ID: Integer;
    FNIVO4_ID: Integer;
    FPAKET: TXSInteger;
    FPOLNJENJE: TXSDecimal;
    FTARIFA_ID: Integer;
    FPLU_CODE: TXSInteger;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FSTANDARD: string;
    FSTANDARD_Specified: boolean;
    FBAZENITEM: string;
    FBAZENITEM_Specified: boolean;
    FIZVOR_PRIHODEK_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetENOTA_NAZIV(Index: Integer; const Astring: string);
    function  ENOTA_NAZIV_Specified(Index: Integer): boolean;
    procedure SetNAZIV_ZA_RAC(Index: Integer; const Astring: string);
    function  NAZIV_ZA_RAC_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetSTANDARD(Index: Integer; const Astring: string);
    function  STANDARD_Specified(Index: Integer): boolean;
    procedure SetBAZENITEM(Index: Integer; const Astring: string);
    function  BAZENITEM_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:     ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property CENA1:             TXSDecimal           Index (IS_NLBL) read FCENA1 write FCENA1;
    property CENA2:             TXSDecimal           Index (IS_NLBL) read FCENA2 write FCENA2;
    property DAVEK_PROC:        TXSDecimal           read FDAVEK_PROC write FDAVEK_PROC;
    property ENOTA_MERE_ID:     TXSInteger           Index (IS_NLBL) read FENOTA_MERE_ID write FENOTA_MERE_ID;
    property ENOTA_NAZIV:       string               Index (IS_OPTN) read FENOTA_NAZIV write SetENOTA_NAZIV stored ENOTA_NAZIV_Specified;
    property IZPIS_NAROCILA:    TXSInteger           Index (IS_NLBL) read FIZPIS_NAROCILA write FIZPIS_NAROCILA;
    property IZVOR_STRM_ID:     TXSInteger           Index (IS_NLBL) read FIZVOR_STRM_ID write FIZVOR_STRM_ID;
    property NACIN_PRODAJE:     TXSInteger           Index (IS_NLBL) read FNACIN_PRODAJE write FNACIN_PRODAJE;
    property NAZIV_ZA_RAC:      string               Index (IS_OPTN) read FNAZIV_ZA_RAC write SetNAZIV_ZA_RAC stored NAZIV_ZA_RAC_Specified;
    property NIVO1_ID:          Integer              read FNIVO1_ID write FNIVO1_ID;
    property NIVO4_ID:          Integer              read FNIVO4_ID write FNIVO4_ID;
    property PAKET:             TXSInteger           Index (IS_NLBL) read FPAKET write FPAKET;
    property POLNJENJE:         TXSDecimal           Index (IS_NLBL) read FPOLNJENJE write FPOLNJENJE;
    property TARIFA_ID:         Integer              read FTARIFA_ID write FTARIFA_ID;
    property PLU_CODE:          TXSInteger           Index (IS_NLBL) read FPLU_CODE write FPLU_CODE;
    property NAZIV:             string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property STANDARD:          string               Index (IS_OPTN) read FSTANDARD write SetSTANDARD stored STANDARD_Specified;
    property BAZENITEM:         string               Index (IS_OPTN) read FBAZENITEM write SetBAZENITEM stored BAZENITEM_Specified;
    property IZVOR_PRIHODEK_ID: TXSInteger           Index (IS_NLBL) read FIZVOR_PRIHODEK_ID write FIZVOR_PRIHODEK_ID;
  end;



  // ************************************************************************ //
  // XML       : CenikVrVrTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CenikVrVrTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FCENA1: TXSDecimal;
    FCENA2: TXSDecimal;
    FCENIKVRNIVO4_ID: Integer;
    FIZVOR_STRM_ID: TXSInteger;
    FKOLICINA: TXSDecimal;
    FNIVO1_ID: Integer;
    FNIVO4_ID: Integer;
    FPOPUST_DANE: TXSInteger;
    FIZVOR_PRIHODEK_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:     ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property CENA1:             TXSDecimal           Index (IS_NLBL) read FCENA1 write FCENA1;
    property CENA2:             TXSDecimal           Index (IS_NLBL) read FCENA2 write FCENA2;
    property CENIKVRNIVO4_ID:   Integer              read FCENIKVRNIVO4_ID write FCENIKVRNIVO4_ID;
    property IZVOR_STRM_ID:     TXSInteger           Index (IS_NLBL) read FIZVOR_STRM_ID write FIZVOR_STRM_ID;
    property KOLICINA:          TXSDecimal           Index (IS_NLBL) read FKOLICINA write FKOLICINA;
    property NIVO1_ID:          Integer              read FNIVO1_ID write FNIVO1_ID;
    property NIVO4_ID:          Integer              read FNIVO4_ID write FNIVO4_ID;
    property POPUST_DANE:       TXSInteger           Index (IS_NLBL) read FPOPUST_DANE write FPOPUST_DANE;
    property IZVOR_PRIHODEK_ID: TXSInteger           Index (IS_NLBL) read FIZVOR_PRIHODEK_ID write FIZVOR_PRIHODEK_ID;
  end;



  // ************************************************************************ //
  // XML       : CenikVrCeneTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CenikVrCeneTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FCENIK_ID: Integer;
    FNIVO4_ID: Integer;
    FDN_ID: string;
    FDN_ID_Specified: boolean;
    FCENA1: TXSDecimal;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetDN_ID(Index: Integer; const Astring: string);
    function  DN_ID_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property CENIK_ID:      Integer              read FCENIK_ID write FCENIK_ID;
    property NIVO4_ID:      Integer              read FNIVO4_ID write FNIVO4_ID;
    property DN_ID:         string               Index (IS_OPTN) read FDN_ID write SetDN_ID stored DN_ID_Specified;
    property CENA1:         TXSDecimal           read FCENA1 write FCENA1;
  end;

  ArrayOfCenikVrTp = array of CenikVrTp;        { "http://ros.si/R16"[GblCplx] }
  ArrayOfCenikVrVrTp = array of CenikVrVrTp;    { "http://ros.si/R16"[GblCplx] }
  ArrayOfCenikVrCeneTp = array of CenikVrCeneTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetPartnerRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetPartnerRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FIskanje: string;
    FIskanje_Specified: boolean;
    FNeIsciPoDurs: Boolean;
    FPARTNER_ID: TXSInteger;
    FTIP_PARTNER: TXSInteger;
    FSTR_MESTO_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetIskanje(Index: Integer; const Astring: string);
    function  Iskanje_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property Iskanje:       string               Index (IS_OPTN) read FIskanje write SetIskanje stored Iskanje_Specified;
    property NeIsciPoDurs:  Boolean              read FNeIsciPoDurs write FNeIsciPoDurs;
    property PARTNER_ID:    TXSInteger           Index (IS_NLBL) read FPARTNER_ID write FPARTNER_ID;
    property TIP_PARTNER:   TXSInteger           Index (IS_NLBL) read FTIP_PARTNER write FTIP_PARTNER;
    property STR_MESTO_ID:  TXSInteger           Index (IS_NLBL) read FSTR_MESTO_ID write FSTR_MESTO_ID;
  end;



  // ************************************************************************ //
  // XML       : ZamenjajLastnikaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ZamenjajLastnikaRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOsebaIdPrej: Integer;
    FOsebaIdPotem: Integer;
    FRacunId: Integer;
    FVseOdprte: Boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OsebaIdPrej:   Integer              read FOsebaIdPrej write FOsebaIdPrej;
    property OsebaIdPotem:  Integer              read FOsebaIdPotem write FOsebaIdPotem;
    property RacunId:       Integer              read FRacunId write FRacunId;
    property VseOdprte:     Boolean              read FVseOdprte write FVseOdprte;
  end;



  // ************************************************************************ //
  // XML       : RacTBonTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  RacTBonTp = class(BaseDAL)
  private
    FRACTBON_ID: Integer;
    FRACUN_ID: Integer;
    FRACPOZIC_POZICIJA_ID: Integer;
    FREFUND_ID: Integer;
    FIME: string;
    FIME_Specified: boolean;
    FPRIIMEK: string;
    FPRIIMEK_Specified: boolean;
    FEMSO: string;
    FEMSO_Specified: boolean;
    FJE_DONOR: string;
    FJE_DONOR_Specified: boolean;
    FZNESEK: TXSDecimal;
    FRECIPIENT_REFUND_ID: TXSInteger;
    FEDP_DOC_NUM: string;
    FEDP_DOC_NUM_Specified: boolean;
    FRowDeleted: Boolean;
    F__OriginalObject: RacTBonTp;
    F__OriginalObject_Specified: boolean;
    procedure SetIME(Index: Integer; const Astring: string);
    function  IME_Specified(Index: Integer): boolean;
    procedure SetPRIIMEK(Index: Integer; const Astring: string);
    function  PRIIMEK_Specified(Index: Integer): boolean;
    procedure SetEMSO(Index: Integer; const Astring: string);
    function  EMSO_Specified(Index: Integer): boolean;
    procedure SetJE_DONOR(Index: Integer; const Astring: string);
    function  JE_DONOR_Specified(Index: Integer): boolean;
    procedure SetEDP_DOC_NUM(Index: Integer; const Astring: string);
    function  EDP_DOC_NUM_Specified(Index: Integer): boolean;
    procedure Set__OriginalObject(Index: Integer; const ARacTBonTp: RacTBonTp);
    function  __OriginalObject_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property RACTBON_ID:           Integer     read FRACTBON_ID write FRACTBON_ID;
    property RACUN_ID:             Integer     read FRACUN_ID write FRACUN_ID;
    property RACPOZIC_POZICIJA_ID: Integer     read FRACPOZIC_POZICIJA_ID write FRACPOZIC_POZICIJA_ID;
    property REFUND_ID:            Integer     read FREFUND_ID write FREFUND_ID;
    property IME:                  string      Index (IS_OPTN) read FIME write SetIME stored IME_Specified;
    property PRIIMEK:              string      Index (IS_OPTN) read FPRIIMEK write SetPRIIMEK stored PRIIMEK_Specified;
    property EMSO:                 string      Index (IS_OPTN) read FEMSO write SetEMSO stored EMSO_Specified;
    property JE_DONOR:             string      Index (IS_OPTN) read FJE_DONOR write SetJE_DONOR stored JE_DONOR_Specified;
    property ZNESEK:               TXSDecimal  read FZNESEK write FZNESEK;
    property RECIPIENT_REFUND_ID:  TXSInteger  Index (IS_NLBL) read FRECIPIENT_REFUND_ID write FRECIPIENT_REFUND_ID;
    property EDP_DOC_NUM:          string      Index (IS_OPTN) read FEDP_DOC_NUM write SetEDP_DOC_NUM stored EDP_DOC_NUM_Specified;
    property RowDeleted:           Boolean     read FRowDeleted write FRowDeleted;
    property __OriginalObject:     RacTBonTp   Index (IS_OPTN) read F__OriginalObject write Set__OriginalObject stored __OriginalObject_Specified;
  end;



  // ************************************************************************ //
  // XML       : SetStornoRacunaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  SetStornoRacunaRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FRACUN_ID: Integer;
    FVERZIJA_ZAPISA: Integer;
    FOSEBA_ID: Integer;
    FSTORNO_RAZLOG_ID: TXSInteger;
    FNovoNarocilo: Boolean;
    FNovoNarocilo_UREJAMSTORNO: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:             ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property RACUN_ID:                  Integer              read FRACUN_ID write FRACUN_ID;
    property VERZIJA_ZAPISA:            Integer              read FVERZIJA_ZAPISA write FVERZIJA_ZAPISA;
    property OSEBA_ID:                  Integer              read FOSEBA_ID write FOSEBA_ID;
    property STORNO_RAZLOG_ID:          TXSInteger           Index (IS_NLBL) read FSTORNO_RAZLOG_ID write FSTORNO_RAZLOG_ID;
    property NovoNarocilo:              Boolean              read FNovoNarocilo write FNovoNarocilo;
    property NovoNarocilo_UREJAMSTORNO: TXSInteger           Index (IS_NLBL) read FNovoNarocilo_UREJAMSTORNO write FNovoNarocilo_UREJAMSTORNO;
  end;



  // ************************************************************************ //
  // XML       : PozicijaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  PozicijaTp = class(BaseDAL)
  private
    FCENA: TXSDecimal;
    FCENA_NABAVNA: TXSDecimal;
    FCENIK_ID: TXSInteger;
    FDODATNI_OPIS: string;
    FDODATNI_OPIS_Specified: boolean;
    FENOTA_PRODAJE_ID: TXSDecimal;
    FHOD: string;
    FHOD_Specified: boolean;
    FIZVOR_STRM_ID: TXSInteger;
    FKOLICINA: TXSDecimal;
    FKUHINJA_ID: TXSInteger;
    FLOJALNOST_POPUST: TXSDecimal;
    FNAROCILO_POSLANO: TXSInteger;
    FNATAKAR_ID: TXSInteger;
    FNIVO4_ID: TXSInteger;
    FPAKET_DISTINCT: TXSInteger;
    FPAKET_NIVO4_ID: TXSInteger;
    FPOS_ID: TXSInteger;
    FPOZICIJA_ID: Integer;
    FRACUN_ID: Integer;
    FRowDeleted: Boolean;
    FSTATUS: TXSDecimal;
    FSTATUS_POZ: TXSInteger;
    FSTOPNJA_DAVKA: TXSDecimal;
    FTARIFA_ID: TXSInteger;
    FTOCILNICA_ID: TXSInteger;
    FVERZIJA_ZAPISA: Integer;
    FZNESEK: TXSDecimal;
    FZNESEK_DAVKA: TXSDecimal;
    FZNESEK_LOJALNOST: TXSDecimal;
    FZNESEK_POPUST: TXSDecimal;
    F_NeNarocaj: Boolean;
    F__OriginalObject: PozicijaTp;
    F__OriginalObject_Specified: boolean;
    FPAKET_KOL: TXSDecimal;
    FCAS_ZADNJE_SPREMEMBE: TXSDateTime;
    FBON_ID: string;
    FBON_ID_Specified: boolean;
    FIZVOR_PRIHODEK_ID: TXSInteger;
    procedure SetDODATNI_OPIS(Index: Integer; const Astring: string);
    function  DODATNI_OPIS_Specified(Index: Integer): boolean;
    procedure SetHOD(Index: Integer; const Astring: string);
    function  HOD_Specified(Index: Integer): boolean;
    procedure Set__OriginalObject(Index: Integer; const APozicijaTp: PozicijaTp);
    function  __OriginalObject_Specified(Index: Integer): boolean;
    procedure SetBON_ID(Index: Integer; const Astring: string);
    function  BON_ID_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property CENA:                 TXSDecimal   Index (IS_NLBL) read FCENA write FCENA;
    property CENA_NABAVNA:         TXSDecimal   Index (IS_NLBL) read FCENA_NABAVNA write FCENA_NABAVNA;
    property CENIK_ID:             TXSInteger   Index (IS_NLBL) read FCENIK_ID write FCENIK_ID;
    property DODATNI_OPIS:         string       Index (IS_OPTN) read FDODATNI_OPIS write SetDODATNI_OPIS stored DODATNI_OPIS_Specified;
    property ENOTA_PRODAJE_ID:     TXSDecimal   Index (IS_NLBL) read FENOTA_PRODAJE_ID write FENOTA_PRODAJE_ID;
    property HOD:                  string       Index (IS_OPTN) read FHOD write SetHOD stored HOD_Specified;
    property IZVOR_STRM_ID:        TXSInteger   Index (IS_NLBL) read FIZVOR_STRM_ID write FIZVOR_STRM_ID;
    property KOLICINA:             TXSDecimal   Index (IS_NLBL) read FKOLICINA write FKOLICINA;
    property KUHINJA_ID:           TXSInteger   Index (IS_NLBL) read FKUHINJA_ID write FKUHINJA_ID;
    property LOJALNOST_POPUST:     TXSDecimal   Index (IS_NLBL) read FLOJALNOST_POPUST write FLOJALNOST_POPUST;
    property NAROCILO_POSLANO:     TXSInteger   Index (IS_NLBL) read FNAROCILO_POSLANO write FNAROCILO_POSLANO;
    property NATAKAR_ID:           TXSInteger   Index (IS_NLBL) read FNATAKAR_ID write FNATAKAR_ID;
    property NIVO4_ID:             TXSInteger   Index (IS_NLBL) read FNIVO4_ID write FNIVO4_ID;
    property PAKET_DISTINCT:       TXSInteger   Index (IS_NLBL) read FPAKET_DISTINCT write FPAKET_DISTINCT;
    property PAKET_NIVO4_ID:       TXSInteger   Index (IS_NLBL) read FPAKET_NIVO4_ID write FPAKET_NIVO4_ID;
    property POS_ID:               TXSInteger   Index (IS_NLBL) read FPOS_ID write FPOS_ID;
    property POZICIJA_ID:          Integer      read FPOZICIJA_ID write FPOZICIJA_ID;
    property RACUN_ID:             Integer      read FRACUN_ID write FRACUN_ID;
    property RowDeleted:           Boolean      read FRowDeleted write FRowDeleted;
    property STATUS:               TXSDecimal   Index (IS_NLBL) read FSTATUS write FSTATUS;
    property STATUS_POZ:           TXSInteger   Index (IS_NLBL) read FSTATUS_POZ write FSTATUS_POZ;
    property STOPNJA_DAVKA:        TXSDecimal   Index (IS_NLBL) read FSTOPNJA_DAVKA write FSTOPNJA_DAVKA;
    property TARIFA_ID:            TXSInteger   Index (IS_NLBL) read FTARIFA_ID write FTARIFA_ID;
    property TOCILNICA_ID:         TXSInteger   Index (IS_NLBL) read FTOCILNICA_ID write FTOCILNICA_ID;
    property VERZIJA_ZAPISA:       Integer      read FVERZIJA_ZAPISA write FVERZIJA_ZAPISA;
    property ZNESEK:               TXSDecimal   Index (IS_NLBL) read FZNESEK write FZNESEK;
    property ZNESEK_DAVKA:         TXSDecimal   Index (IS_NLBL) read FZNESEK_DAVKA write FZNESEK_DAVKA;
    property ZNESEK_LOJALNOST:     TXSDecimal   Index (IS_NLBL) read FZNESEK_LOJALNOST write FZNESEK_LOJALNOST;
    property ZNESEK_POPUST:        TXSDecimal   Index (IS_NLBL) read FZNESEK_POPUST write FZNESEK_POPUST;
    property _NeNarocaj:           Boolean      read F_NeNarocaj write F_NeNarocaj;
    property __OriginalObject:     PozicijaTp   Index (IS_OPTN) read F__OriginalObject write Set__OriginalObject stored __OriginalObject_Specified;
    property PAKET_KOL:            TXSDecimal   Index (IS_NLBL) read FPAKET_KOL write FPAKET_KOL;
    property CAS_ZADNJE_SPREMEMBE: TXSDateTime  Index (IS_NLBL) read FCAS_ZADNJE_SPREMEMBE write FCAS_ZADNJE_SPREMEMBE;
    property BON_ID:               string       Index (IS_OPTN) read FBON_ID write SetBON_ID stored BON_ID_Specified;
    property IZVOR_PRIHODEK_ID:    TXSInteger   Index (IS_NLBL) read FIZVOR_PRIHODEK_ID write FIZVOR_PRIHODEK_ID;
  end;



  // ************************************************************************ //
  // XML       : GetCenikRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetCenikRsTp = class(ResponseType)
  private
    FCENIKGL: CenikGlTp;
    FCENIKGL_Specified: boolean;
    procedure SetCENIKGL(Index: Integer; const ACenikGlTp: CenikGlTp);
    function  CENIKGL_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property CENIKGL: CenikGlTp  Index (IS_OPTN) read FCENIKGL write SetCENIKGL stored CENIKGL_Specified;
  end;

  ArrayOfNacPlacTp = array of NacPlacTp;        { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetNacPlacRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNacPlacRsTp = class(ResponseType)
  private
    FNACPLAC: ArrayOfNacPlacTp;
    FNACPLAC_Specified: boolean;
    procedure SetNACPLAC(Index: Integer; const AArrayOfNacPlacTp: ArrayOfNacPlacTp);
    function  NACPLAC_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property NACPLAC: ArrayOfNacPlacTp  Index (IS_OPTN) read FNACPLAC write SetNACPLAC stored NACPLAC_Specified;
  end;



  // ************************************************************************ //
  // XML       : CenikGlTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CenikGlTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FCENIKVR: ArrayOfCenikVrTp;
    FCENIKVR_Specified: boolean;
    FCENIKVRVR: ArrayOfCenikVrVrTp;
    FCENIKVRVR_Specified: boolean;
    FCENIK_ID: Integer;
    FCENIKVR_CENE: ArrayOfCenikVrCeneTp;
    FCENIKVR_CENE_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetCENIKVR(Index: Integer; const AArrayOfCenikVrTp: ArrayOfCenikVrTp);
    function  CENIKVR_Specified(Index: Integer): boolean;
    procedure SetCENIKVRVR(Index: Integer; const AArrayOfCenikVrVrTp: ArrayOfCenikVrVrTp);
    function  CENIKVRVR_Specified(Index: Integer): boolean;
    procedure SetCENIKVR_CENE(Index: Integer; const AArrayOfCenikVrCeneTp: ArrayOfCenikVrCeneTp);
    function  CENIKVR_CENE_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject   Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property CENIKVR:       ArrayOfCenikVrTp      Index (IS_OPTN) read FCENIKVR write SetCENIKVR stored CENIKVR_Specified;
    property CENIKVRVR:     ArrayOfCenikVrVrTp    Index (IS_OPTN) read FCENIKVRVR write SetCENIKVRVR stored CENIKVRVR_Specified;
    property CENIK_ID:      Integer               read FCENIK_ID write FCENIK_ID;
    property CENIKVR_CENE:  ArrayOfCenikVrCeneTp  Index (IS_OPTN) read FCENIKVR_CENE write SetCENIKVR_CENE stored CENIKVR_CENE_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetTimesRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetTimesRsTp = class(ResponseType)
  private
    FTimes: TimesTp;
    FTimes_Specified: boolean;
    procedure SetTimes(Index: Integer; const ATimesTp: TimesTp);
    function  Times_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Times: TimesTp  Index (IS_OPTN) read FTimes write SetTimes stored Times_Specified;
  end;

  ArrayOfKronologijaTp = array of KronologijaTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : KronologijaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KronologijaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDATUM_URA: TXSDateTime;
    FOBRAT_ID: Integer;
    FOPIS_OPERACIJE: string;
    FOPIS_OPERACIJE_Specified: boolean;
    FOSEBA_ID: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetOPIS_OPERACIJE(Index: Integer; const Astring: string);
    function  OPIS_OPERACIJE_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:  ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DATUM_URA:      TXSDateTime          read FDATUM_URA write FDATUM_URA;
    property OBRAT_ID:       Integer              read FOBRAT_ID write FOBRAT_ID;
    property OPIS_OPERACIJE: string               Index (IS_OPTN) read FOPIS_OPERACIJE write SetOPIS_OPERACIJE stored OPIS_OPERACIJE_Specified;
    property OSEBA_ID:       Integer              read FOSEBA_ID write FOSEBA_ID;
  end;

  ArrayOfMobileSetupsTp = array of MobileSetupsTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetMobileSetupsRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetMobileSetupsRsTp = class(ResponseType)
  private
    FMobileSetups: ArrayOfMobileSetupsTp;
    FMobileSetups_Specified: boolean;
    procedure SetMobileSetups(Index: Integer; const AArrayOfMobileSetupsTp: ArrayOfMobileSetupsTp);
    function  MobileSetups_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property MobileSetups: ArrayOfMobileSetupsTp  Index (IS_OPTN) read FMobileSetups write SetMobileSetups stored MobileSetups_Specified;
  end;



  // ************************************************************************ //
  // XML       : TimesTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TimesTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBAZA: TXSDateTime;
    FR16F: TXSDateTime;
    FR16S: TXSDateTime;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BAZA:          TXSDateTime          read FBAZA write FBAZA;
    property R16F:          TXSDateTime          read FR16F write FR16F;
    property R16S:          TXSDateTime          read FR16S write FR16S;
  end;



  // ************************************************************************ //
  // XML       : HitraTipkaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  HitraTipkaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBARVA: TXSInteger;
    FENOTA_PRODAJE: TXSDecimal;
    FKOLICINA: TXSInteger;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FNIVO4_ID: TXSInteger;
    FSKUPINA_ID: TXSInteger;
    FTIPKA_ID: Integer;
    FDODATEK_ID: TXSInteger;
    FSLIKA: TByteSOAPArray;
    FSLIKA_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetSLIKA(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
    function  SLIKA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BARVA:         TXSInteger           Index (IS_NLBL) read FBARVA write FBARVA;
    property ENOTA_PRODAJE: TXSDecimal           Index (IS_NLBL) read FENOTA_PRODAJE write FENOTA_PRODAJE;
    property KOLICINA:      TXSInteger           Index (IS_NLBL) read FKOLICINA write FKOLICINA;
    property NAZIV:         string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property NIVO4_ID:      TXSInteger           Index (IS_NLBL) read FNIVO4_ID write FNIVO4_ID;
    property SKUPINA_ID:    TXSInteger           Index (IS_NLBL) read FSKUPINA_ID write FSKUPINA_ID;
    property TIPKA_ID:      Integer              read FTIPKA_ID write FTIPKA_ID;
    property DODATEK_ID:    TXSInteger           Index (IS_NLBL) read FDODATEK_ID write FDODATEK_ID;
    property SLIKA:         TByteSOAPArray       Index (IS_OPTN) read FSLIKA write SetSLIKA stored SLIKA_Specified;
  end;

  ArrayOfHitraTipkaTp = array of HitraTipkaTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetHitreTipkeRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetHitreTipkeRsTp = class(ResponseType)
  private
    FHITRETIPKE: ArrayOfHitraTipkaTp;
    FHITRETIPKE_Specified: boolean;
    procedure SetHITRETIPKE(Index: Integer; const AArrayOfHitraTipkaTp: ArrayOfHitraTipkaTp);
    function  HITRETIPKE_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property HITRETIPKE: ArrayOfHitraTipkaTp  Index (IS_OPTN) read FHITRETIPKE write SetHITRETIPKE stored HITRETIPKE_Specified;
  end;



  // ************************************************************************ //
  // XML       : InsertKronologRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  InsertKronologRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDATUM_URA: TXSDateTime;
    FOBRAT_ID: Integer;
    FOPIS_OPERACIJE: string;
    FOPIS_OPERACIJE_Specified: boolean;
    FOSEBA_ID: Integer;
    FDanUraKasa: TXSDateTime;
    FrqId: string;
    FrqId_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetOPIS_OPERACIJE(Index: Integer; const Astring: string);
    function  OPIS_OPERACIJE_Specified(Index: Integer): boolean;
    procedure SetrqId(Index: Integer; const Astring: string);
    function  rqId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:  ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DATUM_URA:      TXSDateTime          read FDATUM_URA write FDATUM_URA;
    property OBRAT_ID:       Integer              read FOBRAT_ID write FOBRAT_ID;
    property OPIS_OPERACIJE: string               Index (IS_OPTN) read FOPIS_OPERACIJE write SetOPIS_OPERACIJE stored OPIS_OPERACIJE_Specified;
    property OSEBA_ID:       Integer              read FOSEBA_ID write FOSEBA_ID;
    property DanUraKasa:     TXSDateTime          read FDanUraKasa write FDanUraKasa;
    property rqId:           string               Index (IS_OPTN) read FrqId write SetrqId stored rqId_Specified;
  end;

  ArrayOfLong = array of Int64;                 { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : NacPlacTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  NacPlacTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FCRM: TXSInteger;
    FFISKALNO: TXSInteger;
    FF_PLACILO: string;
    FF_PLACILO_Specified: boolean;
    FINKASO: TXSInteger;
    FKUPEC_ID: TXSLong;
    FMETODA: Integer;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FPLACILO_ID: Integer;
    FSTKOPIJ: TXSInteger;
    FSTORITEV_ID: TXSInteger;
    FVRSTA: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetF_PLACILO(Index: Integer; const Astring: string);
    function  F_PLACILO_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property CRM:           TXSInteger           Index (IS_NLBL) read FCRM write FCRM;
    property FISKALNO:      TXSInteger           Index (IS_NLBL) read FFISKALNO write FFISKALNO;
    property F_PLACILO:     string               Index (IS_OPTN) read FF_PLACILO write SetF_PLACILO stored F_PLACILO_Specified;
    property INKASO:        TXSInteger           Index (IS_NLBL) read FINKASO write FINKASO;
    property KUPEC_ID:      TXSLong              Index (IS_NLBL) read FKUPEC_ID write FKUPEC_ID;
    property METODA:        Integer              read FMETODA write FMETODA;
    property NAZIV:         string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property PLACILO_ID:    Integer              read FPLACILO_ID write FPLACILO_ID;
    property STKOPIJ:       TXSInteger           Index (IS_NLBL) read FSTKOPIJ write FSTKOPIJ;
    property STORITEV_ID:   TXSInteger           Index (IS_NLBL) read FSTORITEV_ID write FSTORITEV_ID;
    property VRSTA:         Integer              read FVRSTA write FVRSTA;
  end;



  // ************************************************************************ //
  // XML       : RacunTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  RacunTp = class(BaseDAL)
  private
    FA_TOCKE: TXSDecimal;
    FBLAG_ID: TXSInteger;
    FCRM_ID: string;
    FCRM_ID_Specified: boolean;
    FCRM_POPUST: TXSDecimal;
    FCRM_ST_KARTICE: string;
    FCRM_ST_KARTICE_Specified: boolean;
    FDATUM: TXSDateTime;
    FDN_ID: string;
    FDN_ID_Specified: boolean;
    FFISKALIZACIJA: TXSInteger;
    FFISKALNI_RACUN_ID: TXSInteger;
    FF_OZNAKA_DU: string;
    FF_OZNAKA_DU_Specified: boolean;
    FF_PODPIS: string;
    FF_PODPIS_Specified: boolean;
    FF_POSLOVNI_PROSTOR_ID: TXSInteger;
    FF_POS_ID: TXSInteger;
    FF_PRINT_KODA: string;
    FF_PRINT_KODA_Specified: boolean;
    FF_PRINT_VRSTA: string;
    FF_PRINT_VRSTA_Specified: boolean;
    FF_STEVILKA_RACUNA: TXSInteger;
    FKASIRAL: TXSInteger;
    FLOJALNOST_ID: TXSInteger;
    FMARKER: string;
    FMARKER_Specified: boolean;
    FPLACANO: TXSDecimal;
    FRACPLACI: ArrayOfPlaciloTp;
    FRACPLACI_Specified: boolean;
    FRACPOZIC: ArrayOfPozicijaTp;
    FRACPOZIC_Specified: boolean;
    FRACUN_ID: Integer;
    FRowDeleted: Boolean;
    FSTATUS: TXSInteger;
    FSTKOPIJ: TXSInteger;
    FSTORNO_ORIGINAL: TXSInteger;
    FSTORNO_OSEBA_ID: TXSInteger;
    FSTORNO_RACUN_ID: TXSInteger;
    FSTPOGRINJKOV: TXSInteger;
    FTIP: TXSInteger;
    FTIP_RACUNA: TXSInteger;
    FTOCILNICA_ID: TXSInteger;
    FURA: TXSDateTime;
    FURA_PLACILA: TXSDateTime;
    FUREJAMSTORNO: TXSInteger;
    FVERZIJA_ZAPISA: Integer;
    FV_STATUS: TXSInteger;
    FZNESEK: TXSDecimal;
    F__OriginalObject: RacunTp;
    F__OriginalObject_Specified: boolean;
    FSTORNO_RAZLOG_ID: TXSInteger;
    F__Data: string;
    F__Data_Specified: boolean;
    FLOKATOR: string;
    FLOKATOR_Specified: boolean;
    FF_DATUMIZDAJE_VKR: TXSDateTime;
    FF_STEVILKA_VKR: string;
    FF_STEVILKA_VKR_Specified: boolean;
    FF_SET_VKR: string;
    FF_SET_VKR_Specified: boolean;
    FF_SERIAL_VKR: string;
    FF_SERIAL_VKR_Specified: boolean;
    FAKCIJA_ID: TXSInteger;
    FTIP_AKCIJE: TXSInteger;
    FKUPON_ID: string;
    FKUPON_ID_Specified: boolean;
    FBKARTA_ID: TXSLong;
    FRACTBON: ArrayOfRacTBonTp;
    FRACTBON_Specified: boolean;
    FOPOMBA: string;
    FOPOMBA_Specified: boolean;
    procedure SetCRM_ID(Index: Integer; const Astring: string);
    function  CRM_ID_Specified(Index: Integer): boolean;
    procedure SetCRM_ST_KARTICE(Index: Integer; const Astring: string);
    function  CRM_ST_KARTICE_Specified(Index: Integer): boolean;
    procedure SetDN_ID(Index: Integer; const Astring: string);
    function  DN_ID_Specified(Index: Integer): boolean;
    procedure SetF_OZNAKA_DU(Index: Integer; const Astring: string);
    function  F_OZNAKA_DU_Specified(Index: Integer): boolean;
    procedure SetF_PODPIS(Index: Integer; const Astring: string);
    function  F_PODPIS_Specified(Index: Integer): boolean;
    procedure SetF_PRINT_KODA(Index: Integer; const Astring: string);
    function  F_PRINT_KODA_Specified(Index: Integer): boolean;
    procedure SetF_PRINT_VRSTA(Index: Integer; const Astring: string);
    function  F_PRINT_VRSTA_Specified(Index: Integer): boolean;
    procedure SetMARKER(Index: Integer; const Astring: string);
    function  MARKER_Specified(Index: Integer): boolean;
    procedure SetRACPLACI(Index: Integer; const AArrayOfPlaciloTp: ArrayOfPlaciloTp);
    function  RACPLACI_Specified(Index: Integer): boolean;
    procedure SetRACPOZIC(Index: Integer; const AArrayOfPozicijaTp: ArrayOfPozicijaTp);
    function  RACPOZIC_Specified(Index: Integer): boolean;
    procedure Set__OriginalObject(Index: Integer; const ARacunTp: RacunTp);
    function  __OriginalObject_Specified(Index: Integer): boolean;
    procedure Set__Data(Index: Integer; const Astring: string);
    function  __Data_Specified(Index: Integer): boolean;
    procedure SetLOKATOR(Index: Integer; const Astring: string);
    function  LOKATOR_Specified(Index: Integer): boolean;
    procedure SetF_STEVILKA_VKR(Index: Integer; const Astring: string);
    function  F_STEVILKA_VKR_Specified(Index: Integer): boolean;
    procedure SetF_SET_VKR(Index: Integer; const Astring: string);
    function  F_SET_VKR_Specified(Index: Integer): boolean;
    procedure SetF_SERIAL_VKR(Index: Integer; const Astring: string);
    function  F_SERIAL_VKR_Specified(Index: Integer): boolean;
    procedure SetKUPON_ID(Index: Integer; const Astring: string);
    function  KUPON_ID_Specified(Index: Integer): boolean;
    procedure SetRACTBON(Index: Integer; const AArrayOfRacTBonTp: ArrayOfRacTBonTp);
    function  RACTBON_Specified(Index: Integer): boolean;
    procedure SetOPOMBA(Index: Integer; const Astring: string);
    function  OPOMBA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property A_TOCKE:               TXSDecimal         Index (IS_NLBL) read FA_TOCKE write FA_TOCKE;
    property BLAG_ID:               TXSInteger         Index (IS_NLBL) read FBLAG_ID write FBLAG_ID;
    property CRM_ID:                string             Index (IS_OPTN) read FCRM_ID write SetCRM_ID stored CRM_ID_Specified;
    property CRM_POPUST:            TXSDecimal         Index (IS_NLBL) read FCRM_POPUST write FCRM_POPUST;
    property CRM_ST_KARTICE:        string             Index (IS_OPTN) read FCRM_ST_KARTICE write SetCRM_ST_KARTICE stored CRM_ST_KARTICE_Specified;
    property DATUM:                 TXSDateTime        Index (IS_NLBL) read FDATUM write FDATUM;
    property DN_ID:                 string             Index (IS_OPTN) read FDN_ID write SetDN_ID stored DN_ID_Specified;
    property FISKALIZACIJA:         TXSInteger         Index (IS_NLBL) read FFISKALIZACIJA write FFISKALIZACIJA;
    property FISKALNI_RACUN_ID:     TXSInteger         Index (IS_NLBL) read FFISKALNI_RACUN_ID write FFISKALNI_RACUN_ID;
    property F_OZNAKA_DU:           string             Index (IS_OPTN) read FF_OZNAKA_DU write SetF_OZNAKA_DU stored F_OZNAKA_DU_Specified;
    property F_PODPIS:              string             Index (IS_OPTN) read FF_PODPIS write SetF_PODPIS stored F_PODPIS_Specified;
    property F_POSLOVNI_PROSTOR_ID: TXSInteger         Index (IS_NLBL) read FF_POSLOVNI_PROSTOR_ID write FF_POSLOVNI_PROSTOR_ID;
    property F_POS_ID:              TXSInteger         Index (IS_NLBL) read FF_POS_ID write FF_POS_ID;
    property F_PRINT_KODA:          string             Index (IS_OPTN) read FF_PRINT_KODA write SetF_PRINT_KODA stored F_PRINT_KODA_Specified;
    property F_PRINT_VRSTA:         string             Index (IS_OPTN) read FF_PRINT_VRSTA write SetF_PRINT_VRSTA stored F_PRINT_VRSTA_Specified;
    property F_STEVILKA_RACUNA:     TXSInteger         Index (IS_NLBL) read FF_STEVILKA_RACUNA write FF_STEVILKA_RACUNA;
    property KASIRAL:               TXSInteger         Index (IS_NLBL) read FKASIRAL write FKASIRAL;
    property LOJALNOST_ID:          TXSInteger         Index (IS_NLBL) read FLOJALNOST_ID write FLOJALNOST_ID;
    property MARKER:                string             Index (IS_OPTN) read FMARKER write SetMARKER stored MARKER_Specified;
    property PLACANO:               TXSDecimal         Index (IS_NLBL) read FPLACANO write FPLACANO;
    property RACPLACI:              ArrayOfPlaciloTp   Index (IS_OPTN) read FRACPLACI write SetRACPLACI stored RACPLACI_Specified;
    property RACPOZIC:              ArrayOfPozicijaTp  Index (IS_OPTN) read FRACPOZIC write SetRACPOZIC stored RACPOZIC_Specified;
    property RACUN_ID:              Integer            read FRACUN_ID write FRACUN_ID;
    property RowDeleted:            Boolean            read FRowDeleted write FRowDeleted;
    property STATUS:                TXSInteger         Index (IS_NLBL) read FSTATUS write FSTATUS;
    property STKOPIJ:               TXSInteger         Index (IS_NLBL) read FSTKOPIJ write FSTKOPIJ;
    property STORNO_ORIGINAL:       TXSInteger         Index (IS_NLBL) read FSTORNO_ORIGINAL write FSTORNO_ORIGINAL;
    property STORNO_OSEBA_ID:       TXSInteger         Index (IS_NLBL) read FSTORNO_OSEBA_ID write FSTORNO_OSEBA_ID;
    property STORNO_RACUN_ID:       TXSInteger         Index (IS_NLBL) read FSTORNO_RACUN_ID write FSTORNO_RACUN_ID;
    property STPOGRINJKOV:          TXSInteger         Index (IS_NLBL) read FSTPOGRINJKOV write FSTPOGRINJKOV;
    property TIP:                   TXSInteger         Index (IS_NLBL) read FTIP write FTIP;
    property TIP_RACUNA:            TXSInteger         Index (IS_NLBL) read FTIP_RACUNA write FTIP_RACUNA;
    property TOCILNICA_ID:          TXSInteger         Index (IS_NLBL) read FTOCILNICA_ID write FTOCILNICA_ID;
    property URA:                   TXSDateTime        Index (IS_NLBL) read FURA write FURA;
    property URA_PLACILA:           TXSDateTime        Index (IS_NLBL) read FURA_PLACILA write FURA_PLACILA;
    property UREJAMSTORNO:          TXSInteger         Index (IS_NLBL) read FUREJAMSTORNO write FUREJAMSTORNO;
    property VERZIJA_ZAPISA:        Integer            read FVERZIJA_ZAPISA write FVERZIJA_ZAPISA;
    property V_STATUS:              TXSInteger         Index (IS_NLBL) read FV_STATUS write FV_STATUS;
    property ZNESEK:                TXSDecimal         Index (IS_NLBL) read FZNESEK write FZNESEK;
    property __OriginalObject:      RacunTp            Index (IS_OPTN) read F__OriginalObject write Set__OriginalObject stored __OriginalObject_Specified;
    property STORNO_RAZLOG_ID:      TXSInteger         Index (IS_NLBL) read FSTORNO_RAZLOG_ID write FSTORNO_RAZLOG_ID;
    property __Data:                string             Index (IS_OPTN) read F__Data write Set__Data stored __Data_Specified;
    property LOKATOR:               string             Index (IS_OPTN) read FLOKATOR write SetLOKATOR stored LOKATOR_Specified;
    property F_DATUMIZDAJE_VKR:     TXSDateTime        Index (IS_NLBL) read FF_DATUMIZDAJE_VKR write FF_DATUMIZDAJE_VKR;
    property F_STEVILKA_VKR:        string             Index (IS_OPTN) read FF_STEVILKA_VKR write SetF_STEVILKA_VKR stored F_STEVILKA_VKR_Specified;
    property F_SET_VKR:             string             Index (IS_OPTN) read FF_SET_VKR write SetF_SET_VKR stored F_SET_VKR_Specified;
    property F_SERIAL_VKR:          string             Index (IS_OPTN) read FF_SERIAL_VKR write SetF_SERIAL_VKR stored F_SERIAL_VKR_Specified;
    property AKCIJA_ID:             TXSInteger         Index (IS_NLBL) read FAKCIJA_ID write FAKCIJA_ID;
    property TIP_AKCIJE:            TXSInteger         Index (IS_NLBL) read FTIP_AKCIJE write FTIP_AKCIJE;
    property KUPON_ID:              string             Index (IS_OPTN) read FKUPON_ID write SetKUPON_ID stored KUPON_ID_Specified;
    property BKARTA_ID:             TXSLong            Index (IS_NLBL) read FBKARTA_ID write FBKARTA_ID;
    property RACTBON:               ArrayOfRacTBonTp   Index (IS_OPTN) read FRACTBON write SetRACTBON stored RACTBON_Specified;
    property OPOMBA:                string             Index (IS_OPTN) read FOPOMBA write SetOPOMBA stored OPOMBA_Specified;
  end;



  // ************************************************************************ //
  // XML       : PlaciloTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  PlaciloTp = class(BaseDAL)
  private
    FAKCIJA_ID: TXSInteger;
    FA_LIKVIDATOR: string;
    FA_LIKVIDATOR_Specified: boolean;
    FA_STAT_NALOG: string;
    FA_STAT_NALOG_Specified: boolean;
    FA_STRM: string;
    FA_STRM_Specified: boolean;
    FA_VRSTA_STROSKA: string;
    FA_VRSTA_STROSKA_Specified: boolean;
    FBON_ID: string;
    FBON_ID_Specified: boolean;
    FDATUM: TXSDateTime;
    FDAVCNAST: string;
    FDAVCNAST_Specified: boolean;
    FDAVCNA_ST_PARTNER: TXSLong;
    FDELNI_ZNESEK: TXSDecimal;
    FGOST_PRIJAVA_ID: TXSInteger;
    FKUPEC_ID: TXSInteger;
    FKUPON_ID: string;
    FKUPON_ID_Specified: boolean;
    FLIKVIDATOR: TXSInteger;
    FM_REF: string;
    FM_REF_Specified: boolean;
    FNASLOV_PARTNER: string;
    FNASLOV_PARTNER_Specified: boolean;
    FNAZIV_PARTNER: string;
    FNAZIV_PARTNER_Specified: boolean;
    FPARTNER_ID: TXSInteger;
    FPLACILO_ID: Integer;
    FPOZICIJA_ID: Integer;
    FRACUN_ID: Integer;
    FRowDeleted: Boolean;
    FSTATUS: TXSDecimal;
    FSTAT_NALOG: TXSInteger;
    FSTR_MESTO: TXSInteger;
    FST_KARTICE: string;
    FST_KARTICE_Specified: boolean;
    FST_NAROCILNICE: TXSDecimal;
    FTECAJ: TXSDecimal;
    FTOCILNICA_ID: TXSInteger;
    FURA: TXSDateTime;
    FURA_PLACILA: TXSDateTime;
    FVALUTA_ID: TXSInteger;
    FVERZIJA_ZAPISA: Integer;
    FVRSTA_REKLAME: TXSInteger;
    FZNESEK: TXSDecimal;
    F__OriginalObject: PlaciloTp;
    F__OriginalObject_Specified: boolean;
    FHIS_CENIK_AI: TXSInteger;
    FSTNAROC: string;
    FSTNAROC_Specified: boolean;
    FNAPITNINA: TXSDecimal;
    procedure SetA_LIKVIDATOR(Index: Integer; const Astring: string);
    function  A_LIKVIDATOR_Specified(Index: Integer): boolean;
    procedure SetA_STAT_NALOG(Index: Integer; const Astring: string);
    function  A_STAT_NALOG_Specified(Index: Integer): boolean;
    procedure SetA_STRM(Index: Integer; const Astring: string);
    function  A_STRM_Specified(Index: Integer): boolean;
    procedure SetA_VRSTA_STROSKA(Index: Integer; const Astring: string);
    function  A_VRSTA_STROSKA_Specified(Index: Integer): boolean;
    procedure SetBON_ID(Index: Integer; const Astring: string);
    function  BON_ID_Specified(Index: Integer): boolean;
    procedure SetDAVCNAST(Index: Integer; const Astring: string);
    function  DAVCNAST_Specified(Index: Integer): boolean;
    procedure SetKUPON_ID(Index: Integer; const Astring: string);
    function  KUPON_ID_Specified(Index: Integer): boolean;
    procedure SetM_REF(Index: Integer; const Astring: string);
    function  M_REF_Specified(Index: Integer): boolean;
    procedure SetNASLOV_PARTNER(Index: Integer; const Astring: string);
    function  NASLOV_PARTNER_Specified(Index: Integer): boolean;
    procedure SetNAZIV_PARTNER(Index: Integer; const Astring: string);
    function  NAZIV_PARTNER_Specified(Index: Integer): boolean;
    procedure SetST_KARTICE(Index: Integer; const Astring: string);
    function  ST_KARTICE_Specified(Index: Integer): boolean;
    procedure Set__OriginalObject(Index: Integer; const APlaciloTp: PlaciloTp);
    function  __OriginalObject_Specified(Index: Integer): boolean;
    procedure SetSTNAROC(Index: Integer; const Astring: string);
    function  STNAROC_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property AKCIJA_ID:         TXSInteger   Index (IS_NLBL) read FAKCIJA_ID write FAKCIJA_ID;
    property A_LIKVIDATOR:      string       Index (IS_OPTN) read FA_LIKVIDATOR write SetA_LIKVIDATOR stored A_LIKVIDATOR_Specified;
    property A_STAT_NALOG:      string       Index (IS_OPTN) read FA_STAT_NALOG write SetA_STAT_NALOG stored A_STAT_NALOG_Specified;
    property A_STRM:            string       Index (IS_OPTN) read FA_STRM write SetA_STRM stored A_STRM_Specified;
    property A_VRSTA_STROSKA:   string       Index (IS_OPTN) read FA_VRSTA_STROSKA write SetA_VRSTA_STROSKA stored A_VRSTA_STROSKA_Specified;
    property BON_ID:            string       Index (IS_OPTN) read FBON_ID write SetBON_ID stored BON_ID_Specified;
    property DATUM:             TXSDateTime  Index (IS_NLBL) read FDATUM write FDATUM;
    property DAVCNAST:          string       Index (IS_OPTN) read FDAVCNAST write SetDAVCNAST stored DAVCNAST_Specified;
    property DAVCNA_ST_PARTNER: TXSLong      Index (IS_NLBL) read FDAVCNA_ST_PARTNER write FDAVCNA_ST_PARTNER;
    property DELNI_ZNESEK:      TXSDecimal   Index (IS_NLBL) read FDELNI_ZNESEK write FDELNI_ZNESEK;
    property GOST_PRIJAVA_ID:   TXSInteger   Index (IS_NLBL) read FGOST_PRIJAVA_ID write FGOST_PRIJAVA_ID;
    property KUPEC_ID:          TXSInteger   Index (IS_NLBL) read FKUPEC_ID write FKUPEC_ID;
    property KUPON_ID:          string       Index (IS_OPTN) read FKUPON_ID write SetKUPON_ID stored KUPON_ID_Specified;
    property LIKVIDATOR:        TXSInteger   Index (IS_NLBL) read FLIKVIDATOR write FLIKVIDATOR;
    property M_REF:             string       Index (IS_OPTN) read FM_REF write SetM_REF stored M_REF_Specified;
    property NASLOV_PARTNER:    string       Index (IS_OPTN) read FNASLOV_PARTNER write SetNASLOV_PARTNER stored NASLOV_PARTNER_Specified;
    property NAZIV_PARTNER:     string       Index (IS_OPTN) read FNAZIV_PARTNER write SetNAZIV_PARTNER stored NAZIV_PARTNER_Specified;
    property PARTNER_ID:        TXSInteger   Index (IS_NLBL) read FPARTNER_ID write FPARTNER_ID;
    property PLACILO_ID:        Integer      read FPLACILO_ID write FPLACILO_ID;
    property POZICIJA_ID:       Integer      read FPOZICIJA_ID write FPOZICIJA_ID;
    property RACUN_ID:          Integer      read FRACUN_ID write FRACUN_ID;
    property RowDeleted:        Boolean      read FRowDeleted write FRowDeleted;
    property STATUS:            TXSDecimal   Index (IS_NLBL) read FSTATUS write FSTATUS;
    property STAT_NALOG:        TXSInteger   Index (IS_NLBL) read FSTAT_NALOG write FSTAT_NALOG;
    property STR_MESTO:         TXSInteger   Index (IS_NLBL) read FSTR_MESTO write FSTR_MESTO;
    property ST_KARTICE:        string       Index (IS_OPTN) read FST_KARTICE write SetST_KARTICE stored ST_KARTICE_Specified;
    property ST_NAROCILNICE:    TXSDecimal   Index (IS_NLBL) read FST_NAROCILNICE write FST_NAROCILNICE;
    property TECAJ:             TXSDecimal   Index (IS_NLBL) read FTECAJ write FTECAJ;
    property TOCILNICA_ID:      TXSInteger   Index (IS_NLBL) read FTOCILNICA_ID write FTOCILNICA_ID;
    property URA:               TXSDateTime  Index (IS_NLBL) read FURA write FURA;
    property URA_PLACILA:       TXSDateTime  Index (IS_NLBL) read FURA_PLACILA write FURA_PLACILA;
    property VALUTA_ID:         TXSInteger   Index (IS_NLBL) read FVALUTA_ID write FVALUTA_ID;
    property VERZIJA_ZAPISA:    Integer      read FVERZIJA_ZAPISA write FVERZIJA_ZAPISA;
    property VRSTA_REKLAME:     TXSInteger   Index (IS_NLBL) read FVRSTA_REKLAME write FVRSTA_REKLAME;
    property ZNESEK:            TXSDecimal   Index (IS_NLBL) read FZNESEK write FZNESEK;
    property __OriginalObject:  PlaciloTp    Index (IS_OPTN) read F__OriginalObject write Set__OriginalObject stored __OriginalObject_Specified;
    property HIS_CENIK_AI:      TXSInteger   Index (IS_NLBL) read FHIS_CENIK_AI write FHIS_CENIK_AI;
    property STNAROC:           string       Index (IS_OPTN) read FSTNAROC write SetSTNAROC stored STNAROC_Specified;
    property NAPITNINA:         TXSDecimal   Index (IS_NLBL) read FNAPITNINA write FNAPITNINA;
  end;



  // ************************************************************************ //
  // XML       : LojalnostnaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  LojalnostnaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBONITETNI_RAZRED: Integer;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BONITETNI_RAZRED: Integer              read FBONITETNI_RAZRED write FBONITETNI_RAZRED;
    property NAZIV:            string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
  end;



  // ************************************************************************ //
  // XML       : RequestTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  RequestTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FrqId: string;
    FrqId_Specified: boolean;
    FrqTimestamp: TXSDateTime;
    FtxId: string;
    FtxId_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetrqId(Index: Integer; const Astring: string);
    function  rqId_Specified(Index: Integer): boolean;
    procedure SettxId(Index: Integer; const Astring: string);
    function  txId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property rqId:          string               Index (IS_OPTN) read FrqId write SetrqId stored rqId_Specified;
    property rqTimestamp:   TXSDateTime          Index (IS_NLBL) read FrqTimestamp write FrqTimestamp;
    property txId:          string               Index (IS_OPTN) read FtxId write SettxId stored txId_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsSaleRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsSaleRqTp = class(RequestTp)
  private
    FAmount: TXSDecimal;
    FPodrocje: string;
    FPodrocje_Specified: boolean;
    FRacunId: string;
    FRacunId_Specified: boolean;
    procedure SetPodrocje(Index: Integer; const Astring: string);
    function  Podrocje_Specified(Index: Integer): boolean;
    procedure SetRacunId(Index: Integer; const Astring: string);
    function  RacunId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Amount:   TXSDecimal  read FAmount write FAmount;
    property Podrocje: string      Index (IS_OPTN) read FPodrocje write SetPodrocje stored Podrocje_Specified;
    property RacunId:  string      Index (IS_OPTN) read FRacunId write SetRacunId stored RacunId_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsGetStatusRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsGetStatusRqTp = class(RequestTp)
  private
    FCacheId: Integer;
  published
    property CacheId: Integer  read FCacheId write FCacheId;
  end;



  // ************************************************************************ //
  // XML       : ResponseTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ResponseTp = class(RequestTp)
  private
    Ffault: string;
    Ffault_Specified: boolean;
    FrsId: string;
    FrsId_Specified: boolean;
    FrsTimestamp: TXSDateTime;
    procedure Setfault(Index: Integer; const Astring: string);
    function  fault_Specified(Index: Integer): boolean;
    procedure SetrsId(Index: Integer; const Astring: string);
    function  rsId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property fault:       string       Index (IS_OPTN) read Ffault write Setfault stored fault_Specified;
    property rsId:        string       Index (IS_OPTN) read FrsId write SetrsId stored rsId_Specified;
    property rsTimestamp: TXSDateTime  read FrsTimestamp write FrsTimestamp;
  end;



  // ************************************************************************ //
  // XML       : MbillsGetStatusRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsGetStatusRsTp = class(ResponseTp)
  private
    FStatus: Integer;
  published
    property Status: Integer  read FStatus write FStatus;
  end;



  // ************************************************************************ //
  // XML       : MbillsSaleRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsSaleRsTp = class(ResponseTp)
  private
    FCacheId: Integer;
    FQrVsebina: string;
    FQrVsebina_Specified: boolean;
    FTransactionId: string;
    FTransactionId_Specified: boolean;
    procedure SetQrVsebina(Index: Integer; const Astring: string);
    function  QrVsebina_Specified(Index: Integer): boolean;
    procedure SetTransactionId(Index: Integer; const Astring: string);
    function  TransactionId_Specified(Index: Integer): boolean;
  published
    property CacheId:       Integer  read FCacheId write FCacheId;
    property QrVsebina:     string   Index (IS_OPTN) read FQrVsebina write SetQrVsebina stored QrVsebina_Specified;
    property TransactionId: string   Index (IS_OPTN) read FTransactionId write SetTransactionId stored TransactionId_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsFursRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsFursRqTp = class(RequestTp)
  private
    FPodrocje: string;
    FPodrocje_Specified: boolean;
    FRacunId: string;
    FRacunId_Specified: boolean;
    procedure SetPodrocje(Index: Integer; const Astring: string);
    function  Podrocje_Specified(Index: Integer): boolean;
    procedure SetRacunId(Index: Integer; const Astring: string);
    function  RacunId_Specified(Index: Integer): boolean;
  published
    property Podrocje: string  Index (IS_OPTN) read FPodrocje write SetPodrocje stored Podrocje_Specified;
    property RacunId:  string  Index (IS_OPTN) read FRacunId write SetRacunId stored RacunId_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsVoidRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsVoidRqTp = class(RequestTp)
  private
    FAmount: TXSDecimal;
    FPodrocje: string;
    FPodrocje_Specified: boolean;
    FRacunId: string;
    FRacunId_Specified: boolean;
    FTransactionId: string;
    FTransactionId_Specified: boolean;
    procedure SetPodrocje(Index: Integer; const Astring: string);
    function  Podrocje_Specified(Index: Integer): boolean;
    procedure SetRacunId(Index: Integer; const Astring: string);
    function  RacunId_Specified(Index: Integer): boolean;
    procedure SetTransactionId(Index: Integer; const Astring: string);
    function  TransactionId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Amount:        TXSDecimal  read FAmount write FAmount;
    property Podrocje:      string      Index (IS_OPTN) read FPodrocje write SetPodrocje stored Podrocje_Specified;
    property RacunId:       string      Index (IS_OPTN) read FRacunId write SetRacunId stored RacunId_Specified;
    property TransactionId: string      Index (IS_OPTN) read FTransactionId write SetTransactionId stored TransactionId_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsVoidRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsVoidRsTp = class(ResponseTp)
  private
    FStatus: Integer;
  published
    property Status: Integer  read FStatus write FStatus;
  end;



  // ************************************************************************ //
  // XML       : ValuStartPaymentRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ValuStartPaymentRsTp = class(ResponseType)
  private
    FResultCode: Integer;
    FTokenValidity: Integer;
    FTransactionId: Int64;
    FResultDescription: string;
    FResultDescription_Specified: boolean;
    FToken: string;
    FToken_Specified: boolean;
    FTransactionReference: string;
    FTransactionReference_Specified: boolean;
    procedure SetResultDescription(Index: Integer; const Astring: string);
    function  ResultDescription_Specified(Index: Integer): boolean;
    procedure SetToken(Index: Integer; const Astring: string);
    function  Token_Specified(Index: Integer): boolean;
    procedure SetTransactionReference(Index: Integer; const Astring: string);
    function  TransactionReference_Specified(Index: Integer): boolean;
  published
    property ResultCode:           Integer  read FResultCode write FResultCode;
    property TokenValidity:        Integer  read FTokenValidity write FTokenValidity;
    property TransactionId:        Int64    read FTransactionId write FTransactionId;
    property ResultDescription:    string   Index (IS_OPTN) read FResultDescription write SetResultDescription stored ResultDescription_Specified;
    property Token:                string   Index (IS_OPTN) read FToken write SetToken stored Token_Specified;
    property TransactionReference: string   Index (IS_OPTN) read FTransactionReference write SetTransactionReference stored TransactionReference_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsFursRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsFursRsTp = class(ResponseTp)
  private
    FTransactionId: string;
    FTransactionId_Specified: boolean;
    procedure SetTransactionId(Index: Integer; const Astring: string);
    function  TransactionId_Specified(Index: Integer): boolean;
  published
    property TransactionId: string  Index (IS_OPTN) read FTransactionId write SetTransactionId stored TransactionId_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsRefundRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsRefundRqTp = class(RequestTp)
  private
    FAmount: TXSDecimal;
    FPodrocje: string;
    FPodrocje_Specified: boolean;
    FRacunId: string;
    FRacunId_Specified: boolean;
    FTransactionId: string;
    FTransactionId_Specified: boolean;
    procedure SetPodrocje(Index: Integer; const Astring: string);
    function  Podrocje_Specified(Index: Integer): boolean;
    procedure SetRacunId(Index: Integer; const Astring: string);
    function  RacunId_Specified(Index: Integer): boolean;
    procedure SetTransactionId(Index: Integer; const Astring: string);
    function  TransactionId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Amount:        TXSDecimal  read FAmount write FAmount;
    property Podrocje:      string      Index (IS_OPTN) read FPodrocje write SetPodrocje stored Podrocje_Specified;
    property RacunId:       string      Index (IS_OPTN) read FRacunId write SetRacunId stored RacunId_Specified;
    property TransactionId: string      Index (IS_OPTN) read FTransactionId write SetTransactionId stored TransactionId_Specified;
  end;



  // ************************************************************************ //
  // XML       : MbillsRefundRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MbillsRefundRsTp = class(ResponseTp)
  private
    FStatus: Integer;
  published
    property Status: Integer  read FStatus write FStatus;
  end;



  // ************************************************************************ //
  // XML       : AkcijaNazivRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaNazivRsTp = class(ResponseType)
  private
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FTIP_AKCIJE: TXSInteger;
    FTIP_NAZIV: string;
    FTIP_NAZIV_Specified: boolean;
    FPLACILO_ID: TXSInteger;
    FPARTNER_ID_ZAPLACILO: TXSLong;
    FPOPUST_PROC: TXSDecimal;
    FPOPUST_ZNESEK: TXSDecimal;
    FMIN_VREDNOST_RACUNA: TXSDecimal;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetTIP_NAZIV(Index: Integer; const Astring: string);
    function  TIP_NAZIV_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property NAZIV:                string      Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property TIP_AKCIJE:           TXSInteger  Index (IS_NLBL) read FTIP_AKCIJE write FTIP_AKCIJE;
    property TIP_NAZIV:            string      Index (IS_OPTN) read FTIP_NAZIV write SetTIP_NAZIV stored TIP_NAZIV_Specified;
    property PLACILO_ID:           TXSInteger  Index (IS_NLBL) read FPLACILO_ID write FPLACILO_ID;
    property PARTNER_ID_ZAPLACILO: TXSLong     Index (IS_NLBL) read FPARTNER_ID_ZAPLACILO write FPARTNER_ID_ZAPLACILO;
    property POPUST_PROC:          TXSDecimal  Index (IS_NLBL) read FPOPUST_PROC write FPOPUST_PROC;
    property POPUST_ZNESEK:        TXSDecimal  Index (IS_NLBL) read FPOPUST_ZNESEK write FPOPUST_ZNESEK;
    property MIN_VREDNOST_RACUNA:  TXSDecimal  Index (IS_NLBL) read FMIN_VREDNOST_RACUNA write FMIN_VREDNOST_RACUNA;
  end;



  // ************************************************************************ //
  // XML       : AkcijaSetLojalnostRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaSetLojalnostRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FAKCIJA_ID: Integer;
    FRACUN_ID: Integer;
    FVERZIJA: Integer;
    FVrniRacun: Boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property AKCIJA_ID:     Integer              read FAKCIJA_ID write FAKCIJA_ID;
    property RACUN_ID:      Integer              read FRACUN_ID write FRACUN_ID;
    property VERZIJA:       Integer              read FVERZIJA write FVERZIJA;
    property VrniRacun:     Boolean              read FVrniRacun write FVrniRacun;
  end;



  // ************************************************************************ //
  // XML       : AkcijaArtikliRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaArtikliRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FAKCIJA_ID: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property AKCIJA_ID:     Integer              read FAKCIJA_ID write FAKCIJA_ID;
  end;



  // ************************************************************************ //
  // XML       : AkcijaArtikelTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaArtikelTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FNIVO4_ID: Integer;
    FIZBIRA: TXSInteger;
    FENOTA_PRODAJE: TXSDecimal;
    FKOLICINA: TXSDecimal;
    FCENA: TXSDecimal;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property NIVO4_ID:      Integer              read FNIVO4_ID write FNIVO4_ID;
    property IZBIRA:        TXSInteger           Index (IS_NLBL) read FIZBIRA write FIZBIRA;
    property ENOTA_PRODAJE: TXSDecimal           Index (IS_NLBL) read FENOTA_PRODAJE write FENOTA_PRODAJE;
    property KOLICINA:      TXSDecimal           Index (IS_NLBL) read FKOLICINA write FKOLICINA;
    property CENA:          TXSDecimal           Index (IS_NLBL) read FCENA write FCENA;
  end;

  ArrayOfAkcijaArtikelTp = array of AkcijaArtikelTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : AkcijaArtikliRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaArtikliRsTp = class(ResponseType)
  private
    FArtikli: ArrayOfAkcijaArtikelTp;
    FArtikli_Specified: boolean;
    procedure SetArtikli(Index: Integer; const AArrayOfAkcijaArtikelTp: ArrayOfAkcijaArtikelTp);
    function  Artikli_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Artikli: ArrayOfAkcijaArtikelTp  Index (IS_OPTN) read FArtikli write SetArtikli stored Artikli_Specified;
  end;



  // ************************************************************************ //
  // XML       : AkcijaSetLojalnostRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaSetLojalnostRsTp = class(ResponseType)
  private
    FRacun: GetRacunRsTp;
    FRacun_Specified: boolean;
    procedure SetRacun(Index: Integer; const AGetRacunRsTp: GetRacunRsTp);
    function  Racun_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Racun: GetRacunRsTp  Index (IS_OPTN) read FRacun write SetRacun stored Racun_Specified;
  end;

  guid            =  type string;      { "http://microsoft.com/wsdl/types/"[GblSmpl] }


  // ************************************************************************ //
  // XML       : TransactionDetailsWs, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TransactionDetailsWs = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FacquirerNameField: string;
    FacquirerNameField_Specified: boolean;
    FamountField: string;
    FamountField_Specified: boolean;
    FcardTypeField: string;
    FcardTypeField_Specified: boolean;
    FerrorCodeField: Integer;
    FerrorDescriptionField: string;
    FerrorDescriptionField_Specified: boolean;
    FextReferenceIdField: string;
    FextReferenceIdField_Specified: boolean;
    FissuerNameField: string;
    FissuerNameField_Specified: boolean;
    FlastAccessField: TXSDateTime;
    FloyaltyMemberIdField: string;
    FloyaltyMemberIdField_Specified: boolean;
    FmPCodeField: string;
    FmPCodeField_Specified: boolean;
    FreferenceIdField: string;
    FreferenceIdField_Specified: boolean;
    FserialNumberField: Integer;
    FstatusField: Integer;
    FtIDField: Integer;
    FtaxNumberField: string;
    FtaxNumberField_Specified: boolean;
    FtransactionIdField: guid;
    FvCNField: string;
    FvCNField_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetacquirerNameField(Index: Integer; const Astring: string);
    function  acquirerNameField_Specified(Index: Integer): boolean;
    procedure SetamountField(Index: Integer; const Astring: string);
    function  amountField_Specified(Index: Integer): boolean;
    procedure SetcardTypeField(Index: Integer; const Astring: string);
    function  cardTypeField_Specified(Index: Integer): boolean;
    procedure SeterrorDescriptionField(Index: Integer; const Astring: string);
    function  errorDescriptionField_Specified(Index: Integer): boolean;
    procedure SetextReferenceIdField(Index: Integer; const Astring: string);
    function  extReferenceIdField_Specified(Index: Integer): boolean;
    procedure SetissuerNameField(Index: Integer; const Astring: string);
    function  issuerNameField_Specified(Index: Integer): boolean;
    procedure SetloyaltyMemberIdField(Index: Integer; const Astring: string);
    function  loyaltyMemberIdField_Specified(Index: Integer): boolean;
    procedure SetmPCodeField(Index: Integer; const Astring: string);
    function  mPCodeField_Specified(Index: Integer): boolean;
    procedure SetreferenceIdField(Index: Integer; const Astring: string);
    function  referenceIdField_Specified(Index: Integer): boolean;
    procedure SettaxNumberField(Index: Integer; const Astring: string);
    function  taxNumberField_Specified(Index: Integer): boolean;
    procedure SetvCNField(Index: Integer; const Astring: string);
    function  vCNField_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:         ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property acquirerNameField:     string               Index (IS_OPTN) read FacquirerNameField write SetacquirerNameField stored acquirerNameField_Specified;
    property amountField:           string               Index (IS_OPTN) read FamountField write SetamountField stored amountField_Specified;
    property cardTypeField:         string               Index (IS_OPTN) read FcardTypeField write SetcardTypeField stored cardTypeField_Specified;
    property errorCodeField:        Integer              read FerrorCodeField write FerrorCodeField;
    property errorDescriptionField: string               Index (IS_OPTN) read FerrorDescriptionField write SeterrorDescriptionField stored errorDescriptionField_Specified;
    property extReferenceIdField:   string               Index (IS_OPTN) read FextReferenceIdField write SetextReferenceIdField stored extReferenceIdField_Specified;
    property issuerNameField:       string               Index (IS_OPTN) read FissuerNameField write SetissuerNameField stored issuerNameField_Specified;
    property lastAccessField:       TXSDateTime          read FlastAccessField write FlastAccessField;
    property loyaltyMemberIdField:  string               Index (IS_OPTN) read FloyaltyMemberIdField write SetloyaltyMemberIdField stored loyaltyMemberIdField_Specified;
    property mPCodeField:           string               Index (IS_OPTN) read FmPCodeField write SetmPCodeField stored mPCodeField_Specified;
    property referenceIdField:      string               Index (IS_OPTN) read FreferenceIdField write SetreferenceIdField stored referenceIdField_Specified;
    property serialNumberField:     Integer              read FserialNumberField write FserialNumberField;
    property statusField:           Integer              read FstatusField write FstatusField;
    property tIDField:              Integer              read FtIDField write FtIDField;
    property taxNumberField:        string               Index (IS_OPTN) read FtaxNumberField write SettaxNumberField stored taxNumberField_Specified;
    property transactionIdField:    guid                 read FtransactionIdField write FtransactionIdField;
    property vCNField:              string               Index (IS_OPTN) read FvCNField write SetvCNField stored vCNField_Specified;
  end;



  // ************************************************************************ //
  // XML       : MonetaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  MonetaRsTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    Ffault: string;
    Ffault_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure Setfault(Index: Integer; const Astring: string);
    function  fault_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property fault:         string               Index (IS_OPTN) read Ffault write Setfault stored fault_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetTransactionStatusRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetTransactionStatusRsTp = class(MonetaRsTp)
  private
    FTransactionDetails: TransactionDetailsWs;
    FTransactionDetails_Specified: boolean;
    procedure SetTransactionDetails(Index: Integer; const ATransactionDetailsWs: TransactionDetailsWs);
    function  TransactionDetails_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property TransactionDetails: TransactionDetailsWs  Index (IS_OPTN) read FTransactionDetails write SetTransactionDetails stored TransactionDetails_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetTokenRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetTokenRsTp = class(MonetaRsTp)
  private
    FToken: string;
    FToken_Specified: boolean;
    FTransactionId: string;
    FTransactionId_Specified: boolean;
    FValidMinutes: Integer;
    FValidUntil: TXSDateTime;
    procedure SetToken(Index: Integer; const Astring: string);
    function  Token_Specified(Index: Integer): boolean;
    procedure SetTransactionId(Index: Integer; const Astring: string);
    function  TransactionId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Token:         string       Index (IS_OPTN) read FToken write SetToken stored Token_Specified;
    property TransactionId: string       Index (IS_OPTN) read FTransactionId write SetTransactionId stored TransactionId_Specified;
    property ValidMinutes:  Integer      read FValidMinutes write FValidMinutes;
    property ValidUntil:    TXSDateTime  read FValidUntil write FValidUntil;
  end;



  // ************************************************************************ //
  // XML       : CancelTransactionRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CancelTransactionRsTp = class(MonetaRsTp)
  private
    FStatus: Integer;
    FTransactionId: string;
    FTransactionId_Specified: boolean;
    FValidMinutes: Integer;
    FValidUntil: TXSDateTime;
    procedure SetTransactionId(Index: Integer; const Astring: string);
    function  TransactionId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Status:        Integer      read FStatus write FStatus;
    property TransactionId: string       Index (IS_OPTN) read FTransactionId write SetTransactionId stored TransactionId_Specified;
    property ValidMinutes:  Integer      read FValidMinutes write FValidMinutes;
    property ValidUntil:    TXSDateTime  read FValidUntil write FValidUntil;
  end;



  // ************************************************************************ //
  // XML       : KuponAkcijaSaldoRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KuponAkcijaSaldoRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FKuponId: string;
    FKuponId_Specified: boolean;
    FAkcijaId: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKuponId(Index: Integer; const Astring: string);
    function  KuponId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property KuponId:       string               Index (IS_OPTN) read FKuponId write SetKuponId stored KuponId_Specified;
    property AkcijaId:      Integer              read FAkcijaId write FAkcijaId;
  end;



  // ************************************************************************ //
  // XML       : KuponAkcijaKnjiziRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KuponAkcijaKnjiziRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FKuponId: string;
    FKuponId_Specified: boolean;
    FAkcijaId: Integer;
    FZnesekKoriscenja: TXSDecimal;
    FObratId: Integer;
    FZnesek: TXSDecimal;
    FOsebaId: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKuponId(Index: Integer; const Astring: string);
    function  KuponId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property KuponId:          string               Index (IS_OPTN) read FKuponId write SetKuponId stored KuponId_Specified;
    property AkcijaId:         Integer              read FAkcijaId write FAkcijaId;
    property ZnesekKoriscenja: TXSDecimal           read FZnesekKoriscenja write FZnesekKoriscenja;
    property ObratId:          Integer              read FObratId write FObratId;
    property Znesek:           TXSDecimal           read FZnesek write FZnesek;
    property OsebaId:          Integer              read FOsebaId write FOsebaId;
  end;



  // ************************************************************************ //
  // XML       : KuponKnjiziRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KuponKnjiziRsTp = class(ResponseType)
  private
  published
  end;



  // ************************************************************************ //
  // XML       : KuponAkcijaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KuponAkcijaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FAkcijaId: Integer;
    FNaziv: string;
    FNaziv_Specified: boolean;
    FVrednost: TXSDecimal;
    FPartnerId: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNaziv(Index: Integer; const Astring: string);
    function  Naziv_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property AkcijaId:      Integer              read FAkcijaId write FAkcijaId;
    property Naziv:         string               Index (IS_OPTN) read FNaziv write SetNaziv stored Naziv_Specified;
    property Vrednost:      TXSDecimal           Index (IS_NLBL) read FVrednost write FVrednost;
    property PartnerId:     TXSInteger           Index (IS_NLBL) read FPartnerId write FPartnerId;
  end;

  ArrayOfKuponAkcijaTp = array of KuponAkcijaTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetKuponAkcijaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetKuponAkcijaRsTp = class(ResponseType)
  private
    FAkcije: ArrayOfKuponAkcijaTp;
    FAkcije_Specified: boolean;
    procedure SetAkcije(Index: Integer; const AArrayOfKuponAkcijaTp: ArrayOfKuponAkcijaTp);
    function  Akcije_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Akcije: ArrayOfKuponAkcijaTp  Index (IS_OPTN) read FAkcije write SetAkcije stored Akcije_Specified;
  end;



  // ************************************************************************ //
  // XML       : TbBalanceRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TbBalanceRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOsebaId: Integer;
    FEmso: string;
    FEmso_Specified: boolean;
    FName_: string;
    FName__Specified: boolean;
    FSurname: string;
    FSurname_Specified: boolean;
    FC: string;
    FC_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetEmso(Index: Integer; const Astring: string);
    function  Emso_Specified(Index: Integer): boolean;
    procedure SetName_(Index: Integer; const Astring: string);
    function  Name__Specified(Index: Integer): boolean;
    procedure SetSurname(Index: Integer; const Astring: string);
    function  Surname_Specified(Index: Integer): boolean;
    procedure SetC(Index: Integer; const Astring: string);
    function  C_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OsebaId:       Integer              read FOsebaId write FOsebaId;
    property Emso:          string               Index (IS_OPTN) read FEmso write SetEmso stored Emso_Specified;
    property Name_:         string               Index (IS_OPTN) read FName_ write SetName_ stored Name__Specified;
    property Surname:       string               Index (IS_OPTN) read FSurname write SetSurname stored Surname_Specified;
    property C:             string               Index (IS_OPTN) read FC write SetC stored C_Specified;
  end;



  // ************************************************************************ //
  // XML       : TBonRefundTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TBonRefundTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FId: Integer;
    FFirstName: string;
    FFirstName_Specified: boolean;
    FLastName: string;
    FLastName_Specified: boolean;
    FEmso: string;
    FEmso_Specified: boolean;
    FIsDonor: Boolean;
    FRecipientId: string;
    FRecipientId_Specified: boolean;
    FAmount: TXSDecimal;
    FAccommodationFrom: TXSDateTime;
    FAccommodationTo: TXSDateTime;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetFirstName(Index: Integer; const Astring: string);
    function  FirstName_Specified(Index: Integer): boolean;
    procedure SetLastName(Index: Integer; const Astring: string);
    function  LastName_Specified(Index: Integer): boolean;
    procedure SetEmso(Index: Integer; const Astring: string);
    function  Emso_Specified(Index: Integer): boolean;
    procedure SetRecipientId(Index: Integer; const Astring: string);
    function  RecipientId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:     ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property Id:                Integer              read FId write FId;
    property FirstName:         string               Index (IS_OPTN) read FFirstName write SetFirstName stored FirstName_Specified;
    property LastName:          string               Index (IS_OPTN) read FLastName write SetLastName stored LastName_Specified;
    property Emso:              string               Index (IS_OPTN) read FEmso write SetEmso stored Emso_Specified;
    property IsDonor:           Boolean              read FIsDonor write FIsDonor;
    property RecipientId:       string               Index (IS_OPTN) read FRecipientId write SetRecipientId stored RecipientId_Specified;
    property Amount:            TXSDecimal           read FAmount write FAmount;
    property AccommodationFrom: TXSDateTime          read FAccommodationFrom write FAccommodationFrom;
    property AccommodationTo:   TXSDateTime          read FAccommodationTo write FAccommodationTo;
  end;



  // ************************************************************************ //
  // XML       : TBonRefund21Tp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TBonRefund21Tp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FId: Integer;
    FFirstName: string;
    FFirstName_Specified: boolean;
    FLastName: string;
    FLastName_Specified: boolean;
    FEmso: string;
    FEmso_Specified: boolean;
    FIsDonor: Boolean;
    FAmount: TXSDecimal;
    FRecipientId: string;
    FRecipientId_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetFirstName(Index: Integer; const Astring: string);
    function  FirstName_Specified(Index: Integer): boolean;
    procedure SetLastName(Index: Integer; const Astring: string);
    function  LastName_Specified(Index: Integer): boolean;
    procedure SetEmso(Index: Integer; const Astring: string);
    function  Emso_Specified(Index: Integer): boolean;
    procedure SetRecipientId(Index: Integer; const Astring: string);
    function  RecipientId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property Id:            Integer              read FId write FId;
    property FirstName:     string               Index (IS_OPTN) read FFirstName write SetFirstName stored FirstName_Specified;
    property LastName:      string               Index (IS_OPTN) read FLastName write SetLastName stored LastName_Specified;
    property Emso:          string               Index (IS_OPTN) read FEmso write SetEmso stored Emso_Specified;
    property IsDonor:       Boolean              read FIsDonor write FIsDonor;
    property Amount:        TXSDecimal           read FAmount write FAmount;
    property RecipientId:   string               Index (IS_OPTN) read FRecipientId write SetRecipientId stored RecipientId_Specified;
  end;



  // ************************************************************************ //
  // XML       : TbStornoRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TbStornoRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOsebaId: Integer;
    FDocumentNumber: string;
    FDocumentNumber_Specified: boolean;
    FC: string;
    FC_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetDocumentNumber(Index: Integer; const Astring: string);
    function  DocumentNumber_Specified(Index: Integer): boolean;
    procedure SetC(Index: Integer; const Astring: string);
    function  C_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:  ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OsebaId:        Integer              read FOsebaId write FOsebaId;
    property DocumentNumber: string               Index (IS_OPTN) read FDocumentNumber write SetDocumentNumber stored DocumentNumber_Specified;
    property C:              string               Index (IS_OPTN) read FC write SetC stored C_Specified;
  end;

  ArrayOfTBonRefundTp = array of TBonRefundTp;   { "http://ros.si/R16"[GblCplx] }
  ArrayOfTBonRefund21Tp = array of TBonRefund21Tp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : TBonDocumentTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  TBonDocumentTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOsebaId: Integer;
    FFRacunId: string;
    FFRacunId_Specified: boolean;
    FCorrectionForDocument: string;
    FCorrectionForDocument_Specified: boolean;
    FIsReservation: Boolean;
    FC: string;
    FC_Specified: boolean;
    FRefund: ArrayOfTBonRefundTp;
    FRefund_Specified: boolean;
    FRefund21: ArrayOfTBonRefund21Tp;
    FRefund21_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetFRacunId(Index: Integer; const Astring: string);
    function  FRacunId_Specified(Index: Integer): boolean;
    procedure SetCorrectionForDocument(Index: Integer; const Astring: string);
    function  CorrectionForDocument_Specified(Index: Integer): boolean;
    procedure SetC(Index: Integer; const Astring: string);
    function  C_Specified(Index: Integer): boolean;
    procedure SetRefund(Index: Integer; const AArrayOfTBonRefundTp: ArrayOfTBonRefundTp);
    function  Refund_Specified(Index: Integer): boolean;
    procedure SetRefund21(Index: Integer; const AArrayOfTBonRefund21Tp: ArrayOfTBonRefund21Tp);
    function  Refund21_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:         ExtensionDataObject    Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OsebaId:               Integer                read FOsebaId write FOsebaId;
    property FRacunId:              string                 Index (IS_OPTN) read FFRacunId write SetFRacunId stored FRacunId_Specified;
    property CorrectionForDocument: string                 Index (IS_OPTN) read FCorrectionForDocument write SetCorrectionForDocument stored CorrectionForDocument_Specified;
    property IsReservation:         Boolean                read FIsReservation write FIsReservation;
    property C:                     string                 Index (IS_OPTN) read FC write SetC stored C_Specified;
    property Refund:                ArrayOfTBonRefundTp    Index (IS_OPTN) read FRefund write SetRefund stored Refund_Specified;
    property Refund21:              ArrayOfTBonRefund21Tp  Index (IS_OPTN) read FRefund21 write SetRefund21 stored Refund21_Specified;
  end;



  // ************************************************************************ //
  // XML       : CrmTockeRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CrmTockeRsTp = class(ResponseType)
  private
    FKAPLJICE: TXSDecimal;
  public
    destructor Destroy; override;
  published
    property KAPLJICE: TXSDecimal  read FKAPLJICE write FKAPLJICE;
  end;



  // ************************************************************************ //
  // XML       : BonSaldoRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BonSaldoRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBonAi: Integer;
    FKoda: string;
    FKoda_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKoda(Index: Integer; const Astring: string);
    function  Koda_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BonAi:         Integer              read FBonAi write FBonAi;
    property Koda:          string               Index (IS_OPTN) read FKoda write SetKoda stored Koda_Specified;
  end;



  // ************************************************************************ //
  // XML       : BonSaldoRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BonSaldoRsTp = class(ResponseType)
  private
    FSaldo: TXSDecimal;
  public
    destructor Destroy; override;
  published
    property Saldo: TXSDecimal  Index (IS_NLBL) read FSaldo write FSaldo;
  end;



  // ************************************************************************ //
  // XML       : ValuGetPaymentStatusRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  ValuGetPaymentStatusRsTp = class(ResponseType)
  private
    FResultCode: Integer;
    FTransactionStatus: Integer;
    FCustomerMSISDN: string;
    FCustomerMSISDN_Specified: boolean;
    FResultDescription: string;
    FResultDescription_Specified: boolean;
    procedure SetCustomerMSISDN(Index: Integer; const Astring: string);
    function  CustomerMSISDN_Specified(Index: Integer): boolean;
    procedure SetResultDescription(Index: Integer; const Astring: string);
    function  ResultDescription_Specified(Index: Integer): boolean;
  published
    property ResultCode:        Integer  read FResultCode write FResultCode;
    property TransactionStatus: Integer  read FTransactionStatus write FTransactionStatus;
    property CustomerMSISDN:    string   Index (IS_OPTN) read FCustomerMSISDN write SetCustomerMSISDN stored CustomerMSISDN_Specified;
    property ResultDescription: string   Index (IS_OPTN) read FResultDescription write SetResultDescription stored ResultDescription_Specified;
  end;



  // ************************************************************************ //
  // XML       : CrmInfoRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CrmInfoRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FGOST_ID: TXSLong;
    FSTKARTICE: string;
    FSTKARTICE_Specified: boolean;
    FBAREA: TXSInteger;
    FSTR_MESTO_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetSTKARTICE(Index: Integer; const Astring: string);
    function  STKARTICE_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property GOST_ID:       TXSLong              Index (IS_NLBL) read FGOST_ID write FGOST_ID;
    property STKARTICE:     string               Index (IS_OPTN) read FSTKARTICE write SetSTKARTICE stored STKARTICE_Specified;
    property BAREA:         TXSInteger           Index (IS_NLBL) read FBAREA write FBAREA;
    property STR_MESTO_ID:  TXSInteger           Index (IS_NLBL) read FSTR_MESTO_ID write FSTR_MESTO_ID;
  end;



  // ************************************************************************ //
  // XML       : CrmInfoRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  CrmInfoRsTp = class(ResponseType)
  private
    FGOST_ID: Int64;
    FSTKARTICE: string;
    FSTKARTICE_Specified: boolean;
    FPOPUST_PROC: TXSDecimal;
    FSTANJE: TXSDecimal;
    FIME: string;
    FIME_Specified: boolean;
    FPRIIMEK: string;
    FPRIIMEK_Specified: boolean;
    FOPIS: string;
    FOPIS_Specified: boolean;
    procedure SetSTKARTICE(Index: Integer; const Astring: string);
    function  STKARTICE_Specified(Index: Integer): boolean;
    procedure SetIME(Index: Integer; const Astring: string);
    function  IME_Specified(Index: Integer): boolean;
    procedure SetPRIIMEK(Index: Integer; const Astring: string);
    function  PRIIMEK_Specified(Index: Integer): boolean;
    procedure SetOPIS(Index: Integer; const Astring: string);
    function  OPIS_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property GOST_ID:     Int64       read FGOST_ID write FGOST_ID;
    property STKARTICE:   string      Index (IS_OPTN) read FSTKARTICE write SetSTKARTICE stored STKARTICE_Specified;
    property POPUST_PROC: TXSDecimal  read FPOPUST_PROC write FPOPUST_PROC;
    property STANJE:      TXSDecimal  read FSTANJE write FSTANJE;
    property IME:         string      Index (IS_OPTN) read FIME write SetIME stored IME_Specified;
    property PRIIMEK:     string      Index (IS_OPTN) read FPRIIMEK write SetPRIIMEK stored PRIIMEK_Specified;
    property OPIS:        string      Index (IS_OPTN) read FOPIS write SetOPIS stored OPIS_Specified;
  end;



  // ************************************************************************ //
  // XML       : BonKnjiziRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BonKnjiziRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBonAi: Integer;
    FKoda: string;
    FKoda_Specified: boolean;
    FZnesek: TXSDecimal;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKoda(Index: Integer; const Astring: string);
    function  Koda_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BonAi:         Integer              read FBonAi write FBonAi;
    property Koda:          string               Index (IS_OPTN) read FKoda write SetKoda stored Koda_Specified;
    property Znesek:        TXSDecimal           read FZnesek write FZnesek;
  end;



  // ************************************************************************ //
  // XML       : KuponSaldoRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KuponSaldoRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FKuponId: string;
    FKuponId_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKuponId(Index: Integer; const Astring: string);
    function  KuponId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property KuponId:       string               Index (IS_OPTN) read FKuponId write SetKuponId stored KuponId_Specified;
  end;



  // ************************************************************************ //
  // XML       : KuponSaldoRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KuponSaldoRsTp = class(ResponseType)
  private
    FSaldo: TXSDecimal;
  public
    destructor Destroy; override;
  published
    property Saldo: TXSDecimal  Index (IS_NLBL) read FSaldo write FSaldo;
  end;



  // ************************************************************************ //
  // XML       : KuponKnjiziRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  KuponKnjiziRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FKuponId: string;
    FKuponId_Specified: boolean;
    FZnesekKoriscenja: TXSDecimal;
    FObratId: Integer;
    FRacunId: Integer;
    FPozicijaId: Integer;
    FOsebaId: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKuponId(Index: Integer; const Astring: string);
    function  KuponId_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property KuponId:          string               Index (IS_OPTN) read FKuponId write SetKuponId stored KuponId_Specified;
    property ZnesekKoriscenja: TXSDecimal           read FZnesekKoriscenja write FZnesekKoriscenja;
    property ObratId:          Integer              read FObratId write FObratId;
    property RacunId:          Integer              read FRacunId write FRacunId;
    property PozicijaId:       Integer              read FPozicijaId write FPozicijaId;
    property OsebaId:          Integer              read FOsebaId write FOsebaId;
  end;



  // ************************************************************************ //
  // XML       : BonKnjiziRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BonKnjiziRsTp = class(ResponseType)
  private
  published
  end;



  // ************************************************************************ //
  // XML       : BoniIzdajaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BoniIzdajaRqTp = class(ResponseType)
  private
    FVrednost: TXSDecimal;
    FTekst: string;
    FTekst_Specified: boolean;
    FNazivZaKoga: string;
    FNazivZaKoga_Specified: boolean;
    FPrviDanVeljavnosti: TXSDateTime;
    FZadnjiDanVeljavnosti: TXSDateTime;
    FVrsta: Integer;
    FTip: Integer;
    FReportAi: TXSInteger;
    FStrMestoId: Integer;
    FIzdajateljId: Integer;
    FPodrocje: string;
    FPodrocje_Specified: boolean;
    FRFID: string;
    FRFID_Specified: boolean;
    procedure SetTekst(Index: Integer; const Astring: string);
    function  Tekst_Specified(Index: Integer): boolean;
    procedure SetNazivZaKoga(Index: Integer; const Astring: string);
    function  NazivZaKoga_Specified(Index: Integer): boolean;
    procedure SetPodrocje(Index: Integer; const Astring: string);
    function  Podrocje_Specified(Index: Integer): boolean;
    procedure SetRFID(Index: Integer; const Astring: string);
    function  RFID_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Vrednost:             TXSDecimal   read FVrednost write FVrednost;
    property Tekst:                string       Index (IS_OPTN) read FTekst write SetTekst stored Tekst_Specified;
    property NazivZaKoga:          string       Index (IS_OPTN) read FNazivZaKoga write SetNazivZaKoga stored NazivZaKoga_Specified;
    property PrviDanVeljavnosti:   TXSDateTime  read FPrviDanVeljavnosti write FPrviDanVeljavnosti;
    property ZadnjiDanVeljavnosti: TXSDateTime  read FZadnjiDanVeljavnosti write FZadnjiDanVeljavnosti;
    property Vrsta:                Integer      read FVrsta write FVrsta;
    property Tip:                  Integer      read FTip write FTip;
    property ReportAi:             TXSInteger   Index (IS_NLBL) read FReportAi write FReportAi;
    property StrMestoId:           Integer      read FStrMestoId write FStrMestoId;
    property IzdajateljId:         Integer      read FIzdajateljId write FIzdajateljId;
    property Podrocje:             string       Index (IS_OPTN) read FPodrocje write SetPodrocje stored Podrocje_Specified;
    property RFID:                 string       Index (IS_OPTN) read FRFID write SetRFID stored RFID_Specified;
  end;



  // ************************************************************************ //
  // XML       : BoniIzdajaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BoniIzdajaRsTp = class(ResponseType)
  private
    FBonId: string;
    FBonId_Specified: boolean;
    FKoda: string;
    FKoda_Specified: boolean;
    FVKoda: string;
    FVKoda_Specified: boolean;
    FBarKoda: string;
    FBarKoda_Specified: boolean;
    FPdf: TByteSOAPArray;
    FPdf_Specified: boolean;
    procedure SetBonId(Index: Integer; const Astring: string);
    function  BonId_Specified(Index: Integer): boolean;
    procedure SetKoda(Index: Integer; const Astring: string);
    function  Koda_Specified(Index: Integer): boolean;
    procedure SetVKoda(Index: Integer; const Astring: string);
    function  VKoda_Specified(Index: Integer): boolean;
    procedure SetBarKoda(Index: Integer; const Astring: string);
    function  BarKoda_Specified(Index: Integer): boolean;
    procedure SetPdf(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
    function  Pdf_Specified(Index: Integer): boolean;
  published
    property BonId:   string          Index (IS_OPTN) read FBonId write SetBonId stored BonId_Specified;
    property Koda:    string          Index (IS_OPTN) read FKoda write SetKoda stored Koda_Specified;
    property VKoda:   string          Index (IS_OPTN) read FVKoda write SetVKoda stored VKoda_Specified;
    property BarKoda: string          Index (IS_OPTN) read FBarKoda write SetBarKoda stored BarKoda_Specified;
    property Pdf:     TByteSOAPArray  Index (IS_OPTN) read FPdf write SetPdf stored Pdf_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetReportRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetReportRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBlagId: TXSInteger;
    FFormat: string;
    FFormat_Specified: boolean;
    FReport: string;
    FReport_Specified: boolean;
    FStrmId: TXSInteger;
    FOsebaId: TXSInteger;
    FIzpisalaId: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetFormat(Index: Integer; const Astring: string);
    function  Format_Specified(Index: Integer): boolean;
    procedure SetReport(Index: Integer; const Astring: string);
    function  Report_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BlagId:        TXSInteger           Index (IS_NLBL) read FBlagId write FBlagId;
    property Format:        string               Index (IS_OPTN) read FFormat write SetFormat stored Format_Specified;
    property Report:        string               Index (IS_OPTN) read FReport write SetReport stored Report_Specified;
    property StrmId:        TXSInteger           Index (IS_NLBL) read FStrmId write FStrmId;
    property OsebaId:       TXSInteger           Index (IS_NLBL) read FOsebaId write FOsebaId;
    property IzpisalaId:    TXSInteger           Index (IS_NLBL) read FIzpisalaId write FIzpisalaId;
  end;

  ArrayOfDelovniNalogTp = array of DelovniNalogTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : DelovniNalogTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  DelovniNalogTp = class(BaseDAL)
  private
    FDN_AI: Integer;
    FDN_ID: string;
    FDN_ID_Specified: boolean;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FPARTNER_ID: TXSInteger;
    FPARTNER_NAZIV: string;
    FPARTNER_NAZIV_Specified: boolean;
    FSTR_MESTO_ID: Integer;
    FSTR_MESTO_NAZIV: string;
    FSTR_MESTO_NAZIV_Specified: boolean;
    FFIRMA: string;
    FFIRMA_Specified: boolean;
    procedure SetDN_ID(Index: Integer; const Astring: string);
    function  DN_ID_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetPARTNER_NAZIV(Index: Integer; const Astring: string);
    function  PARTNER_NAZIV_Specified(Index: Integer): boolean;
    procedure SetSTR_MESTO_NAZIV(Index: Integer; const Astring: string);
    function  STR_MESTO_NAZIV_Specified(Index: Integer): boolean;
    procedure SetFIRMA(Index: Integer; const Astring: string);
    function  FIRMA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property DN_AI:           Integer     read FDN_AI write FDN_AI;
    property DN_ID:           string      Index (IS_OPTN) read FDN_ID write SetDN_ID stored DN_ID_Specified;
    property NAZIV:           string      Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property PARTNER_ID:      TXSInteger  Index (IS_NLBL) read FPARTNER_ID write FPARTNER_ID;
    property PARTNER_NAZIV:   string      Index (IS_OPTN) read FPARTNER_NAZIV write SetPARTNER_NAZIV stored PARTNER_NAZIV_Specified;
    property STR_MESTO_ID:    Integer     read FSTR_MESTO_ID write FSTR_MESTO_ID;
    property STR_MESTO_NAZIV: string      Index (IS_OPTN) read FSTR_MESTO_NAZIV write SetSTR_MESTO_NAZIV stored STR_MESTO_NAZIV_Specified;
    property FIRMA:           string      Index (IS_OPTN) read FFIRMA write SetFIRMA stored FIRMA_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetSlipEmaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetSlipEmaRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FSTEVILKA_RACUNA: Integer;
    FPOZICIJA_ID: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:   ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property STEVILKA_RACUNA: Integer              read FSTEVILKA_RACUNA write FSTEVILKA_RACUNA;
    property POZICIJA_ID:     Integer              read FPOZICIJA_ID write FPOZICIJA_ID;
  end;



  // ************************************************************************ //
  // XML       : GetSlipEmaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetSlipEmaRsTp = class(ResponseType)
  private
    FSTEVILKA_KARTICE: string;
    FSTEVILKA_KARTICE_Specified: boolean;
    FSLIP_PRINT: string;
    FSLIP_PRINT_Specified: boolean;
    procedure SetSTEVILKA_KARTICE(Index: Integer; const Astring: string);
    function  STEVILKA_KARTICE_Specified(Index: Integer): boolean;
    procedure SetSLIP_PRINT(Index: Integer; const Astring: string);
    function  SLIP_PRINT_Specified(Index: Integer): boolean;
  published
    property STEVILKA_KARTICE: string  Index (IS_OPTN) read FSTEVILKA_KARTICE write SetSTEVILKA_KARTICE stored STEVILKA_KARTICE_Specified;
    property SLIP_PRINT:       string  Index (IS_OPTN) read FSLIP_PRINT write SetSLIP_PRINT stored SLIP_PRINT_Specified;
  end;



  // ************************************************************************ //
  // XML       : SetSlipEmaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  SetSlipEmaRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FSTEVILKA_RACUNA: Integer;
    FPOZICIJA_ID: Integer;
    FSTEVILKA_KARTICE: string;
    FSTEVILKA_KARTICE_Specified: boolean;
    FSLIP_PRINT: string;
    FSLIP_PRINT_Specified: boolean;
    FUSPELO: string;
    FUSPELO_Specified: boolean;
    FSLIP_PRINTS: string;
    FSLIP_PRINTS_Specified: boolean;
    FAVTORIZACIJA: string;
    FAVTORIZACIJA_Specified: boolean;
    FPROJEKT_ID: Integer;
    FACQTRANSREF: string;
    FACQTRANSREF_Specified: boolean;
    FSTTYPE: string;
    FSTTYPE_Specified: boolean;
    FAPPIDENTIFIER: string;
    FAPPIDENTIFIER_Specified: boolean;
    FAUTHREFERENCE: string;
    FAUTHREFERENCE_Specified: boolean;
    FAUTHNUMBER: string;
    FAUTHNUMBER_Specified: boolean;
    FCARDNUMBER: string;
    FCARDNUMBER_Specified: boolean;
    FACQREFERENCE: string;
    FACQREFERENCE_Specified: boolean;
    FSTRM_ID: TXSInteger;
    FZNESEK_SLIP: TXSDecimal;
    FZNESEK: TXSDecimal;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetSTEVILKA_KARTICE(Index: Integer; const Astring: string);
    function  STEVILKA_KARTICE_Specified(Index: Integer): boolean;
    procedure SetSLIP_PRINT(Index: Integer; const Astring: string);
    function  SLIP_PRINT_Specified(Index: Integer): boolean;
    procedure SetUSPELO(Index: Integer; const Astring: string);
    function  USPELO_Specified(Index: Integer): boolean;
    procedure SetSLIP_PRINTS(Index: Integer; const Astring: string);
    function  SLIP_PRINTS_Specified(Index: Integer): boolean;
    procedure SetAVTORIZACIJA(Index: Integer; const Astring: string);
    function  AVTORIZACIJA_Specified(Index: Integer): boolean;
    procedure SetACQTRANSREF(Index: Integer; const Astring: string);
    function  ACQTRANSREF_Specified(Index: Integer): boolean;
    procedure SetSTTYPE(Index: Integer; const Astring: string);
    function  STTYPE_Specified(Index: Integer): boolean;
    procedure SetAPPIDENTIFIER(Index: Integer; const Astring: string);
    function  APPIDENTIFIER_Specified(Index: Integer): boolean;
    procedure SetAUTHREFERENCE(Index: Integer; const Astring: string);
    function  AUTHREFERENCE_Specified(Index: Integer): boolean;
    procedure SetAUTHNUMBER(Index: Integer; const Astring: string);
    function  AUTHNUMBER_Specified(Index: Integer): boolean;
    procedure SetCARDNUMBER(Index: Integer; const Astring: string);
    function  CARDNUMBER_Specified(Index: Integer): boolean;
    procedure SetACQREFERENCE(Index: Integer; const Astring: string);
    function  ACQREFERENCE_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property STEVILKA_RACUNA:  Integer              read FSTEVILKA_RACUNA write FSTEVILKA_RACUNA;
    property POZICIJA_ID:      Integer              read FPOZICIJA_ID write FPOZICIJA_ID;
    property STEVILKA_KARTICE: string               Index (IS_OPTN) read FSTEVILKA_KARTICE write SetSTEVILKA_KARTICE stored STEVILKA_KARTICE_Specified;
    property SLIP_PRINT:       string               Index (IS_OPTN) read FSLIP_PRINT write SetSLIP_PRINT stored SLIP_PRINT_Specified;
    property USPELO:           string               Index (IS_OPTN) read FUSPELO write SetUSPELO stored USPELO_Specified;
    property SLIP_PRINTS:      string               Index (IS_OPTN) read FSLIP_PRINTS write SetSLIP_PRINTS stored SLIP_PRINTS_Specified;
    property AVTORIZACIJA:     string               Index (IS_OPTN) read FAVTORIZACIJA write SetAVTORIZACIJA stored AVTORIZACIJA_Specified;
    property PROJEKT_ID:       Integer              read FPROJEKT_ID write FPROJEKT_ID;
    property ACQTRANSREF:      string               Index (IS_OPTN) read FACQTRANSREF write SetACQTRANSREF stored ACQTRANSREF_Specified;
    property STTYPE:           string               Index (IS_OPTN) read FSTTYPE write SetSTTYPE stored STTYPE_Specified;
    property APPIDENTIFIER:    string               Index (IS_OPTN) read FAPPIDENTIFIER write SetAPPIDENTIFIER stored APPIDENTIFIER_Specified;
    property AUTHREFERENCE:    string               Index (IS_OPTN) read FAUTHREFERENCE write SetAUTHREFERENCE stored AUTHREFERENCE_Specified;
    property AUTHNUMBER:       string               Index (IS_OPTN) read FAUTHNUMBER write SetAUTHNUMBER stored AUTHNUMBER_Specified;
    property CARDNUMBER:       string               Index (IS_OPTN) read FCARDNUMBER write SetCARDNUMBER stored CARDNUMBER_Specified;
    property ACQREFERENCE:     string               Index (IS_OPTN) read FACQREFERENCE write SetACQREFERENCE stored ACQREFERENCE_Specified;
    property STRM_ID:          TXSInteger           Index (IS_NLBL) read FSTRM_ID write FSTRM_ID;
    property ZNESEK_SLIP:      TXSDecimal           Index (IS_NLBL) read FZNESEK_SLIP write FZNESEK_SLIP;
    property ZNESEK:           TXSDecimal           Index (IS_NLBL) read FZNESEK write FZNESEK;
  end;



  // ************************************************************************ //
  // XML       : GetDelovniNalogiRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetDelovniNalogiRsTp = class(ResponseType)
  private
    FNalogi: ArrayOfDelovniNalogTp;
    FNalogi_Specified: boolean;
    procedure SetNalogi(Index: Integer; const AArrayOfDelovniNalogTp: ArrayOfDelovniNalogTp);
    function  Nalogi_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Nalogi: ArrayOfDelovniNalogTp  Index (IS_OPTN) read FNalogi write SetNalogi stored Nalogi_Specified;
  end;



  // ************************************************************************ //
  // XML       : StornoRazlogTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  StornoRazlogTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FSTORNO_RAZLOG_ID: Integer;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property STORNO_RAZLOG_ID: Integer              read FSTORNO_RAZLOG_ID write FSTORNO_RAZLOG_ID;
    property NAZIV:            string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
  end;

  ArrayOfStornoRazlogTp = array of StornoRazlogTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetStornoRazlogiRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetStornoRazlogiRsTp = class(ResponseType)
  private
    FStornoRazlogi: ArrayOfStornoRazlogTp;
    FStornoRazlogi_Specified: boolean;
    procedure SetStornoRazlogi(Index: Integer; const AArrayOfStornoRazlogTp: ArrayOfStornoRazlogTp);
    function  StornoRazlogi_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property StornoRazlogi: ArrayOfStornoRazlogTp  Index (IS_OPTN) read FStornoRazlogi write SetStornoRazlogi stored StornoRazlogi_Specified;
  end;



  // ************************************************************************ //
  // XML       : RecepturaItemTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  RecepturaItemTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FKOLICINA: TXSDecimal;
    FNAZIV_ZA_RAC: string;
    FNAZIV_ZA_RAC_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV_ZA_RAC(Index: Integer; const Astring: string);
    function  NAZIV_ZA_RAC_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property KOLICINA:      TXSDecimal           Index (IS_NLBL) read FKOLICINA write FKOLICINA;
    property NAZIV_ZA_RAC:  string               Index (IS_OPTN) read FNAZIV_ZA_RAC write SetNAZIV_ZA_RAC stored NAZIV_ZA_RAC_Specified;
  end;

  ArrayOfRecepturaItemTp = array of RecepturaItemTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetRecepturaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetRecepturaRsTp = class(ResponseType)
  private
    FReceptura: ArrayOfRecepturaItemTp;
    FReceptura_Specified: boolean;
    procedure SetReceptura(Index: Integer; const AArrayOfRecepturaItemTp: ArrayOfRecepturaItemTp);
    function  Receptura_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Receptura: ArrayOfRecepturaItemTp  Index (IS_OPTN) read FReceptura write SetReceptura stored Receptura_Specified;
  end;

  ArrayOfDodatekTp = array of DodatekTp;        { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetDodatkiRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetDodatkiRsTp = class(ResponseType)
  private
    FDodatki: ArrayOfDodatekTp;
    FDodatki_Specified: boolean;
    procedure SetDodatki(Index: Integer; const AArrayOfDodatekTp: ArrayOfDodatekTp);
    function  Dodatki_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Dodatki: ArrayOfDodatekTp  Index (IS_OPTN) read FDodatki write SetDodatki stored Dodatki_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetQrFursRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetQrFursRsTp = class(ResponseType)
  private
    FQR: TByteSOAPArray;
    FQR_Specified: boolean;
    procedure SetQR(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
    function  QR_Specified(Index: Integer): boolean;
  published
    property QR: TByteSOAPArray  Index (IS_OPTN) read FQR write SetQR stored QR_Specified;
  end;

  ArrayOfLojalnostnaTp = array of LojalnostnaTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetLojalnostnaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetLojalnostnaRsTp = class(ResponseType)
  private
    FLojalnostna: ArrayOfLojalnostnaTp;
    FLojalnostna_Specified: boolean;
    procedure SetLojalnostna(Index: Integer; const AArrayOfLojalnostnaTp: ArrayOfLojalnostnaTp);
    function  Lojalnostna_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Lojalnostna: ArrayOfLojalnostnaTp  Index (IS_OPTN) read FLojalnostna write SetLojalnostna stored Lojalnostna_Specified;
  end;



  // ************************************************************************ //
  // XML       : DodatekTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  DodatekTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FDODATEK_ID: Integer;
    FDODATEK_TEXT: string;
    FDODATEK_TEXT_Specified: boolean;
    FSKUPINA: string;
    FSKUPINA_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetDODATEK_TEXT(Index: Integer; const Astring: string);
    function  DODATEK_TEXT_Specified(Index: Integer): boolean;
    procedure SetSKUPINA(Index: Integer; const Astring: string);
    function  SKUPINA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property DODATEK_ID:    Integer              read FDODATEK_ID write FDODATEK_ID;
    property DODATEK_TEXT:  string               Index (IS_OPTN) read FDODATEK_TEXT write SetDODATEK_TEXT stored DODATEK_TEXT_Specified;
    property SKUPINA:       string               Index (IS_OPTN) read FSKUPINA write SetSKUPINA stored SKUPINA_Specified;
  end;



  // ************************************************************************ //
  // XML       : SearchGostTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  SearchGostTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FGOST_ID: Int64;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FIME: string;
    FIME_Specified: boolean;
    FPRIIMEK: string;
    FPRIIMEK_Specified: boolean;
    FDAT_ROJ: string;
    FDAT_ROJ_Specified: boolean;
    FNASLOV: string;
    FNASLOV_Specified: boolean;
    FDRZAVA: string;
    FDRZAVA_Specified: boolean;
    FDOKUMENT: string;
    FDOKUMENT_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetIME(Index: Integer; const Astring: string);
    function  IME_Specified(Index: Integer): boolean;
    procedure SetPRIIMEK(Index: Integer; const Astring: string);
    function  PRIIMEK_Specified(Index: Integer): boolean;
    procedure SetDAT_ROJ(Index: Integer; const Astring: string);
    function  DAT_ROJ_Specified(Index: Integer): boolean;
    procedure SetNASLOV(Index: Integer; const Astring: string);
    function  NASLOV_Specified(Index: Integer): boolean;
    procedure SetDRZAVA(Index: Integer; const Astring: string);
    function  DRZAVA_Specified(Index: Integer): boolean;
    procedure SetDOKUMENT(Index: Integer; const Astring: string);
    function  DOKUMENT_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property GOST_ID:       Int64                read FGOST_ID write FGOST_ID;
    property NAZIV:         string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property IME:           string               Index (IS_OPTN) read FIME write SetIME stored IME_Specified;
    property PRIIMEK:       string               Index (IS_OPTN) read FPRIIMEK write SetPRIIMEK stored PRIIMEK_Specified;
    property DAT_ROJ:       string               Index (IS_OPTN) read FDAT_ROJ write SetDAT_ROJ stored DAT_ROJ_Specified;
    property NASLOV:        string               Index (IS_OPTN) read FNASLOV write SetNASLOV stored NASLOV_Specified;
    property DRZAVA:        string               Index (IS_OPTN) read FDRZAVA write SetDRZAVA stored DRZAVA_Specified;
    property DOKUMENT:      string               Index (IS_OPTN) read FDOKUMENT write SetDOKUMENT stored DOKUMENT_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetSlipEma2Tp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetSlipEma2Tp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FSTEVILKA_RACUNA: Integer;
    FPOZICIJA_ID: Integer;
    FAVTORIZACIJA: string;
    FAVTORIZACIJA_Specified: boolean;
    FACQTRANSREF: string;
    FACQTRANSREF_Specified: boolean;
    FACQREFERENCE: string;
    FACQREFERENCE_Specified: boolean;
    FSTTYPE: string;
    FSTTYPE_Specified: boolean;
    FDATUM_TRANSAKCIJE: TXSDateTime;
    FZNESEK: TXSDecimal;
    FZNESEK_SLIP: TXSDecimal;
    FSLIP_PRINT: string;
    FSLIP_PRINT_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetAVTORIZACIJA(Index: Integer; const Astring: string);
    function  AVTORIZACIJA_Specified(Index: Integer): boolean;
    procedure SetACQTRANSREF(Index: Integer; const Astring: string);
    function  ACQTRANSREF_Specified(Index: Integer): boolean;
    procedure SetACQREFERENCE(Index: Integer; const Astring: string);
    function  ACQREFERENCE_Specified(Index: Integer): boolean;
    procedure SetSTTYPE(Index: Integer; const Astring: string);
    function  STTYPE_Specified(Index: Integer): boolean;
    procedure SetSLIP_PRINT(Index: Integer; const Astring: string);
    function  SLIP_PRINT_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:     ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property STEVILKA_RACUNA:   Integer              read FSTEVILKA_RACUNA write FSTEVILKA_RACUNA;
    property POZICIJA_ID:       Integer              read FPOZICIJA_ID write FPOZICIJA_ID;
    property AVTORIZACIJA:      string               Index (IS_OPTN) read FAVTORIZACIJA write SetAVTORIZACIJA stored AVTORIZACIJA_Specified;
    property ACQTRANSREF:       string               Index (IS_OPTN) read FACQTRANSREF write SetACQTRANSREF stored ACQTRANSREF_Specified;
    property ACQREFERENCE:      string               Index (IS_OPTN) read FACQREFERENCE write SetACQREFERENCE stored ACQREFERENCE_Specified;
    property STTYPE:            string               Index (IS_OPTN) read FSTTYPE write SetSTTYPE stored STTYPE_Specified;
    property DATUM_TRANSAKCIJE: TXSDateTime          read FDATUM_TRANSAKCIJE write FDATUM_TRANSAKCIJE;
    property ZNESEK:            TXSDecimal           Index (IS_NLBL) read FZNESEK write FZNESEK;
    property ZNESEK_SLIP:       TXSDecimal           Index (IS_NLBL) read FZNESEK_SLIP write FZNESEK_SLIP;
    property SLIP_PRINT:        string               Index (IS_OPTN) read FSLIP_PRINT write SetSLIP_PRINT stored SLIP_PRINT_Specified;
  end;

  ArrayOfGetSlipEma2Tp = array of GetSlipEma2Tp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetSlipEma2RsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetSlipEma2RsTp = class(ResponseType)
  private
    FSlipList: ArrayOfGetSlipEma2Tp;
    FSlipList_Specified: boolean;
    procedure SetSlipList(Index: Integer; const AArrayOfGetSlipEma2Tp: ArrayOfGetSlipEma2Tp);
    function  SlipList_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property SlipList: ArrayOfGetSlipEma2Tp  Index (IS_OPTN) read FSlipList write SetSlipList stored SlipList_Specified;
  end;

  ArrayOfSearchGostTp = array of SearchGostTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : SearchGostRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  SearchGostRsTp = class(ResponseType)
  private
    FGosti: ArrayOfSearchGostTp;
    FGosti_Specified: boolean;
    procedure SetGosti(Index: Integer; const AArrayOfSearchGostTp: ArrayOfSearchGostTp);
    function  Gosti_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Gosti: ArrayOfSearchGostTp  Index (IS_OPTN) read FGosti write SetGosti stored Gosti_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetSlipEma2RqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetSlipEma2RqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FSTEVILKA_RACUNA: Integer;
    FSTRM_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:   ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property STEVILKA_RACUNA: Integer              read FSTEVILKA_RACUNA write FSTEVILKA_RACUNA;
    property STRM_ID:         TXSInteger           Index (IS_NLBL) read FSTRM_ID write FSTRM_ID;
  end;



  // ************************************************************************ //
  // XML       : GetNapitninaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNapitninaRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FRACUN_ID: Integer;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property RACUN_ID:      Integer              read FRACUN_ID write FRACUN_ID;
  end;



  // ************************************************************************ //
  // XML       : GetNapitninaRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNapitninaRsTp = class(ResponseType)
  private
    FN_DATUMURA: TXSDateTime;
    FN_ZNESEK: TXSDecimal;
    FN_PLACILO: string;
    FN_PLACILO_Specified: boolean;
    procedure SetN_PLACILO(Index: Integer; const Astring: string);
    function  N_PLACILO_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property N_DATUMURA: TXSDateTime  Index (IS_NLBL) read FN_DATUMURA write FN_DATUMURA;
    property N_ZNESEK:   TXSDecimal   Index (IS_NLBL) read FN_ZNESEK write FN_ZNESEK;
    property N_PLACILO:  string       Index (IS_OPTN) read FN_PLACILO write SetN_PLACILO stored N_PLACILO_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetBazenKarteRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetBazenKarteRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FODBKARTA_ID: Int64;
    FDOBKARTA_ID: Int64;
    FAKTIVEN: TXSInteger;
    FKORISCENO: Integer;
    FDATUM: TXSDateTime;
    FRFID: string;
    FRFID_Specified: boolean;
    FRACUN_ID: TXSInteger;
    FPRODAJA_RACUN_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetRFID(Index: Integer; const Astring: string);
    function  RFID_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property ODBKARTA_ID:      Int64                read FODBKARTA_ID write FODBKARTA_ID;
    property DOBKARTA_ID:      Int64                read FDOBKARTA_ID write FDOBKARTA_ID;
    property AKTIVEN:          TXSInteger           Index (IS_NLBL) read FAKTIVEN write FAKTIVEN;
    property KORISCENO:        Integer              read FKORISCENO write FKORISCENO;
    property DATUM:            TXSDateTime          read FDATUM write FDATUM;
    property RFID:             string               Index (IS_OPTN) read FRFID write SetRFID stored RFID_Specified;
    property RACUN_ID:         TXSInteger           Index (IS_NLBL) read FRACUN_ID write FRACUN_ID;
    property PRODAJA_RACUN_ID: TXSInteger           Index (IS_NLBL) read FPRODAJA_RACUN_ID write FPRODAJA_RACUN_ID;
  end;

  ArrayOfGetNapitninaSkupajTp = array of GetNapitninaSkupajTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetNapitninaSkupajRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNapitninaSkupajRsTp = class(ResponseType)
  private
    FNapitnine: ArrayOfGetNapitninaSkupajTp;
    FNapitnine_Specified: boolean;
    procedure SetNapitnine(Index: Integer; const AArrayOfGetNapitninaSkupajTp: ArrayOfGetNapitninaSkupajTp);
    function  Napitnine_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Napitnine: ArrayOfGetNapitninaSkupajTp  Index (IS_OPTN) read FNapitnine write SetNapitnine stored Napitnine_Specified;
  end;



  // ************************************************************************ //
  // XML       : SetNapitninaRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  SetNapitninaRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FRACUN_ID: Integer;
    FN_DATUMURA: TXSDateTime;
    FN_ZNESEK: TXSDecimal;
    FN_PLACILO: string;
    FN_PLACILO_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetN_PLACILO(Index: Integer; const Astring: string);
    function  N_PLACILO_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property RACUN_ID:      Integer              read FRACUN_ID write FRACUN_ID;
    property N_DATUMURA:    TXSDateTime          Index (IS_NLBL) read FN_DATUMURA write FN_DATUMURA;
    property N_ZNESEK:      TXSDecimal           Index (IS_NLBL) read FN_ZNESEK write FN_ZNESEK;
    property N_PLACILO:     string               Index (IS_OPTN) read FN_PLACILO write SetN_PLACILO stored N_PLACILO_Specified;
  end;



  // ************************************************************************ //
  // XML       : BazenKartaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  BazenKartaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBKARTA_ID: Int64;
    FVRSTAKARTE: Integer;
    FNIVO4_ID: TXSInteger;
    FKOLICINA: TXSDecimal;
    FCENA: TXSDecimal;
    FPLACILO_ID: TXSInteger;
    FKORISCENO: Integer;
    FSLIKA: TByteSOAPArray;
    FSLIKA_Specified: boolean;
    FIME: string;
    FIME_Specified: boolean;
    FPRIIMEK: string;
    FPRIIMEK_Specified: boolean;
    FNASLOV: string;
    FNASLOV_Specified: boolean;
    FSTKORISCENJ: TXSInteger;
    FVELJAVNODNI: TXSInteger;
    FAKTIVEN: TXSInteger;
    FSTRM_ID: TXSInteger;
    FDATUM_OD: TXSDateTime;
    FDATUM_DO: TXSDateTime;
    FDATUM_AKTIVACIJE: TXSDateTime;
    FDATUM_KORISCENJA: TXSDateTime;
    FKORISTIL_ID: TXSInteger;
    FOSEBA_ID_REVERZ: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetSLIKA(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
    function  SLIKA_Specified(Index: Integer): boolean;
    procedure SetIME(Index: Integer; const Astring: string);
    function  IME_Specified(Index: Integer): boolean;
    procedure SetPRIIMEK(Index: Integer; const Astring: string);
    function  PRIIMEK_Specified(Index: Integer): boolean;
    procedure SetNASLOV(Index: Integer; const Astring: string);
    function  NASLOV_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BKARTA_ID:        Int64                read FBKARTA_ID write FBKARTA_ID;
    property VRSTAKARTE:       Integer              read FVRSTAKARTE write FVRSTAKARTE;
    property NIVO4_ID:         TXSInteger           Index (IS_NLBL) read FNIVO4_ID write FNIVO4_ID;
    property KOLICINA:         TXSDecimal           Index (IS_NLBL) read FKOLICINA write FKOLICINA;
    property CENA:             TXSDecimal           Index (IS_NLBL) read FCENA write FCENA;
    property PLACILO_ID:       TXSInteger           Index (IS_NLBL) read FPLACILO_ID write FPLACILO_ID;
    property KORISCENO:        Integer              read FKORISCENO write FKORISCENO;
    property SLIKA:            TByteSOAPArray       Index (IS_OPTN) read FSLIKA write SetSLIKA stored SLIKA_Specified;
    property IME:              string               Index (IS_OPTN) read FIME write SetIME stored IME_Specified;
    property PRIIMEK:          string               Index (IS_OPTN) read FPRIIMEK write SetPRIIMEK stored PRIIMEK_Specified;
    property NASLOV:           string               Index (IS_OPTN) read FNASLOV write SetNASLOV stored NASLOV_Specified;
    property STKORISCENJ:      TXSInteger           Index (IS_NLBL) read FSTKORISCENJ write FSTKORISCENJ;
    property VELJAVNODNI:      TXSInteger           Index (IS_NLBL) read FVELJAVNODNI write FVELJAVNODNI;
    property AKTIVEN:          TXSInteger           Index (IS_NLBL) read FAKTIVEN write FAKTIVEN;
    property STRM_ID:          TXSInteger           Index (IS_NLBL) read FSTRM_ID write FSTRM_ID;
    property DATUM_OD:         TXSDateTime          Index (IS_NLBL) read FDATUM_OD write FDATUM_OD;
    property DATUM_DO:         TXSDateTime          Index (IS_NLBL) read FDATUM_DO write FDATUM_DO;
    property DATUM_AKTIVACIJE: TXSDateTime          Index (IS_NLBL) read FDATUM_AKTIVACIJE write FDATUM_AKTIVACIJE;
    property DATUM_KORISCENJA: TXSDateTime          Index (IS_NLBL) read FDATUM_KORISCENJA write FDATUM_KORISCENJA;
    property KORISTIL_ID:      TXSInteger           Index (IS_NLBL) read FKORISTIL_ID write FKORISTIL_ID;
    property OSEBA_ID_REVERZ:  TXSInteger           Index (IS_NLBL) read FOSEBA_ID_REVERZ write FOSEBA_ID_REVERZ;
  end;



  // ************************************************************************ //
  // XML       : AkcijaGetRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaGetRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FPRIJAVA_ID: TXSInteger;
    FKUPON_ID: string;
    FKUPON_ID_Specified: boolean;
    FSTR_MESTO_ID: TXSInteger;
    FDATUM_OD: TXSDateTime;
    FDATUM_DO: TXSDateTime;
    FRACUN_ID: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKUPON_ID(Index: Integer; const Astring: string);
    function  KUPON_ID_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property PRIJAVA_ID:    TXSInteger           Index (IS_NLBL) read FPRIJAVA_ID write FPRIJAVA_ID;
    property KUPON_ID:      string               Index (IS_OPTN) read FKUPON_ID write SetKUPON_ID stored KUPON_ID_Specified;
    property STR_MESTO_ID:  TXSInteger           Index (IS_NLBL) read FSTR_MESTO_ID write FSTR_MESTO_ID;
    property DATUM_OD:      TXSDateTime          Index (IS_NLBL) read FDATUM_OD write FDATUM_OD;
    property DATUM_DO:      TXSDateTime          Index (IS_NLBL) read FDATUM_DO write FDATUM_DO;
    property RACUN_ID:      TXSInteger           Index (IS_NLBL) read FRACUN_ID write FRACUN_ID;
  end;



  // ************************************************************************ //
  // XML       : AkcijaTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FAKCIJA_ID: Integer;
    FTIP_AKCIJE: TXSInteger;
    FPLACILO_ID: TXSInteger;
    FPARTNER_ID_ZAPLACILO: TXSInteger;
    FPOPUST_PROC: TXSDecimal;
    FPOPUST_ZNESEK: TXSDecimal;
    FKUPON_ID: string;
    FKUPON_ID_Specified: boolean;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FTIP_NAZIV: string;
    FTIP_NAZIV_Specified: boolean;
    FDATUM_OD: TXSDateTime;
    FDATUM_DO: TXSDateTime;
    FMIN_VREDNOST_RACUNA: TXSDecimal;
    FOPIS: string;
    FOPIS_Specified: boolean;
    FTISK: string;
    FTISK_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetKUPON_ID(Index: Integer; const Astring: string);
    function  KUPON_ID_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetTIP_NAZIV(Index: Integer; const Astring: string);
    function  TIP_NAZIV_Specified(Index: Integer): boolean;
    procedure SetOPIS(Index: Integer; const Astring: string);
    function  OPIS_Specified(Index: Integer): boolean;
    procedure SetTISK(Index: Integer; const Astring: string);
    function  TISK_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:        ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property AKCIJA_ID:            Integer              read FAKCIJA_ID write FAKCIJA_ID;
    property TIP_AKCIJE:           TXSInteger           Index (IS_NLBL) read FTIP_AKCIJE write FTIP_AKCIJE;
    property PLACILO_ID:           TXSInteger           Index (IS_NLBL) read FPLACILO_ID write FPLACILO_ID;
    property PARTNER_ID_ZAPLACILO: TXSInteger           Index (IS_NLBL) read FPARTNER_ID_ZAPLACILO write FPARTNER_ID_ZAPLACILO;
    property POPUST_PROC:          TXSDecimal           Index (IS_NLBL) read FPOPUST_PROC write FPOPUST_PROC;
    property POPUST_ZNESEK:        TXSDecimal           Index (IS_NLBL) read FPOPUST_ZNESEK write FPOPUST_ZNESEK;
    property KUPON_ID:             string               Index (IS_OPTN) read FKUPON_ID write SetKUPON_ID stored KUPON_ID_Specified;
    property NAZIV:                string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property TIP_NAZIV:            string               Index (IS_OPTN) read FTIP_NAZIV write SetTIP_NAZIV stored TIP_NAZIV_Specified;
    property DATUM_OD:             TXSDateTime          Index (IS_NLBL) read FDATUM_OD write FDATUM_OD;
    property DATUM_DO:             TXSDateTime          Index (IS_NLBL) read FDATUM_DO write FDATUM_DO;
    property MIN_VREDNOST_RACUNA:  TXSDecimal           Index (IS_NLBL) read FMIN_VREDNOST_RACUNA write FMIN_VREDNOST_RACUNA;
    property OPIS:                 string               Index (IS_OPTN) read FOPIS write SetOPIS stored OPIS_Specified;
    property TISK:                 string               Index (IS_OPTN) read FTISK write SetTISK stored TISK_Specified;
  end;

  ArrayOfAkcijaTp = array of AkcijaTp;          { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : AkcijaGetRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  AkcijaGetRsTp = class(ResponseType)
  private
    FAkcije: ArrayOfAkcijaTp;
    FAkcije_Specified: boolean;
    procedure SetAkcije(Index: Integer; const AArrayOfAkcijaTp: ArrayOfAkcijaTp);
    function  Akcije_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Akcije: ArrayOfAkcijaTp  Index (IS_OPTN) read FAkcije write SetAkcije stored Akcije_Specified;
  end;

  ArrayOfBazenKartaTp = array of BazenKartaTp;   { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetBazenKarteRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetBazenKarteRsTp = class(ResponseType)
  private
    FBKarte: ArrayOfBazenKartaTp;
    FBKarte_Specified: boolean;
    procedure SetBKarte(Index: Integer; const AArrayOfBazenKartaTp: ArrayOfBazenKartaTp);
    function  BKarte_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property BKarte: ArrayOfBazenKartaTp  Index (IS_OPTN) read FBKarte write SetBKarte stored BKarte_Specified;
  end;



  // ************************************************************************ //
  // XML       : SetBazenKarteRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  SetBazenKarteRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FODBKARTA_ID: TXSLong;
    FDOBKARTA_ID: TXSLong;
    FRACUN_ID: TXSInteger;
    FKORISCENO_STARO: TXSInteger;
    FKORISCENO_NOVO: TXSInteger;
    FAKTIVEN: TXSInteger;
    FPRODAJA_RACUN_ID: TXSInteger;
    FAKTIVIRAL_ID: TXSInteger;
    FW_RACUN_ID: TXSInteger;
    FW_PRODAJA_RACUN_ID: TXSInteger;
    FVRSTAKARTE: TXSInteger;
    FIME: string;
    FIME_Specified: boolean;
    FPRIIMEK: string;
    FPRIIMEK_Specified: boolean;
    FNASLOV: string;
    FNASLOV_Specified: boolean;
    FREFERENCA_AKTIVACIJE: string;
    FREFERENCA_AKTIVACIJE_Specified: boolean;
    FSTKORISCENJ: TXSInteger;
    FDATUM_DO: TXSDateTime;
    FDATUM_AKTIVACIJE: TXSDateTime;
    FDATUM_KORISCENJA: TXSDateTime;
    FKORISTIL_ID: TXSInteger;
    FOSEBA_ID_REVERZ: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetIME(Index: Integer; const Astring: string);
    function  IME_Specified(Index: Integer): boolean;
    procedure SetPRIIMEK(Index: Integer; const Astring: string);
    function  PRIIMEK_Specified(Index: Integer): boolean;
    procedure SetNASLOV(Index: Integer; const Astring: string);
    function  NASLOV_Specified(Index: Integer): boolean;
    procedure SetREFERENCA_AKTIVACIJE(Index: Integer; const Astring: string);
    function  REFERENCA_AKTIVACIJE_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:        ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property ODBKARTA_ID:          TXSLong              Index (IS_NLBL) read FODBKARTA_ID write FODBKARTA_ID;
    property DOBKARTA_ID:          TXSLong              Index (IS_NLBL) read FDOBKARTA_ID write FDOBKARTA_ID;
    property RACUN_ID:             TXSInteger           Index (IS_NLBL) read FRACUN_ID write FRACUN_ID;
    property KORISCENO_STARO:      TXSInteger           Index (IS_NLBL) read FKORISCENO_STARO write FKORISCENO_STARO;
    property KORISCENO_NOVO:       TXSInteger           Index (IS_NLBL) read FKORISCENO_NOVO write FKORISCENO_NOVO;
    property AKTIVEN:              TXSInteger           Index (IS_NLBL) read FAKTIVEN write FAKTIVEN;
    property PRODAJA_RACUN_ID:     TXSInteger           Index (IS_NLBL) read FPRODAJA_RACUN_ID write FPRODAJA_RACUN_ID;
    property AKTIVIRAL_ID:         TXSInteger           Index (IS_NLBL) read FAKTIVIRAL_ID write FAKTIVIRAL_ID;
    property W_RACUN_ID:           TXSInteger           Index (IS_NLBL) read FW_RACUN_ID write FW_RACUN_ID;
    property W_PRODAJA_RACUN_ID:   TXSInteger           Index (IS_NLBL) read FW_PRODAJA_RACUN_ID write FW_PRODAJA_RACUN_ID;
    property VRSTAKARTE:           TXSInteger           Index (IS_NLBL) read FVRSTAKARTE write FVRSTAKARTE;
    property IME:                  string               Index (IS_OPTN) read FIME write SetIME stored IME_Specified;
    property PRIIMEK:              string               Index (IS_OPTN) read FPRIIMEK write SetPRIIMEK stored PRIIMEK_Specified;
    property NASLOV:               string               Index (IS_OPTN) read FNASLOV write SetNASLOV stored NASLOV_Specified;
    property REFERENCA_AKTIVACIJE: string               Index (IS_OPTN) read FREFERENCA_AKTIVACIJE write SetREFERENCA_AKTIVACIJE stored REFERENCA_AKTIVACIJE_Specified;
    property STKORISCENJ:          TXSInteger           Index (IS_NLBL) read FSTKORISCENJ write FSTKORISCENJ;
    property DATUM_DO:             TXSDateTime          Index (IS_NLBL) read FDATUM_DO write FDATUM_DO;
    property DATUM_AKTIVACIJE:     TXSDateTime          Index (IS_NLBL) read FDATUM_AKTIVACIJE write FDATUM_AKTIVACIJE;
    property DATUM_KORISCENJA:     TXSDateTime          Index (IS_NLBL) read FDATUM_KORISCENJA write FDATUM_KORISCENJA;
    property KORISTIL_ID:          TXSInteger           Index (IS_NLBL) read FKORISTIL_ID write FKORISTIL_ID;
    property OSEBA_ID_REVERZ:      TXSInteger           Index (IS_NLBL) read FOSEBA_ID_REVERZ write FOSEBA_ID_REVERZ;
  end;



  // ************************************************************************ //
  // XML       : NatisniHodRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  NatisniHodRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FRACUN_ID: Integer;
    FHOD: string;
    FHOD_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetHOD(Index: Integer; const Astring: string);
    function  HOD_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property RACUN_ID:      Integer              read FRACUN_ID write FRACUN_ID;
    property HOD:           string               Index (IS_OPTN) read FHOD write SetHOD stored HOD_Specified;
  end;



  // ************************************************************************ //
  // XML       : Nivo4TujiTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  Nivo4TujiTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FJEZIKOVNA_SKUPINA: Integer;
    FNIVO4_ID: Integer;
    FNAZIV: string;
    FNAZIV_Specified: boolean;
    FOPIS: string;
    FOPIS_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetNAZIV(Index: Integer; const Astring: string);
    function  NAZIV_Specified(Index: Integer): boolean;
    procedure SetOPIS(Index: Integer; const Astring: string);
    function  OPIS_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:     ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property JEZIKOVNA_SKUPINA: Integer              read FJEZIKOVNA_SKUPINA write FJEZIKOVNA_SKUPINA;
    property NIVO4_ID:          Integer              read FNIVO4_ID write FNIVO4_ID;
    property NAZIV:             string               Index (IS_OPTN) read FNAZIV write SetNAZIV stored NAZIV_Specified;
    property OPIS:              string               Index (IS_OPTN) read FOPIS write SetOPIS stored OPIS_Specified;
  end;

  ArrayOfNivo4TujiTp = array of Nivo4TujiTp;    { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetStornoIdRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetStornoIdRsTp = class(ResponseType)
  private
    FSTORNO_RACUN_ID: TXSInteger;
  public
    destructor Destroy; override;
  published
    property STORNO_RACUN_ID: TXSInteger  Index (IS_NLBL) read FSTORNO_RACUN_ID write FSTORNO_RACUN_ID;
  end;



  // ************************************************************************ //
  // XML       : GetNivo4IdFromBarcodeRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNivo4IdFromBarcodeRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FBAR_CODE: string;
    FBAR_CODE_Specified: boolean;
    FCENIK_ID: TXSInteger;
    FVIR_TABELA: string;
    FVIR_TABELA_Specified: boolean;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetBAR_CODE(Index: Integer; const Astring: string);
    function  BAR_CODE_Specified(Index: Integer): boolean;
    procedure SetVIR_TABELA(Index: Integer; const Astring: string);
    function  VIR_TABELA_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property BAR_CODE:      string               Index (IS_OPTN) read FBAR_CODE write SetBAR_CODE stored BAR_CODE_Specified;
    property CENIK_ID:      TXSInteger           Index (IS_NLBL) read FCENIK_ID write FCENIK_ID;
    property VIR_TABELA:    string               Index (IS_OPTN) read FVIR_TABELA write SetVIR_TABELA stored VIR_TABELA_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetNivo4IdFromBarcodeRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNivo4IdFromBarcodeRsTp = class(ResponseType)
  private
    FNIVO4_ID: TXSInteger;
  public
    destructor Destroy; override;
  published
    property NIVO4_ID: TXSInteger  Index (IS_NLBL) read FNIVO4_ID write FNIVO4_ID;
  end;



  // ************************************************************************ //
  // XML       : GetNivo4TujiRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNivo4TujiRsTp = class(ResponseType)
  private
    FNivo4Tuji: ArrayOfNivo4TujiTp;
    FNivo4Tuji_Specified: boolean;
    procedure SetNivo4Tuji(Index: Integer; const AArrayOfNivo4TujiTp: ArrayOfNivo4TujiTp);
    function  Nivo4Tuji_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Nivo4Tuji: ArrayOfNivo4TujiTp  Index (IS_OPTN) read FNivo4Tuji write SetNivo4Tuji stored Nivo4Tuji_Specified;
  end;



  // ************************************************************************ //
  // XML       : GetNapitninaSkupajRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNapitninaSkupajRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOD_DATUM: TXSDateTime;
    FTOCILNICA_ID: TXSInteger;
    FOSEBA_ID: TXSInteger;
    FSTATUS: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OD_DATUM:      TXSDateTime          Index (IS_NLBL) read FOD_DATUM write FOD_DATUM;
    property TOCILNICA_ID:  TXSInteger           Index (IS_NLBL) read FTOCILNICA_ID write FTOCILNICA_ID;
    property OSEBA_ID:      TXSInteger           Index (IS_NLBL) read FOSEBA_ID write FOSEBA_ID;
    property STATUS:        TXSInteger           Index (IS_NLBL) read FSTATUS write FSTATUS;
  end;



  // ************************************************************************ //
  // XML       : GetNapitninaSkupajTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetNapitninaSkupajTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FKASIRAL: Integer;
    FNAPITNINA: TXSDecimal;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property KASIRAL:       Integer              read FKASIRAL write FKASIRAL;
    property NAPITNINA:     TXSDecimal           Index (IS_NLBL) read FNAPITNINA write FNAPITNINA;
  end;



  // ************************************************************************ //
  // XML       : GetPraznikiRqTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetPraznikiRqTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOBRAT_ID: TXSInteger;
    FDATUM: TXSDateTime;
    FDATUM_DO: TXSDateTime;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData: ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OBRAT_ID:      TXSInteger           Index (IS_NLBL) read FOBRAT_ID write FOBRAT_ID;
    property DATUM:         TXSDateTime          Index (IS_NLBL) read FDATUM write FDATUM;
    property DATUM_DO:      TXSDateTime          Index (IS_NLBL) read FDATUM_DO write FDATUM_DO;
  end;



  // ************************************************************************ //
  // XML       : PraznikTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  PraznikTp = class(TRemotable)
  private
    FExtensionData: ExtensionDataObject;
    FExtensionData_Specified: boolean;
    FOBRAT_ID: TXSInteger;
    FDATUM: TXSDateTime;
    FOPIS: string;
    FOPIS_Specified: boolean;
    FBARVA: TXSLong;
    FNAROCILA_DOSTAVA: TXSDecimal;
    FOBRACUN_ODBITKA: TXSInteger;
    procedure SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
    function  ExtensionData_Specified(Index: Integer): boolean;
    procedure SetOPIS(Index: Integer; const Astring: string);
    function  OPIS_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property ExtensionData:    ExtensionDataObject  Index (IS_OPTN) read FExtensionData write SetExtensionData stored ExtensionData_Specified;
    property OBRAT_ID:         TXSInteger           Index (IS_NLBL) read FOBRAT_ID write FOBRAT_ID;
    property DATUM:            TXSDateTime          Index (IS_NLBL) read FDATUM write FDATUM;
    property OPIS:             string               Index (IS_OPTN) read FOPIS write SetOPIS stored OPIS_Specified;
    property BARVA:            TXSLong              Index (IS_NLBL) read FBARVA write FBARVA;
    property NAROCILA_DOSTAVA: TXSDecimal           Index (IS_NLBL) read FNAROCILA_DOSTAVA write FNAROCILA_DOSTAVA;
    property OBRACUN_ODBITKA:  TXSInteger           Index (IS_NLBL) read FOBRACUN_ODBITKA write FOBRACUN_ODBITKA;
  end;

  ArrayOfPraznikTp = array of PraznikTp;        { "http://ros.si/R16"[GblCplx] }


  // ************************************************************************ //
  // XML       : GetPraznikiRsTp, global, <complexType>
  // Namespace : http://ros.si/R16
  // ************************************************************************ //
  GetPraznikiRsTp = class(ResponseType)
  private
    FPrazniki: ArrayOfPraznikTp;
    FPrazniki_Specified: boolean;
    procedure SetPrazniki(Index: Integer; const AArrayOfPraznikTp: ArrayOfPraznikTp);
    function  Prazniki_Specified(Index: Integer): boolean;
  public
    destructor Destroy; override;
  published
    property Prazniki: ArrayOfPraznikTp  Index (IS_OPTN) read FPrazniki write SetPrazniki stored Prazniki_Specified;
  end;


  // ************************************************************************ //
  // Namespace : http://ros.si/R16
  // soapAction: http://ros.si/R16/%operationName%
  // transport : http://schemas.xmlsoap.org/soap/http
  // style     : document
  // use       : literal
  // binding   : KasaSoap
  // service   : Kasa
  // port      : KasaSoap
  // URL       : https://test.ros.si/r16f/asmx/kasa.asmx
  // ************************************************************************ //
  KasaSoap = interface(IInvokable)
  ['{19B61777-BB61-BD9D-EA15-919191B51225}']
    function  timeoutTest(const milisekund: Integer; const token: string): string; stdcall;
    function  transportTest(const tekst: string; const token: string): string; stdcall;
    function  testGetRacuniSeznam(const koliko: Integer): GetRacuniSeznamRsTp; stdcall;
    function  getOsebaToken(const uIme: string; const geslo: string): OsebaTokenRsTp; stdcall;
    function  getKomanda(const clearOnRead: Boolean; const token: string): ResponseType; stdcall;
    function  SetOsebaDatumPrijaveSysdate(const osebaId: Integer; const token: string): ResponseType; stdcall;
    function  setKomanda(const komanda: string; const token: string): ResponseType; stdcall;
    function  aktivirajMobile(const mobileId: Integer; const token: string): ResponseType; stdcall;
    function  getAppConfig(const mobileId: Integer; const token: string): GetAppConfigRsTp; stdcall;
    function  getMobileSetups(const token: string): GetMobileSetupsRsTp; stdcall;
    function  setMobileMobileSetup(const mobileId: Integer; const mobileSetupId: Integer; const token: string): ResponseType; stdcall;
    function  getTimes(const mobileId: Integer; const kasa: TXSDateTime; const token: string): GetTimesRsTp; stdcall;
    function  insertKronologija(const rq: ArrayOfKronologijaTp; const token: string): ResponseType; stdcall;
    function  insertKronologija2(const rq: ArrayOfKronologijaTp; const danUraKasa: TXSDateTime; const token: string): ResponseType; stdcall;
    function  insertKronolog(const rq: InsertKronologRqTp; const token: string): ResponseType; stdcall;
    function  gdprLog(const imeLoga: string; const idji: ArrayOfLong; const izvor: string; const opis: string; const token: string): ResponseType; stdcall;
    function  getHitreTipke(const stroskovnoId: Integer; const token: string): GetHitreTipkeRsTp; stdcall;
    function  getHitreTipke2(const stroskovnoId: Integer; const token: string): GetHitreTipkeRsTp; stdcall;
    function  hitraTipkaGetOne(const stroskovnoId: Integer; const tipkaId: Integer; const token: string): GetHitreTipkeRsTp; stdcall;
    function  hitraTipkaDelete(const stroskovnoId: Integer; const tipkaId: Integer; const token: string): ResponseType; stdcall;
    function  hitraTipkaInsert(const rq: HitraTipkaTp; const stroskovnoId: Integer; const token: string): ResponseType; stdcall;
    function  hitraTipkaUpdate(const rq: HitraTipkaTp; const stroskovnoId: Integer; const token: string): ResponseType; stdcall;
    function  getCenik(const stroskovnoId: Integer; const token: string): GetCenikRsTp; stdcall;
    function  getNacPlac(const token: string): GetNacPlacRsTp; stdcall;
    function  getNacPlac2(const mobileId: Integer; const token: string): GetNacPlacRsTp; stdcall;
    function  getPartner(const rq: GetPartnerRqTp; const token: string): GetPartnerRsTp; stdcall;
    function  getProstor(const rq: GetProstorRqTp; const token: string): GetProstorRsTp; stdcall;
    function  getKartprij(const rq: GetKartprijRqTp; const token: string): GetKartprijRsTp; stdcall;
    function  getListaSkupin(const rq: GetListaSkupinRqTp; const token: string): GetListaSkupinRsTp; stdcall;
    function  getPrijava(const rq: GetPrijavaRqTp; const token: string): GetPrijavaRsTp; stdcall;
    function  setRacun(const racun: RacunTp; const mobileId: Integer; const token: string): GetRacunRsTp; stdcall;
    function  insertPlacilo(const placilo: PlaciloTp; const osebaId: Integer; const mobileId: Integer; const token: string): GetRacunRsTp; stdcall;
    function  setStorno(const rq: SetStornoRqTp; const token: string): ResponseType; stdcall;
    function  setStornoRacuna(const rq: SetStornoRacunaRqTp; const token: string): ResponseType; stdcall;
    function  getRacuniSeznam(const rq: GetRacuniRqTp; const token: string): GetRacuniSeznamRsTp; stdcall;
    function  getRacuni(const rq: GetRacuniRqTp; const token: string): GetRacuniRsTp; stdcall;
    function  insertIzpisan(const rq: IzpisanTp; const token: string): ResponseType; stdcall;
    function  getVerzijaOfRacglava(const racunId: Integer; const token: string): Integer; stdcall;
    function  narediObracun(const rq: NarediObracunRqTp; const token: string): ResponseType; stdcall;
    function  zamenjajLastnika(const rq: ZamenjajLastnikaRqTp; const token: string): ResponseType; stdcall;
    function  zdruziRacune(const rq: ZdruziRacuneRqTp; const token: string): ResponseType; stdcall;
    function  getNovaNarocila(const tocilnicaId: Integer; const token: string): GetNovaNarocilaRsTp; stdcall;
    function  prevzamiNarocila(const tocilnicaId: Integer; const kasiral: Integer; const token: string): ResponseType; stdcall;
    function  getObroki(const rq: GetObrokiRqTp; const token: string): GetObrokiRsTp; stdcall;
    function  getInkasoOsebe(const strmId: Integer; const osebaId: Integer; const token: string): GetInkasoOsebeRsTp; stdcall;
    function  getNacPlacMakro(const strmId: Integer; const token: string): GetNacPlacMakroRsTp; stdcall;
    function  getLojalnostna(const token: string): GetLojalnostnaRsTp; stdcall;
    function  getDodatki(const token: string): GetDodatkiRsTp; stdcall;
    function  setLojalnost(const bonitetniRazred: Integer; const racunId: Integer; const verzija: Integer; const token: string): GetRacunRsTp; stdcall;
    function  getQrFurs(const niz: string; const token: string): GetQrFursRsTp; stdcall;
    function  searchGost(const search: string; const token: string): SearchGostRsTp; stdcall;
    function  getSlipEma2(const rq: GetSlipEma2RqTp; const token: string): GetSlipEma2RsTp; stdcall;
    function  getSlipEma(const rq: GetSlipEmaRqTp; const token: string): GetSlipEmaRsTp; stdcall;
    function  setSlipEma(const rq: SetSlipEmaRqTp; const token: string): ResponseType; stdcall;
    function  getReport(const rq: GetReportRqTp; const token: string): ResponseType; stdcall;
    function  getDelovniNalogi(const token: string): GetDelovniNalogiRsTp; stdcall;
    function  getReceptura(const nivo4Id: Integer; const token: string): GetRecepturaRsTp; stdcall;
    function  getStornoRazlogi(const token: string): GetStornoRazlogiRsTp; stdcall;
    function  getStornoId(const racunId: Integer; const token: string): GetStornoIdRsTp; stdcall;
    function  getVrednost(const vir: string; const kljuc: string; const token: string): ResponseType; stdcall;
    function  getNivo4IdFromBarcode(const rq: GetNivo4IdFromBarcodeRqTp; const token: string): GetNivo4IdFromBarcodeRsTp; stdcall;
    function  natisniHod(const rq: NatisniHodRqTp; const token: string): GetRacunRsTp; stdcall;
    function  getNivo4Tuji(const token: string): GetNivo4TujiRsTp; stdcall;
    function  getPrazniki(const rq: GetPraznikiRqTp; const token: string): GetPraznikiRsTp; stdcall;
    function  getNapitninaSkupaj(const rq: GetNapitninaSkupajRqTp; const token: string): GetNapitninaSkupajRsTp; stdcall;
    function  setNapitnina(const rq: SetNapitninaRqTp; const token: string): ResponseType; stdcall;
    function  getNapitnina(const rq: GetNapitninaRqTp; const token: string): GetNapitninaRsTp; stdcall;
    function  getBazenKarte(const rq: GetBazenKarteRqTp; const token: string): GetBazenKarteRsTp; stdcall;
    function  setBazenKarte(const rq: SetBazenKarteRqTp; const token: string): ResponseType; stdcall;
    function  akcijaGet(const rq: AkcijaGetRqTp; const token: string): AkcijaGetRsTp; stdcall;
    function  akcijaArtikli(const rq: AkcijaArtikliRqTp; const token: string): AkcijaArtikliRsTp; stdcall;
    function  akcijaNaziv(const akcijaId: Integer; const token: string): AkcijaNazivRsTp; stdcall;
    function  akcijaStornoKupon(const racunId: Integer; const token: string): ResponseType; stdcall;
    function  akcijaKoristiKupon(const kuponId: string; const token: string): ResponseType; stdcall;
    function  akcijaSetLojalnost(const rq: AkcijaSetLojalnostRqTp; const token: string): AkcijaSetLojalnostRsTp; stdcall;
    function  akcijaSetKuponiRacuna(const racunId: Integer; const token: string): AkcijaGetRsTp; stdcall;
    function  monetaGetToken(const pin: string; const amount: string; const externalReferenceId: string; const delayedSale: Boolean; const delayTimeOut: Integer; const token: string
                             ): GetTokenRsTp; stdcall;
    function  monetaCancelTransaction(const pin: string; const transactionId: string; const token: string): CancelTransactionRsTp; stdcall;
    function  monetaGetTransactionStatus(const pin: string; const transactionId: string; const token: string): GetTransactionStatusRsTp; stdcall;
    function  monetaReverse(const pin: string; const referenceId: string; const token: string): CancelTransactionRsTp; stdcall;
    function  mbillsSale(const rq: MbillsSaleRqTp; const token: string): MbillsSaleRsTp; stdcall;
    function  mbillsGetStatus(const rq: MbillsGetStatusRqTp; const token: string): MbillsGetStatusRsTp; stdcall;
    function  mbillsFurs(const rq: MbillsFursRqTp; const token: string): MbillsFursRsTp; stdcall;
    function  mbillsRefund(const rq: MbillsRefundRqTp; const token: string): MbillsRefundRsTp; stdcall;
    function  mbillsVoid(const rq: MbillsVoidRqTp; const token: string): MbillsVoidRsTp; stdcall;
    function  valuStartPayment(const tId: Integer; const totalAmount: TXSDecimal; const token: string): ValuStartPaymentRsTp; stdcall;
    function  valuGetPaymentStatus(const tId: Integer; const transactionReference: string; const token: string): ValuGetPaymentStatusRsTp; stdcall;
    function  valuStornoPayment(const tId: Integer; const transactionReference: string; const amount: TXSDecimal; const token: string): ValuGetPaymentStatusRsTp; stdcall;
    function  crmInfo(const rq: CrmInfoRqTp; const token: string): CrmInfoRsTp; stdcall;
    function  crmTocke(const racunId: Integer; const token: string): CrmTockeRsTp; stdcall;
    function  crmGetStTock(const racunId: Integer; const token: string): CrmTockeRsTp; stdcall;
    function  bonSaldo(const rq: BonSaldoRqTp; const token: string): BonSaldoRsTp; stdcall;
    function  bonKnjizi(const rq: BonKnjiziRqTp; const token: string): BonKnjiziRsTp; stdcall;
    function  bonStorno(const bonAi: Integer; const token: string): ResponseType; stdcall;
    function  boniIzdaja(const rq: BoniIzdajaRqTp; const token: string): BoniIzdajaRsTp; stdcall;
    function  kuponSaldo(const rq: KuponSaldoRqTp; const token: string): KuponSaldoRsTp; stdcall;
    function  kuponKnjizi(const rq: KuponKnjiziRqTp; const token: string): KuponKnjiziRsTp; stdcall;
    function  kuponStorno(const rq: KuponKnjiziRqTp; const token: string): KuponKnjiziRsTp; stdcall;
    function  getKuponAkcija(const strm: Integer; const token: string): GetKuponAkcijaRsTp; stdcall;
    function  kuponAkcijaSaldo(const rq: KuponAkcijaSaldoRqTp; const token: string): KuponSaldoRsTp; stdcall;
    function  kuponAkcijaKnjizi(const rq: KuponAkcijaKnjiziRqTp; const token: string): KuponKnjiziRsTp; stdcall;
    function  kuponAkcijaStorno(const rq: KuponAkcijaKnjiziRqTp; const token: string): KuponKnjiziRsTp; stdcall;
    function  tbBalance(const rq: TbBalanceRqTp; const token: string): ResponseType; stdcall;
    function  tbRacunValidate(const racunId: Integer; const token: string): ResponseType; stdcall;
    function  tbRacunDeposit(const racunId: Integer; const token: string): ResponseType; stdcall;
    function  tbDocumentValidate(const rq: TBonDocumentTp; const token: string): ResponseType; stdcall;
    function  tbDocumentStorno(const rq: TbStornoRqTp; const token: string): ResponseType; stdcall;
    function  tbRacunStorno(const racunId: Integer; const token: string): ResponseType; stdcall;
    function  SixTransaction(const obratId: Integer; const osebaId: Integer; const transType: string; const terminalId: string; const posId: string; const userId: Integer;
                             const amountEur: TXSDecimal; const transSeq: string; const acqTransRef: string; const token: string; const racunId: string
                             ): ResponseType; stdcall;
    function  SixBalance(const obratId: Integer; const osebaId: Integer; const terminalId: string; const posId: string; const userId: Integer; const token: string
                         ): ResponseType; stdcall;
    function  SixTerminalStatus(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  SixTerminalCancel(const terminalId: string; const posId: string; const silent: Boolean; const retainCard: Boolean; const token: string): ResponseType; stdcall;
    function  SixTerminalReboot(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  Six2Transaction(const obratId: Integer; const osebaId: Integer; const transType: string; const terminalId: string; const posId: string; const userId: Integer;
                              const amountEur: TXSDecimal; const transSeq: string; const acqTransRef: string; const token: string; const racunId: string
                              ): ResponseType; stdcall;
    function  Six2Balance(const obratId: Integer; const osebaId: Integer; const terminalId: string; const posId: string; const userId: Integer; const token: string
                          ): ResponseType; stdcall;
    function  Six2TerminalStatus(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  Six2Cancel(const terminalId: string; const posId: string; const silent: Boolean; const retainCard: Boolean; const token: string): ResponseType; stdcall;
    function  Six2DisposeTerminal(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  Six2RebootTerminal(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  Six2HardwareInformation(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  Six2SystemInformation(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  Six2ReceiptRequest(const terminalId: string; const posId: string; const token: string): ResponseType; stdcall;
    function  eDenarGetSaldo(const denarnica: string; const token: string): ResponseType; stdcall;
    function  eDenarKnjizi(const denarnica: string; const znesek: TXSDecimal; const racunId: Integer; const placiloPozicijaId: TXSInteger; const token: string): ResponseType; stdcall;
    function  eDenarBrisi(const denarnica: string; const racunId: Integer; const placiloPozicijaId: TXSInteger; const token: string): ResponseType; stdcall;
    function  eDenarClear(const denarnica: string; const token: string): ResponseType; stdcall;
    function  eDenarSetStatus(const denarnica: string; const oldStatus: Integer; const newStatus: Integer; const token: string): ResponseType; stdcall;
    function  eDenarStornoZaRacun(const zaRacunId: Integer; const noviRacunId: Integer; const token: string): ResponseType; stdcall;
    function  eDenarCountStornoZaRacun(const zaRacunId: Integer; const token: string): ResponseType; stdcall;
    function  monitorWriteLog(const kljuc: string; const tekst: string; const tip: string; const minute: TXSInteger; const token: string): string; stdcall;
  end;

function GetKasaSoap(UseWSDL: Boolean=System.False; Addr: string=''; HTTPRIO: THTTPRIO = nil): KasaSoap;


implementation

 uses System.SysUtils, System.Generics.Collections,globals, Soap.opconvert,Soap.SOAPHTTPTrans,
  DataGisOrder;

function GetKasaSoap(UseWSDL: Boolean; Addr: string; HTTPRIO: THTTPRIO): KasaSoap;
const
  defWSDL = 'C:\xeros\testwsdl\RosKasa_ceniki_wsdl.wsdl';
//  defURL  = 'https://web.ros.si/ora/r16f/asmx/kasa.asmx';
  defSvc  = 'Kasa';
  defPrt  = 'KasaSoap';
var
  RIO: THTTPRIO;
begin
  Result := nil;
  if (Addr = '') then
  begin
    if UseWSDL then
      Addr := defWSDL
    else
      Addr := defURL;
  end;
  if HTTPRIO = nil then
    RIO := THTTPRIO.Create(nil)
  else
    RIO := HTTPRIO;
  try
  try
    Result := (RIO as KasaSoap);
    RIO.HTTPWebNode.ConnectTimeout := 10000;    // 13.03.2025 za test
//    RIO.HTTPWebNode.SendTimeout := 20000;
//    RIO.HTTPWebNode.ReceiveTimeout := 30000;
    if UseWSDL then
    begin
      RIO.WSDLLocation := Addr;
      RIO.Service := defSvc;
      RIO.Port := defPrt;
    end else
      RIO.URL := Addr;
  finally
    if (Result = nil) and (HTTPRIO = nil) then
      RIO.Free;
  end;
  except
    on E: ESOAPHTTPException do
    begin
      if E.StatusCode = 408 then
      begin
        // Handle connection timeout: inform user, retry, etc.
        if debugl1 then dmGisOrder.VpisiKronologijo('HTTPRIO Timeout timout exception');
        RosMessage('Timeout timout exception');
      end
      else
      begin
        // Handle other HTTP errors
        if debugl1 then dmGisOrder.VpisiKronologijo('HTTPRIO Timeout timout exception '+ E.Message);
        RosMessage('An HTTP error occurred: ' + E.Message);
      end;
    end;
    on E: Exception do
    begin
        if debugl1 then dmGisOrder.VpisiKronologijo('An unexpected error occurred '+ E.Message);
        RosMessage('An unexpected error occurred: ' + E.Message);
    end;
  end;
end;



destructor BaseDAL.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure BaseDAL.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function BaseDAL.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor TiskajTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure TiskajTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TiskajTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TiskajTp.SetTiskalnik(Index: Integer; const Astring: string);
begin
  FTiskalnik := Astring;
  FTiskalnik_Specified := True;
end;

function TiskajTp.Tiskalnik_Specified(Index: Integer): boolean;
begin
  Result := FTiskalnik_Specified;
end;

procedure TiskajTp.SetVsebina(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
begin
  FVsebina := ATByteSOAPArray;
  FVsebina_Specified := True;
end;

function TiskajTp.Vsebina_Specified(Index: Integer): boolean;
begin
  Result := FVsebina_Specified;
end;

destructor ResponseType.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure ResponseType.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function ResponseType.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure ResponseType.Setdata1(Index: Integer; const Astring: string);
begin
  Fdata1 := Astring;
  Fdata1_Specified := True;
end;

function ResponseType.data1_Specified(Index: Integer): boolean;
begin
  Result := Fdata1_Specified;
end;

procedure ResponseType.Setdata2(Index: Integer; const Astring: string);
begin
  Fdata2 := Astring;
  Fdata2_Specified := True;
end;

function ResponseType.data2_Specified(Index: Integer): boolean;
begin
  Result := Fdata2_Specified;
end;

procedure ResponseType.Setfault(Index: Integer; const Astring: string);
begin
  Ffault := Astring;
  Ffault_Specified := True;
end;

function ResponseType.fault_Specified(Index: Integer): boolean;
begin
  Result := Ffault_Specified;
end;

procedure ResponseType.Setstrings(Index: Integer; const AArrayOfString: ArrayOfString);
begin
  Fstrings := AArrayOfString;
  Fstrings_Specified := True;
end;

function ResponseType.strings_Specified(Index: Integer): boolean;
begin
  Result := Fstrings_Specified;
end;

destructor GetAppConfigRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FMobileSetup);
  inherited Destroy;
end;

procedure GetAppConfigRsTp.SetMobileSetup(Index: Integer; const AMobileSetupTp: MobileSetupTp);
begin
  FMobileSetup := AMobileSetupTp;
  FMobileSetup_Specified := True;
end;

function GetAppConfigRsTp.MobileSetup_Specified(Index: Integer): boolean;
begin
  Result := FMobileSetup_Specified;
end;

destructor GetRacuniRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.RacunTp>(FRacuni);
  System.SetLength(FRacuni, 0);
  inherited Destroy;
end;

procedure GetRacuniRsTp.SetRacuni(Index: Integer; const AArrayOfRacunTp: ArrayOfRacunTp);
begin
  FRacuni := AArrayOfRacunTp;
  FRacuni_Specified := True;
end;

function GetRacuniRsTp.Racuni_Specified(Index: Integer): boolean;
begin
  Result := FRacuni_Specified;
end;

destructor GetRacunRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.TiskajTp>(FTiskaj);
  System.SetLength(FTiskaj, 0);
  System.SysUtils.FreeAndNil(FRACGLAVA);
  inherited Destroy;
end;

procedure GetRacunRsTp.SetRACGLAVA(Index: Integer; const ARacunTp: RacunTp);
begin
  FRACGLAVA := ARacunTp;
  FRACGLAVA_Specified := True;
end;

function GetRacunRsTp.RACGLAVA_Specified(Index: Integer): boolean;
begin
  Result := FRACGLAVA_Specified;
end;

procedure GetRacunRsTp.SetTiskaj(Index: Integer; const AArrayOfTiskajTp: ArrayOfTiskajTp);
begin
  FTiskaj := AArrayOfTiskajTp;
  FTiskaj_Specified := True;
end;

function GetRacunRsTp.Tiskaj_Specified(Index: Integer): boolean;
begin
  Result := FTiskaj_Specified;
end;

destructor GetInkasoOsebeRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.InkasoOsebeTp>(FInkaso);
  System.SetLength(FInkaso, 0);
  inherited Destroy;
end;

procedure GetInkasoOsebeRsTp.SetInkaso(Index: Integer; const AArrayOfInkasoOsebeTp: ArrayOfInkasoOsebeTp);
begin
  FInkaso := AArrayOfInkasoOsebeTp;
  FInkaso_Specified := True;
end;

function GetInkasoOsebeRsTp.Inkaso_Specified(Index: Integer): boolean;
begin
  Result := FInkaso_Specified;
end;

destructor GetNacPlacMakroRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.NacPlacMakroTp>(FMakro);
  System.SetLength(FMakro, 0);
  inherited Destroy;
end;

procedure GetNacPlacMakroRsTp.SetMakro(Index: Integer; const AArrayOfNacPlacMakroTp: ArrayOfNacPlacMakroTp);
begin
  FMakro := AArrayOfNacPlacMakroTp;
  FMakro_Specified := True;
end;

function GetNacPlacMakroRsTp.Makro_Specified(Index: Integer): boolean;
begin
  Result := FMakro_Specified;
end;

destructor GetObrokiRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.ObrokTp>(FObroki);
  System.SetLength(FObroki, 0);
  inherited Destroy;
end;

procedure GetObrokiRsTp.SetObroki(Index: Integer; const AArrayOfObrokTp: ArrayOfObrokTp);
begin
  FObroki := AArrayOfObrokTp;
  FObroki_Specified := True;
end;

function GetObrokiRsTp.Obroki_Specified(Index: Integer): boolean;
begin
  Result := FObroki_Specified;
end;

destructor GetNovaNarocilaRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.NarociloTp>(FNarocila);
  System.SetLength(FNarocila, 0);
  inherited Destroy;
end;

procedure GetNovaNarocilaRsTp.SetNarocila(Index: Integer; const AArrayOfNarociloTp: ArrayOfNarociloTp);
begin
  FNarocila := AArrayOfNarociloTp;
  FNarocila_Specified := True;
end;

function GetNovaNarocilaRsTp.Narocila_Specified(Index: Integer): boolean;
begin
  Result := FNarocila_Specified;
end;

destructor GetProstorRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.ProstorTp>(FPROSTOR);
  System.SetLength(FPROSTOR, 0);
  inherited Destroy;
end;

procedure GetProstorRsTp.SetPROSTOR(Index: Integer; const AArrayOfProstorTp: ArrayOfProstorTp);
begin
  FPROSTOR := AArrayOfProstorTp;
  FPROSTOR_Specified := True;
end;

function GetProstorRsTp.PROSTOR_Specified(Index: Integer): boolean;
begin
  Result := FPROSTOR_Specified;
end;

destructor GetKartprijRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.KartprijTp>(FKARTPRIJ);
  System.SetLength(FKARTPRIJ, 0);
  inherited Destroy;
end;

procedure GetKartprijRsTp.SetKARTPRIJ(Index: Integer; const AArrayOfKartprijTp: ArrayOfKartprijTp);
begin
  FKARTPRIJ := AArrayOfKartprijTp;
  FKARTPRIJ_Specified := True;
end;

function GetKartprijRsTp.KARTPRIJ_Specified(Index: Integer): boolean;
begin
  Result := FKARTPRIJ_Specified;
end;

destructor GetPartnerRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.PartnerTp>(FPARTNER);
  System.SetLength(FPARTNER, 0);
  inherited Destroy;
end;

procedure GetPartnerRsTp.SetPARTNER(Index: Integer; const AArrayOfPartnerTp: ArrayOfPartnerTp);
begin
  FPARTNER := AArrayOfPartnerTp;
  FPARTNER_Specified := True;
end;

function GetPartnerRsTp.PARTNER_Specified(Index: Integer): boolean;
begin
  Result := FPARTNER_Specified;
end;

destructor GetListaSkupinRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.ListaSkupinTp>(FSkupine);
  System.SetLength(FSkupine, 0);
  inherited Destroy;
end;

procedure GetListaSkupinRsTp.SetSkupine(Index: Integer; const AArrayOfListaSkupinTp: ArrayOfListaSkupinTp);
begin
  FSkupine := AArrayOfListaSkupinTp;
  FSkupine_Specified := True;
end;

function GetListaSkupinRsTp.Skupine_Specified(Index: Integer): boolean;
begin
  Result := FSkupine_Specified;
end;

destructor GetPrijavaRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FPRIJAVA);
  inherited Destroy;
end;

procedure GetPrijavaRsTp.SetPRIJAVA(Index: Integer; const APrijavaTp: PrijavaTp);
begin
  FPRIJAVA := APrijavaTp;
  FPRIJAVA_Specified := True;
end;

function GetPrijavaRsTp.PRIJAVA_Specified(Index: Integer): boolean;
begin
  Result := FPRIJAVA_Specified;
end;

destructor GetRacuniSeznamRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.RacunSeznamTp>(FRacuni);
  System.SetLength(FRacuni, 0);
  inherited Destroy;
end;

procedure GetRacuniSeznamRsTp.SetRacuni(Index: Integer; const AArrayOfRacunSeznamTp: ArrayOfRacunSeznamTp);
begin
  FRacuni := AArrayOfRacunSeznamTp;
  FRacuni_Specified := True;
end;

function GetRacuniSeznamRsTp.Racuni_Specified(Index: Integer): boolean;
begin
  Result := FRacuni_Specified;
end;

destructor PrioritetaProjektaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure PrioritetaProjektaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function PrioritetaProjektaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure PrioritetaProjektaTp.SetCAPTION(Index: Integer; const Astring: string);
begin
  FCAPTION := Astring;
  FCAPTION_Specified := True;
end;

function PrioritetaProjektaTp.CAPTION_Specified(Index: Integer): boolean;
begin
  Result := FCAPTION_Specified;
end;

destructor OsebaTokenRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FosebaId);
  System.SysUtils.FreeAndNil(FstrmId);
  inherited Destroy;
end;

procedure OsebaTokenRsTp.Setfault(Index: Integer; const Astring: string);
begin
  Ffault := Astring;
  Ffault_Specified := True;
end;

function OsebaTokenRsTp.fault_Specified(Index: Integer): boolean;
begin
  Result := Ffault_Specified;
end;

procedure OsebaTokenRsTp.Setnaziv(Index: Integer; const Astring: string);
begin
  Fnaziv := Astring;
  Fnaziv_Specified := True;
end;

function OsebaTokenRsTp.naziv_Specified(Index: Integer): boolean;
begin
  Result := Fnaziv_Specified;
end;

procedure OsebaTokenRsTp.Settoken(Index: Integer; const Astring: string);
begin
  Ftoken := Astring;
  Ftoken_Specified := True;
end;

function OsebaTokenRsTp.token_Specified(Index: Integer): boolean;
begin
  Result := Ftoken_Specified;
end;

procedure OsebaTokenRsTp.Setpin(Index: Integer; const Astring: string);
begin
  Fpin := Astring;
  Fpin_Specified := True;
end;

function OsebaTokenRsTp.pin_Specified(Index: Integer): boolean;
begin
  Result := Fpin_Specified;
end;

procedure OsebaTokenRsTp.Setopozorilo(Index: Integer; const Astring: string);
begin
  Fopozorilo := Astring;
  Fopozorilo_Specified := True;
end;

function OsebaTokenRsTp.opozorilo_Specified(Index: Integer): boolean;
begin
  Result := Fopozorilo_Specified;
end;

procedure OsebaTokenRsTp.Setmodel(Index: Integer; const Astring: string);
begin
  Fmodel := Astring;
  Fmodel_Specified := True;
end;

function OsebaTokenRsTp.model_Specified(Index: Integer): boolean;
begin
  Result := Fmodel_Specified;
end;

destructor OsebaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDATUM_SPREMEMBEPIN);
  System.SysUtils.FreeAndNil(FPRIORITETA);
  System.SysUtils.FreeAndNil(FVELJAVNOSTPIN);
  inherited Destroy;
end;

procedure OsebaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function OsebaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure OsebaTp.SetKARTICA_ID(Index: Integer; const Astring: string);
begin
  FKARTICA_ID := Astring;
  FKARTICA_ID_Specified := True;
end;

function OsebaTp.KARTICA_ID_Specified(Index: Integer): boolean;
begin
  Result := FKARTICA_ID_Specified;
end;

procedure OsebaTp.SetKARTICA2_ID(Index: Integer; const Astring: string);
begin
  FKARTICA2_ID := Astring;
  FKARTICA2_ID_Specified := True;
end;

function OsebaTp.KARTICA2_ID_Specified(Index: Integer): boolean;
begin
  Result := FKARTICA2_ID_Specified;
end;

procedure OsebaTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function OsebaTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure OsebaTp.SetODDELEK(Index: Integer; const Astring: string);
begin
  FODDELEK := Astring;
  FODDELEK_Specified := True;
end;

function OsebaTp.ODDELEK_Specified(Index: Integer): boolean;
begin
  Result := FODDELEK_Specified;
end;

procedure OsebaTp.SetPIN(Index: Integer; const Astring: string);
begin
  FPIN := Astring;
  FPIN_Specified := True;
end;

function OsebaTp.PIN_Specified(Index: Integer): boolean;
begin
  Result := FPIN_Specified;
end;

procedure OsebaTp.SetPRIVILEGIJI(Index: Integer; const Astring: string);
begin
  FPRIVILEGIJI := Astring;
  FPRIVILEGIJI_Specified := True;
end;

function OsebaTp.PRIVILEGIJI_Specified(Index: Integer): boolean;
begin
  Result := FPRIVILEGIJI_Specified;
end;

procedure OsebaTp.SetPravice(Index: Integer; const Astring: string);
begin
  FPravice := Astring;
  FPravice_Specified := True;
end;

function OsebaTp.Pravice_Specified(Index: Integer): boolean;
begin
  Result := FPravice_Specified;
end;

procedure OsebaTp.SetUPORABNISKO_IME(Index: Integer; const Astring: string);
begin
  FUPORABNISKO_IME := Astring;
  FUPORABNISKO_IME_Specified := True;
end;

function OsebaTp.UPORABNISKO_IME_Specified(Index: Integer): boolean;
begin
  Result := FUPORABNISKO_IME_Specified;
end;

destructor MobileSetupsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FTOCILNICA_ID);
  inherited Destroy;
end;

procedure MobileSetupsTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function MobileSetupsTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure MobileSetupsTp.SetFIRMA(Index: Integer; const Astring: string);
begin
  FFIRMA := Astring;
  FFIRMA_Specified := True;
end;

function MobileSetupsTp.FIRMA_Specified(Index: Integer): boolean;
begin
  Result := FFIRMA_Specified;
end;

procedure MobileSetupsTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function MobileSetupsTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

destructor MizaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FRAJON);
  System.SysUtils.FreeAndNil(FZAP);
  inherited Destroy;
end;

procedure MizaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function MizaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure MizaTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function MizaTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

destructor MobileSetupTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.MizaTp>(FMOBILE_SETUP_MIZE);
  System.SetLength(FMOBILE_SETUP_MIZE, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.OsebaTp>(FOSEBE);
  System.SetLength(FOSEBE, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.PrioritetaProjektaTp>(FPRIORITETE_PROJEKTOV);
  System.SetLength(FPRIORITETE_PROJEKTOV, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.TarifaTp>(FTARIFE);
  System.SetLength(FTARIFE, 0);
  System.SysUtils.FreeAndNil(FFISKALIZACIJA);
  System.SysUtils.FreeAndNil(FF_POSLOVNI_PROSTOR_ID);
  System.SysUtils.FreeAndNil(FF_POS_ID);
  System.SysUtils.FreeAndNil(FKUHINJA_ID);
  System.SysUtils.FreeAndNil(FTIPKE_POS_ID);
  System.SysUtils.FreeAndNil(FTOCILNICA_ID);
  inherited Destroy;
end;

procedure MobileSetupTp.SetCERTIFIKAT(Index: Integer; const Astring: string);
begin
  FCERTIFIKAT := Astring;
  FCERTIFIKAT_Specified := True;
end;

function MobileSetupTp.CERTIFIKAT_Specified(Index: Integer): boolean;
begin
  Result := FCERTIFIKAT_Specified;
end;

procedure MobileSetupTp.SetCertBytes(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
begin
  FCertBytes := ATByteSOAPArray;
  FCertBytes_Specified := True;
end;

function MobileSetupTp.CertBytes_Specified(Index: Integer): boolean;
begin
  Result := FCertBytes_Specified;
end;

procedure MobileSetupTp.SetCertPass(Index: Integer; const Astring: string);
begin
  FCertPass := Astring;
  FCertPass_Specified := True;
end;

function MobileSetupTp.CertPass_Specified(Index: Integer): boolean;
begin
  Result := FCertPass_Specified;
end;

procedure MobileSetupTp.SetDAVCNA_ZAFURS(Index: Integer; const Astring: string);
begin
  FDAVCNA_ZAFURS := Astring;
  FDAVCNA_ZAFURS_Specified := True;
end;

function MobileSetupTp.DAVCNA_ZAFURS_Specified(Index: Integer): boolean;
begin
  Result := FDAVCNA_ZAFURS_Specified;
end;

procedure MobileSetupTp.SetDDVPODJETJA(Index: Integer; const Astring: string);
begin
  FDDVPODJETJA := Astring;
  FDDVPODJETJA_Specified := True;
end;

function MobileSetupTp.DDVPODJETJA_Specified(Index: Integer): boolean;
begin
  Result := FDDVPODJETJA_Specified;
end;

procedure MobileSetupTp.SetESCALIGNCENTER(Index: Integer; const Astring: string);
begin
  FESCALIGNCENTER := Astring;
  FESCALIGNCENTER_Specified := True;
end;

function MobileSetupTp.ESCALIGNCENTER_Specified(Index: Integer): boolean;
begin
  Result := FESCALIGNCENTER_Specified;
end;

procedure MobileSetupTp.SetESCALIGNLEFT(Index: Integer; const Astring: string);
begin
  FESCALIGNLEFT := Astring;
  FESCALIGNLEFT_Specified := True;
end;

function MobileSetupTp.ESCALIGNLEFT_Specified(Index: Integer): boolean;
begin
  Result := FESCALIGNLEFT_Specified;
end;

procedure MobileSetupTp.SetESCALIGNRIGHT(Index: Integer; const Astring: string);
begin
  FESCALIGNRIGHT := Astring;
  FESCALIGNRIGHT_Specified := True;
end;

function MobileSetupTp.ESCALIGNRIGHT_Specified(Index: Integer): boolean;
begin
  Result := FESCALIGNRIGHT_Specified;
end;

procedure MobileSetupTp.SetESCBOLDOFF(Index: Integer; const Astring: string);
begin
  FESCBOLDOFF := Astring;
  FESCBOLDOFF_Specified := True;
end;

function MobileSetupTp.ESCBOLDOFF_Specified(Index: Integer): boolean;
begin
  Result := FESCBOLDOFF_Specified;
end;

procedure MobileSetupTp.SetESCBOLDON(Index: Integer; const Astring: string);
begin
  FESCBOLDON := Astring;
  FESCBOLDON_Specified := True;
end;

function MobileSetupTp.ESCBOLDON_Specified(Index: Integer): boolean;
begin
  Result := FESCBOLDON_Specified;
end;

procedure MobileSetupTp.SetESCCPI16(Index: Integer; const Astring: string);
begin
  FESCCPI16 := Astring;
  FESCCPI16_Specified := True;
end;

function MobileSetupTp.ESCCPI16_Specified(Index: Integer): boolean;
begin
  Result := FESCCPI16_Specified;
end;

procedure MobileSetupTp.SetESCCPI20(Index: Integer; const Astring: string);
begin
  FESCCPI20 := Astring;
  FESCCPI20_Specified := True;
end;

function MobileSetupTp.ESCCPI20_Specified(Index: Integer): boolean;
begin
  Result := FESCCPI20_Specified;
end;

procedure MobileSetupTp.SetESCCUT(Index: Integer; const Astring: string);
begin
  FESCCUT := Astring;
  FESCCUT_Specified := True;
end;

function MobileSetupTp.ESCCUT_Specified(Index: Integer): boolean;
begin
  Result := FESCCUT_Specified;
end;

procedure MobileSetupTp.SetESCEOL(Index: Integer; const Astring: string);
begin
  FESCEOL := Astring;
  FESCEOL_Specified := True;
end;

function MobileSetupTp.ESCEOL_Specified(Index: Integer): boolean;
begin
  Result := FESCEOL_Specified;
end;

procedure MobileSetupTp.SetESCINITPRINT(Index: Integer; const Astring: string);
begin
  FESCINITPRINT := Astring;
  FESCINITPRINT_Specified := True;
end;

function MobileSetupTp.ESCINITPRINT_Specified(Index: Integer): boolean;
begin
  Result := FESCINITPRINT_Specified;
end;

procedure MobileSetupTp.SetESCINVERSEOFF(Index: Integer; const Astring: string);
begin
  FESCINVERSEOFF := Astring;
  FESCINVERSEOFF_Specified := True;
end;

function MobileSetupTp.ESCINVERSEOFF_Specified(Index: Integer): boolean;
begin
  Result := FESCINVERSEOFF_Specified;
end;

procedure MobileSetupTp.SetESCINVERSEON(Index: Integer; const Astring: string);
begin
  FESCINVERSEON := Astring;
  FESCINVERSEON_Specified := True;
end;

function MobileSetupTp.ESCINVERSEON_Specified(Index: Integer): boolean;
begin
  Result := FESCINVERSEON_Specified;
end;

procedure MobileSetupTp.SetESCNEWLINE(Index: Integer; const Astring: string);
begin
  FESCNEWLINE := Astring;
  FESCNEWLINE_Specified := True;
end;

function MobileSetupTp.ESCNEWLINE_Specified(Index: Integer): boolean;
begin
  Result := FESCNEWLINE_Specified;
end;

procedure MobileSetupTp.SetESCRESET(Index: Integer; const Astring: string);
begin
  FESCRESET := Astring;
  FESCRESET_Specified := True;
end;

function MobileSetupTp.ESCRESET_Specified(Index: Integer): boolean;
begin
  Result := FESCRESET_Specified;
end;

procedure MobileSetupTp.SetESCUNDERLINEOFF(Index: Integer; const Astring: string);
begin
  FESCUNDERLINEOFF := Astring;
  FESCUNDERLINEOFF_Specified := True;
end;

function MobileSetupTp.ESCUNDERLINEOFF_Specified(Index: Integer): boolean;
begin
  Result := FESCUNDERLINEOFF_Specified;
end;

procedure MobileSetupTp.SetESCUNDERLINEON(Index: Integer; const Astring: string);
begin
  FESCUNDERLINEON := Astring;
  FESCUNDERLINEON_Specified := True;
end;

function MobileSetupTp.ESCUNDERLINEON_Specified(Index: Integer): boolean;
begin
  Result := FESCUNDERLINEON_Specified;
end;

procedure MobileSetupTp.SetESCWIDTH2XOFF(Index: Integer; const Astring: string);
begin
  FESCWIDTH2XOFF := Astring;
  FESCWIDTH2XOFF_Specified := True;
end;

function MobileSetupTp.ESCWIDTH2XOFF_Specified(Index: Integer): boolean;
begin
  Result := FESCWIDTH2XOFF_Specified;
end;

procedure MobileSetupTp.SetESCWIDTH2XON(Index: Integer; const Astring: string);
begin
  FESCWIDTH2XON := Astring;
  FESCWIDTH2XON_Specified := True;
end;

function MobileSetupTp.ESCWIDTH2XON_Specified(Index: Integer): boolean;
begin
  Result := FESCWIDTH2XON_Specified;
end;

procedure MobileSetupTp.SetFIRMA(Index: Integer; const Astring: string);
begin
  FFIRMA := Astring;
  FFIRMA_Specified := True;
end;

function MobileSetupTp.FIRMA_Specified(Index: Integer): boolean;
begin
  Result := FFIRMA_Specified;
end;

procedure MobileSetupTp.SetHTCOLOR(Index: Integer; const Astring: string);
begin
  FHTCOLOR := Astring;
  FHTCOLOR_Specified := True;
end;

function MobileSetupTp.HTCOLOR_Specified(Index: Integer): boolean;
begin
  Result := FHTCOLOR_Specified;
end;

procedure MobileSetupTp.SetHTSTYLENAME(Index: Integer; const Astring: string);
begin
  FHTSTYLENAME := Astring;
  FHTSTYLENAME_Specified := True;
end;

function MobileSetupTp.HTSTYLENAME_Specified(Index: Integer): boolean;
begin
  Result := FHTSTYLENAME_Specified;
end;

procedure MobileSetupTp.SetKKARTICEVPLACILIH(Index: Integer; const Astring: string);
begin
  FKKARTICEVPLACILIH := Astring;
  FKKARTICEVPLACILIH_Specified := True;
end;

function MobileSetupTp.KKARTICEVPLACILIH_Specified(Index: Integer): boolean;
begin
  Result := FKKARTICEVPLACILIH_Specified;
end;

procedure MobileSetupTp.SetMOBILE_NAZIV(Index: Integer; const Astring: string);
begin
  FMOBILE_NAZIV := Astring;
  FMOBILE_NAZIV_Specified := True;
end;

function MobileSetupTp.MOBILE_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FMOBILE_NAZIV_Specified;
end;

procedure MobileSetupTp.SetMOBILE_SETUP_MIZE(Index: Integer; const AArrayOfMizaTp: ArrayOfMizaTp);
begin
  FMOBILE_SETUP_MIZE := AArrayOfMizaTp;
  FMOBILE_SETUP_MIZE_Specified := True;
end;

function MobileSetupTp.MOBILE_SETUP_MIZE_Specified(Index: Integer): boolean;
begin
  Result := FMOBILE_SETUP_MIZE_Specified;
end;

procedure MobileSetupTp.SetMOBILE_SETUP_PLACILA(Index: Integer; const AArrayOfInt: ArrayOfInt);
begin
  FMOBILE_SETUP_PLACILA := AArrayOfInt;
  FMOBILE_SETUP_PLACILA_Specified := True;
end;

function MobileSetupTp.MOBILE_SETUP_PLACILA_Specified(Index: Integer): boolean;
begin
  Result := FMOBILE_SETUP_PLACILA_Specified;
end;

procedure MobileSetupTp.SetMOBINI(Index: Integer; const Astring: string);
begin
  FMOBINI := Astring;
  FMOBINI_Specified := True;
end;

function MobileSetupTp.MOBINI_Specified(Index: Integer): boolean;
begin
  Result := FMOBINI_Specified;
end;

procedure MobileSetupTp.SetMOBINI2(Index: Integer; const Astring: string);
begin
  FMOBINI2 := Astring;
  FMOBINI2_Specified := True;
end;

function MobileSetupTp.MOBINI2_Specified(Index: Integer): boolean;
begin
  Result := FMOBINI2_Specified;
end;

procedure MobileSetupTp.SetNASLOVPODJETJA(Index: Integer; const Astring: string);
begin
  FNASLOVPODJETJA := Astring;
  FNASLOVPODJETJA_Specified := True;
end;

function MobileSetupTp.NASLOVPODJETJA_Specified(Index: Integer): boolean;
begin
  Result := FNASLOVPODJETJA_Specified;
end;

procedure MobileSetupTp.SetNASLOVPRODAJNEGA(Index: Integer; const Astring: string);
begin
  FNASLOVPRODAJNEGA := Astring;
  FNASLOVPRODAJNEGA_Specified := True;
end;

function MobileSetupTp.NASLOVPRODAJNEGA_Specified(Index: Integer): boolean;
begin
  Result := FNASLOVPRODAJNEGA_Specified;
end;

procedure MobileSetupTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function MobileSetupTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure MobileSetupTp.SetNAZIVPODJETJA(Index: Integer; const Astring: string);
begin
  FNAZIVPODJETJA := Astring;
  FNAZIVPODJETJA_Specified := True;
end;

function MobileSetupTp.NAZIVPODJETJA_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVPODJETJA_Specified;
end;

procedure MobileSetupTp.SetNAZIVPRODAJNEGAMESTA(Index: Integer; const Astring: string);
begin
  FNAZIVPRODAJNEGAMESTA := Astring;
  FNAZIVPRODAJNEGAMESTA_Specified := True;
end;

function MobileSetupTp.NAZIVPRODAJNEGAMESTA_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVPRODAJNEGAMESTA_Specified;
end;

procedure MobileSetupTp.SetNAZIVSTREGELVASJE(Index: Integer; const Astring: string);
begin
  FNAZIVSTREGELVASJE := Astring;
  FNAZIVSTREGELVASJE_Specified := True;
end;

function MobileSetupTp.NAZIVSTREGELVASJE_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVSTREGELVASJE_Specified;
end;

procedure MobileSetupTp.SetNAZIVZAHVALA1(Index: Integer; const Astring: string);
begin
  FNAZIVZAHVALA1 := Astring;
  FNAZIVZAHVALA1_Specified := True;
end;

function MobileSetupTp.NAZIVZAHVALA1_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVZAHVALA1_Specified;
end;

procedure MobileSetupTp.SetNAZIVZAHVALA2(Index: Integer; const Astring: string);
begin
  FNAZIVZAHVALA2 := Astring;
  FNAZIVZAHVALA2_Specified := True;
end;

function MobileSetupTp.NAZIVZAHVALA2_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVZAHVALA2_Specified;
end;

procedure MobileSetupTp.SetNAZIVZAHVALA3(Index: Integer; const Astring: string);
begin
  FNAZIVZAHVALA3 := Astring;
  FNAZIVZAHVALA3_Specified := True;
end;

function MobileSetupTp.NAZIVZAHVALA3_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVZAHVALA3_Specified;
end;

procedure MobileSetupTp.SetNAZIVZAHVALA4(Index: Integer; const Astring: string);
begin
  FNAZIVZAHVALA4 := Astring;
  FNAZIVZAHVALA4_Specified := True;
end;

function MobileSetupTp.NAZIVZAHVALA4_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVZAHVALA4_Specified;
end;

procedure MobileSetupTp.SetNFCPRIJAVA(Index: Integer; const Astring: string);
begin
  FNFCPRIJAVA := Astring;
  FNFCPRIJAVA_Specified := True;
end;

function MobileSetupTp.NFCPRIJAVA_Specified(Index: Integer): boolean;
begin
  Result := FNFCPRIJAVA_Specified;
end;

procedure MobileSetupTp.SetOBRATPRODAJNEGAMESTA(Index: Integer; const Astring: string);
begin
  FOBRATPRODAJNEGAMESTA := Astring;
  FOBRATPRODAJNEGAMESTA_Specified := True;
end;

function MobileSetupTp.OBRATPRODAJNEGAMESTA_Specified(Index: Integer): boolean;
begin
  Result := FOBRATPRODAJNEGAMESTA_Specified;
end;

procedure MobileSetupTp.SetOSEBE(Index: Integer; const AArrayOfOsebaTp: ArrayOfOsebaTp);
begin
  FOSEBE := AArrayOfOsebaTp;
  FOSEBE_Specified := True;
end;

function MobileSetupTp.OSEBE_Specified(Index: Integer): boolean;
begin
  Result := FOSEBE_Specified;
end;

procedure MobileSetupTp.SetPOPUST99(Index: Integer; const Astring: string);
begin
  FPOPUST99 := Astring;
  FPOPUST99_Specified := True;
end;

function MobileSetupTp.POPUST99_Specified(Index: Integer): boolean;
begin
  Result := FPOPUST99_Specified;
end;

procedure MobileSetupTp.SetPOPUSTIZPIS(Index: Integer; const Astring: string);
begin
  FPOPUSTIZPIS := Astring;
  FPOPUSTIZPIS_Specified := True;
end;

function MobileSetupTp.POPUSTIZPIS_Specified(Index: Integer): boolean;
begin
  Result := FPOPUSTIZPIS_Specified;
end;

procedure MobileSetupTp.SetPOPUSTLOJALNOST(Index: Integer; const Astring: string);
begin
  FPOPUSTLOJALNOST := Astring;
  FPOPUSTLOJALNOST_Specified := True;
end;

function MobileSetupTp.POPUSTLOJALNOST_Specified(Index: Integer): boolean;
begin
  Result := FPOPUSTLOJALNOST_Specified;
end;

procedure MobileSetupTp.SetPRINTER_RACUNI(Index: Integer; const Astring: string);
begin
  FPRINTER_RACUNI := Astring;
  FPRINTER_RACUNI_Specified := True;
end;

function MobileSetupTp.PRINTER_RACUNI_Specified(Index: Integer): boolean;
begin
  Result := FPRINTER_RACUNI_Specified;
end;

procedure MobileSetupTp.SetPRIORITETE_PROJEKTOV(Index: Integer; const AArrayOfPrioritetaProjektaTp: ArrayOfPrioritetaProjektaTp);
begin
  FPRIORITETE_PROJEKTOV := AArrayOfPrioritetaProjektaTp;
  FPRIORITETE_PROJEKTOV_Specified := True;
end;

function MobileSetupTp.PRIORITETE_PROJEKTOV_Specified(Index: Integer): boolean;
begin
  Result := FPRIORITETE_PROJEKTOV_Specified;
end;

procedure MobileSetupTp.SetSTEVILOZNAKOV(Index: Integer; const Astring: string);
begin
  FSTEVILOZNAKOV := Astring;
  FSTEVILOZNAKOV_Specified := True;
end;

function MobileSetupTp.STEVILOZNAKOV_Specified(Index: Integer): boolean;
begin
  Result := FSTEVILOZNAKOV_Specified;
end;

procedure MobileSetupTp.SetTARIFE(Index: Integer; const AArrayOfTarifaTp: ArrayOfTarifaTp);
begin
  FTARIFE := AArrayOfTarifaTp;
  FTARIFE_Specified := True;
end;

function MobileSetupTp.TARIFE_Specified(Index: Integer): boolean;
begin
  Result := FTARIFE_Specified;
end;

procedure MobileSetupTp.SetVNOSPOGRINJKOV(Index: Integer; const Astring: string);
begin
  FVNOSPOGRINJKOV := Astring;
  FVNOSPOGRINJKOV_Specified := True;
end;

function MobileSetupTp.VNOSPOGRINJKOV_Specified(Index: Integer): boolean;
begin
  Result := FVNOSPOGRINJKOV_Specified;
end;

procedure MobileSetupTp.SetZOI_LOKALNO(Index: Integer; const Astring: string);
begin
  FZOI_LOKALNO := Astring;
  FZOI_LOKALNO_Specified := True;
end;

function MobileSetupTp.ZOI_LOKALNO_Specified(Index: Integer): boolean;
begin
  Result := FZOI_LOKALNO_Specified;
end;

procedure MobileSetupTp.SetMOBINI0(Index: Integer; const Astring: string);
begin
  FMOBINI0 := Astring;
  FMOBINI0_Specified := True;
end;

function MobileSetupTp.MOBINI0_Specified(Index: Integer): boolean;
begin
  Result := FMOBINI0_Specified;
end;

destructor IzpisanTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FCAS_IZPISA);
  System.SysUtils.FreeAndNil(FDATUM);
  System.SysUtils.FreeAndNil(FURA);
  inherited Destroy;
end;

procedure IzpisanTp.SetVSEBINA(Index: Integer; const Astring: string);
begin
  FVSEBINA := Astring;
  FVSEBINA_Specified := True;
end;

function IzpisanTp.VSEBINA_Specified(Index: Integer): boolean;
begin
  Result := FVSEBINA_Specified;
end;

destructor NarediObracunRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure NarediObracunRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function NarediObracunRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure NarediObracunRqTp.SetReportFormat(Index: Integer; const Astring: string);
begin
  FReportFormat := Astring;
  FReportFormat_Specified := True;
end;

function NarediObracunRqTp.ReportFormat_Specified(Index: Integer): boolean;
begin
  Result := FReportFormat_Specified;
end;

destructor GetRacuniRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDO_RACUN_ID);
  System.SysUtils.FreeAndNil(FF_POSLOVNI_PROSTOR_ID);
  System.SysUtils.FreeAndNil(FF_POS_ID);
  System.SysUtils.FreeAndNil(FF_STEVILKA_RACUNA);
  System.SysUtils.FreeAndNil(FOD_DATUM);
  System.SysUtils.FreeAndNil(FOD_RACUN_ID);
  System.SysUtils.FreeAndNil(FOSEBA_ID);
  System.SysUtils.FreeAndNil(FSTATUS);
  System.SysUtils.FreeAndNil(FTOCILNICA_ID);
  inherited Destroy;
end;

procedure GetRacuniRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetRacuniRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor SetStornoRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FSTKOPIJ);
  System.SysUtils.FreeAndNil(FSTORNO_RACUN_ID);
  System.SysUtils.FreeAndNil(FSTORNO_OSEBA_ID);
  System.SysUtils.FreeAndNil(FSTORNO_RAZLOG_ID);
  inherited Destroy;
end;

procedure SetStornoRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function SetStornoRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor NacPlacMakroTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure NacPlacMakroTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function NacPlacMakroTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure NacPlacMakroTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function NacPlacMakroTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure NacPlacMakroTp.SetP1(Index: Integer; const Astring: string);
begin
  FP1 := Astring;
  FP1_Specified := True;
end;

function NacPlacMakroTp.P1_Specified(Index: Integer): boolean;
begin
  Result := FP1_Specified;
end;

procedure NacPlacMakroTp.SetP2(Index: Integer; const Astring: string);
begin
  FP2 := Astring;
  FP2_Specified := True;
end;

function NacPlacMakroTp.P2_Specified(Index: Integer): boolean;
begin
  Result := FP2_Specified;
end;

procedure NacPlacMakroTp.SetP3(Index: Integer; const Astring: string);
begin
  FP3 := Astring;
  FP3_Specified := True;
end;

function NacPlacMakroTp.P3_Specified(Index: Integer): boolean;
begin
  Result := FP3_Specified;
end;

procedure NacPlacMakroTp.SetP4(Index: Integer; const Astring: string);
begin
  FP4 := Astring;
  FP4_Specified := True;
end;

function NacPlacMakroTp.P4_Specified(Index: Integer): boolean;
begin
  Result := FP4_Specified;
end;

destructor NarociloTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FIZVOR_DATUMURA);
  inherited Destroy;
end;

procedure NarociloTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function NarociloTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure NarociloTp.SetMARKER(Index: Integer; const Astring: string);
begin
  FMARKER := Astring;
  FMARKER_Specified := True;
end;

function NarociloTp.MARKER_Specified(Index: Integer): boolean;
begin
  Result := FMARKER_Specified;
end;

destructor ObrokTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure ObrokTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function ObrokTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure ObrokTp.SetIMEGOSTA(Index: Integer; const Astring: string);
begin
  FIMEGOSTA := Astring;
  FIMEGOSTA_Specified := True;
end;

function ObrokTp.IMEGOSTA_Specified(Index: Integer): boolean;
begin
  Result := FIMEGOSTA_Specified;
end;

destructor GetObrokiRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDATUM);
  inherited Destroy;
end;

procedure GetObrokiRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetObrokiRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure KartprijTp.SetIMEGOSTA(Index: Integer; const Astring: string);
begin
  FIMEGOSTA := Astring;
  FIMEGOSTA_Specified := True;
end;

function KartprijTp.IMEGOSTA_Specified(Index: Integer): boolean;
begin
  Result := FIMEGOSTA_Specified;
end;

procedure KartprijTp.SetNAZIVSTORITVE(Index: Integer; const Astring: string);
begin
  FNAZIVSTORITVE := Astring;
  FNAZIVSTORITVE_Specified := True;
end;

function KartprijTp.NAZIVSTORITVE_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVSTORITVE_Specified;
end;

procedure KartprijTp.SetNAZIV_STRM(Index: Integer; const Astring: string);
begin
  FNAZIV_STRM := Astring;
  FNAZIV_STRM_Specified := True;
end;

function KartprijTp.NAZIV_STRM_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_STRM_Specified;
end;

procedure KartprijTp.SetTIP_KART(Index: Integer; const Astring: string);
begin
  FTIP_KART := Astring;
  FTIP_KART_Specified := True;
end;

function KartprijTp.TIP_KART_Specified(Index: Integer): boolean;
begin
  Result := FTIP_KART_Specified;
end;

procedure ProstorTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function ProstorTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

destructor ZdruziRacuneRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure ZdruziRacuneRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function ZdruziRacuneRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure ZdruziRacuneRqTp.SetMARKER(Index: Integer; const Astring: string);
begin
  FMARKER := Astring;
  FMARKER_Specified := True;
end;

function ZdruziRacuneRqTp.MARKER_Specified(Index: Integer): boolean;
begin
  Result := FMARKER_Specified;
end;

procedure ZdruziRacuneRqTp.SetRacuni(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
begin
  FRacuni := AArrayOfInt1;
  FRacuni_Specified := True;
end;

function ZdruziRacuneRqTp.Racuni_Specified(Index: Integer): boolean;
begin
  Result := FRacuni_Specified;
end;

destructor GetKartprijRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDESTINACIJA_ID);
  System.SysUtils.FreeAndNil(FOBRAT_ID);
  System.SysUtils.FreeAndNil(FDAT_ODH_X);
  inherited Destroy;
end;

procedure GetKartprijRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetKartprijRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetKartprijRqTp.SetNajdi(Index: Integer; const Astring: string);
begin
  FNajdi := Astring;
  FNajdi_Specified := True;
end;

function GetKartprijRqTp.Najdi_Specified(Index: Integer): boolean;
begin
  Result := FNajdi_Specified;
end;

procedure GetKartprijRqTp.SetOBRATI(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
begin
  FOBRATI := AArrayOfInt1;
  FOBRATI_Specified := True;
end;

function GetKartprijRqTp.OBRATI_Specified(Index: Integer): boolean;
begin
  Result := FOBRATI_Specified;
end;

destructor GetListaSkupinRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure GetListaSkupinRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetListaSkupinRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetListaSkupinRqTp.SetOBRATI(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
begin
  FOBRATI := AArrayOfInt1;
  FOBRATI_Specified := True;
end;

function GetListaSkupinRqTp.OBRATI_Specified(Index: Integer): boolean;
begin
  Result := FOBRATI_Specified;
end;

destructor GetProstorRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDESTINACIJA_ID);
  System.SysUtils.FreeAndNil(FOBRAT_ID);
  inherited Destroy;
end;

procedure GetProstorRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetProstorRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetProstorRqTp.SetOBRATI(Index: Integer; const AArrayOfInt1: ArrayOfInt1);
begin
  FOBRATI := AArrayOfInt1;
  FOBRATI_Specified := True;
end;

function GetProstorRqTp.OBRATI_Specified(Index: Integer): boolean;
begin
  Result := FOBRATI_Specified;
end;

procedure PrijavaTp.SetIMEGOSTA(Index: Integer; const Astring: string);
begin
  FIMEGOSTA := Astring;
  FIMEGOSTA_Specified := True;
end;

function PrijavaTp.IMEGOSTA_Specified(Index: Integer): boolean;
begin
  Result := FIMEGOSTA_Specified;
end;

procedure PrijavaTp.SetOBRAT_NAZIV(Index: Integer; const Astring: string);
begin
  FOBRAT_NAZIV := Astring;
  FOBRAT_NAZIV_Specified := True;
end;

function PrijavaTp.OBRAT_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FOBRAT_NAZIV_Specified;
end;

procedure PrijavaTp.SetNAZIV_CENIKA(Index: Integer; const Astring: string);
begin
  FNAZIV_CENIKA := Astring;
  FNAZIV_CENIKA_Specified := True;
end;

function PrijavaTp.NAZIV_CENIKA_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_CENIKA_Specified;
end;

destructor GetPrijavaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FCENIK_AI);
  inherited Destroy;
end;

procedure GetPrijavaRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetPrijavaRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure ListaSkupinTp.SetSKUPINA_NAZIV(Index: Integer; const Astring: string);
begin
  FSKUPINA_NAZIV := Astring;
  FSKUPINA_NAZIV_Specified := True;
end;

function ListaSkupinTp.SKUPINA_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FSKUPINA_NAZIV_Specified;
end;

procedure ListaSkupinTp.SetSTORITEV_NAZIV(Index: Integer; const Astring: string);
begin
  FSTORITEV_NAZIV := Astring;
  FSTORITEV_NAZIV_Specified := True;
end;

function ListaSkupinTp.STORITEV_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FSTORITEV_NAZIV_Specified;
end;

destructor RacunSeznamTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FKASIRAL);
  System.SysUtils.FreeAndNil(FSTATUS);
  System.SysUtils.FreeAndNil(FSTORNO_RACUN_ID);
  System.SysUtils.FreeAndNil(FZNESEK);
  inherited Destroy;
end;

procedure RacunSeznamTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function RacunSeznamTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure RacunSeznamTp.SetMARKER(Index: Integer; const Astring: string);
begin
  FMARKER := Astring;
  FMARKER_Specified := True;
end;

function RacunSeznamTp.MARKER_Specified(Index: Integer): boolean;
begin
  Result := FMARKER_Specified;
end;

destructor TarifaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDAVEK_PROC);
  System.SysUtils.FreeAndNil(FMETODA_ID);
  inherited Destroy;
end;

procedure TarifaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TarifaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TarifaTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function TarifaTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure TarifaTp.SetOZNAKA(Index: Integer; const Astring: string);
begin
  FOZNAKA := Astring;
  FOZNAKA_Specified := True;
end;

function TarifaTp.OZNAKA_Specified(Index: Integer): boolean;
begin
  Result := FOZNAKA_Specified;
end;

destructor InkasoOsebeTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FOsebaId);
  System.SysUtils.FreeAndNil(FZnesek);
  inherited Destroy;
end;

procedure InkasoOsebeTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function InkasoOsebeTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure InkasoOsebeTp.SetNaziv(Index: Integer; const Astring: string);
begin
  FNaziv := Astring;
  FNaziv_Specified := True;
end;

function InkasoOsebeTp.Naziv_Specified(Index: Integer): boolean;
begin
  Result := FNaziv_Specified;
end;

procedure InkasoOsebeTp.SetOseba(Index: Integer; const Astring: string);
begin
  FOseba := Astring;
  FOseba_Specified := True;
end;

function InkasoOsebeTp.Oseba_Specified(Index: Integer): boolean;
begin
  Result := FOseba_Specified;
end;

destructor PartnerTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FRABAT);
  inherited Destroy;
end;

procedure PartnerTp.SetDAVCNAST(Index: Integer; const Astring: string);
begin
  FDAVCNAST := Astring;
  FDAVCNAST_Specified := True;
end;

function PartnerTp.DAVCNAST_Specified(Index: Integer): boolean;
begin
  Result := FDAVCNAST_Specified;
end;

procedure PartnerTp.SetNAS_ULICA(Index: Integer; const Astring: string);
begin
  FNAS_ULICA := Astring;
  FNAS_ULICA_Specified := True;
end;

function PartnerTp.NAS_ULICA_Specified(Index: Integer): boolean;
begin
  Result := FNAS_ULICA_Specified;
end;

procedure PartnerTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function PartnerTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure PartnerTp.SetNAZIVPOSTA(Index: Integer; const Astring: string);
begin
  FNAZIVPOSTA := Astring;
  FNAZIVPOSTA_Specified := True;
end;

function PartnerTp.NAZIVPOSTA_Specified(Index: Integer): boolean;
begin
  Result := FNAZIVPOSTA_Specified;
end;

procedure PartnerTp.SetSKLIC(Index: Integer; const Astring: string);
begin
  FSKLIC := Astring;
  FSKLIC_Specified := True;
end;

function PartnerTp.SKLIC_Specified(Index: Integer): boolean;
begin
  Result := FSKLIC_Specified;
end;

destructor CenikVrTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FCENA1);
  System.SysUtils.FreeAndNil(FCENA2);
  System.SysUtils.FreeAndNil(FDAVEK_PROC);
  System.SysUtils.FreeAndNil(FENOTA_MERE_ID);
  System.SysUtils.FreeAndNil(FIZPIS_NAROCILA);
  System.SysUtils.FreeAndNil(FIZVOR_STRM_ID);
  System.SysUtils.FreeAndNil(FNACIN_PRODAJE);
  System.SysUtils.FreeAndNil(FPAKET);
  System.SysUtils.FreeAndNil(FPOLNJENJE);
  System.SysUtils.FreeAndNil(FPLU_CODE);
  System.SysUtils.FreeAndNil(FIZVOR_PRIHODEK_ID);
  inherited Destroy;
end;

procedure CenikVrTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function CenikVrTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure CenikVrTp.SetENOTA_NAZIV(Index: Integer; const Astring: string);
begin
  FENOTA_NAZIV := Astring;
  FENOTA_NAZIV_Specified := True;
end;

function CenikVrTp.ENOTA_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FENOTA_NAZIV_Specified;
end;

procedure CenikVrTp.SetNAZIV_ZA_RAC(Index: Integer; const Astring: string);
begin
  FNAZIV_ZA_RAC := Astring;
  FNAZIV_ZA_RAC_Specified := True;
end;

function CenikVrTp.NAZIV_ZA_RAC_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_ZA_RAC_Specified;
end;

procedure CenikVrTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function CenikVrTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure CenikVrTp.SetSTANDARD(Index: Integer; const Astring: string);
begin
  FSTANDARD := Astring;
  FSTANDARD_Specified := True;
end;

function CenikVrTp.STANDARD_Specified(Index: Integer): boolean;
begin
  Result := FSTANDARD_Specified;
end;

procedure CenikVrTp.SetBAZENITEM(Index: Integer; const Astring: string);
begin
  FBAZENITEM := Astring;
  FBAZENITEM_Specified := True;
end;

function CenikVrTp.BAZENITEM_Specified(Index: Integer): boolean;
begin
  Result := FBAZENITEM_Specified;
end;

destructor CenikVrVrTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FCENA1);
  System.SysUtils.FreeAndNil(FCENA2);
  System.SysUtils.FreeAndNil(FIZVOR_STRM_ID);
  System.SysUtils.FreeAndNil(FKOLICINA);
  System.SysUtils.FreeAndNil(FPOPUST_DANE);
  System.SysUtils.FreeAndNil(FIZVOR_PRIHODEK_ID);
  inherited Destroy;
end;

procedure CenikVrVrTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function CenikVrVrTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor CenikVrCeneTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FCENA1);
  inherited Destroy;
end;

procedure CenikVrCeneTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function CenikVrCeneTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure CenikVrCeneTp.SetDN_ID(Index: Integer; const Astring: string);
begin
  FDN_ID := Astring;
  FDN_ID_Specified := True;
end;

function CenikVrCeneTp.DN_ID_Specified(Index: Integer): boolean;
begin
  Result := FDN_ID_Specified;
end;

destructor GetPartnerRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FPARTNER_ID);
  System.SysUtils.FreeAndNil(FTIP_PARTNER);
  System.SysUtils.FreeAndNil(FSTR_MESTO_ID);
  inherited Destroy;
end;

procedure GetPartnerRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetPartnerRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetPartnerRqTp.SetIskanje(Index: Integer; const Astring: string);
begin
  FIskanje := Astring;
  FIskanje_Specified := True;
end;

function GetPartnerRqTp.Iskanje_Specified(Index: Integer): boolean;
begin
  Result := FIskanje_Specified;
end;

destructor ZamenjajLastnikaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure ZamenjajLastnikaRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function ZamenjajLastnikaRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor RacTBonTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FZNESEK);
  System.SysUtils.FreeAndNil(FRECIPIENT_REFUND_ID);
  System.SysUtils.FreeAndNil(F__OriginalObject);
  inherited Destroy;
end;

procedure RacTBonTp.SetIME(Index: Integer; const Astring: string);
begin
  FIME := Astring;
  FIME_Specified := True;
end;

function RacTBonTp.IME_Specified(Index: Integer): boolean;
begin
  Result := FIME_Specified;
end;

procedure RacTBonTp.SetPRIIMEK(Index: Integer; const Astring: string);
begin
  FPRIIMEK := Astring;
  FPRIIMEK_Specified := True;
end;

function RacTBonTp.PRIIMEK_Specified(Index: Integer): boolean;
begin
  Result := FPRIIMEK_Specified;
end;

procedure RacTBonTp.SetEMSO(Index: Integer; const Astring: string);
begin
  FEMSO := Astring;
  FEMSO_Specified := True;
end;

function RacTBonTp.EMSO_Specified(Index: Integer): boolean;
begin
  Result := FEMSO_Specified;
end;

procedure RacTBonTp.SetJE_DONOR(Index: Integer; const Astring: string);
begin
  FJE_DONOR := Astring;
  FJE_DONOR_Specified := True;
end;

function RacTBonTp.JE_DONOR_Specified(Index: Integer): boolean;
begin
  Result := FJE_DONOR_Specified;
end;

procedure RacTBonTp.SetEDP_DOC_NUM(Index: Integer; const Astring: string);
begin
  FEDP_DOC_NUM := Astring;
  FEDP_DOC_NUM_Specified := True;
end;

function RacTBonTp.EDP_DOC_NUM_Specified(Index: Integer): boolean;
begin
  Result := FEDP_DOC_NUM_Specified;
end;

procedure RacTBonTp.Set__OriginalObject(Index: Integer; const ARacTBonTp: RacTBonTp);
begin
  F__OriginalObject := ARacTBonTp;
  F__OriginalObject_Specified := True;
end;

function RacTBonTp.__OriginalObject_Specified(Index: Integer): boolean;
begin
  Result := F__OriginalObject_Specified;
end;

destructor SetStornoRacunaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FSTORNO_RAZLOG_ID);
  System.SysUtils.FreeAndNil(FNovoNarocilo_UREJAMSTORNO);
  inherited Destroy;
end;

procedure SetStornoRacunaRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function SetStornoRacunaRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor PozicijaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FCENA);
  System.SysUtils.FreeAndNil(FCENA_NABAVNA);
  System.SysUtils.FreeAndNil(FCENIK_ID);
  System.SysUtils.FreeAndNil(FENOTA_PRODAJE_ID);
  System.SysUtils.FreeAndNil(FIZVOR_STRM_ID);
  System.SysUtils.FreeAndNil(FKOLICINA);
  System.SysUtils.FreeAndNil(FKUHINJA_ID);
  System.SysUtils.FreeAndNil(FLOJALNOST_POPUST);
  System.SysUtils.FreeAndNil(FNAROCILO_POSLANO);
  System.SysUtils.FreeAndNil(FNATAKAR_ID);
  System.SysUtils.FreeAndNil(FNIVO4_ID);
  System.SysUtils.FreeAndNil(FPAKET_DISTINCT);
  System.SysUtils.FreeAndNil(FPAKET_NIVO4_ID);
  System.SysUtils.FreeAndNil(FPOS_ID);
  System.SysUtils.FreeAndNil(FSTATUS);
  System.SysUtils.FreeAndNil(FSTATUS_POZ);
  System.SysUtils.FreeAndNil(FSTOPNJA_DAVKA);
  System.SysUtils.FreeAndNil(FTARIFA_ID);
  System.SysUtils.FreeAndNil(FTOCILNICA_ID);
  System.SysUtils.FreeAndNil(FZNESEK);
  System.SysUtils.FreeAndNil(FZNESEK_DAVKA);
  System.SysUtils.FreeAndNil(FZNESEK_LOJALNOST);
  System.SysUtils.FreeAndNil(FZNESEK_POPUST);
  System.SysUtils.FreeAndNil(F__OriginalObject);
  System.SysUtils.FreeAndNil(FPAKET_KOL);
  System.SysUtils.FreeAndNil(FCAS_ZADNJE_SPREMEMBE);
  System.SysUtils.FreeAndNil(FIZVOR_PRIHODEK_ID);
  inherited Destroy;
end;

procedure PozicijaTp.SetDODATNI_OPIS(Index: Integer; const Astring: string);
begin
  FDODATNI_OPIS := Astring;
  FDODATNI_OPIS_Specified := True;
end;

function PozicijaTp.DODATNI_OPIS_Specified(Index: Integer): boolean;
begin
  Result := FDODATNI_OPIS_Specified;
end;

procedure PozicijaTp.SetHOD(Index: Integer; const Astring: string);
begin
  FHOD := Astring;
  FHOD_Specified := True;
end;

function PozicijaTp.HOD_Specified(Index: Integer): boolean;
begin
  Result := FHOD_Specified;
end;

procedure PozicijaTp.Set__OriginalObject(Index: Integer; const APozicijaTp: PozicijaTp);
begin
  F__OriginalObject := APozicijaTp;
  F__OriginalObject_Specified := True;
end;

function PozicijaTp.__OriginalObject_Specified(Index: Integer): boolean;
begin
  Result := F__OriginalObject_Specified;
end;

procedure PozicijaTp.SetBON_ID(Index: Integer; const Astring: string);
begin
  FBON_ID := Astring;
  FBON_ID_Specified := True;
end;

function PozicijaTp.BON_ID_Specified(Index: Integer): boolean;
begin
  Result := FBON_ID_Specified;
end;

destructor GetCenikRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FCENIKGL);
  inherited Destroy;
end;

procedure GetCenikRsTp.SetCENIKGL(Index: Integer; const ACenikGlTp: CenikGlTp);
begin
  FCENIKGL := ACenikGlTp;
  FCENIKGL_Specified := True;
end;

function GetCenikRsTp.CENIKGL_Specified(Index: Integer): boolean;
begin
  Result := FCENIKGL_Specified;
end;

destructor GetNacPlacRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.NacPlacTp>(FNACPLAC);
  System.SetLength(FNACPLAC, 0);
  inherited Destroy;
end;

procedure GetNacPlacRsTp.SetNACPLAC(Index: Integer; const AArrayOfNacPlacTp: ArrayOfNacPlacTp);
begin
  FNACPLAC := AArrayOfNacPlacTp;
  FNACPLAC_Specified := True;
end;

function GetNacPlacRsTp.NACPLAC_Specified(Index: Integer): boolean;
begin
  Result := FNACPLAC_Specified;
end;

destructor CenikGlTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.CenikVrTp>(FCENIKVR);
  System.SetLength(FCENIKVR, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.CenikVrVrTp>(FCENIKVRVR);
  System.SetLength(FCENIKVRVR, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.CenikVrCeneTp>(FCENIKVR_CENE);
  System.SetLength(FCENIKVR_CENE, 0);
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure CenikGlTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function CenikGlTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure CenikGlTp.SetCENIKVR(Index: Integer; const AArrayOfCenikVrTp: ArrayOfCenikVrTp);
begin
  FCENIKVR := AArrayOfCenikVrTp;
  FCENIKVR_Specified := True;
end;

function CenikGlTp.CENIKVR_Specified(Index: Integer): boolean;
begin
  Result := FCENIKVR_Specified;
end;

procedure CenikGlTp.SetCENIKVRVR(Index: Integer; const AArrayOfCenikVrVrTp: ArrayOfCenikVrVrTp);
begin
  FCENIKVRVR := AArrayOfCenikVrVrTp;
  FCENIKVRVR_Specified := True;
end;

function CenikGlTp.CENIKVRVR_Specified(Index: Integer): boolean;
begin
  Result := FCENIKVRVR_Specified;
end;

procedure CenikGlTp.SetCENIKVR_CENE(Index: Integer; const AArrayOfCenikVrCeneTp: ArrayOfCenikVrCeneTp);
begin
  FCENIKVR_CENE := AArrayOfCenikVrCeneTp;
  FCENIKVR_CENE_Specified := True;
end;

function CenikGlTp.CENIKVR_CENE_Specified(Index: Integer): boolean;
begin
  Result := FCENIKVR_CENE_Specified;
end;

destructor GetTimesRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FTimes);
  inherited Destroy;
end;

procedure GetTimesRsTp.SetTimes(Index: Integer; const ATimesTp: TimesTp);
begin
  FTimes := ATimesTp;
  FTimes_Specified := True;
end;

function GetTimesRsTp.Times_Specified(Index: Integer): boolean;
begin
  Result := FTimes_Specified;
end;

destructor KronologijaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDATUM_URA);
  inherited Destroy;
end;

procedure KronologijaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function KronologijaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure KronologijaTp.SetOPIS_OPERACIJE(Index: Integer; const Astring: string);
begin
  FOPIS_OPERACIJE := Astring;
  FOPIS_OPERACIJE_Specified := True;
end;

function KronologijaTp.OPIS_OPERACIJE_Specified(Index: Integer): boolean;
begin
  Result := FOPIS_OPERACIJE_Specified;
end;

destructor GetMobileSetupsRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.MobileSetupsTp>(FMobileSetups);
  System.SetLength(FMobileSetups, 0);
  inherited Destroy;
end;

procedure GetMobileSetupsRsTp.SetMobileSetups(Index: Integer; const AArrayOfMobileSetupsTp: ArrayOfMobileSetupsTp);
begin
  FMobileSetups := AArrayOfMobileSetupsTp;
  FMobileSetups_Specified := True;
end;

function GetMobileSetupsRsTp.MobileSetups_Specified(Index: Integer): boolean;
begin
  Result := FMobileSetups_Specified;
end;

destructor TimesTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FBAZA);
  System.SysUtils.FreeAndNil(FR16F);
  System.SysUtils.FreeAndNil(FR16S);
  inherited Destroy;
end;

procedure TimesTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TimesTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor HitraTipkaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FBARVA);
  System.SysUtils.FreeAndNil(FENOTA_PRODAJE);
  System.SysUtils.FreeAndNil(FKOLICINA);
  System.SysUtils.FreeAndNil(FNIVO4_ID);
  System.SysUtils.FreeAndNil(FSKUPINA_ID);
  System.SysUtils.FreeAndNil(FDODATEK_ID);
  inherited Destroy;
end;

procedure HitraTipkaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function HitraTipkaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure HitraTipkaTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function HitraTipkaTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure HitraTipkaTp.SetSLIKA(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
begin
  FSLIKA := ATByteSOAPArray;
  FSLIKA_Specified := True;
end;

function HitraTipkaTp.SLIKA_Specified(Index: Integer): boolean;
begin
  Result := FSLIKA_Specified;
end;

destructor GetHitreTipkeRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.HitraTipkaTp>(FHITRETIPKE);
  System.SetLength(FHITRETIPKE, 0);
  inherited Destroy;
end;

procedure GetHitreTipkeRsTp.SetHITRETIPKE(Index: Integer; const AArrayOfHitraTipkaTp: ArrayOfHitraTipkaTp);
begin
  FHITRETIPKE := AArrayOfHitraTipkaTp;
  FHITRETIPKE_Specified := True;
end;

function GetHitreTipkeRsTp.HITRETIPKE_Specified(Index: Integer): boolean;
begin
  Result := FHITRETIPKE_Specified;
end;

destructor InsertKronologRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDATUM_URA);
  System.SysUtils.FreeAndNil(FDanUraKasa);
  inherited Destroy;
end;

procedure InsertKronologRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function InsertKronologRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure InsertKronologRqTp.SetOPIS_OPERACIJE(Index: Integer; const Astring: string);
begin
  FOPIS_OPERACIJE := Astring;
  FOPIS_OPERACIJE_Specified := True;
end;

function InsertKronologRqTp.OPIS_OPERACIJE_Specified(Index: Integer): boolean;
begin
  Result := FOPIS_OPERACIJE_Specified;
end;

procedure InsertKronologRqTp.SetrqId(Index: Integer; const Astring: string);
begin
  FrqId := Astring;
  FrqId_Specified := True;
end;

function InsertKronologRqTp.rqId_Specified(Index: Integer): boolean;
begin
  Result := FrqId_Specified;
end;

destructor NacPlacTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FCRM);
  System.SysUtils.FreeAndNil(FFISKALNO);
  System.SysUtils.FreeAndNil(FINKASO);
  System.SysUtils.FreeAndNil(FKUPEC_ID);
  System.SysUtils.FreeAndNil(FSTKOPIJ);
  System.SysUtils.FreeAndNil(FSTORITEV_ID);
  inherited Destroy;
end;

procedure NacPlacTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function NacPlacTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure NacPlacTp.SetF_PLACILO(Index: Integer; const Astring: string);
begin
  FF_PLACILO := Astring;
  FF_PLACILO_Specified := True;
end;

function NacPlacTp.F_PLACILO_Specified(Index: Integer): boolean;
begin
  Result := FF_PLACILO_Specified;
end;

procedure NacPlacTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function NacPlacTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

destructor RacunTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.PlaciloTp>(FRACPLACI);
  System.SetLength(FRACPLACI, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.PozicijaTp>(FRACPOZIC);
  System.SetLength(FRACPOZIC, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.RacTBonTp>(FRACTBON);
  System.SetLength(FRACTBON, 0);
  System.SysUtils.FreeAndNil(FA_TOCKE);
  System.SysUtils.FreeAndNil(FBLAG_ID);
  System.SysUtils.FreeAndNil(FCRM_POPUST);
  System.SysUtils.FreeAndNil(FDATUM);
  System.SysUtils.FreeAndNil(FFISKALIZACIJA);
  System.SysUtils.FreeAndNil(FFISKALNI_RACUN_ID);
  System.SysUtils.FreeAndNil(FF_POSLOVNI_PROSTOR_ID);
  System.SysUtils.FreeAndNil(FF_POS_ID);
  System.SysUtils.FreeAndNil(FF_STEVILKA_RACUNA);
  System.SysUtils.FreeAndNil(FKASIRAL);
  System.SysUtils.FreeAndNil(FLOJALNOST_ID);
  System.SysUtils.FreeAndNil(FPLACANO);
  System.SysUtils.FreeAndNil(FSTATUS);
  System.SysUtils.FreeAndNil(FSTKOPIJ);
  System.SysUtils.FreeAndNil(FSTORNO_ORIGINAL);
  System.SysUtils.FreeAndNil(FSTORNO_OSEBA_ID);
  System.SysUtils.FreeAndNil(FSTORNO_RACUN_ID);
  System.SysUtils.FreeAndNil(FSTPOGRINJKOV);
  System.SysUtils.FreeAndNil(FTIP);
  System.SysUtils.FreeAndNil(FTIP_RACUNA);
  System.SysUtils.FreeAndNil(FTOCILNICA_ID);
  System.SysUtils.FreeAndNil(FURA);
  System.SysUtils.FreeAndNil(FURA_PLACILA);
  System.SysUtils.FreeAndNil(FUREJAMSTORNO);
  System.SysUtils.FreeAndNil(FV_STATUS);
  System.SysUtils.FreeAndNil(FZNESEK);
  System.SysUtils.FreeAndNil(F__OriginalObject);
  System.SysUtils.FreeAndNil(FSTORNO_RAZLOG_ID);
  System.SysUtils.FreeAndNil(FF_DATUMIZDAJE_VKR);
  System.SysUtils.FreeAndNil(FAKCIJA_ID);
  System.SysUtils.FreeAndNil(FTIP_AKCIJE);
  System.SysUtils.FreeAndNil(FBKARTA_ID);
  inherited Destroy;
end;

procedure RacunTp.SetCRM_ID(Index: Integer; const Astring: string);
begin
  FCRM_ID := Astring;
  FCRM_ID_Specified := True;
end;

function RacunTp.CRM_ID_Specified(Index: Integer): boolean;
begin
  Result := FCRM_ID_Specified;
end;

procedure RacunTp.SetCRM_ST_KARTICE(Index: Integer; const Astring: string);
begin
  FCRM_ST_KARTICE := Astring;
  FCRM_ST_KARTICE_Specified := True;
end;

function RacunTp.CRM_ST_KARTICE_Specified(Index: Integer): boolean;
begin
  Result := FCRM_ST_KARTICE_Specified;
end;

procedure RacunTp.SetDN_ID(Index: Integer; const Astring: string);
begin
  FDN_ID := Astring;
  FDN_ID_Specified := True;
end;

function RacunTp.DN_ID_Specified(Index: Integer): boolean;
begin
  Result := FDN_ID_Specified;
end;

procedure RacunTp.SetF_OZNAKA_DU(Index: Integer; const Astring: string);
begin
  FF_OZNAKA_DU := Astring;
  FF_OZNAKA_DU_Specified := True;
end;

function RacunTp.F_OZNAKA_DU_Specified(Index: Integer): boolean;
begin
  Result := FF_OZNAKA_DU_Specified;
end;

procedure RacunTp.SetF_PODPIS(Index: Integer; const Astring: string);
begin
  FF_PODPIS := Astring;
  FF_PODPIS_Specified := True;
end;

function RacunTp.F_PODPIS_Specified(Index: Integer): boolean;
begin
  Result := FF_PODPIS_Specified;
end;

procedure RacunTp.SetF_PRINT_KODA(Index: Integer; const Astring: string);
begin
  FF_PRINT_KODA := Astring;
  FF_PRINT_KODA_Specified := True;
end;

function RacunTp.F_PRINT_KODA_Specified(Index: Integer): boolean;
begin
  Result := FF_PRINT_KODA_Specified;
end;

procedure RacunTp.SetF_PRINT_VRSTA(Index: Integer; const Astring: string);
begin
  FF_PRINT_VRSTA := Astring;
  FF_PRINT_VRSTA_Specified := True;
end;

function RacunTp.F_PRINT_VRSTA_Specified(Index: Integer): boolean;
begin
  Result := FF_PRINT_VRSTA_Specified;
end;

procedure RacunTp.SetMARKER(Index: Integer; const Astring: string);
begin
  FMARKER := Astring;
  FMARKER_Specified := True;
end;

function RacunTp.MARKER_Specified(Index: Integer): boolean;
begin
  Result := FMARKER_Specified;
end;

procedure RacunTp.SetRACPLACI(Index: Integer; const AArrayOfPlaciloTp: ArrayOfPlaciloTp);
begin
  FRACPLACI := AArrayOfPlaciloTp;
  FRACPLACI_Specified := True;
end;

function RacunTp.RACPLACI_Specified(Index: Integer): boolean;
begin
  Result := FRACPLACI_Specified;
end;

procedure RacunTp.SetRACPOZIC(Index: Integer; const AArrayOfPozicijaTp: ArrayOfPozicijaTp);
begin
  FRACPOZIC := AArrayOfPozicijaTp;
  FRACPOZIC_Specified := True;
end;

function RacunTp.RACPOZIC_Specified(Index: Integer): boolean;
begin
  Result := FRACPOZIC_Specified;
end;

procedure RacunTp.Set__OriginalObject(Index: Integer; const ARacunTp: RacunTp);
begin
  F__OriginalObject := ARacunTp;
  F__OriginalObject_Specified := True;
end;

function RacunTp.__OriginalObject_Specified(Index: Integer): boolean;
begin
  Result := F__OriginalObject_Specified;
end;

procedure RacunTp.Set__Data(Index: Integer; const Astring: string);
begin
  F__Data := Astring;
  F__Data_Specified := True;
end;

function RacunTp.__Data_Specified(Index: Integer): boolean;
begin
  Result := F__Data_Specified;
end;

procedure RacunTp.SetLOKATOR(Index: Integer; const Astring: string);
begin
  FLOKATOR := Astring;
  FLOKATOR_Specified := True;
end;

function RacunTp.LOKATOR_Specified(Index: Integer): boolean;
begin
  Result := FLOKATOR_Specified;
end;

procedure RacunTp.SetF_STEVILKA_VKR(Index: Integer; const Astring: string);
begin
  FF_STEVILKA_VKR := Astring;
  FF_STEVILKA_VKR_Specified := True;
end;

function RacunTp.F_STEVILKA_VKR_Specified(Index: Integer): boolean;
begin
  Result := FF_STEVILKA_VKR_Specified;
end;

procedure RacunTp.SetF_SET_VKR(Index: Integer; const Astring: string);
begin
  FF_SET_VKR := Astring;
  FF_SET_VKR_Specified := True;
end;

function RacunTp.F_SET_VKR_Specified(Index: Integer): boolean;
begin
  Result := FF_SET_VKR_Specified;
end;

procedure RacunTp.SetF_SERIAL_VKR(Index: Integer; const Astring: string);
begin
  FF_SERIAL_VKR := Astring;
  FF_SERIAL_VKR_Specified := True;
end;

function RacunTp.F_SERIAL_VKR_Specified(Index: Integer): boolean;
begin
  Result := FF_SERIAL_VKR_Specified;
end;

procedure RacunTp.SetKUPON_ID(Index: Integer; const Astring: string);
begin
  FKUPON_ID := Astring;
  FKUPON_ID_Specified := True;
end;

function RacunTp.KUPON_ID_Specified(Index: Integer): boolean;
begin
  Result := FKUPON_ID_Specified;
end;

procedure RacunTp.SetRACTBON(Index: Integer; const AArrayOfRacTBonTp: ArrayOfRacTBonTp);
begin
  FRACTBON := AArrayOfRacTBonTp;
  FRACTBON_Specified := True;
end;

function RacunTp.RACTBON_Specified(Index: Integer): boolean;
begin
  Result := FRACTBON_Specified;
end;

procedure RacunTp.SetOPOMBA(Index: Integer; const Astring: string);
begin
  FOPOMBA := Astring;
  FOPOMBA_Specified := True;
end;

function RacunTp.OPOMBA_Specified(Index: Integer): boolean;
begin
  Result := FOPOMBA_Specified;
end;

destructor PlaciloTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FAKCIJA_ID);
  System.SysUtils.FreeAndNil(FDATUM);
  System.SysUtils.FreeAndNil(FDAVCNA_ST_PARTNER);
  System.SysUtils.FreeAndNil(FDELNI_ZNESEK);
  System.SysUtils.FreeAndNil(FGOST_PRIJAVA_ID);
  System.SysUtils.FreeAndNil(FKUPEC_ID);
  System.SysUtils.FreeAndNil(FLIKVIDATOR);
  System.SysUtils.FreeAndNil(FPARTNER_ID);
  System.SysUtils.FreeAndNil(FSTATUS);
  System.SysUtils.FreeAndNil(FSTAT_NALOG);
  System.SysUtils.FreeAndNil(FSTR_MESTO);
  System.SysUtils.FreeAndNil(FST_NAROCILNICE);
  System.SysUtils.FreeAndNil(FTECAJ);
  System.SysUtils.FreeAndNil(FTOCILNICA_ID);
  System.SysUtils.FreeAndNil(FURA);
  System.SysUtils.FreeAndNil(FURA_PLACILA);
  System.SysUtils.FreeAndNil(FVALUTA_ID);
  System.SysUtils.FreeAndNil(FVRSTA_REKLAME);
  System.SysUtils.FreeAndNil(FZNESEK);
  System.SysUtils.FreeAndNil(F__OriginalObject);
  System.SysUtils.FreeAndNil(FHIS_CENIK_AI);
  System.SysUtils.FreeAndNil(FNAPITNINA);
  inherited Destroy;
end;

procedure PlaciloTp.SetA_LIKVIDATOR(Index: Integer; const Astring: string);
begin
  FA_LIKVIDATOR := Astring;
  FA_LIKVIDATOR_Specified := True;
end;

function PlaciloTp.A_LIKVIDATOR_Specified(Index: Integer): boolean;
begin
  Result := FA_LIKVIDATOR_Specified;
end;

procedure PlaciloTp.SetA_STAT_NALOG(Index: Integer; const Astring: string);
begin
  FA_STAT_NALOG := Astring;
  FA_STAT_NALOG_Specified := True;
end;

function PlaciloTp.A_STAT_NALOG_Specified(Index: Integer): boolean;
begin
  Result := FA_STAT_NALOG_Specified;
end;

procedure PlaciloTp.SetA_STRM(Index: Integer; const Astring: string);
begin
  FA_STRM := Astring;
  FA_STRM_Specified := True;
end;

function PlaciloTp.A_STRM_Specified(Index: Integer): boolean;
begin
  Result := FA_STRM_Specified;
end;

procedure PlaciloTp.SetA_VRSTA_STROSKA(Index: Integer; const Astring: string);
begin
  FA_VRSTA_STROSKA := Astring;
  FA_VRSTA_STROSKA_Specified := True;
end;

function PlaciloTp.A_VRSTA_STROSKA_Specified(Index: Integer): boolean;
begin
  Result := FA_VRSTA_STROSKA_Specified;
end;

procedure PlaciloTp.SetBON_ID(Index: Integer; const Astring: string);
begin
  FBON_ID := Astring;
  FBON_ID_Specified := True;
end;

function PlaciloTp.BON_ID_Specified(Index: Integer): boolean;
begin
  Result := FBON_ID_Specified;
end;

procedure PlaciloTp.SetDAVCNAST(Index: Integer; const Astring: string);
begin
  FDAVCNAST := Astring;
  FDAVCNAST_Specified := True;
end;

function PlaciloTp.DAVCNAST_Specified(Index: Integer): boolean;
begin
  Result := FDAVCNAST_Specified;
end;

procedure PlaciloTp.SetKUPON_ID(Index: Integer; const Astring: string);
begin
  FKUPON_ID := Astring;
  FKUPON_ID_Specified := True;
end;

function PlaciloTp.KUPON_ID_Specified(Index: Integer): boolean;
begin
  Result := FKUPON_ID_Specified;
end;

procedure PlaciloTp.SetM_REF(Index: Integer; const Astring: string);
begin
  FM_REF := Astring;
  FM_REF_Specified := True;
end;

function PlaciloTp.M_REF_Specified(Index: Integer): boolean;
begin
  Result := FM_REF_Specified;
end;

procedure PlaciloTp.SetNASLOV_PARTNER(Index: Integer; const Astring: string);
begin
  FNASLOV_PARTNER := Astring;
  FNASLOV_PARTNER_Specified := True;
end;

function PlaciloTp.NASLOV_PARTNER_Specified(Index: Integer): boolean;
begin
  Result := FNASLOV_PARTNER_Specified;
end;

procedure PlaciloTp.SetNAZIV_PARTNER(Index: Integer; const Astring: string);
begin
  FNAZIV_PARTNER := Astring;
  FNAZIV_PARTNER_Specified := True;
end;

function PlaciloTp.NAZIV_PARTNER_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_PARTNER_Specified;
end;

procedure PlaciloTp.SetST_KARTICE(Index: Integer; const Astring: string);
begin
  FST_KARTICE := Astring;
  FST_KARTICE_Specified := True;
end;

function PlaciloTp.ST_KARTICE_Specified(Index: Integer): boolean;
begin
  Result := FST_KARTICE_Specified;
end;

procedure PlaciloTp.Set__OriginalObject(Index: Integer; const APlaciloTp: PlaciloTp);
begin
  F__OriginalObject := APlaciloTp;
  F__OriginalObject_Specified := True;
end;

function PlaciloTp.__OriginalObject_Specified(Index: Integer): boolean;
begin
  Result := F__OriginalObject_Specified;
end;

procedure PlaciloTp.SetSTNAROC(Index: Integer; const Astring: string);
begin
  FSTNAROC := Astring;
  FSTNAROC_Specified := True;
end;

function PlaciloTp.STNAROC_Specified(Index: Integer): boolean;
begin
  Result := FSTNAROC_Specified;
end;

destructor LojalnostnaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure LojalnostnaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function LojalnostnaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure LojalnostnaTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function LojalnostnaTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

destructor RequestTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FrqTimestamp);
  inherited Destroy;
end;

procedure RequestTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function RequestTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure RequestTp.SetrqId(Index: Integer; const Astring: string);
begin
  FrqId := Astring;
  FrqId_Specified := True;
end;

function RequestTp.rqId_Specified(Index: Integer): boolean;
begin
  Result := FrqId_Specified;
end;

procedure RequestTp.SettxId(Index: Integer; const Astring: string);
begin
  FtxId := Astring;
  FtxId_Specified := True;
end;

function RequestTp.txId_Specified(Index: Integer): boolean;
begin
  Result := FtxId_Specified;
end;

destructor MbillsSaleRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FAmount);
  inherited Destroy;
end;

procedure MbillsSaleRqTp.SetPodrocje(Index: Integer; const Astring: string);
begin
  FPodrocje := Astring;
  FPodrocje_Specified := True;
end;

function MbillsSaleRqTp.Podrocje_Specified(Index: Integer): boolean;
begin
  Result := FPodrocje_Specified;
end;

procedure MbillsSaleRqTp.SetRacunId(Index: Integer; const Astring: string);
begin
  FRacunId := Astring;
  FRacunId_Specified := True;
end;

function MbillsSaleRqTp.RacunId_Specified(Index: Integer): boolean;
begin
  Result := FRacunId_Specified;
end;

destructor ResponseTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FrsTimestamp);
  inherited Destroy;
end;

procedure ResponseTp.Setfault(Index: Integer; const Astring: string);
begin
  Ffault := Astring;
  Ffault_Specified := True;
end;

function ResponseTp.fault_Specified(Index: Integer): boolean;
begin
  Result := Ffault_Specified;
end;

procedure ResponseTp.SetrsId(Index: Integer; const Astring: string);
begin
  FrsId := Astring;
  FrsId_Specified := True;
end;

function ResponseTp.rsId_Specified(Index: Integer): boolean;
begin
  Result := FrsId_Specified;
end;

procedure MbillsSaleRsTp.SetQrVsebina(Index: Integer; const Astring: string);
begin
  FQrVsebina := Astring;
  FQrVsebina_Specified := True;
end;

function MbillsSaleRsTp.QrVsebina_Specified(Index: Integer): boolean;
begin
  Result := FQrVsebina_Specified;
end;

procedure MbillsSaleRsTp.SetTransactionId(Index: Integer; const Astring: string);
begin
  FTransactionId := Astring;
  FTransactionId_Specified := True;
end;

function MbillsSaleRsTp.TransactionId_Specified(Index: Integer): boolean;
begin
  Result := FTransactionId_Specified;
end;

procedure MbillsFursRqTp.SetPodrocje(Index: Integer; const Astring: string);
begin
  FPodrocje := Astring;
  FPodrocje_Specified := True;
end;

function MbillsFursRqTp.Podrocje_Specified(Index: Integer): boolean;
begin
  Result := FPodrocje_Specified;
end;

procedure MbillsFursRqTp.SetRacunId(Index: Integer; const Astring: string);
begin
  FRacunId := Astring;
  FRacunId_Specified := True;
end;

function MbillsFursRqTp.RacunId_Specified(Index: Integer): boolean;
begin
  Result := FRacunId_Specified;
end;

destructor MbillsVoidRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FAmount);
  inherited Destroy;
end;

procedure MbillsVoidRqTp.SetPodrocje(Index: Integer; const Astring: string);
begin
  FPodrocje := Astring;
  FPodrocje_Specified := True;
end;

function MbillsVoidRqTp.Podrocje_Specified(Index: Integer): boolean;
begin
  Result := FPodrocje_Specified;
end;

procedure MbillsVoidRqTp.SetRacunId(Index: Integer; const Astring: string);
begin
  FRacunId := Astring;
  FRacunId_Specified := True;
end;

function MbillsVoidRqTp.RacunId_Specified(Index: Integer): boolean;
begin
  Result := FRacunId_Specified;
end;

procedure MbillsVoidRqTp.SetTransactionId(Index: Integer; const Astring: string);
begin
  FTransactionId := Astring;
  FTransactionId_Specified := True;
end;

function MbillsVoidRqTp.TransactionId_Specified(Index: Integer): boolean;
begin
  Result := FTransactionId_Specified;
end;

procedure ValuStartPaymentRsTp.SetResultDescription(Index: Integer; const Astring: string);
begin
  FResultDescription := Astring;
  FResultDescription_Specified := True;
end;

function ValuStartPaymentRsTp.ResultDescription_Specified(Index: Integer): boolean;
begin
  Result := FResultDescription_Specified;
end;

procedure ValuStartPaymentRsTp.SetToken(Index: Integer; const Astring: string);
begin
  FToken := Astring;
  FToken_Specified := True;
end;

function ValuStartPaymentRsTp.Token_Specified(Index: Integer): boolean;
begin
  Result := FToken_Specified;
end;

procedure ValuStartPaymentRsTp.SetTransactionReference(Index: Integer; const Astring: string);
begin
  FTransactionReference := Astring;
  FTransactionReference_Specified := True;
end;

function ValuStartPaymentRsTp.TransactionReference_Specified(Index: Integer): boolean;
begin
  Result := FTransactionReference_Specified;
end;

procedure MbillsFursRsTp.SetTransactionId(Index: Integer; const Astring: string);
begin
  FTransactionId := Astring;
  FTransactionId_Specified := True;
end;

function MbillsFursRsTp.TransactionId_Specified(Index: Integer): boolean;
begin
  Result := FTransactionId_Specified;
end;

destructor MbillsRefundRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FAmount);
  inherited Destroy;
end;

procedure MbillsRefundRqTp.SetPodrocje(Index: Integer; const Astring: string);
begin
  FPodrocje := Astring;
  FPodrocje_Specified := True;
end;

function MbillsRefundRqTp.Podrocje_Specified(Index: Integer): boolean;
begin
  Result := FPodrocje_Specified;
end;

procedure MbillsRefundRqTp.SetRacunId(Index: Integer; const Astring: string);
begin
  FRacunId := Astring;
  FRacunId_Specified := True;
end;

function MbillsRefundRqTp.RacunId_Specified(Index: Integer): boolean;
begin
  Result := FRacunId_Specified;
end;

procedure MbillsRefundRqTp.SetTransactionId(Index: Integer; const Astring: string);
begin
  FTransactionId := Astring;
  FTransactionId_Specified := True;
end;

function MbillsRefundRqTp.TransactionId_Specified(Index: Integer): boolean;
begin
  Result := FTransactionId_Specified;
end;

destructor AkcijaNazivRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FTIP_AKCIJE);
  System.SysUtils.FreeAndNil(FPLACILO_ID);
  System.SysUtils.FreeAndNil(FPARTNER_ID_ZAPLACILO);
  System.SysUtils.FreeAndNil(FPOPUST_PROC);
  System.SysUtils.FreeAndNil(FPOPUST_ZNESEK);
  System.SysUtils.FreeAndNil(FMIN_VREDNOST_RACUNA);
  inherited Destroy;
end;

procedure AkcijaNazivRsTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function AkcijaNazivRsTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure AkcijaNazivRsTp.SetTIP_NAZIV(Index: Integer; const Astring: string);
begin
  FTIP_NAZIV := Astring;
  FTIP_NAZIV_Specified := True;
end;

function AkcijaNazivRsTp.TIP_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FTIP_NAZIV_Specified;
end;

destructor AkcijaSetLojalnostRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure AkcijaSetLojalnostRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function AkcijaSetLojalnostRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor AkcijaArtikliRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure AkcijaArtikliRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function AkcijaArtikliRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor AkcijaArtikelTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FIZBIRA);
  System.SysUtils.FreeAndNil(FENOTA_PRODAJE);
  System.SysUtils.FreeAndNil(FKOLICINA);
  System.SysUtils.FreeAndNil(FCENA);
  inherited Destroy;
end;

procedure AkcijaArtikelTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function AkcijaArtikelTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor AkcijaArtikliRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.AkcijaArtikelTp>(FArtikli);
  System.SetLength(FArtikli, 0);
  inherited Destroy;
end;

procedure AkcijaArtikliRsTp.SetArtikli(Index: Integer; const AArrayOfAkcijaArtikelTp: ArrayOfAkcijaArtikelTp);
begin
  FArtikli := AArrayOfAkcijaArtikelTp;
  FArtikli_Specified := True;
end;

function AkcijaArtikliRsTp.Artikli_Specified(Index: Integer): boolean;
begin
  Result := FArtikli_Specified;
end;

destructor AkcijaSetLojalnostRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FRacun);
  inherited Destroy;
end;

procedure AkcijaSetLojalnostRsTp.SetRacun(Index: Integer; const AGetRacunRsTp: GetRacunRsTp);
begin
  FRacun := AGetRacunRsTp;
  FRacun_Specified := True;
end;

function AkcijaSetLojalnostRsTp.Racun_Specified(Index: Integer): boolean;
begin
  Result := FRacun_Specified;
end;

destructor TransactionDetailsWs.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FlastAccessField);
  inherited Destroy;
end;

procedure TransactionDetailsWs.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TransactionDetailsWs.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TransactionDetailsWs.SetacquirerNameField(Index: Integer; const Astring: string);
begin
  FacquirerNameField := Astring;
  FacquirerNameField_Specified := True;
end;

function TransactionDetailsWs.acquirerNameField_Specified(Index: Integer): boolean;
begin
  Result := FacquirerNameField_Specified;
end;

procedure TransactionDetailsWs.SetamountField(Index: Integer; const Astring: string);
begin
  FamountField := Astring;
  FamountField_Specified := True;
end;

function TransactionDetailsWs.amountField_Specified(Index: Integer): boolean;
begin
  Result := FamountField_Specified;
end;

procedure TransactionDetailsWs.SetcardTypeField(Index: Integer; const Astring: string);
begin
  FcardTypeField := Astring;
  FcardTypeField_Specified := True;
end;

function TransactionDetailsWs.cardTypeField_Specified(Index: Integer): boolean;
begin
  Result := FcardTypeField_Specified;
end;

procedure TransactionDetailsWs.SeterrorDescriptionField(Index: Integer; const Astring: string);
begin
  FerrorDescriptionField := Astring;
  FerrorDescriptionField_Specified := True;
end;

function TransactionDetailsWs.errorDescriptionField_Specified(Index: Integer): boolean;
begin
  Result := FerrorDescriptionField_Specified;
end;

procedure TransactionDetailsWs.SetextReferenceIdField(Index: Integer; const Astring: string);
begin
  FextReferenceIdField := Astring;
  FextReferenceIdField_Specified := True;
end;

function TransactionDetailsWs.extReferenceIdField_Specified(Index: Integer): boolean;
begin
  Result := FextReferenceIdField_Specified;
end;

procedure TransactionDetailsWs.SetissuerNameField(Index: Integer; const Astring: string);
begin
  FissuerNameField := Astring;
  FissuerNameField_Specified := True;
end;

function TransactionDetailsWs.issuerNameField_Specified(Index: Integer): boolean;
begin
  Result := FissuerNameField_Specified;
end;

procedure TransactionDetailsWs.SetloyaltyMemberIdField(Index: Integer; const Astring: string);
begin
  FloyaltyMemberIdField := Astring;
  FloyaltyMemberIdField_Specified := True;
end;

function TransactionDetailsWs.loyaltyMemberIdField_Specified(Index: Integer): boolean;
begin
  Result := FloyaltyMemberIdField_Specified;
end;

procedure TransactionDetailsWs.SetmPCodeField(Index: Integer; const Astring: string);
begin
  FmPCodeField := Astring;
  FmPCodeField_Specified := True;
end;

function TransactionDetailsWs.mPCodeField_Specified(Index: Integer): boolean;
begin
  Result := FmPCodeField_Specified;
end;

procedure TransactionDetailsWs.SetreferenceIdField(Index: Integer; const Astring: string);
begin
  FreferenceIdField := Astring;
  FreferenceIdField_Specified := True;
end;

function TransactionDetailsWs.referenceIdField_Specified(Index: Integer): boolean;
begin
  Result := FreferenceIdField_Specified;
end;

procedure TransactionDetailsWs.SettaxNumberField(Index: Integer; const Astring: string);
begin
  FtaxNumberField := Astring;
  FtaxNumberField_Specified := True;
end;

function TransactionDetailsWs.taxNumberField_Specified(Index: Integer): boolean;
begin
  Result := FtaxNumberField_Specified;
end;

procedure TransactionDetailsWs.SetvCNField(Index: Integer; const Astring: string);
begin
  FvCNField := Astring;
  FvCNField_Specified := True;
end;

function TransactionDetailsWs.vCNField_Specified(Index: Integer): boolean;
begin
  Result := FvCNField_Specified;
end;

destructor MonetaRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure MonetaRsTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function MonetaRsTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure MonetaRsTp.Setfault(Index: Integer; const Astring: string);
begin
  Ffault := Astring;
  Ffault_Specified := True;
end;

function MonetaRsTp.fault_Specified(Index: Integer): boolean;
begin
  Result := Ffault_Specified;
end;

destructor GetTransactionStatusRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FTransactionDetails);
  inherited Destroy;
end;

procedure GetTransactionStatusRsTp.SetTransactionDetails(Index: Integer; const ATransactionDetailsWs: TransactionDetailsWs);
begin
  FTransactionDetails := ATransactionDetailsWs;
  FTransactionDetails_Specified := True;
end;

function GetTransactionStatusRsTp.TransactionDetails_Specified(Index: Integer): boolean;
begin
  Result := FTransactionDetails_Specified;
end;

destructor GetTokenRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FValidUntil);
  inherited Destroy;
end;

procedure GetTokenRsTp.SetToken(Index: Integer; const Astring: string);
begin
  FToken := Astring;
  FToken_Specified := True;
end;

function GetTokenRsTp.Token_Specified(Index: Integer): boolean;
begin
  Result := FToken_Specified;
end;

procedure GetTokenRsTp.SetTransactionId(Index: Integer; const Astring: string);
begin
  FTransactionId := Astring;
  FTransactionId_Specified := True;
end;

function GetTokenRsTp.TransactionId_Specified(Index: Integer): boolean;
begin
  Result := FTransactionId_Specified;
end;

destructor CancelTransactionRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FValidUntil);
  inherited Destroy;
end;

procedure CancelTransactionRsTp.SetTransactionId(Index: Integer; const Astring: string);
begin
  FTransactionId := Astring;
  FTransactionId_Specified := True;
end;

function CancelTransactionRsTp.TransactionId_Specified(Index: Integer): boolean;
begin
  Result := FTransactionId_Specified;
end;

destructor KuponAkcijaSaldoRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure KuponAkcijaSaldoRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function KuponAkcijaSaldoRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure KuponAkcijaSaldoRqTp.SetKuponId(Index: Integer; const Astring: string);
begin
  FKuponId := Astring;
  FKuponId_Specified := True;
end;

function KuponAkcijaSaldoRqTp.KuponId_Specified(Index: Integer): boolean;
begin
  Result := FKuponId_Specified;
end;

destructor KuponAkcijaKnjiziRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FZnesekKoriscenja);
  System.SysUtils.FreeAndNil(FZnesek);
  inherited Destroy;
end;

procedure KuponAkcijaKnjiziRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function KuponAkcijaKnjiziRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure KuponAkcijaKnjiziRqTp.SetKuponId(Index: Integer; const Astring: string);
begin
  FKuponId := Astring;
  FKuponId_Specified := True;
end;

function KuponAkcijaKnjiziRqTp.KuponId_Specified(Index: Integer): boolean;
begin
  Result := FKuponId_Specified;
end;

destructor KuponAkcijaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FVrednost);
  System.SysUtils.FreeAndNil(FPartnerId);
  inherited Destroy;
end;

procedure KuponAkcijaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function KuponAkcijaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure KuponAkcijaTp.SetNaziv(Index: Integer; const Astring: string);
begin
  FNaziv := Astring;
  FNaziv_Specified := True;
end;

function KuponAkcijaTp.Naziv_Specified(Index: Integer): boolean;
begin
  Result := FNaziv_Specified;
end;

destructor GetKuponAkcijaRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.KuponAkcijaTp>(FAkcije);
  System.SetLength(FAkcije, 0);
  inherited Destroy;
end;

procedure GetKuponAkcijaRsTp.SetAkcije(Index: Integer; const AArrayOfKuponAkcijaTp: ArrayOfKuponAkcijaTp);
begin
  FAkcije := AArrayOfKuponAkcijaTp;
  FAkcije_Specified := True;
end;

function GetKuponAkcijaRsTp.Akcije_Specified(Index: Integer): boolean;
begin
  Result := FAkcije_Specified;
end;

destructor TbBalanceRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure TbBalanceRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TbBalanceRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TbBalanceRqTp.SetEmso(Index: Integer; const Astring: string);
begin
  FEmso := Astring;
  FEmso_Specified := True;
end;

function TbBalanceRqTp.Emso_Specified(Index: Integer): boolean;
begin
  Result := FEmso_Specified;
end;

procedure TbBalanceRqTp.SetName_(Index: Integer; const Astring: string);
begin
  FName_ := Astring;
  FName__Specified := True;
end;

function TbBalanceRqTp.Name__Specified(Index: Integer): boolean;
begin
  Result := FName__Specified;
end;

procedure TbBalanceRqTp.SetSurname(Index: Integer; const Astring: string);
begin
  FSurname := Astring;
  FSurname_Specified := True;
end;

function TbBalanceRqTp.Surname_Specified(Index: Integer): boolean;
begin
  Result := FSurname_Specified;
end;

procedure TbBalanceRqTp.SetC(Index: Integer; const Astring: string);
begin
  FC := Astring;
  FC_Specified := True;
end;

function TbBalanceRqTp.C_Specified(Index: Integer): boolean;
begin
  Result := FC_Specified;
end;

destructor TBonRefundTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FAmount);
  System.SysUtils.FreeAndNil(FAccommodationFrom);
  System.SysUtils.FreeAndNil(FAccommodationTo);
  inherited Destroy;
end;

procedure TBonRefundTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TBonRefundTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TBonRefundTp.SetFirstName(Index: Integer; const Astring: string);
begin
  FFirstName := Astring;
  FFirstName_Specified := True;
end;

function TBonRefundTp.FirstName_Specified(Index: Integer): boolean;
begin
  Result := FFirstName_Specified;
end;

procedure TBonRefundTp.SetLastName(Index: Integer; const Astring: string);
begin
  FLastName := Astring;
  FLastName_Specified := True;
end;

function TBonRefundTp.LastName_Specified(Index: Integer): boolean;
begin
  Result := FLastName_Specified;
end;

procedure TBonRefundTp.SetEmso(Index: Integer; const Astring: string);
begin
  FEmso := Astring;
  FEmso_Specified := True;
end;

function TBonRefundTp.Emso_Specified(Index: Integer): boolean;
begin
  Result := FEmso_Specified;
end;

procedure TBonRefundTp.SetRecipientId(Index: Integer; const Astring: string);
begin
  FRecipientId := Astring;
  FRecipientId_Specified := True;
end;

function TBonRefundTp.RecipientId_Specified(Index: Integer): boolean;
begin
  Result := FRecipientId_Specified;
end;

destructor TBonRefund21Tp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FAmount);
  inherited Destroy;
end;

procedure TBonRefund21Tp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TBonRefund21Tp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TBonRefund21Tp.SetFirstName(Index: Integer; const Astring: string);
begin
  FFirstName := Astring;
  FFirstName_Specified := True;
end;

function TBonRefund21Tp.FirstName_Specified(Index: Integer): boolean;
begin
  Result := FFirstName_Specified;
end;

procedure TBonRefund21Tp.SetLastName(Index: Integer; const Astring: string);
begin
  FLastName := Astring;
  FLastName_Specified := True;
end;

function TBonRefund21Tp.LastName_Specified(Index: Integer): boolean;
begin
  Result := FLastName_Specified;
end;

procedure TBonRefund21Tp.SetEmso(Index: Integer; const Astring: string);
begin
  FEmso := Astring;
  FEmso_Specified := True;
end;

function TBonRefund21Tp.Emso_Specified(Index: Integer): boolean;
begin
  Result := FEmso_Specified;
end;

procedure TBonRefund21Tp.SetRecipientId(Index: Integer; const Astring: string);
begin
  FRecipientId := Astring;
  FRecipientId_Specified := True;
end;

function TBonRefund21Tp.RecipientId_Specified(Index: Integer): boolean;
begin
  Result := FRecipientId_Specified;
end;

destructor TbStornoRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure TbStornoRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TbStornoRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TbStornoRqTp.SetDocumentNumber(Index: Integer; const Astring: string);
begin
  FDocumentNumber := Astring;
  FDocumentNumber_Specified := True;
end;

function TbStornoRqTp.DocumentNumber_Specified(Index: Integer): boolean;
begin
  Result := FDocumentNumber_Specified;
end;

procedure TbStornoRqTp.SetC(Index: Integer; const Astring: string);
begin
  FC := Astring;
  FC_Specified := True;
end;

function TbStornoRqTp.C_Specified(Index: Integer): boolean;
begin
  Result := FC_Specified;
end;

destructor TBonDocumentTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.TBonRefundTp>(FRefund);
  System.SetLength(FRefund, 0);
  TArray.FreeValues<RosKasa_ceniki_wsdl.TBonRefund21Tp>(FRefund21);
  System.SetLength(FRefund21, 0);
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure TBonDocumentTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function TBonDocumentTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure TBonDocumentTp.SetFRacunId(Index: Integer; const Astring: string);
begin
  FFRacunId := Astring;
  FFRacunId_Specified := True;
end;

function TBonDocumentTp.FRacunId_Specified(Index: Integer): boolean;
begin
  Result := FFRacunId_Specified;
end;

procedure TBonDocumentTp.SetCorrectionForDocument(Index: Integer; const Astring: string);
begin
  FCorrectionForDocument := Astring;
  FCorrectionForDocument_Specified := True;
end;

function TBonDocumentTp.CorrectionForDocument_Specified(Index: Integer): boolean;
begin
  Result := FCorrectionForDocument_Specified;
end;

procedure TBonDocumentTp.SetC(Index: Integer; const Astring: string);
begin
  FC := Astring;
  FC_Specified := True;
end;

function TBonDocumentTp.C_Specified(Index: Integer): boolean;
begin
  Result := FC_Specified;
end;

procedure TBonDocumentTp.SetRefund(Index: Integer; const AArrayOfTBonRefundTp: ArrayOfTBonRefundTp);
begin
  FRefund := AArrayOfTBonRefundTp;
  FRefund_Specified := True;
end;

function TBonDocumentTp.Refund_Specified(Index: Integer): boolean;
begin
  Result := FRefund_Specified;
end;

procedure TBonDocumentTp.SetRefund21(Index: Integer; const AArrayOfTBonRefund21Tp: ArrayOfTBonRefund21Tp);
begin
  FRefund21 := AArrayOfTBonRefund21Tp;
  FRefund21_Specified := True;
end;

function TBonDocumentTp.Refund21_Specified(Index: Integer): boolean;
begin
  Result := FRefund21_Specified;
end;

destructor CrmTockeRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FKAPLJICE);
  inherited Destroy;
end;

destructor BonSaldoRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure BonSaldoRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function BonSaldoRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure BonSaldoRqTp.SetKoda(Index: Integer; const Astring: string);
begin
  FKoda := Astring;
  FKoda_Specified := True;
end;

function BonSaldoRqTp.Koda_Specified(Index: Integer): boolean;
begin
  Result := FKoda_Specified;
end;

destructor BonSaldoRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FSaldo);
  inherited Destroy;
end;

procedure ValuGetPaymentStatusRsTp.SetCustomerMSISDN(Index: Integer; const Astring: string);
begin
  FCustomerMSISDN := Astring;
  FCustomerMSISDN_Specified := True;
end;

function ValuGetPaymentStatusRsTp.CustomerMSISDN_Specified(Index: Integer): boolean;
begin
  Result := FCustomerMSISDN_Specified;
end;

procedure ValuGetPaymentStatusRsTp.SetResultDescription(Index: Integer; const Astring: string);
begin
  FResultDescription := Astring;
  FResultDescription_Specified := True;
end;

function ValuGetPaymentStatusRsTp.ResultDescription_Specified(Index: Integer): boolean;
begin
  Result := FResultDescription_Specified;
end;

destructor CrmInfoRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FGOST_ID);
  System.SysUtils.FreeAndNil(FBAREA);
  System.SysUtils.FreeAndNil(FSTR_MESTO_ID);
  inherited Destroy;
end;

procedure CrmInfoRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function CrmInfoRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure CrmInfoRqTp.SetSTKARTICE(Index: Integer; const Astring: string);
begin
  FSTKARTICE := Astring;
  FSTKARTICE_Specified := True;
end;

function CrmInfoRqTp.STKARTICE_Specified(Index: Integer): boolean;
begin
  Result := FSTKARTICE_Specified;
end;

destructor CrmInfoRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FPOPUST_PROC);
  System.SysUtils.FreeAndNil(FSTANJE);
  inherited Destroy;
end;

procedure CrmInfoRsTp.SetSTKARTICE(Index: Integer; const Astring: string);
begin
  FSTKARTICE := Astring;
  FSTKARTICE_Specified := True;
end;

function CrmInfoRsTp.STKARTICE_Specified(Index: Integer): boolean;
begin
  Result := FSTKARTICE_Specified;
end;

procedure CrmInfoRsTp.SetIME(Index: Integer; const Astring: string);
begin
  FIME := Astring;
  FIME_Specified := True;
end;

function CrmInfoRsTp.IME_Specified(Index: Integer): boolean;
begin
  Result := FIME_Specified;
end;

procedure CrmInfoRsTp.SetPRIIMEK(Index: Integer; const Astring: string);
begin
  FPRIIMEK := Astring;
  FPRIIMEK_Specified := True;
end;

function CrmInfoRsTp.PRIIMEK_Specified(Index: Integer): boolean;
begin
  Result := FPRIIMEK_Specified;
end;

procedure CrmInfoRsTp.SetOPIS(Index: Integer; const Astring: string);
begin
  FOPIS := Astring;
  FOPIS_Specified := True;
end;

function CrmInfoRsTp.OPIS_Specified(Index: Integer): boolean;
begin
  Result := FOPIS_Specified;
end;

destructor BonKnjiziRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FZnesek);
  inherited Destroy;
end;

procedure BonKnjiziRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function BonKnjiziRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure BonKnjiziRqTp.SetKoda(Index: Integer; const Astring: string);
begin
  FKoda := Astring;
  FKoda_Specified := True;
end;

function BonKnjiziRqTp.Koda_Specified(Index: Integer): boolean;
begin
  Result := FKoda_Specified;
end;

destructor KuponSaldoRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure KuponSaldoRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function KuponSaldoRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure KuponSaldoRqTp.SetKuponId(Index: Integer; const Astring: string);
begin
  FKuponId := Astring;
  FKuponId_Specified := True;
end;

function KuponSaldoRqTp.KuponId_Specified(Index: Integer): boolean;
begin
  Result := FKuponId_Specified;
end;

destructor KuponSaldoRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FSaldo);
  inherited Destroy;
end;

destructor KuponKnjiziRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FZnesekKoriscenja);
  inherited Destroy;
end;

procedure KuponKnjiziRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function KuponKnjiziRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure KuponKnjiziRqTp.SetKuponId(Index: Integer; const Astring: string);
begin
  FKuponId := Astring;
  FKuponId_Specified := True;
end;

function KuponKnjiziRqTp.KuponId_Specified(Index: Integer): boolean;
begin
  Result := FKuponId_Specified;
end;

destructor BoniIzdajaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FVrednost);
  System.SysUtils.FreeAndNil(FPrviDanVeljavnosti);
  System.SysUtils.FreeAndNil(FZadnjiDanVeljavnosti);
  System.SysUtils.FreeAndNil(FReportAi);
  inherited Destroy;
end;

procedure BoniIzdajaRqTp.SetTekst(Index: Integer; const Astring: string);
begin
  FTekst := Astring;
  FTekst_Specified := True;
end;

function BoniIzdajaRqTp.Tekst_Specified(Index: Integer): boolean;
begin
  Result := FTekst_Specified;
end;

procedure BoniIzdajaRqTp.SetNazivZaKoga(Index: Integer; const Astring: string);
begin
  FNazivZaKoga := Astring;
  FNazivZaKoga_Specified := True;
end;

function BoniIzdajaRqTp.NazivZaKoga_Specified(Index: Integer): boolean;
begin
  Result := FNazivZaKoga_Specified;
end;

procedure BoniIzdajaRqTp.SetPodrocje(Index: Integer; const Astring: string);
begin
  FPodrocje := Astring;
  FPodrocje_Specified := True;
end;

function BoniIzdajaRqTp.Podrocje_Specified(Index: Integer): boolean;
begin
  Result := FPodrocje_Specified;
end;

procedure BoniIzdajaRqTp.SetRFID(Index: Integer; const Astring: string);
begin
  FRFID := Astring;
  FRFID_Specified := True;
end;

function BoniIzdajaRqTp.RFID_Specified(Index: Integer): boolean;
begin
  Result := FRFID_Specified;
end;

procedure BoniIzdajaRsTp.SetBonId(Index: Integer; const Astring: string);
begin
  FBonId := Astring;
  FBonId_Specified := True;
end;

function BoniIzdajaRsTp.BonId_Specified(Index: Integer): boolean;
begin
  Result := FBonId_Specified;
end;

procedure BoniIzdajaRsTp.SetKoda(Index: Integer; const Astring: string);
begin
  FKoda := Astring;
  FKoda_Specified := True;
end;

function BoniIzdajaRsTp.Koda_Specified(Index: Integer): boolean;
begin
  Result := FKoda_Specified;
end;

procedure BoniIzdajaRsTp.SetVKoda(Index: Integer; const Astring: string);
begin
  FVKoda := Astring;
  FVKoda_Specified := True;
end;

function BoniIzdajaRsTp.VKoda_Specified(Index: Integer): boolean;
begin
  Result := FVKoda_Specified;
end;

procedure BoniIzdajaRsTp.SetBarKoda(Index: Integer; const Astring: string);
begin
  FBarKoda := Astring;
  FBarKoda_Specified := True;
end;

function BoniIzdajaRsTp.BarKoda_Specified(Index: Integer): boolean;
begin
  Result := FBarKoda_Specified;
end;

procedure BoniIzdajaRsTp.SetPdf(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
begin
  FPdf := ATByteSOAPArray;
  FPdf_Specified := True;
end;

function BoniIzdajaRsTp.Pdf_Specified(Index: Integer): boolean;
begin
  Result := FPdf_Specified;
end;

destructor GetReportRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FBlagId);
  System.SysUtils.FreeAndNil(FStrmId);
  System.SysUtils.FreeAndNil(FOsebaId);
  System.SysUtils.FreeAndNil(FIzpisalaId);
  inherited Destroy;
end;

procedure GetReportRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetReportRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetReportRqTp.SetFormat(Index: Integer; const Astring: string);
begin
  FFormat := Astring;
  FFormat_Specified := True;
end;

function GetReportRqTp.Format_Specified(Index: Integer): boolean;
begin
  Result := FFormat_Specified;
end;

procedure GetReportRqTp.SetReport(Index: Integer; const Astring: string);
begin
  FReport := Astring;
  FReport_Specified := True;
end;

function GetReportRqTp.Report_Specified(Index: Integer): boolean;
begin
  Result := FReport_Specified;
end;

destructor DelovniNalogTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FPARTNER_ID);
  inherited Destroy;
end;

procedure DelovniNalogTp.SetDN_ID(Index: Integer; const Astring: string);
begin
  FDN_ID := Astring;
  FDN_ID_Specified := True;
end;

function DelovniNalogTp.DN_ID_Specified(Index: Integer): boolean;
begin
  Result := FDN_ID_Specified;
end;

procedure DelovniNalogTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function DelovniNalogTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure DelovniNalogTp.SetPARTNER_NAZIV(Index: Integer; const Astring: string);
begin
  FPARTNER_NAZIV := Astring;
  FPARTNER_NAZIV_Specified := True;
end;

function DelovniNalogTp.PARTNER_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FPARTNER_NAZIV_Specified;
end;

procedure DelovniNalogTp.SetSTR_MESTO_NAZIV(Index: Integer; const Astring: string);
begin
  FSTR_MESTO_NAZIV := Astring;
  FSTR_MESTO_NAZIV_Specified := True;
end;

function DelovniNalogTp.STR_MESTO_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FSTR_MESTO_NAZIV_Specified;
end;

procedure DelovniNalogTp.SetFIRMA(Index: Integer; const Astring: string);
begin
  FFIRMA := Astring;
  FFIRMA_Specified := True;
end;

function DelovniNalogTp.FIRMA_Specified(Index: Integer): boolean;
begin
  Result := FFIRMA_Specified;
end;

destructor GetSlipEmaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure GetSlipEmaRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetSlipEmaRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetSlipEmaRsTp.SetSTEVILKA_KARTICE(Index: Integer; const Astring: string);
begin
  FSTEVILKA_KARTICE := Astring;
  FSTEVILKA_KARTICE_Specified := True;
end;

function GetSlipEmaRsTp.STEVILKA_KARTICE_Specified(Index: Integer): boolean;
begin
  Result := FSTEVILKA_KARTICE_Specified;
end;

procedure GetSlipEmaRsTp.SetSLIP_PRINT(Index: Integer; const Astring: string);
begin
  FSLIP_PRINT := Astring;
  FSLIP_PRINT_Specified := True;
end;

function GetSlipEmaRsTp.SLIP_PRINT_Specified(Index: Integer): boolean;
begin
  Result := FSLIP_PRINT_Specified;
end;

destructor SetSlipEmaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FSTRM_ID);
  System.SysUtils.FreeAndNil(FZNESEK_SLIP);
  System.SysUtils.FreeAndNil(FZNESEK);
  inherited Destroy;
end;

procedure SetSlipEmaRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function SetSlipEmaRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure SetSlipEmaRqTp.SetSTEVILKA_KARTICE(Index: Integer; const Astring: string);
begin
  FSTEVILKA_KARTICE := Astring;
  FSTEVILKA_KARTICE_Specified := True;
end;

function SetSlipEmaRqTp.STEVILKA_KARTICE_Specified(Index: Integer): boolean;
begin
  Result := FSTEVILKA_KARTICE_Specified;
end;

procedure SetSlipEmaRqTp.SetSLIP_PRINT(Index: Integer; const Astring: string);
begin
  FSLIP_PRINT := Astring;
  FSLIP_PRINT_Specified := True;
end;

function SetSlipEmaRqTp.SLIP_PRINT_Specified(Index: Integer): boolean;
begin
  Result := FSLIP_PRINT_Specified;
end;

procedure SetSlipEmaRqTp.SetUSPELO(Index: Integer; const Astring: string);
begin
  FUSPELO := Astring;
  FUSPELO_Specified := True;
end;

function SetSlipEmaRqTp.USPELO_Specified(Index: Integer): boolean;
begin
  Result := FUSPELO_Specified;
end;

procedure SetSlipEmaRqTp.SetSLIP_PRINTS(Index: Integer; const Astring: string);
begin
  FSLIP_PRINTS := Astring;
  FSLIP_PRINTS_Specified := True;
end;

function SetSlipEmaRqTp.SLIP_PRINTS_Specified(Index: Integer): boolean;
begin
  Result := FSLIP_PRINTS_Specified;
end;

procedure SetSlipEmaRqTp.SetAVTORIZACIJA(Index: Integer; const Astring: string);
begin
  FAVTORIZACIJA := Astring;
  FAVTORIZACIJA_Specified := True;
end;

function SetSlipEmaRqTp.AVTORIZACIJA_Specified(Index: Integer): boolean;
begin
  Result := FAVTORIZACIJA_Specified;
end;

procedure SetSlipEmaRqTp.SetACQTRANSREF(Index: Integer; const Astring: string);
begin
  FACQTRANSREF := Astring;
  FACQTRANSREF_Specified := True;
end;

function SetSlipEmaRqTp.ACQTRANSREF_Specified(Index: Integer): boolean;
begin
  Result := FACQTRANSREF_Specified;
end;

procedure SetSlipEmaRqTp.SetSTTYPE(Index: Integer; const Astring: string);
begin
  FSTTYPE := Astring;
  FSTTYPE_Specified := True;
end;

function SetSlipEmaRqTp.STTYPE_Specified(Index: Integer): boolean;
begin
  Result := FSTTYPE_Specified;
end;

procedure SetSlipEmaRqTp.SetAPPIDENTIFIER(Index: Integer; const Astring: string);
begin
  FAPPIDENTIFIER := Astring;
  FAPPIDENTIFIER_Specified := True;
end;

function SetSlipEmaRqTp.APPIDENTIFIER_Specified(Index: Integer): boolean;
begin
  Result := FAPPIDENTIFIER_Specified;
end;

procedure SetSlipEmaRqTp.SetAUTHREFERENCE(Index: Integer; const Astring: string);
begin
  FAUTHREFERENCE := Astring;
  FAUTHREFERENCE_Specified := True;
end;

function SetSlipEmaRqTp.AUTHREFERENCE_Specified(Index: Integer): boolean;
begin
  Result := FAUTHREFERENCE_Specified;
end;

procedure SetSlipEmaRqTp.SetAUTHNUMBER(Index: Integer; const Astring: string);
begin
  FAUTHNUMBER := Astring;
  FAUTHNUMBER_Specified := True;
end;

function SetSlipEmaRqTp.AUTHNUMBER_Specified(Index: Integer): boolean;
begin
  Result := FAUTHNUMBER_Specified;
end;

procedure SetSlipEmaRqTp.SetCARDNUMBER(Index: Integer; const Astring: string);
begin
  FCARDNUMBER := Astring;
  FCARDNUMBER_Specified := True;
end;

function SetSlipEmaRqTp.CARDNUMBER_Specified(Index: Integer): boolean;
begin
  Result := FCARDNUMBER_Specified;
end;

procedure SetSlipEmaRqTp.SetACQREFERENCE(Index: Integer; const Astring: string);
begin
  FACQREFERENCE := Astring;
  FACQREFERENCE_Specified := True;
end;

function SetSlipEmaRqTp.ACQREFERENCE_Specified(Index: Integer): boolean;
begin
  Result := FACQREFERENCE_Specified;
end;

destructor GetDelovniNalogiRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.DelovniNalogTp>(FNalogi);
  System.SetLength(FNalogi, 0);
  inherited Destroy;
end;

procedure GetDelovniNalogiRsTp.SetNalogi(Index: Integer; const AArrayOfDelovniNalogTp: ArrayOfDelovniNalogTp);
begin
  FNalogi := AArrayOfDelovniNalogTp;
  FNalogi_Specified := True;
end;

function GetDelovniNalogiRsTp.Nalogi_Specified(Index: Integer): boolean;
begin
  Result := FNalogi_Specified;
end;

destructor StornoRazlogTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure StornoRazlogTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function StornoRazlogTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure StornoRazlogTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function StornoRazlogTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

destructor GetStornoRazlogiRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.StornoRazlogTp>(FStornoRazlogi);
  System.SetLength(FStornoRazlogi, 0);
  inherited Destroy;
end;

procedure GetStornoRazlogiRsTp.SetStornoRazlogi(Index: Integer; const AArrayOfStornoRazlogTp: ArrayOfStornoRazlogTp);
begin
  FStornoRazlogi := AArrayOfStornoRazlogTp;
  FStornoRazlogi_Specified := True;
end;

function GetStornoRazlogiRsTp.StornoRazlogi_Specified(Index: Integer): boolean;
begin
  Result := FStornoRazlogi_Specified;
end;

destructor RecepturaItemTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FKOLICINA);
  inherited Destroy;
end;

procedure RecepturaItemTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function RecepturaItemTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure RecepturaItemTp.SetNAZIV_ZA_RAC(Index: Integer; const Astring: string);
begin
  FNAZIV_ZA_RAC := Astring;
  FNAZIV_ZA_RAC_Specified := True;
end;

function RecepturaItemTp.NAZIV_ZA_RAC_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_ZA_RAC_Specified;
end;

destructor GetRecepturaRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.RecepturaItemTp>(FReceptura);
  System.SetLength(FReceptura, 0);
  inherited Destroy;
end;

procedure GetRecepturaRsTp.SetReceptura(Index: Integer; const AArrayOfRecepturaItemTp: ArrayOfRecepturaItemTp);
begin
  FReceptura := AArrayOfRecepturaItemTp;
  FReceptura_Specified := True;
end;

function GetRecepturaRsTp.Receptura_Specified(Index: Integer): boolean;
begin
  Result := FReceptura_Specified;
end;

destructor GetDodatkiRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.DodatekTp>(FDodatki);
  System.SetLength(FDodatki, 0);
  inherited Destroy;
end;

procedure GetDodatkiRsTp.SetDodatki(Index: Integer; const AArrayOfDodatekTp: ArrayOfDodatekTp);
begin
  FDodatki := AArrayOfDodatekTp;
  FDodatki_Specified := True;
end;

function GetDodatkiRsTp.Dodatki_Specified(Index: Integer): boolean;
begin
  Result := FDodatki_Specified;
end;

procedure GetQrFursRsTp.SetQR(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
begin
  FQR := ATByteSOAPArray;
  FQR_Specified := True;
end;

function GetQrFursRsTp.QR_Specified(Index: Integer): boolean;
begin
  Result := FQR_Specified;
end;

destructor GetLojalnostnaRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.LojalnostnaTp>(FLojalnostna);
  System.SetLength(FLojalnostna, 0);
  inherited Destroy;
end;

procedure GetLojalnostnaRsTp.SetLojalnostna(Index: Integer; const AArrayOfLojalnostnaTp: ArrayOfLojalnostnaTp);
begin
  FLojalnostna := AArrayOfLojalnostnaTp;
  FLojalnostna_Specified := True;
end;

function GetLojalnostnaRsTp.Lojalnostna_Specified(Index: Integer): boolean;
begin
  Result := FLojalnostna_Specified;
end;

destructor DodatekTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure DodatekTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function DodatekTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure DodatekTp.SetDODATEK_TEXT(Index: Integer; const Astring: string);
begin
  FDODATEK_TEXT := Astring;
  FDODATEK_TEXT_Specified := True;
end;

function DodatekTp.DODATEK_TEXT_Specified(Index: Integer): boolean;
begin
  Result := FDODATEK_TEXT_Specified;
end;

procedure DodatekTp.SetSKUPINA(Index: Integer; const Astring: string);
begin
  FSKUPINA := Astring;
  FSKUPINA_Specified := True;
end;

function DodatekTp.SKUPINA_Specified(Index: Integer): boolean;
begin
  Result := FSKUPINA_Specified;
end;

destructor SearchGostTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure SearchGostTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function SearchGostTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure SearchGostTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function SearchGostTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure SearchGostTp.SetIME(Index: Integer; const Astring: string);
begin
  FIME := Astring;
  FIME_Specified := True;
end;

function SearchGostTp.IME_Specified(Index: Integer): boolean;
begin
  Result := FIME_Specified;
end;

procedure SearchGostTp.SetPRIIMEK(Index: Integer; const Astring: string);
begin
  FPRIIMEK := Astring;
  FPRIIMEK_Specified := True;
end;

function SearchGostTp.PRIIMEK_Specified(Index: Integer): boolean;
begin
  Result := FPRIIMEK_Specified;
end;

procedure SearchGostTp.SetDAT_ROJ(Index: Integer; const Astring: string);
begin
  FDAT_ROJ := Astring;
  FDAT_ROJ_Specified := True;
end;

function SearchGostTp.DAT_ROJ_Specified(Index: Integer): boolean;
begin
  Result := FDAT_ROJ_Specified;
end;

procedure SearchGostTp.SetNASLOV(Index: Integer; const Astring: string);
begin
  FNASLOV := Astring;
  FNASLOV_Specified := True;
end;

function SearchGostTp.NASLOV_Specified(Index: Integer): boolean;
begin
  Result := FNASLOV_Specified;
end;

procedure SearchGostTp.SetDRZAVA(Index: Integer; const Astring: string);
begin
  FDRZAVA := Astring;
  FDRZAVA_Specified := True;
end;

function SearchGostTp.DRZAVA_Specified(Index: Integer): boolean;
begin
  Result := FDRZAVA_Specified;
end;

procedure SearchGostTp.SetDOKUMENT(Index: Integer; const Astring: string);
begin
  FDOKUMENT := Astring;
  FDOKUMENT_Specified := True;
end;

function SearchGostTp.DOKUMENT_Specified(Index: Integer): boolean;
begin
  Result := FDOKUMENT_Specified;
end;

destructor GetSlipEma2Tp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FDATUM_TRANSAKCIJE);
  System.SysUtils.FreeAndNil(FZNESEK);
  System.SysUtils.FreeAndNil(FZNESEK_SLIP);
  inherited Destroy;
end;

procedure GetSlipEma2Tp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetSlipEma2Tp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetSlipEma2Tp.SetAVTORIZACIJA(Index: Integer; const Astring: string);
begin
  FAVTORIZACIJA := Astring;
  FAVTORIZACIJA_Specified := True;
end;

function GetSlipEma2Tp.AVTORIZACIJA_Specified(Index: Integer): boolean;
begin
  Result := FAVTORIZACIJA_Specified;
end;

procedure GetSlipEma2Tp.SetACQTRANSREF(Index: Integer; const Astring: string);
begin
  FACQTRANSREF := Astring;
  FACQTRANSREF_Specified := True;
end;

function GetSlipEma2Tp.ACQTRANSREF_Specified(Index: Integer): boolean;
begin
  Result := FACQTRANSREF_Specified;
end;

procedure GetSlipEma2Tp.SetACQREFERENCE(Index: Integer; const Astring: string);
begin
  FACQREFERENCE := Astring;
  FACQREFERENCE_Specified := True;
end;

function GetSlipEma2Tp.ACQREFERENCE_Specified(Index: Integer): boolean;
begin
  Result := FACQREFERENCE_Specified;
end;

procedure GetSlipEma2Tp.SetSTTYPE(Index: Integer; const Astring: string);
begin
  FSTTYPE := Astring;
  FSTTYPE_Specified := True;
end;

function GetSlipEma2Tp.STTYPE_Specified(Index: Integer): boolean;
begin
  Result := FSTTYPE_Specified;
end;

procedure GetSlipEma2Tp.SetSLIP_PRINT(Index: Integer; const Astring: string);
begin
  FSLIP_PRINT := Astring;
  FSLIP_PRINT_Specified := True;
end;

function GetSlipEma2Tp.SLIP_PRINT_Specified(Index: Integer): boolean;
begin
  Result := FSLIP_PRINT_Specified;
end;

destructor GetSlipEma2RsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.GetSlipEma2Tp>(FSlipList);
  System.SetLength(FSlipList, 0);
  inherited Destroy;
end;

procedure GetSlipEma2RsTp.SetSlipList(Index: Integer; const AArrayOfGetSlipEma2Tp: ArrayOfGetSlipEma2Tp);
begin
  FSlipList := AArrayOfGetSlipEma2Tp;
  FSlipList_Specified := True;
end;

function GetSlipEma2RsTp.SlipList_Specified(Index: Integer): boolean;
begin
  Result := FSlipList_Specified;
end;

destructor SearchGostRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.SearchGostTp>(FGosti);
  System.SetLength(FGosti, 0);
  inherited Destroy;
end;

procedure SearchGostRsTp.SetGosti(Index: Integer; const AArrayOfSearchGostTp: ArrayOfSearchGostTp);
begin
  FGosti := AArrayOfSearchGostTp;
  FGosti_Specified := True;
end;

function SearchGostRsTp.Gosti_Specified(Index: Integer): boolean;
begin
  Result := FGosti_Specified;
end;

destructor GetSlipEma2RqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FSTRM_ID);
  inherited Destroy;
end;

procedure GetSlipEma2RqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetSlipEma2RqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor GetNapitninaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure GetNapitninaRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetNapitninaRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor GetNapitninaRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FN_DATUMURA);
  System.SysUtils.FreeAndNil(FN_ZNESEK);
  inherited Destroy;
end;

procedure GetNapitninaRsTp.SetN_PLACILO(Index: Integer; const Astring: string);
begin
  FN_PLACILO := Astring;
  FN_PLACILO_Specified := True;
end;

function GetNapitninaRsTp.N_PLACILO_Specified(Index: Integer): boolean;
begin
  Result := FN_PLACILO_Specified;
end;

destructor GetBazenKarteRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FAKTIVEN);
  System.SysUtils.FreeAndNil(FDATUM);
  System.SysUtils.FreeAndNil(FRACUN_ID);
  System.SysUtils.FreeAndNil(FPRODAJA_RACUN_ID);
  inherited Destroy;
end;

procedure GetBazenKarteRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetBazenKarteRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetBazenKarteRqTp.SetRFID(Index: Integer; const Astring: string);
begin
  FRFID := Astring;
  FRFID_Specified := True;
end;

function GetBazenKarteRqTp.RFID_Specified(Index: Integer): boolean;
begin
  Result := FRFID_Specified;
end;

destructor GetNapitninaSkupajRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.GetNapitninaSkupajTp>(FNapitnine);
  System.SetLength(FNapitnine, 0);
  inherited Destroy;
end;

procedure GetNapitninaSkupajRsTp.SetNapitnine(Index: Integer; const AArrayOfGetNapitninaSkupajTp: ArrayOfGetNapitninaSkupajTp);
begin
  FNapitnine := AArrayOfGetNapitninaSkupajTp;
  FNapitnine_Specified := True;
end;

function GetNapitninaSkupajRsTp.Napitnine_Specified(Index: Integer): boolean;
begin
  Result := FNapitnine_Specified;
end;

destructor SetNapitninaRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FN_DATUMURA);
  System.SysUtils.FreeAndNil(FN_ZNESEK);
  inherited Destroy;
end;

procedure SetNapitninaRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function SetNapitninaRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure SetNapitninaRqTp.SetN_PLACILO(Index: Integer; const Astring: string);
begin
  FN_PLACILO := Astring;
  FN_PLACILO_Specified := True;
end;

function SetNapitninaRqTp.N_PLACILO_Specified(Index: Integer): boolean;
begin
  Result := FN_PLACILO_Specified;
end;

destructor BazenKartaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FNIVO4_ID);
  System.SysUtils.FreeAndNil(FKOLICINA);
  System.SysUtils.FreeAndNil(FCENA);
  System.SysUtils.FreeAndNil(FPLACILO_ID);
  System.SysUtils.FreeAndNil(FSTKORISCENJ);
  System.SysUtils.FreeAndNil(FVELJAVNODNI);
  System.SysUtils.FreeAndNil(FAKTIVEN);
  System.SysUtils.FreeAndNil(FSTRM_ID);
  System.SysUtils.FreeAndNil(FDATUM_OD);
  System.SysUtils.FreeAndNil(FDATUM_DO);
  System.SysUtils.FreeAndNil(FDATUM_AKTIVACIJE);
  System.SysUtils.FreeAndNil(FDATUM_KORISCENJA);
  System.SysUtils.FreeAndNil(FKORISTIL_ID);
  System.SysUtils.FreeAndNil(FOSEBA_ID_REVERZ);
  inherited Destroy;
end;

procedure BazenKartaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function BazenKartaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure BazenKartaTp.SetSLIKA(Index: Integer; const ATByteSOAPArray: TByteSOAPArray);
begin
  FSLIKA := ATByteSOAPArray;
  FSLIKA_Specified := True;
end;

function BazenKartaTp.SLIKA_Specified(Index: Integer): boolean;
begin
  Result := FSLIKA_Specified;
end;

procedure BazenKartaTp.SetIME(Index: Integer; const Astring: string);
begin
  FIME := Astring;
  FIME_Specified := True;
end;

function BazenKartaTp.IME_Specified(Index: Integer): boolean;
begin
  Result := FIME_Specified;
end;

procedure BazenKartaTp.SetPRIIMEK(Index: Integer; const Astring: string);
begin
  FPRIIMEK := Astring;
  FPRIIMEK_Specified := True;
end;

function BazenKartaTp.PRIIMEK_Specified(Index: Integer): boolean;
begin
  Result := FPRIIMEK_Specified;
end;

procedure BazenKartaTp.SetNASLOV(Index: Integer; const Astring: string);
begin
  FNASLOV := Astring;
  FNASLOV_Specified := True;
end;

function BazenKartaTp.NASLOV_Specified(Index: Integer): boolean;
begin
  Result := FNASLOV_Specified;
end;

destructor AkcijaGetRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FPRIJAVA_ID);
  System.SysUtils.FreeAndNil(FSTR_MESTO_ID);
  System.SysUtils.FreeAndNil(FDATUM_OD);
  System.SysUtils.FreeAndNil(FDATUM_DO);
  System.SysUtils.FreeAndNil(FRACUN_ID);
  inherited Destroy;
end;

procedure AkcijaGetRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function AkcijaGetRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure AkcijaGetRqTp.SetKUPON_ID(Index: Integer; const Astring: string);
begin
  FKUPON_ID := Astring;
  FKUPON_ID_Specified := True;
end;

function AkcijaGetRqTp.KUPON_ID_Specified(Index: Integer): boolean;
begin
  Result := FKUPON_ID_Specified;
end;

destructor AkcijaTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FTIP_AKCIJE);
  System.SysUtils.FreeAndNil(FPLACILO_ID);
  System.SysUtils.FreeAndNil(FPARTNER_ID_ZAPLACILO);
  System.SysUtils.FreeAndNil(FPOPUST_PROC);
  System.SysUtils.FreeAndNil(FPOPUST_ZNESEK);
  System.SysUtils.FreeAndNil(FDATUM_OD);
  System.SysUtils.FreeAndNil(FDATUM_DO);
  System.SysUtils.FreeAndNil(FMIN_VREDNOST_RACUNA);
  inherited Destroy;
end;

procedure AkcijaTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function AkcijaTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure AkcijaTp.SetKUPON_ID(Index: Integer; const Astring: string);
begin
  FKUPON_ID := Astring;
  FKUPON_ID_Specified := True;
end;

function AkcijaTp.KUPON_ID_Specified(Index: Integer): boolean;
begin
  Result := FKUPON_ID_Specified;
end;

procedure AkcijaTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function AkcijaTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure AkcijaTp.SetTIP_NAZIV(Index: Integer; const Astring: string);
begin
  FTIP_NAZIV := Astring;
  FTIP_NAZIV_Specified := True;
end;

function AkcijaTp.TIP_NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FTIP_NAZIV_Specified;
end;

procedure AkcijaTp.SetOPIS(Index: Integer; const Astring: string);
begin
  FOPIS := Astring;
  FOPIS_Specified := True;
end;

function AkcijaTp.OPIS_Specified(Index: Integer): boolean;
begin
  Result := FOPIS_Specified;
end;

procedure AkcijaTp.SetTISK(Index: Integer; const Astring: string);
begin
  FTISK := Astring;
  FTISK_Specified := True;
end;

function AkcijaTp.TISK_Specified(Index: Integer): boolean;
begin
  Result := FTISK_Specified;
end;

destructor AkcijaGetRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.AkcijaTp>(FAkcije);
  System.SetLength(FAkcije, 0);
  inherited Destroy;
end;

procedure AkcijaGetRsTp.SetAkcije(Index: Integer; const AArrayOfAkcijaTp: ArrayOfAkcijaTp);
begin
  FAkcije := AArrayOfAkcijaTp;
  FAkcije_Specified := True;
end;

function AkcijaGetRsTp.Akcije_Specified(Index: Integer): boolean;
begin
  Result := FAkcije_Specified;
end;

destructor GetBazenKarteRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.BazenKartaTp>(FBKarte);
  System.SetLength(FBKarte, 0);
  inherited Destroy;
end;

procedure GetBazenKarteRsTp.SetBKarte(Index: Integer; const AArrayOfBazenKartaTp: ArrayOfBazenKartaTp);
begin
  FBKarte := AArrayOfBazenKartaTp;
  FBKarte_Specified := True;
end;

function GetBazenKarteRsTp.BKarte_Specified(Index: Integer): boolean;
begin
  Result := FBKarte_Specified;
end;

destructor SetBazenKarteRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FODBKARTA_ID);
  System.SysUtils.FreeAndNil(FDOBKARTA_ID);
  System.SysUtils.FreeAndNil(FRACUN_ID);
  System.SysUtils.FreeAndNil(FKORISCENO_STARO);
  System.SysUtils.FreeAndNil(FKORISCENO_NOVO);
  System.SysUtils.FreeAndNil(FAKTIVEN);
  System.SysUtils.FreeAndNil(FPRODAJA_RACUN_ID);
  System.SysUtils.FreeAndNil(FAKTIVIRAL_ID);
  System.SysUtils.FreeAndNil(FW_RACUN_ID);
  System.SysUtils.FreeAndNil(FW_PRODAJA_RACUN_ID);
  System.SysUtils.FreeAndNil(FVRSTAKARTE);
  System.SysUtils.FreeAndNil(FSTKORISCENJ);
  System.SysUtils.FreeAndNil(FDATUM_DO);
  System.SysUtils.FreeAndNil(FDATUM_AKTIVACIJE);
  System.SysUtils.FreeAndNil(FDATUM_KORISCENJA);
  System.SysUtils.FreeAndNil(FKORISTIL_ID);
  System.SysUtils.FreeAndNil(FOSEBA_ID_REVERZ);
  inherited Destroy;
end;

procedure SetBazenKarteRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function SetBazenKarteRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure SetBazenKarteRqTp.SetIME(Index: Integer; const Astring: string);
begin
  FIME := Astring;
  FIME_Specified := True;
end;

function SetBazenKarteRqTp.IME_Specified(Index: Integer): boolean;
begin
  Result := FIME_Specified;
end;

procedure SetBazenKarteRqTp.SetPRIIMEK(Index: Integer; const Astring: string);
begin
  FPRIIMEK := Astring;
  FPRIIMEK_Specified := True;
end;

function SetBazenKarteRqTp.PRIIMEK_Specified(Index: Integer): boolean;
begin
  Result := FPRIIMEK_Specified;
end;

procedure SetBazenKarteRqTp.SetNASLOV(Index: Integer; const Astring: string);
begin
  FNASLOV := Astring;
  FNASLOV_Specified := True;
end;

function SetBazenKarteRqTp.NASLOV_Specified(Index: Integer): boolean;
begin
  Result := FNASLOV_Specified;
end;

procedure SetBazenKarteRqTp.SetREFERENCA_AKTIVACIJE(Index: Integer; const Astring: string);
begin
  FREFERENCA_AKTIVACIJE := Astring;
  FREFERENCA_AKTIVACIJE_Specified := True;
end;

function SetBazenKarteRqTp.REFERENCA_AKTIVACIJE_Specified(Index: Integer): boolean;
begin
  Result := FREFERENCA_AKTIVACIJE_Specified;
end;

destructor NatisniHodRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure NatisniHodRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function NatisniHodRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure NatisniHodRqTp.SetHOD(Index: Integer; const Astring: string);
begin
  FHOD := Astring;
  FHOD_Specified := True;
end;

function NatisniHodRqTp.HOD_Specified(Index: Integer): boolean;
begin
  Result := FHOD_Specified;
end;

destructor Nivo4TujiTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  inherited Destroy;
end;

procedure Nivo4TujiTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function Nivo4TujiTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure Nivo4TujiTp.SetNAZIV(Index: Integer; const Astring: string);
begin
  FNAZIV := Astring;
  FNAZIV_Specified := True;
end;

function Nivo4TujiTp.NAZIV_Specified(Index: Integer): boolean;
begin
  Result := FNAZIV_Specified;
end;

procedure Nivo4TujiTp.SetOPIS(Index: Integer; const Astring: string);
begin
  FOPIS := Astring;
  FOPIS_Specified := True;
end;

function Nivo4TujiTp.OPIS_Specified(Index: Integer): boolean;
begin
  Result := FOPIS_Specified;
end;

destructor GetStornoIdRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FSTORNO_RACUN_ID);
  inherited Destroy;
end;

destructor GetNivo4IdFromBarcodeRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FCENIK_ID);
  inherited Destroy;
end;

procedure GetNivo4IdFromBarcodeRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetNivo4IdFromBarcodeRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure GetNivo4IdFromBarcodeRqTp.SetBAR_CODE(Index: Integer; const Astring: string);
begin
  FBAR_CODE := Astring;
  FBAR_CODE_Specified := True;
end;

function GetNivo4IdFromBarcodeRqTp.BAR_CODE_Specified(Index: Integer): boolean;
begin
  Result := FBAR_CODE_Specified;
end;

procedure GetNivo4IdFromBarcodeRqTp.SetVIR_TABELA(Index: Integer; const Astring: string);
begin
  FVIR_TABELA := Astring;
  FVIR_TABELA_Specified := True;
end;

function GetNivo4IdFromBarcodeRqTp.VIR_TABELA_Specified(Index: Integer): boolean;
begin
  Result := FVIR_TABELA_Specified;
end;

destructor GetNivo4IdFromBarcodeRsTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FNIVO4_ID);
  inherited Destroy;
end;

destructor GetNivo4TujiRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.Nivo4TujiTp>(FNivo4Tuji);
  System.SetLength(FNivo4Tuji, 0);
  inherited Destroy;
end;

procedure GetNivo4TujiRsTp.SetNivo4Tuji(Index: Integer; const AArrayOfNivo4TujiTp: ArrayOfNivo4TujiTp);
begin
  FNivo4Tuji := AArrayOfNivo4TujiTp;
  FNivo4Tuji_Specified := True;
end;

function GetNivo4TujiRsTp.Nivo4Tuji_Specified(Index: Integer): boolean;
begin
  Result := FNivo4Tuji_Specified;
end;

destructor GetNapitninaSkupajRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FOD_DATUM);
  System.SysUtils.FreeAndNil(FTOCILNICA_ID);
  System.SysUtils.FreeAndNil(FOSEBA_ID);
  System.SysUtils.FreeAndNil(FSTATUS);
  inherited Destroy;
end;

procedure GetNapitninaSkupajRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetNapitninaSkupajRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor GetNapitninaSkupajTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FNAPITNINA);
  inherited Destroy;
end;

procedure GetNapitninaSkupajTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetNapitninaSkupajTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor GetPraznikiRqTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FOBRAT_ID);
  System.SysUtils.FreeAndNil(FDATUM);
  System.SysUtils.FreeAndNil(FDATUM_DO);
  inherited Destroy;
end;

procedure GetPraznikiRqTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function GetPraznikiRqTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

destructor PraznikTp.Destroy;
begin
  System.SysUtils.FreeAndNil(FExtensionData);
  System.SysUtils.FreeAndNil(FOBRAT_ID);
  System.SysUtils.FreeAndNil(FDATUM);
  System.SysUtils.FreeAndNil(FBARVA);
  System.SysUtils.FreeAndNil(FNAROCILA_DOSTAVA);
  System.SysUtils.FreeAndNil(FOBRACUN_ODBITKA);
  inherited Destroy;
end;

procedure PraznikTp.SetExtensionData(Index: Integer; const AExtensionDataObject: ExtensionDataObject);
begin
  FExtensionData := AExtensionDataObject;
  FExtensionData_Specified := True;
end;

function PraznikTp.ExtensionData_Specified(Index: Integer): boolean;
begin
  Result := FExtensionData_Specified;
end;

procedure PraznikTp.SetOPIS(Index: Integer; const Astring: string);
begin
  FOPIS := Astring;
  FOPIS_Specified := True;
end;

function PraznikTp.OPIS_Specified(Index: Integer): boolean;
begin
  Result := FOPIS_Specified;
end;

destructor GetPraznikiRsTp.Destroy;
begin
  TArray.FreeValues<RosKasa_ceniki_wsdl.PraznikTp>(FPrazniki);
  System.SetLength(FPrazniki, 0);
  inherited Destroy;
end;

procedure GetPraznikiRsTp.SetPrazniki(Index: Integer; const AArrayOfPraznikTp: ArrayOfPraznikTp);
begin
  FPrazniki := AArrayOfPraznikTp;
  FPrazniki_Specified := True;
end;

function GetPraznikiRsTp.Prazniki_Specified(Index: Integer): boolean;
begin
  Result := FPrazniki_Specified;
end;

procedure RegisterTypeProc0;
begin
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfRacTBonTp), 'http://ros.si/R16', 'ArrayOfRacTBonTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfPlaciloTp), 'http://ros.si/R16', 'ArrayOfPlaciloTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfPozicijaTp), 'http://ros.si/R16', 'ArrayOfPozicijaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfListaSkupinTp), 'http://ros.si/R16', 'ArrayOfListaSkupinTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfProstorTp), 'http://ros.si/R16', 'ArrayOfProstorTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfKartprijTp), 'http://ros.si/R16', 'ArrayOfKartprijTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfObrokTp), 'http://ros.si/R16', 'ArrayOfObrokTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfNarociloTp), 'http://ros.si/R16', 'ArrayOfNarociloTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfNacPlacMakroTp), 'http://ros.si/R16', 'ArrayOfNacPlacMakroTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfInkasoOsebeTp), 'http://ros.si/R16', 'ArrayOfInkasoOsebeTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfTiskajTp), 'http://ros.si/R16', 'ArrayOfTiskajTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfRacunTp), 'http://ros.si/R16', 'ArrayOfRacunTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfPartnerTp), 'http://ros.si/R16', 'ArrayOfPartnerTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfMizaTp), 'http://ros.si/R16', 'ArrayOfMizaTp');
  RemClassRegistry.RegisterXSClass(BaseDAL, 'http://ros.si/R16', 'BaseDAL');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfTarifaTp), 'http://ros.si/R16', 'ArrayOfTarifaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfOsebaTp), 'http://ros.si/R16', 'ArrayOfOsebaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfPrioritetaProjektaTp), 'http://ros.si/R16', 'ArrayOfPrioritetaProjektaTp');
  RemClassRegistry.RegisterXSClass(ExtensionDataObject, 'http://ros.si/R16', 'ExtensionDataObject');
  RemClassRegistry.RegisterXSClass(TiskajTp, 'http://ros.si/R16', 'TiskajTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfString), 'http://ros.si/R16', 'ArrayOfString');
  RemClassRegistry.RegisterXSClass(ResponseType, 'http://ros.si/R16', 'ResponseType');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(ResponseType), 'strings', '[ArrayItemName="string"]');
  RemClassRegistry.RegisterXSClass(GetAppConfigRsTp, 'http://ros.si/R16', 'GetAppConfigRsTp');
  RemClassRegistry.RegisterXSClass(GetRacuniRsTp, 'http://ros.si/R16', 'GetRacuniRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetRacuniRsTp), 'Racuni', '[ArrayItemName="RacunTp"]');
  RemClassRegistry.RegisterXSClass(GetRacunRsTp, 'http://ros.si/R16', 'GetRacunRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetRacunRsTp), 'Tiskaj', '[ArrayItemName="TiskajTp"]');
  RemClassRegistry.RegisterXSClass(GetInkasoOsebeRsTp, 'http://ros.si/R16', 'GetInkasoOsebeRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetInkasoOsebeRsTp), 'Inkaso', '[ArrayItemName="InkasoOsebeTp"]');
  RemClassRegistry.RegisterXSClass(GetNacPlacMakroRsTp, 'http://ros.si/R16', 'GetNacPlacMakroRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetNacPlacMakroRsTp), 'Makro', '[ArrayItemName="NacPlacMakroTp"]');
  RemClassRegistry.RegisterXSClass(GetObrokiRsTp, 'http://ros.si/R16', 'GetObrokiRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetObrokiRsTp), 'Obroki', '[ArrayItemName="ObrokTp"]');
  RemClassRegistry.RegisterXSClass(GetNovaNarocilaRsTp, 'http://ros.si/R16', 'GetNovaNarocilaRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetNovaNarocilaRsTp), 'Narocila', '[ArrayItemName="NarociloTp"]');
  RemClassRegistry.RegisterXSClass(GetProstorRsTp, 'http://ros.si/R16', 'GetProstorRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetProstorRsTp), 'PROSTOR', '[ArrayItemName="ProstorTp"]');
  RemClassRegistry.RegisterXSClass(GetKartprijRsTp, 'http://ros.si/R16', 'GetKartprijRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetKartprijRsTp), 'KARTPRIJ', '[ArrayItemName="KartprijTp"]');
  RemClassRegistry.RegisterXSClass(GetPartnerRsTp, 'http://ros.si/R16', 'GetPartnerRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetPartnerRsTp), 'PARTNER', '[ArrayItemName="PartnerTp"]');
  RemClassRegistry.RegisterXSClass(GetListaSkupinRsTp, 'http://ros.si/R16', 'GetListaSkupinRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetListaSkupinRsTp), 'Skupine', '[ArrayItemName="ListaSkupinTp"]');
  RemClassRegistry.RegisterXSClass(GetPrijavaRsTp, 'http://ros.si/R16', 'GetPrijavaRsTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfRacunSeznamTp), 'http://ros.si/R16', 'ArrayOfRacunSeznamTp');
  RemClassRegistry.RegisterXSClass(GetRacuniSeznamRsTp, 'http://ros.si/R16', 'GetRacuniSeznamRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetRacuniSeznamRsTp), 'Racuni', '[ArrayItemName="RacunSeznamTp"]');
  RemClassRegistry.RegisterXSClass(PrioritetaProjektaTp, 'http://ros.si/R16', 'PrioritetaProjektaTp');
  RemClassRegistry.RegisterXSClass(OsebaTokenRsTp, 'http://ros.si/R16', 'OsebaTokenRsTp');
  RemClassRegistry.RegisterXSClass(OsebaTp, 'http://ros.si/R16', 'OsebaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfInt), 'http://ros.si/R16', 'ArrayOfInt');
  RemClassRegistry.RegisterXSClass(MobileSetupsTp, 'http://ros.si/R16', 'MobileSetupsTp');
  RemClassRegistry.RegisterXSClass(MizaTp, 'http://ros.si/R16', 'MizaTp');
  RemClassRegistry.RegisterXSClass(MobileSetupTp, 'http://ros.si/R16', 'MobileSetupTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(MobileSetupTp), 'MOBILE_SETUP_MIZE', '[ArrayItemName="MizaTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(MobileSetupTp), 'MOBILE_SETUP_PLACILA', '[ArrayItemName="int"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(MobileSetupTp), 'OSEBE', '[ArrayItemName="OsebaTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(MobileSetupTp), 'PRIORITETE_PROJEKTOV', '[ArrayItemName="PrioritetaProjektaTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(MobileSetupTp), 'TARIFE', '[ArrayItemName="TarifaTp"]');
  RemClassRegistry.RegisterXSClass(IzpisanTp, 'http://ros.si/R16', 'IzpisanTp');
  RemClassRegistry.RegisterXSClass(NarediObracunRqTp, 'http://ros.si/R16', 'NarediObracunRqTp');
  RemClassRegistry.RegisterXSClass(GetRacuniRqTp, 'http://ros.si/R16', 'GetRacuniRqTp');
  RemClassRegistry.RegisterXSClass(SetStornoRqTp, 'http://ros.si/R16', 'SetStornoRqTp');
  RemClassRegistry.RegisterXSClass(NacPlacMakroTp, 'http://ros.si/R16', 'NacPlacMakroTp');
  RemClassRegistry.RegisterXSClass(NarociloTp, 'http://ros.si/R16', 'NarociloTp');
  RemClassRegistry.RegisterXSClass(ObrokTp, 'http://ros.si/R16', 'ObrokTp');
  RemClassRegistry.RegisterXSClass(GetObrokiRqTp, 'http://ros.si/R16', 'GetObrokiRqTp');
  RemClassRegistry.RegisterXSClass(KartprijTp, 'http://ros.si/R16', 'KartprijTp');
  RemClassRegistry.RegisterXSClass(ProstorTp, 'http://ros.si/R16', 'ProstorTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfInt1), 'http://ros.si/R16', 'ArrayOfInt1');
  RemClassRegistry.RegisterXSClass(ZdruziRacuneRqTp, 'http://ros.si/R16', 'ZdruziRacuneRqTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(ZdruziRacuneRqTp), 'Racuni', '[ArrayItemName="int"]');
  RemClassRegistry.RegisterXSClass(GetKartprijRqTp, 'http://ros.si/R16', 'GetKartprijRqTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetKartprijRqTp), 'OBRATI', '[ArrayItemName="int"]');
  RemClassRegistry.RegisterXSClass(GetListaSkupinRqTp, 'http://ros.si/R16', 'GetListaSkupinRqTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetListaSkupinRqTp), 'OBRATI', '[ArrayItemName="int"]');
  RemClassRegistry.RegisterXSClass(GetProstorRqTp, 'http://ros.si/R16', 'GetProstorRqTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetProstorRqTp), 'OBRATI', '[ArrayItemName="int"]');
  RemClassRegistry.RegisterXSClass(PrijavaTp, 'http://ros.si/R16', 'PrijavaTp');
  RemClassRegistry.RegisterXSClass(GetPrijavaRqTp, 'http://ros.si/R16', 'GetPrijavaRqTp');
  RemClassRegistry.RegisterXSClass(ListaSkupinTp, 'http://ros.si/R16', 'ListaSkupinTp');
  RemClassRegistry.RegisterXSClass(RacunSeznamTp, 'http://ros.si/R16', 'RacunSeznamTp');
  RemClassRegistry.RegisterXSClass(TarifaTp, 'http://ros.si/R16', 'TarifaTp');
  RemClassRegistry.RegisterXSClass(InkasoOsebeTp, 'http://ros.si/R16', 'InkasoOsebeTp');
  RemClassRegistry.RegisterXSClass(PartnerTp, 'http://ros.si/R16', 'PartnerTp');
  RemClassRegistry.RegisterXSClass(CenikVrTp, 'http://ros.si/R16', 'CenikVrTp');
  RemClassRegistry.RegisterXSClass(CenikVrVrTp, 'http://ros.si/R16', 'CenikVrVrTp');
  RemClassRegistry.RegisterXSClass(CenikVrCeneTp, 'http://ros.si/R16', 'CenikVrCeneTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfCenikVrTp), 'http://ros.si/R16', 'ArrayOfCenikVrTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfCenikVrVrTp), 'http://ros.si/R16', 'ArrayOfCenikVrVrTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfCenikVrCeneTp), 'http://ros.si/R16', 'ArrayOfCenikVrCeneTp');
  RemClassRegistry.RegisterXSClass(GetPartnerRqTp, 'http://ros.si/R16', 'GetPartnerRqTp');
  RemClassRegistry.RegisterXSClass(ZamenjajLastnikaRqTp, 'http://ros.si/R16', 'ZamenjajLastnikaRqTp');
  RemClassRegistry.RegisterXSClass(RacTBonTp, 'http://ros.si/R16', 'RacTBonTp');
  RemClassRegistry.RegisterXSClass(SetStornoRacunaRqTp, 'http://ros.si/R16', 'SetStornoRacunaRqTp');
  RemClassRegistry.RegisterXSClass(PozicijaTp, 'http://ros.si/R16', 'PozicijaTp');
  RemClassRegistry.RegisterXSClass(GetCenikRsTp, 'http://ros.si/R16', 'GetCenikRsTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfNacPlacTp), 'http://ros.si/R16', 'ArrayOfNacPlacTp');
  RemClassRegistry.RegisterXSClass(GetNacPlacRsTp, 'http://ros.si/R16', 'GetNacPlacRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetNacPlacRsTp), 'NACPLAC', '[ArrayItemName="NacPlacTp"]');
  RemClassRegistry.RegisterXSClass(CenikGlTp, 'http://ros.si/R16', 'CenikGlTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(CenikGlTp), 'CENIKVR', '[ArrayItemName="CenikVrTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(CenikGlTp), 'CENIKVRVR', '[ArrayItemName="CenikVrVrTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(CenikGlTp), 'CENIKVR_CENE', '[ArrayItemName="CenikVrCeneTp"]');
  RemClassRegistry.RegisterXSClass(GetTimesRsTp, 'http://ros.si/R16', 'GetTimesRsTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfKronologijaTp), 'http://ros.si/R16', 'ArrayOfKronologijaTp');
  RemClassRegistry.RegisterXSClass(KronologijaTp, 'http://ros.si/R16', 'KronologijaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfMobileSetupsTp), 'http://ros.si/R16', 'ArrayOfMobileSetupsTp');
  RemClassRegistry.RegisterXSClass(GetMobileSetupsRsTp, 'http://ros.si/R16', 'GetMobileSetupsRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetMobileSetupsRsTp), 'MobileSetups', '[ArrayItemName="MobileSetupsTp"]');
  RemClassRegistry.RegisterXSClass(TimesTp, 'http://ros.si/R16', 'TimesTp');
  RemClassRegistry.RegisterXSClass(HitraTipkaTp, 'http://ros.si/R16', 'HitraTipkaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfHitraTipkaTp), 'http://ros.si/R16', 'ArrayOfHitraTipkaTp');
  RemClassRegistry.RegisterXSClass(GetHitreTipkeRsTp, 'http://ros.si/R16', 'GetHitreTipkeRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetHitreTipkeRsTp), 'HITRETIPKE', '[ArrayItemName="HitraTipkaTp"]');
  RemClassRegistry.RegisterXSClass(InsertKronologRqTp, 'http://ros.si/R16', 'InsertKronologRqTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfLong), 'http://ros.si/R16', 'ArrayOfLong');
  RemClassRegistry.RegisterXSClass(NacPlacTp, 'http://ros.si/R16', 'NacPlacTp');
  RemClassRegistry.RegisterXSClass(RacunTp, 'http://ros.si/R16', 'RacunTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(RacunTp), 'RACPLACI', '[ArrayItemName="PlaciloTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(RacunTp), 'RACPOZIC', '[ArrayItemName="PozicijaTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(RacunTp), 'RACTBON', '[ArrayItemName="RacTBonTp"]');
  RemClassRegistry.RegisterXSClass(PlaciloTp, 'http://ros.si/R16', 'PlaciloTp');
  RemClassRegistry.RegisterXSClass(LojalnostnaTp, 'http://ros.si/R16', 'LojalnostnaTp');
  RemClassRegistry.RegisterXSClass(RequestTp, 'http://ros.si/R16', 'RequestTp');
  RemClassRegistry.RegisterXSClass(MbillsSaleRqTp, 'http://ros.si/R16', 'MbillsSaleRqTp');
  RemClassRegistry.RegisterXSClass(MbillsGetStatusRqTp, 'http://ros.si/R16', 'MbillsGetStatusRqTp');
  RemClassRegistry.RegisterXSClass(ResponseTp, 'http://ros.si/R16', 'ResponseTp');
  RemClassRegistry.RegisterXSClass(MbillsGetStatusRsTp, 'http://ros.si/R16', 'MbillsGetStatusRsTp');
end;

procedure RegisterTypeProc1;
begin
  RemClassRegistry.RegisterXSClass(MbillsSaleRsTp, 'http://ros.si/R16', 'MbillsSaleRsTp');
  RemClassRegistry.RegisterXSClass(MbillsFursRqTp, 'http://ros.si/R16', 'MbillsFursRqTp');
  RemClassRegistry.RegisterXSClass(MbillsVoidRqTp, 'http://ros.si/R16', 'MbillsVoidRqTp');
  RemClassRegistry.RegisterXSClass(MbillsVoidRsTp, 'http://ros.si/R16', 'MbillsVoidRsTp');
  RemClassRegistry.RegisterXSClass(ValuStartPaymentRsTp, 'http://ros.si/R16', 'ValuStartPaymentRsTp');
  RemClassRegistry.RegisterXSClass(MbillsFursRsTp, 'http://ros.si/R16', 'MbillsFursRsTp');
  RemClassRegistry.RegisterXSClass(MbillsRefundRqTp, 'http://ros.si/R16', 'MbillsRefundRqTp');
  RemClassRegistry.RegisterXSClass(MbillsRefundRsTp, 'http://ros.si/R16', 'MbillsRefundRsTp');
  RemClassRegistry.RegisterXSClass(AkcijaNazivRsTp, 'http://ros.si/R16', 'AkcijaNazivRsTp');
  RemClassRegistry.RegisterXSClass(AkcijaSetLojalnostRqTp, 'http://ros.si/R16', 'AkcijaSetLojalnostRqTp');
  RemClassRegistry.RegisterXSClass(AkcijaArtikliRqTp, 'http://ros.si/R16', 'AkcijaArtikliRqTp');
  RemClassRegistry.RegisterXSClass(AkcijaArtikelTp, 'http://ros.si/R16', 'AkcijaArtikelTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfAkcijaArtikelTp), 'http://ros.si/R16', 'ArrayOfAkcijaArtikelTp');
  RemClassRegistry.RegisterXSClass(AkcijaArtikliRsTp, 'http://ros.si/R16', 'AkcijaArtikliRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(AkcijaArtikliRsTp), 'Artikli', '[ArrayItemName="AkcijaArtikelTp"]');
  RemClassRegistry.RegisterXSClass(AkcijaSetLojalnostRsTp, 'http://ros.si/R16', 'AkcijaSetLojalnostRsTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(guid), 'http://microsoft.com/wsdl/types/', 'guid');
  RemClassRegistry.RegisterXSClass(TransactionDetailsWs, 'http://ros.si/R16', 'TransactionDetailsWs');
  RemClassRegistry.RegisterXSClass(MonetaRsTp, 'http://ros.si/R16', 'MonetaRsTp');
  RemClassRegistry.RegisterXSClass(GetTransactionStatusRsTp, 'http://ros.si/R16', 'GetTransactionStatusRsTp');
  RemClassRegistry.RegisterXSClass(GetTokenRsTp, 'http://ros.si/R16', 'GetTokenRsTp');
  RemClassRegistry.RegisterXSClass(CancelTransactionRsTp, 'http://ros.si/R16', 'CancelTransactionRsTp');
  RemClassRegistry.RegisterXSClass(KuponAkcijaSaldoRqTp, 'http://ros.si/R16', 'KuponAkcijaSaldoRqTp');
  RemClassRegistry.RegisterXSClass(KuponAkcijaKnjiziRqTp, 'http://ros.si/R16', 'KuponAkcijaKnjiziRqTp');
  RemClassRegistry.RegisterXSClass(KuponKnjiziRsTp, 'http://ros.si/R16', 'KuponKnjiziRsTp');
  RemClassRegistry.RegisterXSClass(KuponAkcijaTp, 'http://ros.si/R16', 'KuponAkcijaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfKuponAkcijaTp), 'http://ros.si/R16', 'ArrayOfKuponAkcijaTp');
  RemClassRegistry.RegisterXSClass(GetKuponAkcijaRsTp, 'http://ros.si/R16', 'GetKuponAkcijaRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetKuponAkcijaRsTp), 'Akcije', '[ArrayItemName="KuponAkcijaTp"]');
  RemClassRegistry.RegisterXSClass(TbBalanceRqTp, 'http://ros.si/R16', 'TbBalanceRqTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(TbBalanceRqTp), 'Name_', '[ExtName="Name"]');
  RemClassRegistry.RegisterXSClass(TBonRefundTp, 'http://ros.si/R16', 'TBonRefundTp');
  RemClassRegistry.RegisterXSClass(TBonRefund21Tp, 'http://ros.si/R16', 'TBonRefund21Tp');
  RemClassRegistry.RegisterXSClass(TbStornoRqTp, 'http://ros.si/R16', 'TbStornoRqTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfTBonRefundTp), 'http://ros.si/R16', 'ArrayOfTBonRefundTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfTBonRefund21Tp), 'http://ros.si/R16', 'ArrayOfTBonRefund21Tp');
  RemClassRegistry.RegisterXSClass(TBonDocumentTp, 'http://ros.si/R16', 'TBonDocumentTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(TBonDocumentTp), 'Refund', '[ArrayItemName="TBonRefundTp"]');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(TBonDocumentTp), 'Refund21', '[ArrayItemName="TBonRefund21Tp"]');
  RemClassRegistry.RegisterXSClass(CrmTockeRsTp, 'http://ros.si/R16', 'CrmTockeRsTp');
  RemClassRegistry.RegisterXSClass(BonSaldoRqTp, 'http://ros.si/R16', 'BonSaldoRqTp');
  RemClassRegistry.RegisterXSClass(BonSaldoRsTp, 'http://ros.si/R16', 'BonSaldoRsTp');
  RemClassRegistry.RegisterXSClass(ValuGetPaymentStatusRsTp, 'http://ros.si/R16', 'ValuGetPaymentStatusRsTp');
  RemClassRegistry.RegisterXSClass(CrmInfoRqTp, 'http://ros.si/R16', 'CrmInfoRqTp');
  RemClassRegistry.RegisterXSClass(CrmInfoRsTp, 'http://ros.si/R16', 'CrmInfoRsTp');
  RemClassRegistry.RegisterXSClass(BonKnjiziRqTp, 'http://ros.si/R16', 'BonKnjiziRqTp');
  RemClassRegistry.RegisterXSClass(KuponSaldoRqTp, 'http://ros.si/R16', 'KuponSaldoRqTp');
  RemClassRegistry.RegisterXSClass(KuponSaldoRsTp, 'http://ros.si/R16', 'KuponSaldoRsTp');
  RemClassRegistry.RegisterXSClass(KuponKnjiziRqTp, 'http://ros.si/R16', 'KuponKnjiziRqTp');
  RemClassRegistry.RegisterXSClass(BonKnjiziRsTp, 'http://ros.si/R16', 'BonKnjiziRsTp');
  RemClassRegistry.RegisterXSClass(BoniIzdajaRqTp, 'http://ros.si/R16', 'BoniIzdajaRqTp');
  RemClassRegistry.RegisterXSClass(BoniIzdajaRsTp, 'http://ros.si/R16', 'BoniIzdajaRsTp');
  RemClassRegistry.RegisterXSClass(GetReportRqTp, 'http://ros.si/R16', 'GetReportRqTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfDelovniNalogTp), 'http://ros.si/R16', 'ArrayOfDelovniNalogTp');
  RemClassRegistry.RegisterXSClass(DelovniNalogTp, 'http://ros.si/R16', 'DelovniNalogTp');
  RemClassRegistry.RegisterXSClass(GetSlipEmaRqTp, 'http://ros.si/R16', 'GetSlipEmaRqTp');
  RemClassRegistry.RegisterXSClass(GetSlipEmaRsTp, 'http://ros.si/R16', 'GetSlipEmaRsTp');
  RemClassRegistry.RegisterXSClass(SetSlipEmaRqTp, 'http://ros.si/R16', 'SetSlipEmaRqTp');
  RemClassRegistry.RegisterXSClass(GetDelovniNalogiRsTp, 'http://ros.si/R16', 'GetDelovniNalogiRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetDelovniNalogiRsTp), 'Nalogi', '[ArrayItemName="DelovniNalogTp"]');
  RemClassRegistry.RegisterXSClass(StornoRazlogTp, 'http://ros.si/R16', 'StornoRazlogTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfStornoRazlogTp), 'http://ros.si/R16', 'ArrayOfStornoRazlogTp');
  RemClassRegistry.RegisterXSClass(GetStornoRazlogiRsTp, 'http://ros.si/R16', 'GetStornoRazlogiRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetStornoRazlogiRsTp), 'StornoRazlogi', '[ArrayItemName="StornoRazlogTp"]');
  RemClassRegistry.RegisterXSClass(RecepturaItemTp, 'http://ros.si/R16', 'RecepturaItemTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfRecepturaItemTp), 'http://ros.si/R16', 'ArrayOfRecepturaItemTp');
  RemClassRegistry.RegisterXSClass(GetRecepturaRsTp, 'http://ros.si/R16', 'GetRecepturaRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetRecepturaRsTp), 'Receptura', '[ArrayItemName="RecepturaItemTp"]');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfDodatekTp), 'http://ros.si/R16', 'ArrayOfDodatekTp');
  RemClassRegistry.RegisterXSClass(GetDodatkiRsTp, 'http://ros.si/R16', 'GetDodatkiRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetDodatkiRsTp), 'Dodatki', '[ArrayItemName="DodatekTp"]');
  RemClassRegistry.RegisterXSClass(GetQrFursRsTp, 'http://ros.si/R16', 'GetQrFursRsTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfLojalnostnaTp), 'http://ros.si/R16', 'ArrayOfLojalnostnaTp');
  RemClassRegistry.RegisterXSClass(GetLojalnostnaRsTp, 'http://ros.si/R16', 'GetLojalnostnaRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetLojalnostnaRsTp), 'Lojalnostna', '[ArrayItemName="LojalnostnaTp"]');
  RemClassRegistry.RegisterXSClass(DodatekTp, 'http://ros.si/R16', 'DodatekTp');
  RemClassRegistry.RegisterXSClass(SearchGostTp, 'http://ros.si/R16', 'SearchGostTp');
  RemClassRegistry.RegisterXSClass(GetSlipEma2Tp, 'http://ros.si/R16', 'GetSlipEma2Tp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfGetSlipEma2Tp), 'http://ros.si/R16', 'ArrayOfGetSlipEma2Tp');
  RemClassRegistry.RegisterXSClass(GetSlipEma2RsTp, 'http://ros.si/R16', 'GetSlipEma2RsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetSlipEma2RsTp), 'SlipList', '[ArrayItemName="GetSlipEma2Tp"]');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfSearchGostTp), 'http://ros.si/R16', 'ArrayOfSearchGostTp');
  RemClassRegistry.RegisterXSClass(SearchGostRsTp, 'http://ros.si/R16', 'SearchGostRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(SearchGostRsTp), 'Gosti', '[ArrayItemName="SearchGostTp"]');
  RemClassRegistry.RegisterXSClass(GetSlipEma2RqTp, 'http://ros.si/R16', 'GetSlipEma2RqTp');
  RemClassRegistry.RegisterXSClass(GetNapitninaRqTp, 'http://ros.si/R16', 'GetNapitninaRqTp');
  RemClassRegistry.RegisterXSClass(GetNapitninaRsTp, 'http://ros.si/R16', 'GetNapitninaRsTp');
  RemClassRegistry.RegisterXSClass(GetBazenKarteRqTp, 'http://ros.si/R16', 'GetBazenKarteRqTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfGetNapitninaSkupajTp), 'http://ros.si/R16', 'ArrayOfGetNapitninaSkupajTp');
  RemClassRegistry.RegisterXSClass(GetNapitninaSkupajRsTp, 'http://ros.si/R16', 'GetNapitninaSkupajRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetNapitninaSkupajRsTp), 'Napitnine', '[ArrayItemName="GetNapitninaSkupajTp"]');
  RemClassRegistry.RegisterXSClass(SetNapitninaRqTp, 'http://ros.si/R16', 'SetNapitninaRqTp');
  RemClassRegistry.RegisterXSClass(BazenKartaTp, 'http://ros.si/R16', 'BazenKartaTp');
  RemClassRegistry.RegisterXSClass(AkcijaGetRqTp, 'http://ros.si/R16', 'AkcijaGetRqTp');
  RemClassRegistry.RegisterXSClass(AkcijaTp, 'http://ros.si/R16', 'AkcijaTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfAkcijaTp), 'http://ros.si/R16', 'ArrayOfAkcijaTp');
  RemClassRegistry.RegisterXSClass(AkcijaGetRsTp, 'http://ros.si/R16', 'AkcijaGetRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(AkcijaGetRsTp), 'Akcije', '[ArrayItemName="AkcijaTp"]');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfBazenKartaTp), 'http://ros.si/R16', 'ArrayOfBazenKartaTp');
  RemClassRegistry.RegisterXSClass(GetBazenKarteRsTp, 'http://ros.si/R16', 'GetBazenKarteRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetBazenKarteRsTp), 'BKarte', '[ArrayItemName="BazenKartaTp"]');
  RemClassRegistry.RegisterXSClass(SetBazenKarteRqTp, 'http://ros.si/R16', 'SetBazenKarteRqTp');
  RemClassRegistry.RegisterXSClass(NatisniHodRqTp, 'http://ros.si/R16', 'NatisniHodRqTp');
  RemClassRegistry.RegisterXSClass(Nivo4TujiTp, 'http://ros.si/R16', 'Nivo4TujiTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfNivo4TujiTp), 'http://ros.si/R16', 'ArrayOfNivo4TujiTp');
  RemClassRegistry.RegisterXSClass(GetStornoIdRsTp, 'http://ros.si/R16', 'GetStornoIdRsTp');
  RemClassRegistry.RegisterXSClass(GetNivo4IdFromBarcodeRqTp, 'http://ros.si/R16', 'GetNivo4IdFromBarcodeRqTp');
  RemClassRegistry.RegisterXSClass(GetNivo4IdFromBarcodeRsTp, 'http://ros.si/R16', 'GetNivo4IdFromBarcodeRsTp');
  RemClassRegistry.RegisterXSClass(GetNivo4TujiRsTp, 'http://ros.si/R16', 'GetNivo4TujiRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetNivo4TujiRsTp), 'Nivo4Tuji', '[ArrayItemName="Nivo4TujiTp"]');
  RemClassRegistry.RegisterXSClass(GetNapitninaSkupajRqTp, 'http://ros.si/R16', 'GetNapitninaSkupajRqTp');
  RemClassRegistry.RegisterXSClass(GetNapitninaSkupajTp, 'http://ros.si/R16', 'GetNapitninaSkupajTp');
  RemClassRegistry.RegisterXSClass(GetPraznikiRqTp, 'http://ros.si/R16', 'GetPraznikiRqTp');
  RemClassRegistry.RegisterXSClass(PraznikTp, 'http://ros.si/R16', 'PraznikTp');
  RemClassRegistry.RegisterXSInfo(TypeInfo(ArrayOfPraznikTp), 'http://ros.si/R16', 'ArrayOfPraznikTp');
  RemClassRegistry.RegisterXSClass(GetPraznikiRsTp, 'http://ros.si/R16', 'GetPraznikiRsTp');
  RemClassRegistry.RegisterExternalPropName(TypeInfo(GetPraznikiRsTp), 'Prazniki', '[ArrayItemName="PraznikTp"]');
end;

procedure RegisterTypeProc2;
begin
end;

initialization
  { KasaSoap }
  InvRegistry.RegisterInterface(TypeInfo(KasaSoap), 'http://ros.si/R16', '');
  InvRegistry.RegisterDefaultSOAPAction(TypeInfo(KasaSoap), 'http://ros.si/R16/%operationName%');
  InvRegistry.RegisterInvokeOptions(TypeInfo(KasaSoap), ioDocument);
  { KasaSoap.timeoutTest }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'timeoutTest', '',
                                 '[ReturnName="timeoutTestResult"]', IS_OPTN);
  { KasaSoap.transportTest }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'transportTest', '',
                                 '[ReturnName="transportTestResult"]', IS_OPTN);
  { KasaSoap.testGetRacuniSeznam }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'testGetRacuniSeznam', '',
                                 '[ReturnName="testGetRacuniSeznamResult"]', IS_OPTN);
  { KasaSoap.getOsebaToken }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getOsebaToken', '',
                                 '[ReturnName="getOsebaTokenResult"]', IS_OPTN);
  { KasaSoap.getKomanda }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getKomanda', '',
                                 '[ReturnName="getKomandaResult"]', IS_OPTN);
  { KasaSoap.SetOsebaDatumPrijaveSysdate }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'SetOsebaDatumPrijaveSysdate', '',
                                 '[ReturnName="SetOsebaDatumPrijaveSysdateResult"]', IS_OPTN);
  { KasaSoap.setKomanda }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setKomanda', '',
                                 '[ReturnName="setKomandaResult"]', IS_OPTN);
  { KasaSoap.aktivirajMobile }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'aktivirajMobile', '',
                                 '[ReturnName="aktivirajMobileResult"]', IS_OPTN);
  { KasaSoap.getAppConfig }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getAppConfig', '',
                                 '[ReturnName="getAppConfigResult"]', IS_OPTN);
  { KasaSoap.getMobileSetups }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getMobileSetups', '',
                                 '[ReturnName="getMobileSetupsResult"]', IS_OPTN);
  { KasaSoap.setMobileMobileSetup }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setMobileMobileSetup', '',
                                 '[ReturnName="setMobileMobileSetupResult"]', IS_OPTN);
  { KasaSoap.getTimes }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getTimes', '',
                                 '[ReturnName="getTimesResult"]', IS_OPTN);
  { KasaSoap.insertKronologija }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'insertKronologija', '',
                                 '[ReturnName="insertKronologijaResult"]', IS_OPTN);
  InvRegistry.RegisterParamInfo(TypeInfo(KasaSoap), 'insertKronologija', 'rq', '',
                                '[ArrayItemName="KronologijaTp"]');
  { KasaSoap.insertKronologija2 }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'insertKronologija2', '',
                                 '[ReturnName="insertKronologija2Result"]', IS_OPTN);
  InvRegistry.RegisterParamInfo(TypeInfo(KasaSoap), 'insertKronologija2', 'rq', '',
                                '[ArrayItemName="KronologijaTp"]');
  { KasaSoap.insertKronolog }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'insertKronolog', '',
                                 '[ReturnName="insertKronologResult"]', IS_OPTN);
  { KasaSoap.gdprLog }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'gdprLog', '',
                                 '[ReturnName="gdprLogResult"]', IS_OPTN);
  InvRegistry.RegisterParamInfo(TypeInfo(KasaSoap), 'gdprLog', 'idji', '',
                                '[ArrayItemName="long"]');
  { KasaSoap.getHitreTipke }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getHitreTipke', '',
                                 '[ReturnName="getHitreTipkeResult"]', IS_OPTN);
  { KasaSoap.getHitreTipke2 }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getHitreTipke2', '',
                                 '[ReturnName="getHitreTipke2Result"]', IS_OPTN);
  { KasaSoap.hitraTipkaGetOne }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'hitraTipkaGetOne', '',
                                 '[ReturnName="hitraTipkaGetOneResult"]', IS_OPTN);
  { KasaSoap.hitraTipkaDelete }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'hitraTipkaDelete', '',
                                 '[ReturnName="hitraTipkaDeleteResult"]', IS_OPTN);
  { KasaSoap.hitraTipkaInsert }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'hitraTipkaInsert', '',
                                 '[ReturnName="hitraTipkaInsertResult"]', IS_OPTN);
  { KasaSoap.hitraTipkaUpdate }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'hitraTipkaUpdate', '',
                                 '[ReturnName="hitraTipkaUpdateResult"]', IS_OPTN);
  { KasaSoap.getCenik }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getCenik', '',
                                 '[ReturnName="getCenikResult"]', IS_OPTN);
  { KasaSoap.getNacPlac }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNacPlac', '',
                                 '[ReturnName="getNacPlacResult"]', IS_OPTN);
  { KasaSoap.getNacPlac2 }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNacPlac2', '',
                                 '[ReturnName="getNacPlac2Result"]', IS_OPTN);
  { KasaSoap.getPartner }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getPartner', '',
                                 '[ReturnName="getPartnerResult"]', IS_OPTN);
  { KasaSoap.getProstor }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getProstor', '',
                                 '[ReturnName="getProstorResult"]', IS_OPTN);
  { KasaSoap.getKartprij }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getKartprij', '',
                                 '[ReturnName="getKartprijResult"]', IS_OPTN);
  { KasaSoap.getListaSkupin }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getListaSkupin', '',
                                 '[ReturnName="getListaSkupinResult"]', IS_OPTN);
  { KasaSoap.getPrijava }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getPrijava', '',
                                 '[ReturnName="getPrijavaResult"]', IS_OPTN);
  { KasaSoap.setRacun }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setRacun', '',
                                 '[ReturnName="setRacunResult"]', IS_OPTN);
  { KasaSoap.insertPlacilo }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'insertPlacilo', '',
                                 '[ReturnName="insertPlaciloResult"]', IS_OPTN);
  { KasaSoap.setStorno }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setStorno', '',
                                 '[ReturnName="setStornoResult"]', IS_OPTN);
  { KasaSoap.setStornoRacuna }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setStornoRacuna', '',
                                 '[ReturnName="setStornoRacunaResult"]', IS_OPTN);
  { KasaSoap.getRacuniSeznam }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getRacuniSeznam', '',
                                 '[ReturnName="getRacuniSeznamResult"]', IS_OPTN);
  { KasaSoap.getRacuni }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getRacuni', '',
                                 '[ReturnName="getRacuniResult"]', IS_OPTN);
  { KasaSoap.insertIzpisan }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'insertIzpisan', '',
                                 '[ReturnName="insertIzpisanResult"]', IS_OPTN);
  { KasaSoap.getVerzijaOfRacglava }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getVerzijaOfRacglava', '',
                                 '[ReturnName="getVerzijaOfRacglavaResult"]');
  { KasaSoap.narediObracun }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'narediObracun', '',
                                 '[ReturnName="narediObracunResult"]', IS_OPTN);
  { KasaSoap.zamenjajLastnika }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'zamenjajLastnika', '',
                                 '[ReturnName="zamenjajLastnikaResult"]', IS_OPTN);
  { KasaSoap.zdruziRacune }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'zdruziRacune', '',
                                 '[ReturnName="zdruziRacuneResult"]', IS_OPTN);
  { KasaSoap.getNovaNarocila }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNovaNarocila', '',
                                 '[ReturnName="getNovaNarocilaResult"]', IS_OPTN);
  { KasaSoap.prevzamiNarocila }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'prevzamiNarocila', '',
                                 '[ReturnName="prevzamiNarocilaResult"]', IS_OPTN);
  { KasaSoap.getObroki }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getObroki', '',
                                 '[ReturnName="getObrokiResult"]', IS_OPTN);
  { KasaSoap.getInkasoOsebe }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getInkasoOsebe', '',
                                 '[ReturnName="getInkasoOsebeResult"]', IS_OPTN);
  { KasaSoap.getNacPlacMakro }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNacPlacMakro', '',
                                 '[ReturnName="getNacPlacMakroResult"]', IS_OPTN);
  { KasaSoap.getLojalnostna }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getLojalnostna', '',
                                 '[ReturnName="getLojalnostnaResult"]', IS_OPTN);
  { KasaSoap.getDodatki }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getDodatki', '',
                                 '[ReturnName="getDodatkiResult"]', IS_OPTN);
  { KasaSoap.setLojalnost }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setLojalnost', '',
                                 '[ReturnName="setLojalnostResult"]', IS_OPTN);
  { KasaSoap.getQrFurs }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getQrFurs', '',
                                 '[ReturnName="getQrFursResult"]', IS_OPTN);
  { KasaSoap.searchGost }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'searchGost', '',
                                 '[ReturnName="searchGostResult"]', IS_OPTN);
  { KasaSoap.getSlipEma2 }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getSlipEma2', '',
                                 '[ReturnName="getSlipEma2Result"]', IS_OPTN);
  { KasaSoap.getSlipEma }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getSlipEma', '',
                                 '[ReturnName="getSlipEmaResult"]', IS_OPTN);
  { KasaSoap.setSlipEma }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setSlipEma', '',
                                 '[ReturnName="setSlipEmaResult"]', IS_OPTN);
  { KasaSoap.getReport }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getReport', '',
                                 '[ReturnName="getReportResult"]', IS_OPTN);
  { KasaSoap.getDelovniNalogi }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getDelovniNalogi', '',
                                 '[ReturnName="getDelovniNalogiResult"]', IS_OPTN);
  { KasaSoap.getReceptura }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getReceptura', '',
                                 '[ReturnName="getRecepturaResult"]', IS_OPTN);
  { KasaSoap.getStornoRazlogi }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getStornoRazlogi', '',
                                 '[ReturnName="getStornoRazlogiResult"]', IS_OPTN);
  { KasaSoap.getStornoId }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getStornoId', '',
                                 '[ReturnName="getStornoIdResult"]', IS_OPTN);
  { KasaSoap.getVrednost }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getVrednost', '',
                                 '[ReturnName="getVrednostResult"]', IS_OPTN);
  { KasaSoap.getNivo4IdFromBarcode }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNivo4IdFromBarcode', '',
                                 '[ReturnName="getNivo4IdFromBarcodeResult"]', IS_OPTN);
  { KasaSoap.natisniHod }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'natisniHod', '',
                                 '[ReturnName="natisniHodResult"]', IS_OPTN);
  { KasaSoap.getNivo4Tuji }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNivo4Tuji', '',
                                 '[ReturnName="getNivo4TujiResult"]', IS_OPTN);
  { KasaSoap.getPrazniki }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getPrazniki', '',
                                 '[ReturnName="getPraznikiResult"]', IS_OPTN);
  { KasaSoap.getNapitninaSkupaj }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNapitninaSkupaj', '',
                                 '[ReturnName="getNapitninaSkupajResult"]', IS_OPTN);
  { KasaSoap.setNapitnina }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setNapitnina', '',
                                 '[ReturnName="setNapitninaResult"]', IS_OPTN);
  { KasaSoap.getNapitnina }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getNapitnina', '',
                                 '[ReturnName="getNapitninaResult"]', IS_OPTN);
  { KasaSoap.getBazenKarte }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getBazenKarte', '',
                                 '[ReturnName="getBazenKarteResult"]', IS_OPTN);
  { KasaSoap.setBazenKarte }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'setBazenKarte', '',
                                 '[ReturnName="setBazenKarteResult"]', IS_OPTN);
  { KasaSoap.akcijaGet }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'akcijaGet', '',
                                 '[ReturnName="akcijaGetResult"]', IS_OPTN);
  { KasaSoap.akcijaArtikli }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'akcijaArtikli', '',
                                 '[ReturnName="akcijaArtikliResult"]', IS_OPTN);
  { KasaSoap.akcijaNaziv }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'akcijaNaziv', '',
                                 '[ReturnName="akcijaNazivResult"]', IS_OPTN);
  { KasaSoap.akcijaStornoKupon }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'akcijaStornoKupon', '',
                                 '[ReturnName="akcijaStornoKuponResult"]', IS_OPTN);
  { KasaSoap.akcijaKoristiKupon }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'akcijaKoristiKupon', '',
                                 '[ReturnName="akcijaKoristiKuponResult"]', IS_OPTN);
  { KasaSoap.akcijaSetLojalnost }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'akcijaSetLojalnost', '',
                                 '[ReturnName="akcijaSetLojalnostResult"]', IS_OPTN);
  { KasaSoap.akcijaSetKuponiRacuna }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'akcijaSetKuponiRacuna', '',
                                 '[ReturnName="akcijaSetKuponiRacunaResult"]', IS_OPTN);
  { KasaSoap.monetaGetToken }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'monetaGetToken', '',
                                 '[ReturnName="monetaGetTokenResult"]', IS_OPTN);
  { KasaSoap.monetaCancelTransaction }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'monetaCancelTransaction', '',
                                 '[ReturnName="monetaCancelTransactionResult"]', IS_OPTN);
  { KasaSoap.monetaGetTransactionStatus }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'monetaGetTransactionStatus', '',
                                 '[ReturnName="monetaGetTransactionStatusResult"]', IS_OPTN);
  { KasaSoap.monetaReverse }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'monetaReverse', '',
                                 '[ReturnName="monetaReverseResult"]', IS_OPTN);
  { KasaSoap.mbillsSale }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'mbillsSale', '',
                                 '[ReturnName="mbillsSaleResult"]', IS_OPTN);
  { KasaSoap.mbillsGetStatus }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'mbillsGetStatus', '',
                                 '[ReturnName="mbillsGetStatusResult"]', IS_OPTN);
  { KasaSoap.mbillsFurs }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'mbillsFurs', '',
                                 '[ReturnName="mbillsFursResult"]', IS_OPTN);
  { KasaSoap.mbillsRefund }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'mbillsRefund', '',
                                 '[ReturnName="mbillsRefundResult"]', IS_OPTN);
  { KasaSoap.mbillsVoid }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'mbillsVoid', '',
                                 '[ReturnName="mbillsVoidResult"]', IS_OPTN);
  { KasaSoap.valuStartPayment }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'valuStartPayment', '',
                                 '[ReturnName="valuStartPaymentResult"]', IS_OPTN);
  { KasaSoap.valuGetPaymentStatus }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'valuGetPaymentStatus', '',
                                 '[ReturnName="valuGetPaymentStatusResult"]', IS_OPTN);
  { KasaSoap.valuStornoPayment }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'valuStornoPayment', '',
                                 '[ReturnName="valuStornoPaymentResult"]', IS_OPTN);
  { KasaSoap.crmInfo }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'crmInfo', '',
                                 '[ReturnName="crmInfoResult"]', IS_OPTN);
  { KasaSoap.crmTocke }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'crmTocke', '',
                                 '[ReturnName="crmTockeResult"]', IS_OPTN);
  { KasaSoap.crmGetStTock }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'crmGetStTock', '',
                                 '[ReturnName="crmGetStTockResult"]', IS_OPTN);
  { KasaSoap.bonSaldo }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'bonSaldo', '',
                                 '[ReturnName="bonSaldoResult"]', IS_OPTN);
  { KasaSoap.bonKnjizi }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'bonKnjizi', '',
                                 '[ReturnName="bonKnjiziResult"]', IS_OPTN);
  { KasaSoap.bonStorno }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'bonStorno', '',
                                 '[ReturnName="bonStornoResult"]', IS_OPTN);
  { KasaSoap.boniIzdaja }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'boniIzdaja', '',
                                 '[ReturnName="boniIzdajaResult"]', IS_OPTN);
  { KasaSoap.kuponSaldo }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'kuponSaldo', '',
                                 '[ReturnName="kuponSaldoResult"]', IS_OPTN);
  { KasaSoap.kuponKnjizi }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'kuponKnjizi', '',
                                 '[ReturnName="kuponKnjiziResult"]', IS_OPTN);
  { KasaSoap.kuponStorno }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'kuponStorno', '',
                                 '[ReturnName="kuponStornoResult"]', IS_OPTN);
  { KasaSoap.getKuponAkcija }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'getKuponAkcija', '',
                                 '[ReturnName="getKuponAkcijaResult"]', IS_OPTN);
  { KasaSoap.kuponAkcijaSaldo }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'kuponAkcijaSaldo', '',
                                 '[ReturnName="kuponAkcijaSaldoResult"]', IS_OPTN);
  { KasaSoap.kuponAkcijaKnjizi }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'kuponAkcijaKnjizi', '',
                                 '[ReturnName="kuponAkcijaKnjiziResult"]', IS_OPTN);
  { KasaSoap.kuponAkcijaStorno }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'kuponAkcijaStorno', '',
                                 '[ReturnName="kuponAkcijaStornoResult"]', IS_OPTN);
  { KasaSoap.tbBalance }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'tbBalance', '',
                                 '[ReturnName="tbBalanceResult"]', IS_OPTN);
  { KasaSoap.tbRacunValidate }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'tbRacunValidate', '',
                                 '[ReturnName="tbRacunValidateResult"]', IS_OPTN);
  { KasaSoap.tbRacunDeposit }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'tbRacunDeposit', '',
                                 '[ReturnName="tbRacunDepositResult"]', IS_OPTN);
  { KasaSoap.tbDocumentValidate }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'tbDocumentValidate', '',
                                 '[ReturnName="tbDocumentValidateResult"]', IS_OPTN);
  { KasaSoap.tbDocumentStorno }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'tbDocumentStorno', '',
                                 '[ReturnName="tbDocumentStornoResult"]', IS_OPTN);
  { KasaSoap.tbRacunStorno }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'tbRacunStorno', '',
                                 '[ReturnName="tbRacunStornoResult"]', IS_OPTN);
  { KasaSoap.SixTransaction }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'SixTransaction', '',
                                 '[ReturnName="SixTransactionResult"]', IS_OPTN);
  { KasaSoap.SixBalance }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'SixBalance', '',
                                 '[ReturnName="SixBalanceResult"]', IS_OPTN);
  { KasaSoap.SixTerminalStatus }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'SixTerminalStatus', '',
                                 '[ReturnName="SixTerminalStatusResult"]', IS_OPTN);
  { KasaSoap.SixTerminalCancel }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'SixTerminalCancel', '',
                                 '[ReturnName="SixTerminalCancelResult"]', IS_OPTN);
  { KasaSoap.SixTerminalReboot }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'SixTerminalReboot', '',
                                 '[ReturnName="SixTerminalRebootResult"]', IS_OPTN);
  { KasaSoap.Six2Transaction }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2Transaction', '',
                                 '[ReturnName="Six2TransactionResult"]', IS_OPTN);
  { KasaSoap.Six2Balance }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2Balance', '',
                                 '[ReturnName="Six2BalanceResult"]', IS_OPTN);
  { KasaSoap.Six2TerminalStatus }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2TerminalStatus', '',
                                 '[ReturnName="Six2TerminalStatusResult"]', IS_OPTN);
  { KasaSoap.Six2Cancel }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2Cancel', '',
                                 '[ReturnName="Six2CancelResult"]', IS_OPTN);
  { KasaSoap.Six2DisposeTerminal }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2DisposeTerminal', '',
                                 '[ReturnName="Six2DisposeTerminalResult"]', IS_OPTN);
  { KasaSoap.Six2RebootTerminal }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2RebootTerminal', '',
                                 '[ReturnName="Six2RebootTerminalResult"]', IS_OPTN);
  { KasaSoap.Six2HardwareInformation }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2HardwareInformation', '',
                                 '[ReturnName="Six2HardwareInformationResult"]', IS_OPTN);
  { KasaSoap.Six2SystemInformation }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2SystemInformation', '',
                                 '[ReturnName="Six2SystemInformationResult"]', IS_OPTN);
  { KasaSoap.Six2ReceiptRequest }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'Six2ReceiptRequest', '',
                                 '[ReturnName="Six2ReceiptRequestResult"]', IS_OPTN);
  { KasaSoap.eDenarGetSaldo }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'eDenarGetSaldo', '',
                                 '[ReturnName="eDenarGetSaldoResult"]', IS_OPTN);
  { KasaSoap.eDenarKnjizi }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'eDenarKnjizi', '',
                                 '[ReturnName="eDenarKnjiziResult"]', IS_OPTN);
  InvRegistry.RegisterParamInfo(TypeInfo(KasaSoap), 'eDenarKnjizi', 'placiloPozicijaId', '',
                                '', IS_NLBL);
  { KasaSoap.eDenarBrisi }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'eDenarBrisi', '',
                                 '[ReturnName="eDenarBrisiResult"]', IS_OPTN);
  InvRegistry.RegisterParamInfo(TypeInfo(KasaSoap), 'eDenarBrisi', 'placiloPozicijaId', '',
                                '', IS_NLBL);
  { KasaSoap.eDenarClear }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'eDenarClear', '',
                                 '[ReturnName="eDenarClearResult"]', IS_OPTN);
  { KasaSoap.eDenarSetStatus }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'eDenarSetStatus', '',
                                 '[ReturnName="eDenarSetStatusResult"]', IS_OPTN);
  { KasaSoap.eDenarStornoZaRacun }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'eDenarStornoZaRacun', '',
                                 '[ReturnName="eDenarStornoZaRacunResult"]', IS_OPTN);
  { KasaSoap.eDenarCountStornoZaRacun }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'eDenarCountStornoZaRacun', '',
                                 '[ReturnName="eDenarCountStornoZaRacunResult"]', IS_OPTN);
  { KasaSoap.monitorWriteLog }
  InvRegistry.RegisterMethodInfo(TypeInfo(KasaSoap), 'monitorWriteLog', '',
                                 '[ReturnName="monitorWriteLogResult"]', IS_OPTN);
  InvRegistry.RegisterParamInfo(TypeInfo(KasaSoap), 'monitorWriteLog', 'minute', '',
                                '', IS_NLBL);
  RegisterTypeProc0;
  RegisterTypeProc1;

end.
