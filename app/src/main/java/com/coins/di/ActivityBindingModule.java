package com.coins.di;

import com.coins.ui.main.MainActivity;
import com.coins.ui.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xnorcode on 10/09/2018.
 */
@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity providesMainActivity();
}
