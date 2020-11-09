package com.eburg_soft.mynews.presentation.screens

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.R.layout
import com.eburg_soft.mynews.extensions.currentNavigationFragment
import com.eburg_soft.mynews.presentation.screens.policies.PoliciesFragment
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_policies.webView
import timber.log.Timber

class MainActivity : AppCompatActivity(layout.activity_main) {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        initAdMob()
        initController()
        Timber.d("onCreate()")
    }

    private fun initAdMob() {
        MobileAds.initialize(
            this
        ) { }
    }

    private fun initController() {
        navController = Navigation.findNavController(this, R.id.container)
    }

    override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() ?: false

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val policiesFragment = supportFragmentManager.currentNavigationFragment as PoliciesFragment
        if ((keyCode == KeyEvent.KEYCODE_BACK) && policiesFragment.webViewCanGoBack()) {
            policiesFragment.webViewGoBack()
            return true
        }

//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack()
//            return true
//        }
        return super.onKeyDown(keyCode, event)
    }
}