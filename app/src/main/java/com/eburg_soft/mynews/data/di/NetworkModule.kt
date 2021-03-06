package com.eburg_soft.mynews.data.di

import com.eburg_soft.mynews.data.datasource.network.apis.NewsApi
import com.eburg_soft.mynews.data.datasource.network.apiservices.NewsApiService
import toothpick.config.Module

class NetworkModule : Module() {
    init {
        bind(NewsApi::class.java)
            .toInstance(NewsApiService.api())
    }
}