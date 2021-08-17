package com.example.a2021_wedge.Sajang;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.SearchFrag.ItemLatelySearch;
import com.example.a2021_wedge.SearchFrag.SearchFrag;
import com.example.a2021_wedge.StoreFrag.ItemStore;
import com.example.a2021_wedge.databinding.ActivityWaitinglistBinding;
import com.example.a2021_wedge.enterPage;
import com.example.a2021_wedge.retrofit.LikeStoreRequest;
import com.example.a2021_wedge.retrofit.sscount;
import com.example.a2021_wedge.retrofit.sscount2;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class WaitingList extends AppCompatActivity {
    TextView title, cnt, current;
    String storename, countteam, scount;
    String[] team;
    String sname="";
    RecyclerView recyclerView;
    WaitingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitinglist);

//        SharedPreferences preff = this.getApplication().getSharedPreferences("waitlist", Context.MODE_PRIVATE);
//        String wnum = preff.getString("wwnum","");
//        String wname = preff.getString("wwname","");
//        String wnumtteam = preff.getString("wwcountteam","");

        //대기줄
        cnt = findViewById(R.id.textView18);

        title = findViewById(R.id.textView45);
        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sname = pref.getString("stitle","");
        title.setText(sname);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton10);
        imageButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), StoreManagement.class);
            startActivity(intent);
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

                team = new String[jsonArray.length()];
                int count = 0;

                //JSON 배열 길이만큼 반복문을 실행
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    storename = jsonObject.getString("storename");
                    scount = jsonObject.getString("count");
                    team[count] = object.getString("countteam");
                    System.out.println("전달할 값 : "+storename+", "+scount+","+team[count]);
                    if(sname.equals(storename)){
                        adapter.addItem(new ItemWaitingList(Integer.toString(count+1), team[count]));
                    }
                    cnt.setText(scount);
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


