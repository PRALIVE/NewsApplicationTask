package com.example.newsapplication.savedroomdatabase

import androidx.room.*
import com.example.newsapplication.models.Article

@Dao
interface SavedDao {
    @Query("SELECT * FROM Article")
    suspend fun getArticles() : List<Article>

    @Query("DELETE FROM ARTICLE WHERE primaryKey = :userID")
    abstract fun deleteByUserId(userID: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(data: Article)

    @Delete
    suspend fun delete(article:Article)
}