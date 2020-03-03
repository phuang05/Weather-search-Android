package com.example.penghuang.hw9_01.Api;

/**
 * Created by penghuang on 11/27/19.
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCall {
    private static ApiCall mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    final static String awsUrl = "http://571hw-hw8.pekp2tptpj.us-west-1.elasticbeanstalk.com";
    final static String ipUrl = "http://ip-api.com/json";
    public ApiCall(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }
    public static synchronized ApiCall getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApiCall(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    public static void makeString(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "https://itunes.apple.com/search?term=" + query
                + "&country=US";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public static void makeJson(Context ctx, String query, Response.Listener<JSONObject>
            listener, Response.ErrorListener errorListener) {
        String url = query;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonRequest);
    }

    public static void autoComplete (Context ctx, String query, Response.Listener<JSONObject>
            listener, Response.ErrorListener errorListener) {
        String url =  awsUrl+"/stateAuto?curwords=" + query;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonRequest);
        Log.d("tag","doAutoCompleteQuery");

    }


    public static void getGeo (Context ctx, String query, Response.Listener<JSONArray>
            listener, Response.ErrorListener errorListener) {
        String url =  awsUrl+"/geocode?city=" + query;
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonRequest);
        Log.d("tag","doAutoCompleteQuery");

    }

    public static void getCurrentlocation (Context ctx, String query, Response.Listener<JSONObject>
            listener, Response.ErrorListener errorListener) {
        String url =  ipUrl;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonRequest);
        Log.d("tag","get current location");


    }

    public static void getCityImageUrl (Context ctx, String query, Response.Listener<JSONObject>
            listener, Response.ErrorListener errorListener) {
        String url =  awsUrl+"/search?search="+query;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonRequest);


    }


}