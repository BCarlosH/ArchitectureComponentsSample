package com.example.bcarlosh.architecturecomponentssample.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo


@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: AlbumInfo)

    @Query("select * from albums")
    fun getStoredAlbums(): List<AlbumInfo>

    @Query("select * from albums where name = :albumName")
    fun getStoredAlbumByName(albumName: String): AlbumInfo?

    @Query("delete from albums where name = :albumName")
    fun deleteStoredAlbum(albumName: String)

    @Query("select count() from albums where name = :albumName")
    fun countAlbum(albumName: String): Int

}