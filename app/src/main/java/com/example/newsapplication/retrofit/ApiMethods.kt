package com.example.newsapplication.retrofit

import com.example.newsapplication.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMethods {
    @GET("/v2/everything?q=tesla&from=2023-03-15&sortBy=publishedAt&apiKey=4d5281ab72264bc093cf29cef19f3de4")
    suspend fun getNews(@Query("page") page : Int,@Query("pageSize") pageSize : Int ) : NewsResponse
}