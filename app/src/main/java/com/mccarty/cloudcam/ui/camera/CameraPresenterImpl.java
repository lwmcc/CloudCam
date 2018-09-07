package com.mccarty.cloudcam.ui.camera;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.Size;
import android.view.Surface;

import com.mccarty.cloudcam.persistence.CameraModel;
import com.mccarty.cloudcam.utils.AutoFitTextureView;
import com.mccarty.cloudcam.utils.NetworkUtils;

import javax.inject.Inject;

public class CameraPresenterImpl implements CameraPresenter {

    private CameraView view;
    // TODO: remove this use CameraAPI
    private final CameraModel model;

    private final NetworkUtils networkUtils;

    @Inject
    public CameraPresenterImpl(CameraModel model, NetworkUtils networkUtils) {
        this.model = model;
        this.networkUtils = networkUtils;
    }

    @Override
    public void startThread() {
        model.startThread();
    }

    @Override
    public Size getPreviewSize() {
        return model.getPreviewSize();
    }

    @Override
    public void setAspectRatio(Size size, Activity activity) {
        if (activity.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            view.setAspectRatioLandscape(size);
        } else {
            view.setAspectRatioPortrait(size);
        }
    }

    @Override
    public void openCamera(AutoFitTextureView textureView, Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Size size = getPreviewSize();
        view.setTransform(model.openCamera(textureView.getWidth(), textureView.getHeight(),
                rotation, new Surface(textureView.getSurfaceTexture()), size));
        setAspectRatio(size, activity);
    }

    @Override
    public void switchCamera(AutoFitTextureView textureView, Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        model.closeCamera();
        Size size = getPreviewSize();
        model.switchCameraClicked(textureView.getWidth(), textureView.getHeight(),
                rotation, new Surface(textureView.getSurfaceTexture()), size);
        setAspectRatio(size, activity);
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
        view = null;
    }

    @Override
    public boolean hasInternetAccess() {
        return networkUtils.hasNetworkAccess();
    }

    @Override
    public void stopBackgroundThread() {
        model.stopBackgroundThread();
    }

}
