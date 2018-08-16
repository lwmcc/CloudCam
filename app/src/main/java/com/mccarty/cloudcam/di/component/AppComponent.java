package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.CloudCamApp;
import com.mccarty.cloudcam.di.module.AppModule;
import com.mccarty.cloudcam.ui.camera.CameraActivity;


import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class})
public interface AppComponent extends AndroidInjector<CloudCamApp> {
    void inject(CameraActivity cameraActivity);
}
