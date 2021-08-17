package com.example.a2021_wedge.Sajang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.sscount2;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
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

        //새로고침
        current = findViewById(R.id.current);
        current.setOnClickListener(view -> {
        });

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


