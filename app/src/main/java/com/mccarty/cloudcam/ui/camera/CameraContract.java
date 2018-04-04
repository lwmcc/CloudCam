package com.mccarty.cloudcam.ui.camera;

import com.mccarty.cloudcam.ui.base.BasePresenter;
import com.mccarty.cloudcam.ui.base.BaseView;

/**
 * Created by Larry McCarty on 3/29/2018.
 */

public interface CameraContract {

    interface View extends BaseView<Presenter> {
        void switchCameraButtonPressed();

        void takePictureButtonPressed();
    }

    interface Presenter extends BasePresenter {
        int getNumberOfCameras();

        void captureImage();

        void switchCamera();
    }
}
