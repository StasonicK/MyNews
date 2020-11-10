package com.eburg_soft.mynews.presentation.screens.policies

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnKeyListener
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions.Builder
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.core.URL_GOOGLE_POLICIES
import com.eburg_soft.mynews.databinding.FragmentPoliciesBinding
import timber.log.Timber
import java.util.Locale

class PoliciesFragment : Fragment(R.layout.fragment_policies) {

    private var _binding: FragmentPoliciesBinding? = null
    private val binding get() = _binding!!

    private val webHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        @SuppressLint("HandlerLeak")
        override fun handleMessage(message: Message) {
            when (message.what) {
                1 -> {
                    webViewGoBack()
                }
            }
        }
    }

    companion object {

    }

    //region ====================== Android methods ======================

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPoliciesBinding.bind(view)

        if (savedInstanceState != null) {
            binding.webView.restoreState(savedInstanceState)
        }
        setupUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.webView.saveState(outState)
    }

    //endregion

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        binding.apply {
            toolbarPoliciesFragment.root.title = getString(R.string.app_name)

            val language = Locale.getDefault().language
            Timber.d("language $language")
            val country = Locale.getDefault().country
            Timber.d("country $country")

            webView.apply {

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        return super.shouldOverrideUrlLoading(view, request)
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        view?.loadUrl(url ?: URL_GOOGLE_POLICIES)
                        return super.shouldOverrideUrlLoading(view, url)
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }
                }

                if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
                }

                val settings: WebSettings = webView.settings
                settings.apply {
                    // set encoding type "UTF-8" for avoiding issues
                    defaultTextEncodingName = "utf-8"
                    javaScriptEnabled = true;
                    setGeolocationEnabled(true);
                    setAppCacheEnabled(true);
                    databaseEnabled = true;
                    domStorageEnabled = true;
                    cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                }

                requestFocus()

                val url = "${URL_GOOGLE_POLICIES}?hl=${language}-${country}"
                Timber.d("url $url")
                loadUrl(url)

                setOnKeyListener(object : OnKeyListener {
                    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && webViewCanGoBack()) {
                            webHandler.sendEmptyMessage(1)
                            return true
                        }
                        return false
                    }
                })
            }

        }

        binding.buttonAgree.setOnClickListener {
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

        binding.buttonDeny.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    fun webViewCanGoBack(): Boolean = binding.webView.canGoBack()

    fun webViewGoBack() {
        binding.webView.goBack()
    }
}