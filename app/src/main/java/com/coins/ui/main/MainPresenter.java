package com.coins.ui.main;

import com.coins.data.source.DataRepository;
import com.coins.utils.schedulers.BaseSchedulersProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
    public void getLatestFxRates(String base) {
        Disposable disposable = mDataRepository.getLatestFxRates(base)
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
