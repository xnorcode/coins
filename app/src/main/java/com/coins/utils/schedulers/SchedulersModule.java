package com.coins.utils.schedulers;

import dagger.Binds;
import dagger.Module;

/**
 * Created by xnorcode on 11/09/2018.
 */
@Module
public abstract class SchedulersModule {

    @Binds
    abstract BaseSchedulersProvider providesSchedulerProvider(SchedulersProvider schedulersProvider);
}
