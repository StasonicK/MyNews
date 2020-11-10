package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.data.di.Scopes
import com.eburg_soft.mynews.databinding.FragmentNewsArticlesListBinding
import com.eburg_soft.mynews.extensions.injectViewModel
import com.eburg_soft.mynews.extensions.observe
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import timber.log.Timber

class NewsArticlesListFragment : Fragment(R.layout.fragment_news_articles_list) {

    private var _binding: FragmentNewsArticlesListBinding? = null
    private val binding get() = _binding!!

    private var savedInstanceState: Bundle? = null
    private val newsArticleAdapter = NewsArticleAdapter()

    private val viewModel: NewsArticlesListViewModel by lazy {
        injectViewModel(NewsArticlesListViewModel::class, Scopes.newsArticlesList)
    }

    companion object {

        private const val KEY_LAST_ITEM_POSITION = "KEY_LAST_ITEM_POSITION"
    }

    //region ====================== Android methods ======================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.savedInstanceState = savedInstanceState
        _binding = FragmentNewsArticlesListBinding.bind(view)

        setupUI()
        observerLiveData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        binding.toolbarNewsArticlesListFragment.root.title = getString(R.string.app_name)
    }

    private fun showNewsList(newsModels: PagedList<NewsArticleUiModel>) {
        populateNewsList(newsModels)
        restorePreviousUIState()
    }

    // set saved recycler view's position after recreating the fragment
    private fun restorePreviousUIState() {
        savedInstanceState?.let {
            val lastItemPosition = it.getInt(KEY_LAST_ITEM_POSITION)
            binding.recyclerviewNewsArticles.scrollToPosition(lastItemPosition)
            Timber.d("$KEY_LAST_ITEM_POSITION is $lastItemPosition")
        }
    }

    private fun populateNewsList(newsModels: PagedList<NewsArticleUiModel>) {
        binding.recyclerviewNewsArticles.apply {
            newsArticleAdapter.submitList(newsModels)
            adapter = newsArticleAdapter
            setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbarNewsArticlesListFragment.root.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun saveRecyclerViewState(outState: Bundle) {
        binding.recyclerviewNewsArticles.apply {
            layoutManager?.let { it ->
                val lastVisibleItemPosition = (it as LinearLayoutManager).findLastVisibleItemPosition()
                outState.putInt(KEY_LAST_ITEM_POSITION, lastVisibleItemPosition)
            }
        }
    }
}