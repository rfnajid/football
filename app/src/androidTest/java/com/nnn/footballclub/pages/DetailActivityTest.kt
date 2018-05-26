package com.nnn.footballclub.pages

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nnn.footballclub.R
import com.nnn.footballclub.R.id.*
import com.nnn.footballclub.model.Event
import com.nnn.footballclub.model.Team
import com.nnn.footballclub.pages.detail.DetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(DetailActivity::class.java,false,false)

    @Test
    fun testView(){
        val intent = Intent()
        val event = mockEvent()
        intent.putExtra("extra", event)
        activityRule.launchActivity(intent)

        onView(withId(textDate)).check(matches(isDisplayed()))
        onView(withId(textTeamHome)).check(matches(isDisplayed()))
        onView(withId(textTeamAway)).check(matches(isDisplayed()))

        onView(withId(R.id.menu_favorite)).perform(click())
    }


    fun mockEvent():Event{
        return Event(
                1,
                "Arsenal VS MU",
                123,
                234,
                Team(123,"Arsenal",""),
                Team(234,"Manchester United",""),
                1,
                2,
                 Date(),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "","","",3,3)
    }

}