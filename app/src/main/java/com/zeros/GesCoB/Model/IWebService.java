package com.zeros.GesCoB.Model;

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



}
