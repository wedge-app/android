package com.example.a2021_wedge.First;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;



import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class join extends AppCompatActivity {
    ImageButton fin, back;
    EditText id, pwd, name, tel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id = findViewById(R.id.id);
        pwd = findViewById(R.id.pwd);
        name = findViewById(R.id.name);
        tel = findViewById(R.id.tel);

        //뒤로 가기 버튼
        back = findViewById(R.id.imageButton2);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

        //완료 버튼
        fin = findViewById(R.id.imageButton3);
        fin.setOnClickListener(v -> {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://wedge21.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

            Model_UserSignUp m = new Model_UserSignUp(id.getText().toString(), pwd.getText().toString(), name.getText().toString(), tel.getText().toString());
            //RetrofitClient retrofitClient = new RetrofitClient();
            Call<Void> call = retrofitInterface.userSignUp(m);
            //Call<Model_UserLogIn> call = retrofitClient.retrofitInterface.userLogIn(m);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    SharedPreferences pref = getSharedPreferences("user_info",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.clear();

                    System.out.println("코드 들어옴");

                    if(response.code() == 200){
                        Toast.makeText(join.this, "로그인 성공", Toast.LENGTH_LONG).show();
                        System.out.println("코드 들어옴2");
                        //System.out.println("로그인 후 응답 상황 token:"+response.body().getToken());
                        editor.putString("user_email", id.getText().toString());
//                        editor.putString("token", response.body().getToken());
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);

                    } else if(response.code() == 401){
                        Toast.makeText(join.this, "로그인 실패", Toast.LENGTH_LONG).show();
                        System.out.println("코드 들어3");
                    }else {
                        System.out.println(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(join.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    System.out.println(t.getMessage());
                    System.out.println("코드 들어옴4");
                }


            });

        });
    }

}