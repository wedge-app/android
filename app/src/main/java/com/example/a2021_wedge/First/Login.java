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
import com.example.a2021_wedge.retrofit.SjLoginRequest;


import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {
    ImageButton login;
    TextView join;
    EditText email, pw;
    int check = 0;

    String sID, sPW, sintro, saddr, smenu, sname, stitle, otime, ctime, enter;



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
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener(v -> {
            if(check == 0) check++;
            else check--;
        });

        //로그인 버튼
        login = findViewById(R.id.imageButton);
        login.setOnClickListener(v -> {
            String userID = email.getText().toString();
            String userPass = pw.getText().toString();

            if (check == 0) { //사장님로그인인지 판단(수정필요)
                Response.Listener<String> responseListener = response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {//로그인 성공시

                            System.out.println("연결성공");
                            String userID1 = jsonObject.getString("userID");
                            String userPass1 = jsonObject.getString("userPassword");
                            String userName = jsonObject.getString("userName");
                            String userTel = jsonObject.getString("userTel");

                            Intent intent = new Intent(Login.this, MainActivity.class);

                            editor.putString("userID", userID1);
                            editor.putString("userPass", userPass1);
                            editor.putString("userName", userName);
                            editor.putString("userTel", userTel);
                            editor.apply();

                            Toast.makeText(getApplicationContext(), userName+" 님 환영합니다.", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else {//로그인 실패시
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);

            }
            if(check == 1){ //사장님 로그인
                Response.Listener<String> responseListener = response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {//로그인 성공시
                            System.out.println("연결성공");
                            sID = jsonObject.getString("ID");
                            sPW = jsonObject.getString("PW");
                            sintro = jsonObject.getString("intro");
                            saddr = jsonObject.getString("addr");
                            smenu = jsonObject.getString("menu");
                            sname = jsonObject.getString("userName");
                            stitle = jsonObject.getString("name");
                            otime = jsonObject.getString("opentime");
                            ctime = jsonObject.getString("closetime");
                            enter = jsonObject.getString("enter");


                            Intent intent = new Intent(getApplicationContext(), WaitingList.class);

                            editor.putString("userID", sID);
                            editor.putString("userPass", sPW);
                            editor.putString("sintro", sintro);
                            editor.putString("saddr", saddr);
                            editor.putString("smenu", smenu);
                            editor.putString("sname", sname);
                            editor.putString("stitle", stitle);
                            editor.putString("otime", otime);
                            editor.putString("ctime", ctime);
                            editor.putString("enter", enter);
                            editor.apply();

                            Toast.makeText(getApplicationContext(), sname+" 님 환영합니다.", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else {//로그인 실패시
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };
                SjLoginRequest sloginRequest = new SjLoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(sloginRequest);

            }
        });

        FrameLayout sign = findViewById(R.id.signup);
        sign.setVisibility(View.INVISIBLE);
        TextView x = findViewById(R.id.home_title);
        x.setVisibility(View.INVISIBLE);
        TextView q = findViewById(R.id.text_time);
        q.setVisibility(View.INVISIBLE);
        CheckBox gene = findViewById(R.id.checkBox3);
        gene.setVisibility(View.INVISIBLE);
        CheckBox store = findViewById(R.id.checkBox4);
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