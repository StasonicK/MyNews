package com.eburg_soft.mynews.data.datasource.network

import com.eburg_soft.mynews.core.NEWS_API_KEY
import com.eburg_soft.mynews.data.datasource.network.models.NewsMainResponse
import retrofit2.http.GET

/**
 * Describes endpoints to fetch the news from NewsAPI.
 *
 * Read the documentation [here](https://newsapi.org/docs/v2)
 */
interface NewsApi {

    /**
     * Get top headlines.
     *
     * See [article documentation](https://newsapi.org/docs/endpoints/top-headlines).
     */
    @GET("top-headlines?country=us&apiKey=${NEWS_API_KEY}y")
    suspend fun getTopHeadlinesInTheUs(): NewsMainResponse?
}