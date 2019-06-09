package com.zeros.GesCoB.Presenter;

import android.database.Cursor;

import com.zeros.GesCoB.Model.User;

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


}
