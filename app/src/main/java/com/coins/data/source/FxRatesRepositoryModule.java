package com.coins.data.source;

import com.coins.data.source.remote.ApiHelper;
import com.coins.data.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xnorcode on 09/09/2018.
 */
@Module
public class FxRatesRepositoryModule {

    @Singleton
    @Provides
    RemoteDataSource provideRemoteDataSource() {
        return new ApiHelper();
    }
}
