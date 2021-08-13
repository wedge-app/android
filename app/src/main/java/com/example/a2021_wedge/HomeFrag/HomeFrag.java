package com.example.a2021_wedge.HomeFrag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.a2021_wedge.MyPageFrag.Potato.GrowingPotatoActivity;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Sajang.StoreManagement;
import com.example.a2021_wedge.bottomBar.MainActivity;
import com.example.a2021_wedge.enterPage;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFrag extends Fragment implements OnMapReadyCallback  {
    View v;
    MapView mapView;

    public HomeFrag() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    boolean isOpenPage = false;
    LinearLayout linear;
    Animation top, bottom;
    Button btn_slide;

    String[] items = {"한식", "중식", "양식", "일식", "패스트푸드"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_home, container, false);

        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Spinner spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                textView.setText(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                textView.setText("선택 : ");
            }
        });

        linear = v.findViewById(R.id.linear);
        linear.setVisibility(View.INVISIBLE);
        top = AnimationUtils.loadAnimation(getContext(), R.anim.translate_top);
        bottom = AnimationUtils.loadAnimation(getContext(), R.anim.translate_bottom);

        SlidingAnimationListener listener = new SlidingAnimationListener();
        top.setAnimationListener(listener);
        bottom.setAnimationListener(listener);

        btn_slide = v.findViewById(R.id.btn_slide);
        btn_slide.setOnClickListener(v -> {
            if(isOpenPage){
                linear.startAnimation(bottom);
            }else{
                linear.setVisibility(View.VISIBLE);
                linear.startAnimation(top);

            }
        });

        return v;
    }

    class SlidingAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isOpenPage){
                linear.setVisibility(View.INVISIBLE);
                btn_slide.setText("열기");
                isOpenPage=false;
            }else{
                linear.setVisibility(View.VISIBLE);
                btn_slide.setText("닫기");
                isOpenPage=true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //37.591308, 127.022128 성신여대
        //37.59193, 127.01847 언앨리셰프
        //37.59210, 127.01841 제순식당
        GoogleMap gMap = googleMap;



        MapsInitializer.initialize(this.getActivity());
        double[][] location = {{37.59193, 127.01847},{37.59210, 127.01841}};
        LatLng latLng_new;

        // for loop를 통한 n개의 마커 생성
        for (int i = 0; i < location.length; i++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            latLng_new = new LatLng(location[i][0], location[i][1]);
            makerOptions.position(latLng_new); //

            // 2. 마커 생성 (마커를 나타냄)
            gMap.addMarker(makerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng_new,18));

        }

        gMap.setOnMarkerClickListener(marker -> {
            // Triggered when user click any marker on the map

            if(isOpenPage){
                linear.startAnimation(bottom);
            }else{
                linear.setVisibility(View.VISIBLE);
                linear.startAnimation(top);

            }
            return false;
        });


        /*LatLng latLng = new LatLng(37.59193, 127.01847);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .title("언앨리셰프")
                .snippet("아늑한 분위기에서 즐기는 파스타와 피자");
        googleMap.addMarker(markerOptions);

*/

    }

}