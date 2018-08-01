package com.mccarty.cloudcam.di.module;

import android.app.Activity;
import android.util.Size;

import com.mccarty.cloudcam.utils.ConfigurationTransform;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Larry McCarty on 4/9/2018.
 */

@Module
public class ConfigurationTransformModule {

    @Provides
    ConfigurationTransform provideConfigurationTransform(int width, int height, Activity activity, Size size) {
        return new ConfigurationTransform(width, height, activity, size);
    }
}
