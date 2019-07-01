package com.example.bcarlosh.architecturecomponentssample.data.db

import androidx.room.TypeConverter
import com.example.bcarlosh.architecturecomponentssample.data.entity.track.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object TrackListConverter {

    @TypeConverter
    @JvmStatic
    fun restoreTrackList(listOfString: String): List<Track> {
        return Gson().fromJson(listOfString, object : TypeToken<List<Track>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun saveTrackList(listOfString: List<Track>): String {
        return Gson().toJson(listOfString)
    }

}