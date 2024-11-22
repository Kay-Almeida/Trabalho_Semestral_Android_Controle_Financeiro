package kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Receita;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.ReceitaDao;

public class ReceitaController implements IController<Receita>{

    private final ReceitaDao receitaDao;

    public ReceitaController(ReceitaDao receitaDao) {
        this.receitaDao = receitaDao;
    }

    @Override
    public void inserir(Receita receita) throws SQLException {
        if (receitaDao.open() == null) {
            receitaDao.open();
        }
        receitaDao.insert(receita);
        receitaDao.close();
    }

    @Override
    public void modificar(Receita receita) throws SQLException {
        if (receitaDao.open() == null) {
            receitaDao.open();
        }
        receitaDao.update(receita);
        receitaDao.close();
    }

    @Override
    public void deletar(Receita receita) throws SQLException {
        if (receitaDao.open() == null) {
            receitaDao.open();
        }
        receitaDao.delete(receita);
        receitaDao.close();
    }

    @Override
    public Receita buscarPorId(int id) throws SQLException {
        if (receitaDao.open() == null) {
            receitaDao.open();
        }
        return receitaDao.buscarPorId(id);    }

    @Override
    public List<Receita> listar() throws SQLException {
        if (receitaDao.open() == null) {
            receitaDao.open();
        }
        return receitaDao.findAll();    }
}

