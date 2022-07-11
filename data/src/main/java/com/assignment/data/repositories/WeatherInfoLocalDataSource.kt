package com.assignment.data.repositories

import com.assignment.domain.entities.WeatherInfo

interface WeatherInfoLocalDataSource {
    suspend fun saveWeatherInfo(countryName: String, weatherInfo: WeatherInfo)
}