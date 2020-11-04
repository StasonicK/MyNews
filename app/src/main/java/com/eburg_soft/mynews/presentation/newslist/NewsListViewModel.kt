package com.eburg_soft.mynews.presentation.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eburg_soft.mynews.data.repository.NewsRepository
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private var _newsList = MutableLiveData<List<NewsArticleUI>>()
    val newsList: LiveData<List<NewsArticleUI>>
        get() = _newsList

    init {
        getTopHeadlinesInTheUs()
    }

    private fun getTopHeadlinesInTheUs() {
        viewModelScope.launch {
            _newsList.value = repository.getTopHeadlinesInTheUsForUI()
        }
    }
}