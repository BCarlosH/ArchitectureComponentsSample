package com.example.bcarlosh.architecturecomponentssample.data.db

import androidx.room.TypeConverter
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object TagListConverter {

    @TypeConverter
    @JvmStatic
    fun restoreTagList(listOfString: String): List<Tag> {
        return Gson().fromJson(listOfString, object : TypeToken<List<Tag>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun saveTagList(listOfString: List<Tag>): String {
        return Gson().toJson(listOfString)
    }

}