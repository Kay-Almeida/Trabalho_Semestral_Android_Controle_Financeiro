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

import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.Receita;
import kailaine.mobile.trabalho_semestral_android_controle_financeiro.model.ValorInvalidoException;

public class ReceitaDao implements ICRUDDao<Receita>,  IReceitaDAO{
    private final Context context;
    private GenericDAO gDao;
    private SQLiteDatabase database;

    public ReceitaDao(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Receita receita) throws SQLException {
        ContentValues contentValues = getContentValues(receita);
        database.insert("receita", null, contentValues);
    }

    @Override
    public int update(Receita receita) throws SQLException {
        ContentValues contentValues = getContentValues(receita);
        return database.update("receita", contentValues, "id = ?", new String[]{String.valueOf(receita.getId())});
    }

    @Override
    public void delete(Receita receita) throws SQLException {
        database.delete("receita", "id = ?", new String[]{String.valueOf(receita.getId())});
    }

    @Override
    public Receita buscarPorId(int id) throws SQLException {
        Cursor cursor = database.query("receita", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        Receita receita = null;
        if (cursor != null && cursor.moveToFirst()) {
            try {
                receita = getReceitaFromCursor(cursor);
            } catch (ValorInvalidoException e) {
                throw new RuntimeException(e);
            }
            cursor.close();
        }
        return receita;    }

    @Override
    public List<Receita> findAll() throws SQLException {
        List<Receita> receitas = new ArrayList<>();
        Cursor cursor = database.query("receita", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    receitas.add(getReceitaFromCursor(cursor));
                } catch (ValorInvalidoException e) {
                    throw new RuntimeException(e);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return receitas;    }

    @Override
    public IReceitaDAO open() throws SQLException {
        gDao = new GenericDAO(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    private static ContentValues getContentValues(Receita receita) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("valor", receita.getValor());
        contentValues.put("data", receita.getData().toString());
        return contentValues;
    }

    @SuppressLint("Range")
    private static Receita getReceitaFromCursor(Cursor cursor) throws ValorInvalidoException {
        Receita receita = new Receita();
        receita.setId(cursor.getInt(cursor.getColumnIndex("id")));
        receita.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
        return receita;

    }
}
