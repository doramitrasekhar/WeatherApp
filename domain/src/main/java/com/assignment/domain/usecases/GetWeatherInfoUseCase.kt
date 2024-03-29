package com.assignment.domain.usecases

import com.assignment.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(country: String) = weatherRepository.getRemoteWeatherInfo(country)
}

