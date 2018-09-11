package com.coins.ui.main;

import com.coins.data.source.FxRatesRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private FxRatesRepository mFxRatesRepository;

    private CompositeDisposable mCompositeDisposable;


    @Inject
    public MainPresenter(FxRatesRepository mFxRatesRepository) {
        this.mFxRatesRepository = mFxRatesRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getLatestFxRates(String base) {
        Disposable disposable = mFxRatesRepository.getLatestFxRates(base)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rates -> mView.refreshRates(rates),
                        throwable -> mView.showError());
        mCompositeDisposable.add(disposable);
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
