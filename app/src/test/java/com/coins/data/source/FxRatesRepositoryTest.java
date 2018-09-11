package com.coins.data.source;

import com.coins.data.FxRates;
import com.coins.data.Rate;
import com.coins.data.source.remote.FxRatesApi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

/**
 * Created by xnorcode on 09/09/2018.
 */
public class FxRatesRepositoryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private FxRates mRates;

    @Mock
    private FxRatesApi mRemoteDataSource;

    @InjectMocks
    private FxRatesRepository mRatesRepository;


    @Before
    public void setup() {
        // init rates
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate("USD", 1.05));
        mRates = new FxRates("EUR", "today", rates);
    }


    @Test
    public void getLatestFxRates() {
        // make data available in remote data source
        Mockito.when(mRemoteDataSource.getLatestFxRates("EUR")).thenReturn(mRates);

        // call method and subscribe
        TestSubscriber<FxRates> testSubscriber = new TestSubscriber<>();
        mRatesRepository.getLatestFxRates("EUR").subscribe(testSubscriber);

        // verify remote data source method called once
        Mockito.verify(mRemoteDataSource).getLatestFxRates("EUR");

        // check results
        testSubscriber.assertValue(mRates);
    }
}