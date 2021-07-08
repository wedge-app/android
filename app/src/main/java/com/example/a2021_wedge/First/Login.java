package com.example.a2021_wedge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a2021_wedge.bottomBar.MainActivity;


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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });


        //회원가입 버튼
        join = findViewById(R.id.textView);

        join.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), join.class);
            startActivity(intent);
        });
    }

}