package si.ros.RosKasa.ui;

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
import si.ros.RosKasa.print.BluetoothPrintHelper;
import si.ros.RosKasa.soap.RosKasaSoapClient;
import si.ros.RosKasa.soap.VersionConflictException;

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
                            currentRacun = loaded;
                            if (loaded.getRacPozic() != null) {
                                for (PozicijaTp p : loaded.getRacPozic()) {
                                    if (p != null && (p.getNaziv() == null || p.getNaziv().trim().isEmpty()) && p.getNivo4Id() != null && p.getNivo4Id() > 0) {
                                        String n = Globals.getInstance().findNazivByNivo4Id(p.getNivo4Id());
                                        if (n != null && !n.trim().isEmpty()) {
                                            p.setNaziv(n.trim());
                                        }
                                    }
                                }
                            }
                            Globals.getInstance().setCurrentRacun(loaded);
                            populatePlacilaListFromCurrentRacun();
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
            case 399: return "KREDIT. KARTICA";
            default: return "PLAČILO #" + placiloId;
        }
    }

    private void updatePlacilaSummary() {
        placilaAdapter.setItems(placilaItems);

        BigDecimal znesekRacuna = (currentRacun != null && currentRacun.getZnesek() != null) ? currentRacun.getZnesek() : BigDecimal.ZERO;
        BigDecimal totalPlacano = (currentRacun != null && currentRacun.getPlacano() != null) ? currentRacun.getPlacano() : BigDecimal.ZERO;

        String markerText = (currentRacun != null && currentRacun.getMarker() != null && !currentRacun.getMarker().isEmpty())
                ? currentRacun.getMarker()
                : (activeMarker != null ? activeMarker : "");

        binding.tvMizaStatus.setText(String.format(Locale.getDefault(), "M: %s  -  Zn: %.2f / Pl: %.2f", markerText, znesekRacuna, totalPlacano));
    }

    private BigDecimal getPreostanekZaPlacilo() {
        if (currentRacun == null) return BigDecimal.ZERO;
        currentRacun.preracunajVsote();
        BigDecimal remaining = currentRacun.getZnesek().subtract(currentRacun.getPlacano());
        return remaining.compareTo(BigDecimal.ZERO) > 0 ? remaining : BigDecimal.ZERO;
    }

    private void addPlacilo(String nacin, int placiloId, BigDecimal znesek) {
        addPlacilo(nacin, placiloId, znesek, null);
    }

    private void addPlacilo(final String nacin, final int placiloId, final BigDecimal znesek, final String stKarticeOpis) {
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
        pl.setZnesek(znesek);
        pl.setDelniZnesek(znesek);

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

        // Sinhroniziraj RACGLAVA.PLACANO z novim zneskom plačila
        BigDecimal trenPlacano = currentRacun.getPlacano() != null ? currentRacun.getPlacano() : BigDecimal.ZERO;
        currentRacun.setPlacano(trenPlacano.add(znesek));
        currentRacun.preracunajVsote();

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
                        RacunTp returned = response.getRacGlava();
                        // Ohrani lokalne pozicije, če jih strežnik v setRacun ni poslal nazaj
                        if ((returned.getRacPozic() == null || returned.getRacPozic().isEmpty()) && currentRacun.getRacPozic() != null && !currentRacun.getRacPozic().isEmpty()) {
                            returned.setRacPozic(currentRacun.getRacPozic());
                        }
                        // Ohrani plačila, če jih strežnik ni poslal nazaj
                        if ((returned.getRacPlaci() == null || returned.getRacPlaci().isEmpty()) && currentRacun.getRacPlaci() != null && !currentRacun.getRacPlaci().isEmpty()) {
                            returned.setRacPlaci(currentRacun.getRacPlaci());
                        }
                        // Ohrani znesek računa, če je strežniški 0
                        if ((returned.getZnesek() == null || returned.getZnesek().compareTo(BigDecimal.ZERO) == 0) && currentRacun.getZnesek() != null && currentRacun.getZnesek().compareTo(BigDecimal.ZERO) > 0) {
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
                        Globals.getInstance().setCurrentRacun(currentRacun);
                        prefs.setActiveRacunId(currentRacun.getRacunId());
                        populatePlacilaListFromCurrentRacun();

                        currentRacun.preracunajVsote();
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

        final BigDecimal zaplacilo = getPreostanekZaPlacilo();
        if (zaplacilo.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(requireContext(), "Račun je že v celoti plačan!", Toast.LENGTH_SHORT).show();
            return;
        }

        final int tempmetoda = Globals.getInstance().placilometoda(placiloId);
        final String nacinNaziv = getPaymentName(placiloId);

        // Če je račun že shranjen na strežniku (racunId > 0), preveri verzijo računa
        if (currentRacun.getRacunId() > 0) {
            disableEkran("Preverjanje verzije računa...");
            executor.execute(() -> {
                try {
                    int serverVer = RosKasaSoapClient.getVerzijaOfRacglava(prefs.getServerUrl(), prefs.getToken(), currentRacun.getRacunId());
                    if (serverVer > currentRacun.getVerzijaZapisa()) {
                        RacunTp refreshed = RosKasaSoapClient.getRacun(prefs.getServerUrl(), prefs.getToken(), currentRacun.getRacunId());
                        mainHandler.post(() -> {
                            enableEkran();
                            if (refreshed != null) {
                                currentRacun = refreshed;
                                Globals.getInstance().setCurrentRacun(refreshed);
                                populatePlacilaListFromCurrentRacun();
                            }
                            Toast.makeText(requireContext(), "Račun je medtem posodobil drug natakar (auto-refresh)!", Toast.LENGTH_LONG).show();
                        });
                        return;
                    }
                } catch (Exception e) {
                    Log.w(TAG, "getVerzijaOfRacglava preverjanje ni uspelo: " + e.getMessage());
                }

                mainHandler.post(() -> {
                    enableEkran();
                    izvediPlaciloPoMetodi(placiloId, tempmetoda, nacinNaziv, zaplacilo);
                });
            });
        } else {
            izvediPlaciloPoMetodi(placiloId, tempmetoda, nacinNaziv, zaplacilo);
        }
    }

    private void izvediPlaciloPoMetodi(int placiloId, int tempmetoda, String nacinNaziv, BigDecimal zaplacilo) {
        switch (tempmetoda) {
            case 6: // Hotel kredit (sobe)
                showHotelKreditDialog(placiloId, nacinNaziv, zaplacilo);
                break;

            default:
                addPlacilo(nacinNaziv, placiloId, zaplacilo, null);
                break;
        }
    }

    private void showHotelKreditDialog(int placiloId, String nacinNaziv, BigDecimal zaplacilo) {
        final android.widget.EditText etRoom = new android.widget.EditText(requireContext());
        etRoom.setHint("Številka sobe / gosta");
        etRoom.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Hotel Kredit - Soba")
                .setMessage("Vnesite številko hotelske sobe za bremenitev " + String.format(Locale.getDefault(), "%.2f €", zaplacilo) + ":")
                .setView(etRoom)
                .setPositiveButton("Potrdi", (dialog, which) -> {
                    String roomStr = etRoom.getText() != null ? etRoom.getText().toString().trim() : "";
                    String opis = !roomStr.isEmpty() ? nacinNaziv + " (Soba " + roomStr + ")" : nacinNaziv;
                    addPlacilo(opis, placiloId, zaplacilo, roomStr);
                })
                .setNegativeButton("Prekliči", null)
                .show();
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
            if (child instanceof android.widget.Button) {
                buttons.add((android.widget.Button) child);
            }
        }

        for (int i = 0; i < buttons.size(); i++) {
            android.widget.Button btn = buttons.get(i);
            if (i < methods.size()) {
                NacPlacTp np = methods.get(i);
                btn.setVisibility(View.VISIBLE);
                btn.setText(np.getNaziv());
                final int pid = np.getPlaciloId();
                btn.setOnClickListener(v -> klikniPlacilo(pid));
            } else {
                btn.setVisibility(View.INVISIBLE);
                btn.setOnClickListener(null);
            }
        }
    }

    private void renderDefaultPaymentButtons() {
        if (binding == null) return;
        binding.btnPayGotovina.setOnClickListener(v -> klikniPlacilo(1));
        binding.btnPayKredRocno.setOnClickListener(v -> klikniPlacilo(3));
        binding.btnPayKreditnaPos.setOnClickListener(v -> klikniPlacilo(2));
        binding.btnPayReprezentanca.setOnClickListener(v -> klikniPlacilo(5));
        binding.btnPayDobavnica.setOnClickListener(v -> klikniPlacilo(4));
        binding.btnPayValu.setOnClickListener(v -> klikniPlacilo(6));
        binding.btnPayMBills.setOnClickListener(v -> klikniPlacilo(7));
        binding.btnPayGostHotela.setOnClickListener(v -> klikniPlacilo(8));
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

            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Brisanje plačila")
                    .setMessage("Ali res želite stornirati plačilo " + nacin + " (" + String.format(Locale.getDefault(), "%.2f €", zn) + ")?")
                    .setPositiveButton("Izbriši", (dialog, which) -> brisiPlaciloNaStrezniku(placiloZaBrisanje))
                    .setNegativeButton("Prekliči", null)
                    .show();
        });

        binding.btnIzpisRacuna.setOnClickListener(v -> handleIzpisRacuna());
    }

    private void brisiPlaciloNaStrezniku(PlaciloTp pl) {
        if (currentRacun == null || pl == null) return;

        // Zagotovi deep copy v originalObject pred spremembo
        if (currentRacun.getOriginalObject() == null) {
            currentRacun.setOriginalObject(currentRacun.deepCopy());
        }
        if (pl.getOriginalObject() == null) {
            pl.setOriginalObject(pl.deepCopy());
        }

        BigDecimal znesekBrisanega = (pl.getDelniZnesek() != null && pl.getDelniZnesek().compareTo(BigDecimal.ZERO) > 0)
                ? pl.getDelniZnesek()
                : (pl.getZnesek() != null ? pl.getZnesek() : BigDecimal.ZERO);

        pl.setRowDeleted(true);

        // Sinhroniziraj RACGLAVA.PLACANO z zmanjšanim zneskom
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
                        RacunTp returned = response.getRacGlava();
                        // Ohrani lokalne pozicije, če jih strežnik v setRacun ni poslal nazaj
                        if ((returned.getRacPozic() == null || returned.getRacPozic().isEmpty()) && currentRacun.getRacPozic() != null && !currentRacun.getRacPozic().isEmpty()) {
                            returned.setRacPozic(currentRacun.getRacPozic());
                        }
                        // Ohrani plačila, če jih strežnik ni poslal nazaj
                        if ((returned.getRacPlaci() == null || returned.getRacPlaci().isEmpty()) && currentRacun.getRacPlaci() != null && !currentRacun.getRacPlaci().isEmpty()) {
                            returned.setRacPlaci(currentRacun.getRacPlaci());
                        }
                        // Ohrani znesek računa, če je strežniški 0
                        if ((returned.getZnesek() == null || returned.getZnesek().compareTo(BigDecimal.ZERO) == 0) && currentRacun.getZnesek() != null && currentRacun.getZnesek().compareTo(BigDecimal.ZERO) > 0) {
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
                        Globals.getInstance().setCurrentRacun(currentRacun);
                        prefs.setActiveRacunId(currentRacun.getRacunId());
                        populatePlacilaListFromCurrentRacun();
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
                    if ((returned.getRacPlaci() == null || returned.getRacPlaci().isEmpty()) && currentRacun.getRacPlaci() != null) {
                        returned.setRacPlaci(currentRacun.getRacPlaci());
                    }
                    if (returned.getFiskalizacija() == null && currentRacun.getFiskalizacija() != null) {
                        returned.setFiskalizacija(currentRacun.getFiskalizacija());
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

                    // Sprožitev tiskanja računa preko Bluetooth z novim RacunPrintBuilder
                    BluetoothPrintHelper.printReceipt(requireContext(), finalRacunToPrint, 0, new BluetoothPrintHelper.OnPrintListener() {
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

                    // Tiskanje morebitnih dodatnih kopij glede na način plačila
                    if (finalStKopij > 1) {
                        for (int k = 2; k <= finalStKopij; k++) {
                            final int kopijaIndex = k;
                            BluetoothPrintHelper.printReceipt(requireContext(), finalRacunToPrint, kopijaIndex, null);
                        }
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
