package com.mccarty.cloudcam.model;


import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;

import java.util.List;

import io.reactivex.Single;

public class MainModel {

    private final CloudCamDatabase database;

    public MainModel(CloudCamDatabase cloudDatabase) {
        this.database = cloudDatabase;
    }

    public List<ImageEntity> getAllImages() {
        return database.imageDao().getAll();
    }

    public void scanForRemoteImages() {
        RemoteImageDao remoteImageDao = new RemoteImageDao();
        remoteImageDao.getImages();
    }
}
