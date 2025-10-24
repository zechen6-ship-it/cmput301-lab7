package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test 1: Check whether the activity correctly switched
     */
    @Test
    public void testActivitySwitch() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the city in the list
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Verify ShowActivity is displayed by checking city name text view
        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
    }

    /**
     * Test 2: Test whether the city name is consistent
     */
    @Test
    public void testCityNameConsistent() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the city
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Check if text matches the city name passed
        onView(withId(R.id.text_city_name)).check(matches(withText("Edmonton")));
    }

    /**
     * Test 3: Test the "back" button
     */
    @Test
    public void testBackButton() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(androidx.test.espresso.action.ViewActions.typeText("Ottawa"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the city
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Click the back button
        onView(withId(R.id.button_back)).perform(click());

        // Verify that the main activity list view is visible again
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
