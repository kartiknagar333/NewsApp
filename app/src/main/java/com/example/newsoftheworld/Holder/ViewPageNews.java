package com.example.newsoftheworld.Holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsoftheworld.databinding.ViewpageItemBinding;

public class ViewPageNews extends RecyclerView.ViewHolder {

    public ViewpageItemBinding binding;

    public ViewPageNews(@NonNull final ViewpageItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }
}
