package com.mccarty.cloudcam.di.module;
import com.mccarty.cloudcam.ui.main.MainFragment;
import dagger.Module;

@Module
public abstract class MainFragmentModule {

    static MainFragment provideMainFragment() {
        return new MainFragment();
    }
}