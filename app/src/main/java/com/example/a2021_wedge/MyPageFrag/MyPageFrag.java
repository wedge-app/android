package com.example.a2021_wedge.MyPageFrag;

import android.content.Context;
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

import com.example.a2021_wedge.MyPageFrag.Potato.GrowingPotatoActivity;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Review;

public class MyPageFrag extends Fragment {

    Button personal_info, growing_potato, favorite_store, develop_story, faq, notice;
    TextView email;

    private String mJsonString;
    private TextView userName;
    private String SERVER_URL = "http://8c31442c9fdb.ngrok.io/phpMyAdmin-5.1.1/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_my_page, container, false);

        userName = v.findViewById(R.id.user_name);
        userName.setMovementMethod(new ScrollingMovementMethod());

        email = v.findViewById(R.id.textView28);

        //유저명 변경
        email = v.findViewById(R.id.textView28);
        SharedPreferences pref = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String prefname = pref.getString("userName","");
        email.setText(prefname);


        personal_info = v.findViewById(R.id.personal_info);

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        personal_info.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
            startActivity(intent);
        });

        growing_potato = v.findViewById(R.id.growing_potato);
        growing_potato.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), GrowingPotatoActivity.class);
            startActivity(intent);
        });

        favorite_store = v.findViewById(R.id.favorite_store);
        favorite_store.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), gymrestaurant.class);
            startActivity(intent);
        });

        faq = v.findViewById(R.id.faq);
        faq.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), Review.class);
            startActivity(intent);
        });

        notice = v.findViewById(R.id.notice);
        notice.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), NoticeActivity.class);
            startActivity(intent);
        });

        develop_story = v.findViewById(R.id.develop_story);
        develop_story.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), DevelopStoryActivity.class);
            startActivity(intent);
        });

        return v;

    }
}