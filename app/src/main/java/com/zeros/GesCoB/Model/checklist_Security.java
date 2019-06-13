package com.zeros.GesCoB.Model;



import android.content.ContentValues;

import com.zeros.GesCoB.database.ConexionSQLiteHelper;
import com.zeros.GesCoB.database.QuerysLite;

public class checklist_Security extends QuerysLite<User> {

    private int id;
    private String descripcion;

    public checklist_Security() {
    }

    public checklist_Security(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    protected Long insert(User user, String table, String emptyAttribute, ConexionSQLiteHelper conn) throws IllegalAccessException {
        return null;
    }

    @Override
    protected int update(User user, String table, ContentValues contentValues, String whereClausula, String[] whereArgs, ConexionSQLiteHelper conn) {
        return 0;
    }
}
