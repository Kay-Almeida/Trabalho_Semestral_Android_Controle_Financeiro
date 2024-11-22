package kailaine.mobile.trabalho_semestral_android_controle_financeiro.persistence;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.MetaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ReservaFinanceira;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ValorInvalidoException;

public class ReservaFinanceiraDao implements ICRUDDao<ReservaFinanceira>, IReservaFinanceiraDAO  {
    private final Context context;
    private GenericDAO gDao;
    private SQLiteDatabase database;

    public ReservaFinanceiraDao(Context context) {
        this.context = context;
    }

    @Override
    public void insert(ReservaFinanceira reserva) throws SQLException {
        ContentValues contentValues = getContentValues(reserva);
        database.insert("reserva", null, contentValues);
    }

    @Override
    public int update(ReservaFinanceira reserva) throws SQLException {
        ContentValues contentValues = getContentValues(reserva);
        return database.update("reserva", contentValues, "id = ?", new String[]{String.valueOf(reserva.getId())});
    }

    @Override
    public void delete(ReservaFinanceira reserva) throws SQLException {
        database.delete("reserva", "id = ?", new String[]{String.valueOf(reserva.getId())});
    }

    @Override
    public ReservaFinanceira buscarPorId(int id) throws SQLException {
        String query = "SELECT reserva.*, metaFinanceira.nome AS nome_meta " +
                "FROM reserva " +
                "LEFT JOIN metaFinanceira ON reserva.metaFinanceira = metaFinanceira.id " +
                "WHERE reserva.id = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        ReservaFinanceira reserva = null;
        if (cursor != null && cursor.moveToFirst()) {
            try {
                reserva = getReservaFromCursor(cursor);
            } catch (ValorInvalidoException e) {
                throw new RuntimeException(e);
            }
            cursor.close();
        }
        return reserva;    }

    @SuppressLint("Range")
    public List<ReservaFinanceira> findAll() throws SQLException {
        List<ReservaFinanceira> reservas = new ArrayList<>();
        String sql = "SELECT r.id, r.valor, r.metaFinanceira, m.nome AS nome_meta " +
                "FROM reserva r " +
                "LEFT JOIN metaFinanceira m ON r.metaFinanceira = m.id";

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    ReservaFinanceira reserva = new ReservaFinanceira();
                    reserva.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    reserva.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));

                    int metaId = cursor.getInt(cursor.getColumnIndex("metaFinanceira"));
                    if (metaId != 0) {
                        MetaFinanceira meta = new MetaFinanceira();
                        meta.setId(metaId);
                        meta.setNome(cursor.getString(cursor.getColumnIndex("nome_meta")));
                        reserva.setMeta(meta);
                    }

                    reservas.add(reserva);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } while (cursor.moveToNext());
            cursor.close();
        }
        return reservas;

    }

    @Override
    public IReservaFinanceiraDAO open() throws SQLException {
        gDao = new GenericDAO(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }
    private static ContentValues getContentValues(ReservaFinanceira reserva) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", reserva.getData().toString());
        contentValues.put("valor", reserva.getValor());
        contentValues.put("metaFinanceira", reserva.getMeta().getId());
        return contentValues;
    }

    @SuppressLint({"Range", "NewApi"})
    private static ReservaFinanceira getReservaFromCursor(Cursor cursor) throws ValorInvalidoException {
        ReservaFinanceira reserva = new ReservaFinanceira();
        reserva.setId(cursor.getInt(cursor.getColumnIndex("id")));
        reserva.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
        int metaId = cursor.getInt(cursor.getColumnIndex("metaFinanceira"));
        if (metaId != 0) {
            MetaFinanceira meta = new MetaFinanceira();
            meta.setId(metaId);
            meta.setNome(cursor.getString(cursor.getColumnIndex("nome_meta")));
            reserva.setMeta(meta);
        }
        return reserva;
    }

    public List<ReservaFinanceira> buscarReservasPorMeta(int metaId) {
        List<ReservaFinanceira> reservas = new ArrayList<>();
        Cursor cursor = database.query(
                "reserva",
                null,
                "metaFinanceira = ?",
                new String[]{String.valueOf(metaId)},
                null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ReservaFinanceira reserva = new ReservaFinanceira();
                reserva.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                reserva.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
                reserva.getData();
                MetaFinanceira metaFinanceira = new MetaFinanceira();
                metaFinanceira.setId(metaId);
                reserva.setMeta(metaFinanceira);
                reservas.add(reserva);
            }
            cursor.close();
        }
        return reservas;
    }
}
