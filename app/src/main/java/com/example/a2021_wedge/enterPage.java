package com.example.a2021_wedge;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class enterPage extends AppCompatActivity {
    ImageButton info, menu, review, like, wait,back;
    ImageView grey_star;
    TextView wait_num, story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_page);

        //뒤로가기
        back = findViewById(R.id.back3);

        info = findViewById(R.id.imageButton6);
        menu = findViewById(R.id.imageButton7);
        review = findViewById(R.id.imageButton8);

        //가게 설명
        story = findViewById(R.id.textView21);

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
                //story.setText("");
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                story.setText("엽기떡볶이 4인 세트");
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                story.setText("완전 최고예요!");
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
}