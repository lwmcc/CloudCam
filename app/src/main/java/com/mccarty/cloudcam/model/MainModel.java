package com.mccarty.cloudcam.model;

import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;
import com.mccarty.cloudcam.utils.NetworkUtils;

import java.util.List;

public class MainModel {

    private final CloudCamDatabase database;
    private final NetworkUtils networkUtils;
    private final RemoteImageDao remoteImageDao;
    private final ImageDao imageDao;

    public MainModel(CloudCamDatabase cloudDatabase, NetworkUtils networkUtils, RemoteImageDao remoteImageDao,
                     ImageDao imageDao) {
        this.database = cloudDatabase;
        this.networkUtils = networkUtils;
        this.remoteImageDao = remoteImageDao;
        this.imageDao = imageDao;
    }

    /**
     * Get all local or remote images
     *
     * @return List
     */
    public List<ImageEntity> getAllImages() {
        if (!database.imageDao().getAll().isEmpty()) {
            return database.imageDao().getAll();
        } else {
            remoteImageDao.getImages().forEach(imageDao::insertImage);
        }
        return database.imageDao().getAll();
    }

    public boolean hasInternetAccess() {
        return networkUtils.hasNetworkAccess();
    }
}