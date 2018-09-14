package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.main.MainFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MainFragmentModule {

    @Provides
    MainFragment provideMainFragment() {
        return new MainFragment();
    }
}