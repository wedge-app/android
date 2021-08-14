package com.example.a2021_wedge;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class enterPage extends AppCompatActivity {
    ImageButton info, menu, review, like, wait,back;
    ImageView grey_star;
    TextView wait_num, story, story2, Name;
    ArrayList<String> menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_page);

        //뒤로가기
        back = findViewById(R.id.back3);
        back.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent(); /*데이터 수신*/
        String sname = intent.getExtras().getString("name");
        String stel = intent.getExtras().getString("tel");
        String sintro = intent.getExtras().getString("intro");
        String saddr = intent.getExtras().getString("addr");
        String smenu = intent.getExtras().getString("menu");

        info = findViewById(R.id.imageButton6);
        menu = findViewById(R.id.imageButton7);
        review = findViewById(R.id.imageButton8);

        //가게 이름
        Name = findViewById(R.id.textView8);
        Name.setText(sname);

        //가게 설명
        story = findViewById(R.id.textView21);
        story2 = findViewById(R.id.textView12);

        //대기 인원 숫자
        wait_num = findViewById(R.id.textView18);

        //미리 줄서기
        wait = findViewById(R.id.imageButton9);

        //회색 별 버튼
        like = findViewById(R.id.imageButton11);
        grey_star = findViewById(R.id.imageView15);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grey_star.setImageResource(R.drawable.shop_star2);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                story.setText(" 사장님의 말 : "+sintro+"\n 전화 : "+stel+"\n 가게 위치 :"+saddr);
//                String openHour = test.getString("hour1", null);
//                String openMin = test.getString("min1", null);
//                String closeHour = test.getString("hour2", null);
//                String closeMin = test.getString("min2", null);
//                story.setText("영업 시간 : " + openHour + "시 " + openMin + "분 ~ " +
//                        closeHour + "시 " + closeMin +"분");
            }
        });

        Intent menu_intent = getIntent();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                story.setText(smenu);
//                ArrayList<String> menuItem = (ArrayList<String>)menu_intent.getStringArrayListExtra("menu");
//                Serializable s = menu_intent.getSerializableExtra("menu");
//
//                for(int i = 0; i < menuItem.size(); i++)
//                {
//                    story.append("- " + menuItem.get(i));
//                }


            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                story.setText("완전 최고예요!"+"\n"+"자주 시켜먹어요! 너무 맛있어요~");
            }
        });


        wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(enterPage.this, "줄서기가 정상신청되었습니다.", Toast.LENGTH_SHORT).show();
                //미리 줄서기
                int w = Integer.parseInt(wait_num.getText().toString());
                wait_num.setText(w + 1);
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