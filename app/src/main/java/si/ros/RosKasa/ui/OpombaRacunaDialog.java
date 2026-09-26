package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import si.ros.RosKasa.R;

public class OpombaRacunaDialog {

    public interface OnOpombaSavedListener {
        void onOpombaSaved(String opomba);
    }

    public static void show(Context context, String currentOpomba, OnOpombaSavedListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_opomba_racuna);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        EditText etOp1 = dialog.findViewById(R.id.etOp1);
        EditText etOp2 = dialog.findViewById(R.id.etOp2);
        EditText etOp3 = dialog.findViewById(R.id.etOp3);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnPotrdiOpombo = dialog.findViewById(R.id.btnPotrdiOpombo);

        if (currentOpomba != null && !currentOpomba.isEmpty()) {
            String[] lines = currentOpomba.split("\\r?\\n");
            if (lines.length > 0) etOp1.setText(lines[0]);
            if (lines.length > 1) etOp2.setText(lines[1]);
            if (lines.length > 2) etOp3.setText(lines[2]);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnPotrdiOpombo.setOnClickListener(v -> {
            String op1 = etOp1.getText() != null ? etOp1.getText().toString().trim() : "";
            String op2 = etOp2.getText() != null ? etOp2.getText().toString().trim() : "";
            String op3 = etOp3.getText() != null ? etOp3.getText().toString().trim() : "";

            StringBuilder sb = new StringBuilder();
            if (!op1.isEmpty()) sb.append(op1);
            if (!op2.isEmpty()) {
                if (sb.length() > 0) sb.append("\n");
                sb.append(op2);
            }
            if (!op3.isEmpty()) {
                if (sb.length() > 0) sb.append("\n");
                sb.append(op3);
            }

            dialog.dismiss();
            if (listener != null) {
                listener.onOpombaSaved(sb.toString());
            }
        });

        dialog.show();
    }
}
