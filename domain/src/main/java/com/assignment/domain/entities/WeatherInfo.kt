package com.assignment.domain.entities

data class WeatherInfo(
    val description: String,
    val forecast: List<Forecast>,
    val temperature: String,
    val wind: String
)

data class Forecast(
    val day: String,
    val temperature: String,
    val wind: String
)