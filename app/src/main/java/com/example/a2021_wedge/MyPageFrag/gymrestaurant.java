package com.example.a2021_wedge.MyPageFrag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.SearchFrag.ItemLatelySearch;
import com.example.a2021_wedge.SearchFrag.SearchFrag;
import com.example.a2021_wedge.enterPage;
import com.example.a2021_wedge.retrofit.LikeStoreListRequest;
import com.example.a2021_wedge.retrofit.ShowStoreRequest;
import com.example.a2021_wedge.retrofit.storesearchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class gymrestaurant extends AppCompatActivity {

    ListView gym_listview;
    ArrayAdapter<String> adapter2;
    ArrayList<String> listItem2;
    String[] ss;

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


        gym_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {

            }
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}