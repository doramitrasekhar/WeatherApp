package com.assignment.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.assignment.data.db.Converters
import com.assignment.data.util.Constants.WEATHER_INFO_TABLE_NAME
import com.assignment.domain.entities.WeatherInfo


@Entity(tableName = WEATHER_INFO_TABLE_NAME)
data class WeatherInfoEntity(
    @PrimaryKey
    val countryName: String,

    @TypeConverters(Converters::class)
    val weatherInfo: WeatherInfo
)