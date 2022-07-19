package com.assignment.data.di

import com.assignment.data.error.GeneralErrorHandlerImpl
import com.assignment.data.repositories.*
import com.assignment.domain.error.ErrorHandler
import com.assignment.domain.repositories.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideWeatherRepoImpl(
        weatherInfoLocalDataSourceImpl: WeatherInfoLocalDataSourceImpl,
        weatherInfoRemoteSourceImpl: WeatherInfoRemoteDataSourceImpl
    ): WeatherRepositoryImpl {
        return WeatherRepositoryImpl(weatherInfoLocalDataSourceImpl,weatherInfoRemoteSourceImpl)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository {
        return weatherRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideErrorHandler(): ErrorHandler {
        return GeneralErrorHandlerImpl()
    }
}