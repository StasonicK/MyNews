package com.eburg_soft.mynews.data.di

import com.eburg_soft.mynews.data.repository.NewsRepository
import com.eburg_soft.mynews.data.repository.NewsRepositoryImpl
import toothpick.config.Module

class RepositoryModule : Module() {

    init {
        bind(NewsRepository::class.java)
            .to(NewsRepositoryImpl::class.java)
    }
}