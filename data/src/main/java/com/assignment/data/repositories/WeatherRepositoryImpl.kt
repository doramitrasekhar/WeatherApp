package com.assignment.data.repositories

import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.repositories.WeatherRepository

class WeatherRepositoryImpl(
    private val localDataSource: WeatherInfoLocalDataSource,
    private val remoteDataSource: WeatherInfoRemoteDataSource
) :
    WeatherRepository {

    override suspend fun saveWeatherInfoInDB(weatherEntityInfo: WeatherEntityInfo) {
        return localDataSource.saveWeatherInfo(weatherEntityInfo)
    }

    override suspend fun getRemoteWeatherInfo(countryName: String): Result<WeatherInfo> {
        return remoteDataSource.getWeatherInfo(countryName)
    }
}