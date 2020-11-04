package com.eburg_soft.mynews.presentation.newslist

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.mynews.presentation.models.NewsArticleUI

class NewsArticleDiffUtilCallback : DiffUtil.ItemCallback<NewsArticleUI>() {

    override fun areItemsTheSame(oldItem: NewsArticleUI, newItem: NewsArticleUI): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsArticleUI, newItem: NewsArticleUI): Boolean {
        return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.publishedAt == newItem.publishedAt && oldItem.url == newItem.url && oldItem.urlToImage == newItem.urlToImage
    }
}