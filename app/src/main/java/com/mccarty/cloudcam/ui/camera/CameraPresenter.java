package com.mccarty.cloudcam.ui.camera;

import android.content.Context;

import com.mccarty.cloudcam.ui.base.BasePresenter;

public interface CameraPresenter<V> extends BasePresenter {
    void takePicture();
}
