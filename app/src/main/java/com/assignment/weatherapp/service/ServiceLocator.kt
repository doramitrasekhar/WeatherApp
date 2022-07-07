package com.assignment.weatherapp.service

import com.assignment.data.repositories.WeatherRepositoryImpl
import javax.inject.Inject

class ServiceLocator @Inject constructor(private val weatherRepositoryImpl: WeatherRepositoryImpl) {
    fun provideWeatherRepository(): WeatherRepositoryImpl {
        synchronized(this) {
            return weatherRepositoryImpl
        }
    }
}