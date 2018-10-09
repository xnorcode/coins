package com.coins.data;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class Rate {

    private String name;

    private double rate;

    public Rate(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) rate;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Rate rate = (Rate) obj;

        return rate.getName().equals(this.name)
                && rate.getRate() == this.rate;
    }
}
