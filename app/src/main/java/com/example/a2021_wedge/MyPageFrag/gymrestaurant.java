package com.example.a2021_wedge.MyPageFrag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.MyPageFrag.Potato.GrowingPotatoActivity;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.Sajang.StoreManagement;
import com.example.a2021_wedge.SearchFrag.ItemLatelySearch;
import com.example.a2021_wedge.enterPage;
import com.example.a2021_wedge.retrofit.LikeStoreListRequest;
import com.example.a2021_wedge.retrofit.LikeStoreRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class gymrestaurant extends AppCompatActivity {

    private ListView gym_listview;
    ArrayAdapter<String> adapter2;
    ArrayList<String> listItem2;

    String uname, sname;
    static String res ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymrestaurant);

        new BackgroundTask().execute();
        System.out.println("res 값 : "+res);

        gym_listview = (ListView) findViewById(R.id.gym_listview);

        listItem2 = new ArrayList<String>();

        gym_listview.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = new Intent(gymrestaurant.this, enterPage.class);
            startActivity(intent);
        });

        SharedPreferences pref = this.getApplication().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uname2 = pref.getString("userName","");

        try{
            //JSONObject타입으로 가져옵니다(res값 오류)
            JSONObject jsonObject = new JSONObject(res);

            //List.php 웹페이지에서 response라는 변수명으로 JSON 배열을 만들었음..
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            System.out.println("JSON 배열 길이 : "+jsonArray.length());

            //JSON 배열 길이만큼 반복문을 실행
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                uname = object.getString("uname");
                sname = object.getString("sname");
                if(uname.equals(uname2)) listItem2.add(sname);

                count++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        adapter2 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,listItem2);


        gym_listview.setAdapter(adapter2);

//        System.out.println(uname);
        //서버로 Volley를 이용해서 요청
//        LikeStoreListRequest likestorerequest = new LikeStoreListRequest(uname, responseListener);
//        RequestQueue queue = Volley.newRequestQueue(gymrestaurant.this);
//        queue.add(likestorerequest);
        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });

    }
    private long time= 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

    public class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            //파싱으로 가져올 웹페이지
            target = "http://mywedge21.dothome.co.kr/likestorelist.php";
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