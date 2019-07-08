package com.example.bcarlosh.architecturecomponentssample.base

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bcarlosh.architecturecomponentssample.di.configureAppComponent
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import java.io.InputStream


abstract class BaseIT : BaseTest() {


    @Before
    override fun setUp() {
        super.setUp()
        safeInitCustomDependencies()
    }

    private fun safeInitCustomDependencies() {
        try {
            getKoin()
            loadKoinModules(configureAppComponent(getMockUrl()))
        } catch (e: IllegalStateException) {
            Log.e("safeInitCustomDependencies", "Koin not started")
            startKoin {
                androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
                loadKoinModules(configureAppComponent(getMockUrl()))
            }
        }
    }

    fun mockHttpResponse(fileName: String, responseCode: Int) {
        val json = getJson(fileName)
        return mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(json)
        )
    }

    private fun getJson(path: String): String {
        val inputStream: InputStream = InstrumentationRegistry.getInstrumentation().context.resources.assets.open(path)
        return inputStream.bufferedReader().use { it.readText() }
    }

}