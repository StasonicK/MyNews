package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eburg_soft.mynews.databinding.ItemNewsArticleBinding
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import com.eburg_soft.mynews.presentation.screens.newsarticleslist.NewsArticleAdapter.NewsArticleHolder

class NewsArticleAdapter : PagedListAdapter<NewsArticleUiModel, NewsArticleHolder>(NewsArticleDiffUtilCallback()) {

    //    class NewsArticleHolder(view: View) : ViewHolder(view) {
    class NewsArticleHolder(private val binding: ItemNewsArticleBinding) : ViewHolder(binding.root) {

//        private val author = itemView.newsAuthorItem
//        private val title = itemView.newsTitleItem
//        private val publishedAt = itemView.newsPublishedAtItem

        fun bind(item: NewsArticleUiModel?) {
            item?.let {
//                author.text = item.author ?: "Anonymous author"
//                title.text = item.title
//                publishedAt.text = item.publishedAt
                binding.apply {
                    newsAuthorItem.text = item.author ?: "Anonymous author"
                    newsTitleItem.text = item.title
                    newsPublishedAtItem.text = item.publishedAt
                }

                itemView.setOnClickListener {
                    val direction = NewsArticlesListFragmentDirections.openDetailedNewsArticleFragment(item)
                    Navigation.findNavController(itemView).navigate(direction)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleHolder {
//        val view = parent.inflate(R.layout.item_news_article)
//        return NewsArticleHolder(view)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsArticleBinding.inflate(layoutInflater, parent, false)
        return NewsArticleHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsArticleHolder, position: Int) {
        holder.bind(getItem(position))
    }
}