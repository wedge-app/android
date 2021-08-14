package com.example.a2021_wedge;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.First.join;
import com.example.a2021_wedge.bottomBar.MainActivity;
import com.example.a2021_wedge.databinding.ActivityStorejoin2Binding;
import com.example.a2021_wedge.retrofit.RegisterRequest;
import com.example.a2021_wedge.retrofit.storesRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class storejoin2 extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    EditText ssaddr, storename;

    ImageButton search, back, storesubmit;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storejoin2);

        //뒤로 가기 버튼
        back = findViewById(R.id.storeback2);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Storejoin1.class);
            startActivity(intent);
        });

        ssaddr = findViewById(R.id.addr);
        storename = findViewById(R.id.storename);

        SharedPreferences pref = this.getSharedPreferences("store_info", Context.MODE_PRIVATE);
        String ID = pref.getString("ID","");
        String PW = pref.getString("PW","");
        String username = pref.getString("userName","");
        String tel = pref.getString("Tel","");

        storesubmit = findViewById(R.id.storesubmit);
        storesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("onclick 성공");
                String sID = ID;
                String sPW = PW;
                String userName = username;
                String sTel = tel;
                String saddr = ssaddr.getText().toString();
                String sname = storename.getText().toString();

                if (sname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(storejoin2.this);
                    dialog = builder.setMessage("가게명은 빈 칸일 수 없습니다")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            //회원가입 성공시
                            if (success) {
                                System.out.println("연결 성공");
                                Toast.makeText(getApplicationContext(), "가게 회원가입 성공", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(storejoin2.this, Login.class);
                               startActivity(intent);
                                //회원가입 실패시
                            } else {
                                System.out.println("연결 실패");
                                Toast.makeText(getApplicationContext(), "가게 회원가입 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                storesRequest registerRequest = new  storesRequest(sID, sPW, userName, sTel, saddr, sname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(storejoin2.this);
                queue.add(registerRequest);
            }
        });


        if (search != null) {
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(storejoin2.this, storesublist.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        ssaddr.setText(data);
                    }
                }
                break;
        }
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