package com.zeros.GesCoB;

import java.util.ArrayList;
import java.util.List;

public class Config {
   // public static final String baseUrl = "http://gescob.cloudsoluciones.co:81/rest/";
   public static final String baseUrl = "http://192.168.0.29:81/gescob.cloudsoluciones.co/rest/";
    public static final String DB_NAME = "gescob";

    public static final String DB_TABLE_USER = "user";
    public static final String DB_TABLE_USER_ID = "id";
    public static final String DB_TABLE_USER_USERNAME = "username";
    public static final String DB_TABLE_USER_PASSWORD = "password";

    public static final String CREATE_TABLE_USER = "CREATE TABLE "+DB_TABLE_USER+" " +
            "("
            +DB_TABLE_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DB_TABLE_USER_USERNAME+" TEXT, "
            +DB_TABLE_USER_PASSWORD+" TEXT" +
            ")";

    public static final String DB_TABLE_VISIT = "visit";
    public static final String DB_TABLE_VISIT_ID = "id_visita";

    public static final String CREATE_TABLE_VISIT = "CREATE TABLE "+DB_TABLE_VISIT+" (" +
            "id_visita INTEGER primary key autoincrement, nic integer INTEGER," +
            "nis  INTEGER, departamento  TEXT, municipio TEXT," +
            "corregimiento TEXT,barrio TEXT,tipo_via  TEXT," +
            "nombre_calle  TEXT, duplicador TEXT, nro_via TEXT," +
            "cgv TEXT, direccion TEXT, cliente       TEXT," +
            "cedula TEXT, telefono TEXT," +
            "tarifa TEXT, estado_suministro  TEXT,ruta TEXT," +
            "itinerario_lectura TEXT, aol_finca TEXT, medidor TEXT," +
            "tipo_aparato TEXT,marca_aparato TEXT,deuda_energia REAL," +
            "deuda_terceros REAL, deuda_financiada REAL, facturas_vencidas INTEGER," +
            "facturas_acordadas INTEGER, pago_datafono   TEXT," +
            "estado_visita  INTEGER,orden INTEGER)";


    public static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS "+DB_TABLE_USER+" ";

    public static final String DROP_TABLE_VISIT = "DROP TABLE IF EXISTS "+DB_TABLE_VISIT+" ";


    public class Querys{
        public static final String QUERY_TABLE_USER_EXITS = "SELECT username, password FROM "+Config.DB_TABLE_USER+" where username = ? and password = ?";
        public static  final String  QUERY_TABLE_VISIT_COUNT = "SELECT id_visita FROM "+DB_TABLE_VISIT;
    }

}

