package com.assignment.data.repositories

import com.assignment.data.db.WeatherDao
import com.assignment.data.mappers.WeatherEntityMapper
import com.assignment.domain.entities.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherInfoLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
) : WeatherInfoLocalDataSource {
    override suspend fun saveWeatherInfo(countryName: String,weatherInfo: WeatherInfo) = withContext(Dispatchers.IO) {
        weatherDao.saveWeatherInfo(WeatherEntityMapper().toWeatherInfoEntity(countryName,weatherInfo))
    }
}