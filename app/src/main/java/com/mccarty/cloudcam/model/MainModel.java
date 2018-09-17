package com.mccarty.cloudcam.model;


import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;

import java.util.List;

public class MainModel {

    private final CloudCamDatabase database;

    public MainModel(CloudCamDatabase cloudDatabase) {
        this.database = cloudDatabase;
    }

    public List<ImageEntity> getAllImages() {
        return database.imageDao().getAll();
    }
}
