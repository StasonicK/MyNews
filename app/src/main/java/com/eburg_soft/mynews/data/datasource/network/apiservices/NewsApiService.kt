package com.eburg_soft.mynews.data.datasource.network.apiservices

import com.eburg_soft.mynews.core.NEWS_URL
import com.eburg_soft.mynews.data.datasource.network.apis.NewsApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiService : BaseService<NewsApi>() {

    override fun api(): NewsApi = retrofitInstance().create(NewsApi::class.java)

    override fun retrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(NEWS_URL)
        .client(okHttpInstance())
        .addConverterFactory(GsonConverterFactory.create(gsonInstance()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}