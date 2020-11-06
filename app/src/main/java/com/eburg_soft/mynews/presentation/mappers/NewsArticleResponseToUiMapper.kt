package com.eburg_soft.mynews.presentation.mappers

import android.annotation.SuppressLint
import com.eburg_soft.mynews.core.BaseMapper
import com.eburg_soft.mynews.data.datasource.network.models.NewsArticleResponse
import com.eburg_soft.mynews.presentation.models.NewsArticleUi
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

class NewsArticleResponseToUiMapper : BaseMapper<NewsArticleResponse, NewsArticleUi> {

    companion object {

        const val EXCEPTION_DATE = "Unparseable date"

        //  Sample: "2020-11-05T08:18:00"
        private const val recievedDatePattern = "yyyy-MM-dd'T'HH:mm:ss"

        //  Sample: "03.09.2020
        const val resultDatePattern = "dd.MM.yyyy"
    }

    override fun map(type: NewsArticleResponse?): NewsArticleUi {
        val newsArticleUI = NewsArticleUi()
        type.let {
            it?.apply {
                if (author.isNullOrBlank()) {
                    newsArticleUI.author = "Anonymous author"
                } else newsArticleUI.author = author
                newsArticleUI.title = title
                newsArticleUI.description = description
                newsArticleUI.publishedAt = publishedAt?.let { it1 -> mapDate(it1) }
                newsArticleUI.url = url
                newsArticleUI.urlToImage = urlToImage
            }
        }
        return newsArticleUI
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(Exception::class)
    private fun mapDate(date: String): String {
        val simpleDate: Date?
        val trimedData = date.substring(0, date.length - 1)
        Timber.d("$trimedData")
        try {
            simpleDate = SimpleDateFormat(recievedDatePattern).parse(trimedData)
        } catch (e: Exception) {
            throw Exception(EXCEPTION_DATE)
        }
        Timber.d("date: %s", simpleDate.toString())
        return SimpleDateFormat(resultDatePattern).format(simpleDate)
    }
}