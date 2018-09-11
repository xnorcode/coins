package com.coins.ui.base;

/**
 * Created by xnorcode on 11/09/2018.
 */
public interface BaseView<T> {


    /**
     * Pass presenter ref. to view
     *
     * @param presenter The view presenter
     */
    void setPresenter(T presenter);
}
