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

        public MizaItem(String naziv, boolean isOccupied, BigDecimal znesek, int racunId) {
            this.naziv = naziv;
            this.isOccupied = isOccupied;
            this.znesek = znesek;
            this.racunId = racunId;
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
            holder.itemView.setBackgroundColor(Color.parseColor("#B71C1C"));
            if (item.znesek != null && item.znesek.compareTo(BigDecimal.ZERO) > 0) {
                holder.tvMizaZnesek.setText(String.format(Locale.getDefault(), "%.2f €", item.znesek));
                holder.tvMizaZnesek.setVisibility(View.VISIBLE);
            } else {
                holder.tvMizaZnesek.setVisibility(View.GONE);
            }
        } else {
            holder.tvMizaName.setTextColor(Color.parseColor("#ECEFF1"));
            holder.itemView.setBackgroundColor(Color.parseColor("#37474F"));
            holder.tvMizaZnesek.setVisibility(View.GONE);
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

        ViewHolder(View itemView) {
            super(itemView);
            tvMizaName = itemView.findViewById(R.id.tvMizaName);
            tvMizaZnesek = itemView.findViewById(R.id.tvMizaZnesek);
        }
    }
}
