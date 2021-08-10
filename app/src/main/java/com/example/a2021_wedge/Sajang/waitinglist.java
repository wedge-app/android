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

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.databinding.ActivityWaitinglistBinding;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class waitinglist extends AppCompatActivity {

    ListView listView2;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;

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


