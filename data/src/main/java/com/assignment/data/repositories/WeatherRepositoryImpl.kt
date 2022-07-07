package com.assignment.data.repositories

import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.repositories.WeatherRepository

class WeatherRepositoryImpl(private val remoteDataSource: WeatherInfoRemoteDataSource) :
    WeatherRepository {
    override suspend fun getRemoteWeatherInfo(countryName: String): Result<WeatherInfo> {
        return remoteDataSource.getWeatherInfo(countryName)
    }
}