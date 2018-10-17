package com.coins.ui.main;

import android.support.v7.util.DiffUtil;

import com.coins.ui.base.BasePresenter;
import com.coins.ui.base.BaseView;

/**
 * Created by xnorcode on 11/09/2018.
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {


        /**
         * Show updated FxRates on screen
         *
         * @param diffResult Diff Result ref
         */
        // TODO: 17/10/2018 DiffUtil must be removed from here and only be in the View
        void showNewRates(DiffUtil.DiffResult diffResult);


        /**
         * Notify user there was an error
         */
        void showError();
    }

    interface Presenter extends BasePresenter<View> {


        /**
         * Initialize Presenter
         */
        void init();


        /**
         * Request to download latest FxRates
         *
         * @param base The base currency
         */
        void getLatestFxRates(String base);


        /**
         * Bind RecyclerView Row View in presenter
         *
         * @param position The current RecyclerView row item position
         * @param rowView  RecyclerView row item view
         */
        void onBindRecyclerRowView(int position, RecyclerRowView rowView);


        /**
         * Get Count of Currencies in list
         *
         * @return The list item count
         */
        int getCurrenciesCount();


        /**
         * Get the new base rate set by the user's input
         *
         * @param newRate The input string from the user
         */
        void setNewBaseRateFromUserInput(String newRate);


        /**
         * Switch base currencies
         *
         * @param position The position of the currency selected as a new base currency
         */
        void switchBaseCurrency(int position);
    }

    interface RecyclerRowView {


        /**
         * Set the icon
         *
         * @param iconName The name of the Icon
         */
        void setIcon(String iconName);


        /**
         * Set the name
         *
         * @param name The name of the currency
         */
        void setName(String name);


        /**
         * Set the description
         *
         * @param name The name of the currency
         */
        void setDescription(String name);


        /**
         * Set the rate
         *
         * @param rate The currency's rate
         */
        void setRate(String rate);
    }
}
