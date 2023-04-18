package com.example.newsapplication.retrofit

import com.example.newsapplication.utils.HelperConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitCall {

    @Singleton
    @Provides
    fun getRetro() : Retrofit{
        return Retrofit.Builder().baseUrl(HelperConstant.base_url).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getNewsApi(retrofit : Retrofit) : ApiMethods{
        return retrofit.create(ApiMethods::class.java)
    }
}