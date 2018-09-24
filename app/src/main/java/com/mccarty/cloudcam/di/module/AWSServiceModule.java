package com.mccarty.cloudcam.di.module;

import android.app.Application;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AWSServiceModule {

    @Provides
    @Singleton
    AWSConfiguration provideAWSConfiguration(Application application) {
        return new AWSConfiguration(application);
    }

    @Provides
    @Singleton
    IdentityManager provideIdentityManager(Application application, AWSConfiguration awsConfig) {
        return new IdentityManager(application, awsConfig);
    }



}
