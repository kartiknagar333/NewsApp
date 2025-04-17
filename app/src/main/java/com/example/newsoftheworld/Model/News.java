package com.example.newsoftheworld.Model;

import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class News implements Serializable {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("url")
    private final String url;

    @JsonProperty("category")
    private final String category;

    @JsonProperty("language")
    private String language;

    @JsonProperty("country")
    private String country;

    private int color;

    public News(String id, String name, String description, String url, String category, String language, String country,int color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public void setLanguage(String lan) {
        language = lan;
    }

    public void setCountry(String con) {
        country = con;
    }

}
