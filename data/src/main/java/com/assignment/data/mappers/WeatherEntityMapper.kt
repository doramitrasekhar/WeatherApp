package com.assignment.data.mappers

import com.assignment.data.entities.WeatherInfoEntity
import com.assignment.domain.entities.WeatherEntityInfo
import javax.inject.Inject

class WeatherEntityMapper @Inject constructor() : Mapper<WeatherInfoEntity, WeatherEntityInfo> {
    override fun mapToDomainLayer(input: WeatherEntityInfo): WeatherInfoEntity {
        return WeatherInfoEntity(countryName = input.countryName, weatherInfo = input.weatherInfo)
    }
}