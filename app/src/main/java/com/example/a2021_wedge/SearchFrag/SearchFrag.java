package com.example.a2021_wedge.SearchFrag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a2021_wedge.R;

public class SearchFrag extends Fragment {
    EditText search;
    ImageButton s, enter;

    TextView a,a1,a2,a3,b,b1,b2,b3,result;
    ImageView line, bline;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_search, container, false);

        a = v.findViewById(R.id.textView9);
        a1 = v.findViewById(R.id.textView11);
        a2 = v.findViewById(R.id.textView12);
        a3 = v.findViewById(R.id.textView13);

        b = v.findViewById(R.id.textView10);
        b1 = v.findViewById(R.id.textView14);
        b2 = v.findViewById(R.id.textView15);
        b3 = v.findViewById(R.id.textView16);


        //검색 가게 결과(검색어를 데베와 비교하여 결과 출력)
        result = v.findViewById(R.id.textView17);
        result.setVisibility(View.INVISIBLE);

        //입장 버튼
        enter = v.findViewById(R.id.imageButton5);
        enter.setVisibility(View.INVISIBLE);

        //구분선
        line = v.findViewById(R.id.imageView13);
        line.setVisibility(View.INVISIBLE);

        bline = v.findViewById(R.id.imageView12);


        //검색창
        search = v.findViewById(R.id.editTextTextPersonName5);
        String srh = search.getText().toString();  //검색어

        //돋보기 버튼
        s = v.findViewById(R.id.imageButton4);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(srh != null){
                    a.setVisibility(View.INVISIBLE);
                    a1.setVisibility(View.INVISIBLE);
                    a2.setVisibility(View.INVISIBLE);
                    a3.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    bline.setVisibility(View.INVISIBLE);

                    //검색화면 구현
                    result.setVisibility(View.VISIBLE);
                    enter.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                }else{
                    a.setVisibility(View.VISIBLE);
                    a1.setVisibility(View.VISIBLE);
                    a2.setVisibility(View.VISIBLE);
                    a3.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);

                    result.setVisibility(View.INVISIBLE);
                    enter.setVisibility(View.INVISIBLE);
                    line.setVisibility(View.INVISIBLE);
                }
            }
        });


        return v;
    }
}
