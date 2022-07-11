package com.assignment.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.assignment.domain.entities.WeatherInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


@ProvidedTypeConverter
class Converters {
    private val gson = Gson()
    @TypeConverter
    fun toWeatherInfoJSON(weatherInfo: WeatherInfo): String {
        return gson.toJson(weatherInfo)
    }

    @TypeConverter
    fun fromWeatherInfoJSON(json: String): WeatherInfo {
        val type = object : TypeToken<WeatherInfo>() {}.type
        return Gson().fromJson(json, type)
    }
}