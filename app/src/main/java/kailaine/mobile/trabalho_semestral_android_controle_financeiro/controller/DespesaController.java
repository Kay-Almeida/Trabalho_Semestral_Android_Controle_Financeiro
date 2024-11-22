package kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Despesa;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.DespesaDao;

public class DespesaController implements IController<Despesa>{

    private final DespesaDao despesaDao;

    public DespesaController(DespesaDao despesaDao) {
        this.despesaDao = despesaDao;
    }

    @Override
    public void inserir(Despesa despesa) throws SQLException {
        if (despesaDao.open() == null) {
            despesaDao.open();
        }
        despesaDao.insert(despesa);
        despesaDao.close();
    }

    @Override
    public void modificar(Despesa despesa) throws SQLException {
        if (despesaDao.open() == null) {
            despesaDao.open();
        }
        despesaDao.update(despesa);
        despesaDao.close();
    }

    @Override
    public void deletar(Despesa despesa) throws SQLException {
        if (despesaDao.open() == null) {
            despesaDao.open();
        }
        despesaDao.delete(despesa);
        despesaDao.close();
    }

    @Override
    public Despesa buscarPorId(int id) throws SQLException {
        if (despesaDao.open() == null) {
            despesaDao.open();
        }
        return despesaDao.buscarPorId(id);    }

    @Override
    public List<Despesa> listar() throws SQLException {
        if (despesaDao.open() == null) {
            despesaDao.open();
        }
        return despesaDao.findAll();    }
}
