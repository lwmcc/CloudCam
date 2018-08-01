package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.di.module.CameraFragmentModule;
import com.mccarty.cloudcam.ui.camera.CameraActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Larry McCarty on 7/26/2018.
 */

@Singleton
@Component(modules = CameraFragmentModule.class)
public interface CameraFragmentComponent {
    void inject(CameraActivity cameraActivity);
}
