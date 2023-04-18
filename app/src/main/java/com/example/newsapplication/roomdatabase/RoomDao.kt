package com.example.newsapplication.roomdatabase

import androidx.room.*
import com.example.newsapplication.models.NewsResponse

@Dao
interface RoomDao {
    @Query("SELECT * FROM NewsResponse")
   suspend fun getAll() : List<NewsResponse>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(newsResponse: NewsResponse)
}