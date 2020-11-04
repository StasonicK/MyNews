package com.eburg_soft.mynews.data.datasource.network.models

import com.google.gson.annotations.SerializedName

/**
 * Describes the response from news service API.
 */
data class NewsMainResponse(
    @SerializedName("status")
    val status: String = "",

    @SerializedName("totalResults")
    val totalResults: Int = 0,

    @SerializedName("articles")
    val articleResponses: List<NewsArticleResponse> = emptyList()
)