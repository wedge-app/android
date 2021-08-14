package com.example.a2021_wedge.retrofit;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class storesRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private  String URL = "http://mywedge21.dothome.co.kr/stores.php";
    private Map<String, String> map;


    public storesRequest(String ID, String PW, String userName, String Tel, String addr,  String name, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("ID", ID);
        map.put("PW", PW);
        map.put("userName", userName);
        map.put("Tel", Tel);
        map.put("addr", addr);
        map.put("name", name);
    }


    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
