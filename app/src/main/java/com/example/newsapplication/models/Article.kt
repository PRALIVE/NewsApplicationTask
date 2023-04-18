package com.example.newsapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity()
data class Article(
    @PrimaryKey var primaryKey : Int,
    @ColumnInfo(name = "id")var id: String? = null,
    @ColumnInfo(name = "source")var source: Source? = null,
    @ColumnInfo(name = "author")var author: String? = null,
    @ColumnInfo(name = "title")var title: String? = null,
    @ColumnInfo(name = "description")var description: String? = null,
    @ColumnInfo(name = "url")var url: String? = null,
    @ColumnInfo(name = "urlToImage")var urlToImage: String? = null,
    @ColumnInfo(name = "publishedAt")var publishedAt: String? = null,
    @ColumnInfo(name = "content")var content: String? = null
)