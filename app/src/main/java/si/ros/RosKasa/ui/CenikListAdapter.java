package si.ros.RosKasa.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import si.ros.RosKasa.R;

public class CenikListAdapter extends RecyclerView.Adapter<CenikListAdapter.ViewHolder> {

    public static class CenikItem {
        public int nivo4Id;
        public String naziv;
        public String plu;
        public BigDecimal cena;
        public int nacinProdaje;
        public double polnjenje;
        public int em;
        public int paket;
        public int nivo1Id;
        public int tarifaId;
        public int izvorStrmId;
        public double davekProc;

        public CenikItem(int nivo4Id, String naziv, String plu, BigDecimal cena, int nacinProdaje, double polnjenje) {
            this.nivo4Id = nivo4Id;
            this.naziv = naziv;
            this.plu = plu;
            this.cena = cena;
            this.nacinProdaje = nacinProdaje;
            this.polnjenje = polnjenje;
            this.em = 1;
            this.paket = 0;
            this.nivo1Id = 1;
            this.tarifaId = 1;
            this.izvorStrmId = 1;
            this.davekProc = 22.0;
        }

        public String getFormattedNaziv() {
            String res = naziv != null ? naziv : "";
            if (plu != null && !plu.trim().isEmpty()) {
                String formattedPlu = String.format(Locale.getDefault(), "%05d", Integer.parseInt(plu.trim()));
                res += " [" + formattedPlu + "]";
            }
            switch (nacinProdaje) {
                case 1:
                case 3:
                case 4:
                case 9:
                case 10:
                    res += " (0,1/0,125/0,5/1)";
                    break;
                case 2:
                    res += " (0,03/0,05)";
                    break;
            }
            return res;
        }
    }

    public interface OnCenikItemClickListener {
        void onItemClick(CenikItem item);
    }

    private final List<CenikItem> allOriginalItems = new ArrayList<>();
    private final List<CenikItem> items = new ArrayList<>();
    private final OnCenikItemClickListener listener;

    public CenikListAdapter(OnCenikItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<CenikItem> newItems) {
        allOriginalItems.clear();
        items.clear();
        if (newItems != null) {
            allOriginalItems.addAll(newItems);
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    public void filter(String query) {
        items.clear();
        if (query == null || query.trim().isEmpty()) {
            items.addAll(allOriginalItems);
        } else {
            String q = query.trim().toLowerCase(Locale.getDefault());
            for (CenikItem item : allOriginalItems) {
                String formatted = item.getFormattedNaziv().toLowerCase(Locale.getDefault());
                String rawNaziv = item.naziv != null ? item.naziv.toLowerCase(Locale.getDefault()) : "";
                String pluCode = item.plu != null ? item.plu : "";
                if (formatted.contains(q) || rawNaziv.contains(q) || pluCode.contains(q)) {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cenik_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CenikItem item = items.get(position);
        holder.tvNaziv.setText(item.getFormattedNaziv());
        holder.tvCena.setText(String.format(Locale.getDefault(), "%.2f €", item.cena));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNaziv;
        TextView tvCena;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNaziv = itemView.findViewById(R.id.tvCenikNaziv);
            tvCena = itemView.findViewById(R.id.tvCenikCena);
        }
    }
}
