package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.data.di.Scopes
import com.eburg_soft.mynews.extensions.injectViewModel
import com.eburg_soft.mynews.extensions.observe
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import kotlinx.android.synthetic.main.fragment_news_articles_list.progressbarNewsArticlesListFragment
import kotlinx.android.synthetic.main.fragment_news_articles_list.recyclerviewNewsArticles
import timber.log.Timber

class NewsArticlesListFragment() : Fragment(R.layout.fragment_news_articles_list) {

    private lateinit var toolbar: Toolbar
    private var savedInstanceState: Bundle? = null
    private val newsArticleAdapter = NewsArticleAdapter()

    private val viewModel: NewsArticlesListViewModel by lazy {
        injectViewModel(NewsArticlesListViewModel::class, Scopes.newsArticlesList)
    }

    companion object {

        private const val KEY_LAST_ITEM_POSITION = "KEY_LAST_ITEM_POSITION"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.savedInstanceState = savedInstanceState

        setupUI()
        observerLiveData()
        Timber.d("onActivityCreated()")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        toolbar = activity?.findViewById(R.id.toolbarNewsArticlesListFragment)!!
        toolbar.setTitle(R.string.app_name)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveRecyclerViewState(outState)
        super.onSaveInstanceState(outState)
    }

    //endregion

    private fun observerLiveData() {
        observe(viewModel.isLoadingLiveData) { showLoading(it) }
        observe(viewModel.getNewsList()) { showNewsList(it) }
    }

    private fun showNewsList(newsModels: PagedList<NewsArticleUiModel>) {
        populateNewsList(newsModels)
        restorePreviousUIState()
    }

    // set saved recycler view's position after recreating the fragment
    private fun restorePreviousUIState() {
        savedInstanceState?.let {
            val lastItemPosition = it.getInt(KEY_LAST_ITEM_POSITION)
            recyclerviewNewsArticles.scrollToPosition(lastItemPosition)
            Timber.d("$KEY_LAST_ITEM_POSITION is $lastItemPosition")
        }
    }

    private fun populateNewsList(newsModels: PagedList<NewsArticleUiModel>) {
        recyclerviewNewsArticles.apply {
            newsArticleAdapter.submitList(newsModels)
            adapter = newsArticleAdapter
            setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressbarNewsArticlesListFragment.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun saveRecyclerViewState(outState: Bundle) {
        recyclerviewNewsArticles.apply {
            layoutManager?.let { it ->
                val lastVisibleItemPosition = (it as LinearLayoutManager).findLastVisibleItemPosition()

                outState.putInt(KEY_LAST_ITEM_POSITION, lastVisibleItemPosition)
            }
        }
    }
}