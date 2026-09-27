package si.ros.RosKasa.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import si.ros.RosKasa.Globals;
import si.ros.RosKasa.R;
import si.ros.RosKasa.models.GetRacunRsTp;
import si.ros.RosKasa.models.PozicijaTp;
import si.ros.RosKasa.models.RacunTp;
import si.ros.RosKasa.soap.RosKasaSoapClient;

public class HodiDialog extends DialogFragment {

    private static final String TAG = "HodiDialog";

    public interface OnHodiSavedListener {
        void onHodiSaved(RacunTp racun);
    }

    private RacunTp racun;
    private OnHodiSavedListener listener;

    private final List<PozicijaTp> allPositions = new ArrayList<>();
    private final List<PozicijaTp> filteredPositions = new ArrayList<>();
    private final Map<PozicijaTp, String> originalHods = new HashMap<>();

    private int selectedIndex = -1;
    private HodiAdapter adapter;
    private EditText etSearch;
    private ImageButton btnClearSearch;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static HodiDialog newInstance(RacunTp racun, OnHodiSavedListener listener) {
        HodiDialog dialog = new HodiDialog();
        dialog.racun = racun;
        dialog.listener = listener;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFullScreen);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_hodi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPositions();

        RecyclerView rv = view.findViewById(R.id.rvHodiList);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new HodiAdapter();
        rv.setAdapter(adapter);

