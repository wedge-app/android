package com.example.a2021_wedge;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.First.join;
import com.example.a2021_wedge.Rev.ReviewList;
import com.example.a2021_wedge.Sajang.StoreManagement;
import com.example.a2021_wedge.SearchFrag.SearchFrag;
import com.example.a2021_wedge.retrofit.LikeStoreRequest;
import com.example.a2021_wedge.retrofit.RegisterRequest;
import com.example.a2021_wedge.retrofit.WaitingStoreRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class enterPage extends AppCompatActivity {
    ImageButton info, menu, review, like, wait,back;
    ImageView grey_star;
    TextView wait_num, story, story2, Name;
    ArrayList<String> menuItem;
    int i = 0;
    String sname = "", num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_page);


        Intent intent = getIntent(); /*데이터 수신*/
        sname = intent.getExtras().getString("name");
        String stel = intent.getExtras().getString("tel");
        String sintro = intent.getExtras().getString("intro");
        String saddr = intent.getExtras().getString("addr");
        String smenu = intent.getExtras().getString("menu");


        SharedPreferences pref = this.getApplication().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uname = pref.getString("userName","");

        SharedPreferences storeName = getSharedPreferences("storeName", MODE_PRIVATE);
        SharedPreferences.Editor editor_s = storeName.edit();
        editor_s.putString("store", sname);
        editor_s.putString("userID", uname);
        editor_s.apply();

        ImageView star = findViewById(R.id.star);
        star.setOnClickListener(v -> {
            Response.Listener<String> responseListener = response -> {
                System.out.println("Listener 진입 성공/ response 값 : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        System.out.println("연결 성공");
                        onBackPressed();
                    } else {
                        System.out.println("연결 실패");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            System.out.println(sname+uname);
            //서버로 Volley를 이용해서 요청
            LikeStoreRequest likestorerequest = new LikeStoreRequest(uname, sname, responseListener);
            RequestQueue queue = Volley.newRequestQueue(enterPage.this);
            queue.add(likestorerequest);
        });

        //뒤로가기
        back = findViewById(R.id.back3);
        back.setOnClickListener(v -> {
            finish();
//                    Intent intent2 = new Intent(this, SearchFrag.class);
//                    intent2.putExtra("ssname",sname);
//                    startActivity(intent2);
                    //onBackPressed();
                }
        );

        info = findViewById(R.id.imageButton6);
        menu = findViewById(R.id.imageButton7);
        review = findViewById(R.id.imageButton8);

        //가게 이름
        Name = findViewById(R.id.textView8);
        Name.setText(sname);

        //가게 설명
        story = findViewById(R.id.textView21);
        story2 = findViewById(R.id.textView12);


        //미리 줄서기
        wait = findViewById(R.id.imageButton9);

        //회색 별 버튼
        like = findViewById(R.id.imageButton11);
        grey_star = findViewById(R.id.star);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i == 0){
                    grey_star.setImageResource(R.drawable.shop_star2);
                    i++;
                }else if(i == 0){
                    grey_star.setImageResource(R.drawable.shop_star);
                    i--;
                }
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                story.setText(" 사장님의 말 : "+sintro+"\n\n 전화 : "+stel+"\n\n 가게 위치 :"+saddr+"\n");
                String openHour = test.getString("hour1", null);
                String openMin = test.getString("min1", null);
                String closeHour = test.getString("hour2", null);
                String closeMin = test.getString("min2", null);
                story2.append("영업 시간 : " + openHour + "시 " + openMin + "분 ~ " +
                        closeHour + "시 " + closeMin +"분");
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ReviewList.class);
                startActivity(i);
            }
        });

        Intent menu_intent = getIntent();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                story.setText(smenu);
                story2.setText("");
//                ArrayList<String> menuItem = (ArrayList<String>)menu_intent.getStringArrayListExtra("menu");
//                Serializable s = menu_intent.getSerializableExtra("menu");
//
//                for(int i = 0; i < menuItem.size(); i++)
//                {
//                    story2.append("- " + menuItem.get(i));
//                }


            }
        });


        wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(enterPage.this, "줄서기가 정상신청되었습니다.", Toast.LENGTH_SHORT).show();
                //미리 줄서기
                int w = Integer.parseInt(wait_num.getText().toString());
                wait_num.setText(Integer.toString(w + 1));

                DialogWaiting dlg = new DialogWaiting(enterPage.this);
                dlg.show();

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

    public class DialogWaiting extends Dialog {

        private EditText et_text;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_wating);

            Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            et_text = findViewById(R.id.num);
            Button saveButton = findViewById(R.id.btnSave);
            Button cancelButton = findViewById(R.id.btnCancel);

            SharedPreferences pref = this.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
            String tel = pref.getString("userTel","");

            saveButton.setOnClickListener(view -> {

                Response.Listener<String> responseListener = response -> {
                    System.out.println("Listener 진입 성공/ response 값 : " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        //회원가입 성공시
                        if (success) {
                            System.out.println("연결 성공");

                            onBackPressed();
                            //회원가입 실패시
                        } else {
                            System.out.println("연결 실패");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };
                num = et_text.getText().toString();
                System.out.println(tel+sname+num);
                //서버로 Volley를 이용해서 요청
                WaitingStoreRequest waitingStoreRequest = new WaitingStoreRequest(tel, sname, num, responseListener);
                RequestQueue queue = Volley.newRequestQueue(enterPage.this);
                queue.add(waitingStoreRequest);

                dismiss();
            });

            cancelButton.setOnClickListener(view -> dismiss());
        }

        public DialogWaiting(Context mContext) {
            super(mContext);
        }
    }
}