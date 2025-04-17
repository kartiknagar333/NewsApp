package com.example.newsoftheworld.Activity;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.newsoftheworld.API.ArticlesAPI;
import com.example.newsoftheworld.API.NewsResourceAPI;
import com.example.newsoftheworld.Adapter.ViewPageItem;
import com.example.newsoftheworld.Model.Articles;
import com.example.newsoftheworld.Source.ArticlesSource;
import com.example.newsoftheworld.Source.NewsSource;
import com.example.newsoftheworld.Adapter.DrawerItem;
import com.example.newsoftheworld.Model.News;
import com.example.newsoftheworld.R;
import com.example.newsoftheworld.Utils.CategoryColorsUtils;
import com.example.newsoftheworld.Utils.CountryUtils;
import com.example.newsoftheworld.Utils.LanguageUtils;
import com.example.newsoftheworld.databinding.ActivityMainBinding;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private NewsSource newsSource;
    private SubMenu topicmenu, languagemenu, countrymenu;
    private DrawerItem arrayAdapter;
    private String topic = "", language = "", country = "", title = "";
    private ArrayList<News> newsList = new ArrayList<>();
    private ArrayList<Articles> articleslist = new ArrayList<>();
    private ConnectivityManager connectivityManager;
    private ViewPageItem viewPageItem = null;
    private int count = 0;
    private boolean isConnected = false;
    private JSONArray articleJsonarray = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarTitle.setText(R.string.app_name);

        setSupportActionBar(binding.toolbar);
        binding.lvnews.setOnItemClickListener(
                (parent, view, position, id) -> {
                    OpenArticles(newsList.get(position).getName());
                }
        );

        toggle = new ActionBarDrawerToggle(this, binding.main, binding.toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.getDrawerArrowDrawable().
                setColor(getResources().getColor(R.color.white, getTheme()));

        Objects.requireNonNull(binding.toolbar.getOverflowIcon()).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        binding.main.addDrawerListener(toggle);
        toggle.syncState();
        LanguageUtils.initialize(getResources());
        CountryUtils.initialize(getResources());
        binding.toolbarTitle.setSelected(true);
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                count = position;
            }
        });
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                runOnUiThread(() -> {
                    isConnected = true;
                    if(newsSource == null){
                        invalidateOptionsMenu();
                    }
                });
            }

            @Override
            public void onLost(Network network) {
                runOnUiThread(() -> {
                    isConnected = false;
                    Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void OpenArticles(final String name) {
        if(isConnected){
            final ArticlesAPI articlesAPI = new ArticlesAPI(this);
            articlesAPI.fetchData(name,new ArticlesAPI.DataResponseListener(){
                @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                @Override
                public void onSuccess(JSONArray srcList) {
                    articleJsonarray = srcList;
                    binding.toolbarTitle.setText(name + " (" + srcList.length() + ")");
                    title = name + " (" + srcList.length() + ")";
                    articleslist.clear();
                    articleslist.addAll(ArticlesSource.getArticles(srcList));
                    if(viewPageItem == null){
                        viewPageItem  = new ViewPageItem(MainActivity.this,articleslist);
                        binding.viewpager.setAdapter(viewPageItem);
                        binding.viewpager.setCurrentItem(0);
                    }else {
                        viewPageItem.notifyDataSetChanged();
                        binding.viewpager.setCurrentItem(0);

                    }
                    binding.main.closeDrawer(binding.leftSideLayout);
                }
                @Override
                public void onFailure(final String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            binding.main.closeDrawer(binding.leftSideLayout);
            Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(isConnected){
            NewsResourceAPI newsResourceAPI = new NewsResourceAPI(this);
            newsResourceAPI.fetchData(new NewsResourceAPI.DataResponseListener() {
                @Override
                public void onSuccess(JSONArray srcList) {
                    updateUI(menu, srcList);
                }
                @Override
                public void onFailure() {
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    getMenuInflater().inflate(R.menu.menu, menu);
                    topicmenu = menu.addSubMenu(R.string.topics);
                    languagemenu = menu.addSubMenu(R.string.languages);
                    countrymenu = menu.addSubMenu(R.string.countries);
                    menu.add("Clear All");
                }
            });
        }else{
            Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @SuppressLint("ResourceAsColor")
    private void updateUI(final Menu menu, final JSONArray srcList) {
        newsSource = NewsSource.getInstance(srcList);
        getMenuInflater().inflate(R.menu.menu, menu);
        topicmenu = menu.addSubMenu(0,100,0, R.string.topics);
        int id = 101;
        topicmenu.add(0,id , 0,R.string.all);
        for (String topic : newsSource.getCategoryList()) {
            id++;
            SpannableString s = new SpannableString(topic);
            s.setSpan(new ForegroundColorSpan(CategoryColorsUtils.getColor(topic)), 0, s.length(), 0);
            topicmenu.add(0,id , 0,s);
        }
        id = 201;
        languagemenu = menu.addSubMenu(0,200,0, R.string.languages);
        languagemenu.add(0,id , 0,R.string.all);
        for (String language : newsSource.getLanguageList()) {
            id++;
            languagemenu.add(0,id , 0,language);
        }
        id = 301;
        countrymenu = menu.addSubMenu(0,300,0,R.string.countries);
        countrymenu.add(0,id , 0,R.string.all);
        for (String country : newsSource.getCountryList()) {
            id++;
            countrymenu.add(0,id , 0,country);
        }
        menu.add(0,400,0,"Clear All");
        binding.tvmsg.setVisibility(View.GONE);
        newsList = (ArrayList<News>) newsSource.getNewsList(topic, country, language);
        arrayAdapter = new DrawerItem(this, newsList);
        binding.lvnews.setAdapter(arrayAdapter);
        binding.toolbarTitle.setText(!title.isEmpty() ? title : getString(R.string.app_name) + " (" + newsList.size() + ")");

    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() > 100 && item.getItemId() < 200){
            if(Objects.requireNonNull(item.getTitle()).toString().equals("All")){
                topic = "";
            }else{
                topic = item.getTitle().toString();
            }
        }else if(item.getItemId() > 200 && item.getItemId() < 300){
            if(Objects.requireNonNull(item.getTitle()).toString().equals("All")){
                language = "";
            }else{
                language = item.getTitle().toString();
            }
        }else if(item.getItemId() > 300 && item.getItemId() < 400){
            if(Objects.requireNonNull(item.getTitle()).toString().equals("All")){
                country = "";
            }else{
                country = item.getTitle().toString();
            }
        }else{
            if(item.getTitle().toString().equals("Clear All")){
                topic = "";
                language = "";
                country = "";
                articleJsonarray = null;
                articleslist.clear();
                if(viewPageItem != null){
                    viewPageItem.notifyDataSetChanged();
                }

            }
        }

        newsList.clear();
        if(newsSource != null) {
            newsList.addAll(newsSource.getNewsList(topic, country, language));
            arrayAdapter.notifyDataSetChanged();
            binding.tvmsg.setVisibility(View.GONE);
        }

        if(newsSource.getNewsList(topic, country, language).isEmpty()){
            binding.tvmsg.setVisibility(View.VISIBLE);
        }else{
            binding.tvmsg.setVisibility(View.GONE);
        }

        if(articleslist.isEmpty()){
            binding.toolbarTitle.setText(getString(R.string.app_name) + " (" + newsList.size() + ")");
            title = getString(R.string.app_name) + " (" + newsList.size() + ")";

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if( menu.findItem(100)!=null){
            if(!topic.isEmpty()){
                menu.findItem(100).setTitle("Topics ("+topic + ")");
            }else {
                menu.findItem(100).setTitle("Topics (All)");
            }
            if( menu.findItem(200)!=null){
                if(!language.isEmpty()){
                    menu.findItem(200).setTitle("Languages ("+language + ")");
                }else {
                    menu.findItem(200).setTitle("Languages (All)");
                }
            }
            if( menu.findItem(300)!=null){
                if(!country.isEmpty()){
                    menu.findItem(300).setTitle("Countries ("+country + ")");
                }else {
                    menu.findItem(300).setTitle("Countries (All)");
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (binding.main.isDrawerOpen(GravityCompat.START)) {
            binding.main.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@androidx.annotation.NonNull Bundle outState) {
        outState.putString("articlelist", articleJsonarray != null ? articleJsonarray.toString() : "");
        outState.putString("title", title);
        outState.putString("topic", topic);
        outState.putString("language", language);
        outState.putString("country", country);
        outState.putInt("count", count);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@androidx.annotation.NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        title = savedInstanceState.getString("title");
        binding.toolbarTitle.setText(savedInstanceState.getString("title"));
        topic = savedInstanceState.getString("topic");
        language = savedInstanceState.getString("language");
        country = savedInstanceState.getString("country");
        count = savedInstanceState.getInt("count");
        String jsonArrayString = savedInstanceState.getString("articlelist");
        if (jsonArrayString != null) {
            binding.tvmsg.setVisibility(View.GONE);
            try {
                articleJsonarray = new JSONArray(jsonArrayString);
                articleslist.clear();
                articleslist.addAll(ArticlesSource.getArticles(articleJsonarray));
                viewPageItem = new ViewPageItem(MainActivity.this,articleslist);
                binding.viewpager.setAdapter(viewPageItem);
                binding.viewpager.setCurrentItem(count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            binding.tvmsg.setVisibility(View.VISIBLE);
        }
    }




}