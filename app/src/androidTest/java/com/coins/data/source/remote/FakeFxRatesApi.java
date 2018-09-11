package com.coins.data.source.remote;

import com.coins.data.FxRates;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xnorcode on 10/09/2018.
 */
public class FakeFxRatesApi implements FxRatesApi {

    @Override
    public FxRates getLatestFxRates(String base) {
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.05);
        return new FxRates("EUR", "today", rates);
    }
}
