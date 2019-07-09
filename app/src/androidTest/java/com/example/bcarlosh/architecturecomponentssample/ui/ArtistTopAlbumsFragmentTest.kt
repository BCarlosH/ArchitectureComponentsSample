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
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher.Companion.withRecyclerView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistTopAlbumsFragmentTest : BaseIT() {


    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    override fun setUp() {
        super.setUp()
        activityRule.launchActivity(null)
    }

    override fun isMockServerEnabled() = true


    @Test
    fun checkTopAlbumList() {

        navigateToTopAlbum()

        onView(withRecyclerView(R.id.top_albums_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText("Senderos De Traición"))))

        onView(withId(R.id.top_albums_recycler_view))
            .check(matches(isDisplayed()))
            .perform(swipeDown())

    }

    private fun navigateToTopAlbum() {
        mockHttpResponse("artist_search.json", HttpURLConnection.HTTP_OK)

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes del"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(500)

        mockHttpResponse("artist_top_albums.json", HttpURLConnection.HTTP_OK)

        onView(RecyclerViewMatcher.withRecyclerView(R.id.artist_search_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(500)

    }

}