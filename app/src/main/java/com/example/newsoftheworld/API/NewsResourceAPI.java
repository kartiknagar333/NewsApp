package com.example.newsoftheworld.API;

import android.content.Context;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class NewsResourceAPI {
    public static final String API_KEY = "447f216f1d1c4ca0a9db72e971cba234";
    private static final String ARTICLE = "https://newsapi.org/v2/top-headlines";
    private static final String SOURCE = "https://newsapi.org/v2/sources";  // Fixed source for CNN
    private final Context context;

    public NewsResourceAPI(final Context context) {
        this.context = context;
    }

    private static String getRequestUrl() {
        return SOURCE + "?apiKey=" + API_KEY;
    }

    public void fetchData(final DataResponseListener listener) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getRequestUrl(),
                null,
                response -> {
                    try {
                        if(response.has("status") && response.getString("status").equals("ok")){
                            if(response.has("sources") && response.getJSONArray("sources").length() > 0){
                                listener.onSuccess(response.getJSONArray("sources"));
                            }else{
                                listener.onFailure();
                            }
                        }else{
                            listener.onFailure();
                        }
                    } catch (JSONException e) {
                        listener.onFailure();
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    listener.onFailure();
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "News-App");
                headers.put("X-Api-Key", "Bearer " + API_KEY);
                return headers;
            }
        };
        MyVolley.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface DataResponseListener {
        void onSuccess(final JSONArray srcList);
        void onFailure();
    }
}