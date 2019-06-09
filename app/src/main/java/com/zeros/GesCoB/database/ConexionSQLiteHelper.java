package com.zeros.GesCoB.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.SQLException;

import com.zeros.GesCoB.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;




public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    private static String DB_NAME = Config.DB_NAME;

    public ConexionSQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(Config.CREATE_TABLE_USER);
       db.execSQL(Config.CREATE_TABLE_VISIT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Config.DROP_TABLE_USER);
        db.execSQL(Config.DROP_TABLE_VISIT);
        this.onCreate(db);
    }



}