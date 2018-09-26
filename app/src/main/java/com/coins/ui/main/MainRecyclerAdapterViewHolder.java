package com.coins.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coins.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xnorcode on 26/09/2018.
 */
public class MainRecyclerAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_item_icon)
    ImageView mIcon;

    @BindView(R.id.list_item_name)
    TextView mName;

    @BindView(R.id.list_item_description)
    TextView mDescription;

    @BindView(R.id.list_item_rate)
    EditText mRate;

    public MainRecyclerAdapterViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

}
