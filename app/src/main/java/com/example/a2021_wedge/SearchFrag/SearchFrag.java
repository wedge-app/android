package com.example.a2021_wedge.SearchFrag;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.enterPage;
import com.example.a2021_wedge.retrofit.storesearchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


public class SearchFrag extends Fragment {

    String storename;
    EditText search;
    ImageButton s, enter;

    TextView a,a1,a2,a3,b,b1,b2,b3,result;
    String sname="", stel="", sintro="", saddr="", smenu="";
    static String[] sstorename;
    static String[] storetel;
    static String[] storeintro;
    static String[] storeaddr;
    static String[] storemenu;
    static String res ="";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_search, container, false);


        new BackgroundTask().execute();
        System.out.println("res 값 : "+res);

        RecyclerView recyclerView = v.findViewById(R.id.totalList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LatelySearchListAdapter adapter = new LatelySearchListAdapter();

        try{
            //JSONObject타입으로 가져옵니다(한글만 됨)
            JSONObject jsonObject = new JSONObject(res);

            //List.php 웹페이지에서 response라는 변수명으로 JSON 배열을 만들었음..
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            System.out.println("JSON 배열 길이 : "+jsonArray.length());

            sstorename = new String[jsonArray.length()];
            storetel = new String[jsonArray.length()];
            storeintro = new String[jsonArray.length()];
            storeaddr = new String[jsonArray.length()];
            storemenu = new String[jsonArray.length()];

            //JSON 배열 길이만큼 반복문을 실행
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                sstorename[count] = object.getString("name");
                storetel[count] = object.getString("tel");
                storeintro[count] = object.getString("intro");
                storeaddr[count] = object.getString("addr");
                storemenu[count] = object.getString("menu");
                adapter.addItem(new ItemLatelySearch(sstorename[count]));
                count++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view, position) -> {
            System.out.println("클릭됨");
            System.out.println("선택된 인덱스 : "+position);
            Intent intent = new Intent(getActivity(), enterPage.class);
            intent.putExtra("name",sstorename[position]);
            intent.putExtra("tel",storetel[position]);
            intent.putExtra("intro",storeintro[position]);
            intent.putExtra("addr",storeaddr[position]);
            intent.putExtra("menu",storemenu[position]);
            startActivity(intent);
        });


        RecyclerView recyclerView2 = v.findViewById(R.id.search_list);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchListAdapter adapter2 = new SearchListAdapter();
        recyclerView2.setVisibility(View.INVISIBLE);


        recyclerView2.setAdapter(adapter2);


        a = v.findViewById(R.id.textView9);

        //검색 가게 결과(검색어를 데베와 비교하여 결과 출력)
//        result = v.findViewById(R.id.textView17);
//        result.setVisibility(View.INVISIBLE);

        FrameLayout frame1 = v.findViewById(R.id.frame1);
        frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        //검색창
        search = v.findViewById(R.id.editTextTextPersonName5);
        String srh = search.getText().toString();  //검색어

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = search.getText().toString();
                if (s1.length() == 0) {
                    a.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.INVISIBLE);
//                    result.setVisibility(View.INVISIBLE);
                    hideKeyboard();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //돋보기 버튼
        s = v.findViewById(R.id.imageButton4);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(srh != null){
                    a.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    //검색화면 구현
//                    result.setVisibility(View.VISIBLE);
                    hideKeyboard();

                    String search_word = search.getText().toString();

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
                                adapter2.addItem(new ItemSearchList(sname));
                                adapter2.notifyDataSetChanged();
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
                }else{
                    a.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.INVISIBLE);
//                    result.setVisibility(View.INVISIBLE);
                    hideKeyboard();


                }
            }
        });

        adapter2.setOnItemClickListener((holder, view, position) -> {
            System.out.println("클릭됨2");
            Intent intent = new Intent(getActivity(), enterPage.class);
            intent.putExtra("name",sname); /*송신*/
            intent.putExtra("tel",stel);
            intent.putExtra("intro",sintro);
            intent.putExtra("addr",saddr);
            intent.putExtra("menu",smenu);
            startActivity(intent);
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

    private void hideKeyboard() {
        if (getActivity() != null && getActivity().getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    //모든 가게에 대한 정보를 가져오기 위한 스레드
    public class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            //파싱으로 가져올 웹페이지
            target = "http://mywedge21.dothome.co.kr/showstores.php";
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