package com.coins.data.source;

import com.coins.data.FxRates;

import io.reactivex.Flowable;

/**
 * Created by xnorcode on 09/09/2018.
 */
public interface FxRatesDataSource {

    Flowable<FxRates> getLatestFxRates(String base);

}
