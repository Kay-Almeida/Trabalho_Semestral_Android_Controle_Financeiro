package kailaine.mobile.trabalho_semestral_android_controle_financeiro;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
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
import java.util.ArrayList;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.DespesaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.ReceitaController;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Despesa;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Receita;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ValorInvalidoException;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.DespesaDao;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.ReceitaDao;

public class NovaEntradaFragment extends Fragment {
    private View view;
    private Button btnSalvarEntrada;
    private EditText etValorEntrada;
    private TextView textView, textView2;
    private RadioGroup radioGroup;
    private RadioButton rbDespesaHistorico, rbReceitaHistorico;

    private ReceitaController receitaController;
    private DespesaController despesaController;

    public NovaEntradaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_nova_entrada, container, false);
        btnSalvarEntrada = view.findViewById(R.id.btnSalvarEntrada);
        etValorEntrada = view.findViewById(R.id.etValorEntrada);

        textView = view.findViewById(R.id.textView);
        textView2 = view.findViewById(R.id.textView2);

        radioGroup = view.findViewById(R.id.radioGroup);
        rbDespesaHistorico = view.findViewById(R.id.rbDespesaHistorico);
        rbReceitaHistorico = view.findViewById(R.id.rbReceitaHistorico);

        receitaController = new ReceitaController(new ReceitaDao(getContext()));
        despesaController = new DespesaController(new DespesaDao(getContext()));

        btnSalvarEntrada.setOnClickListener(op-> salvar());
        return view;
    }

    private void salvar() {
        String valorEntrada = etValorEntrada.getText().toString();
        double valor = Double.parseDouble(valorEntrada);
        if(rbReceitaHistorico.isChecked()){
            try {
                Receita receita = new Receita(valor, new ArrayList<>());
                receita.getData();
                try {
                    receitaController.inserir(receita);
                    Toast.makeText(view.getContext(), receita.toString(), Toast.LENGTH_LONG).show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (ValorInvalidoException e) {
                throw new RuntimeException(e);
            }

        }else{
            try {
                Despesa despesa = new Despesa(valor, new ArrayList<>());
                despesa.getData();
                try {
                    despesaController.inserir(despesa);
                    Toast.makeText(view.getContext(), despesa.toString(), Toast.LENGTH_LONG).show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (ValorInvalidoException e) {
                throw new RuntimeException(e);
            }
        }
        limpaCampos();

    }

    private void limpaCampos() {
        etValorEntrada.setText("");
        radioGroup.clearCheck();
    }
}