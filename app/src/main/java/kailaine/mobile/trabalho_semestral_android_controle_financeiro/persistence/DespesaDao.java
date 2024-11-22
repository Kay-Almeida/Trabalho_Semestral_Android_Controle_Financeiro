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

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Despesa;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ValorInvalidoException;

public class DespesaDao implements ICRUDDao<Despesa>, IDespesaDAO {
    private final Context context;
    private GenericDAO gDao;
    private SQLiteDatabase database;

    public DespesaDao(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Despesa despesa) throws SQLException {
        ContentValues contentValues = getContentValues(despesa);
        database.insert("despesa", null, contentValues);
    }

    @Override
    public int update(Despesa despesa) throws SQLException {
        ContentValues contentValues = getContentValues(despesa);
        return database.update("despesa", contentValues, "id = ?", new String[]{String.valueOf(despesa.getId())});
    }

    @Override
    public void delete(Despesa despesa) throws SQLException {
        database.delete("despesa", "id = ?", new String[]{String.valueOf(despesa.getId())});
    }

    @Override
    public Despesa buscarPorId(int id) throws SQLException {
        Cursor cursor = database.query("despesa", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Despesa despesa = null;
        if (cursor != null && cursor.moveToFirst()) {
            try {
                despesa = getDespesaFromCursor(cursor);
            } catch (ValorInvalidoException e) {
                throw new RuntimeException(e);
            } finally {
                cursor.close();
            }
        }
        return despesa;
    }

    @Override
    public List<Despesa> findAll() throws SQLException {
        List<Despesa> despesas = new ArrayList<>();
        Cursor cursor = database.query("despesa", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    despesas.add(getDespesaFromCursor(cursor));
                } catch (ValorInvalidoException e) {
                    throw new RuntimeException(e);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return despesas;
    }

    @Override
    public IDespesaDAO open() throws SQLException {
        gDao = new GenericDAO(context);
        database = gDao.getWritableDatabase();
        return this;    }

    @Override
    public void close() {
        gDao.close();
    }
    private static ContentValues getContentValues(Despesa despesa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("valor", despesa.getValor());
        contentValues.put("data", despesa.getData().toString());
        return contentValues;
    }
    @SuppressLint({"Range", "NewApi"})
    private static Despesa getDespesaFromCursor(Cursor cursor) throws ValorInvalidoException {
        Despesa despesa = new Despesa();
        despesa.setId(cursor.getInt(cursor.getColumnIndex("id")));
        despesa.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
        return despesa;
    }
}
