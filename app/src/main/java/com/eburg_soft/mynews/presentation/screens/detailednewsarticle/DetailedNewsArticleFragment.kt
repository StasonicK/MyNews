package com.eburg_soft.mynews.presentation.screens.detailednewsarticle

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
import com.eburg_soft.mynews.databinding.FragmentDetailedNewsArticleBinding
import com.eburg_soft.mynews.extensions.viewLifecycleLazy
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration.Builder
import com.squareup.picasso.Picasso
import timber.log.Timber

class DetailedNewsArticleFragment : Fragment(R.layout.fragment_detailed_news_article) {

    private val binding by viewLifecycleLazy { FragmentDetailedNewsArticleBinding.bind(requireView()) }

    private lateinit var newsArticleUiModel: NewsArticleUiModel

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

    private fun getItem() {
        newsArticleUiModel = DetailedNewsArticleFragmentArgs.fromBundle(requireArguments()).url
    }

    private fun setupUI() {
//        toolbar = view?.findViewById(R.id.toolbarDetailedNewsFragment)!!
        binding.toolbarDetailedNewsFragment.root.apply {
            setTitle(R.string.app_name)
            setNavigationIcon(R.drawable.baseline_arrow_back_white_24)
            setNavigationOnClickListener {
                Navigation.findNavController(requireView()).navigateUp()
            }
        }

        binding.textViewAuthor.text = newsArticleUiModel.author
        binding.textViewTitle.text = newsArticleUiModel.title
        binding.textViewDescription.text = newsArticleUiModel.description
        binding.textViewPublishedAt.text = newsArticleUiModel.publishedAt

        Picasso.get()
            .load(newsArticleUiModel.urlToImage)
            .placeholder(R.drawable.image_placeholder)
            .fit()
            .centerCrop()
            .into(binding.imageViewNews)

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

        val testDeviceIds: List<String> = listOf("1C32CE28B535D98D63750D869B1813AF")
        val configuration = Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        // launch advertisement banner
//        for (i in 0..999) {
        val adRequest = AdRequest.Builder()
            .build()
        binding.adView.loadAd(adRequest)
        Timber.d("adRequest: $adRequest")
//        }
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