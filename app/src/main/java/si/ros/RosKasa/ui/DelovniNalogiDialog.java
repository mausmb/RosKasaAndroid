package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.DelovniNalogTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class DelovniNalogiDialog {

    public interface OnDelovniNalogSelectedListener {
        void onSelected(DelovniNalogTp dn);
        void onRemoved();
    }

    public static void show(Context context, String currentDnId, OnDelovniNalogSelectedListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delovni_nalogi);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        EditText etSearchDn = dialog.findViewById(R.id.etSearchDn);
        RecyclerView rvDelovniNalogi = dialog.findViewById(R.id.rvDelovniNalogi);
        ProgressBar pbLoading = dialog.findViewById(R.id.pbLoading);
        TextView tvEmpty = dialog.findViewById(R.id.tvEmpty);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOdstraniDn = dialog.findViewById(R.id.btnOdstraniDn);

        List<DelovniNalogTp> allNalogi = new ArrayList<>();
        List<DelovniNalogTp> filteredNalogi = new ArrayList<>();

        DnAdapter adapter = new DnAdapter(filteredNalogi, item -> {
            dialog.dismiss();
            if (listener != null) listener.onSelected(item);
        });

        rvDelovniNalogi.setLayoutManager(new LinearLayoutManager(context));
        rvDelovniNalogi.setAdapter(adapter);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOdstraniDn.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onRemoved();
        });

        Runnable applyFilter = () -> {
            String query = etSearchDn.getText() != null ? etSearchDn.getText().toString().trim().toLowerCase(Locale.ROOT) : "";
            filteredNalogi.clear();
            if (query.isEmpty()) {
                filteredNalogi.addAll(allNalogi);
            } else {
                for (DelovniNalogTp dn : allNalogi) {
                    if (dn.getDnId().toLowerCase(Locale.ROOT).contains(query)
                            || dn.getNaziv().toLowerCase(Locale.ROOT).contains(query)
                            || dn.getPartnerNaziv().toLowerCase(Locale.ROOT).contains(query)
                            || dn.getStrMestoNaziv().toLowerCase(Locale.ROOT).contains(query)) {
                        filteredNalogi.add(dn);
                    }
                }
            }
            adapter.notifyDataSetChanged();
            tvEmpty.setVisibility(filteredNalogi.isEmpty() ? View.VISIBLE : View.GONE);
        };

        etSearchDn.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int count, int after) {
                applyFilter.run();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        pbLoading.setVisibility(View.VISIBLE);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppPreferences prefs = new AppPreferences(context);

        executor.execute(() -> {
            try {
                String serverUrl = prefs.getServerUrl();
                String token = prefs.getToken();
                List<DelovniNalogTp> loaded = RosKasaSoapClient.getDelovniNalogi(serverUrl, token);

                mainHandler.post(() -> {
                    pbLoading.setVisibility(View.GONE);
                    allNalogi.clear();
                    if (loaded != null) allNalogi.addAll(loaded);
                    applyFilter.run();
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    pbLoading.setVisibility(View.GONE);
                    Toast.makeText(context, "Napaka pri prenosu delovnih nalogov: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    tvEmpty.setVisibility(View.VISIBLE);
                });
            }
        });

        dialog.show();
    }

    private static class DnAdapter extends RecyclerView.Adapter<DnAdapter.ViewHolder> {
        private final List<DelovniNalogTp> items;
        private final OnItemClickListener listener;

        public interface OnItemClickListener {
            void onItemClick(DelovniNalogTp item);
        }

        public DnAdapter(List<DelovniNalogTp> items, OnItemClickListener listener) {
            this.items = items;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delovni_nalog, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DelovniNalogTp item = items.get(position);
            holder.tvDnNaziv.setText("ID: " + item.getDnId() + " - " + item.getNaziv());
            StringBuilder sb = new StringBuilder();
            if (!item.getStrMestoNaziv().isEmpty()) {
                sb.append("Strm: ").append(item.getStrMestoNaziv());
            }
            if (!item.getPartnerNaziv().isEmpty()) {
                if (sb.length() > 0) sb.append("  |  ");
                sb.append("Partner: ").append(item.getPartnerNaziv());
            }
            holder.tvDnPodrobnosti.setText(sb.toString());

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(item);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView tvDnNaziv;
            final TextView tvDnPodrobnosti;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDnNaziv = itemView.findViewById(R.id.tvDnNaziv);
                tvDnPodrobnosti = itemView.findViewById(R.id.tvDnPodrobnosti);
            }
        }
    }
}
