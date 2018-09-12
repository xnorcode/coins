package com.coins.data.source.remote;

import com.coins.data.FxRates;
import com.coins.data.source.remote.utils.FxRatesApi;
import com.coins.data.source.remote.utils.FxRatesApiClient;

import java.io.IOException;

/**
 * Created by xnorcode on 12/09/2018.
 * <p>
 * Remote Data Source Manager
 */
public class ApiHelper implements RemoteDataSource {


    public ApiHelper() {
    }


    @Override
    public FxRates getLatestFxRates(String base) throws IOException {
        return FxRatesApiClient.getClient().create(FxRatesApi.class)
                .getLatestFxRates(base)
                .execute()
                .body();
    }
}