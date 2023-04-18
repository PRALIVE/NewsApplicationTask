package com.example.newsapplication.savedroomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapplication.models.Article
import com.example.newsapplication.roomdatabase.SourceTypeConverter

@Database(entities = [Article::class], version = 8, exportSchema = false)
@TypeConverters(SourceTypeConverter::class)
abstract class SavedDatabase : RoomDatabase() {
    abstract fun savedDao() : SavedDao
}