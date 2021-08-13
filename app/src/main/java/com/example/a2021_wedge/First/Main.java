package com.example.a2021_wedge.First;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a2021_wedge.R;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setVisibility(View.INVISIBLE);

        ImageView imageView2 = findViewById(R.id.imageView5);
        imageView2.setVisibility(View.INVISIBLE);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            imageView2.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }, 500);


        handler.postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }, 2000);
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