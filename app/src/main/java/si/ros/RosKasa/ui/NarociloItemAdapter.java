package si.ros.RosKasa.ui;

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

    private List<NarociloItem> items = new ArrayList<>();

    public void setItems(List<NarociloItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
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
