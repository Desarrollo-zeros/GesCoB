package com.zeros.GesCoB.Model;

import java.util.Date;

public class State extends GeoLocation {
    private int id_estado;
    private Date fecha;

    public State(){
        super();
    }

    public State(int id_estado, Date fecha) {
        super();
        this.id_estado = id_estado;
        this.fecha = fecha;
    }


    public State(int id_estado, Date fecha, double latitud, double longitud) {
        super(latitud,longitud);
        this.id_estado = id_estado;
        this.fecha = fecha;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "State{" +
                "id_estado=" + id_estado +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", fecha=" + fecha +
                '}';
    }
}
