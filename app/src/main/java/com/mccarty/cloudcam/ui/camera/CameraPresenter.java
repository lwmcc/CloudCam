package com.mccarty.cloudcam.ui.camera;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.ui.base.BaseView;

public interface CameraPresenter extends BasePresenter<CameraView> {
    void takePicture();
    void switchCamera();
}
