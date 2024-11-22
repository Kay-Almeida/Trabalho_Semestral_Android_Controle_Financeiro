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
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ValorInvalidoException;

public class MetaFinanceiraDao implements ICRUDDao<MetaFinanceira>, IMetaFinanceiraDAO{
    private final Context context;
    private GenericDAO gDao;
    private SQLiteDatabase database;

    public MetaFinanceiraDao(Context context) {
        this.context = context;
    }

    @Override
    public void insert(MetaFinanceira metaFinanceira) throws SQLException {
        ContentValues contentValues = getContentValues(metaFinanceira);
        database.insert("metaFinanceira", null, contentValues);

    }

    @Override
    public int update(MetaFinanceira metaFinanceira) throws SQLException {
        ContentValues contentValues = getContentValues(metaFinanceira);
        return database.update("metaFinanceira", contentValues, "id = ?", new String[]{String.valueOf(metaFinanceira.getId())});
    }

    @Override
    public void delete(MetaFinanceira metaFinanceira) throws SQLException {
        database.delete("metaFinanceira", "id = ?", new String[]{String.valueOf(metaFinanceira.getId())});
    }

    @Override
    public MetaFinanceira buscarPorId(int id) throws SQLException {
        return null;
    }


    @SuppressLint("Range")
    public MetaFinanceira buscarPorObjeto(MetaFinanceira metaFinanceira) throws SQLException {
        String sql = "SELECT m.id, m.nome, m.valor, SUM(r.valor) AS valor_reserva " +
                "FROM metaFinanceira m " +
                "LEFT JOIN reserva r ON r.metaFinanceira = m.id "+
                "WHERE m.id = ? " +
                "GROUP BY m.id";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(metaFinanceira.getId())});
        MetaFinanceira meta = null;
        if (cursor != null && cursor.moveToFirst()) {
            meta = new MetaFinanceira();
            meta.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            meta.setId(cursor.getInt(cursor.getColumnIndex("id")));
            meta.setValorMeta(cursor.getDouble(cursor.getColumnIndex("valor")));
            meta.setTotalReserva(cursor.getDouble(cursor.getColumnIndex("valor_reserva")));
        }

        if (cursor != null) {
            cursor.close();
        }
        return meta;

    }

    @Override
    public List<MetaFinanceira> findAll() throws SQLException {
        List<MetaFinanceira> metas = new ArrayList<>();
        Cursor cursor = database.query("metaFinanceira", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    metas.add(getMetaFinanceiraFromCursor(cursor));
                } catch (ValorInvalidoException e) {
                    throw new RuntimeException(e);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return metas;
    }

    @Override
    public IMetaFinanceiraDAO open() throws SQLException {
        gDao = new GenericDAO(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    private static ContentValues getContentValues(MetaFinanceira metaFinanceira) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", metaFinanceira.getNome());
        contentValues.put("valor", metaFinanceira.getValorMeta());
        return contentValues;
    }

    @SuppressLint({"Range", "NewApi"})
    private static MetaFinanceira getMetaFinanceiraFromCursor(Cursor cursor) throws ValorInvalidoException {
        MetaFinanceira metaFinanceira = new MetaFinanceira();
        metaFinanceira.setId(cursor.getInt(cursor.getColumnIndex("id")));
        metaFinanceira.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        metaFinanceira.setValorMeta(cursor.getDouble(cursor.getColumnIndex("valor")));
        return metaFinanceira;
    }

}
