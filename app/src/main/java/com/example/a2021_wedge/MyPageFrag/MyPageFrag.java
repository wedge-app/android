package com.example.a2021_wedge.MyPageFrag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.MyPageFrag.Potato.GrowingPotatoActivity;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Rev.Review;

public class MyPageFrag extends Fragment {

    Button growing_potato, growing_fixinfo, favorite_store, faq, notice;
    TextView email, text_logout;

    private String mJsonString;
    private TextView userName;
    private String SERVER_URL = "http://8c31442c9fdb.ngrok.io/phpMyAdmin-5.1.1/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_my_page, container, false);


        email = v.findViewById(R.id.textView28);

        //유저명 변경
        email = v.findViewById(R.id.textView28);
        SharedPreferences pref = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String prefname = pref.getString("userName","");
        email.setText(prefname);

        growing_potato = v.findViewById(R.id.growing_potato);
        growing_potato.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), GrowingPotatoActivity.class);
            startActivity(intent);
        });

        growing_fixinfo = v.findViewById(R.id.growing_fixinfo);
        growing_fixinfo.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), fixmyinfo.class);
            startActivity(intent);
        });

        favorite_store = v.findViewById(R.id.favorite_store);
        favorite_store.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), gymrestaurant.class);
            startActivity(intent);
        });

        faq = v.findViewById(R.id.faq);
        faq.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), FAQActivity.class);
            startActivity(intent);
        });

        notice = v.findViewById(R.id.notice);
        notice.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), NoticeActivity.class);
            startActivity(intent);
        });

        text_logout = v.findViewById(R.id.text_logout);
        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_logout(v);
            }
        });


        return v;

    }


    public void btn_logout(View v) {
        new AlertDialog.Builder(getActivity())
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(getActivity(), Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }
}