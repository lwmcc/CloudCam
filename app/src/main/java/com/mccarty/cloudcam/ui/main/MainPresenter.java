package com.mccarty.cloudcam.ui.main;

import com.mccarty.cloudcam.ui.base.BasePresenter;

/**
 * Created by Larry McCarty on 7/25/2018.
 */

public interface MainPresenter extends BasePresenter {
    boolean hasInternetConnection();
    int getNumbereOfCameras();
    void captureImage();
}
