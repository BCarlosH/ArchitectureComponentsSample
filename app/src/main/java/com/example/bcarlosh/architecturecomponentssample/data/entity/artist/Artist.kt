package com.example.bcarlosh.architecturecomponentssample.data.entity.artist

import com.example.bcarlosh.architecturecomponentssample.data.entity.Image


data class Artist(
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)