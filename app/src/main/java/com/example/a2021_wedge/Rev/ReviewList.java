package com.example.a2021_wedge.Rev;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Bundle;


import com.example.a2021_wedge.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReviewList extends AppCompatActivity {

    private static String ADDRESS = "http://mywedge21.dothome.co.kr/reviewlist.php";
    private static String TAG = "phpreviewdownload";

    private TextView mTextViewResult;
    private ArrayList<ReviewData> mArrayList;
    private ReviewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    Button button_review;

    String store;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

            SharedPreferences storeName = getSharedPreferences("storeName", MODE_PRIVATE);
        store = storeName.getString("store","");
        //String userID = storeName.getString("userID","");

        GetData task = new GetData();
        task.execute( ADDRESS, store);


        //mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView)findViewById(R.id.listView_main_list);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), 1));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        mArrayList = new ArrayList<>();

        mAdapter = new ReviewAdapter(mArrayList);


        mRecyclerView.setAdapter(mAdapter);

        button_review = findViewById(R.id.button_review);

        button_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Review.class);
                startActivity(i);
            }
        });

        ImageButton b = findViewById(R.id.back_p);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReviewList.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

               // mTextViewResult.setText(errorString);
                System.out.println("result == null");
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String store = params[1];
            //String userID = params[2];

            String postParameters = "store=" + store ;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();




            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="user";
        String TAG_store="store";
        String TAG_userID ="userID";
        String TAG_rate = "rate";
        String TAG_review="review";



        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                String rate = item.getString(TAG_rate);
                String userID = item.getString(TAG_userID);
                String store =item.getString(TAG_store);
                String review =item.getString(TAG_review);


                ReviewData ReviewData = new ReviewData();

                ReviewData.setRate(rate);
                ReviewData.setUserId(userID);
                ReviewData.setStoreName(store);
                ReviewData.setReview(review);


                mArrayList.add(ReviewData);
                mAdapter.notifyDataSetChanged();

            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }



}
