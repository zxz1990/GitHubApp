package com.mcdull.githubapp

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setUp() {
        Log.d(TAG, "setUp() called")
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test1() {
        Log.i(TAG, "test1: started")
        onView(withId(R.id.navigation_search)).perform(click())
        onView(ViewMatchers.withText("搜索"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    fun test2() {
        Log.i(TAG, "test2: started")
        onView(withId(R.id.navigation_search)).perform(click())
        onView(withId(R.id.languageSpinner)).perform(click())
        onView(ViewMatchers.withText("Java"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @After
    fun reset() {
        Log.d(TAG, "reset() called")
    }

    companion object {
        private const val TAG = "ExampleEspressoTest"

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            Log.d(TAG, "beforeClass() called")
            ActivityScenario.launch(MainActivity::class.java)
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            Log.d(TAG, "afterClass() called")
        }
    }
}