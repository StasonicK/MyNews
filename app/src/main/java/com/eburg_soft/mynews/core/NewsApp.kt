package com.eburg_soft.mynews.core

import android.app.Application
import com.eburg_soft.mynews.BuildConfig
import com.eburg_soft.mynews.data.di.NetworkModule
import com.eburg_soft.mynews.data.di.RepositoryModule
import com.eburg_soft.mynews.data.di.Scopes
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initToothpick()
        initTimber()

        Timber.d("App is created")
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) Configuration.forDevelopment()
            .preventMultipleRootScopes() else Configuration.forProduction()

        Toothpick.openScope(Scopes.app)
            .installModules(RepositoryModule(), NetworkModule())
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}