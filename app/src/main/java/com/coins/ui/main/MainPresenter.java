package com.coins.ui.main;

import com.coins.data.source.FxRatesRepository;
import com.coins.utils.Schedulers.BaseSchedulersProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private FxRatesRepository mFxRatesRepository;

    private CompositeDisposable mCompositeDisposable;

    private BaseSchedulersProvider mSchedulersProvider;


    @Inject
    public MainPresenter(FxRatesRepository fxRatesRepository, BaseSchedulersProvider schedulersProvider) {
        this.mFxRatesRepository = fxRatesRepository;
        this.mSchedulersProvider = schedulersProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getLatestFxRates(String base) {
        Disposable disposable = mFxRatesRepository.getLatestFxRates(base)
                .subscribeOn(mSchedulersProvider.io())
                .observeOn(mSchedulersProvider.ui())
                .subscribe(rates -> mView.showRates(rates),
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
