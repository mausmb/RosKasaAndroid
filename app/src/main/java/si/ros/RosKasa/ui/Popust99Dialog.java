package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.R;

public class Popust99Dialog {

    public interface OnPopustAppliedListener {
        void onPopustApplied(BigDecimal procent, BigDecimal znesek);
        void onCancelled();
    }

    public static void show(@NonNull Context context, BigDecimal racunZnesek, OnPopustAppliedListener listener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_popust99, null);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        final BigDecimal skupajRacun = (racunZnesek != null && racunZnesek.compareTo(BigDecimal.ZERO) > 0)
                ? racunZnesek : BigDecimal.ZERO;

        TextView tvRacunSkupaj = view.findViewById(R.id.tvRacunSkupaj);
        tvRacunSkupaj.setText(String.format(Locale.getDefault(), "Znesek računa: %.2f €", skupajRacun));

        MaterialButton btnTipProcent = view.findViewById(R.id.btnTipProcent);
        MaterialButton btnTipZnesek = view.findViewById(R.id.btnTipZnesek);
        LinearLayout llHitriProcenti = view.findViewById(R.id.llHitriProcenti);
        EditText etValue = view.findViewById(R.id.etPopustValue);
        TextView tvPreview = view.findViewById(R.id.tvPopustPreview);

        final boolean[] isProcentMode = new boolean[]{true};
        final boolean[] isFirstInput = new boolean[]{true};

