package com.eburg_soft.mynews.data.datasource.network.apis

import com.eburg_soft.mynews.core.NEWS_API_KEY
import com.eburg_soft.mynews.core.PAGE_SIZE
import com.eburg_soft.mynews.data.datasource.network.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Describes endpoints to fetch the news from "News API".
 *
 * Read the documentation [here](https://newsapi.org/docs/v2)
 */
interface NewsApi : BaseApi {

    /**
     * Get top headlines.
     *
     * See [article documentation](https://newsapi.org/docs/endpoints/top-headlines).
     * Sample: https://newsapi.org/v2/top-headlines?country=us&apiKey=1f9f0f56fac04cc886b6e6a2f1ed8e0e&pageSize=20&page=2
     */
    @GET("v2/top-headlines?country=us&apiKey=${NEWS_API_KEY}")
    suspend fun getNewsArticlesFromApi(
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("page") pageNumber: Int = 1
    ): NewsResponse?
}