package com.example.bcarlosh.architecturecomponentssample.data.network

import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import java.io.IOException


class LastFmNetworkDataSourceImpl(
    private val lastFmApiService: LastFmApiService
) : LastFmNetworkDataSource {


    override suspend fun fetchArtistSearchByName(artist: String): CallResult<ArtistSearchResponse> {
        val response = lastFmApiService.searchArtistByNameAsync(artist).await()

        return if (response.isSuccessful) {
            CallResult.Success(response.body()!!)
        } else {
            CallResult.Error(IOException("Error occurred during fetching artist search"))
        }
    }

    override suspend fun fetchArtistTopAlbums(artist: String): CallResult<TopAlbumsResponse> {
        val response = lastFmApiService.getArtistTopAlbumsAsync(artist).await()

        return if (response.isSuccessful) {
            CallResult.Success(response.body()!!)
        } else {
            CallResult.Error(IOException("Error occurred during fetching top albums"))
        }
    }

    override suspend fun fetchAlbumInfo(artist: String, album: String): CallResult<AlbumInfoResponse> {
        val response = lastFmApiService.getAlbumInfoAsync(artist, album).await()

        return if (response.isSuccessful) {
            CallResult.Success(response.body()!!)
        } else {
            CallResult.Error(IOException("Error occurred during fetching album info"))
        }
    }

}