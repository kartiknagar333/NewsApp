# NewsApp

## Overview
NewsApp is a mobile application that brings you the latest news articles from various sources around the world. It allows users to explore top stories by selecting their preferred news sources, topics, countries, and languages. This app is powered by the [NewsAPI.org](https://newsapi.org/) API to fetch news data in real time. Users can also view complete articles directly on the news source's website.

## Features

- **News Source Selection**: Choose from a wide range of news sources such as CNN, BBC, etc.
- **Topic-based Filtering**: Filter news based on topics like Business, Sports, Entertainment, etc.
- **Country-based Filtering**: Get news from specific countries.
- **Language-based Filtering**: Choose news articles in your preferred language.
- **Article Navigation**: Swipe between news articles seamlessly.
- **Complete Article Access**: Tap on the article title, text, or image to view the full content on the source's website.
- **Clear Filters**: Reset all filters with a single button.
- **Professional Design**: The app includes a polished launcher icon and intuitive UI for ease of use.

## Technologies Used
- **API**: [NewsAPI.org](https://newsapi.org/) for fetching news sources and articles.
- **Drawer Layout**: For displaying the list of news sources and categories.
- **ViewPager2**: For displaying articles with smooth swipe navigation.
- **Volley**: To handle HTTP requests to the NewsAPI.
- **Dynamic Menus**: Based on the fetched data from the NewsAPI, topics, countries, and languages are displayed dynamically.

## Authentication

The application uses the NewsAPI to fetch news sources and articles. Below are the key API endpoints:

1. **Get News Sources**
   - **API Key**: Your unique API key from [NewsAPI.org](https://newsapi.org/)
     - `X-Api-Key`: (Optional) Filter sources by category (e.g., "business", "sports").
     - `language`: (Optional) Filter sources by language (e.g., "en" for English, "fr" for French).
     - `country`: (Optional) Filter sources by country (e.g., "us" for the United States, "gb" for Great Britain).
Example request using Volley in the app:

```bash
   JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(),
        null, listener, error) {
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "News-App");
        headers.put("X-Api-Key", "your_api_key_here");
        return headers;
    }
};

## Screenshots
