package com.mccarty.cloudcam.ui.camera;

import android.util.Log;

import com.mccarty.cloudcam.model.CameraModel;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

public class CameraPresenterImpl implements CameraPresenter {

    private CameraView view;
    private final CameraModel model;

    private static final String TAG = CameraPresenterImpl.class.getSimpleName();

    @Inject
    public CameraPresenterImpl(CameraModel model) {
        this.model = model;
    }

    public void switchCamera() {
        // TODO:
    }


    public void takePicture() {
        model.snapPhoto();
    }

    @Override
    public void takeView(CameraView view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        // TODO:
    }

}
