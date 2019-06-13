package com.zeros.GesCoB.Presenter;

import android.content.ContentValues;
import android.database.Cursor;

import com.zeros.GesCoB.Model.Config;
import com.zeros.GesCoB.database.ConexionSQLiteHelper;

import java.util.List;

public class ConfigPresenter extends Config {

    public ConfigPresenter(String cod_gestion, String desc_gestion, String cod_resultado, String desc_resultado, String cod_anomalia, String desc_anomalia) {
       super(cod_gestion,desc_gestion,cod_resultado,desc_resultado,cod_anomalia,desc_anomalia);
    }

    public ConfigPresenter(){
        super();
    }


}
