package com.eburg_soft.mynews.presentation.detailednewsarticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eburg_soft.mynews.presentation.models.NewsArticleUI

class CustomViewModelFactory(private val newsArticleUI: NewsArticleUI) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailedNewsArticleViewModel(newsArticleUI) as T
    }
}