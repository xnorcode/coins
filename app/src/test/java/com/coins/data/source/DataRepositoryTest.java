package com.coins.data.source;

import com.coins.data.FxRates;
import com.coins.data.Rate;
import com.coins.data.source.remote.RemoteDataSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

/**
 * Created by xnorcode on 09/09/2018.
 */
public class DataRepositoryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private FxRates rates;

    @Mock
    private RemoteDataSource remoteDataSource;

    @InjectMocks
    private DataRepository dataRepository;


    @Before
    public void setup() {
        // init rates
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate("USD", 1.05));
        this.rates = new FxRates("EUR", "today", rates);
    }


    @Test
    public void getLatestFxRates() throws IOException {
        // make data available in remote data source
        Mockito.when(remoteDataSource.getLatestFxRatesFromApi("EUR")).thenReturn(rates);

        // call method and subscribe
        TestSubscriber<FxRates> testSubscriber = new TestSubscriber<>();
        dataRepository.getLatestFxRates("EUR").subscribe(testSubscriber);

        // verify remote data source method called once
        Mockito.verify(remoteDataSource).getLatestFxRatesFromApi("EUR");

        // check results
        testSubscriber.assertValue(rates);
    }
}