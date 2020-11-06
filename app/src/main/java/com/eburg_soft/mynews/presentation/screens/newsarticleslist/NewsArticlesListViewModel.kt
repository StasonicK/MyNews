package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eburg_soft.mynews.core.PAGE_SIZE
import com.eburg_soft.mynews.data.repository.Repository
import com.eburg_soft.mynews.presentation.models.NewsArticleUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsArticlesListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var mNewsList: LiveData<PagedList<NewsArticleUi>>? = null

    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = isLoadingMutableLiveData

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        getTopHeadlinesInTheUs(config)
    }

    fun getNewsList(): LiveData<PagedList<NewsArticleUi>>? = mNewsList

    private fun getTopHeadlinesInTheUs(config: PagedList.Config) {
        viewModelScope.launch {
            // show progressbar
            isLoadingMutableLiveData.value = true

            val dataSourceFactory = object : DataSource.Factory<Int, NewsArticleUi>() {
                override fun create(): DataSource<Int, NewsArticleUi> {
                    return NewsArticlesPositionalDataSource(repository)
                }
            }
            mNewsList = LivePagedListBuilder(dataSourceFactory, config).build()

            // show progressbar during 500 milliseconds
            delay(500)
            // hide progressbar
            isLoadingMutableLiveData.value = false
        }
    }
}