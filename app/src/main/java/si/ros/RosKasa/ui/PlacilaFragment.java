package si.ros.RosKasa.ui;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.Globals;
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.databinding.FragmentPlacilaBinding;
import si.ros.RosKasa.models.GetRacunRsTp;
import si.ros.RosKasa.models.NacPlacTp;
import si.ros.RosKasa.models.NarociloItem;
import si.ros.RosKasa.models.PlaciloTp;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunTp;
import si.ros.RosKasa.models.KartprijTp;
import si.ros.RosKasa.models.PartnerTp;
import si.ros.RosKasa.models.DelovniNalogTp;
import si.ros.RosKasa.models.LojalnostnaTp;
import si.ros.RosKasa.models.SlipEmaTp;
import si.ros.RosKasa.payment.PaytenPaymentService;
import si.ros.RosKasa.payment.SixTapPaymentService;
import si.ros.RosKasa.payment.PaymentRecoveryManager;
import si.ros.RosKasa.payment.models.PaytenResponse;
import si.ros.RosKasa.payment.models.SixTapResponse;
import java.math.RoundingMode;
import si.ros.RosKasa.print.BluetoothPrintHelper;
import si.ros.RosKasa.soap.RosKasaSoapClient;
import si.ros.RosKasa.soap.VersionConflictException;
import si.ros.RosKasa.R;
import android.graphics.Color;

public class PlacilaFragment extends Fragment {

    private static final String TAG = "PlacilaFragment";

    private FragmentPlacilaBinding binding;
    private AppPreferences prefs;

    private NarociloItemAdapter placilaAdapter;
    private final List<NarociloItem> placilaItems = new ArrayList<>();
    private String activeMarker = "Miza 1";

    private RacunTp currentRacun;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlacilaBinding.inflate(inflater, container, false);
        prefs = new AppPreferences(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!prefs.getActiveMarker().isEmpty()) {
            activeMarker = prefs.getActiveMarker();
        }

        setupPlacilaRecyclerView();
        setupNavigationButtons();
        setupPaymentMethodButtons();

