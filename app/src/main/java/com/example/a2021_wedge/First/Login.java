package com.example.a2021_wedge.First;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Storejoin1;
import com.example.a2021_wedge.bottomBar.MainActivity;
import com.example.a2021_wedge.retrofit.RetrofitClient;
import com.example.a2021_wedge.retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login extends AppCompatActivity {
    ImageButton login;
    TextView join;
    EditText email, pw;
    int check = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextID);
        pw = findViewById(R.id.editTextPW);

        SharedPreferences pref = getSharedPreferences("user_info",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();




        //사장님 로그인
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check % 2 == 0) check++;
                else if(check % 2 != 0) check--;
            }
        });

        //로그인 버튼
        login = findViewById(R.id.imageButton);

        login.setOnClickListener(v -> {
            editor.putString("user_email", email.getText().toString());
            editor.putString("user_pwd", pw.getText().toString());
            editor.apply();

            if(check % 2 == 0){ //사장님로그인인지 판단(수정필요)
                Intent store = new Intent(getApplicationContext(),  MainActivity.class);
                startActivity(store);
            }
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
            loginBtn(login);

        });

        FrameLayout sign = findViewById(R.id.signup);
        sign.setVisibility(View.INVISIBLE);
        TextView x = findViewById(R.id.textView37);
        x.setVisibility(View.INVISIBLE);
        TextView q = findViewById(R.id.textView38);
        q.setVisibility(View.INVISIBLE);
        CheckBox gene = (CheckBox) findViewById(R.id.checkBox3);
        gene.setVisibility(View.INVISIBLE);
        CheckBox store = (CheckBox) findViewById(R.id.checkBox4);
        store.setVisibility(View.INVISIBLE);


        //회원가입 버튼
        join = findViewById(R.id.textView);
        join.setOnClickListener(v -> {
            sign.setVisibility(View.VISIBLE);
            x.setVisibility(View.VISIBLE);
            q.setVisibility(View.VISIBLE);
            gene.setVisibility(View.VISIBLE);
            store.setVisibility(View.VISIBLE);

            x.setOnClickListener(new CheckBox.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sign.setVisibility(View.INVISIBLE);
                    x.setVisibility(View.INVISIBLE);
                    q.setVisibility(View.INVISIBLE);
                    gene.setVisibility(View.INVISIBLE);
                    store.setVisibility(View.INVISIBLE);
                }
            });

            //일반 회원 가입
            gene.setOnClickListener(new CheckBox.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), join.class);
                    startActivity(intent);
                }
            });

            //사장님 가입
            store.setOnClickListener(new CheckBox.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Storejoin1.class);
                    startActivity(intent);
                }
            });

        });
    }

    public void loginBtn(View view) {
        String user = email.getText().toString();
        String pass = pw.getText().toString();

        background bg = new background(this);
        bg.execute(user,pass);
    }

}