package com.assignment.weatherapp.mappers

import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.entities.WeatherInfo
import com.assignment.weatherapp.entities.ForecastResult
import com.assignment.weatherapp.entities.WeatherInfoResult
import javax.inject.Inject

class WeatherInfoResultMapper @Inject constructor() : Mapper<WeatherInfoResult, WeatherInfo> {
    override fun mapToView(input: WeatherInfo): WeatherInfoResult {
        return WeatherInfoResult(input.description, input.forecast.map {
            ForecastResult(it.day, it.temperature, it.wind)
        }, input.temperature, input.wind)
    }

    fun mapToWeatherEntityInfo(countryName: String, input: WeatherInfo): WeatherEntityInfo {
        return WeatherEntityInfo(countryName = countryName, weatherInfo = input)
    }
}