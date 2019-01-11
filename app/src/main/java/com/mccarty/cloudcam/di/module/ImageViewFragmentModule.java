package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.ui.imageview.ImageViewFragment;
import com.mccarty.cloudcam.ui.main.MainFragment;

import dagger.Module;

@Module
public abstract class ImageViewFragmentModule {

    static ImageViewFragment provideImageViewFragment() {
        return new ImageViewFragment();
    }
}