package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

import si.ros.RosKasa.R;

public class LestvicaPolnjenjaDialog {

    public interface OnScaleSelectedListener {
        void onScaleSelected(double epScale);
        void onCancelled();
    }

    public static void show(@NonNull Context context, String itemTitle, int nacinProdaje, double pomPolnjenje, OnScaleSelectedListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_lestvica_polnjenja, null);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView tvTitle = view.findViewById(R.id.tvLestvicaTitle);
        tvTitle.setText(itemTitle != null ? itemTitle : "");

        MaterialButton btnEp1 = view.findViewById(R.id.btnEp1);
        MaterialButton btnEp2 = view.findViewById(R.id.btnEp2);
        MaterialButton btnEp3 = view.findViewById(R.id.btnEp3);
        MaterialButton btnEp4 = view.findViewById(R.id.btnEp4);
        MaterialButton btnEp5 = view.findViewById(R.id.btnEp5);
        MaterialButton btnEp6 = view.findViewById(R.id.btnEp6);
        MaterialButton btnEp7 = view.findViewById(R.id.btnEp7);
        MaterialButton btnCancel = view.findViewById(R.id.btnEpCancel);

        MaterialButton[] buttons = new MaterialButton[]{btnEp1, btnEp2, btnEp3, btnEp4, btnEp5, btnEp6, btnEp7};

        // Reset all buttons
        for (MaterialButton btn : buttons) {
            btn.setVisibility(View.GONE);
            btn.setEnabled(false);
            btn.setOnClickListener(null);
        }

        switch (nacinProdaje) {
            case 1:
            case 3:
            case 4:
            case 9:
            case 10:
                // od 0,1 - 1: 0.1, 0.125, 0.2, 0.25, 0.3, 0.5, 1
                setupButton(btnEp1, 0.1, listener, dialog);
                setupButton(btnEp2, 0.125, listener, dialog);
                setupButton(btnEp3, 0.2, listener, dialog);
                setupButton(btnEp4, 0.25, listener, dialog);
                setupButton(btnEp5, 0.3, listener, dialog);
                setupButton(btnEp6, 0.5, listener, dialog);
                setupButton(btnEp7, 1.0, listener, dialog);
                break;

            case 2:
                // žgane pijače: 0.03, 0.05, in pompolnjenje (če != 0)
                setupButton(btnEp1, 0.03, listener, dialog);
                setupButton(btnEp2, 0.05, listener, dialog);
                if (pomPolnjenje > 0 && pomPolnjenje != 0.03 && pomPolnjenje != 0.05) {
                    setupButton(btnEp3, pomPolnjenje, listener, dialog);
                } else if (pomPolnjenje == 1.0) {
                    setupButton(btnEp3, 1.0, listener, dialog);
                }
                break;

            case 5:
            case 6:
            default:
                setupButton(btnEp1, 1.0, listener, dialog);
                break;
        }

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelled();
        });

        dialog.show();
    }

    private static void setupButton(MaterialButton btn, double scaleValue, OnScaleSelectedListener listener, Dialog dialog) {
        btn.setVisibility(View.VISIBLE);
        btn.setEnabled(true);
        String label = (scaleValue == (long) scaleValue) ? String.format(Locale.getDefault(), "%d", (long) scaleValue) : String.format(Locale.getDefault(), "%s", scaleValue).replace('.', ',');
        btn.setText(label);
        btn.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onScaleSelected(scaleValue);
        });
    }
}
