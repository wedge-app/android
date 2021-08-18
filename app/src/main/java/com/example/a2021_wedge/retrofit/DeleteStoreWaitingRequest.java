package com.example.a2021_wedge.retrofit;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteStoreWaitingRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private  String URL = "http://mywedge21.dothome.co.kr/deletestorecount.php";
    private Map<String, String> map;


    public DeleteStoreWaitingRequest(String countteam, String count, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        System.out.println("DeleteStoreWaitingRequest : 인원 : " + countteam  +  "대기번호" + count);
        map = new HashMap<>();
        map.put("countteam", countteam);
        map.put("count", count);

        for (Map.Entry<String, String> entrySet : map.entrySet()) {
            System.out.println("Map 출력 : " + entrySet.getKey() + " : " + entrySet.getValue());
        }

    }


    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}