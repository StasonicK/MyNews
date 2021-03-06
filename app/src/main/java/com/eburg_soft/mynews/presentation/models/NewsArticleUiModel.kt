package com.eburg_soft.mynews.presentation.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsArticleUiModel(
    /**
     * Name of the author for the article
     */
    @SerializedName("author")
    var author: String? = null,

    /**
     * Title of the article
     */
    @SerializedName("title")
    var title: String? = null,

    /**
     * Complete description of the article
     */
    @SerializedName("description")
    var description: String? = null,

    /**
     * URL to the article
     */
    @SerializedName("url")
    var url: String? = null,

    /**
     * URL of the artwork shown with article
     */
    @SerializedName("urlToImage")
    var urlToImage: String? = null,

    /**
     * Date-time when the article was published
     */
    @SerializedName("publishedAt")
    var publishedAt: String? = null
) : Parcelable {

    constructor() : this(
        author = null,
        title = null,
        description = null,
        url = null,
        urlToImage = null,
        publishedAt = null
    )
}