package com.assignment.data.repositories

import com.assignment.data.api.WeatherApi
import com.assignment.data.api.WeatherApiResponse
import com.assignment.data.mappers.WeatherInfoResponseMapper
import com.assignment.data.util.Constants
import com.assignment.data.util.Constants.SERVICE_UNAVAILABLE_CODE
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherInfoRemoteDataSourceImpl @Inject constructor(
    private val service: WeatherApi
) :
    WeatherInfoRemoteDataSource {

    override suspend fun getWeatherInfo(countryName: String): Result<WeatherInfo> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getWeatherDetails(countryName)
                if (response.isSuccessful) {
                    val weatherApiResponse: WeatherApiResponse? = response.body()
                    if (weatherApiResponse != null) {
                        if (weatherApiResponse.wind.isNotEmpty()
                            && weatherApiResponse.description.isNotEmpty()
                            && weatherApiResponse.temperature.isNotEmpty()
                        ) {
                            return@withContext Result.Success(
                                WeatherInfoResponseMapper().toWeatherInfo(
                                    response.body()!!
                                )
                            )
                        }
                    }
                    return@withContext Result.Error(java.lang.Exception(Constants.WRONG_INPUT))
                } else {
                    if (response.raw().code == SERVICE_UNAVAILABLE_CODE) {
                        return@withContext Result.Error(
                            Exception(Constants.SERVICE_UNAVAILABLE)
                        )
                    }
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            } catch (e: HttpException) {
                return@withContext Result.Error(
                    Exception(e.localizedMessage ?: Constants.UNKNOWN_ERROR)
                )
            } catch (e: IOException) {
                return@withContext Result.Error(
                    Exception(e.localizedMessage ?: Constants.CONNECTIVITY_ERROR)
                )
            } catch (e: Exception) {
                return@withContext Result.Error(
                    Exception(e.localizedMessage ?: Constants.CONNECTIVITY_ERROR)
                )
            }
        }
}
