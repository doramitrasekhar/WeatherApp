package com.assignment.weatherapp.core

import com.assignment.domain.entities.WeatherInfo
import com.assignment.weatherapp.entities.WeatherInfoResult
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.util.AppConstants
import com.assignment.weatherapp.util.Resource
import com.google.gson.Gson

object MockResponse {

    const val countryName = "LONDON"

    const val error_message = "something went wrong"

    fun getWeatherResult(): WeatherInfo {
        val jsonString =
            "{\"temperature\":\"14 °C\",\"wind\":\"7 km/h\",\"description\":\"Clear\",\"forecast\":[{\"day\":\"1\",\"temperature\":\"25 °C\",\"wind\":\"18 km/h\"},{\"day\":\"2\",\"temperature\":\" °C\",\"wind\":\" km/h\"},{\"day\":\"3\",\"temperature\":\"10 °C\",\"wind\":\" km/h\"}]}"
        return Gson().fromJson(jsonString, WeatherInfo::class.java)
    }

    fun getSuccessResult(): com.assignment.domain.common.Result<WeatherInfo> {
        return  com.assignment.domain.common.Result.Success(getWeatherResult())
    }

    fun getFailureResult(): com.assignment.domain.common.Result<WeatherInfo> {
        return  com.assignment.domain.common.Result.Error(Exception(error_message))
    }

    fun getSuccessResource(weatherInfo: WeatherInfo): Resource<WeatherInfoResult> {
        return  Resource.success(
            WeatherInfoResultMapper().toWeatherInfo(
                weatherInfo
        ))
    }

    fun getErrorResource(): Resource<Nothing> {
        return Resource.error(AppConstants.SOMETHING_WENT_WRONG, null)
    }
}