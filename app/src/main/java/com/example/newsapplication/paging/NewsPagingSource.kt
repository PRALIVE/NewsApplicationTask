package com.example.newsapplication.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapplication.NewsApplication
import com.example.newsapplication.models.Article
import com.example.newsapplication.models.NewsResponse
import com.example.newsapplication.retrofit.ApiMethods
import com.example.newsapplication.roomdatabase.RoomDao

class NewsPagingSource(val newsapi: ApiMethods, val dao: RoomDao) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            var response: NewsResponse? = null
            val currentPage = params.key ?: 1
            for (newsResponse in dao.getAll()) {
                if (newsResponse.page == currentPage) {
                    response = newsResponse
                    break
                }
            }
            response?.let {
                Log.d("page source","from database")
                LoadResult.Page(
                    data = response.articles,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if ((currentPage*10) == response.totalResults) null else currentPage + 1,
                    )
            }?:run{
                Log.d("page source","from the API")
                val newsData=newsapi.getNews(currentPage,10)
               for(article in newsData.articles)
               {
                   article.primaryKey= NewsApplication.count++

               }
                newsData.page=currentPage
                dao.insertAll(newsData)
                LoadResult.Page(
                    data = newsData.articles,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if ((currentPage*10) == newsData.totalResults) null else currentPage + 1,
                    )
            }



        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}