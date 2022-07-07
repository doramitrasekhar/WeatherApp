package com.assignment.weatherapp.entities

data class WeatherInfoResult(
    val description: String,
    val forecast: List<ForecastResult>,
    val temperature: String,
    val wind: String
)

data class ForecastResult(
    val day: String,
    val temperature: String,
    val wind: String
)