package com.example.bcarlosh.architecturecomponentssample.data.entity

import com.google.gson.annotations.SerializedName


data class Attr(
    @SerializedName("for")
    val forX: String,
    val artist: String,
    val page: String,
    val perPage: String,
    val total: String,
    val totalPages: String,
    val rank: String
)