package com.assignment.domain.usecases

import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.repositories.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SaveWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(countryName: String, weatherInfo: WeatherInfo) =
        weatherRepository.saveWeatherInfoInDB(countryName, weatherInfo)
}