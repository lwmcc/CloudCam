package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.ui.camera.CameraFragment;

import dagger.android.AndroidInjector;
//@CameraScope
//@Subcomponent(modules = CameraModule.class)
public interface CameraComponent extends AndroidInjector<CameraFragment> {
  /*  @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CameraFragment>{
        abstract Builder cameraModule(CameraModule cameraModule);
    }*/
}
