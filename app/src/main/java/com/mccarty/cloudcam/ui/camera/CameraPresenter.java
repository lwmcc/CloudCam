package com.mccarty.cloudcam.ui.camera;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mccarty.cloudcam.utils.CameraAPI;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Larry McCarty on 3/30/2018.
 */

public class CameraPresenter implements CameraContract.Presenter {

    private static final String TAG = CameraPresenter.class.getSimpleName();

    private final CameraContract.View cameraView;
    private final CameraAPI camera;

    @Inject
    public CameraPresenter(@NonNull CameraAPI cameraAPI, @NonNull CameraContract.View camView) {
        camera = checkNotNull(cameraAPI, "Camera Cannot Be NUll");
        cameraView = checkNotNull(camView, "CameraView Cannot Be NUll");
        cameraView.setPresenter(this);
    }

    @Override
    public int getNumberOfCameras() {
        int numCameras = 0;

        Log.d(TAG, "ENTER getNumberOfCameras()");

        //camera.setUpCameraOutput();
        return numCameras;
    }

    @Override
    public void captureImage() {
        Log.d(TAG,"CAPTURE IMAGE");
    }

    @Override
    public void switchCamera() {
        Log.d(TAG,"SWITCH CAMERA");
    }

    @Override
    public void start() {
        Log.d(TAG,"CAMERA START");
    }
}
