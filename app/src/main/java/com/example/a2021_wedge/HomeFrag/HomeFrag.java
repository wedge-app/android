package com.example.a2021_wedge.HomeFrag;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.GMap.Stores;
import com.example.a2021_wedge.arrClass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import com.example.a2021_wedge.R;
import com.example.a2021_wedge.enterPage;
import com.example.a2021_wedge.retrofit.storesearchRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeFrag extends Fragment implements OnMapReadyCallback {
    View v;
    MapView mapView;
    TextView home_title, home_time, home_tel;
    Button enter;
    String sname="", stel="", sintro="", saddr="", smenu="";

    Stores item = new Stores();

    public HomeFrag() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    boolean isOpenPage = false;
    LinearLayout linear;
    Animation top, bottom;

    String[] items = {"한식", "중식", "양식", "일식", "패스트푸드"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_home, container, false);

        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        Spinner spinner = v.findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                textView.setText(items[position]);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
////                textView.setText("선택 : ");
//            }
//        });

        home_title = v.findViewById(R.id.text_home);
        home_time = v.findViewById(R.id.text_time);
        home_tel = v.findViewById(R.id.text_tel);
        linear = v.findViewById(R.id.linear);
        linear.setVisibility(View.INVISIBLE);
        top = AnimationUtils.loadAnimation(getContext(), R.anim.translate_top);
        bottom = AnimationUtils.loadAnimation(getContext(), R.anim.translate_bottom);

        SlidingAnimationListener listener = new SlidingAnimationListener();
        top.setAnimationListener(listener);
        bottom.setAnimationListener(listener);

        //입장 버튼
        enter = v.findViewById(R.id.button12);

        return v;
    }

    class SlidingAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isOpenPage) {
                linear.setVisibility(View.INVISIBLE);
                isOpenPage = false;
            } else {
                linear.setVisibility(View.VISIBLE);
                isOpenPage = true;
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
        LatLng latLng_new;

        for (int i = 0; i < arrClass.location_arr.length; i++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            latLng_new = new LatLng(arrClass.location_arr[i][0], arrClass.location_arr[i][1]);
            makerOptions.position(latLng_new).title(arrClass.name_arr[i]);
            //


            // 2. 마커 생성 (마커를 나타냄)
            gMap.addMarker(makerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng_new, 16));

        }

        gMap.setOnMapClickListener(latLng -> {

            if(isOpenPage){
                linear.setVisibility(View.VISIBLE);
                linear.startAnimation(bottom);
            }
        });

        gMap.setOnMarkerClickListener(marker -> {
            // Triggered when user click any marker on the map

            if (!isOpenPage) {
                linear.startAnimation(top);
            }
            home_title.setText(marker.getTitle());
//            SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
//            String openHour = test.getString("hour1", null);
//            String openMin = test.getString("min1", null);
//            String closeHour = test.getString("hour2", null);
//            String closeMin = test.getString("min2", null);
//            home_time.append("영업 시간 : " + openHour + "시 " + openMin + "분 ~ " +
//                    closeHour + "시 " + closeMin +"분");
            home_time.setText("영업 시간 : 11시 00분 ~ 22시 00분");


            String search_word = marker.getTitle();

            Response.Listener<String> responseListener = response -> {
                System.out.println("Listener 진입 성공/ response 값 : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        System.out.println("연결 성공");
                        sname = jsonObject.getString("name");
                        stel = jsonObject.getString("tel");
                        sintro = jsonObject.getString("intro");
                        saddr = jsonObject.getString("addr");
                        smenu = jsonObject.getString("menu");
                        System.out.println("sname="+sname);
                    } else {
                        System.out.println("연결 실패");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            storesearchRequest SearchRequest = new  storesearchRequest(search_word, responseListener);
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(SearchRequest);

            home_tel.setText("전화 주문 : "+stel);

            return false;


        });

        //입장 버튼 클릭시
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), enterPage.class);
                intent.putExtra("name",sname);
                intent.putExtra("tel",stel);
                intent.putExtra("intro",sintro);
                intent.putExtra("addr",saddr);
                intent.putExtra("menu",smenu);
                startActivity(intent);
            }
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