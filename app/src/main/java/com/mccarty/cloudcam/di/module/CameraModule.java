package com.mccarty.cloudcam.di.module;


import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.mccarty.cloudcam.model.api.CameraAPI;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraModule {

    Application application;

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    CameraManager provideCameraManager(Context context) {
        return (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @Provides
    @Inject
    @Singleton
    CameraAPI provideCamera(CameraManager manager) {
        return new CameraAPI(manager);
    }
}
