package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.StornoRazlogTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class StornoRazlogDialog {

    public interface OnStornoConfirmedListener {
        void onStornoConfirmed(Integer stornoRazlogId, String stornoRazlogNaziv, boolean novoNarocilo);
    }

    public static void show(@NonNull Context context, String serverUrl, String token, OnStornoConfirmedListener listener) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_storno_razlog, null);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        RecyclerView rvRazlogi = view.findViewById(R.id.rvStornoRazlogi);
        ProgressBar pbLoading = view.findViewById(R.id.pbStornoLoading);
        MaterialButton btnPreklici = view.findViewById(R.id.btnStornoPreklici);
        MaterialButton btnPotrdi = view.findViewById(R.id.btnStornoPotrdi);

        rvRazlogi.setLayoutManager(new LinearLayoutManager(context));

        List<StornoRazlogTp> items = new ArrayList<>();
        // Prva opcija: brez razloga (opcijsko)
        items.add(new StornoRazlogTp(0, "Brez navedbe razloga (opcijsko)"));

        // Dodaj predpomnjene ali privzete razloge
        List<StornoRazlogTp> cached = Globals.getInstance().getCachedStornoRazlogi();
        if (cached != null && !cached.isEmpty()) {
            items.addAll(cached);
        }

        RazlogAdapter adapter = new RazlogAdapter(items);
        rvRazlogi.setAdapter(adapter);

        btnPreklici.setOnClickListener(v -> dialog.dismiss());

        btnPotrdi.setOnClickListener(v -> {
            StornoRazlogTp selected = adapter.getSelectedItem();
            Integer razlogId = (selected != null && selected.getStornoRazlogId() > 0) ? selected.getStornoRazlogId() : null;
            String razlogNaziv = selected != null ? selected.getNaziv() : "";

            new AlertDialog.Builder(context)
                    .setTitle("Storno računa")
                    .setMessage("Ali naredimo nov račun na podlagi storno računa?")
                    .setPositiveButton("Da", (d, which) -> {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onStornoConfirmed(razlogId, razlogNaziv, true);
                        }
                    })
                    .setNegativeButton("Ne", (d, which) -> {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onStornoConfirmed(razlogId, razlogNaziv, false);
                        }
                    })
                    .show();
        });

        // Če še nimamo shranjenih razlogov v predpomnilniku, jih poskusi prenesti s strežnika
        if (!Globals.getInstance().hasCachedStornoRazlogi() && serverUrl != null && !serverUrl.isEmpty()) {
            pbLoading.setVisibility(View.VISIBLE);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                try {
                    List<StornoRazlogTp> remote = RosKasaSoapClient.getStornoRazlogi(serverUrl, token);
                    if (remote != null && !remote.isEmpty()) {
                        Globals.getInstance().setCachedStornoRazlogi(remote);
                        handler.post(() -> {
                            pbLoading.setVisibility(View.GONE);
                            items.clear();
                            items.add(new StornoRazlogTp(0, "Brez navedbe razloga (opcijsko)"));
                            items.addAll(remote);
                            adapter.notifyDataSetChanged();
                        });
                    } else {
                        handler.post(() -> pbLoading.setVisibility(View.GONE));
                    }
                } catch (Exception e) {
                    handler.post(() -> pbLoading.setVisibility(View.GONE));
                }
            });
        }

        dialog.show();
    }

    private static class RazlogAdapter extends RecyclerView.Adapter<RazlogAdapter.ViewHolder> {
        private final List<StornoRazlogTp> items;
        private int selectedIndex = 0; // Privzeto izbran prvi ("Brez navedbe razloga")

        public RazlogAdapter(List<StornoRazlogTp> items) {
            this.items = items;
        }

        public StornoRazlogTp getSelectedItem() {
            if (selectedIndex >= 0 && selectedIndex < items.size()) {
                return items.get(selectedIndex);
            }
            return null;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storno_razlog, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            StornoRazlogTp item = items.get(position);
            holder.tvNaziv.setText(item.getNaziv());
            boolean isSelected = (position == selectedIndex);
            holder.rbSelected.setChecked(isSelected);
            holder.itemView.setBackgroundResource(isSelected ? R.drawable.bg_storno_item_selected : R.drawable.bg_storno_item_unselected);

            holder.itemView.setOnClickListener(v -> {
                int oldIndex = selectedIndex;
                selectedIndex = holder.getAdapterPosition();
                if (oldIndex != selectedIndex) {
                    notifyItemChanged(oldIndex);
                    notifyItemChanged(selectedIndex);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            RadioButton rbSelected;
            TextView tvNaziv;

            ViewHolder(View itemView) {
                super(itemView);
                rbSelected = itemView.findViewById(R.id.rbSelected);
                tvNaziv = itemView.findViewById(R.id.tvRazlogNaziv);
            }
        }
    }
}
