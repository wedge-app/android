package com.example.a2021_wedge.retrofit;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PersonalRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private  String URL = "http://mywedge21.dothome.co.kr/fixmyinfo.php";
    private Map<String, String> map;


    public PersonalRequest(String userID, String userPassword, String userName, String userTel, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userTel", userTel);
    }


    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}