package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import si.ros.RosKasa.models.PartnerTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class PartnerVnosDialog {

    public interface OnPartnerPotrjenListener {
        void onPartnerPotrjen(int partnerId, String naziv, String naslov, String davcna, String stNarocilnice, BigDecimal rabat);
    }

    public static void show(Context context, BigDecimal zaplacilo, int storitevId, OnPartnerPotrjenListener listener) {
        show(context, zaplacilo, storitevId, "", "", "", "", listener);
    }

    public static void show(Context context, BigDecimal zaplacilo, int storitevId,
                            String initNaziv, String initNaslov, String initDavcna, String initNarocilnica,
                            OnPartnerPotrjenListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_partner_vnos);
        dialog.setCancelable(true);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.94);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView tvZnesek = dialog.findViewById(R.id.tvPartnerZnesekSubtitle);
        if (zaplacilo != null) {
            tvZnesek.setText(String.format(Locale.getDefault(), "Znesek za plačilo: %.2f €", zaplacilo));
        } else {
            tvZnesek.setText("Opis / Partner računa");
        }

        EditText etNaziv = dialog.findViewById(R.id.etPartnerNaziv);
        EditText etNaslov = dialog.findViewById(R.id.etPartnerNaslov);
        EditText etDavcna = dialog.findViewById(R.id.etPartnerDavcna);
        EditText etNarocilnica = dialog.findViewById(R.id.etPartnerNarocilnica);

        if (initNaziv != null && !initNaziv.isEmpty()) etNaziv.setText(initNaziv);
        if (initNaslov != null && !initNaslov.isEmpty()) etNaslov.setText(initNaslov);
        if (initDavcna != null && !initDavcna.isEmpty()) etDavcna.setText(initDavcna);
        if (initNarocilnica != null && !initNarocilnica.isEmpty()) etNarocilnica.setText(initNarocilnica);
        Button btnSearch = dialog.findViewById(R.id.btnSearchPartner);
        ProgressBar pbLoading = dialog.findViewById(R.id.pbPartnerLoading);
        TextView tvPartnerResultsCount = dialog.findViewById(R.id.tvPartnerResultsCount);
        RecyclerView rvPartnerji = dialog.findViewById(R.id.rvPartnerji);
        Button btnCancel = dialog.findViewById(R.id.btnPartnerCancel);
        Button btnPotrdi = dialog.findViewById(R.id.btnPartnerPotrdi);

        final int[] selectedPartnerId = {0};
        final BigDecimal[] selectedRabat = {BigDecimal.ZERO};

        List<PartnerTp> partnerList = new ArrayList<>();
        PartnerAdapter adapter = new PartnerAdapter(partnerList, item -> {
            selectedPartnerId[0] = item.getPartnerId();
            selectedRabat[0] = item.getRabat();
            etNaziv.setText(item.getNaziv());
            etNaslov.setText(item.getPolniNaslov());
            etDavcna.setText(item.getDavcnaSt());
            btnPotrdi.setText("Potrdi: " + item.getNaziv());
            btnPotrdi.setEnabled(true);
        });

        rvPartnerji.setLayoutManager(new LinearLayoutManager(context));
        rvPartnerji.setAdapter(adapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppPreferences prefs = new AppPreferences(context);
        Globals globals = Globals.getInstance();

        Runnable performSearch = () -> {
            String query = etNaziv.getText() != null ? etNaziv.getText().toString().trim() : "";
            if (query.isEmpty() && etDavcna.getText() != null) {
                query = etDavcna.getText().toString().trim();
            }

            if (query.length() < 2 && storitevId != 8 && storitevId != 31 && storitevId != 90) {
                Toast.makeText(context, "Vnesite vsaj 2 znaka za iskanje partnerja!", Toast.LENGTH_SHORT).show();
                return;
            }

            pbLoading.setVisibility(View.VISIBLE);

            final String searchQuery = query;
            executor.execute(() -> {
                try {
                    String serverUrl = prefs.getServerUrl();
                    String token = prefs.getToken();

                    Integer pId = null;
                    try {
                        pId = Integer.parseInt(searchQuery);
                    } catch (Exception ignored) {}

                    Integer tipPartner = null;
                    if (storitevId == globals.getkKarticaTippartnerRocno()) {
                        tipPartner = storitevId;
                    } else if (storitevId == 8 || storitevId == 31 || storitevId == 90) {
                        tipPartner = storitevId;
                    } else if (storitevId == 5) {
                        tipPartner = 5;
                    }

                    Integer strMestoId = null;
                    if (globals.isReprezentancaPoFirmah()) {
                        strMestoId = globals.getTocilnicaId();
                    }

                    boolean neIsciPoDurs = !globals.isPartnerFurs();
                    if (storitevId == 8 || storitevId == 31 || storitevId == 90) {
                        neIsciPoDurs = true;
                    }

                    int racId = (globals.getCurrentRacun() != null) ? globals.getCurrentRacun().getRacunId() : 0;
                    globals.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                            "Partner iskanje: " + searchQuery + (racId > 0 ? " R:" + racId : ""),
                            globals.getTekocaOsebaId(), globals.getTocilnicaId());

                    List<PartnerTp> results = RosKasaSoapClient.getPartner(
                            serverUrl, token, (pId != null ? "" : searchQuery), pId, tipPartner, strMestoId, neIsciPoDurs
                    );

                    globals.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                            "Partner rezultat za iskanje: " + searchQuery + " zadetkov: " + (results != null ? results.size() : 0) + (racId > 0 ? " R:" + racId : ""),
                            globals.getTekocaOsebaId(), globals.getTocilnicaId());

                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        partnerList.clear();
                        if (results != null && !results.isEmpty()) {
                            partnerList.addAll(results);
                            adapter.notifyDataSetChanged();
                            rvPartnerji.setVisibility(View.VISIBLE);
                            if (tvPartnerResultsCount != null) {
                                tvPartnerResultsCount.setText("Najdeni partnerji (" + results.size() + ") - izberite s seznama:");
                                tvPartnerResultsCount.setVisibility(View.VISIBLE);
                            }
                        } else {
                            rvPartnerji.setVisibility(View.GONE);
                            if (tvPartnerResultsCount != null) {
                                tvPartnerResultsCount.setVisibility(View.GONE);
                            }
                            Toast.makeText(context, "Ni najdenih partnerjev za: " + searchQuery, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        if (tvPartnerResultsCount != null) {
                            tvPartnerResultsCount.setVisibility(View.GONE);
                        }
                        Toast.makeText(context, "Napaka pri iskanju partnerja: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        };

        btnSearch.setOnClickListener(v -> performSearch.run());

        etNaziv.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch.run();
                return true;
            }
            return false;
        });

        if ((initNaziv != null && initNaziv.trim().length() >= 2) || (initDavcna != null && initDavcna.trim().length() >= 3)) {
            mainHandler.post(performSearch);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnPotrdi.setOnClickListener(v -> {
            String naziv = etNaziv.getText() != null ? etNaziv.getText().toString().trim() : "";
            String naslov = etNaslov.getText() != null ? etNaslov.getText().toString().trim() : "";
            String davcna = etDavcna.getText() != null ? etDavcna.getText().toString().trim() : "";
            String stNaroc = etNarocilnica.getText() != null ? etNarocilnica.getText().toString().trim() : "";

            // Delphi FormPartner.pas kontrole:
            // ((edPartnernaziv.Text.Length>=3) and (edPartnerNaslov.Text.Length>4) and (edPartnerDavcna.Text.Length>6)) or selectedPartnerId > 0 or storitevId == 8
            boolean isDataValid = (naziv.length() >= 3 && naslov.length() > 4 && davcna.length() > 6)
                    || (selectedPartnerId[0] > 0 && naziv.length() >= 3)
                    || (storitevId == 8 && naziv.length() >= 3);

            if (!isDataValid) {
                Toast.makeText(context, "Podatki ne ustrezajo! Zahtevan je vnos: Naziv (vsaj 3 znaki), Naslov (vsaj 5 znakov) in Davčna št. (vsaj 7 znakov).", Toast.LENGTH_LONG).show();
                return;
            }

            int racId = (globals.getCurrentRacun() != null) ? globals.getCurrentRacun().getRacunId() : 0;
            globals.vpisiKronologijoDebugL1(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                    "Partner potrjen: " + selectedPartnerId[0] + " - " + naziv + " davčna: " + davcna + (racId > 0 ? " R:" + racId : ""),
                    globals.getTekocaOsebaId(), globals.getTocilnicaId());

            dialog.dismiss();
            if (listener != null) {
                listener.onPartnerPotrjen(selectedPartnerId[0], naziv, naslov, davcna, stNaroc, selectedRabat[0]);
            }
        });

        dialog.show();
    }

    private static class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.ViewHolder> {
        private final List<PartnerTp> items;
        private final OnItemClickListener clickListener;
        private int selectedPos = -1;

        interface OnItemClickListener {
            void onItemClick(PartnerTp item);
        }

        PartnerAdapter(List<PartnerTp> items, OnItemClickListener clickListener) {
            this.items = items;
            this.clickListener = clickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partner, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PartnerTp item = items.get(position);
            holder.tvNaziv.setText(item.getNaziv());
            holder.tvNaslov.setText(item.getPolniNaslov());
            holder.tvDavcna.setText("Davčna: " + item.getDavcnaSt());

            if (item.getRabat() != null && item.getRabat().compareTo(BigDecimal.ZERO) > 0) {
                holder.tvRabat.setText("Rabat: " + item.getRabat() + " %");
                holder.tvRabat.setVisibility(View.VISIBLE);
            } else {
                holder.tvRabat.setVisibility(View.GONE);
            }

            boolean isSel = (position == selectedPos);
            holder.itemView.setBackgroundResource(isSel ? R.drawable.bg_storno_item_selected : R.drawable.bg_storno_item_unselected);

            holder.itemView.setOnClickListener(v -> {
                int prev = selectedPos;
                selectedPos = holder.getAdapterPosition();
                if (prev >= 0) notifyItemChanged(prev);
                notifyItemChanged(selectedPos);
                clickListener.onItemClick(item);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNaziv, tvNaslov, tvDavcna, tvRabat;

            ViewHolder(View itemView) {
                super(itemView);
                tvNaziv = itemView.findViewById(R.id.tvPartnerNaziv);
                tvNaslov = itemView.findViewById(R.id.tvPartnerNaslov);
                tvDavcna = itemView.findViewById(R.id.tvPartnerDavcna);
                tvRabat = itemView.findViewById(R.id.tvPartnerRabat);
            }
        }
    }
}
