package com.assignment.data.mappers

import com.assignment.data.api.WeatherApiResponse
import com.assignment.domain.entities.Forecast
import com.assignment.domain.entities.WeatherInfo

class WeatherInfoResponseMapper {
    fun toWeatherInfo(response: WeatherApiResponse): WeatherInfo {
        return WeatherInfo(response.description, response.forecast.map {
            Forecast(it.day, it.temperature, it.wind)
        }, response.temperature, response.wind)
    }
}