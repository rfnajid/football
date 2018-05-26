package com.nnn.footballclub.pages

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nnn.footballclub.R
import com.nnn.footballclub.R.id.*
import com.nnn.footballclub.pages.main.ListFragment
import com.nnn.footballclub.pages.main.ListPresenter
import com.nnn.footballclub.pages.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init(){
        val fragment = ListFragment.create(ListPresenter.TYPE.PAST)
        activityRule.activity.supportFragmentManager.beginTransaction().add(R.id.frame, fragment).commit()
    }

    @Test
    fun testNavigation(){
        onView(withId(navigation)).check(matches(isDisplayed()))
        onView(withId(navigation_favorite)).perform(click())
        onView(withId(navigation_next)).perform(click())
        onView(withId(navigation_past)).perform(click())
    }
}