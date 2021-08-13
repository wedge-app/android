package com.example.a2021_wedge.Sajang;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.databinding.ActivityWaitinglistBinding;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class WaitingList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitinglist);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton10);
        imageButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), StoreManagement.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.list_waiting);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WaitingListAdapter adapter = new WaitingListAdapter();

        adapter.addItem(new ItemWaitingList("1", "2"));
        adapter.addItem(new ItemWaitingList("1", "2"));
        adapter.addItem(new ItemWaitingList("1", "2"));
        adapter.addItem(new ItemWaitingList("1", "2"));
        adapter.addItem(new ItemWaitingList("1", "2"));
        adapter.addItem(new ItemWaitingList("1", "2"));
        adapter.addItem(new ItemWaitingList("1", "2"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view, position) -> {

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


