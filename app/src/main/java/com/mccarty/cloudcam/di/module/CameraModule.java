package com.mccarty.cloudcam.di.module;


import android.content.Context;
import android.hardware.camera2.CameraManager;

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

   /* @Provides
    @Inject
    CameraAPI provideCamera(CameraManager manager) {
        return new CameraAPI(manager);
    }*/
}
