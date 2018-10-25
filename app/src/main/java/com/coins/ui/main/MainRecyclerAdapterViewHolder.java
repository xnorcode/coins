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
    ImageView icon;

    @BindView(R.id.list_item_name)
    TextView name;

    @BindView(R.id.list_item_description)
    TextView description;

    @BindView(R.id.list_item_rate)
    EditText rate;

    private String packageName;

    private Resources resources;


    public MainRecyclerAdapterViewHolder(View itemView) {
        super(itemView);

        // Inject Views
        ButterKnife.bind(this, itemView);

        // Get package name
        packageName = itemView.getContext().getApplicationContext().getPackageName();

        // Get ref to resources
        resources = itemView.getResources();
    }

    @Override
    public void setIcon(String iconName) {
        Picasso.get()
                .load(resources.getIdentifier(iconName, "drawable", packageName))
                .into(icon);
    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setDescription(String name) {
        // get the resource id
        int resourceId = resources.getIdentifier(name, "string", packageName);

        // get the text from the string resource
        String description = resources.getString(resourceId);

        // set description
        this.description.setText(description);
    }

    @Override
    public void setRate(String rate) {
        this.rate.setText(rate);
    }
}
