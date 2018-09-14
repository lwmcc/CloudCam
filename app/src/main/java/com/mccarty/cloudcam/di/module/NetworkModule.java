package com.mccarty.cloudcam.di.module;

import android.app.Application;

import com.mccarty.cloudcam.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    static NetworkUtils provideNetworkUtils(Application application) {
        return new NetworkUtils(application);
    }
}
