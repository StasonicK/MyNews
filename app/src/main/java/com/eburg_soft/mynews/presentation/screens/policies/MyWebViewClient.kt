package com.eburg_soft.mynews.presentation.screens.policies

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import com.eburg_soft.mynews.core.URL_GOOGLE_POLICIES

class MyWebViewClient(private val activity: Activity) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        // all links that contain 'https://policies.google.com/privacy'
        // will open inside the app
        if (url.toString().contains(URL_GOOGLE_POLICIES)) {
            return false
        }
        // all other links will ask which browser to open
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
        return true
    }
}