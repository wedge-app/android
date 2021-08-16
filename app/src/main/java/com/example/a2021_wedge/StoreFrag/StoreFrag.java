package com.example.a2021_wedge.StoreFrag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.StoreListRequest;

import org.json.JSONArray;
import org.json.JSONObject;


public class StoreFrag extends Fragment {

    public String sname = "", tel = "", userTel2;
    public StoreListAdapter adapter;
    public RecyclerView recyclerView;
    int count = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_store, container, false);

        recyclerView = v.findViewById(R.id.wait_list);
        adapter = new StoreListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        SharedPreferences pref = this.requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userTel2 = pref.getString("userTel", "");

        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONArray jsonArray = jsonObject.getJSONArray("response");

                System.out.println("JSON 배열 길이 : " + jsonArray.length());

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    String tel = object.getString("userTel");
                    String sname = object.getString("sname");
                    if (userTel2.equals(tel)) adapter.addItem(new ItemStore(sname));

                    count++;
                }

                recyclerView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        adapter.setOnItemClickListener((holder, view, position) -> {
            System.out.println("클릭됨");
            adapter.notifyDataSetChanged();
        });

        StoreListRequest loginRequest = new StoreListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(loginRequest);

        return v;
    }
}
