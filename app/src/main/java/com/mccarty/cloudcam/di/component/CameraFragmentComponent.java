package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.di.module.CameraFragmentModule;
import com.mccarty.cloudcam.di.module.CameraModule;
import com.mccarty.cloudcam.di.module.CameraPresenterModule;
import com.mccarty.cloudcam.ui.camera.CameraFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@CameraScope
@Subcomponent(modules = {CameraFragmentModule.class})
public interface CameraFragmentComponent extends AndroidInjector<CameraFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CameraFragment>{
        abstract Builder cameraFragmentModule(CameraFragmentModule cameraFragmentModule);
    }
}
