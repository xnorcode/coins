package com.coins.ui.main;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.coins.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

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

        // verify items are shown
        onView(withText("EUR")).check(matches(isDisplayed()));
        onView(withText("USD")).check(matches(isDisplayed()));

        // change rate input
        onView(allOf(withClassName(endsWith("EditText")), withText(is("100.0")))).perform(replaceText("1"));

        // 1sec delay to load changes
        Thread.sleep(1000);

        // verify rate changed to USD currency
        onView(withText("1.16")).check(matches(isDisplayed()));
    }
}
