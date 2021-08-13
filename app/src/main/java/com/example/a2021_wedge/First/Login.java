package com.example.a2021_wedge.First;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Sajang.WaitingList;
import com.example.a2021_wedge.Storejoin1;
import com.example.a2021_wedge.bottomBar.MainActivity;
import com.example.a2021_wedge.retrofit.LoginRequest;


import org.json.JSONException;
import org.json.JSONObject;


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
                if(check == 0) check++;
                else check--;
            }
        });

        //로그인 버튼
        login = findViewById(R.id.imageButton);
        login.setOnClickListener(v -> {
            String userID = email.getText().toString();
            String userPass = pw.getText().toString();

            if (check == 0) { //사장님로그인인지 판단(수정필요){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {//로그인 성공시
                                System.out.println("연결성공");
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                String userTel = jsonObject.getString("userTel");

                                Intent intent = new Intent(Login.this, MainActivity.class);

                                editor.putString("userID", userID);
                                editor.putString("userPass", userPass);
                                editor.putString("userName", userName);
                                editor.putString("userTel", userTel);
                                editor.apply();

                                Toast.makeText(getApplicationContext(), userName+"님 환영합니다.", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            } else {//로그인 실패시
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }else {
                Intent intent = new Intent(getApplicationContext(), WaitingList.class);
                startActivity(intent);
            }
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

            x.setOnClickListener(v1 -> {
                sign.setVisibility(View.INVISIBLE);
                x.setVisibility(View.INVISIBLE);
                q.setVisibility(View.INVISIBLE);
                gene.setVisibility(View.INVISIBLE);
                store.setVisibility(View.INVISIBLE);
            });

            //일반 회원 가입
            gene.setOnClickListener(v12 -> {
                Intent intent = new Intent(getApplicationContext(), join.class);
                startActivity(intent);
            });

            //사장님 가입
            store.setOnClickListener(v13 -> {
                Intent intent = new Intent(getApplicationContext(), Storejoin1.class);
                startActivity(intent);
            });

        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrooges = new int[2];
            view.getLocationOnScreen(scrooges);
            float x = ev.getRawX() + view.getLeft() - scrooges[0];
            float y = ev.getRawY() + view.getTop() - scrooges[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
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