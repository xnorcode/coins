package com.coins.data.source;

import com.coins.data.FxRates;
import com.coins.data.source.remote.FxRatesApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by xnorcode on 09/09/2018.
 */
@Singleton
public class FxRatesRepository implements FxRatesDataSource {

    private FxRatesApi mRemoteDataSource;

    private FxRates mCachedRates;


    @Inject
    public FxRatesRepository(FxRatesApi remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }


    @Override
    public Flowable<FxRates> getLatestFxRates(String base) {
        return Flowable.<FxRates>create(emitter -> {

            // execute network call
            FxRates rates = mRemoteDataSource.getLatestFxRates(base);

            if (rates != null) {
                // cache response
                mCachedRates = rates;
                // proceed
                emitter.onNext(rates);
            } else if (mCachedRates != null) {
                // show cached data
                emitter.onNext(mCachedRates);
            } else {
                // throw error if no data to show
                emitter.onError(new Exception("Could not get latest fx rates..."));
                return;
            }

            emitter.onComplete();
        }, BackpressureStrategy.ERROR);
    }
}