        Runnable updatePreview = () -> {
            String txt = etValue.getText().toString().replace(",", ".").trim();
            if (txt.isEmpty()) {
                tvPreview.setText(String.format(Locale.getDefault(), "Popust: 0,00 €  |  Novo za plačilo: %.2f €", skupajRacun));
                return;
            }
            try {
                BigDecimal val = new BigDecimal(txt);
                if (val.compareTo(BigDecimal.ZERO) < 0) val = BigDecimal.ZERO;

                BigDecimal popustZnesek;
                BigDecimal popustProcent;

                if (isProcentMode[0]) {
                    popustProcent = val;
                    if (popustProcent.compareTo(BigDecimal.valueOf(100)) > 0) {
                        popustProcent = BigDecimal.valueOf(100);
                    }
                    popustZnesek = skupajRacun.multiply(popustProcent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                } else {
                    popustZnesek = val;
                    if (popustZnesek.compareTo(skupajRacun) > 0) {
                        popustZnesek = skupajRacun;
                    }
                    if (skupajRacun.compareTo(BigDecimal.ZERO) > 0) {
                        popustProcent = popustZnesek.multiply(BigDecimal.valueOf(100)).divide(skupajRacun, 2, RoundingMode.HALF_UP);
                    } else {
                        popustProcent = BigDecimal.ZERO;
                    }
                }

                BigDecimal novoZaPlacilo = skupajRacun.subtract(popustZnesek);
                if (novoZaPlacilo.compareTo(BigDecimal.ZERO) < 0) novoZaPlacilo = BigDecimal.ZERO;

                tvPreview.setText(String.format(Locale.getDefault(), "Popust: %.2f € (%.1f %%)  |  Novo: %.2f €",
                        popustZnesek, popustProcent, novoZaPlacilo));
            } catch (Exception e) {
                tvPreview.setText(String.format(Locale.getDefault(), "Popust: 0,00 €  |  Novo za plačilo: %.2f €", skupajRacun));
            }
        };

        btnTipProcent.setOnClickListener(v -> {
            isProcentMode[0] = true;
            btnTipProcent.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#1565C0")));
            btnTipZnesek.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#37474F")));
            llHitriProcenti.setVisibility(View.VISIBLE);
            etValue.setHint("0 %");
            etValue.setText("");
            isFirstInput[0] = true;
            updatePreview.run();
        });

        btnTipZnesek.setOnClickListener(v -> {
            isProcentMode[0] = false;
            btnTipZnesek.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#1565C0")));
            btnTipProcent.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#37474F")));
            llHitriProcenti.setVisibility(View.GONE);
            etValue.setHint("0,00 €");
            etValue.setText("");
            isFirstInput[0] = true;
            updatePreview.run();
        });

        // Hitri procenti
        view.findViewById(R.id.btnQuick5).setOnClickListener(v -> {
            etValue.setText("5");
            isFirstInput[0] = false;
            updatePreview.run();
        });
        view.findViewById(R.id.btnQuick10).setOnClickListener(v -> {
            etValue.setText("10");
            isFirstInput[0] = false;
            updatePreview.run();
        });
        view.findViewById(R.id.btnQuick15).setOnClickListener(v -> {
            etValue.setText("15");
            isFirstInput[0] = false;
            updatePreview.run();
        });
        view.findViewById(R.id.btnQuick20).setOnClickListener(v -> {
            etValue.setText("20");
            isFirstInput[0] = false;
            updatePreview.run();
        });

        etValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updatePreview.run();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        View.OnClickListener numListener = v -> {
            String curr = etValue.getText().toString();
            String digit = ((MaterialButton) v).getText().toString();

            if (digit.equals("C")) {
                etValue.setText("");
                isFirstInput[0] = false;
                updatePreview.run();
                return;
            }

            if (isFirstInput[0]) {
                isFirstInput[0] = false;
                if (digit.equals(",")) {
                    etValue.setText("0,");
                } else {
                    etValue.setText(digit);
                }
                updatePreview.run();
                return;
            }

            if (digit.equals(",")) {
                if (!curr.contains(",") && !curr.contains(".")) {
                    etValue.setText(curr.isEmpty() ? "0," : curr + ",");
                }
            } else {
                etValue.setText(curr + digit);
            }
            updatePreview.run();
        };

        int[] numBtnIds = new int[]{
                R.id.btnNum0, R.id.btnNum1, R.id.btnNum2, R.id.btnNum3, R.id.btnNum4,
                R.id.btnNum5, R.id.btnNum6, R.id.btnNum7, R.id.btnNum8, R.id.btnNum9,
                R.id.btnNumComma, R.id.btnNumC
        };
        for (int id : numBtnIds) {
            View b = view.findViewById(id);
            if (b != null) b.setOnClickListener(numListener);
        }

        view.findViewById(R.id.btnPopustCancel).setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelled();
        });

        view.findViewById(R.id.btnPopustOk).setOnClickListener(v -> {
            String txt = etValue.getText().toString().replace(",", ".").trim();
            if (txt.isEmpty()) {
                Toast.makeText(context, "Vnesite vrednost popusta!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                BigDecimal val = new BigDecimal(txt);
                if (val.compareTo(BigDecimal.ZERO) <= 0) {
                    Toast.makeText(context, "Vrednost popusta mora biti večja od 0!", Toast.LENGTH_SHORT).show();
                    return;
                }

                BigDecimal koncenProcent;
                BigDecimal koncenZnesek;

                if (isProcentMode[0]) {
                    koncenProcent = val.setScale(2, RoundingMode.HALF_UP);
                    if (koncenProcent.compareTo(BigDecimal.valueOf(100)) > 0) {
                        Toast.makeText(context, "Procent popusta ne more presegati 100%!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    koncenZnesek = skupajRacun.multiply(koncenProcent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                } else {
                    koncenZnesek = val.setScale(2, RoundingMode.HALF_UP);
                    if (koncenZnesek.compareTo(skupajRacun) > 0) {
                        Toast.makeText(context, "Znesek popusta ne more presegati zneska računa (" + skupajRacun + " €)!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (skupajRacun.compareTo(BigDecimal.ZERO) > 0) {
                        koncenProcent = koncenZnesek.multiply(BigDecimal.valueOf(100)).divide(skupajRacun, 2, RoundingMode.HALF_UP);
                    } else {
                        koncenProcent = BigDecimal.ZERO;
                    }
                }

                dialog.dismiss();
                if (listener != null) {
                    listener.onPopustApplied(koncenProcent, koncenZnesek);
                }
            } catch (Exception e) {
                Toast.makeText(context, "Neveljavna vrednost: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
