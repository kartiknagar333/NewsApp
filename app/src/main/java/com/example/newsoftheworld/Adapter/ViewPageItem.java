package com.example.newsoftheworld.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsoftheworld.Holder.ViewPageNews;
import com.example.newsoftheworld.Model.Articles;
import com.example.newsoftheworld.R;
import com.example.newsoftheworld.databinding.ViewpageItemBinding;
import com.squareup.picasso.Picasso;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ViewPageItem extends RecyclerView.Adapter<ViewPageNews> {

    private Context context = null;
    private ArrayList<Articles> articlesList = new ArrayList<>();
    private DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy  HH:mm");


    public ViewPageItem(Context context, ArrayList<Articles> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public ViewPageNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewpageItemBinding binding = ViewpageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewPageNews(binding);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewPageNews holder, int position) {
        Articles articles = articlesList.get(position);
        holder.binding.title.setText(articles.getTitle().isEmpty() ? "" : articles.getTitle());
        holder.binding.description.setText(articles.getDescription().isEmpty() ? "" : articles.getDescription());
        holder.binding.author.setText(articles.getAuthor().isEmpty() ? "" : articles.getAuthor());
        holder.binding.publishedAt.setText(articles.getPublishedAt().isEmpty() ? "" :
                OffsetDateTime.parse(articles.getPublishedAt(), inputFormatter).format(outputFormatter));
        if (articles.getUrlToImage() != null && !articles.getUrlToImage().isEmpty()) {
            Picasso.get()
                    .load(articles.getUrlToImage())
                    .error(R.drawable.noimage)
                    .placeholder(R.drawable.noimage)
                    .into(holder.binding.urlToImage);
        }
        holder.binding.count.setText(String.valueOf(position+1) + " of " + articlesList.size());
        holder.binding.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrlInBrowser(articles.getUrl());
            }
        });
        holder.binding.urlToImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrlInBrowser(articles.getUrl());
            }
        });
        holder.binding.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrlInBrowser(articles.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    private void openUrlInBrowser(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        Intent chooser = Intent.createChooser(intent, "Open with");
        context.startActivity(chooser);
    }


}
