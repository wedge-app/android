package com.example.a2021_wedge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

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
}