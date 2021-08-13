package com.example.a2021_wedge.MyPageFrag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.First.join;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.bottomBar.MainActivity;
import com.example.a2021_wedge.retrofit.RegisterRequest;
import com.example.a2021_wedge.retrofit.getuserRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalInfoActivity extends AppCompatActivity {
    ImageButton back;
    EditText pw, pw2, name, tel;
    private AlertDialog dialog2;
    private AlertDialog dialog;
    String userID, userPass, userName, userTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String prefname = pref.getString("userName","");
        String prefpw = pref.getString("userPass","");
        String preftel = pref.getString("userTel","");
        String prefID = pref.getString("userID","");

        pw = findViewById(R.id.edit_new_password);
        pw2 = findViewById(R.id.edit_password_done);
        pw.setText(prefpw);

        if(pw2.getText().toString().equals(pw.getText().toString())){
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder( PersonalInfoActivity.this );
            dialog2=builder.setMessage("비밀번호가 일치하지 않습니다.")
                    .setPositiveButton("확인",null)
                    .create();
            dialog2.show();
        }
        name = findViewById(R.id.edit_name);
        tel = findViewById(R.id.edit_phone_number);
        name.setText(prefname);
        tel.setText(preftel);

        Button com = findViewById(R.id.button10);
        com.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {//로그인 성공시
                                if(prefID.equals(userID)){
                                    String userID = jsonObject.getString("userID");
                                    String userPass = jsonObject.getString("userPassword");
                                    String userName = jsonObject.getString("userName");
                                    String userTel = jsonObject.getString("userTel");
                                }
                                Toast.makeText(getApplicationContext(), "정보 수정 완료", Toast.LENGTH_SHORT).show();

                            } else {//로그인 실패시
                                Toast.makeText(getApplicationContext(), "정보 수정 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                getuserRequest getuserRequest = new getuserRequest(userID, userPass, userName, userTel, responseListener);
                RequestQueue queue = Volley.newRequestQueue( PersonalInfoActivity.this );
                queue.add(getuserRequest );
            }
        });

    }

    private long time= 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
}
