package com.mccarty.cloudcam.di.module;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.CameraManager;

import com.mccarty.cloudcam.model.api.CameraAPI;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.main.Main3Activity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppModule {
/*

    Application application;

    @ContributesAndroidInjector
    abstract Main3Activity main3Activity();

    @ContributesAndroidInjector
    abstract CameraActivity cameraActivity();

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
*/

    /*@Provides
    @Singleton
    Context provideContext() {
        return application;
    }
*/
}
