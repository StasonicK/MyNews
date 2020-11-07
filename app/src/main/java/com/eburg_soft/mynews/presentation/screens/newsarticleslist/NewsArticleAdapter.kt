package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.extensions.inflate
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import com.eburg_soft.mynews.presentation.screens.newsarticleslist.NewsArticleAdapter.NewsArticleHolder
import kotlinx.android.synthetic.main.item_news_article.view.newsAuthorItem
import kotlinx.android.synthetic.main.item_news_article.view.newsPublishedAtItem
import kotlinx.android.synthetic.main.item_news_article.view.newsTitleItem

class NewsArticleAdapter : PagedListAdapter<NewsArticleUiModel, NewsArticleHolder>(NewsArticleDiffUtilCallback()) {

    class NewsArticleHolder(view: View) : ViewHolder(view) {

        private val author = itemView.newsAuthorItem
        private val title = itemView.newsTitleItem
        private val publishedAt = itemView.newsPublishedAtItem

        fun bind(item: NewsArticleUiModel?) {
            item?.let {
                author.text = item.author ?: "Anonymous author"
                title.text = item.title
                publishedAt.text = item.publishedAt

                itemView.setOnClickListener {
                    val direction = NewsArticlesListFragmentDirections.openDetailedNewsArticleFragment(item)
                    Navigation.findNavController(itemView).navigate(direction)
                }
            }
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