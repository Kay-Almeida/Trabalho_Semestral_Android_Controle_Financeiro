package kailaine.mobile.trabalho_semestral_android_controle_financeiro.model;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.time.LocalDate;

public abstract class Transacao {
    private int id;
    protected double valor;
    protected LocalDate data;

    public Transacao(double valor) {
        this.valor = valor;
        this.data = LocalDate.now();
    }

    public Transacao() {
        this.data = LocalDate.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return  "Valor: " + valor + " - Data: " + data;
    }
}
