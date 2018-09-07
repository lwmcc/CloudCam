package com.mccarty.cloudcam.ui.camera;

import android.graphics.Matrix;
import android.util.Size;

import com.mccarty.cloudcam.ui.base.BaseView;

public interface CameraView extends BaseView<CameraPresenter> {
    void cameraImageButtonClicked();
    void switchCameraButtonClicked();
    void setTransform(Matrix matrix);
    void setAspectRatioLandscape(Size size);
    void setAspectRatioPortrait(Size size);
    void requestCameraPermission();
    boolean hasCameraPermission();
}
