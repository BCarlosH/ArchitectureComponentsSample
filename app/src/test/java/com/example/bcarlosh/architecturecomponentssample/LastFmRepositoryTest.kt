package com.example.bcarlosh.architecturecomponentssample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bcarlosh.architecturecomponentssample.base.BaseUT
import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.Tags
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.Tracks
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.di.configureAppComponent
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection


class LastFmRepositoryTest : BaseUT() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val lastFmRepository by inject<LastFmRepository>()


    override fun isMockServerEnabled() = true

    @Before
    override fun setUp() {
        super.setUp()

        startKoin {
            androidContext(mock())
            modules(configureAppComponent(getMockUrl()))
        }
    }


    @Test
    fun `it should have success finding user by name`() {
        mockHttpResponse("artist_search.json", HttpURLConnection.HTTP_OK)

        runBlocking {

            /* When */
            val value = lastFmRepository.getArtistSearchByName("Heroes del")

            /* Then */
            value.results.artistmatches.let { result ->
                assertEquals(30, result.artist.size)
                assertEquals("Héroes del Silencio", result.artist[0].name)
                assertEquals("265968", result.artist[0].listeners)
                assertEquals(
                    "https://lastfm-img2.akamaized.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
                    result.artist[0].image
                )
            }

        }
    }

    @Test
    fun `it should have success returning top albums from artist`() {
        mockHttpResponse("artist_top_albums.json", HttpURLConnection.HTTP_OK)

        runBlocking {

            /* When */
            val value = lastFmRepository.getArtistTopAlbums("Heroes del Silencio")

            /* Then */
            value.topalbums.let { result ->
                assertEquals(50, result.album.size)
                assertEquals("Senderos De Traición", result.album[0].name)
                assertEquals("Héroes del Silencio", result.album[0].artist.name)
                assertEquals(1068013, result.album[0].playcount)
                assertEquals(
                    "https://lastfm-img2.akamaized.net/i/u/174s/f5f80c8d6aee45da83bcfc3d8b8477b1.png",
                    result.album[0].image
                )
            }
        }
    }

    @Ignore
    @Test
    fun `it should have success returning album info from network`() {
        mockHttpResponse("artist_top_albums.json", HttpURLConnection.HTTP_OK)

        runBlocking {

            /* When */
            val value = lastFmRepository.getAlbumInfo("Heroes del Silencio", "Senderos De Traición")

            /* Then */
            value.album.let { result ->
                assertEquals("Héroes del Silencio", result.artist)
                assertEquals("Senderos De Traición", result.name)
                assertEquals(12, result.tracks.track.size)
                assertEquals("Entre Dos Tierras", result.tracks.track[0].name)
                assertEquals("365", result.tracks.track[0].duration)
                assertEquals(
                    "https://lastfm-img2.akamaized.net/i/u/300x300/f5f80c8d6aee45da83bcfc3d8b8477b1.png",
                    result.image
                )
            }
        }
    }

    @Ignore
    @Test
    fun `it should store an album in database`() {
        val album = AlbumInfo(
            "Heroes del Silencio",
            listOf(
                Image(
                    "small",
                    "https://lastfm-img2.akamaized.net/i/u/34s/f5f80c8d6aee45da83bcfc3d8b8477b1.png"
                ),
                Image(
                    "medium",
                    "https://lastfm-img2.akamaized.net/i/u/64s/f5f80c8d6aee45da83bcfc3d8b8477b1.png"
                ),
                Image(
                    "large",
                    "https://lastfm-img2.akamaized.net/i/u/174s/f5f80c8d6aee45da83bcfc3d8b8477b1.png"
                ),
                Image(
                    "extralarge",
                    "https://lastfm-img2.akamaized.net/i/u/300x300/f5f80c8d6aee45da83bcfc3d8b8477b1.png"
                ),
                Image(
                    "mega",
                    "https://lastfm-img2.akamaized.net/i/u/300x300/f5f80c8d6aee45da83bcfc3d8b8477b1.png"
                )
            ),
            "100019",
            "Senderos De Traición",
            "1068013",
            Tags(listOf()),
            Tracks(listOf()),
            ""
        )
    }

    @Ignore
    @Test
    fun `is album stored success`() = runBlocking {
        /* Given */
        val lastFmRepository = mock<LastFmRepository> {
            onBlocking { isAlbumStored("FAKE") } doReturn true
        }

        /* When */
        val result = lastFmRepository.isAlbumStored("FAKE")

        /* Then */
        assertEquals(true, result)
    }

}