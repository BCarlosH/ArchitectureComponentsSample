package com.example.bcarlosh.architecturecomponentssample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo


@Database(
    entities = [AlbumInfo::class],
    version = 1
)
@TypeConverters(ImageListConverter::class, TagListConverter::class, TrackListConverter::class)
abstract class LastFmDataBase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

}