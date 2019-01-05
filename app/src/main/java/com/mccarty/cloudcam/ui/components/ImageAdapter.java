package com.mccarty.cloudcam.ui.components;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<ImageEntity> data;

    public ImageAdapter(List<ImageEntity> data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        ViewHolder(ImageView v) {
            super(v);
            image = v.findViewById(R.id.imageView);
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView v =(ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.image).load(data.get(position).getImagePath()).
                into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}