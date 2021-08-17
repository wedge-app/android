package com.example.a2021_wedge.StoreFrag;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.DeleteWaitingRequest;
import com.example.a2021_wedge.retrofit.StoreListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StoreFrag extends Fragment {

    public String sname = "", tel = "", userTel2;
    public RecyclerView recyclerView;
    public StoreListAdapter adapter2;
    int count = 0;
    Button btn;

    private List<ItemStore> mItemList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_store, container, false);

        btn = v.findViewById(R.id.cancel);

        recyclerView = v.findViewById(R.id.wait_list);
        adapter2 = new StoreListAdapter(mItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        SharedPreferences pref = this.requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userTel2 = pref.getString("userTel", "");

        bindDelete();

        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONArray jsonArray = jsonObject.getJSONArray("response");

                System.out.println("JSON 배열 길이 : " + jsonArray.length());

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    String tel = object.getString("userTel");
                    String sname = object.getString("sname");
                    if (userTel2.equals(tel)) mItemList.add(new ItemStore(sname));

                    count++;
                }

                recyclerView.setAdapter(adapter2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

//        adapter.setOnItemClickListener((holder, view, position) -> {
//            System.out.println("클릭됨");
//            adapter.notifyDataSetChanged();
//        });

        StoreListRequest loginRequest = new StoreListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(loginRequest);

        return v;
    }

    private void bindDelete() {
        btn.setOnClickListener(v1 -> {
            final ItemStore recyclerItem = adapter2.getSelected();



//            String s = textView.getText().toString();
            Response.Listener<String> responseListener = response -> {
                System.out.println("Listener 진입 성공/ response 값 : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        System.out.println("연결 성공");
                    } else {
                        System.out.println("연결 실패");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

            DeleteWaitingRequest deleteWaitingRequest = new DeleteWaitingRequest(adapter2.getSelected().getName(), responseListener);
            RequestQueue queue = Volley.newRequestQueue(btn.getContext());
            queue.add(deleteWaitingRequest);

            // 선택한 item 삭제
            mItemList.remove(recyclerItem);

            // 선택 항목 초기화
            adapter2.clearSelected();

            // List 반영
            adapter2.notifyDataSetChanged();
        });
    }

}
