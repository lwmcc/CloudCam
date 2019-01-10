package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.mccarty.cloudcam.apis.CameraAPI;
import com.mccarty.cloudcam.persistence.local.AppPreferences;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.persistence.remote.image.RemoteImageDao;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;
import com.mccarty.cloudcam.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DatabaseModule.class, NetworkModule.class, AWSModule.class})
public class CameraPresenterModule {

  @Provides
  static CameraManager provideCameraManager(Application application) {
    return (CameraManager) application.getSystemService(Context.CAMERA_SERVICE);
  }

  @Provides
  static CameraAPI provideCamera(Application application, CameraManager manager, AppPreferences appPreferences,
                                 ImageDao imageDao, NetworkUtils networkUtils, TransferUtility transferUtility, CognitoUserPool cognitoUserPool,
                                 RemoteImageDao remoteImageDao) {
    return new CameraAPI(application, manager, appPreferences, imageDao, remoteImageDao, networkUtils, transferUtility, cognitoUserPool);
  }

  @Provides
  static CameraPresenterImpl provideCameraPresenter(CameraAPI cameraAPI, NetworkUtils networkUtils) {
    return new CameraPresenterImpl(cameraAPI, networkUtils);
  }
}