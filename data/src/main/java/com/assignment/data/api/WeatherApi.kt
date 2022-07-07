package com.assignment.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi{
    @GET("weather/{country}")
    suspend fun getWeatherDetails(@Path("country") countryName: String): Response<WeatherApiResponse>
}