package com.example.a2021_wedge.retrofit;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewMYCountRequest extends StringRequest {

    final static private  String URL = "http://mywedge21.dothome.co.kr/countmyreview.php";
    private Map<String, String> map;

    public ReviewMYCountRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
        map = new HashMap<>();
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}