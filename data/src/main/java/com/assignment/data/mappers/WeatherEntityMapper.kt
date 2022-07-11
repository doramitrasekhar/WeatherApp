package com.assignment.data.mappers

import com.assignment.data.entities.WeatherInfoEntity
import com.assignment.domain.entities.Forecast
import com.assignment.domain.entities.WeatherInfo

class WeatherEntityMapper {
    fun toWeatherInfoEntity(countryInfo: String, weatherInfo: WeatherInfo): WeatherInfoEntity {
        return WeatherInfoEntity(
            countryName = countryInfo,
            weatherInfo = weatherInfo
        )
    }

    fun toWeatherInfo(weatherInfoEntity: WeatherInfoEntity): WeatherInfo {
        return WeatherInfo(
            weatherInfoEntity.weatherInfo.description,
            weatherInfoEntity.weatherInfo.forecast.map {
                Forecast(it.day, it.temperature, it.wind)
            },
            weatherInfoEntity.weatherInfo.temperature, weatherInfoEntity.weatherInfo.wind
        )
    }
}