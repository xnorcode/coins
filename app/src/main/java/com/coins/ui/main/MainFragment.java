package com.coins.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coins.R;
import com.coins.data.FxRates;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by xnorcode on 10/09/2018.
 */
public class MainFragment extends DaggerFragment implements MainContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    MainRecyclerAdapter adapter;

    @Inject
    MainContract.Presenter presenter;

    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        // create layout manager
        linearLayoutManager = new LinearLayoutManager(getContext());

        // set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);

        // set adapter to recycler view
        recyclerView.setAdapter(adapter);

        // bind this view to presenter
        presenter.setView(this);

        // initialize presenter
        presenter.init();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (presenter != null) presenter.dropView();
        presenter = null;

        recyclerView = null;

        if (adapter != null) adapter.destroy();
        adapter = null;

        linearLayoutManager = null;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        // used when manual injection of presenter
    }

    @Override
    public void showNewRates(FxRates newRates, FxRates oldRates, boolean presenterStatus) {
        adapter.updateItems(newRates, oldRates, presenterStatus);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_msg), Toast.LENGTH_LONG).show();
    }

    @Override
    public void scrollBackToTop() {

        // create smooth scroller
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        // set position to scroll to
        smoothScroller.setTargetPosition(0);

        // start scrolling
        linearLayoutManager.startSmoothScroll(smoothScroller);
    }
}
