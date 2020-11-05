package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.data.datasource.network.NewsApi
import com.eburg_soft.mynews.presentation.mappers.NewsArticleResponseToUiMapper
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

    private val newsArticleResponseToUiMapper = NewsArticleResponseToUiMapper()

    // fetch news from API  and map data for UI
    override suspend fun getTopHeadlinesInTheUsForUI(pageNumber: Int): Pair<ArrayList<NewsArticleUI>, Int> {
        val response = newsApi.getTopHeadlinesInTheUs(pageNumber = pageNumber)
        val size = response?.totalResults ?: 0
        val list: ArrayList<NewsArticleUI> = arrayListOf()
        response.let { response1 ->
            response1?.articleResponses.let {
                it?.forEach { newsArticleResponse ->

                    list.add(newsArticleResponseToUiMapper.map(newsArticleResponse))
                }
            }
        }
        return list to size
    }
}