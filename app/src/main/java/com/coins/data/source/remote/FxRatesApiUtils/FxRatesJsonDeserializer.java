package com.coins.data.source.remote.FxRatesApiUtils;

import com.coins.data.FxRates;
import com.coins.data.Rate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by xnorcode on 09/09/2018.
 * <p>
 * Custom Deserializer for FxRates object used in order
 * to build a map set for fx rate values.
 */
public class FxRatesJsonDeserializer implements JsonDeserializer<FxRates> {
    @Override
    public FxRates deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        // get root json object
        JsonObject root = json.getAsJsonObject();

        // get rates json object
        JsonObject ratesObj = root.get("rates").getAsJsonObject();

        // initialize rates list
        List<Rate> rates = new ArrayList<>();

        // get all key members from json object
        Set<String> keys = ratesObj.keySet();

        // Add base currency rate in list position 0
        rates.add(new Rate(root.get("base").getAsString(), 1));

        // iterate json object elements, store
        // keys and values in list
        for (String key : keys) {
            rates.add(new Rate(key, ratesObj.get(key).getAsDouble()));
        }

        // construct and return FxRates object
        return new FxRates(root.get("base").getAsString(), root.get("date").getAsString(), rates);
    }
}
