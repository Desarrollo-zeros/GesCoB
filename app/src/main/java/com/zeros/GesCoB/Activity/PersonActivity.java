package com.zeros.GesCoB.Activity;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zeros.GesCoB.Activity.Adapter.VisitAdapter;
import com.zeros.GesCoB.Config;
import com.zeros.GesCoB.Contract.Activity;
import com.zeros.GesCoB.Contract.Contract;
import com.zeros.GesCoB.Model.User;
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

public class PersonActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Contract<Visit>, Contract.OnNoteListener, View.OnClickListener {

     private static final String TAG = "ejemplo";

    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this);
    private final VisitPresenter visitaPresenter = new VisitPresenter();
    private String document;
    private  OnNoteListener onNoteListener;
    Activity activity = new Activity();
    Context context;
    private int id;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_person);
        this.onNoteListener = this;
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
        this.document = getIntent().getExtras().getString("document");
        this.id = getIntent().getExtras().getInt("id");
        this.username = getIntent().getExtras().getString("username");
        this.password = getIntent().getExtras().getString("password");

        Cursor cursor = this.query(new Visit(),"SELECT *FROM "+Config.DB_TABLE_VISIT+" WHERE cedula = "+getIntent().getExtras().getString("document")+"",null);
        cursor.moveToFirst();
        Toast.makeText(this,"Cedula: "+ cursor.getString(14)+" Nombre"+cursor.getString(13),Toast.LENGTH_SHORT).show();
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
                activity.confirmDialog(context,MainActivity.class, new UserPresenter(this.username,this.password),this.id);
                break;
            }
            case R.id.nav_close:{
                activity.start(getString(R.string.destroy_session),1000,R.style.Theme_AppCompat_DayNight_Dialog_Alert,context);
                activity.destroy(context,LoginActivity.class,conn);
                break;
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void send() {

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
        Log.d("EJemplo", document);
    }

    @Override
    public void onClick(View v) {

    }
}

