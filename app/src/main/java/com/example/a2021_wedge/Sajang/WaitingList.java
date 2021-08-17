package com.example.a2021_wedge.Sajang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.sscount2;
import com.example.a2021_wedge.retrofit.waittoggle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WaitingList extends AppCompatActivity {
    TextView title, cnt, current;
    String storename, scount, team, sname="";
    RecyclerView recyclerView;
    WaitingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitinglist);

        SharedPreferences preff = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String enter = preff.getString("enter","");
        String ID = preff.getString("userID", "");

        //입장 가능 or 대기 시작 여부
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
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
                    waittoggle ssequest = new waittoggle("1", ID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(WaitingList.this);
                    queue.add(ssequest);

                    Toast.makeText(getApplicationContext(), "지금부터 매장 대기번호를 받습니다.",Toast.LENGTH_SHORT).show();


                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
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
                    waittoggle ssequest = new waittoggle("0", ID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(WaitingList.this);
                    queue.add(ssequest);
                    Toast.makeText(getApplicationContext(), "매장 바로 입장 가능합니다.",Toast.LENGTH_SHORT).show();

                }
            }
        });

        //대기 리스트 리셋
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(view -> {
            //대기 리스트 삭제 delete
        });

        //대기줄
        cnt = findViewById(R.id.textView18);

        title = findViewById(R.id.textView45);
        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sname = pref.getString("stitle","");
        title.setText(sname);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton10);
        imageButton.setOnClickListener(view -> {
            Intent intentt = new Intent(getApplicationContext(), StoreManagement.class);
            startActivity(intentt);
        });

        recyclerView = findViewById(R.id.list_waiting);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WaitingListAdapter();


        Response.Listener<String> responseListener = response -> {
            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                System.out.println("JSON 배열 길이 : " + jsonArray.length());

//                team = new String[jsonArray.length()];
                int count = 0, list_count = 0;

                //JSON 배열 길이만큼 반복문을 실행
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
//
                    storename = object.getString("storename");
                    scount = object.getString("count");
                    team = object.getString("countteam");

                    if(sname.equals(storename)){
                        adapter.addItem(new ItemWaitingList(scount, team));
                        list_count++;
                    }
                    cnt.setText(String.valueOf(list_count));
                    count++;

                }

                recyclerView.setAdapter(adapter);

            }catch(Exception e){
                e.printStackTrace();
            }
        };

        adapter.setOnItemClickListener((holder, view, position) -> {
            System.out.println("클릭됨");
            //adapter.notifyDataSetChanged();
        });
        //서버로 Volley를 이용해서 요청
        sscount2 request = new  sscount2(responseListener);
        RequestQueue queue = Volley.newRequestQueue(WaitingList.this);
        queue.add(request);
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


