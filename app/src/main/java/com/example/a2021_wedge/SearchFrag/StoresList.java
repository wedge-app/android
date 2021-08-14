package com.example.a2021_wedge.SearchFrag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2021_wedge.R;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class StoresList extends AppCompatActivity {
//    private ArrayList<searchItem> list = new ArrayList<>();
//    private RecyclerView recyclerView;
//    private searchAdapter adapter;
//    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_list);

        TextView txt = findViewById(R.id.textView10);

//        RecyclerView recyclerView = findViewById(R.id.searchview);
//        recyclerView.setHasFixedSize(true);
//
//        adapter = new searchAdapter(list);
//
//        RecyclerView.LayoutManager mLayoutm = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(mLayoutm);
//        recyclerView.scrollToPosition(0);
//
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);

        //json 파일 읽어와서 분석하기
        //assets폴더의 파일을 가져오기 위해
        //창고관리자(AssetManager) 얻어오기
        AssetManager assetManager= getAssets();

        //assets/ youngsangu.json 파일 읽기 위한 InputStream
        try {
            InputStream is= assetManager.open("youngsangu.json");
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader reader= new BufferedReader(isr);

            StringBuffer buffer= new StringBuffer();
            String line= reader.readLine();
            while (line!=null){
                buffer.append(line+"\n");
                line=reader.readLine();
            }

            String jsonData= buffer.toString();

            //json 데이터가 []로 시작하는 배열일때..
            JSONArray jsonArray= new JSONArray(jsonData);
            String s ="";

            for(int i=0; i<jsonArray.length();i++){
                JSONObject jo=jsonArray.getJSONObject(i);

                String storename= jo.getString("업소명");
                s += storename+"\n";
                //list.add(new searchItem(storename, ContextCompat.getDrawable(this, R.drawable.button_enter)));
            }
            txt.setText(s);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//    public void clickBtn(View view) {
//
//        //json 파일 읽어와서 분석하기
//
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
//            //json 데이터
//            JSONObject jo= new JSONObject(jsonData);
//
//            String storename= jo.getString("업소명");
//            String introduce= jo.getString("사장님이자랑하는내가게한마디");
//            int type= jo.getInt("종류(01한식,02중식,03일식,04양식,05기타외국음식,06디저트/카페)");
//            String tema= jo.getString("테마(국가)");
//            String menu= jo.getString("주요요리");
//            String addr1= jo.getString("주소1");
//            String addr2= jo.getString("주소2");
//            String tel= jo.getString("전화번호");
//
//            textView.setText(storename+"\n"+introduce+"\n"+tel+"\n"+addr1+" "+addr2+"\n"+menu+"\n");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

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
}