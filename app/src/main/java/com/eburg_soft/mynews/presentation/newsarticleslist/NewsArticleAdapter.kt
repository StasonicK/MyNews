package com.eburg_soft.mynews.presentation.newsarticleslist

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.extensions.inflate
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import com.eburg_soft.mynews.presentation.newsarticleslist.NewsArticleAdapter.NewsArticleHolder
import kotlinx.android.synthetic.main.item_news.view.newsAuthorItem
import kotlinx.android.synthetic.main.item_news.view.newsPublishedAtItem
import kotlinx.android.synthetic.main.item_news.view.newsTitleItem

class NewsArticleAdapter : PagedListAdapter<NewsArticleUI, NewsArticleHolder>(NewsArticleDiffUtilCallback()) {

    class NewsArticleHolder(view: View) : ViewHolder(view) {

        private val author = itemView.newsAuthorItem
        private val title = itemView.newsTitleItem
        private val publishedAt = itemView.newsPublishedAtItem

        fun bind(item: NewsArticleUI?) {
            author.text = item?.author
            title.text = item?.title
            publishedAt.text = item?.publishedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleHolder {
        val view = parent.inflate(R.layout.item_news_article)
        return NewsArticleHolder(view)
    }

    override fun onBindViewHolder(holder: NewsArticleHolder, position: Int) {
        holder.bind(getItem(position))
    }
}