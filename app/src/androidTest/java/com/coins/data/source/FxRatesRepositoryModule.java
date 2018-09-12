package com.coins.data.source;

import com.coins.data.source.remote.FakeFxRatesApi;
import com.coins.data.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xnorcode on 10/09/2018.
 */
@Module
public class FxRatesRepositoryModule {

    @Singleton
    @Provides
    RemoteDataSource providesFxRatesApi() {
        return new FakeFxRatesApi();
    }

}
