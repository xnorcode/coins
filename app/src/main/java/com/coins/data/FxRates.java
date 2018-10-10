package com.coins.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xnorcode on 07/09/2018.
 */
public class FxRates {

    private String base;

    private String date;

    private List<Rate> rates;

    public FxRates() {
        rates = new ArrayList<>();
    }

    public FxRates(String base, String date, List<Rate> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
