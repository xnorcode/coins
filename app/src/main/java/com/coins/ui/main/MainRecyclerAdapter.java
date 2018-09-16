package com.coins.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coins.R;
import com.coins.data.FxRates;
import com.coins.utils.schedulers.BaseSchedulersProvider;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {


    /**
     * Custom ViewHolder for our list items
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_icon)
        ImageView mIcon;

        @BindView(R.id.list_item_name)
        TextView mName;

        @BindView(R.id.list_item_description)
        TextView mDescription;

        @BindView(R.id.list_item_rate)
        EditText mRate;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * List of updated rates
     */
    private FxRates mRates;


    /**
     * Initial base rate value
     */
    private double mBaseRate;


    /**
     * Flag to notify adapter that base currency has changed
     */
    private boolean mBaseCurrencyChanged;


    /**
     * Schedulers Provider
     */
    private BaseSchedulersProvider mSchedulersProvider;


    /**
     * Ref to the view
     */
    private MainContract.View mView;


    /**
     * Disposable for Base Rate Input Monitoring
     */
    private Disposable mDisposable;


    /**
     * Constructor
     *
     * @param schedulersProvider Scheduler Provider
     */
    @Inject
    public MainRecyclerAdapter(BaseSchedulersProvider schedulersProvider) {

        //set schedulers provider
        this.mSchedulersProvider = schedulersProvider;

        // set initial base rate
        this.mBaseRate = 100;

        // set update base currency flag
        this.mBaseCurrencyChanged = true;
    }


    /**
     * Get view ref.
     *
     * @param mView Reference to view
     */
    public void setView(MainContract.View mView) {
        this.mView = mView;
    }


    /**
     * Update adapter data
     *
     * @param rates updated rates object
     */
    public void swapData(FxRates rates) {
        // update rates list with updated rates
        this.mRates = rates;

        // set base rate value to the list
        mRates.getRates().get(0).setRate(mBaseRate);

        // update all only on each new base currency
        if (mBaseCurrencyChanged) {

            // reset flag
            mBaseCurrencyChanged = false;

            // update entire data set
            notifyDataSetChanged();

            return;
        }

        // update all except first item in list
        notifyItemRangeChanged(1, getItemCount() - 1);
    }


    /**
     * Release memory refs.
     */
    public void destroy() {
        mRates = null;
        mView = null;
        if (mDisposable != null) mDisposable.dispose();
        mDisposable = null;
        mSchedulersProvider = null;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // list null checks
        if (mRates == null || mRates.getRates() == null) return;

        // get current item currency name
        String name = mRates.getRates().get(position).getName();

        // set icon
        int drawableId = holder.itemView.getContext()
                .getResources()
                // named the TRY icon resource as "turkey" to avoid system resource conflicts
                .getIdentifier(!name.equals("TRY") ? name.toLowerCase() : "turkey", "drawable", "com.coins");
        Picasso.get().load(drawableId).into(holder.mIcon);

        // set name
        holder.mName.setText(name);

        // set description
        int descId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(name, "string", "com.coins");
        holder.mDescription.setText(holder.itemView.getContext().getResources().getString(descId));


        // set rate value
        if (position == 0) {

            // set base value
            holder.mRate.setText(String.valueOf(mBaseRate));

            // set edit text change listener to update rates in list
            mDisposable = RxTextView.textChangeEvents(holder.mRate)
                    .filter(changes -> changes != null && !changes.text().toString().isEmpty())
                    .debounce(200, TimeUnit.MILLISECONDS)
                    .subscribeOn(mSchedulersProvider.io())
                    .observeOn(mSchedulersProvider.ui())
                    .subscribe(rate -> {

                        // get edited base rate
                        mBaseRate = Double.parseDouble(rate.text().toString());

                        // update all other items based on new base rate
                        notifyItemRangeChanged(1, getItemCount() - 1);
                    });
        } else {

            // set rate for currencies
            holder.mRate.setText(String.valueOf(mBaseRate * mRates.getRates().get(position).getRate()));

            // set current currency as a base currency
            holder.itemView.setOnClickListener(v -> {

                // null check
                if (mView == null) return;

                // request rates with new base currency
                mView.refreshRates(name);

                // notify adapter base currency changed
                mBaseCurrencyChanged = true;
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mRates == null || mRates.getRates() == null || mRates.getRates().size() == 0) return 0;
        return mRates.getRates().size();
    }
}
