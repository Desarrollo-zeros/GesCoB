package com.zeros.GesCoB.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.zeros.GesCoB.database.ConexionSQLiteHelper;
import com.zeros.GesCoB.database.QuerysLite;

import java.lang.reflect.Field;
import java.util.List;

public class Visit extends QuerysLite<Visit> {
    public String id_visita;
    public String nic;
    public String nis;
    public String departamento;
    public String municipio;
    public String corregimiento;
    public String barrio;
    public String tipo_via;
    public String nombre_calle;
    public String duplicador;
    public String nro_via;
    public String cgv;
    public String direccion;
    public String cliente;
    public String cedula;
    public String telefono;
    public String tarifa;
    public String estado_suministro;
    public String ruta;
    public String itinerario_lectura;
    public String aol_finca;
    public String medidor;
    public String tipo_aparato;
    public String marca_aparato;
    public float deuda_energia;
    public float deuda_terceros;
    public float deuda_financiada;
    public float facturas_vencidas;
    public float facturas_acordadas;
    public String pago_datafono;
    public float estado_visita;
    public int orden;

    public Visit(){

    }

    public Visit(String cedula, String cliente, String direccion, String estado_suministro, String departamento, String municipio, String barrio){
        this.cedula = cedula;
        this.cliente = cliente;
        this.direccion = direccion;
        this.estado_suministro = estado_suministro;
        this.departamento = departamento;
        this.municipio = municipio;
        this.barrio = barrio;
    }

    public Visit(String id_visita, String nic, String nis, String departamento, String municipio, String corregimiento, String barrio, String tipo_via, String nombre_calle, String duplicador, String nro_via, String cgv, String direccion, String cliente, String cedula, String telefono, String tarifa, String estado_suministro, String ruta, String itinerario_lectura, String aol_finca, String medidor, String tipo_aparato, String marca_aparato, float deuda_energia, float deuda_terceros, float deuda_financiada, float facturas_vencidas, float facturas_acordadas, String pago_datafono, float estado_visita, int orden) {
        this.id_visita = id_visita;
        this.nic = nic;
        this.nis = nis;
        this.departamento = departamento;
        this.municipio = municipio;
        this.corregimiento = corregimiento;
        this.barrio = barrio;
        this.tipo_via = tipo_via;
        this.nombre_calle = nombre_calle;
        this.duplicador = duplicador;
        this.nro_via = nro_via;
        this.cgv = cgv;
        this.direccion = direccion;
        this.cliente = cliente;
        this.cedula = cedula;
        this.telefono = telefono;
        this.tarifa = tarifa;
        this.estado_suministro = estado_suministro;
        this.ruta = ruta;
        this.itinerario_lectura = itinerario_lectura;
        this.aol_finca = aol_finca;
        this.medidor = medidor;
        this.tipo_aparato = tipo_aparato;
        this.marca_aparato = marca_aparato;
        this.deuda_energia = deuda_energia;
        this.deuda_terceros = deuda_terceros;
        this.deuda_financiada = deuda_financiada;
        this.facturas_vencidas = facturas_vencidas;
        this.facturas_acordadas = facturas_acordadas;
        this.pago_datafono = pago_datafono;
        this.estado_visita = estado_visita;
        this.orden = orden;
    }




    // Getter Methods

    public String getId_visita() {
        return id_visita;
    }

    public String getNic() {
        return nic;
    }

