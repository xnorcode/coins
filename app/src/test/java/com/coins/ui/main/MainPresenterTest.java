package com.coins.ui.main;

import com.coins.data.FxRates;
import com.coins.data.source.FxRatesRepository;
import com.coins.utils.Schedulers.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

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
    FxRatesRepository mFxRatesRepository;

    MainPresenter mPresenter;

    FxRates mRates;


    @Before
    public void setUp() throws Exception {
        // set presenter with mocks
        mPresenter = new MainPresenter(mFxRatesRepository, TestSchedulerProvider.getInstance());
        mPresenter.setView(mView);

        // set fake
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.05);
        mRates = new FxRates("EUR", "today", rates);
    }


    @Test
    public void getLatestFxRates() {
        // make data available in repository
        Mockito.when(mFxRatesRepository.getLatestFxRates("EUR")).thenReturn(Flowable.just(mRates));

        // request data
        mPresenter.getLatestFxRates("EUR");

        // validate View got the response data
        Mockito.verify(mView).refreshRates(mRates);
    }
}