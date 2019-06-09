package com.zeros.GesCoB.Activity;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.zeros.GesCoB.Config;
import com.zeros.GesCoB.Contract.Activity;
import com.zeros.GesCoB.Contract.Contract;
import com.zeros.GesCoB.Model.*;
import com.zeros.GesCoB.R;
import com.zeros.GesCoB.Presenter.*;
import com.zeros.GesCoB.database.ConexionSQLiteHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Contract<User>, Contract.Users {


    Button loginButton;
    TextView _username, _password;
    private final UserPresenter userPresenter = new UserPresenter();
    public static final String EXTRA_MESSAGE = "com.zeros.GesCoB.panel";
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this);
    private UserPresenter user;
    private int id;
    private Context context;


    Activity activity = new Activity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);
        _username = (TextView) findViewById(R.id.editTextUsername);
        _password = (TextView) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.cirLoginButton);
        this.loginStart();
        loginButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        boolean valid = validate(_username.getText().toString().toLowerCase(),_password.getText().toString().toLowerCase());
        if(valid){
            this.user = new UserPresenter(_username.getText().toString().toLowerCase().trim(),_password.getText().toString().toLowerCase().trim());
            this.send();
        }
    }

    @Override
    public boolean validate(String username, String password) {
        boolean valid = true;
        if(username.isEmpty() || username.length()< 4){
            valid = false;
            String string = getString(R.string.error_username);
            _username.setError(string);
        }else{
            _username.setError(null,null);
        }

        if(password.isEmpty() || password.length()< 4){
            valid = false;
            String string = getString(R.string.error_password);
            _password.setError(string);
        }else{
            _password.setError(null,null);
        }
        return valid;
    }



    @Override
    public void send() {
        Call<com.zeros.GesCoB.Model.Response> list =  ApiClient.getInstance().getApi().login(user);
        list.enqueue(new Callback<com.zeros.GesCoB.Model.Response>() {
            @Override
            public void onResponse(Call<com.zeros.GesCoB.Model.Response> call, Response<com.zeros.GesCoB.Model.Response> response) {
                String string = getString(R.string.menssage_auth);
                activity.start(string,3000,R.style.Theme_AppCompat_DayNight_Dialog_Alert,context);
                loginButton.setEnabled(true);
                setResult(RESULT_OK, null);
                loginStart(response.body());
            }
            @Override
            public void onFailure(Call<com.zeros.GesCoB.Model.Response> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }





    @Override
    public boolean insert(User user){
        boolean valid = false;
        String [] parametros = {user.getUsername(),user.getPassword()};
        Cursor cursor = this.query(user,Config.Querys.QUERY_TABLE_USER_EXITS,parametros);
        if(cursor.getCount() == 0){
            Long result = userPresenter.insert(user,Config.DB_TABLE_USER, Config.DB_TABLE_USER_ID,conn);
            valid = (result > 0);
        }else{
            this.id = cursor.getInt(0);
            valid = true;
        }
        return valid;
    }

    @Override
    public boolean update(User user, String []WhereArgs, ContentValues contentValues) {
        int rows = userPresenter.update(user,Config.DB_TABLE_USER,contentValues,Config.DB_TABLE_USER_ID+"=?",WhereArgs,conn);
        return  rows > 0;
    }

    @Override
    public boolean delete(User user,String table, String clausula, String []where) {
        userPresenter.delete(user,table,clausula,where,conn);
        return true;
    }

    @Override
    public Cursor query(User user, String query, String [] parametros) {
         return userPresenter.query(user,query,parametros,conn);
    }


    @Override
    public void loginStart(){
        Cursor cursor = userPresenter.query(user,"SELECT *FROM user",null,conn);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            user = new UserPresenter(cursor.getString(1),cursor.getString(2));
            activity.menssage(getString(R.string.menssage_auth_success)+" "+user.getUsername(),Color.CYAN,this);
            activity.start(getString(R.string.menssage_auth)+" please wait, "+user.getUsername(),3000,R.style.Theme_AppCompat_DayNight_Dialog_Alert,context);
            this.id = cursor.getInt(0);
            this.activityNew();
        }
    }

    @Override
    public void activityNew(){
        activity.goNav(context,MainActivity.class,user,id);
    }

    @Override
    public void loginStart(com.zeros.GesCoB.Model.Response resp) {
        if(resp.isError()){
            String string = getString(R.string.menssage_auth_alert);
            Toast toast = Toast.makeText(getBaseContext(), string, Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.RED);
            toast.show();
        }else{
            this.insert(this.user);
            this.activityNew();
        }
    }
}
