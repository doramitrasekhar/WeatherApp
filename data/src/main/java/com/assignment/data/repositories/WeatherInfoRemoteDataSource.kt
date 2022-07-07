package com.assignment.data.repositories

import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo

interface WeatherInfoRemoteDataSource {
    suspend fun getWeatherInfo(countryName: String): Result<WeatherInfo>
}