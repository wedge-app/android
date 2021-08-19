package com.example.a2021_wedge.retrofit;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteAllRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private  String URL = "http://mywedge21.dothome.co.kr/del.php";
    private Map<String, String> map;


    public DeleteAllRequest(String storename, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        System.out.println("storename : " + storename );
        map = new HashMap<>();
        map.put("storename", storename);

    }
    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
