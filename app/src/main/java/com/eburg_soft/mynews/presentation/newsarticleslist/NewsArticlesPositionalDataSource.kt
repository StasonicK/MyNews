package com.eburg_soft.mynews.presentation.newsarticleslist

import androidx.paging.PageKeyedDataSource
import com.eburg_soft.mynews.core.PAGE_SIZE
import com.eburg_soft.mynews.data.repository.NewsRepository
import com.eburg_soft.mynews.extensions.round
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.RoundingMode.CEILING
import javax.inject.Inject

class NewsDataSource @Inject constructor(private val repository: NewsRepository) :
    PageKeyedDataSource<Int, NewsArticleUI>() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    companion object {

        var currentPage = 1
        var nextPage = currentPage + 1
        var size = 0
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsArticleUI>) {
        scope.launch {
            Timber.d("currentPage = $currentPage")
            val data = repository.getTopHeadlinesInTheUsForUI()
            size = data.second
            Timber.d("size = $size")
            if (isNextPageNumber(data.second, PAGE_SIZE, currentPage)) {
                callback.onResult(data.first, null, nextPage)
                Timber.d("nextPage = $nextPage")
            } else {
                callback.onResult(data.first, null, null)
                Timber.d("nextPage = null")
            }
            Timber.d(
                "data: /n" +
                        "$data.first"
            )
            currentPage++
            nextPage++
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticleUI>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsArticleUI>) {
        scope.launch {
            Timber.d("currentPage = $currentPage")
            val data = repository.getTopHeadlinesInTheUsForUI(pageNumber = currentPage)
            if (isNextPageNumber(size, PAGE_SIZE, currentPage)) {
                callback.onResult(data.first, nextPage)
                Timber.d("nextPage = $nextPage")
            } else {
                callback.onResult(data.first, null)
                Timber.d("nextPage = null")
            }
            Timber.i(
                "data: /n" +
                        "$data.first"
            )
            currentPage++
            nextPage++
        }
    }

    private fun isNextPageNumber(size: Int, pageSize: Int, currentPage: Int): Boolean {
        val maxPageDouble = (size.toDouble() / pageSize.toDouble())
        val maxPage = maxPageDouble.round(0, CEILING).toInt()
        Timber.d("maxPage = $maxPage")
        Timber.d("currentPage = $currentPage")
        Timber.d("${maxPage > currentPage}")
        return maxPage > currentPage
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}