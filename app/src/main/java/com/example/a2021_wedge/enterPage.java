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
import com.example.a2021_wedge.retrofit.scount;
import com.example.a2021_wedge.retrofit.storecount;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class enterPage extends AppCompatActivity {
    ImageButton info, menu, review, like, wait,back;
    ImageView grey_star;
    TextView wait_num, waitn, story, story2, Name, waitenter;
    ArrayList<String> menuItem;
    int i = 0;
    String sname = "", num = "";
    String wname="", wnum="";
    String stel, sintro, saddr, smenu, scount, otime, ctime,enter;
    int w;
    int cancel = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_page);

        SharedPreferences waitlist = getSharedPreferences("waitlist", MODE_PRIVATE);
        SharedPreferences.Editor wlist = waitlist.edit();


        Intent intent = getIntent(); /*데이터 수신*/
        sname = intent.getExtras().getString("name");
        stel = intent.getExtras().getString("tel");
        sintro = intent.getExtras().getString("intro");
        saddr = intent.getExtras().getString("addr");
        smenu = intent.getExtras().getString("menu");
        scount = intent.getExtras().getString("scount");
        otime = intent.getExtras().getString("otime");
        ctime = intent.getExtras().getString("ctime");
        enter = intent.getExtras().getString("enter");

        SharedPreferences pref = this.getApplication().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uname = pref.getString("userName","");

        SharedPreferences storeName = getSharedPreferences("storeName", MODE_PRIVATE);
        SharedPreferences.Editor editor_s = storeName.edit();
        editor_s.putString("store", sname);
        editor_s.putString("userID", uname);
        editor_s.apply();

        waitenter = findViewById(R.id.waitenter);
        if(enter.equals("0")){
          waitenter.setText("대기 없는 매장");
        }else if(enter.equals("1")){
            waitenter.setText("미리 줄서기");
        }


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

        //대기인원
        wait_num = findViewById(R.id.textView9);
        wait_num.setText(scount);

        waitn = findViewById(R.id.wait);
        waitn.setVisibility(View.VISIBLE);

        //가게 설명
        story = findViewById(R.id.textView21);
        story2 = findViewById(R.id.textView12);

        story.setText("사장님의 말 : "+sintro+"\n\n전화 : "+stel+"\n\n가게 위치 :"+saddr+"\n");
        story2.append("영업 시간 : " + otime + " ~ " + ctime);


        //미리 줄서기
        wait = findViewById(R.id.imageButton9);

        //회색 별 버튼
        //like = findViewById(R.id.imageButton11);
        grey_star = findViewById(R.id.star);
        grey_star.setOnClickListener(new View.OnClickListener() {
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
                story.setText("사장님의 말 : "+sintro+"\n\n전화 : "+stel+"\n\n가게 위치 :"+saddr+"\n");
                story2.append("영업 시간 : " + otime + " ~ " + ctime);
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

                if(enter.equals("0")){
                    Toast.makeText(getApplicationContext(), "현재 이 매장은 대기없이 바로 입장 가능합니다.",Toast.LENGTH_SHORT).show();
                }else if(enter.equals("1")){
                    DialogWaiting dlg = new DialogWaiting(enterPage.this);
                    dlg.show();
                    //미리 줄서기
                }

            }
        });

        wlist.putString("wwnum", wnum);
        wlist.putString("wwname", wname);
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
                try {
                    System.out.println("try문 집입 완료");
                    w = Integer.parseInt(String.valueOf(scount));
                    wait_num.setText(Integer.toString(w + 1));
                    System.out.println("!!!!!!!wait_num : " + wait_num);
                    wname = sname;
                    wnum = Integer.toString(w + 1);
                    System.out.println("결과확인!!!!!!!wait_num : " + wnum);

                } catch (NumberFormatException e) {
                    System.out.println("예외발생");
                } catch (Exception e) {
                    System.out.println("예외발생");
                }
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

                scount scRequest = new scount(sname, num, wnum, responseListener);
                RequestQueue queue2 = Volley.newRequestQueue(enterPage.this);
                queue2.add(scRequest);

                //wlist.putString("wwcountteam", num);



                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Listener 진입 성공/ response 값 : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            //회원가입 성공시
                            if (success) {
                                System.out.println("연결 성공");

                            } else {
                                System.out.println("연결 실패");
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                storecount ssRequest = new storecount(wnum, wname, responseListener2);
                RequestQueue queue3 = Volley.newRequestQueue(enterPage.this);
                queue3.add(ssRequest);

                Toast.makeText(enterPage.this, "줄서기가 정상신청되었습니다.", Toast.LENGTH_SHORT).show();

                dismiss();
            });

            cancelButton.setOnClickListener(view -> {
                try {
                    System.out.println("try문 집입 완료");
                    w = Integer.parseInt(String.valueOf(scount));
                    wait_num.setText(Integer.toString(w));

                    wname = sname;
                    wnum = Integer.toString(w);
                } catch (NumberFormatException e) {
                    System.out.println("예외발생");
                } catch (Exception e) {
                    System.out.println("예외발생");
                }

                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Listener 진입 성공/ response 값 : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            //회원가입 성공시
                            if (success) {
                                System.out.println("연결 성공");

                            } else {
                                System.out.println("연결 실패");
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 Volley를 이용해서 요청
                storecount ssRequest = new storecount(wnum, wname, responseListener3);
                RequestQueue queue = Volley.newRequestQueue(enterPage.this);
                queue.add(ssRequest);
                dismiss();
            });
        }

        public DialogWaiting(Context mContext) {
            super(mContext);
        }
    }
}