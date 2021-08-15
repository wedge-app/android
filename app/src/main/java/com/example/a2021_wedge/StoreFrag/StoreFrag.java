package com.example.a2021_wedge.StoreFrag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2021_wedge.First.join;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.SearchFrag.ItemLatelySearch;
import com.example.a2021_wedge.SearchFrag.LatelySearchListAdapter;
import com.example.a2021_wedge.SearchFrag.SearchFrag;
import com.example.a2021_wedge.enterPage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class StoreFrag extends Fragment {
    static String res ="", sname = "", tel = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_store, container, false);

        new StoreFrag.BackgroundTask().execute();
        System.out.println("res 값 : "+res);

        RecyclerView recyclerView = v.findViewById(R.id.wait_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StoreListAdapter adapter = new StoreListAdapter();

        SharedPreferences pref = this.requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userTel2 = pref.getString("userTel","");

        try{
            //JSONObject타입으로 가져옵니다(한글만 됨)
            JSONObject jsonObject = new JSONObject(res);

            //List.php 웹페이지에서 response라는 변수명으로 JSON 배열을 만들었음..
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            System.out.println("JSON 배열 길이 : "+jsonArray.length());

            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                tel = object.getString("userTel");
                sname = object.getString("sname");
                if(userTel2.equals(tel)) adapter.addItem(new ItemStore(sname));

                count++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view, position) -> {
            System.out.println("클릭됨");
            adapter.notifyDataSetChanged();
        });

        return v;
    }

    public class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            //파싱으로 가져올 웹페이지
            target = "http://mywedge21.dothome.co.kr/waitingstorelist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);//URL 객체 생성

                //URL을 이용해서 웹페이지에 연결하는 부분
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                //바이트단위 입력스트림 생성 소스는 httpURLConnection
                InputStream inputStream = httpURLConnection.getInputStream();

                //웹페이지 출력물을 버퍼로 받음 버퍼로 하면 속도가 더 빨라짐
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                //문자열 처리를 더 빠르게 하기 위해 StringBuilder클래스를 사용함
                StringBuilder stringBuilder = new StringBuilder();

                //한줄씩 읽어서 stringBuilder에 저장함
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            res = result;
        }
    }

}
