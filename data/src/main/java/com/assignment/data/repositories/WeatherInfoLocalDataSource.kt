package com.assignment.data.repositories

import com.assignment.domain.entities.WeatherEntityInfo

interface WeatherInfoLocalDataSource {
    suspend fun saveWeatherInfo(weatherEntityInfo: WeatherEntityInfo)
}