package com.coins.data.source.remote;

import com.coins.data.FxRates;
import com.coins.data.Rate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xnorcode on 10/09/2018.
 */
public class FakeFxRatesApi implements RemoteDataSource {

    @Override
    public FxRates getLatestFxRatesFromApi(String base) {
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate("EUR", 1));
        rates.add(new Rate("USD", 1.16));
        rates.add(new Rate("GBP", 0.88));
        rates.add(new Rate("CHF", 1.12));
        return new FxRates("EUR", "today", rates);
    }
}
