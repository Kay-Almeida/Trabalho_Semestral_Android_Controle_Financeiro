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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.DespesaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.ReceitaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.ReservaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Despesa;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Receita;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ReservaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Transacao;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.DespesaDao;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.ReceitaDao;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.ReservaFinanceiraDao;

public class HistoricoFragment extends Fragment {
    private View view;
    private Button btnExcluirHistorico, btnModificarHistorico, btnPesquisarHistorico;
    private EditText etIDEntradaHistorico, etValorHistorico;
    private TextView tvEditorEntrada, tvResultadoListarHistorico, tvHistorico;
    private RadioGroup radioGroup3;
    private RadioButton rbDespesaHistorico, rbReceitaHistorico, rbReservaHistorico;

    private DespesaController despesaController;
    private ReceitaController receitaController;
    private ReservaController reservaController;

    public HistoricoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_historico, container, false);

        btnExcluirHistorico = view.findViewById(R.id.btnExcluirHistorico);
        btnModificarHistorico = view.findViewById(R.id.btnModificarHistorico);
        btnPesquisarHistorico = view.findViewById(R.id.btnPesquisarHistorico);

        etIDEntradaHistorico = view.findViewById(R.id.etIDEntradaHistorico);
        etValorHistorico = view.findViewById(R.id.etValorHistorico);

        tvEditorEntrada = view.findViewById(R.id.tvEditorEntrada);
        tvResultadoListarHistorico = view.findViewById(R.id.tvResultadoListarHistorico);
        tvHistorico = view.findViewById(R.id.tvHistorico);
        tvResultadoListarHistorico.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        radioGroup3 = view.findViewById(R.id.radioGroup3);
        rbDespesaHistorico = view.findViewById(R.id.rbDespesaHistorico);
        rbReceitaHistorico = view.findViewById(R.id.rbReceitaHistorico);
        rbReservaHistorico = view.findViewById(R.id.rbReservaHistorico);

        despesaController = new DespesaController(new DespesaDao(view.getContext()));
        receitaController = new ReceitaController(new ReceitaDao(view.getContext()));
        reservaController = new ReservaController(new ReservaFinanceiraDao(view.getContext()));

        btnExcluirHistorico.setOnClickListener(op -> {
            try {
                excluirEntrada();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnModificarHistorico.setOnClickListener(op -> {
            try {
                editarEntrada();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnPesquisarHistorico.setOnClickListener(op -> {
            try {
                pesquisarEntrada();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        listarTodasEntradas();
        return view;
    }

    @SuppressLint("DefaultLocale")
    private void listarTodasEntradas() {
            StringBuilder resultado = new StringBuilder();

            try {
                List<Despesa> despesas = despesaController.listar();
                for (Despesa despesa : despesas) {
                    resultado.append("Despesa - "+ despesa.getData()).append(String.format(" ID: %d, Valor: %.2f\n", despesa.getId(), despesa.getValor()));
                }

                List<Receita> receitas = receitaController.listar();
                for (Receita receita : receitas) {
                    resultado.append("Receita - "+ receita.getData()).append(String.format(" ID: %d, Valor: %.2f\n", receita.getId(), receita.getValor()));
                }

                List<ReservaFinanceira> reservas = reservaController.listar();
                for (ReservaFinanceira reserva : reservas) {
                    resultado.append("Reserva - "+ reserva.getMeta().getNome()).append(String.format(" ID: %d, Valor: %.2f\n", reserva.getId(), reserva.getValor()));
                }

                tvResultadoListarHistorico.setText(resultado.toString());
            } catch (Exception e) {
                Toast.makeText(view.getContext(), "Erro ao listar entradas: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    private void pesquisarEntrada() throws SQLException {
    try {
        if (rbDespesaHistorico.isChecked()) {
            int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
            Despesa despesa = new Despesa();
            despesa = despesaController.buscarPorId(id);
            etIDEntradaHistorico.setText(String.valueOf(despesa.getId()));
            etValorHistorico.setText(String.valueOf(despesa.getValor()));
            Toast.makeText(view.getContext(), "Despesa encontrada com sucesso", Toast.LENGTH_LONG).show();

        } else if (rbReceitaHistorico.isChecked()) {
            int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
            Receita receita = new Receita();
            receita = receitaController.buscarPorId(id);
            etIDEntradaHistorico.setText(String.valueOf(receita.getId()));
            etValorHistorico.setText(String.valueOf(receita.getValor()));
            Toast.makeText(view.getContext(), "Receita encontrada com sucesso", Toast.LENGTH_LONG).show();

        } else if (rbReservaHistorico.isChecked()) {
            int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
            ReservaFinanceira reserva = new ReservaFinanceira();
            reserva = reservaController.buscarPorId(id);
            etIDEntradaHistorico.setText(String.valueOf(reserva.getId()));
            etValorHistorico.setText(String.valueOf(reserva.getValor()));
            Toast.makeText(view.getContext(), "Reserva encontrada com sucesso", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(view.getContext(), "Escolha um Tipo de entrada", Toast.LENGTH_LONG).show();

        }
    }catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao pesquisar entrada: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void editarEntrada() throws SQLException {
    try{
        if(rbDespesaHistorico.isChecked()){
            int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
            double valor = Double.parseDouble(etValorHistorico.getText().toString());
            Despesa despesa = new Despesa();
            despesa.setId(id);
            despesa.setValor(valor);
            despesaController.modificar(despesa);
            Toast.makeText(view.getContext(), "Despesa editada com sucesso", Toast.LENGTH_LONG).show();

        } else if (rbReceitaHistorico.isChecked()) {
            int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
            double valor = Double.parseDouble(etValorHistorico.getText().toString());
            Receita receita = new Receita();
            receita.setId(id);
            receita.setValor(valor);
            receitaController.modificar(receita);
            Toast.makeText(view.getContext(), "Receita editada com sucesso", Toast.LENGTH_LONG).show();

        }else if(rbReservaHistorico.isChecked()){
            int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
            double valor = Double.parseDouble(etValorHistorico.getText().toString());
            ReservaFinanceira reserva = new ReservaFinanceira();
            reserva = reservaController.buscarPorId(id);
            reserva.setId(id);
            reserva.setValor(valor);
            reservaController.modificar(reserva);
            Toast.makeText(view.getContext(), "Reserva editada com sucesso", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(view.getContext(), "Escolha um Tipo de entrada", Toast.LENGTH_LONG).show();

        }
    } catch (Exception e) {
        Toast.makeText(view.getContext(), "Erro ao editar entrada: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
        listarTodasEntradas();
        limparCampos();
    }

    private void limparCampos() {
        etIDEntradaHistorico.setText("");
        etValorHistorico.setText("");
        rbDespesaHistorico.setChecked(false);
        rbReceitaHistorico.setChecked(false);
        rbReservaHistorico.setChecked(false);
    }

    private void excluirEntrada() throws SQLException {
        try {
            if (rbDespesaHistorico.isChecked()) {
                int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
                double valor = Double.parseDouble(etValorHistorico.getText().toString());
                Despesa despesa = new Despesa();
                despesa.setId(id);
                despesa.setValor(valor);
                despesaController.deletar(despesa);
                Toast.makeText(view.getContext(), "Despesa excluída com sucesso", Toast.LENGTH_LONG).show();
            } else if (rbReceitaHistorico.isChecked()) {
                int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
                double valor = Double.parseDouble(etValorHistorico.getText().toString());
                Receita receita = new Receita();
                receita.setValor(valor);
                receita.setId(id);
                receitaController.deletar(receita);
                Toast.makeText(view.getContext(), "Receita excluída com sucesso", Toast.LENGTH_LONG).show();
            } else if (rbReservaHistorico.isChecked()) {
                int id = Integer.parseInt(etIDEntradaHistorico.getText().toString());
                double valor = Double.parseDouble(etValorHistorico.getText().toString());
                ReservaFinanceira reserva = new ReservaFinanceira();
                reserva.setId(id);
                reserva.setValor(valor);
                reservaController.deletar(reserva);
                Toast.makeText(view.getContext(), "Reserva excluída com sucesso", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(view.getContext(), "Escolha um Tipo de entrada", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao excluir entrada: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
            listarTodasEntradas();
             limparCampos();
        }
}


