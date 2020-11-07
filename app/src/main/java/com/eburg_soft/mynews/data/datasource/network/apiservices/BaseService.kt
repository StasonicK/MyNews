package com.eburg_soft.mynews.data.datasource.network.apiservices

import com.eburg_soft.mynews.data.datasource.network.apis.BaseApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

abstract class BaseService<T : BaseApi> {

    abstract fun api(): T

    protected fun okHttpInstance(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()
    }

    protected fun gsonInstance(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    abstract fun retrofitInstance(): Retrofit
//    private fun retrofitInstance(): Retrofit = Retrofit.Builder()
//        .baseUrl(baseUrl)
//        .client(okHttpInstance())
//        .addConverterFactory(GsonConverterFactory.create(gsonInstance()))
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//        .build()
}