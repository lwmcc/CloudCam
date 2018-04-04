package com.mccarty.cloudcam;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.mccarty.cloudcam.awsutils.AWSProvider;
import com.mccarty.cloudcam.di.component.AppComponent;
import com.mccarty.cloudcam.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Larry McCarty on 3/20/2018.
 */

public class CloudCamApp extends MultiDexApplication implements HasActivityInjector {

    private AppComponent component;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AWSProvider.initialize(getApplicationContext());
        //registerActivityLifecycleCallbacks(new ActivityLifeCycle());
    }
}
