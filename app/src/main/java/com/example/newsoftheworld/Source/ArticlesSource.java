package com.example.newsoftheworld.Source;


import com.example.newsoftheworld.Model.Articles;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticlesSource {
    private static volatile ArticlesSource instance = null;
    private static final ArrayList<Articles> articlesList = new ArrayList<>();


    private ArticlesSource() {
        final ObjectMapper mapper = new ObjectMapper();
  }

    public static ArticlesSource getInstance() {
        if (instance == null) {
            synchronized (ArticlesSource.class) {
                if (instance == null) {
                    instance = new ArticlesSource();
                }
            }
        }
        return instance;
    }

    public static ArrayList<Articles> getArticles(final JSONArray srcList) {
        articlesList.clear();
        for (int i = 0; i < srcList.length(); i++) {
            try {
                JSONObject src = srcList.getJSONObject(i);
                Articles articles  =  new Articles(src.getString("author"),
                        src.getString("title"),
                        src.getString("description"),
                        src.getString("url"),
                        src.getString("urlToImage"),
                        src.getString("publishedAt"));
                articlesList.add(articles);
            }catch (Exception ignored){
                return null;
            }
        }
        return articlesList;
    }

    public static ArrayList<Articles> getArticles() {
        return articlesList;
    }
}
