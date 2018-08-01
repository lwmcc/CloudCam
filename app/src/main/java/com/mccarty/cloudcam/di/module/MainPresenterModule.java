package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 7/25/2018.
 */

@Module
public class MainPresenterModule {
    @Provides
    MainPresenterImpl provideMainPresenter() { return new MainPresenterImpl();
    }
}
