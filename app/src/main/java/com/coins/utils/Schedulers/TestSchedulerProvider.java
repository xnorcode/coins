package com.coins.utils.Schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class TestSchedulerProvider implements BaseSchedulersProvider {

    private static TestSchedulerProvider INSTANCE;

    private TestSchedulerProvider() {
    }

    public static synchronized TestSchedulerProvider getInstance() {
        if (INSTANCE == null) INSTANCE = new TestSchedulerProvider();
        return INSTANCE;
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
