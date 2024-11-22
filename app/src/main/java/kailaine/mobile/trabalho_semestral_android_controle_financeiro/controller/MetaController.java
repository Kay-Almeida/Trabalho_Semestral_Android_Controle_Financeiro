package kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.MetaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.MetaFinanceiraDao;

public class MetaController implements IController<MetaFinanceira>{
    private final MetaFinanceiraDao metaFinanceiraDao;

    public MetaController(MetaFinanceiraDao metaFinanceiraDao) {
        this.metaFinanceiraDao = metaFinanceiraDao;
    }


    @Override
    public void inserir(MetaFinanceira metaFinanceira) throws SQLException {
        if (metaFinanceiraDao.open() == null) {
            metaFinanceiraDao.open();
        }
        metaFinanceiraDao.insert(metaFinanceira);
        metaFinanceiraDao.close();
    }

    @Override
    public void modificar(MetaFinanceira metaFinanceira) throws SQLException {
        if (metaFinanceiraDao.open() == null) {
            metaFinanceiraDao.open();
        }
        metaFinanceiraDao.update(metaFinanceira);
        metaFinanceiraDao.close();
    }

    @Override
    public void deletar(MetaFinanceira metaFinanceira) throws SQLException {
        if (metaFinanceiraDao.open() == null) {
            metaFinanceiraDao.open();
        }
        metaFinanceiraDao.delete(metaFinanceira);
        metaFinanceiraDao.close();
    }

    @Override
    public MetaFinanceira buscarPorId(int id) throws SQLException {
        if (metaFinanceiraDao.open() == null) {
            metaFinanceiraDao.open();
        }
        return metaFinanceiraDao.buscarPorId(id);
    }

    @Override
    public List<MetaFinanceira> listar() throws SQLException {
        if (metaFinanceiraDao.open() == null) {
            metaFinanceiraDao.open();
        }
        return metaFinanceiraDao.findAll();       }

    public MetaFinanceira buscarPorObjeto(MetaFinanceira metaFinanceira) throws SQLException {
        if (metaFinanceiraDao.open() == null) {
            metaFinanceiraDao.open();
        }
        return metaFinanceiraDao.buscarPorObjeto(metaFinanceira);
    }
    }
