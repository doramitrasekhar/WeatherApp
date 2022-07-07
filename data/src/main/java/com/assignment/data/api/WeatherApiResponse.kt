package com.assignment.data.api

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("forecast")
    val forecast: List<ForecastApiResponse>,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("wind")
    val wind: String
)

data class ForecastApiResponse(
    @SerializedName("day")
    val day: String,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("wind")
    val wind: String
)