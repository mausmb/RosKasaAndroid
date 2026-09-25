package si.ros.RosKasa.ui;

import android.graphics.Color;
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

public class MizeAdapter extends RecyclerView.Adapter<MizeAdapter.ViewHolder> {

    public static class MizaItem {
        public String naziv;
        public boolean isOccupied;
        public BigDecimal znesek;
        public int racunId;
        public int kasiralOsebaId;
        public String kasiralNaziv = "";
        public String kasiralInicialke = "";
        public boolean isMyTable = false;

        public MizaItem(String naziv, boolean isOccupied, BigDecimal znesek, int racunId) {
            this(naziv, isOccupied, znesek, racunId, 0, "", "", false);
        }

        public MizaItem(String naziv, boolean isOccupied, BigDecimal znesek, int racunId,
                        int kasiralOsebaId, String kasiralNaziv, String kasiralInicialke, boolean isMyTable) {
            this.naziv = naziv;
            this.isOccupied = isOccupied;
            this.znesek = znesek;
            this.racunId = racunId;
            this.kasiralOsebaId = kasiralOsebaId;
            this.kasiralNaziv = kasiralNaziv != null ? kasiralNaziv : "";
            this.kasiralInicialke = kasiralInicialke != null ? kasiralInicialke : "";
            this.isMyTable = isMyTable;
        }
    }

    public interface OnMizaClickListener {
        void onMizaClick(MizaItem miza);
    }

    private final List<MizaItem> mizeList = new ArrayList<>();
    private final OnMizaClickListener listener;

    public MizeAdapter(OnMizaClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<MizaItem> items) {
        mizeList.clear();
        if (items != null) {
            mizeList.addAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_miza, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MizaItem item = mizeList.get(position);
        holder.tvMizaName.setText(item.naziv);

        if (item.isOccupied) {
            holder.tvMizaName.setTextColor(Color.WHITE);

            // Barva glede na to, ali je mizo odprl trenutno prijavljeni natakar ali drug natakar
            if (item.isMyTable) {
                holder.itemView.setBackgroundColor(Color.parseColor("#1565C0")); // Modra - moja miza
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#B71C1C")); // Rdeča - zasedena miza drugega
            }

            if (item.znesek != null && item.znesek.compareTo(BigDecimal.ZERO) > 0) {
                holder.tvMizaZnesek.setText(String.format(Locale.getDefault(), "%.2f €", item.znesek));
                holder.tvMizaZnesek.setVisibility(View.VISIBLE);
            } else {
                holder.tvMizaZnesek.setVisibility(View.GONE);
            }

            String natakarStr = !item.kasiralInicialke.isEmpty() ? item.kasiralInicialke : item.kasiralNaziv;
            if (!natakarStr.isEmpty()) {
                holder.tvMizaNatakar.setText("👤 " + natakarStr);
                holder.tvMizaNatakar.setVisibility(View.VISIBLE);
            } else {
                holder.tvMizaNatakar.setVisibility(View.GONE);
            }
        } else {
            holder.tvMizaName.setTextColor(Color.parseColor("#ECEFF1"));
            holder.itemView.setBackgroundColor(Color.parseColor("#37474F"));
            holder.tvMizaZnesek.setVisibility(View.GONE);
            holder.tvMizaNatakar.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMizaClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return mizeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMizaName;
        TextView tvMizaZnesek;
        TextView tvMizaNatakar;

        ViewHolder(View itemView) {
            super(itemView);
            tvMizaName = itemView.findViewById(R.id.tvMizaName);
            tvMizaZnesek = itemView.findViewById(R.id.tvMizaZnesek);
            tvMizaNatakar = itemView.findViewById(R.id.tvMizaNatakar);
        }
    }
}
