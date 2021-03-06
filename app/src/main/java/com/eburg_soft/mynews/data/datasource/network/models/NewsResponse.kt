package com.eburg_soft.mynews.data.datasource.network.models

import com.google.gson.annotations.SerializedName

/**
 * Describes the response from news service API.
 */
data class NewsResponse(
    @SerializedName("status")
    val status: String = "",

    @SerializedName("totalResults")
    val totalResults: Int = 0,

    @SerializedName("articles")
    val articleResponses: List<NewsArticleResponse> = emptyList()
)

data class NewsArticleResponse(

    @SerializedName("source")
    val source: Source,

    /**
     * Name of the author for the article
     */
    @SerializedName("author")
    val author: String? = null,

    /**
     * Title of the article
     */
    @SerializedName("title")
    val title: String? = null,

    /**
     * Complete description of the article
     */
    @SerializedName("description")
    val description: String? = null,

    /**
     * URL to the article
     */
    @SerializedName("url")
    val url: String? = null,

    /**
     * URL of the artwork shown with article
     */
    @SerializedName("urlToImage")
    val urlToImage: String? = null,

    /**
     * Date-time when the article was published
     */
    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("content")
    val content: String? = null
) {

    data class Source(

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("name")
        val name: String? = null
    )
}