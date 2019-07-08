package com.example.bcarlosh.architecturecomponentssample.data.network

import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface LastFmApiService {

    @GET("?method=artist.search")
    fun searchArtistByNameAsync(
        @Query("artist") artist: String
    ): Deferred<Response<ArtistSearchResponse>>

    @GET("?method=artist.getTopAlbums")
    fun getArtistTopAlbumsAsync(
        @Query("artist") artist: String
    ): Deferred<Response<TopAlbumsResponse>>

    @GET("?method=album.getinfo")
    fun getAlbumInfoAsync(
        @Query("artist") artist: String,
        @Query("album") album: String
    ): Deferred<Response<AlbumInfoResponse>>

}