package com.eburg_soft.mynews.presentation.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eburg_soft.mynews.core.PAGE_SIZE
import com.eburg_soft.mynews.data.repository.NewsRepository
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private var _newsList = MutableLiveData<PagedList<NewsArticleUI>>()
    var newsList: LiveData<PagedList<NewsArticleUI>> = LiveData<PagedList<NewsArticleUI>>()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        getTopHeadlinesInTheUs(config)
    }

    private fun getTopHeadlinesInTheUs(config: PagedList.Config) {
        viewModelScope.launch {
            val dataSourceFactory = object : DataSource.Factory<Int, NewsArticleUI>() {
                override fun create(): DataSource<Int, NewsArticleUI> {
                    return NewsDataSource(repository)
                }
            }
            newsList = LivePagedListBuilder(dataSourceFactory, config).build()
        }
    }
}