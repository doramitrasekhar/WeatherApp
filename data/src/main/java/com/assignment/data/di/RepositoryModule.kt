package com.assignment.data.di

import com.assignment.data.error.GeneralErrorHandlerImpl
import com.assignment.data.repositories.*
import com.assignment.domain.error.ErrorHandler
import com.assignment.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideWeatherInfoRemoteDataSource(
        weatherInfoRemoteDataSourceImpl: WeatherInfoRemoteDataSourceImpl
    ): WeatherInfoRemoteDataSource

    @Binds
    abstract fun provideWeatherInfoLocalDataSource(
        weatherInfoLocalDataSourceImpl: WeatherInfoLocalDataSourceImpl
    ): WeatherInfoLocalDataSource

    @Binds
    abstract fun provideWeatherRepoImpl(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    abstract fun provideErrorHandler(generalErrorHandlerImpl: GeneralErrorHandlerImpl): ErrorHandler
}