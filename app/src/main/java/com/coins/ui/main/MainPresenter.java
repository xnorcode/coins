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

    private MainContract.View view;

    private DataRepository dataRepository;

    private CompositeDisposable compositeDisposable;

    private BaseSchedulersProvider schedulersProvider;

    // cache is visible only for the tests and presenter
    protected FxRates cachedRates;

    private double baseRate;

    private static boolean isUpdating = false;


    @Inject
    public MainPresenter(DataRepository dataRepository, BaseSchedulersProvider schedulersProvider) {
        this.dataRepository = dataRepository;
        this.schedulersProvider = schedulersProvider;
        this.compositeDisposable = new CompositeDisposable();

        // set initial base rate
        this.baseRate = 100;

        // init cache
        this.cachedRates = new FxRates();
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
        cachedRates = null;
        if (compositeDisposable != null) compositeDisposable.clear();
        compositeDisposable = null;
    }

    @Override
    public void init() {
        // get default EUR rates when app initializes
        getLatestFxRates("EUR");
    }

    @Override
    public void getLatestFxRates(String base) {
        compositeDisposable.clear();
        compositeDisposable.add(dataRepository.getLatestFxRates(base)
                .subscribeOn(schedulersProvider.io())
                .repeatWhen(completed -> completed.delay(1, TimeUnit.SECONDS))
                .filter(rates -> !isUpdating)
                .observeOn(schedulersProvider.ui())
                .subscribe(newRates -> {

                    // set updating list status to true
                    setUpdateStatus(true);

                    // update view with new rates
                    view.showNewRates(newRates, cachedRates, false);

                }, throwable -> view.showError()));

    }

    @Override
    public void onBindRecyclerRowView(int position, MainContract.RecyclerRowView rowView) {

        // null check
        if (cachedRates == null || cachedRates.getRates() == null) return;

        // get data from list
        double rate = cachedRates.getRates().get(position).getRate();
        String name = cachedRates.getRates().get(position).getName();

        // set rate
        rowView.setRate(String.valueOf(position == 0 ? baseRate : baseRate * rate));

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

            // first item (position=0) will only update on
            // first data load at the empty payload scenario
            if (data.containsKey("RATE") && (boolean) data.get("RATE") && position > 0) {
                // get data from list
                double rate = cachedRates.getRates().get(position).getRate();

                // set rate
                rowView.setRate(String.valueOf(baseRate * rate));
            }
        }
    }

    @Override
    public void setUpdateStatus(boolean updating) {
        isUpdating = updating;
    }

    @Override
    public int getCurrenciesCount() {
        if (cachedRates == null || cachedRates.getRates() == null) return 0;
        return cachedRates.getRates().size();
    }

    @Override
    public void setNewBaseRateFromUserInput(String newRate) {
        if (newRate == null || newRate.length() == 0) {
            baseRate = 1;
            return;
        }

        try {
            baseRate = Double.parseDouble(newRate);
        } catch (Exception e) {
            e.printStackTrace();
            baseRate = 1;
        }

        cachedRates.getRates().get(0).setRate(baseRate);
    }

    @Override
    public void switchBaseCurrency(int position) {

        // null check
        if (cachedRates == null || cachedRates.getRates() == null) return;

        // set updating list status to true
        setUpdateStatus(true);

        // scroll list back to the top
        view.scrollBackToTop();

        // get selected new base currency
        Rate selected = cachedRates.getRates().get(position);

        // maintain rate shown on screen
        selected.setRate(selected.getRate() * baseRate);

        // reorder list
        FxRates rates = new FxRates();
        rates.setBase(selected.getName());
        rates.setDate(cachedRates.getDate());
        rates.getRates().clear();
        rates.getRates().addAll(cachedRates.getRates());
        rates.getRates().remove(selected);
        rates.getRates().add(0, selected);

        // update new base rate
        baseRate = selected.getRate();

        // show new list order
        view.showNewRates(rates, cachedRates, false);

        // get latest fx rates for new base currency
        getLatestFxRates(selected.getName());
    }
}
