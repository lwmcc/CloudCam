package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.model.CameraModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraModelModule {
    @Provides
    @Singleton
    CameraModel provideCameraModel() {
        return new CameraModel();
    }
}
