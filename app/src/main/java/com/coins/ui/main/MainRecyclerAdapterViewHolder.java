package com.coins.ui.main;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coins.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xnorcode on 26/09/2018.
 */
public class MainRecyclerAdapterViewHolder extends RecyclerView.ViewHolder implements MainContract.RecyclerRowView {

    @BindView(R.id.list_item_icon)
    ImageView mIcon;

    @BindView(R.id.list_item_name)
    TextView mName;

    @BindView(R.id.list_item_description)
    TextView mDescription;

    @BindView(R.id.list_item_rate)
    EditText mRate;

    private String mPackageName;

    private Resources mResources;


    public MainRecyclerAdapterViewHolder(View itemView) {
        super(itemView);

        // Inject Views
        ButterKnife.bind(this, itemView);

        // Get package name
        mPackageName = itemView.getContext().getApplicationContext().getPackageName();

        // Get ref to resources
        mResources = itemView.getResources();
    }

    @Override
    public void setIcon(String iconName) {
        Picasso.get()
                .load(mResources.getIdentifier(iconName, "drawable", mPackageName))
                .into(mIcon);
    }

    @Override
    public void setName(String name) {
        mName.setText(name);
    }

    @Override
    public void setDescription(String name) {
        // get the resource id
        int resourceId = mResources.getIdentifier(name, "string", mPackageName);

        // get the text from the string resource
        String description = mResources.getString(resourceId);

        // set description
        mDescription.setText(description);
    }

    @Override
    public void setRate(String rate) {
        mRate.setText(rate);
    }
}
