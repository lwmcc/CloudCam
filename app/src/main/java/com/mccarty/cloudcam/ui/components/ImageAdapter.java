package com.mccarty.cloudcam.ui.components;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.ui.imageview.MainOnClick;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<ImageEntity> data;

    private MainOnClick clickListener;

    public ImageAdapter(List<ImageEntity> data, MainOnClick listener) {
        this.data = data;
        this.clickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;

        ViewHolder(ImageView v) {
            super(v);
            v.setOnClickListener(this);
            image = v.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false);
        return new ViewHolder(v);
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