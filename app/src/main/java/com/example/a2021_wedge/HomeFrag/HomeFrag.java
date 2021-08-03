package com.example.a2021_wedge.HomeFrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.a2021_wedge.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_home, container, false);

        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return v;
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
        GoogleMap gMap = googleMap;

        MapsInitializer.initialize(this.getActivity());
        double[][] location = {{37.59193, 127.01847},{37.59210, 127.01841}};
        LatLng latLng_new;

        // for loop를 통한 n개의 마커 생성
        for (int i = 0; i < location.length; i++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            latLng_new = new LatLng(location[i][0], location[i][1]);
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(latLng_new); // 타이틀.

            // 2. 마커 생성 (마커를 나타냄)
            gMap.addMarker(makerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng_new,18));

        }


        /*LatLng latLng = new LatLng(37.59193, 127.01847);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .title("언앨리셰프")
                .snippet("아늑한 분위기에서 즐기는 파스타와 피자");
        googleMap.addMarker(markerOptions);

*/

    }

}
