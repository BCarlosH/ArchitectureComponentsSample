package com.example.bcarlosh.architecturecomponentssample.ui

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.base.BaseIT
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher.Companion.withRecyclerView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistSearchFragmentTest : BaseIT() {


    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    override fun setUp() {
        super.setUp()
        activityRule.launchActivity(null)
    }

    override fun isMockServerEnabled() = true


    @Test
    fun checkSearchSuccessResult() {
        mockHttpResponse("artist_search.json", HttpURLConnection.HTTP_OK)

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes del"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(500)

        onView(withRecyclerView(R.id.artist_search_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText("Héroes del Silencio"))))

        onView(withId(R.id.artist_search_recycler_view))
            .check(matches(isDisplayed()))
            .perform(swipeUp())
    }

    @Test
    fun checkSearchErrorResult() {
        mockHttpResponse("artist_search_empty.json", HttpURLConnection.HTTP_OK)

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes delfffffgggggppppp"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(500)

        onView(withId(R.id.error_view_textView))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.no_artists_on_search)))
    }

}