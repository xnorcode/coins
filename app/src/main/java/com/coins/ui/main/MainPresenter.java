package com.coins.ui.main;

import com.coins.data.FxRates;
import com.coins.data.source.DataRepository;
import com.coins.utils.schedulers.BaseSchedulersProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xnorcode on 11/09/2018.
 */
@Singleton
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private DataRepository mDataRepository;

    private CompositeDisposable mCompositeDisposable;

    private BaseSchedulersProvider mSchedulersProvider;

    private FxRates mCachedRates;

    private double mBaseRate;

    private boolean mUpdateAll;


    @Inject
    public MainPresenter(DataRepository dataRepository, BaseSchedulersProvider schedulersProvider) {
        this.mDataRepository = dataRepository;
        this.mSchedulersProvider = schedulersProvider;
        this.mCompositeDisposable = new CompositeDisposable();

        // set initial base rate
        this.mBaseRate = 100;

        // set flag to update all the list items
        this.mUpdateAll = true;
    }

    @Override
    public void init() {

        // get default EUR rates when app initializes
        getLatestFxRates("EUR");
    }

    @Override
    public void getLatestFxRates(String base) {
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
        mCompositeDisposable.add(mDataRepository.getLatestFxRates(base)
                .subscribeOn(mSchedulersProvider.io())
                .observeOn(mSchedulersProvider.ui())
                .repeatWhen(completed -> completed.delay(1, TimeUnit.SECONDS))
                .subscribe(rates -> {
                    // TODO: 29/09/2018 use DiffUtil for changes here (in the background thread)
                    // replace all below code by using DiffUtil

                    // set new rates
                    mCachedRates = rates;

                    // set base rate value to the list
                    mCachedRates.getRates().get(0).setRate(mBaseRate);

                    if (mUpdateAll) {

                        mUpdateAll = false;

                        // update all items
                        mView.showNewRates(true);
                    } else {

                        // update only first item
                        mView.showNewRates(false);
                    }


                }, throwable -> mView.showError()));

    }

    @Override
    public void setView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
        mCompositeDisposable = null;
    }

    @Override
    public void onBindRecyclerRowView(int position, MainContract.RecyclerRowView rowView) {

        // null check
        if (mCachedRates == null || mCachedRates.getRates() == null) return;

        // get data from list
        double rate = mCachedRates.getRates().get(position).getRate();
        String name = mCachedRates.getRates().get(position).getName();

        // set rate
        rowView.setRate(String.valueOf(position == 0 ? mBaseRate : mBaseRate * rate));

        // set description
        rowView.setDescription(name);

        // set icon (Note: renamed TRY icon resource as "turkey" to avoid system resource conflicts)
        rowView.setIcon(!name.equals("TRY") ? name.toLowerCase() : "turkey");

        // set name
        rowView.setName(name);

    }

    @Override
    public int getCurrenciesCount() {
        if (mCachedRates == null || mCachedRates.getRates() == null) return 0;
        return mCachedRates.getRates().size();
    }

    @Override
    public void setNewBaseRateFromUserInput(String newRate) {
        if (newRate == null || newRate.length() == 0) {
            mBaseRate = 1;
            return;
        }

        try {
            mBaseRate = Double.parseDouble(newRate);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseRate = 1;
        }

        // update only first item
        mView.showNewRates(false);
    }

    @Override
    public void switchBaseCurrency(int position) {
        getLatestFxRates(mCachedRates.getRates().get(position).getName());
        mUpdateAll = true;
    }
}
