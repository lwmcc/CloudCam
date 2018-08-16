package com.mccarty.cloudcam.ui.camera;

import android.content.Context;
import android.util.Log;

import com.mccarty.cloudcam.model.CameraModel;
import com.mccarty.cloudcam.ui.base.BaseView;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

public class CameraPresenterImpl<V extends CameraView> implements CameraPresenter<V> {

    private V view;
    private final CameraModel model;

    private static final String TAG = CameraPresenterImpl.class.getSimpleName();

    public CameraPresenterImpl(V view, CameraModel model) {
        this.view = view;
        this.model = model;
    }

    public V getView() {
        return view;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void takePicture() {
        // TODO:
        model.snapPhoto();
    }









/*    private final CameraContract.View cameraView;

    @Inject
    public CameraPresenterImpl(@NonNull CameraContract.View camView,
                           @NonNull CameraAPI cameraAPI,
                           //@NonNull ConfigurationTransform config,
                           @NonNull Activity act) {
        cameraView = checkNotNull(camView, "CameraView Cannot Be Null");
        //configureTransform = checkNotNull(config, "ConfigurationTransform Cannot Be Null");
        //camera = checkNotNull(cameraAPI, "Camera API Cannot Be Null");
        //cameraView.setPresenter(this);
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
        cameraView.setTextureViewTransform(new ConfigurationTransform(width, height, act, previewSize).configureTransform());
    }

    @Override
    public void lockFocus() {

    }

    public void openCamera(final int width, final int height, Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
           *//* if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                new CameraFragment.ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }*//*
            return;
        }
    }

    private boolean requestCameraPermission(Fragment fragmet) {
        return fragmet.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
    }

    public boolean checkCameraPermissions(Activity activity) {
      return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
              != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean activityAndCameraDeviceAreNull(Activity activity, CameraDevice cam) {
        if (null == activity || null == cam) {
            return true;
        } else {
            return false;
        }
    }*/

}
