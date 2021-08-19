package com.example.a2021_wedge.MyPageFrag.Potato;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.DeleteReviewList;
import com.example.a2021_wedge.retrofit.ReviewMyListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewMyListActivity extends AppCompatActivity {

    public String sname = "", userID;
    public RecyclerView recyclerView;
    public ReviewMyListAdapter adapter2;
    int count = 0;
    Button btn, back;
    public String list, store, userID2, review, rate;
    private List<ItemReviewList> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_my_list);

        btn = findViewById(R.id.cancel);

        recyclerView = findViewById(R.id.review_list);
        adapter2 = new ReviewMyListAdapter(mItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userID = pref.getString("userID", "");

        bindDelete();

        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONArray jsonArray = jsonObject.getJSONArray("response");

                System.out.println("JSON 배열 길이 : " + jsonArray.length());

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    list = object.getString("list");
                    store = object.getString("store");
                    userID2 = object.getString("userID");
                    rate = object.getString("rate");
                    review = object.getString("review");
                    if (userID.equals(userID2))
                        mItemList.add(new ItemReviewList(store, rate, review, list));

                    count++;
                }

                recyclerView.setAdapter(adapter2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        ReviewMyListRequest reviewMyListRequest = new ReviewMyListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(reviewMyListRequest);

        ImageButton back = findViewById(R.id.back_p);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), GrowingPotatoActivity.class);
            startActivity(intent);
        });

    }

    private void bindDelete() {
        btn.setOnClickListener(v1 -> {
            final ItemReviewList recyclerItem = adapter2.getSelected();

            if (adapter2.getSelected() != null) {
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
                DeleteReviewList deleteReviewList = new DeleteReviewList(adapter2.getSelected().getList(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(btn.getContext());
                queue.add(deleteReviewList);

                // 선택한 item 삭제
                mItemList.remove(recyclerItem);

                // 선택 항목 초기화
                adapter2.clearSelected();

                // List 반영
                adapter2.notifyDataSetChanged();
            }
        });
    }

}
