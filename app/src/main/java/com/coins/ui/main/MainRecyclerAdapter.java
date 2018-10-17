package com.coins.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.coins.R;
import com.coins.utils.Constants;
import com.coins.utils.schedulers.BaseSchedulersProvider;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapterViewHolder> {


    /**
     * Ref to Main Activity Presenter
     */
    private MainContract.Presenter mPresenter;


    /**
     * Schedulers Provider
     */
    private BaseSchedulersProvider mSchedulersProvider;


    /**
     * Disposable for Base Rate Input Monitoring
     */
    private Disposable mDisposable;


    @Inject
    public MainRecyclerAdapter(MainContract.Presenter presenter, BaseSchedulersProvider schedulersProvider) {
        this.mPresenter = presenter;
        this.mSchedulersProvider = schedulersProvider;
    }


    /**
     * Release memory refs.
     */
    public void destroy() {
        mPresenter = null;
        if (mDisposable != null) mDisposable.dispose();
        mDisposable = null;
        mSchedulersProvider = null;
    }


    @NonNull
    @Override
    public MainRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create new custom view holder (for each adapter item)
        MainRecyclerAdapterViewHolder vh = new MainRecyclerAdapterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item, parent, false));

        // check item type
        switch (viewType) {
            case Constants.ITEM_WITH_CLICK:

                // set onclick listener to change base currencies
                vh.itemView.setOnClickListener(v -> mPresenter.switchBaseCurrency(vh.getAdapterPosition()));
                break;

            case Constants.ITEM_WITH_TEXT_WATCHER:

                // set edit text change listener to update base rate
                mDisposable = RxTextView.textChangeEvents(vh.mRate)
                        .debounce(200, TimeUnit.MILLISECONDS)
                        .subscribeOn(mSchedulersProvider.io())
                        .observeOn(mSchedulersProvider.ui())
                        .subscribe(rate -> mPresenter.setNewBaseRateFromUserInput(rate.text().toString()),
                                throwable -> mPresenter.setNewBaseRateFromUserInput(null));
                break;
        }

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapterViewHolder holder, int position) {
        // pass current position and row view into presenter
        mPresenter.onBindRecyclerRowView(position, holder);
    }


    @Override
    public int getItemCount() {
        return mPresenter.getCurrenciesCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constants.ITEM_WITH_TEXT_WATCHER : Constants.ITEM_WITH_CLICK;
    }
}
