package com.example.a2021_wedge;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class Login extends AppCompatActivity {
    ImageButton login;
    TextView join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //로그인 버튼
        login = findViewById(R.id.imageButton);

        login.setOnClickListener(v -> {
            //로그인하고 들어가는 메인 페이지
        });


        //회원가입 버튼
        join = findViewById(R.id.textView);

        join.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), join.class);
            startActivity(intent);
        });
    }

}