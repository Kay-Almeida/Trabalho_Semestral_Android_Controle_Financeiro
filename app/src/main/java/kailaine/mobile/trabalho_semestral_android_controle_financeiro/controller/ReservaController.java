package kailaine.mobile.trabalho_semestral_android_controle_financeiro.controller;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import java.sql.SQLException;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ReservaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence.ReservaFinanceiraDao;

public class ReservaController implements IController<ReservaFinanceira>{
    private final ReservaFinanceiraDao reservaFinanceiraDao;

    public ReservaController(ReservaFinanceiraDao reservaFinanceiraDao) {
        this.reservaFinanceiraDao = reservaFinanceiraDao;
    }


    @Override
    public void inserir(ReservaFinanceira reservaFinanceira) throws SQLException {
        if (reservaFinanceiraDao.open() == null) {
            reservaFinanceiraDao.open();
        }
        reservaFinanceiraDao.insert(reservaFinanceira);
        reservaFinanceiraDao.close();
    }

    @Override
    public void modificar(ReservaFinanceira reservaFinanceira) throws SQLException {
        if (reservaFinanceiraDao.open() == null) {
            reservaFinanceiraDao.open();
        }
        reservaFinanceiraDao.update(reservaFinanceira);
        reservaFinanceiraDao.close();
    }

    @Override
    public void deletar(ReservaFinanceira reservaFinanceira) throws SQLException {
        if (reservaFinanceiraDao.open() == null) {
            reservaFinanceiraDao.open();
        }
        reservaFinanceiraDao.delete(reservaFinanceira);
        reservaFinanceiraDao.close();
    }

    @Override
    public ReservaFinanceira buscarPorId(int id) throws SQLException {
        if (reservaFinanceiraDao.open() == null) {
            reservaFinanceiraDao.open();
        }
        return reservaFinanceiraDao.buscarPorId(id);    }

    @Override
    public List<ReservaFinanceira> listar() throws SQLException {
        if (reservaFinanceiraDao.open() == null) {
            reservaFinanceiraDao.open();
        }
        return reservaFinanceiraDao.findAll();    }

    public List<ReservaFinanceira> buscarReservasPorMeta(int metaId) throws SQLException {
        if (reservaFinanceiraDao.open() == null) {
            reservaFinanceiraDao.open();
        }
        return reservaFinanceiraDao.buscarReservasPorMeta(metaId);
    }
    }
