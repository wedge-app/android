package com.example.a2021_wedge.Sajang;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.databinding.ActivityWaitinglistBinding;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class waitinglist extends AppCompatActivity {
    RecyclerView rv;
    ListAdapter adapter;
    ItemTouchHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitinglist);

        rv = findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

        adapter = new ListAdapter();
        rv.setAdapter(adapter);

        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(rv);

        Person person1 = new Person(R.drawable.selectbox, "뱃시" , 1);
        Person person2 = new Person(R.drawable.selectbox, "딕" , 2);
        Person person3 = new Person(R.drawable.selectbox, "제이슨" , 3);
        Person person4 = new Person(R.drawable.selectbox, "티미" , 4);
        adapter.addItem(person1);
        adapter.addItem(person2);
        adapter.addItem(person3);
        adapter.addItem(person4);
    }
}

/*
public class waitinglist extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitinglist);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton10);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreManagement.class);
                startActivity(intent);
            }
        });
    }
}
 */


