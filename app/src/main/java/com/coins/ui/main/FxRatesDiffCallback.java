package com.coins.ui.main;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.coins.data.FxRates;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xnorcode on 05/10/2018.
 */
public class FxRatesDiffCallback extends DiffUtil.Callback {

    private final FxRates oldRates, newRates;

    public FxRatesDiffCallback(FxRates oldRates, FxRates newRates) {
        this.oldRates = oldRates;
        this.newRates = newRates;
    }

    @Override
    public int getOldListSize() {
        return oldRates == null ? 0 : oldRates.getRates().size();
    }

    @Override
    public int getNewListSize() {
        return newRates == null ? 0 : newRates.getRates().size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRates.getRates().get(oldItemPosition).getName()
                .equals(newRates.getRates().get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRates.getRates().get(oldItemPosition)
                .equals(newRates.getRates().get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // here I can pass any change values I require,
        // for this example I just change the rate so
        // I'll just include a flag
        Map<String, Boolean> changes = new HashMap<>();
        changes.put("RATE", true);
        return changes;
    }
}
