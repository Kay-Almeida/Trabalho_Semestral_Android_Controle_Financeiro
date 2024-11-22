package kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;
import java.util.List;

public interface ICRUDDao<T> {

    public void insert(T t) throws SQLException;
    public int update(T t) throws SQLException;
    public void delete(T t) throws SQLException;
    T buscarPorId(int id) throws SQLException;
    public List<T> findAll() throws SQLException;

}
