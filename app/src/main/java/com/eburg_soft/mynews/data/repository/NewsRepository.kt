package com.eburg_soft.mynews.data.repository

import com.eburg_soft.mynews.presentation.models.NewsArticleUI

interface NewsRepository {

     suspend fun getTopHeadlinesInTheUsForUI(pageNumber:Int=1): Pair<ArrayList<NewsArticleUI>,Int>
}