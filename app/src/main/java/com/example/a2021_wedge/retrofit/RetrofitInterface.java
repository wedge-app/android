package com.example.a2021_wedge.retrofit;

import com.example.a2021_wedge.First.Model_UserSignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
//    String SERVER_URL = "https://fierce-mesa-76163.herokuapp.com/";
//     String SERVER_URL = "http://8c31442c9fdb.ngrok.io/phpMyAdmin-5.1.1/";

     @FormUrlEncoded
     @POST("test")
     Call<Model_UserSignUp> post_join(@Body Model_UserSignUp model_userSignUp);

     @GET("test")
     Call<Model_UserSignUp> get_join();

    @POST("/auth/register")
//    Call<Model_UserSignUp> userSignUp(@Body Model_UserSignUp modelUserSignUp);
    Call<Void> userSignUp(@Body Model_UserSignUp model_userSignUp);
}
