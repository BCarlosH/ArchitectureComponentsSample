package com.example.bcarlosh.architecturecomponentssample.data.repository

import com.example.bcarlosh.architecturecomponentssample.data.db.AlbumDao
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSource
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse


class LastFmRepositoryImpl(
    private val albumDao: AlbumDao,
    private val lastFmNetworkDataSource: LastFmNetworkDataSource
) : LastFmRepository {


    override suspend fun getArtistSearchByName(artist: String): ArtistSearchResponse {
        return lastFmNetworkDataSource.fetchArtistSearchByName(artist)
    }

    override suspend fun getArtistTopAlbums(artist: String): TopAlbumsResponse {
        return lastFmNetworkDataSource.fetchArtistTopAlbums(artist)
    }

    override suspend fun getAlbumInfo(artist: String, album: String): AlbumInfoResponse {
        val albumInfo = albumDao.getStoredAlbumByName(album)

        return if (albumInfo != null) {
            AlbumInfoResponse(albumInfo)
        } else {
            lastFmNetworkDataSource.fetchAlbumInfo(artist, album)
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