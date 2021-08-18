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
import com.example.a2021_wedge.retrofit.ShowStoreRequest;
import com.example.a2021_wedge.retrofit.storesearchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchFrag extends Fragment {

    EditText search;
    ImageButton s;

    TextView a;
    String sname="", stel="", sintro="", saddr="", smenu="", scount="", sotime="", sctime="", senter="";
    static String[] sstorename;
    static String[] storetel;
    static String[] storeintro;
    static String[] storeaddr;
    static String[] storemenu;
    static String[] storecount;
    static String[] otime;
    static String[] ctime;
    static String[] enter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_search, container, false);


        RecyclerView recyclerView = v.findViewById(R.id.totalList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LatelySearchListAdapter adapter = new LatelySearchListAdapter();

        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                System.out.println("JSON 배열 길이 : " + jsonArray.length());

                sstorename = new String[jsonArray.length()];
                storetel = new String[jsonArray.length()];
                storeintro = new String[jsonArray.length()];
                storeaddr = new String[jsonArray.length()];
                storemenu = new String[jsonArray.length()];
                storecount = new String[jsonArray.length()];
                otime = new String[jsonArray.length()];
                ctime = new String[jsonArray.length()];
                enter = new String[jsonArray.length()];

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    sstorename[count] = object.getString("name");
                    storetel[count] = object.getString("tel");
                    storeintro[count] = object.getString("intro");
                    storeaddr[count] = object.getString("addr");
                    storemenu[count] = object.getString("menu");
                    storecount[count] = object.getString("count");
                    otime[count] = object.getString("opentime");
                    ctime[count] = object.getString("closetime");
                    enter[count] = object.getString("enter");
                    System.out.println(object.getString("count"));
                    adapter.addItem(new ItemLatelySearch(sstorename[count]));
                    count++;
                }

                recyclerView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        ShowStoreRequest showStoreRequest = new  ShowStoreRequest(responseListener);
        RequestQueue queue1 = Volley.newRequestQueue(requireContext());
        queue1.add(showStoreRequest);

        adapter.setOnItemClickListener((holder, view, position) -> {
            System.out.println("클릭됨");
            System.out.println("선택된 인덱스 : "+position);
            Intent intent = new Intent(getActivity(), enterPage.class);
            intent.putExtra("name",sstorename[position]);
            intent.putExtra("tel",storetel[position]);
            intent.putExtra("intro",storeintro[position]);
            intent.putExtra("addr",storeaddr[position]);
            intent.putExtra("menu",storemenu[position]);
            intent.putExtra("scount",storecount[position]);
            intent.putExtra("otime",otime[position]);
            intent.putExtra("ctime",ctime[position]);
            intent.putExtra("enter",enter[position]);
            startActivity(intent);
        });

        RecyclerView recyclerView2 = v.findViewById(R.id.search_list);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchListAdapter adapter2 = new SearchListAdapter();
        recyclerView2.setVisibility(View.INVISIBLE);

        recyclerView2.setAdapter(adapter2);

        a = v.findViewById(R.id.textView9);

        FrameLayout frame1 = v.findViewById(R.id.frame1);
        frame1.setOnClickListener(v1 -> hideKeyboard());

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
        s.setOnClickListener(view -> {
            if(srh != null){
                a.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
                //검색화면 구현
//                    result.setVisibility(View.VISIBLE);
                hideKeyboard();

                String search_word = search.getText().toString();

                Response.Listener<String> responseListener1 = response -> {
                    System.out.println("Listener 진입 성공/ response 값 : " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            if(sname.equals(jsonObject.getString("name"))){
                            }else {
                                System.out.println("연결 성공");
                                sname = jsonObject.getString("name");
                                stel = jsonObject.getString("tel");
                                sintro = jsonObject.getString("intro");
                                saddr = jsonObject.getString("addr");
                                smenu = jsonObject.getString("menu");
                                scount = jsonObject.getString("count");
                                sotime = jsonObject.getString("opentime");
                                sctime = jsonObject.getString("closetime");
                                senter = jsonObject.getString("enter");
                                adapter2.addItem(new ItemSearchList(sname));
                                adapter2.notifyDataSetChanged();
                            }
                        } else {
                            System.out.println("연결 실패");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };

                storesearchRequest SearchRequest = new  storesearchRequest(search_word, responseListener1);
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                queue.add(SearchRequest);
            }else{
                a.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.INVISIBLE);

                hideKeyboard();
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
            intent.putExtra("scount",scount);
            intent.putExtra("otime",sotime);
            intent.putExtra("ctime",sctime);
            intent.putExtra("enter",senter);
            startActivity(intent);
        });

        return v;
    }

    private void hideKeyboard() {
        if (getActivity() != null && getActivity().getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}