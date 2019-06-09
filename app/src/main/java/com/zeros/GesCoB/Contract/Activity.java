package com.zeros.GesCoB.Contract;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zeros.GesCoB.Activity.PersonActivity;
import com.zeros.GesCoB.Config;
import com.zeros.GesCoB.Model.User;
import com.zeros.GesCoB.Presenter.UserPresenter;
import com.zeros.GesCoB.Presenter.VisitPresenter;
import com.zeros.GesCoB.database.ConexionSQLiteHelper;

public  class Activity {

    UserPresenter userPresenter;
    VisitPresenter visitPresenter;
    public boolean validDialg = false;

    public Activity(){
        userPresenter = new UserPresenter();
        visitPresenter = new VisitPresenter();
    }

    public void start(String menssage, int time, int theme, Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context, theme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(menssage);
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, time);
    }


    public void menssage(String menssage,int color, Context context){
        Toast toast = Toast.makeText(context, menssage, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(color);
        toast.show();
    }

    public void changeTextViewNavigation(NavigationView navigationView, String text, int id){
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(id);
        navUsername.setText(text);
    }

    public void goNav(Context context, Class<?> cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public void goNav(Context context, Class<?> cls, User user, int id){
        Intent intent = new Intent(context, cls);
        intent.putExtra("username",user.getUsername());
        intent.putExtra("password",user.getPassword());
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    public void goNav(Context context, Class<?> cls, User user, int id, String document){
        Intent intent = new Intent(context, cls);
        intent.putExtra("username",user.getUsername());
        intent.putExtra("password",user.getPassword());
        intent.putExtra("id",id);
        intent.putExtra("document",document);
        context.startActivity(intent);
    }

    public void destroy(Context context, Class<?> cls,ConexionSQLiteHelper conn){
        userPresenter.delete(userPresenter,Config.DB_TABLE_USER,null,null,conn);
        visitPresenter.delete(visitPresenter,Config.DB_TABLE_VISIT,null,null,conn);
        this.goNav(context,cls);
    }

    public void confirmDialog(final Context context, final Class<?> cls, final User user, int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        goNav(context,cls,user,id);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void confirmDialog(final Context context, final Class<?> cls,final ConexionSQLiteHelper conn) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        destroy(context,cls,conn);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();
                    }
                })
                .show();

    }


    public void forgotPassword(final Context context){
        final EditText username = new EditText(context);
        final EditText old_password = new EditText(context);
        final EditText new_password = new EditText(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Prompt dialog demo !");
        builder.setMessage("What is your name?");
        builder.setCancelable(false);
        builder.setView(username);
        builder.setView(old_password);
        builder.setView(new_password);
        builder.setNeutralButton("Cambiar Contraseña", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context.getApplicationContext(), "Hello " + edtText.getText() + " ! how are you?", Toast.LENGTH_LONG).show();

            }
        });
        builder.show();
    }

}
