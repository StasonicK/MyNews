package com.eburg_soft.mynews.presentation.screens.detailednewsarticle

import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.databinding.FragmentDetailedNewsArticleBinding
import com.eburg_soft.mynews.presentation.models.NewsArticleUiModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration.Builder
import com.squareup.picasso.Picasso
import timber.log.Timber

class DetailedNewsArticleFragment : Fragment(R.layout.fragment_detailed_news_article) {

    private var _binding: FragmentDetailedNewsArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsArticleUiModel: NewsArticleUiModel

    //region ====================== Android methods ======================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailedNewsArticleBinding.bind(view)

        getItem()
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    private fun getItem() {
        newsArticleUiModel = DetailedNewsArticleFragmentArgs.fromBundle(requireArguments()).url
    }

    private fun setupUI() {
        binding.apply {
            toolbarDetailedNewsFragment.root.apply {
                setTitle(R.string.app_name)
                setNavigationIcon(R.drawable.baseline_arrow_back_white_24)
                setNavigationOnClickListener {
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }

            textViewAuthor.text = newsArticleUiModel.author
            textViewTitle.text = newsArticleUiModel.title
            textViewDescription.text = newsArticleUiModel.description
            textViewPublishedAt.text = newsArticleUiModel.publishedAt

            Picasso.get()
                .load(newsArticleUiModel.urlToImage)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.ic_error)
                .fit()
                .centerCrop()
                .into(imageViewNews)

            // launch advertisement banner
//          for (i in 0..999) {
            val adRequest = AdRequest.Builder()
                .build()
            adView.loadAd(adRequest)
            Timber.d("adRequest: $adRequest")
//          }
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

        val testDeviceIds: List<String> = listOf("1C32CE28B535D98D63750D869B1813AF")
        val configuration = Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
    }
}