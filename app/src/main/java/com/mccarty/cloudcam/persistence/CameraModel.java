package com.mccarty.cloudcam.persistence;

import android.graphics.Matrix;
import android.util.Size;
import android.view.Surface;

import com.mccarty.cloudcam.persistence.api.CameraAPI;

import javax.inject.Inject;

public class CameraModel {

    private final CameraAPI cameraAPI;

    @Inject
    public CameraModel(CameraAPI api) {
        this.cameraAPI = api;
    }

    public void snapPhoto() {
        // TODO:
        cameraAPI.lockFocus();
    }

    public Matrix openCamera(int width, int height, int rotation, Surface surface,
                             Size size) {
        return cameraAPI.openCamera(height, width, rotation, surface, size);
    }

   /* public void configureTransform(int width, int height) {

    }*/

    public void cameraSetup(int width, int height, int rotation) {
        //cameraAPI.setCamera(width, height, rotation);
    }

    public void switchCameraClicked(int width, int height, int rotation, Surface surface,
                                    Size size) {
        cameraAPI.switchCamera(width, height, rotation, surface, size);
    }

    public void startThread() {
        cameraAPI.startBackgroundThread();
    }

    public void closeCamera() {
        cameraAPI.closeCamera();
    }

    public Size getPreviewSize() {
        return cameraAPI.getPreviewSize();
    }

    public void stopBackgroundThread() {
        cameraAPI.stopBackgroundThread();
    }

}
