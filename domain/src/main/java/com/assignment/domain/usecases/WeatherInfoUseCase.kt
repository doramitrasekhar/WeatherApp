package com.assignment.domain.usecases

import com.assignment.domain.repositories.WeatherRepository

class WeatherInfoUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(country: String) = weatherRepository.getRemoteWeatherInfo(country)
}

