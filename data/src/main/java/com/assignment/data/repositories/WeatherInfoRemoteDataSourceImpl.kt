package com.assignment.data.repositories

import com.assignment.data.api.WeatherApi
import com.assignment.data.api.WeatherApiResponse
import com.assignment.data.mappers.WeatherInfoResponseMapper
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
                    return@withContext Result.Error(java.lang.Exception("WRONG INPUT"))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}
