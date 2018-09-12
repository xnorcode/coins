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
    public void getLatestFxRates() {
        // make data available in repository
        Mockito.when(mDataRepository.getLatestFxRates("EUR")).thenReturn(Flowable.just(mRates));

        // request data
        mPresenter.getLatestFxRates("EUR");

        // validate View got the response data
        Mockito.verify(mView).showRates(mRates);
    }
}