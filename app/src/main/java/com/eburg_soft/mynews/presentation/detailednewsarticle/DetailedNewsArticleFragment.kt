package com.eburg_soft.mynews.presentation.detailednewsarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.presentation.models.NewsArticleUI
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detailed_news_article.buttonOpenOriginalPage
import kotlinx.android.synthetic.main.fragment_detailed_news_article.imageViewNews
import kotlinx.android.synthetic.main.fragment_detailed_news_article.textViewAuthor
import kotlinx.android.synthetic.main.fragment_detailed_news_article.textViewDescription
import kotlinx.android.synthetic.main.fragment_detailed_news_article.textViewPublishedAt
import kotlinx.android.synthetic.main.fragment_detailed_news_article.textViewTitle
import timber.log.Timber

class DetailedNewsArticleFragment : Fragment(R.layout.fragment_detailed_news_article) {

    private lateinit var toolbar: Toolbar
    private var factory: CustomViewModelFactory? = null

    private lateinit var item: NewsArticleUI

    companion object {

        const val AUTHOR = "author"
        const val TITLE = "Title"
        const val DESCRIPTION = "Description"
        const val PUBLISHED_AT = "PublishedAt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getItem()
        setupUI()

        Timber.d("onActivityCreated()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putString()
//        outState.putString()
//        outState.putString()
//        outState.put()
        super.onSaveInstanceState(outState)
    }

    private fun getItem() {
        item = DetailedNewsArticleFragmentArgs.fromBundle(requireArguments()).url
    }

    private fun setupUI() {
        toolbar = view?.findViewById(R.id.toolbarDetailedNewsFragment)!!
        toolbar.apply {
            setNavigationIcon(R.drawable.baseline_arrow_back_white_24)
            setNavigationOnClickListener {
                Navigation.findNavController(requireView()).navigateUp()
            }
        }

        textViewAuthor.text = item.author
        textViewTitle.text = item.title
        textViewDescription.text = item.description
        textViewPublishedAt.text = item.publishedAt

        Picasso.get()
            .load(item.urlToImage)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(imageViewNews)

        // TODO: 05.11.2020 finish
        buttonOpenOriginalPage.setOnClickListener {

        }

        // handle back button
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Timber.d("Activity back pressed invoked")
                    // if you want onBackPressed() to be called as normal afterwards
                    if (isEnabled) {
                        isEnabled = false
                        view?.let { Navigation.findNavController(it).navigateUp() }
                    }
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_news_article, container, false)
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
}