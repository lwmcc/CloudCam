package com.mccarty.cloudcam.ui.components;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mccarty.cloudcam.R;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<String> data;
    private int thumbSize;

    public ImageAdapter(List<String> data, int thumbSize) {
        this.data = data;
        this.thumbSize = thumbSize;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        ViewHolder(ImageView v) {
            super(v);
            image = v.findViewById(R.id.imageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v =(ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.image).load(data.get(position)).
                apply(new RequestOptions().override(thumbSize, thumbSize).
                centerCrop().placeholder(R.drawable.ic_panorama)).
                into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}