package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.camera.CameraFragment;
import com.mccarty.cloudcam.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {MainFragmentModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {CameraActivityModule.class,
            NetworkUtilsModule.class, CameraModule.class})
    abstract CameraActivity bindCameraActivity();
}
