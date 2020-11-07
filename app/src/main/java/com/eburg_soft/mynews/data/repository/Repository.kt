package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel

interface Repository {

    suspend fun getNewsArticlesFromApi(pageNumber: Int = 1): Pair<ArrayList<NewsArticleUiModel>, Int>
}