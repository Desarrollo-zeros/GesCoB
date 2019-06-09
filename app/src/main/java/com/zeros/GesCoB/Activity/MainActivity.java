package com.zeros.GesCoB.Activity;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.widget.EditText;
import android.widget.Toast;


import com.zeros.GesCoB.Activity.Adapter.VisitAdapter;
import com.zeros.GesCoB.Config;
import com.zeros.GesCoB.Contract.Activity;
import com.zeros.GesCoB.Contract.Contract;
import com.zeros.GesCoB.Model.Visit;
import com.zeros.GesCoB.Presenter.ApiClient;
import com.zeros.GesCoB.Presenter.UserPresenter;
import com.zeros.GesCoB.Presenter.VisitPresenter;
import com.zeros.GesCoB.R;
import com.zeros.GesCoB.database.ConexionSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Contract<Visit>, Contract.OnNoteListener, View.OnClickListener {

     private static final String TAG = "ejemplo";
    public static final String EXTRA_MESSAGE = "com.zeros.GesCoB.person";

    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this);
    private final VisitPresenter visitaPresenter = new VisitPresenter();
    private int id;
    private String username, password;
    private RecyclerView reyclerViewVisit;
    private  Contract.OnNoteListener onNoteListener;
    Activity activity = new Activity();
    Context context;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        this.onNoteListener = this;
        this.id = getIntent().getExtras().getInt("id");
        this.username = getIntent().getExtras().getString("username");
        this.password = getIntent().getExtras().getString("password");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        activity.changeTextViewNavigation(navigationView,getString(R.string.login_email_hint)+": "+this.username,R.id.nameUserView);

        reyclerViewVisit = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        reyclerViewVisit.setHasFixedSize(true);

        // use a linear layout manager
        reyclerViewVisit.setLayoutManager(new LinearLayoutManager(this));
        string = getString(R.string.menssage_loader_view);
        activity.start(string,3000,R.style.Theme_AppCompat_DayNight_Dialog_Alert,context);
        Cursor cursor = this.query(visitaPresenter,"SELECT *FROM "+Config.DB_TABLE_VISIT,null);
        Toast.makeText(context,"Loader API..."+cursor.getCount(),Toast.LENGTH_SHORT).show();
        if(!existList()){
            //Toast.makeText(context,"Loader API...",Toast.LENGTH_SHORT).show();
            this.send();
        }else{
            Toast.makeText(context,"Loader DB...",Toast.LENGTH_SHORT).show();
           this.cargarList();
        }
    }



   public void cargarList(){
       Cursor cursor = this.query(visitaPresenter,"SELECT *FROM "+Config.DB_TABLE_VISIT,null);
       List<Visit> visitList = new ArrayList<>();
       while (cursor.moveToNext()){
           visitList.add( new VisitPresenter(
                   cursor.getString(14),
                   cursor.getString(13),
                   cursor.getString(12),
                   cursor.getString(17),
                   cursor.getString(3),
                   cursor.getString(4)
           ));
       }
       this.addList(visitList);
   }
    public boolean existList(){
        Cursor cursor = this.query(visitaPresenter, Config.Querys.QUERY_TABLE_VISIT_COUNT,null);
        return cursor.getCount() > 0;
    }

    public List<VisitPresenter> getData(List<Visit> visitList){
        List<VisitPresenter> visitaPresenters = new ArrayList<>();
        for (Visit v : visitList){
            visitaPresenters.add(new VisitPresenter(v.getCedula(),v.getCliente(),v.getDireccion(),v.getEstado_suministro(),v.getDepartamento(),v.getMunicipio()));
        }
        return visitaPresenters;
    }


    public void addList(List<Visit> visitList){
        reyclerViewVisit.setAdapter(new VisitAdapter(getData(visitList),onNoteListener));
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:{
                activity.goNav(context,MainActivity.class, new UserPresenter(this.username,this.password),this.id);
                break;
            }
            case  R.id.nav_close:{
                activity.start(getString(R.string.destroy_session),1000,R.style.Theme_AppCompat_DayNight_Dialog_Alert,context);
                activity.confirmDialog(context,LoginActivity.class,conn);
                break;
            }
            case R.id.nav_refresh_view :{
                this.refresh();
                break;
            }
            case R.id.nav_change_password :{
               // activity.forgotPassword(context);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.change_password, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context,R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                        .setTitle(getString(R.string.change_password_text))
                        .setCancelable(false);
                alertDialogBuilderUserInput.setView(mView);


                final EditText oldPassword = (EditText) mView.findViewById(R.id.oldPasswordText);
                final EditText newPassword1 = (EditText) mView.findViewById(R.id.newPassword1Text);
                final EditText newPassword2 = (EditText) mView.findViewById(R.id.newPassword2Text);
                alertDialogBuilderUserInput
                        .setPositiveButton(getString(R.string.change_password_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                if(oldPassword.getText().toString().equals(password)){
                                    if(newPassword1.getText().toString().equals(newPassword2.getText().toString()) && newPassword1.getText().toString().length() > 4){

                                    }else{
                                        activity.menssage(getString(R.string.password_no_equals), Color.RED, context);
                                    }
                                }else{
                                    activity.menssage(getString(R.string.error_password), Color.RED, context);
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel_button),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void refresh(){
        this.delete(visitaPresenter,Config.DB_TABLE_VISIT,null,null);
        this.send();
        activity.start(string,5000,R.style.Theme_AppCompat_DayNight_Dialog_Alert,context);
    }

    @Override
    public void send() {
        Call<List<Visit>> list =  ApiClient.getInstance().getApi().visit(new UserPresenter(this.username,this.password));
        list.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                addList(response.body());
                for(Visit visit : response.body()){
                    try {
                        insert(visit);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }



    @Override
    public void activityNew() {

    }


    @Override
    public boolean insert(Visit visit) throws IllegalAccessException {
        Long count = visit.insert(visit,Config.DB_TABLE_VISIT,null,conn);
        return count > 0;
    }

    @Override
    public boolean update(Visit visit, String[] WhereArgs, ContentValues contentValues) {
        return false;
    }

    @Override
    public boolean delete(Visit visit, String table, String clausula, String []where) {
       return visitaPresenter.delete(visit,table,clausula,where,conn) > 1;
    }

    @Override
    public Cursor query(Visit visit, String query, String[] parametros) {
        return visitaPresenter.query(visit,query,parametros,conn);
    }


    @Override
    public void onClick(View view,String document) {
        activity.goNav(context,PersonActivity.class, new UserPresenter(this.username,this.password),this.id,document);
    }

    @Override
    public void onClick(View v) {

    }
}

