package com.mccarty.cloudcam.ui.camera;

import android.app.Activity;
import android.util.Size;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.utils.AutoFitTextureView;

public interface CameraPresenter extends BasePresenter<CameraView> {
    void openCamera(AutoFitTextureView textureView, int rotation);
    void takePicture();
    void switchCamera(AutoFitTextureView textureView, int rotation);
    void startThread();
    Size getPreviewSize();
    void setAspectRatio(Size size, int rotation);
    void stopBackgroundThread();
    void configureTransform(int width, int height, int rotation);
}
