package com.example.arnold.takehome;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.arnold.takehome.testingUtils.RecyclerViewMatcher;
import com.example.arnold.takehome.views.UI.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by ArnoldGoncharenko on 2018-06-06.
 */
@RunWith(AndroidJUnit4.class)
public class UITestSuite {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void searchElementsPresent() {
        //Check if the proper UI appeared
        onView(withId(R.id.edt_user)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_search)).check(matches(isDisplayed()));
    }

    @Test
    public void searchFunctional() {
        //Perform type to begin searching
        onView(withId(R.id.edt_user)).perform(clearText(), typeText("octocat"), closeSoftKeyboard());
        onView(withId(R.id.btn_search)).perform(click());

        //check if all the basic elements are present
        onView(withId(R.id.userAvatar)).check(matches(isDisplayed()));
        onView(withId(R.id.lblUsername)).check(matches(isDisplayed()));
        onView(withText("The Octocat")).check(matches(isDisplayed()));
        onView(withId(R.id.repo_list)).check(matches(isDisplayed()));

        //check if the repo list populated correctly
        onView(withRecyclerView(R.id.repo_list).atPosition(0))
            .check(matches(hasDescendant(withText("boysenberry-repo-1"))));
        onView(withRecyclerView(R.id.repo_list).atPosition(0))
                .check(matches(hasDescendant(withText("Testing"))));
    }

    @Test
    public void repoListClickFunctional() {
        //Perform type to begin searching
        onView(withId(R.id.edt_user)).perform(clearText(), typeText("octocat"), closeSoftKeyboard());
        onView(withId(R.id.btn_search)).perform(click());

        //Click on one of the items in the recycler view
        onView(withId(R.id.repo_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if the initial layers are visible.
        onView(withId(R.id.lblLastUpdated)).check(matches(isDisplayed()));
        onView(withId(R.id.lblStars)).check(matches(isDisplayed()));
        onView(withId(R.id.lblForks)).check(matches(isDisplayed()));

        //Check if the values appear and the data is correct
        onView(allOf(withId(R.id.lblLastUpdatedValue), withText("2018-05-10T17:51:31Z"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.lblStarsValue), withText("0"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.lblForksValue), withText("1"))).check(matches(isDisplayed()));

        //Check if the onclick for the translucent layer functions
        onView(withId(R.id.translucentLayerView)).perform(click());

        //Check another recycler view action just to confirm data has changed
        onView(withId(R.id.repo_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        //Check if the initial layers are visible.
        onView(withId(R.id.lblLastUpdated)).check(matches(isDisplayed()));
        onView(withId(R.id.lblStars)).check(matches(isDisplayed()));
        onView(withId(R.id.lblForks)).check(matches(isDisplayed()));

        //Check if the values appear and the data is correct
        onView(allOf(withId(R.id.lblLastUpdatedValue), withText("2018-05-15T16:40:18Z"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.lblStarsValue), withText("15"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.lblForksValue), withText("41"))).check(matches(isDisplayed()));

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
