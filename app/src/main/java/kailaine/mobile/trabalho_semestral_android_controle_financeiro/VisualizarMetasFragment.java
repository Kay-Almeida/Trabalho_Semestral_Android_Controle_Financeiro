package kailaine.mobile.trabalho_semestral_android_controle_financeiro;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.MetaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.ReservaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.MetaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ReservaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.MetaFinanceiraDao;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.ReservaFinanceiraDao;

public class VisualizarMetasFragment extends Fragment {
    private View view;
    private TextView tvTituloMetas;
    private Spinner spMetasVisualizar;
    private EditText etValorTotalMeta;
    private Button btnConcluirMeta;
    private Button btnEditarMeta;
    private Button btnExluirMeta;
    private TextView tvResultaPorcentagemMeta;

    private MetaController metaController;
    private List<MetaFinanceira> metas;
    private MetaFinanceiraDao metaFinanceiraDao;
    private ReservaController reservaController;

    public VisualizarMetasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visualizar_metas, container, false);
        tvTituloMetas = view.findViewById(R.id.tvTituloMetas);
        spMetasVisualizar = view.findViewById(R.id.spMetasVisualizar);
        etValorTotalMeta = view.findViewById(R.id.etValorTotalMeta);
        btnConcluirMeta = view.findViewById(R.id.btnConcluirMeta);
        btnEditarMeta = view.findViewById(R.id.btnEditarMeta);
        btnExluirMeta = view.findViewById(R.id.btnExluirMeta);
        tvResultaPorcentagemMeta = view.findViewById(R.id.tvResultaPorcentagemMeta);
        metaController = new MetaController(new MetaFinanceiraDao(getContext()));
        reservaController = new ReservaController(new ReservaFinanceiraDao(getContext()));
        
        preencheSpinner();
        
        btnConcluirMeta.setOnClickListener( op -> mostrarValor());
        btnEditarMeta.setOnClickListener(op -> editarMeta());
        btnExluirMeta.setOnClickListener(op -> excluirMeta());
        return view;
    }

    private void excluirMeta() {
        int selectedPosition = spMetasVisualizar.getSelectedItemPosition();
        if (selectedPosition > 0) {
            MetaFinanceira metaSelecionada = metas.get(selectedPosition);
            try {
                List<ReservaFinanceira> reservasAssociadas = reservaController.buscarReservasPorMeta(metaSelecionada.getId());

                for (ReservaFinanceira reserva : reservasAssociadas) {
                    reservaController.deletar(reserva);
                }
                metaController.deletar(metaSelecionada);
                Toast.makeText(getContext(), "Meta deleta com Sucesso", Toast.LENGTH_SHORT).show();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        preencheSpinner();
    }

    private void editarMeta() {
        int selectedPosition = spMetasVisualizar.getSelectedItemPosition();
        if (selectedPosition > 0) {
            MetaFinanceira metaSelecionada = metas.get(selectedPosition);
            metaSelecionada.setValorMeta(Double.parseDouble(etValorTotalMeta.getText().toString()));
            try{
                metaController.modificar(metaSelecionada);
                Toast.makeText(getContext(), "Valor de meta atualizado: " + metaSelecionada.toString(), Toast.LENGTH_SHORT).show();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void mostrarValor() {
        int selectedPosition = spMetasVisualizar.getSelectedItemPosition();
        if (selectedPosition > 0) {
            MetaFinanceira metaSelecionada = metas.get(selectedPosition);

            try {
                MetaFinanceira metaAtualizada = metaController.buscarPorObjeto(metaSelecionada);
                if (metaAtualizada != null) {
                    etValorTotalMeta.setText(String.valueOf(metaAtualizada.getValorMeta()));
                    double progresso = metaAtualizada.calcularProgresso();
                    @SuppressLint("DefaultLocale") String textoProgresso = String.format("Valor Reserva: %.2f\nProgresso: %.2f%%", metaAtualizada.getTotalReserva(), progresso);
                    tvResultaPorcentagemMeta.setText(textoProgresso);
                } else {
                    Toast.makeText(getContext(), "Meta não encontrada no banco de dados.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Erro ao buscar a meta: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            etValorTotalMeta.setText("");
            tvResultaPorcentagemMeta.setText("Progresso: 0.00%");
            Toast.makeText(getContext(), "Por favor, selecione uma meta válida.", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencheSpinner() {
        try {
            metas = metaController.listar();
            MetaFinanceira m0 = new MetaFinanceira();
            m0.setValorMeta(0);
            m0.setNome("Selecione uma Meta");
            metas.add(0, m0);

            ArrayAdapter<MetaFinanceira> mf = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, metas);
            mf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMetasVisualizar.setAdapter(mf);

        }catch (Exception e){
           Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
}

