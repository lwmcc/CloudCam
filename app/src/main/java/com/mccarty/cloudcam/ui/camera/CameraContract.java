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

        void checkTextureView();

        void openCamera(int width, int height);

        void setupCamera(int width, int height);

        void lockFocus();

        void showToast(String message);
    }

    interface Presenter extends BasePresenter {
        void captureImage();

        void switchCamera();

        void setUpCameraOutput();

        void openCamera(int width, int height);

        void lockFocus();
    }
}
