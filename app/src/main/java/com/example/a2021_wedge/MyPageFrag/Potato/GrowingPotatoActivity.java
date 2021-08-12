package com.example.a2021_wedge.MyPageFrag.Potato;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Sajang.StoreManagement;

public class GrowingPotatoActivity extends AppCompatActivity {
    ImageButton back;
    Button b;
    TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growing_potato);
        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GrowingPotatoActivity.this, TotalPotato.class);
                startActivity(i);
            }
        });
        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        id = findViewById(R.id.textView44);
        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String prefname = pref.getString("userName","");
        id.setText(prefname);
    }
}
