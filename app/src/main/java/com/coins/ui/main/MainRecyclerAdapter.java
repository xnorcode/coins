package com.coins.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.coins.R;
import com.coins.data.FxRates;
import com.coins.utils.Constants;
import com.coins.utils.schedulers.BaseSchedulersProvider;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
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
     * Composite Disposable for Observables Management
     */
    private CompositeDisposable mCompositeDisposable;


    @Inject
    public MainRecyclerAdapter(MainContract.Presenter presenter, BaseSchedulersProvider schedulersProvider) {
        this.mPresenter = presenter;
        this.mSchedulersProvider = schedulersProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }


    /**
     * Release memory refs.
     */
    public void destroy() {
        mPresenter = null;
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
        mCompositeDisposable = null;
        mSchedulersProvider = null;
    }


    /**
     * Update recycler view adapter items
     */
    public void updateItems(FxRates newRates, FxRates oldRates, boolean updatingStatus) {

        Disposable disposable = Flowable.<DiffUtil.DiffResult>create(emitter -> {

            // pass items to check for diffs
            // set detect item moves to true
            DiffUtil.DiffResult diffResult = DiffUtil
                    .calculateDiff(new FxRatesDiffCallback(oldRates, newRates), true);

            // update main rates cache list
            oldRates.getRates().clear();
            oldRates.getRates().addAll(newRates.getRates());
            oldRates.setBase(newRates.getBase());
            oldRates.setDate(newRates.getDate());

            // proceed and pass diff results
            emitter.onNext(diffResult);
            emitter.onComplete();

        }, BackpressureStrategy.ERROR)
                .subscribeOn(mSchedulersProvider.io())
                .observeOn(mSchedulersProvider.ui())
                .subscribe(diffResult -> {

                            // show items from update cache
                            diffResult.dispatchUpdatesTo(this);

                            // set presenter updating status to false
                            mPresenter.setUpdateStatus(updatingStatus);
                        },
                        throwable -> mPresenter.setUpdateStatus(false));
        mCompositeDisposable.add(disposable);
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
                vh.mName.setOnClickListener(v -> mPresenter.switchBaseCurrency(vh.getAdapterPosition()));
                break;

            case Constants.ITEM_WITH_TEXT_WATCHER:

                // set edit text change listener to update base rate
                Disposable disText = RxTextView.textChangeEvents(vh.mRate)
                        .debounce(200, TimeUnit.MILLISECONDS)
                        .subscribeOn(mSchedulersProvider.io())
                        .observeOn(mSchedulersProvider.ui())
                        .subscribe(rate -> mPresenter.setNewBaseRateFromUserInput(rate.text().toString()),
                                throwable -> mPresenter.setNewBaseRateFromUserInput(null));
                mCompositeDisposable.add(disText);
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
    public void onBindViewHolder(@NonNull MainRecyclerAdapterViewHolder holder, int position, @NonNull List<Object> payloads) {
        // pass current position, row view and change payloads into presenter
        mPresenter.onBindRecyclerRowView(position, holder, payloads);
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
