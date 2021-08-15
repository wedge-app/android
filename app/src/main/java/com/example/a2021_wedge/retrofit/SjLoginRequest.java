package com.example.a2021_wedge.retrofit;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SjLoginRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private  String URL = "http://mywedge21.dothome.co.kr/sajang_login.php";
    private Map<String, String> map;

    public SjLoginRequest(String ID, String PW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("ID", ID);
        map.put("PW", PW);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}