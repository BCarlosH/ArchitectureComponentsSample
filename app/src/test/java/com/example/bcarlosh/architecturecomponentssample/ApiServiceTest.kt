package com.example.bcarlosh.architecturecomponentssample

import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmApiService
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.CompletableDeferred
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class ApiServiceTest {

    lateinit var service: LastFmApiService
    private val topAlbumsResponse = mock<Response<TopAlbumsResponse>>()
    private val artistSearchResponse = mock<Response<ArtistSearchResponse>>()
    private val albumInfoResponse = mock<Response<AlbumInfoResponse>>()


    @Before
    fun before() {

        service = mock {
            on { searchArtistByNameAsync("artistName") } doReturn artistSearchResponse.toDeferred()
            on { getArtistTopAlbumsAsync("artistName") } doReturn topAlbumsResponse.toDeferred()
            on { getAlbumInfoAsync("artistName", "AlbumName") } doReturn albumInfoResponse.toDeferred()
        }
    }

    @After
    fun after() {
    }


    @Test
    @Suppress("DeferredResultUnused")
    fun `calls searchArtistByNameAsync on retrofit service`() {
        service.searchArtistByNameAsync("artistName")
        verify(service).searchArtistByNameAsync("artistName")
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `calls getArtistTopAlbumsAsync on retrofit service`() {
        service.getArtistTopAlbumsAsync("artistName")
        verify(service).getArtistTopAlbumsAsync("artistName")
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `calls getAlbumInfoAsync on retrofit service`() {
        service.getAlbumInfoAsync("artistName", "AlbumName")
        verify(service).getAlbumInfoAsync("artistName", "AlbumName")
    }


    companion object {

        /**
         * Source from:
         * https://proandroiddev.com/mocking-coroutines-7024073a8c09
         */
        fun <T> T.toDeferred() = CompletableDeferred(this)
    }

}