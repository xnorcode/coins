package com.coins.ui.main;

import android.os.Bundle;

import com.coins.R;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // remove activity's background theme
        getWindow().setBackgroundDrawable(null);

        // create and attach fragment to activity
        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

}
