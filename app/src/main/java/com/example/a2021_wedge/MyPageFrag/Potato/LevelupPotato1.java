package com.example.a2021_wedge.MyPageFrag.Potato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.a2021_wedge.R;

public class LevelupPotato1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelup_potato1);

        TextView id = findViewById(R.id.textView48);
        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String prefname = pref.getString("userName","");
        id.setText(prefname);
    }
}