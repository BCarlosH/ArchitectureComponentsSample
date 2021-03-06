package com.example.bcarlosh.architecturecomponentssample.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.base.BaseIT
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class StoredAlbumListFragmentTest : BaseIT() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    override fun setUp() {
        super.setUp()
        activityRule.launchActivity(null)
    }

    override fun isMockServerEnabled() = false


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