package com.mccarty.cloudcam.ui.camera;

import android.view.Surface;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.utils.AutoFitTextureView;

public interface CameraPresenter extends BasePresenter<CameraView> {
    void openCamera(int width, int height);
    void configureTransform(int width, int height);
    void takePicture();
    void switchCamera(int width, int height);
    void cameraSetup(AutoFitTextureView textureView, int rotation);
    void startThread();
}
