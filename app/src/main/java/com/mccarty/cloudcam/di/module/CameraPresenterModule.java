package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.mccarty.cloudcam.apis.CameraAPI;
import com.mccarty.cloudcam.persistence.local.AppPreferences;
import com.mccarty.cloudcam.persistence.local.Image.ImageDao;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;
import com.mccarty.cloudcam.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DatabaseModule.class, NetworkModule.class})
public class CameraPresenterModule {

    @Provides
    static CameraManager provideCameraManager(Application application) {
        return (CameraManager) application.getSystemService(Context.CAMERA_SERVICE);
    }

    @Provides
    static CameraAPI provideCamera(Application application, CameraManager manager, AppPreferences appPreferences,
                                   ImageDao imageDao) {
        return new CameraAPI(application, manager, appPreferences, imageDao);
    }

    @Provides
    static CameraPresenterImpl provideCameraPresenter(CameraAPI cameraAPI, NetworkUtils networkUtils) {
        return new CameraPresenterImpl(cameraAPI, networkUtils);
    }
}