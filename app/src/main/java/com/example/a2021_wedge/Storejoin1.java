package com.example.a2021_wedge;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.First.join;
import com.example.a2021_wedge.databinding.ActivityStorejoin1Binding;


public class Storejoin1 extends AppCompatActivity {
    ImageButton fin, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storejoin1);

        //뒤로 가기 버튼
        back = findViewById(R.id.storeback);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });



        //다음 버튼
        fin = findViewById(R.id.storenext);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), storejoin2.class);
                startActivity(intent);
            }
        });
    }

}