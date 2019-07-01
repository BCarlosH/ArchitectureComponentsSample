package com.example.bcarlosh.architecturecomponentssample.data.entity.artist


import com.example.bcarlosh.architecturecomponentssample.data.entity.OpensearchQuery
import com.google.gson.annotations.SerializedName

data class ArtistSearchResults(
    val artistmatches: Artistmatches,
    @SerializedName("opensearch:itemsPerPage")
    val opensearchItemsPerPage: String,
    @SerializedName("opensearch:Query")
    val opensearchQuery: OpensearchQuery,
    @SerializedName("opensearch:startIndex")
    val opensearchStartIndex: String,
    @SerializedName("opensearch:totalResults")
    val opensearchTotalResults: String
)