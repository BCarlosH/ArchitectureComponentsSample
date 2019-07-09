package com.example.bcarlosh.architecturecomponentssample.ui

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.base.BaseIT
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher.Companion.withRecyclerView
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.withCollapsibleToolbarTitle
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.withDrawable
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
class AlbumDetailFragmentTest : BaseIT() {


    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    override fun setUp() {
        super.setUp()
        activityRule.launchActivity(null)
    }

    override fun isMockServerEnabled() = true


    @Test
    fun checkAlbumDetail() {

        navigateToAlbumDetail()

        checkDetailData()

        checkStoreFab()

        pressBack()
        pressBack()
        pressBack()

        checkStoredAlbum()

    }

    private fun navigateToAlbumDetail() {
        mockHttpResponse("artist_search.json", HttpURLConnection.HTTP_OK)

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes del"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(500)

        mockHttpResponse("artist_top_albums.json", HttpURLConnection.HTTP_OK)

        onView(withRecyclerView(R.id.artist_search_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(500)

        mockHttpResponse("album_info.json", HttpURLConnection.HTTP_OK)

        onView(withRecyclerView(R.id.top_albums_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(500)

    }

    private fun checkDetailData() {

        onView(isAssignableFrom(CollapsingToolbarLayout::class.java))
            .check(
                matches(
                    withCollapsibleToolbarTitle(
                        `is`("Senderos De Traición")
                    )
                )
            )

        onView(withRecyclerView(R.id.album_detail_tracks_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText("Entre Dos Tierras"))))
            .check(matches(hasDescendant(withText("06:05"))))

    }

    private fun checkStoreFab() {

        onView(withId(R.id.store_delete_album_fab))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.album_stored)))

        onView(withId(R.id.store_delete_album_fab))
            .check(matches(isDisplayed()))
            .check(matches(withDrawable(R.drawable.ic_delete_store_album)))

    }

    private fun checkDeleteFab() {

        onView(withId(R.id.store_delete_album_fab))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.album_deleted)))

        onView(withId(R.id.store_delete_album_fab))
            .check(matches(isDisplayed()))
            .check(matches(withDrawable(R.drawable.ic_store_delete_album)))

    }

    private fun checkStoredAlbum() {

        onView(withRecyclerView(R.id.stored_albums_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText("Senderos De Traición"))))

        onView(withRecyclerView(R.id.stored_albums_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .perform(click())

        checkDetailData()

        checkDeleteFab()

        pressBack()

        onView(withId(R.id.initial_textView))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.no_albums_stored_yet)))

    }

    private fun pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }

}