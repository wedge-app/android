package com.example.a2021_wedge;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.bottomBar.MainActivity;
import com.example.a2021_wedge.databinding.ActivityStorejoin2Binding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class storejoin2 extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private EditText et_address;

    ImageButton search, back, storesubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storejoin2);

        //뒤로 가기 버튼
        back = findViewById(R.id.storeback2);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Storejoin1.class);
            startActivity(intent);
        });

        storesubmit = findViewById(R.id.storesubmit);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        et_address = (EditText) findViewById(R.id.et_address);
        search = findViewById(R.id.srh);

        if (search != null) {
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(storejoin2.this, storesublist.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        et_address.setText(data);
                    }
                }
                break;
        }
    }
}