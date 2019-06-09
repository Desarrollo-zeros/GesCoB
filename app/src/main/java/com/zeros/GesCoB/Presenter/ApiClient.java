package com.zeros.GesCoB.Presenter;

import com.zeros.GesCoB.Config;
import com.zeros.GesCoB.Model.IWebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = Config.baseUrl;
    private static Retrofit retrofit = null;
    private static ApiClient mIntances;

    private ApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance(){
        if(mIntances == null){
            mIntances = new ApiClient();
        }
        return mIntances;
    }

    public IWebService getApi(){
        return retrofit.create(IWebService.class);
    }

}
