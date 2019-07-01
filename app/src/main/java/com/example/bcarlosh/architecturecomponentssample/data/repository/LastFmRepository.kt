package com.example.bcarlosh.architecturecomponentssample.data.repository

import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse


interface LastFmRepository {

    suspend fun getArtistSearchByName(artist: String): CallResult<ArtistSearchResponse>

    suspend fun getArtistTopAlbums(artist: String): CallResult<TopAlbumsResponse>

    suspend fun getAlbumInfo(artist: String, album: String): CallResult<AlbumInfoResponse>

    suspend fun storeAlbum(album: AlbumInfo)

    suspend fun getStoredAlbums(): List<AlbumInfo>

    suspend fun deleteStoredAlbum(albumName: String)

    suspend fun isAlbumStored(albumName: String): Boolean

}