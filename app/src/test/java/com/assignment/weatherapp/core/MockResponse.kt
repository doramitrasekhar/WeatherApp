package com.assignment.weatherapp.core

import android.content.Context
import com.assignment.data.util.Constants
import com.assignment.domain.common.ErrorEntity
import com.assignment.domain.entities.WeatherInfo
import com.assignment.weatherapp.mappers.WeatherInfoErrorViewMapper
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.util.WeatherInfoState
import com.google.gson.Gson
import com.assignment.domain.common.Result as MyResult

object MockResponse {

    const val countryName = "LONDON"

    const val error_message = "something went wrong"

    fun getWeatherResult(): WeatherInfo {
        val jsonString =
            "{\"temperature\":\"14 °C\",\"wind\":\"7 km/h\",\"description\":\"Clear\",\"forecast\":[{\"day\":\"1\",\"temperature\":\"25 °C\",\"wind\":\"18 km/h\"},{\"day\":\"2\",\"temperature\":\" °C\",\"wind\":\" km/h\"},{\"day\":\"3\",\"temperature\":\"10 °C\",\"wind\":\" km/h\"}]}"
        return Gson().fromJson(jsonString, WeatherInfo::class.java)
    }

    fun getSuccessResult(): MyResult<WeatherInfo> {
        return MyResult.Success(getWeatherResult())
    }

    fun getLiveDataSuccessResult(): WeatherInfoState {
        return WeatherInfoState(data = WeatherInfoResultMapper().mapToView(getWeatherResult()))
    }


    fun getLiveDataErrorResult(mContext: Context): WeatherInfoState {
        return WeatherInfoState(error = WeatherInfoErrorViewMapper(context = mContext).mapToView(
            getErrorResult().errorEntity))
    }

    fun getErrorResult(): MyResult.Error<WeatherInfo> {
        return MyResult.Error(
            message = Constants.WRONG_INPUT,
            errorEntity = ErrorEntity.Unknown
        )
    }
}