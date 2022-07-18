package com.assignment.weatherapp.util

import com.assignment.weatherapp.entities.ErrorUIModel
import com.assignment.weatherapp.entities.WeatherInfoResult

/**
 * Model class to load weather info status
 */
data class WeatherInfoState(
    val isLoading: Boolean = false,
    val data: WeatherInfoResult? = null,
    val error: ErrorUIModel? = null
)