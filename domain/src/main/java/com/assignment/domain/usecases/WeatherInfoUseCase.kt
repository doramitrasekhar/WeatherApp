package com.assignment.domain.usecases

import com.assignment.domain.repositories.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class WeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(country: String) = weatherRepository.getRemoteWeatherInfo(country)
}

