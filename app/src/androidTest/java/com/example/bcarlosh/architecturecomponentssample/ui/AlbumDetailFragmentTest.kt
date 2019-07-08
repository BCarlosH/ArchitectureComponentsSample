package com.example.bcarlosh.architecturecomponentssample.ui

import android.util.Log
import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher.Companion.withRecyclerView
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.withCollapsibleToolbarTitle
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.withDrawable
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.hamcrest.CoreMatchers.`is`
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AlbumDetailFragmentTest {


    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, true)


    companion object {

        @BeforeClass
        @JvmStatic
        fun before_class_method() {
            Log.e("@Before Class", "Run before anything")
        }

        @AfterClass
        @JvmStatic
        fun after_class_method() {
            Log.e("@After Class", "Run after everything")
        }

    }


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

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes del"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(2000)

        onView(withRecyclerView(R.id.artist_search_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(2000)

        onView(withRecyclerView(R.id.top_albums_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(2000)

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
            .check(matches(hasDescendant(withText("05:48"))))

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