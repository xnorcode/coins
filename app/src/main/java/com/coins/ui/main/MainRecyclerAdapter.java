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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xnorcode on 11/09/2018.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

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


    private FxRates mRates;


    public void swapData(FxRates rates) {
        this.mRates = rates;
        notifyDataSetChanged();
    }


    public void destroy() {
        mRates = null;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mRates == null || mRates.getRates() == null) return;

        // get data
        String name = mRates.getRates().get(position).getName();

        // set icon
        int drawableId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(!name.equals("TRY") ? name.toLowerCase() : "turkey", "drawable", "com.coins");
        Picasso.get().load(drawableId).into(holder.mIcon);

        // set name
        holder.mName.setText(name);

        // set description
        int descId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(name, "string", "com.coins");
        holder.mDescription.setText(holder.itemView.getContext().getResources().getString(descId));

        // set rate
        holder.mRate.setText(String.valueOf(mRates.getRates().get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        if (mRates == null || mRates.getRates() == null || mRates.getRates().size() == 0) return 0;
        return mRates.getRates().size();
    }
}
