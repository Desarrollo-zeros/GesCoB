package com.zeros.GesCoB.Model;

import android.content.ContentValues;

import com.zeros.GesCoB.database.ConexionSQLiteHelper;
import com.zeros.GesCoB.database.QuerysLite;

public class Security extends QuerysLite<Security> {

    private int id;
    private char respuesta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Security(){

    }

    public Security(int id, char respuesta) {
        this.id = id;
        this.respuesta = respuesta;
    }

    public char getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(char respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    protected Long insert(Security security, String table, String emptyAttribute, ConexionSQLiteHelper conn) throws IllegalAccessException {
        return null;
    }

    @Override
    protected int update(Security security, String table, ContentValues contentValues, String whereClausula, String[] whereArgs, ConexionSQLiteHelper conn) {
        return 0;
    }
}
