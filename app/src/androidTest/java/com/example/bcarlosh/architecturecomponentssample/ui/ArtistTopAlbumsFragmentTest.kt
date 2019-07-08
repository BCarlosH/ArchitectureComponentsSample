package com.example.bcarlosh.architecturecomponentssample.ui

import android.util.Log
import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher.Companion.withRecyclerView
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ArtistTopAlbumsFragmentTest {

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

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes del"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(2000)

        onView(RecyclerViewMatcher.withRecyclerView(R.id.artist_search_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(2000)

    }

}