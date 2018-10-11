package com.coins.ui.main;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.coins.data.FxRates;

/**
 * Created by xnorcode on 05/10/2018.
 */
public class FxRatesDiffCallback extends DiffUtil.Callback {

    private final FxRates mOldRates, mNewRates;

    public FxRatesDiffCallback(FxRates mOldRates, FxRates mNewRates) {
        this.mOldRates = mOldRates;
        this.mNewRates = mNewRates;
    }

    @Override
    public int getOldListSize() {
        return mOldRates.getRates().size();
    }

    @Override
    public int getNewListSize() {
        return mNewRates.getRates().size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldRates.getRates().get(oldItemPosition).getName()
                .equals(mNewRates.getRates().get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldRates.getRates().get(oldItemPosition)
                .equals(mNewRates.getRates().get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // implement this method for ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
