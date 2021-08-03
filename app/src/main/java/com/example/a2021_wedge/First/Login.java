package com.example.a2021_wedge.First;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.bottomBar.MainActivity;


public class Login extends AppCompatActivity {
    ImageButton login;
    TextView join;
    EditText email, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextID);
        pw = findViewById(R.id.editTextPW);

        SharedPreferences pref = getSharedPreferences("user_info",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();

        //로그인 버튼
        login = findViewById(R.id.imageButton);

        login.setOnClickListener(v -> {
            editor.putString("user_email", email.getText().toString());
            editor.putString("user_pwd", pw.getText().toString());
            editor.apply();

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