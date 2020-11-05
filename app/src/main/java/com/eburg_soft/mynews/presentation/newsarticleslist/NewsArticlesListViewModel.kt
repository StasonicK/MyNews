package com.eburg_soft.mynews.presentation.newsarticleslist

import androidx.lifecycle.LiveData
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

class NewsArticlesListViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private var newsList: LiveData<PagedList<NewsArticleUI>>? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        getTopHeadlinesInTheUs(config)
    }

    fun getNewsList(): LiveData<PagedList<NewsArticleUI>>? = newsList

    private fun getTopHeadlinesInTheUs(config: PagedList.Config) {
        viewModelScope.launch {
            val dataSourceFactory = object : DataSource.Factory<Int, NewsArticleUI>() {
                override fun create(): DataSource<Int, NewsArticleUI> {
                    return NewsArticlesPositionalDataSource(repository)
                }
            }
            newsList = LivePagedListBuilder(dataSourceFactory, config).build()
        }
    }
}