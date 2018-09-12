package com.coins.utils.schedulers;

import io.reactivex.Scheduler;

/**
 * Created by xnorcode on 11/09/2018.
 */
public interface BaseSchedulersProvider {

    /**
     * Retrieve background thread schedulers
     *
     * @return Background thread schedulers
     */
    Scheduler io();


    /**
     * Retrieve main thread schedulers
     *
     * @return Main thread schedulers
     */
    Scheduler ui();
}
