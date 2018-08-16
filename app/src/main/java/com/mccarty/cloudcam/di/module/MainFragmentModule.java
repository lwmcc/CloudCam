package com.mccarty.cloudcam.di.module;

import dagger.Module;
import dagger.Provides;

import com.mccarty.cloudcam.ui.main.MainFragment;

import javax.inject.Singleton;

@Module
public class MainFragmentModule {
    @Provides
    MainFragment provideMainFragment() {
        return new MainFragment();
    }
}