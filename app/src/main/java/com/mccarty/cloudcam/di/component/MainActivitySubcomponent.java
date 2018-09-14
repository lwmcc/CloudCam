package com.mccarty.cloudcam.di.component;

import com.mccarty.cloudcam.di.module.MainFragmentModule;
import com.mccarty.cloudcam.ui.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = MainFragmentModule.class)
public interface MainActivitySubcomponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<MainActivity> {}
}
