package com.example.a2021_wedge.MyPageFrag.Potato;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.a2021_wedge.R;

public class TotalPotato extends AppCompatActivity {
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_potato);


        //뒤로 가기 버튼
        back = findViewById(R.id.back_p);
        back.setOnClickListener(v -> onBackPressed());

    }
}