package com.coins.ui.main;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.coins.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by xnorcode on 16/09/2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void showAndEditRatesList() {

        // verify list is shown
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

        // make sure we are at the top of the list
        onView(withId(R.id.recyclerView)).perform(swipeDown());

        // verify items are shown
        onView(withText("EUR")).check(matches(isDisplayed()));
        onView(withText("USD")).check(matches(isDisplayed()));

        // scroll to bottom of the list
        onView(withId(R.id.recyclerView)).perform(swipeUp());

        // click on HUF item to swap currency
        onView(withText("HUF")).perform(click());

        // make sure we are at the top of the list
        onView(withId(R.id.recyclerView)).perform(swipeDown());

        // verify HUF is at the top fo the list
        onView(withText("HUF")).check(matches(isDisplayed()));
    }
}
