package com.coins.data.source;

import com.coins.data.source.remote.FxRatesApi;
import com.coins.data.source.remote.FxRatesApiClient;

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
    FxRatesApi provideRemoteDataSource() {
        return FxRatesApiClient.getClient().create(FxRatesApi.class);
    }
}
