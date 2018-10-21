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
        rates.add(new Rate("AUD", 1.12));
        rates.add(new Rate("BGN", 2.42));
        rates.add(new Rate("BRL", 2.72));
        rates.add(new Rate("CAD", 1.62));
        rates.add(new Rate("CHF", 23.11));
        rates.add(new Rate("CNY", 35.10));
        rates.add(new Rate("CZK", 10.12));
        rates.add(new Rate("DKK", 5.12));
        rates.add(new Rate("HUF", 4.12));

        if (base.equals("HUF")) {
            Rate rate = rates.get(rates.size() - 1);
            rates.remove(rate);
            rates.add(0, rate);
        }

        return new FxRates(base, "today", rates);
    }
}
