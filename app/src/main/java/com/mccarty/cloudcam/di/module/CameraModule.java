package com.mccarty.cloudcam.di.module;


import com.mccarty.cloudcam.utils.CameraAPI;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 3/29/2018.
 */

@Module
public class CameraModule {

    @Provides
    CameraAPI provideCamera() {
        return new CameraAPI();
    }
}
