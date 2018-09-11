package com.coins.ui.base;

/**
 * Created by xnorcode on 11/09/2018.
 */
public interface BasePresenter<T> {


    /**
     * Vind view to presenter
     *
     * @param view The current view
     */
    void setView(T view);


    /**
     * Removes view
     */
    void dropView();
}
