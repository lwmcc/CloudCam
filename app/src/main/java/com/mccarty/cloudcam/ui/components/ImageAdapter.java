package com.mccarty.cloudcam.ui.components;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mccarty.cloudcam.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<String> data;

    public ImageAdapter(List<String> data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.imageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Picasso.get().load(new File(data.get(position))).
                    //resize(100, 100).
                    //error(R.drawable.ic_report).
                    //placeholder(R.drawable.ic_panorama)
                    //centerCrop()
                    into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}