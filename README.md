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

## Setup
**How to Set Your API KEY**
   - Visit [NewsAPI.org](https://newsapi.org/) to register and get your API key. 
   - Replace "your api key goes here" in the [ArticlesAPI.java](app/src/main/java/com/example/newsoftheworld/API/ArticlesAPI.java) with your actual API key.

```bash
public class ArticlesAPI {
    public static final String API_KEY = "your api key goes here";
```
## Usage
- **News Source Selection:** Open the navigation drawer and select a news source (e.g., CNN, BBC).
- **Apply Filters:** Use the menu options to filter news by topic, country, or language.
- **Article Navigation:** Swipe right to go to the next article, left to go back.
- **Read Full Article:** Tap on the article title or image to open the full article on the source’s website.

## Download APK for Testing
You can download the APK file for testing from the release below:
- [Download v1.0 Testing APK](https://github.com/kartiknagar333/NewsApp/releases/tag/v1.0)
<br>

## Screenshots
- **HomeActivity**



![3](https://github.com/user-attachments/assets/684db1a2-2f52-474e-b399-6d07949e6815)

