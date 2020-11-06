package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.mynews.presentation.models.NewsArticleUi

class NewsArticleDiffUtilCallback : DiffUtil.ItemCallback<NewsArticleUi>() {

    override fun areItemsTheSame(oldItem: NewsArticleUi, newItem: NewsArticleUi): Boolean {
        return oldItem.url === newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsArticleUi, newItem: NewsArticleUi): Boolean {
        return oldItem.url == newItem.url && oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.publishedAt == newItem.publishedAt && oldItem.urlToImage == newItem.urlToImage
    }
}