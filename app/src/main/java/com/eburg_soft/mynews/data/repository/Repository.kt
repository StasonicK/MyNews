package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.presentation.models.IpUi
import com.eburg_soft.mynews.presentation.models.NewsArticleUi

interface NewsRepository {

    suspend fun getTopHeadlinesInTheUsForUI(pageNumber: Int = 1): Pair<ArrayList<NewsArticleUi>, Int>

    suspend fun getIpDetails(ip: String): IpUi
}