package com.example.a2021_wedge.First;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.RegisterRequest;
import com.example.a2021_wedge.retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class join extends AppCompatActivity {
    ImageButton fin, back;
    EditText id, pwd, name, tel;

    RetrofitClient mInstance;
    private AlertDialog dialog;



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
        fin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("onclick 성공");
                String userID = id.getText().toString();
                String userPass = pwd.getText().toString();
                String userName = name.getText().toString();
                String userTel = tel.getText().toString();

                if(userID.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( join.this );
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Listener 진입 성공/ response 값 : "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean( "success" );

                            //회원가입 성공시
                            if(success) {
                                System.out.println("연결 성공");
                                Toast.makeText( getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT ).show();
                                onBackPressed();
                                //회원가입 실패시
                            } else {
                                System.out.println("연결 실패");
                                Toast.makeText( getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest( userID, userPass, userName, userTel, responseListener);
                RequestQueue queue = Volley.newRequestQueue( join.this );
                queue.add(registerRequest );
            }
        });

    }
}