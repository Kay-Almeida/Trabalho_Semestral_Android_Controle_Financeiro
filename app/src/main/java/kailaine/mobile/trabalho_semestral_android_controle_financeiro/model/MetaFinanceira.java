package kailaine.mobile.trabalho_semestral_android_controle_financeiro.model;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.util.List;

public class MetaFinanceira  {
    private int id;
    private String nome;
    private double valorMeta;
    private double totalReserva;

    private List<ReservaFinanceira> reservas;

    public MetaFinanceira(String nome, double valorMeta) {
        this.nome = nome;
        this.valorMeta = valorMeta;
        this.totalReserva = 0;
    }

    public MetaFinanceira(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(double valorMeta) {
        this.valorMeta = valorMeta;
    }

    public double getTotalReserva() {
        return totalReserva;
    }

    public void setTotalReserva(double totalReserva) {
        this.totalReserva = totalReserva;
    }


    public double calcularProgresso() {
        return valorMeta > 0 ? (totalReserva / valorMeta) * 100 : 0;
    }

    @Override
    public String toString() {
        return  nome + " - Meta: " + valorMeta + " - Progresso: " + calcularProgresso() + "%";
    }
}

