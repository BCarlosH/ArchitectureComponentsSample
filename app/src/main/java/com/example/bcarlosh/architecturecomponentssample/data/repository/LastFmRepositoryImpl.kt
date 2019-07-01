package com.example.bcarlosh.architecturecomponentssample.data.repository

import com.example.bcarlosh.architecturecomponentssample.data.db.AlbumDao
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSource
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import com.example.bcarlosh.architecturecomponentssample.helpers.safeApiCall


class LastFmRepositoryImpl(
    private val albumDao: AlbumDao,
    private val lastFmNetworkDataSource: LastFmNetworkDataSource
) : LastFmRepository {


    override suspend fun getArtistSearchByName(artist: String): CallResult<ArtistSearchResponse> = safeApiCall(
        call = { lastFmNetworkDataSource.fetchArtistSearchByName(artist) },
        errorMessage = "An error occurred"
    )

    override suspend fun getArtistTopAlbums(artist: String): CallResult<TopAlbumsResponse> = safeApiCall(
        call = { lastFmNetworkDataSource.fetchArtistTopAlbums(artist) },
        errorMessage = "An error occurred"
    )

    override suspend fun getAlbumInfo(artist: String, album: String): CallResult<AlbumInfoResponse> {
        val albumInfo = albumDao.getStoredAlbumByName(album)

        return if (albumInfo != null) {
            CallResult.Success(AlbumInfoResponse(albumInfo))
        } else {
            safeApiCall(
                call = { lastFmNetworkDataSource.fetchAlbumInfo(artist, album) },
                errorMessage = "An error occurred"
            )
        }

    }

    override suspend fun storeAlbum(album: AlbumInfo) {
        albumDao.insert(album)
    }

    override suspend fun getStoredAlbums(): List<AlbumInfo> {
        return albumDao.getStoredAlbums()
    }

    override suspend fun deleteStoredAlbum(albumName: String) {
        albumDao.deleteStoredAlbum(albumName)
    }

    override suspend fun isAlbumStored(albumName: String): Boolean {
        return albumDao.countAlbum(albumName) >= 1
    }

}