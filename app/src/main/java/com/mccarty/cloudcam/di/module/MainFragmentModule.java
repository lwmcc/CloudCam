package com.mccarty.cloudcam.di.module;

import dagger.Module;
import dagger.Provides;

import com.mccarty.cloudcam.ui.main.MainFragment;

import javax.inject.Singleton;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

@Module
public class MainFragmentModule {

    @Provides
    MainFragment provideMainFragment() {
        return new MainFragment();
    }
}