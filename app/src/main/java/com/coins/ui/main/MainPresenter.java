package com.coins.ui.main;

import com.coins.data.source.DataRepository;
import com.coins.utils.schedulers.BaseSchedulersProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private DataRepository mDataRepository;

    private CompositeDisposable mCompositeDisposable;

    private BaseSchedulersProvider mSchedulersProvider;


    @Inject
    public MainPresenter(DataRepository dataRepository, BaseSchedulersProvider schedulersProvider) {
        this.mDataRepository = dataRepository;
        this.mSchedulersProvider = schedulersProvider;
        this.mCompositeDisposable = new CompositeDisposable();
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
                .subscribe(rates -> mView.showRates(rates),
                        throwable -> mView.showError()));
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
}
