package com.mccarty.cloudcam.di.module;

import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.di.component.FragmentScope;
import com.mccarty.cloudcam.ui.main.MainContract;
import com.mccarty.cloudcam.ui.main.MainFragment;
import com.mccarty.cloudcam.ui.main.MainPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//@Module
public abstract class MainActivityModule {

   /* @FragmentScope
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @ActivityScope
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);*/
}
