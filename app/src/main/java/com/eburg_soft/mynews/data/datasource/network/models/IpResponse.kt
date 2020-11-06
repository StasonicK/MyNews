package com.eburg_soft.mynews.data.datasource.network.models

import com.google.gson.annotations.SerializedName

data class IpResponse(

    @SerializedName("ip")
    var ip: String? = null,

    @SerializedName("hostname")
    var hostname: String? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("continent_code")
    var continentCode: String? = null,

    @SerializedName("continent_name")
    var continentName: String? = null,

    @SerializedName("country_code")
    var countryCode: String? = null,

    @SerializedName("country_name")
    var countryName: String? = null,

    @SerializedName("region_code")
    var regionCode: String? = null,

    @SerializedName("region_name")
    var regionName: String? = null,

    @SerializedName("city")
    var city: String? = null,

    @SerializedName("zip")
    var zip: String? = null,

    @SerializedName("latitude")
    var latitude: Double? = null,

    @SerializedName("longitude")
    var longitude: Double? = null,

    @SerializedName("location")
    var location: Location? = null,

    @SerializedName("time_zone")
    var timeZone: TimeZone? = null,

    @SerializedName("currency")
    var currency: Currency? = null,

    @SerializedName("connection")
    var connection: Connection? = null,

    @SerializedName("security")
    var security: Security? = null
) {
}

data class Connection(

    @SerializedName("asn")
    var asn: Int? = null,

    @SerializedName("isp")
    var isp: String? = null
) {

}

data class Currency(

    @SerializedName("code")
    var code: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("plural")
    var plural: String? = null,

    @SerializedName("symbol")
    var symbol: String? = null,

    @SerializedName("symbol_native")
    var symbolNative: String? = null
) {
}

data class Language(

    @SerializedName("code")
    var code: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("native")
    var _native: String? = null
) {
}

data class Location(

    @SerializedName("geoname_id")
    var geonameId: Int? = null,

    @SerializedName("capital")
    var capital: String? = null,

    @SerializedName("languages")
    var languages: List<Language>? = null,

    @SerializedName("country_flag")
    var countryFlag: String? = null,

    @SerializedName("country_flag_emoji")
    var countryFlagEmoji: String? = null,

    @SerializedName("country_flag_emoji_unicode")
    var countryFlagEmojiUnicode: String? = null,

    @SerializedName("calling_code")
    var callingCode: String? = null,

    @SerializedName("is_eu")
    var isEu: Boolean? = null
) {
}

data class Security(

    @SerializedName("is_proxy")
    var isProxy: Boolean? = null,

    @SerializedName("proxy_type")
    var proxyType: Any? = null,

    @SerializedName("is_crawler")
    var isCrawler: Boolean? = null,

    @SerializedName("crawler_name")
    var crawlerName: Any? = null,

    @SerializedName("crawler_type")
    var crawlerType: Any? = null,

    @SerializedName("is_tor")
    var isTor: Boolean? = null,

    @SerializedName("threat_level")
    var threatLevel: String? = null,

    @SerializedName("threat_types")
    var threatTypes: Any? = null
) {
}

data class TimeZone(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("current_time")
    var currentTime: String? = null,

    @SerializedName("gmt_offset")
    var gmtOffset: Int? = null,

    @SerializedName("code")
    var code: String? = null,

    @SerializedName("is_daylight_saving")
    var isDaylightSaving: Boolean? = null
) {
}