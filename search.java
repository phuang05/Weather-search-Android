package com.example.penghuang.hw9_01.Api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by penghuang on 11/25/19.
 */

public class search {
    private static JSONObject result;
    public static void getJsonFile(String jsonUrl, RequestQueue mQueue, final VolleyCallback callback) {
//        String jsonUrl = "http://571hw-hw8.pekp2tptpj.us-west-1.elasticbeanstalk.com/stateList";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        result = response;
                        Log.d("TAG", "json data : " + response);
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
//        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        mQueue.add(jsonRequest);
        return ;
    }

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }


}

