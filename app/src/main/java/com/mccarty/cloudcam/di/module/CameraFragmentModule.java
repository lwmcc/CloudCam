package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.camera.CameraFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 7/26/2018.
 */
@Module
public class CameraFragmentModule {
    @Provides
    CameraFragment provideCameraFragment() {
        return new CameraFragment();
    }
}
