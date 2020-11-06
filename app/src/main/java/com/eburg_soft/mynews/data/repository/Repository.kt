package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.presentation.models.IpUi
import com.eburg_soft.mynews.presentation.models.NewsArticleUi

interface Repository {

    suspend fun getNewsArticlesFromApi(pageNumber: Int = 1): Pair<ArrayList<NewsArticleUi>, Int>

    suspend fun getIpDetailsFromApi(ip: String): IpUi
}