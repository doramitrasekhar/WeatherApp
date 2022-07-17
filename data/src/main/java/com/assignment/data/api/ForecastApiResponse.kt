package com.assignment.data.api

import com.google.gson.annotations.SerializedName

data class ForecastApiResponse(
    @SerializedName("day")
    val day: String,
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("wind")
    val wind: String
)
