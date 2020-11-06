package com.eburg_soft.mynews.presentation.screens.policies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions.Builder
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.eburg_soft.currencyconverter.data.di.Scopes
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.extensions.injectViewModel
import com.eburg_soft.mynews.extensions.observe
import com.eburg_soft.mynews.presentation.screens.newsarticleslist.NewsArticlesListViewModel
import com.eburg_soft.mynews.utils.NetworkUtils
import kotlinx.android.synthetic.main.fragment_policies.IP
import kotlinx.android.synthetic.main.fragment_policies.button_agree
import kotlinx.android.synthetic.main.fragment_policies.button_deny
import kotlinx.android.synthetic.main.fragment_policies.progressbarPoliciesFragment
import kotlinx.android.synthetic.main.fragment_policies.webView
import timber.log.Timber
import java.util.Locale

class PoliciesFragment : Fragment(R.layout.fragment_policies) {

    private lateinit var toolbar: Toolbar
    private var savedInstanceState: Bundle? = null

    var ipAddress: String? = null


    private val viewModel: PoliciesViewModel by lazy {
        injectViewModel(PoliciesViewModel::class, Scopes.policies)
    }

    companion object {

        private const val URL_GOOGLE_POLICIES = "https://policies.google.com/privacy"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.savedInstanceState = savedInstanceState

        setupUI()
        Timber.d("onActivityCreated()")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        showLoading(true)

        ipAddress = NetworkUtils.detectNetwork(requireContext())

        toolbar = activity?.findViewById(R.id.toolbarPoliciesFragment)!!
        toolbar.setTitle(R.string.app_name)

        val language = Locale.getDefault().language
        Timber.d("language $language")
        val country = Locale.getDefault().country
        Timber.d("country $country")

        webView.apply {
            // set encoding type "UTF-8" for avoiding issues
            val settings: WebSettings = webView.settings
            settings.defaultTextEncodingName = "utf-8"

            loadUrl(URL_GOOGLE_POLICIES)
            settings.javaScriptEnabled = true
        }
        IP.text = ipAddress

        button_agree.setOnClickListener {
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

        button_deny.setOnClickListener {
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(startMain)
        }

        showLoading(false)
    }


    private fun observerLiveData() {
        observe(viewModel.getCountryCode()) { showLoading(it) }
    }

    private fun showLoading(isLoading: Boolean) {
        progressbarPoliciesFragment.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}