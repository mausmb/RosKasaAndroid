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
import si.ros.RosKasa.models.RacunSeznamItem;

public class RacunSeznamAdapter extends RecyclerView.Adapter<RacunSeznamAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(RacunSeznamItem item, int position);
        void onItemDoubleClick(RacunSeznamItem item, int position);
    }

    private List<RacunSeznamItem> items = new ArrayList<>();
    private int selectedPosition = -1;
    private OnItemClickListener listener;
    private long lastClickTime = 0;

    public RacunSeznamAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<RacunSeznamItem> newItems) {
        this.items = newItems != null ? newItems : new ArrayList<>();
        this.selectedPosition = -1;
        notifyDataSetChanged();
    }

    public RacunSeznamItem getSelectedItem() {
        if (selectedPosition >= 0 && selectedPosition < items.size()) {
            return items.get(selectedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_racun_seznam, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RacunSeznamItem item = items.get(position);
        holder.tvRacunId.setText("Račun #" + item.getRacunId());
        holder.tvMarker.setText("Miza: " + (item.getMarker() != null ? item.getMarker() : "-"));
        holder.tvZnesek.setText(item.getFormattedZnesek());
        holder.tvStatus.setText(item.getStatusDescription());

        boolean isSelected = (position == selectedPosition);
        holder.itemView.setBackgroundColor(isSelected ? Color.parseColor("#1976D2") : Color.parseColor("#333333"));

        holder.itemView.setOnClickListener(v -> {
            long clickTime = System.currentTimeMillis();
            int currentPos = holder.getAdapterPosition();
            if (currentPos == RecyclerView.NO_POSITION) return;

            selectedPosition = currentPos;
            notifyDataSetChanged();

            if (clickTime - lastClickTime < 400) {
                // Double tap detected
                if (listener != null) {
                    listener.onItemDoubleClick(item, currentPos);
                }
            } else {
                // Single tap
                if (listener != null) {
                    listener.onItemClick(item, currentPos);
                }
            }
            lastClickTime = clickTime;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRacunId, tvMarker, tvZnesek, tvStatus;

        ViewHolder(View itemView) {
            super(itemView);
            tvRacunId = itemView.findViewById(R.id.tvRacunId);
            tvMarker = itemView.findViewById(R.id.tvMarker);
            tvZnesek = itemView.findViewById(R.id.tvZnesek);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
