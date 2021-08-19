package com.example.a2021_wedge.MyPageFrag;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.First.join;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.databinding.ActivityFixmyinfoBinding;
import com.example.a2021_wedge.retrofit.RegisterRequest;
import com.example.a2021_wedge.retrofit.fmyinfo;

import org.json.JSONException;
import org.json.JSONObject;


public class fixmyinfo extends AppCompatActivity {
    ImageButton back;
    EditText id, pwd, name, tel;
    Button fixsave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixmyinfo);

        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String idd = pref.getString("userID","");
        String pww = pref.getString("userPass","");
        String namee = pref.getString("userName","");
        String tell = pref.getString("usertel","");

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        //수정 부분
        id = findViewById(R.id.id);
        id.setText(idd);
        pwd = findViewById(R.id.pwd);
        pwd.setText(pww);
        name = findViewById(R.id.name);
        name.setText(namee);
        tel = findViewById(R.id.tel);
        tel.setText(tell);

        //수정 완료 버튼
        fixsave = findViewById(R.id.fixsave);
        fixsave.setOnClickListener(v -> {

            String userID = id.getText().toString();
            String userPass = pwd.getText().toString();
            String userName = name.getText().toString();
            String userTel = tel.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Listener 진입 성공/ response 값 : " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        //회원가입 성공시
                        if (success) {
                            System.out.println("연결 성공");
                            Toast.makeText(getApplicationContext(), "정보 수정 성공", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            //회원가입 실패시
                        } else {
                            System.out.println("연결 실패");
                            Toast.makeText(getApplicationContext(), "정보 수정 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            //서버로 Volley를 이용해서 요청
            fmyinfo registerRequest = new fmyinfo(userPass, userName, userTel,idd, responseListener);
            RequestQueue queue = Volley.newRequestQueue(fixmyinfo.this);
            queue.add(registerRequest);
        });

    }

    @Override
    public void onBackPressed() {
            finish();
    }
}