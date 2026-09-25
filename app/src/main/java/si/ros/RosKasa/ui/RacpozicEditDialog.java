package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.DodatekTp;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class RacpozicEditDialog {

    public interface OnItemEditedListener {
        void onItemUpdated();
        void onItemDeleted();
        void onCancelled();
    }

    public static void show(@NonNull Context context,
                            final PozicijaTp poz,
                            final boolean isPaket,
                            final List<PozicijaTp> allPaketPozicije,
                            final OnItemEditedListener listener) {
        if (poz == null) return;

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_racpozic_edit, null);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView tvNaziv = view.findViewById(R.id.tvEditNaziv);
        TextView tvCena = view.findViewById(R.id.tvEditCena);
        EditText etKolicina = view.findViewById(R.id.etKolicina);
        MaterialButton btnSub1 = view.findViewById(R.id.btnSub1);
        MaterialButton btnAdd1 = view.findViewById(R.id.btnAdd1);
        EditText etPopustProcent = view.findViewById(R.id.etPopustProcent);
        TextView tvPopustZnesek = view.findViewById(R.id.tvPopustZnesek);
        EditText etHod = view.findViewById(R.id.etHod);
        EditText etEP = view.findViewById(R.id.etEP);
        EditText etOpomba = view.findViewById(R.id.etOpomba);
        MaterialButton btnIzberiOpombo = view.findViewById(R.id.btnIzberiOpombo);
        TextView tvKoncniZnesek = view.findViewById(R.id.tvKoncniZnesek);
        MaterialButton btnDeletePozic = view.findViewById(R.id.btnDeletePozic);
        MaterialButton btnCancelEdit = view.findViewById(R.id.btnCancelEdit);
        MaterialButton btnOkEdit = view.findViewById(R.id.btnOkEdit);

        // Začetne vrednosti
        String initialNaziv = poz.getNaziv();
        if (isPaket && poz.getPaketNivo4Id() != null && poz.getPaketNivo4Id() > 0) {
            String pNaz = Globals.getInstance().findNazivByNivo4Id(poz.getPaketNivo4Id());
            if (pNaz != null && !pNaz.trim().isEmpty()) {
                initialNaziv = "[PAKET] " + pNaz.trim();
            }
        }
        tvNaziv.setText(initialNaziv != null ? initialNaziv : "Artikel");

        final double initialKol = isPaket
                ? (poz.getPaketKol() != null ? poz.getPaketKol().doubleValue() : 1.0)
                : poz.getKolicina();

        final BigDecimal unitPrice;
        if (isPaket) {
            BigDecimal paketSum = BigDecimal.ZERO;
            if (allPaketPozicije != null) {
                for (PozicijaTp p : allPaketPozicije) {
                    if (p != null && !p.isRowDeleted() && p.getZnesek() != null) {
                        paketSum = paketSum.add(p.getZnesek());
                    }
                }
            }
            unitPrice = initialKol > 0
                    ? paketSum.divide(BigDecimal.valueOf(initialKol), 2, RoundingMode.HALF_UP)
                    : poz.getCena();
        } else {
            unitPrice = poz.getCena() != null ? poz.getCena() : BigDecimal.ZERO;
        }

        tvCena.setText(String.format(Locale.getDefault(), "Cena: %.2f €", unitPrice));
        etKolicina.setText(String.format(Locale.US, "%.1f", initialKol));

        // Popust % (iz CENA_NABAVNA)
        BigDecimal initPopProc = (poz.getCenaNabavna() != null && poz.getCenaNabavna().compareTo(BigDecimal.ZERO) > 0)
                ? poz.getCenaNabavna() : BigDecimal.ZERO;
        if (initPopProc.compareTo(BigDecimal.ZERO) > 0) {
            etPopustProcent.setText(String.format(Locale.US, "%.1f", initPopProc));
        } else {
            etPopustProcent.setText("");
        }

        etHod.setText(poz.getHod() != null ? poz.getHod() : "");

        double initEP = (poz.getEnotaProdajeId() != null && poz.getEnotaProdajeId().compareTo(BigDecimal.ZERO) > 0)
                ? poz.getEnotaProdajeId().doubleValue() : 1.0;
        etEP.setText(String.format(Locale.US, "%.2f", initEP));
        if (isPaket) {
            etEP.setEnabled(false); // Paketi imajo sestavo določeno v CENIKVRVR
        }

        etOpomba.setText(poz.getDodatniOpis() != null ? poz.getDodatniOpis() : "");

        // Funkcija preračuna vsot
        Runnable preracunaj = () -> {
            try {
                String kolStr = etKolicina.getText().toString().replace(",", ".").trim();
                double k = kolStr.isEmpty() ? 0.0 : Double.parseDouble(kolStr);
                if (k < 0) k = 0.0;

                String epStr = etEP.getText().toString().replace(",", ".").trim();
                double epVal = epStr.isEmpty() ? 1.0 : Double.parseDouble(epStr);
                if (epVal <= 0) epVal = 1.0;

                String popStr = etPopustProcent.getText().toString().replace(",", ".").trim();
                BigDecimal popProc = popStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(popStr);
                if (popProc.compareTo(BigDecimal.ZERO) < 0) popProc = BigDecimal.ZERO;
                if (popProc.compareTo(BigDecimal.valueOf(100)) > 0) popProc = BigDecimal.valueOf(100);

                BigDecimal polna = unitPrice.multiply(BigDecimal.valueOf(k * epVal)).setScale(2, RoundingMode.HALF_UP);
                BigDecimal popZnesek = BigDecimal.ZERO;
                if (popProc.compareTo(BigDecimal.ZERO) > 0) {
                    popZnesek = polna.multiply(popProc).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                }

                BigDecimal končni = polna.subtract(popZnesek);
                if (končni.compareTo(BigDecimal.ZERO) < 0) končni = BigDecimal.ZERO;

                tvPopustZnesek.setText(String.format(Locale.getDefault(), "%.2f €", popZnesek));
                tvKoncniZnesek.setText(String.format(Locale.getDefault(), "Skupaj postavka: %.2f €", končni));
            } catch (Exception ignored) {}
        };

        preracunaj.run();

        // Poslušalci sprememb za preračun
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                preracunaj.run();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
        etKolicina.addTextChangedListener(tw);
        etPopustProcent.addTextChangedListener(tw);
        etEP.addTextChangedListener(tw);

        // Gumba -1 in +1
        btnSub1.setOnClickListener(v -> {
            try {
                String s = etKolicina.getText().toString().replace(",", ".").trim();
                double curr = s.isEmpty() ? 1.0 : Double.parseDouble(s);
                if (curr > 1.0) {
                    etKolicina.setText(String.format(Locale.US, "%.1f", curr - 1.0));
                }
            } catch (Exception ignored) {}
        });

        btnAdd1.setOnClickListener(v -> {
            try {
                String s = etKolicina.getText().toString().replace(",", ".").trim();
                double curr = s.isEmpty() ? 0.0 : Double.parseDouble(s);
                etKolicina.setText(String.format(Locale.US, "%.1f", curr + 1.0));
            } catch (Exception ignored) {}
        });

        // Gumb za izbiro opombe iz šifranta getDodatki
        btnIzberiOpombo.setOnClickListener(v -> {
            prikaziDodatkePicker(context, etOpomba);
        });

        // Brisanje postavke
        btnDeletePozic.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Brisanje postavke")
                    .setMessage("Ali res želite izbrisati postavko " + tvNaziv.getText().toString() + "?")
                    .setPositiveButton("Briši", (d, w) -> {
                        dialog.dismiss();
                        if (isPaket && allPaketPozicije != null) {
                            for (PozicijaTp p : allPaketPozicije) {
                                if (p.getPozicijaId() <= 0) {
                                    Globals.getInstance().getCurrentRacun().getRacPozic().remove(p);
                                } else {
                                    p.setRowDeleted(true);
                                }
                            }
                        } else {
                            if (poz.getPozicijaId() <= 0) {
                                Globals.getInstance().getCurrentRacun().getRacPozic().remove(poz);
                            } else {
                                poz.setRowDeleted(true);
                            }
                        }
                        Globals.getInstance().getCurrentRacun().posodobiZnesekIzNarocila();
                        Globals.getInstance().getCurrentRacun().preracunajVsote();
                        if (listener != null) listener.onItemDeleted();
                    })
                    .setNegativeButton("Prekliči", null)
                    .show();
        });

        // Prekliči
        btnCancelEdit.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelled();
        });

        // Shrani
        btnOkEdit.setOnClickListener(v -> {
            try {
                String kolStr = etKolicina.getText().toString().replace(",", ".").trim();
                double novaKol = kolStr.isEmpty() ? 1.0 : Double.parseDouble(kolStr);
                if (novaKol <= 0) {
                    Toast.makeText(context, "Količina mora biti večja od 0!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String epStr = etEP.getText().toString().replace(",", ".").trim();
                double novEP = epStr.isEmpty() ? 1.0 : Double.parseDouble(epStr);
                if (novEP <= 0) novEP = 1.0;

                String popStr = etPopustProcent.getText().toString().replace(",", ".").trim();
                BigDecimal novPopProc = popStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(popStr);
                if (novPopProc.compareTo(BigDecimal.ZERO) < 0) novPopProc = BigDecimal.ZERO;
                if (novPopProc.compareTo(BigDecimal.valueOf(100)) > 0) novPopProc = BigDecimal.valueOf(100);

                String novHod = etHod.getText().toString().trim();
                String novaOpomba = etOpomba.getText().toString().trim();

                if (isPaket && allPaketPozicije != null) {
                    // Posodobi vse pozicije paketa
                    double origPaketKol = initialKol > 0 ? initialKol : 1.0;
                    for (PozicijaTp p : allPaketPozicije) {
                        double kolicinaZaEnega = p.getKolicina() / origPaketKol;
                        p.setPaketKol(BigDecimal.valueOf(novaKol));
                        p.setKolicina(kolicinaZaEnega * novaKol);
                        p.setHod(novHod);
                        p.setDodatniOpis(novaOpomba);

                        double pEp = p.getEnotaProdajeId() != null && p.getEnotaProdajeId().compareTo(BigDecimal.ZERO) > 0
                                ? p.getEnotaProdajeId().doubleValue() : 1.0;
                        BigDecimal pPolna = p.getCena().multiply(BigDecimal.valueOf(p.getKolicina() * pEp)).setScale(2, RoundingMode.HALF_UP);

                        BigDecimal pPop = BigDecimal.ZERO;
                        if (novPopProc.compareTo(BigDecimal.ZERO) > 0) {
                            pPop = pPolna.multiply(novPopProc).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                            p.setCenaNabavna(novPopProc);
                            p.setZnesekPopust(pPop);
                        } else {
                            p.setCenaNabavna(BigDecimal.ZERO);
                            p.setZnesekPopust(BigDecimal.ZERO);
                        }
                        p.setZnesek(pPolna);

                        BigDecimal pNeto = pPolna.subtract(pPop);
                        if (p.getStopnjaDavka() > 0 && pNeto.compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal zd = pNeto.multiply(BigDecimal.valueOf(p.getStopnjaDavka()))
                                    .divide(BigDecimal.valueOf(100.0 + p.getStopnjaDavka()), 4, RoundingMode.HALF_UP);
                            p.setZnesekDavka(zd);
                        }
                    }
                } else {
                    // Posodobi enojno pozicijo
                    poz.setKolicina(novaKol);
                    poz.setEnotaProdajeId(BigDecimal.valueOf(novEP));
                    poz.setHod(novHod);
                    poz.setDodatniOpis(novaOpomba);

                    BigDecimal polna = poz.getCena().multiply(BigDecimal.valueOf(novaKol * novEP)).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal popZ = BigDecimal.ZERO;
                    if (novPopProc.compareTo(BigDecimal.ZERO) > 0) {
                        popZ = polna.multiply(novPopProc).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        poz.setCenaNabavna(novPopProc);
                        poz.setZnesekPopust(popZ);
                    } else {
                        poz.setCenaNabavna(BigDecimal.ZERO);
                        poz.setZnesekPopust(BigDecimal.ZERO);
                    }
                    poz.setZnesek(polna);

                    BigDecimal neto = polna.subtract(popZ);
                    if (poz.getStopnjaDavka() > 0 && neto.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal zd = neto.multiply(BigDecimal.valueOf(poz.getStopnjaDavka()))
                                .divide(BigDecimal.valueOf(100.0 + poz.getStopnjaDavka()), 4, RoundingMode.HALF_UP);
                        poz.setZnesekDavka(zd);
                    }
                }

                Globals.getInstance().getCurrentRacun().posodobiZnesekIzNarocila();
                Globals.getInstance().getCurrentRacun().preracunajVsote();
                dialog.dismiss();
                if (listener != null) listener.onItemUpdated();
            } catch (Exception e) {
                Toast.makeText(context, "Napaka pri shranjevanju: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private static void prikaziDodatkePicker(Context context, EditText etOpomba) {
        List<DodatekTp> dodatki = Globals.getInstance().getCachedDodatki();
        if (dodatki != null && !dodatki.isEmpty()) {
            odpriDodatkeDialog(context, dodatki, etOpomba);
            return;
        }

        AppPreferences prefs = new AppPreferences(context);
        String serverUrl = prefs.getServerUrl();
        String token = prefs.getToken();

        if (serverUrl == null || serverUrl.isEmpty() || token == null || token.isEmpty()) {
            prikaziPrivzeteDodatke(context, etOpomba);
            return;
        }

        new Thread(() -> {
            try {
                List<DodatekTp> loaded = RosKasaSoapClient.getDodatki(serverUrl, token);
                if (loaded != null && !loaded.isEmpty()) {
                    Globals.getInstance().setCachedDodatki(loaded);
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        odpriDodatkeDialog(context, loaded, etOpomba);
                    });
                } else {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        prikaziPrivzeteDodatke(context, etOpomba);
                    });
                }
            } catch (Exception e) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    prikaziPrivzeteDodatke(context, etOpomba);
                });
            }
        }).start();
    }

    private static void odpriDodatkeDialog(Context context, List<DodatekTp> list, EditText etOpomba) {
        String[] imena = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            imena[i] = list.get(i).getDodatekText();
        }

        new AlertDialog.Builder(context)
                .setTitle("Izberite opombo")
                .setItems(imena, (dialog, which) -> {
                    String selected = imena[which];
                    String obstojece = etOpomba.getText().toString().trim();
                    if (obstojece.isEmpty()) {
                        etOpomba.setText(selected);
                    } else {
                        etOpomba.setText(obstojece + "; " + selected);
                    }
                })
                .setNegativeButton("Prekliči", null)
                .show();
    }

    private static void prikaziPrivzeteDodatke(Context context, EditText etOpomba) {
        String[] privzeti = new String[]{
                "Brez ledu", "Z ledom", "Z limono", "Brez sladkorja",
                "Močno zapečeno", "Srednje pečeno", "Krvavo",
                "Brez čebule", "Dodatna porcija", "Za s seboj"
        };
        new AlertDialog.Builder(context)
                .setTitle("Izberite opombo")
                .setItems(privzeti, (dialog, which) -> {
                    String selected = privzeti[which];
                    String obstojece = etOpomba.getText().toString().trim();
                    if (obstojece.isEmpty()) {
                        etOpomba.setText(selected);
                    } else {
                        etOpomba.setText(obstojece + "; " + selected);
                    }
                })
                .setNegativeButton("Prekliči", null)
                .show();
    }
}
