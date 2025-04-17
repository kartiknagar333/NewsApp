package com.example.newsoftheworld.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Articles implements Serializable {

    @JsonProperty("author")
    private final String author;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("url")
    private final String url;

    @JsonProperty("urlToImage")
    private final String urlToImage;

    @JsonProperty("publishedAt")
    private final String publishedAt;

    public Articles(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
