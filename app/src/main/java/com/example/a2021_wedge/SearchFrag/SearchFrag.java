package com.example.a2021_wedge.SearchFrag;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.res.AssetManager;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.First.Login;
import com.example.a2021_wedge.MyPageFrag.PersonalInfoActivity;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.enterPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchFrag extends Fragment {

    private ArrayList<searchItem> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private searchAdapter adapter;
    String storename;
    EditText search;
    ImageButton s, enter;

    TextView a,a1,a2,a3,b,b1,b2,b3,result;
    ImageView line, bline;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_search, container, false);

//        RecyclerView recyclerView = v.findViewById(R.id.searchview);
//        recyclerView.setHasFixedSize(true);
//
//        adapter = new searchAdapter(list);
//
//        RecyclerView.LayoutManager mLayoutm = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutm);
//        recyclerView.scrollToPosition(0);
//
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//
//        a = v.findViewById(R.id.textView9);
//        a1 = v.findViewById(R.id.textView11);

        //검색 가게 결과(검색어를 데베와 비교하여 결과 출력)
        result = v.findViewById(R.id.textView17);
        result.setVisibility(View.INVISIBLE);

        //입장 버튼
        enter = v.findViewById(R.id.imageButton5);
        enter.setVisibility(View.INVISIBLE);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

        RecyclerView recyclerView = v.findViewById(R.id.lately_search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LatelySearchListAdapter adapter = new LatelySearchListAdapter();

        adapter.addItem(new ItemLatelySearch("1"));
        adapter.addItem(new ItemLatelySearch("1"));
        adapter.addItem(new ItemLatelySearch("1"));
        adapter.addItem(new ItemLatelySearch("1"));
        adapter.addItem(new ItemLatelySearch("1"));
        adapter.addItem(new ItemLatelySearch("1"));


        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view, position) -> {
            System.out.println("클릭됨");
        });


        return v;
    }

//    public void clickBtn() {
//        //json 파일 읽어와서 분석하기
//        //assets폴더의 파일을 가져오기 위해
//        //창고관리자(AssetManager) 얻어오기
//        AssetManager assetManager= getAssets();
//
//        //assets/ youngsangu.json 파일 읽기 위한 InputStream
//        try {
//            InputStream is= assetManager.open("youngsangu.json");
//            InputStreamReader isr= new InputStreamReader(is);
//            BufferedReader reader= new BufferedReader(isr);
//
//            StringBuffer buffer= new StringBuffer();
//            String line= reader.readLine();
//            while (line!=null){
//                buffer.append(line+"\n");
//                line=reader.readLine();
//            }
//
//            String jsonData= buffer.toString();
//
//            //json 데이터가 []로 시작하는 배열일때..
//            JSONArray jsonArray= new JSONArray(jsonData);
//
//            for(int i=0; i<jsonArray.length();i++){
//                JSONObject jo=jsonArray.getJSONObject(i);
//
//                storename= jo.getString("업소명");
//                list.add(new searchItem(storename, ContextCompat.getDrawable(getContext(), R.drawable.button_enter)));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        clickBtn();
//    }

//    private  void prepareData(){
//        list.add(new searchItem(storename, ContextCompat.getDrawable(getContext(), R.drawable.button_enter)));
//
//    }
}
