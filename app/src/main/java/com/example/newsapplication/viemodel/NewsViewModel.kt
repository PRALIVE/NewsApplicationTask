package com.example.newsapplication.viemodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.room.Room
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.retrofit.ApiMethods
import com.example.newsapplication.roomdatabase.myroomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(val newsApi: ApiMethods, @ApplicationContext val context: Context) : ViewModel() {
    val db = Room.databaseBuilder(
        context,
        myroomDatabase::class.java,"myArticles"
    ).fallbackToDestructiveMigration().build()
    val repository= NewsRepository(newsApi,db.roomDao())
    val list = repository.getNews().cachedIn(viewModelScope)

}