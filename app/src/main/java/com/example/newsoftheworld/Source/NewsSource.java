package com.example.newsoftheworld.Source;


import com.example.newsoftheworld.Model.News;
import com.example.newsoftheworld.Utils.CategoryColorsUtils;
import com.example.newsoftheworld.Utils.CountryUtils;
import com.example.newsoftheworld.Utils.LanguageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NewsSource {
    private static volatile NewsSource instance = null;
    private static final List<News> newsList = new ArrayList<>();
    private static final List<String> categorylist = new ArrayList<>();
    private static final List<String> languagelist = new ArrayList<>();
    private static final List<String> countrylsit = new ArrayList<>();
    private static final Set<String> categorySet = new HashSet<>();
    private static final Set<String> languageSet = new HashSet<>();
    private static final Set<String> countrySet = new HashSet<>();


    private NewsSource() {
        final ObjectMapper mapper = new ObjectMapper();
  }

    public static NewsSource getInstance(final JSONArray srcList) {
        if (instance == null) {
            synchronized (NewsSource.class) {
                if (instance == null) {
                    instance = new NewsSource();
                    setNews(srcList);
                }
            }
        }
        return instance;
    }

    private static void setNews(final JSONArray srcList) {
        for (int i = 0; i < srcList.length(); i++) {
            try {
                JSONObject src = srcList.getJSONObject(i);
                News news  =  new News(src.getString("id"),
                        src.getString("name"),
                        src.getString("description"),
                        src.getString("url"),
                        src.getString("category").substring(0, 1).toUpperCase() + src.getString("category").substring(1),
                        LanguageUtils.getLanguageName(src.getString("language").toUpperCase()),
                        CountryUtils.getCountryName(src.getString("country").toUpperCase()),
                        CategoryColorsUtils.getColor(src.getString("category")));

                newsList.add(news);

                if (src.has("category")) {
                    categorySet.add(news.getCategory());
                }
                if (src.has("language")) {
                    languageSet.add(news.getLanguage());
                }
                if (src.has("country")) {
                    countrySet.add(news.getCountry());
                }
            }catch (Exception ignored){}
        }
        categorylist.addAll(categorySet);
        languagelist.addAll(languageSet);
        countrylsit.addAll(countrySet);

    }

    public List<News> getNewsList() {
        return newsList;
    }

    public List<News> getNewsList(final String topic, final String country, final String language) {
        List<News> filteredNewsList = new ArrayList<>(newsList);

        if (!topic.isEmpty()) {
            filteredNewsList = filteredNewsList.stream()
                    .filter(news -> news.getCategory().equalsIgnoreCase(topic))
                    .collect(Collectors.toList());
        }

        if (!country.isEmpty()) {
            filteredNewsList = filteredNewsList.stream()
                    .filter(news -> news.getCountry().equalsIgnoreCase(country))
                    .collect(Collectors.toList());
        }

        if (!language.isEmpty()) {
            filteredNewsList = filteredNewsList.stream()
                    .filter(news -> news.getLanguage().equalsIgnoreCase(language))
                    .collect(Collectors.toList());
        }

        return filteredNewsList;
    }

    

    public List<String> getCategoryList() {
        List<String> sortedList = new ArrayList<>(categorylist);
        Collections.sort(sortedList);
        return sortedList;
    }
    
    public List<String> getLanguageList() {
        List<String> sortedList = new ArrayList<>(languagelist);
        Collections.sort(sortedList);
        return sortedList;
    }
    
    public List<String> getCountryList() {
        List<String> sortedList = new ArrayList<>(countrylsit);
        Collections.sort(sortedList);
        return sortedList;
    }

}
