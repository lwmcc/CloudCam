package com.mccarty.cloudcam.ui.camera;

import android.app.Activity;
import android.util.Size;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.utils.AutoFitTextureView;

public interface CameraPresenter extends BasePresenter<CameraView> {
    void openCamera(AutoFitTextureView textureView, Activity activity);

    void takePicture();

    void switchCamera(AutoFitTextureView textureView, Activity activity);

    void startThread();

    Size getPreviewSize();

    void setAspectRatio(Size size, Activity activity);

    void stopBackgroundThread();
}
