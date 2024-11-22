package kailaine.mobile.trabalho_semestral_android_controle_financeiro.model;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.util.ArrayList;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.MetodologiaFinanceira;

public class Despesa extends Transacao implements MetodologiaFinanceira {
    private List<Despesa> listaDeDespesas;
    private double total;

    public Despesa(double valor, List<Despesa> listaDeDespesas) throws ValorInvalidoException {
        super(valor);
        if (valor < 0) {
            throw new ValorInvalidoException("O valor da despesa não pode ser negativo!");
        }
        this.listaDeDespesas = listaDeDespesas != null ? listaDeDespesas : new ArrayList<>();
        calcularSaldo();
    }

    public Despesa(List<Despesa> listaDeDespesas) {
        super();
        this.listaDeDespesas = listaDeDespesas != null ? listaDeDespesas : new ArrayList<>();
        calcularSaldo();
    }

    public Despesa() {
        super();
        this.listaDeDespesas = new ArrayList<>();
        calcularSaldo();
    }

    @Override
    public double calcularSaldo() {
        total = 0;
        for (Despesa despesa : listaDeDespesas) {
            total += despesa.getValor();
        }
        return total;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Despesa -" + super.toString();
    }
}
