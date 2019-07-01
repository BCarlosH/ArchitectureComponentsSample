package com.example.bcarlosh.architecturecomponentssample

import android.util.Log
import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.bcarlosh.architecturecomponentssample.instrumentationtestutils.RecyclerViewMatcher.Companion.withRecyclerView
import com.example.bcarlosh.architecturecomponentssample.ui.MainActivity
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ArtistSearchFragmentTest {

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
    fun checkSearchErrorResult() {

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes delfffffgggggppppp"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(2000)

        onView(withId(R.id.error_view_textView))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.no_artists_on_search)))

    }

    @Test
    fun checkSearchSuccessResult() {

        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("Heroes del"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(2000)

        onView(withRecyclerView(R.id.artist_search_recycler_view).atPosition(0))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText("Héroes del Silencio"))))

        onView(withId(R.id.artist_search_recycler_view))
            .check(matches(isDisplayed()))
            .perform(swipeUp())

    }

}