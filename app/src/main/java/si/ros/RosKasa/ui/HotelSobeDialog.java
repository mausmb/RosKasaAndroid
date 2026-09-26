package si.ros.RosKasa.ui;

import android.app.Activity;
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
import si.ros.RosKasa.MainActivity;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.KartprijTp;
import si.ros.RosKasa.nfc.NfcHelper;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class HotelSobeDialog {

    public interface OnHotelSobaSelectedListener {
        void onHotelSobaSelected(KartprijTp selectedRoom);
    }

    public static void show(Context context, BigDecimal zaplacilo, OnHotelSobaSelectedListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_hotel_sobe);
        dialog.setCancelable(true);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.92);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView tvZnesek = dialog.findViewById(R.id.tvHotelZnesekSubtitle);
        if (zaplacilo != null) {
            tvZnesek.setText(String.format(Locale.getDefault(), "Znesek bremenitve: %.2f €", zaplacilo));
        }

        EditText etSearch = dialog.findViewById(R.id.etSearchSobe);
        Button btnSearch = dialog.findViewById(R.id.btnSearchSobe);
        Button btnSearchDest = dialog.findViewById(R.id.btnSearchDestinacija);
        ProgressBar pbLoading = dialog.findViewById(R.id.pbSobeLoading);
        TextView tvEmptyMessage = dialog.findViewById(R.id.tvEmptySobeMessage);
        RecyclerView rvSobe = dialog.findViewById(R.id.rvSobe);
        Button btnCancel = dialog.findViewById(R.id.btnSobeCancel);
        Button btnPotrdi = dialog.findViewById(R.id.btnSobePotrdi);

        Globals globals = Globals.getInstance();
        if (globals.getHisDestinacija() > 0) {
            btnSearchDest.setVisibility(View.VISIBLE);
        }

        List<KartprijTp> sobeList = new ArrayList<>();
        final KartprijTp[] selectedItem = {null};

        SobeAdapter adapter = new SobeAdapter(sobeList, item -> {
            if (item.isBlokadaHK()) {
                Toast.makeText(context, "Soba / gost ima blokado hotel kredit (Blokada HK)!", Toast.LENGTH_SHORT).show();
                selectedItem[0] = null;
                btnPotrdi.setEnabled(false);
            } else {
                selectedItem[0] = item;
                btnPotrdi.setEnabled(true);
                String guest = (item.getImeGosta() != null && !item.getImeGosta().isEmpty()) ? (" - " + item.getImeGosta()) : "";
                btnPotrdi.setText("Potrdi (Soba " + item.getProstorId() + guest + ")");
            }
        });

        rvSobe.setLayoutManager(new LinearLayoutManager(context));
        rvSobe.setAdapter(adapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppPreferences prefs = new AppPreferences(context);

        Runnable performSearch = () -> {
            String query = etSearch.getText() != null ? etSearch.getText().toString().trim() : "";
            if (query.isEmpty()) {
                Toast.makeText(context, "Vnesite številko sobe ali priimek za iskanje!", Toast.LENGTH_SHORT).show();
                return;
            }

            pbLoading.setVisibility(View.VISIBLE);
            tvEmptyMessage.setVisibility(View.GONE);
            rvSobe.setVisibility(View.GONE);
            selectedItem[0] = null;
            btnPotrdi.setEnabled(false);

            executor.execute(() -> {
                try {
                    String serverUrl = prefs.getServerUrl();
                    String token = prefs.getToken();
                    int obratId = globals.getHisObrat();
                    int destinacijaId = globals.getHisDestinacija();
                    String hisObrati = globals.getHisObrati();
                    int racId = (globals.getCurrentRacun() != null) ? globals.getCurrentRacun().getRacunId() : 0;
                    globals.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                            "Hotel kredit iskanje: " + query + (racId > 0 ? " R:" + racId : ""),
                            globals.getTekocaOsebaId(), globals.getTocilnicaId());

                    List<KartprijTp> results = RosKasaSoapClient.getKartprij(
                            serverUrl, token, query, obratId, destinacijaId, hisObrati, false
                    );

                    globals.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                            "Hotel kredit rezultat za iskanje: " + query + " zadetkov: " + (results != null ? results.size() : 0) + (racId > 0 ? " R:" + racId : ""),
                            globals.getTekocaOsebaId(), globals.getTocilnicaId());

                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        sobeList.clear();
                        if (results != null && !results.isEmpty()) {
                            sobeList.addAll(results);
                            adapter.notifyDataSetChanged();
                            rvSobe.setVisibility(View.VISIBLE);
                            tvEmptyMessage.setVisibility(View.GONE);
                        } else {
                            rvSobe.setVisibility(View.GONE);
                            tvEmptyMessage.setVisibility(View.VISIBLE);
                            tvEmptyMessage.setText("Ni najdenih aktivnih sob za: \"" + query + "\"");
                        }
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        tvEmptyMessage.setVisibility(View.VISIBLE);
                        tvEmptyMessage.setText("Napaka pri iskanju sob: " + e.getMessage());
                        Toast.makeText(context, "Napaka SOAP getKartprij: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        };

        btnSearch.setOnClickListener(v -> performSearch.run());

        btnSearchDest.setOnClickListener(v -> {
            String query = etSearch.getText() != null ? etSearch.getText().toString().trim() : "";
            if (query.isEmpty()) {
                Toast.makeText(context, "Vnesite številko sobe ali priimek za iskanje!", Toast.LENGTH_SHORT).show();
                return;
            }

            pbLoading.setVisibility(View.VISIBLE);
            tvEmptyMessage.setVisibility(View.GONE);
            rvSobe.setVisibility(View.GONE);
            selectedItem[0] = null;
            btnPotrdi.setEnabled(false);

            int racId = (globals.getCurrentRacun() != null) ? globals.getCurrentRacun().getRacunId() : 0;
            globals.vpisiKronologijoDebugL1(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                    "Hotel kredit iskanje : " + globals.getHisDestinacija() + " Iskanje: " + query + (racId > 0 ? " R:" + racId : ""),
                    globals.getTekocaOsebaId(), globals.getTocilnicaId());

            executor.execute(() -> {
                try {
                    String serverUrl = prefs.getServerUrl();
                    String token = prefs.getToken();
                    int obratId = globals.getHisObrat();
                    Integer destinacijaId = globals.getHisDestinacija();
                    String hisObrati = globals.getHisObrati();

                    List<KartprijTp> results = RosKasaSoapClient.getKartprij(
                            serverUrl, token, query, obratId, destinacijaId, hisObrati, true
                    );

                    globals.vpisiKronologijoDebugL1(serverUrl, token, prefs.getMobileId(),
                            "Hotel kredit rezultat za iskanje (destinacija): " + query + " zadetkov: " + (results != null ? results.size() : 0) + (racId > 0 ? " R:" + racId : ""),
                            globals.getTekocaOsebaId(), globals.getTocilnicaId());

                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        sobeList.clear();
                        if (results != null && !results.isEmpty()) {
                            sobeList.addAll(results);
                            adapter.notifyDataSetChanged();
                            rvSobe.setVisibility(View.VISIBLE);
                            tvEmptyMessage.setVisibility(View.GONE);
                        } else {
                            rvSobe.setVisibility(View.GONE);
                            tvEmptyMessage.setVisibility(View.VISIBLE);
                            tvEmptyMessage.setText("Ni najdenih aktivnih sob na destinaciji za: \"" + query + "\"");
                        }
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        tvEmptyMessage.setVisibility(View.VISIBLE);
                        tvEmptyMessage.setText("Napaka pri iskanju sob: " + e.getMessage());
                    });
                }
            });
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch.run();
                return true;
            }
            return false;
        });

        // NFC Listener Registration on MainActivity
        if (context instanceof MainActivity) {
            MainActivity mainAct = (MainActivity) context;
            mainAct.setOnNfcTagReadListener(cardInfo -> {
                if (cardInfo != null && dialog.isShowing()) {
                    mainHandler.post(() -> {
                        String code = (cardInfo.decimalId != null && !cardInfo.decimalId.isEmpty())
                                ? cardInfo.decimalId
                                : (cardInfo.hexId != null ? cardInfo.hexId : "");
                        etSearch.setText(code);
                        Toast.makeText(context, "NFC kartica zaznana: " + code, Toast.LENGTH_SHORT).show();
                        performSearch.run();
                    });
                }
            });

            dialog.setOnDismissListener(d -> {
                mainAct.setOnNfcTagReadListener(null);
            });
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnPotrdi.setOnClickListener(v -> {
            if (selectedItem[0] != null) {
                if (selectedItem[0].isBlokadaHK()) {
                    Toast.makeText(context, "Izbrana soba ima blokado hotel kredit!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int racId = (globals.getCurrentRacun() != null) ? globals.getCurrentRacun().getRacunId() : 0;
                globals.vpisiKronologijoDebugL1(prefs.getServerUrl(), prefs.getToken(), prefs.getMobileId(),
                        "Hotel kredit izbira sobe: Soba " + selectedItem[0].getProstorId() + " prijava: " + selectedItem[0].getPrijavaId() + " gost: " + selectedItem[0].getImeGosta() + (racId > 0 ? " R:" + racId : ""),
                        globals.getTekocaOsebaId(), globals.getTocilnicaId());

                dialog.dismiss();
                if (listener != null) {
                    listener.onHotelSobaSelected(selectedItem[0]);
                }
            }
        });

        dialog.show();
    }

    private static class SobeAdapter extends RecyclerView.Adapter<SobeAdapter.ViewHolder> {
        private final List<KartprijTp> items;
        private final OnItemClickListener clickListener;
        private int selectedPos = -1;

        interface OnItemClickListener {
            void onItemClick(KartprijTp item);
        }

        SobeAdapter(List<KartprijTp> items, OnItemClickListener clickListener) {
            this.items = items;
            this.clickListener = clickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_soba, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            KartprijTp item = items.get(position);
            holder.tvRoom.setText("Soba: " + item.getProstorId());
            holder.tvObrat.setText(item.getNazivStrm());
            holder.tvGuest.setText(item.getImeGosta());
            holder.tvService.setText(item.getNazivStoritve());

            if (item.isBlokadaHK()) {
                holder.tvStatusTag.setText("(Blokada HK)");
                holder.tvStatusTag.setVisibility(View.VISIBLE);
                holder.tvStatusTag.setTextColor(0xFFFF5252);
            } else if (item.isVOdhodu()) {
                holder.tvStatusTag.setText("(v odhodu)");
                holder.tvStatusTag.setVisibility(View.VISIBLE);
                holder.tvStatusTag.setTextColor(0xFFFFD54F);
            } else {
                holder.tvStatusTag.setVisibility(View.GONE);
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
            TextView tvRoom, tvObrat, tvGuest, tvStatusTag, tvService;

            ViewHolder(View itemView) {
                super(itemView);
                tvRoom = itemView.findViewById(R.id.tvRoomNumber);
                tvObrat = itemView.findViewById(R.id.tvObratNaziv);
                tvGuest = itemView.findViewById(R.id.tvGuestName);
                tvStatusTag = itemView.findViewById(R.id.tvRoomStatusTag);
                tvService = itemView.findViewById(R.id.tvServiceName);
            }
        }
    }
}
