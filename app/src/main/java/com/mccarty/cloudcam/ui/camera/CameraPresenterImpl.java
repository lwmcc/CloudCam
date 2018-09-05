package com.mccarty.cloudcam.ui.camera;

import com.mccarty.cloudcam.persistence.CameraModel;
import com.mccarty.cloudcam.utils.AutoFitTextureView;

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

    @Override
    public void switchCamera(int width, int height) {
        model.switchCameraClicked(width, height);
    }

    @Override
    public void cameraSetup(AutoFitTextureView textureView, int rotation) {
        model.cameraSetup(textureView, rotation);
    }

    @Override
    public void startThread() {
        model.startThread();
    }

    /**
     * @param width
     *      * @param height
     */
    @Override
    public void openCamera(int width, int height) {
        view.setTransform(model.openCamera(width, height));
    }

    @Override
    public void configureTransform(int width, int height) {
        model.configureTransform(width, height);
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

    @Override
    public void finishActivity() {
        view.finishActivity();
    }

}
