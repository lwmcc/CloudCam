package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.mccarty.cloudcam.persistence.CameraModel;
import com.mccarty.cloudcam.persistence.api.CameraAPI;
import com.mccarty.cloudcam.persistence.local.AppPreferences;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;

import java.io.File;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraPresenterModule {

    @Provides
    File provideFile(Application application) {
        return new File(application.getExternalFilesDir(null), "pic.jpg");
    }

    @Provides
    CameraManager provideCameraManager(Application application) {
        return (CameraManager) application.getSystemService(Context.CAMERA_SERVICE);
    }

    @Provides
    CameraAPI provideCamera(CameraManager manager, File file, AppPreferences appPreferences) {
        return new CameraAPI(manager, file, appPreferences);
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