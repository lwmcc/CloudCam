package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.di.component.FragmentScope;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.camera.CameraFragment;
import com.mccarty.cloudcam.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = CameraFragmentModule.class)
    abstract CameraActivity cameraActivity();

    @FragmentScope
    @ContributesAndroidInjector(modules = CameraPresenterModule.class)
    abstract CameraFragment cameraFragment();
}
