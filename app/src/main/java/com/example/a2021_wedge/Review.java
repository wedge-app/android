package com.example.a2021_wedge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a2021_wedge.MyPageFrag.MyPageFrag;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Review extends AppCompatActivity {

    String store;
    String userID;
    float rate;
    private AlertDialog dialog;
    String temp="";
    RatingBar ratingBar;
    TextView stars;
    TextView textView_result;
    EditText reviewEdit;
    Button button_review_add;
    Button button_review_cancel;

    private static String ADDRESS = "http://mywedge21.dothome.co.kr/storecon.php";
    private static final String TAG = "phpreviewup";
    public static final int sub = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        SharedPreferences storeName = getSharedPreferences("storeName", MODE_PRIVATE);
        store = storeName.getString("store", "");
        userID = storeName.getString("userID", "");
        System.out.println();

        //Intent intent = getIntent();
        //store = intent.getExtras().getString("store");
        //userID = intent.getExtras().getString("userID");

        textView_result = (TextView)findViewById(R.id.textView_result);
        textView_result.setMovementMethod(new ScrollingMovementMethod());

        EditText reviewEdit=(EditText) findViewById(R.id.reviewEdit);
        button_review_add = findViewById(R.id.button_review_add);
        button_review_cancel = findViewById(R.id.button_review_cancel);


        button_review_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Review.this, enterPage.class);
                startActivity(intent);
            }
        });

        stars = findViewById(R.id.stars);

        stars.setText("나의 별점은...");

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                stars.setText(Float.toString(rating) + "점");
                rate = rating;
            }
        });

        button_review_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Review.InsertData task = new InsertData();
                task.execute(ADDRESS, store, userID, Float.toString(rate), reviewEdit.getText().toString());

                System.out.println(store + userID + Float.toString(rate) + reviewEdit.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(Review.this);
                dialog = builder.setMessage("리뷰작성이 완료되었습니다.")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })

                        .create();
                dialog.show();


                return;
            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Review.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected String doInBackground(String... params) {

            String store = (String)params[1];
            String userID = (String)params[2];
            String rate = (String)params[3];
            String review = (String)params[4];


            String serverURL = (String)params[0];
            String postParameters = "store=" + store + "&userID=" + userID + "&rate=" + rate + "&review=" + review;


            try {

                URL url5 = new URL(serverURL);
                HttpURLConnection httpURLConnection5 = (HttpURLConnection) url5.openConnection();


                httpURLConnection5.setReadTimeout(5000);
                httpURLConnection5.setConnectTimeout(5000);
                httpURLConnection5.setRequestMethod("POST");
                httpURLConnection5.connect();


                OutputStream outputStream = httpURLConnection5.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection5.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection5.getInputStream();
                }
                else{
                    inputStream = httpURLConnection5.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            textView_result.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }
    }

}


