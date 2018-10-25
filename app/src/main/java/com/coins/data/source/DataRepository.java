package com.coins.data.source;

import com.coins.data.FxRates;
import com.coins.data.source.remote.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by xnorcode on 09/09/2018.
 */
@Singleton
public class DataRepository implements DataSource {

    private RemoteDataSource remoteDataSource;

    private FxRates cachedRates;


    @Inject
    public DataRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }


    @Override
    public Flowable<FxRates> getLatestFxRates(String base) {
        return Flowable.<FxRates>create(emitter -> {

            // execute network call
            FxRates rates = remoteDataSource.getLatestFxRatesFromApi(base);

            if (rates != null) {
                // cache response
                cachedRates = rates;
                // proceed
                emitter.onNext(rates);
            } else if (cachedRates != null) {
                // show cached data
                emitter.onNext(cachedRates);
            } else {
                // throw error if no data to show
                emitter.onError(new Exception("Could not get latest fx rates..."));
                return;
            }

            emitter.onComplete();
        }, BackpressureStrategy.ERROR);
    }
}
