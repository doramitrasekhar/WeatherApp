package com.assignment.data.di

import android.content.Context
import androidx.room.Room
import com.assignment.data.db.Converters
import com.assignment.data.db.WeatherDao
import com.assignment.data.db.WeatherInfoDatabase
import com.assignment.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ): WeatherInfoDatabase {
        return Room.databaseBuilder(
            app,
            WeatherInfoDatabase::class.java,
            Constants.WEATHER_INFO_DB_NAME
        ).addTypeConverter(Converters()).build()
    }

    @Singleton
    @Provides
    fun provideYourDao(db: WeatherInfoDatabase): WeatherDao {
        return db.weatherDao()
    }
}