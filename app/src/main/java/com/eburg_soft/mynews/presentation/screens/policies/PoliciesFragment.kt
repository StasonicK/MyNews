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
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions.Builder
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.core.URL_GOOGLE_POLICIES
import kotlinx.android.synthetic.main.fragment_policies.buttonAgree
import kotlinx.android.synthetic.main.fragment_policies.buttonDeny
import kotlinx.android.synthetic.main.fragment_policies.progressbarPoliciesFragment
import kotlinx.android.synthetic.main.fragment_policies.webView
import timber.log.Timber
import java.util.Locale

class PoliciesFragment : Fragment(R.layout.fragment_policies) {

//    private var cookieStore = CookieStore()

    private lateinit var toolbar: Toolbar

    private val webHandler: Handler = object : Handler() {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        }
        setupUI()

        Timber.d("onActivityCreated()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {

        toolbar = activity?.findViewById(R.id.toolbarPoliciesFragment)!!
        toolbar.setTitle(R.string.app_name)

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
                    showLoading(true)
                    view?.loadUrl(url ?: URL_GOOGLE_POLICIES)
                    return super.shouldOverrideUrlLoading(view, url)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    showLoading(false)
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
    }

    private fun showLoading(isLoading: Boolean) {
        progressbarPoliciesFragment.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    fun webViewCanGoBack(): Boolean = webView.canGoBack()

    fun webViewGoBack() {
        webView.goBack()
    }
}