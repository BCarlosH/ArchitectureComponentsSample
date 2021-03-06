package com.example.bcarlosh.architecturecomponentssample.data.entity


import com.google.gson.annotations.SerializedName


data class Image(
    val size: String,
    @SerializedName("#text")
    val text: String
) {

    enum class ImageSizeEnum(val size: String) {
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large"),
        EXTRALARGE("extralarge"),
        MEGA("mega")
    }

}