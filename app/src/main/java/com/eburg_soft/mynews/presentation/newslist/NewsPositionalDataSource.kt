package com.eburg_soft.mynews.presentation.newslist

import androidx.paging.PageKeyedDataSource
import com.eburg_soft.mynews.data.repository.NewsRepository
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDataSource @Inject constructor(private val repository: NewsRepository) :
    PageKeyedDataSource<Int, NewsArticleUI>() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    companion object {

        var currentPage = 1
        var size = 0
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsArticleUI>) {
        scope.launch {
            currentPage++
            val nextPage = currentPage
            val data = repository.getTopHeadlinesInTheUsForUI()
            size = data.second
            if (isNextPageNumber(data.second, params.requestedLoadSize, currentPage)) {
                callback.onResult(data.first, null, nextPage)
            } else {
                callback.onResult(data.first, null, null)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticleUI>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticleUI>) {
        scope.launch {
            currentPage++
            val nextPage = currentPage
            val data = repository.getTopHeadlinesInTheUsForUI(pageNumber = currentPage)
            if (isNextPageNumber(size, params.requestedLoadSize, currentPage)) {
                callback.onResult(data.first, nextPage)
            } else {
                callback.onResult(data.first, null)
            }
        }
    }

    private fun isNextPageNumber(size: Int, pageSize: Int, currentPage: Int): Boolean {
        val maxPage = (size / pageSize).toDouble()
        return maxPage > currentPage
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}