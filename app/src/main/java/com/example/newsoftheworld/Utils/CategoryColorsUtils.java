package com.example.newsoftheworld.Utils;

import android.graphics.Color;

import java.util.HashMap;

public class CategoryColorsUtils {
    private static final HashMap<String, Integer> categoryColors = new HashMap<>();

    static {
        categoryColors.put("business", Color.parseColor("#77B254"));
        categoryColors.put("entertainment", Color.parseColor("#872341"));
        categoryColors.put("general", Color.parseColor("#DF9755"));
        categoryColors.put("health", Color.parseColor("#A35C7A"));
        categoryColors.put("science", Color.parseColor("#F93827"));
        categoryColors.put("sports", Color.parseColor("#6A80B9"));
        categoryColors.put("technology", Color.parseColor("#8174A0"));
    }

    public static Integer getColor(String category) {
        return categoryColors.getOrDefault(category.toLowerCase(), Color.WHITE);
    }
}
