package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.mccarty.cloudcam.persistence.api.CameraAPI;
import com.mccarty.cloudcam.persistence.local.AppPreferences;
import com.mccarty.cloudcam.ui.camera.CameraPresenterImpl;
import com.mccarty.cloudcam.utils.NetworkUtils;
import com.mccarty.cloudcam.utils.UIUtils;

import java.io.File;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraPresenterModule {

    @Provides
    File provideFile(Application application) {
        return new File(application.getExternalFilesDir(null), UIUtils.prependToImage());
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
    NetworkUtils provideNetworkUtils(Application application) {
        return new NetworkUtils(application);
    }

    @Provides
    CameraPresenterImpl provideCameraPresenter(CameraAPI cameraAPI, NetworkUtils networkUtils) {
        return new CameraPresenterImpl(cameraAPI, networkUtils);
    }
}