package com.example.bcarlosh.architecturecomponentssample

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.bcarlosh.architecturecomponentssample.ui.MainActivity
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StoredAlbumListFragmentTest {

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
    fun checkInfoMessage() {
        onView(withId(R.id.initial_textView))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.no_albums_stored_yet)))
    }

    @Test
    fun checkInitialSearchButton() {
        onView(withId(R.id.search_imageView))
            .check(matches(isDisplayed()))
            .perform(click())
    }

}