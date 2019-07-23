package com.example.bcarlosh.architecturecomponentssample.data.entity.album

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.google.gson.annotations.SerializedName


@Entity(tableName = "albums")
data class AlbumInfo(
    val artist: String,
    @SerializedName("image")
    val imageList: List<Image>,
    val listeners: String,
    @PrimaryKey
    val name: String,
    val playcount: String,
    @Embedded(prefix = "tags_")
    val tags: Tags,
    @Embedded(prefix = "tracks_")
    val tracks: Tracks,
    val url: String
) {

    val image: String
        get() {

            return if (imageList.isNotEmpty()) {
                val foundImage = imageList.find {
                    Image.ImageSizeEnum.EXTRALARGE.size == it.size
                }
                return foundImage?.text ?: ""

            } else {
                ""
            }

        }

}