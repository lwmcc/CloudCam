package com.mccarty.cloudcam.ui.camera;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

import com.mccarty.cloudcam.apis.CameraAPI;
import com.mccarty.cloudcam.utils.AutoFitTextureView;
import com.mccarty.cloudcam.utils.NetworkUtils;

import javax.inject.Inject;

public class CameraPresenterImpl implements CameraPresenter {

  private CameraView view;

  private final CameraAPI cameraAPI;

  private final NetworkUtils networkUtils;

  @Inject
  public CameraPresenterImpl(CameraAPI cameraAPI, NetworkUtils networkUtils) {
    this.cameraAPI = cameraAPI;
    this.networkUtils = networkUtils;
  }

  @Override
  public void startThread() {
    cameraAPI.startBackgroundThread();
  }

  @Override
  public Size getPreviewSize() {
    return cameraAPI.getPreviewSize();
  }

  @Override
  public void setAspectRatio(Size size, int rotation) {
    if (rotation ==
            Configuration.ORIENTATION_LANDSCAPE) {
      view.setAspectRatioLandscape(size);
    } else {
      view.setAspectRatioPortrait(size);
    }
  }

  @Override
  public void openCamera(AutoFitTextureView textureView, int rotation) {
    Size size = getPreviewSize();
    view.setTransform(cameraAPI.openCamera(textureView.getWidth(), textureView.getHeight(),
            rotation, new Surface(textureView.getSurfaceTexture()), size));
    setAspectRatio(size, rotation);
  }

  @Override
  public void switchCamera(AutoFitTextureView textureView, int rotation) {

    cameraAPI.closeCamera();
    Size size = getPreviewSize();
    cameraAPI.switchCamera(textureView.getWidth(), textureView.getHeight(),
            rotation, new Surface(textureView.getSurfaceTexture()), size);
    setAspectRatio(size, rotation);
  }

  public void takePicture() {
    cameraAPI.lockFocus();
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
    cameraAPI.stopBackgroundThread();
  }

  @Override
  public void configureTransform(int width, int height, int rotation) {
    cameraAPI.configureTransform(width, height, getPreviewSize(), rotation);
  }

}