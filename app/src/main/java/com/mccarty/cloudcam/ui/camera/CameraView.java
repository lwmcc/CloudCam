package com.mccarty.cloudcam.ui.camera;

import android.graphics.Matrix;
import android.util.Size;
import android.view.Surface;

import com.mccarty.cloudcam.ui.base.BaseView;

/**
 * Created by Larry McCarty on 7/21/2018.
 */

public interface CameraView extends BaseView<CameraPresenter> {
    void cameraImageButtonClicked();
    void switchCameraButtonClicked();
    void setTransform(Matrix matrix);
    void setAspectRatio(Size size);
    void requestCameraPermission();
    boolean hasCameraPermission();
    Surface getOutputSurface(Size previewSize);
}
