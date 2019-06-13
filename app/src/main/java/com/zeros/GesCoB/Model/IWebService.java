package com.zeros.GesCoB.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;


public interface IWebService {

    @POST("loginSuccess.json")
    Call<Response> login(@Body User user);

    @POST("visit.json")
    Call<List<Visit>> visit(@Body User user);

    @FormUrlEncoded
    @POST("loginSuccess.json")
    Call<Response> changePassword(@Field("username") String username, @Field("old_password") String old_password, @Field("new_password") String new_password);

}
