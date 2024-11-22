package kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;
import java.util.List;

public interface IController <T>{
    public void inserir(T t) throws SQLException;
    public void modificar(T t) throws SQLException;
    public void deletar(T t) throws SQLException;
    T buscarPorId(int id) throws SQLException;
    public List<T> listar() throws SQLException;
}
