package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.camera.CameraFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 3/26/2018.
 */

@Module
public class CameraActivityModule {

    @Provides
    CameraFragment providesCameraFragment() {
        return new CameraFragment();
    }
}
