package com.assignment.weatherapp.mappers

import com.assignment.domain.entities.WeatherInfo
import com.assignment.weatherapp.entities.ForecastResult
import com.assignment.weatherapp.entities.WeatherInfoResult

class WeatherInfoResultMapper {
    fun toWeatherInfo(response: WeatherInfo): WeatherInfoResult {
        return WeatherInfoResult(response.description, response.forecast.map {
            ForecastResult(it.day, it.temperature, it.wind)
        }, response.temperature, response.wind)
    }
}