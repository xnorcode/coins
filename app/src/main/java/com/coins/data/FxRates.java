package com.coins.data;

import java.util.Map;

/**
 * Created by xnorcode on 07/09/2018.
 */
public class FxRates {

    //@SerializedName("base")
    private String base;

    //@SerializedName("date")
    private String date;

    private Map<String, Double> rates;

    public FxRates(String base, String date, Map<String, Double> rates) {
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

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
