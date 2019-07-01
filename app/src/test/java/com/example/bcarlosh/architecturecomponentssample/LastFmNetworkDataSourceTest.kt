package com.example.bcarlosh.architecturecomponentssample

import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSource
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class LastFmNetworkDataSourceTest {


    lateinit var lastFmNetworkDataSource: LastFmNetworkDataSource
    private val artistSearchResponse = mock<CallResult<ArtistSearchResponse>>()
    private val topAlbumsResponse = mock<CallResult<TopAlbumsResponse>>()
    private val albumInfoResponse = mock<CallResult<AlbumInfoResponse>>()

    @Before
    fun before() {

        lastFmNetworkDataSource = mock {
            onBlocking { fetchArtistSearchByName("artistName") } doReturn artistSearchResponse
            onBlocking { fetchArtistTopAlbums("artistName") } doReturn topAlbumsResponse
            onBlocking { fetchAlbumInfo("artistName", "albumName") } doReturn albumInfoResponse
        }
    }

    @After
    fun after() {
    }


    @Test
    fun `calls fetchArtistSearchByName on NetworkDataSource`() {
        runBlocking {
            lastFmNetworkDataSource.fetchArtistSearchByName("artistName")
        }
        verifyBlocking(lastFmNetworkDataSource) {
            fetchArtistSearchByName("artistName")
        }
    }

    @Test
    fun `calls fetchArtistTopAlbums on NetworkDataSource`() {
        runBlocking {
            lastFmNetworkDataSource.fetchArtistTopAlbums("artistName")
        }
        verifyBlocking(lastFmNetworkDataSource) {
            fetchArtistTopAlbums("artistName")
        }
    }

    @Test
    fun `calls fetchAlbumInfo on NetworkDataSource`() {
        runBlocking {
            lastFmNetworkDataSource.fetchAlbumInfo("artistName", "albumName")
        }
        verifyBlocking(lastFmNetworkDataSource) {
            fetchAlbumInfo("artistName", "albumName")
        }
    }

}