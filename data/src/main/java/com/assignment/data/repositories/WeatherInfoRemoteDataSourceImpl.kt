package com.assignment.data.repositories

import com.assignment.data.api.WeatherApi
import com.assignment.data.api.WeatherApiResponse
import com.assignment.data.di.IoDispatcher
import com.assignment.data.mappers.WeatherInfoResponseMapper
import com.assignment.data.util.Constants
import com.assignment.data.util.Constants.SERVICE_UNAVAILABLE_CODE
import com.assignment.domain.common.ErrorEntity
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.error.ErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class WeatherInfoRemoteDataSourceImpl @Inject constructor(
    private val service: WeatherApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val mapper: WeatherInfoResponseMapper,
    private val errorHandler: ErrorHandler,
) : WeatherInfoRemoteDataSource {
    override suspend fun getWeatherInfo(countryName: String): Result<WeatherInfo> =
        withContext(dispatcher) {
            try {
                val response: Response<WeatherApiResponse> = service.getWeatherDetails(countryName)
                val weatherApiResponse = response.body()
                weatherApiResponse?.let {
                    val dataLayerResponse = mapper.mapToDomainLayer(weatherApiResponse)
                    if (response.isSuccessful && mapper.isValidResponse(dataLayerResponse)) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext getWrongInputResult()
                    }
                } ?: run {
                    return@withContext getErrorResult(response)
                }
            } catch (e: Exception) {
                return@withContext getExceptionResult(e)
            }
        }

    /**
     * gets the exception Error result
     */
    private fun getExceptionResult(e: Exception): Result.Error<WeatherInfo> {
        return Result.Error(
            message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
            errorEntity = errorHandler.getError(e)
        )
    }

    /**
     * gets the wrong Input Error Result
     */
    private fun getWrongInputResult(): Result.Error<WeatherInfo> {
        return Result.Error(
            message = Constants.WRONG_INPUT,
            errorEntity = ErrorEntity.Unknown
        )
    }

    /**
     * Gets the error Result
     */
    private fun getErrorResult(response: Response<WeatherApiResponse>): Result.Error<WeatherInfo> {
        return when (response.raw().code) {
            SERVICE_UNAVAILABLE_CODE -> Result.Error(
                message = Constants.SERVICE_UNAVAILABLE,
                errorEntity = ErrorEntity.ServiceUnavailable
            )
            else -> {
                Result.Error(
                    message = Constants.NOT_FOUND,
                    errorEntity = ErrorEntity.NotFound
                )
            }
        }
    }
}
