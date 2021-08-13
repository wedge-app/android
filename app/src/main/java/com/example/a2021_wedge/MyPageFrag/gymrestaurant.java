package com.example.a2021_wedge.MyPageFrag;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2021_wedge.MyPageFrag.Potato.GrowingPotatoActivity;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Sajang.StoreManagement;
import com.example.a2021_wedge.enterPage;

import java.util.ArrayList;

public class gymrestaurant extends AppCompatActivity {

    private ListView gym_listview;
    ArrayAdapter<String> adapter2;
    ArrayList<String> listItem2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymrestaurant);

        gym_listview = (ListView) findViewById(R.id.gym_listview);

        listItem2 = new ArrayList<String>();
        listItem2.add("동대문 엽기떡볶이 성신여대점");

        adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listItem2);
        gym_listview = findViewById(R.id.gym_listview);
        gym_listview.setAdapter(adapter2);

        gym_listview.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = new Intent(gymrestaurant.this, enterPage.class);
            startActivity(intent);
        });

    }

}