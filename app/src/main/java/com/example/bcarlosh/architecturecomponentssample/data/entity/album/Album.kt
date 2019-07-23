package com.example.bcarlosh.architecturecomponentssample.data.entity.album

import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.example.bcarlosh.architecturecomponentssample.data.entity.artist.Artist
import com.google.gson.annotations.SerializedName


data class Album(
    val artist: Artist,
    @SerializedName("image")
    val imageList: List<Image>,
    val name: String,
    val playcount: Int,
    val url: String
) {

    val image: String
        get() {

            return if (imageList.isNotEmpty()) {
                val foundImage = imageList.find {
                    Image.ImageSizeEnum.LARGE.size == it.size
                }
                return foundImage?.text ?: ""

            } else {
                ""
            }

        }

}