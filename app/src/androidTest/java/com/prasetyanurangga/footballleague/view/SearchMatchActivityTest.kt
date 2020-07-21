package com.prasetyanurangga.footballleague.view

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.prasetyanurangga.footballleague.R
import com.prasetyanurangga.footballleague.ui.view.SearchMatchActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchMatchActivityTest {


    @Rule
    @JvmField
    var activityRule = ActivityTestRule(SearchMatchActivity::class.java)

    @Test
    fun testAppBehaviour() {
        onView(withId(R.id.appSearchBar))
            .check(matches(isDisplayed()))

        onView(withId(R.id.appSearchBar))
            .perform(typeText("arse"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER) )

        Thread.sleep(5000)

        onView(withId(R.id.list_event_search))
            .check(matches(isDisplayed()))
    }
}


