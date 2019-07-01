package com.example.bcarlosh.architecturecomponentssample.data.network.response

import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.google.gson.annotations.SerializedName


data class AlbumInfoResponse(
    @SerializedName("album")
    val album: AlbumInfo
)