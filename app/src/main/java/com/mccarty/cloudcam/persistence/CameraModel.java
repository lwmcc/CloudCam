package com.mccarty.cloudcam.persistence;

import android.graphics.Matrix;

import com.mccarty.cloudcam.persistence.api.CameraAPI;
import com.mccarty.cloudcam.utils.AutoFitTextureView;

import javax.inject.Inject;

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

    public Matrix openCamera(int width, int height) {
        return cameraAPI.openCamera(height, width);
    }

    public void configureTransform(int width, int height) {

    }

    public void cameraSetup(AutoFitTextureView textureView, int rotation) {
        cameraAPI.setCamera(textureView, rotation);
    }

    public void switchCameraClicked(int width, int height) {
        cameraAPI.switchCamera(width, height);
    }

    public void startThread() {
        cameraAPI.startBackgroundThread();
    }


}
