package com.example.newsapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsapplication.paging.NewsPagingSource
import com.example.newsapplication.retrofit.ApiMethods
import com.example.newsapplication.roomdatabase.RoomDao

class NewsRepository (val newsapi : ApiMethods, val db: RoomDao) {

    fun getNews() = Pager(
       config = PagingConfig(pageSize = 1, maxSize = 10),
       pagingSourceFactory = { NewsPagingSource(newsapi,db) }
    ).liveData
}