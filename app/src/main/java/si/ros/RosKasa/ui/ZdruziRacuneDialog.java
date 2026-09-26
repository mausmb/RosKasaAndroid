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
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.AppPreferences;
import si.ros.RosKasa.Globals;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.RacunSeznamItem;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class ZdruziRacuneDialog {

    public interface OnRacuniZdruzeniListener {
        void onZdruzeni();
    }

    public static void show(Context context, List<RacunSeznamItem> openRacuni, OnRacuniZdruzeniListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_zdruzi_racune);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        RecyclerView rvOdprtiRacuni = dialog.findViewById(R.id.rvOdprtiRacuni);
        ProgressBar pbLoading = dialog.findViewById(R.id.pbLoading);
        TextView tvEmpty = dialog.findViewById(R.id.tvEmpty);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnPotrdiZdruzi = dialog.findViewById(R.id.btnPotrdiZdruzi);

        List<RacunSeznamItem> validItems = new ArrayList<>();
        if (openRacuni != null) {
            for (RacunSeznamItem item : openRacuni) {
                if (item.getStatus() != null && item.getStatus() == 1 && item.getRacunId() > 0) {
                    validItems.add(item);
                }
            }
        }

        if (validItems.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        }

        Set<Integer> selectedRacunIds = new HashSet<>();

        ZdruziAdapter adapter = new ZdruziAdapter(validItems, selectedRacunIds);
        rvOdprtiRacuni.setLayoutManager(new LinearLayoutManager(context));
        rvOdprtiRacuni.setAdapter(adapter);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnPotrdiZdruzi.setOnClickListener(v -> {
            if (selectedRacunIds.size() < 2) {
                Toast.makeText(context, "Za združitev morate izbrati vsaj dva računa!", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder confirm = new AlertDialog.Builder(context);
            confirm.setTitle("Združi račune");
            confirm.setMessage("Ali res želite združiti " + selectedRacunIds.size() + " izbranih računov?");
            confirm.setPositiveButton("Združi", (d, w) -> {
                pbLoading.setVisibility(View.VISIBLE);
                btnPotrdiZdruzi.setEnabled(false);

                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler mainHandler = new Handler(Looper.getMainLooper());
                AppPreferences prefs = new AppPreferences(context);

                executor.execute(() -> {
                    try {
                        String serverUrl = prefs.getServerUrl();
                        String token = prefs.getToken();
                        Globals g = Globals.getInstance();
                        int osebaId = g.getTekocaOsebaId() > 0 ? g.getTekocaOsebaId() : 1;

                        List<Integer> listIds = new ArrayList<>(selectedRacunIds);
                        StringBuilder sbMarker = new StringBuilder("Z: ");
                        for (int i = 0; i < listIds.size(); i++) {
                            if (i > 0) sbMarker.append("/");
                            sbMarker.append(listIds.get(i));
                        }
                        String marker = sbMarker.toString();
                        if (marker.length() > 19) {
                            marker = marker.substring(0, 19);
                        }

                        String fault = RosKasaSoapClient.zdruziRacune(serverUrl, token, osebaId, listIds, marker);

                        mainHandler.post(() -> {
                            pbLoading.setVisibility(View.GONE);
                            if (fault != null && !fault.isEmpty()) {
                                Toast.makeText(context, "Napaka pri združitvi: " + fault, Toast.LENGTH_LONG).show();
                                btnPotrdiZdruzi.setEnabled(true);
                            } else {
                                Toast.makeText(context, "Računi so bili uspešno združeni!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                if (listener != null) listener.onZdruzeni();
                            }
                        });
                    } catch (Exception e) {
                        mainHandler.post(() -> {
                            pbLoading.setVisibility(View.GONE);
                            btnPotrdiZdruzi.setEnabled(true);
                            Toast.makeText(context, "Napaka: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                });
            });
            confirm.setNegativeButton("Prekliči", null);
            confirm.show();
        });

        dialog.show();
    }

    private static class ZdruziAdapter extends RecyclerView.Adapter<ZdruziAdapter.ViewHolder> {
        private final List<RacunSeznamItem> items;
        private final Set<Integer> selectedIds;

        public ZdruziAdapter(List<RacunSeznamItem> items, Set<Integer> selectedIds) {
            this.items = items;
            this.selectedIds = selectedIds;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zdruzi_racun, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RacunSeznamItem item = items.get(position);
            int racunId = item.getRacunId();
            String marker = item.getMarker() != null && !item.getMarker().isEmpty() ? item.getMarker() : ("Račun #" + racunId);

            holder.tvMarkerInId.setText(marker + "  (#" + racunId + ")");
            holder.tvDatumInUra.setText("Status: " + item.getStatusDescription());
            holder.tvZnesek.setText(item.getFormattedZnesek());

            boolean isChecked = selectedIds.contains(racunId);
            holder.cbIzbran.setChecked(isChecked);

            holder.itemView.setOnClickListener(v -> {
                if (selectedIds.contains(racunId)) {
                    selectedIds.remove(racunId);
                    holder.cbIzbran.setChecked(false);
                } else {
                    selectedIds.add(racunId);
                    holder.cbIzbran.setChecked(true);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final CheckBox cbIzbran;
            final TextView tvMarkerInId;
            final TextView tvDatumInUra;
            final TextView tvZnesek;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cbIzbran = itemView.findViewById(R.id.cbIzbran);
                tvMarkerInId = itemView.findViewById(R.id.tvMarkerInId);
                tvDatumInUra = itemView.findViewById(R.id.tvDatumInUra);
                tvZnesek = itemView.findViewById(R.id.tvZnesek);
            }
        }
    }
}
