package com.coins.data.source.remote.FxRatesApiUtils;

import com.coins.data.FxRates;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xnorcode on 07/09/2018.
 */
public interface FxRatesApi {


    /**
     * GET Request to API to get latest fx rates
     * based on given base currency
     *
     * @param base String of base currency
     * @return Latest FxRates object with latest rates
     */
    @GET("latest")
    Call<FxRates> getLatestFxRates(@Query("base") String base);
}
