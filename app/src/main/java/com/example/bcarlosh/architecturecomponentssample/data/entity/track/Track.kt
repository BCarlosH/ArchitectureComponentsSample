package com.example.bcarlosh.architecturecomponentssample.data.entity.track

import com.example.bcarlosh.architecturecomponentssample.data.entity.artist.Artist


data class Track(
    val artist: Artist,
    val duration: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)