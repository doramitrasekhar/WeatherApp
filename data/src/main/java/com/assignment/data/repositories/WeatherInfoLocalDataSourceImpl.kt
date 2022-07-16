package com.assignment.data.repositories

import com.assignment.data.db.WeatherDao
import com.assignment.data.di.IoDispatcher
import com.assignment.data.mappers.WeatherEntityMapper
import com.assignment.domain.entities.WeatherEntityInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherInfoLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val weatherEntityMapper: WeatherEntityMapper
) : WeatherInfoLocalDataSource {
    override suspend fun saveWeatherInfo(weatherEntityInfo: WeatherEntityInfo) =
        withContext(dispatcher) {
            weatherDao.saveWeatherInfo(weatherEntityMapper.mapToDomainLayer(weatherEntityInfo))
        }
}