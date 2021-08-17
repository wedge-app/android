package com.example.a2021_wedge.First;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2021_wedge.GMap.Stores;
import com.example.a2021_wedge.R;
import com.example.a2021_wedge.arrClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main extends AppCompatActivity {

    private TextView textView_pa;
    private String jsonString;
    ArrayList<Stores> StoresArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setVisibility(View.VISIBLE);

        ImageView imageView2 = findViewById(R.id.imageView5);
        imageView2.setVisibility(View.INVISIBLE);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            imageView2.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }, 500);


        handler.postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }, 2000);


        JsonParse jsonParse = new JsonParse();      // AsyncTask 생성
        jsonParse.execute("http://mywedge21.dothome.co.kr/conn.php");


        /*FrameLayout frame_pa = findViewById(R.id.frame_pa);
        frame_pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });*/
    }


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

    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest";

        @Override
        protected String doInBackground(String... strings) {    // execute의 매개변수를 받아와서 사용
            String url = strings[0];
            try {
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, sb.toString().trim());

                return sb.toString().trim();        // 받아온 JSON의 공백을 제거
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                String errorString = e.toString();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String fromdoInBackgroundString) { // doInBackgroundString에서 return한 값을 받음
            super.onPostExecute(fromdoInBackgroundString);

            if (fromdoInBackgroundString == null)
                System.out.println("error");
            else {
                jsonString = fromdoInBackgroundString;
                StoresArrayList = doParse();

                if(StoresArrayList.size() != 0) {
                    Log.d(TAG, StoresArrayList.get(0).getName());
                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        private ArrayList<Stores> doParse() {
            ArrayList<Stores> tmpStoresArray = new ArrayList<Stores>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("Stores");

                for(int i=0;i<jsonArray.length();i++) {
                    Stores tmpStores = new Stores();
                    JSONObject item = jsonArray.getJSONObject(i);
                    tmpStores.setName(item.getString("name"));
                    tmpStores.setH(item.getDouble("h"));
                    tmpStores.setW(item.getDouble("w"));
                    tmpStoresArray.add(tmpStores);
                    arrClass.name_arr[i] = tmpStores.getName();
                    arrClass.location_arr[i][0] = tmpStores.getW();
                    arrClass.location_arr[i][1] = tmpStores.getH();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return tmpStoresArray;
        } // JSON을 가공하여 ArrayList에 넣음


    }


}