    public String getNis() {
        return nis;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getCorregimiento() {
        return corregimiento;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getTipo_via() {
        return tipo_via;
    }

    public String getNombre_calle() {
        return nombre_calle;
    }

    public String getDuplicador() {
        return duplicador;
    }

    public String getNro_via() {
        return nro_via;
    }

    public String getCgv() {
        return cgv;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCliente() {
        return cliente;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTarifa() {
        return tarifa;
    }

    public String getEstado_suministro() {
        return estado_suministro;
    }

    public String getRuta() {
        return ruta;
    }

    public String getItinerario_lectura() {
        return itinerario_lectura;
    }

    public String getAol_finca() {
        return aol_finca;
    }

    public String getMedidor() {
        return medidor;
    }

    public String getTipo_aparato() {
        return tipo_aparato;
    }

    public String getMarca_aparato() {
        return marca_aparato;
    }

    public float getDeuda_energia() {
        return deuda_energia;
    }

    public float getDeuda_terceros() {
        return deuda_terceros;
    }

    public float getDeuda_financiada() {
        return deuda_financiada;
    }

    public float getFacturas_vencidas() {
        return facturas_vencidas;
    }

    public float getFacturas_acordadas() {
        return facturas_acordadas;
    }

    public String getPago_datafono() {
        return pago_datafono;
    }

    public float getEstado_visita() {
        return estado_visita;
    }

    public int getOrden() {
        return orden;
    }

    // Setter Methods

    public void setId_visita(String id_visita) {
        this.id_visita = id_visita;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setCorregimiento(String corregimiento) {
        this.corregimiento = corregimiento;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setTipo_via(String tipo_via) {
        this.tipo_via = tipo_via;
    }

    public void setNombre_calle(String nombre_calle) {
        this.nombre_calle = nombre_calle;
    }

    public void setDuplicador(String duplicador) {
        this.duplicador = duplicador;
    }

    public void setNro_via(String nro_via) {
        this.nro_via = nro_via;
    }

    public void setCgv(String cgv) {
        this.cgv = cgv;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public void setEstado_suministro(String estado_suministro) {
        this.estado_suministro =  estado_suministro;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setItinerario_lectura(String itinerario_lectura) {
        this.itinerario_lectura = itinerario_lectura;
    }

    public void setAol_finca(String aol_finca) {
        this.aol_finca = aol_finca;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public void setTipo_aparato(String tipo_aparato) {
        this.tipo_aparato = tipo_aparato;
    }

    public void setMarca_aparato(String marca_aparato) {
        this.marca_aparato = marca_aparato;
    }

    public void setDeuda_energia(float deuda_energia) {
        this.deuda_energia = deuda_energia;
    }

    public void setDeuda_terceros(float deuda_terceros) {
        this.deuda_terceros = deuda_terceros;
    }

    public void setDeuda_financiada(float deuda_financiada) {
        this.deuda_financiada = deuda_financiada;
    }

    public void setFacturas_vencidas(float facturas_vencidas) {
        this.facturas_vencidas = facturas_vencidas;
    }

    public void setFacturas_acordadas(float facturas_acordadas) {
        this.facturas_acordadas = facturas_acordadas;
    }

    public void setPago_datafono(String pago_datafono) {
        this.pago_datafono = pago_datafono;
    }

    public void setEstado_visita(float estado_visita) {
        this.estado_visita = estado_visita;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id_visita='" + id_visita + '\'' +
                ", nic='" + nic + '\'' +
                ", nis='" + nis + '\'' +
                ", departamento='" + departamento + '\'' +
                ", municipio='" + municipio + '\'' +
                ", corregimiento='" + corregimiento + '\'' +
                ", barrio='" + barrio + '\'' +
                ", tipo_via='" + tipo_via + '\'' +
                ", nombre_calle='" + nombre_calle + '\'' +
                ", duplicador='" + duplicador + '\'' +
                ", nro_via='" + nro_via + '\'' +
                ", cgv='" + cgv + '\'' +
                ", direccion='" + direccion + '\'' +
                ", cliente='" + cliente + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tarifa='" + tarifa + '\'' +
                ", estado_suministro='" + estado_suministro + '\'' +
                ", ruta='" + ruta + '\'' +
                ", itinerario_lectura='" + itinerario_lectura + '\'' +
                ", aol_finca='" + aol_finca + '\'' +
                ", medidor='" + medidor + '\'' +
                ", tipo_aparato='" + tipo_aparato + '\'' +
                ", marca_aparato='" + marca_aparato + '\'' +
                ", deuda_energia=" + deuda_energia +
                ", deuda_terceros=" + deuda_terceros +
                ", deuda_financiada=" + deuda_financiada +
                ", facturas_vencidas=" + facturas_vencidas +
                ", facturas_acordadas=" + facturas_acordadas +
                ", pago_datafono='" + pago_datafono + '\'' +
                ", estado_visita=" + estado_visita +
                ", orden=" + orden +
                '}';
    }


    @Override
    public Long insert(Visit visit, String table, String emptyAttribute, ConexionSQLiteHelper conn) throws IllegalAccessException {
        ContentValues contentValues = new ContentValues();
        Field[] field =  visit.getClass().getDeclaredFields();
        SQLiteDatabase db = conn.getWritableDatabase();
        for(Field f : field){
            Object v = f.get(visit);
            if(!f.getName().equals("$change") && !f.getName().equals("serialVersionUID")){
                contentValues.put(f.getName(), String.valueOf(v));
            }
        }

        return  db.insert(table, emptyAttribute, contentValues);
    }

    @Override
    public int update(Visit visit, String table, ContentValues contentValues, String whereClausula, String[] whereArgs, ConexionSQLiteHelper conn) {
        return 0;
    }

    @Override
    public int count(List<Visit> list) {
        return super.count(list);
    }

    @Override
    public int count(Cursor cursor) {
        return super.count(cursor);
    }

    @Override
    public Cursor query(Visit visit, String query, String[] parametros, ConexionSQLiteHelper conn) {
        return super.query(visit, query, parametros, conn);
    }
    @Override
    public  int delete(Visit visit,String table, String clausula, String []where, ConexionSQLiteHelper conn){
        return super.delete(visit,table,clausula,where,conn);
    }
}