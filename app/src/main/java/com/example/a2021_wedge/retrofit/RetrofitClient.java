package com.example.a2021_wedge.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    String SERVER_URL = "http://8c31442c9fdb.ngrok.io/phpMyAdmin-5.1.1/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //public RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
}
