package com.zeros.GesCoB.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;


public interface IWebService {

    @POST("loginSuccess.json")
    Call<Response> login(@Body User user);

    @POST("visit.json")
    Call<List<Visit>> visit(@Body User user);
}
