package com.zeros.GesCoB.Activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zeros.GesCoB.Activity.Adapter.VisitAdapter;
import com.zeros.GesCoB.Config;
import com.zeros.GesCoB.Contract.Activity;
import com.zeros.GesCoB.Contract.Contract;
import com.zeros.GesCoB.Model.Visit;
import com.zeros.GesCoB.Presenter.ApiClient;
import com.zeros.GesCoB.Presenter.ConfigPresenter;
import com.zeros.GesCoB.Presenter.UserPresenter;
import com.zeros.GesCoB.Presenter.VisitPresenter;
import com.zeros.GesCoB.R;
import com.zeros.GesCoB.database.ConexionSQLiteHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Contract<Visit>, Contract.OnVisitListener, View.OnClickListener, VisitAdapter.VisitAdapterListener {

    ConexionSQLiteHelper conn;
    private final VisitPresenter visitaPresenter = new VisitPresenter();
    private int id;
    private String username, password;
    private RecyclerView reyclerViewVisit;
    private Contract.OnVisitListener onVisitListener;
    Activity activity = new Activity(this);
    Context context;
    String string;
    private VisitAdapter mAdapter;
    private SearchView searchView;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        conn = new ConexionSQLiteHelper(this);
        setContentView(R.layout.activity_main);
        this.onVisitListener = this;
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
        activity.changeTextViewNavigation(navigationView, getString(R.string.login_email_hint) + ": " + this.username, R.id.nameUserView);

        reyclerViewVisit = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        reyclerViewVisit.setHasFixedSize(true);

        // use a linear layout manager
        reyclerViewVisit.setLayoutManager(new LinearLayoutManager(this));

        // white background notification bar
        whiteNotificationBar(reyclerViewVisit);

        string = getString(R.string.menssage_loader_view);
        activity.start(string, 3000, R.style.Theme_AppCompat_DayNight_Dialog_Alert, context);
        Cursor cursor = this.query(visitaPresenter, "SELECT *FROM " + Config.DB_TABLE_VISIT, null);
        Toast.makeText(context, "Loader API..." + cursor.getCount(), Toast.LENGTH_SHORT).show();
        if (!existList()) {
            //Toast.makeText(context,"Loader API...",Toast.LENGTH_SHORT).show();
            this.send();
        } else {
            Toast.makeText(context, "Loader DB...", Toast.LENGTH_SHORT).show();
            this.cargarList();
        }
        activity.getLocation();
        activity.gerPermision(Manifest.permission.READ_PHONE_STATE, 0x1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity.locationManager.removeUpdates(activity.locationListener);
    }

   @RequiresApi(api = Build.VERSION_CODES.N)
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
                   cursor.getString(4),
                   cursor.getString(6)
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
            visitaPresenters.add(new VisitPresenter(v.getCedula(),v.getCliente(),v.getDireccion(),v.getEstado_suministro(),v.getDepartamento(),v.getMunicipio(),v.getBarrio()));
        }
        return visitaPresenters;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addList(List<Visit> visitList){
        visitList.sort(new Comparator<Visit>() {
            @Override
            public int compare(Visit o1, Visit o2) {
                return new Integer(o1.getOrden()).compareTo(new Integer(o2.getOrden()));
            }
        });
        mAdapter = new VisitAdapter(getData(visitList),onVisitListener,this);
        reyclerViewVisit.setAdapter(mAdapter);
    }


    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

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
                activity.confirmDialog(LoginActivity.class,conn);
                break;
            }
            case R.id.nav_refresh_view :{
                this.refresh();
                break;
            }
            case R.id.nav_refresh_vonfig:{
                this.activity.loader_config(new UserPresenter(username,password,"12345"),getString(R.string.config_auth),conn);
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
                            @Override public void onClick(DialogInterface dialogBox, int id) {}
                        })
                        .setNegativeButton(getString(R.string.cancel_button),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

                Button button = alertDialogAndroid.getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                        if(oldPassword.getText().toString().equals(password)){
                            oldPassword.setError(null,null);
                            if(newPassword1.getText().toString().equals(newPassword2.getText().toString()) && newPassword1.getText().toString().length() > 4){
                                newPassword1.setError(null,null);
                                newPassword2.setError(null,null);

                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder
                                        .setMessage(getString(R.string.question_modal))
                                        .setPositiveButton(getString(R.string.success_button),  new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int id) {
                                                Call<com.zeros.GesCoB.Model.Response> list = ApiClient.getInstance().getApi().changePassword("12345",username,password,newPassword1.getText().toString());
                                                list.enqueue(new Callback<com.zeros.GesCoB.Model.Response>() {
                                                    @Override
                                                    public void onResponse(Call<com.zeros.GesCoB.Model.Response> call, Response<com.zeros.GesCoB.Model.Response> response) {
                                                        String string = getString(R.string.menssage_auth);
                                                        activity.start(string,2000,R.style.Theme_AppCompat_DayNight_Dialog_Alert,context);
                                                        if(!response.body().isError()){
                                                            activity.menssage(getString(R.string.change_password_success),Color.GREEN,context);
                                                            ContentValues contentValues = new ContentValues();
                                                            contentValues.put("password",newPassword1.getText().toString());
                                                           int i = new UserPresenter().update(new UserPresenter(username,newPassword1.getText().toString()),Config.DB_TABLE_USER,contentValues,null,null,conn);
                                                           if(i>0)
                                                               Toast.makeText(context,"Password Update",Toast.LENGTH_LONG).show();
                                                        }
                                                        alertDialogAndroid.dismiss();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<com.zeros.GesCoB.Model.Response> call, Throwable t) {
                                                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                                        call.cancel();
                                                        alertDialogAndroid.dismiss();
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                                alertDialogAndroid.dismiss();
                                            }
                                        })
                                        .show();
                            }else{
                                activity.menssage(getString(R.string.password_no_equals), Color.RED, context);
                                newPassword1.setError(getString(R.string.password_no_equals));
                                newPassword2.setError(getString(R.string.password_no_equals));
                            }
                        }else{
                            oldPassword.setError(getString(R.string.error_password));
                            activity.menssage(getString(R.string.error_password), Color.RED, context);
                        }
                    }
                });
                break;
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
        Call<List<Visit>> list =  ApiClient.getInstance().getApi().visit(new UserPresenter(this.username,this.password,"12345"));
        list.enqueue(new Callback<List<Visit>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
    public boolean update(Visit visit, String table, ContentValues contentValues, String whereClausula, String[] whereArgs) {
        return false;
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
        activity.goNav(PersonActivity.class, new UserPresenter(this.username,this.password),this.id,document);
    }

    @Override
    public void onClick(View v) {

    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(VisitPresenter visitPresenter) {
        Toast.makeText(getApplicationContext(), "Selected: " + visitPresenter.getCedula() + ", " + visitPresenter.getCliente(), Toast.LENGTH_LONG).show();
    }
}

