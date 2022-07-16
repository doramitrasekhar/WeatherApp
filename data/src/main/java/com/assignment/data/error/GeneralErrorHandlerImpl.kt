package com.assignment.data.error

import com.assignment.domain.common.ErrorEntity
import com.assignment.domain.error.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class GeneralErrorHandlerImpl @Inject constructor() : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> {
                when (throwable.code()) {
                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied
                    // unavailable service
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Network
                }
            }
            else -> ErrorEntity.Unknown
        }
    }
}