package com.assignment.weatherapp.ui.util

import android.annotation.SuppressLint
import androidx.core.text.isDigitsOnly
import com.assignment.weatherapp.entities.WeatherStatus
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(dateFormat: String): String =
        SimpleDateFormat(dateFormat).format(Date())

    fun getWeatherStatusFromTemperature(tempVal: Double): WeatherStatus {
        return if (tempVal > 18) {
            WeatherStatus.SUNNY
        } else if (tempVal > 0) {
            WeatherStatus.RAINY
        } else {
            WeatherStatus.SNOWY
        }
    }

    fun getNumberFromString(input: String): String {
        return input.replace("[^0-9]".toRegex(), "")
    }

    fun getWeatherStatus(description: String, tempVal: String): WeatherStatus {
        if (description == "Sunny") {
            return WeatherStatus.SUNNY
        } else if (description == "Light rain shower") {
            return WeatherStatus.RAINY
        } else if (description == "Partly cloudy") {
            return WeatherStatus.CLOUDY
        } else if (description == "Clear") {
            return WeatherStatus.SUNNY
        } else if (description == "Heavy rain at times") {
            return WeatherStatus.RAINY
        } else if (description.contains("rain", ignoreCase = true)) {
            return WeatherStatus.RAINY
        } else if (description.contains("snow", ignoreCase = true)) {
            return WeatherStatus.SNOWY
        } else {
            val temp = getNumberFromString(tempVal)
            if (temp.isDigitsOnly()) {
                return getWeatherStatusFromTemperature(temp.toDouble())
            } else {
                return WeatherStatus.SUNNY
            }
        }
    }

}