package si.ros.RosKasa.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import si.ros.RosKasa.R;
import si.ros.RosKasa.models.NarociloItem;

public class NarociloItemAdapter extends RecyclerView.Adapter<NarociloItemAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(NarociloItem item, int position);
    }

    private List<NarociloItem> items = new ArrayList<>();
    private int selectedPosition = -1;
    private OnItemClickListener listener;

    public NarociloItemAdapter() {}

    public NarociloItemAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<NarociloItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        if (this.items.isEmpty()) {
            selectedPosition = -1;
        } else if (selectedPosition < 0 || selectedPosition >= this.items.size()) {
            selectedPosition = this.items.size() - 1; // privzeto označi zadnjo dodano vrstico
        }
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public NarociloItem getSelectedItem() {
        if (selectedPosition >= 0 && selectedPosition < items.size()) {
            return items.get(selectedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_narocilo_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NarociloItem item = items.get(position);
        holder.tvNaziv.setText(item.getNaziv());
        holder.tvCena.setText(String.format("%.2f", item.getCena()));
        holder.tvKolicina.setText(String.format("%.1f", item.getKolicina()));
        holder.tvZnesek.setText(String.format("%.2f €", item.getZnesek()));

        // Vizualna označitev izbrane vrstice: moder poudarek #1976D2 proti privzetemu #2B2B2B
        boolean isSelected = (position == selectedPosition);
        holder.itemView.setBackgroundColor(isSelected ? Color.parseColor("#1976D2") : Color.parseColor("#2B2B2B"));

        holder.itemView.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                selectedPosition = currentPos;
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onItemClick(item, currentPos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNaziv, tvCena, tvKolicina, tvZnesek;

        ViewHolder(View itemView) {
            super(itemView);
            tvNaziv = itemView.findViewById(R.id.tvNaziv);
            tvCena = itemView.findViewById(R.id.tvCena);
            tvKolicina = itemView.findViewById(R.id.tvKolicina);
            tvZnesek = itemView.findViewById(R.id.tvZnesek);
        }
    }
}
