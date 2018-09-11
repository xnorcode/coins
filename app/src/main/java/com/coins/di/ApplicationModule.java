package com.coins.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by xnorcode on 09/09/2018.
 */
@Module
public abstract class ApplicationModule {

    @Binds
    abstract Context bindContext(Application application);
}
