package com.coins.di;

import android.app.Application;

import com.coins.CoinsApp;
import com.coins.data.source.DataRepositoryModule;
import com.coins.utils.schedulers.SchedulersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by xnorcode on 09/09/2018.
 */
@Singleton
@Component(modules = {DataRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        SchedulersModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<CoinsApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
