package com.example.assaply.data.domain.remote
import com.example.assaply.data.domain.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.assaply.util.Constants.API_KEY

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}