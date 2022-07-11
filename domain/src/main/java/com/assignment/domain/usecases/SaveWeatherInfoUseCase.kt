package com.assignment.domain.usecases

import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.repositories.WeatherRepository

class SaveWeatherInfoUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(countryName: String, weatherInfo: WeatherInfo) =
        weatherRepository.saveWeatherInfoInDB(countryName, weatherInfo)
}