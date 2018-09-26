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
    }
}
