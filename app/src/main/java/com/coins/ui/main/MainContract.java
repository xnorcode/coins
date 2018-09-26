package com.coins.ui.main;

import com.coins.data.FxRates;
import com.coins.ui.base.BasePresenter;
import com.coins.ui.base.BaseView;

/**
 * Created by xnorcode on 11/09/2018.
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {


        /**
         * User requests for new currency rates
         *
         * @param base The base currency
         */
        void refreshRates(String base);


        /**
         * Show updated FxRates on screen
         *
         * @param rates The updated FxRates
         */
        void showRates(FxRates rates);


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
