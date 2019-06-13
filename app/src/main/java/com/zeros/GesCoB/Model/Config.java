package com.zeros.GesCoB.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zeros.GesCoB.database.ConexionSQLiteHelper;
import com.zeros.GesCoB.database.QuerysLite;

import java.lang.reflect.Field;
import java.util.List;

public class Config extends QuerysLite<Config> {


    private  String cod_gestion;
    private String desc_gestion;
    private String cod_resultado;
    private String desc_resultado;
    private String cod_anomalia;
    private String desc_anomalia;


    public Config(){

    }

    public Config(String cod_gestion, String desc_gestion, String cod_resultado, String desc_resultado, String cod_anomalia, String desc_anomalia) {
        this.cod_gestion = cod_gestion;
        this.desc_gestion = desc_gestion;
        this.cod_resultado = cod_resultado;
        this.desc_resultado = desc_resultado;
        this.cod_anomalia = cod_anomalia;
        this.desc_anomalia = desc_anomalia;
    }

    @Override
    public Long insert(Config config, String table, String emptyAttribute, ConexionSQLiteHelper conn) throws IllegalAccessException {
        ContentValues contentValues = new ContentValues();
        Field[] field =  config.getClass().getDeclaredFields();
        SQLiteDatabase db = conn.getWritableDatabase();
        for(Field f : field){
            Object v = f.get(config);
            if(!f.getName().equals("$change") && !f.getName().equals("serialVersionUID")){
                contentValues.put(f.getName(), String.valueOf(v));
            }
        }
        return  db.insert(table, emptyAttribute, contentValues);
    }

    @Override
    public int update(Config config, String table, ContentValues contentValues, String whereClausula, String[] whereArgs, ConexionSQLiteHelper conn) {
        return 0;
    }

    @Override
    public int count(List<Config> list) {
        return super.count(list);
    }

    @Override
    public int count(Cursor cursor) {
        return super.count(cursor);
    }

    @Override
    public Cursor query(Config config, String query, String[] parametros, ConexionSQLiteHelper conn) {
        return super.query(config, query, parametros, conn);
    }

    @Override
    public  int delete(Config config,String table, String clausula, String []where, ConexionSQLiteHelper conn){
        return super.delete(config,table,clausula,where,conn);
    }

    public String getCod_gestion() {
        return cod_gestion;
    }

    public void setCod_gestion(String cod_gestion) {
        this.cod_gestion = cod_gestion;
    }

    public String getDesc_gestion() {
        return desc_gestion;
    }

    public void setDesc_gestion(String desc_gestion) {
        this.desc_gestion = desc_gestion;
    }

    public String getCod_resultado() {
        return cod_resultado;
    }

    public void setCod_resultado(String cod_resultado) {
        this.cod_resultado = cod_resultado;
    }

    public String getDesc_resultado() {
        return desc_resultado;
    }

    public void setDesc_resultado(String desc_resultado) {
        this.desc_resultado = desc_resultado;
    }

    public String getCod_anomalia() {
        return cod_anomalia;
    }

    public void setCod_anomalia(String cod_anomalia) {
        this.cod_anomalia = cod_anomalia;
    }

    public String getDesc_anomalia() {
        return desc_anomalia;
    }

    public void setDesc_anomalia(String desc_anomalia) {
        this.desc_anomalia = desc_anomalia;
    }


    @Override
    public String toString() {
        return "Config{" +
                "cod_gestion='" + cod_gestion + '\'' +
                ", desc_gestion='" + desc_gestion + '\'' +
                ", cod_resultado='" + cod_resultado + '\'' +
                ", desc_resultado='" + desc_resultado + '\'' +
                ", cod_anomalia='" + cod_anomalia + '\'' +
                ", desc_anomalia='" + desc_anomalia + '\'' +
                '}';
    }
}
