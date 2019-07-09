package com.example.bcarlosh.architecturecomponentssample.base

import android.util.Log
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest


abstract class BaseTest : KoinTest {

    protected lateinit var mockServer: MockWebServer


    @Before
    open fun setUp() {
        Log.e("@Before", "Run before anything")
        configureMockServer()
    }

    @After
    open fun tearDown() {
        Log.e("@After", "Run after everything")
        stopMockServer()
        stopKoin()
    }


    //region Mock Server
    /**
     * Because it's not always enabled on all tests
     */
    abstract fun isMockServerEnabled(): Boolean

    private fun configureMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    private fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    fun getMockUrl(): String {
        return if (isMockServerEnabled()) {
            mockServer.url("/").toString()
        } else {
            "http://localhost/"
        }
    }
    //endregion

}