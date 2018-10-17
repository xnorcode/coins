package com.coins.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coins.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by xnorcode on 10/09/2018.
 */
public class MainFragment extends DaggerFragment implements MainContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    MainRecyclerAdapter mAdapter;

    @Inject
    MainContract.Presenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        // set layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // set adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);

        // bind this view to presenter
        mPresenter.setView(this);

        // initialize presenter
        mPresenter.init();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) mPresenter.dropView();
        mPresenter = null;

        mRecyclerView = null;

        if (mAdapter != null) mAdapter.destroy();
        mAdapter = null;
    }

    @Override
    public void showNewRates(DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(mAdapter);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_msg), Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        // used when manual injection of presenter
    }
}
