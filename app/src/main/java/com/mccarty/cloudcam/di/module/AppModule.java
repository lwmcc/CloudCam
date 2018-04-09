package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mccarty.cloudcam.CloudCamApp;
import com.mccarty.cloudcam.ui.main.MainActivity;
import com.mccarty.cloudcam.utils.CameraAPI;
import com.mccarty.cloudcam.utils.NetworkUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.CAMERA_SERVICE;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    CameraManager provideCameraManager(Context context) {
       return (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @Provides
    @Singleton
    CameraAPI provideCameraAPI(CameraManager manager) {
        return new CameraAPI(manager);
    }

    @Provides
    @Singleton
    MainActivity provideMainActivity() {
        return new MainActivity();
    }
}
