package com.coins.ui.main;

import com.coins.data.FxRates;
import com.coins.data.Rate;
import com.coins.data.source.DataRepository;
import com.coins.utils.schedulers.BaseSchedulersProvider;

import java.util.List;
import java.util.Map;
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

    // cache is visible only for the tests and presenter
    protected FxRates mCachedRates;

    private double mBaseRate;

    private static boolean mUpdating = false;


    @Inject
    public MainPresenter(DataRepository dataRepository, BaseSchedulersProvider schedulersProvider) {
        this.mDataRepository = dataRepository;
        this.mSchedulersProvider = schedulersProvider;
        this.mCompositeDisposable = new CompositeDisposable();

        // set initial base rate
        this.mBaseRate = 100;

        // init cache
        this.mCachedRates = new FxRates();
    }

    @Override
    public void setView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
        mCachedRates = null;
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
        mCompositeDisposable = null;
    }

    @Override
    public void init() {
        // get default EUR rates when app initializes
        getLatestFxRates("EUR");
    }

    @Override
    public void getLatestFxRates(String base) {
        mCompositeDisposable.clear();
        mCompositeDisposable.add(mDataRepository.getLatestFxRates(base)
                .subscribeOn(mSchedulersProvider.io())
                .repeatWhen(completed -> completed.delay(1, TimeUnit.SECONDS))
                .filter(rates -> !mUpdating)
                .observeOn(mSchedulersProvider.ui())
                .subscribe(newRates -> {

                    // set updating list status to true
                    setUpdateStatus(true);

                    // update view with new rates
                    mView.showNewRates(newRates, mCachedRates, false);

                }, throwable -> mView.showError()));

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
    public void onBindRecyclerRowView(int position, MainContract.RecyclerRowView rowView, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindRecyclerRowView(position, rowView);
        } else {
            // get payload data
            Map data = (Map) payloads.get(0);

            if (data.containsKey("RATE") && (boolean) data.get("RATE")) {
                // get data from list
                double rate = mCachedRates.getRates().get(position).getRate();

                // set rate
                rowView.setRate(String.valueOf(position == 0 ? mBaseRate : mBaseRate * rate));
            }
        }
    }

    @Override
    public void setUpdateStatus(boolean updating) {
        mUpdating = updating;
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

        mCachedRates.getRates().get(0).setRate(mBaseRate);
    }

    @Override
    public void switchBaseCurrency(int position) {

        // null check
        if (mCachedRates == null || mCachedRates.getRates() == null) return;

        // set updating list status to true
        setUpdateStatus(true);

        // scroll list back to the top
        mView.scrollBackToTop();

        // get selected new base currency
        Rate selected = mCachedRates.getRates().get(position);

        // maintain rate shown on screen
        selected.setRate(selected.getRate() * mBaseRate);

        // reorder list
        FxRates rates = new FxRates();
        rates.setBase(selected.getName());
        rates.setDate(mCachedRates.getDate());
        rates.getRates().clear();
        rates.getRates().addAll(mCachedRates.getRates());
        rates.getRates().remove(selected);
        rates.getRates().add(0, selected);

        // update new base rate
        mBaseRate = selected.getRate();

        // show new list order
        mView.showNewRates(rates, mCachedRates, false);

        // get latest fx rates for new base currency
        getLatestFxRates(selected.getName());
    }
}
