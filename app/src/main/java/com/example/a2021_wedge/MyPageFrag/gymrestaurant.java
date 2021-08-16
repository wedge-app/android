package com.example.a2021_wedge.MyPageFrag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.enterPage;
import com.example.a2021_wedge.retrofit.LikeStoreListRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class gymrestaurant extends AppCompatActivity {

    ListView gym_listview;
    ArrayAdapter<String> adapter2;
    ArrayList<String> listItem2;

    String uname, sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymrestaurant);

        gym_listview = findViewById(R.id.gym_listview);

        listItem2 = new ArrayList<>();

        gym_listview.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = new Intent(gymrestaurant.this, enterPage.class);
            startActivity(intent);
        });

        SharedPreferences pref = this.getApplication().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uname2 = pref.getString("userName","");

        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                System.out.println("JSON 배열 길이 : " + jsonArray.length());

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    uname = object.getString("uname");
                    sname = object.getString("sname");
                    if (uname.equals(uname2)) listItem2.add(sname);

                    count++;
                }
                adapter2 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,listItem2);
                gym_listview.setAdapter(adapter2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        LikeStoreListRequest likestorerequest = new LikeStoreListRequest(uname, responseListener);
        RequestQueue queue = Volley.newRequestQueue(gymrestaurant.this);
        queue.add(likestorerequest);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
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