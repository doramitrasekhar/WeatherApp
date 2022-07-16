package com.assignment.domain.repositories

import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.entities.WeatherInfo

interface WeatherRepository {
    suspend fun getRemoteWeatherInfo(countryName: String): Result<WeatherInfo>
    suspend fun saveWeatherInfoInDB(weatherEntityInfo: WeatherEntityInfo)
}