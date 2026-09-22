package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.util.Locale;

import si.ros.RosKasa.R;

public class VnosCeneDialog {

    public interface OnPriceEnteredListener {
        void onPriceEntered(BigDecimal price);
        void onCancelled();
    }

    public static void show(@NonNull Context context, String itemTitle, boolean allowNegative, OnPriceEnteredListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_vnos_cene, null);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView tvTitle = view.findViewById(R.id.tvVnosCeneTitle);
        tvTitle.setText(itemTitle != null ? itemTitle : "VNOS CENE ARTIKLA");

        EditText etValue = view.findViewById(R.id.etVnosCeneValue);
        MaterialButton btnMinus = view.findViewById(R.id.btnNumMinus);
        btnMinus.setVisibility(allowNegative ? View.VISIBLE : View.GONE);

        View.OnClickListener numListener = v -> {
            String curr = etValue.getText().toString();
            String digit = ((MaterialButton) v).getText().toString();
            if (digit.equals(",")) {
                if (!curr.contains(",") && !curr.contains(".")) {
                    etValue.setText(curr + ",");
                }
            } else if (digit.equals("-")) {
                if (curr.startsWith("-")) {
                    etValue.setText(curr.substring(1));
                } else {
                    etValue.setText("-" + curr);
                }
            } else {
                etValue.setText(curr + digit);
            }
        };

        int[] numBtnIds = new int[]{
                R.id.btnNum0, R.id.btnNum1, R.id.btnNum2, R.id.btnNum3, R.id.btnNum4,
                R.id.btnNum5, R.id.btnNum6, R.id.btnNum7, R.id.btnNum8, R.id.btnNum9,
                R.id.btnNumComma, R.id.btnNumMinus
        };

        for (int id : numBtnIds) {
            View b = view.findViewById(id);
            if (b != null) b.setOnClickListener(numListener);
        }

        view.findViewById(R.id.btnVnosCeneCancel).setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelled();
        });

        view.findViewById(R.id.btnVnosCeneOk).setOnClickListener(v -> {
            String valStr = etValue.getText().toString().replace(',', '.').trim();
            BigDecimal price = BigDecimal.ZERO;
            if (!valStr.isEmpty()) {
                try {
                    price = new BigDecimal(valStr);
                } catch (Exception ignored) {}
            }
            dialog.dismiss();
            if (listener != null) listener.onPriceEntered(price);
        });

        dialog.show();
    }
}
