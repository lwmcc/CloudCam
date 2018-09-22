package com.mccarty.cloudcam.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mccarty.cloudcam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

    public class ImageAdapter extends RecyclerView.Adapter<com.mccarty.cloudcam.ui.components.adapters.ImageAdapter.ViewHolder> {
        private List<String> data;

        public ImageAdapter(List<String> data) {
            this.data = data;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public ViewHolder(ImageView v) {
                super(v);
                imageView = v;
            }
        }

        @Override
        public com.mccarty.cloudcam.ui.components.adapters.ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item_layout, parent, false);
            com.mccarty.cloudcam.ui.components.adapters.ImageAdapter.ViewHolder vh = new com.mccarty.cloudcam.ui.components.adapters.ImageAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(com.mccarty.cloudcam.ui.components.adapters.ImageAdapter.ViewHolder holder, int position) {
            Picasso.get().load(data.get(position)).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }