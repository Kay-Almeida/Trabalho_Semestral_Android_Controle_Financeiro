package kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;

public interface IReceitaDAO {
    public IReceitaDAO open() throws SQLException;
    public void close();
}