        etSearch = view.findViewById(R.id.etSearchHodi);
        btnClearSearch = view.findViewById(R.id.btnClearSearch);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPositions(s != null ? s.toString() : "");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (btnClearSearch != null) {
                    btnClearSearch.setVisibility(s != null && s.length() > 0 ? View.VISIBLE : View.GONE);
                }
            }
        });

        if (btnClearSearch != null) {
            btnClearSearch.setOnClickListener(v -> etSearch.setText(""));
        }

        setupHodButtons(view);

        MaterialButton btnOK = view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHodiSaved(racun);
            }
            dismiss();
        });

        MaterialButton btnObvestilo = view.findViewById(R.id.btnObvestilo);
        btnObvestilo.setOnClickListener(v -> handleObvestiloPoziv());

        MaterialButton btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> {
            // Revert sprememb
            for (Map.Entry<PozicijaTp, String> entry : originalHods.entrySet()) {
                if (entry.getKey() != null) {
                    entry.getKey().setHod(entry.getValue());
                }
            }
            dismiss();
        });
    }

    private void initPositions() {
        allPositions.clear();
        originalHods.clear();
        if (racun != null && racun.getRacPozic() != null) {
            for (PozicijaTp p : racun.getRacPozic()) {
                if (p != null && !p.isRowDeleted()) {
                    if (p.getNaziv() == null || p.getNaziv().trim().isEmpty()) {
                        String naz = Globals.getInstance().findNazivByNivo4Id(p.getNivo4Id());
                        if (naz != null && !naz.trim().isEmpty()) {
                            p.setNaziv(naz);
                        } else {
                            p.setNaziv("Artikel #" + p.getNivo4Id());
                        }
                    }
                    allPositions.add(p);
                    originalHods.put(p, p.getHod());
                }
            }
        }
        filteredPositions.clear();
        filteredPositions.addAll(allPositions);
        selectedIndex = filteredPositions.isEmpty() ? -1 : 0;
    }

    private void filterPositions(String query) {
        filteredPositions.clear();
        String q = query != null ? query.trim().toLowerCase() : "";
        for (PozicijaTp p : allPositions) {
            String name = p.getNaziv() != null ? p.getNaziv().toLowerCase() : "";
            if (q.isEmpty() || name.contains(q)) {
                filteredPositions.add(p);
            }
        }
        selectedIndex = filteredPositions.isEmpty() ? -1 : 0;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void setupHodButtons(View root) {
        int[] buttonIds = new int[] {
                R.id.btnHod1, R.id.btnHod2, R.id.btnHod3, R.id.btnHod4, R.id.btnHod5,
                R.id.btnHod6, R.id.btnHod7, R.id.btnHod8, R.id.btnHod9, R.id.btnHod10
        };

        for (int i = 0; i < buttonIds.length; i++) {
            final int hodNum = i + 1;
            MaterialButton btn = root.findViewById(buttonIds[i]);
            if (btn != null) {
                btn.setOnClickListener(v -> applyHodToSelected(String.valueOf(hodNum)));
            }
        }

        MaterialButton btnBrez = root.findViewById(R.id.btnHodBrez);
        if (btnBrez != null) {
            btnBrez.setOnClickListener(v -> applyHodToSelected(""));
        }
    }

    private void applyHodToSelected(String newHod) {
        if (selectedIndex < 0 || selectedIndex >= filteredPositions.size()) {
            Toast.makeText(requireContext(), "Prosim, najprej izberite artikel iz seznama!", Toast.LENGTH_SHORT).show();
            return;
        }

        PozicijaTp selectedPoz = filteredPositions.get(selectedIndex);
        if (selectedPoz != null) {
            String currentHod = selectedPoz.getHod() != null ? selectedPoz.getHod() : "";
            if (newHod.equals(currentHod)) {
                // Če je isti hod ponovno kliknjen, ga pobrišemo (toggle)
                selectedPoz.setHod("");
            } else {
                selectedPoz.setHod(newHod);
                if (!newHod.isEmpty()) {
                    Globals.getInstance().setTekociHod(newHod);
                }
            }

            if (adapter != null) {
                adapter.notifyItemChanged(selectedIndex);
            }
        }
    }

    private void handleObvestiloPoziv() {
        if (selectedIndex < 0 || selectedIndex >= filteredPositions.size()) {
            Toast.makeText(requireContext(), "Hod ni izbran/označen v seznamu ali še ni poslano naročilo kuhinji!", Toast.LENGTH_LONG).show();
            return;
        }

        PozicijaTp selectedPoz = filteredPositions.get(selectedIndex);
        if (selectedPoz == null || selectedPoz.getPozicijaId() <= 0
                || selectedPoz.getHod() == null || selectedPoz.getHod().trim().isEmpty()
                || racun == null || racun.getRacunId() <= 0) {
            Toast.makeText(requireContext(), "Hod ni izbran/označen v seznamu ali še ni poslano naročilo kuhinji!", Toast.LENGTH_LONG).show();
            return;
        }

        final int racunId = racun.getRacunId();
        final String hod = selectedPoz.getHod().trim();
        final String serverUrl = Globals.getInstance().getServerUrl();
        final String token = Globals.getInstance().getToken();

        AlertDialog progress = new AlertDialog.Builder(requireContext())
                .setTitle("Pošiljanje poziva")
                .setMessage("Pošiljam obvestilo za Hod " + hod + " v kuhinjo...")
                .setCancelable(false)
                .show();

        executor.execute(() -> {
            try {
                GetRacunRsTp resp = RosKasaSoapClient.natisniHod(serverUrl, token, racunId, hod);
                requireActivity().runOnUiThread(() -> {
                    if (progress.isShowing()) progress.dismiss();
                    if (resp != null && resp.getFault() != null && !resp.getFault().trim().isEmpty()) {
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Napaka")
                                .setMessage("Napaka: " + resp.getFault())
                                .setPositiveButton("V redu", null)
                                .show();
                    } else {
                        Toast.makeText(requireContext(), "Poziv za Hod " + hod + " poslan v kuhinjo!", Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "natisniHod error: " + e.getMessage(), e);
                requireActivity().runOnUiThread(() -> {
                    if (progress.isShowing()) progress.dismiss();
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Napaka")
                            .setMessage("Napaka pri klicu natisniHod: " + (e.getMessage() != null ? e.getMessage() : e.toString()))
                            .setPositiveButton("V redu", null)
                            .show();
                });
            }
        });
    }

    private class HodiAdapter extends RecyclerView.Adapter<HodiAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hodi_row, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PozicijaTp item = filteredPositions.get(position);
            holder.tvNaziv.setText(item.getNaziv() != null ? item.getNaziv() : "");
            String h = item.getHod() != null ? item.getHod().trim() : "";
            holder.tvHod.setText(h);

            boolean isSelected = (position == selectedIndex);
            holder.rowContent.setBackgroundColor(isSelected ? Color.parseColor("#1976D2") : Color.TRANSPARENT);

            holder.itemView.setOnClickListener(v -> {
                int prev = selectedIndex;
                selectedIndex = holder.getAdapterPosition();
                if (prev >= 0) notifyItemChanged(prev);
                notifyItemChanged(selectedIndex);
            });
        }

        @Override
        public int getItemCount() {
            return filteredPositions.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View rowContent;
            TextView tvNaziv, tvHod;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                rowContent = itemView.findViewById(R.id.llRowContent);
                tvNaziv = itemView.findViewById(R.id.tvNaziv);
                tvHod = itemView.findViewById(R.id.tvHod);
            }
        }
    }
}
