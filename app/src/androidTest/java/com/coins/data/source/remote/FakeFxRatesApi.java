package com.coins.data.source.remote;

import com.coins.data.FxRates;
import com.coins.data.Rate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xnorcode on 10/09/2018.
 */
public class FakeFxRatesApi implements FxRatesApi {

    @Override
    public FxRates getLatestFxRates(String base) {
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate("USD", 1.05));
        return new FxRates("EUR", "today", rates);
    }
}
