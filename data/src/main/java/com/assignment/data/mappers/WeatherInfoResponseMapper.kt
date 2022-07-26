package com.assignment.data.mappers

import com.assignment.data.api.WeatherApiResponse
import com.assignment.domain.entities.Forecast
import com.assignment.domain.entities.WeatherInfo
import javax.inject.Inject

class WeatherInfoResponseMapper @Inject constructor() : Mapper<WeatherInfo, WeatherApiResponse> {
    override fun mapToDomainLayer(input: WeatherApiResponse): WeatherInfo {
        return WeatherInfo(input.description, input.forecast.map {
            Forecast(it.day, it.temperature, it.wind)
        }, input.temperature, input.wind)
    }
}