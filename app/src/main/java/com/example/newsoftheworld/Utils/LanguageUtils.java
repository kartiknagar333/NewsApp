package com.example.newsoftheworld.Utils;

import android.content.res.Resources;

import com.example.newsoftheworld.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LanguageUtils {

    private static Map<String, String> languageMap = new HashMap<>();

    public static void initialize(Resources resources) {
        try {
            InputStream inputStream = resources.openRawResource(R.raw.language_codes);
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String jsonText = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            JSONObject jsonObject = new JSONObject(jsonText);
            JSONArray languagesArray = jsonObject.getJSONArray("languages");

            for (int i = 0; i < languagesArray.length(); i++) {
                JSONObject language = languagesArray.getJSONObject(i);
                languageMap.put(language.getString("code"), language.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLanguageName(String code) {
        return languageMap.getOrDefault(code, "Unknown Language");
    }
}
