package com.example.a2021_wedge.bottomBar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a2021_wedge.HomeFrag.HomeFrag;
import com.example.a2021_wedge.MyPageFrag.MyPageFrag;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.SearchFrag.SearchFrag;
import com.example.a2021_wedge.StoreFrag.StoreFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new HomeFrag()).commit();

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home)
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFrag()).commit();
            else if (item.getItemId() == R.id.nav_search)
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SearchFrag()).commit();
            else if (item.getItemId() == R.id.nav_store)
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new StoreFrag()).commit();
            else if (item.getItemId() == R.id.nav_myPage)
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MyPageFrag()).commit();

            return false;
        });

    }
}