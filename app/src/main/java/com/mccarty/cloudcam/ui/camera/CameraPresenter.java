package com.mccarty.cloudcam.ui.camera;

import android.content.Context;

import com.mccarty.cloudcam.ui.base.BasePresenter;

/**
 * Created by Larry McCarty on 7/25/2018.
 */

public interface CameraPresenter<V> extends BasePresenter {
    void takePicture();
    void setView(V view);
}
