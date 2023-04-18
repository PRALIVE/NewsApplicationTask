package com.example.newsapplication.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapplication.models.NewsResponse

@Database(entities = [NewsResponse::class], version = 1, exportSchema = false)
@TypeConverters(SourceTypeConverter::class, arrayconvertor::class)
abstract class myroomDatabase : RoomDatabase(){
    abstract fun roomDao() : RoomDao
}