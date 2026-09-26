package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.Globals;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class SplitRacunDialog {

    public interface OnSplitCompleteListener {
        void onSplitComplete();
    }

    public static void show(Context context, RacunTp originalRacun, OnSplitCompleteListener listener) {
        if (originalRacun == null || originalRacun.getRacPozic() == null || originalRacun.getRacPozic().isEmpty()) {
            Toast.makeText(context, "Račun nima postavk za delitev!", Toast.LENGTH_SHORT).show();
            return;
        }

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_split_racun);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView tvOriginalLabel = dialog.findViewById(R.id.tvOriginalLabel);
        TextView tvOriginalZnesek = dialog.findViewById(R.id.tvOriginalZnesek);
        TextView tvNoviZnesek = dialog.findViewById(R.id.tvNoviZnesek);
        TextView tvEmptyOriginal = dialog.findViewById(R.id.tvEmptyOriginal);
        TextView tvEmptyNovi = dialog.findViewById(R.id.tvEmptyNovi);
        ProgressBar pbLoading = dialog.findViewById(R.id.pbLoading);
        RecyclerView rvOriginal = dialog.findViewById(R.id.rvOriginalPozicije);
        RecyclerView rvNovi = dialog.findViewById(R.id.rvNoviPozicije);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnPotrdiSplit = dialog.findViewById(R.id.btnPotrdiSplit);

        String origMarker = originalRacun.getMarker() != null && !originalRacun.getMarker().isEmpty()
                ? originalRacun.getMarker()
                : ("Račun #" + originalRacun.getRacunId());
        tvOriginalLabel.setText("Originalni račun (" + origMarker + " #" + originalRacun.getRacunId() + "):");

        List<PozicijaTp> sourceList = new ArrayList<>();
        for (PozicijaTp p : originalRacun.getRacPozic()) {
            if (!p.isRowDeleted() && p.getKolicina() > 0) {
                sourceList.add(p.deepCopy());
            }
        }

        List<PozicijaTp> targetList = new ArrayList<>();

        SplitPozicijeAdapter sourceAdapter = new SplitPozicijeAdapter(sourceList);
        SplitPozicijeAdapter targetAdapter = new SplitPozicijeAdapter(targetList);

        rvOriginal.setLayoutManager(new LinearLayoutManager(context));
        rvOriginal.setAdapter(sourceAdapter);

        rvNovi.setLayoutManager(new LinearLayoutManager(context));
        rvNovi.setAdapter(targetAdapter);

        Runnable updateSums = () -> {
            BigDecimal sumSource = BigDecimal.ZERO;
            for (PozicijaTp p : sourceList) {
                if (p.getZnesek() != null) sumSource = sumSource.add(p.getZnesek());
            }
            BigDecimal sumTarget = BigDecimal.ZERO;
            for (PozicijaTp p : targetList) {
                if (p.getZnesek() != null) sumTarget = sumTarget.add(p.getZnesek());
            }

            tvOriginalZnesek.setText(String.format(Locale.GERMAN, "%.2f €", sumSource));
            tvNoviZnesek.setText(String.format(Locale.GERMAN, "%.2f €", sumTarget));

            tvEmptyOriginal.setVisibility(sourceList.isEmpty() ? View.VISIBLE : View.GONE);
            tvEmptyNovi.setVisibility(targetList.isEmpty() ? View.VISIBLE : View.GONE);
        };

        sourceAdapter.setOnItemClickListener(pos -> {
            PozicijaTp sourcePoz = sourceList.get(pos);
            double kol = sourcePoz.getKolicina();

            PozicijaTp targetPoz = null;
            for (PozicijaTp tp : targetList) {
                if (tp.getPozicijaId() == sourcePoz.getPozicijaId()) {
                    targetPoz = tp;
                    break;
                }
            }

            if (targetPoz == null) {
                targetPoz = sourcePoz.deepCopy();
                targetPoz.setKolicina(1.0);
                targetPoz.recalculateZnesek();
                targetList.add(targetPoz);
            } else {
                targetPoz.setKolicina(targetPoz.getKolicina() + 1.0);
                targetPoz.recalculateZnesek();
            }

            if (kol <= 1.0) {
                sourceList.remove(pos);
            } else {
                sourcePoz.setKolicina(kol - 1.0);
                sourcePoz.recalculateZnesek();
            }

            sourceAdapter.notifyDataSetChanged();
            targetAdapter.notifyDataSetChanged();
            updateSums.run();
        });

        targetAdapter.setOnItemClickListener(pos -> {
            PozicijaTp targetPoz = targetList.get(pos);
            double kol = targetPoz.getKolicina();

            PozicijaTp sourcePoz = null;
            for (PozicijaTp sp : sourceList) {
                if (sp.getPozicijaId() == targetPoz.getPozicijaId()) {
                    sourcePoz = sp;
                    break;
                }
            }

            if (sourcePoz == null) {
                sourcePoz = targetPoz.deepCopy();
                sourcePoz.setKolicina(1.0);
                sourcePoz.recalculateZnesek();
                sourceList.add(sourcePoz);
            } else {
                sourcePoz.setKolicina(sourcePoz.getKolicina() + 1.0);
                sourcePoz.recalculateZnesek();
            }

            if (kol <= 1.0) {
                targetList.remove(pos);
            } else {
                targetPoz.setKolicina(kol - 1.0);
                targetPoz.recalculateZnesek();
            }

            sourceAdapter.notifyDataSetChanged();
            targetAdapter.notifyDataSetChanged();
            updateSums.run();
        });

        updateSums.run();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnPotrdiSplit.setOnClickListener(v -> {
            if (targetList.isEmpty()) {
                Toast.makeText(context, "Na novi račun morate prenesti vsaj eno postavko!", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder confirm = new AlertDialog.Builder(context);
            confirm.setTitle("Potrdi delitev računa");
            confirm.setMessage("Ali potrjujete delitev računa na dva računa?");
            confirm.setPositiveButton("Potrdi", (d, w) -> {
                pbLoading.setVisibility(View.VISIBLE);
                btnPotrdiSplit.setEnabled(false);

                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler mainHandler = new Handler(Looper.getMainLooper());
                AppPreferences prefs = new AppPreferences(context);

                executor.execute(() -> {
                    try {
                        String serverUrl = prefs.getServerUrl();
                        String token = prefs.getToken();
                        int mobileId = 1;
                        try { mobileId = Integer.parseInt(prefs.getMobileId()); } catch (Exception ignored) {}

                        // 1. Posodobi originalni račun (brisanje ali zmanjšanje prenesenih postavk)
                        RacunTp origUpdated = originalRacun.deepCopy();
                        List<PozicijaTp> origPositions = new ArrayList<>();

                        for (PozicijaTp origP : originalRacun.getRacPozic()) {
                            PozicijaTp remaining = null;
                            for (PozicijaTp sp : sourceList) {
                                if (sp.getPozicijaId() == origP.getPozicijaId()) {
                                    remaining = sp;
                                    break;
                                }
                            }
                            if (remaining != null && remaining.getKolicina() > 0) {
                                PozicijaTp copy = origP.deepCopy();
                                copy.setKolicina(remaining.getKolicina());
                                copy.recalculateZnesek();
                                copy.setStatusPoz(9);
                                copy.setOriginalObject(null);
                                origPositions.add(copy);
                            } else {
                                // Postavka je bila v celoti prenesena - brišemo jo iz originalnega računa
                                if (origP.getPozicijaId() > 0) {
                                    PozicijaTp copy = origP.deepCopy();
                                    copy.setRowDeleted(true);
                                    copy.setNeNarocaj(true);
                                    copy.setStatusPoz(9);
                                    copy.setOriginalObject(null);
                                    origPositions.add(copy);
                                }
                            }
                        }
                        origUpdated.setRacPozic(origPositions);

                        BigDecimal sumOrig = BigDecimal.ZERO;
                        for (PozicijaTp p : origPositions) {
                            if (!p.isRowDeleted() && p.getZnesek() != null) {
                                sumOrig = sumOrig.add(p.getZnesek());
                            }
                        }
                        origUpdated.setZnesek(sumOrig);
                        String curKoda = origUpdated.getfPrintKoda() != null ? origUpdated.getfPrintKoda() : "";
                        if (!curKoda.contains("SPLIT")) {
                            origUpdated.setfPrintKoda(curKoda.trim() + " SPLIT ");
                        }

                        RosKasaSoapClient.setRacun(serverUrl, token, mobileId, origUpdated);

                        // 2. Ustvari nov deljeni račun
                        RacunTp noviRacun = new RacunTp();
                        noviRacun.setRacunId(Globals.getNextNegativeRacunId());
                        String baseMarker = originalRacun.getMarker() != null && !originalRacun.getMarker().isEmpty()
                                ? originalRacun.getMarker()
                                : ("R" + originalRacun.getRacunId());
                        noviRacun.setMarker(baseMarker + "_1");
                        noviRacun.setStatus(1);
                        noviRacun.setTocilnicaId(originalRacun.getTocilnicaId() != null && originalRacun.getTocilnicaId() > 0 ? originalRacun.getTocilnicaId() : 512200);
                        noviRacun.setfPosId(originalRacun.getfPosId() != null && originalRacun.getfPosId() > 0 ? originalRacun.getfPosId() : 500);
                        noviRacun.setfPoslovniProstorId(originalRacun.getfPoslovniProstorId() != null && originalRacun.getfPoslovniProstorId() > 0 ? originalRacun.getfPoslovniProstorId() : 5000);
                        noviRacun.setKasiral(Globals.getInstance().getTekocaOsebaId() > 0 ? Globals.getInstance().getTekocaOsebaId() : (originalRacun.getKasiral() != null ? originalRacun.getKasiral() : 9999));
                        noviRacun.setTipRacuna(1);
                        noviRacun.setStPogrinjkov(1);
                        noviRacun.setStKopij(0);
                        noviRacun.setfPrintKoda(baseMarker + " " + originalRacun.getRacunId() + " ");
                        noviRacun.setOriginalObject(null);

                        List<PozicijaTp> noviPositions = new ArrayList<>();
                        int pId = -1;
                        BigDecimal sumNovi = BigDecimal.ZERO;
                        for (PozicijaTp tp : targetList) {
                            PozicijaTp np = tp.deepCopy();
                            np.setPozicijaId(pId--);
                            np.setRacunId(noviRacun.getRacunId());
                            np.setVerzijaZapisa(0);
                            np.setOriginalObject(null);
                            np.setRowDeleted(false);
                            np.setNeNarocaj(true);
                            if (originalRacun.getRacunId() > 0 && np.getStatus() != null && np.getStatus().compareTo(BigDecimal.ZERO) > 0) {
                                np.setStatus(BigDecimal.valueOf(np.getKolicina()));
                            } else {
                                np.setStatus(BigDecimal.ZERO);
                            }
                            np.setStatusPoz(0);
                            np.setNarociloPoslano(0);
                            noviPositions.add(np);
                            if (np.getZnesek() != null) sumNovi = sumNovi.add(np.getZnesek());
                        }
                        noviRacun.setRacPozic(noviPositions);
                        noviRacun.setZnesek(sumNovi);

                        RosKasaSoapClient.setRacun(serverUrl, token, mobileId, noviRacun);

                        Globals.getInstance().vpisiKronologijoDebugL0(serverUrl, token, String.valueOf(mobileId),
                                "SPLIT iz original R:" + originalRacun.getRacunId() + " v nov R z markerjem " + noviRacun.getMarker(),
                                Globals.getInstance().getTekocaOsebaId(), Globals.getInstance().getTocilnicaId());

                        mainHandler.post(() -> {
                            pbLoading.setVisibility(View.GONE);
                            Toast.makeText(context, "Račun je bil uspešno razdeljen!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            if (listener != null) listener.onSplitComplete();
                        });
                    } catch (Exception e) {
                        mainHandler.post(() -> {
                            pbLoading.setVisibility(View.GONE);
                            btnPotrdiSplit.setEnabled(true);
                            Toast.makeText(context, "Napaka pri delitvi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
            });
            confirm.setNegativeButton("Prekliči", null);
            confirm.show();
        });

        dialog.show();
    }

    private static class SplitPozicijeAdapter extends RecyclerView.Adapter<SplitPozicijeAdapter.ViewHolder> {
        private final List<PozicijaTp> items;
        private OnItemClickListener listener;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public SplitPozicijeAdapter(List<PozicijaTp> items) {
            this.items = items;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_split_pozicija, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PozicijaTp item = items.get(position);
            holder.tvNaziv.setText(item.getNaziv() != null && !item.getNaziv().isEmpty() ? item.getNaziv() : ("Artikel " + item.getNivo4Id()));
            String kolStr = String.format(Locale.GERMAN, "%.0fx", item.getKolicina());
            holder.tvKolicina.setText(kolStr);
            String znStr = item.getZnesek() != null ? String.format(Locale.GERMAN, "%.2f €", item.getZnesek()) : "0,00 €";
            holder.tvZnesek.setText(znStr);

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(position);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView tvNaziv;
            final TextView tvKolicina;
            final TextView tvZnesek;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvNaziv = itemView.findViewById(R.id.tvNaziv);
                tvKolicina = itemView.findViewById(R.id.tvKolicina);
                tvZnesek = itemView.findViewById(R.id.tvZnesek);
            }
        }
    }
}
