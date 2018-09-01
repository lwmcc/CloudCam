package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.mccarty.cloudcam.model.CameraModel;
import com.mccarty.cloudcam.model.api.CameraAPI;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;
import com.mccarty.cloudcam.ui.main.MainPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraPresenterModule {

    @Provides
    CameraManager provideCameraManager(Application application) {
        return (CameraManager) application.getSystemService(Context.CAMERA_SERVICE);
    }

    @Provides
    CameraAPI provideCamera(CameraManager manager) {
        return new CameraAPI(manager);
    }

    @Provides
    CameraModel provideCameraModel(CameraAPI api) {
        return new CameraModel(api);
    }

    @Provides
    CameraPresenterImpl provideCameraPresenter(CameraModel cameraModel) {
        return new CameraPresenterImpl(cameraModel);
    }
}