package com.assignment.weatherapp.entities

/**
 * Weather Info Model Class
 */
data class WeatherInfoResult(
    val description: String,
    val forecast: List<ForecastResult>,
    val temperature: String,
    val wind: String
)

/**
 * Forecast result Model class
 */
data class ForecastResult(
    val day: String,
    val temperature: String,
    val wind: String
)