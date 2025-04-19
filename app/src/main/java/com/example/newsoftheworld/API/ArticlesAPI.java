package com.example.newsoftheworld.API;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ArticlesAPI {
    public static final String API_KEY = "your api key goes here";
    private static final String ARTICLE = "https://newsapi.org/v2/top-headlines";
    private final Context context;

    public ArticlesAPI(final Context context) {
        this.context = context;
    }
    private static String getRequestUrl(final String name) {
        return ARTICLE + "?sources=" + name + "&apiKey=" + API_KEY;
    }

    public void fetchData(final String name,final DataResponseListener listener) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getRequestUrl(name),
                null,
                response -> {
                    try {
                        if(response.has("status") && response.getString("status").equals("ok")){
                            if(response.has("articles") && response.getJSONArray("articles").length() > 0){
                                listener.onSuccess(response.getJSONArray("articles"));
                            }else{
                                listener.onFailure("No articles found.");
                            }
                        }else{
                            listener.onFailure("Failed to fetch data");
                        }
                    } catch (JSONException e) {
                        listener.onFailure(e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    listener.onFailure("Failed to fetch data");
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
        void onFailure(final String message);
    }
}
