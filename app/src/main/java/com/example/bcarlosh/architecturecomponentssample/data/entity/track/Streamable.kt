package com.example.bcarlosh.architecturecomponentssample.data.entity.track


import com.google.gson.annotations.SerializedName

data class Streamable(
    val fulltrack: String,
    @SerializedName("#text")
    val text: String
)