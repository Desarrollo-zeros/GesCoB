package com.zeros.GesCoB.Model;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;


public interface IWebService {

    @POST("loginSuccess.json")
    Call<Response> login(@Field("token") String tokent ,@Body User user);

    @POST("visit.json")
    Call<List<Visit>> visit(@Field("token") String tokent ,@Body User user);

    @FormUrlEncoded
    @POST("loginSuccess.json")
    Call<Response> changePassword(@Field("token") String tokent ,@Field("username") String username, @Field("old_password") String old_password, @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("loginSuccess.json")
    Call<Response> fotgotPassword(@Field("token") String tokent ,@Field("username") String username, @Field("email") String email);


    @POST("config.json")
    Call<List<Config>> config(@Field("token") String tokent ,@Body User user);


    @POST("config.json")
    Call<State> change_state(@Field("token") String tokent, @Field("id_visita") String id_visita, @Body State state,@Field("username") String username);

    @POST("loginSuccess.json")
    Call<Response> tracker(@Field("token") String tokent, @Field("id_visita") String id_visita, @Field("imei") String imei, @Field("latitud") double latitud, @Field("longitud") double longitud, @Field("fecha") Date fecha, @Field("estado") String estado);

    @POST("checklist_security.json")
    Call<List<checklist_Security>> checklist_security(@Field("token") String tokent);

    @POST("loginSuccess.json")
    Call<Response> security(@Field("token") String tokent, @Field("id_visita") int id_visita, @Field("username") String username, @Field("fecha") Date fecha, @Body() List<Security> securityList);

    @POST("loginSuccess.json")
    Call<Response> capture(@Field("token") String tokent, @Field("id_visita") int id_visita, @Field("fecha") Date fecha,
                           @Field("gestion_id") String gestion_id, @Field("resultado_id") String resultado_id, @Field("anomalia_id") String anomalia_id,
                           @Field("compromiso_pago") boolean compromiso_pago, @Field("fecha_compromiso_pago") Date fecha_compromiso_pago, @Field("pago") boolean pago,
                           @Field("fecha_pago") Date fecha_pago, @Field("valor_pago") double valor_pago, @Field("entidad_recaudo") int entidad_recaudo,
                           @Field("observacion") String observacion,@Field("forma_pago") String forma_pago, @Field("cedula_titular") String cedula_titular,
                           @Field("nombre_titular") String nombre_titular, @Field("telefono_titular") String telefono_titular,@Field("nombre_contacto") String nombre_contacto,
                           @Field("telefono_contacto") String telefono_contacto, @Field("correo_contacto") String correo_contacto, @Field("direccion_correcta") boolean direccion_correcta,
                           @Field("pago_datafono") boolean pago_datafono, @Field("laltitud") double laltitud, @Field("longitud") double longitud, @Body List<Photo> photoList);


    @POST("compromisos.json")
    Call<List<Compromise>> comprimesos(@Field("token") String token, @Field("usename") String username);

    @POST("loginSuccess.json")
    Call<Response> auto_generate_visita(@Field("token") String token, @Field("nic") String nic, @Field("username") String username);


    @POST("estadistica.json")
    Call<List<Statistics>> estadistica(@Field("token") String token, @Field("username") String username);

    @POST("loginSuccess.json")
    Call<Response> register_push(@Field("token") String token, @Field("username") String username, @Field("key") String key);
}
