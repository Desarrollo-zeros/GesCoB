package com.zeros.GesCoB.Model;

import java.util.Date;

public class Photo  extends GeoLocation{
    private String data;
    private Date fecha;

    public Photo(){
        super();
    }

    public Photo(String data, Date fecha){
        super();
        this.data = data;
        this.fecha = fecha;
    }

    public Photo(String data, Date fecha, double latitud, double longitud){
        super(latitud,longitud);
        this.data = data;
        this.fecha = fecha;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
