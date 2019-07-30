package com.example.bcarlosh.architecturecomponentssample.data.network

import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse


interface LastFmNetworkDataSource {

    suspend fun fetchArtistSearchByName(artist: String): ArtistSearchResponse

    suspend fun fetchArtistTopAlbums(artist: String): TopAlbumsResponse

    suspend fun fetchAlbumInfo(artist: String, album: String): AlbumInfoResponse

}