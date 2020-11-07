package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel

class NewsArticleDiffUtilCallback : DiffUtil.ItemCallback<NewsArticleUiModel>() {

    override fun areItemsTheSame(oldItem: NewsArticleUiModel, newItem: NewsArticleUiModel): Boolean {
        return oldItem.url === newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsArticleUiModel, newItem: NewsArticleUiModel): Boolean {
        return oldItem.url == newItem.url && oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.publishedAt == newItem.publishedAt && oldItem.urlToImage == newItem.urlToImage
    }
}