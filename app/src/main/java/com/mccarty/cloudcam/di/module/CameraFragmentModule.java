package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.camera.CameraFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraFragmentModule {

    @Provides
    CameraFragment provideCameraFragment() {
        return new CameraFragment();
    }
}
