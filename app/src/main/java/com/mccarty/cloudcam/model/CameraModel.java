package com.mccarty.cloudcam.model;

import android.util.Log;

import com.mccarty.cloudcam.model.api.CameraAPI;

import javax.inject.Inject;

import dagger.Module;

public class CameraModel {

    private static final String TAG = CameraModel.class.getSimpleName();

    private final CameraAPI cameraAPI;

    @Inject
    public CameraModel(CameraAPI api) {
        this.cameraAPI = api;
    }


    public void snapPhoto() {
        // TODO:
        cameraAPI.lockFocus();
    }

}
