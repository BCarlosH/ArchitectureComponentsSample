package com.example.bcarlosh.architecturecomponentssample.data.network

import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import com.example.bcarlosh.architecturecomponentssample.ui.error.ErrorType
import java.io.IOException


class LastFmNetworkDataSourceImpl(
    private val lastFmApiService: LastFmApiService
) : LastFmNetworkDataSource {


    override suspend fun fetchArtistSearchByName(artist: String): ArtistSearchResponse {
        val response = lastFmApiService.searchArtistByNameAsync(artist).await()

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw IOException(ErrorType.FETCHING_ARTIST_SEARCH.name)
        }
    }

    override suspend fun fetchArtistTopAlbums(artist: String): TopAlbumsResponse {
        val response = lastFmApiService.getArtistTopAlbumsAsync(artist).await()

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw IOException(ErrorType.FETCHING_TOP_ALBUMS.name)
        }
    }

    override suspend fun fetchAlbumInfo(artist: String, album: String): AlbumInfoResponse {
        val response = lastFmApiService.getAlbumInfoAsync(artist, album).await()

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw IOException(ErrorType.FETCHING_ALBUM_INFO.name)
        }
    }

}