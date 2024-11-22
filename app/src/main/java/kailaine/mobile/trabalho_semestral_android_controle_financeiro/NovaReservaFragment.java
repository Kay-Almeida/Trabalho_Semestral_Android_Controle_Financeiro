package kailaine.mobile.trabalho_semestral_android_controle_financeiro;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.MetaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.ReservaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.MetaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ReservaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.MetaFinanceiraDao;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.ReservaFinanceiraDao;

public class NovaReservaFragment extends Fragment {
    private View view;
    private Spinner spMetaReserva;
    private TextView tvTitulo;
    private EditText etValorReserva;
    private Button btnSalvarReserva;

    private ReservaController reservaController;
    private MetaController metaController;
    private List<MetaFinanceira> metas;

    public NovaReservaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_nova_reserva, container, false);
        spMetaReserva = view.findViewById(R.id.spMetaReserva);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        etValorReserva = view.findViewById(R.id.etValorReserva);
        btnSalvarReserva = view.findViewById(R.id.btnSalvarReserva);

        reservaController = new ReservaController(new ReservaFinanceiraDao(getContext()));
        metaController = new MetaController(new MetaFinanceiraDao(getContext()));

        preencheSpinner();
        btnSalvarReserva.setOnClickListener(op -> salvarReserva());

        return view;
    }

    private void salvarReserva() {
        int spReserva = spMetaReserva.getSelectedItemPosition();
        if(spReserva > 0){
            ReservaFinanceira reserva = new ReservaFinanceira();
            String valorReserva = etValorReserva.getText().toString();
            Double valor = Double.parseDouble(valorReserva);
            reserva.setValor(valor);
            reserva.setMeta((MetaFinanceira) spMetaReserva.getSelectedItem());
            reserva.getData();
            reserva.getId();
            try{
               reservaController.inserir(reserva);
                Toast.makeText(view.getContext(), reserva.toString(), Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        etValorReserva.setText("");
    }
    private void preencheSpinner() {
        MetaFinanceira m0 = new MetaFinanceira();
        m0.setValorMeta(0);
        m0.setNome("Selecione uma Meta");

        try {
            metas = metaController.listar();
            metas.add(0, m0);

            ArrayAdapter<MetaFinanceira> mf = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, metas);
            spMetaReserva.setAdapter(mf);
        }catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}