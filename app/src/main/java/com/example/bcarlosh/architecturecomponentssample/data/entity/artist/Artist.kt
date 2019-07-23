package com.example.bcarlosh.architecturecomponentssample.data.entity.artist

import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.google.gson.annotations.SerializedName


data class Artist(
    @SerializedName("image")
    val imageList: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
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