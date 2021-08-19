package com.example.a2021_wedge.MyPageFrag.Potato;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a2021_wedge.MyPageFrag.MyPageFrag;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.retrofit.ReviewMYCountRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class GrowingPotatoActivity extends AppCompatActivity {
    ImageButton back;
    Button b, a;
    TextView id, review_num, info_levelup;
    int nums = 0;
    String userID2, s, s2;
    ImageView image, image2, image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growing_potato);

        image = findViewById(R.id.image_hpotato);
        image.setVisibility(View.INVISIBLE);
        image2 = findViewById(R.id.image_bpotato);
        image2.setVisibility(View.INVISIBLE);
        image3 = findViewById(R.id.image_wpotato);
        image3.setVisibility(View.INVISIBLE);


        b = findViewById(R.id.button_re);
        b.setOnClickListener(view -> {
            Intent i = new Intent(GrowingPotatoActivity.this, TotalPotato.class);
            startActivity(i);
        });
        //뒤로 가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyPageFrag.class);
            startActivity(intent);

        });

        a = findViewById(R.id.button_ps);
        a.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReviewMyListActivity.class);
            startActivity(intent);
        });

        id = findViewById(R.id.textView44);
        SharedPreferences pref = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String prefname = pref.getString("userName", "");
        id.setText(prefname);

        String userID = pref.getString("userID", "");
        review_num = findViewById(R.id.review_num);
        info_levelup = findViewById(R.id.info_levelup);

        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONArray jsonArray = jsonObject.getJSONArray("response");

                int count = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    userID2 = object.getString("userID");

                    System.out.println(userID + userID2 + nums);
                    if (userID.equals(userID2)) {
                        nums++;
                        s = "리뷰 작성 횟수 : " + nums + " 번";
                        if(nums < 5) {
                            s2 = "바삭 감자가 되기 위해서는... \n" + (5-nums) + "번의 리뷰 작성이 필요해요~";
                            image.setVisibility(View.VISIBLE);
                            image2.setVisibility(View.INVISIBLE);
                            image3.setVisibility(View.INVISIBLE);

                        }
                        else if(nums < 10) {
                            s2 = "웨지 감자가 되기 위해서는... \n" + (10-nums) + "번의 리뷰 작성이 필요해요~";
                            image.setVisibility(View.INVISIBLE);
                            image2.setVisibility(View.VISIBLE);
                            image3.setVisibility(View.INVISIBLE);
                        }
                        else {
                            s2 = "멋쟁이 웨지 감자인 당신!\n 멋쟁이라고~!";
                            image.setVisibility(View.INVISIBLE);
                            image2.setVisibility(View.INVISIBLE);
                            image3.setVisibility(View.VISIBLE);
                        }
                        review_num.setText(s);
                        info_levelup.setText(s2);
                    }
                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        ReviewMYCountRequest reviewMYCountRequest = new ReviewMYCountRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(reviewMYCountRequest);


    }
}
