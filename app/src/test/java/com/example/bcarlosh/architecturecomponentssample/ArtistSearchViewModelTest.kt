package com.example.bcarlosh.architecturecomponentssample

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bcarlosh.architecturecomponentssample.base.BaseUT
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.di.configureAppComponent
import com.example.bcarlosh.architecturecomponentssample.ui.artistsearch.ArtistSearchViewModel
import com.example.bcarlosh.architecturecomponentssample.utils.observeOnce
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.inject
import org.mockito.Mockito.mock
import java.net.HttpURLConnection


class ArtistSearchViewModelTest : BaseUT() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val artistSearchViewModel by inject<ArtistSearchViewModel>()


    override fun isMockServerEnabled() = true

    @Before
    override fun setUp() {
        super.setUp()

        startKoin {
            androidContext(mock(Context::class.java))
            modules(configureAppComponent(getMockUrl()))
        }
    }


    @Test
    fun `it should have success searching user by name`() {
        /* Given */
        mockHttpResponse("artist_search.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            artistSearchViewModel.searchArtist("Héroes del Silencio").join()
        }

        /* When */
        val resultList = mutableListOf<ArtistSearchResponse>()
        artistSearchViewModel.artistSearchResponse.observeOnce {
            resultList.add(it)
        }

        /* Then */
        assertTrue(resultList.size > 0)
        assertEquals(30, resultList[0].results.artistmatches.artist.size)
        assertEquals("Héroes del Silencio", resultList[0].results.artistmatches.artist[0].name)
        assertEquals("265968", resultList[0].results.artistmatches.artist[0].listeners)
        assertEquals(
            "https://lastfm-img2.akamaized.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
            resultList[0].results.artistmatches.artist[0].image
        )
    }

    @Test
    fun `it should have failure searching user by name`() {
        /* Given */
        mockHttpResponse("artist_search.json", HttpURLConnection.HTTP_INTERNAL_ERROR)
        runBlocking {
            artistSearchViewModel.searchArtist("Héroes del Silencio").join()
        }

        /* When */
        val resultList = mutableListOf<String>()
        artistSearchViewModel.error.observeOnce {
            resultList.add(it)
        }

        /* Then */
        assertTrue(resultList.size > 0)
        assertEquals("Error occurred during fetching artist search", resultList[0])
    }

}