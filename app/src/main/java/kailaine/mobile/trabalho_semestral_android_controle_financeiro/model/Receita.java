package kailaine.mobile.trabalho_semestral_android_controle_financeiro.model;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.util.ArrayList;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.MetodologiaFinanceira;

public class Receita extends Transacao implements MetodologiaFinanceira {
    private List<Receita> listaDeReceitas;
    private double total;

    public Receita(double valor, List<Receita> listaDeReceitas) throws ValorInvalidoException {
        super(valor);
        if (valor < 0) {
            throw new ValorInvalidoException("O valor da receita não pode ser negativo!");
        }
        this.listaDeReceitas = listaDeReceitas != null ? listaDeReceitas : new ArrayList<>();
        calcularSaldo();
    }

    public Receita(List<Receita> listaDeReceitas) {
        super();
        this.listaDeReceitas = listaDeReceitas != null ? listaDeReceitas : new ArrayList<>();
        calcularSaldo();
    }


    public Receita() {
        super();
        this.listaDeReceitas = new ArrayList<>();
        calcularSaldo();
    }

    @Override
    public double calcularSaldo() {
        total = 0;
        for (Receita receita : listaDeReceitas) {
            total += receita.getValor();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Receita -" + super.toString() ;
    }
}
