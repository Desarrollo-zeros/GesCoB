package com.zeros.GesCoB.Presenter;

import android.content.ContentValues;
import android.database.Cursor;

import com.zeros.GesCoB.Model.User;
import com.zeros.GesCoB.database.ConexionSQLiteHelper;

public class UserPresenter extends User {

    public UserPresenter(String username, String password) {
        super(username, password);
    }

    public UserPresenter(String username, String password, String tokent) {
        super(username, password,tokent);
    }

    public UserPresenter(String username, String password, String tokent, String email) {
        super(username, password,tokent,email);
    }

    public UserPresenter() {
    }

    @Override
    public int update(User user, String table, ContentValues contentValues, String whereClausula, String[] whereArgs, ConexionSQLiteHelper conn) {
        return super.update(user, table, contentValues, whereClausula, whereArgs, conn);
    }
}
