package com.mccarty.cloudcam.model;


import android.arch.lifecycle.LiveData;

import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import java.util.List;

public class MainModel {

    private final CloudCamDatabase database;

    public MainModel(CloudCamDatabase cloudDatabase) {
        this.database = cloudDatabase;
    }

    public LiveData<List<ImageEntity>> getAllImages() {
        return database.imageDao().getAll();
    }
}
