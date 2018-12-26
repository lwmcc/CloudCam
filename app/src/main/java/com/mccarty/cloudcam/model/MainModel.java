package com.mccarty.cloudcam.model;


import android.app.Application;
import android.content.Context;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.mccarty.cloudcam.di.module.AWSModule;
import com.mccarty.cloudcam.di.module.NetworkModule;
import com.mccarty.cloudcam.persistence.local.CloudCamDatabase;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;
import com.mccarty.cloudcam.utils.NetworkUtils;

import java.util.List;

import io.reactivex.Single;

public class MainModel {

    private final CloudCamDatabase database;
    private final NetworkUtils networkUtils;
    private final RemoteImageDao remoteImageDao;

    public MainModel(CloudCamDatabase cloudDatabase, NetworkUtils networkUtils, RemoteImageDao remoteImageDao) {
        this.database = cloudDatabase;
        this.networkUtils = networkUtils;
        this.remoteImageDao = remoteImageDao;
    }

    /**
     * Get all local images
     * @return List
     */
    public List<ImageEntity> getAllImages() {
        return database.imageDao().getAll();
        // TODO:
        // if empty query remote db for iamges
        // download remote images
        // insert them into local db
        // show them on UI
    }

    public void scanForRemoteImages() {
       remoteImageDao.getImages();
    }

    public boolean canAccessAWS() {
        return  networkUtils.hasNetworkAccess() && IdentityManager.getDefaultIdentityManager().isUserSignedIn();
    }
}
