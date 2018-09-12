package com.coins.data.source.remote;

import com.coins.data.FxRates;

import java.io.IOException;

/**
 * Created by xnorcode on 12/09/2018.
 */
public interface RemoteDataSource {

    /**
     * Returns latest Fx rates from remote data source
     *
     * @param base The base currency
     * @return The FxRates Object with the currency rates
     */
    FxRates getLatestFxRates(String base) throws IOException;
}
