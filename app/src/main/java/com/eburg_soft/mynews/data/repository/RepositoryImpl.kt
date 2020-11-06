package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.data.datasource.network.apis.IpApi
import com.eburg_soft.mynews.data.datasource.network.apis.NewsApi
import com.eburg_soft.mynews.presentation.mappers.IpToUiMapper
import com.eburg_soft.mynews.presentation.mappers.NewsArticleResponseToUiMapper
import com.eburg_soft.mynews.presentation.models.IpUi
import com.eburg_soft.mynews.presentation.models.NewsArticleUi
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val ipApi: IpApi
) : NewsRepository {

    private val newsArticleResponseToUiMapper = NewsArticleResponseToUiMapper()
    private val ipToUiMapper = IpToUiMapper()

    // fetch news from API  and map data for UI
    override suspend fun getTopHeadlinesInTheUsForUI(pageNumber: Int): Pair<ArrayList<NewsArticleUi>, Int> {
        val response = newsApi.getTopHeadlinesInTheUs(pageNumber = pageNumber)
        val size = response?.totalResults ?: 0
        val list: ArrayList<NewsArticleUi> = arrayListOf()
        response.let { response1 ->
            response1?.articleResponses.let {
                it?.forEach { newsArticleResponse ->

                    list.add(newsArticleResponseToUiMapper.map(newsArticleResponse))
                }
            }
        }
        return list to size
    }

    override suspend fun getIpDetails(ip: String): IpUi {
        val response = ipApi.getIpDetails(ip)
        return ipToUiMapper.map(response)
    }
}