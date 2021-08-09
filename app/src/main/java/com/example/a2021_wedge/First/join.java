package com.example.a2021_wedge.First;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.RetrofitClient;
import com.example.a2021_wedge.retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class join extends AppCompatActivity {
    ImageButton fin, back;
    EditText id, pwd, name, tel;

    RetrofitClient mInstance;
    private String SERVER_URL = "http://8c31442c9fdb.ngrok.io/phpMyAdmin-5.1.1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id = findViewById(R.id.id);
        pwd = findViewById(R.id.pwd);
        name = findViewById(R.id.name);
        tel = findViewById(R.id.tel);

        //뒤로 가기 버튼
        back = findViewById(R.id.storeback2);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

        //완료 버튼
        fin = findViewById(R.id.imageButton3);
        fin.setOnClickListener(v -> {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
            mInstance = new RetrofitClient();
            Model_UserSignUp userjoin = new Model_UserSignUp(id.getText().toString(), pwd.getText().toString(), name.getText().toString(), tel.getText().toString());
            Call<Model_UserSignUp> call = retrofitInterface.post_join(userjoin);

            call.enqueue(new Callback<Model_UserSignUp>() {
                @Override
                public void onResponse(Call<Model_UserSignUp> call, Response<Model_UserSignUp> response) {
                    Model_UserSignUp join = response.body();

                    System.out.println("연결 상태 : "+response.body().toString());

                }

                @Override
                public void onFailure(Call<Model_UserSignUp> call, Throwable t) {
                    System.out.println("연결 실패");
                }
            });

        });
    }

}