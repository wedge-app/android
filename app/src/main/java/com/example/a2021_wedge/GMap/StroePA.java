package com.example.a2021_wedge.GMap;
/*
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2021_wedge.MyPageFrag.Potato.GrowingPotatoActivity;
import com.example.a2021_wedge.MyPageFrag.Potato.TotalPotato;
import com.example.a2021_wedge.arrClass;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.bottomBar.MainActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StroePA extends AppCompatActivity {

    private TextView textView_pa;
    private String jsonString;
    ArrayList<Stores> StoresArrayList;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroe_pa);

        textView_pa = (TextView) findViewById(R.id.textView_pa);

        JsonParse jsonParse = new JsonParse();      // AsyncTask 생성
        jsonParse.execute("http://mywedge21.dothome.co.kr/conn.php");

        b = findViewById(R.id.button_pa);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StroePA.this, MainActivity.class);
                startActivity(i);
            }
        });

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
                textView_pa.setText("error");
            else {
                jsonString = fromdoInBackgroundString;
                StoresArrayList = doParse();

                if(StoresArrayList.size() != 0) {
                    Log.d(TAG, StoresArrayList.get(0).getName());
                    textView_pa.setText(StoresArrayList.get(0).getName());
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
                    arrClass.location_arr[i][0] = tmpStores.getW();
                    arrClass.location_arr[i][1] = tmpStores.getH();
                }
                for(int i = 0; i<arrClass.location_arr.length; i++){
                    System.out.print(arrClass.location_arr[i][0] + ":" + arrClass.location_arr[i][1]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return tmpStoresArray;
        } // JSON을 가공하여 ArrayList에 넣음


    }
}*/