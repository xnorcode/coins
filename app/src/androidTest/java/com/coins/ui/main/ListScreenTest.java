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
    public void showAndEditRatesList() throws InterruptedException {

        // verify list is shown
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

        // 2sec delay for UI to load
        Thread.sleep(2000);

        // make sure we are at the top of the list
        onView(withId(R.id.recyclerView)).perform(swipeDown());

        // 0.5sec delay for scroll
        Thread.sleep(500);

        // verify items are shown
        onView(withText("EUR")).check(matches(isDisplayed()));
        onView(withText("USD")).check(matches(isDisplayed()));

        // scroll to bottom of the list
        onView(withId(R.id.recyclerView)).perform(swipeUp());

        // 2sec delay to load changes
        Thread.sleep(2000);

        // click on HUF item to swap currency
        onView(withText("HUF")).perform(click());

        // 2sec delay to load changes
        Thread.sleep(2000);

        // make sure we are at the top of the list
        onView(withId(R.id.recyclerView)).perform(swipeDown());

        // 2sec delay to load changes
        Thread.sleep(2000);

        // verify HUF is at the top fo the list
        onView(withText("HUF")).check(matches(isDisplayed()));
    }
}
