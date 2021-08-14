package com.example.a2021_wedge.SearchFrag;

import android.content.Context;
import android.content.Intent;

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

import org.json.JSONException;
import org.json.JSONObject;

public class SearchFrag extends Fragment {

    String storename;
    EditText search;
    ImageButton s, enter;

    TextView a,a1,a2,a3,b,b1,b2,b3,result;
    String sname="", stel="", sintro="", saddr="", smenu="";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_search, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.totalList);
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

        RecyclerView recyclerView2 = v.findViewById(R.id.search_list);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchListAdapter adapter2 = new SearchListAdapter();
        recyclerView2.setVisibility(View.INVISIBLE);


//        adapter2.addItem(new ItemSearchList("2"));
//        adapter2.addItem(new ItemSearchList("2"));
//        adapter2.addItem(new ItemSearchList("2"));
//        adapter2.addItem(new ItemSearchList("2"));
//        adapter2.addItem(new ItemSearchList("2"));
//        adapter2.addItem(new ItemSearchList("2"));
//        adapter2.addItem(new ItemSearchList("2"));

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

                    //서버로 Volley를 이용해서 요청
//                    SearchRequest SearchRequest = new SearchRequest(search_word, responseListener);
//                    RequestQueue queue = Volley.newRequestQueue(getContext());
//                    queue.add(SearchRequest);
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


}
