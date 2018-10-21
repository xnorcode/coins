package com.coins.ui.main;

import com.coins.data.FxRates;
import com.coins.data.Rate;
import com.coins.data.source.DataRepository;
import com.coins.utils.schedulers.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class MainPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    MainContract.View mView;

    @Mock
    DataRepository mDataRepository;

    MainPresenter mPresenter;

    FxRates mRates;


    @Before
    public void setUp() throws Exception {
        // set presenter with mocks
        mPresenter = new MainPresenter(mDataRepository, TestSchedulerProvider.getInstance());
        mPresenter.setView(mView);

        // set fake
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate("USD", 1.05));
        mRates = new FxRates("EUR", "today", rates);
    }


    @Test
    public void getLatestFxRates_Successful() throws InterruptedException {
        // make data available in repository
        Mockito.when(mDataRepository.getLatestFxRates("EUR")).thenReturn(Flowable.just(mRates));

        // request data
        mPresenter.init();

        // simulate 4sec delay
        Thread.sleep(4000);

        // validate View got response data only once
        // since presenter is now in updating mode
        Mockito.verify(mView).showNewRates(mRates, mPresenter.mCachedRates, false);

        // set updating status to false to trigger
        // another call to the View, testing the
        // filtering and timer
        mPresenter.setUpdateStatus(false);

        // simulate 2sec delay
        Thread.sleep(2000);

        // verify View is called one more time,
        // in total is called twice
        Mockito.verify(mView, Mockito.times(2)).showNewRates(mRates, mPresenter.mCachedRates, false);
    }


    @Test
    public void getLatestFxRates_Fails(){
        // provide empty data
        Mockito.when(mDataRepository.getLatestFxRates("EUR"))
                .thenReturn(Flowable.error(new Throwable("No Connection")));

        // request data
        mPresenter.init();

        // validate View was called to show an error
        Mockito.verify(mView).showError();
    }
}