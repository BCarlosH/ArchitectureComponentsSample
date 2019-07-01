package com.example.bcarlosh.architecturecomponentssample.data.entity.album

import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.example.bcarlosh.architecturecomponentssample.data.entity.artist.Artist


data class Album(
    val artist: Artist,
    val image: List<Image>,
    val name: String,
    val playcount: Int,
    val url: String
)