        loadRacunData();
    }

    private void setupPlacilaRecyclerView() {
        placilaAdapter = new NarociloItemAdapter();
        binding.rvPlacilaItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPlacilaItems.setAdapter(placilaAdapter);
    }

    private void loadRacunData() {
        currentRacun = Globals.getInstance().getCurrentRacun();
        int activeRacunId = prefs.getActiveRacunId();

        if (activeRacunId > 0 && (currentRacun == null || currentRacun.getRacunId() != activeRacunId || currentRacun.getZnesek().compareTo(BigDecimal.ZERO) == 0)) {
            disableEkran("Nalaganje podatkov računa #" + activeRacunId + "...");
            executor.execute(() -> {
                try {
                    RacunTp loaded = RosKasaSoapClient.getRacun(prefs.getServerUrl(), prefs.getToken(), activeRacunId);
                    mainHandler.post(() -> {
                        enableEkran();
                        if (loaded != null) {
                            posodobiRacunIzStreznik(loaded);
                        } else {
                            Toast.makeText(requireContext(), "Račun #" + activeRacunId + " ni bil najden na strežniku.", Toast.LENGTH_SHORT).show();
                            updatePlacilaSummary();
                        }
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                        enableEkran();
                        Toast.makeText(requireContext(), "Napaka pri nalaganju računa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        updatePlacilaSummary();
                    });
                }
            });
        } else {
            populatePlacilaListFromCurrentRacun();
        }
    }

    private void populatePlacilaListFromCurrentRacun() {
        placilaItems.clear();
        if (currentRacun != null) {
            currentRacun.preracunajVsote();
            if (currentRacun.getRacPlaci() != null) {
                for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                    if (pl != null && !pl.isRowDeleted()) {
                        String nacin = getPaymentName(pl.getPlaciloId());
                        BigDecimal amt = (pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0)
                                ? pl.getDelniZnesek()
                                : (pl.getZnesek() != null ? pl.getZnesek() : BigDecimal.ZERO);
                        placilaItems.add(new NarociloItem(nacin, amt, 1.0));
                    }
                }
            }
        }
        updatePlacilaSummary();
    }

    private String getPaymentName(Integer placiloId) {
        if (placiloId == null) return "GOTOVINA";
        NacPlacTp np = Globals.getInstance().getPlaciloById(placiloId);
        if (np != null && np.getNaziv() != null && !np.getNaziv().trim().isEmpty()) {
            return np.getNaziv();
        }
        switch (placiloId) {
            case 1: return "GOTOVINA";
            case 2: return "KREDITNA K POS";
            case 3: return "KRED. K ROCNO";
            case 4: return "DOBAVNICA";
            case 5: return "REPREZENTANCA";
            case 6: return "VALU";
            case 7: return "mBills";
            case 8: return "GOST HOTELA";
            case 99: return "POPUST NA RAČUN";
            case 399: return "KREDIT. KARTICA";
            default: return "PLAČILO #" + placiloId;
        }
    }

    private void updatePlacilaSummary() {
        placilaAdapter.setItems(placilaItems);

        BigDecimal popust99 = BigDecimal.ZERO;
        if (currentRacun != null && currentRacun.getRacPlaci() != null) {
            for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() == 99) {
                    if (pl.getZnesek() != null) {
                        popust99 = popust99.add(pl.getZnesek());
                    }
                }
            }
        }

        BigDecimal znesekRacuna = (currentRacun != null && currentRacun.getZnesek() != null) ? currentRacun.getZnesek() : BigDecimal.ZERO;
        BigDecimal totalPlacano = (currentRacun != null && currentRacun.getPlacano() != null) ? currentRacun.getPlacano() : BigDecimal.ZERO;

        String markerText = (currentRacun != null && currentRacun.getMarker() != null && !currentRacun.getMarker().isEmpty())
                ? currentRacun.getMarker()
                : (activeMarker != null ? activeMarker : "");

        if (popust99.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal polnaVsota = znesekRacuna.add(popust99);
            binding.tvMizaStatus.setText(String.format(Locale.getDefault(), "M: %s  -  Zn: %.2f (Popust: %.2f, ZaPl: %.2f / Pl: %.2f)", markerText, polnaVsota, popust99, znesekRacuna, totalPlacano));
        } else {
            binding.tvMizaStatus.setText(String.format(Locale.getDefault(), "M: %s  -  Zn: %.2f / Pl: %.2f", markerText, znesekRacuna, totalPlacano));
        }

        if (currentRacun != null && currentRacun.getDnId() != null && !currentRacun.getDnId().trim().isEmpty()) {
            binding.btnDelovniNalog.setText("DN:\n" + currentRacun.getDnId());
        } else {
            binding.btnDelovniNalog.setText("Delovni\nnalog");
        }

        if (Globals.getInstance().ispLojalnostPopust() && binding != null && binding.btnPayLoyPopust != null) {
            binding.btnPayLoyPopust.setVisibility(View.VISIBLE);
            if (currentRacun != null && currentRacun.getLojalnostId() != null && currentRacun.getLojalnostId() > 0) {
                binding.btnPayLoyPopust.setText("Lojalnost\n[R" + currentRacun.getLojalnostId() + "]");
                binding.btnPayLoyPopust.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#EF6C00")));
            } else {
                binding.btnPayLoyPopust.setText("Lojalnostni\npopust");
                binding.btnPayLoyPopust.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#E65100")));
            }
        }
    }

    private BigDecimal getPreostanekZaPlacilo() {
        if (currentRacun == null) return BigDecimal.ZERO;
        currentRacun.preracunajVsote();
        BigDecimal zaPlacilo = currentRacun.getZnesek();
        BigDecimal remaining = zaPlacilo.subtract(currentRacun.getPlacano());
        return remaining.compareTo(BigDecimal.ZERO) > 0 ? remaining : BigDecimal.ZERO;
    }

    private void addPlacilo(String nacin, int placiloId, BigDecimal znesek) {
        addPlacilo(nacin, placiloId, znesek, null);
    }

    private void addPlacilo(final String nacin, final int placiloId, final BigDecimal znesek, final String stKarticeOpis) {
        addPlaciloFull(nacin, placiloId, znesek, stKarticeOpis, null, null, null, null, null, null, null);
    }

    private void addPlaciloHotelKredit(final String nacin, final int placiloId, final BigDecimal znesek, final KartprijTp room) {
        if (room == null) return;
        String opis = nacin + " (Soba " + room.getProstorId() + " - " + room.getImeGosta() + ")";
        int racId = (currentRacun != null) ? currentRacun.getRacunId() : 0;
        Globals.getInstance().vpisiKronologijoDebugL0(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                "Hotel kredit ZapisiPlaciloSoba R:" + racId + " prijava: " + room.getPrijavaId() + " soba: " + room.getProstorId() + " znesek: " + znesek,
                Globals.getInstance().getTekocaOsebaId(), Globals.getInstance().getTocilnicaId());
        addPlaciloFull(opis, placiloId, znesek, String.valueOf(room.getProstorId()), room.getPrijavaId(), null, room.getImeGosta(), null, null, null, 2);
    }

    private void addPlaciloPartner(final String nacin, final int placiloId, final BigDecimal znesek, final int partnerId, final String naziv, final String naslov, final String davcna, final String stNaroc, final BigDecimal rabat) {
        BigDecimal dejanskiZnesek = znesek;
        if (rabat != null && rabat.compareTo(BigDecimal.ZERO) > 0) {
            boolean zeImaPopust = false;
            if (currentRacun != null && currentRacun.getRacPlaci() != null) {
                for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                    if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() == 99) {
                        zeImaPopust = true;
                        break;
                    }
                }
            }
            if (!zeImaPopust && currentRacun != null) {
                Globals.getInstance().popustNaRacun(currentRacun, rabat, BigDecimal.ZERO);
                currentRacun.preracunajVsote();
                dejanskiZnesek = getPreostanekZaPlacilo();
            }
        }
        int racId = (currentRacun != null) ? currentRacun.getRacunId() : 0;
        Globals.getInstance().vpisiKronologijoDebugL1(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                "Partner ZapisiPlacilo R:" + racId + " partner: " + partnerId + " (" + (naziv != null ? naziv : "") + ") znesek: " + dejanskiZnesek,
                Globals.getInstance().getTekocaOsebaId(), Globals.getInstance().getTocilnicaId());
        String opis = (naziv != null && !naziv.isEmpty()) ? (nacin + " (" + naziv + ")") : nacin;
        addPlaciloFull(opis, placiloId, dejanskiZnesek, null, null, (partnerId > 0 ? partnerId : null), naziv, naslov, davcna, stNaroc, null);
    }

    private void addPlaciloFull(final String nacin, final int placiloId, final BigDecimal znesek,
                                final String stKarticeOpis, final Integer gostPrijavaId, final Integer partnerId,
                                final String nazivPartner, final String naslovPartner, final String davcnaSt,
                                final String stNaroc, final Integer overrideFiskalizacija) {
        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni odprtega računa!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (znesek == null || znesek.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(requireContext(), "Račun je že v celoti pokrit!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentRacun.getOriginalObject() == null) {
            currentRacun.setOriginalObject(currentRacun.deepCopy());
        }

        final PlaciloTp pl = new PlaciloTp(currentRacun.getRacunId(), placiloId, znesek);
        pl.setPlaciloId(placiloId);
        pl.setDelniZnesek(znesek);
        pl.setZnesek(BigDecimal.ZERO);

        int nextPozId = -1;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp p : currentRacun.getRacPlaci()) {
                if (p != null && p.getPozicijaId() <= nextPozId) {
                    nextPozId = p.getPozicijaId() - 1;
                }
            }
        }
        pl.setPozicijaId(nextPozId);
        pl.setVerzijaZapisa(0);
        pl.setRowDeleted(false);
        pl.setOriginalObject(null);
        if (stKarticeOpis != null && !stKarticeOpis.isEmpty()) {
            pl.setStKartice(stKarticeOpis);
        }
        if (gostPrijavaId != null && gostPrijavaId > 0) {
            pl.setGostPrijavaId(gostPrijavaId);
        }
        if (partnerId != null && partnerId > 0) {
            pl.setPartnerId(partnerId);
            pl.setKupecId(partnerId);
        }
        if (nazivPartner != null && !nazivPartner.isEmpty()) {
            pl.setNazivPartner(nazivPartner);
        }
        if (naslovPartner != null && !naslovPartner.isEmpty()) {
            pl.setNaslovPartner(naslovPartner);
        }
        if (davcnaSt != null && !davcnaSt.isEmpty()) {
            pl.setDavcnaSt(davcnaSt);
        }
        if (stNaroc != null && !stNaroc.isEmpty()) {
            pl.setStNarocilnice(stNaroc);
        }

        int tocId = (currentRacun.getTocilnicaId() != null && currentRacun.getTocilnicaId() > 0)
                ? currentRacun.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        pl.setTocilnicaId(tocId);

        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setNeNarocaj(true);
            }
        }
        currentRacun.setStatus(1);
        currentRacun.setMarker(activeMarker);
        currentRacun.setfPosId(prefs.getfPosId() > 0 ? prefs.getfPosId() : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500));
        currentRacun.setfPoslovniProstorId(Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000);
        currentRacun.setTocilnicaId(tocId);
        if (overrideFiskalizacija != null) {
            currentRacun.setFiskalizacija(overrideFiskalizacija);
        }
        if (partnerId != null && partnerId > 0) {
            currentRacun.setPartnerId(partnerId);
        }
        if (Globals.getInstance().getTekocaOsebaId() > 0) {
            currentRacun.setKasiral(Globals.getInstance().getTekocaOsebaId());
        } else if (currentRacun.getKasiral() == null || currentRacun.getKasiral() <= 0) {
            currentRacun.setKasiral(9999);
        }
        if (currentRacun.getTipRacuna() == null || currentRacun.getTipRacuna() <= 0) {
            currentRacun.setTipRacuna(1);
        }
        if (currentRacun.getStPogrinjkov() == null || currentRacun.getStPogrinjkov() <= 0) {
            currentRacun.setStPogrinjkov(1);
        }
        if (currentRacun.getStKopij() == null) {
            currentRacun.setStKopij(0);
        }

        if (currentRacun.getRacPlaci() == null) {
            currentRacun.setRacPlaci(new ArrayList<>());
        }
        currentRacun.getRacPlaci().add(pl);

        // Sinhroniziraj RACGLAVA.PLACANO z novim zneskom plačila
        BigDecimal trenPlacano = currentRacun.getPlacano() != null ? currentRacun.getPlacano() : BigDecimal.ZERO;
        currentRacun.setPlacano(trenPlacano.add(znesek));
        currentRacun.preracunajVsote();
        updatePlacilaSummary();

        disableEkran("Knjiženje plačila " + nacin + " na strežnik...");
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                mainHandler.post(() -> {
                    enableEkran();
                    if (response != null && response.getRacGlava() != null) {
                        posodobiRacunIzStreznik(response.getRacGlava());

                        if (currentRacun.getPlacano().compareTo(currentRacun.getZnesek()) >= 0) {
                            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                                    .setTitle("Račun v celoti plačan")
                                    .setMessage("Plačilo " + nacin + " (" + String.format(Locale.getDefault(), "%.2f €", znesek) + ") uspešno knjiženo na strežnik!\nRačun #" + currentRacun.getRacunId() + " je v celoti pokrit.\nŽelite natisniti račun?")
                                    .setPositiveButton("Zaključi in natisni", (d, w) -> handleIzpisRacuna())
                                    .setNegativeButton("Ostani na plačilih", null)
                                    .show();
                        } else {
                            BigDecimal preostanek = currentRacun.getZnesek().subtract(currentRacun.getPlacano());
                            Toast.makeText(requireContext(), "Plačilo " + nacin + " knjiženo! Preostanek za plačilo: " + String.format(Locale.getDefault(), "%.2f €", preostanek), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Strežnik ni vrnil posodobljenega računa.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (VersionConflictException vce) {
                mainHandler.post(() -> {
                    enableEkran();
                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Konflikt verzije računa")
                            .setMessage("Račun je medtem spremenil drug natakar. Podatki se bodo osvežili s strežnika.")
                            .setPositiveButton("V redu", (dialog, which) -> loadRacunData())
                            .setCancelable(false)
                            .show();
                });
            } catch (Exception e) {
                Globals.getInstance().vpisiKronologijo("setRacun NAPAKA R:" + (currentRacun != null ? currentRacun.getRacunId() : 0) + ": " + e.getMessage());
                mainHandler.post(() -> {
                    enableEkran();
                    if (currentRacun != null && currentRacun.getRacPlaci() != null) {
                        currentRacun.getRacPlaci().remove(pl);
                        currentRacun.preracunajVsote();
                    }
                    populatePlacilaListFromCurrentRacun();
                    Toast.makeText(requireContext(), "Napaka pri knjiženju plačila: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private boolean isPlaciloFiskalno(int plId) {
        NacPlacTp np = Globals.getInstance().getPlaciloById(plId);
        if (np != null && np.getFiskalno() != null) {
            return np.getFiskalno() == 1;
        }
        // Privzete vrednosti po metodah, če ni v cachedPlacila
        return plId == 1 || plId == 2 || plId == 3 || plId == 399;
    }

    private long lastActionTime = 0;
    private static final long DEBOUNCE_DELAY = 400; // ms

    public void klikniPlacilo(final int placiloId) {
        long now = System.currentTimeMillis();
        if (now - lastActionTime < DEBOUNCE_DELAY) {
            return;
        }
        lastActionTime = now;

        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni odprtega računa!", Toast.LENGTH_SHORT).show();
            return;
        }

        final int racId = currentRacun.getRacunId();
        // Kronologija ob kliku na katerokoli plačilo (točno po specifikaciji uporabnika):
        Globals.getInstance().vpisiKronologijo("KLIK PLACILO R:" + racId + " Placilo ID: " + placiloId);

        final BigDecimal zaplacilo = getPreostanekZaPlacilo();
        if (zaplacilo.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(requireContext(), "Račun je že v celoti plačan!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Preverjanje pravila FiskalnoEnako: če ima račun že plačila, mora biti fiskalnost enaka
        PlaciloTp firstActive = null;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() != 99) {
                    firstActive = pl;
                    break;
                }
            }
        }
        if (firstActive != null && Globals.getInstance().isFiskalnoEnako()) {
            boolean existingIsFisk = isPlaciloFiskalno(firstActive.getPlaciloId());
            boolean newIsFisk = isPlaciloFiskalno(placiloId);
            if (existingIsFisk != newIsFisk) {
                String obstojeceIme = getPaymentName(firstActive.getPlaciloId());
                String tipObstoj = existingIsFisk ? "fiskalno" : "nefiskalno";
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("FiskalnoEnako - Omejitev")
                        .setMessage("Račun že ima " + tipObstoj + " plačilo (" + obstojeceIme + ").\n\n"
                                + "Zaradi nastavitve FiskalnoEnako je dovoljeno doknjižiti le " + tipObstoj + " plačilo.")
                        .setPositiveButton("V redu", null)
                        .show();
                return;
            }
        }

        final int tempmetoda = Globals.getInstance().placilometoda(placiloId);
        final String nacinNaziv = getPaymentName(placiloId);
        final String uNaziv = nacinNaziv != null ? nacinNaziv.toUpperCase() : "";

        // Za metodo 14 (ali karticno POS placilo):
        // V Delphi: ob metodi 14 se placilo izvede TAKOJ brez odpiranja dialoga za vnos cene (celoten saldo zaplacilo)
        boolean isPosPayment = (tempmetoda == 14
                || (tempmetoda != 3 && (uNaziv.contains("POS") || placiloId == 2)));

        if (tempmetoda == 14 || isPosPayment) {
            boolean isIntentOn = Globals.getInstance().isPosIntentActive();
            klikniHitroPlaciloPoMetodi(isIntentOn ? 14 : tempmetoda);
            return;
        }

        // 2. Odpiranje okna za vnos zneska (privzeto racglava.znesek - racglava.placano)
        VnosCeneDialog.show(requireContext(), "ZNESEK - " + nacinNaziv, zaplacilo, false, new VnosCeneDialog.OnPriceEnteredListener() {
            @Override
            public void onPriceEntered(BigDecimal enteredAmt) {
                if (enteredAmt == null || enteredAmt.compareTo(BigDecimal.ZERO) <= 0) {
                    Toast.makeText(requireContext(), "Znesek mora biti večji od 0!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (enteredAmt.compareTo(zaplacilo) > 0) {
                    Toast.makeText(requireContext(), "Znesek (" + enteredAmt + " €) presega preostanek (" + zaplacilo + " €)!", Toast.LENGTH_SHORT).show();
                    return;
                }
                nadaljujSPlacilom(placiloId, tempmetoda, nacinNaziv, enteredAmt);
            }

            @Override
            public void onCancelled() {}
        });
    }

    /**
     * Hitra tipka za plačilo po METODI:
     * 1. Preveri ali je račun na strežniku (če ni, kliče setRacun)
     * 2. Izvede plačilo in izpis do konca - odvisno od metode:
     *    1 : gotovina
     *    3 : kreditnakartica za določenega kupca
     *    6 : hotel kredit
     *    14: Intent plačilo, če je vklopljeno
     */
    /**
     * Hitra tipka za plačilo po PLACILO_ID:
     * 1. Iz PLACILO_ID poiščemo METODO (preko Globals.placilometoda(placiloId))
     * 2. Preveri ali je račun na strežniku (če ni, kliče setRacun)
     * 3. Izvede se plačilo in izpis do konca z dodeljenim placiloId za racplaciTp in metodo za logiko
     */
    public void klikniHitroPlacilo(final int placiloId) {
        long now = System.currentTimeMillis();
        if (now - lastActionTime < DEBOUNCE_DELAY) {
            return;
        }
        lastActionTime = now;

        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni odprtega računa!", Toast.LENGTH_SHORT).show();
            return;
        }

        final BigDecimal zaplacilo = getPreostanekZaPlacilo();
        if (zaplacilo.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(requireContext(), "Račun je že v celoti plačan!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Poišči METODO iz PLACILO_ID
        final int metoda = Globals.getInstance().placilometoda(placiloId);

        NacPlacTp np = Globals.getInstance().getPlaciloById(placiloId);
        final String nacinNaziv = (np != null && !np.getNaziv().trim().isEmpty()) ? np.getNaziv() : getPaymentName(placiloId);

        // Preverjanje pravila FiskalnoEnako
        PlaciloTp firstActive = null;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() != 99) {
                    firstActive = pl;
                    break;
                }
            }
        }
        if (firstActive != null && Globals.getInstance().isFiskalnoEnako()) {
            boolean existingIsFisk = isPlaciloFiskalno(firstActive.getPlaciloId());
            boolean newIsFisk = isPlaciloFiskalno(placiloId);
            if (existingIsFisk != newIsFisk) {
                String obstojeceIme = getPaymentName(firstActive.getPlaciloId());
                String tipObstoj = existingIsFisk ? "fiskalno" : "nefiskalno";
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("FiskalnoEnako - Omejitev")
                        .setMessage("Račun že ima " + tipObstoj + " plačilo (" + obstojeceIme + ").\n\n"
                                + "Zaradi nastavitve FiskalnoEnako je dovoljeno doknjižiti le " + tipObstoj + " plačilo.")
                        .setPositiveButton("V redu", null)
                        .show();
                return;
            }
        }

        final int racId = currentRacun.getRacunId();
        Globals.getInstance().vpisiKronologijo("KLIK HITRO PLACILO R:" + racId + " Placilo ID: " + placiloId + " Metoda:" + metoda);

        // 1. ZA VSA HITRA PLAČILA: najprej se preveri ali je račun na serverju, če ni kliče setRacun
        zagotoviRacunNaStrezniku(() -> {
            // 2. Izvede se plačilo in izpis do konca - z dodeljenim placiloId za racplaciTp in metodo za logiko
            izvediHitroPlaciloPoMetodiInterno(metoda, placiloId, nacinNaziv, zaplacilo);
        });
    }

    public void klikniHitroPlaciloPoMetodi(final int metoda) {
        NacPlacTp np = Globals.getInstance().getPlaciloByMetoda(metoda);
        final int placiloId = (np != null) ? np.getPlaciloId() : ((metoda == 14) ? 2 : metoda);
        klikniHitroPlacilo(placiloId);
    }

    private void posodobiRacunIzStreznik(RacunTp returned) {
        if (returned == null) return;
        if ((returned.getRacPozic() == null || returned.getRacPozic().isEmpty()) && currentRacun != null && currentRacun.getRacPozic() != null) {
            returned.setRacPozic(currentRacun.getRacPozic());
        }
        if (returned.getRacPlaci() == null) {
            returned.setRacPlaci(new ArrayList<>());
        }
        if (currentRacun != null && currentRacun.getZnesek() != null && currentRacun.getZnesek().compareTo(BigDecimal.ZERO) > 0) {
            returned.setZnesek(currentRacun.getZnesek());
        }
        if (returned.getRacPozic() != null) {
            for (PozicijaTp p : returned.getRacPozic()) {
                if (p != null && (p.getNaziv() == null || p.getNaziv().trim().isEmpty()) && p.getNivo4Id() != null && p.getNivo4Id() > 0) {
                    String lookupName = Globals.getInstance().findNazivByNivo4Id(p.getNivo4Id());
                    if (lookupName != null && !lookupName.trim().isEmpty()) {
                        p.setNaziv(lookupName.trim());
                    }
                }
            }
        }
        currentRacun = returned;
        currentRacun.preracunajVsote();
        currentRacun.setOriginalObject(currentRacun.deepCopy());
        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setOriginalObject(p.deepCopy());
            }
        }
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                if (pl != null) pl.setOriginalObject(pl.deepCopy());
            }
        }
        Globals.getInstance().setCurrentRacun(currentRacun);
        prefs.setActiveRacunId(currentRacun.getRacunId());
        populatePlacilaListFromCurrentRacun();
    }

    private void posodobiLokalniRacun(RacunTp saved) {
        posodobiRacunIzStreznik(saved);
    }

    private void zagotoviRacunNaStrezniku(final Runnable onReady) {
        if (currentRacun == null) return;

        // Če račun še nima veljavnega strežniškega ID-ja (racunId <= 0):
        if (currentRacun.getRacunId() <= 0) {
            disableEkran("Shranjevanje računa na strežnik pred plačilom...");
            executor.execute(() -> {
                try {
                    String serverUrl = prefs.getServerUrl();
                    String token = prefs.getToken();
                    int mobileId = 1;
                    try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                    GetRacunRsTp resp = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                    mainHandler.post(() -> {
                        enableEkran();
                        if (resp != null && resp.getRacGlava() != null) {
                            posodobiLokalniRacun(resp.getRacGlava());
                            if (onReady != null) onReady.run();
                        } else {
                            Toast.makeText(requireContext(), "Napaka pri shranjevanju računa pred plačilom!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                        enableEkran();
                        Toast.makeText(requireContext(), "Napaka pri sinhronizaciji računa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
            return;
        }

        // Če račun že ima racunId > 0, preveri ali obstaja na strežniku preko getVerzijaOfRacglava
        disableEkran("Preverjanje računa na strežniku...");
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                int serverVer = RosKasaSoapClient.getVerzijaOfRacglava(serverUrl, token, currentRacun.getRacunId());
                if (serverVer <= 0) {
                    // Račun z racunId ne obstaja na strežniku -> takoj kliči setRacun
                    GetRacunRsTp resp = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                    mainHandler.post(() -> {
                        enableEkran();
                        if (resp != null && resp.getRacGlava() != null) {
                            posodobiLokalniRacun(resp.getRacGlava());
                            if (onReady != null) onReady.run();
                        } else {
                            Toast.makeText(requireContext(), "Napaka pri shranjevanju računa pred plačilom!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                if (serverVer > currentRacun.getVerzijaZapisa()) {
                    RacunTp refreshed = RosKasaSoapClient.getRacun(serverUrl, token, currentRacun.getRacunId());
                    mainHandler.post(() -> {
                        enableEkran();
                        if (refreshed != null) {
                            posodobiLokalniRacun(refreshed);
                            populatePlacilaListFromCurrentRacun();
                        }
                        Toast.makeText(requireContext(), "Račun je medtem posodobil drug natakar (osveženo s strežnika)!", Toast.LENGTH_SHORT).show();
                        if (onReady != null) onReady.run();
                    });
                    return;
                }

                mainHandler.post(() -> {
                    enableEkran();
                    if (onReady != null) onReady.run();
                });
            } catch (Exception e) {
                Log.w(TAG, "Preverjanje računa na strežniku opozorilo: " + e.getMessage());
                // Če je prišlo do napake (račun morda ne obstaja), poizkusi setRacun
                try {
                    String serverUrl = prefs.getServerUrl();
                    String token = prefs.getToken();
                    int mobileId = 1;
                    try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
                    GetRacunRsTp resp = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                    mainHandler.post(() -> {
                        enableEkran();
                        if (resp != null && resp.getRacGlava() != null) {
                            posodobiLokalniRacun(resp.getRacGlava());
                        }
                        if (onReady != null) onReady.run();
                    });
                } catch (Exception ex2) {
                    mainHandler.post(() -> {
                        enableEkran();
                        Toast.makeText(requireContext(), "Napaka pri sinhronizaciji računa: " + ex2.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void izvediHitroPlaciloPoMetodiInterno(final int metoda, final int placiloId, final String nacinNaziv, final BigDecimal zaplacilo) {
        final boolean isIntentOn = Globals.getInstance().isPosIntentActive();

        if (metoda == 14 && isIntentOn) {
            // Intent plačilo (če je vklopljeno)
            izvediPosPlacilo(placiloId, nacinNaziv, zaplacilo);
        } else if (metoda == 6 || placiloId == 8) {
            // Metoda 6: hotel kredit
            HotelSobeDialog.show(requireContext(), zaplacilo, selectedRoom -> {
                zakljuciInNatisniHotelKredit(placiloId, nacinNaziv, zaplacilo, selectedRoom);
            });
        } else if (metoda == 3) {
            // Metoda 3: kreditnakartica za določenega kupca / ročno
            Globals g = Globals.getInstance();
            NacPlacTp np = g.getPlaciloById(placiloId);
            int PlaciloPartnerStoritevId = 0;
            if (np != null && np.getStoritevId() != null) {
                PlaciloPartnerStoritevId = np.getStoritevId();
            }
            int nacplacstoritevid = PlaciloPartnerStoritevId;
            int kKarticaTippartnerRocno = g.getkKarticaTippartnerRocno();
            int KKARTICA_KUPECID_ROCNO = g.getkKarticaKupecIdRocno();

            if ((nacplacstoritevid == kKarticaTippartnerRocno) && ((KKARTICA_KUPECID_ROCNO > 0) && (PlaciloPartnerStoritevId == 0))) {
                // Knjižimo plačilo na KKARTICA_KUPECID_ROCNO
                g.vpisiKronologijo("Hitro plačilo KK ročno avtomatski KUPEC_ID: " + KKARTICA_KUPECID_ROCNO + " za R:" + (currentRacun != null ? currentRacun.getRacunId() : 0));
                zakljuciInNatisniHitroPlacilo(placiloId, nacinNaziv, zaplacilo, (KKARTICA_KUPECID_ROCNO > 0 ? KKARTICA_KUPECID_ROCNO : null));
            } else {
                // Izbor partnerja iz liste za tip partner = STORITEV_ID
                PartnerVnosDialog.show(requireContext(), zaplacilo, PlaciloPartnerStoritevId, (partnerId, naziv, naslov, davcna, stNarocilnice, rabat) -> {
                    zakljuciInNatisniPartner(placiloId, nacinNaziv, zaplacilo, partnerId, naziv, naslov, davcna, stNarocilnice, rabat);
                });
            }
        } else if (metoda == 4 || placiloId == 4) {
            // Metoda 4: dobavnica / partner
            NacPlacTp np = Globals.getInstance().getPlaciloById(placiloId);
            int PlaciloPartnerStoritevId = 0;
            if (np != null && np.getStoritevId() != null) {
                PlaciloPartnerStoritevId = np.getStoritevId();
            }
            PartnerVnosDialog.show(requireContext(), zaplacilo, PlaciloPartnerStoritevId, (partnerId, naziv, naslov, davcna, stNarocilnice, rabat) -> {
                zakljuciInNatisniPartner(placiloId, nacinNaziv, zaplacilo, partnerId, naziv, naslov, davcna, stNarocilnice, rabat);
            });
        } else {
            // Metoda 1: gotovina (ali kartica če intent ni vklopljen ali ostale metode)
            zakljuciInNatisniHitroPlacilo(placiloId, nacinNaziv, zaplacilo);
        }
    }

    private void zakljuciInNatisniHitroPlacilo(final int placiloId, final String nacinNaziv, final BigDecimal zaplacilo) {
        zakljuciInNatisniHitroPlacilo(placiloId, nacinNaziv, zaplacilo, null);
    }

    private void zakljuciInNatisniHitroPlacilo(final int placiloId, final String nacinNaziv, final BigDecimal zaplacilo, final Integer kupecId) {
        if (currentRacun == null) return;
        disableEkran("Knjiženje in zaključek (" + nacinNaziv + ")...");

        int nextPozId = -1;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp p : currentRacun.getRacPlaci()) {
                if (p != null && p.getPozicijaId() <= nextPozId) {
                    nextPozId = p.getPozicijaId() - 1;
                }
            }
        }

        final PlaciloTp pl = new PlaciloTp(currentRacun.getRacunId(), placiloId, zaplacilo);
        pl.setPozicijaId(nextPozId);
        pl.setVerzijaZapisa(0);
        pl.setRowDeleted(false);
        pl.setDelniZnesek(zaplacilo);
        pl.setZnesek(BigDecimal.ZERO);
        if (kupecId != null && kupecId > 0) {
            pl.setKupecId(kupecId);
            pl.setPartnerId(kupecId);
            currentRacun.setPartnerId(kupecId);
        }

        int tocId = (currentRacun.getTocilnicaId() != null && currentRacun.getTocilnicaId() > 0)
                ? currentRacun.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        pl.setTocilnicaId(tocId);

        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setNeNarocaj(true);
            }
        }
        if (currentRacun.getRacPlaci() == null) {
            currentRacun.setRacPlaci(new ArrayList<>());
        }
        currentRacun.getRacPlaci().add(pl);

        // Fiskalizacija:
        boolean imaFiskalnoPlacilo = false;
        for (PlaciloTp p : currentRacun.getRacPlaci()) {
            if (p != null && !p.isRowDeleted() && p.getPlaciloId() != 99) {
                if (isPlaciloFiskalno(p.getPlaciloId())) {
                    imaFiskalnoPlacilo = true;
                    break;
                }
            }
        }
        currentRacun.setFiskalizacija(imaFiskalnoPlacilo ? 1 : null);
        currentRacun.setStatus(2); // ZAKLJUČEN račun
        currentRacun.setMarker(activeMarker);
        int fPosId = prefs.getfPosId() > 0 ? prefs.getfPosId() : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500);
        currentRacun.setfPosId(fPosId);
        int fPoslovniProstorId = Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000;
        currentRacun.setfPoslovniProstorId(fPoslovniProstorId);
        currentRacun.setTocilnicaId(tocId);
        if (Globals.getInstance().getTekocaOsebaId() > 0) {
            currentRacun.setKasiral(Globals.getInstance().getTekocaOsebaId());
        } else if (currentRacun.getKasiral() == null || currentRacun.getKasiral() <= 0) {
            currentRacun.setKasiral(9999);
        }
        if (currentRacun.getTipRacuna() == null || currentRacun.getTipRacuna() <= 0) {
            currentRacun.setTipRacuna(1);
        }
        if (currentRacun.getStPogrinjkov() == null || currentRacun.getStPogrinjkov() <= 0) {
            currentRacun.setStPogrinjkov(1);
        }
        if (currentRacun.getStKopij() == null) {
            currentRacun.setStKopij(0);
        }

        BigDecimal trenPlacano = currentRacun.getPlacano() != null ? currentRacun.getPlacano() : BigDecimal.ZERO;
        currentRacun.setPlacano(trenPlacano.add(zaplacilo));
        currentRacun.preracunajVsote();

        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                RacunTp racunZaTisk = currentRacun;
                if (response != null && response.getRacGlava() != null) {
                    RacunTp returned = response.getRacGlava();
                    if ((returned.getRacPozic() == null || returned.getRacPozic().isEmpty()) && currentRacun.getRacPozic() != null) {
                        returned.setRacPozic(currentRacun.getRacPozic());
                    }
                    if (returned.getRacPlaci() == null && currentRacun.getRacPlaci() != null) {
                        returned.setRacPlaci(currentRacun.getRacPlaci());
                    }
                    if (returned.getFiskalizacija() == null && currentRacun.getFiskalizacija() != null) {
                        returned.setFiskalizacija(currentRacun.getFiskalizacija());
                    }
                    if (currentRacun.getZnesek() != null && currentRacun.getZnesek().compareTo(BigDecimal.ZERO) > 0) {
                        returned.setZnesek(currentRacun.getZnesek());
                    }
                    racunZaTisk = returned;
                }

                if (racunZaTisk != null && racunZaTisk.getRacPozic() != null) {
                    for (PozicijaTp p : racunZaTisk.getRacPozic()) {
                        if (p != null && p.getNivo4Id() != null && p.getNivo4Id() > 0) {
                            String lookup = Globals.getInstance().findNazivByNivo4Id(p.getNivo4Id());
                            if (lookup != null && !lookup.trim().isEmpty()) {
                                p.setNaziv(lookup.trim());
                            }
                        }
                    }
                }

                final RacunTp finalRacunToPrint = racunZaTisk;
                mainHandler.post(() -> {
                    enableEkran();
                    if (getContext() != null) {
                        Toast.makeText(requireContext(), "Račun #" + finalRacunToPrint.getRacunId() + " (" + nacinNaziv + ") zaključen!", Toast.LENGTH_SHORT).show();
                    }

                    if (getContext() != null) {
                        BluetoothPrintHelper.printReceiptComplete(requireContext(), finalRacunToPrint, null);
                    }

                    prefs.setActiveRacunId(0);
                    Globals.getInstance().setCurrentRacun(null);

                    if (Globals.getInstance().ispLogoutPoIzpisu()) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
                        }
                    } else if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
                    }
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    enableEkran();
                    if (currentRacun != null && currentRacun.getRacPlaci() != null) {
                        currentRacun.getRacPlaci().remove(pl);
                        currentRacun.setStatus(1);
                        currentRacun.preracunajVsote();
                    }
                    Toast.makeText(requireContext(), "Napaka pri zaključku računa: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void zakljuciInNatisniHotelKredit(final int placiloId, final String nacinNaziv, final BigDecimal zaplacilo, final KartprijTp selectedRoom) {
        if (currentRacun == null || selectedRoom == null) return;
        disableEkran("Knjiženje hotel kredita...");

        int nextPozId = -1;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp p : currentRacun.getRacPlaci()) {
                if (p != null && p.getPozicijaId() <= nextPozId) {
                    nextPozId = p.getPozicijaId() - 1;
                }
            }
        }

        final PlaciloTp pl = new PlaciloTp(currentRacun.getRacunId(), placiloId, zaplacilo);
        pl.setPozicijaId(nextPozId);
        pl.setVerzijaZapisa(0);
        pl.setRowDeleted(false);
        pl.setDelniZnesek(zaplacilo);
        pl.setZnesek(BigDecimal.ZERO);
        pl.setStKartice(String.valueOf(selectedRoom.getProstorId()));
        pl.setGostPrijavaId(selectedRoom.getPrijavaId());
        pl.setNazivPartner(selectedRoom.getImeGosta());

        int tocId = (currentRacun.getTocilnicaId() != null && currentRacun.getTocilnicaId() > 0)
                ? currentRacun.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        pl.setTocilnicaId(tocId);

        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setNeNarocaj(true);
            }
        }
        if (currentRacun.getRacPlaci() == null) {
            currentRacun.setRacPlaci(new ArrayList<>());
        }
        currentRacun.getRacPlaci().add(pl);

        currentRacun.setStatus(2); // ZAKLJUČEN račun
        currentRacun.setFiskalizacija(null); // Hotel kredit je nefiskalen
        currentRacun.setMarker(activeMarker);
        int fPosId = prefs.getfPosId() > 0 ? prefs.getfPosId() : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500);
        currentRacun.setfPosId(fPosId);
        int fPoslovniProstorId = Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000;
        currentRacun.setfPoslovniProstorId(fPoslovniProstorId);
        currentRacun.setTocilnicaId(tocId);
        if (Globals.getInstance().getTekocaOsebaId() > 0) {
            currentRacun.setKasiral(Globals.getInstance().getTekocaOsebaId());
        } else if (currentRacun.getKasiral() == null || currentRacun.getKasiral() <= 0) {
            currentRacun.setKasiral(9999);
        }

        BigDecimal trenPlacano = currentRacun.getPlacano() != null ? currentRacun.getPlacano() : BigDecimal.ZERO;
        currentRacun.setPlacano(trenPlacano.add(zaplacilo));
        currentRacun.preracunajVsote();

        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                RacunTp racunZaTisk = (response != null && response.getRacGlava() != null) ? response.getRacGlava() : currentRacun;

                final RacunTp finalRacunToPrint = racunZaTisk;
                mainHandler.post(() -> {
                    enableEkran();
                    Toast.makeText(requireContext(), "Račun #" + finalRacunToPrint.getRacunId() + " (Soba " + selectedRoom.getProstorId() + ") zaključen!", Toast.LENGTH_SHORT).show();
                    BluetoothPrintHelper.printReceiptComplete(requireContext(), finalRacunToPrint, null);

                    prefs.setActiveRacunId(0);
                    Globals.getInstance().setCurrentRacun(null);

                    if (Globals.getInstance().ispLogoutPoIzpisu()) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
                        }
                    } else if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
                    }
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    enableEkran();
                    if (currentRacun != null && currentRacun.getRacPlaci() != null) {
                        currentRacun.getRacPlaci().remove(pl);
                        currentRacun.setStatus(1);
                        currentRacun.preracunajVsote();
                    }
                    Toast.makeText(requireContext(), "Napaka pri knjiženju hotel kredita: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void zakljuciInNatisniPartner(final int placiloId, final String nacinNaziv, final BigDecimal zaplacilo,
                                         final int partnerId, final String naziv, final String naslov, final String davcna, final String stNarocilnice, final BigDecimal rabat) {
        if (currentRacun == null) return;
        disableEkran("Knjiženje partnerja (" + (naziv != null ? naziv : nacinNaziv) + ")...");

        BigDecimal dejanskiZnesek = zaplacilo;
        if (rabat != null && rabat.compareTo(BigDecimal.ZERO) > 0) {
            boolean zeImaPopust = false;
            if (currentRacun.getRacPlaci() != null) {
                for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                    if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() == 99) {
                        zeImaPopust = true;
                        break;
                    }
                }
            }
            if (!zeImaPopust) {
                Globals.getInstance().popustNaRacun(currentRacun, rabat, BigDecimal.ZERO);
                currentRacun.preracunajVsote();
                dejanskiZnesek = getPreostanekZaPlacilo();
            }
        }

        int nextPozId = -1;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp p : currentRacun.getRacPlaci()) {
                if (p != null && p.getPozicijaId() <= nextPozId) {
                    nextPozId = p.getPozicijaId() - 1;
                }
            }
        }

        final PlaciloTp pl = new PlaciloTp(currentRacun.getRacunId(), placiloId, dejanskiZnesek);
        pl.setPozicijaId(nextPozId);
        pl.setVerzijaZapisa(0);
        pl.setRowDeleted(false);
        pl.setDelniZnesek(dejanskiZnesek);
        pl.setZnesek(BigDecimal.ZERO);
        if (partnerId > 0) {
            pl.setPartnerId(partnerId);
            pl.setKupecId(partnerId);
        }
        if (naziv != null && !naziv.isEmpty()) pl.setNazivPartner(naziv);
        if (naslov != null && !naslov.isEmpty()) pl.setNaslovPartner(naslov);
        if (davcna != null && !davcna.isEmpty()) pl.setDavcnaSt(davcna);
        if (stNarocilnice != null && !stNarocilnice.isEmpty()) pl.setStNarocilnice(stNarocilnice);

        int tocId = (currentRacun.getTocilnicaId() != null && currentRacun.getTocilnicaId() > 0)
                ? currentRacun.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        pl.setTocilnicaId(tocId);

        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setNeNarocaj(true);
            }
        }
        if (currentRacun.getRacPlaci() == null) {
            currentRacun.setRacPlaci(new ArrayList<>());
        }
        currentRacun.getRacPlaci().add(pl);

        boolean imaFisk = isPlaciloFiskalno(placiloId);
        currentRacun.setFiskalizacija(imaFisk ? 1 : null);
        currentRacun.setStatus(2); // ZAKLJUČEN račun
        currentRacun.setMarker(activeMarker);
        int fPosId = prefs.getfPosId() > 0 ? prefs.getfPosId() : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500);
        currentRacun.setfPosId(fPosId);
        int fPoslovniProstorId = Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000;
        currentRacun.setfPoslovniProstorId(fPoslovniProstorId);
        currentRacun.setTocilnicaId(tocId);
        if (partnerId > 0) currentRacun.setPartnerId(partnerId);
        if (Globals.getInstance().getTekocaOsebaId() > 0) {
            currentRacun.setKasiral(Globals.getInstance().getTekocaOsebaId());
        } else if (currentRacun.getKasiral() == null || currentRacun.getKasiral() <= 0) {
            currentRacun.setKasiral(9999);
        }

        BigDecimal trenPlacano = currentRacun.getPlacano() != null ? currentRacun.getPlacano() : BigDecimal.ZERO;
        currentRacun.setPlacano(trenPlacano.add(dejanskiZnesek));
        currentRacun.preracunajVsote();

        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                RacunTp racunZaTisk = (response != null && response.getRacGlava() != null) ? response.getRacGlava() : currentRacun;

                final RacunTp finalRacunToPrint = racunZaTisk;
                mainHandler.post(() -> {
                    enableEkran();
                    Toast.makeText(requireContext(), "Račun #" + finalRacunToPrint.getRacunId() + " (" + nacinNaziv + ") zaključen!", Toast.LENGTH_SHORT).show();
                    BluetoothPrintHelper.printReceiptComplete(requireContext(), finalRacunToPrint, null);

                    prefs.setActiveRacunId(0);
                    Globals.getInstance().setCurrentRacun(null);

                    if (Globals.getInstance().ispLogoutPoIzpisu()) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
                        }
                    } else if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
                    }
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    enableEkran();
                    if (currentRacun != null && currentRacun.getRacPlaci() != null) {
                        currentRacun.getRacPlaci().remove(pl);
                        currentRacun.setStatus(1);
                        currentRacun.preracunajVsote();
                    }
                    Toast.makeText(requireContext(), "Napaka pri zaključku računa za partnerja: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void nadaljujSPlacilom(final int placiloId, final int tempmetoda, final String nacinNaziv, final BigDecimal znesekPlacila) {
        // Preveri račun na strežniku pred plačilom
        zagotoviRacunNaStrezniku(() -> {
            izvediPlaciloPoMetodi(placiloId, tempmetoda, nacinNaziv, znesekPlacila);
        });
    }

    private void izvediPlaciloPoMetodi(int placiloId, int tempmetoda, String nacinNaziv, BigDecimal zaplacilo) {
        NacPlacTp np = Globals.getInstance().getPlaciloById(placiloId);
        int PlaciloPartnerStoritevId = 0;
        if (np != null && np.getStoritevId() != null) {
            PlaciloPartnerStoritevId = np.getStoritevId();
        }
        int nacplacstoritevid = PlaciloPartnerStoritevId;
        String uNaziv = nacinNaziv != null ? nacinNaziv.toUpperCase() : "";

        boolean isPosPayment = (tempmetoda == 14
                || (tempmetoda != 3 && (uNaziv.contains("POS") || placiloId == 2)));

        if (tempmetoda == 14 || isPosPayment) {
            // POS Plačilni Intent (PayTen ali Worldline SoftPOS / SixTap) ima absolutno prednost
            izvediPosPlacilo(placiloId, nacinNaziv, zaplacilo);
        } else if (tempmetoda == 6 || placiloId == 8) {
            // Hotel kredit (sobe)
            showHotelKreditDialog(placiloId, nacinNaziv, zaplacilo);
        } else if (tempmetoda == 3) {
            // Kreditna kartica ročno:
            Globals g = Globals.getInstance();
            int kKarticaTippartnerRocno = g.getkKarticaTippartnerRocno();
            int KKARTICA_KUPECID_ROCNO = g.getkKarticaKupecIdRocno();

            if ((nacplacstoritevid == kKarticaTippartnerRocno) && ((KKARTICA_KUPECID_ROCNO > 0) && (PlaciloPartnerStoritevId == 0))) {
                // Knjižimo partner in kupec preko parametra:
                g.vpisiKronologijo("Placilo KK ročno avtomatski KUPEC_ID: " + KKARTICA_KUPECID_ROCNO + " za R:" + (currentRacun != null ? currentRacun.getRacunId() : 0));
                addPlaciloFull(nacinNaziv, placiloId, zaplacilo, null, null, (KKARTICA_KUPECID_ROCNO > 0 ? KKARTICA_KUPECID_ROCNO : null), null, null, null, null, null);
            } else {
                // Izbor partnerja iz liste za tip partner = STORITEV_ID
                showPartnerVnosDialog(placiloId, nacinNaziv, zaplacilo, PlaciloPartnerStoritevId);
            }
        } else if (tempmetoda == 4 || placiloId == 4) {
            // Dobavnica / Naročilnica / Partner
            showPartnerVnosDialog(placiloId, nacinNaziv, zaplacilo, PlaciloPartnerStoritevId);
        } else {
            addPlacilo(nacinNaziv, placiloId, zaplacilo, null);
        }
    }

    private void showHotelKreditDialog(int placiloId, String nacinNaziv, BigDecimal zaplacilo) {
        HotelSobeDialog.show(requireContext(), zaplacilo, selectedRoom -> {
            addPlaciloHotelKredit(nacinNaziv, placiloId, zaplacilo, selectedRoom);
        });
    }

    private void showPartnerVnosDialog(int placiloId, String nacinNaziv, BigDecimal zaplacilo, int storitevId) {
        PartnerVnosDialog.show(requireContext(), zaplacilo, storitevId, (partnerId, naziv, naslov, davcna, stNarocilnice, rabat) -> {
            addPlaciloPartner(nacinNaziv, placiloId, zaplacilo, partnerId, naziv, naslov, davcna, stNarocilnice, rabat);
        });
    }

    private void setupPaymentMethodButtons() {
        if (!Globals.getInstance().hasCachedPlacila()) {
            int mId = 1;
            try { mId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}
            final int finalMId = mId;
            executor.execute(() -> {
                try {
                    List<NacPlacTp> methods = RosKasaSoapClient.getNacPlac2(prefs.getServerUrl(), prefs.getToken(), finalMId);
                    Globals.getInstance().filterAndSetCachedPlacila(methods);
                    mainHandler.post(this::renderPaymentButtons);
                } catch (Exception e) {
                    Log.w(TAG, "Napaka pri nalaganju plačil: " + e.getMessage());
                    mainHandler.post(this::renderDefaultPaymentButtons);
                }
            });
        } else {
            renderPaymentButtons();
        }
    }

    private void renderPaymentButtons() {
        if (binding == null) return;
        List<NacPlacTp> methods = Globals.getInstance().getCachedPlacila();
        if (methods == null || methods.isEmpty()) {
            renderDefaultPaymentButtons();
            return;
        }

        List<android.widget.Button> buttons = new ArrayList<>();
        for (int i = 0; i < binding.gridPlacilaMethods.getChildCount(); i++) {
            View child = binding.gridPlacilaMethods.getChildAt(i);
            if (child instanceof android.widget.Button && child.getId() != R.id.btnPayLoyPopust) {
                buttons.add((android.widget.Button) child);
            }
        }

        for (int i = 0; i < buttons.size(); i++) {
            android.widget.Button btn = buttons.get(i);
            if (i < methods.size()) {
                NacPlacTp np = methods.get(i);
                final int pid = np.getPlaciloId();
                final int met = np.getMetoda();
                final String naz = np.getNaziv() != null ? np.getNaziv().toUpperCase() : "";

                if (met == 1 && !Globals.getInstance().isTipkaGotovina()) {
                    btn.setVisibility(View.INVISIBLE);
                    btn.setEnabled(false);
                    btn.setOnClickListener(null);
                    continue;
                }

                btn.setVisibility(View.VISIBLE);
                btn.setEnabled(true);
                btn.setText(np.getNaziv());
                btn.setOnClickListener(v -> {
                    if (met == 14 || (met != 3 && (naz.contains("POS") || naz.contains("KARTIC") || naz.contains("KREDITN")))) {
                        klikniHitroPlacilo(pid);
                    } else {
                        klikniPlacilo(pid);
                    }
                });
            } else {
                btn.setVisibility(View.INVISIBLE);
                btn.setEnabled(false);
                btn.setOnClickListener(null);
            }
        }

        if (Globals.getInstance().ispLojalnostPopust()) {
            binding.btnPayLoyPopust.setVisibility(View.VISIBLE);
            if (currentRacun != null && currentRacun.getLojalnostId() != null && currentRacun.getLojalnostId() > 0) {
                binding.btnPayLoyPopust.setText("Lojalnost\n[R" + currentRacun.getLojalnostId() + "]");
                binding.btnPayLoyPopust.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#EF6C00")));
            } else {
                binding.btnPayLoyPopust.setText("Lojalnostni\npopust");
                binding.btnPayLoyPopust.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#E65100")));
            }
            binding.btnPayLoyPopust.setOnClickListener(v -> handleLojalnostPopustClick());
        } else {
            binding.btnPayLoyPopust.setVisibility(View.GONE);
        }
    }

    private void renderDefaultPaymentButtons() {
        if (binding == null) return;
        boolean gotovinaAktivna = Globals.getInstance().isTipkaGotovina();
        binding.btnPayGotovina.setVisibility(gotovinaAktivna ? View.VISIBLE : View.INVISIBLE);
        binding.btnPayGotovina.setEnabled(gotovinaAktivna);
        if (gotovinaAktivna) {
            binding.btnPayGotovina.setOnClickListener(v -> klikniHitroPlacilo(1));
        } else {
            binding.btnPayGotovina.setOnClickListener(null);
        }

        binding.btnPayKredRocno.setOnClickListener(v -> klikniHitroPlaciloPoMetodi(3));
        binding.btnPayKreditnaPos.setOnClickListener(v -> {
            boolean isIntentOn = Globals.getInstance().isPosIntentActive();
            klikniHitroPlaciloPoMetodi(isIntentOn ? 14 : 2);
        });
        binding.btnPayReprezentanca.setOnClickListener(v -> klikniHitroPlaciloPoMetodi(5));
        binding.btnPayDobavnica.setOnClickListener(v -> klikniHitroPlaciloPoMetodi(4));
        binding.btnPayValu.setOnClickListener(v -> {
            NacPlacTp np = Globals.getInstance().findPlaciloByNameContains("VALU");
            int m = (np != null) ? np.getMetoda() : 6;
            klikniHitroPlaciloPoMetodi(m);
        });
        binding.btnPayMBills.setOnClickListener(v -> {
            NacPlacTp np = Globals.getInstance().findPlaciloByNameContains("MBILLS");
            int m = (np != null) ? np.getMetoda() : 7;
            klikniHitroPlaciloPoMetodi(m);
        });
        binding.btnPayGostHotela.setOnClickListener(v -> klikniHitroPlaciloPoMetodi(6));
        if (Globals.getInstance().ispLojalnostPopust()) {
            binding.btnPayLoyPopust.setVisibility(View.VISIBLE);
            binding.btnPayLoyPopust.setOnClickListener(v -> handleLojalnostPopustClick());
        } else {
            binding.btnPayLoyPopust.setVisibility(View.GONE);
        }
    }

    private void setupNavigationButtons() {
        binding.btnNavMize.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
            }
        });

        binding.btnNavRacuni.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new RacuniFragment());
            }
        });

        binding.btnNavNaroci.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new NarocilaFragment());
            }
        });

        binding.btnOdjava.setOnClickListener(v -> {
            Globals.getInstance().setTekocaOseba(null);
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
            }
        });

        binding.btnBrisanje.setOnClickListener(v -> {
            if (currentRacun == null || currentRacun.getRacPlaci() == null || currentRacun.getRacPlaci().isEmpty()) {
                Toast.makeText(requireContext(), "Ni knjiženih plačil za brisanje", Toast.LENGTH_SHORT).show();
                return;
            }

            PlaciloTp zadnje = null;
            for (int i = currentRacun.getRacPlaci().size() - 1; i >= 0; i--) {
                PlaciloTp p = currentRacun.getRacPlaci().get(i);
                if (p != null && !p.isRowDeleted()) {
                    zadnje = p;
                    break;
                }
            }

            if (zadnje == null) {
                Toast.makeText(requireContext(), "Ni aktivnih plačil za brisanje", Toast.LENGTH_SHORT).show();
                return;
            }

            final PlaciloTp placiloZaBrisanje = zadnje;
            String nacin = getPaymentName(placiloZaBrisanje.getPlaciloId());
            BigDecimal zn = (placiloZaBrisanje.getDelniZnesek() != null && placiloZaBrisanje.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0)
                    ? placiloZaBrisanje.getDelniZnesek()
                    : (placiloZaBrisanje.getZnesek() != null ? placiloZaBrisanje.getZnesek() : BigDecimal.ZERO);

            String prompt = (placiloZaBrisanje.getPlaciloId() == 99)
                    ? "Ali res želite stornirati popust na račun (" + String.format(Locale.getDefault(), "%.2f €", zn) + ")?"
                    : "Ali res želite stornirati plačilo " + nacin + " (" + String.format(Locale.getDefault(), "%.2f €", zn) + ")?";

            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle(placiloZaBrisanje.getPlaciloId() == 99 ? "Brisanje popusta" : "Brisanje plačila")
                    .setMessage(prompt)
                    .setPositiveButton("Izbriši", (dialog, which) -> brisiPlaciloNaStrezniku(placiloZaBrisanje))
                    .setNegativeButton("Prekliči", null)
                    .show();
        });

        binding.btnPopust.setOnClickListener(v -> handlePopust99Click());
        binding.btnOpis.setOnClickListener(v -> handleOpisPartnerja());
        binding.btnDelovniNalog.setOnClickListener(v -> handleDelovniNalog());
        binding.btnOpombaRacuna.setOnClickListener(v -> handleOpombaRacuna());
        binding.btnIzpisRacuna.setOnClickListener(v -> handleIzpisRacuna());
        binding.btnPayLoyPopust.setOnClickListener(v -> handleLojalnostPopustClick());
        binding.btnTapOn.setOnClickListener(v -> handleTapOnClick());
    }

    private void brisiPlaciloNaStrezniku(PlaciloTp pl) {
        if (currentRacun == null || pl == null) return;

        final int metoda = Globals.getInstance().placilometoda(pl.getPlaciloId());
        final boolean isCardOrPos = (metoda == 14 || pl.getPlaciloId() == 2 || (pl.getMRef() != null && !pl.getMRef().trim().isEmpty()));
        final boolean isSixTapActive = Globals.getInstance().isSixTap();
        final boolean isPaytenActive = Globals.getInstance().isPayTenA();

        if (isCardOrPos && (pl.getMRef() == null || pl.getMRef().trim().isEmpty())) {
            // Poskusi pridobiti M_REF iz SLIP_EMA pred stornom
            disableEkran("Preverjanje POS transakcije za storno...");
            executor.execute(() -> {
                try {
                    List<SlipEmaTp> slips = RosKasaSoapClient.getSlipEma2(prefs.getServerUrl(), prefs.getToken(), currentRacun.getRacunId());
                    if (slips != null && !slips.isEmpty()) {
                        for (SlipEmaTp s : slips) {
                            if (s != null && s.getAcqTransRef() != null && !s.getAcqTransRef().trim().isEmpty()) {
                                pl.setMRef(s.getAcqTransRef().trim());
                                break;
                            }
                        }
                    }
                } catch (Exception ignored) {}
                mainHandler.post(() -> {
                    enableEkran();
                    final String fetchedRef = pl.getMRef() != null ? pl.getMRef().trim() : "";
                    if (isSixTapActive && !fetchedRef.isEmpty()) {
                        izvediSixTapStornoPlacila(pl);
                    } else if (isPaytenActive && !fetchedRef.isEmpty()) {
                        izvediPaytenStornoPlacila(pl);
                    } else {
                        izvediBrisanjePlacilaNaStreznikuDirect(pl);
                    }
                });
            });
            return;
        }

        final String mRef = pl.getMRef() != null ? pl.getMRef().trim() : "";

        if (isSixTapActive && !mRef.isEmpty()) {
            izvediSixTapStornoPlacila(pl);
            return;
        } else if (isPaytenActive && !mRef.isEmpty()) {
            izvediPaytenStornoPlacila(pl);
            return;
        }

        izvediBrisanjePlacilaNaStreznikuDirect(pl);
    }


    private void izvediSixTapStornoPlacila(PlaciloTp pl) {
        if (!(getActivity() instanceof MainActivity)) {
            Toast.makeText(requireContext(), "MainActivity ni na voljo!", Toast.LENGTH_SHORT).show();
            return;
        }
        MainActivity activity = (MainActivity) getActivity();

        final int racunId = currentRacun.getRacunId();
        final int pozicijaId = pl.getPozicijaId();
        final BigDecimal znesek = (pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0)
                ? pl.getDelniZnesek()
                : (pl.getZnesek() != null ? pl.getZnesek() : BigDecimal.ZERO);
        final BigDecimal napitnina = pl.getNapitnina() != null ? pl.getNapitnina() : BigDecimal.ZERO;
        final BigDecimal skupajZnesek = znesek.add(napitnina);
        final String mRef = pl.getMRef();
        final String wpiSessionId = pl.getWpiSessionId();

        // Delphi: if ZadnjiSixRacunId = RACUN_ID then WPI_SVC_CANCEL_PAYMENT else WPI_SVC_REFUND
        final String op = (Globals.getInstance().getZadnjiSixRacunId() == racunId)
                ? SixTapPaymentService.OP_CANCEL_PAYMENT
                : SixTapPaymentService.OP_REFUND;

        Intent sixTapIntent = SixTapPaymentService.buildPaymentIntent(requireContext(), racunId, pozicijaId, skupajZnesek, napitnina, op, mRef, wpiSessionId);
        disableEkran("Storniranje na POS terminalu (Worldline Tap On)...");

        activity.launchSixTap(sixTapIntent, new MainActivity.PaymentResultListener() {
            @Override
            public void onPaytenResult(int resultCode, Intent data) {}

            @Override
            public void onSixTapResult(int resultCode, Intent data) {
                enableEkran();
                if (resultCode == android.app.Activity.RESULT_OK && data != null) {
                    SixTapResponse resp = SixTapPaymentService.parseResponseIntent(data);
                    if (resp.isSuccess()) {
                        String sessionId = resp.getpWpiSessionId() != null ? resp.getpWpiSessionId() : (Globals.getInstance().getZadnjiWpiSessionId() != null ? Globals.getInstance().getZadnjiWpiSessionId() : "");
                        String errCond = resp.getErrorCondition() != null && !resp.getErrorCondition().isEmpty() ? resp.getErrorCondition() : "WPI_ERR_COND_NONE";
                        String remark = resp.getRemark() != null ? resp.getRemark() : "";
                        String znesekStr = String.format(Locale.GERMANY, "%.2f", skupajZnesek).replace(",00", ",0");
                        String authAmt = resp.getAuthorizedAmount() != null ? resp.getAuthorizedAmount() : "";
                        String tipAmt = resp.getTipAmount() != null ? resp.getTipAmount() : "";
                        String pSolRef = resp.getPaymentSolutionReference() != null ? resp.getPaymentSolutionReference() : "";

                        // SIXTAP RESULT OK R:61556 LokalReference R:8e4ee40a-43d1-4c34-a0c0-0167f4c4a554 SIXTAP WPI_SVC_CANCEL_PAYMENT result: WPI_RESULT_SUCCESS Error: WPI_ERR_COND_NONE za M_REF:  remark:  SESSION_ID: 6B7A3073747C4D59AC55
                        Globals.getInstance().vpisiKronologijo("SIXTAP RESULT OK R:" + racunId + " LokalReference R:" + mRef + " SIXTAP " + op + " result: WPI_RESULT_SUCCESS Error: " + errCond + " za M_REF: " + pSolRef + " remark: " + remark + " SESSION_ID: " + sessionId);

                        // SIXTAP WPI_RESULT_SUCCESS za  R:61556 reference: 8e4ee40a-43d1-4c34-a0c0-0167f4c4a554 paymentSolutionReference:  authorizedAmount:  tipAmount:  Z:3,52
                        Globals.getInstance().vpisiKronologijo("SIXTAP WPI_RESULT_SUCCESS za  R:" + racunId + " reference: " + mRef + " paymentSolutionReference: " + pSolRef + " authorizedAmount: " + authAmt + " tipAmount: " + tipAmt + " Z:" + znesekStr);

                        // SIXTAP setSlipEma R:61556 M_REF:  Op: WPI_SVC_CANCEL_PAYMENT
                        Globals.getInstance().vpisiKronologijo("SIXTAP setSlipEma R:" + racunId + " M_REF: " + pSolRef + " Op: " + op);

                        // SIXTAP ClearPendingPayment ReqEmaAfterRefund=6  R:61556
                        Globals.getInstance().vpisiKronologijo("SIXTAP ClearPendingPayment ReqEmaAfterRefund=" + pozicijaId + "  R:" + racunId);

                        disableEkran("Knjiženje storno slipa na strežnik...");
                        executor.execute(() -> {
                            try {
                                String serverUrl = prefs.getServerUrl();
                                String token = prefs.getToken();

                                SlipEmaTp slip = new SlipEmaTp();
                                slip.setStevilkaRacuna(racunId);
                                slip.setPozicijaId(pozicijaId);
                                slip.setSlipPrint(resp.getClient() != null ? resp.getClient() : "");
                                slip.setSlipPrints(resp.getMerchant() != null ? resp.getMerchant() : "");
                                slip.setStevilkaKartice(resp.getBrandName() != null ? resp.getBrandName() : (resp.getCardnumber() != null ? resp.getCardnumber() : ""));
                                slip.setUspelo("DA");
                                slip.setProjektId(3);
                                slip.setStType(op);
                                slip.setZnesek(znesek.negate());
                                slip.setZnesekSlip(resp.getZnesekPOS().compareTo(BigDecimal.ZERO) > 0 ? resp.getZnesekPOS().negate() : skupajZnesek.negate());
                                slip.setAcqTransRef(resp.getPaymentSolutionReference() != null ? resp.getPaymentSolutionReference() : mRef);
                                slip.setAppIdentifier(resp.getApplicationIdentifier() != null ? resp.getApplicationIdentifier() : "");
                                slip.setAcqReference(resp.getAcqreference() != null ? resp.getAcqreference() : mRef);
                                slip.setAuthReference(resp.getAcqreference() != null ? resp.getAcqreference() : mRef);
                                slip.setAvtorizacija("REF");

                                RosKasaSoapClient.setSlipEma(serverUrl, token, slip);
                                PaymentRecoveryManager.clearAllRecoveryData(requireContext());

                                mainHandler.post(() -> {
                                    enableEkran();
                                    izvediBrisanjePlacilaNaStreznikuDirect(pl);
                                });
                            } catch (Exception e) {
                                mainHandler.post(() -> {
                                    enableEkran();
                                    Log.w(TAG, "setSlipEma storno opozorilo: " + e.getMessage());
                                    izvediBrisanjePlacilaNaStreznikuDirect(pl);
                                });
                            }
                        });
                    } else {
                        PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                        String sessionId = resp.getpWpiSessionId() != null ? resp.getpWpiSessionId() : "";
                        Globals.getInstance().vpisiKronologijo("SIXTAP RESULT OK R:" + racunId + " LokalReference R:" + mRef + " SIXTAP " + op + " result: WPI_RESULT_FAILURE Error: " + resp.getErrorCondition() + " za M_REF:  remark: " + resp.getRemark() + " SESSION_ID: " + sessionId);
                        Globals.getInstance().vpisiKronologijo("SIXTAP ClearPendingPayment ReqEmaAfterRefund=" + pozicijaId + " Error:" + resp.getErrorCondition() + "  R:" + racunId);

                        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                                .setTitle("Napaka storno SixTap")
                                .setMessage("Storno na POS terminalu ni uspel: " + resp.getErrorCondition() + " " + resp.getRemark() + "\n\nPlačilo NI bilo izbrisano!")
                                .setPositiveButton("V redu", null)
                                .show();
                    }
                } else {
                    PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                    Globals.getInstance().vpisiKronologijo("SIXTAP ClearPendingPayment ReqEmaAfterRefund=" + pozicijaId + " Preklic_uporabnika  R:" + racunId);
                    Toast.makeText(requireContext(), "Storno na POS terminalu preklican. Plačilo NI bilo izbrisano.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void izvediPaytenStornoPlacila(PlaciloTp pl) {
        if (!(getActivity() instanceof MainActivity)) {
            Toast.makeText(requireContext(), "MainActivity ni na voljo!", Toast.LENGTH_SHORT).show();
            return;
        }
        MainActivity activity = (MainActivity) getActivity();

        final int racunId = currentRacun.getRacunId();
        final int pozicijaId = pl.getPozicijaId();
        final BigDecimal znesek = (pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0)
                ? pl.getDelniZnesek()
                : (pl.getZnesek() != null ? pl.getZnesek() : BigDecimal.ZERO);
        final BigDecimal napitnina = pl.getNapitnina() != null ? pl.getNapitnina() : BigDecimal.ZERO;
        final BigDecimal skupajZnesek = znesek.add(napitnina);
        final String mRef = pl.getMRef();

        Intent paytenIntent = PaytenPaymentService.buildPaymentIntent(requireContext(), racunId, pozicijaId, skupajZnesek, "void", mRef);
        disableEkran("Storniranje na POS terminalu (PayTen void)...");

        activity.launchPayten(paytenIntent, new MainActivity.PaymentResultListener() {
            @Override
            public void onPaytenResult(int resultCode, Intent data) {
                enableEkran();
                if (resultCode == android.app.Activity.RESULT_OK && data != null) {
                    PaytenResponse resp = PaytenPaymentService.parseResponseIntent(data);
                    if (resp.isSuccess()) {
                        Globals.getInstance().vpisiKronologijo("PAYTEN RESULT OK R:" + racunId + " void M_REF:" + resp.getPaymentSolutionReference() + " Auth:" + resp.getpAuthorizationCode());
                        disableEkran("Knjiženje storno slipa na strežnik...");
                        executor.execute(() -> {
                            try {
                                String serverUrl = prefs.getServerUrl();
                                String token = prefs.getToken();

                                SlipEmaTp slip = new SlipEmaTp();
                                slip.setStevilkaRacuna(racunId);
                                slip.setPozicijaId(pozicijaId);
                                slip.setSlipPrint(resp.getClient() != null ? resp.getClient() : "");
                                slip.setSlipPrints(resp.getReceipt() != null ? resp.getReceipt() : "");
                                slip.setStevilkaKartice(resp.getCardNumber() != null ? resp.getCardNumber() : "");
                                slip.setUspelo("DA");
                                slip.setProjektId(3);
                                slip.setStType("void");
                                slip.setZnesek(znesek.negate());
                                slip.setZnesekSlip(resp.getZnesekPOS().compareTo(BigDecimal.ZERO) > 0 ? resp.getZnesekPOS().negate() : skupajZnesek.negate());
                                slip.setAcqTransRef(resp.getPaymentSolutionReference() != null ? resp.getPaymentSolutionReference() : mRef);
                                slip.setAppIdentifier(resp.getApplicationIdentifier() != null ? resp.getApplicationIdentifier() : "");
                                slip.setAcqReference(resp.getpAuthorizationCode() != null ? resp.getpAuthorizationCode() : mRef);
                                slip.setAuthReference(resp.getpAuthorizationCode() != null ? resp.getpAuthorizationCode() : mRef);
                                slip.setAvtorizacija("REF");

                                RosKasaSoapClient.setSlipEma(serverUrl, token, slip);
                                PaymentRecoveryManager.clearAllRecoveryData(requireContext());

                                mainHandler.post(() -> {
                                    enableEkran();
                                    izvediBrisanjePlacilaNaStreznikuDirect(pl);
                                });
                            } catch (Exception e) {
                                mainHandler.post(() -> {
                                    enableEkran();
                                    Log.w(TAG, "setSlipEma Payten storno opozorilo: " + e.getMessage());
                                    izvediBrisanjePlacilaNaStreznikuDirect(pl);
                                });
                            }
                        });
                    } else {
                        PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                        Globals.getInstance().vpisiKronologijo("PAYTEN RESULT FAIL R:" + racunId + " void Code:" + resp.getCode() + " Msg:" + resp.getMessage());
                        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                                .setTitle("Napaka storno PayTen")
                                .setMessage("Storno na POS terminalu ni uspel: " + resp.getCode() + " " + resp.getMessage() + "\n\nPlačilo NI bilo izbrisano!")
                                .setPositiveButton("V redu", null)
                                .show();
                    }
                } else {
                    PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                    Globals.getInstance().vpisiKronologijo("PAYTEN void prekinjen R:" + racunId);
                    Toast.makeText(requireContext(), "Storno PayTen preklican. Plačilo NI bilo izbrisano.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSixTapResult(int resultCode, Intent data) {}
        });
    }

    private void izvediBrisanjePlacilaNaStreznikuDirect(PlaciloTp pl) {

        // Zagotovi deep copy v originalObject pred spremembo
        if (currentRacun.getOriginalObject() == null) {
            currentRacun.setOriginalObject(currentRacun.deepCopy());
        }
        if (pl.getOriginalObject() == null) {
            pl.setOriginalObject(pl.deepCopy());
        }

        if (pl.getPlaciloId() == 99) {
            // Razveljavi popust 99 na postavkah računa (Delphi BrisiPopustNaRacun)
            Globals.getInstance().brisiPopustNaRacun(currentRacun, pl.getStatus(), pl.getZnesek());
        }

        BigDecimal znesekBrisanega = (pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0)
                ? pl.getDelniZnesek()
                : (pl.getZnesek() != null ? pl.getZnesek() : BigDecimal.ZERO);

        pl.setRowDeleted(true);

        // Sinhroniziraj RACGLAVA.PLACANO z zmanjšanim zneskom (za popust 99 je delni_znesek 0)
        BigDecimal novoPlacano = (currentRacun.getPlacano() != null ? currentRacun.getPlacano() : BigDecimal.ZERO).subtract(znesekBrisanega);
        if (novoPlacano.compareTo(BigDecimal.ZERO) < 0) {
            novoPlacano = BigDecimal.ZERO;
        }
        currentRacun.setPlacano(novoPlacano);
        currentRacun.preracunajVsote();

        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setNeNarocaj(true);
            }
        }

        disableEkran("Brisanje plačila na strežniku...");
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                mainHandler.post(() -> {
                    enableEkran();
                    if (response != null && response.getRacGlava() != null) {
                        posodobiRacunIzStreznik(response.getRacGlava());
                        Toast.makeText(requireContext(), "Plačilo uspešno izbrisano na strežniku.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Strežnik ni vrnil posodobljenega računa.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (VersionConflictException vce) {
                mainHandler.post(() -> {
                    enableEkran();
                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Konflikt verzije računa")
                            .setMessage("Račun je medtem spremenil drug natakar. Podatki se bodo osvežili.")
                            .setPositiveButton("V redu", (d, w) -> loadRacunData())
                            .show();
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    enableEkran();
                    pl.setRowDeleted(false);
                    currentRacun.preracunajVsote();
                    populatePlacilaListFromCurrentRacun();
                    Toast.makeText(requireContext(), "Napaka pri brisanju plačila: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void handleLojalnostPopustClick() {
        if (currentRacun == null || currentRacun.getRacPozic() == null || currentRacun.getRacPozic().isEmpty()) {
            Toast.makeText(requireContext(), "Ni odprtega računa s postavkami za lojalnost!", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer currentLojId = currentRacun.getLojalnostId();

        if ((currentLojId == null || currentLojId <= 0) && preveriPopustNaRacunu()) {
            Toast.makeText(requireContext(), "Na računu je že popust! Dodaten lojalnostni popust ni mogoč.", Toast.LENGTH_LONG).show();
            return;
        }

        LojalnostPopustDialog.show(requireContext(), prefs.getServerUrl(), prefs.getToken(), currentLojId, new LojalnostPopustDialog.OnLojalnostSelectedListener() {
            @Override
            public void onSelected(LojalnostnaTp lojalnost) {
                if (preveriPopustNaRacunu()) {
                    Toast.makeText(requireContext(), "Na računu je že popust! Dodaten lojalnostni popust ni mogoč.", Toast.LENGTH_LONG).show();
                    return;
                }
                izvediSetLojalnost(lojalnost.getBonitetniRazred());
            }

            @Override
            public void onRemoved() {
                izvediSetLojalnost(0);
            }
        });
    }

    private boolean preveriPopustNaRacunu() {
        if (currentRacun == null) return false;
        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p == null || p.isRowDeleted()) continue;
                if (p.getZnesekPopust() != null && p.getZnesekPopust().compareTo(BigDecimal.ZERO) > 0) return true;
            }
        }
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() == 99) return true;
            }
        }
        return false;
    }

    private void izvediSetLojalnost(int bonitetniRazred) {
        if (currentRacun == null) return;
        final int racunId = currentRacun.getRacunId();
        final int verzija = currentRacun.getVerzijaZapisa();

        disableEkran("Uveljavljam lojalnostni popust...");
        executor.execute(() -> {
            try {
                GetRacunRsTp result = RosKasaSoapClient.setLojalnost(prefs.getServerUrl(), prefs.getToken(), bonitetniRazred, racunId, verzija);
                mainHandler.post(() -> {
                    enableEkran();
                    if (result != null && result.getRacGlava() != null) {
                        currentRacun = result.getRacGlava();
                        Globals.getInstance().setCurrentRacun(currentRacun);
                        prefs.setActiveRacunId(currentRacun.getRacunId());
                        populatePlacilaListFromCurrentRacun();
                        String msg = bonitetniRazred > 0
                                ? "Lojalnostni popust (razred " + bonitetniRazred + ") uspešno uveljavljen!"
                                : "Lojalnostni popust uspešno odstranjen!";
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
                    } else {
                        String errMsg = (result != null && result.getFault() != null && !result.getFault().isEmpty())
                                ? result.getFault()
                                : "Strežnik ni vrnil posodobljenega računa.";
                        Toast.makeText(requireContext(), errMsg, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Napaka pri klicu setLojalnost: " + e.getMessage(), e);
                mainHandler.post(() -> {
                    enableEkran();
                    Toast.makeText(requireContext(), "Napaka pri uveljavljanju lojalnosti: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void handlePopust99Click() {
        if (currentRacun == null || currentRacun.getRacPozic() == null || currentRacun.getRacPozic().isEmpty()) {
            Toast.makeText(requireContext(), "Ni odprtega računa s postavkami za popust!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentRacun.getLojalnostId() != null && currentRacun.getLojalnostId() > 0) {
            Toast.makeText(requireContext(), "Račun že ima lojalnostni popust! Pred vnosom popusta ga odstranite.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean zeImaPopust = false;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() == 99) {
                    zeImaPopust = true;
                    break;
                }
            }
        }
        if (zeImaPopust) {
            Toast.makeText(requireContext(), "Račun že ima vnesen popust (99)! Pred vnosom novega izbrišite obstoječega.", Toast.LENGTH_SHORT).show();
            return;
        }

        currentRacun.preracunajVsote();
        BigDecimal znesekRacuna = currentRacun.getZnesek();
        if (znesekRacuna == null || znesekRacuna.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(requireContext(), "Znesek računa mora biti večji od 0!", Toast.LENGTH_SHORT).show();
            return;
        }

        Popust99Dialog.show(requireContext(), znesekRacuna, new Popust99Dialog.OnPopustAppliedListener() {
            @Override
            public void onPopustApplied(BigDecimal procent, BigDecimal znesek) {
                applyPopust99(procent, znesek);
            }

            @Override
            public void onCancelled() {}
        });
    }

    private void applyPopust99(BigDecimal procent, BigDecimal znesek) {
        if (currentRacun == null) return;

        if (currentRacun.getOriginalObject() == null) {
            currentRacun.setOriginalObject(currentRacun.deepCopy());
        }

        BigDecimal totalPopust = Globals.getInstance().popustNaRacun(currentRacun, procent, znesek);

        int nextPozId = -1;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp p : currentRacun.getRacPlaci()) {
                if (p != null && p.getPozicijaId() <= nextPozId) {
                    nextPozId = p.getPozicijaId() - 1;
                }
            }
        }

        final PlaciloTp pl = new PlaciloTp(currentRacun.getRacunId(), 99, totalPopust);
        pl.setPlaciloId(99);
        pl.setZnesek(totalPopust);
        pl.setDelniZnesek(BigDecimal.ZERO); // Delphi popusti.md pravilo: delni_znesek = 0
        pl.setStatus(procent != null ? procent : BigDecimal.ZERO);
        pl.setPozicijaId(nextPozId);
        pl.setVerzijaZapisa(0);
        pl.setRowDeleted(false);
        pl.setOriginalObject(null);

        int tocId = (currentRacun.getTocilnicaId() != null && currentRacun.getTocilnicaId() > 0)
                ? currentRacun.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        pl.setTocilnicaId(tocId);

        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setNeNarocaj(true);
            }
        }
        currentRacun.setStatus(1);
        currentRacun.setMarker(activeMarker);
        currentRacun.setfPosId(prefs.getfPosId() > 0 ? prefs.getfPosId() : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500));
        currentRacun.setfPoslovniProstorId(Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000);
        currentRacun.setTocilnicaId(tocId);
        if (currentRacun.getKasiral() == null || currentRacun.getKasiral() <= 0) {
            currentRacun.setKasiral(9999);
        }
        if (currentRacun.getTipRacuna() == null || currentRacun.getTipRacuna() <= 0) {
            currentRacun.setTipRacuna(1);
        }
        if (currentRacun.getStPogrinjkov() == null || currentRacun.getStPogrinjkov() <= 0) {
            currentRacun.setStPogrinjkov(1);
        }
        if (currentRacun.getStKopij() == null) {
            currentRacun.setStKopij(0);
        }

        if (currentRacun.getRacPlaci() == null) {
            currentRacun.setRacPlaci(new ArrayList<>());
        }
        currentRacun.getRacPlaci().add(pl);
        currentRacun.preracunajVsote();

        disableEkran("Knjiženje popusta na strežnik...");
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                mainHandler.post(() -> {
                    enableEkran();
                    if (response != null && response.getRacGlava() != null) {
                        posodobiRacunIzStreznik(response.getRacGlava());
                        Toast.makeText(requireContext(), "Popust uspešno knjižen (-" + String.format(Locale.getDefault(), "%.2f €", totalPopust) + ")!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Strežnik ni vrnil posodobljenega računa.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (VersionConflictException vce) {
                mainHandler.post(() -> {
                    enableEkran();
                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Konflikt verzije računa")
                            .setMessage("Račun je medtem spremenil drug natakar. Podatki se bodo osvežili s strežnika.")
                            .setPositiveButton("V redu", (dialog, which) -> loadRacunData())
                            .setCancelable(false)
                            .show();
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    enableEkran();
                    if (currentRacun != null && currentRacun.getRacPlaci() != null) {
                        currentRacun.getRacPlaci().remove(pl);
                        Globals.getInstance().brisiPopustNaRacun(currentRacun, procent, znesek);
                        currentRacun.preracunajVsote();
                    }
                    populatePlacilaListFromCurrentRacun();
                    Toast.makeText(requireContext(), "Napaka pri knjiženju popusta: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void handleOpisPartnerja() {
        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni aktivnega računa!", Toast.LENGTH_SHORT).show();
            return;
        }

        PlaciloTp zadnje = null;
        if (currentRacun.getRacPlaci() != null) {
            for (int i = currentRacun.getRacPlaci().size() - 1; i >= 0; i--) {
                PlaciloTp p = currentRacun.getRacPlaci().get(i);
                if (p != null && !p.isRowDeleted() && p.getPlaciloId() != 99) {
                    zadnje = p;
                    break;
                }
            }
        }

        String initNaziv = (zadnje != null && zadnje.getNazivPartner() != null) ? zadnje.getNazivPartner() : "";
        String initNaslov = (zadnje != null && zadnje.getNaslovPartner() != null) ? zadnje.getNaslovPartner() : "";
        String initDavcna = (zadnje != null && zadnje.getDavcnaSt() != null) ? zadnje.getDavcnaSt() : "";
        String initNaroc = (zadnje != null && zadnje.getStNarocilnice() != null) ? zadnje.getStNarocilnice() : "";

        final PlaciloTp finalPlacilo = zadnje;

        PartnerVnosDialog.show(requireContext(), null, 0, initNaziv, initNaslov, initDavcna, initNaroc, (partnerId, naziv, naslov, davcna, stNarocilnice, rabat) -> {
            if (currentRacun == null) return;
            if (currentRacun.getOriginalObject() == null) {
                currentRacun.setOriginalObject(currentRacun.deepCopy());
            }
            if (partnerId > 0) {
                currentRacun.setPartnerId(partnerId);
            }

            if (finalPlacilo != null) {
                if (finalPlacilo.getOriginalObject() == null) {
                    finalPlacilo.setOriginalObject(finalPlacilo.deepCopy());
                }
                if (partnerId > 0) {
                    finalPlacilo.setPartnerId(partnerId);
                    finalPlacilo.setKupecId(partnerId);
                }
                finalPlacilo.setNazivPartner(naziv);
                finalPlacilo.setNaslovPartner(naslov);
                finalPlacilo.setDavcnaSt(davcna);
                finalPlacilo.setStNarocilnice(stNarocilnice);
            }

            if (rabat != null && rabat.compareTo(BigDecimal.ZERO) > 0) {
                applyPopust99(rabat, BigDecimal.ZERO);
            } else {
                shraniRacunNaServer("Partner opis: " + naziv);
            }
            Toast.makeText(requireContext(), "Podatki partnerja shranjeni na račun!", Toast.LENGTH_SHORT).show();
            updatePlacilaSummary();
        });
    }

    private void handleDelovniNalog() {
        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni aktivnega računa!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Delphi: if DNCENIK and (dmGisOrder.tblRacGlavaDN_ID.AsString<>'') and (dmGisOrder.tblRacPozic.recordcount>0) then
        if (Globals.getInstance().isDnCenik()
                && currentRacun.getDnId() != null && !currentRacun.getDnId().trim().isEmpty()
                && currentRacun.getRacPozic() != null && !currentRacun.getRacPozic().isEmpty()) {
            Toast.makeText(requireContext(), "Zamenjava DN ni možna !", Toast.LENGTH_LONG).show();
            return;
        }

        DelovniNalogiDialog.show(requireContext(), currentRacun.getDnId(), new DelovniNalogiDialog.OnDelovniNalogSelectedListener() {
            @Override
            public void onSelected(DelovniNalogTp dn) {
                if (currentRacun == null || dn == null) return;
                if (currentRacun.getOriginalObject() == null) {
                    currentRacun.setOriginalObject(currentRacun.deepCopy());
                }
                currentRacun.setDnId(dn.getDnId());
                if (dn.getPartnerId() != null && dn.getPartnerId() > 0) {
                    currentRacun.setPartnerId(dn.getPartnerId());
                }
                Globals.getInstance().vpisiKronologijo("IZBOR DN: " + dn.getDnId() + " R:" + currentRacun.getRacunId());
                shraniRacunNaServer("Izbran DN: " + dn.getDnId());
                Toast.makeText(requireContext(), "Delovni nalog nastavljen: " + dn.getDnId(), Toast.LENGTH_SHORT).show();
                updatePlacilaSummary();
            }

            @Override
            public void onRemoved() {
                if (currentRacun == null) return;
                if (currentRacun.getOriginalObject() == null) {
                    currentRacun.setOriginalObject(currentRacun.deepCopy());
                }
                currentRacun.setDnId("");
                Globals.getInstance().vpisiKronologijo("ODSTRANJEN DN R:" + currentRacun.getRacunId());
                shraniRacunNaServer("Odstranjen DN");
                Toast.makeText(requireContext(), "Delovni nalog odstranjen", Toast.LENGTH_SHORT).show();
                updatePlacilaSummary();
            }
        });
    }

    private void handleOpombaRacuna() {
        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni aktivnega računa!", Toast.LENGTH_SHORT).show();
            return;
        }

        OpombaRacunaDialog.show(requireContext(), currentRacun.getOpomba(), opomba -> {
            if (currentRacun == null) return;
            if (currentRacun.getOriginalObject() == null) {
                currentRacun.setOriginalObject(currentRacun.deepCopy());
            }
            currentRacun.setOpomba(opomba);
            shraniRacunNaServer("Posodobljena opomba računa");
            Toast.makeText(requireContext(), "Opomba računa shranjena.", Toast.LENGTH_SHORT).show();
            updatePlacilaSummary();
        });
    }

    private void shraniRacunNaServer(String operacijaOpis) {
        if (currentRacun == null) return;
        disableEkran("Shranjevanje (" + operacijaOpis + ")...");
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                mainHandler.post(() -> {
                    enableEkran();
                    if (response != null && response.getRacGlava() != null) {
                        posodobiRacunIzStreznik(response.getRacGlava());
                    }
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    enableEkran();
                    Toast.makeText(requireContext(), "Napaka pri shranjevanju: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void handleIzpisRacuna() {
        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni aktivnega računa za izpis!", Toast.LENGTH_SHORT).show();
            return;
        }

        currentRacun.preracunajVsote();

        if (currentRacun.getPlacano().compareTo(currentRacun.getZnesek()) < 0) {
            String opozorilo = String.format(Locale.getDefault(), "Račun ni v celoti plačan!\nZnesek: %.2f €  |  Plačano: %.2f €", currentRacun.getZnesek(), currentRacun.getPlacano());
            Toast.makeText(requireContext(), opozorilo, Toast.LENGTH_LONG).show();
            return;
        }

        izvediZakljucekRacuna(true);
    }

    private void izvediZakljucekRacuna(boolean natisniRacun) {
        disableEkran("Zaključevanje in fiskalizacija računa...");

        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                // Določitev fiskalizacije glede na vnešena plačila (NACPLAC.FISKALNO)
                boolean imaFiskalnoPlacilo = false;
                if (currentRacun.getRacPlaci() != null) {
                    for (PlaciloTp pl : currentRacun.getRacPlaci()) {
                        if (pl != null && !pl.isRowDeleted() && pl.getPlaciloId() != 99) {
                            NacPlacTp np = Globals.getInstance().getPlaciloById(pl.getPlaciloId());
                            if (np != null && np.getFiskalno() != null && np.getFiskalno() == 1) {
                                imaFiskalnoPlacilo = true;
                                break;
                            }
                        }
                    }
                }
                if (imaFiskalnoPlacilo) {
                    currentRacun.setFiskalizacija(1);
                } else {
                    currentRacun.setFiskalizacija(null); // Todo.md pravilo: Update FISKALIZACIJA je samo za FISKALIZACIJA=1 drugače je null
                }

                // STATUS = 2 pomeni izpisan / fiskaliziran račun
                currentRacun.setStatus(2);
                currentRacun.setMarker(activeMarker);
                currentRacun.setfPosId(prefs.getfPosId() > 0 ? prefs.getfPosId() : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500));
                currentRacun.setfPoslovniProstorId(Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000);
                if (currentRacun.getRacPozic() != null) {
                    for (PozicijaTp p : currentRacun.getRacPozic()) {
                        if (p != null) p.setNeNarocaj(true);
                    }
                }

                // Klic setRacun za zaključek
                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);

                RacunTp racunZaTisk = currentRacun;
                if (response != null && response.getRacGlava() != null) {
                    RacunTp returned = response.getRacGlava();
                    if ((returned.getRacPozic() == null || returned.getRacPozic().isEmpty()) && currentRacun.getRacPozic() != null) {
                        returned.setRacPozic(currentRacun.getRacPozic());
                    }
                    if (returned.getRacPlaci() == null && currentRacun.getRacPlaci() != null) {
                        returned.setRacPlaci(currentRacun.getRacPlaci());
                    }
                    if (returned.getFiskalizacija() == null && currentRacun.getFiskalizacija() != null) {
                        returned.setFiskalizacija(currentRacun.getFiskalizacija());
                    }
                    // 100% KONTROLA: RACGLAVA.ZNESEK je vedno suma narocila in se NIKOLI ne spreminja!
                    if (currentRacun.getZnesek() != null && currentRacun.getZnesek().compareTo(BigDecimal.ZERO) > 0) {
                        returned.setZnesek(currentRacun.getZnesek());
                    }
                    racunZaTisk = returned;
                }

                // Zagotovi, da imajo vse postavke veljaven naziv iz cenika/hitrih tipk
                if (racunZaTisk != null && racunZaTisk.getRacPozic() != null) {
                    for (PozicijaTp p : racunZaTisk.getRacPozic()) {
                        if (p != null && p.getNivo4Id() != null && p.getNivo4Id() > 0) {
                            String lookup = Globals.getInstance().findNazivByNivo4Id(p.getNivo4Id());
                            if (lookup != null && !lookup.trim().isEmpty()) {
                                p.setNaziv(lookup.trim());
                            }
                        }
                    }
                }

                final RacunTp finalRacunToPrint = racunZaTisk;
                final int finalStKopij = Globals.getInstance().stKopijPlacila(racunZaTisk);

                mainHandler.post(() -> {
                    enableEkran();
                    Toast.makeText(requireContext(), "Račun #" + currentRacun.getRacunId() + " uspešno zaključen!", Toast.LENGTH_SHORT).show();

                    // Sprožitev celovitega tiskanja računa in dodatkov preko Bluetooth (Delphi uPrintData.pas skladnost)
                    if (natisniRacun) {
                        BluetoothPrintHelper.printReceiptComplete(requireContext(), finalRacunToPrint, new BluetoothPrintHelper.OnPrintListener() {
                            @Override
                            public void onStart() {}

                            @Override
                            public void onSuccess(String message) {
                                if (getContext() != null) {
                                    Toast.makeText(requireContext(), "Tisk računa: " + message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(String errorMessage) {
                                if (getContext() != null) {
                                    Toast.makeText(requireContext(), "Napaka tiskanja: " + errorMessage, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    // Počisti aktivni račun
                    prefs.setActiveRacunId(0);
                    Globals.getInstance().setCurrentRacun(null);

                    // Navigacija nazaj na mize ali prijavo
                    if (Globals.getInstance().ispLogoutPoIzpisu()) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
                        }
                    } else if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
                    }
                });

            } catch (VersionConflictException vce) {
                mainHandler.post(() -> {
                    enableEkran();
                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Konflikt verzije računa")
                            .setMessage("Račun je medtem spremenil drug natakar. Podatki se bodo osvežili.")
                            .setPositiveButton("V redu", (dialog, which) -> loadRacunData())
                            .show();
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    enableEkran();
                    Toast.makeText(requireContext(), "Napaka pri zaključku računa: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void izvediPosPlacilo(final int placiloId, final String nacinNaziv, final BigDecimal zaplacilo) {
        if (zaplacilo == null || zaplacilo.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        if (!(getActivity() instanceof MainActivity)) {
            Toast.makeText(requireContext(), "Napaka: MainActivity ni dostopen!", Toast.LENGTH_SHORT).show();
            return;
        }
        MainActivity activity = (MainActivity) getActivity();
        if (currentRacun == null) return;
        final int racunId = currentRacun.getRacunId();

        if (Globals.getInstance().isPayTenA()) {
            Globals.getInstance().setPayTenAPlaciloId(placiloId);
            Globals.getInstance().vpisiKronologijo("PAYTEN purchase za R:" + racunId + " Z:" + zaplacilo);
            android.content.Intent paytenIntent = PaytenPaymentService.buildPaymentIntent(requireContext(), racunId, 0, zaplacilo, "purchase", null);
            disableEkran("Čakam na plačilo PayTen...");
            activity.launchPayten(paytenIntent, new MainActivity.PaymentResultListener() {
                @Override
                public void onPaytenResult(int resultCode, android.content.Intent data) {
                    enableEkran();
                    handlePaytenResult(resultCode, data, placiloId, nacinNaziv, zaplacilo);
                }

                @Override
                public void onSixTapResult(int resultCode, android.content.Intent data) {}
            });
        } else if (Globals.getInstance().isSixTap()) {
            Globals.getInstance().setSixTapPlaciloId(placiloId);
            android.content.Intent sixTapIntent = SixTapPaymentService.buildPaymentIntent(requireContext(), racunId, 0, zaplacilo, BigDecimal.ZERO, SixTapPaymentService.OP_PAYMENT, null, null);
            disableEkran("Čakam na plačilo Worldline Tap On...");
            activity.launchSixTap(sixTapIntent, new MainActivity.PaymentResultListener() {
                @Override
                public void onPaytenResult(int resultCode, android.content.Intent data) {}

                @Override
                public void onSixTapResult(int resultCode, android.content.Intent data) {
                    enableEkran();
                    handleSixTapResult(resultCode, data, placiloId, nacinNaziv, zaplacilo);
                }
            });
        } else {
            // Intent plačilo NI vklopljeno! Izvedi direktno kartično plačilo in izpis do konca!
            zakljuciInNatisniHitroPlacilo(placiloId, nacinNaziv, zaplacilo);
        }
    }

    private void handlePaytenResult(int resultCode, android.content.Intent data, int placiloId, String nacinNaziv, BigDecimal zaplacilo) {
        final int racunId = currentRacun != null ? currentRacun.getRacunId() : 0;
        if (resultCode == android.app.Activity.RESULT_OK && data != null) {
            PaytenResponse resp = PaytenPaymentService.parseResponseIntent(data);
            if (resp.isSuccess()) {
                Globals.getInstance().vpisiKronologijo("PAYTEN RESULT OK R:" + racunId + " M_REF:" + resp.getPaymentSolutionReference() + " Auth:" + resp.getpAuthorizationCode());
                BigDecimal znesekPOS = resp.getZnesekPOS().compareTo(BigDecimal.ZERO) > 0 ? resp.getZnesekPOS() : zaplacilo;
                zakljuciUspesnoPosPlacilo(nacinNaziv, placiloId, znesekPOS, resp.getCardNumber(), resp.getPaymentSolutionReference(),
                        null, resp.getReceipt(), resp.getClient(), resp.getApplicationIdentifier(), resp.getpAuthorizationCode(),
                        resp.getpAuthorizationCode(), resp.getpNapitnina(), "purchase");
            } else {
                PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                Globals.getInstance().vpisiKronologijo("PAYTEN RESULT FAIL R:" + racunId + " Code:" + resp.getCode() + " Msg:" + resp.getMessage());
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Napaka PayTen")
                        .setMessage("Transakcija ni uspela: " + resp.getCode() + " " + resp.getMessage())
                        .setPositiveButton("V redu", null)
                        .show();
            }
        } else {
            PaymentRecoveryManager.clearAllRecoveryData(requireContext());
            Globals.getInstance().vpisiKronologijo("PAYTEN preklican ali neuspešen R:" + racunId);
            Toast.makeText(requireContext(), "Plačilo PayTen preklicano ali neuspešno.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSixTapResult(int resultCode, android.content.Intent data, int placiloId, String nacinNaziv, BigDecimal zaplacilo) {
        final int racunId = currentRacun != null ? currentRacun.getRacunId() : 0;
        if (resultCode == android.app.Activity.RESULT_OK && data != null) {
            SixTapResponse resp = SixTapPaymentService.parseResponseIntent(data);
            if (resp.isSuccess()) {
                PaymentRecoveryManager.saveConfirmedPOSTransaction(requireContext(), resp, PaymentRecoveryManager.POS_STATE_POS_CONFIRMED);
                BigDecimal znesekPOS = resp.getZnesekPOS().compareTo(BigDecimal.ZERO) > 0 ? resp.getZnesekPOS() : zaplacilo;

                String ref = resp.getReference() != null && !resp.getReference().isEmpty() ? resp.getReference() : String.valueOf(racunId);
                String mRef = resp.getPaymentSolutionReference() != null ? resp.getPaymentSolutionReference() : "";
                String authAmt = resp.getAuthorizedAmount() != null ? resp.getAuthorizedAmount() : "";
                String tipAmt = resp.getTipAmount() != null ? resp.getTipAmount() : "";
                String remark = resp.getRemark() != null ? resp.getRemark() : "Transaction finished";
                String sessionId = resp.getpWpiSessionId() != null ? resp.getpWpiSessionId() : (Globals.getInstance().getZadnjiWpiSessionId() != null ? Globals.getInstance().getZadnjiWpiSessionId() : "");
                String znesekPOSStr = String.format(Locale.GERMANY, "%.2f", znesekPOS).replace(",00", ",0");
                String zaplaciloStr = String.format(Locale.GERMANY, "%.2f", zaplacilo).replace(",00", ",0");

                // Kronologija uspešnega SixTap plačila (točno po vzorcu iz uporabnikovih specifikacij):
                // 1. RESULT OK
                Globals.getInstance().vpisiKronologijo("SIXTAP RESULT OK R:" + racunId + " LokalReference R:" + ref + " SIXTAP " + SixTapPaymentService.OP_PAYMENT + " result: WPI_RESULT_SUCCESS Error: WPI_ERR_COND_NONE za M_REF: " + mRef + " remark: " + remark + " SESSION_ID: " + sessionId);

                // 2. Kontrola napitnina (če je napitnina)
                if (resp.getTipAmount() != null && !resp.getTipAmount().isEmpty() && !"0".equals(resp.getTipAmount())) {
                    Globals.getInstance().vpisiKronologijo("SIXTAP Kontrola napitnina R:" + racunId + " " + SixTapPaymentService.OP_PAYMENT + " znesek: " + zaplaciloStr + " znesekPOS: " + znesekPOSStr + " napitnina: " + tipAmt + " TapOn napitnina: " + tipAmt);
                }

                // 3. WPI_SVC_PAYMENT
                Globals.getInstance().vpisiKronologijo("SIXTAP " + SixTapPaymentService.OP_PAYMENT + " za  R:" + racunId + " reference: " + ref + " paymentSolutionReference: " + mRef + " authorizedAmount: " + authAmt + " tipAmount: " + tipAmt);

                // 4. WPI_RESULT_SUCCESS
                Globals.getInstance().vpisiKronologijo("SIXTAP WPI_RESULT_SUCCESS za  R:" + racunId + " reference: " + ref + " paymentSolutionReference: " + mRef + " authorizedAmount: " + authAmt + " tipAmount: " + tipAmt + " Z:" + zaplaciloStr);

                zakljuciUspesnoPosPlacilo(nacinNaziv, placiloId, znesekPOS, resp.getCardnumber(), resp.getPaymentSolutionReference(),
                        resp.getpWpiSessionId(), resp.getClient(), resp.getMerchant(), resp.getApplicationIdentifier(), resp.getAcqreference(),
                        resp.getAuthNumber(), resp.getpNapitnina(), resp.getpOperacija());
            } else {
                PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                String errCond = resp.getErrorCondition() != null && !resp.getErrorCondition().isEmpty() ? resp.getErrorCondition() : "WPI_ERR_COND_FAIL";
                String remark = resp.getRemark() != null ? resp.getRemark() : "";
                String sessionId = resp.getpWpiSessionId() != null ? resp.getpWpiSessionId() : (Globals.getInstance().getZadnjiWpiSessionId() != null ? Globals.getInstance().getZadnjiWpiSessionId() : "");

                Globals.getInstance().vpisiKronologijo("SIXTAP RESULT OK R:" + racunId + " LokalReference R: SIXTAP " + SixTapPaymentService.OP_PAYMENT + " result: WPI_RESULT_FAILURE Error: " + errCond + " za M_REF:  remark: " + remark + " SESSION_ID: " + sessionId);
                Globals.getInstance().vpisiKronologijo("SIXTAP ClearPendingPayment paymentSolutionReference=null " + errCond + "  R:" + racunId);

                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Napaka SixTap")
                        .setMessage("Transakcija ni uspela: " + resp.getErrorCondition() + " " + resp.getRemark())
                        .setPositiveButton("V redu", null)
                        .show();
            }
        } else {
            // Preklic s strani uporabnika ali napaka klica
            Globals g = Globals.getInstance();
            final String sessionId = g.getZadnjiWpiSessionId() != null ? g.getZadnjiWpiSessionId() : "";
            int amountCents = zaplacilo.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).intValue();

            // Kronologija ob preklicu s strani uporabnika:
            Globals.getInstance().vpisiKronologijo("SIXTAP RESULT OK R:" + racunId + " LokalReference R: SIXTAP " + SixTapPaymentService.OP_PAYMENT + " result: WPI_RESULT_FAILURE Error: WPI_ERR_COND_USER_CANCEL za M_REF:  remark: Transaction cancelled by user. SESSION_ID: " + sessionId);
            Globals.getInstance().vpisiKronologijo("SIXTAP ClearPendingPayment paymentSolutionReference=null WPI_ERR_COND_USER_CANCEL  R:" + racunId);

            if (g.isSixTapManualLast() || g.isSixTapAutoLast()) {
                PaymentRecoveryManager.saveRecoveryPayment(requireContext(), sessionId, String.valueOf(racunId), String.valueOf(amountCents),
                        sessionId, String.valueOf(racunId), String.valueOf(amountCents));
                if (g.isSixTapAutoLast()) {
                    Toast.makeText(requireContext(), "Prekinjeno. Avtomatsko preverjam status plačila...", Toast.LENGTH_SHORT).show();
                    izvediSixTapLastTransaction(sessionId, racunId, zaplacilo, placiloId, nacinNaziv);
                } else {
                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Plačilo prekinjeno")
                            .setMessage("Plačila - Tap On tipka !")
                            .setPositiveButton("V redu", null)
                            .show();
                }
            } else {
                PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                Toast.makeText(requireContext(), "Plačilo Worldline Tap On preklicano.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleTapOnClick() {
        if (!Globals.getInstance().isSixTap()) {
            Toast.makeText(requireContext(), "Worldline Tap On ni vklopljen v nastavitvah.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentRacun == null) {
            Toast.makeText(requireContext(), "Ni odprtega računa!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Preveri, ali že imamo potrjeno transakcijo
        SixTapResponse confirmedResp = new SixTapResponse();
        int[] confirmedState = new int[1];
        if (PaymentRecoveryManager.tryGetConfirmedPOSTransaction(requireContext(), confirmedResp, confirmedState)) {
            if (confirmedState[0] >= PaymentRecoveryManager.POS_STATE_POS_CONFIRMED && confirmedResp.getpRacunId() == currentRacun.getRacunId()) {
                Toast.makeText(requireContext(), "Zaključujem že potrjeno plačilo...", Toast.LENGTH_SHORT).show();
                String nacin = getPaymentName(confirmedResp.getpPlaciloId() > 0 ? confirmedResp.getpPlaciloId() : 2);
                zakljuciUspesnoPosPlacilo(nacin, confirmedResp.getpPlaciloId() > 0 ? confirmedResp.getpPlaciloId() : 2,
                        confirmedResp.getZnesekPOS(), confirmedResp.getCardnumber(), confirmedResp.getPaymentSolutionReference(),
                        confirmedResp.getpWpiSessionId(), confirmedResp.getClient(), confirmedResp.getMerchant(),
                        confirmedResp.getApplicationIdentifier(), confirmedResp.getAcqreference(), confirmedResp.getAuthNumber(),
                        confirmedResp.getpNapitnina(), confirmedResp.getpOperacija());
                return;
            }
        }

        // 2. Preveri shranjene vrednosti seje
        PaymentRecoveryManager.PendingPaymentInfo info = PaymentRecoveryManager.tryGetRecoveryPayment(requireContext());
        if (!info.isValid()) {
            info = PaymentRecoveryManager.tryGetPendingPayment(requireContext());
        }

        if (!info.isValid()) {
            Toast.makeText(requireContext(), "Ni podatkov o zadnji plačilni seansi!", Toast.LENGTH_SHORT).show();
            return;
        }

        String savedRacunIdStr = info.getEffectiveRacunId();
        if (!String.valueOf(currentRacun.getRacunId()).equals(savedRacunIdStr)) {
            Toast.makeText(requireContext(), "Ni pravilen račun - izberite račun #" + savedRacunIdStr, Toast.LENGTH_LONG).show();
            return;
        }

        BigDecimal preostanek = getPreostanekZaPlacilo();
        if (preostanek.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(requireContext(), "Račun nima odprtega zneska za plačilo!", Toast.LENGTH_SHORT).show();
            PaymentRecoveryManager.clearAllRecoveryData(requireContext());
            return;
        }

        int retry = PaymentRecoveryManager.incrementRecoveryRetryCount(requireContext());
        if (retry >= 2) {
            Globals.getInstance().vpisiKronologijo("SIXTAP ClearPendingPayment retry >= 2 preklic obnove R:" + currentRacun.getRacunId());
            PaymentRecoveryManager.clearAllRecoveryData(requireContext());
            Toast.makeText(requireContext(), "Obnova transakcije ni uspela. Shramba plačil je bila ponastavljena.", Toast.LENGTH_LONG).show();
            return;
        }

        int placiloId = Globals.getInstance().getSixTapPlaciloId() > 0 ? Globals.getInstance().getSixTapPlaciloId() : 2;
        String nacinNaziv = getPaymentName(placiloId);
        izvediSixTapLastTransaction(info.getEffectiveSessionId(), currentRacun.getRacunId(), preostanek, placiloId, nacinNaziv);
    }

    private void izvediSixTapLastTransaction(String sessionId, int racunId, BigDecimal preostanek, int placiloId, String nacinNaziv) {
        if (!(getActivity() instanceof MainActivity)) return;
        MainActivity activity = (MainActivity) getActivity();

        android.content.Intent lastIntent = SixTapPaymentService.buildPaymentIntent(requireContext(), racunId, 0, preostanek, BigDecimal.ZERO,
                SixTapPaymentService.OP_LAST_TRANSACTION, null, sessionId);
        disableEkran("Preverjam zadnjo transakcijo na POS terminalu...");
        activity.launchSixTap(lastIntent, new MainActivity.PaymentResultListener() {
            @Override
            public void onPaytenResult(int resultCode, android.content.Intent data) {}

            @Override
            public void onSixTapResult(int resultCode, android.content.Intent data) {
                enableEkran();
                if (resultCode == android.app.Activity.RESULT_OK && data != null) {
                    SixTapResponse resp = SixTapPaymentService.parseResponseIntent(data);
                    if (resp.isSuccess() && resp.getPaymentSolutionReference() != null && !resp.getPaymentSolutionReference().isEmpty()) {
                        PaymentRecoveryManager.saveConfirmedPOSTransaction(requireContext(), resp, PaymentRecoveryManager.POS_STATE_POS_CONFIRMED);
                        BigDecimal znesekPOS = resp.getZnesekPOS().compareTo(BigDecimal.ZERO) > 0 ? resp.getZnesekPOS() : preostanek;
                        zakljuciUspesnoPosPlacilo(nacinNaziv, placiloId, znesekPOS, resp.getCardnumber(), resp.getPaymentSolutionReference(),
                                resp.getpWpiSessionId(), resp.getClient(), resp.getMerchant(), resp.getApplicationIdentifier(), resp.getAcqreference(),
                                resp.getAuthNumber(), resp.getpNapitnina(), resp.getpOperacija());
                    } else {
                        PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                        Toast.makeText(requireContext(), "LAST_TRANSACTION: Transakcija na POS ni bila uspešna.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    PaymentRecoveryManager.clearAllRecoveryData(requireContext());
                    Toast.makeText(requireContext(), "LAST_TRANSACTION: Preverjanje prekinjeno ali neuspešno.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void zakljuciUspesnoPosPlacilo(final String nacin, final int placiloId, final BigDecimal znesek,
                                           final String stKartice, final String mRef, final String wpiSessionId,
                                           final String slipClient, final String slipMerchant,
                                           final String appIdentifier, final String authRef, final String authNumber,
                                           final BigDecimal napitnina, final String operacija) {
        if (currentRacun == null) return;

        final int racunId = currentRacun.getRacunId();

        if (currentRacun.getOriginalObject() == null) {
            currentRacun.setOriginalObject(currentRacun.deepCopy());
        }

        int nextPozId = -1;
        if (currentRacun.getRacPlaci() != null) {
            for (PlaciloTp p : currentRacun.getRacPlaci()) {
                if (p != null && p.getPozicijaId() <= nextPozId) {
                    nextPozId = p.getPozicijaId() - 1;
                }
            }
        }

        final BigDecimal znesekRacuna = (napitnina != null && napitnina.compareTo(BigDecimal.ZERO) > 0 && znesek.subtract(napitnina).compareTo(BigDecimal.ZERO) > 0)
                ? znesek.subtract(napitnina)
                : znesek;

        final PlaciloTp pl = new PlaciloTp(racunId, placiloId, znesekRacuna);
        pl.setPlaciloId(placiloId);
        pl.setDelniZnesek(znesekRacuna);
        pl.setZnesek(BigDecimal.ZERO);
        pl.setPozicijaId(nextPozId);
        pl.setVerzijaZapisa(0);
        pl.setRowDeleted(false);
        pl.setOriginalObject(null);

        // Za kreditno kartico se knjižijo KUPEC_ID, M_REF in NAPITNINA (če > 0, če 0 se ne knjiži)
        NacPlacTp np = Globals.getInstance().getPlaciloById(placiloId);
        int kupecId = Globals.getInstance().getKredKarticaKupecId();
        if (kupecId <= 0 && np != null && np.getKupecId() != null && np.getKupecId() > 0) {
            kupecId = np.getKupecId().intValue();
        }
        if (kupecId > 0) {
            pl.setKupecId(kupecId);
            pl.setPartnerId(kupecId);
        }
        pl.setMRef(mRef != null ? mRef.trim() : "");
        if (napitnina != null && napitnina.compareTo(BigDecimal.ZERO) > 0) {
            pl.setNapitnina(napitnina);
        } else {
            pl.setNapitnina(null);
        }

        int tocId = (currentRacun.getTocilnicaId() != null && currentRacun.getTocilnicaId() > 0)
                ? currentRacun.getTocilnicaId()
                : (Globals.getInstance().getTocilnicaId() != null && Globals.getInstance().getTocilnicaId() > 0 ? Globals.getInstance().getTocilnicaId() : 512200);
        pl.setTocilnicaId(tocId);

        if (currentRacun.getRacPozic() != null) {
            for (PozicijaTp p : currentRacun.getRacPozic()) {
                if (p != null) p.setNeNarocaj(true);
            }
        }
        currentRacun.setStatus(1);
        currentRacun.setMarker(activeMarker);
        currentRacun.setfPosId(prefs.getfPosId() > 0 ? prefs.getfPosId() : (Globals.getInstance().getfPosId() != null && Globals.getInstance().getfPosId() > 0 ? Globals.getInstance().getfPosId() : 500));
        currentRacun.setfPoslovniProstorId(Globals.getInstance().getfPoslovniProstorId() != null && Globals.getInstance().getfPoslovniProstorId() > 0 ? Globals.getInstance().getfPoslovniProstorId() : 5000);
        currentRacun.setTocilnicaId(tocId);
        if (Globals.getInstance().getTekocaOsebaId() > 0) {
            currentRacun.setKasiral(Globals.getInstance().getTekocaOsebaId());
        } else if (currentRacun.getKasiral() == null || currentRacun.getKasiral() <= 0) {
            currentRacun.setKasiral(9999);
        }
        if (currentRacun.getTipRacuna() == null || currentRacun.getTipRacuna() <= 0) {
            currentRacun.setTipRacuna(1);
        }
        if (currentRacun.getStPogrinjkov() == null || currentRacun.getStPogrinjkov() <= 0) {
            currentRacun.setStPogrinjkov(1);
        }
        if (currentRacun.getStKopij() == null) {
            currentRacun.setStKopij(0);
        }

        if (currentRacun.getRacPlaci() == null) {
            currentRacun.setRacPlaci(new ArrayList<>());
        }
        currentRacun.getRacPlaci().add(pl);
        BigDecimal trenPlacano = currentRacun.getPlacano() != null ? currentRacun.getPlacano() : BigDecimal.ZERO;
        currentRacun.setPlacano(trenPlacano.add(znesekRacuna));
        currentRacun.preracunajVsote();
        if (currentRacun.getPlacano().compareTo(currentRacun.getZnesek()) >= 0) {
            currentRacun.setStatus(2);
            currentRacun.setFiskalizacija(1);
        }
        updatePlacilaSummary();

        disableEkran("Knjiženje POS plačila na strežnik...");
        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                int mobileId = 1;
                try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                // 1. setSlipEma
                String fullSlip = (slipClient != null && !slipClient.isEmpty()) ? slipClient : (slipMerchant != null ? slipMerchant : "");
                if (!fullSlip.isEmpty()) {
                    Globals.getInstance().setZadnjiSlip(racunId, fullSlip);
                }

                String op = (operacija != null && !operacija.isEmpty()) ? operacija : SixTapPaymentService.OP_PAYMENT;
                SlipEmaTp slip = new SlipEmaTp();
                slip.setStevilkaRacuna(racunId);
                slip.setPozicijaId(1);
                slip.setSlipPrint(slipClient != null ? slipClient : "");
                slip.setSlipPrints(slipMerchant != null ? slipMerchant : "");

                // STEVILKA_KARTICE: samo številka kartice brez "Mastercard" (npr. "543661******0033")
                String samoStKartice = SixTapPaymentService.extractDigitsOnly(stKartice);
                slip.setStevilkaKartice(samoStKartice);

                slip.setUspelo("DA");
                slip.setProjektId(3);
                slip.setStType(op);
                slip.setZnesek(znesekRacuna);
                slip.setZnesekSlip(znesek);
                slip.setAcqTransRef(wpiSessionId != null ? wpiSessionId : mRef);
                slip.setAppIdentifier(appIdentifier != null ? appIdentifier : "");
                slip.setAcqReference(authRef != null ? authRef : "");
                slip.setAuthReference(authNumber != null && !authNumber.isEmpty() ? authNumber : (authRef != null ? authRef : ""));
                slip.setAuthNumber(authNumber != null ? authNumber : "");

                // CARDNUMBER: celotna maskirana številka z znamko (npr. "Mastercard 543661******0033")
                String cleanFullCard = SixTapPaymentService.cleanCardNumber(stKartice);
                slip.setCardNumber(cleanFullCard);

                slip.setAvtorizacija("PAY");
                slip.setStrmId(tocId);

                Globals.getInstance().vpisiKronologijo("SIXTAP setSlipEma R:" + racunId + " Kartica:" + slip.getStevilkaKartice() + " Auth:" + slip.getAuthNumber() + " M_REF: " + mRef + " Op: " + op);

                boolean slipOk = RosKasaSoapClient.setSlipEma(serverUrl, token, slip);
                if (slipOk) {
                    Globals.getInstance().vpisiKronologijo("SIXTAP setSlipEma USPEH R:" + racunId);
                } else {
                    Globals.getInstance().vpisiKronologijo("SIXTAP setSlipEma OPOZORILO: strežnik ni potrdil slipa R:" + racunId);
                }

                // 2. setRacun
                Globals.getInstance().vpisiKronologijo("setRacun START R:" + racunId + " Znesek:" + znesekRacuna + " Placano:" + currentRacun.getPlacano());
                GetRacunRsTp response = RosKasaSoapClient.setRacun(serverUrl, token, mobileId, currentRacun);
                Globals.getInstance().vpisiKronologijo("setRacun USPEH R:" + racunId);
                mainHandler.post(() -> {
                    enableEkran();
                    // Počisti vse začasne podatke transakcije
                    PaymentRecoveryManager.clearAllRecoveryData(requireContext());

                    if (response != null && response.getRacGlava() != null) {
                        posodobiRacunIzStreznik(response.getRacGlava());

                        // Samodejno tiskanje zaključenega računa s slipom
                        BluetoothPrintHelper.printReceiptComplete(requireContext(), currentRacun, null);
                        Toast.makeText(requireContext(), "Plačilo " + nacin + " uspešno knjiženo in račun natisnjen!", Toast.LENGTH_SHORT).show();

                        // Po izpisu zaključenega računa se aplikacija vrne na fragment mize (enako kot tap na gumb Mize)
                        if (currentRacun.getPlacano().compareTo(currentRacun.getZnesek()) >= 0) {
                            prefs.setActiveRacunId(0);
                            Globals.getInstance().setCurrentRacun(null);
                            if (Globals.getInstance().ispLogoutPoIzpisu()) {
                                if (getActivity() instanceof MainActivity) {
                                    ((MainActivity) getActivity()).navigateToFragment(new LoginFragment());
                                }
                            } else if (getActivity() instanceof MainActivity) {
                                ((MainActivity) getActivity()).navigateToFragment(new MizeFragment());
                            }
                        }
                    }
                });
            } catch (Exception e) {
                Globals.getInstance().vpisiKronologijo("setRacun NAPAKA R:" + racunId + ": " + e.getMessage());
                mainHandler.post(() -> {
                    enableEkran();
                    Toast.makeText(requireContext(), "Plačilo je bilo izvedeno na POS, vendar je prišlo do napake pri knjiženju na strežnik: " + e.getMessage() + ". Uporabite Tap On za zaključek.", Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void disableEkran(String msg) {
        if (binding == null) return;
        binding.btnIzpisRacuna.setEnabled(false);
        binding.btnBrisanje.setEnabled(false);
        binding.btnNavMize.setEnabled(false);
        binding.btnNavRacuni.setEnabled(false);
        binding.btnNavNaroci.setEnabled(false);
        binding.btnOdjava.setEnabled(false);
        if (msg != null && !msg.isEmpty() && getContext() != null) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void enableEkran() {
        if (binding == null) return;
        binding.btnIzpisRacuna.setEnabled(true);
        binding.btnBrisanje.setEnabled(true);
        binding.btnNavMize.setEnabled(true);
        binding.btnNavRacuni.setEnabled(true);
        binding.btnNavNaroci.setEnabled(true);
        binding.btnOdjava.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
