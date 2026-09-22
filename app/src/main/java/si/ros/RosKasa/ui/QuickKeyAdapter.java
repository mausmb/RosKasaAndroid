package si.ros.RosKasa.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import si.ros.RosKasa.R;

public class QuickKeyAdapter extends RecyclerView.Adapter<QuickKeyAdapter.ViewHolder> {

    public interface OnKeyClickListener {
        void onKeyClick(QuickKey key);
    }

    public static class QuickKey {
        public String title;
        public boolean isCategory;
        public boolean isBack;
        public String categoryGroup;
        public Integer categoryTargetId;
        public double price;
        public int nivo4Id;
        public int nacinProdaje;
        public double polnjenje;
        public int paket;

        public QuickKey(String title, boolean isCategory, boolean isBack, String categoryGroup, double price) {
            this.title = title;
            this.isCategory = isCategory;
            this.isBack = isBack;
            this.categoryGroup = categoryGroup;
            this.price = price;
            this.nivo4Id = 0;
            this.nacinProdaje = 0;
            this.polnjenje = 1.0;
            this.paket = 0;
        }
    }

    private List<QuickKey> keys = new ArrayList<>();
    private final OnKeyClickListener listener;

    public QuickKeyAdapter(OnKeyClickListener listener) {
        this.listener = listener;
    }

    public void setKeys(List<QuickKey> keys) {
        this.keys = keys != null ? keys : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quick_key, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuickKey key = keys.get(position);
        holder.btnKey.setText(key.title);

        if (key.isBack) {
            holder.btnKey.setBackgroundColor(Color.parseColor("#B71C1C")); // Dark Red for Back
            holder.btnKey.setTextColor(Color.WHITE);
        } else if (key.isCategory) {
            holder.btnKey.setBackgroundColor(Color.parseColor("#555555")); // Grey for Category
            holder.btnKey.setTextColor(Color.WHITE);
        } else {
            holder.btnKey.setBackgroundColor(Color.parseColor("#1565C0")); // Blue for Article
            holder.btnKey.setTextColor(Color.WHITE);
        }

        holder.btnKey.setOnClickListener(v -> {
            if (listener != null) listener.onKeyClick(key);
        });
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnKey;

        ViewHolder(View itemView) {
            super(itemView);
            btnKey = itemView.findViewById(R.id.btnQuickKey);
        }
    }
}
