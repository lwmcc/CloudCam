package com.mccarty.cloudcam;

import android.app.Activity;
import android.app.Fragment;
import android.support.multidex.MultiDexApplication;

import com.mccarty.cloudcam.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;

public class CloudCamApp extends MultiDexApplication implements HasActivityInjector, HasFragmentInjector {

  @Inject
  DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

  @Override
  public void onCreate() {
    super.onCreate();
    DaggerAppComponent.builder().application(this).build().inject(this);
  }

  @Override
  public AndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  @Override
  public AndroidInjector<Fragment> fragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
}
