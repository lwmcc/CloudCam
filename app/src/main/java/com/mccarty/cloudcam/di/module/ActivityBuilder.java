package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {MainActivityModule.class, CameraActivityModule.class})
    abstract MainActivity bindMainActivity();
}
