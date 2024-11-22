package kailaine.mobile.trabalho_semestral_android_controle_financeiro.model;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.util.ArrayList;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller.MetodologiaFinanceira;

public class ReservaFinanceira extends Transacao implements MetodologiaFinanceira {
    private List<ReservaFinanceira> listaDeReservas;
    private double total;
    private MetaFinanceira meta;

    public ReservaFinanceira(double valor, List<ReservaFinanceira> listaDeReservas, MetaFinanceira meta) throws ValorInvalidoException {
        super(valor);
        if (valor < 0) {
            throw new ValorInvalidoException("O valor da reserva financeira não pode ser negativo!");
        }
        this.listaDeReservas = listaDeReservas != null ? listaDeReservas : new ArrayList<>();
        this.meta = meta;
        calcularSaldo();
    }

    public ReservaFinanceira(List<ReservaFinanceira> listaDeReservas,  MetaFinanceira meta) {
        super();
        this.listaDeReservas = listaDeReservas != null ? listaDeReservas : new ArrayList<>();
        this.meta = meta;
        calcularSaldo();
    }

    public ReservaFinanceira() {
        super();
        this.listaDeReservas = new ArrayList<>();
        this.meta = new MetaFinanceira();
        calcularSaldo();
    }

    @Override
    public double calcularSaldo() {
        total = 0;
        for (ReservaFinanceira reserva : listaDeReservas) {
            total += reserva.getValor();
        }
        return total;
    }

    public double getTotal() {
        return total;
    }

    public MetaFinanceira getMeta() {
        return meta;
    }

    public void setMeta(MetaFinanceira meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Reserva - "+ super.toString();
    }
}
