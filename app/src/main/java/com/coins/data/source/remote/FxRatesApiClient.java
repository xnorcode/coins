package com.coins.data.source.remote;

import com.coins.data.FxRates;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xnorcode on 09/09/2018.
 * <p>
 * Retrofit Client for FxRates API
 * Can move this class's components into dagger module as well
 */
public class FxRatesApiClient {

    private static final String BASE_URL = "https://revolut.duckdns.org/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            // create Gson with custom deserializer
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(FxRates.class, new FxRatesJsonDeserializer())
                    .create();

            // create retrofit client
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
