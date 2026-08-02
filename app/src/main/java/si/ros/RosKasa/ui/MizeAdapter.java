package si.ros.RosKasa.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import si.ros.RosKasa.R;

public class MizeAdapter extends RecyclerView.Adapter<MizeAdapter.ViewHolder> {

    public interface OnMizaClickListener {
        void onMizaClick(String miza);
    }

    private final List<String> mizeList;
    private final OnMizaClickListener listener;

    public MizeAdapter(List<String> mizeList, OnMizaClickListener listener) {
        this.mizeList = mizeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_miza, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String miza = mizeList.get(position);
        holder.tvMizaName.setText(miza);

        // Primer za prikaz zasedene mize (npr. Miza 7)
        boolean isOccupied = miza.equals("Miza 7") || miza.equals("Miza 12");
        if (isOccupied) {
            holder.tvMizaName.setTextColor(Color.RED);
            holder.itemView.setBackgroundColor(Color.parseColor("#4A2020"));
        } else {
            holder.tvMizaName.setTextColor(Color.WHITE);
            holder.itemView.setBackgroundColor(Color.parseColor("#405060"));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMizaClick(miza);
        });
    }

    @Override
    public int getItemCount() {
        return mizeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMizaName;

        ViewHolder(View itemView) {
            super(itemView);
            tvMizaName = itemView.findViewById(R.id.tvMizaName);
        }
    }
}
