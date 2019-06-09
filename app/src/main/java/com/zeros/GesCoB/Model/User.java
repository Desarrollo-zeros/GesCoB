package com.zeros.GesCoB.Model;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zeros.GesCoB.database.ConexionSQLiteHelper;
import com.zeros.GesCoB.database.QuerysLite;

import java.util.List;

public class User extends QuerysLite<User> {
    private String username;
    private String password;
    private String tokent;
    private String email;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String tokent) {
        this.username = username;
        this.password = password;
        this.tokent = tokent;
    }

    public User(String username, String password, String tokent, String email) {
        this.username = username;
        this.password = password;
        this.tokent = tokent;
        this.email = email;
    }


    protected User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokent() {
        return tokent;
    }

    public void setTokent(String tokent) {
        this.tokent = tokent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public Long insert(User user, String table, String emptyAttribute, ConexionSQLiteHelper conn){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user.getUsername());
        contentValues.put("password",user.getPassword());
        SQLiteDatabase db = conn.getWritableDatabase();
        return db.insert(table, emptyAttribute, contentValues);
    }

    @Override
    public int update(User user, String table, ContentValues contentValues, String whereClausula, String[] whereArgs, ConexionSQLiteHelper conn) {
        SQLiteDatabase db = conn.getWritableDatabase();
        return  db.update(table,contentValues,whereClausula,whereArgs);
    }

    @Override
    public int count(List<User> list) {
        return super.count(list);
    }

    @Override
    public int count(Cursor cursor) {
        return super.count(cursor);
    }

    @Override
    public Cursor query(User user, String query, String[] parametros, ConexionSQLiteHelper conn) {
        return super.query(user, query, parametros, conn);
    }

    @Override
    public  int delete(User user,String table, String clausula, String []where, ConexionSQLiteHelper conn){
        return super.delete(user,table,clausula,where,conn);
    }

}


