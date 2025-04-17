package com.example.newsoftheworld.Utils;


import android.content.res.Resources;

import com.example.newsoftheworld.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountryUtils {
    private static final Map<String, String> countryMap = new HashMap<>();

    public static void initialize(Resources resources) {
        try {
            InputStream inputStream = resources.openRawResource(R.raw.country_codes);
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String jsonText = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            JSONObject jsonObject = new JSONObject(jsonText);
            JSONArray countriesArray = jsonObject.getJSONArray("countries");

            for (int i = 0; i < countriesArray.length(); i++) {
                JSONObject country = countriesArray.getJSONObject(i);
                countryMap.put(country.getString("code"), country.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCountryName(String code) {
        return countryMap.getOrDefault(code, "Unknown Country");
    }

}
