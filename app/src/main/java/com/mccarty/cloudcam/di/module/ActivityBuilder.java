package com.mccarty.cloudcam.di.module;

import android.app.Activity;
import android.hardware.Camera;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.di.component.FragmentScope;
import com.mccarty.cloudcam.di.component.MainActivitySubcomponent;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.camera.CameraFragment;
import com.mccarty.cloudcam.ui.main.Main3Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = CameraFragmentModule.class)
    abstract CameraActivity cameraActivity();

    @FragmentScope
    @ContributesAndroidInjector(modules = CameraPresenterModule.class)
    abstract CameraFragment cameraFragment();
}
