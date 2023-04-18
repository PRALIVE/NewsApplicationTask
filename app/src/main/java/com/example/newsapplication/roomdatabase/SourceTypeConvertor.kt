package com.example.newsapplication.roomdatabase

import androidx.room.TypeConverter
import com.example.newsapplication.models.Source
import com.google.gson.Gson

class SourceTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun sourceToString(source: Source?): String? {
        return gson.toJson(source)
    }

    @TypeConverter
    fun stringToSource(sourceString: String?): Source? {
        return gson.fromJson(sourceString, Source::class.java)
    }
}