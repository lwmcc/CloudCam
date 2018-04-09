package com.mccarty.cloudcam.ui.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import com.mccarty.cloudcam.utils.CameraAPI;
import com.mccarty.cloudcam.utils.ConfigurationTransform;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Larry McCarty on 3/30/2018.
 */

public class CameraPresenter implements CameraContract.Presenter {

    private static final String TAG = CameraPresenter.class.getSimpleName();

    private final CameraContract.View cameraView;

    @Inject
    ConfigurationTransform configureTransform;

    @Inject
    public CameraPresenter(@NonNull CameraContract.View camView,
                           @NonNull CameraAPI cameraAPI, @NonNull Activity act) {
        cameraView = checkNotNull(camView, "CameraView Cannot Be Null");
        //camera = checkNotNull(cameraAPI, "Camera API Cannot Be Null");
        cameraView.setPresenter(this);
    }

    @Override
    public void setUpCameraOutput() {

    }

    @Override
    public void openCamera(int width, int height) {

    }

    @Override
    public void captureImage() {
        Log.d(TAG, "CAPTURE IMAGE");
    }

    @Override
    public void switchCamera() {
        Log.d(TAG, "SWITCH CAMERA");
    }

    @Override
    public void start() {
        Log.d(TAG, "CAMERA START");
        setupCamera();
    }

    private void setupCamera() {
        cameraView.checkTextureView();
    }

    @Override
    public void setTextureTransform(int width, int height, Activity act, Size previewSize) {
        Matrix matrix = new ConfigurationTransform(width, height, act, previewSize).configureTransform(width,
                height, act, previewSize);
        cameraView.setTextureViewTransform(matrix);
    }

    @Override
    public void lockFocus() {

    }
}
