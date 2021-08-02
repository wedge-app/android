package com.example.a2021_wedge.retrofit;

import com.example.a2021_wedge.First.Model_UserSignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
//    String SERVER_URL = "https://fierce-mesa-76163.herokuapp.com/";

    @POST("/auth/register")
//    Call<Model_UserSignUp> userSignUp(@Body Model_UserSignUp modelUserSignUp);
    Call<Void> userSignUp(@Body Model_UserSignUp model_userSignUp);
}
