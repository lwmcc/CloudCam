package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.utils.NetworkUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 3/29/2018.
 */

@Module
public class NetworkUtilsModule {

    @Provides
    NetworkUtils providesNetworkUtils() {
        return new NetworkUtils();
    }
}
