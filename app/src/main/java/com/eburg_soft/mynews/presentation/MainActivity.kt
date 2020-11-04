package com.eburg_soft.mynews.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.eburg_soft.mynews.R
import com.eburg_soft.mynews.R.layout
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        initController()

        Timber.d("onCreate()")
    }
    private fun initController() {
        navController = Navigation.findNavController(this, R.id.container)
    }

    override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() ?: false
}