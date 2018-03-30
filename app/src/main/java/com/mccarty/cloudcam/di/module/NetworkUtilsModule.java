package com.mccarty.cloudcam.di.module;

import android.content.Context;
import android.net.NetworkInfo;

import com.mccarty.cloudcam.utils.NetworkUtils;

import javax.inject.Inject;

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
