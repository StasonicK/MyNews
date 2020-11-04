package com.eburg_soft.mynews.data.datasource.network.exceptions

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun handleNetworkExceptions(ex: Exception): Exception {
    return when (ex) {
        is IOException -> NetworkConnectionException()
        is UnknownHostException -> NetworkConnectionException()
        is HttpException -> apiErrorFromCodeException(ex.code())
        else -> GenericNetworkException()
    }
}

private fun apiErrorFromCodeException(code: Int): Exception {
    return when (code) {
        400 -> BadRequestException()

        else ->
            GenericNetworkException()
    }
}

