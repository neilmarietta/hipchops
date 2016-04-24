package com.neilmarietta.hipchops;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.presentation.view.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.neilmarietta.hipchops.matcher.RecyclerViewMatcher.atPosition;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fabsDisplayed() {
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_send)).check(matches(isDisplayed()));
    }

    @Test
    public void addMessageFromInput() {
        String text = "(test)";

        // Type text and then press the button.
        onView(withId(R.id.input_edit_text))
                .perform(typeText(text), closeSoftKeyboard());
        onView(withId(R.id.fab_send)).perform(click());

        onView(withId(R.id.rv_messages))
                .perform(scrollToPosition(0))
                .check(matches(atPosition(0, hasDescendant(withText(text)))));
    }

    @Test
    public void addMessageFromTestCases() {
        TestCases.resetCurrentIndex();
        onView(withId(R.id.fab_add)).perform(click());

        TestCases.resetCurrentIndex();
        onView(withId(R.id.rv_messages))
                .perform(scrollToPosition(0))
                .check(matches(atPosition(0, hasDescendant(withText(TestCases.getNextInput())))));
    }
}
