package com.eburg_soft.mynews.presentation.mappers

import com.eburg_soft.mynews.core.BaseMapper
import com.eburg_soft.mynews.data.datasource.network.models.NewsArticleResponse
import com.eburg_soft.mynews.presentation.models.NewsArticleUI

class NewsArticleResponseToUiMapper : BaseMapper<NewsArticleResponse, NewsArticleUI> {

    override fun map(type: NewsArticleResponse?): NewsArticleUI {
        val newsArticleUI = NewsArticleUI()
        type.let {
            it?.apply {
                newsArticleUI.author = author?:"Anonymous author"
                newsArticleUI.title = title
                newsArticleUI.description = description
                newsArticleUI.publishedAt = publishedAt
                newsArticleUI.url = url
                newsArticleUI.urlToImage = urlToImage
            }
        }
        return newsArticleUI
    }
}