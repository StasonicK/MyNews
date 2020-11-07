package com.eburg_soft.mynews.data.di

import com.eburg_soft.mynews.data.repository.Repository
import com.eburg_soft.mynews.data.repository.RepositoryImpl
import toothpick.config.Module

class RepositoryModule : Module() {

    init {
        bind(Repository::class.java)
            .to(RepositoryImpl::class.java)
    }
}