package com.example.bcarlosh.architecturecomponentssample.data.network.response

import com.example.bcarlosh.architecturecomponentssample.data.entity.artist.ArtistSearchResults
import com.google.gson.annotations.SerializedName


data class ArtistSearchResponse(
    @SerializedName("results")
    val results: ArtistSearchResults
)