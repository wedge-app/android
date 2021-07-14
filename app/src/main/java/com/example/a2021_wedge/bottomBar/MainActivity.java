package com.example.a2021_wedge.bottomBar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a2021_wedge.HomeFrag.HomeFrag;
import com.example.a2021_wedge.MyPageFrag.MyPageFrag;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.SearchFrag.SearchFrag;
import com.example.a2021_wedge.StoreFrag.StoreFrag;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    //Button b;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        /*b = findViewById(R.id.goMap);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainHome.class);
                startActivity(i);
            }
        });*/


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        //37.591308, 127.022128
        //37.592641, 127.016387
        LatLng latLng = new LatLng(37.591308, 127.022128);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("성신여대");
        markerOptions.snippet("3단 언덕 맛집!!");
        googleMap.addMarker(markerOptions);


        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
    }
}