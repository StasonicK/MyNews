package com.eburg_soft.mynews.presentation.screens.newsarticleslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.data.di.Scopes
import com.eburg_soft.mynews.databinding.FragmentNewsArticlesListBinding
import com.eburg_soft.mynews.extensions.injectViewModel
import com.eburg_soft.mynews.extensions.observe
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import kotlinx.android.synthetic.main.fragment_news_articles_list.recyclerviewNewsArticles

class NewsArticlesListFragment : Fragment(R.layout.fragment_news_articles_list) {

    private var _binding: FragmentNewsArticlesListBinding? = null
    private val binding get() = _binding!!

    private val newsArticleAdapter = NewsArticleAdapter()

    private val viewModel: NewsArticlesListViewModel by lazy {
        injectViewModel(NewsArticlesListViewModel::class, Scopes.newsArticlesList)
    }

    companion object {

        private const val KEY_LAST_ITEM_POSITION = "key last item position"
    }

    //region ====================== Android methods ======================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewsArticlesListBinding.bind(view)

        setupUI()
        observerLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    //endregion

    private fun observerLiveData() {
        observe(viewModel.isLoadingLiveData) { showLoading(it) }
        observe(viewModel.getNewsArticlesListLiveData()) { showNewsList(it) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        binding.apply {
            toolbarNewsArticlesListFragment.root.title = getString(R.string.app_name)
            with(recyclerviewNewsArticles) {
                newsArticleAdapter
                adapter = newsArticleAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun showNewsList(newsModels: PagedList<NewsArticleUiModel>) {
        populateNewsList(newsModels)
    }

    private fun populateNewsList(newsModels: PagedList<NewsArticleUiModel>) {
        binding.recyclerviewNewsArticles.apply {
            newsArticleAdapter.submitList(newsModels)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbarNewsArticlesListFragment.root.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}