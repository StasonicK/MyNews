package com.eburg_soft.mynews.presentation.detailednewsarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eburg_soft.mynews.presentation.models.NewsArticleUI

class DetailedNewsArticleViewModel(newsArticleUI: NewsArticleUI) : ViewModel() {

    private var newsArticle: LiveData<NewsArticleUI>? = null

    fun fetchNewsArticle(): LiveData<NewsArticleUI>? = newsArticle
}