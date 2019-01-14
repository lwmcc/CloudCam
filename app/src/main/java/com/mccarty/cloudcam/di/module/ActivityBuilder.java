package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.di.component.FragmentScope;
import com.mccarty.cloudcam.ui.camera.CameraActivity;
import com.mccarty.cloudcam.ui.camera.CameraFragment;
import com.mccarty.cloudcam.ui.imageview.ImageView2Activity;
import com.mccarty.cloudcam.ui.imageview.ImageViewFragment;
import com.mccarty.cloudcam.ui.main.MainActivity;
import com.mccarty.cloudcam.ui.main.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainFragmentModule.class,  MainPresenterModule.class})
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {ImageViewFragmentModule.class, ImageViewPresenterModule.class})
    abstract ImageView2Activity imageView2Activity();

    @ActivityScope
    @ContributesAndroidInjector(modules = MainPresenterModule.class)
    abstract MainFragment mainFragment();

    @ActivityScope
    @ContributesAndroidInjector(modules = ImageViewPresenterModule.class)
    abstract ImageViewFragment imageViewFragment();

    @ActivityScope
    @ContributesAndroidInjector(modules = CameraFragmentModule.class)
    abstract CameraActivity cameraActivity();

    @FragmentScope
    @ContributesAndroidInjector(modules = CameraPresenterModule.class)
    abstract CameraFragment cameraFragment();
}
