package com.eburg_soft.mynews.presentation.newslist

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.mynews.presentation.models.NewsArticleUI

class NewsArticleDiffUtilCallback : DiffUtil.ItemCallback<NewsArticleUI>() {

    override fun areItemsTheSame(oldItem: NewsArticleUI, newItem: NewsArticleUI): Boolean {
        return oldItem.url === newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsArticleUI, newItem: NewsArticleUI): Boolean {
        return oldItem.url == newItem.url && oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.publishedAt == newItem.publishedAt && oldItem.urlToImage == newItem.urlToImage
    }
}