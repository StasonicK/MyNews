package com.eburg_soft.mynews.data.di

import com.eburg_soft.mynews.data.datasource.network.NewsApi
import com.eburg_soft.mynews.data.datasource.network.NewsApiService
import toothpick.config.Module

class NetworkModule : Module() {
    init {
        bind(NewsApi::class.java)
            .toInstance(NewsApiService.newsApi())
    }
}