package com.eburg_soft.mynews.presentation.newslist

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.eburg_soft.currencyconverter.data.di.Scopes
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.extensions.injectViewModel
import com.eburg_soft.mynews.extensions.observe
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import kotlinx.android.synthetic.main.fragment_news_list.recyclerview_news
import timber.log.Timber

class NewsListFragment() : Fragment(R.layout.fragment_news_list) {

    private var savedInstanceState: Bundle? = null
    private val newsArticleAdapter = NewsArticleAdapter()

    private val viewModel: NewsListViewModel by lazy {
        injectViewModel(NewsListViewModel::class, Scopes.NewsList)
    }

    companion object {

        private const val KEY_LAST_ITEM_POSITION = "KEY_LAST_ITEM_POSITION"
    }

    //region ====================== Android methods ======================

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.savedInstanceState = savedInstanceState

        observerLiveData()
        // handle back button
//        requireActivity().onBackPressedDispatcher.addCallback(
//            requireActivity(),
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    Timber.d("Activity back pressed invoked")
//                    // if you want onBackPressed() to be called as normal afterwards
//                    if (isEnabled) {
//                        isEnabled = false
//                        view?.let { Navigation.findNavController(it).navigateUp() }
//                    }
//                }
//            }
//        )
        Timber.d("onActivityCreated()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        saveRecyclerViewState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // handle navigateUp
            android.R.id.home -> {
                requireActivity().onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //endregion

    private fun observerLiveData() {
        observe(viewModel.getNewsList()) { showNewsList(it) }
    }

    private fun showNewsList(news: PagedList<NewsArticleUI>) {
        populateNewsList(news)
        restorePreviousUIState()
    }

    // set saved recycler view's position after recreating the fragment
    private fun restorePreviousUIState() {
        savedInstanceState?.let {
            val lastItemPosition = it.getInt(KEY_LAST_ITEM_POSITION)
            recyclerview_news.scrollToPosition(lastItemPosition)
            Timber.d("$KEY_LAST_ITEM_POSITION is $lastItemPosition")
        }
    }

    private fun populateNewsList(news: PagedList<NewsArticleUI>) {
        recyclerview_news.apply {
            newsArticleAdapter.submitList(news)
            adapter = newsArticleAdapter
            setHasFixedSize(true)
        }
    }

    private fun saveRecyclerViewState(outState: Bundle) {
        recyclerview_news.apply {
            layoutManager?.let { it ->
                val lastVisibleItemPosition = (it as LinearLayoutManager).findLastVisibleItemPosition()

                outState.putInt(KEY_LAST_ITEM_POSITION, lastVisibleItemPosition)
            }
        }
    }
}