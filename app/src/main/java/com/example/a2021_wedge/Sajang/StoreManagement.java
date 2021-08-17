package com.example.a2021_wedge.Sajang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.RecyclerView.CustomAdapter;
import com.example.a2021_wedge.RecyclerView.Dictionary;
import com.example.a2021_wedge.enterPage;
import com.example.a2021_wedge.retrofit.storemanagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreManagement extends AppCompatActivity {

    Button save;
    private int count = -1;

    Button button_2, button_3, button_4;
    EditText editText1, editText2;


    Button timePickerDialogButton1, timePickerDialogButton2;
    int alarmHour=0, alarmMinute=0;

    TextView textView_open, textView_close;
    TextView textView_2, textView_3, textView_4;

    String pID, m="", m2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_management);

        SharedPreferences preff = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String otime = preff.getString("otime","");
        String ctime = preff.getString("ctime","");
        String smenu = preff.getString("smenu","");
        String sintro = preff.getString("sintro","");
        pID = preff.getString("userID","");

        System.out.println("초기전달값 : "+otime+", "+ctime+", "+smenu+" , "+sintro+", ID="+pID);



        textView_close = findViewById(R.id.textView_close);
        textView_close.setVisibility(View.INVISIBLE);

        button_2 = findViewById(R.id.button_2);
        button_2.setOnClickListener(view -> {
            button_2.setVisibility(View.INVISIBLE);
            textView_close.setVisibility(View.VISIBLE);

        });

        button_3 = findViewById(R.id.button_3);
        button_3.setOnClickListener(view -> {
            button_3.setVisibility(View.INVISIBLE);
        });

        button_4 = findViewById(R.id.button_4);
        button_4.setOnClickListener(view -> {
            button_4.setVisibility(View.INVISIBLE);
        });


        textView_2 = findViewById(R.id.textView_2);
        textView_3 = findViewById(R.id.textView_3);
        textView_4 = findViewById(R.id.textView_4);


        textView_2.setOnClickListener(view -> {
            button_2.setVisibility(View.VISIBLE);
            textView_close.setVisibility(View.INVISIBLE);
        });

        textView_3.setOnClickListener(view -> {
            button_3.setVisibility(View.VISIBLE);
        });

        textView_4.setOnClickListener(view -> {
            button_4.setVisibility(View.VISIBLE);

        });




        /////////////////// 오픈 시간 설정
        timePickerDialogButton1 = findViewById(R.id.timePickerDialogButton1);
        textView_open = findViewById(R.id.textView_open);
        textView_open.setText(otime);

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = test.edit();

        timePickerDialogButton1.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (StoreManagement.this, (view, hourOfDay, minute) -> {
                        textView_open.setText(hourOfDay + ":" + minute);
                        editor.putString("hour1", String.valueOf(hourOfDay));
                        editor.putString("min1", String.valueOf(minute));
                        editor.commit();
                    },alarmHour, alarmMinute, false);
            timePickerDialog.show();
        });

        ////////////////// 마감 시간 설정
        timePickerDialogButton2 = findViewById(R.id.timePickerDialogButton2);
        textView_close.setText(ctime);


        timePickerDialogButton2.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (StoreManagement.this, (view, hourOfDay, minute) -> {
                        textView_close.setText(hourOfDay + ":" + minute);
                        editor.putString("hour2", String.valueOf(hourOfDay));
                        editor.putString("min2", String.valueOf(minute));
                        editor.commit();
                    },alarmHour, alarmMinute, false);
            timePickerDialog.show();
        });




        ////////////////////메뉴 설정
        editText1 = findViewById(R.id.editText1);
        editText1.setText(smenu);

        try { //예외처리
            m = editText1.getText().toString();
        }catch (NullPointerException e){
            System.out.println("널값 오류 -> editText1");
        }


        ////////////////////사장님 한마디 설정
        editText2 = findViewById(R.id.editText2);
        editText2.setText(sintro);

        try { //예외처리
            m2 = editText2.getText().toString();
        }catch (NullPointerException e){
            System.out.println("널값 오류 -> editText2");
        }



        System.out.println("결과값 확인 : "+editText1.getText().toString()+", "+editText2.getText().toString()+", "+textView_open.getText().toString()+", "+textView_close.getText().toString()+", "+pID);


        //저장버튼
        save = findViewById(R.id.button_save);
        save.setOnClickListener(view -> { // 버튼 메뉴를 클릭하면
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Listener 진입 성공/ response 값 : " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        //회원가입 성공시
                        if (success) {
                            System.out.println("연결 성공");
                        } else {
                            System.out.println("연결 실패");
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            //서버로 Volley를 이용해서 요청
            storemanagement ssequest = new storemanagement(editText2.getText().toString(), editText1.getText().toString(), textView_open.getText().toString(), textView_close.getText().toString(), pID, responseListener);
            RequestQueue queue = Volley.newRequestQueue(StoreManagement.this);
            queue.add(ssequest);


        });
    }

    private long time= 0;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WaitingList.class);
        startActivity(intent);
//        if (System.currentTimeMillis() - time >= 2000) {
//            time = System.currentTimeMillis();
//            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
//        } else if (System.currentTimeMillis() - time < 2000) {
//            finishAffinity();
//            System.runFinalization();
//            System.exit(0);
//        }
    }
}