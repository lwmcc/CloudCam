package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.di.module.CameraFragmentModule;
import com.mccarty.cloudcam.di.module.MainFragmentModule;
import com.mccarty.cloudcam.ui.main.Main3Activity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Larry McCarty on 7/28/2018.
 */

@Subcomponent(modules = MainFragmentModule.class)
public interface MainActivitySubcomponent extends AndroidInjector<Main3Activity> {
    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<Main3Activity> {}
}
