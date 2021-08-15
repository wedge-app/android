package com.example.a2021_wedge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a2021_wedge.First.Login;



public class Storejoin1 extends AppCompatActivity {
    ImageButton fin, back;
    EditText id, pw, name, tel;

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

        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);
        name = findViewById(R.id.name);
        tel = findViewById(R.id.tel);


        SharedPreferences pref = getSharedPreferences("store_info",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();


        //다음 버튼
        fin = findViewById(R.id.storenext);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("ID", id.getText().toString());
                editor.putString("PW", pw.getText().toString());
                editor.putString("userName", name.getText().toString());
                editor.putString("Tel", tel.getText().toString());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), storejoin2.class);
                startActivity(intent);
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