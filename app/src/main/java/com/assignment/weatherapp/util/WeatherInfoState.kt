package com.assignment.weatherapp.util

import com.assignment.weatherapp.entities.ErrorUIModel
import com.assignment.weatherapp.entities.WeatherInfoResult

/**
 * Model class to load weather info status
 */
sealed class WeatherInfoState {
    class Success(var data: WeatherInfoResult) : WeatherInfoState()
    class Error(var error: ErrorUIModel) : WeatherInfoState()
    class Loading(var isLoading: Boolean) : WeatherInfoState()
}