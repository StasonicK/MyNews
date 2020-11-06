package com.eburg_soft.mynews.data.datasource.network.apis

import com.eburg_soft.mynews.core.IP_API_KEY
import com.eburg_soft.mynews.data.datasource.network.models.IpResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Describes rules to fetch the details about IP from "ipstack API".
 *
 * Read the documentation [here](https://ipstack.com/documentation)
 */
interface IpApi : BaseApi {

    @GET("{ip}?access_key=$IP_API_KEY")
    suspend fun getIpDetailsFromApi(
        @Path("ip") ip: String
    ): IpResponse?
}