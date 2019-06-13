package com.zeros.GesCoB.Contract;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zeros.GesCoB.Config;
import com.zeros.GesCoB.Model.User;
import com.zeros.GesCoB.Presenter.ApiClient;
import com.zeros.GesCoB.Presenter.ConfigPresenter;
import com.zeros.GesCoB.Presenter.UserPresenter;
import com.zeros.GesCoB.Presenter.VisitPresenter;
import com.zeros.GesCoB.R;
import com.zeros.GesCoB.database.ConexionSQLiteHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity extends AppCompatActivity implements LocationListener {

    UserPresenter userPresenter;
    VisitPresenter visitPresenter;
    public boolean validDialg = false;
    public LocationListener locationListener = this;

    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;
    final Context context;
    public String imei;

    public Activity(Context context) {
        userPresenter = new UserPresenter();
        visitPresenter = new VisitPresenter();
        this.context = context;
    }

    public void start(String menssage, int time, int theme, Context context) {
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


    public void menssage(String menssage, int color, Context context) {
        Toast toast = Toast.makeText(context, menssage, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(color);
        toast.show();
    }

    public void changeTextViewNavigation(NavigationView navigationView, String text, int id) {
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(id);
        navUsername.setText(text);
    }

    public void goNav(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public void goNav(Context context, Class<?> cls, User user, int id) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("username", user.getUsername());
        intent.putExtra("password", user.getPassword());
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    public void goNav(Class<?> cls, User user, int id, String document) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("username", user.getUsername());
        intent.putExtra("password", user.getPassword());
        intent.putExtra("id", id);
        intent.putExtra("document", document);
        context.startActivity(intent);
    }

    public void destroy(Class<?> cls, ConexionSQLiteHelper conn) {
        userPresenter.delete(userPresenter, Config.DB_TABLE_USER, null, null, conn);
        visitPresenter.delete(visitPresenter, Config.DB_TABLE_VISIT, null, null, conn);
        visitPresenter.delete(visitPresenter, Config.DB_TABLE_CONFIG, null, null, conn);
        this.goNav(context, cls);
    }

    public void confirmDialog(final Class<?> cls, final User user, int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        goNav(context, cls, user, id);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void confirmDialog(final Class<?> cls, final ConexionSQLiteHelper conn) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        destroy(cls, conn);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                })
                .show();
    }


    public void forgotPassword() {
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

    public void loader_config(final UserPresenter userPresenter, final String string, final ConexionSQLiteHelper conn) {
        Call<List<com.zeros.GesCoB.Model.Config>> list = ApiClient.getInstance().getApi().config("12345", userPresenter);
        list.enqueue(new Callback<List<com.zeros.GesCoB.Model.Config>>() {
            @Override
            public void onResponse(Call<List<com.zeros.GesCoB.Model.Config>> call, Response<List<com.zeros.GesCoB.Model.Config>> response) {
                userPresenter.delete(userPresenter, Config.DB_TABLE_CONFIG, null, null, conn);
                for (com.zeros.GesCoB.Model.Config c : response.body()) {
                    ConfigPresenter config = new ConfigPresenter(c.getCod_gestion(), c.getDesc_gestion(), c.getCod_resultado(), c.getDesc_resultado(), c.getCod_anomalia(), c.getDesc_anomalia());
                    try {
                        config.insert(config, Config.DB_TABLE_CONFIG, Config.DB_TABLE_CONFIG_ID, conn);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                start(string, 3000, R.style.Theme_AppCompat_DayNight_Dialog_Alert, context);
            }

            @Override
            public void onFailure(Call<List<com.zeros.GesCoB.Model.Config>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }


    public static boolean isLocationEnabled(Context context) {
        //...............
        return true;
    }

    public void getLocation() {
        if (isLocationEnabled(context)) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(context, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
            } else {
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        } else {
            //prompt user to enable location....
            //.................
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        //Hey, a non null location! Sweet!

        //remove location callback:
        locationManager.removeUpdates(this);

        //open the map:
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Toast.makeText(this.context, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void searchNearestPlace(String v2txt) {
        //.....
    }


    // Con este método mostramos en un Toast con un mensaje que el usuario ha concedido los permisos a la aplicación
    public void gerPermision(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((android.app.Activity) context, permission)) {

                ActivityCompat.requestPermissions((android.app.Activity) context, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions((android.app.Activity) context, new String[]{permission}, requestCode);
            }
        } else {
            imei = getIMEI();
            Toast.makeText(context, permission + " El permiso a la aplicación esta concedido.", Toast.LENGTH_SHORT).show();
        }
    }


    // Con este método consultamos al usuario si nos puede dar acceso a leer los datos internos del móvil
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // Validamos si el usuario acepta el permiso para que la aplicación acceda a los datos internos del equipo, si no denegamos el acceso
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imei = getIMEI();
                } else {
                    Toast.makeText(context, "Has negado el permiso a la aplicación", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private String getIMEI() {
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            }
            return telephonyManager.getImei();
        } else {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            }
            return telephonyManager.getDeviceId();
        }

    }


}
