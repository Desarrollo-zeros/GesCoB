package com.zeros.GesCoB.Presenter;

import com.zeros.GesCoB.Model.Visit;

public class VisitPresenter extends Visit {
    public VisitPresenter() {
    }

    public VisitPresenter(String cedula, String cliente, String direccion, String estado_suministro, String departamento, String municipio, String barrio) {
        super(cedula,cliente,direccion,estado_suministro.toUpperCase(),departamento,municipio,barrio);
    }

    public VisitPresenter(String id_visita, String nic, String nis, String departamento, String municipio, String corregimiento, String barrio, String tipo_via, String nombre_calle, String duplicador, String nro_via, String cgv, String direccion, String cliente, String cedula, String telefono, String tarifa, String estado_suministro, String ruta, String itinerario_lectura, String aol_finca, String medidor, String tipo_aparato, String marca_aparato, float deuda_energia, float deuda_terceros, float deuda_financiada, float facturas_vencidas, float facturas_acordadas, String pago_datafono, float estado_visita, float orden) {
        super(id_visita, nic, nis, departamento, municipio, corregimiento, barrio, tipo_via, nombre_calle, duplicador, nro_via, cgv, direccion, cliente, cedula, telefono, tarifa, estado_suministro, ruta, itinerario_lectura, aol_finca, medidor, tipo_aparato, marca_aparato, deuda_energia, deuda_terceros, deuda_financiada, facturas_vencidas, facturas_acordadas, pago_datafono, estado_visita, orden);
    }




}
