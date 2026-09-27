package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.LojalnostnaTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class LojalnostPopustDialog {

    private static final String TAG = "LojalnostPopustDialog";

    public interface OnLojalnostSelectedListener {
        void onSelected(LojalnostnaTp lojalnost);
        void onRemoved();
    }

    public static void show(@NonNull Context context, String serverUrl, String token, Integer currentLojalnostId, OnLojalnostSelectedListener listener) {
        Dialog dialog = new Dialog(context, R.style.DialogFullScreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_lojalnost_popust);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        TextView tvTrenutniLojalnost = dialog.findViewById(R.id.tvTrenutniLojalnost);
        EditText etSearchLojalnost = dialog.findViewById(R.id.etSearchLojalnost);
        RecyclerView rvLojalnostna = dialog.findViewById(R.id.rvLojalnostna);
        ProgressBar pbLoading = dialog.findViewById(R.id.pbLoading);
        TextView tvEmpty = dialog.findViewById(R.id.tvEmpty);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOdstraniLojalnost = dialog.findViewById(R.id.btnOdstraniLojalnost);

        if (currentLojalnostId != null && currentLojalnostId > 0) {
            tvTrenutniLojalnost.setText("Trenutno uveljavljen lojalnostni razred: " + currentLojalnostId);
            tvTrenutniLojalnost.setVisibility(View.VISIBLE);
            btnOdstraniLojalnost.setVisibility(View.VISIBLE);
        } else {
            tvTrenutniLojalnost.setVisibility(View.GONE);
            btnOdstraniLojalnost.setVisibility(View.GONE);
        }

        List<LojalnostnaTp> allItems = new ArrayList<>();
        List<LojalnostnaTp> filteredItems = new ArrayList<>();

        LojalnostAdapter adapter = new LojalnostAdapter(filteredItems, item -> {
            dialog.dismiss();
            if (listener != null) listener.onSelected(item);
        });

        rvLojalnostna.setLayoutManager(new LinearLayoutManager(context));
        rvLojalnostna.setAdapter(adapter);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnOdstraniLojalnost.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onRemoved();
        });

        Runnable applyFilter = () -> {
            String query = etSearchLojalnost.getText() != null ? etSearchLojalnost.getText().toString().trim().toLowerCase(Locale.ROOT) : "";
            filteredItems.clear();
            if (query.isEmpty()) {
                filteredItems.addAll(allItems);
            } else {
                for (LojalnostnaTp item : allItems) {
                    if (String.valueOf(item.getBonitetniRazred()).contains(query)
                            || (item.getNaziv() != null && item.getNaziv().toLowerCase(Locale.ROOT).contains(query))) {
                        filteredItems.add(item);
                    }
                }
            }
            adapter.notifyDataSetChanged();
            tvEmpty.setVisibility(filteredItems.isEmpty() ? View.VISIBLE : View.GONE);
        };

        etSearchLojalnost.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int count, int after) {
                applyFilter.run();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        if (Globals.getInstance().hasCachedLojalnostna()) {
            allItems.addAll(Globals.getInstance().getCachedLojalnostna());
            applyFilter.run();
        } else if (serverUrl != null && !serverUrl.isEmpty()) {
            pbLoading.setVisibility(View.VISIBLE);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                try {
                    List<LojalnostnaTp> loaded = RosKasaSoapClient.getLojalnostna(serverUrl, token);
                    if (loaded != null && !loaded.isEmpty()) {
                        Globals.getInstance().setCachedLojalnostna(loaded);
                    }
                    handler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        allItems.clear();
                        if (loaded != null) {
                            allItems.addAll(loaded);
                        }
                        applyFilter.run();
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Napaka pri prenosu lojalnostnih popustov: " + e.getMessage(), e);
                    handler.post(() -> {
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(context, "Napaka pri pridobivanju lojalnostnih popustov: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        applyFilter.run();
                    });
                }
            });
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }

        dialog.show();
    }

    private static class LojalnostAdapter extends RecyclerView.Adapter<LojalnostAdapter.ViewHolder> {
        private final List<LojalnostnaTp> items;
        private final OnItemClickListener listener;

        interface OnItemClickListener {
            void onItemClick(LojalnostnaTp item);
        }

        LojalnostAdapter(List<LojalnostnaTp> items, OnItemClickListener listener) {
            this.items = items;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lojalnost_popust, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LojalnostnaTp item = items.get(position);
            holder.tvRazredId.setText(String.valueOf(item.getBonitetniRazred()));
            holder.tvRazredNaziv.setText(item.getNaziv() != null ? item.getNaziv() : "");
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(item);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView tvRazredId;
            final TextView tvRazredNaziv;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvRazredId = itemView.findViewById(R.id.tvRazredId);
                tvRazredNaziv = itemView.findViewById(R.id.tvRazredNaziv);
            }
        }
    }
}
