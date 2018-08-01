package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;

import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.main.Main3Activity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public abstract class AppModule {

    Application application;

    @ContributesAndroidInjector
    abstract Main3Activity main3Activity();

    @ContributesAndroidInjector
    abstract CameraActivity cameraActivity();

   /* public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }
*/
   /* @Provides
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
    }*/
}
