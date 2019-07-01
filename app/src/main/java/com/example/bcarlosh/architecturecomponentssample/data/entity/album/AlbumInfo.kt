package com.example.bcarlosh.architecturecomponentssample.data.entity.album

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bcarlosh.architecturecomponentssample.data.entity.Image


@Entity(tableName = "albums")
data class AlbumInfo(
    val artist: String,
    val image: List<Image>,
    val listeners: String,
    @PrimaryKey
    val name: String,
    val playcount: String,
    @Embedded(prefix = "tags_")
    val tags: Tags,
    @Embedded(prefix = "tracks_")
    val tracks: Tracks,
    val url: String
)