package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.di.module.CameraFragmentModule;
import com.mccarty.cloudcam.di.module.CameraModule;
import com.mccarty.cloudcam.model.CameraModel;
import com.mccarty.cloudcam.ui.camera.CameraActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = CameraModule.class)
public interface CameraComponent {
    //void inject(CameraModel cameraModel);
}
