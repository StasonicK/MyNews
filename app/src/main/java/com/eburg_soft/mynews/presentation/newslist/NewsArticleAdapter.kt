package com.eburg_soft.mynews.presentation.newslist

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.extensions.inflate
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import com.eburg_soft.mynews.presentation.newslist.NewsArticleAdapter.NewsArticleHolder
import kotlinx.android.synthetic.main.item_news.view.newsAuthor
import kotlinx.android.synthetic.main.item_news.view.newsPublishedAt
import kotlinx.android.synthetic.main.item_news.view.newsTitle

class NewsArticleAdapter : PagedListAdapter<NewsArticleUI, NewsArticleHolder>(NewsArticleDiffUtilCallback()) {

    class NewsArticleHolder(view: View) : ViewHolder(view) {
        private val author = itemView.newsAuthor
        private val title = itemView.newsTitle
        val publishedAt = itemView.newsPublishedAt

        fun bind(item: NewsArticleUI?) {
            author.text = item?.author
            title.text = item?.title
            publishedAt.text = item?.publishedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleHolder {
        val view = parent.inflate(R.layout.item_news)
        return NewsArticleHolder(view)
    }

    override fun onBindViewHolder(holder: NewsArticleHolder, position: Int) {
        holder.bind(getItem(position))
    }
}