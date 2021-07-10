package com.example.a2021_wedge.First;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.a2021_wedge.R;


public class join extends AppCompatActivity {
    ImageButton fin, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //뒤로 가기 버튼
        back = findViewById(R.id.imageButton2);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

        //완료 버튼
        fin = findViewById(R.id.imageButton3);
        fin.setOnClickListener(v -> {
            //나중에 데베 연결 후 수정하기
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });
    }

}