package com.eburg_soft.mynews.presentation.screens

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.R.layout
import com.eburg_soft.mynews.extensions.currentNavigationFragment
import com.eburg_soft.mynews.presentation.screens.policies.PoliciesFragment
import com.google.android.gms.ads.MobileAds
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity(layout.activity_main) {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdMob()
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
        return super.onKeyDown(keyCode, event)
    }
}