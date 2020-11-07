package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.data.datasource.network.apis.NewsApi
import com.eburg_soft.mynews.presentation.mappers.NewsArticleResponseToUiMapper
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : Repository {

    private val newsArticleResponseToUiMapper = NewsArticleResponseToUiMapper()

    // fetch news from API  and map data for UI
    override suspend fun getNewsArticlesFromApi(pageNumber: Int): Pair<ArrayList<NewsArticleUiModel>, Int> {
        val response = newsApi.getNewsArticlesFromApi(pageNumber = pageNumber)
        val size = response?.totalResults ?: 0
        val list: ArrayList<NewsArticleUiModel> = arrayListOf()
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