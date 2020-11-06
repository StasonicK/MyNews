package com.eburg_soft.mynews.data.datasource.network.apiservices

import com.eburg_soft.mynews.core.IP_URL
import com.eburg_soft.mynews.data.datasource.network.apis.IpApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IpApiService : BaseService<IpApi>() {

    override fun api(): IpApi = retrofitInstance().create(IpApi::class.java)

    override fun retrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(IP_URL)
        .client(okHttpInstance())
        .addConverterFactory(GsonConverterFactory.create(gsonInstance()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}