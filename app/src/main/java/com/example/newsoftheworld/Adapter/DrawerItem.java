package com.example.newsoftheworld.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.newsoftheworld.Model.News;
import com.example.newsoftheworld.R;
import com.example.newsoftheworld.Utils.CategoryColorsUtils;

import java.util.List;

public class DrawerItem extends ArrayAdapter<News> {

    private Context context;
    private List<News> items;

    public DrawerItem(Context context, List<News> items) {
        super(context, R.layout.drawer_item, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.drawer_item, parent, false);


        TextView textView = convertView.findViewById(R.id.text_view);
        textView.setText(items.get(position).getName());
        Integer color = CategoryColorsUtils.getColor(items.get(position).getCategory());
        if (color != null) {
            textView.setTextColor(color);
        } else {
            textView.setTextColor(Color.WHITE); // Default color
        }

        return convertView;
    }
}
