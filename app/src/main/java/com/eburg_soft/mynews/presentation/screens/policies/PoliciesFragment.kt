package com.eburg_soft.mynews.presentation.screens.policies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions.Builder
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.eburg_soft.currencyconverter.data.di.Scopes
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.core.URL_GOOGLE_POLICIES
import com.eburg_soft.mynews.extensions.injectViewModel
import com.eburg_soft.mynews.utils.NetworkUtils
import kotlinx.android.synthetic.main.fragment_policies.buttonAgree
import kotlinx.android.synthetic.main.fragment_policies.buttonDeny
import kotlinx.android.synthetic.main.fragment_policies.progressbarPoliciesFragment
import kotlinx.android.synthetic.main.fragment_policies.textviewCountryCode
import kotlinx.android.synthetic.main.fragment_policies.textviewLanguageCode
import kotlinx.android.synthetic.main.fragment_policies.webView
import timber.log.Timber
import java.util.Locale

class PoliciesFragment : Fragment(R.layout.fragment_policies) {

    private lateinit var toolbar: Toolbar
    private var savedInstanceState: Bundle? = null

    private val viewModel: PoliciesViewModel by lazy {
        injectViewModel(PoliciesViewModel::class, Scopes.policies)
    }

    companion object {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.savedInstanceState = savedInstanceState

        observerLiveData()
        setupUI()
        Timber.d("onActivityCreated()")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        showLoading(true)

        toolbar = activity?.findViewById(R.id.toolbarPoliciesFragment)!!
        toolbar.setTitle(R.string.app_name)

        val language = Locale.getDefault().language
        Timber.d("language $language")
        val country = Locale.getDefault().country
        Timber.d("country $country")

        webView.apply {

            // set encoding type "UTF-8" for avoiding issues
            val settings: WebSettings = webView.settings
            settings.javaScriptEnabled = true
            settings.defaultTextEncodingName = "utf-8"
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            
            val url = "${URL_GOOGLE_POLICIES}?hl=${language}-${country}"
            Timber.d("url $url")
            loadUrl(url)
        }

        textviewLanguageCode.text = language
        textviewCountryCode.text = country

        buttonAgree.setOnClickListener {
            // remove that fragment from back stack
            findNavController(this)
                .navigate(
                    R.id.newsArticlesListFragment,
                    null,
                    Builder()
                        .setPopUpTo(
                            R.id.policiesFragment,
                            true
                        ).build()
                )
        }

        buttonDeny.setOnClickListener {
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(startMain)
        }

        showLoading(false)
    }

    private fun observerLiveData() {
    }

    private fun showLoading(isLoading: Boolean) {
        progressbarPoliciesFragment.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    fun webViewCanGoBack(): Boolean = webView.canGoBack()

    //
    fun webViewGoBack() {
        webView.goBack()
    }
}