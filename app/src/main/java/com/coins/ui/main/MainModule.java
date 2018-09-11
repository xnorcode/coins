package com.coins.ui.main;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xnorcode on 10/09/2018.
 */
@Module
public abstract class MainModule {

    @ContributesAndroidInjector
    abstract MainFragment providesMainFragment();

    @Binds
    abstract MainContract.Presenter providesPresenter(MainPresenter presenter);
}
