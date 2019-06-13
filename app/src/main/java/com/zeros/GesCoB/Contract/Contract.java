package com.zeros.GesCoB.Contract;


import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.view.View;

import com.zeros.GesCoB.Model.User;
import com.zeros.GesCoB.Model.Visit;


public interface Contract<T> {
    interface Users{
        boolean validate(String username, String password);
        void loginStart(com.zeros.GesCoB.Model.Response response);
        void loginStart();
    }

    void send();

    interface OnVisitListener{
        void onClick(View view, String document);
    }
    void activityNew();


    boolean insert(T t) throws IllegalAccessException;
    boolean update(T t,String table, ContentValues contentValues, String whereClausula, String[] whereArgs);
    boolean update(T t, String []WhereArgs, ContentValues contentValues);
    boolean delete(T t, String table, String clausula, String []where);
    Cursor query(T t, String query, String [] parametros);


}
