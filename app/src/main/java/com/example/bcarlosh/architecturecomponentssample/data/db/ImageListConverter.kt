package com.example.bcarlosh.architecturecomponentssample.data.db

import androidx.room.TypeConverter
import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object ImageListConverter {

    @TypeConverter
    @JvmStatic
    fun restoreImageList(listOfString: String): List<Image> {
        return Gson().fromJson(listOfString, object : TypeToken<List<Image>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun saveImageList(listOfString: List<Image>): String {
        return Gson().toJson(listOfString)
    }

}