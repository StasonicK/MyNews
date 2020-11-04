package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.data.datasource.network.NewsApi
import com.eburg_soft.mynews.presentation.mappers.NewsArticleResponseToUiMapper
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    private val newsApi: NewsApi,
    private val newsArticleResponseToUiMapper: NewsArticleResponseToUiMapper
) : NewsRepository {

    // fetch from API news and map data for UI
    override suspend fun getTopHeadlinesInTheUsForUI(): List<NewsArticleUI> {
        val response = newsApi.getTopHeadlinesInTheUs()
        val result: ArrayList<NewsArticleUI> = arrayListOf()
        response.let { response1 ->
            response1?.articleResponses.let {
                it?.forEach { newsArticleResponse ->
                    result.add(newsArticleResponseToUiMapper.map(newsArticleResponse))
                }
            }
        }
        return result
    }
}