package com.zeros.GesCoB.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class QuerysLite<T>{


    protected abstract Long insert(T t, String table, String emptyAttribute, ConexionSQLiteHelper conn) throws IllegalAccessException;

    protected abstract int update(T t,String table, ContentValues contentValues,String whereClausula, String []whereArgs, ConexionSQLiteHelper conn);

    protected  int delete(T t,String table, String whereClausula, String []whereArgs, ConexionSQLiteHelper conn){
        SQLiteDatabase db = conn.getWritableDatabase();
        return db.delete(table,whereClausula,whereArgs);
    }
    protected Cursor query(T t, String query, String [] parametros, ConexionSQLiteHelper conn){
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,parametros);
        return cursor;
    }

    protected int count(List<T> list){
        return  list.size();
    }
    protected int count(Cursor cursor){
        return cursor.getCount();
    }



}
