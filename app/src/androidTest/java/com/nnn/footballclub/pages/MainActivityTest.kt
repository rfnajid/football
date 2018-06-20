package com.nnn.footballclub.pages

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import com.nnn.footballclub.R
import com.nnn.footballclub.R.id.*
import com.nnn.footballclub.pages.main.MainActivity
import com.nnn.footballclub.pages.main.match.MatchListFragment
import com.nnn.footballclub.utils.RecyclerViewItemCountAssertion
import com.nnn.footballclub.utils.TestUtil.Companion.sleepFast
import com.nnn.footballclub.utils.TestUtil.Companion.sleepFlash
import com.nnn.footballclub.utils.TestUtil.Companion.sleepLong
import com.nnn.footballclub.utils.TestUtil.Companion.sleepMedium
import com.nnn.footballclub.utils.TestUtil.Companion.sleepQuiteFast
import com.nnn.footballclub.utils.TestUtil.Companion.sleepQuiteLong
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    lateinit var activity : MainActivity

    private val testedTeam = "Arsenal"
    private val testedLeague = "Spanish La Liga"

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init(){
        activityRule.launchActivity(Intent())
        activity = activityRule.activity as MainActivity
    }

    @Test
    private fun fullBehaviorTest(){
        testTeam()
        testMatch()
        testFavorite()
        testSearch()
    }

    private fun testMatch(){
        onView(withId(navigation_match)).perform(click())
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText(testedLeague)).perform(click())

        sleepQuiteLong()

        onView(withId(recycler))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        pressFavorite()

        sleepFlash()

        Espresso.pressBack()
    }

    private fun testTeam(){
        onView(withId(navigation_team)).perform(click())

        onView(withId(recycler)).check(matches(isDisplayed()))

        sleepQuiteFast()

        onView(withText(testedTeam)).perform(click())

        pressFavorite()

        onView(withText(activity.getString(R.string.tab_player)))
                .check(matches(isDisplayed()))
                .perform(click())

        sleepMedium()

        onView(withId(recycler))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(textDesc))
                .perform(scrollTo())
                .check(matches(isDisplayed()))

        sleepFlash()

        Espresso.pressBack()

        onView(withText(activity.getString(R.string.tab_overview)))
                .check(matches(isDisplayed()))
                .perform(click())

        Espresso.pressBack()

    }

    private fun testFavorite(){
        onView(withId(navigation_favorite)).perform(click())
        testFavoriteTeam()
        testFavoriteMatch()
    }

    private fun testFavoriteMatch(){

        val tabMatch = allOf(withText(activity.getString(R.string.tab_match)),
                isDescendantOfA(withId(tabLayout)))

        onView(tabMatch)
                .check(matches(isDisplayed()))
                .perform(scrollTo())
                .perform(click())

        val presenter = (activity.supportFragmentManager.findFragmentById(frame)
                .childFragmentManager.findFragmentById(frame) as MatchListFragment)
                .listPresenter

        val lastPos = presenter.adapter.itemCount-1

        onView(withId(recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (lastPos, click()))

        sleepFast()

        pressFavorite()

        sleepFlash()

        Espresso.pressBack()

        onView(withId(recycler))
                .check(RecyclerViewItemCountAssertion(lastPos))
    }

    private fun testFavoriteTeam(){

        val tabTeam = allOf(withText(activity.getString(R.string.tab_team)),
                isDescendantOfA(withId(tabLayout)))

        onView(tabTeam)
                .check(matches(isDisplayed()))
                .perform(scrollTo())
                .perform(click())

        onView(withText(testedTeam))
                .perform(click())

        sleepFast()

        onView(withText(activity.getString(R.string.tab_player)))
                .perform(click())

        pressFavorite()

        sleepFlash()

        Espresso.pressBack()

        onView(withText(testedTeam))
                .check(doesNotExist())

    }

    private fun testSearch(){
        testSearchMatch()
        testSearchTeam()
    }

    private fun testSearchMatch(){
        onView(withId(navigation_match)).perform(click())

        onView(withId(menu_search))
                .check(matches(isDisplayed()))
                .perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText(testedTeam))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER))
                .perform(pressKey(KeyEvent.KEYCODE_BACK))

        sleepLong()

        onView(withId(recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, click()))

        pressFavorite()
        sleepFast()
        pressFavorite()

        Espresso.pressBack()
        Espresso.pressBack()
    }

    private fun testSearchTeam(){
        onView(withId(navigation_team)).perform(click())

        onView(withId(menu_search))
                .check(matches(isDisplayed()))
                .perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText(testedTeam))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER))
                .perform(pressKey(KeyEvent.KEYCODE_BACK))

        sleepQuiteFast()

        onView(withId(recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (0, click()))


        pressFavorite()
        sleepFast()
        pressFavorite()

        Espresso.pressBack()
        Espresso.pressBack()
    }

    private fun pressFavorite(){
        onView(withId(menu_favorite))
                .check(matches(isDisplayed()))
                .perform(click())
    }

}